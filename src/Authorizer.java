package store;

public class Authorizer{
	private String userName;
	private String passW;
	private boolean changePW;
	private boolean isAuthzr;
	
	public Authorizer(String t_name, String t_password, boolean ynAuth){
		/*check is userName is already in use*/
		userName = t_name;
		changePW = true;
		passW = t_password;
		isAuthzr = ynAuth;
	}
	
	public void changePassword(String opW, String npW){
		//take in old opW and make sure it matches stored password
		//maybe have type in new password twice and make sure they match 
		if(changePW){
			int wrongpW = 0;
			if(passW != opW & wrongpW <3){
				wrongpW++;
				//Error message and retry
			} else if(wrongpW >= 3){
				changePW = false;
				//can't try anymore, lock out from passW channging
			}//check for if over 3 for if something when wrong?
			passW = npW;
		}else{
			//to many attempts at trying to change password, lock out contact admin
		}
		
		//otherwise make them try again and if they
		//keep on failing lock out (failing on opW)
	}
	
	public void addAuthorizer(/*user object*/){
		//convert the user object to an authorizer
		//object
		//can only add an authorizer if you are an authorizer
	}
	
	public void removeAuthorizer(/*user object*/){
		//convert authorizer back down to user
		//can only remove an authorizer if you are an authorizer
	}
	
	public String getUsername(){
		return userName;
	}
	
	public String getPassword(){
		return passW;
	}
	
	public boolean getAuthorization(){
		return isAuthzr;
	}
}