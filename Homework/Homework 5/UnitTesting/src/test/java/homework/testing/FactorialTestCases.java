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
	 * Calculates the factorial of 2
	 * Factorial of 2 should return 2
	 * The same is tested below
	 */
	@Test
	public void testFactorial3() {
		int num1 = 2;
		assertEquals(2, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 3
	 * Factorial of 3 should return 6
	 * The same is tested below
	 */
	@Test
	public void testFactorial4() {
		int num1 = 3;
		assertEquals(6, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 4
	 * Factorial of 4 should return 24
	 * The same is tested below
	 */
	@Test
	public void testFactorial5() {
		int num1 = 4;
		assertEquals(24, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 5
	 * Factorial of 5 should return 120
	 * The same is tested below
	 */
	@Test
	public void testFactorial6() {
		int num1 = 5;
		assertEquals(120, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 6
	 * Factorial of 6 should return 720
	 * The same is tested below
	 */
	@Test
	public void testFactorial7() {
		int num1 = 6;
		assertEquals(720, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 7
	 * Factorial of 7 should return 5040
	 * The same is tested below
	 */
	@Test
	public void testFactorial8() {
		int num1 = 7;
		assertEquals(5040, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 8
	 * Factorial of 8 should return 40320
	 * The same is tested below
	 */
	@Test
	public void testFactorial9() {
		int num1 = 8;
		assertEquals(40320, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 9
	 * Factorial of 9 should return 362880
	 * The same is tested below
	 */
	@Test
	public void testFactorial10() {
		int num1 = 9;
		assertEquals(362880, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 10
	 * Factorial of 10 should return 3628800
	 * The same is tested below
	 */
	@Test
	public void testFactorial11() {
		int num1 = 10;
		assertEquals(3628800, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 11
	 * Factorial of 11 should return 39916800
	 * The same is tested below
	 */
	@Test
	public void testFactoria12() {
		int num1 = 11;
		assertEquals(39916800, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 12
	 * Factorial of 12 should return 479001600
	 * The same is tested below
	 */
	@Test
	public void testFactorial13() {
		int num1 = 12;
		assertEquals(479001600, inst.factorial(num1));
	}
	
	/**
	 * Calculates the factorial of 13
	 * Factorial of 13 should throw an exception
	 * The same is tested below
	 */
	@Test
	public void testFactorial14() {
		int num1 = 13;
		assertThrows(Exception.class, () -> {inst.factorial(num1);});
	}
	
	/**
	 * Calculates the factorial of -1
	 * Factorial of -1 should throw an exception
	 * The same is tested below
	 */
	@Test
	public void testFactorial15() {
		int num1 = -1;
		assertThrows(Exception.class, () -> {inst.factorial(num1);});
	}
}
