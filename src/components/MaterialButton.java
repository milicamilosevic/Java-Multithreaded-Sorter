/***********************************************************************
 * Modul:  	MaterialButton.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise klasu izvedenu iz klase JButton gdje je izgled
 * 			dugmeta prilagodjen trenutnoj temi
 ***********************************************************************/

package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import settings.ColorTheme;
import settings.Context;
import settings.FontTheme;

@SuppressWarnings("serial")
public class MaterialButton extends JButton {

	private boolean hover = false;
	private boolean mousePressed = false;

	private ColorTheme theme;
	private FontTheme fonts;

	public MaterialButton(String text) {
		super(text);
		this.theme = Context.getContext().getColorTheme();
		this.fonts = Context.getContext().getFonts();
		setFont(fonts.getMainButtonFont());
		this.addMouseListener(mouseListener);
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		theme = Context.getContext().getColorTheme();
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension dimensions = this.getPreferredSize();

		int borderRadius = (dimensions.width > dimensions.height) ? dimensions.height / 4 : dimensions.width / 4;

		if (hover && !mousePressed) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			graphics2d.setColor(lighten(theme.getAccentColor()));
			graphics2d.fillRoundRect(0, 0, dimensions.width, dimensions.height, borderRadius, borderRadius);
			graphics2d.setColor(theme.getTextSecondaryColor());
			Point p = getTextPosition(getFont());
			graphics2d.drawString(getText(), p.x, p.y);
		} else {
			graphics2d.setColor(theme.getAccentColor());
			graphics2d.fillRoundRect(0, 0, dimensions.width, dimensions.height, borderRadius, borderRadius);
			graphics2d.setColor(theme.getTextSecondaryColor());
			Point p = getTextPosition(getFont());
			graphics2d.drawString(getText(), p.x, p.y);
		}
	}

	/**
	 * Odredjuje lokaciju teksta uzimajuci u obzir izabrani font i velicinu komponente
	 * @param font - izarani pont
	 * @return Tacka koja predstavlja lokaciju od koje se ispisuje tekst
	 */
	private Point getTextPosition(Font font) {
		FontMetrics fm = getFontMetrics(font);
		int lineWidth = 0;
		for (int i = 0; i < getText().length(); i++) {
			lineWidth += fm.charWidth(getText().charAt(i));
		}
		int lineHeight = fm.getHeight();
		int x = getSize().width / 2 - lineWidth / 2;
		int y = getPreferredSize().height / 2 + lineHeight / 4 + lineHeight / 16;
		return new Point(x, y);
	}

	/**
	 * Objekat koji definise interakciju sa dugmetom. Zaduzen za vizuelne promjene pri interakciji.
	 */
	private MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1)
				mousePressed = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1)
				mousePressed = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			MaterialButton.this.hover = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			MaterialButton.this.hover = true;
		}

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {
		}
	};

	/**
	 * Metoda kreira svjetliju nijansu proslijedjene boje
	 * @param color - boja koja se prosljedjuje
	 * @return	- Kreirana boja
	 */
	private Color lighten(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		r = (r <= 220) ? r + 35 : 255;
		g = (g <= 220) ? g + 35 : 255;
		b = (b <= 220) ? b + 35 : 255;
		return new Color(r, g, b);
	}

}
