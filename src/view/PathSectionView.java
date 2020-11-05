/***********************************************************************
 * Modul:  	PathSectionView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled podsekcije serijskog testa
 ***********************************************************************/

package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.MaterialButton;
import components.MaterialTextField;
import model.PathSectionModel;
import settings.ColorTheme;
import settings.Context;

@SuppressWarnings("serial")
public class PathSectionView extends JPanel {

	private PathSectionModel model;

	private JLabel number;
	private JLabel messageLabel;
	private JButton remove;
	private JTextField pathTxtField;
	private ColorTheme theme;

	public PathSectionView(PathSectionModel model) {
		this.theme = Context.getContext().getColorTheme();
		this.model = model;
		this.model.setView(this);
		BoxLayout mainLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(mainLayout);

		setUpNumber(model.getNumber());
		add(Box.createHorizontalStrut(20));
		setUpMainSection();
		add(Box.createHorizontalStrut(20));
		setUpRemoveButton();
		remove.addActionListener(removeBtnListener);

		setOpaque(false);
	}

	/**
	 * Metoda postavlja redni broj sekcije
	 * @param num - redni broj
	 */
	private void setUpNumber(int num) {
		JPanel panel = new JPanel();
		number = new JLabel(String.valueOf(num));
		number.setFont(Context.getContext().getFonts().getImportantFont());
		panel.add(number);
		panel.setOpaque(false);
		this.add(panel);
	}

	/**
	 * Metoda kreira i dodaje dugme za uklanjanje podsekcije
	 */
	private void setUpRemoveButton() {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(layout);
		remove = new MaterialButton("Ukloni");
		remove.setFont(Context.getContext().getFonts().getMainButtonFont());
		panel.add(remove);
		panel.setOpaque(false);
		Border border = BorderFactory.createEmptyBorder(0, 0, 0, 20);
		panel.setBorder(border);
		this.add(panel);
	}

	/**
	 * Metoda kreira i dodaje komponente u pogled
	 */
	private void setUpMainSection() {
		JPanel mainSection = new JPanel();
		BoxLayout layout = new BoxLayout(mainSection, BoxLayout.PAGE_AXIS);
		mainSection.setLayout(layout);

		JLabel pathLabel = new JLabel("Putanja:");
		pathLabel.setFont(Context.getContext().getFonts().getLabelFont());
		pathTxtField = new MaterialTextField(512);
		pathTxtField.setText(model.getPath());
		pathTxtField.getDocument().addDocumentListener(textChangeListener);
		messageLabel = new JLabel(model.getMessage());
		messageLabel.setFont(Context.getContext().getFonts().getNoteFont());
		messageLabel.setForeground(theme.getAccentColor());

		JPanel upperRow = new JPanel();
		BoxLayout upperRowLayout = new BoxLayout(upperRow, BoxLayout.LINE_AXIS);
		upperRow.setLayout(upperRowLayout);
		upperRow.add(pathLabel);
		upperRow.add(Box.createHorizontalStrut(5));
		upperRow.add(pathTxtField);
		upperRow.setOpaque(false);

		JPanel lowerRow = new JPanel();
		lowerRow.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerRow.add(messageLabel);
		lowerRow.setOpaque(false);

		mainSection.add(upperRow);
		mainSection.add(Box.createVerticalStrut(10));
		mainSection.add(lowerRow);
		mainSection.setOpaque(false);

		Border mainSectionBorder = BorderFactory.createEmptyBorder(45, 0, 10, 0);
		mainSection.setBorder(mainSectionBorder);

		this.add(mainSection);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(theme.getSectionColor());
		graphics2d.fillRoundRect(0, 0, getSize().width, getSize().height, 50, 50);
		super.paint(g);
	}

	public void update() {
		messageLabel.setText(model.getMessage());
	}

	/**
	 * Listener zaduzen za uklanjanje podsekcije pritiskom na dugme Ukloni
	 */
	private ActionListener removeBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SinglethreadedTestView parentView = (SinglethreadedTestView) (getParent().getParent().getParent()
					.getParent().getParent());
			parentView.getModel().removeFile(model);
			parentView.getModel().notifyObservers();
		}
	};

	/**
	 * Listener zaduzen za azuriranje modela pri promjeni putanje u tekstualnom polju
	 */
	private DocumentListener textChangeListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			PathSectionView.this.model.setPath(PathSectionView.this.pathTxtField.getText());
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			PathSectionView.this.model.setPath(PathSectionView.this.pathTxtField.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			PathSectionView.this.model.setPath(PathSectionView.this.pathTxtField.getText());
		}
	};

}
