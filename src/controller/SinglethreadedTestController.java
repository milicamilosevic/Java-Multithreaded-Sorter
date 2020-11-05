/***********************************************************************
 * Modul:  	SinglethreadedTestController.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise kontroler sekcije serijski test
 ***********************************************************************/

package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.SinglethreadedTestModel;
import view.SinglethreadedTestView;

public class SinglethreadedTestController {

	private SinglethreadedTestModel model;
	private SinglethreadedTestView view;

	public SinglethreadedTestController(SinglethreadedTestModel model, SinglethreadedTestView view) {
		this.model = model;
		this.view = view;
		view.getAddButton().addActionListener(addBtnListener);
		view.getRemoveButton().addActionListener(removeBtnListener);
		view.getSortButton().addActionListener(sortBtnListener);
	}

	/**
	 * Listener koji otvara fajl dijalog prozor, te omogucuje dodavanje fajlova
	 */
	private ActionListener addBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setPreferredSize(new Dimension(800, 600));
			fileChooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("TXT files", "txt");
			fileChooser.addChoosableFileFilter(fileFilter);
			fileChooser.setMultiSelectionEnabled(true);
			int returnValue = fileChooser.showOpenDialog(view);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				model.addFiles(fileChooser.getSelectedFiles());
				model.notifyObservers();
			}
		}
	};

	/**
	 * Listener za uklanjanje svih fajlova iz sekcije serijski test
	 */
	private ActionListener removeBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			model.removeAllFiles();
			model.notifyObservers();
		}
	};

	/**
	 * Listener za pokretanje testa
	 */
	private ActionListener sortBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			model.setAlgorithmName(view.getComboBox().getSelectedItem().toString());
			Thread multiSorterThread = new Thread(model);
			multiSorterThread.start();
		}
	};

}
