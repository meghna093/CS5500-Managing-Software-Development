package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Tests cases for calculating square root of an integer
 * @author meghna
 *
 */
class SqrtTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();

	/**
	 * Calculates the square root of an even integer 
	 * Square root of an even integer should return a even double number
	 * The same is tested below
	 */
	@Test
	public void testSqrt1() {
		int num1 = 4;
		assertEquals(2.0, inst.sqrt(num1));
	}

	/**
	 * Calculates the square root of an odd integer 
	 * Square root of an odd integer should return a double number
	 * The same is tested below
	 */
	@Test
	public void testSqrt2() {
		int num1 = 3;
		assertEquals(1.73, inst.sqrt(num1));
	}

	/**
	 * Calculates the square root of negative number
	 * Since square root of a negative number returns
	 * an imaginary number but the method is expected
	 * to return a double value, we convert the 
	 * square root of a negative number into a long value
	 * which will return 0.0.
	 * The same is tested below
	 */
	@Test
	public void testSqrt3() {
		int num1 = -4;
		assertEquals(0.0, inst.sqrt(num1));		
	}
	
	/**
	 * Calculates the square root of 0 
	 * Square root of 0 should return 0.0
	 * The same is tested below
	 */
	@Test
	public void testSqrt4() {
		int num1 = 0;
		assertEquals(0.0, inst.sqrt(num1));
	}
	
	/**
	 * Calculates the square root of 1 
	 * Square root of 1 should return 1.0
	 * The same is tested below
	 */
	@Test
	public void testSqrt5() {
		int num1 = 1;
		assertEquals(1.0, inst.sqrt(num1));
	}
}
