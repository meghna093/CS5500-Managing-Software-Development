package problem2;

import java.util.List;

/**
 * @author meghna
 */
public interface Hand {
	/**
	 * @return a list of cards present in hand
	 */
	public List<Cards> showCards();
	
	
	/**
	 * @param card
	 */
	public void accept(Card card);
	
	
	/**
	 * @return the first card from the hand
	 */
	public Card pullCard();
	
	
	/**
	 * @param cardToFind
	 * @return true if the mentioned card is present in hand else false
	 */
	public boolean hasCard(Card cardToFind);
	
	
	/**
	 * @param guidance specifies whether to sort by Rank or Suit or Both(i.e. sort by suit and rank)
	 */
	public void sort(String guidance);
	
	
	/**
	 * @return shuffled set of cards in hand
	 */
	public void shuffle();
}
