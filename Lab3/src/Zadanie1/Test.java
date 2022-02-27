package Zadanie1;


public class Test {

	public static void main(String[] args) {
		
		ProdKons ProdKons = new ProdKons();
		
		Thread p1 = new Producent(1,100,ProdKons);
		Thread p2 = new Producent(2,100,ProdKons);
		Thread p3 = new Producent(3,100,ProdKons);
		Thread p4 = new Producent(4,100,ProdKons);
		
		Thread k1 = new Konsument(1,80,ProdKons);
		Thread k2 = new Konsument(2,80,ProdKons);
		Thread k3 = new Konsument(3,80,ProdKons);
		Thread k4 = new Konsument(4,80,ProdKons);
		Thread k5 = new Konsument(5,80,ProdKons);
		
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		
		k1.start();
		k2.start();
		k3.start();
		k4.start();
		k5.start();
		
		try {
			p1.join();
			p2.join();
			p3.join();
			p4.join();
			
			k1.join();
			k2.join();
			k3.join();
			k4.join();
			k5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
