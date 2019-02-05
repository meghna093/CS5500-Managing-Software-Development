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
	
	/**
	 * Calculates the sum of 6 and 85343
	 * It should return 85349
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction13() {
		int num1 = 6;
		int num2 = 85343;
		assertEquals(85349, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 85343 and 6
	 * It should return 85349
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction14() {
		int num1 = 85343;
		int num2 = 6;
		assertEquals(85349, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 6 and -85343
	 * It should return -85337
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction15() {
		int num1 = 6;
		int num2 = -85343;
		assertEquals(-85337, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -85343 and 6
	 * It should return -85337
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction16() {
		int num1 = -85343;
		int num2 = 6;
		assertEquals(-85337, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 200 and 85143
	 * It should return 85343
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction17() {
		int num1 = 200;
		int num2 = 85143;
		assertEquals(85343, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 85143 and 200
	 * It should return 85343
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction18() {
		int num1 = 85143;
		int num2 = 200;
		assertEquals(85343, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -200 and -85143
	 * It should return -85343
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction19() {
		int num1 = -200;
		int num2 = -85143;
		assertEquals(-85343, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -85143 and -200
	 * It should return -85343
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction20() {
		int num1 = -85143;
		int num2 = -200;
		assertEquals(-85343, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of 85400 and 2000
	 * It should return 87400
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction21() {
		int num1 = 85400;
		int num2 = 2000;
		assertEquals(87400, inst.simpleFunctionXplusY(num1, num2));
	}

	/**
	 * Calculates the sum of 2000 and 85400
	 * It should return 87400
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction22() {
		int num1 = 2000;
		int num2 = 85400;
		assertEquals(87400, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -85400 and -1
	 * It should return -85401
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction23() {
		int num1 = -85400;
		int num2 = -1;
		assertEquals(-85401, inst.simpleFunctionXplusY(num1, num2));
	}

	/**
	 * Calculates the sum of -1 and -85400
	 * It should return -85401
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction24() {
		int num1 = -1;
		int num2 = -85400;
		assertEquals(-85401, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -85400 and -2000
	 * It should return -87400
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction25() {
		int num1 = -85400;
		int num2 = -2000;
		assertEquals(-87400, inst.simpleFunctionXplusY(num1, num2));
	}

	/**
	 * Calculates the sum of -2000 and -85400
	 * It should return -87400
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction26() {
		int num1 = -2000;
		int num2 = -85400;
		assertEquals(-87400, inst.simpleFunctionXplusY(num1, num2));
	}
	
	/**
	 * Calculates the sum of -1 and MIN_VALUE	
	 * It should throw an exception 
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction27() {
		int num1 = -1;
		int num2 = Integer.MIN_VALUE;
		assertThrows(Exception.class, () -> {inst.simpleFunctionXplusY(num1, num2);});
	}
	
	/**
	 * Calculates the sum of MIN_VALUE and -1	
	 * It should throw an exception 
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction28() {
		int num1 = Integer.MIN_VALUE;
		int num2 = -1;
		assertThrows(Exception.class, () -> {inst.simpleFunctionXplusY(num1, num2);});
	}
	
	/**
	 * Calculates the sum of 1 and MAX_VALUE	
	 * It should throw an exception 
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction29() {
		int num1 = 1;
		int num2 = Integer.MAX_VALUE;
		assertThrows(Exception.class, () -> {inst.simpleFunctionXplusY(num1, num2);});
	}
	
	/**
	 * Calculates the sum of MAX_VALUE and -1	
	 * It should throw an exception 
	 * The same is tested below
	 */
	@Test
	public void testSimpleFunction30() {
		int num1 = Integer.MAX_VALUE;
		int num2 = -1;
		assertThrows(Exception.class, () -> {inst.simpleFunctionXplusY(num1, num2);});
	}
}