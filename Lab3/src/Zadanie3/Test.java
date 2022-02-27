package Zadanie3;


public class Test {

	public static void main(String[] args) {
		
		CzytPis CzytPis = new CzytPis();
		
		Thread c1 = new Czytelnik(1,100,CzytPis);
		Thread c2 = new Czytelnik(2,100,CzytPis);
		Thread c3 = new Czytelnik(3,100,CzytPis);
		Thread c4 = new Czytelnik(4,100,CzytPis);
		Thread c5 = new Czytelnik(5,100,CzytPis);
		
		Thread p1 = new Pisarz(1,100,CzytPis);
		Thread p2 = new Pisarz(2,100,CzytPis);
		
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
		
		p1.start();
		p2.start();
		
		try {
			c1.join();
			c2.join();
			c3.join();
			c4.join();
			c5.join();
			
			p1.join();
			p2.join();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
