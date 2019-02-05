package edu.northeastern.ccs.im.data_models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.northeastern.ccs.im.data_models.Message.MessageType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Junit test class for Message.
 *
 * @author Akshat Shukla
 * @author Matthew Lazarcheck
 */
class MessageTest {
  private static User userMatt;
  private static User userMeghna;
  private static User userAk;
  private static Group team101;
  private static Group team102;

  private static Message hlo;
  private static Message ack;
  private static Message nak;
  private static Message bye;
  private static Message umg;
  private static Message lgn;
  private static Message reg;
  private static Message del;
  private static Message upt;
  private static Message fnd;
  private static Message grp;
  private static Message crt;
  private static Message add;
  private static Message upg;
  private static Message deg;
  private static Message rmu;
  private static Message leg;
  private static Message noId;
  private static Message noReceiver;
  private static Message gmg;
  private static Message noTime;
  private static Message makeAck;
  private static Message makeNoAck;
  private static Message trackUser;
  private static Message trackGroup;
  private static Message startLogging;
  private static Message stopLogging;

  /**
   * Create message instances to test and user and group instances to use.
   */
  @BeforeAll
  static void setUp() {
    userMatt = new User("Matt", "pass");
    userMeghna = new User("Meghna", "Meghna");
    userAk = new User("Ak", "Ak");
    team101 = new Group("team101");
    team102 = new Group("team102");

    hlo = new Message(1, userMatt.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("HLO"), "Hey music file!", "1542863031199");
    ack = new Message(2, userMeghna.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("ACK"), "Hey image file!", "1542863031199");
    nak = new Message(3, userAk.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("NAK"), "Hey another image file!", "1542863031199");
    bye = new Message(4, userMatt.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("BYE"), "Hey music file!", "1542863031199");
    umg = new Message(5, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("UMG"), "Hey image file!", "1542863031199");
    lgn = new Message(6, userAk.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("LGN"), "Hey another image file!", "1542863031199");
    reg = new Message(7, userMatt.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("REG"), "Hey music file!", "1542863031199");
    del = new Message(8, userMeghna.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("DEL"), "Hey image file!", "1542863031199");
    upt = new Message(9, userAk.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("UPT"), "Hey another image file!", "1542863031199");
    fnd = new Message(10, userMatt.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("FND"), "Hey music file!", "1542863031199");
    grp = new Message(11, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("GRP"), "Hey image file!", "1542863031199");
    crt = new Message(12, userAk.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("CRT"), "Hey another image file!", "1542863031199");
    add = new Message(13, userMatt.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("ADD"), "Hey music file!", "1542863031199");
    upg = new Message(14, userMeghna.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("UPG"), "Hey image file!", "1542863031199");
    deg = new Message(15, userAk.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("DEG"), "Hey another image file!", "1542863031199");
    rmu = new Message(16, userMatt.getUsername(), team102.getGroupName(),
            Message.MessageType.getMessageType("RMU"), "Hey music file!", "1542863031199");
    leg = new Message(17, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("LEG"), "Hey image file!", "1542863031199");
    noReceiver = new Message(18, userMeghna.getUsername(), null,
            Message.MessageType.getMessageType("LEG"), "Hey image file!", "1542863031199");
    gmg = new Message(19, userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("GMG"), "Hey image file!", "1542863031199");
    noId = new Message(userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("UPG"),
            "Hey image file!", "1542863031199");
    noTime = new Message(userMeghna.getUsername(), team101.getGroupName(),
            Message.MessageType.getMessageType("UPG"),
            "Hey image file!", null);
    trackUser = new Message("Meghna", "team101", MessageType.getMessageType("TRU"), "", "", false);
    trackGroup = new Message("team102", "team101", MessageType.getMessageType("TRG"), "", "",
            false);
    startLogging = new Message("admin", "", MessageType.getMessageType("LON"), "", "", false);
    stopLogging = new Message("admin", "", MessageType.getMessageType("LOF"), "", "", false);
    makeAck = Message.makeAcknowledgeMessage("Matt");
    makeNoAck = Message.makeNoAcknowledgeMessage();
  }

