package edu.northeastern.ccs.im.data_models;

/**
 * The message class to represent messages of the messaging system.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
public class Message {
  /**
   * List of the different possible message types.
   */
  public enum MessageType {
    /**
     * Message sent by the user attempting to login using a specified username and password.
     */
    HELLO("HLO"),
    /**
     * Message sent by the server acknowledging success.
     */
    ACKNOWLEDGE("ACK"),
    /**
     * Message sent by the server acknowledging a rejection.
     */
    NO_ACKNOWLEDGE("NAK"),
    /**
     * Message sent by the user to start the logging out process and sent by the
     * server once the logout process completes.
     */
    QUIT("BYE"),
    /**
     * Message whose contents is broadcast to all connected users.
     */
    USER_MESSAGE("UMG"),
    GROUP_MESSAGE("GMG"),
    /**
     * Message for logging in.
     */
    LOGIN("LGN"),
    /**
     * Message for registering.
     */
    REGISTER("REG"),
    /**
     * Message for deleting user.
     */
    DELETE_USER("DEL"),
    /**
     * Message for updating username.
     */
    UPDATE_USER("UPT"),
    /**
     * Message for finding a user.
     */
    FIND_USER("FND"),
    /**
     * Message to join a group.
     */
    JOIN_GROUP("GRP"),
    /**
     * Message for creating a group.
     */
    CREATE_GROUP("CRT"),
    /**
     * Message for adding a user to a group.
     */
    ADD_USER("ADD"),
    /**
     * Message for updating groupname.
     */
    UPDATE_GROUP("UPG"),
    /**
     * Message for deleting group.
     */
    DELETE_GROUP("DEG"),
    /**
     * Message for removing a user from a group.
     */
    REMOVE_USER("RMU"),
    /**
     * Message for leaving a group.
     */
    LEAVE_GROUP("LEG"),
    TRACK_USER("TRU"),
    TRACK_GROUP("TRG"),
    /**
     * Message for stopping logging.
     */
    STOP_LOGGING("LOF"),
    /**
     * Message for starting logging.
     */
    START_LOGGING("LON");
    /**
     * Store the short name of this message type.
     */
    private String tla;

    /**
     * Define the message type and specify its short name.
     *
     * @param abbrev Short name of this message type, as a String.
     */
    private MessageType(String abbrev) {
      tla = abbrev;
    }

    /**
     * Message type getter.
     *
     * @param type the message type.
     * @return the the messageType enum.
     */
    public static MessageType getMessageType(String type) {
      switch (type) {
        case "HLO":
          return MessageType.HELLO;
        case "ACK":
          return MessageType.ACKNOWLEDGE;
        case "NAK":
          return MessageType.NO_ACKNOWLEDGE;
        case "BYE":
          return MessageType.QUIT;
        case "UMG":
          return MessageType.USER_MESSAGE;
        case "GMG":
          return MessageType.GROUP_MESSAGE;
        case "LGN":
          return MessageType.LOGIN;
        case "REG":
          return MessageType.REGISTER;
        case "DEL":
          return MessageType.DELETE_USER;
        case "UPT":
          return MessageType.UPDATE_USER;
        case "FND":
          return MessageType.FIND_USER;
        case "GRP":
          return MessageType.JOIN_GROUP;
        case "CRT":
          return MessageType.CREATE_GROUP;
        case "ADD":
          return MessageType.ADD_USER;
        case "UPG":
          return MessageType.UPDATE_GROUP;
        case "DEG":
          return MessageType.DELETE_GROUP;
        case "RMU":
          return MessageType.REMOVE_USER;
        case "LEG":
          return MessageType.LEAVE_GROUP;
        case "TRU":
          return MessageType.TRACK_USER;
        case "TRG":
          return MessageType.TRACK_GROUP;
        case "LOF":
          return MessageType.STOP_LOGGING;
        case "LON":
          return MessageType.START_LOGGING;
        default:
          return null;
      }
    }

    /**
     * Return a representation of this Message as a String.
     *
     * @return Three letter abbreviation for this type of message.
     */
    @Override
    public String toString() {
      return tla;
    }
  }

  /**
   * The string sent when a field is null.
   */
  private static final String NULL_OUTPUT = "--";

  private Integer messageId;
  private String sender;
  private String receiver;
  private MessageType type;
  private String content;
  private String time;
  private boolean flagged = false;
  private String srcIP;

  /**
   * Constructor for message that includes the six parameters below.
   *
   * @param messageId the messageId of the message.
   * @param sender    the sender of the message.
   * @param receiver  the receiver of the message.
   * @param type      the type of the message as enum.
   * @param content   the content of the message.
   * @param time      the time of the message.
   */
  public Message(Integer messageId, String sender, String receiver, MessageType type, String
          content, String time) {
    this.messageId = messageId;
    this.sender = sender;
    this.receiver = receiver;
    this.type = type;
    this.content = content;
    this.time = time;
  }

  /**
   * Constructor for message that includes the five parameters below.
   *
   * @param sender   the sender of the message.
   * @param receiver the receiver of the message.
   * @param type     the type of the message as enum.
   * @param content  the content of the message.
   * @param time     the time of the message.
   */
  public Message(String sender, String receiver, MessageType type, String content, String time) {
    this.sender = sender;
    this.receiver = receiver;
    this.type = type;
    this.content = content;
    this.time = time;
  }

  /**
   * Constructor for message that includes the five parameters below.
   *
   * @param sender   the sender of the message.
   * @param receiver the receiver of the message.
   * @param type     the type of the message as enum.
   * @param content  the content of the message.
   * @param time     the time of the message.
   */
  public Message(String sender, String receiver, MessageType type, String content, String time, boolean flagged) {
    this.sender = sender;
    this.receiver = receiver;
    this.type = type;
    this.content = content;
    this.time = time;
    this.setFlagged(flagged);
  }

  /**
   * Getter for messageId.
   *
   * @return the messageId.
   */
  public Integer getMessageId() {
    return messageId;
  }

  /**
   * Setter for messageId.
   *
   * @param messageId the messageId.
   */
  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  /**
   * Getter for sender.
   *
   * @return the sender.
   */
  public String getSender() {
    return sender;
  }

  /**
   * Getter for receiver.
   *
   * @return the receiver.
   */
  public String getReceiver() {
    return receiver;
  }

  /**
   * Setter for receiver.
   *
   * @param receiver the receiver of the message.
   */
  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  /**
   * Getter for messageType
   *
   * @return return the messageType enum.
   */
  public MessageType getType() {
    return type;
  }

  /**
   * Getter for content.
   *
   * @return the content of the message.
   */
  public String getContent() {
    return content;
  }

  /**
   * Getter for the time of message.
   *
   * @return the message time.
   */
  public String getTime() {
    return time;
  }

  /**
   * Setter for the time of the message.
   *
   * @param time the time to set
   */
  public void setTime(String time) {
    this.time = time;
  }

  /**
   * Create an acknowledgement message.
   *
   * @param sender the name of the sender.
   * @return the message.
   */
  public static Message makeAcknowledgeMessage(String sender) {
    return new Message(null, null, MessageType.ACKNOWLEDGE, sender, null);
  }

  /**
   * Create a noAcknowledgement message.
   *
   * @return the message.
   */
  public static Message makeNoAcknowledgeMessage() {
    return new Message(null, null, MessageType.NO_ACKNOWLEDGE, null, null);
  }

  public boolean isFlagged() {
    return flagged;
  }

  public void setFlagged(boolean flagged) {
    this.flagged = flagged;
  }

  public String getSrcIP() {
    return srcIP;
  }

  public void setSrcIP(String srcIP) {
    this.srcIP = srcIP;
  }

  /**
   * Representation of this message as a String. This begins with the message
   * type and then contains the length (as an integer) and the value of the next
   * three arguments: sender, receiver and content.
   *
   * @return Representation of this message as a String.
   */
  @Override
  public String toString() {
    String result = type.toString();
    if (sender != null) {
      result += " " + sender.length() + " " + sender;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    if (receiver != null) {
      result += " " + receiver.length() + " " + receiver;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    if (content != null) {
      result += " " + content.length() + " " + content;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    return result;
  }

  /**
   * Override hashCode to differentiate messages based on messageId.
   *
   * @return the hashCode.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
    return result;
  }

  /**
   * Override the equals method to compare messages based on messageId.
   *
   * @param obj the object to compare this to.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Message))
      return false;
    Message other = (Message) obj;
    if (messageId == null) {
      if (other.messageId != null)
        return false;
    } else if (!messageId.equals(other.messageId)) {
      return false;
    }
    return true;
  }
}
