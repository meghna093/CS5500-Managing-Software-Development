package edu.northeastern.ccs.im.server;

import edu.northeastern.ccs.im.dao.GroupMongo;
import edu.northeastern.ccs.im.dao.UserMongo;
import edu.northeastern.ccs.im.dao.exceptions.UserDoesNotExistException;
import edu.northeastern.ccs.im.data_models.User;
import edu.northeastern.ccs.im.data_models.Message.MessageType;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.messaging.PrintNetNB;
import edu.northeastern.ccs.im.messaging.ScanNetNB;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.bson.Document;

import edu.northeastern.ccs.im.dao.exceptions.GroupDoesNotExistException;

/**
 * Instances of this class handle all of the incoming communication from a
 * single IM client. Instances are created when the client signs-on with the
 * server. After instantiation, it is executed periodically on one of the
 * threads from the thread pool and will stop being run only when the client
 * signs off.
 * <p>
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/4.0/. It is based on work
 * originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @version 1.3
 */
public class ClientRunnable implements Runnable {

  /**
   * Number of milliseconds after which we terminate a client due to inactivity.
   * This is currently equal to 10 minutes.
   */
  private static final long TERMINATE_AFTER_INACTIVE_INITIAL_IN_MS = 600000;

  /**
   * Time at which the client should be terminated due to lack of activity.
   */
  private GregorianCalendar terminateInactivity;

  /**
   * Socket over which the conversation with the single client occurs.
   */
  private final SocketChannel socket;

  /**
   * Utility class which we will use to receive communication from this client.
   */
  private ScanNetNB input;

  /**
   * Utility class which we will use to send communication to this client.
   */
  private PrintNetNB output;

  /**
   * Whether this client has been initialized, set its user name, and is ready to
   * receive messages.
   */
  private boolean initialized;

  /**
   * The future that is used to schedule the client for execution in the thread
   * pool.
   */
  private ScheduledFuture<ClientRunnable> runnableMe;

  /**
   * Collection of messages queued up to be sent to this client.
   */
  private Queue<Message> waitingList;

  /**
   * Initialize logger for the class.
   */
  private static final Logger logger = Logger.getLogger(ClientRunnable.class.getName());

  /**
   * The Mongo User Collection.
   */
  private UserMongo usermongo;

  private GroupMongo groupmongo;

  private String user;

  private String group;

  private boolean terminate;

  private static final String ACK_SENT = "ack sent";

  private static final String ADMIN = "admin";

  /**
   * Create a new thread with which we will communicate with this single client.
   *
   * @param client SocketChannel over which we will communicate with this new
   *               client
   * @throws IOException Exception thrown if we have trouble completing this
   *                     connection
   */
  public ClientRunnable(SocketChannel client) throws IOException {
    // Set up the SocketChannel over which we will communicate.
    socket = client;
    socket.configureBlocking(false);
    // Create the class we will use to receive input
    input = new ScanNetNB(socket);
    // Create the class we will use to send output
    output = new PrintNetNB(socket);
    // Mark that we are not initialized
    initialized = false;
    // Create the queue of messages to be sent
    waitingList = new ConcurrentLinkedQueue<>();
    // Mark that the client is active now and start the timer until we
    // terminate for inactivity.
    terminateInactivity = new GregorianCalendar();
    terminateInactivity
            .setTimeInMillis(terminateInactivity.getTimeInMillis() + TERMINATE_AFTER_INACTIVE_INITIAL_IN_MS);

    usermongo = UserMongo.getInstance();
    groupmongo = GroupMongo.getInstance();
  }

  /**
   * Perform the periodic actions needed to work with this client.
   */
  public void run() {
    // The client must be initialized before we can do anything else
    if (!initialized) {
      checkForInitialization();
    } else {
      processIfInitialized();
    }
    // Finally, check if this client have been inactive for too long and,
    // when they have, terminate the client.
    if (!terminate && terminateInactivity.before(new GregorianCalendar())) {
      logger.log(Level.WARNING, () -> "Timing out or forcing off a user " + user);
      terminateClient();
    }
  }

