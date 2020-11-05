/***********************************************************************
 * Modul:  	QuickSort.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise algoritam sortiranja - Quick sort
 ***********************************************************************/
package algorithm;

import java.util.*;

public class QuickSort implements SortingAlgorithm, Runnable {

	private ArrayList<Integer> array;
	private int down;
	private int up;
	private Boolean done = false;

	/** @param array */
	public QuickSort(ArrayList<Integer> array) {
		this.array = array;
		down = 0;
		up = array.size() - 1;
	}

	public QuickSort(ArrayList<Integer> array, int down, int up) {
		this.array = array;
		this.down = down;
		this.up = up;
	}

	/** @param args */
	public long sort(String[] args) {
		long startTime = System.currentTimeMillis();
		int down = this.down;
		int up = this.up;
		if (args != null) {
			down = Integer.parseInt(args[0]);
			up = Integer.parseInt(args[1]);
		}
		if (down < up) {
			int j = partition(down, up);
			String[] newArgs = { String.format("%d", down), String.format("%d", j - 1) };
			sort(newArgs);
			newArgs[0] = String.format("%d", j + 1);
			newArgs[1] = String.valueOf(up);
			sort(newArgs);
		}
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Uzima pivot element i rasporedjuje elemente u nizu u odnosu na taj element
	 * @param down - donja granica
	 * @param up - gornja granica
	 * @return	pivot element
	 */
	private int partition(int down, int up) {
		int i = down, j = up;
		int pivot = array.get(down);
		while (i < j) {
			while (array.get(i) <= pivot && i < j)
				i++;
			while (array.get(j) > pivot) {
				j--;
			}
			if (i < j) {
				int temp = array.get(i);
				array.set(i, array.get(j));
				array.set(j, temp);
			}
		}
		array.set(down, array.get(j));
		array.set(j, pivot);
		return j;
	}

	@Override
	public void run() {
		String[] args = { String.valueOf(down), String.valueOf(up) };
		sort(args);
		done = true;
	}

	@Override
	public boolean isDone() {
		return done;
	}

	@Override
	public void setArray(ArrayList<Integer> array) {
		this.array = array;
		down = 0;
		up = array.size() - 1;
	}

	@Override
	public ArrayList<Integer> getArray() {
		return this.array;
	}
}