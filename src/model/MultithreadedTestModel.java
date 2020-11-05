/***********************************************************************
 * Modul:  	MultithreadedTestModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model sekcije paralelni test
 ***********************************************************************/
package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.data.xy.DefaultXYDataset;

import view.LineChartWindow;
import view.SorterProgressWindow;

public class MultithreadedTestModel implements Runnable {
	private int maxThreads;
	private int minFilesPerThread;
	private String algorithmName;
	private String path;
	private boolean exit = false;

	private ArrayList<Integer> array;
	private ArrayList<ParallelSorterModel> sorters;

	private SorterProgressWindow progressWindow;

	/**
	 * Metoda kreira modele koji u sebi sadrze sortere i koji se uporedo izvrsavaju
	 */
	public void createSorters() {
		sorters = new ArrayList<>();
		for (int i = 1; i <= maxThreads; i *= 2)
			sorters.add(new ParallelSorterModel(new ArrayList<>(array), i, maxThreads * minFilesPerThread / i,
					algorithmName));
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public void setMinFilesPerThread(int minFilesPerThread) {
		this.minFilesPerThread = minFilesPerThread;
	}

	/**
	 * Metoda otvara fajl sa zadate putanje i ucitava podatke iz njega u niz
	 * @param path - putanja fajla
	 */
	private void readDataFromFile(String path) {
		array = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(path)));
			Pattern pattern = Pattern.compile("\\d+");
			while (in.ready()) {
				Matcher matcher = pattern.matcher(in.readLine());
				while (matcher.find())
					array.add(Integer.valueOf(matcher.group()));
			}
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public void run() {
		progressWindow = new SorterProgressWindow();
		progressWindow.getAbort().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit = true;
				progressWindow.dispose();
			}
		});
		progressWindow.setDescription("U\u010ditavanje podataka iz fajla...");
		readDataFromFile(path);
		progressWindow.setDescription("Kreiranje sortera...");
		createSorters();
		progressWindow.setDescription("Izvr\u0161avanje testova...");
		ArrayList<Long> times = new ArrayList<>();

		for (int i = 0; i < sorters.size(); i++) {
			if (exit)
				return;
			progressWindow.setProgress((i + 1) + "/" + sorters.size());
			times.add(sorters.get(i).sort());
		}

		progressWindow.setDescription("Kreiranje grafikona...");
		double data[][] = new double[2][sorters.size()];
		for (int i = 0; i < sorters.size(); i++) {
			data[0][i] = sorters.get(i).getThreads().size();
			data[1][i] = times.get(i);
		}
		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries(algorithmName, data);
		progressWindow.dispose();
		new LineChartWindow(dataset, "Vrijeme sortiranja u zavisnosti od broja tredova", "Broj niti", "Vrijeme [ms]");

	}

}
