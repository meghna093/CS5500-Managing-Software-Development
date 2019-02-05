package edu.northeastern.ccs.cs5500.homework3;

import java.util.*;


/**
 * Defines the hands that participate in a game of cards. 
 * It can be implemented to different type of games such as euchre, pinochle, etc.
 * @author meghna
 */
public class Hands implements newHand  {
	private List<Cards> cards;
	private RankComp rankComp = new RankComp();
	private SuitComp suitComp = new SuitComp();
	private int ind;
	private int count;

	/**
	 * Hand Constructor
	 */
	public Hands() {
		cards = new ArrayList<Cards>();
	}


	/**
	 * Displays all the cards present in the hand
	 * @return list of all cards present in a hand
	 */
	public List<Cards> showCards()
	{
		try
		{
			Iterator<Cards> it = cards.iterator();
			while (it.hasNext()) 
			{
				System.out.println(it.next());
			}
		}
		catch (Exception e)
		{
			System.out.println("There are no cards to display");
			e.printStackTrace();
		}
		return cards;
	}


	/**
	 * Adds the given card into the hand
	 * @param card the card to be added into the hand
	 */
	public void accept(Card card) {
		cards.add((Cards) Objects.requireNonNull(card));
	}


	/**
	 * Removes the first card from hand
	 * @return the removed card from hand
	 */
	public Card pullCard() {
		Cards selectedCard = null;
		try {
			selectedCard = cards.get(cards.size() - 1);
			if (cards.size() > 0) {
				cards.remove(cards.size() - 1);
			}
		} catch (Exception e) {
			System.out.println("There are no cards to pull");
			e.printStackTrace();
		}
		return selectedCard;
	}

	
	/**
	 * Sorts the cards in hand based on the provided input
	 * @param guidance states whether to sort by rank or suit or both
	 * @return a sorted list of cards
	 */
	public void sort(String guidance) {
		if(guidance.equalsIgnoreCase("suit")) {
			Collections.sort(cards, suitComp);
		}
		else if(guidance.equalsIgnoreCase("rank")) {
			Collections.sort(cards, rankComp);
		}
		else if(guidance.equalsIgnoreCase("both")) {
			Collections.sort(cards);
		}
	}


	/**
	 * Comparator to compare between two ranks
	 * @param Cards card to compare for highest rank
	 * @return card with highest rank
	 */
	class RankComp implements Comparator<Cards> {
		public int compare(Cards c1, Cards c2) {
			if (c1.getRank() == c2.getRank()) {
				return c1.getSuit().compareTo(c2.getSuit());
			}
			return c1.getRank().compareTo(c2.getRank());
		}
	}


	/**
	 * Comparator to compare between two suits
	 * @param Cards card to compare for highest suit
	 * @return card with highest suit
	 */
	class SuitComp implements Comparator<Cards> {
		public int compare(Cards c1, Cards c2) {
			if (c1.getSuit() == c2.getSuit()) {
				return c1.getRank().compareTo(c2.getRank());
			}
			return c1.getSuit().compareTo(c2.getSuit());
		}
	}


	/**
	 * Converts the list of cards into their string values
	 * @return string values of cards in hand
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cards card : cards) {
			sb.append(card + "\n");
		}
		return sb.toString();
	}


	/**
	 * Shuffles all cards in hand
	 * @return cards in a different order
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}


	/**
	 * Making the list of cards unmodifiable
	 * @return unmodifiable list of cards
	 */
	public Collection<Cards> getUnmodifiableCollection() {
		return Collections.unmodifiableCollection(cards);
	}


	/**
	 * Gives the count of cards that are present in hand
	 * @return size of hand i.e. count of cards in hand currenlty
	 */
	public int handSize() {
		return cards.size();
	}


	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ newHand IMPLEMENTATION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * Indicates whether a card is present in hand
	 * @param cardToFind the card to search in hand
	 * @return true if the mentioned card is present else false
	 */
	public Boolean hasCard(Card cardToFind) {
		boolean res = false;
		try
		{
			Iterator<Cards> it = this.iterator();
			while(it.hasNext()){
				if(cardToFind == it.next()) 
				{
					res = true;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("There are no more cards");
			e.printStackTrace();
		}
		return res;
	}



	/**
	 * Counts the number of times the specified cards appears in hand
	 * @param cardToFind
	 * @return the count of number of times the mentioned card appears in hand
	 */
	public int occurrencesInHand(Card cardToFind) {
		try
		{
			Iterator<Cards> it = this.iterator();
			while(it.hasNext()){
				if(cardToFind.equals(it.next())){            

					count++;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("There are no more cards");
			e.printStackTrace();
		}

		return count;
	}


	/**
	 * Counts the number of times the specified card's rank appears in hand
	 * @param cardToFind
	 * @return the count of number of times the mentioned card rank appears in hand
	 */
	public int occurencesInHand(Rank rankToFind) {
		try
		{
			Iterator<Cards> it = this.iterator();
			while(it.hasNext()){
				if(rankToFind.equals(it.next().getRank())){            
					count++;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("There are no more cards");
			e.printStackTrace();
		}

		return count;
	}


	/**
	 * Iterates over a list of cards in hand, part of newHand implementation.
	 */
	public Iterator<Cards> iterator() {
		return new CardIterator<Cards>(this);
	}


	/**
	 * @param <Cards> List of cards in hand
	 * @return Iterator for hand class
	 */
	@SuppressWarnings("hiding")
	private class CardIterator<Cards> implements Iterator<Cards> {
		Hand hand;
		public CardIterator(Hands hand) {
			this.hand = hand;
		}

		/**
		 * returns true if the next card is present in list of cards
		 * else returns false
		 */
		public boolean hasNext() {
			return ind < cards.size();
		}

		/**
		 * returns the next card from list of cards present in hand
		 */
		@SuppressWarnings("unchecked")
		public Cards next() {
			if( ind >= cards.size() ){
				throw new NoSuchElementException( "There are no more cards" );
			}
			return (Cards) cards.get(ind++);
		}
	}
}





