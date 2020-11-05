/***********************************************************************
 * Modul:  	GeneratorModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model sekcije generator test podataka
 ***********************************************************************/

package model;

import java.util.ArrayList;

import view.ObserverInterface;

public class GeneratorModel implements SubjectInterface {
	private ArrayList<ObserverInterface> observers = null;
	private ArrayList<GeneratorSectionModel> generators;

	public GeneratorModel() {
		generators = new ArrayList<>();
		GeneratorSectionModel model = new GeneratorSectionModel();
		model.setSectionNumber(1);
		generators.add(model);
	}

	/**
	 * Metoda zaduzena za dodavanje nove podsekcije
	 * @param generator - podsekcija koja se dodaje
	 */
	public void addGenerator(GeneratorSectionModel generator) {
		generator.setSectionNumber(generators.size() + 1);
		generators.add(generator);

	}

	/**
	 * Metoda uklanja sve podsekcije
	 */
	public void clearGenerators() {
		generators = new ArrayList<>();
	}

	/**
	 * @return - sve podsekcije koje se nalaze unutar komponente
	 */
	public ArrayList<GeneratorSectionModel> getGenerators() {
		return generators;
	}

	@Override
	public void addObserver(ObserverInterface observer) {
		if (observers == null)
			observers = new ArrayList<>();
		observers.add(observer);
	}

	@Override
	public void removeObserver(ObserverInterface observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (int i = 0; i < generators.size(); i++) {
			generators.get(i).setSectionNumber(i + 1);
		}

		for (ObserverInterface o : observers) {
			o.update();
		}
	}

	/**
	 * Metoda poziva generisanje nad svim podsekcijama
	 */
	public void generateAll() {
		for (GeneratorSectionModel sectionModel : generators)
			if (sectionModel.validate())
				sectionModel.generate();
	}

}