package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;
/**
 * Creates various functionalities related to player of the game
 */
public abstract class Player {
	private String name;
	private Hand h;
	private PlayerState currentState;
	private ArrayList<PlayerList> viewers = new ArrayList<PlayerList>();


	/**
	 * Constructor
	 * @param name name of the player
	 * @param h hand of the player
	 */
	public Player(String name, Hand h) {
		this.name = name;
		this.h = h;
		setCurrentState(getInitialState());
	}


	/**
	 * Name of the player
	 * @return player name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Starts a game
	 */
	public void startGame(Dealer d) {
		currentState.initiate(d);
	}


	/**
	 * Adds a card to player hand
	 * @param c card to be added
	 */
	public void addCard(Card c) {
		h.addCard(c);
	}


	/**
	 * Includes this player in to a list
	 */
	public void includeIn(PlayerList list) {
		viewers.add(list);
	}


	/**
	 * Resets the hand of player for next game
	 */
	public void resetGame() {
		h.reset();
		setCurrentState(getInitialState());
		isChanged();
	}

	
	/**
	 * Removes a card from player hand
	 */
	public void removeCard() {
		h.removeCard();
	}
	
	
	/**
	 * Gets the hand of the player
	 * @return player hand
	 */
	public Hand getHand() {
		return h;
	}

	
	/**
	 * Returns the number of cards present in player's hand
	 * @return card count
	 */
	public int getHandSize() {
		return h.handSize();
	}
	
	
	/**
	 * Checks if the player has blackjack 
	 */
	public void blackjack() {
		isBlackJack();
	}


	/**
	 * Checks if the player has won
	 */
	public void ifWin() {
		pingWin();
	}


	/**
	 * Checks if the player is in Stand condition
	 */
	public void ifStand() {
		isStandOff();
	}


	/**
	 * Checks if the player has lost
	 */
	public void ifLose() {
		pingLose();
	}

	/**
	 * String representation of player name and list of cards they have
	 */

	public String toString() {
		return (name + ": " + h.toString());
	}


	/**
	 * Checks if the player is busted
	 */
	protected void isBusted() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.bustState(this);
		}
	}


	/**
	 * Checks if the player is in stand condition
	 */
	protected void isStanding() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.standState(this);
		}
	}


	/**
	 * Checks if its the same player
	 */
	protected void isChanged() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.changedState(this);
		}
	}


	/**
	 * Checks if the player has blackjack
	 */
	protected void isBlackJack() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.bjackState(this);
		}
	}


	/**
	 * Checks if there is a tie condition
	 */
	protected void isStandOff() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.stdState( this );
		}
	}


	/**
	 * Notifies if the player loses
	 */
	protected void pingLose() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.lostState(this);
		}
	}


	/**
	 * Notifies if the player won
	 */
	protected void pingWin() {
		Iterator<PlayerList> itr = viewers.iterator();
		while(itr.hasNext()) {
			PlayerList pList = (PlayerList) itr.next();
			pList.wonState(this);
		}
	}


	protected abstract boolean playerHit(Dealer d);
	
	protected abstract boolean playerSplit(Player p);


	protected abstract PlayerState getInitialState();

	
	/**
	 * @return current state of the player
	 */
	protected final PlayerState getCurrentState() {
		return currentState;
	}


	/**
	 * Sets the current state of the player
	 * @param state 
	 */
	protected final void setCurrentState(PlayerState state) {
		currentState = state;
		h.assign(state);
	}


	/**
	 * @return current state of the player game
	 */
	protected PlayerState getPlayState() {
		return new PlayingCards();
	}


	/**
	 * @return stand state of the player
	 */
	protected PlayerState getStandState() {
		return new StandGame();
	}


	/**
	 * @return busted state of player
	 */
	protected PlayerState getBustState() {
		return new BustedGame();
	}


	/**
	 * @return win state of player
	 */
	protected PlayerState getWinState() {
		return new Blackjack();
	}


	/**
	 * @return wait state of player
	 */
	protected PlayerState getWaitState() {
		return new WaitGame();
	}


	/**
	 * Sets current state to win state
	 */
	private class WaitGame implements PlayerState {
		public void initiate(Dealer d) {

		}
		public void bustedState() {

		}
		public void playState() {
			setCurrentState(getPlayState());
		}
		public void changeState() {
			isChanged();
		}

		public void winState() {
			setCurrentState(getWinState());
			isBlackJack();
		}
	}


	/**
	 * Blackjack state 
	 */
	private class Blackjack implements PlayerState {
		public void initiate(Dealer d) {
			d.blackjack(Player.this);
		}
		public void bustedState() {

		}
		public void playState() {

		}
		public void changeState() {

		}
		public void winState() {

		}
	}


	/**
	 * Busted state
	 */
	private class BustedGame implements PlayerState {
		public void initiate(Dealer d) {
			d.busted(Player.this);
		}
		public void bustedState() {

		}
		public void playState() {

		}
		public void changeState() {

		}
		public void winState() {

		}
	}


	/**
	 * Hit state and the respective states that follows after
	 */
	private class PlayingCards implements PlayerState {
		public void initiate(Dealer d) {
			if(playerHit(d)) {
				d.hit(Player.this);
			} else {
				setCurrentState(getStandState());
				isStanding();
			}
			currentState.initiate(d);
		}
		public void bustedState() {
			setCurrentState(getBustState());
			isBusted();
		}
		public void playState() {

		}
		public void changeState() {
			isChanged();
		}
		public void winState() {

		}
	}


	/**
	 * Stand state
	 */
	private class StandGame implements PlayerState {
		public void initiate(Dealer d) {
			d.stand(Player.this);
		}
		public void bustedState() {

		}
		public void playState() {

		}
		public void changeState() {

		}
		public void winState() {

		}
	}
}

