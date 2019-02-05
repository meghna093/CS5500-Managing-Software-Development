package edu.northeastern.ccs.im.chatter;

import java.util.Scanner;

import edu.northeastern.ccs.im.IMConnection;
import edu.northeastern.ccs.im.KeyboardScanner;
import edu.northeastern.ccs.im.Message;
import edu.northeastern.ccs.im.MessageScanner;

/**
 * Class which can be used as a command-line IM client.
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0
 * International License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/4.0/. It is based on work
 * originally written by Matthew Hertz and has been adapted for use in a class
 * assignment at Northeastern University.
 *
 * @version 1.3
 */
public class CommandLineMain {

  /**
   * This main method will perform all of the necessary actions for this phase of
   * the course project.
   *
   * @param args Command-line arguments which we ignore
   */
  public static void main(String[] args) {
    IMConnection connect;
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);

    System.out.println("Welcome to Chatter.");
    while (true) {
      System.out.println("Would you like to login as an existing user, or register as a new user? (R|L)");
      String input = in.nextLine();
      if (input.toUpperCase().equals("R")) {
        System.out.print("Please enter the username you would like to use: ");
        String username = in.nextLine();
        System.out.print("Please enter the password you would like to use: ");
        String password = in.nextLine();
        connect = new IMConnection(args[0], Integer.parseInt(args[1]), username, password);
        if (connect.register()) {
          System.out.println("Registration successful");
          break;
        } else {
          System.out.println("Sorry, couldn't register a new user with those credentials. Please try again.");
        }
      } else if (input.toUpperCase().equals("L")) {
        System.out.print("Please enter your username: ");
        String username = in.nextLine();
        System.out.print("Please enter your password: ");
        String password = in.nextLine();
        connect = new IMConnection(args[0], Integer.parseInt(args[1]), username, password);
        if (connect.login()) {
          System.out.println("Login successful");
          break;
        } else {
          System.out.println("Sorry, couldn't login with those credentials. Please try again.");
        }
      }
    }

    // Create the objects needed to read & write IM messages.
    KeyboardScanner scan = connect.getKeyboardScanner();
    MessageScanner mess = connect.getMessageScanner();

