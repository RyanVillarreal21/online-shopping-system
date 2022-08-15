package main;

public class itemDetail {
	String itemName;
	String description; 
	double regularPrice;
	double premiumPrice;
	int available;	
	int reserved;		
	
	
	
	//Constructor 
	itemDetail(String name, String info, double regPrice, double premPrice, int avail, int reser){
		itemName = name;
		description = info;
		regularPrice = regPrice;
		premiumPrice = premPrice;
		available = avail;
		reserved = reser;
	}
	
	public String getDetail() {
		String item = String.format("[Item]: %-30s	[Description]: %-70s	[Regular Price]: $%6.2f          [Premium Price]: $%6.2f", itemName, description, regularPrice, premiumPrice);
		return item;
	}
	
	
	//For when Use Case: Select Items
	//"2. The system displays catalog items, short descriptions, regular customer prices, and premium customer prices. "
	public void displayDetail() {
		System.out.println("    [Item]: " + itemName);
		System.out.println("    [Description]: " + description);
		System.out.println("    [Regular Price]: $ " + regularPrice);
		System.out.println("    [Premium Price]: $ " + premiumPrice);
		System.out.println(); 	
	}
	
	
	//For when Use Case: Process order
	//"4. The system determines that the items for the order are available in stock. "
	public int getAvailable() {
		return available;
	}
	
	
	
	//For when Use Case: Process Order AND Ship Order
	//"5. If the items are in stock, the system reserves the items ..."
	//"5. The system updates the number of reserved items in stock."
	public void reserveItem(int quantity_toReserve) {
		
		available -= quantity_toReserve;
		reserved += quantity_toReserve;
	}
	public void shipItem(int quantity_toShip) {
		reserved -= quantity_toShip;
	}
	public void updateAvailable(int newAvail) {
		available = newAvail;
	}
	
	
	
}