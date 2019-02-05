package edu.northeastern.ccs.im.dao;

import edu.northeastern.ccs.im.dao.exceptions.GroupDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.GroupNameNotAvailableException;
import edu.northeastern.ccs.im.dao.exceptions.MessageDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.RelationshipException;
import edu.northeastern.ccs.im.dao.exceptions.UserDoesNotExistException;
import edu.northeastern.ccs.im.dao.exceptions.UsernameNotAvailableException;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

import org.bson.Document;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class for UserMongo.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
class DaoTest {
  private static User userMatt;
  private static User userJason;
  private static User userMeghna;
  private static User userAk;
  private static User userCurt;
  private static Group groupTeam101;
  private static Group groupTeam102;
  private static Group groupTeam103;
  private static Group groupTeam104;
  private static Group groupTeam10;
  private static UserMongo mongoUser;
  private static GroupMongo mongoGroup;
  private static MessageMongo mongoMessage;


  /**
   * 50% chance of a 2.5 minute timeout due to a Jenkins concurrency issue with database tests
   * when open a pull request.
   */
  @BeforeAll
  static void setUp() {
    Random random = new Random();

    if (random.nextBoolean()) {
      try {
        TimeUnit.SECONDS.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Create group, user, userMongo and groupMongo instances to test.
   */
  @BeforeEach
  void setUpAll() {
    mongoUser = UserMongo.getInstance();
    mongoGroup = GroupMongo.getInstance();
    mongoMessage = MessageMongo.getInstance();

    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    mongoMessage.deleteAllMessages();
    mongoMessage.deleteAllMessageCounters();

    userMatt = new User("Matt", "Matt");
    userJason = new User("Jason", "Jason");
    userMeghna = new User("Meghna", "Meghna");
    userAk = new User("Ak", "Ak");
    userCurt = new User("Curt", "Curt");
    groupTeam101 = new Group("Team101");
    groupTeam102 = new Group("Team102");
    groupTeam103 = new Group("Team103");
    groupTeam104 = new Group("Team104");
    groupTeam10 = new Group("Team10");
  }

  /**
   * Delete documents in collection to avoid conflicts.
   */
  @AfterEach
  void tearDown() {
    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    mongoMessage.deleteAllMessages();
    mongoMessage.deleteAllMessageCounters();
  }

  /**
   * Test createUser.
   */
  @Test
  void testCreateUser() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);
    mongoUser.createUser(userMeghna);
    mongoUser.createUser(userAk);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    assertEquals("Matt", mattByName.get("name"));
    assertEquals(User.encryptPassword("Matt"), mattByName.get("password"));
    Document jasonByName = mongoUser.findUserByUsername(userJason.getUsername());
    assertEquals("Jason", jasonByName.get("name"));
    assertEquals(User.encryptPassword("Jason"), jasonByName.get("password"));
    Document meghnaByName = mongoUser.findUserByUsername(userMeghna.getUsername());
    assertEquals("Meghna", meghnaByName.get("name"));
    assertEquals(User.encryptPassword("Meghna"), meghnaByName.get("password"));
    Document akByName = mongoUser.findUserByUsername(userAk.getUsername());
    assertEquals("Ak", akByName.get("name"));
    assertEquals(User.encryptPassword("Ak"), akByName.get("password"));
  }

  /**
   * Test createUser when the username already exists.
   */
  @Test
  void testCreateUserExists() {
    mongoUser.createUser(userMatt);
    User newUser = new User("Matt", "Matt");
    assertThrows(UsernameNotAvailableException.class, () -> {
      mongoUser.createUser(newUser);
    });
  }

  /**
   * Test addUserToGroup.
   */
  @Test
  void testAddToGroup() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);
    mongoUser.createUser(userMeghna);
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userMatt, groupTeam10);
    mongoUser.addUserToGroup(userJason, groupTeam101);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    Document jasonByName = mongoUser.findUserByUsername(userJason.getUsername());
    User matt = UserMongo.makeUser(mattByName);
    User jason = UserMongo.makeUser(jasonByName);

    assertEquals(userMatt.getGroups().size(), matt.getGroups().size());
    assertEquals(groupTeam101.getGroupName(), matt.getGroups().get(0));
    assertEquals(groupTeam10.getGroupName(), matt.getGroups().get(1));
    assertEquals(groupTeam101.getGroupName(), jason.getGroups().get(0));
  }

  /**
   * Test addUserToGroup when user already exists in group.
   */
  @Test
  void testAddToGroupExists() {
    mongoUser.createUser(userMatt);
    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userMatt, groupTeam101);

    assertThrows(RelationshipException.class, () -> {
      mongoUser.addUserToGroup(userMatt, groupTeam101);
    });
  }

  /**
   * Test removeUserFromGroup.
   */
  @Test
  void testRemoveFromGroup() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userMatt, groupTeam10);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    User matt = UserMongo.makeUser(mattByName);

    assertEquals(2, matt.getGroups().size());

    mongoUser.removeUserFromGroup(userMatt, groupTeam10);

    mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    matt = UserMongo.makeUser(mattByName);
    assertEquals(1, matt.getGroups().size());
  }

  /**
   * Test enqueueMessageToUser.
   */
  @Test
  void testEnqueueMessageToUser() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMatt, groupTeam10);
    mongoUser.addUserToGroup(userAk, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("GMG"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("UMG"), "Hello", "12th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);

    mongoUser.enqueueMessageToUser(userMatt, message1);
    mongoUser.enqueueMessageToUser(userMatt, message2);

    mongoUser.enqueueMessageToUser(userAk, message2);
    mongoUser.enqueueMessageToUser(userAk, message1);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    Document akByName = mongoUser.findUserByUsername(userAk.getUsername());
    User matt = UserMongo.makeUser(mattByName);
    User ak = UserMongo.makeUser(akByName);

    assertEquals(message1, matt.getMessageQueue().get(0));
    assertEquals(message2, matt.getMessageQueue().get(1));

    assertEquals(message2, ak.getMessageQueue().get(0));
    assertEquals(message1, ak.getMessageQueue().get(1));
  }

  /**
   * Test clearMessageQueue.
   */
  @Test
  void testClearMessageQueue() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMatt, groupTeam10);
    mongoUser.addUserToGroup(userAk, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("GMG"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("UMG"), "Hello", "12th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);

    mongoUser.enqueueMessageToUser(userMatt, message1);
    mongoUser.enqueueMessageToUser(userMatt, message2);

    mongoUser.enqueueMessageToUser(userAk, message2);
    mongoUser.enqueueMessageToUser(userAk, message1);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    Document akByName = mongoUser.findUserByUsername(userAk.getUsername());
    User matt = UserMongo.makeUser(mattByName);
    User ak = UserMongo.makeUser(akByName);

    assertEquals(message1, matt.getMessageQueue().get(0));
    assertEquals(message2, matt.getMessageQueue().get(1));

    assertEquals(message2, ak.getMessageQueue().get(0));
    assertEquals(message1, ak.getMessageQueue().get(1));

    mongoUser.clearMessageQueue(matt);
    mongoUser.clearMessageQueue(ak);

    mattByName = mongoUser.findUserByUsername(matt.getUsername());
    akByName = mongoUser.findUserByUsername(ak.getUsername());
    matt = UserMongo.makeUser(mattByName);
    ak = UserMongo.makeUser(akByName);

    assertTrue(matt.getMessageQueue().isEmpty());
    assertTrue(ak.getMessageQueue().isEmpty());
  }

  /**
   * Test removeUserFromGroup when user is not present in the group.
   */
  @Test
  void testRemoveFromGroupNotExists() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);

    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userMatt, groupTeam101);

    assertThrows(RelationshipException.class, () -> {
      mongoUser.removeUserFromGroup(userJason, groupTeam101);
    });
  }

  /**
   * Test findUser.
   */
  @Test
  void testFindUser() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);
    mongoUser.createUser(userMeghna);
    mongoUser.createUser(userAk);

    assertNull(mongoUser.findUserByUsername(userCurt.getUsername()));

    Document mattByLogin = mongoUser.findUserByLogin(userMatt.getUsername(), userMatt.getPassword());
    assertEquals(User.encryptPassword("Matt"), mattByLogin.get("password"));

    assertThrows(UserDoesNotExistException.class, () -> {
      mongoUser.findUserByLogin(userMatt.getUsername(), "wrongPassword");
    });

    assertThrows(UserDoesNotExistException.class, () -> {
      mongoUser.findUserByLogin("wrongUsername", userMatt.getPassword());
    });
  }

  /**
   * Test updateUser.
   */
  @Test
  void testUpdateUser() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userMeghna);

    mongoUser.updateUser("Matt", "Jason");
    mongoUser.updateUser("Meghna", "Ak");

    assertNull(mongoUser.findUserByUsername(userMatt.getUsername()));
    assertNull(mongoUser.findUserByUsername(userMeghna.getUsername()));
    assertEquals("Jason", mongoUser.findUserByUsername("Jason").get("name"));
    assertEquals("Ak", mongoUser.findUserByUsername("Ak").get("name"));
  }

  /**
   * Test updateUser when user does not exist.
   */
  @Test
  void testUpdateUserExist() {
    mongoUser.createUser(userMatt);

    assertThrows(UserDoesNotExistException.class, () -> {
      mongoUser.updateUser("Matthew", "NewMatt");
    });
  }

  /**
   * Test updateUser when new username is taken.
   */
  @Test
  void testUpdateUserNewExist() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);
    Assertions.assertThrows(UserDoesNotExistException.class, () -> {
      mongoUser.updateUser("Matt", "Jason");
    });
  }

  /**
   * Test deleteUser.
   */
  @Test
  void testDeleteUser() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userJason);
    mongoUser.createUser(userMeghna);
    mongoUser.createUser(userAk);

    Document mattByName = mongoUser.findUserByUsername(userMatt.getUsername());
    assertEquals("Matt", mattByName.get("name"));
    Document jasonByName = mongoUser.findUserByUsername(userJason.getUsername());
    assertEquals("Jason", jasonByName.get("name"));
    Document meghnaByName = mongoUser.findUserByUsername(userMeghna.getUsername());
    assertEquals("Meghna", meghnaByName.get("name"));
    Document akByName = mongoUser.findUserByUsername(userAk.getUsername());
    assertEquals("Ak", akByName.get("name"));

    mongoUser.deleteUser("Matt");
    mongoUser.deleteUser("Jason");
    mongoUser.deleteUser("Meghna");
    mongoUser.deleteUser("Ak");


    assertNull(mongoUser.findUserByUsername("Matt"));
    assertNull(mongoUser.findUserByUsername("Jason"));
    assertNull(mongoUser.findUserByUsername("Meghna"));
    assertNull(mongoUser.findUserByUsername("Ak"));
  }

  /**
   * Test deleteUser not in database.
   */
  @Test
  void testDeleteUserNotExist() {
    assertThrows(UserDoesNotExistException.class,
            () -> {
              mongoUser.deleteUser("Matt");
            });
  }

  /**
   * Test createGroup.
   */
  @Test
  void testCreateGroup() {
    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);
    mongoGroup.createGroup(groupTeam103);
    mongoGroup.createGroup(groupTeam104);

    Document team101ByName = mongoGroup.findGroupByName(groupTeam101.getGroupName());
    assertEquals(groupTeam101.getGroupName(), team101ByName.get("name"));
    Document team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    assertEquals(groupTeam102.getGroupName(), team102ByName.get("name"));
    Document team103ByName = mongoGroup.findGroupByName(groupTeam103.getGroupName());
    assertEquals(groupTeam103.getGroupName(), team103ByName.get("name"));
    Document team104ByName = mongoGroup.findGroupByName(groupTeam104.getGroupName());
    assertEquals(groupTeam104.getGroupName(), team104ByName.get("name"));
  }

  /**
   * Test createGroup when the group name already exists.
   */
  @Test
  void testCreateGroupExists() {
    mongoGroup.createGroup(groupTeam101);
    Group newGroup = new Group("Team101");
    assertThrows(GroupNameNotAvailableException.class, () -> {
      mongoGroup.createGroup(newGroup);
    });
  }

  /**
   * Test addUserToGroup.
   */
  @Test
  void testAddToGroup0() {
    mongoUser.createUser(userAk);
    mongoUser.createUser(userMeghna);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);

    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMeghna, groupTeam102);
    mongoUser.addUserToGroup(userAk, groupTeam102);

    Document team101ByName = mongoGroup.findGroupByName(groupTeam101.getGroupName());
    Document team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    Group team101 = GroupMongo.makeGroup(team101ByName);
    Group team102 = GroupMongo.makeGroup(team102ByName);

    assertEquals(groupTeam101.getUsers().size(), team101.getUsers().size());
    assertEquals(groupTeam101.getGroupName(), userAk.getGroups().get(0));
    assertEquals(groupTeam101.getUsers().get(0), team101.getUsers().get(0));
    assertEquals(groupTeam102.getUsers().get(0), team102.getUsers().get(0));
    assertEquals(groupTeam102.getUsers().get(1), team102.getUsers().get(1));
    assertEquals(userAk.getUsername(), team101.getUsers().get(0));
    assertEquals(userMeghna.getUsername(), team102.getUsers().get(0));
    assertEquals(userAk.getUsername(), team102.getUsers().get(1));
  }

  /**
   * Test addUserToGroup when group already has user.
   */
  @Test
  void testAddToGroupExists0() {
    mongoUser.createUser(userAk);
    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userAk, groupTeam101);

    assertThrows(RelationshipException.class, () -> {
      mongoUser.addUserToGroup(userAk, groupTeam101);
    });
  }

  /**
   * Test removeUserFromGroup.
   */
  @Test
  void testRemoveFromGroup0() {
    mongoUser.createUser(userAk);
    mongoUser.createUser(userMeghna);

    mongoGroup.createGroup(groupTeam102);

    mongoUser.addUserToGroup(userMeghna, groupTeam102);
    mongoUser.addUserToGroup(userAk, groupTeam102);

    Document team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    Group team102 = GroupMongo.makeGroup(team102ByName);

    assertEquals(2, team102.getUsers().size());

    mongoUser.removeUserFromGroup(userAk, groupTeam102);

    team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    team102 = GroupMongo.makeGroup(team102ByName);
    assertEquals(1, team102.getUsers().size());
  }

  /**
   * Test removeUserFromGroup when group does not have the user.
   */
  @Test
  void testRemoveFromGroupNotExists0() {
    mongoUser.createUser(userAk);
    mongoUser.createUser(userMeghna);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);

    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMeghna, groupTeam102);

    assertThrows(RelationshipException.class, () -> {
      mongoUser.removeUserFromGroup(userMeghna, groupTeam101);
    });

    assertThrows(RelationshipException.class, () -> {
      mongoUser.removeUserFromGroup(userAk, groupTeam102);
    });
  }

  /**
   * Test findGroup.
   */
  @Test
  void testFindGroup() {
    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);
    mongoGroup.createGroup(groupTeam103);

    Document team101ByName = mongoGroup.findGroupByName(groupTeam101.getGroupName());
    assertEquals(groupTeam101.getGroupName(), team101ByName.get("name"));
    Document team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    assertEquals(groupTeam102.getGroupName(), team102ByName.get("name"));
    Document team103ByName = mongoGroup.findGroupByName(groupTeam103.getGroupName());
    assertEquals(groupTeam103.getGroupName(), team103ByName.get("name"));

    assertNull(mongoGroup.findGroupByName(groupTeam104.getGroupName()));
  }

  /**
   * Test updateGroup.
   */
  @Test
  void testUpdateGroup() {
    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);

    mongoGroup.updateGroup("Team101", "newTeam101");
    mongoGroup.updateGroup("Team102", "newTeam102");

    assertNull(mongoGroup.findGroupByName(groupTeam101.getGroupName()));
    assertNull(mongoGroup.findGroupByName(groupTeam102.getGroupName()));
    assertEquals("newTeam101", mongoGroup.findGroupByName("newTeam101").get("name"));
    assertEquals("newTeam102", mongoGroup.findGroupByName("newTeam102").get("name"));
  }

  /**
   * Test updateGroup with oldName does not exist.
   */
  @Test
  void testUpdateGroupOldDoesNotExist() {
    mongoGroup.createGroup(groupTeam101);

    assertThrows(GroupNameNotAvailableException.class, () -> {
      mongoGroup.updateGroup("Team102", "newTeam102");
    });
  }

  /**
   * Test updateGroup with newName already taken.
   */
  @Test
  void testUpdateGroupNewAlreadyExists() {
    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);

    assertThrows(GroupNameNotAvailableException.class, () -> {
      mongoGroup.updateGroup("Team101", "Team102");
    });
  }

  /**
   * Test deleteGroup.
   */
  @Test
  void testDeleteGroup() {
    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam102);
    mongoGroup.createGroup(groupTeam103);
    mongoGroup.createGroup(groupTeam104);

    Document team101ByName = mongoGroup.findGroupByName(groupTeam101.getGroupName());
    assertEquals(groupTeam101.getGroupName(), team101ByName.get("name"));
    Document team102ByName = mongoGroup.findGroupByName(groupTeam102.getGroupName());
    assertEquals(groupTeam102.getGroupName(), team102ByName.get("name"));
    Document team103ByName = mongoGroup.findGroupByName(groupTeam103.getGroupName());
    assertEquals(groupTeam103.getGroupName(), team103ByName.get("name"));
    Document team104ByName = mongoGroup.findGroupByName(groupTeam104.getGroupName());
    assertEquals(groupTeam104.getGroupName(), team104ByName.get("name"));

    mongoGroup.deleteGroup("Team101");
    mongoGroup.deleteGroup("Team102");
    mongoGroup.deleteGroup("Team103");
    mongoGroup.deleteGroup("Team104");

    assertNull(mongoGroup.findGroupByName("Team101"));
    assertNull(mongoGroup.findGroupByName("Team102"));
    assertNull(mongoGroup.findGroupByName("Team103"));
    assertNull(mongoGroup.findGroupByName("Team104"));
  }

  /**
   * Test deleteGroup not in database.
   */
  @Test
  void testDeleteGroupNotExist() {
    assertThrows(GroupDoesNotExistException.class,
            () -> {
              mongoGroup.deleteGroup("Team101");
            });
  }

  /**
   * Test createMessage.
   */
  @Test
  void testCreateMessage() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("UMG"), "Hello", "12th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);

    assertEquals(message1.getMessageId(), mongoMessage.findMessageById(1).get("mid"));
    assertEquals(message2.getMessageId(), mongoMessage.findMessageById(2).get("mid"));
  }

  /**
   * Test createMessage exceptions.
   */
  @Test
  void testCreateMessageException() {
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userAk, groupTeam101);

    Message message1 = new Message(userAk.getUsername(), groupTeam10.getGroupName(), null, "Hi " +
            "there", "11th Jan");

    assertThrows(RelationshipException.class, () -> {
      mongoMessage.createMessage(message1);
    });
  }

  /**
   * Test findMessageById.
   */
  @Test
  void testFindMessageById() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);
    mongoUser.createUser(userJason);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userJason, groupTeam10);
    mongoUser.addUserToGroup(userMatt, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hello", "12th jan");
    Message message3 = new Message(userJason.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("BYE"), "Hi Jason", "13th jan");
    Message message4 = new Message(userMatt.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hi Second Message", "14th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);
    mongoMessage.createMessage(message3);
    mongoMessage.createMessage(message4);

    assertEquals(message1.getMessageId(), mongoMessage.findMessageById(1).get("mid"));
    assertEquals(message2.getMessageId(), mongoMessage.findMessageById(2).get("mid"));
    assertEquals(message3.getMessageId(), mongoMessage.findMessageById(3).get("mid"));
    assertEquals(message4.getMessageId(), mongoMessage.findMessageById(4).get("mid"));
  }

  /**
   * Test findMessageById when it message does not exist.
   */
  @Test
  void testFindMessageByIdNotExist() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);

    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("REG"), "Hello", "12th jan");
    message2.setMessageId(2);

    mongoMessage.createMessage(message1);

    assertThrows(MessageDoesNotExistException.class, () -> {
      mongoMessage.findMessageById(message2.getMessageId());
    });
  }

  /**
   * Test findMessagesBySender.
   */
  @Test
  void testFindMessagesBySender() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);
    mongoUser.createUser(userJason);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userJason, groupTeam10);
    mongoUser.addUserToGroup(userMatt, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hello", "12th jan");
    Message message3 = new Message(userJason.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hi Jason", "13th jan");
    Message message4 = new Message(userMatt.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("REG"), "Hi Second Message", "14th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);
    mongoMessage.createMessage(message3);
    mongoMessage.createMessage(message4);

    List<Document> mattMessages = mongoMessage.findMessagesBySender(userMatt);
    List<Integer> mattMsgIds = new ArrayList<>();
    for (Document message : mattMessages) {
      mattMsgIds.add((int) message.get("mid"));
    }
    assertEquals(2, mattMsgIds.size());
    assertEquals(message1.getMessageId(), mattMsgIds.get(0));
    assertEquals(message4.getMessageId(), mattMsgIds.get(1));

    List<Document> akMessages = mongoMessage.findMessagesBySender(userAk);
    List<Integer> akMsgIds = new ArrayList<>();
    for (Document message : akMessages) {
      akMsgIds.add((int) message.get("mid"));
    }
    assertEquals(1, akMsgIds.size());
    assertEquals(message2.getMessageId(), akMsgIds.get(0));

    List<Document> jasonMessages = mongoMessage.findMessagesBySender(userJason);
    List<Integer> jasonMsgIds = new ArrayList<>();
    for (Document message : jasonMessages) {
      jasonMsgIds.add((int) message.get("mid"));
    }
    assertEquals(1, jasonMsgIds.size());
    assertEquals(message3.getMessageId(), jasonMsgIds.get(0));
  }

  /**
   * Test findMessagesBySender when none exist.
   */
  @Test
  void testFindMessagesBySenderNotExist() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);
    mongoUser.createUser(userMeghna);

    mongoGroup.createGroup(groupTeam101);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMeghna, groupTeam101);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hello", "12th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);

    assertThrows(MessageDoesNotExistException.class, () -> {
      mongoMessage.findMessagesBySender(userMeghna);
    });
  }

  /**
   * Test findMessagesByReceiver.
   */
  @Test
  void testFindMessagesByReceiver() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);
    mongoUser.createUser(userJason);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam10);
    mongoUser.addUserToGroup(userJason, groupTeam101);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam10.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hello", "12th jan");
    Message message3 = new Message(userJason.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("REG"), "Hi Jason", "13th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);
    mongoMessage.createMessage(message3);

    List<Document> team101Messages = mongoMessage.findMessagesByReceiver(groupTeam101);
    List<Integer> team101MsgIds = new ArrayList<>();
    for (Document message : team101Messages) {
      team101MsgIds.add((int) message.get("mid"));
    }
    assertEquals(2, team101Messages.size());
    assertEquals(message1.getMessageId(), team101MsgIds.get(0));
    assertEquals(message3.getMessageId(), team101MsgIds.get(1));

    List<Document> team10Messages = mongoMessage.findMessagesByReceiver(groupTeam10);
    List<Integer> team10MsgIds = new ArrayList<>();
    for (Document message : team10Messages) {
      team10MsgIds.add((int) message.get("mid"));
    }
    assertEquals(1, team10MsgIds.size());
    assertEquals(message2.getMessageId(), team10MsgIds.get(0));
  }

  /**
   * Test findMessagesByReceiever when none exist.
   */
  @Test
  void testFindMessagesByReceiverNotExist() {
    mongoUser.createUser(userMatt);
    mongoUser.createUser(userAk);
    mongoUser.createUser(userMeghna);

    mongoGroup.createGroup(groupTeam101);
    mongoGroup.createGroup(groupTeam10);

    mongoUser.addUserToGroup(userMatt, groupTeam101);
    mongoUser.addUserToGroup(userAk, groupTeam101);
    mongoUser.addUserToGroup(userMeghna, groupTeam10);

    Message message1 = new Message(userMatt.getUsername(), groupTeam101.getGroupName(), Message.MessageType.getMessageType("HLO"), "Hi there", "11th Jan");
    Message message2 = new Message(userAk.getUsername(), groupTeam101.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hello", "12th jan");

    mongoMessage.createMessage(message1);
    mongoMessage.createMessage(message2);

    assertThrows(MessageDoesNotExistException.class, () -> {
      mongoMessage.findMessagesByReceiver(groupTeam10);
    });
  }
}