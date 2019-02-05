package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;
/**
 * Populates a deck of cards and handles various standard functionalities 
 * on the deck of cards
 * @author adim
 *
 */
public class CardDecks {
	private Random getRand = new Random();
	private ArrayList<Card> deckOfCards = new ArrayList<Card>();
	private int ind;


	/**
	 * populates an empty deck with cards
	 * @param c an empty set of cards, which will be populated
	 */
	public void populateDeck(Card[] c) {
		for(int i = 0; i < c.length; i++) {
			deckOfCards.add(c[i]);
		}
	}


	/**
	 * Indicates whether the given card is present in the deck
	 * @param card the card to be searched in the deck
	 * @return true if the card is present else false
	 */
	public boolean hasCard(Card card) {
		if(deckOfCards.contains(Objects.requireNonNull(card)))
			return true;
		else 
			return false;
	}

	
	/**
	 * Deals cards from the deck
	 * @return the card dealt from deck
	 */
	private Card deal() {
		if(ind != deckOfCards.size()) {
			Card c = (Card) deckOfCards.get(ind);
			ind++;
			return c;
		}
		return null;
	}


	/**
	 * Since this is a game of blackjack, one of the card that belongs to the
	 * dealer should be hidden. The below method deals the card in hidden form.
	 * @return hidden card that is dealt from the deck
	 */
	public Card hiddenDeal() {
		Card c = deal();
		if(c != null) {
			c.setPos(false);
		}
		return c;
	}


	/**
	 * Resets and shuffles a deck of cards
	 */
	public void shuffle() {
		reset();
		shuffleDeck();
		shuffleDeck();
		shuffleDeck();
		shuffleDeck();
	}

	private void shuffleDeck() {
		int count = deckOfCards.size();
		for(int i = 0; i < count; i++) {
			int ind = getRand.nextInt(count);
			Card firstCard = (Card) deckOfCards.get(i);
			Card nextCard = (Card) deckOfCards.get(ind);
			deckOfCards.set(i, nextCard);
			deckOfCards.set(ind, firstCard);
		}
	}

	/**
	 * Once the card is dealt the cards are exposed, so the cards has to be 
	 * hidden again for a new game. The below method handles this factor.
	 */
	public void reset() {
		ind = 0;
		Iterator<Card> itr = deckOfCards.iterator();
		while(itr.hasNext()) {
			Card c = (Card) itr.next();
			c.setPos(false);
		}
	}


	/**
	 * States the size of the deck
	 * @return count of cards present in the deck
	 */
	public int deckSize() {
		return deckOfCards.size();
	}
	
	
	/**
	 * Since this is a game of blackjack, except for one card that belongs to the
	 * dealer rest of the cards should be dealt normally, the below method 
	 * deals the card in normal form.
	 * @return normally dealt card
	 */
	public Card normalDeal() {
		Card c = deal();
		if(c != null) {
			c.setPos(true);
		}
		return c;
	}
}
