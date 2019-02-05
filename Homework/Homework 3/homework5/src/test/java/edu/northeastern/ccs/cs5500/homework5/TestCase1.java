package edu.northeastern.ccs.cs5500.homework5;

import org.junit.Test;
import junit.framework.TestCase;
/**
 * Test cases for blackjack game
 * @author meghna
 *
 */
public class TestCase1 extends TestCase {
	Card c1 = new Card(Suit.DIAMONDS, Rank.ACE);
	Card c2 = new Card(Suit.DIAMONDS, Rank.TWO);
	Card c3 = new Card(Suit.CLUBS, Rank.KING);
	Card c4 = new Card(Suit.HEARTS, Rank.KING);
	Card c5 = new Card(Suit.DIAMONDS, Rank.ACE);
	Card c6 = new Card(Suit.SPADES, Rank.KING);
	Card c7 = new Card(Suit.SPADES, Rank.FOUR);
	Card[] cardList = new Card[] {c1,c2,c3,c4,c5,c6,c7};
	Rank r1 = new Rank("Ace",0);
	Suit s1 = new Suit("Spades");


	/**
	 * Testing the creation of rank pips.
	 * Testing if the rank is created with proper pip value.
	 */
	@Test
	public void testRankPips() {
		assertEquals(0, r1.getRank());
	}


	/**
	 * Testing the creation of suit name.
	 * Testing if the suit is created with proper name value.
	 */
	@Test
	public void testSuitName() {
		assertEquals("Spades", s1.getName().toString());
	}


	/**
	 * Testing the creation of individual card.
	 * Testing if the card is created with proper suit value.
	 */
	@Test
	public void testCardSuit() {
		assertEquals("Diamonds", c1.getSuit().toString());
	}


	/**
	 * Testing the creation of individual card.
	 * Testing if the card is created with proper rank value.
	 */
	@Test
	public void testCardRank() {
		assertEquals("Two", c2.getRank().toString());
	}



	/**
	 * Testing if a deck gets populated with the right cards. 
	 * First an empty deck is created and populated with a set of 
	 * cards. Then we check whether the deck contains the right card. 
	 */
	@Test
	public void testDeckPopulate() {
		CardDecks cd = new CardDecks();
		Card[] crd = new Card[] {c1,c2};
		cd.populateDeck(crd);
		assertEquals(true, cd.hasCard(c1));
		assertEquals(true, cd.hasCard(c2));
		assertEquals(false, cd.hasCard(c3));
	}


	/**
	 * Testing if a deck gets populated with the right number of cards. 
	 * First an empty deck is created and populated with a set of cards. Then we check 
	 * whether the deck contains the right number of cards. 
	 */
	@Test
	public void testDeckPopulateSize() {
		CardDecks cd = new CardDecks();
		Card[] crd = new Card[] {c1,c2};
		cd.populateDeck(crd);
		assertEquals(2,cd.deckSize());

	}


	/**
	 * Testing if a card is hidden. 
	 * Since this is a game of blackjack, one of the dealer card will be hidden 
	 * from the players. The same is tested below. First an empty deck is created 
	 * and populated with a card, then we check if this card is hidden.
	 */
	@Test
	public void testCardHidden() {
		CardDecks cd = new CardDecks();
		Card[] crd = new Card[] {c1,c2};
		cd.populateDeck(crd);
		Card c1 = cd.normalDeal();
		assertEquals(true, c1.cardUp());
		c1.setPos(false);
		assertEquals(false, c1.cardUp());
	}


	/**
	 * Testing if a card is not hidden. 
	 * Since this is a game of blackjack, except for one card that belongs to 
	 * the dealer all the other cards will be up and show its value. The same 
	 * is tested below. First an empty deck is created and populated with a card, 
	 * then we check if this card is not hidden.
	 */
	@Test
	public void testCardShown() {
		CardDecks cd = new CardDecks();
		Card[] crd = new Card[] {c1,c2};
		cd.populateDeck(crd);
		Card c1 = cd.normalDeal();
		assertEquals(true, c1.cardUp());
	}


	/**
	 * Testing creation of deck.
	 * Testing whether a deck is created with 6 standard decks. 6 standard decks contains
	 * 312 cards in total. The same is tested below.
	 */
	@Test
	public void testDeckSize() {
		CardDecks cards = new CardDecks();
		Deck deck = new Deck();
		deck.addDeck(cards);
		assertEquals(312, cards.deckSize());
	}


