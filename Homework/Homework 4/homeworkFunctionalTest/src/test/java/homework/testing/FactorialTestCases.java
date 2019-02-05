package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test cases to calculate factorial of an integer
 * @author meghna
 *
 */
class FactorialTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();
	
	/**
	 * Calculates the factorial of 0
	 * Factorial of 0 should return 1
	 * The same is tested below 
	 */
	@Test
	public void testFactorial1() {
		int num1 = 0;
		assertEquals(1, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 1
	 * Factorial of 1 should return 1
	 * The same is tested below
	 */
	@Test
	public void testFactorial2() {
		int num1 = 1;
		assertEquals(1, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of a positive integer
	 * Factorial of a positive integer should return a positive integer
	 * The same is tested below
	 */
	@Test
	public void testFactorial3() {
		int num1 = 5;
		assertEquals(120, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of a positive integer which is a maximum value
	 * Factorial of a maximum value will round off
	 * The same is tested below
	 * 
	 */
	@Test
	public void testFactorial4() {
		int num1 = 17;
		assertEquals(-288522240, inst.factorial(num1));
	}
}
