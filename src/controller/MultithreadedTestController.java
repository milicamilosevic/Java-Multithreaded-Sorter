/***********************************************************************
 * Modul:  	MultithreadedTestController.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise kontroler sekcije Paralelni test
 ***********************************************************************/

package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.MultithreadedTestModel;
import settings.Context;
import view.MultithreadedTestView;

public class MultithreadedTestController {
	private MultithreadedTestModel model;
	private MultithreadedTestView view;

	public MultithreadedTestController(MultithreadedTestModel model, MultithreadedTestView view) {
		this.model = model;
		this.view = view;
		this.view.getSortBtn().addActionListener(sortBtnListener);
		this.view.getBrowseBtn().addActionListener(browseBtnListener);
	}

	/**
	 * Metoda provjerava da li zadati minimalan broj fajlova prekoracuje trenutne postavke
	 */
	private Boolean validateNumOfFiles() {
		try {
			int num = Integer.parseInt(view.getMinFilesPerThread().getText());
			return (num >= 1 && num <= Context.getContext().getPreferences().getFilesLimit());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Listener za dugme Sortiraj
	 */
	private ActionListener sortBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String path = view.getPath().getText();

			String message1 = "Plesae enter file path.";
			String message2 = "Can't open file.";
			if (path.length() == 0)
				JOptionPane.showMessageDialog(view, message1, "Info", JOptionPane.INFORMATION_MESSAGE);
			else if (!Files.isRegularFile(Paths.get(path)))
				JOptionPane.showMessageDialog(view, message2, "Error", JOptionPane.ERROR_MESSAGE);
			else {
				if (!validateNumOfFiles()) {
					String message = "For minimum files per thread please choose a value between 1 and "
							+ Context.getContext().getPreferences().getFilesLimit()+", or change this in settings";
					JOptionPane.showMessageDialog(view, message, "Info", JOptionPane.INFORMATION_MESSAGE);
				} else {
					view.updateModel();
					Thread thread = new Thread(model);
					thread.start();
				}
			}
		}
	};

	/**
	 * Listener za dugme browse. Otvara Fajl dijalog prozor.
	 */
	private ActionListener browseBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("TXT files", "txt");
			fileChooser.addChoosableFileFilter(fileFilter);
			fileChooser.setPreferredSize(new Dimension(800, 600));

			int returnValue = fileChooser.showOpenDialog(view);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String selectedFile = fileChooser.getSelectedFile().getPath().toString();
				view.getPath().setText(selectedFile);
			}
		}
	};
}
