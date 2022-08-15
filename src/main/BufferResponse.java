package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class BufferResponse {
	private String creditcard; 
	private double price;
	
	private Boolean messageFull;
	//private Boolean responseFull;
	
	public BufferResponse() {
		this.messageFull = true;
	}
	
	/* Send */
	public synchronized void send(String creditcard, double price) {
		while(!this.messageFull) {
			try {
				this.wait();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.creditcard = creditcard;
		this.price = price;
		
		this.messageFull = false;
		this.notify();
		
		//System.out.println("makeOrder: " + creditcard +"   "+ price);
	}
	
	
	/* Receive */
	public synchronized int receive() {
		while(this.messageFull) {
			try {
				this.wait();
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.messageFull = true;
		this.notify();
		
		
		
		/* functionality code start ------------------------------------------*/
		int valid = -1; //DEFUALT; Not valid
		
		
		
		/* ArrayList of saved credit card info from CreditCards.txt (Acts like like a copy of CreditCards.txt) */
		ArrayList<String> allcreditCards = new ArrayList<String>();
		int index_toReplace = 0; 
		
		/* Try making allcreditCards ArrayList */
		try {
			File ccFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\CreditCards.txt");
			Scanner process = new Scanner(ccFile);
		
			/* Read in all credit card info into ArrayList 'allCreditCards' */
			while(process.hasNextLine()) {
				String ccLine = process.nextLine();
				allcreditCards.add(ccLine);
			}process.close();

		} 
		catch(FileNotFoundException e) {
			System.out.println("[CreditCards.txt File was not found..]");
		}
		/* Exit making allcreditCards ArrayList */
		
		
		
		try {
			//Search CreditCards.txt and see if credit card entered is in system 
			File account = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\CreditCards.txt");
			Scanner scan = new Scanner(account);
			
			//Search for credit card in creditcards.txt
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String values[] = line.split(",");
				/* [0] Full Name, [1] Credit card, [2] Balance */
				
				String savedCC = values[1];
				double balance = Double.parseDouble(values[2]);
				
				/* If credit card entered matches the saved Credit Card */
				/* Check to see if this saved credit card has enough balance for purchase */
				if(creditcard.equals(savedCC)) {
					if(price > balance ) {
						valid = -1; //This credit card DOES NOT have enough balance to pay
						break;
						
						
						
					}else {
						valid = 1; //This credit card has enough balance to pay
						
						double newBalance = (balance - price);
						String overWriteBalance = values[0]+","+values[1]+","+newBalance;
						
						/* Overwrite this credit card info at index in the saved 'CreditCards.txt' arraylist */
						allcreditCards.set(index_toReplace, overWriteBalance);						
						try {
							PrintWriter outputFileCC = new PrintWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\CreditCards.txt");
							
							for(int i = 0; i < allcreditCards.size(); i++) {
								outputFileCC.println(allcreditCards.get(i));
							} outputFileCC.close();
							
						} catch(FileNotFoundException e) {
						      System.out.println("[CreditCards.txt or Stock.txt File was not found...]");
					    }
						break;
					}
				}
				
				
				
				index_toReplace++;
			}
			
			
			scan.close();
		}catch(Exception e){ e.printStackTrace();}
		/* functionality code ends -----------------------------------------------*/
		

		return valid;
	}
}
