package edu.northeastern.ccs.cs5500.homework4;

/**
 * Interface Specification for playing card hands
 * 
 * @author yì xiē rén
 *
 */
public interface Shou {

	/**
	 * Accept. Adds a card to the shou
	 *
	 * @param renHeKa a card to be placed into a shou
	 */
	void accept(Ka renHeKa);

	/**
	 * Returns a ka from the shou.
	 *
	 * @return the card
	 */
	Ka pullKa();

	/**
	 * Returns a specific ka from the shou, if it is present in the shou.
	 *
	 * @return the card
	 */
	Ka pullKa(Ka renHeKa);

	/**
	 * Checks whether a given ka is present in the shou.
	 *
	 * @param anyCard
	 * @return true, if successful
	 */
	Boolean hasKa(Ka renHeKa);

	/**
	 * Sorts the kas in the shou by rank.
	 *
	 */
	void sort();

}