	/**
	 * Testing if only the cards from the deck is dealt. 
	 * First a deck is created and populated with a set of 
	 * cards. Then we check whether only these cards are dealt and
	 * not other cards which are not present in the deck.  
	 */
	@Test
	public void testDeckNormalDeal() {
		CardDecks cd = new CardDecks();
		Card[] crd = new Card[] {c1,c2};
		cd.populateDeck(crd);
		Card c1 = cd.normalDeal();
		assertEquals("Diamonds", c1.getSuit().toString());
	}


	/**
	 * Testing the win status of a hand. 
	 * First we create two hands, one for dealer and one for player. Then 
	 * 2 cards are added to each hand, the hand with highest value of cards
	 * will be the winner. The same is tested below.
	 * 
	 */
	@Test
	public void testDealerWin() {
		Hand dealerHand = new Hand();
		Hand playerHand = new Hand();

		dealerHand.addCard(c1);
		dealerHand.addCard(c3);
		

		playerHand.addCard(c2);
		playerHand.addCard(c7);

		if(dealerHand.greater(playerHand))
			assertEquals(true,dealerHand.handWin());
		else
			assertEquals(false,dealerHand.handWin());
	}


	/**
	 * Testing busted status of a hand.
	 * First we create a hand and add cards such that the total value of 
	 * these cards is greater than 21, then we check if the hand is busted.
	 * The same is tested below.
	 */
	@Test
	public void testPlayerBusted() {
		Hand playerHand = new Hand();
		playerHand.addCard(c3);
		playerHand.addCard(c4);
		playerHand.addCard(c6);
		assertEquals(true,playerHand.busted());
	}


	/**
	 * Testing tie status of two hands.
	 * First two hands are created and each hand is populated with the same
	 * card. Hence their total value will also be same and then we check if
	 * the hands are tied.
	 * The same is tested below.
	 */
	@Test
	public void testStandOff() {
		Hand playerHand = new Hand();
		Hand dealerHand = new Hand();
		playerHand.addCard(c1);
		playerHand.addCard(c4);
		dealerHand.addCard(c1);
		dealerHand.addCard(c4);
		assertEquals(true, playerHand.isEqual(dealerHand));
	}


	/**
	 * Testing the soft hand and hard hand functionality.
	 * First we create a hand and populate it with 2 cards, among these
	 * two cards one card is an ace card. First the total value of the
	 * hand is 13. Then the hand is hit with another card, now the 
	 * total value of hand will be 13 instead of 23 to prevent the value
	 * of hand from exceeding 21.
	 */
	@Test
	public void testAceValue() {
		Hand playerHand = new Hand();
		playerHand.addCard(c1);
		playerHand.addCard(c2);
		assertEquals(13, playerHand.finalValue());
		playerHand.addCard(c3);
		assertEquals(13, playerHand.finalValue());
	}


	/**
	 * Testing the hit functionality on a hand.
	 * A dealer can hit a card to a player. So we create a dealer 
	 * and a player. The dealer is provided with a deck of cards. 
	 * Initially the player's hand is empty, then dealer hits the player
	 * with a card. Now the player's hand consists of one card.
	 * The same is tested below.
	 * 
	 */
	@Test
	public void testHitCard() {
		BetFactor playerMoney = new BetFactor(5);
		Hand playerHand = new Hand();
		Player player = new PlayGame("Player", playerHand, playerMoney);
		Hand dHand = new Hand();
		CardDecks cards = new CardDecks();
		MainDealer bJackDealer = new MainDealer("Dealer", dHand, cards);
		Deck deck = new Deck();
		deck.addDeck(cards);
		
		assertEquals(0,playerHand.handSize());
		
		bJackDealer.hit(player);
		
		assertEquals(1,playerHand.handSize());
	}


