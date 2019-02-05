package edu.northeastern.ccs.im.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import edu.northeastern.ccs.im.dao.exceptions.RelationshipException;
import edu.northeastern.ccs.im.dao.exceptions.UserDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.UsernameNotAvailableException;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * UserMongo is the data access object class for users from the User collection.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
public class UserMongo extends BaseMongo {
  private static UserMongo instance = null;
  private static GroupMongo groupMongo = GroupMongo.getInstance();
  private static MessageMongo messageMongo = MessageMongo.getInstance();
  private static String userNotFound = "User not found";
  private static String userNameField = "name";
  private static String passField = "password";
  private static String groupsField = "groups";
  private static String messageQueueField = "messageQueue";
  private static String listenersField = "listeners";
  private static String usernameTaken = "Username already taken by other user.";
  private MongoCollection<Document> userCollection;
  private static final String PUSH = "$push";

  private static final Logger logger = Logger.getLogger(UserMongo.class.getName());

  /**
   * Private constructor that initializes the database collection.
   *
   * @param collection the collection of the database.
   */
  private UserMongo(String collection) {
    super.collection = collection;
    userCollection = super.getCollection("user");
  }

  /**
   * Public static singleton getInstance for UserMongo. Instantiates or returns userMongo.
   *
   * @return the UserMongo instance.
   */
  public static UserMongo getInstance() {
    if (instance == null || instance.isClosed()) {
      instance = new UserMongo("user");
    }
    return instance;
  }

  /**
   * Create user from the user provided.
   *
   * @param user the user to create in the User collection.
   * @throws UsernameNotAvailableException if the username already exists.
   */
  public void createUser(User user) {
    Document document = new Document();
    try {
      Document d = userCollection.find(eq(userNameField, user.getUsername())).first();
      assert d == null;
      document.append(userNameField, user.getUsername());
      document.append(passField, User.encryptPassword(user.getPassword()));
      document.append(groupsField, user.getGroups());
      document.append(messageQueueField, user.getMessageQueue());
      document.append(listenersField, user.getListeners());
      userCollection.insertOne(document);
    } catch (AssertionError e) {
      throw new UsernameNotAvailableException(usernameTaken);
    }
  }

  /**
   * Find the user in the database using the user's name.
   *
   * @param name the username to search for in the User collection.
   * @return the user.
   * @throws UserDoesNotExistException if the username does not exist in the User collection.
   */
  public Document findUserByUsername(String name) {
    logger.info(() -> "searching for " + name);
    Document dbObject;
    dbObject = userCollection.find(eq(userNameField, name)).first();
    logger.info(() -> "user found is: " + dbObject);
    return dbObject;
  }

  /**
   * Find the user in the database with the given username and password.
   *
   * @param username the username of the user.
   * @param password the password of the user.
   * @return the document which represents the found user.
   */
  public Document findUserByLogin(String username, String password) {
    Document dbObject = null;
    try {
      String encodedPassword = User.encryptPassword(password);
      dbObject = userCollection.find(and(eq(userNameField, username), eq(passField, encodedPassword))).first();
      assert dbObject != null;
    } catch (AssertionError e) {
      throw new UserDoesNotExistException(userNotFound);
    }
    return dbObject;
  }

  /**
   * Update the oldName user in the User collection by changing the user's name to newName.
   *
   * @param oldName the old username of the user.
   * @param newName the new username of the user.
   * @throws UsernameNotAvailableException if the newName group already exists.
   * @throws UserDoesNotExistException     if the oldName group already exists.
   */
  public void updateUser(String oldName, String newName) {
    BasicDBObject newDocument = new BasicDBObject();
    newDocument.append("$set", new BasicDBObject().append(userNameField, newName));
    if (findUserByUsername(newName) == null && findUserByUsername(oldName) != null) {
        BasicDBObject searchQuery = new BasicDBObject(userNameField, oldName);
        userCollection.updateOne(searchQuery, newDocument);
    } else {
      throw new UserDoesNotExistException(usernameTaken);
    }
  }

  /**
   * Delete the user with the provided username from the User collection.
   *
   * @param name the username of the user to delete.
   * @throws UserDoesNotExistException if the user does not exist.
   */
  public void deleteUser(String name) {
    try {
      Document d = userCollection.findOneAndDelete(eq(userNameField, name));
      assert d != null;
    } catch (AssertionError e) {
      throw new UserDoesNotExistException(userNotFound);
    }
  }

  /**
   * Delete all users from the User collection.
   */
  public void deleteAllUsers() {
    BasicDBObject document = new BasicDBObject();
    // Delete All documents from collection Using blank BasicDBObject
    userCollection.deleteMany(document);
  }

