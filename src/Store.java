package store;

import java.util.Scanner;
import java.sql.*;

public class Store {
	//private ArrayList cart;
	Scanner in = new Scanner(System.in);
	Connection conn = null;
	String driver = "com.mysql.jdbc.Driver";
	String username = "manager";
	String password = "password";
	
	/**
	 * Constructor - Creates the store class which justs establishes a connection to the database
	 */
	public Store () {
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/store?&useSSL=false", username, password);
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
	 * Creates a user in the database using user input
	 * Checks if the username is already in use
	 * @param t_username the user inputed username
	 * @param t_password the user inputed password
	 */
	public void createUser(String t_username, String t_password) {
		PreparedStatement getUsername = null;
		PreparedStatement createUser = null;
		ResultSet usernames = null;
		try { //check if the username is in the database already
			String queryStatement = "SELECT username FROM users WHERE username=?";
			getUsername = conn.prepareStatement(queryStatement);
			getUsername.setString(1, t_username);
			
			usernames = getUsername.executeQuery();
			
			//if it is get the user to change it
			while(usernames.next()){//when there is nothing in usernames next() returns false
				System.out.format(">>>The username: "+usernames.getString("username") + " ,is already in use.\nPlease choose a different one.\n>>>");
				t_username = in.nextLine();
				getUsername.setString(1, t_username); //sets the first '?' in the string equal to the var string
				usernames = getUsername.executeQuery();
			}
			
			//when it's not add the user to the database
			String createStatement = "INSERT INTO users(username,password) VALUE(?, ?)";
			createUser = conn.prepareStatement(createStatement);
			createUser.setString(1, t_username);
			createUser.setString(2, t_password);
			
			createUser.executeUpdate();
			
		}
		catch(SQLException e){
			System.out.println(e);
		}
		finally {//close the PreparedStatement objects
			System.out.println(">>>User Account Created");
			if(getUsername != null) {
				try {
					getUsername.close();
				} catch(Exception c) {/*ignore*/}
			}
			if(createUser != null) {
				try {
					createUser.close();
				} catch(Exception c) {/*ignore*/}
			}
			if(usernames != null) {
				try {
					usernames.close();
				} catch(Exception c) {/*ignore*/}
			}
		}
	}
	
	//TODO: create item table in store database
	
	/**
	 */
	public void sellItem() {
		//TODO: implement sell function that allows users to add an to be sold in the store if authorized
	}
	
	/**
	 */
	public void browseStore() {
		
	}
	
	/**
	 */
	public void refineSearch() {
		
	}
	
	/**
	 */
	public void close() {
		// TODO: create function to close connection in closing the store object
		if(conn != null) {
			try {
				conn.close();
			} catch(Exception c) {/*ignore*/}
		}
	}
}