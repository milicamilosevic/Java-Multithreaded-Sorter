/***********************************************************************
 * Modul:  	ParallelSorterModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model za uporedno izvrsavanje sortera
 ***********************************************************************/

package model;

import java.util.ArrayList;

public class ParallelSorterModel {
	private ArrayList<Thread> threads;

	public ParallelSorterModel(ArrayList<Integer> array, int threadCount, int filesPerThread, String algorithmName) {
		this.threads = new ArrayList<>();
		for (int i = 0; i < threadCount; i++)
			threads.add(new Thread(new SerialSorterModel(new ArrayList<>(array), filesPerThread, algorithmName)));
	}

	/**
	 * Pokretanje sortera i mjerenje ukupnog vremena
	 * @return - proteklo vrijeme u milisekundama
	 */
	public long sort() {
		long startTime = System.currentTimeMillis();
		for (Thread th : threads)
			th.start();
		for (Thread th : threads)
			try {
				th.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return System.currentTimeMillis() - startTime;
	}

	
	public ArrayList<Thread> getThreads() {
		return threads;
	}
}
