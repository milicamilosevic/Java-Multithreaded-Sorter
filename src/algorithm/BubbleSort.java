/***********************************************************************
 * Modul:  	BubbleSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Bubble sort
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public class BubbleSort implements SortingAlgorithm, Runnable {
	private ArrayList<Integer> array;
	private Boolean done = false;

	public BubbleSort(ArrayList<Integer> array) {
		this.array = array;
		this.done = false;
	}

	@Override
	public long sort(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < array.size() - 1; i++) {
			for (int j = 0; j < array.size() - i - 1; j++) {
				int temp = array.get(j);
				array.set(j, array.get(j + 1));
				array.set(j + 1, temp);
			}
		}
		return System.currentTimeMillis() - startTime;
	}

	@Override
	public void run() {
		sort(null);
		this.done = true;
	}

	@Override
	public ArrayList<Integer> getArray() {
		return this.array;
	}

	@Override
	public void setArray(ArrayList<Integer> array) {
		this.array = array;
	}

	@Override
	public boolean isDone() {
		return done;
	}

}
