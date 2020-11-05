/***********************************************************************
 * Modul:  	ShellSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Shell sort
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public class ShellSort implements Runnable, SortingAlgorithm {
	private ArrayList<Integer> array;
	private boolean done = false;

	public ShellSort(ArrayList<Integer> array) {
		this.array = array;
	}

	@Override
	public long sort(String[] args) {
		long startTime = System.currentTimeMillis();

		int n = array.size();
		for (int gap = n / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < n; i++) {
				int temp = array.get(i);
				int j;
				for (j = i; j >= gap && array.get(j - gap) > temp; j -= gap)
					array.set(j, array.get(j - gap));
				array.set(j, temp);
			}
		}

		return System.currentTimeMillis() - startTime;
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

	@Override
	public void run() {
		sort(null);
		this.done = true;
	}

}
