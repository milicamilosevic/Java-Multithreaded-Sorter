/***********************************************************************
 * Modul:  	ImageBackgroundContentPane.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise prilagodjenu klasu, izvedenu iz JComponent sa
 * 			dodatom slikom u pozadini
 ***********************************************************************/

package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

import settings.ColorTheme;
import settings.Context;

@SuppressWarnings("serial")
public class ImageBackgroundContentPane extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		ColorTheme theme = Context.getContext().getColorTheme();
		g2d.setColor(theme.getBackgroundColor());
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		Image bgImage = Toolkit.getDefaultToolkit().getImage(theme.getBackgroundImageUrl());
		g2d.drawImage(bgImage, 0, 0, getSize().width, getSize().height, this);
	}
}
