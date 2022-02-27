package Zadanie4;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Sem_Filo {

	public static volatile Semaphore dopusc = new Semaphore(4);
	public static volatile Semaphore[] widelec = new Semaphore [] {
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1),
			new Semaphore(1),
	};
	Random rand = new Random();
	public static volatile String[] widelceWartosc = new String[] {"W","W","W","W","W"};
	public static volatile int iloscWidelcy = 0;
	
	public void filozof(int N, String nazwaW, int nr)
	{
		for(int i = 0; i<N; i++)
		{
			try {
				Thread.sleep(rand.nextInt(11)+5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				dopusc.acquire();
				widelec[nr].acquire();
				widelceWartosc[nr] = String.valueOf(nr);
				widelec[(nr+1) % 5].acquire();
				widelceWartosc[(nr+1) % 5] = String.valueOf(nr);
				System.out.print("");
				iloscWidelcy = iloscWidelcy + 1;
				System.out.println(">>> [" + nazwaW + ", " + i + "] :: ["+ iloscWidelcy + ", " + widelceWartosc[0] + ", " + widelceWartosc[1] + ", " + widelceWartosc[2] +", " + widelceWartosc[3] +", " + widelceWartosc[4] +"]");
				Thread.sleep(rand.nextInt(5)+1);
				System.out.println("<<< [" + nazwaW + ", " + i + "] :: ["+ iloscWidelcy + ", " + widelceWartosc[0] + ", " + widelceWartosc[1] + ", " + widelceWartosc[2] +", " + widelceWartosc[3] +", " + widelceWartosc[4] +"]");
				System.out.print("");
				iloscWidelcy = iloscWidelcy - 1;
				widelec[nr].release();
				widelceWartosc[nr] = "W";
				widelec[(nr+1) % 5].release();
				widelceWartosc[(nr+1) % 5] = "W";
				dopusc.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	
}

class Filozof extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private Sem_Filo Sem_Filo;
	private int nr;
	
	Filozof(int numer, int N, Sem_Filo Sem_Filo, int nr){
		super("F-"+numer);
		this.N = N;
		this.Sem_Filo = Sem_Filo;
		this.nr = nr;
	}
	
	public void run() {
		
	Sem_Filo.filozof(N, getName(), nr);
		
	}
	
}
