/***********************************************************************
 * Modul:  	PanelSwitchingModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise klasu zaduzenu za prikaz odgovarajucih sekcija na
 * 			osnovu izbora iz menija
 ***********************************************************************/

package model;

import java.util.ArrayList;

import components.PanelButton;
import view.ObserverInterface;

public class PanelSwitchingModel implements SubjectInterface {

	private ArrayList<ObserverInterface> observers;

	private ArrayList<PanelButton> buttons;
	private int active;

	public PanelSwitchingModel() {
		observers = new ArrayList<>();
		populateButtons();
		active = 0;
	}

	/**
	 * Metoda kreira i dodaje dugmad za izbor sekcija
	 */
	private void populateButtons() {
		buttons = new ArrayList<>();
		PanelButton button1 = new PanelButton("Sistemske informacije");
		PanelButton button2 = new PanelButton("Generisanje podataka");
		PanelButton button3 = new PanelButton("Serijski test");
		PanelButton button4 = new PanelButton("Paralelni test");
		PanelButton button5 = new PanelButton("Pode\u0161avanja");
		PanelButton button6 = new PanelButton("O programu");

		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
		buttons.add(button6);
	}

	public ArrayList<PanelButton> getButtons() {
		return buttons;
	}

	public int getActive() {
		return active;
	}

	/**
	 * Metoda dugme sa datim indeksom oznacava kao izabrano
	 * @param active - indeks dugmeta
	 */
	public void setActive(int active) {
		this.active = active;
		for (PanelButton b : buttons) {
			if (buttons.indexOf(b) == active)
				b.setPressed(true);
			else
				b.setPressed(false);
		}
	}

	@Override
	public void addObserver(ObserverInterface observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(ObserverInterface observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		for (ObserverInterface observer : observers) {
			observer.update();
		}
	}

}
