package edu.northeastern.ccs.cs5500.homework5;

import java.io.*;
/**
 * Reads input from console and writes output to console
 * @author meghna
 *
 */
public class Screen implements PlayerList {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static final Screen st = new Screen();


	/**
	 * Constructor
	 */
	private Screen() {

	}


	/**
	 * Reads data from the console
	 * @param inpt
	 * @return String value of the read content
	 */
	public String readContent(String inpt) {
		try {
			return br.readLine();
		} 
		catch (IOException e) {
			return inpt;
		}
	}


	/**
	 * Displays content onto the console
	 * @param content message to be displayed
	 */
	public void displayMsg(String content) {
		System.out.println(content);
	}


	/**
	 * Displays standing message
	 */
	public void standState(Player p) {
		displayMsg(p.toString() + " is standing");
	}


	/**
	 * Displays losing message
	 */
	public void lostState(Player p) {
		displayMsg(p.toString() + " has lost");
	}


	/**
	 * Displays winning message
	 */
	public void wonState(Player p) {
		displayMsg(p.toString() + " has won");
	}


	/**
	 * Displays change message
	 */
	public void changedState(Player p) {
		displayMsg(p.toString());
	}


	/**
	 * Displays blackjack message
	 */
	public void bjackState(Player p) {
		displayMsg(p.toString() + " has blackjack");
	}


	/**
	 * Displays busted message
	 */
	public void bustState(Player p) {
		displayMsg(p.toString() + " is busted");
	}


	/**
	 * Displays tie message
	 */
	public void stdState(Player p) {
		displayMsg(p.toString() + " is a tie");
	}
}
