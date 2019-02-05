package edu.northeastern.ccs.cs5500.homework5;
/**
 * Handles the interaction between player and bet factor
 * @author meghna
 */
public abstract class PlaceBetPlayer extends Player {
	
	private BetFactor money;

	/**
	 * Constructor
	 * @param name name of the player
	 * @param h hand of the player
	 * @param money the total amount they own
	 */
	public PlaceBetPlayer(String name, Hand h, BetFactor money) {
		super(name, h);
		this.money = money;
	}


	/**
	 * @return String representation of player and the money they own
	 */
	public String getName() {
		return (super.getName() + " " + money.toString());
	}


	/**
	 * Fetches the bet placed by the players
	 */
	protected final BetFactor getBet() {
		return money;
	}


	/**
	 * Gets the new state of game when player is asked
	 * to place the bet
	 */
	protected PlayerState getPlayState() {
		return new PlayBet();
	}


	/**
	 * Gets the initial state
	 */
	protected PlayerState getInitialState() {
		return getBetState();
	}


	protected abstract void playerBet();


	/**
	 * Gets the bet state
	 */
	protected PlayerState getBetState() {
		return new NewBet();
	}


	/**
	 * Handles blackjack state of the game
	 */
	public void blackjack() {
		money.ifWin();
		super.blackjack();
	}


	/**
	 * Handles stand state of the game
	 */
	public void ifStand() {
		money.ifStand();
		super.ifStand();
	}


	/**
	 * Handles lose state of the game
	 */
	public void ifLose() {
		money.ifLose();
		super.ifLose();
	}


	/**
	 * Handles win state of the game
	 */
	public void ifWin() {
		money.ifWin();
		super.ifWin();
	}


	/**
	 * String representation of player and their money
	 */
	public String toString() {
		return (super.getName() + ": " + getHand().toString() + "\n" + money.toString());
	}


	/**
	 * Sets various states of game such as hit, stand, bust
	 */
	private class PlayBet implements PlayerState {
		public void initiate(Dealer d) {
			if(playerHit(d)) {
				d.hit(PlaceBetPlayer.this);
			} 
			else {
				setCurrentState(getStandState());
				isStanding();
			}
			getCurrentState().initiate(d);
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
	 * Player places bet to start the game
	 */
	private class NewBet implements PlayerState {
		public void initiate(Dealer d) {
			playerBet();
			setCurrentState(getWaitState());
			d.placeBetting(PlaceBetPlayer.this);
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
