package hw1;
import java.util.*;

/**
 * 
 * @author meghna
 *
 */

/*
 * Problem Statement: Given 2 numbers, 
 * print all the numbers that exist between the given 2 numbers, 
 * excluding the given number
 */

public class PrintNumbers implements NumberSeries {
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		int n1,n2;
		PrintNumbers num = new PrintNumbers();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the first number:");
		n1=input.nextInt();
		System.out.println("Enter the second number:");
		n2=input.nextInt();
		List<Integer> newList = new ArrayList<>(); //List that replicates num
		newList = num.range(n1, n2);
		System.out.println(newList);
	}
	
	public List<Integer> range(int beg,int end){
		List<Integer> in = new ArrayList<>(); //Stores integer number
		for(int i=beg+1;i<end;i++) {
			if(i%2!=0) { // changes to print odd numbers
				in.add(i);
			}
		}
		return in;
	}
	

}
