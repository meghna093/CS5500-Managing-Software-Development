package edu.northeastern.ccs.cs5500.homework3;

import java.util.*;

/**
 * @author meghna
 *
 */
public interface newHand extends Hand {

	/**
	 * @param cardToFind
	 * @return true if the mentioned card is present in hand else false
	 */
	public Boolean hasCard(Card cardToFind);
	

	/**
	 * 
	 * @param cardToFind
	 * @return the count of number of times the mentioned card appears in hand
	 */
	public int occurrencesInHand(Card cardToFind);

	
	/**
	 * 
	 * @param rankToFind
	 * @return the count of number of times the mentioned card rank appears in hand
	 */
	public int occurencesInHand(Rank rankToFind);
	
	
	/**
	 * Iterates over a list of cards in hand. Part of newHand implementation
	 */
	public Iterator<Cards> iterator();

}
