package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test cases to calculate sum of two integers
 * @author meghna
 *
 */
class SimpleFunctionXplusYTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();

	/**
	 * Calculates the sum of 2 positive integers
	 * Sum of 2 positive integers should return a positive integer
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction1() {
		int num1 = 1;
		int num2 = 2;
		assertEquals(3, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 positive integers
	 * Here one of the positive integer is a maximum value, i.e. the 
	 * first input will be maximum value
	 * Maximum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction2() {
		int num1 = Integer.MAX_VALUE;
		int num2 = 2;
		assertEquals(-2147483647, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 positive integers
	 * Here one of the positive integer is a maximum value, i.e. the 
	 * second input will be maximum value
	 * Maximum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction3() {
		int num1 = 2;
		int num2 = Integer.MAX_VALUE;
		assertEquals(-2147483647, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 positive integers
	 * Both the inputs will be maximum value
	 * Maximum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction4() {
		int num1 = Integer.MAX_VALUE;
		int num2 = Integer.MAX_VALUE;
		assertEquals(-2, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 negative integers
	 * Sum of 2 negative integers should return a negative integer
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction5() {
		int num1 = -1;
		int num2 = -2;
		assertEquals(-3, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 negative integers
	 * Here one of the negative integer is a minimum value, i.e. the 
	 * second input will be minimum value
	 * Minimum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction6() {
		int num1 = -2;
		int num2 = Integer.MIN_VALUE;
		assertEquals(2147483646, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 negative integers
	 * Here one of the negative integer is a minimum value, i.e. the 
	 * first input will be minimum value
	 * Minimum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction7() {
		int num1 = Integer.MIN_VALUE;
		int num2 = -2;
		assertEquals(2147483646, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 2 negative integers
	 * Both the inputs will be minimum value
	 * Minimum value will round off, the same is tested below
	 */
	@Test
	public void testSimpleFunction8() {
		int num1 = Integer.MIN_VALUE;
		int num2 = Integer.MIN_VALUE;
		assertEquals(0, inst.simpleFunctionXplusY(num1, num2));
	}

	/**
	 * Calculates the sum of one positive and one negative integer
	 * When value of positive integer is greater than negative integer, 
	 * then a positive integer will be returned  
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction9() {
		int num1 = -1;
		int num2 = 2;
		assertEquals(1, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of one positive and one negative integer
	 * When value of negative integer is greater than positive integer, 
	 * then a negative integer will be returned  
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction10() {
		int num1 = 1;
		int num2 = -2;
		assertEquals(-1, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 1 and 0
	 * It should return 1
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction11() {
		int num1 = 1;
		int num2 = 0;
		assertEquals(1, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum 2 zeros
	 * It should return 0
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction12() {
		int num1 = 0;
		int num2 = 0;
		assertEquals(0, inst.simpleFunctionXplusY(num1, num2));
	}
}
