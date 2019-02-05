package edu.northeastern.ccs.cs5500.homework3;

public class GameFactory {
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
	public void selectGame(String game, int num1, int num2) {
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
	}

}
