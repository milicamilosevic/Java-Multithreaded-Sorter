/***********************************************************************
 * Modul:  	MultithreadedTestView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled sekcije paralelni test
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
import controller.MultithreadedTestController;
import model.MultithreadedTestModel;
import settings.Algorithms;
import settings.ColorTheme;
import settings.Context;

@SuppressWarnings("serial")
public class MultithreadedTestView extends JPanel {
	private MultithreadedTestModel model;

	private JTextField path;
	private JButton browseBtn;
	private JComboBox<String> algorithm;
	private JComboBox<Integer> maxThreads;
	private JTextField minFilesPerThread;
	private JButton sortBtn;
	private JPanel mainPanel;

	public MultithreadedTestView(MultithreadedTestModel model) {
		this.model = model;

		BoxLayout containerLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(containerLayout);
		setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		setOpaque(false);

		this.mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
		this.mainPanel.setLayout(mainPanelLayout);
		this.mainPanel.setOpaque(false);
		this.mainPanel.setMaximumSize(new Dimension(2000, 500));
		this.mainPanel.setPreferredSize(new Dimension(2000, 500));

		ColorTheme th = Context.getContext().getColorTheme();
		this.mainPanel.add(createPathPanel(th));
		this.mainPanel.add(createAmmountPanel(th));
		this.mainPanel.add(Box.createVerticalStrut(20));
		this.mainPanel.add(createAlgorithmPanel(th));
		this.mainPanel.add(createNoticePanel(th));
		this.mainPanel.add(createBtnPanel(th));

		add(Box.createVerticalGlue());
		add(mainPanel);
		add(Box.createVerticalGlue());

		new MultithreadedTestController(model, this);
	}

	/**
	 * Metoda kreira panel sa tekstualnim poljem za unos putanje
	 */
	private JPanel createPathPanel(ColorTheme th) {
		Font lblFont = Context.getContext().getFonts().getLabelFont();

		JPanel panel = new JPanel();
		BoxLayout ly = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(ly);
		panel.setMaximumSize(new Dimension(2000, 46));
		panel.setOpaque(false);
		JLabel label = new JLabel("Putanja: ");
		label.setFont(lblFont);
		this.path = new MaterialTextField(20);
		this.browseBtn = new MaterialButton("...");

		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(50, 100, 25, 100));
		container.setLayout(new GridLayout(1, 1));
		container.setMaximumSize(new Dimension(2000, 118));
		container.setOpaque(false);

		panel.add(label);
		panel.add(path);
		panel.add(Box.createHorizontalStrut(20));
		panel.add(browseBtn);
		container.add(panel);
		return container;
	}

	/**
	 * Metoda kreira panel sa drop down listom za izbor algoritma sortiranja
	 */
	private JPanel createAlgorithmPanel(ColorTheme th) {
		Font lblFont = Context.getContext().getFonts().getLabelFont();

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setMaximumSize(new Dimension(2000, 100));
		panel.setOpaque(false);

		JLabel label = new JLabel("Algoritam: ");
		label.setFont(lblFont);

		this.algorithm = new MaterialComboBox<>(Algorithms.getInstance().getList());
		this.algorithm.setFont(lblFont);
		this.algorithm.setPreferredSize(new Dimension(300, 46));
		this.algorithm.setUI(new CustomComboBoxUI());

		ComboBoxEditor editor = this.algorithm.getEditor();
		JPanel editorPanel = (JPanel) editor.getEditorComponent();
		editorPanel.setFont(lblFont);
		JLabel editorLabel = (JLabel) editorPanel.getComponent(0);
		editorLabel.setFont(lblFont);
		editorLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		editorPanel.setLayout(new GridLayout(1, 1));
		this.algorithm.setEditor(editor);

		panel.add(label);
		panel.add(algorithm);
		return panel;
	}

	/**
	 * Metoda kreira panel sa poljima za unos parametara testa
	 */
	private JPanel createAmmountPanel(ColorTheme th) {
		JPanel panel = new JPanel();
		Font lblFont = Context.getContext().getFonts().getLabelFont();

		BoxLayout ly = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(ly);
		panel.setMaximumSize(new Dimension(2000, 46));
		panel.setOpaque(false);

		JLabel lbl1 = new JLabel("Maksimalno niti: ");
		lbl1.setFont(lblFont);

		JLabel lbl2 = new JLabel("Minimalno fajlova po niti*: ");
		lbl2.setFont(lblFont);

		// Thread Count combo box
		Vector<Integer> count = new Vector<>();
		for (int i = 1; i <= Context.getContext().getPreferences().getMaxThreadsAllowed(); i *= 2)
			count.add(i);
		this.maxThreads = new MaterialComboBox<>(count);
		this.maxThreads.setFont(lblFont);
		this.maxThreads.setPreferredSize(new Dimension(90, 46));
		this.maxThreads.setMaximumSize(new Dimension(100, 100));
		this.maxThreads.setUI(new CustomComboBoxUI());
		ComboBoxEditor editor = this.maxThreads.getEditor();
		JPanel editorPanel = (JPanel) editor.getEditorComponent();
		editorPanel.setFont(lblFont);
		JLabel label = (JLabel) editorPanel.getComponent(0);
		label.setFont(lblFont);
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		editorPanel.setLayout(new GridLayout(1, 1));
		this.maxThreads.setEditor(editor);

		this.minFilesPerThread = new MaterialTextField(3);
		this.minFilesPerThread.setFont(lblFont);
		this.minFilesPerThread.setMaximumSize(new Dimension(100, 100));
		this.minFilesPerThread.setText("1");

		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(20, 100, 25, 100));
		container.setMaximumSize(new Dimension(2000, 91));
		container.setLayout(new GridLayout(1, 1));
		container.setOpaque(false);

		panel.add(Box.createHorizontalGlue());
		panel.add(lbl1);
		panel.add(maxThreads);
		panel.add(Box.createHorizontalGlue());
		panel.add(lbl2);
		panel.add(minFilesPerThread);
		panel.add(Box.createHorizontalGlue());
		container.add(panel);

		return container;
	}

	/**
	 * Metoda kreira panel sa tekstualnim uputstvom koje se odnosi na minimalan broj fajlova
	 */
	private JPanel createNoticePanel(ColorTheme th) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new GridLayout(1, 1));
		panel.setMaximumSize(new Dimension(2000, 150));
		panel.setOpaque(false);

		JLabel label = new JLabel(
				"<html>* Pojam minimalan broj fajlova po niti se odnosi na maksimalan broj niti. Za slede\u0107i manji broj niti (duplo manji), broj fajlova po niti se udvostru\u010duje. Poenta je da se pri testovima sa razli\u010ditim brojem niti uvijek sortira jednak broj fajlova.</html>");

		label.setFont(Context.getContext().getFonts().getNoteFont());
		label.setForeground(th.getAccentColor());
		panel.add(label);

		JPanel container = new JPanel(new GridLayout(1, 1));
		container.setMaximumSize(new Dimension(2000, 175));
		container.setBorder(BorderFactory.createEmptyBorder(0, 50, 25, 50));
		container.setOpaque(false);
		container.add(panel);
		return container;
	}

	/**
	 * Metoda kreira panel sa dugmadima
	 */
	private JPanel createBtnPanel(ColorTheme th) {
		Font fontLg = new Font(Context.getContext().getFonts().getMainButtonFont().getName(), Font.TRUETYPE_FONT, 32);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setMaximumSize(new Dimension(2000, 80));
		panel.setOpaque(false);
		this.sortBtn = new MaterialButton("   Sortiraj   ");
		this.sortBtn.setFont(fontLg);

		panel.add(sortBtn);
		return panel;
	}

	public JTextField getPath() {
		return path;
	}

	public JComboBox<String> getAlgorithm() {
		return algorithm;
	}

	public JButton getSortBtn() {
		return sortBtn;
	}

	public JButton getBrowseBtn() {
		return browseBtn;
	}

	public JTextField getMinFilesPerThread() {
		return minFilesPerThread;
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

	/**
	 * Metoda azurira model sekcije na osnovu stanja pogleda
	 */
	public void updateModel() {
		model.setPath(path.getText());
		model.setAlgorithmName(algorithm.getSelectedItem().toString());
		model.setMaxThreads(Integer.valueOf(maxThreads.getSelectedItem().toString()));
		model.setMinFilesPerThread(Integer.valueOf(minFilesPerThread.getText()));
	}
}
