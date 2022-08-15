package main;


import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.util.Scanner;    

public class Create {

	/* Variables used in this class */
	private String userId;		private TextField userId_TextField;
	private String userPass;	private TextField userPass_TextField;
	private String name;		private TextField name_TextField;
	private String userAdd;		private TextField userAdd_TextField;
	private String userPhon;	private TextField userPhon_TextField;
	private String userAcc;		private TextField userAcc_TextField;
	private String prem_firstOrder_ofYear;	
	private String cardName;	private TextField cardName_TextField;
	private String userCred;	private TextField userCred_TextField;
								private Label createResult_Label;
	
	/* Objects of Types(Class) used in this class */
	Login log = new Login();
	
	/* This Class's Scene */
	Scene Create_Scene;
	
	
	public void CreateGUI() {
		
		/* Initialize the private variables */
		createResult_Label = new Label();
		userId_TextField = new TextField();
		userPass_TextField = new TextField();
		name_TextField = new TextField();
		userAdd_TextField =  new TextField();
		userPhon_TextField = new TextField();
		userAcc_TextField = new TextField();
		cardName_TextField = new TextField();
		userCred_TextField = new TextField();
		
		/* Labels to prompt text */
		Label promptCreateInstruction = new Label("Welcome New User"+'\n'+"Please enter the following information");
		Label userId_Label = new Label("User ID:");
		Label userPass_Label = new Label("Password:");
		Label name_Label = new Label("First Name: ");
		Label userAdd_Label = new Label("Address (Please use spaces and no commas):");
		Label userPhon_Label = new Label("Phone Number (Please include '-'):");
		Label userAcc_Label = new Label("Account Type ('Regular','Premium','Supplier'):");
		Label cardName_Label = new Label("Credit Card Name (Ex. Charles Brandon):");
		Label userCred_Label = new Label("Credit Card Number (Please include a space in between numbers):");
		
		/* Formatting Text Output */
		promptCreateInstruction.setFont(Font.font("Verdana", 20));
		userPass_Label.setFont(Font.font("Verdana", 15));
		userId_Label.setFont(Font.font("Verdana", 15));
		name_Label.setFont(Font.font("Verdana", 15));
		userAdd_Label.setFont(Font.font("Verdana", 15));
		userPhon_Label.setFont(Font.font("Verdana", 15));
		userAcc_Label.setFont(Font.font("Verdana", 15));
		cardName_Label.setFont(Font.font("Verdana", 15));
		userCred_Label.setFont(Font.font("Verdana", 15));
		
		
		/* Horizontal HBox, or 'rows', with elements */
		HBox userId_hbox = new HBox(10, userId_Label, userId_TextField);
		userId_hbox.setAlignment(Pos.CENTER);
		HBox userPass_hbox = new HBox(10, userPass_Label, userPass_TextField);
		userPass_hbox.setAlignment(Pos.CENTER);
		HBox name_hbox = new HBox(10, name_Label, name_TextField);
		name_hbox.setAlignment(Pos.CENTER);
		HBox userAdd_hbox = new HBox(10, userAdd_Label, userAdd_TextField);
		userAdd_hbox.setAlignment(Pos.CENTER);
		HBox userPhon_hbox = new HBox(10, userPhon_Label, userPhon_TextField);
		userPhon_hbox.setAlignment(Pos.CENTER);
		HBox userAcc_hbox = new HBox(10, userAcc_Label, userAcc_TextField);
		userAcc_hbox.setAlignment(Pos.CENTER);
		HBox cardName_hbox = new HBox(10, cardName_Label, cardName_TextField);
		cardName_hbox.setAlignment(Pos.CENTER);
		HBox userCred_hbox = new HBox(10, userCred_Label, userCred_TextField);
		userCred_hbox.setAlignment(Pos.CENTER);
		
		/* Create Account Button with action */
		Button createAccountButton = new Button("Create Account");
		createAccountButton.setOnAction(new createButtonHandler());
		
		/* Return to Menu Button with formatting */
		Button mainMenuButton = new Button("Return to Menu");
		mainMenuButton.setOnAction(new returnMenuHandler());
		HBox menubutton_hbox = new HBox(10, mainMenuButton);
		menubutton_hbox.setAlignment(Pos.TOP_LEFT); menubutton_hbox.setPadding(new Insets(10));
		
		
		/* Vertical VBox, or 'Columns', with elements */
		VBox CreatePage_vbox = new VBox(20, menubutton_hbox, promptCreateInstruction, userId_hbox, userPass_hbox, name_hbox, userAdd_hbox, userPhon_hbox, userAcc_hbox, cardName_hbox, userCred_hbox, createAccountButton,createResult_Label);
		CreatePage_vbox.setAlignment(Pos.CENTER);
		
		Create_Scene = new Scene(CreatePage_vbox, 1280,720);
		
		App.getWindow().setTitle("Create Account");
		App.getWindow().setScene(Create_Scene);
		App.getWindow().show();
	}
	
	
	
	/* Actions to do if Create Account Button is clicked ----------------------------------------------------------------*/
	class createButtonHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			try {
			
