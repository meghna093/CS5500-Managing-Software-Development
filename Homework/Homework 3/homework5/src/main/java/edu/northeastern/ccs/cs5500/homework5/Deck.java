package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;

/**
 * Creates 6 standard deck of cards which holds 312 cards totally.
 * @author meghna
 */
public class Deck {
	private int ind = 0;
	private Card[] deck;
	
	
	/**
	 * Deck Constructor. Creates 6 standard deck of cards.
	 */
	public Deck() {
		genDecks();
	}

	protected void genDecks() {
		deck = new Card[312];
		Iterator<Suit> itr = Suit.SUITS.iterator();
		while(itr.hasNext()) {
			Suit suit = (Suit) itr.next();
			Iterator<Rank> it = Rank.RANKS.iterator();
			while(it.hasNext()) {
				Rank rank = (Rank) it.next();
				deck[ind] = new Card(suit, rank);
				ind++;
			}
		}

	}


	/**
	 * Sets a deck of cards ready to be dealt
	 * @param d the deck to be set
	 */
	protected void pushDeck(Card[] d) {
		this.deck = d;
	}


	/**
	 * Adds cards to the deck
	 * @param s the card to be added
	 */
	public void addDeck(CardDecks s) {
		s.populateDeck(deck);
	}
}

