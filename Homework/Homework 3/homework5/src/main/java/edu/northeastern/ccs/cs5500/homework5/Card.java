package edu.northeastern.ccs.cs5500.homework5;

/**
 * Creation of a single card that will be present in a deck of cards.
 * @author meghna
 */
public class Card {
	private boolean cardPos;
	private Suit suit;
	private Rank rank;


	/**
	 * Card constructor
	 * @param suit states the suit of this card
	 * @param rank states the rank of this card
	 */
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}


	/**
	 * @return rank of this card
	 */
	public Rank getRank() {
		return rank;
	}


	/**
	 * @return the suit of this card
	 */
	public Suit getSuit() {
		return suit;
	}

	
	/**
	 * Since this is a game of blackjack, one card of the dealer should
	 * be hidden. The below method handles this factor of the card
	 * @return true if card is not hidden, false if its hidden
	 */
	public boolean cardUp() {
		return cardPos;
	}


	/**
	 * We can explicitly set the hidden factor of card discussed in the 
	 * previous method using the below method
	 * @param cond true to display the card, false to hide the card
	 */
	public void setPos(boolean cond) {
		cardPos = cond;
	}


	/**
	 * @return string value of this card
	 */
	public String toString() {
		if(!cardUp()) 
			return "???";
		return rank.toString() +" of "+ suit.toString();
	}

	
	/**
	 * Compares two different objects for their equality. Returns true if two 
	 * cards are same of, else returns false.
	 */
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Card)
		{
			Card r = (Card) o;
			return (r.getRank() == this.getRank() && r.getSuit() == this.getSuit());

		}
		return false;
	}
}

