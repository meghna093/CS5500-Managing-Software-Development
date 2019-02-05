package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;

/**
 * Defines a hand that participate in the game of blackjack. 
 * @author meghna
 *
 */

public class Hand {
	private static final int WINSCORE = 21;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int aceCount; 
	private PopulateHand pop;


	/**
	 * Hand constructor. It sets the holder to a blank listener.
	 */
	public Hand() {
		assign(new PopulateHand() {
			public void playState() {

			}
			public void winState() {

			}
			public void bustedState() {

			}
			public void changeState() {

			}});
	}


	/**
	 * Assigns a card to the hand
	 * @param pop
	 */
	public void assign(PopulateHand pop) {
		this.pop = pop;
	}


	/**
	 * Checks whether a hand is busted or not
	 * @return true if hand is not busted, else returns false
	 */
	public boolean busted() {
		if(finalValue() > WINSCORE) {
			return true;
		}
		return false;
	}


	/**
	 * Checks whether a hand won or not
	 * @return true if the hand won, else returns false
	 */
	public boolean handWin() {
		if(cards.size() == 2 && finalValue() == WINSCORE) {
			return true;
		}
		return false;
	}


	/**
	 * Displays all the cards that are present in the hand
	 */
	public void showCard() {
		Iterator<Card> itr = cards.iterator();
		while(itr.hasNext()) {
			Card card = (Card) itr.next();
			card.setPos(true);
		}
	}


	/**
	 * Returns the number of cards in hand
	 * @return count of number of cards
	 */
	public int handSize() {
		return cards.size();
	}


	/**
	 * Removes the cards present in index 0
	 */
	public void removeCard() {
		cards.remove(0);
	}

	
	/**
	 * Checks whether 2 cards in a hand are equal
	 * @return true if 2 cards are equal, else returns false
	 */
	public boolean sameCard() {
		for(int i=0;i<cards.size();i++) {
			for(int j=i+1;j<cards.size();j++) {
				if(cards.get(i).equals(cards.get(j))) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Resets a hand by clearing out the cards in hand and prepares
	 * it for the next game
	 */
	public void reset() {
		cards.clear();
		aceCount = 0;
	}


	/**
	 * Adds a card into the hand
	 * @param c card to be added
	 */
	public void addCard(Card c) {
		cards.add(c);
		pop.changeState();
		if(c.getRank() == Rank.ACE) {
			aceCount++;
		}
		if(busted()) {
			pop.bustedState();
			return;
		}
		if(handWin()) {
			pop.winState();
			return;
		}
		if(cards.size() >= 2){
			pop.playState();
			return;
		}
	}


	/**
	 * Fetches the list of cards present in hand
	 * @return list of cards in hand
	 */
	public Iterator<Card> fetchCards() {
		return cards.iterator();
	}


	/**
	 * Checks whether two hands are equal
	 * @param h hand to be checked
	 * @return true if the hands are equal, else false
	 */
	public boolean isEqual(Hand h) {
		if(h.finalValue() == this.finalValue()) {
			return true;
		}
		return false;
	}


	/**
	 * @return string representation of cards in hand
	 */
	public String toString() {
		Iterator<Card> itr = cards.iterator();
		String res = "";
		while(itr.hasNext()) {
			Card c = (Card)itr.next();
			res = res + " " + c.toString();
		}
		return res;
	}

	
	/**
	 * Checks which hand has highest values of cards
	 * @param h hand to be checked
	 * @return true if the hand is greater than the other
	 */
	public boolean greater(Hand h) {
		return this.finalValue() > h.finalValue();
	}


	/**
	 * Calculates the total value of cards in hand. It maintains 
	 * the number of Ace cards in hand. If there is only one Ace 
	 * card then the value of that Ace card will be 11 (soft hand). If there
	 * are more than one Ace card the the value of Ace card is reduced
	 * to 1 by deducting 10 (hard hand). This can be seen when the user has an Ace 
	 * card and asks for a hit and gets another Ace card, then the value of
	 * Ace is considered to be 1.
	 * With this logic the total value of hand will be calculated.
	 * @return total value of cards in hand
	 */
	public int finalValue() {
		Iterator<Card> itr = cards.iterator();
		int count = 0;
		while(itr.hasNext()) {
			Card c = (Card) itr.next();
			count += c.getRank().getRank();
		}
		int storeAce = aceCount;
		while(count > WINSCORE && storeAce > 0) {
			count = count - 10;
			storeAce--;
		}
		return count;
	}
}
