package edu.northeastern.ccs.cs5500.homework4;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing of proper creation of individual cards.
 * @author meghna
 *
 */
public class TestCard {

	Cards c1 = new Cards(Ranks.ACE, Suits.DIAMONDS);
	Cards c2 = new Cards(Ranks.FIVE, Suits.CLUBS);
	Ranks r1 = new Ranks("Ace",0);
	Suits s1 = new Suits("Spades", 's');

	/**
	 * Testing the creation of rank name.
	 * Testing if the rank is created with proper name value.
	 */
	@Test
	public void testRankName() {
		assertEquals("Ace", r1.getName().toString());
	}
	

	/**
	 * Testing the creation of rank pips.
	 * Testing if the rank is created with proper pip value.
	 */
	@Test
	public void testRankPips() {
		assertEquals(0, r1.getPips());
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
	 * Testing the creation of suit symbol.
	 * Testing if the suit is created with proper symbol value.
	 */
	@Test
	public void testSuitSymbol() {
		assertEquals('s', s1.getSymbol());
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
		assertEquals("Five", c2.getRank().toString());
	}
}