  /**
   * Check to see for an initialization attempt and process the message sent.
   */
  private void checkForInitialization() {
    // Check if there are any input messages to read
    if (input.hasNextMessage()) {
      // If a message exists, try to use it to initialize the connection
      Message msg = input.nextMessage();
      if (msg.getType() == MessageType.LOGIN) {
        logger.info("login");
        String username = msg.getSender();
        if (username != null) {
          // try logging in
          String password = msg.getContent();
          checkForInitializationHelperLogin(password, username);
        } else {
          initialized = false;
        }
        if (!initialized) {
          sendMessage(Message.makeNoAcknowledgeMessage());
        }
      } else if (msg.getType() == MessageType.REGISTER) {
        checkForInitializationHelperRegister(msg);
        if (!initialized) {
          sendMessage(Message.makeNoAcknowledgeMessage());
        }
      }
    }
  }

  /**
   * Checks if user credentials exist to process login attempt. Sets initialized to true if user
   * credentials exist, to false otherwise. Sends Acknowledgment message if true, NoAcknowledgment
   * message if false.
   *
   * @param password the password of the user.
   * @param username the username of the user.
   */
  private void checkForInitializationHelperLogin(String password, String username) {
    try {
      Document temp = usermongo.findUserByLogin(username, password);
      if (temp == null) {
        initialized = false;
      } else {
        user = username;
        // Update the time until we terminate this client due to inactivity.
        terminateInactivity.setTimeInMillis(
                new GregorianCalendar().getTimeInMillis() + TERMINATE_AFTER_INACTIVE_INITIAL_IN_MS);
        // Set that the client is initialized.
        initialized = true;
        logger.info(ACK_SENT);
        enqueueMessage(Message.makeAcknowledgeMessage(username));
        // send any messages in the queue
        User u = UserMongo.makeUser(temp);
        for (Message m : u.getMessageQueue()) {
          enqueueMessage(m);
        }
        usermongo.clearMessageQueue(u);
      }
    } catch (Exception e) {
      initialized = false;
    }
  }

  /**
   * Checks if username does not exist to process register attempt. Sets initialized to true if
   * username does not exist, and to false otherwise. Sends Acknowledgment message if true,
   * NoAcknowledgment message if false.
   *
   * @param msg the register message from the client.
   */
  private void checkForInitializationHelperRegister(Message msg) {
    logger.info("register");
    String username = msg.getSender();
    if (username != null) {
      // check if username already taken
      try {
        user = UserMongo.makeUser(usermongo.findUserByUsername(username)).getUsername();
        initialized = false;
      } catch (Exception e) {
        if (user == null) {
          user = username;
          String password = msg.getContent();
          User userObj = new User(username, password);
          usermongo.createUser(userObj);
          // Update the time until we terminate this client due to inactivity.
          terminateInactivity.setTimeInMillis(
                  new GregorianCalendar().getTimeInMillis() + TERMINATE_AFTER_INACTIVE_INITIAL_IN_MS);
          // Set that the client is initialized.
          initialized = true;
          logger.info(ACK_SENT);
          enqueueMessage(Message.makeAcknowledgeMessage(username));
        }
      }
    }
  }

  /**
   * Run process if client is initialized.
   */
  private void processIfInitialized() {
    // Client has already been initialized, so we should first check
    // if there are any input messages.
    if (input.hasNextMessage()) {
      processMessage();
    }

    if (!waitingList.isEmpty()) {
      do {
        Message msg = waitingList.remove();
        sendMessage(msg);
      } while (!waitingList.isEmpty());
    }
  }

