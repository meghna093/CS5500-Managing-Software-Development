package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;

/**
 * Defines a list of suit names and suit symbols.
 * Returns the suit names and suit symbols.
 * The default order set for suit ordering is: SPADES, HEARTS, CLUBS, DIAMONDS in increasing order.
 * @author meghna
 */
public class Suit {
	public static final Suit SPADES = new Suit("Spades");
	public static final Suit CLUBS = new Suit("Clubs"); 
    public static final Suit HEARTS = new Suit("Hearts");
    public static final Suit DIAMONDS = new Suit("Diamonds");
    private final String name;
    
    /**
	 * List of suit for iterations.
	 */
    public static final List<Suit> SUITS = Collections.unmodifiableList(Arrays.asList
			(new Suit[]{ SPADES, HEARTS, CLUBS, DIAMONDS}));

    
    /**
	 * Suit Constructor
	 * @param name
	 */
    Suit(String name) {
        this.name = name;
    }

    
    /**
	 * @return name of this suit
	 */
    public String getName() {
        return name;
    }
    
    
    /**
	 * Converts the name of the suit into a string value.
	 * @return string value of the suit name
	 */
    public String toString() {
        return String.valueOf( name );
    }
    
    
    /**
	 * Compares two different objects for their equality. Returns true if suit of
	 * the objects are equal, else returns false.
	 */
    public boolean equalsSuit(Object o)
	{
		if(o instanceof Suit)
		{
			Suit r = (Suit) o;
			return (r.getName() == this.getName());
		
		}
		return false;
	}
}
