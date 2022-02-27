package Zadanie3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CzytPis {

	public static volatile int K = 3;
	public static volatile int czytelnik_wCzyt = 0;
	public static volatile int pisarz_wCzyt = 0;
	public static volatile Semaphore wolne = new Semaphore(K);
	public static volatile Semaphore pis = new Semaphore(1);
	Random rand = new Random();
	
	public void czytelnik(int N, String nazwaW)
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
				wolne.acquire();
				System.out.print("");
				czytelnik_wCzyt = czytelnik_wCzyt +1;
				System.out.println(">>> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + pisarz_wCzyt + "]");
				Thread.sleep(rand.nextInt(5)+1);
				System.out.println("<<< [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + pisarz_wCzyt + "]");
				wolne.release();
				czytelnik_wCzyt = czytelnik_wCzyt - 1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	public void pisarz(int N, String nazwaW)
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
				pis.acquire();
				pisarz_wCzyt = pisarz_wCzyt + 1;
				for(int j = 1; j<=K;j++)
				{
					wolne.acquire();
				}
				System.out.println("==> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + pisarz_wCzyt + "]");
				Thread.sleep(rand.nextInt(5)+1);
				System.out.println("<== [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + pisarz_wCzyt + "]");
				for(int j = 1; j<=K;j++)
				{
					wolne.release();
				}
				pis.release();
				pisarz_wCzyt = pisarz_wCzyt - 1;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
	
}


class Czytelnik extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private CzytPis CzytPis;

	Czytelnik(int numer, int N, CzytPis CzytPis){
		super("C-"+numer);
		this.N = N;
		this.CzytPis = CzytPis;
	}
	
	public void run() {
		
		CzytPis.czytelnik(N, getName());
		
	}
	
}

class Pisarz extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private CzytPis CzytPis;

	Pisarz(int numer, int N, CzytPis CzytPis){
		super("P-"+numer);
		this.N = N;
		this.CzytPis = CzytPis;
	}
	
	public void run() {
		
		CzytPis.pisarz(N, getName());
		
	}
	
}