  /**
   * Process next message from ScanNetNB.
   */
  private void processMessage() {
    // Get the next message
    Message msg = input.nextMessage();
    // Update the time until we terminate the client for inactivity.
    terminateInactivity.setTimeInMillis(
            new GregorianCalendar().getTimeInMillis() + TERMINATE_AFTER_INACTIVE_INITIAL_IN_MS);
    switch (msg.getType()) {
      case ADD_USER:
        runHelperAddUser(msg);
        break;
      case USER_MESSAGE:
        runHelperMessage(msg);
        break;
      case GROUP_MESSAGE:
        runHelperMessage(msg);
        break;
      case CREATE_GROUP:
        runHelperCreateGroup(msg);
        break;
      case DELETE_GROUP:
        try {
          groupmongo.deleteGroup(this.group);
          enqueueMessage(Message.makeAcknowledgeMessage(this.group));
          this.group = null;
        } catch (GroupDoesNotExistException e) {
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }

        break;
      case DELETE_USER:
        try {
          usermongo.deleteUser(this.getUsername());
          terminate = true;
          enqueueMessage(Message.makeAcknowledgeMessage(null));
        } catch (Exception e) {
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }
        break;
      case FIND_USER:
        Document d = usermongo.findUserByUsername(msg.getContent());
        logger.log(Level.INFO, "Document: {0}", d);
        if (d != null) {
          logger.info(ACK_SENT);
          enqueueMessage(Message.makeAcknowledgeMessage(msg.getContent()));
        } else {
          logger.info("nack sent");
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }
        break;
      case JOIN_GROUP:
        runHelperJoinGroup(msg);
        break;
      case LEAVE_GROUP:
        enqueueMessage(Message.makeAcknowledgeMessage(group));
        group = null;
        break;
      case QUIT:
        // Stop sending the poor client message.
        terminate = true;
        // Reply with a quit message.
        terminateClient();
        break;
      case REMOVE_USER:
        try {
          usermongo.removeUserFromGroup(UserMongo.makeUser(usermongo.findUserByUsername(msg
                  .getContent())), GroupMongo.makeGroup(groupmongo.findGroupByName(group)));
          enqueueMessage(Message.makeAcknowledgeMessage(this.group));
        } catch (Exception e) {
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }
        break;
      case UPDATE_GROUP:
        try {
          String newName = msg.getContent();
          groupmongo.updateGroup(this.group, newName);
          this.group = newName;
          enqueueMessage(Message.makeAcknowledgeMessage(newName));
        } catch (Exception e) {
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }
        break;
      case UPDATE_USER:
        try {
          String newName = msg.getContent();
          usermongo.updateUser(msg.getSender(), newName);
          this.user = newName;
          enqueueMessage(Message.makeAcknowledgeMessage(newName));
        } catch (Exception e) {
          enqueueMessage(Message.makeNoAcknowledgeMessage());
        }
        break;
      case TRACK_GROUP:
        runHelperTrackGroup(msg);
        break;
      case TRACK_USER:
        runHelperTrackUser(msg);
        break;
      case STOP_LOGGING:
        logger.info(() -> "Stopping logging...");
        LogManager.getLogManager().reset();
        enqueueMessage(Message.makeAcknowledgeMessage(msg.getContent()));
        break;
      case START_LOGGING:
        try {
          LogManager.getLogManager().readConfiguration();
          logger.info(() -> "Started logging...");
          enqueueMessage(Message.makeAcknowledgeMessage(msg.getContent()));
          break;
        } catch (IOException e) {
          logger.warning(e.toString());
          enqueueMessage(Message.makeNoAcknowledgeMessage());
          break;
        }
      default:
        break;
      // Otherwise, ignore it (for now).
    }
  }

  private void runHelperTrackUser(Message msg) {
    String trackedUser = msg.getSender();
    String receivingGroup = msg.getReceiver();
    int duration = Integer.parseInt(msg.getContent());
    usermongo.addListener(trackedUser, receivingGroup, duration);
    enqueueMessage(Message.makeAcknowledgeMessage(user));
  }

  private void runHelperTrackGroup(Message msg) {
    String trackedGroup = msg.getSender();
    String receivingGroup = msg.getReceiver();
    int duration = Integer.parseInt(msg.getContent());
    groupmongo.addListener(trackedGroup, receivingGroup, duration);
    enqueueMessage(Message.makeAcknowledgeMessage(user));
  }

