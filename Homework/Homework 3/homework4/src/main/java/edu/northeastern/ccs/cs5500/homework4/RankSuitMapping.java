package edu.northeastern.ccs.cs5500.homework4;

/**
 * Maps the suit and rank values from own implementation of cards to
 * suit and rank values of kaShiXia implementation. 
 * @author meghna
 *
 */
public class RankSuitMapping {
	
	/**
	 * Maps integer suit value of kaShiXia implementation to Suits suit value
	 * of cards implementation.
	 * @param res integer value of suit
	 * @return Suits value of suit
	 */
	public static Suits getIntSuit(int res) {
		String suitName = null;
		char suitChar = 0;
		if(res == 0) {
			suitName = "Hearts";
			suitChar = 'h';
		}
		else if(res == 1) {
			suitName = "Spades";
			suitChar = 's';
		}
		else if(res == 2) {
			suitName = "Diamonds";
			suitChar = 'd';
		}
		else if(res == 3) {
			suitName = "Clubs";
			suitChar = 'c';
		}
		return new Suits(suitName,suitChar);

	}
	
	
	/**
	 * Maps integer rank value of kaShiXia implementation to Ranks rank value
	 * of cards implementation.
	 * @param res integer value of rank
	 * @return Ranks value of rank
	 */
	public static Ranks getIntRank(int res) {
		String rankName = null;
		res = res +1;
		int rankPip = 0;
		if(res == 5) {
			rankName = "Two";
			rankPip = 2;
		}
		else if(res == 9) {
			rankName = "Three";
			rankPip = 3;
		}
		else if(res == 13) {
			rankName = "Four";
			rankPip = 4;
		}
		else if(res == 4) {
			rankName = "Five";
			rankPip = 5;
		}
		else if(res == 8) {
			rankName = "Six";
			rankPip = 6;
		}
		else if(res == 12) {
			rankName = "Seven";
			rankPip = 7;
		}
		else if(res == 3) {
			rankName = "Eight";
			rankPip = 8;
		}
		else if(res == 7) {
			rankName = "Nine";
			rankPip = 9;
		}
		else if(res == 11) {
			rankName = "Ten";
			rankPip = 10;
		}
		else if(res == 2) {
			rankName = "Jack";
			rankPip = 0;
		}
		else if(res == 6) {
			rankName = "Queen";
			rankPip = 0;
		}
		else if(res == 10) {
			rankName = "King";
			rankPip = 0;
		}
		else if(res == 1) {
			rankName = "Ace";
			rankPip = 0;
		}
		return new Ranks(rankName,rankPip);
	}
	
	
	/**
	 * Maps Suits suit value of card implementation to integer suit value
	 * of kaShiXia implementation.
	 * @param mySuit Suits value of suit
	 * @return integer value of suit
	 */
	public static int getSuitVal(Suits mySuit) {
		int res = 0;
		if(mySuit.getName() == "Spades") {
			res = 1;
		}
		else if(mySuit.getName() == "Hearts") {
			res = 0;
		}
		else if(mySuit.getName() == "Clubs") {
			res = 3;
		}
		else if(mySuit.getName() == "Diamonds") {
			res = 2;
		}
		return res;
	}

	
	/**
	 * Maps Ranks rank value of card implementation to integer rank value
	 * of kaShiXia implementation.
	 * @param myRank Ranks value of rank
	 * @return integer value of rank
	 */
	public static int getRankVal(Ranks myRank) {
		int res = 0;

		if(myRank.getName() == "Two") {
			res = 5;
		}
		else if(myRank.getName() == "Three") {
			res = 9;
		}
		else if(myRank.getName() == "Four") {
			res = 13;
		}
		else if(myRank.getName() == "Five") {
			res = 4;
		}
		else if(myRank.getName() == "Six") {
			res = 8;
		}
		else if(myRank.getName() == "Seven") {
			res = 12;
		}
		else if(myRank.getName() == "Eight") {
			res = 3;
		}
		else if(myRank.getName() == "Nine") {
			res = 7;
		}
		else if(myRank.getName() == "Ten") {
			res = 11;
		}
		else if(myRank.getName() == "Jack") {
			res = 2;
		}
		else if(myRank.getName() == "Queen") {
			res = 6;
		}
		else if(myRank.getName() == "King") {
			res = 10;
		}
		else if(myRank.getName() == "Ace") {
			res = 1;
		}
		return res-1;
	}

}

