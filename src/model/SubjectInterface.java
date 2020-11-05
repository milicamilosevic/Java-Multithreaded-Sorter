/***********************************************************************
 * Modul:  	SubjectInterface.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise interfejs za implementiranje Observer dizajn sablona
 * 			na strani modela
 ***********************************************************************/

package model;

import view.ObserverInterface;

public interface SubjectInterface {

	/**
	 * Dodavanje novog observera (pogleda)
	 * @param observer - pogled koji se dodaje
	 */
	public void addObserver(ObserverInterface observer);

	/**
	 * Uklanjanje observera (pogleda)
	 * @param observer - pogled koji se uklanja
	 */
	public void removeObserver(ObserverInterface observer);

	/**
	 * Obavjestavanje svih registrovanih observera o promjeni
	 */
	public void notifyObservers();

}