  /**
   * Helper method for processing Broadcast message.
   *
   * @param msg the user or group message.
   */
  private void runHelperMessage(Message msg) {
    // Check if the message is legal formatted
    if (messageChecks(msg)) {
      try {
        if (msg.getType() == MessageType.USER_MESSAGE) {
          determineReceiverIsValid(msg.getReceiver());
          logger.info("Private User Message");
          String sender = msg.getSender().toUpperCase();
          String receiver = msg.getReceiver().toUpperCase();
          String groupName;

          int alphanumericSort = sender.compareTo(receiver);

          if (alphanumericSort < 0) {
            groupName = sender + receiver;
          } else {
            groupName = receiver + sender;
          }

          groupName = Integer.toString(groupName.hashCode());

          try {
            groupName = GroupMongo.makeGroup(groupmongo.findGroupByName(groupName)).getGroupName();
          } catch (Exception e) {
            groupmongo.createGroup(new Group(groupName));
            logger.info("private group made");
            usermongo.addUserToGroup(UserMongo.makeUser(usermongo.findUserByUsername(msg.getSender())),
                    GroupMongo.makeGroup(groupmongo.findGroupByName(groupName)));
            usermongo.addUserToGroup(UserMongo.makeUser(usermongo.findUserByUsername(msg.getReceiver())),
                    GroupMongo.makeGroup(groupmongo.findGroupByName(groupName)));
          }
          msg.setReceiver(groupName);
        }
        if (msg.getType() == MessageType.GROUP_MESSAGE) {
          determineReceiverIsValid(msg.getReceiver());
          logger.info("Group Message");
          String receiver = msg.getReceiver();
          msg.setReceiver(receiver);
        }
        // add sender id to msg
        msg.setSrcIP(socket.getRemoteAddress().toString());
        Prattle.sendMessage(msg);
      } catch (UserDoesNotExistException e) {
        logger.info("failed to find user");
        Message sendMsg;
        sendMsg = new Message(ADMIN, null, MessageType.NO_ACKNOWLEDGE, "Last message was " +
                "rejected because it specified an incorrect user name.", null);
        enqueueMessage(sendMsg);
      } catch (IOException e) {
        logger.info("failed to find source IP");
        Message sendMsg;
        sendMsg = new Message(ADMIN, null, MessageType.NO_ACKNOWLEDGE, "Last message was " +
                "rejected because the source IP of the sender could not be determined.", null);
        enqueueMessage(sendMsg);
      }
    } else {
      logger.info("failed checks");
      Message sendMsg;
      sendMsg = new Message(ADMIN, null, MessageType.NO_ACKNOWLEDGE, "Last message was rejected because" +
              " it specified an incorrect user name.", null);
      enqueueMessage(sendMsg);
    }
  }

  /**
   * Determine if receiver of private message is valid.
   *
   * @param receiverName the receiver of a private message.
   * @return the receiverName if valid.
   * @throws UserDoesNotExistException if the user does not exist.
   */
  private void determineReceiverIsValid(String receiverName) {
    Document receiverNameDoc = usermongo.findUserByUsername(receiverName);
    Document receiverGroupDoc = groupmongo.findGroupByName(receiverName);
    if (receiverNameDoc == null && receiverGroupDoc == null) {
      throw new UserDoesNotExistException("User does not exist");
    }
  }

  /**
   * Helper method for processing CreateGroup message.
   *
   * @param msg the CreateGroup message.
   */
  private void runHelperCreateGroup(Message msg) {
    logger.info("create group");
    String groupName = msg.getContent();
    try {
      group = GroupMongo.makeGroup(groupmongo.findGroupByName(groupName)).getGroupName();
      enqueueMessage(Message.makeNoAcknowledgeMessage());
      group = null;
    } catch (Exception e) {
      group = groupName;
      groupmongo.createGroup(new Group(group));
      logger.info("group made");
      usermongo.addUserToGroup(UserMongo.makeUser(usermongo.findUserByUsername(user)),
              GroupMongo.makeGroup(groupmongo.findGroupByName(groupName)));

      enqueueMessage(Message.makeAcknowledgeMessage(groupName));
    }
  }

