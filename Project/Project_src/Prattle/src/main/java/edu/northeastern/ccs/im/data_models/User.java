package edu.northeastern.ccs.im.data_models;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * User class that represents a user of the messaging system.
 *
 * @author Matthew Lazarcheck
 */
public class User {
  private String username;
  private String password;
  private List<String> groups;
  private List<Message> messageQueue;
  private List<Group> listeners;

  /**
   * The constructor of a user of the messaging system.
   *
   * @param username the username of the user.
   * @param password the password of the user.
   * @throws IllegalArgumentException if the username or password is invalid.
   */
  public User(String username, String password) {
    this.validateInput(username);
    this.validateInput(password);

    this.username = username;
    this.password = password;
    this.groups = new ArrayList<>();
    this.messageQueue = new ArrayList<>();
    this.listeners = new ArrayList<>();
  }

  /**
   * User constructor that creates a user instance with a username and userId parameter.
   *
   * @param username the username of the user.
   * @param password the password of the user.
   * @param groups   the list of groups the user is in.
   * @throws IllegalArgumentException if the username or password is invalid.
   */
  public User(String username, String password, List<String> groups) {
    this.validateInput(username);
    this.validateInput(password);

    this.username = username;
    this.password = password;
    this.groups = new ArrayList<>(groups);
    this.messageQueue = new ArrayList<>();
    this.listeners = new ArrayList<>();
  }

  public User(String username, String password, List<String> groupList, List<Message> messageList, List<Group> listeners) {
    this.validateInput(username);
    this.validateInput(password);

    this.username = username;
    this.password = password;
    this.groups = new ArrayList<>(groupList);
    this.messageQueue = new ArrayList<>(messageList);
    this.listeners = new ArrayList<>(listeners);
  }

  /**
   * Validates username and password input for the constructors is not null or length zero.
   *
   * @param credential the username or password to validate.
   */
  private void validateInput(String credential) {
    if (credential == null) {
      throw new IllegalArgumentException("Username cannot be null.");
    }

    if (credential.length() == 0) {
      throw new IllegalArgumentException("Username cannot be blank.");
    }
  }

  /**
   * Get the username of the user.
   *
   * @return the username of the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Get the password of the user.
   *
   * @return the password of the user.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Add group membership to the user.
   *
   * @param group the group to add the user to.
   */
  public void addGroup(String group) {
    this.groups.add(group);
  }

  /**
   * Remove group membership from the user.
   *
   * @param group the group to remove the user from.
   */
  public void removeGroup(Group group) {
    int i = 0;
    for (String groupSearch : groups) {
      if (groupSearch.equals(group.getGroupName())) {
        this.groups.remove(i);
        return;
      } else {
        i++;
      }
    }
  }

  /**
   * Get the user's groups.
   *
   * @return the user's groups.
   */
  public List<String> getGroups() {
    return this.groups;
  }

  /**
   * Add a message to the message queue.
   *
   * @param m the message to enqueue
   */
  public void enqueueMessage(Message m) {
    messageQueue.add(m);
  }

  /**
   * Clears the message queue.
   */
  public void clearMessageQueue() {
    messageQueue = new ArrayList<>();
  }

  /**
   * Returns the message queue.
   *
   * @return the message queue
   */
  public List<Message> getMessageQueue() {
    return messageQueue;
  }
  
  public void addListener(Group listener) {
	  listeners.add(listener);
  }
  
  public void removeListener(Group listener) {
	  listeners.remove(listener);
  }
  
  public List<Group> getListeners() {
	  return listeners;
  }

  /**
   * Uses Base64 to encode and encrypt the given password.
   *
   * @param password to be encrypted.
   * @return Base64 encoded password string.
   */
  public static String encryptPassword(String password) {
    return Base64.getEncoder().encodeToString(password.getBytes());
  }

  /**
   * Uses Base64 to decode and decrypt the given encoded password.
   *
   * @param encodedPassword the Base64 encoded password.
   * @return the decoded password.
   */
  public static String decryptPassword(String encodedPassword) {
    byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
    return new String(decodedBytes);
  }
}
