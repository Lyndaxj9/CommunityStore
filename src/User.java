package store;

import java.util.Scanner;
import java.sql.*;

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
	//Scanner in = new Scanner(System.in);
	Connection conn = null;
	String driver = "com.mysql.jdbc.Driver";
	String dbusername = "manager";
	String dbpassword = "password";	
	
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
	
	public User() {
		username = "";
		password = "";
		changePW = false;
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/store?&useSSL=false", dbusername, dbpassword);
		}
		catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Modifier - Updates the User obj to contain the user that was entered
	 * closes connection if all tries to login sucessfully fail
	 * @param t_username
	 * @param t_password
	 */
	public boolean login(String t_username, String t_password) {
		PreparedStatement getUser = null;
		// TODO: continue writing login function
		// make sure that  the User object is updated
		try {
			String queryStatement = "SELECT username, password FROM users WHERE username=? && password=?";
			getUser = conn.prepareStatement(queryStatement);
			getUser.setString(1, t_username);
			getUser.setString(2,t_password);
			
			ResultSet singleUser = getUser.executeQuery();
			
			if(singleUser.next()) {
				System.out.println("We found you in the system");
				return true;
			}
			//while(!singleUser.next()) {
				
			//}
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
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
	
	public void logout() {
		
	}
}