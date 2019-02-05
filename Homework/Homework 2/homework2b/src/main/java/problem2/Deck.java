package problem2;

/**
 * @author meghna
 */
public interface Deck {
	/**
	 * Shuffles the deck of cards
	 */
	public void shuffle();


	/**
	 * Sorts the deck of cards based on input string
	 * @param guidance indicates whether to sort based on rank or suit or both
	 */
	public void sort(String guidance);


	/**
	 * Cuts the deck based on the given point
	 * @param cutPoint which point to cut the deck at
	 */
	public void cut(int cutPoint);

	/**
	 * Removes the first card from the deck
	 * @return cards pulled from the top of the deck
	 */
	public Card pullCard();


	/**
	 * Tells whether a deck is empty or not
	 * @return true if the deck is empty else false
	 */
	public boolean emptyDeck();
}
