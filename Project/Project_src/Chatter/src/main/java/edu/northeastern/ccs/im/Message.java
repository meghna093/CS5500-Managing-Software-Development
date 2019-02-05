package edu.northeastern.ccs.im;

/**
 * Each instance of this class represents a single transmission by our IM
 * clients.
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/4.0/. It is based on work
 * originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @version 1.3
 */
public class Message {
  /**
   * List of the different possible message types.
   */
  protected enum MessageType {
    /**
     * Message sent by the user attempting to login using a specified username.
     */
    HELLO("HLO"),
    /**
     * Message sent by the server acknowledging a successful log in.
     */
    ACKNOWLEDGE("ACK"),
    /**
     * Message sent by the server rejecting a login attempt.
     */
    NO_ACKNOWLEDGE("NAK"),
    /**
     * Message sent by the user to start the logging out process and sent by the
     * server once the logout process completes.
     */
    QUIT("BYE"),
    USER_MESSAGE("UMG"),
    GROUP_MESSAGE("GMG"),
    LOGIN("LGN"),
    REGISTER("REG"),
    DELETE_USER("DEL"),
    UPDATE_USER("UPT"),
    FIND_USER("FND"),
    JOIN_GROUP("GRP"),
    CREATE_GROUP("CRT"),
    ADD_USER("ADD"),
    UPDATE_GROUP("UPG"),
    DELETE_GROUP("DEG"),
    REMOVE_USER("RMU"),
    LEAVE_GROUP("LEG"),
    TRACK_USER("TRU"),
    TRACK_GROUP("TRG");
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

  /**
   * The handle of the message.
   */
  private MessageType msgType;

  /**
   * The first argument used in the message. This will be the sender's identifier.
   */
  private String msgSender;

  private String receiver;

  /**
   * The second argument used in the message.
   */
  private String msgText;

  /**
   * Create a new message that contains actual IM text. The type of distribution
   * is defined by the handle and we must also set the name of the message sender,
   * message recipient, and the text to send.
   *
   * @param handle  Handle for the type of message being created.
   * @param srcName Name of the individual sending this message
   * @param text    Text of the instant message
   */
  private Message(MessageType handle, String srcName, String text) {
    msgType = handle;
    // Save the properly formatted identifier for the user sending the
    // message.
    msgSender = srcName;
    // Save the text of the message.
    msgText = text;
  }

  public Message(MessageType type, String sender, String receiver, String text) {
    msgType = type;
    msgSender = sender;
    this.receiver = receiver;
    msgText = text;
  }

  /**
   * Create simple command type message that does not include any data.
   *
   * @param handle Handle for the type of message being created.
   */
  private Message(MessageType handle) {
    this(handle, null, null);
  }

  /**
   * Create a new message that contains a command sent the server that requires a
   * single argument. This message contains the given handle and the single
   * argument.
   *
   * @param handle  Handle for the type of message being created.
   * @param srcName Argument for the message; at present this is the name used to
   *                log-in to the IM server.
   */
  private Message(MessageType handle, String srcName) {
    this(handle, srcName, null);
  }

  /**
   * Create a new message to continue the logout process.
   *
   * @return Instance of Message that specifies the process is logging out.
   */
  public static Message makeQuitMessage(String myName) {
    return new Message(MessageType.QUIT, myName, null);
  }

  /**
   * Given a handle, name and text, return the appropriate message instance or an
   * instance from a subclass of message.
   *
   * @param handle  Handle of the message to be generated.
   * @param srcName Name of the originator of the message (may be null)
   * @param text    Text sent in this message (may be null)
   * @return Instance of Message (or its subclasses) representing the handle,
   * name, & text.
   */
  protected static Message makeMessage(String handle, String srcName, String text) {
    Message result = null;
    if (handle.compareTo(MessageType.QUIT.toString()) == 0) {
      result = makeQuitMessage(srcName);
    } else if (handle.compareTo(MessageType.HELLO.toString()) == 0) {
      result = makeLoginMessage(srcName, text);
    } else if (handle.compareTo(MessageType.USER_MESSAGE.toString()) == 0) {
      result = makeUserMessage(srcName, text);
    } else if (handle.compareTo(MessageType.GROUP_MESSAGE.toString()) == 0) {
      result = makeGroupMessage(srcName, text);
    } else if (handle.compareTo(MessageType.ACKNOWLEDGE.toString()) == 0) {
      result = makeAcknowledgeMessage(srcName);
    } else if (handle.compareTo(MessageType.NO_ACKNOWLEDGE.toString()) == 0) {
      result = makeNoAcknowledgeMessage(srcName, text);
    } else if (handle.compareTo(MessageType.DELETE_USER.toString()) == 0) {
      result = makeDeleteMessage(srcName);
    } else if (handle.compareTo(MessageType.UPDATE_USER.toString()) == 0) {
      result = makeUpdateMessage(srcName, text);
    } else if (handle.compareTo(MessageType.FIND_USER.toString()) == 0) {
      result = makeFindMessage(srcName, text);
    }
    return result;
  }

