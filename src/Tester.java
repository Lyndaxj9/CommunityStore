package store;

import java.util.Scanner;

public class Tester{
	public static final int DEBUG = 0;
	
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		String input;
		
		System.out.println(">>>Enter a Command:\n" + "EXIT");
		input = in.nextLine();
		
		Store mainStore = new Store();
		while (!input.equals("EXIT")) {
			if (input.equals("REGISTER")) {
				String inputUn;
				String inputPw;
				
				System.out.println(">>>You selected to REGISTER");
				System.out.println(">>>Enter a Username");
				inputUn = in.nextLine();
				System.out.println(">>>Enter a password");
				inputPw = in.nextLine();
				
				mainStore.createUser(inputUn, inputPw);
				
				// TODO: write the code to handle loginnig in
			} else if (input.equals("LOGIN")) {
					System.out.println("You selected to LOGIN");
					System.out.println("Enter Username");
					// to handle LOGIN maybe consider having either store or user class query database then save user_id for future queries
					// todo: move get input to top?
					while (!input.equals("LOGOUT")) {
						if (input.equals("BROWSE")) {
							System.out.println("You selected to BROWSE");
							
						} else if (input.equals("SELL")) {
								System.out.println("You selected to SELL");
								
						} else if (input.equals("AUTHORIZE")) {
								System.out.println("You selected to AUTHORIZE");
								
						} else if (input.equals("CHANGEPW")) {
							
						}
						
						System.out.println(">>>You are in your account, enter command");
						System.out.print(">>>");
						input = in.nextLine();
					}
			} else {
					System.out.println("You selected an unrecognized COMMAND");
			}
			
			System.out.println(">>>In the while loop, enter command");
			System.out.print(">>>");
			input = in.nextLine();
			
		}
		/* TODO:
			- Start writing code to create new user into
		database within while loop asking for different commands
			- How to test if the username is already in use?
				= Select from users username = inputedname and if something is returned disallow the username?
		*/
		if (DEBUG != 0) {
			User aUser = new User("Lynda", "password", false);
		
			System.out.println("Enter your old password");
			String opw = in.nextLine();
			System.out.println("Enter your new password");
			String npw = in.nextLine();
			aUser.changePassword(opw, npw);
			System.out.format("User username is: %s\n", aUser.getUsername());
		
			Item tea = new Item("Tea Sampler", 123, "A box of 5 different chai teas to try", 20, false);
			tea.printItem();
		}

	}
}