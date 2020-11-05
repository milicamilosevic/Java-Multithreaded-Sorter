/***********************************************************************
 * Modul:  	SerialSorterModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model za sekvencijalno sortiranje fajlova sa
 * 			mogucnoscu pokretanja u odvojenoj niti
 ***********************************************************************/

package model;

import java.util.ArrayList;

import algorithm.SortingAlgorithm;
import settings.Algorithms;

public class SerialSorterModel implements Runnable {

	private ArrayList<SortingAlgorithm> sorters;

	/**
	 * Konstruktor
	 * @param array - niz za sortiranje (kopirace se u svaki sorter)
	 * @param numOfFiles - broj fajlova (broj kopija niza)
	 * @param algorithmName	- izabrani algoritam
	 */
	public SerialSorterModel(ArrayList<Integer> array, int numOfFiles, String algorithmName) {
		sorters = new ArrayList<>();
		for (int i = 0; i < numOfFiles; i++)
			sorters.add(Algorithms.getInstance().createAlgorithm(algorithmName, new ArrayList<>(array)));
	}

	/**
	 * Metoda sekvencijalno sortira zadate nizove
	 */
	public void sort() {
		for(SortingAlgorithm sorter : sorters)
			sorter.sort(null);
	}


	@Override
	public void run() {
		sort();
	}
	
}
