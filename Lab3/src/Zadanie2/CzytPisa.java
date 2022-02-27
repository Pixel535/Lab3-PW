package Zadanie2;

import java.util.Random;
import java.util.concurrent.Semaphore;

import Zadanie1.ProdKons;

public class CzytPisa {

	public static int czytelnik_akt = 0;//ac
	public static int czytelnik_wCzyt = 0;//dc
	public static int pisarz_akt = 0;//ap
	public static int pisarz_wCzyt = 0;//dp
	public static Semaphore czyt = new Semaphore(0);
	public static Semaphore pis = new Semaphore(0);
	public static Semaphore czytelnicy = new Semaphore(1);//chron
	public static Semaphore pisarze = new Semaphore(1);//mutex pis
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
				czytelnicy.acquire();
				System.out.println(">>> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnik_akt = czytelnik_akt+1;
				if(pisarz_akt == 0)
				{
					czytelnik_wCzyt = czytelnik_wCzyt +1;
					czyt.release();
				}
				System.out.println(">>> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnicy.release();
				czyt.acquire();
				Thread.sleep(rand.nextInt(5)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				czytelnicy.acquire();
				System.out.println("<<< [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnik_wCzyt = czytelnik_wCzyt - 1;
				czytelnik_akt = czytelnik_akt-1;
				if(czytelnik_wCzyt == 0)
				{
					while(pisarz_wCzyt < pisarz_akt)
					{
						pisarz_wCzyt = pisarz_wCzyt + 1;
						pis.release();
					}
				}
				System.out.println("<<< [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnicy.release();
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
				czytelnicy.acquire();
				System.out.println("==> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				pisarz_akt = pisarz_akt + 1;
				if(czytelnik_akt == 0)
				{
					pisarz_wCzyt = pisarz_wCzyt + 1;
					pis.release();
				}
				System.out.println("==> [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnicy.release();
				pis.acquire();
				pisarze.acquire();
				Thread.sleep(rand.nextInt(5)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pisarze.release();
			try {
				czytelnicy.acquire();
				System.out.println("<== [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				pisarz_wCzyt = pisarz_wCzyt - 1;
				pisarz_akt = pisarz_akt - 1;
				if(pisarz_wCzyt == 0)
				{
					while(czytelnik_wCzyt < czytelnik_akt)
					{
						czytelnik_wCzyt = czytelnik_wCzyt + 1;
						czyt.release();
					}
				}
				System.out.println("<== [" + nazwaW + ", " + i + "] :: [" + czytelnik_wCzyt + ", " + (czytelnik_akt-czytelnik_wCzyt) + ", " + pisarz_wCzyt + ", " + (pisarz_akt-pisarz_wCzyt) + "]");
				czytelnicy.release();
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
	private CzytPisa CzytPisa;

	Czytelnik(int numer, int N, CzytPisa CzytPisa){
		super("C-"+numer);
		this.N = N;
		this.CzytPisa = CzytPisa;
	}
	
	public void run() {
		
		CzytPisa.czytelnik(N, getName());
		
	}
	
}

class Pisarz extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private CzytPisa CzytPisa;

	Pisarz(int numer, int N, CzytPisa CzytPisa){
		super("P-"+numer);
		this.N = N;
		this.CzytPisa = CzytPisa;
	}
	
	public void run() {
		
		CzytPisa.pisarz(N, getName());
		
	}
	
}
