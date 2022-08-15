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
import java.util.Scanner;

public class Login {
	
	
	/* Variables used in this class */
	private String userId;		private TextField userId_TextField;
	private String userPass;	private TextField userPassword_TextField;
	private String accType;		private Label loginResult_Label;
	
	
	/* Objects of Types(Class) used in this class */
	CustomerApp cust = new CustomerApp();
	SupplierApp supp = new SupplierApp();
	
	
	/* This Class's Scene */
	Scene Login_Scene;
	
	
	
	public void LoginGUI() {
		
		/* Initialize the private variables */
		loginResult_Label = new Label();
		userId_TextField = new TextField();
		userPassword_TextField = new TextField();
		
		/* Labels to prompt text */
		Label promptLoginInstruction = new Label("Login"+'\n'+"Please enter User ID & Password");
		Label userID_Label = new Label("User ID:");
		Label userPassword_Label = new Label("User Password:");
		
		/* Formatting Text Output */
		promptLoginInstruction.setFont(Font.font("Verdana", 20));
		userID_Label.setFont(Font.font("Verdana", 15));
		userPassword_Label.setFont(Font.font("Verdana", 15));
		
		/* Horizontal HBox, or 'rows', with elements */
		HBox userID_hbox = new HBox(10, userID_Label, userId_TextField);
		userID_hbox.setAlignment(Pos.CENTER);
		HBox userPassword_hbox = new HBox(10, userPassword_Label, userPassword_TextField);
		userPassword_hbox.setAlignment(Pos.CENTER);
		
		/* Login button with action */
		Button loginAccountButton = new Button("Login");
		loginAccountButton.setOnAction(new loginButtonHandler());
		
		/* Return to Menu Button with formatting */
		Button mainMenuButton = new Button("Return to Menu");
		mainMenuButton.setOnAction(new returnMenuHandler());
		HBox menubutton_hbox = new HBox(10, mainMenuButton);
		menubutton_hbox.setAlignment(Pos.TOP_LEFT); menubutton_hbox.setPadding(new Insets(10));
		
		/* Vertical VBox, or 'Columns', with elements */
		VBox LoginPage_vbox = new VBox(20, menubutton_hbox, promptLoginInstruction, userID_hbox, userPassword_hbox, loginAccountButton, loginResult_Label);
		LoginPage_vbox.setAlignment(Pos.CENTER);
		
		Login_Scene = new Scene(LoginPage_vbox, 1280, 720);
		
		App.getWindow().setTitle("Login");
		App.getWindow().setScene(Login_Scene);
		App.getWindow().show();
	}
	
	
	/* Actions to do if Login Button is clicked ---------------------------------------------------------------------*/
	class loginButtonHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			try {
			
				/* Store User input ID & Password in Private Variables */
				userId = userId_TextField.getText();
				userPass = userPassword_TextField.getText();
			
			
				File account = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\SavedAccounts.txt");
				Scanner scan = new Scanner(account);
			
				/* The while loop is used to check the file for the username & password */
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					String[] splitLine = line.split(",");
				
					String accUser = splitLine[0];
					String accPass = splitLine[1];
					accType = splitLine[6];
				
					/* Checks for incorrect login  */
					if(accUser.trim().equalsIgnoreCase(userId.trim()) && accPass.equals(userPass)) {
						loginResult_Label.setText("Login was successful");
						loginResult_Label.setFont(Font.font("Verdana", 15));
						loginResult_Label.setTextFill(Color.GREEN);
						
						
						if(accType.equals("Premium") || accType.equals("Regular")) {
							cust.setUserId(userId);
							cust.setUserAccountType(accType);
							//Call Customer GUI Method
							cust.CustomerGUI();
						}
						
						if(accType.equals("Supplier")) {
							supp.setUserId(userId);
							//Call Supplier GUI Method
							supp.SupplierGUI();
						}
						break;
					}else {
						loginResult_Label.setText("Invalid Username or Password"+'\n'+"Please try again");
						loginResult_Label.setFont(Font.font("Verdana", 15));
						loginResult_Label.setTextFill(Color.RED);
					}
				}
			
				scan.close();

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


	//The methods below are used to get and set the userId and userPass-----------------------------------------------------
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
	
	public String getAccType() {
		return accType;
	}
	
	public void setAccType(String accType) {
		this.accType = accType;
	}
}
