package homework.testing;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Test cases to count the number of multiple contiguous space substrings
 * in a given string.
 * @author meghna
 *
 */
public class StructuralTesting extends TestCase {
	UnitTestImpl inst = new UnitTestImpl();
	
	/**
	 * If there are no multiple contiguous space substrings in a given string, 
	 * 0 should be returned
	 * The same is tested below
	 */
	@Test
	public void test1() {
		String s1 = "Hello World";
		assertEquals(0, inst.example(s1));
	}

	/**
	 * Should count all the heading, trailing and in between multiple contiguous spaces
	 * In the below input string the count should be 3 since there are multiple 
	 * contiguous spaces in three places, but the count is returned as 2 which is not
	 * correct
	 * The same is tested below
	 */
	@Test
	public void test2() {
		String s1 = "    Hello       World      ";
		assertNotSame(3, inst.example(s1));
	}

	/**
	 * If there are no multiple contiguous spaces in the given string it should
	 * return 0
	 * The same is tested below
	 */
	@Test
	public void test3() {
		String s1 = "HelloWorld";
		assertEquals(0, inst.example(s1));
	}
	
	/**
	 * Should count the heading multiple contiguous spaces
	 * It should return 1 for the below input string 
	 * The same is tested below
	 */
	@Test
	public void test4() {
		String s1 = "    Hello World";
		assertEquals(1, inst.example(s1));
	}
	
	/**
	 * Should count the trailing multiple contiguous spaces
	 * In the below input string the count should be 1 since there are multiple 
	 * contiguous spaces at the end, but the count is returned as 0 which is not
	 * correct
	 * The same is tested below
	 */
	@Test
	public void test5() {
		String s1 = "Hello World      ";
		assertNotSame(1, inst.example(s1));
	}
	
	
	/**
	 * Should count all the heading, trailing and in between multiple contiguous spaces
	 * In the below input string the count should be 1 since it has multiple 
	 * contiguous spaces, but the count is returned as 0 which is not
	 * correct
	 * The same is tested below
	 */
	@Test
	public void test6() {
		String s1 = "      ";
		assertNotSame(1, inst.example(s1));
	}
}
