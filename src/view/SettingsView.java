/***********************************************************************
 * Modul:  	SettingsView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled sekcije podesavanja
 ***********************************************************************/

package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.CustomComboBoxUI;
import components.MaterialButton;
import components.MaterialComboBox;
import components.MaterialTextField;
import controller.SettingsController;
import model.SettingsModel;
import settings.ColorTheme;
import settings.Context;
import settings.Preferences;

@SuppressWarnings("serial")
public class SettingsView extends JPanel {
	private JComboBox<String> themesCmbBox;
	private JComboBox<String> graphCmbBox;
	private JTextField threadsTxt;
	private JTextField filesTxt;
	private JButton applyBtn;
	private JButton cancelBtn;
	private JPanel mainContainer;

	private SettingsModel model;

	public SettingsView(SettingsModel model) {
		this.model = model;

		BoxLayout containerLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(containerLayout);
		setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		setOpaque(false);

		mainContainer = new JPanel();
		BoxLayout mainContainerLayout = new BoxLayout(mainContainer, BoxLayout.PAGE_AXIS);
		mainContainer.setLayout(mainContainerLayout);
		mainContainer.setOpaque(false);
		mainContainer.setMaximumSize(new Dimension(800, 500));

		mainContainer.add(Box.createVerticalStrut(50));
		mainContainer.add(createThemeRow());
		mainContainer.add(createGraphRow());
		mainContainer.add(createThreadsRow());
		mainContainer.add(createFilesRow());
		mainContainer.add(Box.createVerticalStrut(50));
		mainContainer.add(createButtonsRow());
		mainContainer.add(Box.createVerticalStrut(30));

		add(Box.createVerticalGlue());
		add(mainContainer);
		add(Box.createVerticalGlue());

		updateFieldValues();
		new SettingsController(model, this);
	}

	/**
	 * Metoda kreira panel sa podesavanjima vezanim za izbor teme
	 */
	private JPanel createThemeRow() {
		Context context = Context.getContext();
		ColorTheme theme = context.getColorTheme();
		Font lblFont = context.getFonts().getLabelFont();

		Vector<String> themeNames = new Vector<>(model.getThemes().keySet());
		this.themesCmbBox = new MaterialComboBox<>(themeNames);
		this.themesCmbBox.setFont(lblFont);
		this.themesCmbBox.setPreferredSize(new Dimension(400, 46));
		this.themesCmbBox.setUI(new CustomComboBoxUI());
		ComboBoxEditor editor = this.themesCmbBox.getEditor();
		JPanel editorPanel = (JPanel) editor.getEditorComponent();
		editorPanel.setFont(lblFont);
		JLabel editorLabel = (JLabel) editorPanel.getComponent(0);
		editorLabel.setFont(lblFont);
		editorLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		editorPanel.setLayout(new GridLayout(1, 1));
		this.themesCmbBox.setEditor(editor);

		JLabel label = new JLabel("Tema: ");
		label.setFont(lblFont);
		label.setForeground(theme.getTextPrimaryColor());
		label.setPreferredSize(new Dimension(130, 46));

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);
		panel.add(themesCmbBox);
		panel.setOpaque(false);

