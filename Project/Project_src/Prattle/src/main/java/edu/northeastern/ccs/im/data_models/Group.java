package edu.northeastern.ccs.im.data_models;

import java.util.ArrayList;
import java.util.List;

/**
 * Group class that represents a group of users on the messaging system.
 *
 * @author Matthew Lazarcheck
 */
public class Group {
  private String groupName;
  private List<String> users;
  private List<Message> messages;
  private List<Group> listeners;
  private long expirationDate;

  /**
   * The group constructor that has a groupName parameter.
   *
   * @param groupName the group name.
   * @throws IllegalArgumentException if the username is invalid.
   */
  public Group(String groupName) {
    this.validateGroupName(groupName);

    this.groupName = groupName;
    this.users = new ArrayList<>();
    this.messages = new ArrayList<>();
    this.listeners = new ArrayList<>();
    this.expirationDate = 0;
  }

  /**
   * The group constructor that has a groupName, users and messsages parameters.
   *
   * @param groupName the group name.
   * @param users     the list of users.
   * @param messages  the list of messages.
   * @throws IllegalArgumentException if the username is invalid.
   */
  public Group(String groupName, List<String> users, List<Message> messages, List<Group> listeners, long expirationDate) {
    this.validateGroupName(groupName);

    this.groupName = groupName;
    this.users = users;
    this.messages = new ArrayList<>(messages);
    this.listeners = new ArrayList<>(listeners);
    this.expirationDate = expirationDate;
  }

  /**
   * Validate group name is not null and length of zero.
   *
   * @param groupName the group name to validate.
   */
  private void validateGroupName(String groupName) {
    if (groupName == null) {
      throw new IllegalArgumentException("Groupname cannot be null.");
    }

    if (groupName.length() == 0) {
      throw new IllegalArgumentException("Groupname cannot be blank.");
    }
  }

  /**
   * Get the group name.
   *
   * @return the group name.
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Add a user to the group.
   *
   * @param user the user to add to the group.
   */
  public void addUser(String user) {
    this.users.add(user);
  }

  /**
   * Get the users from the group.
   *
   * @return the users of the group.
   */
  public List<String> getUsers() {
    return this.users;
  }

  /**
   * Remove user from the group.
   *
   * @param user the user to remove from the group.
   */
  public void removeUser(User user) {
    int i = 0;
    for (String userSearch : users) {
      if (userSearch.equals(user.getUsername())) {
        this.users.remove(i);
        return;
      } else {
        i++;
      }
    }
  }

  /**
   * Add message to the group.
   *
   * @param message the message to add to the group.
   */
  public void addMessage(Message message) {
    this.messages.add(message);
  }

  /**
   * Get the messages from the group.
   *
   * @return the messages of the group.
   */
  public List<Message> getMessages() {
    return messages;
  }

  /**
   * Add listener to group.
   *
   * @param listener the listening group to add.
   */
  public void addListener(Group listener) {
	  listeners.add(listener);
  }

  /**
   * Remove lister from group.
   *
   * @param listener the listening group to remove.
   */
  public void removeListener(Group listener) {
	  listeners.remove(listener);
  }

  /**
   * Get the listeners for the group.
   *
   * @return the listeners for the group.
   */
  public List<Group> getListeners() {
	  return listeners;
  }

  /**
   * Expiration date for listeners of the group.
   *
   * @param expirationDate the expiration date of the listeners.
   */
  public void setExpirationDate(long expirationDate) {
	  this.expirationDate = expirationDate;
  }

  /**
   * Get the expiration date for listeners of the group.
   *
   * @return the expiration date of the listeners.
   */
  public long getExpirationDate() {
	  return expirationDate;
  }
}
