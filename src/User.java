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
		changePW = true;
		
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
	 * @param t_username user inputed username
	 * @param t_password user inputed password
	 * @return a boolean is return to let the main program know if the login was successful or not
	 */
	public boolean login(String t_username, String t_password) {
		PreparedStatement getUser = null;
		ResultSet singleUser = null;
		int loginAttempts = 0;
		//TODO: create a separate function to check if the user has been locked out of their account
		//check after the username and password have been verified in the table
		
		try {
			String queryStatement = "SELECT username, password, user_id FROM users WHERE username=? && password=?";
			getUser = conn.prepareStatement(queryStatement);
			getUser.setString(1, t_username);
			getUser.setString(2,t_password);
			
			singleUser = getUser.executeQuery();
			loginAttempts++;
			
			while(!singleUser.next() && loginAttempts<=PWTRIES) {
				System.out.println(">>>Invalid username or password\n>>>Please try again.");
				System.out.print(">>>Enter the username\n>>>");
				t_username = input.nextLine();
				System.out.print(">>>Enter the password.\n>>>");
				t_password = input.nextLine();
				
				getUser.setString(1, t_username);
				getUser.setString(2, t_password);
				singleUser = getUser.executeQuery();
				loginAttempts++;
			}
			
			//TODO: create a function that automatically changes and a column to the users table
			//the loginAttempts to equal 0
			if(loginAttempts>PWTRIES){return false;}
			
			user_id = singleUser.getInt("user_id");
			username = t_username;
			password = t_password;
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
		finally { //should close Statement and ResultSet after use
			if(getUser != null) {
				try {
					getUser.close();
				} catch(Exception c) {/*ignore*/}
			}
			if(singleUser != null) {
				try {
					singleUser.close();
				} catch(Exception c) {/*ignore*/}
			}
		}
	}
	
	/**
	 * Modifier - Allows user to change their password but locks them out if they have to many wrong tries
	 * @param old_pw to hold the old password the user enters
	 * @param new_pw to hold the new password the user enters
	 */
	public void changePassword (String old_pw, String new_pw) {
		int wrongPW = 0;
		boolean pwChanged = false;
		boolean changePW = true;

		PreparedStatement lockPW = null;
		
		while (changePW && !pwChanged) {
			if (!password.equals(old_pw) && wrongPW<PWTRIES) { //checks if password stored and password entered are the same
				wrongPW++;
				System.out.print(">>>The password you entered did not match the one stored. Please try again.\n>>>Enter you old password.\n>>>");
				old_pw = input.nextLine();
			} else if (wrongPW >= PWTRIES) { //if there have been too many attempts prevents user from change password
					changePW = false;
					
					try {
						String updateStatement = "UPDATE users SET changepw=0, timechanged=NOW() WHERE user_id=?";
						lockPW = conn.prepareStatement(updateStatement);
						lockPW.setInt(1, user_id);
						
						lockPW.executeUpdate();
					}
					catch(Exception e) {
						System.out.println(e);
					}
					finally {
						//close things
						if(lockPW != null) {
							try {
								lockPW.close();
							} catch(Exception c) {/*ignore*/}
						}
					}

					System.out.println(">>>Too many failed attempts to change password.\n>>>You have been blocked from changing your password");
			} else { 
					while (password.equals(new_pw)) { //checks if the new password is not the same as the old password
						System.out.print(">>>New password cannot be equal to previous password stored.\n>>>Please enter a new password.\n>>>");
						new_pw = input.nextLine();
					}
					
					PreparedStatement updatePW = null;
					
					try {
						String updateStatement = "UPDATE users SET password=? WHERE user_id=?";
						updatePW = conn.prepareStatement(updateStatement);
						updatePW.setString(1, new_pw);
						updatePW.setInt(2, user_id);
						
						updatePW.executeUpdate();
					}
					catch(Exception e) {
						System.out.println(e);
					}
					finally {
						password = new_pw;
						System.out.println("SUCCESS\n");
						pwChanged = true;
						
						if(updatePW != null) {
							try {
								updatePW.close();
							} catch(Exception c) {/*ignore*/}
						}					
					}
			}
		}
	}
	
	/**
	 * Accessor - Checks database if logged in user is able to change their password or if they have been blocked from doing so
	 * @return a boolean to state whether or not the user is allowed to change their password
	 */
	public boolean checkPW() {
		PreparedStatement pwState = null;
		ResultSet canChangePW = null;
		boolean canChange = true;
				
		try {
			String queryStatement = "SELECT changepw FROM users WHERE user_id = ?";
			pwState = conn.prepareStatement(queryStatement);
			pwState.setInt(1, user_id);
			
			canChangePW = pwState.executeQuery();
			canChangePW.next();
			
			if(canChangePW.getInt("changepw") == 0){
				canChange = false;
				//return false;
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			if(pwState != null) {
				try {
					pwState.close();
				} catch(Exception c) {/*ignore*/}
			}
			
			if(canChangePW != null) {
				try {
					canChangePW.close();
				} catch(Exception c) {/*ignore*/}
			}
		}
		
		return canChange;
	}
	
	
	/**
	 */
	//TODO: implement authorizeUser function that allows authorized users to promote other users to become authorizers
	
	/**
	 */
	//TODO: implement authorizeItem function that allows authorized users to authorize an item to be sold in the store
	
	/**
	 * Accessor
	 * @return returns the the User's username
	 */
	public String getUsername() {
		return username;
	}
	
	public void logout() {
		if(conn != null) {
			try {
				conn.close();
			} catch(Exception c) {/*ignore*/}
		}
		//close database connection here?
	}
}