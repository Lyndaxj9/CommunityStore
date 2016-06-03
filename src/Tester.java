/*
 * CommunityStore
 *
 * @author Lynda A.
 *
 */
 
package store;

import java.util.Scanner;

public class Tester{
	public static final int DEBUG = 0;
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		String input;
		String inputUn;
		String inputPw;
		
		System.out.print(">>>Enter a Command:\n" + ">>>REGISTER | LOGIN | EXIT\n>>>");
		input = in.nextLine();
		
		Store mainStore = new Store();
		while (!input.equals("EXIT")) {
			if (input.equals("REGISTER")) {
				System.out.println(">>>You selected to REGISTER");
				System.out.println(">>>Enter a Username");
				inputUn = in.nextLine();
				System.out.println(">>>Enter a password");
				inputPw = in.nextLine();
				
				mainStore.createUser(inputUn, inputPw);
				
			} else if (input.equals("LOGIN")) {
					System.out.println(">>>You selected to LOGIN");
					System.out.print(">>>Enter Username\n>>>");
					inputUn = in.nextLine();
					System.out.print(">>>Enter Password\n>>>");
					inputPw = in.nextLine();
					boolean loginSuccess;
					
					User loggedUser = new User();
					loginSuccess = loggedUser.login(inputUn, inputPw);
					
					//while loop to handle commands carried out
					//while the user is logged in
					while (!input.equals("LOGOUT")) {
						if (input.equals("BROWSE")) {
							System.out.println("You selected to BROWSE");
							
						} else if (input.equals("SELL")) {
								System.out.println("You selected to SELL");
								
						} else if (input.equals("AUTHORIZE")) {
								System.out.println("You selected to AUTHORIZE");
								
						} else if (input.equals("CHANGEPW")) {
								String oldpw;
								String newpw;
								
								System.out.println("You selected to CHANGEPW (change your password)");
								System.out.print("Enter your old password\n>>>");
								oldpw = in.nextLine();
								System.out.print("Enter desired new password\n>>>");
								newpw = in.nextLine();
								//enter new password again?
								
								loggedUser.changePassword(oldpw, newpw);
						}
						
						if(loginSuccess) {
							System.out.println(">>>You are in your account, enter command\n>>>BROWSE | SELL | AUTHORIZE | CHANGEPW");
						  System.out.print(">>>");
							input = in.nextLine();
						} else {
								System.out.println(">>>Too many unsuccessful login attempts\n>>>Account Locked");
								input = "LOGOUT";
						}

					}
					if(input.equals("LOGOUT")){
						loggedUser.logout();
						loggedUser=null;
					}
			} else {
					System.out.println(">>>You selected an unrecognized COMMAND");
			}
			
			mainStore.close(); //close the database connection in the store object
			
			//if login false then call logout command
			System.out.println(">>>In the while loop, enter command");
			System.out.print(">>>");
			input = in.nextLine();
			
		}

		if (DEBUG != 0) {
			Item tea = new Item("Tea Sampler", 123, "A box of 5 different chai teas to try", 20, false);
			tea.printItem();
		}

	}
}