package edu.northeastern.ccs.cs5500.homework5;
/**
 * 
 * @author meghna
 *
 */
public interface PopulateHand {
	
	/**
	 * Defines the busted state
	 */
	public void bustedState();
    
	
	/**
	 * Defines the game state
	 */
	public void playState();
    
	
	/**
	 * Defines the changes in game state
	 */
	public void changeState();
    
	
	/**
	 * Defines the win state
	 */
	public void winState();
}