  /**
   * Test getMessageId.
   */
  @Test
  void testGetMessageId() {
    assertEquals(1, (int) hlo.getMessageId());
    assertEquals(2, (int) ack.getMessageId());
    assertEquals(3, (int) nak.getMessageId());
    assertEquals(4, (int) bye.getMessageId());
    assertEquals(5, (int) umg.getMessageId());
    assertEquals(6, (int) lgn.getMessageId());
    assertEquals(7, (int) reg.getMessageId());
    assertEquals(8, (int) del.getMessageId());
    assertEquals(9, (int) upt.getMessageId());
    assertEquals(10, (int) fnd.getMessageId());
    assertEquals(11, (int) grp.getMessageId());
    assertEquals(12, (int) crt.getMessageId());
    assertEquals(13, (int) add.getMessageId());
    assertEquals(14, (int) upg.getMessageId());
    assertEquals(15, (int) deg.getMessageId());
    assertEquals(16, (int) rmu.getMessageId());
    assertEquals(17, (int) leg.getMessageId());
    assertEquals(null, makeAck.getMessageId());
    assertEquals(null, makeNoAck.getMessageId());
  }

  /**
   * Test setMessageId.
   */
  @Test
  void testSetMessageId() {
    assertEquals(null, noId.getMessageId());
    assertEquals(18, (int) noReceiver.getMessageId());
    noReceiver.setMessageId(20);
    noId.setMessageId(18);
    assertEquals(18, (int) noId.getMessageId());
    assertEquals(20, (int) noReceiver.getMessageId());
  }

  /**
   * Test getSender.
   */
  @Test
  void testGetSender() {
    assertEquals(userMatt.getUsername(), hlo.getSender());
    assertEquals(userMeghna.getUsername(), ack.getSender());
    assertEquals(userAk.getUsername(), nak.getSender());
    assertEquals(userMatt.getUsername(), bye.getSender());
    assertEquals(userMeghna.getUsername(), umg.getSender());
    assertEquals(userAk.getUsername(), lgn.getSender());
    assertEquals(userMatt.getUsername(), reg.getSender());
    assertEquals(userMeghna.getUsername(), del.getSender());
    assertEquals(userAk.getUsername(), upt.getSender());
    assertEquals(userMatt.getUsername(), fnd.getSender());
    assertEquals(userMeghna.getUsername(), grp.getSender());
    assertEquals(userAk.getUsername(), crt.getSender());
    assertEquals(userMatt.getUsername(), add.getSender());
    assertEquals(userMeghna.getUsername(), upg.getSender());
    assertEquals(userAk.getUsername(), deg.getSender());
    assertEquals(userMatt.getUsername(), rmu.getSender());
    assertEquals(userMeghna.getUsername(), leg.getSender());
    assertEquals(userMeghna.getUsername(), gmg.getSender());
    assertEquals(null, makeAck.getSender());
    assertEquals(null, makeNoAck.getSender());
  }

  /**
   * Test getReceiver.
   */
  @Test
  void testGetReceiver() {
    assertEquals(team101.getGroupName(), hlo.getReceiver());
    assertEquals(team102.getGroupName(), ack.getReceiver());
    assertEquals(team101.getGroupName(), nak.getReceiver());
    assertEquals(team102.getGroupName(), bye.getReceiver());
    assertEquals(team101.getGroupName(), umg.getReceiver());
    assertEquals(team102.getGroupName(), lgn.getReceiver());
    assertEquals(team101.getGroupName(), reg.getReceiver());
    assertEquals(team102.getGroupName(), del.getReceiver());
    assertEquals(team101.getGroupName(), upt.getReceiver());
    assertEquals(team102.getGroupName(), fnd.getReceiver());
    assertEquals(team101.getGroupName(), grp.getReceiver());
    assertEquals(team102.getGroupName(), crt.getReceiver());
    assertEquals(team101.getGroupName(), add.getReceiver());
    assertEquals(team102.getGroupName(), upg.getReceiver());
    assertEquals(team101.getGroupName(), deg.getReceiver());
    assertEquals(team102.getGroupName(), rmu.getReceiver());
    assertEquals(team101.getGroupName(), leg.getReceiver());
    assertEquals(team101.getGroupName(), gmg.getReceiver());
    assertEquals(null, makeAck.getReceiver());
    assertEquals(null, makeNoAck.getReceiver());
  }

