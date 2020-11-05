/***********************************************************************
 * Modul:  	HeapSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Heap sort
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public class HeapSort implements SortingAlgorithm, Runnable {

	private ArrayList<Integer> array;
	private boolean done;

	public HeapSort(ArrayList<Integer> array) {
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
		for (int i = n / 2 - 1; i >= 0; i--)
			heapify(n, i);

		for (int i = n - 1; i > 0; i--) {
			int swap = array.get(0);
			array.set(0, array.get(i));
			array.set(i, swap);
			heapify(i, 0);
		}
		return System.currentTimeMillis() - startTime;
	}

	private void heapify(int n, int i) {
		int largest = i;
		int l = 2 * i + 1;
		int r = 2 * i + 2;

		if (l < n && array.get(l) > array.get(largest))
			largest = l;
		if (r < n && array.get(r) > array.get(largest))
			largest = r;

		if (largest != i) {
			int swap = array.get(i);
			array.set(i, array.get(largest));
			array.set(largest, swap);
			heapify(n, largest);
		}
	}

	@Override
	public ArrayList<Integer> getArray() {
		return array;
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
