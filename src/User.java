package store;

import java.util.Scanner;

// maybe change the class to Store or have a separate Store class for something like BROWSE
public class User {
	private String username;
	private String password;
	private boolean changePW;
	//may move this v-- to the Authorizer class
	private boolean isAuth;
	private int user_id;
	public static final int PWTRIES = 2;
	Scanner input = new Scanner(System.in);
	
	// TODO create a function that opens a connection?
	// then create functions using the connection to
	// then query or update the datebase
	
	/** Maybe move this entire function to a store class
	 * Constructor - Creates a User Object
	 * @param t_name the username entered by the user
	 * @param t_password the password entered by the user
	 */
	public User (String t_name, String t_password, boolean ynAuth) {
		//check if the username is already in use separate function
		//then run database update
		username = t_name;
		changePW = true;
		password = t_password;
		//isAuth = ynAuth;
	}
	
	/**
	 * Modifier - Allows user to change their password but locks them out if they have to many wrong tries
	 * @param old_pw to hold the old password the user enters
	 * @param new_pw to hold the new password the user enters
	 */
	public void changePassword (String old_pw, String new_pw) {
		int wrongPW = 0;
		
		while (changePW) {
			if (!password.equals(old_pw) && wrongPW<PWTRIES) { //checks if password stored and password entered are the same
				wrongPW++;
				System.out.format("The password you entered did not match the one stored. Please try again.\nEnter you old password.\n");
				old_pw = input.nextLine();
			} else if (wrongPW >= PWTRIES) { //if there have been too many attempts prevents user from change password
					changePW = false;
					System.out.println("MANYTRIES\n");
			} else { 
					while (password.equals(new_pw)) { //checks if the new password is not the same as the old password
						System.out.format("New password cannot be equal to previous password stored.\nPlease enter a new password.\n");
						new_pw = input.nextLine();
					}
					password = new_pw;
					System.out.println("SUCCESS\n");
					break;
			}
		}
	}
	
	/**
	 * Accessor
	 * @return returns the the User's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Accessor - this function may not be neccessary
	 * @return returns the User's password
	 */
	private String getPassword() {
		return password;
	}
}