		return panel;
	}

	/**
	 * Metoda kreira panel sa podesavanjima vezanim za stil grafikona
	 */
	private JPanel createGraphRow() {
		Context context = Context.getContext();
		ColorTheme theme = context.getColorTheme();
		Font lblFont = context.getFonts().getLabelFont();

		Vector<String> graphStyles = new Vector<>();
		graphStyles.add("Line graph");
		graphStyles.add("Spline graph");
		this.graphCmbBox = new MaterialComboBox<>(graphStyles);
		this.graphCmbBox.setFont(lblFont);
		this.graphCmbBox.setPreferredSize(new Dimension(400, 46));
		this.graphCmbBox.setUI(new CustomComboBoxUI());
		ComboBoxEditor editor = this.graphCmbBox.getEditor();
		JPanel editorPanel = (JPanel) editor.getEditorComponent();
		editorPanel.setFont(lblFont);
		JLabel editorLabel = (JLabel) editorPanel.getComponent(0);
		editorLabel.setFont(lblFont);
		editorLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		editorPanel.setLayout(new GridLayout(1, 1));
		this.graphCmbBox.setEditor(editor);

		JLabel label = new JLabel("Stil grafikona: ");
		label.setFont(lblFont);
		label.setForeground(theme.getTextPrimaryColor());
		label.setPreferredSize(new Dimension(130, 46));

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);
		panel.add(graphCmbBox);
		panel.setOpaque(false);

		return panel;
	}

	/**
	 * Metoda kreira panel sa podesavanjima vezanim za maksimalni broj niti
	 */
	private JPanel createThreadsRow() {
		Context context = Context.getContext();
		ColorTheme theme = context.getColorTheme();
		Font lblFont = context.getFonts().getLabelFont();

		this.threadsTxt = new MaterialTextField(3);
		this.threadsTxt.setFont(lblFont);
		this.threadsTxt.setPreferredSize(new Dimension(100, 46));

		JLabel label = new JLabel("Ograni\u010denje broja niti:");
		label.setFont(lblFont);
		label.setForeground(theme.getTextPrimaryColor());
		label.setPreferredSize(new Dimension(230, 46));

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);
		panel.add(threadsTxt);
		panel.setOpaque(false);

		return panel;
	}

	/**
	 * Metoda kreira panel sa podesavanjima vezanim za izbor minimalnog broja fajlova po niti
	 */
	private JPanel createFilesRow() {
		Context context = Context.getContext();
		ColorTheme theme = context.getColorTheme();
		Font lblFont = context.getFonts().getLabelFont();

		this.filesTxt = new MaterialTextField(3);
		this.filesTxt.setFont(lblFont);
		this.filesTxt.setPreferredSize(new Dimension(100, 46));

		JLabel label = new JLabel("Ograni\u010denje broja fajlova:");
		label.setFont(lblFont);
		label.setForeground(theme.getTextPrimaryColor());
		label.setPreferredSize(new Dimension(230, 46));

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(label);
		panel.add(filesTxt);
		panel.setOpaque(false);

		return panel;
	}

	/**
	 * Metoda kreira panel sa kontrolnom dugmadi
	 */
	private JPanel createButtonsRow() {
		Font fontLg = new Font(Context.getContext().getFonts().getMainButtonFont().getName(), Font.TRUETYPE_FONT, 32);

		this.applyBtn = new MaterialButton("Potvrdi");
		this.applyBtn.setFont(fontLg);

		this.cancelBtn = new MaterialButton("Odustani");
		this.cancelBtn.setFont(fontLg);

		JPanel panel = new JPanel(new FlowLayout());
		panel.add(applyBtn);
		panel.add(Box.createHorizontalStrut(100));
		panel.add(cancelBtn);
		panel.setOpaque(false);

		return panel;
	}

	/**
	 * Metoda podesava vrijednosti unesene u komponentama na osnovu trenutnih podesavanja
	 */
	public void updateFieldValues() {
		Preferences preferences = Context.getContext().getPreferences();
		this.themesCmbBox.setSelectedItem(preferences.getThemeName());
		this.graphCmbBox.setSelectedItem(preferences.isSplineRenderer() ? "Spline graph" : "Line graph");
		this.threadsTxt.setText(String.valueOf(preferences.getMaxThreadsAllowed()));
		this.filesTxt.setText(String.valueOf(preferences.getFilesLimit()));
	}

	public JButton getApplyBtn() {
		return applyBtn;
	}

	public JButton getCancelBtn() {
		return cancelBtn;
	}

	public JComboBox<String> getThemesCmbBox() {
		return themesCmbBox;
	}

	public JComboBox<String> getGraphCmbBox() {
		return graphCmbBox;
	}

	public JTextField getThreadsTxt() {
		return threadsTxt;
	}

	public JTextField getFilesTxt() {
		return filesTxt;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		ColorTheme theme = Context.getContext().getColorTheme();
		graphics2d.setColor(theme.getSectionColor());
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.fillRoundRect(mainContainer.getLocation().x, mainContainer.getLocation().y,
				mainContainer.getSize().width, mainContainer.getSize().height, 50, 50);
		super.paint(g);
	}
}
