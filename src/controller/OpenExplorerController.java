/***********************************************************************
 * Modul:  	OpenExplorerController.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise kontroler koji otvara sistem eksplorer iz sekcije
 * 			informacije
 ***********************************************************************/

package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;

import model.InfoModel.Disk;

public class OpenExplorerController implements MouseListener {

	private Disk model;

	public OpenExplorerController(JLabel view, Disk model) {
		this.model = model;
		view.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("explorer " + model.getPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
