package problem2;
import java.util.Scanner;

/**
 * Playing card games is made more flexible. The below class allows users 
 * to directly create n number of hands and distribute m number of cards.
 * Where n and m are the inputs provided by the user. The user can also
 * select which type of game should be created i.e. Euchre or Pinochle
 * or vegas blackjack. 
 * @author meghna
 */
public class PlayingGames extends Games {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please select a game:\n1: Euchre\n"
					+ "2: Pinochle\n"
					+ "3: Vegas\n");
			int op=scanner.nextInt();
			System.out.println("Please enter number of hands");
			int num1=scanner.nextInt();
			System.out.println("Please enter number of cards");
			int num2=scanner.nextInt();
			switch(op) {
			case 1 :
				selectGame("Euchre", num1, num2);
				break;
			case 2 :
				selectGame("Pinochle", num1, num2);
				break;
			case 3 :
				selectGame("Vegas", num1, num2);
				break;
			default:System.out.println("Invalid input");
			break;
			}
		} catch (Exception e) {
			System.out.println("Invalid input, please provide proper input");
			e.printStackTrace();
		}

	}

}
