package edu.northeastern.ccs.cs5500.homework3;

/**
 * @author meghna
 */
public interface Game {
	/**
	 * Creates a deck for a particular type of game
	 * @param deckType indicates the type of game to be created
	 */
	public void createDeck(String deckType);
	
	
	/**
	 * Creates a number of decks for a particular type of game
	 * @param deckType indicates the type of game to be created
	 * @param numberOfDecks indicates how many decks should be created
	 */
	public void createDeck(String deckType, int numberOfDecks);
	
	
	/**
	 * Deals card from the deck
	 */
	public void deal();
	
	
	/**
	 * Indicates the number of hands to be set
	 * @param howManyHands gives the count to set the number of hands
	 */
	public void setNumberOfHands(int howManyHands);

}

