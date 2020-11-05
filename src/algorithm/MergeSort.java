/***********************************************************************
 * Modul:  	MergeSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Merge sort
 ***********************************************************************/

package algorithm;

import java.util.ArrayList;

public class MergeSort implements SortingAlgorithm, Runnable {

	private ArrayList<Integer> array;
	private boolean done;

	public MergeSort(ArrayList<Integer> array) {
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
		int l = (args == null) ? 0 : Integer.valueOf(args[0]);
		int r = (args == null) ? array.size() - 1 : Integer.valueOf(args[1]);
		if (l < r) {
			int m = (l + r) / 2;
			String[] args1 = { String.valueOf(l), String.valueOf(m) };
			String[] args2 = { String.valueOf(m + 1), String.valueOf(r) };
			sort(args1);
			sort(args2);
			merge(l, m, r);
		}
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Merges two subarrays
	 * 
	 * @param l - left
	 * @param m - middle
	 * @param r - right
	 */
	private void merge(int l, int m, int r) {
		int n1 = m - l + 1;
		int n2 = r - m;

		ArrayList<Integer> L = new ArrayList<>();
		ArrayList<Integer> R = new ArrayList<>();

		for (int i = 0; i < n1; i++)
			L.add(array.get(l + i));
		for (int i = 0; i < n2; i++)
			R.add(array.get(m + 1 + i));

		int i = 0, j = 0, k = l;
		while (i < n1 && j < n2) {
			if (L.get(i) <= R.get(j)) {
				array.set(k, L.get(i));
				i++;
			} else {
				array.set(k, R.get(j));
				j++;
			}
			k++;
		}

		while (i < n1) {
			array.set(k, L.get(i));
			i++;
			k++;
		}
		while (j < n2) {
			array.set(k, R.get(j));
			j++;
			k++;
		}
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
