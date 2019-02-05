package edu.northeastern.ccs.im.data_models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class for User.
 *
 * @author Matthew Lazarcheck
 */
class UserTest {
  private static User userMatt;
  private static User userJason;
  private static User userMeghna;
  private static User userAk;
  private static Group team101;
  private static Group team102;
  private static Message umg;
  private static Message gmg;


  /**
   * Create user instances to test.
   */
  @BeforeAll
  static void setUp() {
    userMatt = new User("Matt", "pass");
    userJason = new User("Jason", "pass", new ArrayList<>());
    userMeghna = new User("Meghna", "Meghna", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    userAk = new User("Ak", "Meghna", new ArrayList<>());
    team101 = new Group("team101");
    team102 = new Group("team102");
    umg = new Message(5, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("UMG"), "Hey image file!", "1542863031199");
    gmg = new Message(19, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("GMG"), "Hey image file!", "1542863031199");
  }

  /**
   * Test blank username for double parameter constructor.
   */
  @Test
  void testBlankUsernameTwo() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("", "pass");
    });
  }

  /**
   * Test blank username for triple parameter constructor.
   */
  @Test
  void testBlankUsernameThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("", "pass", new ArrayList<>());
    });
  }

  /**
   * Test blank password for double parameter constructor.
   */
  @Test
  void testBlankPasswordTwo() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("Matt", "");
    });
  }

  /**
   * Test blank password for triple parameter constructor.
   */
  @Test
  void testBlankPasswordThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("Matt", "", new ArrayList<>());
    });
  }

  /**
   * Test null username for double parameter constructor.
   */
  @Test
  void testNullUsernameTwo() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User(null, "pass");
    });
  }

  /**
   * Test null username for triple parameter constructor.
   */
  @Test
  void testNullUsernameThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User(null, "pass");
    });
  }

  /**
   * Test null password for double parameter constructor.
   */
  @Test
  void testNullPasswordTwo() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("Matt", null);
    });
  }

  /**
   * Test null password for triple parameter constructor.
   */
  @Test
  void testNullPasswordThree() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new User("Matt", null);
    });
  }

  /**
   * Test getUsername.
   */
  @Test
  void testGetUsername() {
    assertEquals("Matt", userMatt.getUsername());
    assertEquals("Jason", userJason.getUsername());
    assertEquals("Meghna", userMeghna.getUsername());
    assertEquals("Ak", userAk.getUsername());
  }


  /**
   * Test getPassword.
   */
  @Test
  void testGetPassword() {
    assertEquals("pass", userMatt.getPassword());
    assertEquals("pass", userJason.getPassword());
    assertEquals("Meghna", userMeghna.getPassword());
    assertEquals("Meghna", userAk.getPassword());
  }

  /**
   * Test addGroup.
   */
  @Test
  void testAddGroup() {
    userJason.addGroup(team101.getGroupName());
    userJason.addGroup(team102.getGroupName());
    assertEquals("team101", userJason.getGroups().get(0));
    assertEquals("team102", userJason.getGroups().get(1));

    userJason.removeGroup(team102);
    assertEquals("team101", userJason.getGroups().get(0));
    userJason.removeGroup(team101);
    assertTrue(userJason.getGroups().isEmpty());
  }

  /**
   * Test removeGroup.
   */
  @Test
  void testRemoveGroup() {
    userMatt.addGroup(team101.getGroupName());
    userMatt.addGroup(team102.getGroupName());
    assertEquals("team101", userMatt.getGroups().get(0));
    assertEquals("team102", userMatt.getGroups().get(1));

    userMatt.removeGroup(team102);
    assertEquals("team101", userMatt.getGroups().get(0));
    userMatt.removeGroup(team101);
    assertTrue(userMatt.getGroups().isEmpty());
  }

  /**
   * Test removeGroup empty.
   */
  @Test
  void testRemoveGroupEmpty() {
    userMatt.removeGroup(team102);
  }

  /**
   * Test enqueueMessage.
   */
  @Test
  void testEnqueueMessage() {
    assertTrue(userMatt.getMessageQueue().isEmpty());
    assertTrue(userJason.getMessageQueue().isEmpty());

    userMatt.enqueueMessage(umg);
    userMatt.enqueueMessage(gmg);

    userJason.enqueueMessage(gmg);
    userJason.enqueueMessage(umg);

    assertEquals(umg, userMatt.getMessageQueue().get(0));
    assertEquals(gmg, userMatt.getMessageQueue().get(1));

    assertEquals(gmg, userJason.getMessageQueue().get(0));
    assertEquals(umg, userJason.getMessageQueue().get(1));

    userMatt.clearMessageQueue();
    userJason.clearMessageQueue();
  }

  /**
   * Test getMessageQueue.
   */
  @Test
  void testGetMessageQueue() {
    assertTrue(userMatt.getMessageQueue().isEmpty());
    assertTrue(userJason.getMessageQueue().isEmpty());

    userMatt.enqueueMessage(umg);
    userMatt.enqueueMessage(gmg);

    userJason.enqueueMessage(gmg);
    userJason.enqueueMessage(umg);

    assertEquals(umg, userMatt.getMessageQueue().get(0));
    assertEquals(gmg, userMatt.getMessageQueue().get(1));

    assertEquals(gmg, userJason.getMessageQueue().get(0));
    assertEquals(umg, userJason.getMessageQueue().get(1));

    userMatt.clearMessageQueue();
    userJason.clearMessageQueue();
  }

  /**
   * Test clearMessageQueue.
   */
  @Test
  void testClearMessageQueue() {
    assertTrue(userMatt.getMessageQueue().isEmpty());
    assertTrue(userJason.getMessageQueue().isEmpty());

    userMatt.enqueueMessage(umg);
    userMatt.enqueueMessage(gmg);

    userJason.enqueueMessage(gmg);
    userJason.enqueueMessage(umg);

    assertEquals(umg, userMatt.getMessageQueue().get(0));
    assertEquals(gmg, userMatt.getMessageQueue().get(1));

    assertEquals(gmg, userJason.getMessageQueue().get(0));
    assertEquals(umg, userJason.getMessageQueue().get(1));

    userMatt.clearMessageQueue();
    userJason.clearMessageQueue();

    assertTrue(userMatt.getMessageQueue().isEmpty());
    assertTrue(userJason.getMessageQueue().isEmpty());
  }

  /**
   * Test getGroups.
   */
  @Test
  void testGetGroups() {

  }

  /**
   * Test encryptPassword.
   */
  @Test
  void testEncryptPassword() {
    String encryptedPass0 = User.encryptPassword("pass");
    String encryptedPass1 = User.encryptPassword("password12345");

    assertTrue(encryptedPass0 != "pass");
    assertEquals("pass", User.decryptPassword(encryptedPass0));
    assertTrue(encryptedPass0 != "password12345");
    assertEquals("password12345", User.decryptPassword(encryptedPass1));
  }

  /**
   * Test decryptPassword.
   */
  @Test
  void testDecryptPassword() {
    String encryptedPass0 = User.encryptPassword("anotherpass");
    String encryptedPass1 = User.encryptPassword("54321password");

    assertTrue(encryptedPass0 != "anotherpass");
    assertEquals("anotherpass", User.decryptPassword(encryptedPass0));
    assertTrue(encryptedPass0 != "54321password");
    assertEquals("54321password", User.decryptPassword(encryptedPass1));
  }

  /**
   * Test addListener.
   */
  @Test
  public void testListener() {
    Group listener = new Group("listener");
    userMatt.addListener(listener);
    assertEquals(listener, userMatt.getListeners().get(0));
    userMatt.removeListener(listener);
    assertTrue(userMatt.getListeners().isEmpty());
  }
}