  /**
   * Add the given user to the given group.
   *
   * @param user  the user to be added.
   * @param group the group to which the user is to be added.
   * @throws RelationshipException if given user already belongs to the given group.
   */
  public void addUserToGroup(User user, Group group) {
    logger.info(() -> "adding " + user.getUsername() + " to " + group.getGroupName());
    if (user.getGroups().contains(group.getGroupName()) || group.getUsers().contains(user.getUsername()))
      throw new RelationshipException("User and Group Relation Already Exists");
    user.addGroup(group.getGroupName());
    group.addUser(user.getUsername());
    BasicDBObject match = new BasicDBObject();
    match.put(userNameField, user.getUsername());
    BasicDBObject update = new BasicDBObject();
    update.put(PUSH, new BasicDBObject(groupsField, group.getGroupName()));
    boolean res = userCollection.updateOne(match, update).wasAcknowledged();
    logger.info(() -> "UserMongo.addUserToGroup: " + res);
    groupMongo.addUserToGroup(user, group);
  }

  /**
   * Remove the given user from the given group.
   *
   * @param user  the user to be removed from the group.
   * @param group the group from which the user is to be removed.
   * @throws RelationshipException if the given user does not belong to the given group.
   */
  public void removeUserFromGroup(User user, Group group) {
    if (!user.getGroups().contains(group.getGroupName()) || !group.getUsers().contains(user.getUsername()))
      throw new RelationshipException("User and Group have no relationship");
    user.removeGroup(group);
    group.removeUser(user);
    BasicDBObject match = new BasicDBObject();
    match.put(userNameField, user.getUsername());
    BasicDBObject update = new BasicDBObject();
    update.put("$pull", new BasicDBObject(groupsField, group.getGroupName()));
    userCollection.updateOne(match, update);
    groupMongo.removeUserFromGroup(user, group);
  }

  /**
   * Puts a message in the user's queue
   *
   * @param user the user to add the message to
   * @param m    the message to enqueue
   */
  public void enqueueMessageToUser(User user, Message m) {
    user.enqueueMessage(m);
    BasicDBObject match = new BasicDBObject();
    match.put(userNameField, user.getUsername());
    BasicDBObject update = new BasicDBObject();
    update.put(PUSH, new BasicDBObject(messageQueueField, m.getMessageId()));
    boolean res = userCollection.updateOne(match, update).wasAcknowledged();
    logger.info(() -> "UserMongo.enqueueMessageToUser: " + res);
  }

  /**
   * Clears the user's message queue.
   *
   * @param user the user whose message queue to clear
   */
  public void clearMessageQueue(User user) {
    user.clearMessageQueue();
    BasicDBObject match = new BasicDBObject();
    match.put(userNameField, user.getUsername());
    BasicDBObject update = new BasicDBObject();
    update.put("$set", new BasicDBObject(messageQueueField, new ArrayList<Integer>()));
    userCollection.updateOne(match, update);
  }

	/**
	 * Adds the receiving group as a listener of the user, with an expiration determined by the duration
	 * @param trackedUser
	 * @param receivingGroup
	 * @param duration the duration of the wiretap in minutes
	 */
	public void addListener(String trackedUser, String receivingGroup, int duration) {
		Group recGroup = groupMongo.addExpiration(System.currentTimeMillis() + (duration*60000), receivingGroup);
		User user = UserMongo.makeUser(findUserByUsername(trackedUser));
		assert user != null;
		user.addListener(recGroup);
	    BasicDBObject match = new BasicDBObject();
	    match.put(userNameField, user.getUsername());
	    BasicDBObject update = new BasicDBObject();
	    update.put(PUSH, new BasicDBObject(listenersField, recGroup.getGroupName()));
	    userCollection.updateOne(match, update);
	}

  /**
   * Make a new User object from the retrieved document from User collection.
   *
   * @param document the document retrieved from User collection.
   * @return the User representing the given document from User collection.
   */
  @SuppressWarnings("unchecked")
  public static User makeUser(Document document) {
    User user = null;
    if (document != null) {
      String username = document.get(userNameField).toString();
      String encodedPassword = document.get(passField).toString();
      String password = User.decryptPassword(encodedPassword);
      List<String> groupList = (List<String>) document.get(groupsField);
      List<Integer> messageIdList = (List<Integer>) document.get(messageQueueField);
      List<Message> messageList = new ArrayList<>();
      if (messageIdList != null && !messageIdList.isEmpty()) {
        for (int i : messageIdList) {
          Document m = messageMongo.findMessageById(i);
          Message newMessage = new Message(i,
                  (String) m.get("sender"),
                  (String) m.get("receiver"),
                  Message.MessageType.getMessageType(m.get("type").toString()),
                  m.get("content").toString(),
                  m.get("time").toString());

          messageList.add(newMessage);
        }
      }
      List<String> listenerNames = (List<String>) document.get(listenersField);
      List<Group> listeners = new ArrayList<>();
      if (listenerNames != null) {
    	  for (String listenerName : listenerNames) {
    		  Group g = GroupMongo.makeGroup(groupMongo.findGroupByName(listenerName));
    		  listeners.add(g);
    	  }
      }
      user = new User(username, password, groupList, messageList, listeners);
    }
    return user;
  }
}
