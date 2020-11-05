/***********************************************************************
 * Modul:  	PathSectionModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model podsekcije za serijski test
 ***********************************************************************/

package model;

import view.PathSectionView;

public class PathSectionModel {

	private int number;
	private String path;
	private String message;

	private PathSectionView view;
	
	/**
	 * Konstruktor
	 * @param number -redni broj sekcije
	 */
	public PathSectionModel(int number) {
		this.number = number;
		this.path = "";
		this.message = " ";
	}

	/**
	 * Konstruktor
	 * @param number - redni broj sekcije
	 * @param path - putanja fajla
	 */
	public PathSectionModel(int number, String path) {
		this.number = number;
		this.path = path;
		this.message = " ";
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setView(PathSectionView view) {
		this.view = view;
	}
	
	public void updateView() {
		view.update();
	}
	
}
