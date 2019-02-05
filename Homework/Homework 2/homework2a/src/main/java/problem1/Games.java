package problem1;

import java.util.*;

/**
 * Creates different types of games and its respective decks.
 * Euchre, Pinochle and Vegas Blackjack are the games that can be created.
 * @author meghna
 *
 */
public class Games implements Game {
	private static List<Cards> deck;
	private int ind;
	private int handCount;


	/**
	 * Game Constructor
	 */
	public Games() {
		deck = new ArrayList<Cards>();
		ind = 0;
	}


	/**
	 * Creates a deck of cards based on the type of game name
	 * @param deckType indicates for which game the deck should be created
	 */
	public void createDeck(String deckType) {
		if(deckType.equalsIgnoreCase("Euchre")) {
			createDeck("Euchre", 1);
		}
		else if(deckType.equalsIgnoreCase("Standard")) {
			createDeck("Standard", 1);
		}
		else if(deckType.equalsIgnoreCase("Pinochle")) {
			createDeck("pinochle", 1);
		}
		else if(deckType.equalsIgnoreCase("Vegas")) {
			createDeck("Vegas", 2);
		}
	}


	/**
	 * Creates multiple number of  decks as specified based on the type of game  
	 * @param deckType indicates for which game the deck should be created
	 * @param numberOfDecks tells how many decks for the game should be created
	 */
	public void createDeck(String deckType, int numberOfDecks) {
		for(int i=0; i<numberOfDecks; i++) {
			if(deckType.equalsIgnoreCase("Euchre")) {
				for(Suits suit : Suits.VALUES) {
					for(Ranks rank : Ranks.EUCHRE_VALUES) { deck.add(new Cards(rank, suit)); }
				}
			}
			else if(deckType.equalsIgnoreCase("Standard")) {
				for(Suits suit : Suits.VALUES) {
					for(Ranks rank : Ranks.VALUES) { deck.add(new Cards(rank, suit)); }
				}
			}
			else if(deckType.equalsIgnoreCase("Pinochle")) {
				for(Suits suit : Suits.VALUES) {
					for(Ranks rank : Ranks.PINOCHLE_VALUES) { deck.add(new Cards(rank, suit)); }
				}
			}
		}
		if(numberOfDecks<1 || numberOfDecks>8) 
			System.out.println("Minimum number of deck should be 2 and maximum number of deck should be 8");
		else if(2<=numberOfDecks && numberOfDecks<=8){
			for(int i=0; i<numberOfDecks; i++) {
				if(deckType.equalsIgnoreCase("Vegas")) {
					for(Suits suit : Suits.VALUES) {
						for(Ranks rank : Ranks.VALUES) { deck.add(new Cards(rank, suit)); }
					}
				}
			}
		}
	}


	/**
	 * Deals cards from the deck
	 */
	public void deal() {
		try {
			List<Cards> dealCard = new ArrayList<Cards>();
			for (int ind = 0; ind < 1; ind++) {
				dealCard.add(deck.remove(0));
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Creates a particular number of hands
	 * @param howManyHands tells how many hands should be created
	 */
	public void setNumberOfHands(int howManyHands) {
		handCount = howManyHands;
	}


	/**
	 * Tells the size of the deck 
	 * @return size of the deck currently
	 */
	public int deckSize() {
		return deck.size();
	}


	/**
	 * Pulls the first card from the deck
	 * @return card that is removed from the deck
	 */
	public Cards pullCard() {
		Cards drawnCard = null;
		try {
			drawnCard = deck.get(deck.size() - 1);
			if (deck.size() > 0){
				deck.remove(deck.size() - 1);
			}
		} catch (Exception e) {
			System.out.println("There are no cards to pull");
			e.printStackTrace();
		}
		return drawnCard;
	}


	/**
	 * Informs whether the mentioned card is present
	 * @param card the card to be searched in the game
	 * @return true if the mentioned card is present else false
	 */
	public boolean hasCard(Cards card) {
		if(deck.contains(Objects.requireNonNull(card)))
			return true;
		else 
			return false;
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
}