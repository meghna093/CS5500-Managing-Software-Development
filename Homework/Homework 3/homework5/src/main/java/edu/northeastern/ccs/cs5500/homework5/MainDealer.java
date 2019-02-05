package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;

/**
 * Create and handles various functionalities of the game related to dealer
 * @author meghna
 *
 */
public class MainDealer extends Player implements Dealer {

	private ArrayList<Player> players = new ArrayList<Player>();
	private CardDecks cards;
	private ArrayList<Player> playerBlackJack;
	private ArrayList<Player> playerBust;
	private ArrayList<Player> playerStand;
	protected ArrayList<Player> playerBet;
	protected ArrayList<Player> playerWaiting;
	private ArrayList<Card> deckOfCards = new ArrayList<Card>();
	private int ind;
	private Deck deck = new Deck();


	/**
	 * MaiDealer Constructor 
	 * @param name Name of the dealer
	 * @param h hand of the dealer
	 * @param c deck of cards to be dealt
	 */
	public MainDealer(String name, Hand h, CardDecks c) {
		super(name, h);
		this.cards = c;
	}


	/**
	 * If a player is in 'Stand' state then that player
	 * is added to a list and game continues to its respective
	 * next state
	 * @param p Player
	 */
	public void stand(Player p) {
		playerStand.add(p);
		startGame(this);
	}


	/**
	 * Rotates a card from its hidden form and displays the 
	 * value of the card.
	 * @return card that is rotated
	 */
	public Card rotateCard() {
		Iterator<Card> itr = getHand().fetchCards();
		while(itr.hasNext()) {
			Card c = (Card) itr.next();
			if(c.cardUp()) {
				return c;
			}
		}
		return null;
	}


	/**
	 * Deals the cards from a deck consisting of 6 set of standard deck. 
	 * Here a cut point is generated randomly and that point is noted. Once
	 * the cards are dealt from deck to that point, a new deck is generated,
	 * shuffled and the game continues.
	 */
	public void dealCards() {
		Random rand = new Random();
		int cutPoint = rand.nextInt(34);
		if(ind != cutPoint) {
			Card c = (Card) deckOfCards.get(ind);
			ind++;
		}
		else {
			deck.genDecks();
		}
	}


	/**
	 * The below methods allows the player to place a bet and
	 * the player is made to wait till the bet is placed
	 * @param p Player
	 */
	public void placeBetting(Player p) {
		playerWaiting.add(p);
		startGame(this);
	}


	/**
	 * This method deals one card to the player when asked to hit 
	 * a card
	 * @param p Player
	 */
	public void hit(Player p) {
		p.addCard(cards.normalDeal());
	}
	
	
	/**
	 * This method splits 2 same cards that are present in hand
	 * @param p Player
	 */
	public List<Player> split(Player p) {
		List<Player> playerList1 = new ArrayList<Player>();
		BetFactor playerMoney = new BetFactor(5);
		Hand pHand = new Hand();
		Player player = new PlayGame("NewHand", pHand, playerMoney);
		playerList1.add(p);
		playerList1.add(player);
		return playerList1;
	}


	/**
	 * This method handles if a player is busted
	 * @param p Player
	 */
	public void busted(Player p) {
		playerBust.add(p);
		startGame(this);
	}


	/**
	 * This method handles if a player wins
	 * @param p Player
	 */
	public void blackjack(Player p) {
		playerBlackJack.add(p);
		startGame(this);
	}


	/**
	 * Starts a new game
	 */
	public void freshGame() {
		resetGame();
		startGame(this);
	}

	
	/**
	 * Resets a game for next new game
	 */
	public void resetGame() {
		super.resetGame();
		playerBlackJack = new ArrayList<Player>();
		playerBust = new ArrayList<Player>();
		playerStand = new ArrayList<Player>();
		playerBet = new ArrayList<Player>();
		playerWaiting = new ArrayList<Player>();
		playerBet.addAll(players);
		cards.reset();
		Iterator<Player> itr = players.iterator();
		while(itr.hasNext()) {
			Player player = (Player) itr.next();
			player.resetGame();
		}
	}


	/**
	 * Displays the list of cards in hand
	 */
	protected void showHand() {
		getHand().showCard();
		isChanged();
	}


	/**
	 * Adds a player to the list of players
	 * @param p Player
	 */
	public void includePlayer(Player p) {
		players.add(p);
	}


	/**
	 * Checks whether a player can be hit with one card
	 * @return true if player is eligible to be dealt with a card
	 * 		   else returns false
	 */
	protected boolean playerHit(Dealer d) {
		if(playerStand.size() > 0 && getHand().finalValue() < 17) {
			return true;
		}
		return false;
	}


