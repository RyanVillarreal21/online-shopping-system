package main;

import java.util.ArrayList;
import java.util.List;


public class Select_Items {
	
	// Cart to hold the item objects select to cart
	// quantity to hold the quantity of the item selected
	// Cart[1] has quantity[1]
	ArrayList<itemDetail> cart = new ArrayList<itemDetail>();
	List<Integer> quantity = new ArrayList<Integer>();
	double totalPrice;	
	
	
	
	public void clearCart() {
		cart.clear();
		quantity.clear();
		totalPrice = 0;
	}
		
	//For Use Case: Select Items
	//"4. The system adds the selected items to a cart"
	public void addToCartPremium(itemDetail ob, int total) {
		cart.add(ob);
		quantity.add(total);
		totalPrice += (ob.premiumPrice * total); 
	}
	
	public void addToCartRegular(itemDetail ob, int total) {
		cart.add(ob);
		quantity.add(total);
		totalPrice += (ob.regularPrice * total);  
	}
	

	
	
	//For Use Case: Select Items
	//"5. The system displays the selected (cart) items, quantities, and the total price "
	public void displayCart() {
		System.out.println("[Displaying Cart: Selected Items, Quantities, and Total Price]");
		System.out.println();
		
		for (int i = 0; i < cart.size(); i++) { 				//for each element/item in the cart
			itemDetail item = (itemDetail) cart.get(i);			//get the element/item value and store to local var "item
			System.out.println(item.itemName + ", quantity: "+ quantity.get(i));
		}
		System.out.println('\n'+"Total Price: $ "+ String.format("%.2f", totalPrice));
	}
	
	public String getCart_OrderStringGUI() {
		
		String orderStr = "";
		
		for (int i = 0; i < cart.size(); i++) { 				
			itemDetail item = (itemDetail) cart.get(i);			
			orderStr += item.itemName + ", quantity: "+ quantity.get(i) +'\n';
		}
		
		orderStr += "\n[Total Price]: $ "+ String.format("%.2f", totalPrice);
		return orderStr;
	}
	

}


