package main;

class MakeOrder extends Thread{
	
	String cc; double price;
	private BufferResponse buffe;
	
	public MakeOrder(BufferResponse buffe) {
		this.buffe = buffe;
		
	}
	public void sendcred(String cc, double price) {
		this.cc = cc;
		this.price = price;
		buffe.send(cc, price);
	}
	
	public void run() {
		
			//buffe.send(cc, price);
			
			//System.exit(1);
		
	}
}
