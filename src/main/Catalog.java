package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Catalog {
	
	ArrayList<itemDetail> catalogArray = new ArrayList<itemDetail>();

		/* Initialize the Catalog Array from Catalog.txt & Stock.txt */
		/* Default Constructor */
		Catalog() {
			try {
				File inputFile = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Catalog.txt");
				Scanner CatalogFile = new Scanner(inputFile);
				File inputFile2 = new File("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Stock.txt");
				Scanner StockFile = new Scanner(inputFile2);
			
			
				while(CatalogFile.hasNext()) {
					String Line = CatalogFile.nextLine();
					String values[] = Line.split(", ");
				
					String Line2 = StockFile.nextLine();
					String values2[] = Line2.split(", ");
				
				
					/* Catalog - Values:  [0] Name, [1] Description, [2] Reg Price, [3] Prem Price */
					/* Stock -   Values2: [0] Name, [1] Total items, [2] avail, [3] reserved */
					String itemName = values[0];
					String description = values[1];
					double regularPrice = Double.parseDouble(values[2]);
					double premiumPrice = Double.parseDouble(values[3]);
					int available = Integer.parseInt(values2[2]);		
					int reserved = Integer.parseInt(values2[3]);			
				
				
					itemDetail item = new itemDetail(itemName, description, regularPrice, premiumPrice, available, reserved);
					catalogArray.add(item);	
				}CatalogFile.close(); StockFile.close();
			}
			catch(FileNotFoundException e) {
				System.out.println("[Catalog.txt or Stock.txt File was not found...]");
			}
			
		}
		
		
		/* OverWrites to existing Catalog.txt & Stock.txt files with values from Catalog Array */
		/* Any changes done to the Catalog Array MUST call this function after to store changes in the files */
		public void updateCatalogFiles() {
			try {
				PrintWriter outputFileCatalog = new PrintWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Catalog.txt");
				PrintWriter outputFileStock = new PrintWriter("C:\\Users\\gabri\\eclipse-workspace\\OSS v1 GUI\\src\\Stock.txt");
			
				for (int i = 0; i < catalogArray.size(); i++) { 				//for each element/item in the catalog
					itemDetail item = (itemDetail) catalogArray.get(i);		//get the item value and store to local var item
				
					outputFileCatalog.println(item.itemName + ", " + item.description + ", " + item.regularPrice + ", " + item.premiumPrice);
					outputFileStock.println(item.itemName + ", " + (item.available + item.reserved) + ", " + item.available + ", " + item.reserved);
				
				}outputFileCatalog.close();outputFileStock.close();
			}
			catch(FileNotFoundException e) {
				System.out.println("[Catalog.txt or Stock.txt File was not found...]");
			}
		}
		
		
		
		public void displayCatalog() {
			System.out.println("[Displaying our Catalog items...]");
			System.out.println();
			
			for (int i = 0; i < catalogArray.size(); i++) { 				//for each element/item in the catalog
				System.out.println("Item #"+i);						//Item index in the Catalog Array
				itemDetail item = (itemDetail) catalogArray.get(i);		//get the element/item value and store to local var "item
				item.displayDetail();								//Display that items detail
			}
		}

}
