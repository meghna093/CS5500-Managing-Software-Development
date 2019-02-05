package edu.northeastern.ccs.cs5500.homework3;

import junit.framework.TestCase;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases to test the functionality of hand class. 
 * @author meghna
 *
 */
public class TestHand {
	Cards card1 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	Cards card2 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	Cards card3 = new Cards(Ranks.KING, Suits.SPADES);
	Cards card4 = new Cards(Ranks.JACK, Suits.HEARTS);
	Cards card5 = new Cards(Ranks.FIVE, Suits.CLUBS);
	Cards card6 = new Cards(Ranks.TWO  , Suits.SPADES);

	/**
	 * Testing of hand creation and it accepts a card properly. 
	 */
	@Test
	public void testHand(){
		Hands h1 = new Hands();
		h1.accept(card6);
		assertEquals("Spades", card6.getSuit().toString());
		assertEquals("Two", card6.getRank().toString());	
	}
	
	
	/**
	 * Testing pullCard functionality on a hand. Pull function pulls the top card
	 * from the deck. Once a card is pulled, the size of the hand is reduced by 1
	 * and the card is removed from hand. The same is tested below.
	 */
	@Test
	public void testHandPullCard() {
		Hands h1 = new Hands();
		h1.accept(card6);
		assertEquals(1, h1.handSize());
		h1.pullCard();
		assertEquals(0, h1.handSize());
		assertEquals(false, h1.hasCard(card6));
	}

	
	/**
	 * Testing sorting functionality on a hand. The below test is to check whether
	 * all the cards in hand are sorted based on the card rank values. Initially two 
	 * hands are created, one hand has cards sorted by rank values, the other hand has
	 * cards in a random manner. This other card is then sorted by rank values and 
	 * compared to check if both hands are equal. The same is tested below.
	 */
	@Test
	public void testHandSortRank(){
		Hands h1 = new Hands();
		Hands checkRank = new Hands();
		h1.accept(card1);
		h1.accept(card2);
		h1.accept(card3);
		h1.accept(card4);
		h1.accept(card5);
		
		checkRank.accept(card5);
		checkRank.accept(card4);
		checkRank.accept(card3);
		checkRank.accept(card2);
		checkRank.accept(card1);
		h1.sort("rank");
		assertEquals(h1.toString(), checkRank.toString());
	}

	
	/**
	 * Testing sorting functionality on a hand. The below test is to check whether
	 * all the cards in hand are sorted based on the card suit values. Initially two 
	 * hands are created, one hand has cards sorted by suit values, the other hand has
	 * cards in a random manner. This other card is then sorted by suit values and 
	 * compared to check if both hands are equal. The same is tested below.
	 */
	@Test
	public void testHandSortSuit(){
		Hands h1 = new Hands();
		Hands checkSuit = new Hands();
		h1.accept(card1);
		h1.accept(card2);
		h1.accept(card3);
		h1.accept(card4);
		h1.accept(card5);
		
		checkSuit.accept(card3);
		checkSuit.accept(card4);
		checkSuit.accept(card5);
		checkSuit.accept(card2);
		checkSuit.accept(card1);
		h1.sort("suit");
		assertEquals(h1.toString(), checkSuit.toString());
	}

	
	/**
	 * Testing sorting functionality on a hand. The below test is to check whether
	 * all the cards in hand are sorted based on the card suit values first and then 
	 * by rank values. Initially two hands are created, one hand has cards sorted by 
	 * suit values first and then by rank values, the other hand has cards in a random 
	 * manner. This other card is then sorted by suit and rank values and compared 
	 * to check if both hands are equal. The same is tested below.
	 */
	@Test
	public void testHandSortBoth(){
		Hands h1 = new Hands();
		Hands checkBoth = new Hands();
		h1.accept(card1);
		h1.accept(card2);
		h1.accept(card3);
		h1.accept(card4);
		h1.accept(card5);
		
		checkBoth.accept(card5);
		checkBoth.accept(card4);
		checkBoth.accept(card3);
		checkBoth.accept(card2);
		checkBoth.accept(card1);
		h1.sort("both");
		assertEquals(h1.toString(), checkBoth.toString());
	}

	/**
	 * Testing shuffle functionality on a hand. Initially 2 different hands
	 * are created, both with same cards in the same order and tested for 
	 * equality. Then one hand is shuffled and compared with the other hand, 
	 * this test returns false indicating that the two hands are different now.
	 */
	@Test
	public void testHandShuffle(){
		Hands h1 = new Hands();
		Hands checkShuffle = new Hands();
		assertEquals(h1.toString(), checkShuffle.toString());
		h1.accept(card1);
		h1.accept(card2);
		h1.accept(card3);
		h1.accept(card4);
		h1.accept(card5);
		
		checkShuffle.accept(card1);
		checkShuffle.accept(card2);
		checkShuffle.accept(card3);
		checkShuffle.accept(card4);
		checkShuffle.accept(card5);
		h1.shuffle();
		assertFalse(h1.equals(checkShuffle));
	}
}

