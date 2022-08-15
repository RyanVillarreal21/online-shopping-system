package main;
import java.util.Random;

class bankapp extends Thread{
	
	private BufferResponse buffe;
	int valid =0; int aNum;
	
	public bankapp(BufferResponse buffe ) {
		this.buffe = buffe;
	}
	
	public void rerun() {
		
		aNum = -1;
		valid = buffe.receive();
		
		
		if(valid == 1) {
			Random rand = new Random(); //instance of random class
		    int upperbound = 10000;//generate random values from 0-10,000
		    int Authorization_number = rand.nextInt(upperbound); 
		    aNum = Authorization_number; //Access from the customerApp makeOrderHandler
		    
		}else {
			
			aNum = -1;
		}
		
	    
	    
	   
		
	}
	
	public void run() {
			//valid = buffe.receive();
	}	
}