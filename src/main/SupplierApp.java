package main;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierApp {
	
	/* Variables used in this */
	private String userId;
	
	/* Objects of Types(Class) used in this class */
	Stock stock = new Stock();
	Catalog catalog = new Catalog();
	
	/* This Class's Scene & BoderPane*/
	Scene Supplier_Scene;
	BorderPane Supplier_panel;
	
	
	public void SupplierGUI() {
		
		/* Labels */
		Label  promptWelcome_Label = new Label("Welcome, supplier: " + userId);
		Label chooseAction_Label = new Label("Click One of the Actions Buttons below to perform:");
		
		/* Formatting Welcome Text Output */
		promptWelcome_Label.setFont(Font.font("Verdana", 30));
		chooseAction_Label.setFont(Font.font("Verdana", 15));
		
		
		/* Return to Menu Button with formatting */
		Button mainMenuButton = new Button("Log Out");
		mainMenuButton.setOnAction(new returnMenuHandler());
		VBox welcomeUser_vbox = new VBox(10, mainMenuButton,promptWelcome_Label, chooseAction_Label);
		
		/* Buttons for each Action */
		Button processOrderButton = new Button("Process Order");
		processOrderButton.setOnAction(new processOrderHandler()); 
		processOrderButton.setPrefHeight(40);
		processOrderButton.setPrefWidth(100);
		Button shipOrderButton = new Button("Ship Order");
		shipOrderButton.setOnAction(new shipOrderHandler());  
		shipOrderButton.setPrefHeight(40);
		shipOrderButton.setPrefWidth(100);
		Button viewStockButton = new Button("View Stock");
		viewStockButton.setOnAction(new viewStockHandler());  
		viewStockButton.setPrefHeight(40); 
		viewStockButton.setPrefWidth(100);

		/* VBox for Buttons */
		VBox staticButtons_vbox = new VBox(30, processOrderButton, shipOrderButton, viewStockButton);
		staticButtons_vbox.setAlignment(Pos.CENTER);
		
		//Initiated our global variable customer Panel ; has options {top, bottom, left, right, center}
		Supplier_panel = new BorderPane();
		Supplier_panel.setTop(welcomeUser_vbox);
		Supplier_panel.setLeft(staticButtons_vbox);
		Supplier_panel.setPadding(new Insets(30));
		
		
		/* Set & Show Supplier Scene */
		Supplier_Scene = new Scene(Supplier_panel, 1280, 720);
		App.getWindow().setTitle("Supplier");
		App.getWindow().setScene(Supplier_Scene);
		App.getWindow().show();
	}
	
	
	
	

	/* Actions to do if "Process Order" Button is clicked ----------------------------------------------------------------*/
	class processOrderHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
				
			/* Labels */
			Label instruction_label = new Label("Click on one of the order(s) below to select it");
			Label result_label = new Label();
			
			/* Text Formatting */
			instruction_label.setFont(Font.font("Verdana", 20));
			result_label.setFont(Font.font("Verdana", 15));
			
			
			/* ArrayList of ALL Orders from Orders.txt (Acts like like a copy of orders.txt) */
			ArrayList<String> allOrders = new ArrayList<String>();
			
			/* Try making allOrders ArrayList */
			try {
				File orderFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
				Scanner process = new Scanner(orderFile);
			
				/* Read in all Orders into ArrayList 'allOrders' */
				while(process.hasNextLine()) {
					String orderLine = process.nextLine();
					allOrders.add(orderLine);
				}process.close();
	
			} 
			catch(FileNotFoundException e) {
				System.out.println("[Orders.txt File was not found..]");
			}
			/* Exit making allOrders ArrayList */
			
			
			//All Order(s) LISTVIEW : List View with all order(s) built from ArrayList 'allOrders'
			ObservableList<String> orders = FXCollections.observableArrayList(allOrders);
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
						customerOrder = values[0] + " Order:\t" + values[1] +"\n\t\t\t[Total]: "+ String.format("%-15s", values[2]) + "[Authorization #]: "+ values[3] +"\t\t[Status]: "+ String.format("%-15s", values[4]) +"[Order Date]: "+values[5]; 
						setText(customerOrder);
					}
				}
				
			});
			ordersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/* Set list of orders width and height */
			ordersList.setPrefHeight(150); ordersList.setPrefWidth(100);
			ordersList.maxHeight(200); ordersList.maxWidth(100);
			
			
			
			/* PROCESS ORDER BUTTON Action event to do if process Order button is clicked */
			class processOrderButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					/* Get currently selected order from list */
					String orderSelected = ordersList.getSelectionModel().getSelectedItem();
					int orderIndex_toWrite = ordersList.getSelectionModel().getSelectedIndex();
					
					String values[] = orderSelected.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String cartOrder[] = values[1].split(" ");
					/* Evens index are quantity, while odd index is item Name | Always quantity in front of item's Name*/
					/* EX:[0]:5 [1]:monitor [2]:5 [3]:TV  == "5 monitor 5 TV" */
					
					/* Make a two separate ArrayList to hold the items & their integer quantity from the order */
					ArrayList<String> itemsList = new ArrayList<String>();
					for(int i = 1; i < cartOrder.length; i += 2) {
						itemsList.add(cartOrder[i]);
					}
					
					ArrayList<Integer> itemQuantityList = new ArrayList<Integer>();
					for(int i = 0; i < cartOrder.length; i += 2) {
						int quantity = Integer.parseInt(cartOrder[i]);
						itemQuantityList.add(quantity);
					}
					
					
					/* Start actual process order code -------------------------------------------------------*/
					/* If item has NOT been processed */	
					if( values[4].trim().equalsIgnoreCase("ordered") ) {
				
						
						/* For each item in the item list*/
						for(int i = 0; i < itemsList.size(); i++) {
							
							/* Find the item in the catalog */
							for(int k = 0; k < catalog.catalogArray.size(); k++) {
								if(catalog.catalogArray.get(k).itemName.equalsIgnoreCase(itemsList.get(i))) {
									
									/* If item available > ordered quantity | reserve it*/
									if(catalog.catalogArray.get(k).available >= itemQuantityList.get(i)) {
										catalog.catalogArray.get(k).reserveItem(itemQuantityList.get(i));
									}
								}
								
							}
						}
						
						
						/* Overwrite this order saying its 'ready' in the ArrayList allOrder( the copy of orders.txt )*/
						/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
						String readyOrder = values[0]+ "," +values[1]+ "," +values[2]+ "," +values[3]+ ","+"ready"+ "," +values[5]+ "," +values[6];
						allOrders.set(orderIndex_toWrite, readyOrder);
						
						try {
							PrintWriter outputFileOrders = new PrintWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
							
							for(int i = 0; i < allOrders.size(); i++) {
								outputFileOrders.println(allOrders.get(i));
							} outputFileOrders.close();
							
						} catch(FileNotFoundException e) {
						      System.out.println("[Orders.txt or Stock.txt File was not found...]");
					    }
						
						
						 catalog.updateCatalogFiles();
						/* End actual process order code -------------------------------------------------------*/
						
						 Supplier_panel.setCenter(result_label);
						 
						result_label.setText("This Order has been processed.\nThe items have been reserved.");
						result_label.setTextFill(Color.GREEN);
						
						
					/* If item has already been processed */	
					}else {
						result_label.setText("This Order has already been processed.");
						result_label.setTextFill(Color.RED);
					}
				}
			}
			
			/* Button process Order */
			Button processOrderButton = new Button("Process Order");
			processOrderButton.setOnAction(new processOrderButtonHandler());
			processOrderButton.setPrefHeight(30);
			processOrderButton.setPrefWidth(200);
			
			
			
			/* VBox for center panel */
			VBox centerPanel_vbox = new VBox(20, instruction_label, ordersList, processOrderButton, result_label);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			
			Supplier_panel.setCenter(centerPanel_vbox);
		}
	}

	
	
	
	/* Actions to do if "Ship Order" Button is clicked ----------------------------------------------------------------*/
	class shipOrderHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
				
			/* Labels */
			Label instruction_label = new Label("Click on one of the order(s) below to select it");
			Label result_label = new Label();
			
			/* Text Formatting */
			instruction_label.setFont(Font.font("Verdana", 20));
			result_label.setFont(Font.font("Verdana", 15));
			
			
			/* ArrayList of ALL Orders from Orders.txt (Acts like like a copy of orders.txt) */
			/* And ArrayList of all READY orders from orders.txt  with an index ArrayList for the ready orders*/
			ArrayList<String> allOrders = new ArrayList<String>();
			ArrayList<String> readyOrders = new ArrayList<String>();
			ArrayList<Integer> readyOrders_index = new ArrayList<Integer>();
			
			/* Try making allOrders ArrayList */
			try {
				File orderFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
				Scanner process = new Scanner(orderFile);
				
				int index = 0;
				/* Read in all Orders into ArrayList 'allOrders' ; and only ready orders into readyOrders*/
				while(process.hasNextLine()) {
					String orderLine = process.nextLine();
					String values[] = orderLine.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					if(values[4].trim().equalsIgnoreCase("ready")) {
						readyOrders.add(orderLine);
						readyOrders_index.add(index);
					}
					allOrders.add(orderLine);
					index++;
				}process.close();
	
			} 
			catch(FileNotFoundException e) {
				System.out.println("[Orders.txt File was not found..]");
			}
			/* Exit making  ArrayList */
			
			
			//All Order(s) LISTVIEW : List View with all order(s) built from ArrayList 'allOrders'
			ObservableList<String> orders = FXCollections.observableArrayList(readyOrders);
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
						customerOrder = values[0] + " Order:\t" + values[1] +"\n\t\t\t[Total]: "+ String.format("%-15s", values[2]) + "[Authorization #]: "+ values[3] +"\t\t[Status]: "+ String.format("%-15s", values[4]) +"[Order Date]: "+values[5]; 
						setText(customerOrder);
					}
				}
				
			});
			ordersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			/* Set list of orders width and height */
			ordersList.setPrefHeight(150); ordersList.setPrefWidth(100);
			ordersList.maxHeight(200); ordersList.maxWidth(100);
			
			
			
			/* PROCESS ORDER BUTTON Action event to do if process Order button is clicked */
			class shipOrderButtonHandler implements EventHandler<ActionEvent>{
				
				@Override
				public void handle(ActionEvent event) {
					
					/* Get currently selected order from list */
					String orderSelected = ordersList.getSelectionModel().getSelectedItem();
					int ready_index = ordersList.getSelectionModel().getSelectedIndex();
					
					String values[] = orderSelected.split(",");
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					
					String cartOrder[] = values[1].split(" ");
					/* Evens index are quantity, while odd index is item Name | Always quantity in front of item's Name*/
					/* EX:[0]:5 [1]:monitor [2]:5 [3]:TV  == "5 monitor 5 TV" */
					
					/* Make a two separate ArrayList to hold the items & their integer quantity from the order */
					ArrayList<String> itemsList = new ArrayList<String>();
					for(int i = 1; i < cartOrder.length; i += 2) {
						itemsList.add(cartOrder[i]);
					}
					
					ArrayList<Integer> itemQuantityList = new ArrayList<Integer>();
					for(int i = 0; i < cartOrder.length; i += 2) {
						int quantity = Integer.parseInt(cartOrder[i]);
						itemQuantityList.add(quantity);
					}
					
					
					/* Start actual ship order code -------------------------------------------------------*/
					
				
						
					/* For each item in the item list*/
					for(int i = 0; i < itemsList.size(); i++) {
							
						/* Find the item in the catalog */
						for(int k = 0; k < catalog.catalogArray.size(); k++) {
							if(catalog.catalogArray.get(k).itemName.equalsIgnoreCase(itemsList.get(i))) {
									
								/* update the number of reserved items in stock*/
								catalog.catalogArray.get(k).shipItem(itemQuantityList.get(i));				
							}
								
						}
					}
						
						
					/* Overwrite this order saying its 'ready' in the ArrayList allOrder( the copy of orders.txt )*/
					/* [0]UserID, [1]Cart/order, [2]TotalPrice, [3]Authorization#, [4]OrderStatus, [5]OrderDate, [6]PaymentMethod */
					String shipOrder = values[0]+ "," +values[1]+ "," +values[2]+ "," +values[3]+ ","+"shipped"+ "," +values[5]+ "," +values[6];
					allOrders.set(readyOrders_index.get(ready_index), shipOrder);
						
					try {
						PrintWriter outputFileOrders = new PrintWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Orders.txt");
							
						for(int i = 0; i < allOrders.size(); i++) {
							outputFileOrders.println(allOrders.get(i));
						} outputFileOrders.close();
							
					} catch(FileNotFoundException e) {
					      System.out.println("[Orders.txt or Stock.txt File was not found...]");
					 }
						
						
					 catalog.updateCatalogFiles();
					/* End actual process order code -------------------------------------------------------*/
						
					 Supplier_panel.setCenter(result_label);
						 
					result_label.setText("[Order Status]: This Order has been shipped");
					result_label.setTextFill(Color.GREEN);	
				}
			}
			
			/* Button process Order */
			Button shipOrderButton = new Button("Ship Order");
			shipOrderButton.setOnAction(new shipOrderButtonHandler());
			shipOrderButton.setPrefHeight(30);
			shipOrderButton.setPrefWidth(200);
			
			
			
			/* VBox for center panel */
			VBox centerPanel_vbox = new VBox(20, instruction_label, ordersList, shipOrderButton, result_label);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			
			Supplier_panel.setCenter(centerPanel_vbox);
		}
	}

		
	
	
	/* Actions to do if "View Stock" Button is clicked ----------------------------------------------------------------*/
	class viewStockHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			Label Stock_label = new Label();
			
			Stock_label.setText(stock.getViewStock());
			Stock_label.setFont(Font.font("Verdana", 15));
			
			/* VBox with elements to append to centerPanel */
			VBox centerPanel_vbox = new VBox(20, Stock_label);
			centerPanel_vbox.setPadding(new Insets(40));
			centerPanel_vbox.setAlignment(Pos.CENTER);
			
			Supplier_panel.setCenter(centerPanel_vbox);
			
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
	
	
	
	//These get and set methods are used to display the userId used from the Login page --------------------
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
