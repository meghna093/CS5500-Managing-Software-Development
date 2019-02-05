package edu.northeastern.ccs.cs5500.homework5;

import java.util.List;
/**
 * Handles the communication between a player and a dealer
 * @author meghna
 *
 */
public interface Dealer {
	
	/**
	 * Rotates a card from its hidden form and displays the 
	 * value of the card.
	 * @return card that is rotated
	 */
	public Card rotateCard();
	
	
	/**
	 * Player uses this method to inform the dealer to deal 
	 * a card from the deck
	 * @param player
	 */
	public void hit(Player player);
	
	
	/**
	 * Player uses this method to inform the dealer to split 
	 * a two cards that has same value
	 * @param player
	 */
	public List<Player> split(Player player);
	
	
	/**
	 * Player uses this method to inform the dealer that the 
	 * they want to stand with the cards they have
	 * @param player
	 */
	public void stand(Player player);
	
	
	/**
	 * This method is used to communicate between the player
	 * and dealer that the game has been busted, that is either
	 * dealer or player lost the game
	 * @param player
	 */
	public void busted(Player player);
	
	
	/**
	 * This method is used to communicate between the player
	 * and dealer that the game is won, that is either dealer 
	 * or player won the game
	 * @param player
	 */
	public void blackjack(Player player);
	
	
	/**
	 * This method is used to communicate between the player
	 * and dealer to place an amount of bet on the game
	 * @param player
	 */
	public void placeBetting(Player player);
}

