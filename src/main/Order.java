package main;

class Order {

	
	//ACTOR: Customer & Bank
	public void makeOrder(Select_Items order, String userID) {
		/*
		 * Display delivery methods option (Mail[$3] or In-Store Pickup[FREE])
		 * Customer selects one delivery method for order
		 * 
		 * System request to bank to charge the total purchases amount using cutomer's credit card
		 * 		If approved, receive an purchase authorization number from Bank System
		 * 		
		 * Basically stores the customer Id, selected items, purchase authorization number, order status in one line in "Orders.txt"
		 * Make Status set as "ordered"
		 * Display order confirmation
		 * 
		 * [Check for alternative sequences in Project PDF]
		 */
		
		
		/*
		 * Orders.txt Should follow this format
		 * [customer ID],[quantity items quantity items quantity items],[purchase authorization number],[order status]
		 * 
		 * EX:
		 * 		ryavilla,2 Monitor 1 Headset 1 Mouse,00000001,ordered
		 */
		
		
		//Example code to access items & quantity in 'Order'
		System.out.println("[Displaying Order...]");
		System.out.println();
		
		
		System.out.println(userID);
		
		for (int i = 0; i < order.cart.size(); i++) { 				//for each item in the cart
			
			itemDetail item = (itemDetail) order.cart.get(i);		//get the item value and store to local var 'item'
			
			
			System.out.println(item.itemName + ", quantity: "+ order.quantity.get(i));
		}
		System.out.println('\n'+"Order's Total Price: $ "+ String.format("%.2f", order.totalPrice));
		
		
	}
	
	//ACTOR: Customer
	public void viewOrder() {
		/*
		 * Read orders from "Orders.txt, that matches customer's ID"
		 * Customer Selects an order
		 * Display the order detail and status
		 */
	}
	
	//ACTOR: Customer
	public void viewInvoice() {
		/*
		 * Basically the exact thing as "viewOrder" method
		 * 
		 */
	}
	
	
	
	//ACTOR: Supplier
	public void processOrder() {
		/*
		 * Read from "Orders.txt"
		 * Display all orders
		 * select an order
		 * check if items in order are available, if so reserve them (check 'itemDetail class'  reserve method; Gabe needs to Fix it)
		 * change order status from "ordered" to "ready"
		 * Display a message that the items have been reserved
		 */
	}
	
	//ACTOR: Supplier
	public void shipOrder() {
		/*
		 * Read from "Orders.txt"
		 * Display only orders with "ready"
		 * Select an order
		 * 'Ship' the items in the order
		 * 		By subtracting the itemsDetail reserved value by the item quantity in that order
		 * Change order status from "ready" to "shipped"
		 * Display the order's status to the supplier
		 */
	}
}
