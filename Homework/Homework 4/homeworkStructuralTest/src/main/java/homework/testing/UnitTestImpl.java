package homework.testing;

public class UnitTestImpl {

	public int example(String inputText) {
		/**
		 * counts the number of multiple contiguous space substrings
		 * in a given string.
		 **
		 * @param String - the string to process
		 * @return how many 
		 */
		
		boolean foundSpace = false;
		boolean multiple = false;
		int result = 0;

		for (int i = 0; i < inputText.length(); i++) {

			if (inputText.charAt(i) == ' ') {
				if (foundSpace) {
					multiple = true;
				}
				foundSpace = true;
			} else {
				if (foundSpace && multiple) {
					result++;
				}
				foundSpace = false;
				multiple = false;
			}
		}

		return result;

	}

}
