/***********************************************************************
 * Modul:  	MainClass.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Klasa predstavlja ulaznu tacku aplikacije. Pokrece glavni
 * 			prozor aplikacije.
 ***********************************************************************/

package main;

import model.PanelSwitchingModel;
import view.MainWindow;

public class MainClass {

	public static void main(String[] args) {
		new MainWindow(new PanelSwitchingModel());
	}

}
