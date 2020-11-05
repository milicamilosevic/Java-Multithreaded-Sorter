/***********************************************************************
 * Modul:  	AboutView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled sekcije informacije o programu
 ***********************************************************************/

package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import settings.ColorTheme;
import settings.Context;
import settings.FontTheme;

@SuppressWarnings("serial")
public class AboutView extends JPanel {
	private JPanel mainPanel;

	public AboutView() {
		BoxLayout containerLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(containerLayout);
		setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		setOpaque(false);

		this.mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
		this.mainPanel.setLayout(mainPanelLayout);
		this.mainPanel.setOpaque(false);
		this.mainPanel.setPreferredSize(new Dimension(2000, 500));
		this.mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 20));

		createComponents();

		add(Box.createVerticalGlue());
		add(mainPanel);
		add(Box.createVerticalGlue());
	}

	/**
	 * Metoda kreira i dodaje komponente pogleda
	 */
	private void createComponents() {
		FontTheme fonts = Context.getContext().getFonts();
		ColorTheme theme = Context.getContext().getColorTheme();

		Font titleFont = fonts.getMainButtonFont();
		titleFont = titleFont.deriveFont(Font.BOLD);
		titleFont = titleFont.deriveFont(32f);
		JLabel title = new JLabel("O programu");
		title.setFont(titleFont);
		title.setForeground(theme.getSpecialColor());

		String aboutText = "<html><br /><p>Aplikacija vi\u0161enitni sorter je razvijena kao projekat iz predmeta Paralelni ra\u010dunarski sistemi. Svrha aplikacije je da poka\u017ee prednosti izvr\u0161avanja zadataka u okru\u017eenju sa vi\u0161e niti. Kao tema aplikacije uzet je problem sortiranja objekata. Omogu\u0107eno je mjerenje vremena sortiranja pri razli\u010ditim ulaznim veli\u010dinama i njihovo pore\u0111enje kao i grafi\u010dko predstavljanje zavisnosti.</p><br /><p>Pored izvr\u0161avanja testova aplikacija ima i mogu\u0107nost generisanja testnih podataka, \u010duvanja rezultata testova, te mijenjanja i \u010duvanja pode\u0161avanja same aplikacije.</p><br /></html>";
		JLabel about = new JLabel(aboutText);
		Font aboutFont = fonts.getLabelFont().deriveFont(18f);
		about.setFont(aboutFont);
		about.setForeground(theme.getTextPrimaryColor());

		JLabel developers=new JLabel("Programeri");
		developers.setFont(titleFont);
		title.setForeground(theme.getSpecialColor());
		
		JLabel milica = new JLabel("<html>Milica Milo\u0161evi\u0107,<br />Elektrotehni\u010dki fakultet, Isto\u010dno Sarajevo</html>");
		milica.setFont(aboutFont);
		milica.setForeground(theme.getTextPrimaryColor());
		JLabel milicaMail=new JLabel("milicamilosevicll97@gmail.com");
		milicaMail.setFont(fonts.getNoteFont());
		milicaMail.setForeground(theme.getAccentColor());
		
		JLabel boris = new JLabel("<html>Boris Bo\u0161kovi\u0107,<br />Elektrotehni\u010dki fakultet, Isto\u010dno Sarajevo</html>");
		boris.setFont(aboutFont);
		boris.setForeground(theme.getTextPrimaryColor());
		JLabel borisMail=new JLabel("boris.boskovic92@gmail.com");
		borisMail.setFont(fonts.getNoteFont());
		borisMail.setForeground(theme.getAccentColor());
		
		mainPanel.add(title);
		mainPanel.add(about);
		mainPanel.add(developers);
		mainPanel.add(milica);
		mainPanel.add(milicaMail);
		mainPanel.add(Box.createVerticalStrut(10));
		mainPanel.add(boris);
		mainPanel.add(borisMail);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		ColorTheme theme = Context.getContext().getColorTheme();
		graphics2d.setColor(theme.getSectionColor());
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.fillRoundRect(mainPanel.getLocation().x, mainPanel.getLocation().y, mainPanel.getSize().width,
				mainPanel.getSize().height, 50, 50);
		super.paint(g);
	}
}
