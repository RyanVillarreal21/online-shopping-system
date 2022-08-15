package main;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CustomerApp /*extends Thread*/{
	
	/* Variables used in this class */
	private String userId;	
	private String userAccountType;
							
	
	/* Objects of Types(Class) used in this class */
	Catalog catalog = new Catalog();
	Select_Items order = new Select_Items();
	Order order_Methods = new Order();
	
	
	/* This Class's Scene & BoderPane*/
	Scene Customer_Scene;
	BorderPane Customer_panel;
	


	public void CustomerGUI() {
		
		/* Labels */
		Label  promptWelcome_Label = new Label("Welcome, "+userAccountType+" customer: " + userId);
		Label chooseAction_Label = new Label("Click One of the Actions Buttons below to perform:");
		
		/* Formatting Welcome Text Output */
		promptWelcome_Label.setFont(Font.font("Verdana", 30));
		chooseAction_Label.setFont(Font.font("Verdana", 15));
		
		
		/* Return to Menu Button with formatting */
		Button mainMenuButton = new Button("Log Out");
		mainMenuButton.setOnAction(new returnMenuHandler());
		VBox welcomeUser_vbox = new VBox(10, mainMenuButton,promptWelcome_Label, chooseAction_Label);
		
		/* Buttons for each Action */
		Button selectItemsButton = new Button("Select Items");
		selectItemsButton.setOnAction(new selectItemsHandler()); 
		selectItemsButton.setPrefHeight(40);
		selectItemsButton.setPrefWidth(100);
		Button makeOrderButton = new Button("Make Order");
		makeOrderButton.setOnAction(new makeOrderHandler());  
		makeOrderButton.setPrefHeight(40);
		makeOrderButton.setPrefWidth(100);
		Button viewOrderButton = new Button("View Order");
		viewOrderButton.setOnAction(new viewOrderHandler());  
		viewOrderButton.setPrefHeight(40); 
		viewOrderButton.setPrefWidth(100);
		Button viewInvoiceButton = new Button("View Invoice");
		viewInvoiceButton.setOnAction(new viewInvoiceHandler());  
		viewInvoiceButton.setPrefHeight(40); 
		viewInvoiceButton.setPrefWidth(100);
		
		/* VBox for Buttons */
		VBox staticButtons_vbox = new VBox(30, selectItemsButton, makeOrderButton, viewOrderButton, viewInvoiceButton);
		staticButtons_vbox.setAlignment(Pos.CENTER);
		
		//Initiated our global variable customer Panel ; has options {top, bottom, left, right, center}
		Customer_panel = new BorderPane();
		Customer_panel.setTop(welcomeUser_vbox);
		Customer_panel.setLeft(staticButtons_vbox);
		Customer_panel.setPadding(new Insets(30));
		
		
		Customer_Scene = new Scene(Customer_panel, 1280, 720);
		App.getWindow().setTitle("Customer");
		App.getWindow().setScene(Customer_Scene);
		App.getWindow().show();	
	}
	
	

	/* Actions to do if "select items" Button is clicked ----------------------------------------------------------------*/
	class selectItemsHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			
			
			//SELECT ITEMS LISTVIEW : List View with items from catalog Array
			ObservableList<itemDetail> items = FXCollections.observableArrayList(catalog.catalogArray);
			ListView<itemDetail> itemslist = new ListView<itemDetail>(items);
			itemslist.setCellFactory(param -> new ListCell<itemDetail>(){
				
				@Override
				protected void updateItem(itemDetail item, boolean empty) {
					super.updateItem(item, empty);
					
					if (empty || item == null || item.getDetail() == null) {
						setText(null);
					} else {
						setText(item.getDetail());
					}
				}
			});
			itemslist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/* Set list of items width and height */
			itemslist.setPrefHeight(300); itemslist.setPrefWidth(200);
			itemslist.maxHeight(350); itemslist.maxWidth(200);
			
			
			/* Local variables Select items action */
			Label instruction_label = new Label("Click on one of the items below to select it");
			Label Quantity_label = new Label("Enter the Quantity of selected item (Highlighted Blue): ");
			
			/* Text Formatting */
			instruction_label.setFont(Font.font("Verdana", 20));
			Quantity_label.setFont(Font.font("Verdana", 15));
			
			/* TextField & labels to show result output of operations */
			TextField quantity_textfield = new TextField();
			Label resultOperation_label = new Label();
		
			
			
			/* ADD BUTTON Action event to do if add button is clicked */
			class addButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					String quantity_str = quantity_textfield.getText();
					
					try {
						int quantity = Integer.parseInt(quantity_str);
						
						/* Get currently selected item from list */
						itemDetail itemSelected = itemslist.getSelectionModel().getSelectedItem();

						if(quantity > 0) {
							
							/* Add item to Cart/order (Class Select_items) ; Using Select_items method */
							if(userAccountType.equals("Premium")) {
								order.addToCartPremium(itemSelected, quantity);
							}else if(userAccountType.equals("Regular")) {
								order.addToCartRegular(itemSelected, quantity);
							}
							
							resultOperation_label.setText(quantity +" "+ itemSelected.itemName+ " added to cart...");
							resultOperation_label.setFont(Font.font("Verdana", 15));
							resultOperation_label.setTextFill(Color.GREEN);
						} else {
							resultOperation_label.setText("[Error]: quantity must be positive");
							resultOperation_label.setFont(Font.font("Verdana", 15));
							resultOperation_label.setTextFill(Color.RED);
						}
						
						
					} catch (Exception e) {
						resultOperation_label.setText("[Error]: Enter valid integer quantity");
						resultOperation_label.setFont(Font.font("Verdana", 15));
						resultOperation_label.setTextFill(Color.RED);
					}	
				}
			}
			
			
			/* EXIT FINISH BUTTON Action event to do if exitFinish button is clicked */
			class exitFinishButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					/* Button & Label to clear cart/order */
					Label clearCart_label = new Label();
					Button clearCartButton = new Button("Clear Cart");
					clearCartButton.setPrefHeight(40);
					clearCartButton.setPrefWidth(80);
					clearCartButton.setOnAction(e -> {order.clearCart(); 
													clearCart_label.setText("Cart cleared...");
													clearCart_label.setFont(Font.font("Verdana", 18));
													clearCart_label.setTextFill(Color.GREEN);});
			
					/* Label to hold the cart/order */
					Label cart_order_label = new Label();
					cart_order_label.setFont(Font.font("Verdana", 20));
					cart_order_label.setText(order.getCart_OrderStringGUI());
					
					/* Other Labels */
					Label displayCart_label = new Label("Displaying your cart...");
					displayCart_label.setFont(Font.font("Verdana", 20));
					displayCart_label.setTextFill(Color.GREEN);
					
					
					/* Format Label result */
					resultOperation_label.setText("* Continue to select items to add to your cart by clicking the 'Select Items' button.\n"
							+ "* Click the 'Clear Cart' button to clear your cart.\n"
							+ "* Or if your done selecting items click the 'Make Order' button.");
					resultOperation_label.setFont(Font.font("Verdana", 18));
					resultOperation_label.setTextFill(Color.GREY);
					
					/* VBox with elements to append to centerPanel */
					VBox centerPanel_vbox = new VBox(20,displayCart_label, cart_order_label, resultOperation_label, clearCartButton, clearCart_label);
					centerPanel_vbox.setPadding(new Insets(40));
					centerPanel_vbox.setAlignment(Pos.CENTER);
					
					Customer_panel.setCenter(centerPanel_vbox);
				}
			}
			
			
			/* Button */
			Button addToCartButton = new Button("Add to Cart");
			addToCartButton.setOnAction(new addButtonHandler());
			Button exitFinishButton = new Button("Finished/Exit");
			exitFinishButton.setOnAction(new exitFinishButtonHandler());
			
			/* HBox for VBox */
			HBox enterQuantity_hbox = new HBox(10, Quantity_label, quantity_textfield);
			HBox operation_hbox = new HBox(10, addToCartButton, exitFinishButton);
			
		
			/* VBox for center panel */
			VBox centerPanel_vbox = new VBox(20,instruction_label, itemslist, enterQuantity_hbox, operation_hbox, resultOperation_label);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			
			
			Customer_panel.setCenter(centerPanel_vbox);
		}
	}	
	
	
	
	
	/* Actions to do if "Make Order" Button is clicked ----------------------------------------------------------------*/
	class makeOrderHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			
			/* Order Date */
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
			LocalDateTime orderDate = LocalDateTime.now();  
			String orderDate_str = dtf.format(orderDate);
			
			Label userOrder_label = new Label();
			userOrder_label.setFont(Font.font("Verdana", 20));	
			
		
			if(order.cart.isEmpty()) {
				userOrder_label.setText("[Cart is empty]: Click the \"Select Items\" button to add items to your cart.");
				Customer_panel.setCenter(userOrder_label);
				
			}else {

				Label intruction_label = new Label("In-store pickup is FREE.\nIf you want mail delivery click the \"Mail Delivery\" button");
				intruction_label.setFont(Font.font("Verdana", 20));
				
				/* Enter Credit Card Label & TextField */
				TextField creditCard_textfield = new TextField();
				Label enterCC_label = new Label("Enter your Credit Card with spaces");
				enterCC_label.setFont(Font.font("Verdana", 20));
				
				HBox creditCardLine_hbox = new HBox(20,enterCC_label, creditCard_textfield);
				creditCardLine_hbox.setAlignment(Pos.CENTER);
				
				
				
				/* PUT ORDER BUTTON Action event to do if Put Order button is clicked */
				class putOrderHandler implements EventHandler<ActionEvent>{
					
					@Override
					public void handle(ActionEvent event) {
						String creditCard  = creditCard_textfield.getText();
						
						/* Send Credit Card and Total Price */
						App.getmakeOrderThread().sendcred(creditCard, order.totalPrice);
						App.getbankThread().rerun();
						try {
							App.getmakeOrderThread().join();
							App.getbankThread().join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						/* if valid credit card ; write to file orders.txt */
						if(App.getbankThread().valid == 1) {
							
							
		
							try {
								FileWriter orderWriter= new FileWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt",true);
								
								orderWriter.write('\n');
								orderWriter.write(userId+",");
								
								/* Go through each element in cart/order */						
								int i = 0;
								while(i < order.cart.size()) {
									
									orderWriter.write(order.quantity.get(i)+" "+order.cart.get(i).itemName);
									i++;
									
									if(i == order.cart.size() ) {
										/* don't print space since there is no more items */
									}else{
										orderWriter.write(" ");
									}
								}
								
								orderWriter.write(",");
								
								orderWriter.write("$"+order.totalPrice+","); //DONT FORGET TO ADD 40 FOR FIRST TIME PREMIUM PURCHASE 
								orderWriter.write(App.getbankThread().aNum + ",");
								orderWriter.write("ordered,");
								orderWriter.write(orderDate_str+",");
								orderWriter.write("Credit Card");
								
								orderWriter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							
							
							userOrder_label.setText("Order has been made.");
							userOrder_label.setTextFill(Color.GREEN);
							Customer_panel.setCenter(userOrder_label);
							
						}else {
							/* Insufficient funds; prints order can't be made */
							userOrder_label.setText("* Order denied...enter another different credit card number\n* Or cancel order");
							userOrder_label.setTextFill(Color.RED);
						}
					}
				}
				
				
				/* CANCEL ORDER BUTTON Action event to do if Cancel Order button is clicked */
				class cancelOrderHandler implements EventHandler<ActionEvent>{
					
					@Override
					public void handle(ActionEvent event) {
						order.clearCart();
						userOrder_label.setText("Order cancelled: Cart has been emptied");
						Customer_panel.setCenter(userOrder_label);
					}
				}
				
				/* Put in order Button */
				Button putInOrderButton = new Button("Put in Order");
				putInOrderButton.setOnAction(new putOrderHandler());
				putInOrderButton.setPrefHeight(30);
				putInOrderButton.setPrefWidth(200);
				
				
				/* Cancel order: clears cart BUTTON */
				Button cancelOrderButton = new Button("Cancel Order");
				cancelOrderButton.setOnAction(new cancelOrderHandler());
				cancelOrderButton.setPrefHeight(30);
				cancelOrderButton.setPrefWidth(200);
				
				HBox makeOrderButtons = new HBox(30,putInOrderButton, cancelOrderButton);
				makeOrderButtons.setAlignment(Pos.CENTER);
				
				/* Label for confirming mail button is clicked */
				Label mailconfirmed_label = new Label();
				mailconfirmed_label.setFont(Font.font("Verdana",20));
				mailconfirmed_label.setTextFill(Color.GREEN);
				
				/* Buttons with action */
				Button mailButton = new Button("Mail Delivery $3 Fee ");
				mailButton.setPrefHeight(30);
				mailButton.setPrefWidth(200);
				mailButton.setOnAction(e -> {order.totalPrice += 3; mailconfirmed_label.setText("Mail Delivery selected, added $3 to total purchase amount.");} );
				
				/* Charger the premium 40$ , first purchase of year*/
				
				VBox panel_vbox = new VBox(20, intruction_label, mailButton, mailconfirmed_label,creditCardLine_hbox, makeOrderButtons, userOrder_label);
				panel_vbox.setAlignment(Pos.CENTER);
				
				
				
				Customer_panel.setCenter(panel_vbox);
				
			}	
		}
	}	
	
	
	
	
	
	
	
	/* Actions to do if "View Order" Button is clicked ----------------------------------------------------------------*/
	class viewOrderHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			
			
			/* Labels */
			Label instruction_label = new Label("Click on one of your order(s) below to select it");
			Label displayedOrder_label = new Label();
			
			/* Text Formatting */
			instruction_label.setFont(Font.font("Verdana", 20));
			
			
			
			/* ArrayList of Customer Orders from Orders.txt (The whole line) */
			ArrayList<String> myOrders = new ArrayList<String>();
			
			/* Try making myOrders ArrayList */
			try {
				File orderFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
				Scanner process = new Scanner(orderFile);
			
				/* Read in orders of Customer into ArrayList 'myOrders' */
				while(process.hasNextLine()) {
					String orderLine = process.nextLine();
					String values[] = orderLine.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String user = userId;
					String orderId = values[0];
					
					/* If order line matches user ID */
					if(orderId.trim().equals(user.trim())) {
						myOrders.add(orderLine);
					}
				}process.close();
	
			} 
			catch(FileNotFoundException e) {
				System.out.println("[Orders.txt File was not found..]");
			}
			/* Exit making myOrders ArrayList */
			
			
			//Customer's Order(s) LISTVIEW : List View with customer's order(s) built from ArrayList 'myOrders'
			ObservableList<String> orders = FXCollections.observableArrayList(myOrders);
			ListView<String> ordersList = new ListView<String>(orders);
			ordersList.setCellFactory(param -> new ListCell<String>() {
				
				@Override
				protected void updateItem(String orders, boolean empty) {
					super.updateItem(orders, empty);
					
					if (empty || orders == null ) {
						setText(null);
					} else {
						String customerOrder;
						String values[] = orders.split(",");
						/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
						customerOrder = values[0] + " Order"; 
						setText(customerOrder);
					}
				}
				
			});
			ordersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/* Set list of orders width and height */
			ordersList.setPrefHeight(150); ordersList.setPrefWidth(100);
			ordersList.maxHeight(200); ordersList.maxWidth(100);
			
			
			
			/* DISPLAY ORDER BUTTON Action event to do if Display Order button is clicked */
			class displayOrderButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					/* Get currently selected order from list */
					String orderSelected = ordersList.getSelectionModel().getSelectedItem();
					
					String values[] = orderSelected.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String items[] = values[1].split(" ");
					/* Evens index are quantity, while odd index is item Name | Always quantity in front of item's Name*/
					/* EX:[0]:5 [1]:monitor [2]:5 [3]:TV  == "5 monitor 5 TV" */
					
					/* Format your display order string */
					String displayOrder_str = "";
					for(int i = 0; i < items.length; i += 2) {
						displayOrder_str +=  "[Item]: " + String.format("%-20s", items[(i+1)]) + "[Quantity]: " + items[i] +'\n';
					}
					displayOrder_str += "\n[Status]: " + values[4];
					
					displayedOrder_label.setText(displayOrder_str);
					displayedOrder_label.setFont(Font.font("Verdana", 15));
				}
			}
			
			/* Button Display Order Detail & Status*/
			Button displayOrderButton = new Button("Display Order Detail & Status");
			displayOrderButton.setOnAction(new displayOrderButtonHandler());
			displayOrderButton.setPrefHeight(30);
			displayOrderButton.setPrefWidth(200);
			
			
			
			/* VBox for center panel */
			VBox centerPanel_vbox = new VBox(20, instruction_label, ordersList, displayOrderButton, displayedOrder_label);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			
			Customer_panel.setCenter(centerPanel_vbox);
		}
	}	
	
	
	
	
	/* Actions to do if "View Invoice" Button is clicked ----------------------------------------------------------------*/
	class viewInvoiceHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			
			
			/* Labels & HBox for result */
			Label instruction_label = new Label("Click on one of your order(s) below to select it");
			Label orderDate = new Label();
			Label TotalAmount = new Label();
			Label PaymentMethod = new Label();
			Label orderItems = new Label();
			
			HBox displayInvoice_hbox = new HBox(35, orderDate, orderItems, TotalAmount, PaymentMethod);
			displayInvoice_hbox.setAlignment(Pos.CENTER);
			
			/* Text Formatting */
			instruction_label.setFont(Font.font("Verdana", 20));
			orderDate.setFont(Font.font("Verdana", 15));
			TotalAmount.setFont(Font.font("Verdana", 15));
			PaymentMethod.setFont(Font.font("Verdana", 15));
			orderItems.setFont(Font.font("Verdana", 15));
			
			
			/* ArrayList of Customer Orders from Orders.txt (The whole line) */
			ArrayList<String> myOrders = new ArrayList<String>();
			
			/* Try making myOrders ArrayList */
			try {
				File orderFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
				Scanner process = new Scanner(orderFile);
			
				/* Read in orders of Customer into ArrayList 'myOrders' */
				while(process.hasNextLine()) {
					String orderLine = process.nextLine();
					String values[] = orderLine.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String user = userId;
					String orderId = values[0];
					
					/* If order line matches user ID */
					if(orderId.trim().equals(user.trim())) {
						myOrders.add(orderLine);
					}
				}process.close();
	
			} 
			catch(FileNotFoundException e) {
				System.out.println("[Orders.txt File was not found..]");
			}
			/* Exit making myOrders ArrayList */
			
			
			//Customer's Order(s) LISTVIEW : List View with customer's order(s) built from ArrayList 'myOrders'
			ObservableList<String> orders = FXCollections.observableArrayList(myOrders);
			ListView<String> ordersList = new ListView<String>(orders);
			ordersList.setCellFactory(param -> new ListCell<String>() {
				
				@Override
				protected void updateItem(String orders, boolean empty) {
					super.updateItem(orders, empty);
					
					if (empty || orders == null ) {
						setText(null);
					} else {
						String customerOrder;
						String values[] = orders.split(",");
						/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
						customerOrder = values[0] + " Order"; 
						setText(customerOrder);
					}
				}
				
			});
			ordersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/* Set list of orders width and height */
			ordersList.setPrefHeight(150); ordersList.setPrefWidth(100);
			ordersList.maxHeight(200); ordersList.maxWidth(100);
			
			
			
			/* DISPLAY ORDER BUTTON Action event to do if Display Order button is clicked */
			class displayOrderButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					/* Get currently selected order from list */
					String orderSelected = ordersList.getSelectionModel().getSelectedItem();
					
					String values[] = orderSelected.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String items[] = values[1].split(" ");
					/* Evens index are quantity, while odd index is item Name | Always quantity in front of item's Name*/
					/* EX:[0]:5 [1]:monitor [2]:5 [3]:TV  == "5 monitor 5 TV" */
					
					/* Format your items string */
					String items_str = "";
					for(int i = 0; i < items.length; i += 2) {
						items_str +=  "[Item]: " + String.format("%-20s", items[(i+1)]) + "[Quantity]: " + items[i] +'\n';
					}
				
				    orderDate.setText("[Order Date]: \n" + values[5]);
					TotalAmount.setText("[Total Amount]: \n" + values[2]);
					PaymentMethod.setText("[Payment Method]: \n"+ values[6]);
					orderItems.setText(items_str);

				}
			}
			
			/* Button Display Order for View Invoice*/
			Button displayOrderButton = new Button("Display Order Invoice");
			displayOrderButton.setOnAction(new displayOrderButtonHandler());
			displayOrderButton.setPrefHeight(30);
			displayOrderButton.setPrefWidth(200);
			
			
			
			/* VBox for center panel */
			VBox centerPanel_vbox = new VBox(20, instruction_label, ordersList, displayOrderButton, displayInvoice_hbox);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			
			Customer_panel.setCenter(centerPanel_vbox);
		}
	}

	

	
	/* Actions to do if "Return to Menu" Button is clicked ----------------------------------------------------------------*/
	class returnMenuHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
				Scene menu;
				menu = App.getAppScene();
				
				App.getWindow().setTitle("App");
				App.getWindow().setScene(menu);
				App.getWindow().show();
		}
	}	
	
	
	
	//These get and set methods are used to display the userId used from the Login page
		public String getUserId() {
			return userId;
		}
		
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		public void setUserAccountType(String userAcc) {
			this.userAccountType = userAcc;
		}
		

}//class close
