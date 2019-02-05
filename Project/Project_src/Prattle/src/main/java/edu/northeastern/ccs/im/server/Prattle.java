package edu.northeastern.ccs.im.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import edu.northeastern.ccs.im.dao.GroupMongo;
import edu.northeastern.ccs.im.dao.MessageMongo;
import edu.northeastern.ccs.im.dao.UserMongo;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

/**
 * A network server that communicates with IM clients that connect to it. This
 * version of the server spawns a new thread to handle each client that connects
 * to it. At this point, messages are broadcast to all of the other clients.
 * It does not send a response when the user has gone off-line.
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/4.0/. It is based on work
 * originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @author Matthew lazarcheck
 * @version 1.3
 */
public abstract class Prattle {

  private static boolean done = false;

  /**
   * Amount of time we should wait for a signal to arrive.
   */
  private static final int DELAY_IN_MS = 50;

  /**
   * Number of threads available in our thread pool.
   */
  private static final int THREAD_POOL_SIZE = 20;

  /**
   * Delay between times the thread pool runs the client check.
   */
  private static final int CLIENT_CHECK_DELAY = 200;

  /**
   * Collection of threads that are currently being used.
   */
  private static ConcurrentLinkedQueue<ClientRunnable> active;

  private static UserMongo mongoUser = UserMongo.getInstance();

  private static GroupMongo mongoGroup = GroupMongo.getInstance();

  private static MessageMongo mongoMessage = MessageMongo.getInstance();
  
  private static final String[] KEYWORDS = new String[] {
	"fuck",
	"shit",
	"damn",
	"bastard",
	"bitch"
  };

  private static final String ADMIN = "admin";

  /**
   * Initialize logger for the class.
   */
  private static final Logger logger = Logger.getLogger(Prattle.class.getName());

  /** All of the static initialization occurs in this "method" */
  static {
    // Create the new queue of active threads.
    active = new ConcurrentLinkedQueue<>();
  }

  /**
   * Send a given message in the appropriate recipient (group).
   * Any message which cannot be immediately delivered gets saved in a message queue.
   *
   * @param message the message to be sent.
   */
  public static void sendMessage(Message message) {
	  // check for keywords for flagging
	  for (String word : message.getContent().split("\\s+")) {
		  if (Arrays.asList(KEYWORDS).contains(word.toLowerCase())) {
			  message.setFlagged(true);
		  }
	  }
    // save the message
    message.setTime(Long.toString(System.currentTimeMillis()));
    message = mongoMessage.createMessage(message);
    
    // check if the sender is being tracked
    User sender = UserMongo.makeUser(mongoUser.findUserByUsername(message.getSender()));
    List<Group> listeners = sender.getListeners();
    sendToListeners(message, listeners);
    
    // prepare a list for offline clients
    List<String> onlineClients = new ArrayList<>();
    String receiverName = message.getReceiver();
    Group receiver = GroupMongo.makeGroup(mongoGroup.findGroupByName(receiverName));
    
    // check if receiving group is being tracked
    listeners = receiver.getListeners();
    sendToListeners(message, listeners);

    // first send to online clients
    for (ClientRunnable tt : active) {
      if (tt.isInitialized() && (receiverName.equals(tt.getGroup())) || receiver.getUsers().contains(tt.getUsername())) {
        tt.enqueueMessage(message);
        onlineClients.add(tt.getUsername());
      }
    }
    // put the message in the queue for all offline clients
    for (String username : receiver.getUsers()) {
    	if (!username.equals(message.getSender())) {
	    	// also check if receiver is being tracked
	        sender = UserMongo.makeUser(mongoUser.findUserByUsername(username));
	        listeners = sender.getListeners();
	        sendToListeners(message, listeners);
    	}
      if (!onlineClients.contains(username)) {
        logger.info("enqueuing message");
        User user = UserMongo.makeUser(mongoUser.findUserByUsername(username));
        mongoUser.enqueueMessageToUser(user, message);
      }
    }
  }

