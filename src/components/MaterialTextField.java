/***********************************************************************
 * Modul:  	MaterialTextField.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise klasu izvedenu iz klase JTextField gdje je izgled
 * 			komponente prilagodjen trenutnoj temi
 ***********************************************************************/

package components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import settings.ColorTheme;
import settings.Context;

@SuppressWarnings("serial")
public class MaterialTextField extends JTextField {

	private Font font;

	public MaterialTextField(int length) {
		super(length);
		font = Context.getContext().getFonts().getTextFieldFont();
		setFont(font);
		applyStyle();
		this.addFocusListener(selectOnFocus);
	}

	/**
	 * Metoda prilagodjava izgled komponente trenutnoj temi
	 */
	private void applyStyle() {
		ColorTheme theme = Context.getContext().getColorTheme();

		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, theme.getThemeColor()),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));

		setBackground(theme.getInputColor());
		setForeground(theme.getTextSecondaryColor());
		setCaretColor(theme.getTextSecondaryColor());
		setSelectionColor(theme.getAccentColor());
		setSelectedTextColor(theme.getTextSecondaryColor());

		setCaretPosition(0);

		setMinimumSize(new Dimension(0, 50));
		setSize(new Dimension(0, 50));
	}

	@Override
	public void paint(Graphics g) {
		ColorTheme theme = Context.getContext().getColorTheme();
		setBackground(theme.getInputColor());
		setForeground(theme.getTextSecondaryColor());
		setCaretColor(theme.getTextSecondaryColor());
		setSelectionColor(theme.getAccentColor());
		setSelectedTextColor(theme.getTextSecondaryColor());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, theme.getInputColor()),
				BorderFactory.createEmptyBorder(5, 10, 0, 10)));
		super.paint(g);
	}
	
	/**
	 * Objekat zaduzen za osluskivanje fokusa na komponentu. U slucaju fokusiranja 
	 * selektuje sav tekst unutar komponente.
	 */
	private FocusListener selectOnFocus = new FocusListener() {

		@Override
		public void focusLost(FocusEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
			MaterialTextField.this.selectAll();
		}
	};

}
