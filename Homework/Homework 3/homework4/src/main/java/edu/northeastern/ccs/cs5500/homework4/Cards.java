package edu.northeastern.ccs.cs5500.homework4;

import java.util.*;

/**
 * Creation of a single card that will be present in a deck of cards.
 * @author meghna
 */
public class Cards implements Card, Comparable{
	private static boolean sortBoth = true;
	HashMap<String, Character> suitMap = new HashMap<String, Character>();
	HashMap<String, Integer> rankMap = new HashMap<String, Integer>();
	Suits suitval;
	Ranks rankval;
	kaShiXia k;
	
	
	/**
	 * Card constructor
	 * @param rank states the rank of this card
	 * @param suit states the suit of this card
	 */
	public Cards(Ranks rank, Suits suit) {
		suitval = suit;
		rankval = rank;
		k = new kaShiXia(RankSuitMapping.getRankVal(rank), RankSuitMapping.getSuitVal(suit));
	}


	/**
	 * @return the suit of this card
	 */
	public Suits getSuit() {
		return RankSuitMapping.getIntSuit(k.getSuit());
	}

	
	/**
	 * @return rank of this card
	 */
	public Ranks getRank() {
		return RankSuitMapping.getIntRank(k.getRank());
	}


	/**
	 * @return string value of this card
	 */
	public String toString() {
		return rankval.toString() + " of " + suitval.toString();
	}


	/**
	 * Compares between cards and sorts them. Sorting is done first by suit
	 * and then by rank.
	 * @param newCardObj
	 * @return integer indicating whether the card is greater or lesser or equal
	 */
	public int compareTo( Object newCardObj ) {
		Cards newCard = (Cards) newCardObj;
		int newRank = rankval.compareTo(newCard.rankval);
		int newSuit = suitval.compareTo(newCard.suitval);
		if (sortBoth) {
			if (newRank != 0)
				return newRank;
			else
				return newSuit;
		}
		else {
			if (newSuit != 0)
				return newSuit;
			else
				return newRank;
		}
	}	
}


