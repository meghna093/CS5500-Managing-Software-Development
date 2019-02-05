package edu.northeastern.ccs.im.darserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.northeastern.ccs.im.IMConnection;
import edu.northeastern.ccs.im.dao.GroupMongo;
import edu.northeastern.ccs.im.dao.MessageMongo;
import edu.northeastern.ccs.im.dao.UserMongo;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.User;
import edu.northeastern.ccs.im.server.ClientRunnable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Junit test class for ClientRunnableExt. For some reason these tests do not run right in the
 * clientRunnableTest class on Jenkins, but they work in a separate test class.
 *
 * @author Matthew Lazarcheck
 */
public class ClientRunnableExtTest {
  private static UserMongo mongoUser;
  private static GroupMongo mongoGroup;
  private static MessageMongo mongoMessage;

  private static int PORT = 4544;
  private static int THREAD_POOL_SIZE = 20;
  private static int CLIENT_CHECK_DELAY = 200;
  private static ConcurrentLinkedQueue<ClientRunnable> active;
  private static ScheduledExecutorService threadPool;
  private static String[] names;

  private static ServerSocketChannel serverSocket = null;

  /**
   * Create group, user, userMongo and groupMongo instances to test.
   * Sets up the listening socket.
   */
  @BeforeEach
  void setUpAll() {
    mongoUser = UserMongo.getInstance();
    mongoGroup = GroupMongo.getInstance();
    mongoMessage = MessageMongo.getInstance();

    names = new String[3];
    names[0] = "one";
    names[1] = "two";
    names[2] = "three";

    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    for (int i = 1; i <= 3; i++) {
      User user = new User(names[i - 1], names[i - 1]);
      mongoUser.createUser(user);
    }

    mongoUser.createUser(new User("admin", "admin"));
    mongoGroup.createGroup(new Group("group0"));

    mongoUser.addUserToGroup(UserMongo.makeUser(mongoUser.findUserByUsername("one")),
            GroupMongo.makeGroup(mongoGroup.findGroupByName("group0")));
    mongoUser.addUserToGroup(UserMongo.makeUser(mongoUser.findUserByUsername("two")),
            GroupMongo.makeGroup(mongoGroup.findGroupByName("group0")));

    active = new ConcurrentLinkedQueue<>();
    try {
      serverSocket = ServerSocketChannel.open();
    } catch (Exception e) {
      fail("Failed to open server socket channel");
    }
    try {
      serverSocket.configureBlocking(false);
    } catch (Exception e) {
      fail("Failed to configure blocking");
    }
    while (PORT < 8000) {
      try {
        serverSocket.socket().bind(new InetSocketAddress(PORT));
        break;
      } catch (IOException e) {
        PORT++;
      }
    }
    if (PORT == 8000) fail();

    // Create the Selector with which our channel is registered.
    Selector selector = null;
    try {
      selector = SelectorProvider.provider().openSelector();
    } catch (IOException e1) {
      fail("Failed to open selector");
    }
    // Register to receive any incoming connection messages.
    try {
      serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    } catch (ClosedChannelException e1) {
      fail("Failed to register selector");
    }
    // Create our pool of threads on which we will execute.
    threadPool = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
  }

  /**
   * Close a serverSocketChannel on port 4545 after testing ScanNetNB with socket channels.
   */
  @AfterEach
  void tearDown() throws IOException, InterruptedException {
    TimeUnit.MILLISECONDS.sleep(500);

    mongoUser.deleteAllUsers();
    mongoGroup.deleteAllGroups();
    mongoMessage.deleteAllMessages();
    mongoMessage.deleteAllMessageCounters();

    serverSocket.close();
  }

  /**
   * Test private message.
   */
  @Test
  void testPrivateMessageUser() throws InterruptedException {
    Thread t = new Thread(() -> {
      IMConnection connect = new IMConnection("localhost", PORT, "one", "one");
      assertTrue(connect.login());
      connect.sendMessage("@two hello three!");
      connect.disconnect();
    });
    t.start();
    TimeUnit.MILLISECONDS.sleep(100);

    try {
      SocketChannel socket = serverSocket.accept();
      assertNotNull(socket, "failed to accept new connection");
      ClientRunnable tt = new ClientRunnable(socket);
      active.add(tt);
      @SuppressWarnings({"unchecked"})
      ScheduledFuture<ClientRunnable> clientFuture = (ScheduledFuture<ClientRunnable>) threadPool.scheduleAtFixedRate(tt, CLIENT_CHECK_DELAY,
              CLIENT_CHECK_DELAY, TimeUnit.MILLISECONDS);
      tt.setFuture(clientFuture);
      TimeUnit.MILLISECONDS.sleep(CLIENT_CHECK_DELAY * 2 + 10);
    } catch (Exception e) {
      fail();
    }

    t.join(1000);
  }

  /**
   * Test delete user.
   */
  @Test
  void testDeleteUser() throws InterruptedException {
    Thread t1 = new Thread(() -> {
      IMConnection connect = new IMConnection("localhost", PORT, "one", "one");
      assertTrue(connect.login());
      connect.deleteUser();
      connect.disconnect();
    });
    t1.start();
    TimeUnit.MILLISECONDS.sleep(100);

    try {
      SocketChannel socket = serverSocket.accept();
      assertNotNull(socket, "failed to accept new connection");
      ClientRunnable tt = new ClientRunnable(socket);
      active.add(tt);
      @SuppressWarnings({"unchecked"})
      ScheduledFuture<ClientRunnable> clientFuture = (ScheduledFuture<ClientRunnable>) threadPool.scheduleAtFixedRate(tt, CLIENT_CHECK_DELAY,
              CLIENT_CHECK_DELAY, TimeUnit.MILLISECONDS);
      tt.setFuture(clientFuture);
      TimeUnit.MILLISECONDS.sleep(CLIENT_CHECK_DELAY * 2 + 10);
    } catch (Exception e) {
      fail();
    }
    t1.join(1000);
  }
}
