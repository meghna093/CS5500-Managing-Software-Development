package problem1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases to test the functionality of deck class. 
 * @author meghna
 *
 */
public class TestDeck {
	Cards c1 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	
	/**
	 * Testing of creation of deck. A standard deck of 52 cards is
	 * used for testing purpose, this deck is created in a standard sequence.
	 * A standard deck is created with the following ascending order of suit 
	 * values: Spades, Hearts, Clubs, Diamonds. So the first set of suit values 
	 * in a deck will be Diamonds followed by Clubs then Hearts and then Spades.
	 * In the below test I have created a new set of deck and tested whether 
	 * the deck is created as I expected. The test below confirms that the deck 
	 * is created in the expected order of suit values. First 13 cards from the
	 * deck are pulled and checked for its suit value and so on. Once all the 
	 * 52 cards are pulled out, the deck is empty.
	 */
	@Test
	public void testDeck() {
		Decks deck1 = new Decks();
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		assertEquals(true, deck1.emptyDeck());
	}

	/**
	 * Testing of deck size. A standard deck of 52 cards is used for testing 
	 * purpose. A deck is created and checked if it has 52 cards.
	 */
	@Test
	public void testDeckSize(){
		Decks deck1 = new Decks();
		assertEquals(52, deck1.deckSize());
	}

	/**
	 * Testing of shuffling of deck. A standard deck of 52 cards is used for 
	 * testing purpose. Two new decks, deck1 and deck2 are created. First it 
	 * is tested whether the two decks are equal. Then deck1 is shuffled, again 
	 * deck1 and deck2 are tested if they are equal which they are not.
	 */
	@Test
	public void testDeckShuffle(){
		Decks deck1 = new Decks();
		Decks deck2 = new Decks();
		assertEquals(deck1.toString(), deck2.toString());
		deck1.shuffle();
		assertFalse(deck1.equals(deck2));
	}

	/**
	 * Testing of sorting a deck by its rank. A standard deck of 52 cards is used for 
	 * testing purpose. A standard deck is created and sorted by its rank. The default 
	 * order of rank which I have set is: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace.
	 * According to the default order when the first 4 cards are pulled from a deck which is 
	 * sorted by its rank, its rank value will be Ace followed by King and so on. The same is 
	 * tested below. Once all the 52 cards are pulled out, the deck is empty.
	 */
	@Test
	public void testDeckSortRank(){
		Decks deck1 = new Decks();
		deck1.sort("rank");
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Ace", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("King", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Queen", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Jack", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Ten", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Nine", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Eight", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Seven", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Six", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Five", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Four", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals( "Three", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Two", c.getRank().toString());
		}
		assertEquals(true, deck1.emptyDeck());
	}

	/**
	 * Testing of sorting a deck by its suit. A standard deck of 52 cards is used for 
	 * testing purpose. A standard deck is created and sorted by its suit. The default 
	 * order of suit which I have set is: Spades, Hearts, Clubs, Diamonds. According to 
	 * the default order when the first 13 cards are pulled from a deck which is sorted 
	 * by its suit, its suit value will be Diamonds followed by Clubs and Hearts at Spades.
	 * The same is tested below. Once all the 52 cards are pulled out, the deck is empty.
	 */
	@Test
	public void testDeckSortSuit(){
		Decks deck1 = new Decks();
		deck1.sort("suit");
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		assertEquals(true, deck1.emptyDeck());
	}

