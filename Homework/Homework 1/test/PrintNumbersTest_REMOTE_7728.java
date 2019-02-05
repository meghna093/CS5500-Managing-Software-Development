package hw1;
import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * 
 * @author meghna
 *
 */
public class PrintNumbersTest {
	private PrintNumbers test;
	/**
	 * 
	 * @throws Exception
	 */
	public void setUp() throws Exception{
		test = new PrintNumbers();
	}
	
	public void testCases() {
		int first = 1;
		int second = 3;
		
		assertFalse (test.range(first, second) .contains(-9)); //test to check if the list does not contains an integer 
		
		assertTrue (test.range(first, second).contains(2)); //test to check if the list contains an integer 
		
		List<Integer> test1 = Arrays.asList();
		assertThat (test.range(second, first),is(test1)); // test for beg>end
		
		List<Integer> test2 = Arrays.asList(-1,0,1);
		assertThat (test.range(first, second),is(test2)); // test for beg<end
		
		List<Integer> test3 = Arrays.asList(-1,1,2);
		assertNotEquals (test.range(first, second),is(test3)); // test for expected and actual result
		
		
	}
	

}
