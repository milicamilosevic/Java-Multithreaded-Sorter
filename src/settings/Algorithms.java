/***********************************************************************
 * Modul:  	Algorithms.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Klasa zaduzena za cuvanje referenci na postojece algoritme
 * 			sortiranja
 ***********************************************************************/

package settings;

import java.util.ArrayList;
import java.util.Vector;

import algorithm.BubbleSort;
import algorithm.HeapSort;
import algorithm.InsertionSort;
import algorithm.MergeSort;
import algorithm.QuickSort;
import algorithm.SelectionSort;
import algorithm.ShellSort;
import algorithm.SortingAlgorithm;

public class Algorithms {

	private static Algorithms instance = null;

	private Vector<String> list;

	/**
	 * Privatni konstruktor. Singleton klasa
	 */
	private Algorithms() {
		list = new Vector<>();
		list.add("Quick Sort");
		list.add("Bubble Sort");
		list.add("Insertion Sort");
		list.add("Merge Sort");
		list.add("Heap Sort");
		list.add("Selection Sort");
		list.add("Shell Sort");
	};

	/**
	 * Dohvatanje singleton instance klase
	 * @return - instanca
	 */
	public static Algorithms getInstance() {
		if (instance == null)
			instance = new Algorithms();
		return instance;
	}

	/**
	 * Metoda vradca imena svih postojecih algoritama u listi
	 */
	public Vector<String> getList() {
		return list;
	}

	/**
	 * Metoda na osnovu imena algoritma vraca njegovu instancu
	 * @param name - ime algoritma
	 * @param array - niz za sortiranje
	 * @return  - algoritam sa dodatim nizom
	 */
	public SortingAlgorithm createAlgorithm(String name, ArrayList<Integer> array) {
		if (name.equals(list.get(0)))
			return new QuickSort(array);
		else if (name.equals(list.get(1)))
			return new BubbleSort(array);
		else if (name.equals(list.get(2)))
			return new InsertionSort(array);
		else if (name.equals(list.get(3)))
			return new MergeSort(array);
		else if (name.equals(list.get(4)))
			return new HeapSort(array);
		else if (name.equals(list.get(5)))
			return new SelectionSort(array);
		else if (name.equals(list.get(6)))
			return new ShellSort(array);
		return null;
	}

}
