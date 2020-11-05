/***********************************************************************
 * Modul:  	CustomComboBoxUI.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise prilagodnjeni korisnicki interfejs elementa JComboBox
 ***********************************************************************/

package components;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

import settings.ColorTheme;
import settings.Context;

public class CustomComboBoxUI extends BasicComboBoxUI {
	private Font font;
	private JButton button;
	private ColorTheme theme;

	/**
	 * Konstruktor klase. Koriste se boja i font iz trenutne teme
	 */
	public CustomComboBoxUI() {
		this.theme = Context.getContext().getColorTheme();
		this.font = Context.getContext().getFonts().getLabelFont();
	}

	@Override
	protected JButton createArrowButton() {
		button = super.createArrowButton();
		button = new JButton("\u02c5");
		button.setFont(font);
		button.setBackground(theme.getAccentColor());
		button.setForeground(theme.getTextSecondaryColor());
		button.setBorderPainted(false);
		return button;
	}

	public JButton getButton() {
		return button;
	}
}