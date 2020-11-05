/***********************************************************************
 * Modul:  	CustomScrollBarr.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise prilagodjeni korisnicki interfejs scroll trake
 ***********************************************************************/

package components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

import settings.ColorTheme;
import settings.Context;

public class CustomScrollBar extends MetalScrollBarUI {
	private ColorTheme theme;

	public CustomScrollBar() {
		this.theme = Context.getContext().getColorTheme();
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(theme.getThemeDarkerColor());
		graphics2d.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(theme.getThemeLighterColor());
		graphics2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return createZeroButton();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		theme = Context.getContext().getColorTheme();
		super.paint(g, c);
	}

	/**
	 * Kreira se dugme na ivicama trake, dimentija 0, 0
	 * @return
	 */
	private JButton createZeroButton() {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0, 0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

}