    boolean printed = false;
    // Repeat the following loop
    while (connect.connectionActive()) {
      if (!printed) {
        System.out.print(connect.getGroupName() + ": ");
        printed = true;
      }
      // Check if the user has typed in a line of text to broadcast to the IM server.
      // If there is a line of text to be
      // broadcast:
      if (scan.hasNext()) {
        // Read in the text they typed
        String line = scan.nextLine();

        // If the line equals "/logout", close the connection to the IM server.
        if (line.equalsIgnoreCase("/logout")) {
          connect.disconnect();
          break;
        } else if (line.equalsIgnoreCase("/help")) {
          System.out.println("Commands: ");
          System.out.println("/delete user");
          System.out.println("/update user");
          System.out.println("/search user");
          System.out.println("/add user");
          System.out.println("/remove user");
          System.out.println("/join group");
          System.out.println("/create group");
          System.out.println("/update group");
          System.out.println("/delete group");
          System.out.println("/leave group");
        } else if (line.equalsIgnoreCase("/delete user")) {
          connect.deleteUser();
          break;
        } else if (line.equalsIgnoreCase("/update user")) {
          System.out.println("Please enter new username.");
          while (!scan.hasNext()) {
          }
          String username = scan.nextLine();
          if (connect.updateUser(username)) {
            System.out.println("Username updated successful");
          } else {
            System.out.println("Sorry, couldn't updateUser username with that username. Please " +
                    "try again.");
          }
        } else if (line.equalsIgnoreCase("/search user")) {
          System.out.println("Please enter username to see if it exists.");
          while (!scan.hasNext()) ;
          String username = scan.nextLine();
          if (connect.findUser(username)) {
            System.out.println("Username exists.");
          } else {
            System.out.println("Username does not exist.");
          }
        } else if (line.equalsIgnoreCase("/join group")) {
          System.out.println("Please enter the name of the group you'd like to enter");
          while (!scan.hasNext()) ;
          String groupname = scan.nextLine();
          if (connect.connectToGroup(groupname)) {
            System.out.println("Successfully joined group " + groupname);
          } else {
            System.out.println("Could not join group. Please check that the group exists, " +
                    "and that you have permission to enter the group.");
          }
        } else if (line.equalsIgnoreCase("/create group")) {
          System.out.println("Please enter the name of the group you'd like to create");
          while (!scan.hasNext()) ;
          String groupname = scan.nextLine();
          if (connect.createGroup(groupname)) {
            System.out.println("Successfully created group " + groupname);
          } else {
            System.out.println("Could not create group. Please check that the group does not already exist.");
          }
        } else if (line.equalsIgnoreCase("/add user")) {
          if (connect.getGroupName() != null) {
            System.out.println("Please enter the name of the user you'd like to add to the current group.");
            while (!scan.hasNext()) ;
            String username = scan.nextLine();
            if (connect.addUser(username)) {
              System.out.println("Successfully added user to current group.");
            } else {
              System.out.println("Could not add user to group.");
            }
          } else {
            System.out.println("You must be in a group to add a user to the group.");
          }
        } else if (line.equalsIgnoreCase("/update group")) {
          if (connect.getGroupName() != null) {
            System.out.println("Please enter new groupname.");
            while (!scan.hasNext()) ;
            String groupname = scan.nextLine();
            if (connect.updateGroup(groupname)) {
              System.out.println("Groupname updated successful.");
            } else {
              System.out.println("Sorry, couldn't update with that groupname. \" +\n" +
                      "                    \"Please try again");
            }
          } else {
            System.out.println("You must be in a group to update a groupname.");
          }
        } else if (line.equalsIgnoreCase("/delete group")) {
          if (connect.getGroupName() != null) {
            if (connect.deleteGroup()) {
              System.out.println("Group updated successful.");
            } else {
              System.out.println("Sorry, couldn't delete group. Please try again.");
            }
          } else {
            System.out.println("You must be in a group to delete a group.");
          }
        } else if (line.equalsIgnoreCase("/remove user")) {
          if (connect.getGroupName() != null) {
            System.out.println("Please enter the name of the user you'd like to remove from the " +
                    "current group.");
            while (!scan.hasNext()) ;
            String username = scan.nextLine();
            if (connect.removeUser(username)) {
              System.out.println("Successfully removed user from the current group.");
            } else {
              System.out.println("Could not remove user from the group.");
            }
          } else {
            System.out.println("You must be in a group to remove a user from the group.");
          }
        } else if (line.equalsIgnoreCase("/leave group")) {
          if (connect.getGroupName() != null) {
            if (connect.leaveGroup()) {
              System.out.println("Successfully left the group.");
            } else {
              System.out.println("Could not leave the group.");
            }
          } else {
            System.out.println("You must be in a group to leave.");
          }
        } else if (line.equalsIgnoreCase("/wiretap")) {
        	if (!"admin".equals(connect.getUserName())) {
        		System.out.println("You must be logged in as an admin in order to " + 
        				"set up a wiretap");
        	} else {
        		while (true) {
	        		System.out.println("Please enter whether you would like to track a user or a group.");
	        		while (!scan.hasNext());
	        		String choice = scan.nextLine();
	        		
	        		if (choice.equals("group")) {
		        		System.out.println("Please enter the name of the group you'd like to track.");
		        		while (!scan.hasNext());
		        		String groupName = scan.nextLine();
		        		System.out.println("Please enter the duration of the wiretap in minutes");
		        		while (!scan.hasNext());
		        		int duration = Integer.parseInt(scan.nextLine());
		        		String username = null;
		        		while (username == null) {
			        		System.out.println("Please enter the username the agency will use to access the message logs");
			        		while (!scan.hasNext());
			        		username = scan.nextLine();
			        		if (connect.findUser(username)) {
			        			System.out.println("Sorry, that username is already in use. Try another username.");
			        			username = null;
			        		}
		        		}
		        		System.out.println("Please enter the password the agency will use to login");
		        		while (!scan.hasNext());
		        		String password = scan.nextLine();
		        		IMConnection agency = new IMConnection(args[0], Integer.parseInt(args[1]), username, password);
		        		agency.register();
		        		String agencyGroupName = String.valueOf(username.hashCode());
		        		agency.createGroup(agencyGroupName);
		        		if (agency.addListenerToGroup(groupName, agencyGroupName, duration)) {
		        			System.out.println("successfully created wiretap");
		        		} else {
		        			System.out.println("failed to create wiretap, please try again.");
		        		}
		        		break;
	        		} else if (choice.equals("user")) {
		        		System.out.println("Please enter the name of the user you'd like to track.");
		        		while (!scan.hasNext());
		        		String trackedUser = scan.nextLine();
		        		System.out.println("Please enter the duration of the wiretap in minutes");
		        		while (!scan.hasNext());
		        		int duration = Integer.parseInt(scan.nextLine());
		        		String username = null;
		        		while (username == null) {
			        		System.out.println("Please enter the username the agency will use to access the message logs");
			        		while (!scan.hasNext());
			        		username = scan.nextLine();
			        		if (connect.findUser(username)) {
			        			System.out.println("Sorry, that username is already in use. Try another username.");
			        			username = null;
			        		}
		        		}
		        		System.out.println("Please enter the password the agency will use to login");
		        		while (!scan.hasNext());
		        		String password = scan.nextLine();
		        		IMConnection agency = new IMConnection(args[0], Integer.parseInt(args[1]), username, password);
		        		agency.register();
		        		String agencyGroupName = String.valueOf(username.hashCode());
		        		agency.createGroup(agencyGroupName);
		        		if (agency.addListenerToUser(trackedUser, agencyGroupName, duration)) {
		        			System.out.println("successfully created wiretap");
		        		} else {
		        			System.out.println("failed to create wiretap, please try again.");
		        		}
		        		break;
	        		}
        		}
        	}
        } else {
          // Else, send the text so that it is broadcast to all users in the group
          // server.
          try {
            connect.sendMessage(line);
          } catch (IllegalArgumentException e) {
            System.out.println("Join a group or send a private message to a valid user.");
          }
        }
        printed = false;
      }
      // Get any recent messages received from the IM server.
      if (mess.hasNext()) {
        Message message = mess.next();
        if (!message.getSender().equals(connect.getUserName())) {
          System.out.println(message.getSender() + ": " + message.getText());
          printed = false;
        }
      }
    }
    System.out.println("Successfully logged out.");
    System.exit(0);
  }
}
