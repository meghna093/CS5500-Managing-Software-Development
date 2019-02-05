package edu.northeastern.ccs.cs5500.homework3;

import junit.framework.TestCase;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases to test the functionality of hand class. 
 * @author meghna
 *
 */
public class TestNewHand {
	Cards card1 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	Cards card2 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	Cards card3 = new Cards(Ranks.KING, Suits.SPADES);
	Cards card4 = new Cards(Ranks.JACK, Suits.HEARTS);
	Cards card5 = new Cards(Ranks.FIVE, Suits.CLUBS);
	Cards card6 = new Cards(Ranks.TWO  , Suits.SPADES);

	
	/**
	 * Checks whether the mentioned card is present in the hand. First a new hand is
	 * created and cards are added into that hand. Then it is checked whether a 
	 * specific card is present in the hand. Returns true if the card is present.
	 */
	@Test
	public void testHandhasCard(){
		Hands h1 = new Hands();
		h1.accept(card1);
		h1.accept(card2);
		h1.accept(card3);
		h1.accept(card4);
		
		assertEquals(true, h1.hasCard(card1));
		assertEquals(false, h1.hasCard(card5));
	}
	
	
	/**
	 * Tests the number of times a particular card is present in a hand. A new
	 * hand is created and cards are added into that hand. Then we test whether
	 * a particular card is present for particular times in the hand.
	 */
	@Test
	public void testHandCardCount(){
		Hands h1 = new Hands();
		h1.accept(card1);
		h1.accept(card1);
		h1.accept(card3);
		h1.accept(card4);
		assertEquals(2, h1.occurrencesInHand(card1));
	}
	
	
	/**
	 * Tests the number of times a particular rank is present in a hand. A new
	 * hand is created and cards are added into that hand. Then we test whether
	 * a particular rank is present for particular times in the hand.
	 */
	@Test
	public void testHandRankCount(){
		Hands h1 = new Hands();
		h1.accept(card1);
		h1.accept(card1);
		h1.accept(card3);
		h1.accept(card4);
		
		assertEquals(2, h1.occurencesInHand(card1.getRank()));
	}
}

