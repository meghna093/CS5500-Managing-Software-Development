package edu.northeastern.ccs.cs5500.homework5;
/**
 * 
 * @author meghna
 *
 */
public interface PlayerList {
	
	/**
	 * Defines the stand state of player
	 * @param p
	 */
	public void standState(Player p);
    
	
	/**
	 * Defines the lose state of player
	 * @param p
	 */
	public void lostState(Player p);
    
	
	/**
	 * Defines the win state of player
	 * @param p
	 */
	public void wonState(Player p);
    
	
	/**
	 * Defines the change state of player
	 * @param p
	 */
	public void changedState(Player p);
    
	
	/**
	 * Defines the blackjack state of player
	 * @param p
	 */
	public void bjackState(Player p);
    
	
	/**
	 * Defines the bust state of player
	 * @param p
	 */
	public void bustState(Player p);


	/**
	 * Defines the tie state of player
	 * @param p
	 */
    public void stdState(Player p);
}