  /**
   * Send messages to listeners.
   *
   * @param message the message to send.
   * @param listeners the listeners to send the message to.
   */
	private static void sendToListeners(Message message, List<Group> listeners) {
		for (Group g : listeners) {
			// check for expiration
			if (System.currentTimeMillis() > g.getExpirationDate()) {
				logger.info("expired");
				continue;
			}
	    	g.addMessage(message);
    	    List<String> onlineClients = new ArrayList<>();
	    	for (String uname : g.getUsers()) {
	    	    // first send to online clients
	    	    for (ClientRunnable tt : active) {
	    	      if (tt.isInitialized() && tt.getUsername().equals(uname)) {
	    	        tt.enqueueMessage(message);
	    	        onlineClients.add(tt.getUsername());
	    	      }
	    	    }
	    	}
	    	for (String uname : g.getUsers()) {
	    	      if (!onlineClients.contains(uname)) {
	    	          logger.info("enqueuing message");
	    	          User user = UserMongo.makeUser(mongoUser.findUserByUsername(uname));
	    	          mongoUser.enqueueMessageToUser(user, message);
	    	      } else {
		    		User u = UserMongo.makeUser(mongoUser.findUserByUsername(uname));
		    		mongoUser.enqueueMessageToUser(u, message);
	    	      }
	    	}
	    }
	}

  /**
   * Start up the threaded talk server. This class accepts incoming connections on
   * a specific port specified on the command-line. Whenever it receives a new
   * connection, it will spawn a thread to perform all of the I/O with that
   * client. This class relies on the server not receiving too many requests -- it
   * does not include any code to limit the number of extant threads.
   *
   * @param args String arguments to the server from the command line. At present
   *             the only legal (and required) argument is the port on which this
   *             server should list.
   * @throws IOException Exception thrown if the server cannot connect to the port
   *                     to which it is supposed to listen.
   */
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws IOException {
	  if (mongoUser.findUserByUsername(ADMIN) == null) {
		  User admin = new User(ADMIN, ADMIN);
		  mongoUser.createUser(admin);
	  }
    // Connect to the socket on the appropriate port to which this server connects.
    ServerSocketChannel serverSocket = null;
    try {
      serverSocket = ServerSocketChannel.open();
      serverSocket.configureBlocking(false);
      serverSocket.socket().bind(new InetSocketAddress(4545));
      // Create the Selector with which our channel is registered.
      Selector selector = SelectorProvider.provider().openSelector();
      // Register to receive any incoming connection messages.
      serverSocket.register(selector, SelectionKey.OP_ACCEPT);
      // Create our pool of threads on which we will execute.
      ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
      // Listen on this port until ...
      while (!done) {
        // Check if we have a valid incoming request, but limit the time we may wait.
        while (selector.select(DELAY_IN_MS) != 0) {
          // Get the list of keys that have arrived since our last check
          Set<SelectionKey> acceptKeys = selector.selectedKeys();
          // Now iterate through all of the keys
          Iterator<SelectionKey> it = acceptKeys.iterator();
          while (it.hasNext()) {
            // Get the next key; it had better be from a new incoming connection
            it.next();
            it.remove();
            // Create a new thread to handle the client for which we just received a
            // request.
            // Accept the connection and create a new thread to handle this client.
            SocketChannel socket = serverSocket.accept();
            // Make sure we have a connection to work with.
            if (socket != null) {
              ClientRunnable tt = new ClientRunnable(socket);
              // Add the thread to the queue of active threads
              active.add(tt);
              // Have the client executed by our pool of threads.
              @SuppressWarnings("rawtypes")
              ScheduledFuture clientFuture = threadPool.scheduleAtFixedRate(tt, CLIENT_CHECK_DELAY,
                      CLIENT_CHECK_DELAY, TimeUnit.MILLISECONDS);
              tt.setFuture(clientFuture);
            }
          }
        }
      }
    } catch (Exception e) {
      logger.info(e.toString());
      throw e;
    } finally {
      assert serverSocket != null;
      serverSocket.close();
    }
  }

  /**
   * Remove the given IM client from the list of active threads.
   *
   * @param dead Thread which had been handling all the I/O for a client who has
   *             since quit.
   */
  static void removeClient(ClientRunnable dead) {
    // Test and see if the thread was in our list of active clients so that we
    // can remove it.
    if (!active.remove(dead)) {
      logger.warning("Could not find a thread that I tried to remove!\n");
    }
  }

  /**
   * Setter method for the done field.
   * 
   * @param done true if Prattle is done, false otherwise.
   */
  public static void setDone(boolean done) {
    Prattle.done = done;
  }
}
