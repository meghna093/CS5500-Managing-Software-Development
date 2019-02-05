package edu.northeastern.ccs.cs5500.homework5;

import java.util.List;
/**
 * Sets all the parameters required to call the main method and start the
 * blackjack game
 * @author meghna
 *
 */
public class PlayGame extends PlaceBetPlayer {
	private static final String screenMsg = "Hit\nSplit\nStand";
	private static final String hit   = "hit";
	private static final String split = "split";
	private static final String stand = "stand";
	private static final String betAmount  = "1";
	private static final String placeBet = "Place a bet of $1\n";
	private static final String def = "default";
	private static final String yes = "yes";
	private static final String no  = "no";


	/**
	 * Constructor
	 * @param name Player name
	 * @param h Hand name
	 * @param b Total money with player
	 */
	public PlayGame(String name, Hand h, BetFactor b) {
		super(name, h, b);
	}


	/**
	 * Call to place bet to begin the game
	 */
	protected void playerBet() {
		while(true) {
			Screen.st.displayMsg(placeBet);
			String ret = Screen.st.readContent(def);
			if( ret.equals(betAmount)) {
				getBet().callBet(1);
				return;
			}
		}
	}


	/**
	 * This method is called when hit is selected by the user
	 */
	protected boolean playerHit(Dealer d) {
		while(true) {
			Screen.st.displayMsg(screenMsg);
			String ret = Screen.st.readContent(def);
			if(ret.equalsIgnoreCase(hit)) {
				return true;
			} 
			else if(ret.equalsIgnoreCase(stand)) {
				return false;
			}
		}
	}


	/**
	 * This method is called when split is selected by the user
	 */
	protected boolean playerSplit(Player p) {
		while(true) {
			String ret = Screen.st.readContent(def);
			if(ret.equalsIgnoreCase(split)) {
				return true;
			} 
			else if(ret.equalsIgnoreCase(stand)) {
				return false;
			}
		}
	}
}
