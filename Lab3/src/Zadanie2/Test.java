package Zadanie2;

public class Test {

	public static void main(String[] args) {
		CzytPisa sem = new CzytPisa();
		
		Czytelnik czytelnik1 = new Czytelnik(0,100,sem);
		Czytelnik czytelnik2 = new Czytelnik(1,100,sem);
		Czytelnik czytelnik3 = new Czytelnik(2,100,sem);
		Czytelnik czytelnik4 = new Czytelnik(3,100,sem);
		
		Pisarz pisarz1 = new Pisarz(0,100,sem);
		Pisarz pisarz2 = new Pisarz(1,100,sem);
		
		czytelnik1.start();
		czytelnik2.start();
		czytelnik3.start();
		czytelnik4.start();
		
		pisarz1.start();
		pisarz2.start();
		
		try {
			czytelnik1.join();
			czytelnik2.join();
			czytelnik3.join();
			czytelnik4.join();
			
			
			pisarz1.join();
			pisarz2.join();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
