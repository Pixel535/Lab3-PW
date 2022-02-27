package Zadanie1;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProdKons extends Thread{

	public static volatile int p = 0;
	public static volatile int k = 0;
	public static volatile int MAX = 10;
	public static volatile String[] buf = new String[MAX];
	public static volatile Semaphore wolne = new Semaphore(MAX);
	public static volatile Semaphore zajete = new Semaphore(0);
	public static volatile Semaphore produkowanie = new Semaphore(1);
	public static volatile Semaphore konsumowanie = new Semaphore(1);
	Random rand = new Random();
	
	
	public void producent(int N, String nazwaW) {
		for(int i = 0; i<N; i++)
		{
			try {
				Thread.sleep(rand.nextInt(9)+1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int x = rand.nextInt(99);
			try {
				wolne.acquire();
				produkowanie.acquire();
				//System.out.println("["+ nazwaW + ", " + i + ", " + x + "]");
				buf[p] = "["+ nazwaW + ", " + i + ", " + x + "]";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			zajete.release();
			p = (p+1)%MAX;
			produkowanie.release();
			
			
		}
	}
	
	public void konsument(int N, String nazwaW) {
		String Dana = null;
		for(int i = 0; i<N; i++)
		{
			try {
				zajete.acquire();
				konsumowanie.acquire();
				Dana = buf[k];
				buf[k] = null;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wolne.release();
			k = (k+1)%MAX;
			konsumowanie.release();
			try {
				Thread.sleep(rand.nextInt(11)+2);
				System.out.println("["+ nazwaW + ", " + i + "] >> Dana = " + Dana);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

class Konsument extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private ProdKons ProdKons;

	Konsument(int numer, int N, ProdKons ProdKons){
		super("K-"+numer);
		this.N = N;
		this.ProdKons = ProdKons;
	}
	
	public void run() {
		
		ProdKons.konsument(N, getName());
		
	}
	
}

  class Producent extends Thread{
	
	private int numer;
	private int N;
	String nazwaW;
	private ProdKons ProdKons;
	
	Producent(int numer, int N, ProdKons ProdKons){
		super("P-"+numer);
		this.N = N;
		this.ProdKons = ProdKons;
	}
	
	public void run() {
		ProdKons.producent(N, getName());
	}
	
}