  /**
   * Create a new message to reject the bad login attempt.
   *
   * @return Instance of Message that rejects the bad login attempt.
   */
  public static Message makeNoAcknowledgeMessage(String srcName, String text) {
    return new Message(MessageType.NO_ACKNOWLEDGE, srcName, text);
  }

  /**
   * Create a new message to acknowledge that the user successfully logged as the
   * name <code>srcName</code>.
   *
   * @param srcName Name the user was able to use to log in.
   * @return Instance of Message that acknowledges the successful login.
   */
  public static Message makeAcknowledgeMessage(String srcName) {
    return new Message(MessageType.ACKNOWLEDGE, srcName);
  }

  /**
   * Create a new message for the early stages when the user logs in without all
   * the special stuff.
   *
   * @param myName Name of the user who has just logged in.
   * @return Instance of Message specifying a new friend has just logged in.
   */
  public static Message makeLoginMessage(String myName, String password) {
    return new Message(MessageType.LOGIN, myName, password);
  }

  /**
   * Create a new register message to create a new user.
   *
   * @param username the username to use
   * @param password the password to use
   */
  public static Message makeRegisterMessage(String username, String password) {
    return new Message(MessageType.REGISTER, username, password);
  }

  public static Message makeDeleteMessage(String username) {
    return new Message(MessageType.DELETE_USER, username);
  }

  public static Message makeUpdateMessage(String username, String newUsername) {
    return new Message(MessageType.UPDATE_USER, username, newUsername);
  }

  public static Message makeFindMessage(String username, String userName) {
    return new Message(MessageType.FIND_USER, username, userName);
  }

  public static Message makeUserMessage(String username, String userName) {
    return new Message(MessageType.USER_MESSAGE, username, userName);
  }

  public static Message makeGroupMessage(String username, String userName) {
    return new Message(MessageType.GROUP_MESSAGE, username, userName);
  }

  /**
   * Return the type of this message.
   *
   * @return MessageType for this message.
   */
  public MessageType getType() {
    return msgType;
  }

  /**
   * Return the name of the sender of this message.
   *
   * @return String specifying the name of the message originator.
   */
  public String getSender() {
    return msgSender;
  }

  /**
   * Return the text of thdis message.
   *
   * @return String equal to the text sent by this message.
   */
  public String getText() {
    return msgText;
  }

  /**
   * Determine if this message is an acknowledgement message.
   *
   * @return True if the message is an acknowledgement message; false otherwise.
   */
  public boolean isAcknowledge() {
    return (msgType == MessageType.ACKNOWLEDGE);
  }

  /**
   * Determine if this message is broadcasting text to one user.
   *
   * @return True if the message is a user message; false otherwise.
   */
  public boolean isUserMessage() {
    return (msgType == MessageType.USER_MESSAGE);
  }

  /**
   * Determine if this message is broadcasting text to a group.
   *
   * @return True if the message is a group message; false otherwise.
   */
  public boolean isGroupMessage() {
    return (msgType == MessageType.GROUP_MESSAGE);
  }

  /**
   * Determine if this message is sent by a new client to log-in to the server.
   *
   * @return True if the message is an initialization message; false otherwise
   */
  public boolean isInitialization() {
    return (msgType == MessageType.HELLO);
  }

  /**
   * Determine if this message is a message signing off from the IM server.
   *
   * @return True if the message is sent when signing off; false otherwise
   */
  public boolean terminate() {
    return (msgType == MessageType.QUIT);
  }

  public boolean isJoingGroup() {
    return msgType == MessageType.JOIN_GROUP;
  }

  public boolean isCreateGroup() {
    return msgType == MessageType.CREATE_GROUP;
  }

  public boolean isAddUser() {
    return msgType == MessageType.ADD_USER;
  }

  public boolean isUpdateGroup() {
    return msgType == MessageType.UPDATE_GROUP;
  }

  public boolean isDeleteGroup() {
    return msgType == MessageType.DELETE_GROUP;
  }

  public boolean isRemoveUser() {
    return msgType == MessageType.REMOVE_USER;
  }

  public boolean isLeaveGroup() {
    return msgType == MessageType.LEAVE_GROUP;
  }


  /**
   * Representation of this message as a String. This begins with the message
   * handle and then contains the length (as an integer) and the value of the next
   * two arguments.
   *
   * @return Representation of this message as a String.
   */
  @Override
  public String toString() {
    String result = msgType.toString();
    if (msgSender != null) {
      result += " " + msgSender.length() + " " + msgSender;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    if (receiver != null) {
      result += " " + receiver.length() + " " + receiver;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    if (msgText != null) {
      result += " " + msgText.length() + " " + msgText;
    } else {
      result += " " + NULL_OUTPUT.length() + " " + NULL_OUTPUT;
    }
    return result;
  }
}
