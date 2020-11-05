/***********************************************************************
 * Modul:  	SelectionSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Selection sort
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public class SelectionSort implements SortingAlgorithm, Runnable {

	private ArrayList<Integer> array;
	private boolean done;

	public SelectionSort(ArrayList<Integer> array) {
		this.array = array;
		this.done = false;
	}

	@Override
	public void run() {
		sort(null);
		done = true;
	}

	@Override
	public long sort(String[] args) {
		long startTime = System.currentTimeMillis();
		int n = array.size();

		for (int i = 0; i < n - 1; i++) {
			int min_idx = i;
			for (int j = i + 1; j < n; j++)
				if (array.get(j) < array.get(min_idx))
					min_idx = j;

			int swap = array.get(min_idx);
			array.set(min_idx, array.get(i));
			array.set(i, swap);
		}

		return System.currentTimeMillis() - startTime;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public ArrayList<Integer> getArray() {
		return array;
	}

	@Override
	public void setArray(ArrayList<Integer> array) {
		this.array = array;
	}

}
