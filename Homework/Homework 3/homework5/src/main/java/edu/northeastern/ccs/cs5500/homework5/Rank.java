package edu.northeastern.ccs.cs5500.homework5;

import java.util.*;

/**
 * Defines a list of rank names and rank symbols.
 * Returns the rank names and rank symbols.
 * Order of the ranks will be: 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace in increasing order.
 * @author meghna
 *
 */

public class Rank {

	private final String name;
	private final int pip;
    
    public static final Rank TWO   = new Rank("Two", 2);
    public static final Rank THREE = new Rank("Three", 3);
    public static final Rank FOUR  = new Rank("Four", 4);
    public static final Rank FIVE  = new Rank("Five", 5);
    public static final Rank SIX   = new Rank("Six", 6);
    public static final Rank SEVEN = new Rank("Seven", 7);
    public static final Rank EIGHT = new Rank("Eight", 8);
    public static final Rank NINE  = new Rank("Nine", 9);
    public static final Rank TEN   = new Rank("Ten", 10);
    public static final Rank JACK  = new Rank("Jack", 10);
    public static final Rank QUEEN = new Rank("Queen", 10);
    public static final Rank KING  = new Rank("King", 10);
    public static final Rank ACE   = new Rank("Ace", 11);

    public static final List<Rank> RANKS  = Collections.unmodifiableList(
			Arrays.asList( new Rank[] { TWO, TWO, TWO, TWO, TWO, TWO, THREE, THREE, THREE, THREE, THREE, THREE, 
					FOUR, FOUR, FOUR, FOUR, FOUR, FOUR, FIVE, FIVE, FIVE, FIVE, FIVE, FIVE, SIX, SIX, SIX, SIX, 
					SIX, SIX, SEVEN, SEVEN, SEVEN, SEVEN, SEVEN, SEVEN, EIGHT, EIGHT, EIGHT, EIGHT, EIGHT, EIGHT, 
					NINE, NINE, NINE, NINE, NINE, NINE, TEN, TEN, TEN, TEN, TEN, TEN, JACK, JACK, JACK, JACK, JACK, 
					JACK, QUEEN, QUEEN, QUEEN, QUEEN, QUEEN, QUEEN, KING, KING, KING, KING, KING, KING, ACE, ACE, 
					ACE, ACE, ACE, ACE}));

    /**
	 * Rank constructor 
	 * @param name
	 * @param pip
	 */
    Rank(String name, int pip) {
        this.pip = pip;
        this.name = name;
    }

    /**
	 * @return the symbol of this rank.
	 */
    public int getRank() {
        return pip;
    }

    /**
	 * @return the name of this rank.
	 */
    public String toString() {
        return name;
    }
    
    
    /**
	 * Compares two different objects for their equality. Returns true if rank 
	 * of the objects are equal, else returns false.
	 */
    @Override
	public boolean equals(Object o)
	{
		if(o instanceof Rank)
		{
			Rank r = (Rank) o;
			return (r.getRank() == this.getRank());
		
		}
		return false;
	}
}