	/**
	 * Checks whether a hand can be split
	 * @param p Player
	 * @return true if a hand can be split, else false
	 */
	public boolean playerSplit(Player p) {
		if(p.getHand() == null) {
			return false;
		}
		else if(p.getHand().handSize() != 2 && !p.getHand().sameCard()) {
			return false;
		}
		else {
			p.removeCard();
			playerSplit(p);
			return true;
		}
	}
	
	
	/**
	 * Deals cards from the deck 
	 */
	public void dealCard() {
		cards.shuffle();
		Player[] p = new Player[playerWaiting.size()];
		playerWaiting.toArray(p);
		for(int i = 0; i < p.length; i++) {
			p[i].addCard(cards.normalDeal());
		}
		this.addCard(cards.normalDeal());
		for(int i = 0; i < p.length; i++) {
			p[i].addCard(cards.normalDeal());
		}
		this.addCard(cards.hiddenDeal());
	}
	
	
	/**
	 * Gets the initial state of the game
	 * @return state
	 */
	protected PlayerState getInitialState() {
		return new DBet();
	}


	/**
	 * Gets the deal state of the game
	 * @return state
	 */
	protected PlayerState getDealState() {
		return new DDeal();
	}


	/**
	 * Gets the wait state of the game
	 * @return state
	 */
	protected PlayerState getWaitState() {
		return new DWait();
	}


	/**
	 * Gets the bet state of the game
	 * @return state
	 */
	protected PlayerState getBetsState() {
		return new DBet();
	}


	/**
	 * Gets the stand state of the game
	 * @return state
	 */
	protected PlayerState getStandState() {
		return new DStand();
	}


	/**
	 * Gets the bust state of the game
	 * @return state
	 */
	protected PlayerState getBustState() {
		return new DBust();
	}


	/**
	 * Gets the win state of the game
	 * @return state
	 */
	protected PlayerState getWinState() {
		return new DBlackJack();
	}


	/**
	 * Checks if a player has placed the bet and starts the game
	 */
	private class DBet implements PlayerState {
		public void initiate(Dealer d) {
			if(!playerBet.isEmpty()) {
				Player p = (Player) playerBet.get(0);
				playerBet.remove(p);
				p.startGame(d);
			} 
			else {
				setCurrentState(getDealState());
				getCurrentState().initiate(d);
			}
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
	 * Deals a deck of cards
	 */
	private class DDeal implements PlayerState {
		public void initiate(Dealer d) {
			dealCard();
			getCurrentState().initiate(d);
		}
		public void bustedState() {

		}
		public void playState() {
			setCurrentState(getWaitState());
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
	 * Checks if any player is waiting for their turn and starts 
	 * the game
	 */
	private class DWait implements PlayerState {
		public void initiate(Dealer d) {
			if(!playerWaiting.isEmpty()) {
				Player p = (Player) playerWaiting.get(0);
				playerWaiting.remove(p);
				p.startGame(d);
			} 
			else {
				setCurrentState(getPlayState());
				showHand();
				getCurrentState().initiate(d);
			}
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
	 * Checks which player hand in stand state has highest value of cards
	 * and declares winners and losers
	 */
	private class DStand implements PlayerState {
		public void initiate(Dealer d) {
			Iterator<Player> itr = playerStand.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				if(p.getHand().isEqual(getHand())) {
					p.ifStand();
				} 
				else if(p.getHand().greater(getHand())) {
					p.ifWin();
				} 
				else {
					p.ifLose();
				}
			}
			itr = playerBlackJack.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				p.blackjack();
			}
			itr = playerBust.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				p.ifLose();
			}
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
	 * Checks which player in stand state has won or lost
	 */
	private class DBust implements PlayerState {
		public void initiate(Dealer d) {
			Iterator<Player> itr = playerStand.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				p.ifWin();
			}
			itr = playerBlackJack.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				p.blackjack();
			}
			itr = playerBust.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				p.ifLose();
			}
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
	 * Checks which player have won the game
	 */
	private class DBlackJack implements PlayerState {
		public void initiate(Dealer d) {
			showHand();
			Iterator<Player> itr = players.iterator();
			while(itr.hasNext()) {
				Player p = (Player) itr.next();
				if(p.getHand().handWin()) {
					p.ifStand();
				} 
				else {
					p.ifLose();
				}
			}
		}
		public void bustedState() {

		}
		public void playState() {

		}
		public void changeState() {
			isChanged();
		}
		public void winState() {

		}
	}
}
