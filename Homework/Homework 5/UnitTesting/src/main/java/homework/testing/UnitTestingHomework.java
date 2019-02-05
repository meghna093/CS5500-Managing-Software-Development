/**
 * 
 */
package homework.testing;

/**
 * This interface specifies a number of methods for which you need to create a sufficient test suite.
 * 
 * @author Michael Weintraub
 *
 */
public interface UnitTestingHomework {

	/**
	 * Sqrt.
	 *
	 * @param n the radicand
	 * @return the square root of the radicant
	 */
	double sqrt (int n);
	
	
	/**
	 * Sqr.
	 *
	 * @param n the factor 
	 * @return the product of the factor times itself
	 */
	int sqr (int n);
	
	/**
	 * Factorial.
	 *
	 * @param n the largest factor to consider
	 * @return n!
	 */
	int factorial (int n);
	
	 /**
 	 * Produces the sum from 0 to n.
 	 *
 	 * @param n the largest addend
 	 * @return the sum
 	 */
 	int sumUp(int n);
 	
 	/**
	  * Simple function that adds two numbers together.
	  *
	  * @param x the first addend
	  * @param y the second addend
	  * @return the sum of x and y
	  */
	 int simpleFunctionXplusY(int x, int y);
	 
	 
	 /**
 	 * Despacer.  Replace multiple contiguous spaces with a single space
 	 *
 	 * @param inputText the input text
 	 * @return the string with only single spaces in it
 	 */
 	String despacer(String inputText);
}
