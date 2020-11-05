/***********************************************************************
 * Modul:  	InsertionSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Insertion sort
 ***********************************************************************/
package algorithm;

import java.util.ArrayList;

public class InsertionSort implements SortingAlgorithm, Runnable {
	private ArrayList<Integer> array;
	private Boolean done = false;

	public InsertionSort(ArrayList<Integer> array) {
		this.array = array;
	}

	@Override
	public void run() {
		sort(null);
		this.done = true;
	}

	@Override
	public long sort(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < array.size(); i++) {
			int key = array.get(i);
			int j = i - 1;
			while (j >= 0 && array.get(j) > key) {
				array.set(j + 1, array.get(j));
				j = j - 1;
			}
			array.set(j + 1, key);
		}
		return System.currentTimeMillis() - startTime;
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
