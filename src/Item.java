package store;

public class Item{
	private String name;
	private double idNum;
	private String describe;
	private float expPrice;
	private boolean isAuth;
	private float price;
	private String comments;
	private boolean agree;
	private boolean sold;
	
	/**will need to have a idNum generator somewhere*/
	public Item(String a_name, double a_idnum, String a_description, float a_exprice, boolean doAgree){
		name = a_name;
		idNum = a_idnum;
		describe = a_description;
		expPrice = a_exprice;
		agree = doAgree;
	}
	
	/*make sure to do error checking in make the data within bounds*/
	public void setPrice(float finalPrice){
		price = finalPrice;
	}
	
	public void authorize(){
		isAuth = true;
	}
	
	public void setComment(String aCom){
		comments = aCom;
	}
	
	public void agreePrice(){
		agree = true;
	}
	
	public void disagreePrice(){
		agree = false;
	}
	
	public void isSold(){
		sold = true;
	}
	
	public void printItem(){
		System.out.format("Item Name: %s%nItem Number: %.0f%nItem Description: %s%nExpected Price: $%.2f%n", name, idNum, describe, expPrice);
	}
}