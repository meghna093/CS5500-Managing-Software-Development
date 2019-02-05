package problem1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases to test the functionality of game class. 
 * Also test cases to check different types of decks creation.
 * @author meghna
 *
 */
public class TestGame {

	/**
	 * Testing of creation of a standard deck. A standard deck has 52 
	 * cards. The below test checks whether the deck has 52 cards.
	 */
	@Test
	public void testGameDeck(){
		Games g = new Games();
		g.createDeck("Standard");
		assertEquals(52, g.deckSize());
	}


	/**
	 * Testing of creation of standard deck. A standard deck of 52 cards is
	 * used for testing purpose, this deck is created in a standard sequence.
	 * A standard deck is created with the following ascending order of suit 
	 * values: Spades, Hearts, Clubs, Diamonds. So the first set of suit values 
	 * in a deck will be Diamonds followed by Clubs then Hearts and then Spades.
	 * The test below confirms that the deck is created in the expected order of 
	 * suit values. First 13 cards from the deck are pulled and checked for its 
	 * suit value and so on.
	 * Note: While creating a deck, it is created in the default sequence.
	 */
	@Test
	public void testStandardDeck() {
		Games g = new Games();
		g.createDeck("Standard");
		for(int i=0;i<13;i++) {
			Card c = g.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = g.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = g.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = g.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		assertEquals(true, g.emptyDeck());
	}


	/**
	 * Testing of creation of a euchre game. A euchre game deck has 24 
	 * cards. The below test checks whether the deck has 24 cards.
	 */
	@Test
	public void testGameCreateEuchre(){
		Games g = new Games();
		g.createDeck("Euchre");
		assertEquals(24, g.deckSize());
	}


	/**
	 * Testing of creation of a euchre game. A euchre game has 24 cards. It has the 
	 * following cards: 9, 10, Jack, Queen, King and Ace. So The first 6 cards from
	 * the deck has Diamonds suit value followed by Clubs and Hearts and Spades. The
	 * same is tested below. Once all the cards are pulled out the deck is empty.
	 * Note: While creating a deck, it is created in the default sequence.
	 */
	@Test
	public void testEuchreDeck() {
		Games g = new Games();
		g.createDeck("Euchre");
		for(int i=0;i<6;i++) {
			Card c = g.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<6;i++) {
			Card c = g.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<6;i++) {
			Card c = g.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		for(int i=0;i<6;i++) {
			Card c = g.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		assertEquals(true, g.emptyDeck());
	}


	/**
	 * Testing of creation of a pinochle game. A pinochle game deck has 48
	 * cards. The below test checks whether the deck has 48 cards.
	 */
	@Test
	public void testGameCreatePinochle(){
		Games g = new Games();
		g.createDeck("Pinochle");
		assertEquals(48, g.deckSize());
	}


	/**
	 * Testing of creation of a pinochle game. A pinochle game has 48 cards. It has the 
	 * following cards: 9, 10, Jack, Queen, King and Ace, but two of each. So The first 12
	 * cards from the deck has Diamonds suit value followed by Clubs and Hearts and Spades. 
	 * The same is tested below. Once all the cards are pulled out the deck is empty.
	 * Note: While creating a deck, it is created in the default sequence.
	 */
	@Test
	public void testPinochleDeck() {
		Games g = new Games();
		g.createDeck("Pinochle");
		for(int i=0;i<12;i++) {
			Card c = g.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<12;i++) {
			Card c = g.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<12;i++) {
			Card c = g.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		for(int i=0;i<12;i++) {
			Card c = g.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		assertEquals(true, g.emptyDeck());
	}


	/**
	 * Testing of creation of a vegas blackjack game. A vegas blackjack game 
	 * deck has 104 cards. The below test checks whether the deck has 104 cards.
	 */
	@Test
	public void testGameCreateVegas(){
		Games g = new Games();
		g.createDeck("Vegas");
		assertEquals(104, g.deckSize());
	}


	/**
	 * Testing of creation of a vegas blackjack game. A vegas blackjack game has 104 cards. 
	 * It is a standard deck but has 2 standard decks at minimum and can have upto 8 standard
	 * decks. So The first 12 cards from the deck has Diamonds suit value followed by Clubs and 
	 * Hearts and Spades. Also there are two decks, the same loop can be ran twice. The same 
	 * is tested below. 
	 * Note: While creating a deck, it is created in the default sequence.
	 */
	@Test
	public void testVegasDeck() {
		Games g = new Games();
		g.createDeck("Vegas");
		for(int j=2; j<0; j--) {
			for(int i=0;i<12;i++) {
				Card c = g.pullCard();
				assertEquals("Diamonds", c.getSuit().toString());
			}
			for(int i=0;i<12;i++) {
				Card c = g.pullCard();
				assertEquals("Clubs", c.getSuit().toString());
			}
			for(int i=0;i<12;i++) {
				Card c = g.pullCard();
				assertEquals("Hearts", c.getSuit().toString());
			}
			for(int i=0;i<12;i++) {
				Card c = g.pullCard();
				assertEquals("Spades", c.getSuit().toString());
			}
		}
	}

	
	/**
	 * Testing of creation of a standard deck along with the number of decks that has to be 
	 * created. A standard deck has 52 cards.The below test creates 2 standard decks hence 
	 * it checks whether the deck has 104 cards.
	 */
	@Test
	public void testGameCreateStandard(){
		Games g = new Games();
		g.createDeck("Standard",2);
		assertEquals(104, g.deckSize());
	}

	
	/**
	 * Testing of creation of a euchre game deck along with the number of decks that has to be 
	 * created. A euchre game deck has 24 cards.The below test creates 2 euchre game decks hence 
	 * it checks whether the deck has 48 cards.
	 */
	@Test
	public void testGameCreateEuchreInt(){
		Games g = new Games();
		g.createDeck("Euchre",2);
		assertEquals(48, g.deckSize());
	}

	
	/**
	 * Testing of creation of a pinochle game deck along with the number of decks that has to be 
	 * created. A pinochle game deck has 48 cards.The below test creates 2 pinochle game decks hence 
	 * it checks whether the deck has 96 cards.
	 */
	@Test
	public void testGameCreatePinochleInt(){
		Games g = new Games();
		g.createDeck("Pinochle",2);
		assertEquals(96, g.deckSize());
	}

	
	/**
	 * Testing of creation of a vegas blackjack deck along with the number of decks that has to be 
	 * created. A vegas blackjack deck has 104 cards.The below test creates 3 vegas blackjack decks 
	 * hence it checks whether the deck has 156 cards.
	 */
	@Test
	public void testGameCreateVegasInt(){
		Games g = new Games();
		g.createDeck("Vegas",3);
		assertEquals(156, g.deckSize());
	}

	
	/**
	 * Testing deal functionality on a standard deck. Once a card is dealt from the deck
	 * it has one card less. The same is tested below.
	 */
	@Test
	public void testGameCreateStandardInt(){
		Games g = new Games();
		g.createDeck("Standard");
		g.deal();
		assertEquals(51, g.deckSize());
	}

	
	/**
	 * Testing pullCard functionality on a standard deck. Once a card is pulled out from 
	 * a standard deck, the size is reduced to 51 and the card is removed from the deck.
	 * The same is tested below.
	 */
	@Test
	public void testGamePullCardS(){
		Games g = new Games();
		g.createDeck("Standard");
		Cards testCard = g.pullCard();
		assertEquals(51, g.deckSize());
		assertEquals(false, g.hasCard(testCard));
	}

	
	/**
	 * Testing pullCard functionality on a euchre game deck. Once a card is pulled out from 
	 * a euchre game deck, the size is reduced to 23 and the card is removed from the deck.
	 * The same is tested below.
	 */
	@Test
	public void testGamePullCardE(){
		Games g = new Games();
		g.createDeck("Euchre");
		Cards testCard = g.pullCard();
		assertEquals(23, g.deckSize());
		assertEquals(false, g.hasCard(testCard));
	}

	
	/**
	 * Testing pullCard functionality on a pinochle game deck. Once a card is pulled out from 
	 * a pinochle game deck, the size is reduced to 47 and the card is removed from the deck.
	 * The same is tested below.
	 */
	@Test
	public void testGamePullCardP(){
		Games g = new Games();
		g.createDeck("Pinochle");
		Cards testCard1 = new Cards(Ranks.ACE, Suits.DIAMONDS);
		g.pullCard();
		assertEquals(47, g.deckSize());
		assertEquals(g.pullCard().toString(), testCard1.toString());
	}

	
	/**
	 * Testing pullCard functionality on a vegas blackjack deck. Once a card is pulled out from 
	 * a vegas blackjack deck, the size is reduced to 103 and the card is removed from the deck.
	 * The same is tested below.
	 */
	@Test
	public void testGamePullCardV(){
		Games g = new Games();
		g.createDeck("Vegas");
		g.pullCard();
		Cards testCard1 = new Cards(Ranks.KING, Suits.DIAMONDS);
		assertEquals(103, g.deckSize());
		assertEquals(g.pullCard().toString(), testCard1.toString());
	}

}
