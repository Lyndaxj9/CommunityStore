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
	 * TODO: clean up the code in this function
	 */
	public void createUser(String t_username, String t_password) {
		PreparedStatement getUsername = null;
		PreparedStatement createUser = null;
		try {
			String queryStatement = "SELECT username FROM users WHERE username=?";
			// todo: change the name of the prepared statement object
			getUsername = conn.prepareStatement(queryStatement);
			getUsername.setString(1, t_username);
			
			ResultSet usernames = getUsername.executeQuery();
			
			while(usernames.next()){
				System.out.format(">>>The username: "+usernames.getString("username") + " ,is already in use.\nPlease choose a different one.\n>>>");
				t_username = in.nextLine();
				getUsername.setString(1, t_username);
				usernames = getUsername.executeQuery();
			}
			
			String createStatement = "INSERT INTO users(username,password) VALUE(?, ?)";
			createUser = conn.prepareStatement(createStatement);
			createUser.setString(1, t_username);
			createUser.setString(2, t_password);
			
			createUser.executeUpdate();
			
		}
		catch(SQLException e){
			System.out.println(e);
		}
		finally {
			System.out.println(">>>User Account Created");
			if(getUsername != null) {
				try {
					getUsername.close();
				} catch(SQLException c) {
						System.out.println(c);
				}
			}
			if(createUser != null) {
				try {
					createUser.close();
				} catch(SQLException c) {
						System.out.println(c);
				}
			}
		}
	}
	
	/**
	 */
	public void close() {
		// TODO: create function to close connection in closing the store object
	}
}