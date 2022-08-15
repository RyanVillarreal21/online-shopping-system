package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;



public class App extends Application{
	
	/* Your window; displays Scenes */
	private static Stage Window;
	
	/* Objects of Types(Class) used in this class */
	Login log = new Login();
	Create cre = new Create();
	
	/* This Class's Scene */
	private static Scene App_Scene;
	
	/* Message & Response Buffer */
	private static BufferResponse buffe = new BufferResponse();
	
	/* Separate Threads */
	private static MakeOrder makeOrder = new MakeOrder(buffe);
	private static bankapp bank = new bankapp(buffe);
	
	
	/* Launch Application */
	public static void main(String[] args) {
	
		/* Start Threads */
		makeOrder.start();
		bank.start();
		
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		Window = primaryStage;
		
		/* Labels to prompt text */
		Label promptStoreName = new Label("Welcome to Alucard's Tech!");
		promptStoreName.setFont(Font.font("Verdana", 30));
		
	
		/*Three Buttons w/ Actions: Login, Create, Quit */
		Button loginButton = new Button("Login Account");
		loginButton.setOnAction(e -> log.LoginGUI());
		Button createButton = new Button("Create Account");
		createButton.setOnAction(e -> cre.CreateGUI());
		Button quitButton = new Button("Quit Program");
		quitButton.setOnAction(e -> Platform.exit());
		
	
		
		VBox AppPage_vbox = new VBox(20, promptStoreName, loginButton, createButton, quitButton);
		AppPage_vbox.setAlignment(Pos.CENTER);
		App_Scene = new Scene(AppPage_vbox, 1280, 720);
		
		
		Window.setTitle("App");
		Window.setScene(App_Scene);
		Window.show();
	}
	
	/*This method is used in other Classes's Methods to get the 'Window' Stage (primaryStage) */
	public static Stage getWindow() {
		return Window;
	}
	
	/* This method is for the button "Return to Menu" */
	public static Scene getAppScene() {
		return App_Scene;
	}
	
	
	
	
	/* This method is for to get private static thread MakeOrder*/
	public static MakeOrder getmakeOrderThread() {
		return makeOrder;
	}
	
	/* This method is for to get private static thread BankApp*/
	public static bankapp getbankThread() {
		return bank;
	}
}
	