  /**
   * Test setReceiver.
   */
  @Test
  void testSetReceiver() {
    assertEquals(team101.getGroupName(), noId.getReceiver());
    assertEquals(null, noReceiver.getReceiver());
    noReceiver.setReceiver(team101.getGroupName());
    noId.setReceiver(team102.getGroupName());
    assertEquals(team102.getGroupName(), noId.getReceiver());
    assertEquals(team101.getGroupName(), noReceiver.getReceiver());
  }

  /**
   * Test getMessageType.
   */
  @Test
  void testGetMessageType() {
    assertEquals(MessageType.HELLO, hlo.getType());
    assertEquals(MessageType.ACKNOWLEDGE, ack.getType());
    assertEquals(MessageType.NO_ACKNOWLEDGE, nak.getType());
    assertEquals(MessageType.QUIT, bye.getType());
    assertEquals(MessageType.USER_MESSAGE, umg.getType());
    assertEquals(MessageType.LOGIN, lgn.getType());
    assertEquals(MessageType.REGISTER, reg.getType());
    assertEquals(MessageType.DELETE_USER, del.getType());
    assertEquals(MessageType.UPDATE_USER, upt.getType());
    assertEquals(MessageType.FIND_USER, fnd.getType());
    assertEquals(MessageType.JOIN_GROUP, grp.getType());
    assertEquals(MessageType.CREATE_GROUP, crt.getType());
    assertEquals(MessageType.ADD_USER, add.getType());
    assertEquals(MessageType.UPDATE_GROUP, upg.getType());
    assertEquals(MessageType.DELETE_GROUP, deg.getType());
    assertEquals(MessageType.REMOVE_USER, rmu.getType());
    assertEquals(MessageType.LEAVE_GROUP, leg.getType());
    assertEquals(MessageType.LEAVE_GROUP, noReceiver.getType());
    assertEquals(MessageType.UPDATE_GROUP, noId.getType());
    assertEquals(MessageType.ACKNOWLEDGE, makeAck.getType());
    assertEquals(MessageType.NO_ACKNOWLEDGE, makeNoAck.getType());
    assertEquals(MessageType.GROUP_MESSAGE, gmg.getType());
    assertEquals(MessageType.TRACK_USER, trackUser.getType());
    assertEquals(MessageType.TRACK_GROUP, trackGroup.getType());
    assertEquals(MessageType.START_LOGGING, startLogging.getType());
    assertEquals(MessageType.STOP_LOGGING, stopLogging.getType());
  }

  /**
   * Test getContent.
   */
  @Test
  void testGetContent() {
    assertEquals("Hey music file!", hlo.getContent());
    assertEquals("Hey image file!", ack.getContent());
    assertEquals("Hey another image file!", nak.getContent());
    assertEquals("Hey music file!", bye.getContent());
    assertEquals("Hey image file!", umg.getContent());
    assertEquals("Hey another image file!", lgn.getContent());
    assertEquals("Hey music file!", reg.getContent());
    assertEquals("Hey image file!", del.getContent());
    assertEquals("Hey another image file!", upt.getContent());
    assertEquals("Hey music file!", fnd.getContent());
    assertEquals("Hey image file!", grp.getContent());
    assertEquals("Hey another image file!", crt.getContent());
    assertEquals("Hey music file!", add.getContent());
    assertEquals("Hey image file!", upg.getContent());
    assertEquals("Hey another image file!", deg.getContent());
    assertEquals("Hey music file!", rmu.getContent());
    assertEquals("Hey image file!", leg.getContent());
    assertEquals("Hey image file!", gmg.getContent());
    assertEquals("Matt", makeAck.getContent());
    assertEquals(null, makeNoAck.getContent());
  }

  /**
   * Test getTime.
   */
  @Test
  void testGetTime() {
    assertEquals("1542863031199", hlo.getTime());
    assertEquals("1542863031199", ack.getTime());
    assertEquals("1542863031199", nak.getTime());
    assertEquals("1542863031199", bye.getTime());
    assertEquals("1542863031199", umg.getTime());
    assertEquals("1542863031199", lgn.getTime());
    assertEquals("1542863031199", reg.getTime());
    assertEquals("1542863031199", del.getTime());
    assertEquals("1542863031199", upt.getTime());
    assertEquals("1542863031199", fnd.getTime());
    assertEquals("1542863031199", grp.getTime());
    assertEquals("1542863031199", crt.getTime());
    assertEquals("1542863031199", add.getTime());
    assertEquals("1542863031199", upg.getTime());
    assertEquals("1542863031199", deg.getTime());
    assertEquals("1542863031199", rmu.getTime());
    assertEquals("1542863031199", leg.getTime());
    assertEquals("1542863031199", gmg.getTime());
    assertEquals(null, makeAck.getTime());
    assertEquals(null, makeNoAck.getTime());
  }

