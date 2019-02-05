package edu.northeastern.ccs.im.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

import edu.northeastern.ccs.im.dao.exceptions.GroupDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.GroupNameNotAvailableException;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

/**
 * GroupMongo is the data access object class for groups from the Group collection.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
public class GroupMongo extends BaseMongo {
  private static GroupMongo instance = null;
  private static MessageMongo messageMongo = MessageMongo.getInstance();
  private static String groupNotFound = "Group not found";
  private static String groupNameField = "name";
  private static String usersField = "users";
  private static String messagesField = "messages";
  private static String listenersField = "listeners";
  private static String expirationField = "expirationDate";
  private static String groupExists = "Group name already exists";
  private MongoCollection<Document> groupCollection;
  private static final String PUSH = "$push";

  private static final Logger logger = Logger.getLogger(GroupMongo.class.getName());

  /**
   * Private constructor that initializes the database collection.
   *
   * @param collection the collection of the database.
   */
  private GroupMongo(String collection) {
    super.collection = collection;
    groupCollection = super.getCollection("group");
  }

  /**
   * Public static singleton getInstance for GroupMongo. Instantiates or returns groupMongo.
   *
   * @return the GroupMongo instance.
   */
  public static GroupMongo getInstance() {
    if (instance == null || instance.isClosed()) {
      instance = new GroupMongo("collection");
    }
    return instance;
  }

  /**
   * Create group from the group provided.
   *
   * @param group the group to create in the Group collection.
   * @throws GroupNameNotAvailableException if the group name already exists.
   */
  public void createGroup(Group group) {
    Document document = new Document();
    try {
      Document d = groupCollection.find(eq("name", group.getGroupName())).first();
      assert d == null;
      document.append(groupNameField, group.getGroupName());
      document.append(usersField, group.getUsers());
      document.append(messagesField, group.getMessages());
      document.append(listenersField, group.getListeners());
      document.append(expirationField, group.getExpirationDate());
      groupCollection.insertOne(document);
    } catch (AssertionError e) {
      throw new GroupNameNotAvailableException(groupExists);
    }
  }

  /**
   * Find the group in the database using the group's name.
   *
   * @param name the group name to search for in the Group collection.
   * @return the group document.
   * @throws GroupDoesNotExistException if the group does not exist in the Group collection.
   */
  public Document findGroupByName(String name) {
    logger.info(() -> "searching for " + name);
    Document dbObject;
    dbObject = groupCollection.find(eq(groupNameField, name)).first();
    logger.info(() -> "group found is: " + dbObject);
    return dbObject;
  }

  /**
   * Update the oldName group in the Group collection by changing the group's name to newName.
   *
   * @param oldName the old name of the group.
   * @param newName the new name of the group.
   * @throws GroupNameNotAvailableException if the newName group already exists.
   * @throws GroupDoesNotExistException     if the oldName group already exists.
   */
  public void updateGroup(String oldName, String newName) {
    BasicDBObject newDocument = new BasicDBObject();
    newDocument.append("$set", new BasicDBObject().append(groupNameField, newName));
    if (findGroupByName(newName) == null && findGroupByName(oldName) != null) {
      BasicDBObject searchQuery = new BasicDBObject(groupNameField, oldName);
      groupCollection.updateOne(searchQuery, newDocument);
    } else {
      throw new GroupNameNotAvailableException(groupExists);
    }
  }

  /**
   * Delete the group with the provided name from the Group collection.
   *
   * @param name the name of the group to delete.
   * @throws GroupDoesNotExistException if the group does not exist.
   */
  public void deleteGroup(String name) {
    try {
      Document d = groupCollection.findOneAndDelete(eq(groupNameField, name));
      assert d != null;
    } catch (AssertionError e) {
      throw new GroupDoesNotExistException(groupNotFound);
    }
  }

  /**
   * Delete all groups from the Group collection.
   */
  public void deleteAllGroups() {
    BasicDBObject document = new BasicDBObject();
    // Delete All documents from collection Using blank BasicDBObject
    groupCollection.deleteMany(document);
  }

  /**
   * Add the given user to the given group.
   *
   * @param user  the user to be added.
   * @param group the group to which the user is to be added.
   */
  public void addUserToGroup(User user, Group group) {
    BasicDBObject match = new BasicDBObject();
    match.put(groupNameField, group.getGroupName());
    BasicDBObject update = new BasicDBObject();
    update.put(PUSH, new BasicDBObject(usersField, user.getUsername()));
    groupCollection.updateOne(match, update);
  }

  /**
   * Remove the given user from the given group.
   *
   * @param user  the user to be removed from the group.
   * @param group the group from which the user is to be removed.
   */
  public void removeUserFromGroup(User user, Group group) {
    BasicDBObject match = new BasicDBObject();
    match.put(groupNameField, group.getGroupName());
    BasicDBObject update = new BasicDBObject();
    update.put("$pull", new BasicDBObject(usersField, user.getUsername()));
    groupCollection.updateOne(match, update);
  }

  /**
   * Add message to the given group.
   *
   * @param message the message to be added to the group.
   * @param group   the group that the message should be added.
   * @throws GroupDoesNotExistException if the group does not exist in the Group collection.
   */
  public void addMessageToGroup(Message message, String group) {
    Document foundDoc = findGroupByName(group);
    Group foundGroup = makeGroup(foundDoc);
    foundGroup.addMessage(message);
    message.setReceiver(group);
    BasicDBObject match = new BasicDBObject();
    match.put(groupNameField, foundDoc.get(groupNameField));
    BasicDBObject update = new BasicDBObject();
    update.put(PUSH, new BasicDBObject(messagesField, message.getMessageId()));
    groupCollection.updateOne(match, update);
  }

	/**
	 * Sets the expiration date of the group and saves it to MongoDB.
	 * @param l the expiration date, in milliseconds since Unix Epoch time.
	 * @return the saved group with the expiration date added.
	 */
	public Group addExpiration(long expirationDate, String group) {
	    Document foundDoc = findGroupByName(group);
	    Group foundGroup = makeGroup(foundDoc);
	    foundGroup.setExpirationDate(expirationDate);
	    BasicDBObject match = new BasicDBObject();
	    match.put(groupNameField, group);
	    BasicDBObject update = new BasicDBObject();
	    update.put("$set", new BasicDBObject(expirationField, expirationDate));
	    groupCollection.updateOne(match, update);
	    
		return foundGroup;
	}

	/**
	 * Adds the receiving group as a listener of the group, with an expiration determined by the duration
	 * @param trackedGroup
	 * @param receivingGroup
	 * @param duration the duration of the wiretap in minutes
	 */
	public void addListener(String trackedGroup, String receivingGroup, int duration) {
		Group recGroup = addExpiration(System.currentTimeMillis() + (duration*60000), receivingGroup);
		Group group = makeGroup(findGroupByName(trackedGroup));
		assert group != null;
		group.addListener(recGroup);
	    BasicDBObject match = new BasicDBObject();
	    match.put(groupNameField, trackedGroup);
	    BasicDBObject update = new BasicDBObject();
	    update.put(PUSH, new BasicDBObject(listenersField, recGroup.getGroupName()));
	    groupCollection.updateOne(match, update);
	}

  /**
   * Make a new Group object from the retrieved document from Group collection.
   *
   * @param document the document retrieved from Group collection.
   * @return the Group representing the given document from Group collection.
   */
  @SuppressWarnings("unchecked")
public static Group makeGroup(Document document) {
    Group group;
    List<Message> messageList = new ArrayList<>();

    String groupname = document.get(groupNameField).toString();
    List<String> userList = (List<String>) document.get(usersField);
    List<Integer> messageObj = (List<Integer>) document.get(messagesField);
    if (messageObj != null && !messageObj.isEmpty()) {
      for (int i : messageObj) {
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
    if (listenerNames != null && !listenerNames.isEmpty()) {
    	logger.info("adding listeners");
  	  for (String listenerName : listenerNames) {
  		  Group g = makeGroup(getInstance().findGroupByName(listenerName));
  		  listeners.add(g);
  	  }
    }
    long expirationDate = (long) document.get(expirationField);
    group = new Group(groupname, userList, messageList, listeners, expirationDate);

    return group;
  }
}
