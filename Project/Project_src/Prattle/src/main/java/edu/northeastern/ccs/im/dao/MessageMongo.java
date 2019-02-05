package edu.northeastern.ccs.im.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import edu.northeastern.ccs.im.dao.exceptions.MessageDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.RelationshipException;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * MessageMongo is the data access object class for messages from the Message collection.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
public class MessageMongo extends BaseMongo {
  private static MessageMongo instance = null;
  private static UserMongo userMongo = UserMongo.getInstance();
  private static GroupMongo groupMongo = GroupMongo.getInstance();
  private static String messageNotFound = "Message not found";
  private static String messageIdField = "mid";
  private static String senderField = "sender";
  private static String receiverField = "receiver";
  private static String typeField = "type";
  private static String contentField = "content";
  private static String timeField = "time";
  private static String flaggedField = "flagged";
  private static String srcIPField = "srcIP";
  private MongoCollection<Document> messageCollection;
  private MongoCollection<Document> messageCounterCollection;

  /**
   * Private constructor that initializes the database collection.
   *
   * @param collection the collection of the database.
   */
  private MessageMongo(String collection) {
    super.collection = collection;
    messageCollection = super.getCollection("message");
    messageCounterCollection = super.getCollection("messageCounter");
  }

  /**
   * Public static singleton getInstance for MessageMongo. Instantiates or returns messageMongo.
   *
   * @return the MessageMongo instance.
   */
  public static MessageMongo getInstance() {
    if (instance == null || instance.isClosed()) {
      instance = new MessageMongo("message");
    }
    return instance;
  }

  /**
   * Creates a message with the specified parameters.
   *
   * @param message that is to be created in the Message collection.
   * @return the message that was saved
   * @throws RelationshipException if the user is not part of the receiving group.
   */
  public Message createMessage(Message message) {
    Document document = new Document();
    if (isUserInGroup(message.getSender(), message.getReceiver())) {
      int nextId = getNextMessageId();
      message.setMessageId(nextId);
      document.append(messageIdField, nextId);
      document.append(senderField, message.getSender());
      document.append(receiverField, message.getReceiver());
      document.append(typeField, message.getType().toString());
      document.append(contentField, message.getContent());
      document.append(timeField, message.getTime());
      document.append(flaggedField, message.isFlagged());
      document.append(srcIPField, message.getSrcIP());
      messageCollection.insertOne(document);
      groupMongo.addMessageToGroup(makeMessage(document), message.getReceiver());
    } else {
      throw new RelationshipException("User " + message.getSender() + " is not part of Group "
              + message.getReceiver());
    }
    return message;
  }

  /**
   * Document representing a message found by a given message ID.
   *
   * @param messageId is the given message ID.
   * @return document representing the message with the given message ID.
   * @throws MessageDoesNotExistException when the message does not exist in the database.
   */
  public Document findMessageById(int messageId) {
    Document document;
    try {
      document = messageCollection.find(eq(messageIdField, messageId)).first();
      assert document != null;
    } catch (AssertionError e) {
      throw new MessageDoesNotExistException(messageNotFound);
    }
    return document;
  }

  /**
   * The list of documents representing messages sent by the given user.
   *
   * @param sender the user who sent the messages.
   * @return the list of messages sent by the given user.
   * @throws MessageDoesNotExistException when user sent no messages.
   */
  public List<Document> findMessagesBySender(User sender) {
    List<Document> messages = new ArrayList<>();
    try {
      FindIterable<Document> iterable = messageCollection.find(eq(senderField, sender.getUsername()));
      assert iterable.first() != null;
      for (Document message : iterable) {
        messages.add(message);
      }
    } catch (AssertionError e) {
      throw new MessageDoesNotExistException(messageNotFound);
    }
    return messages;
  }

  /**
   * The list of documents representing messages that belong to the given group.
   *
   * @param receiver the group which receives the messages.
   * @return the messages that belong to the given group.
   * @throws MessageDoesNotExistException when group received no messages.
   */
  public List<Document> findMessagesByReceiver(Group receiver) {
    List<Document> messages = new ArrayList<>();
    try {
      FindIterable<Document> iterable = messageCollection.find(eq(receiverField, receiver.getGroupName()));
      assert iterable.first() != null;
      for (Document message : iterable) {
        messages.add(message);
      }
    } catch (AssertionError e) {
      throw new MessageDoesNotExistException(messageNotFound);
    }
    return messages;
  }

  /**
   * Delete all messages from the Message collection.
   */
  public void deleteAllMessages() {
    BasicDBObject document = new BasicDBObject();
    // Delete All documents from collection Using blank BasicDBObject
    messageCollection.deleteMany(document);
  }

  /**
   * Delete all message counters stored in the messageCounter collection.
   */
  public void deleteAllMessageCounters() {
    BasicDBObject document = new BasicDBObject();
    // Delete All documents from collection Using blank BasicDBObject
    messageCounterCollection.deleteMany(document);
  }

  /**
   * Get the current messageID to be generated for new message.
   *
   * @return the object with current sequence messageId.
   */
  private int getNextMessageId() {
    Document doc = new Document();
    if (messageCounterCollection.countDocuments(doc) == 0) {
      Document document = new Document();
      document.append("_id", "messageId");
      document.append(messageIdField, 1);
      messageCounterCollection.insertOne(document);
    }
    Document searchQuery = new Document("_id", "messageId");
    Document increase = new Document(messageIdField, 1);
    Document updateQuery = new Document("$inc", increase);
    Document result = messageCounterCollection.findOneAndUpdate(searchQuery, updateQuery);
    return (int) result.get(messageIdField);
  }

  /**
   * Determine if the user is in the group provided.
   *
   * @param user the user.
   * @param group the group.
   * @return true if the user is in the group, false in all other cases (e.g. user does not
   * exist, group does not exist or user is not in group).
   */
  private boolean isUserInGroup(String user, String group) {
    try {
      User userObj = UserMongo.makeUser(userMongo.findUserByUsername(user));
      Group groupObj = GroupMongo.makeGroup(groupMongo.findGroupByName(group));
      return groupObj.getUsers().contains(user) && userObj.getGroups().contains(group);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Make a new Message object from the retrieved document from Message collection.
   *
   * @param document the document retrieved from Message collection.
   * @return the Message representing the given document from Message collection.
   */
  public static Message makeMessage(Document document) {
    Message message = null;
    if (document != null) {
      int messageId = (int) document.get(messageIdField);
      String sender = document.get(senderField).toString();
      String receiver = document.get(receiverField).toString();
      String type = document.get(typeField).toString();
      String content = document.get(contentField).toString();
      String time = document.get(timeField).toString();

      message = new Message(messageId, sender, receiver,
              Message.MessageType.getMessageType(type), content, time);
    }
    return message;
  }
}
