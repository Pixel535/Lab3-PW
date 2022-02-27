package Zadanie4;


public class Test {

	public static void main(String[] args) {
		
		Sem_Filo Sem_Filo = new Sem_Filo();
		
		Thread f1 = new Filozof(0,80,Sem_Filo, 0);
		Thread f2 = new Filozof(1,80,Sem_Filo, 1);
		Thread f3 = new Filozof(2,80,Sem_Filo, 2);
		Thread f4 = new Filozof(3,80,Sem_Filo, 3);
		Thread f5 = new Filozof(4,80,Sem_Filo, 4);
		
		f1.start();
		f2.start();
		f3.start();
		f4.start();
		f5.start();
		
		try {
			f1.join();
			f2.join();
			f3.join();
			f4.join();
			f5.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
