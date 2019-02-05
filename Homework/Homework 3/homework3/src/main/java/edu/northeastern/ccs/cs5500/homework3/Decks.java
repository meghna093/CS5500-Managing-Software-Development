package edu.northeastern.ccs.cs5500.homework3;

import java.util.*;

/**
 * Creates a standard deck of cards which holds 52 cards. The rank order and suit order 
 * remains the same as mentioned in Ranks and Suits java class. Performs various operations
 * on the deck of cards generated.
 * @author meghna
 *
 */
public class Decks implements Deck {
	private int ind;
	private static List<Cards> deck = new ArrayList<Cards>();
	private RankComp rankComp = new RankComp();
	private SuitComp suitComp = new SuitComp();

	/**
	 * Deck Constructor. Creates a standard deck of cards.
	 */
	public Decks() {
		this(1);
	}

	public Decks(int deckCount) {
		List<Cards> deck = new ArrayList<Cards>();
		resetDeck();
		for (int i = 0; i < deckCount; i++) {
			generateDeck();
		}
	}

	
	/**
	 * Clears a deck 
	 * @return an empty deck
	 */
	public void resetDeck() {
		deck.clear();
	}

	
	/**
	 * Populates a deck with cards with appropriate rank and suit values
	 * @return standard deck
	 */
	public void generateDeck() {
		for(Suits suit : Suits.VALUES) {
			for(Ranks rank : Ranks.VALUES) {
				deck.add(new Cards(rank, suit));
			}
		}
	}

	public void generateDeck(int deckCount) {
		for(int i = 0; i < deckCount; i++) {
			generateDeck();
		}
	}

	
	/**
	 * Shuffles a deck of cards
	 * @return deck of cards in a different order
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	
	/**
	 * Sorts the given deck of cards based on the provided input
	 * @param guidance states whether to sort by rank or suit or both
	 * @return a sorted deck of cards
	 */
	public void sort(String guidance) {
		if(guidance.equalsIgnoreCase("suit")) {
			Collections.sort(deck, suitComp);
		}
		else if(guidance.equalsIgnoreCase("rank")) {
			Collections.sort(deck, rankComp);
		}
		else if(guidance.equalsIgnoreCase("both")) {
			Collections.sort(deck);
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
				return (c1.getSuit()).compareTo(c2.getSuit());
			}
			return (c1.getRank()).compareTo(c2.getRank());
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
				return (c1.getRank()).compareTo(c2.getRank());
			}
			return (c1.getSuit()).compareTo(c2.getSuit());
		}
	}

	
	/**
	 * Converts the list of cards into their string values
	 * @return string values of cards within a deck
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cards card : deck) {
			sb.append(card + "\n");
		}
		return sb.toString();
	}

	
	/**
	 * Cuts the card at the provided point
	 * @param cutPoint point at which the cut has to be made
	 * @return new deck after being cut
	 */
	public void cut(int cutPoint) {
		try {
			List<Cards> cutDeck = new ArrayList<Cards>(deck.size());
			cutDeck.addAll(deck.subList(cutPoint, deck.size()));
			cutDeck.addAll(deck.subList(0, cutPoint));
			deck = cutDeck;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Pulls a card from the top of the deck
	 * @return card present at the top of the deck
	 */
	public Card pullCard() {
		Cards selectedCard = null;
		try {
			selectedCard = (Cards) deck.get(deck.size() - 1);
			if (deck.size() > 0) {
				deck.remove(deck.size() - 1);
			} 
		} catch (Exception e) {
			System.out.println("There are no cards to pull");
			e.printStackTrace();
		}
		return selectedCard;
	}

	
	/**
	 * Indicates whether a deck is empty of not
	 * @return true if a deck is empty else false
	 */
	public boolean emptyDeck() {
		if(ind >= deck.size())
			return true;
		else 
			return false;	
	}

	
	/**
	 * States the current size of the deck
	 * @return count of cards present in the deck currently
	 */
	public int deckSize() {
		return deck.size();
	}
	

	/**
	 * Indicates whether the given card is present in the deck
	 * @param card the card to be searched in the deck
	 * @return true if the card is present else false
	 */
	public boolean hasCard(Cards card) {
		if(deck.contains(Objects.requireNonNull(card)))
			return true;
		else 
			return false;
	}
	
	
	/**
	 * Making the list of cards unmodifiable
	 * @return unmodifiable list of cards
	 */
	public Collection<Cards> getUnmodifiableCollection() {
		return new ArrayList<Cards>(deck);
	}	
}

