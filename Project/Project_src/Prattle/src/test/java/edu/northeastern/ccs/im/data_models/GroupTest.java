package edu.northeastern.ccs.im.data_models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Junit test class for Group.
 *
 * @author Matthew Lazarcheck
 */
class GroupTest {
  private static Group team101;
  private static Group team102;
  private static Group team103;
  private static Group team104;

  private static User user1;
  private static User user2;

  private static Message message1;
  private static Message message2;

  /**
   * Create user instances to test.
   */
  @BeforeAll
  public static void setUp() {
    team101 = new Group("team101");
    team102 = new Group("team102", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
    team103 = new Group("team103");
    team104 = new Group("team104", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
    user1 = new User("user1", "pass");
    user2 = new User("user2", "pass");
    message1 = new Message(1, "user1", null, Message.MessageType.USER_MESSAGE, "hello", null);
    message2 = new Message(2, "user2", null, Message.MessageType.USER_MESSAGE, "hello! How are you?",
            null);
  }

  /**
   * Test blank groupName for single parameter constructor.
   */
  @Test
  public void testBlankGroupnameOne() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Group("");
    });
  }

  /**
   * Test blank groupName for triple parameter constructor.
   */
  @Test
  public void testBlankGroupnameThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Group("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
    });
  }

  /**
   * Test null username for single parameter constructor.
   */
  @Test
  public void testNullGroupNameOne() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Group(null);
    });
  }

  /**
   * Test null username for triple parameter constructor.
   */
  @Test
  public void testNullGroupNameThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Group(null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
    });
  }

  /**
   * Test getGroupName.
   */
  @Test
  public void testGetGroupName() {
    assertEquals("team101", team101.getGroupName());
    assertEquals("team102", team102.getGroupName());
    assertEquals("team103", team103.getGroupName());
    assertEquals("team104", team104.getGroupName());
  }

  /**
   * Test addUser.
   */
  @Test
  public void testAddUser() {
    team101.addUser(user1.getUsername());
    team101.addUser(user2.getUsername());
    assertEquals("user1", team101.getUsers().get(0));
    assertEquals("user2", team101.getUsers().get(1));

    team101.removeUser(user2);
    assertEquals("user1", team101.getUsers().get(0));
    team101.removeUser(user1);
    assertTrue(team101.getUsers().isEmpty());
  }

  /**
   * Test getUsers.
   */
  @Test
  public void testGetUsers() {
    team101.addUser(user1.getUsername());
    team101.addUser(user2.getUsername());
    assertEquals("user1", team101.getUsers().get(0));
    assertEquals("user2", team101.getUsers().get(1));

    team101.removeUser(user2);
    assertEquals("user1", team101.getUsers().get(0));
    team101.removeUser(user1);
    assertTrue(team101.getUsers().isEmpty());
  }

  /**
   * Test removeUser.
   */
  @Test
  public void testRemoveUser() {
    team102.addUser(user1.getUsername());
    team102.addUser(user2.getUsername());
    assertEquals("user1", team102.getUsers().get(0));
    assertEquals("user2", team102.getUsers().get(1));

    team102.removeUser(user2);
    assertEquals("user1", team102.getUsers().get(0));
    team102.removeUser(user1);
    assertTrue(team102.getUsers().isEmpty());
  }

  /**
   * Test removeUser.
   */
  @Test
  public void testRemoveUserEmpty() {
    team102.removeUser(user2);
  }

  /**
   * Test addMessage.
   */
  @Test
  public void addMessage() {
    team101.addMessage(message1);
    team101.addMessage(message2);

    Message result1 = team101.getMessages().get(0);
    Message result2 = team101.getMessages().get(1);

    assertTrue(result1.equals(message1) && "hello".equals(message1.getContent()));
    assertTrue(result2.equals(message2) && "hello! How are you?".equals(message2.getContent()));
  }

  /**
   * Test getMessage.
   */
  @Test
  public void getMessages() {
    team102.addMessage(message1);
    team102.addMessage(message2);

    Message result1 = team102.getMessages().get(0);
    Message result2 = team102.getMessages().get(1);

    assertTrue(result1.equals(message1) && "hello".equals(message1.getContent()));
    assertTrue(result2.equals(message2) && "hello! How are you?".equals(message2.getContent()));
  }

  /**
   * Test addListener.
   */
  @Test
  public void testListener() {
    Group listener = new Group("listener");
    team102.addListener(listener);
    assertEquals(listener, team102.getListeners().get(0));
    team102.removeListener(listener);
    assertTrue(team102.getListeners().isEmpty());
  }

  /**
   * Test setExpirationDate..
   */
  @Test
  public void testExpirationDate() {
    team102.setExpirationDate(200);
    assertEquals(team102.getExpirationDate(), 200);
  }
}
