package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Stock {
	
	
	public void viewStock() {
		
		try {
			System.out.println("[Displaying Stock Status...]"+'\n');
			File inputFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Stock.txt");
			Scanner StockFile = new Scanner(inputFile);
		
			while(StockFile.hasNext()) {
				String Line = StockFile.nextLine();
				String values[] = Line.split(", ");
			
				//Align/Spacing with GUI
				System.out.println("[Item]"+values[0] + ":"+ "Total Items: "+values[1]+" - Avaiable: "+values[2]+" - Reserved: "+values[3]);
		
			}StockFile.close();
		} 
		catch(FileNotFoundException e) {
			System.out.println("[Stock.txt File was not found..]");
		}
	}
	
	
	
	
	public String getViewStock() {
		String Stock = "";
		String itemStock;
		
		try {
			//System.out.println("[Displaying Stock Status...]"+'\n');
			File inputFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Stock.txt");
			Scanner StockFile = new Scanner(inputFile);
		
			while(StockFile.hasNext()) {
				String Line = StockFile.nextLine();
				String values[] = Line.split(", ");
			
				//Align/Spacing with GUI
				
				
				itemStock = "[Item]:"+String.format("%-25s", values[0]) + "[Total Items]: "+values[1]+" 		- Avaiable: "+values[2]+"		 - Reserved: "+values[3] +'\n';
				Stock = Stock+itemStock;
		
			}StockFile.close();
		} 
		catch(FileNotFoundException e) {
			//System.out.println("[Stock.txt File was not found..]");
		}
		
		return Stock;
	}
	
	
	
	
	
	
}
