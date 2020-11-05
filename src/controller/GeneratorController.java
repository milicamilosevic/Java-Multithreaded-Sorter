/***********************************************************************
 * Modul:  	GeneratorController.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise kontroler sekcije generator test podataka
 ***********************************************************************/

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.GeneratorModel;
import model.GeneratorSectionModel;
import view.GeneratorView;

public class GeneratorController {

	private GeneratorModel model;
	private GeneratorView view;

	public GeneratorController(GeneratorModel model, GeneratorView view) {
		this.model = model;
		this.view = view;
		view.getPlus().addActionListener(addButtonListener);
		view.getGenerate().addActionListener(generateButtonListener);
		view.getClear().addActionListener(clearButtonListener);
	}

	/**
	 * Listener zaduzen za dodavanje nove sekcije unutar generatora
	 */
	private ActionListener addButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.updateModels();
			GeneratorSectionModel newSection = new GeneratorSectionModel();

			if (model.getGenerators().size() > 0)
				if (model.getGenerators().get(model.getGenerators().size() - 1).validate()) {
					GeneratorSectionModel previousSection = model.getGenerators().get(model.getGenerators().size() - 1);
					if (!previousSection.getPath().equals(""))
						newSection.setPath(copyPath(previousSection.getPath()));
					newSection.setAmmount(previousSection.getAmmount());
					newSection.setFrom(previousSection.getFrom());
					newSection.setTo(previousSection.getTo());
				}

			model.addGenerator(newSection);
			model.notifyObservers();
		}

		/**
		 * Metoda kopira putanju dodajuci joj redni broj
		 * @param oldPath	-	Putanja koja se kopira
		 * @return	Nova putanja
		 */
		private String copyPath(String oldPath) {
			Path path = Paths.get(oldPath);
			Pattern pattern1 = Pattern.compile("(.+)(\\s\\((\\d+)\\))(\\.[\\d\\w]+)");
			Matcher matcher1 = pattern1.matcher(path.toString());
			Pattern pattern2 = Pattern.compile("(.+)(\\s\\((\\d+)\\))$");
			Matcher matcher2 = pattern2.matcher(path.toString());
			Pattern pattern3 = Pattern.compile("(.+)(\\..+)");
			Matcher matcher3 = pattern3.matcher(path.toString());
			Pattern pattern4 = Pattern.compile("(.+)");
			Matcher matcher4 = pattern4.matcher(path.toString());

			if (matcher1.matches()) {
				int number = Integer.valueOf(matcher1.group(3));
				number++;
				return matcher1.group(1) + " (" + number + ")" + matcher1.group(4);
			} else if (matcher2.matches()) {
				int number = Integer.valueOf(matcher2.group(3));
				number++;
				return matcher2.group(1) + " (" + number + ").txt";
			} else if (matcher3.matches()) {
				return matcher3.group(1) + " (1)" + matcher3.group(2);
			} else if (matcher4.matches()) {
				return matcher4.group(1) + " (1).txt";
			}

			return "";
		}

	};

	/**
	 * Listener za dugme Clear
	 */
	private ActionListener clearButtonListener = (e) -> {
		model.clearGenerators();
		model.notifyObservers();
	};

	/**
	 * Listener za dugme generisi
	 */
	private ActionListener generateButtonListener = (e) -> {
		model.generateAll();
		model.notifyObservers();
	};

}