	/**
	 * Testing split functionality on a hand.
	 * First we create a hand and add two same cards to that hand. 
	 * Then we create a dealer and a player, the hand created previously 
	 * is assigned to the player hand. 
	 * First we check if the player hand has the same cards and player hand
	 * has two cards, then we call a split on this hand. Now the number of 
	 * cards in player hand is reduced to one and one hand is split into
	 * two hands.
	 * The same is tested below.
	 * 
	 */
	@Test
	public void testSplitCardTrue() {
		Hand h = new Hand();
		Hand dHand = new Hand();
		h.addCard(c1);
		h.addCard(c5);
		
		CardDecks cards = new CardDecks();
		MainDealer dealer = new MainDealer("Dealer", dHand, cards);
		BetFactor playerMoney = new BetFactor(5);
		Hand pHand = h;
		Player player = new PlayGame("Player", pHand, playerMoney);
		
		pHand = h;
		
		assertEquals(true, pHand.sameCard());
		assertEquals(2, player.getHandSize());
		dealer.playerSplit(player);
		assertEquals(1, player.getHandSize());	
	}
	
	
	/**
	 * Testing split functionality on a hand.
	 * First we create a hand and add two same cards to that hand. 
	 * Then we create a dealer and a player, the hand created previously 
	 * is assigned to the player hand. 
	 * First we check if the player hand has the same cards and player hand
	 * has two cards, then we call a split on this hand. Now one hand is split into
	 * two hands.
	 * The same is tested below.
	 * 
	 */
	@Test
	public void testSplitCardHandCount() {
		Hand h = new Hand();
		Hand dHand = new Hand();
		h.addCard(c1);
		h.addCard(c5);
		
		CardDecks cards = new CardDecks();
		MainDealer dealer = new MainDealer("Dealer", dHand, cards);
		BetFactor playerMoney = new BetFactor(5);
		Hand pHand = h;
		Player player = new PlayGame("Player", pHand, playerMoney);
		
		pHand = h;
		
		assertEquals(true, pHand.sameCard());
		assertEquals(2, player.getHandSize());
		
		assertEquals(2, dealer.split(player).size());	
	}
	


	/**
	 * Testing split functionality on hand.
	 * A hand can be split only when there are 2 same cards, if the cards
	 * are different or if there is only one card, split functionality 
	 * will return false. 
	 * The same is tested below.
	 */
	@Test
	public void testSplitOneCard() {
		Hand h = new Hand();
		Hand dHand = new Hand();
		h.addCard(c1);
		CardDecks cards = new CardDecks();
		MainDealer bJackDealer = new MainDealer("Dealer", dHand, cards);
		BetFactor playerMoney = new BetFactor(5);
		Hand pHand = h;
		Player player = new PlayGame("Player", pHand, playerMoney);
		pHand = h;
		assertEquals(1, player.getHandSize());
		assertEquals(false, bJackDealer.playerSplit(player));
	}


	/**
	 * Testing bet functionality.
	 * The player starts with $5 in bank. Then a bet of $1 will be placed,
	 * if the player loses then the total in bank will be reduced by $1. So
	 * the amount in bank will be $4 after the player loses.
	 * The same is tested below.
	 */
	@Test
	public void testBetLose() {
		BetFactor playerMoney = new BetFactor(5);
		assertEquals("$5",playerMoney.toString());
		playerMoney.callBet(1);
		playerMoney.ifLose();
		assertEquals("$4",playerMoney.toString());
	}


	/**
	 * Testing bet functionality.
	 * The player starts with $5 in bank. Then a bet of $1 will be placed,
	 * if the player wins then the total in bank will be increased by $1. So
	 * the amount in bank will be $6 after the player wins.
	 * The same is tested below.
	 */
	@Test
	public void testBetWin() {
		BetFactor playerMoney = new BetFactor(5);
		assertEquals("$5",playerMoney.toString());
		playerMoney.callBet(1);
		playerMoney.ifWin();
		assertEquals("$6",playerMoney.toString());
	}


	/**
	 * Testing bet functionality.
	 * The player starts with $5 in bank. Then a bet of $1 will be placed,
	 * if the player neither wins nor loses that is if the game is tied
	 * then the total in bank remains same. So the amount in bank will be $5. 
	 * The same is tested below.
	 */
	@Test
	public void testBetStand() {
		BetFactor playerMoney = new BetFactor(5);
		assertEquals("$5",playerMoney.toString());
		playerMoney.callBet(1);
		playerMoney.ifStand();
		assertEquals("$5",playerMoney.toString());
	}


	/**
	 * Testing comparison of hands to decide which hand wins and which
	 * hand loses or if the hands are tied. 
	 * Two hands are created and added different cards and tested which hand
	 * has the highest value.
	 */
	@Test
	public void testHandValue() {
		Hand h1 = new Hand();
		Hand h2 = new Hand();
		h1.addCard(c1);
		h2.addCard(c2);
		assertEquals(true,h1.greater(h2));
	}


	/**
	 * Testing the hidden functionality of a card.
	 * One of the card that belongs to the dealer should be hidden and should
	 * be shown only at the end, the hidden functionality of the card is tested 
	 * below.
	 * A dealer is created and dealt a card, but when the card is displayed
	 * it's value is hidden. 
	 */
	@Test
	public void testHiddenCard() {
		Hand dHand = new Hand();
		CardDecks cards = new CardDecks();
		MainDealer bJackDealer = new MainDealer("Dealer", dHand, cards);
		bJackDealer.addCard(c1);
		assertEquals(" ???",bJackDealer.getHand().toString());		
	}
}


