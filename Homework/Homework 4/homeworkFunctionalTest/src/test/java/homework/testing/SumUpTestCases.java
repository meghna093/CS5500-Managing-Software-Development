package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test cases to calculate sum of first n integers
 * @author meghna
 *
 */
class SumUpTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();
	
	/**
	 * Calculates sum from 0 to 8
	 * Sum of 0 to 8 should return 36
	 * The same is tested below
	 */
	@Test
	public void testSumUp1() {
		int num1 = 8;
		assertEquals(36, inst.sumUp(num1));
	}
	
	
	/**
	 * Calculates sum from 0 to 1
	 * Sum of 0 to 1 should return 1
	 * The same is tested below
	 */
	@Test
	public void testSumUp2() {
		int num1 = 1;
		assertEquals(1, inst.sumUp(num1));
	}
	
	/**
	 * Calculates sum from 0 to 0
	 * Sum of 0 to 0 should return 0
	 * The same is tested below 
	 */
	@Test
	public void testSumUp3() {
		int num1 = 0;
		assertEquals(0, inst.sumUp(num1));
	}
	
	/**
	 * Calculates sum from 0 to 9000000
	 * Sum of 0 to 9000000 will exceed the maximum limit 
	 * hence will be rounded off
	 * The same is tested below 
	 */
	@Test
	public void testSumUp4() {
		int num1 = 9000000;
		assertEquals(-1537101280, inst.sumUp(num1));
	}
}