  /**
   * Test setTime.
   */
  @Test
  void testSetTime() {
    assertEquals("1542863031199", noReceiver.getTime());
    assertEquals(null, noTime.getTime());
    noReceiver.setTime("1542863031200");
    noTime.setTime("1542863031199");
    assertEquals("1542863031200", noReceiver.getTime());
    assertEquals("1542863031199", noTime.getTime());
  }

  /**
   * Test setFlagged.
   */
  @Test
  void testFlags() {
	  trackUser.setFlagged(true);
	  assertTrue(trackUser.isFlagged());
  }

  /**
   * Test toString.
   */
  @Test
  void testToString() {
    assertEquals("HLO 4 Matt 7 team101 15 Hey music file!", hlo.toString());
    assertEquals("ACK 6 Meghna 7 team102 15 Hey image file!", ack.toString());
    assertEquals("NAK 2 Ak 7 team101 23 Hey another image file!", nak.toString());
    assertEquals("BYE 4 Matt 7 team102 15 Hey music file!", bye.toString());
    assertEquals("UMG 6 Meghna 7 team101 15 Hey image file!", umg.toString());
    assertEquals("LGN 2 Ak 7 team102 23 Hey another image file!", lgn.toString());
    assertEquals("REG 4 Matt 7 team101 15 Hey music file!", reg.toString());
    assertEquals("DEL 6 Meghna 7 team102 15 Hey image file!", del.toString());
    assertEquals("UPT 2 Ak 7 team101 23 Hey another image file!", upt.toString());
    assertEquals("FND 4 Matt 7 team102 15 Hey music file!", fnd.toString());
    assertEquals("GRP 6 Meghna 7 team101 15 Hey image file!", grp.toString());
    assertEquals("CRT 2 Ak 7 team102 23 Hey another image file!", crt.toString());
    assertEquals("ADD 4 Matt 7 team101 15 Hey music file!", add.toString());
    assertEquals("UPG 6 Meghna 7 team102 15 Hey image file!", upg.toString());
    assertEquals("DEG 2 Ak 7 team101 23 Hey another image file!", deg.toString());
    assertEquals("RMU 4 Matt 7 team102 15 Hey music file!", rmu.toString());
    assertEquals("LEG 6 Meghna 7 team101 15 Hey image file!", leg.toString());
    assertEquals("GMG 6 Meghna 7 team101 15 Hey image file!", gmg.toString());
    assertEquals("ACK 2 -- 2 -- 4 Matt", makeAck.toString());
    assertEquals("NAK 2 -- 2 -- 2 --", makeNoAck.toString());

  }

  /**
   * Test hashCode and equals method.
   */
  @Test
  void testHashCodeAndEquals() {
    Message messageBCT0 = new Message(10, "Matt", "Jason",
            Message.MessageType.getMessageType("BCT"), "Hi", null);
    Message messageBCT1 = new Message(10, "Jason", "Matt",
            Message.MessageType.getMessageType("UPG"), "Hi", null);
    Message messageBCT2 = new Message("Jason", "Matt", MessageType.UPDATE_GROUP,
            "Hi", null);
    assertTrue(messageBCT0.equals(messageBCT1) && messageBCT1.equals(messageBCT0));
    assertTrue(messageBCT0.equals(messageBCT0));
    assertFalse(messageBCT0.equals(messageBCT2));
    assertFalse(messageBCT2.equals(messageBCT0));
    assertFalse(messageBCT0.equals("Hi:"));
    assertFalse(messageBCT0.equals("Hi:"));
    assertFalse(messageBCT0.equals(messageBCT2));
    assertFalse(messageBCT0.equals(null));
    assertTrue(messageBCT0.hashCode() == messageBCT1.hashCode());
  }
}