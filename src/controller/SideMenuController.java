/***********************************************************************
 * Modul:  	SideMenuController.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise kontroler zaduzen za izbor trenutno aktivne sekcije
 ***********************************************************************/

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.PanelButton;
import model.PanelSwitchingModel;
import view.SideMenuView;

public class SideMenuController {

	private PanelSwitchingModel model;
	@SuppressWarnings("unused")
	private SideMenuView view;

	public SideMenuController(PanelSwitchingModel model, SideMenuView view) {
		this.model = model;
		this.view = view;
		for (PanelButton button : model.getButtons()) {
			button.addActionListener(activeButtonSwitcher);
		}
	}

	/**
	 * Listener koji klikom na stavku meniju mijenja trenutno aktivnu sekciju 
	 */
	private ActionListener activeButtonSwitcher = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int curentindex = model.getActive();
			int newIndex = model.getButtons().indexOf((PanelButton) e.getSource());
			if (curentindex != newIndex) {
				model.setActive(model.getButtons().indexOf((PanelButton) e.getSource()));
				model.notifyObservers();
			}
		}
	};

}