  /**
   * Helper method for processing JoinGroup message.
   *
   * @param msg the JoinGroup message.
   */
  private void runHelperJoinGroup(Message msg) {
    String groupName = msg.getContent();
    Group groupObj;
    try {
      groupObj = GroupMongo.makeGroup(groupmongo.findGroupByName(groupName));
      if (groupObj.getUsers().contains(this.user)) {
        enqueueMessage(Message.makeAcknowledgeMessage(groupName));
        group = groupName;
      } else {
        enqueueMessage(Message.makeNoAcknowledgeMessage());
      }
    } catch (Exception e) {
      enqueueMessage(Message.makeNoAcknowledgeMessage());
    }
  }

  /**
   * Helper method for processing AddUser message.
   *
   * @param msg the AddUser message.
   */
  public void runHelperAddUser(Message msg) {
    String newuser;
    if (group == null) {
      enqueueMessage(Message.makeNoAcknowledgeMessage());
    } else {
      try {
        newuser = usermongo.findUserByUsername(msg.getContent()).get("name").toString();
        usermongo.addUserToGroup(UserMongo.makeUser(usermongo.findUserByUsername(newuser)),
                GroupMongo.makeGroup(groupmongo.findGroupByName(group)));

        enqueueMessage(Message.makeAcknowledgeMessage(msg.getContent()));
      } catch (Exception e) {
        enqueueMessage(Message.makeNoAcknowledgeMessage());
      }
    }
  }

  /**
   * Check if the message is properly formed. At the moment, this means checking
   * that the identifier is set properly.
   *
   * @param msg Message to be checked
   * @return True if message is correct; false otherwise
   */
  private boolean messageChecks(Message msg) {
    // Check that the message name matches.
    return (msg.getSender() != null) && (msg.getSender().compareToIgnoreCase(getUsername()) == 0);
  }

  /**
   * Immediately send this message to the client. This returns if we were
   * successful or not in our attempt to send the message.
   *
   * @param message Message to be sent immediately.
   * @return True if we sent the message successfully; false otherwise.
   */
  private void sendMessage(Message message) {
    logger.log(Level.FINE, () -> "\t" + message);
    output.print(message);
  }

  /**
   * Get the name of the user for which this ClientRunnable was created.
   *
   * @return Returns the name of this client.
   */
  public String getUsername() {
    return user;
  }

  /**
   * Get the group name of the group the user is currently in to chat.
   *
   * @return the group name the user is currently in to chat.
   */
  public String getGroup() {
    return group;
  }

  /**
   * Add the given message to this client to the queue of message to be sent to
   * the client.
   *
   * @param message Complete message to be sent.
   */
  void enqueueMessage(Message message) {
    waitingList.add(message);
  }

  /**
   * Return if this thread has completed the initialization process with its
   * client and is read to receive messages.
   *
   * @return True if this thread's client should be considered; false otherwise.
   */
  public boolean isInitialized() {
    return initialized;
  }

  /**
   * Store the object used by this client runnable to control when it is scheduled
   * for execution in the thread pool.
   *
   * @param future Instance controlling when the runnable is executed from within
   *               the thread pool.
   */
  public void setFuture(ScheduledFuture<ClientRunnable> future) {
    runnableMe = future;
  }

  /**
   * Terminate a client that we wish to remove. This termination could happen at
   * the client's request or due to system need.
   */
  private void terminateClient() {
    try {
      // Once the communication is done, close this connection.
      input.close();
      socket.close();
    } catch (IOException e) {
      // If we have an IOException, ignore the problem
      logger.log(Level.WARNING, e.toString());
    } finally {
      // Remove the client from our client listing.
      Prattle.removeClient(this);
      // And remove the client from our client pool.
      runnableMe.cancel(false);
    }
  }
}
