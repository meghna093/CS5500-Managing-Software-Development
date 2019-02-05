package edu.northeastern.ccs.cs5500.homework5;
/**
 * Handles the multiple deals dealt by dealer to multiple players
 * @author meghna
 *
 */
public class MultipleDeal extends MainDealer {

	/**
	 * Constructor
	 * @param name Name of the dealer
	 * @param h Hand of the dealer
	 * @param c Deck of cards
	 */
	public MultipleDeal(String name, Hand h, CardDecks c) {
		super(name, h, c);
	}


	/**
	 * Gets the bet state of the game
	 */
	protected PlayerState getBetsState() {
		return new DBetting();
	}


	/**
	 * Gets the wait state of the game
	 */
	protected PlayerState getWaitState() {
		return new DWaiting();
	}


	/**
	 * Gets each player in waiting and begins the same
	 */
	private class DWaiting implements PlayerState {
		public void initiate(final Dealer d) {
			if(!playerWaiting.isEmpty()) {
				final Player p = (Player) playerWaiting.get(0);
				playerWaiting.remove(p);
				Runnable rnnbl = new Runnable() {
					public void run() {
						p.startGame(d);
					}
				};
				Thread trd = new Thread(rnnbl);
				trd.start();
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
	 * Each player places the bet and proceeds further with the game
	 */
	private class DBetting implements PlayerState {
		public void initiate(final Dealer d) {
			if(!playerBet.isEmpty()) {
				final Player p = (Player) playerBet.get(0);
				playerBet.remove(p);
				Runnable rnnbl = new Runnable() {
					public void run() {
						p.startGame(d);
					}
				};
				Thread trd = new Thread(rnnbl);
				trd.start();
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
}