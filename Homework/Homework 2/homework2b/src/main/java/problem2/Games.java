package problem2;

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
	private int cardCount;


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
	
	
	/**
	 * Method to create a particular type of game i.e. Euchre or Pinochle or
	 * vegas blackjack. Once the type of game is selected to be created its 
	 * respective deck will be populated. Since a vegas blackjack deck has two
	 * or more count of a standard deck, I have taken 2 standard decks as default
	 * number of decks for a vegas blackjack deck.
	 * @param game the type of game to be created i.e. Euchre or Pinochle or
	 * vegas blackjack
	 * @param num1 number of hands to be created 
	 * @param num2 number of cards to be dealt to each hand
	 */
	public static void selectGame(String game, int num1, int num2) {
		String[] suit = {"SPADES", "HEARTS", "CLUBS", "DIAMONDS"};
		if(game.equalsIgnoreCase("Euchre")) {
			String[] euchreRank = {"NINE", "TEN", "JACK", "QUEEN", "KING", "ACE"};
			int n1 = euchreRank.length*suit.length;
			String[] euchreDeck = new String[n1];
			for (int i=0; i<euchreRank.length; i++) {
				for (int j=0; j<suit.length; j++) {
					euchreDeck[4*i + j] = euchreRank[i] + " of " + suit[j];
				}
			}
			createGame(euchreDeck, n1, num1, num2);
		}
		else if(game.equalsIgnoreCase("Pinochle")) {
			String[] pinochleRank = {"NINE", "NINE", "TEN", "TEN", "JACK", "JACK", "QUEEN", 
					"QUEEN", "KING", "KING", "ACE", "ACE"};
			int n2 = pinochleRank.length*suit.length;
			String[] pinochleDeck = new String[n2];
			for (int i=0; i<pinochleRank.length; i++) {
				for (int j=0; j<suit.length; j++) {
					pinochleDeck[4*i + j] = pinochleRank[i] + " of " + suit[j];
				}
			}
			createGame(pinochleDeck, n2, num1, num2);
		}
		else if(game.equalsIgnoreCase("Vegas")) {
			String[] vegasRank = {"TWO", "TWO", "THREE", "THREE", "FOUR", "FOUR", "FIVE", "FIVE", 
					"SIX", "SIX",	"SEVEN", "SEVEN", "EIGHT", "EIGHT", "NINE", "NINE", "TEN", 
					"TEN", "JACK", "JACK", "QUEEN", "QUEEN", "KING", "KING", "ACE", "ACE"};
			int n3 = vegasRank.length*suit.length;
			String[] vegasDeck = new String[n3];
			for (int i=0; i<vegasRank.length; i++) {
				for (int j=0; j<suit.length; j++) {
					vegasDeck[4*i + j] = vegasRank[i] + " of " + suit[j];
				}
			}
			createGame(vegasDeck, n3, num1, num2);
		}
	}

	
	/**
	 * Creates num1 count of hands and distributes num2 count of cards
	 * to the respective hands.
	 * @param deck deck populated from selectGame() method
	 * @param n1 product of length of suit and length of rank
	 * @param num1 number of hands to be created 
	 * @param num2 number of cards to be dealt to each hand
	 * @throws error if number of cards to be distributed among created 
	 * hands falls short
	 */
	private static void createGame(String[] deck, int n1, int num1, int num2) {
		if (num2*num1 > n1)
			throw new RuntimeException("Number of players are high");
		for (int i=0; i<n1; i++) {
			int j = i + (int)((n1-i)*Math.random());
			String store = deck[j];
			deck[j] = deck[i];
			deck[i] = store;
		}
		for (int i=0; i<num1*num2; i++) {
			System.out.println(deck[i]);
			if (i%num2 == num2-1)
				System.out.println();
		}
	}
}