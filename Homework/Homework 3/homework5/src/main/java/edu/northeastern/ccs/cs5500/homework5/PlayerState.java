package edu.northeastern.ccs.cs5500.homework5;
/**
 * 
 * @author meghna
 *
 */
public interface PlayerState extends PopulateHand {

	/**
	 * Call to start the game by dealing cards
	 * @param d
	 */
    public void initiate(Dealer d);

}
