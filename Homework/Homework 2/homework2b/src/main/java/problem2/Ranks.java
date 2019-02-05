package problem2;
import java.util.*;
/**
 * Defines a list of rank names and rank symbols.
 * Returns the rank names and rank symbols.
 * The user has the flexibility of setting Ace or King as the highest rank. I have set Ace as the highest rank card.
 * So the order of the ranks will be: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace in increasing order.
 * @author meghna
 *
 */
public class Ranks implements Rank {
	private static boolean firstAce = true;
	private String name;
	private int pips;

	public static final Ranks ACE = new Ranks("Ace", 0);
	public static final Ranks TWO = new Ranks("Two", 2);
	public static final Ranks THREE = new Ranks("Three", 3);
	public static final Ranks FOUR = new Ranks("Four", 4);
	public static final Ranks FIVE = new Ranks("Five", 5);
	public static final Ranks SIX = new Ranks("Six", 6);
	public static final Ranks SEVEN = new Ranks("Seven", 7);
	public static final Ranks EIGHT = new Ranks("Eight", 8);
	public static final Ranks NINE = new Ranks("Nine", 9);
	public static final Ranks TEN = new Ranks("Ten", 10);
	public static final Ranks JACK = new Ranks("Jack", 0);
	public static final Ranks QUEEN = new Ranks("Queen", 0);
	public static final Ranks KING = new Ranks("King", 0);

	private static final List<Ranks> KINGVAL = Collections.unmodifiableList(
			Arrays.asList(new Ranks[] {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}));

	private static final List<Ranks> ACEVAL  = Collections.unmodifiableList(
			Arrays.asList( new Ranks[] { TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}));
	
	private static final List<Ranks> EUCHREVAL  = Collections.unmodifiableList(
			Arrays.asList( new Ranks[] { NINE, TEN, JACK, QUEEN, KING, ACE}));
	
	private static final List<Ranks> PINOCHLEVAL  = Collections.unmodifiableList(
			Arrays.asList( new Ranks[] { NINE, NINE, TEN, TEN, JACK, JACK, QUEEN, QUEEN, KING, KING, ACE, ACE}));

	/**
	 * List of ranks for standard deck, euchre and pinochle games. Mainly used for iterations.
	 */
	public static final List<Ranks> EUCHRE_VALUES = Collections.unmodifiableList(EUCHREVAL);
	public static final List<Ranks> PINOCHLE_VALUES = Collections.unmodifiableList(PINOCHLEVAL);
	public static final List<Ranks> VALUES = Collections.unmodifiableList(ACEVAL);

	
	/**
	 * Ranks constructor 
	 * @param nameval
	 * @param pipsval
	 */
	Ranks(String nameval, int pipsval) {
		name = nameval;
		pips = pipsval;
	}

	/**
	 * @return the name of this rank.
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @return the symbol of this rank.
	 */
	public int getPips() {
		return pips;
	}
	
	
	/**
	 * Compares between two ranks and returns the highest rank. Here Ace has the highest rank.
	 * If King is set to highest, then King will be the highest rank.
	 * @param oRank
	 * @return card with the highest rank
	 */
	public int compareTo(Ranks oRank) {
		Ranks nRank = oRank;
		if(firstAce) {
			return ACEVAL.indexOf(this) - ACEVAL.indexOf(nRank);
		}
		else {
			return KINGVAL.indexOf(this) - KINGVAL.indexOf(nRank);
		}
	}

	
	/**
	 * Converts the name of the rank into a string value.
	 * @return string value of the rank name
	 */
	public String toString() {
		return name;
	}
	
	
	/**
	 * Sets Ace to be the highest rank, can be changed by users.
	 * @return true indicating ace as highest.
	 */
	public static void aceSet() {
		firstAce = true;
	}

	
	/**
	 * Sets King as the next highest to ace, can be changed by users if they want to set King as highest rank.
	 * @return false indicating ace as highest.
	 */
	public static void kingSet() {
		firstAce = false;
	}
}
