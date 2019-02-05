package problem2;
import java.util.*;

/**
 * Defines a list of suit names and suit symbols.
 * Returns the suit names and suit symbols.
 * The default order set for suit ordering is: SPADES, HEARTS, CLUBS, DIAMONDS in increasing order.
 * @author meghna
 */
public class Suits implements Suit {
	private String name;
	private char symbol;

	public static final Suits SPADES = new Suits("Spades", 's');
	public static final Suits HEARTS = new Suits("Hearts", 'h');
	public static final Suits CLUBS = new Suits("Clubs", 'c');
	public static final Suits DIAMONDS = new Suits("Diamonds", 'd');
	
	/**
	 * List of suits for iterations.
	 */
	public static final List<Suits> VALUES = Collections.unmodifiableList(Arrays.asList
			(new Suits[]{SPADES, HEARTS, CLUBS, DIAMONDS}));

	
	/**
	 * Suits Constructor
	 * @param nameval
	 * @param symval
	 */
	Suits (String nameval, char symval) {
		name = nameval;
		symbol = symval;
	}
	
	
	/**
	 * @return name of this suit
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @return symbol of this suit
	 */
	public char getSymbol() {
		return symbol;
	}

	
	/**
	 * Compares between two suits and returns the highest suit value.
	 * @param oSuit
	 * @return suit with highest order value
	 */
	public int compareTo(Suits oSuit) {
		Suits nSuit = oSuit;
		return VALUES.indexOf(this) - VALUES.indexOf(nSuit);
	}

	
	/**
	 * Converts the name of the suit into a string value.
	 * @return string value of the suit name
	 */
	public String toString() {
		return name;
	}
}
