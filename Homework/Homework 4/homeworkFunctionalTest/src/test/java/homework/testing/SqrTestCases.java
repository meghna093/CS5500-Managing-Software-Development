package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test cases for calculating square of an integer
 * @author meghna
 *
 */
class SqrTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();

	/**
	 * Calculates the square of a positive integer
	 * Square of a positive integer should return a positive integer
	 * The same is tested below
	 */
	@Test
	public void testSqr1() {
		int num1 = 4;
		assertEquals(16, inst.sqr(num1));
	}

	/**
	 * Calculates the positive edge case
	 * Square of maximum value will return 1
	 * The same is tested below
	 */
	@Test
	public void testSqr2() {
		int n = Integer.MAX_VALUE;
		assertEquals(1,inst.sqr(n));
	}
	
	/**
	 * Calculates the square of a negative integer
	 * Square of a negative integer should return a positive integer
	 * The same is tested below
	 */
	@Test
	public void testSqr3() {
		int num1 = -4;
		assertEquals(16, inst.sqr(num1));
	}

	/**
	 * Calculates the negative edge case
	 * Square of minimum value will return 0
	 * The same is tested below
	 */
	@Test
	public void testSqr4() {
		int n = Integer.MIN_VALUE;
		assertEquals(0,inst.sqr(n));
	}
	
	/**
	 * Calculates the square of 0
	 * Square of 0 should return 0
	 * The same is tested below
	 */
	@Test
	public void testSqr5() {
		int num1 = 0;
		assertEquals(0, inst.sqr(num1));
	}
	
	/**
	 * Calculates the square of 1
	 * Square of 1 should return 1
	 * The same is tested below
	 */
	@Test
	public void testSqr6() {
		int num1 = 1;
		assertEquals(1, inst.sqr(num1));
	}
}
