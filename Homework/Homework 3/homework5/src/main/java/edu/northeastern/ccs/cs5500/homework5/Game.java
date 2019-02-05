package edu.northeastern.ccs.cs5500.homework5;

import java.util.Scanner;
/**
 * Executes the blackjack game.
 * @author adim
 *
 */
public class Game {

	/**
	 * This method checks whether the player wants to begin a new game or quit once
	 * the previous game is over
	 * @return
	 */
	private static boolean nextGame() {
		Screen.st.displayMsg("To continue enter 'Yes'\nTo quit press any other key to exit");
		String response = Screen.st.readContent("default");
		if(response.equalsIgnoreCase("yes")) {
			return true;
		}
		return false;
	}


	/**
	 * Main method. 
	 * This calls the blackjack game. First the user is asked to enter the number of 
	 * players that will play the game. The minimum number of player is one and the maximum 
	 * number of players are five. 
	 * Once the number of players is entered the user is asked to enter the bet money for each 
	 * player, in this case only $1 amount of bet can be placed. 
	 * After this step, each player is dealt with two cards and the control is passed on to the 
	 * first player. 
	 * The first player has three options, to 'Hit' or 'Split' or 'Stand'.
	 * Split can happen only when there are 2 same cards. 
	 * If the player selects 'Hit', then one card from the deck is dealt. Next the user can 
	 * select hit or stand. 
	 * If the player selects hit again then another card is dealt to this player. If the total 
	 * value of the card exceeds 21, then the player loses. If not the player is again provided 
	 * with options to hit or stand.
	 * If stand is selected then the first player's cards is noted and the control is passed 
	 * on to the next player and the same continues till each player has played. 
	 * At the end a list of winners and losers is displayed.
	 * @param args
	 */
	public static void main(String [] args) {
		Hand dHand = new Hand();
		CardDecks cards = new CardDecks();
		MainDealer bJackDealer = new MainDealer("Dealer", dHand, cards);
		System.out.println("Please enter the number of players, maximum players can be 5\n");
		Scanner scanner = new Scanner(System.in);
		int res = scanner.nextInt();
		bJackDealer.includeIn(Screen.st);
		if(res<=5 && res>=1) {
			for(int j=1;j<=res;j++) {
				BetFactor playerMoney = new BetFactor(5);
				Hand pHand = new Hand();
				Player player = new PlayGame("Player"+j, pHand, playerMoney);
				for(int i = 0; i < 4; i++) {
					Deck deck = new Deck();
					cards.shuffle();
					deck.addDeck(cards);
					cards.shuffle();
				}

				player.includeIn(Screen.st);
				bJackDealer.includePlayer(player);
			}
		}
		else {
			System.out.println("Please enter valid input\n");
		}

		do {
			bJackDealer.freshGame();
		} while(nextGame());
		Screen.st.displayMsg("Good Game");
	}
}