				/* Store User input in Private Variables */
				userId = userId_TextField.getText();
				userPass = userPass_TextField.getText();
				name = name_TextField.getText();
				userAdd = userAdd_TextField.getText();
				userPhon = userPhon_TextField.getText();
				userAcc = userAcc_TextField.getText();
				cardName = cardName_TextField.getText();
				userCred = userCred_TextField.getText();
			
				FileWriter myWriter= new FileWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\SavedAccounts.txt",true);
				FileWriter cardWriter = new FileWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\CreditCards.txt",true);
				
				File account = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\SavedAccounts.txt");
				Scanner scan = new Scanner(account);
				
				boolean invalidAccount = false;
				boolean emptyTextFields = false;
				boolean idError = false;
				/* The while loop is used to check for if user input ID already exists in the system*/
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					String[] splitLine = line.split(",");
				
					String accID = splitLine[0];
					
					if( accID.trim().equalsIgnoreCase(userId.trim()) ) {
						createResult_Label.setText("[Error]: ID already exist in system."+'\n'+"Enter a different User ID");
						createResult_Label.setFont(Font.font("Verdana", 15));
						createResult_Label.setTextFill(Color.RED);
						idError = true;
						break;
					}
				}
				scan.close();
				
				/* Check for misspelling of account type */
				if(userAcc.trim().equals("Premium") || 
				   userAcc.trim().equals("Regular")|| 
				   userAcc.trim().equals("Supplier")) {
					//if account type entered is valid continue
				}else { 
					createResult_Label.setText("[Error]: Invalid account type, check spelling. Make sure first letter is uppercase.");
					createResult_Label.setFont(Font.font("Verdana", 15));
					createResult_Label.setTextFill(Color.RED);
					invalidAccount = true;
				}
				
				/* Check if user when creating account leaves critical information  blank or empty*/
				if(userPass.isEmpty() || userId.isEmpty() || userPass.isBlank() || userId.isBlank()) {
					createResult_Label.setText("[Error]: Check to make sure values are entered for User ID & User Password");
					createResult_Label.setFont(Font.font("Verdana", 15));
					createResult_Label.setTextFill(Color.RED);
					emptyTextFields = true;
				}
				
				/* Checks to see if CUSTOMER enters all their required information */
				if(userAcc.trim().equals("Premium") || userAcc.trim().equals("Regular")) {
					if(name.isBlank() || name.isEmpty() || 
					   userAdd.isBlank() || userAdd.isEmpty() ||
					   userPhon.isBlank() || userPhon.isEmpty() ||
					   cardName.isBlank() || cardName.isEmpty() ||
					   userCred.isBlank() || userCred.isEmpty()  ) {
						createResult_Label.setText("[Error]: [Customers] Enter all information"+'\n'+"          [Suppliers] Enter only ID & Password");
						createResult_Label.setFont(Font.font("Verdana", 15));
						createResult_Label.setTextFill(Color.RED);
						invalidAccount = true; //Because customer didn't enter all information required for customer
					}
						
				}
					
				/* Start a new line for the new account in file (SavedAccounts.txt) */
				if(idError == false && emptyTextFields == false && invalidAccount == false) {
					myWriter.write("\n");
					cardWriter.write("\n");
					
					myWriter.write(userId + ",");
					myWriter.write(userPass + ",");
					myWriter.write(name + ",");
					myWriter.write(userAdd + ",");
					myWriter.write(userPhon + ",");
					
					/* If premium account set first item of year purchase membership fee to '1' */
					/* Used in Make Order method*/
					if(userAcc.equalsIgnoreCase("Premium")) {
						prem_firstOrder_ofYear = "1";
					}else {prem_firstOrder_ofYear = "0";}
					
					myWriter.write(prem_firstOrder_ofYear + ",");
					myWriter.write(userAcc);
					cardWriter.write(cardName + ",");
					cardWriter.write(userCred);
					cardWriter.write(",5000.00");
					
					
					myWriter.close();
					cardWriter.close();
					
					createResult_Label.setText("Account successfully created"+'\n'+"Return to Main Menu to login");
					createResult_Label.setFont(Font.font("Verdana", 15));
					createResult_Label.setTextFill(Color.GREEN);
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}	
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
	
	
	
	//The methods below are used to get and set the userId, userPass, name, userAdd, userPhon,userAcc, and userCred
		public String getUserId() {
			return userId;
		}
		
		public String getUserPass() {
			return userPass;
		}
		
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		public void setUserPass(String userPass) {
			this.userPass = userPass;
		}
		
		public String getName() {
			return name;
		}
		
		public String getUserAdd() {
			return userAdd;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public void setUserAdd(String userAdd) {
			this.userAdd = userAdd;
		}
		
		public String getUserPhon() {
			return userPhon;
		}
		
		public String getUserAcc() {
			return userAcc;
		}
		
		public void setUserPhon(String userPhon) {
			this.userPhon = userPhon;
		}
		
		public void setUserAcc(String userAcc) {
			this.userAcc = userAcc;
		}
		
		public String getCardName() {
			return cardName;
		}
		
		public void setCardName(String cardName) {
			this.cardName = cardName;
		}
		
		public String getUserCred() {
			return userCred;
		}
		
		public void setUserCred(String userCred) {
			this.userCred = userCred;
		}
}