/***********************************************************************
 * Modul:  	SinglethreadedModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model sekcije serijski test
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
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.jfree.data.xy.DefaultXYDataset;

import algorithm.SortingAlgorithm;
import helpers.ArrayWithPath;
import settings.Algorithms;
import view.LineChartWindow;
import view.SorterProgressWindow;
import view.ObserverInterface;
import view.SorterFinishWindow;

public class SinglethreadedTestModel implements SubjectInterface, Runnable {

	private ArrayList<ObserverInterface> observers;

	private String algorithmName = "";
	private ArrayList<PathSectionModel> pathModels = null;
	private boolean exit = false;

	private SorterProgressWindow progressWindow;

	public SinglethreadedTestModel() {
		this.pathModels = new ArrayList<>();
		this.observers = new ArrayList<>();
	}

	public ArrayList<PathSectionModel> getPathModels() {
		return pathModels;
	}

	/**
	 * Metoda kreira modele podsekcija na osnovu proslijedjenih fajlova
	 * @param files - niz fajlova koji se prosljedjuje
	 */
	public void addFiles(File[] files) {
		for (File f : files) {
			Boolean repeated = false;
			for (PathSectionModel model : pathModels) {
				if (model.getPath().equals(f.toString()))
					repeated = true;
			}
			if (!repeated)
				pathModels.add(new PathSectionModel(pathModels.size() + 1, f.toString()));
		}
	}

	/**
	 * Metoda uklanja sve fajlove
	 */
	public void removeAllFiles() {
		pathModels = new ArrayList<>();
	}

	/**
	 * Metoda uklanja odredjenu podsekciju
	 * @param modelToRemove - model koji se uklanja
	 */
	public void removeFile(PathSectionModel modelToRemove) {
		// Nemoguce je prolaziti kroz listu ovakvog tipa i u isto vrijeme uklanjati
		// elemente iz liste.
		// Zbog toga je potrebno napraviti kopiju.
		ArrayList<PathSectionModel> temp = new ArrayList<>();

		for (PathSectionModel model : pathModels)
			if (!modelToRemove.equals(model))
				temp.add(model);

		pathModels = new ArrayList<>();
		for (int i = 0; i < temp.size(); i++) {
			pathModels.add(new PathSectionModel(i + 1, temp.get(i).getPath()));
		}
	}

	@Override
	public void addObserver(ObserverInterface observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(ObserverInterface observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (ObserverInterface o : observers)
			o.update();
	}

	@Override
	public void run() {
		ArrayList<ArrayWithPath> arraysCollection = new ArrayList<>();

		progressWindow = new SorterProgressWindow();
		progressWindow.setDescription("U\u010ditavanje podataka iz fajlova...");
		progressWindow.getAbort().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit = true;
				progressWindow.dispose();
			}
		});

		// Ucitavanje podataka iz fajlova
		for (PathSectionModel sectionModel : pathModels) {
			try {
				progressWindow.setProgress((pathModels.indexOf(sectionModel) + 1) + "/" + pathModels.size());
				BufferedReader in = new BufferedReader(new FileReader(sectionModel.getPath()));
				Pattern pattern = Pattern.compile("\\d+");
				ArrayList<Integer> array = new ArrayList<>();
				while (in.ready()) {
					Matcher matcher = pattern.matcher(in.readLine());
					while (matcher.find()) {
						array.add(Integer.valueOf(matcher.group()));
					}
				}
				in.close();
				arraysCollection.add(new ArrayWithPath(array, sectionModel.getPath()));
			} catch (FileNotFoundException e) {
				sectionModel.setMessage("GRE\u0160KA! Fajl nije pronaðen.");
				sectionModel.updateView();
			} catch (IOException e) {
				sectionModel.setMessage("GRE\\u0160KA! Pristup odbijen.");
				sectionModel.updateView();
			}
		}

		// Redjanje nizova po broju elemenata
		Comparator<ArrayWithPath> comparator = new Comparator<ArrayWithPath>() {
			@Override
			public int compare(ArrayWithPath o1, ArrayWithPath o2) {
				return Integer.valueOf(o1.getArray().size()).compareTo(Integer.valueOf(o2.getArray().size()));
			}
		};
		arraysCollection.sort(comparator);

		// Kreiranje sortera
		ArrayList<SortingAlgorithm> algorithms = new ArrayList<>();
		for (ArrayWithPath arrayWithPath : arraysCollection) {
			algorithms.add(Algorithms.getInstance().createAlgorithm(algorithmName, arrayWithPath.getArray()));
		}

		if (algorithms.size() == 0) {
			progressWindow.dispose();
			JOptionPane.showMessageDialog(progressWindow, "Nema fajlova za sortiranje.", "",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// Sortiranje i prikaz rezultata
		ArrayList<Integer> ammounts = new ArrayList<>();
		ArrayList<Long> times = new ArrayList<>();
		progressWindow.setDescription("Sortiranje podataka...");
		progressWindow.setProgress("1/" + algorithms.size());
		for (SortingAlgorithm algorithm : algorithms) {
			if (exit)
				return;
			progressWindow.setProgress((algorithms.indexOf(algorithm) + 1) + "/" + algorithms.size());
			times.add(algorithm.sort(null));
			ammounts.add(algorithm.getArray().size());
		}
		progressWindow.setProgress(" ");
		progressWindow.setDescription("Kreiranje grafikona...");
		double[][] results = new double[2][ammounts.size()];
		for (int i = 0; i < ammounts.size(); i++) {
			results[0][i] = ammounts.get(i);
			results[1][i] = times.get(i);
		}

		DefaultXYDataset dataset = new DefaultXYDataset();
		dataset.addSeries(algorithmName, results);
		progressWindow.dispose();
		new SorterFinishWindow(arraysCollection, algorithms.size() + "/" + pathModels.size() + " sortirano");
		new LineChartWindow(dataset, "Vrijeme sortiranja u zavisnosti od koli\u010dine podataka", "Koli\u010dina podataka",
				"Vrijeme [ms]");
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
}
