package edu.northeastern.ccs.cs5500.homework5;


/**
 * Handles the betting factor for the whole game.
 * @author meghna
 *
 */
public class BetFactor {
	private int betAmount;
	private int finalAmount;


	/**
	 * Constructor for BetFactor class
	 * @param cash the total amount a player has
	 */
	public BetFactor(int cash) {
		finalAmount = cash;
	}


	/**
	 * If the game is won, the bet amount is increased by $1
	 */
	public void ifWin() {
		finalAmount += (2 * betAmount);
		betAmount = 0;
	}


	/**
	 * If the game is a tie then, the bet amount remains the same
	 */
	public void ifStand() {
		finalAmount += betAmount;
		betAmount = 0;
	}


	/**
	 * If the game is lost, the bet amount is deducted by $1.
	 */
	public void ifLose() {
		betAmount = 0;
	}


	/**
	 * Places a bet by passing the amount to be bet on, in
	 * our case it always remains $1.
	 * @param amount money to call bet on
	 */
	public void callBet(int amount) {
		betAmount = amount;
		finalAmount -= amount;
	}


	/**
	 * @return string value of the bet money
	 */
	public String toString() {
		return ("$" + finalAmount);
	}
}

