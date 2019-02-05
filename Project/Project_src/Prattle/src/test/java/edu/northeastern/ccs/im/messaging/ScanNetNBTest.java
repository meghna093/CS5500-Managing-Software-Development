package edu.northeastern.ccs.im.messaging;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.dao.GroupMongo;
import edu.northeastern.ccs.im.dao.MessageMongo;
import edu.northeastern.ccs.im.dao.UserMongo;
import edu.northeastern.ccs.im.data_models.Group;
import edu.northeastern.ccs.im.data_models.Message;
import edu.northeastern.ccs.im.data_models.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.junit.jupiter.api.Assertions.*;


class ScanNetNBTest {
  private static User userMatt;
  private static Group group1;
  private static final String host = "localhost";
  private static int PORT = 4544;
  private static ServerSocketChannel serverSocket;
  private static Thread th;

  /**
   * Clear the database, create a user and group, create a serverSocketChannel on port 4544 to
   * test ScanNetNB using PrintNetNB.
   */
  @BeforeAll
  public static void setUp() {
    userMatt = new User("Matt", "Matt");
    group1 = new Group("group1");
    group1.addUser(userMatt.getUsername());

    th = new Thread(() -> {
      try {
        serverSocket = ServerSocketChannel.open();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        serverSocket.socket().bind(new InetSocketAddress(PORT));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    th.start();
  }

  /**
   * Clear the database and close a serverSocketChannel on port 4544 after testing ScanNetNB using
   * PrintNetNB.
   */
  @AfterAll
  public static void tearDown() throws IOException {
    serverSocket.close();
    try {
      th.join(100);
    } catch (InterruptedException e) {
      fail();
    }
  }

  /**
   * Test reading a no-acknowledge message using ScanNetNB from the serverSocket.
   */
  @Test
  void testNakMessage() throws IOException {
    Message msg = Message.makeNoAcknowledgeMessage();

    SocketChannel printSocket = socketChannelHelper(host, PORT);
    PrintNetNB pnb = new PrintNetNB(printSocket);

    SocketChannel scanSocket = scanConnectToServer();
    ScanNetNB snb = new ScanNetNB(scanSocket);

    assertTrue(pnb.print(msg));
    assertTrue(snb.hasNextMessage());
    assertTrue(compareMessages(msg, snb.nextMessage()));
    printSocket.close();
    snb.close();
    scanSocket.close();
  }

  /**
   * Test reading an acknowledge message using ScanNetNB from the serverSocket.
   */
  @Test
  void testAkMessage() throws IOException {
    Message msg = Message.makeAcknowledgeMessage("Matt");

    SocketChannel printSocket = socketChannelHelper(host, PORT);
    PrintNetNB pnb = new PrintNetNB(printSocket);

    SocketChannel scanSocket = scanConnectToServer();
    ScanNetNB snb = new ScanNetNB(scanSocket);

    assertTrue(pnb.print(msg));
    assertTrue(snb.hasNextMessage());
    assertTrue(compareMessages(msg, snb.nextMessage()));
    printSocket.close();
    snb.close();
    scanSocket.close();
  }

  /**
   * Nested class to close scanSocket and scanNetNB selector for testNextNone.
   */
  @Nested
  class wrapperClassTestNonMessage {
    SocketChannel scanSocket;
    ScanNetNB snb;

    /**
     * Close ScanNetNB selector and scanSocket.
     *
     * @throws IOException various socket issues.
     */
    @AfterEach
    public void closeSockets() throws IOException {
      snb.close();
      scanSocket.close();
    }

    /**
     * Test nextMessage throws NextDoesNotExistException when there no next messages.
     *
     * @throws IOException various socket issues.
     */
    @Test
    public void testNextNone() throws IOException {
      scanSocket = socketChannelHelper(host, PORT);
      snb = new ScanNetNB(scanSocket);

      assertFalse(snb.hasNextMessage());

      assertThrows(NextDoesNotExistException.class, () -> {
        snb.nextMessage();
      });
    }
  }

  /**
   * Helper method to create a socket channel and connect to the serverSocketChannel on port 4544.
   *
   * @param hostSocket the host of the serverSocketChannel to connect to.
   * @param portSocket the port of the serverSocketChannel to connect to.
   * @return the socket channel that has connected with the serverSocketChannel.
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

  /**
   * Helper method that has the serverSocket accept the message written by PrintNetNB and create
   * a SocketChannel to use with ScanNetNB.
   *
   * @return the SocketChannel connected to serverSocket.
   */
  private SocketChannel scanConnectToServer() {
    SocketChannel scanSocket = null;
    try {
      scanSocket = serverSocket.accept();
    } catch (Exception e1) {
      fail();
    }
    try {
      scanSocket.configureBlocking(false);
    } catch (Exception e1) {
      fail();
    }
    return scanSocket;
  }

  /**
   * Compare msg and nextMessage toString results.
   *
   * @param msg         the message to compare with nextMessage.
   * @param nextMessage the message to compare with msg.
   * @return true if the message's toString results are the same, false otherwise.
   */
  private boolean compareMessages(Message msg, Message nextMessage) {
    return msg.toString().equals(nextMessage.toString());
  }
}