	/**
	 * Testing of sorting a deck by suit first and then by rank. A standard deck of 52 
	 * cards is used for testing purpose. A standard deck is created and sorted by both
	 * below. When a deck that is sorted by suit and the by rank, the first four card will 
	 * have the same rank value and its respective suit values will be Diamonds, Clubs, Hearts 
	 * and Spades. The rank value is tested below. Once all the cards are pulled the deck
	 * is empty.
	 */
	@Test
	public void testDeckSortBothRank(){
		Decks deck1 = new Decks();
		deck1.sort("both");
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Ace", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("King", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Queen", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Jack", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Ten", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Nine", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Eight", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Seven", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Six", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Five", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Four", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals( "Three", c.getRank().toString());
		}
		for(int i=0;i<4;i++) {
			Card c = deck1.pullCard();
			assertEquals("Two", c.getRank().toString());
		}
		assertEquals(true, deck1.emptyDeck());
	}


	/**
	 * Testing of sorting a deck by suit first and then by rank. A standard deck of 52 
	 * cards is used for testing purpose. A standard deck is created and sorted by both
	 * below. When a deck that is sorted by suit and the by rank, the first four card 
	 * will have the same rank value and its respective suit values will be Diamonds, Clubs, 
	 * Hearts and Spades. The suit value is tested below.
	 */
	@Test
	public void testDeckSortBothSuit() {
		Decks deck1 = new Decks();
		deck1.sort("both");
		for(int j=13; j<0; j--) {
			for(int i=0;i<4;i++) {
				Card c1 = deck1.pullCard();
				assertEquals("Diamonds", c1.getSuit().toString());
				Card c2 = deck1.pullCard();
				assertEquals("Clubs", c2.getSuit().toString());
				Card c3 = deck1.pullCard();
				assertEquals("Hearts", c3.getSuit().toString());
				Card c4 = deck1.pullCard();
				assertEquals("Spades", c4.getSuit().toString());
			}
		}
	}


	/**
	 * Testing cut functionality of a deck. A standard deck of 52 cards is used for 
	 * testing purpose. When a deck is cut at a given point, those cards are removed from 
	 * the bottom of the deck and placed on the top of the deck. the same is tested 
	 * in the below test. Cut point is given as 13, so the last 13 cards are removed
	 * from the bottom of the deck and placed on the top. So the first 13 cards of the
	 * new deck will have Spades suit value followed by Diamonds and Clubs and Hearts.
	 */
	@Test
	public void testDeckCut(){
		Decks deck1 = new Decks();
		deck1.cut(13);
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Spades", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Diamonds", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Clubs", c.getSuit().toString());
		}
		for(int i=0;i<13;i++) {
			Card c = deck1.pullCard();
			assertEquals("Hearts", c.getSuit().toString());
		}
		assertEquals(true, deck1.emptyDeck());
	}


	/**
	 * Testing pullCard functionality on a deck of cards. A standard deck of 52 
	 * cards is used for testing purpose. This functionality pulls the top card 
	 * from the deck and removes it from the deck. The first card will be
	 * Ace of Diamonds, in the below test case we pull out the first card
	 * and check whether Ace of Diamonds is present in the deck, which returns
	 * false. Also the size of deck is reduced by one card.
	 */
	@Test
	public void testDeckPullCard(){
		Decks deck1 = new Decks();
		Cards card6 = deck1.pullCard();
		assertEquals(card6.toString(), c1.toString());
		assertEquals(false, deck1.hasCard(card6));
		assertEquals(51, deck1.deckSize());
	}


	/**
	 * Testing whether a deck is empty. A standard deck of 52 cards is used for 
	 * testing purpose. In the below test a new deck is created and resetDeck()
	 * function is called which removes all the card from the deck. Hence the 
	 * deck will be empty and returns true when tested for empty check.
	 */
	@Test
	public void testDeckEmpty(){
		Decks deck1 = new Decks();
		deck1.resetDeck();
		assertEquals(true, deck1.emptyDeck());
	}


	/**
	 * Testing whether a deck is not empty. A standard deck of 52 cards is used for 
	 * testing purpose. In the below test a new deck is created and the test returns
	 * false when tested for empty.
	 * 
	 */
	@Test
	public void testDeckNotEmpty(){
		Decks deck1 = new Decks();
		assertEquals(false, deck1.emptyDeck());
	}



}
