package homework.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * Test cases to replace multiple contiguous spaces in a text string with a single space
 * @author meghna
 *
 */
class DespacerTestCases {
	UnitTestingHomeworkImpl inst = new UnitTestingHomeworkImpl();

	/**
	 * Should replace all the heading, trailing and in between multiple contiguous spaces
	 * with a single space 
	 * The same is tested below
	 */
	@Test
	public void testDespacer1() {
		String s = "     Hello      World    ";
		assertEquals("Hello World", inst.despacer(s));
	}
	
	/**
	 * Should replace all the trailing and in between multiple contiguous spaces
	 * with a single space 
	 * The same is tested below
	 */
	@Test
	public void testDespacer2() {
		String s = "Hello      World    ";
		assertEquals("Hello World", inst.despacer(s));
	}
	
	/**
	 * Should replace all the heading and in between multiple contiguous spaces
	 * with a single space 
	 * The same is tested below
	 */
	@Test
	public void testDespacer3() {
		String s = "     Hello      World";
		assertEquals("Hello World", inst.despacer(s));
	}
	
	/**
	 * Should return a string as is if there are no multiple contiguous spaces
	 * The same is tested below
	 */
	@Test
	public void testDespacer4() {
		String s = "Hello World";
		assertEquals("Hello World", inst.despacer(s));
	}
	
	/**
	 * An empty string with no multiple contiguous spaces should be returned as
	 * an empty string with one space.
	 * The same is tested below
	 */
	@Test
	public void testDespacer5() {
		String s = "      ";
		assertEquals(" ", inst.despacer(s));
	}
}
