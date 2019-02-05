package edu.northeastern.ccs.im.darserver;

import edu.northeastern.ccs.im.dao.GroupMongo;
import edu.northeastern.ccs.im.dao.MessageMongo;
import edu.northeastern.ccs.im.dao.UserMongo;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.Message.MessageType;
import edu.northeastern.ccs.im.data_models.User;
import edu.northeastern.ccs.im.messaging.PrintNetNB;
import edu.northeastern.ccs.im.server.Prattle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Junit test class for Prattle main method.
 *
 * @author Jason Teng
 * @author Matthew Lazarcheck
 * @author Akshat Shukla
 */
class PrattleTest {
  private static UserMongo mongoUser;
  private static GroupMongo mongoGroup;
  private static MessageMongo mongoMessage;
  private static User userMatt;
  private static Group groupTeam101;

  private static int PORT = 4545;
  private static String HOST = "localhost";

  /**
   * Clear database and create user instance to test.
   */
  @BeforeEach
  void setUpAll() {
    mongoUser = UserMongo.getInstance();
    mongoGroup = GroupMongo.getInstance();
    mongoMessage = MessageMongo.getInstance();

    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    userMatt = new User("Matt", "Matt");
    mongoUser.createUser(userMatt);
    groupTeam101 = new Group("Team101");
    mongoGroup.createGroup(groupTeam101);
    mongoUser.addUserToGroup(userMatt, groupTeam101);
  }

  /**
   * Clear database after tests.
   */
  @AfterAll
  static void tearDown() {
    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    mongoMessage.deleteAllMessages();
    mongoMessage.deleteAllMessageCounters();
  }

  /**
   * Test Prattle main method.
   */
  @Test
  void testMain() throws InterruptedException {
    assertTrue(true);
    Prattle.setDone(false);
    Thread th0 = new Thread(() -> {
      String[] args = new String[]{Integer.toString(PORT)};
      try {
        Prattle.main(args);
      } catch (IOException e) {
        fail();
      }
    });
    th0.start();

    TimeUnit.MILLISECONDS.sleep(100);

    // Simulate a client connection
    Thread th1 = new Thread(() -> {
      Message msgLogin = new Message("Matt", null, Message.MessageType.LOGIN, "Matt", null);
      Message msgJoin = new Message("Matt", null, Message.MessageType.JOIN_GROUP, "Team101",
              null);
      Message msgGrp = new Message("Matt", "Team101", Message.MessageType.GROUP_MESSAGE, "Hello. " +
              "Does this work.", null);
      Message msgLgt = new Message("Matt", null, Message.MessageType.QUIT, null, null);

      SocketChannel printSocket = null;
      try {
        printSocket = socketChannelHelper(HOST, PORT);
      } catch (IOException e) {
        fail();
      }
      PrintNetNB pnb = new PrintNetNB(printSocket);

      assertTrue(pnb.print(msgLogin));
      assertTrue(pnb.print(msgJoin));
      assertTrue(pnb.print(msgGrp));
      assertTrue(pnb.print(msgLgt));
      try {
        printSocket.close();
      } catch (IOException e) {
        fail();
      }

      try {
        TimeUnit.MILLISECONDS.sleep(1500);
      } catch (InterruptedException e) {
        fail();
      }
    });
    th1.start();

    try {
      TimeUnit.MILLISECONDS.sleep(1500);
    } catch (InterruptedException e) {
      fail();
    }

    Prattle.setDone(true);

    try {
      th0.join(3000);
      th1.join(3000);
    } catch (InterruptedException e) {
      fail();
    }
  }

  /**
   * Test sendToListeners.
   */
  @Test
  public void testSendToListeners1() {
    List<Group> listeners = new ArrayList<>();
    Group expiredListener = new Group("g1", new ArrayList<String>(), new ArrayList<>(), new ArrayList<>(), System.currentTimeMillis());
    mongoGroup.createGroup(expiredListener);
    listeners.add(expiredListener);
    List<String> users = new ArrayList<>();
    users.add(userMatt.getUsername());
    Group activeListener = new Group("g2", users, new ArrayList<>(), new ArrayList<>(), System.currentTimeMillis() + 1000000);
    mongoGroup.createGroup(activeListener);
    listeners.add(activeListener);
    mongoGroup.addListener("Team101", "g1", 0);
    mongoGroup.addListener("Team101", "g2", 5);
    Prattle.sendMessage(new Message("Matt", "Team101", MessageType.GROUP_MESSAGE, "hello", null));
  }

  /**
   * Test sendToListeners.
   */
  @Test
  public void testSendToListeners2() {
    List<Group> listeners = new ArrayList<>();
    Group expiredListener = new Group("g1", new ArrayList<String>(), new ArrayList<>(), new ArrayList<>(), System.currentTimeMillis());
    mongoGroup.createGroup(expiredListener);
    listeners.add(expiredListener);
    List<String> users = new ArrayList<>();
    users.add(userMatt.getUsername());
    Group activeListener = new Group("g2", users, new ArrayList<>(), new ArrayList<>(), System.currentTimeMillis() + 1000000);
    mongoGroup.createGroup(activeListener);
    listeners.add(activeListener);
    mongoUser.addListener("Matt", "g1", 0);
    mongoUser.addListener("Matt", "g2", 5);
    Prattle.sendMessage(new Message("Matt", "Team101", MessageType.GROUP_MESSAGE, "hello", null));
  }

  /**
   * Helper method to create a socket channel and connect to the prattle main serverSocket on port
   * 4544.
   *
   * @param hostSocket the host of prattle main to connect to.
   * @param portSocket the port of prattle main to connect to.
   * @return the socket channel that has connected with prattle main.
   * @throws IOException for various socket issues.
   */
  private SocketChannel socketChannelHelper(String hostSocket, int portSocket) throws
          IOException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);
    if (!channel.connect(new InetSocketAddress(hostSocket, portSocket))) {
      final Selector selector = Selector.open();
      final SelectionKey key = channel.register(selector, SelectionKey.OP_CONNECT);
      selector.select(0);
      assert key.isConnectable();
      if (!channel.finishConnect()) {
        throw new IOException("Error, something went wrong and I was unable" + " to finish making this connection");
      }
      selector.close();
    }
    return channel;
  }
}
