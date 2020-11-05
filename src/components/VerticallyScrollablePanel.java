/***********************************************************************
 * Modul:  	VerticallyScrollablePanel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise klasu izvedenu iz klase JPanel cije se dimenzije po
 * 			visini prilagodjavaju velicini i broju elemenata unutar nje.
 ***********************************************************************/

package components;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VerticallyScrollablePanel extends JPanel {

	@Override
	public Dimension getPreferredSize() {
		int h = super.getPreferredSize().height;
		int w = getParent().getSize().width;
		return new java.awt.Dimension(w, h);
	}
}
