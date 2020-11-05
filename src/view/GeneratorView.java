/***********************************************************************
 * Modul:  	GeneratorView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled sekcije generator testnih podataka
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import components.CustomScrollBar;
import components.MaterialButton;
import components.VerticallyScrollablePanel;
import controller.GeneratorController;
import model.GeneratorModel;
import model.GeneratorSectionModel;
import settings.Context;

@SuppressWarnings("serial")
public class GeneratorView extends JPanel implements ObserverInterface {

	private GeneratorModel model;
	private JPanel sectionsPanel;
	private JScrollPane scrollPane;
	private JButton plus;
	private JButton generate;
	private JButton clear;

	public GeneratorView(GeneratorModel model) {
		this.model = model;
		model.addObserver(this);

		setLayout(new BorderLayout());

		setUpSections();
		setUpButtons();

		scrollPane = new JScrollPane(sectionsPanel);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());
		scrollPane.getVerticalScrollBar().setUnitIncrement(32);

		add(scrollPane, BorderLayout.CENTER);
		setOpaque(false);

		new GeneratorController(model, this);
	}

	/**
	 * Metoda kreira kontrolnu dugmad sekcije
	 */
	private void setUpButtons() {
		JPanel buttonPanel = new JPanel();
		BoxLayout bl = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
		buttonPanel.setLayout(bl);
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 20, 20));

		Font fnt = Context.getContext().getFonts().getMainButtonFont();
		Font large = new Font(fnt.getName(), fnt.getStyle(), 32);
		this.plus = new MaterialButton(" + ");
		this.plus.setFont(large);
		this.generate = new MaterialButton("Generisi");
		this.generate.setFont(large);
		this.clear = new MaterialButton(" X ");
		this.clear.setFont(large);

		buttonPanel.add(plus);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(clear);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(generate);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * Metoda dodaje podsekcije iz modela u pogled
	 */
	private void setUpSections() {
		sectionsPanel = new VerticallyScrollablePanel();
		BoxLayout sectionsLayout = new BoxLayout(sectionsPanel, BoxLayout.PAGE_AXIS);
		sectionsPanel.setLayout(sectionsLayout);

		for (GeneratorSectionModel generator : model.getGenerators()) {
			GeneratorSectionView section = new GeneratorSectionView(generator);
			section.getPath().setText(generator.getPath());
			section.getAmmount().setText(String.valueOf(generator.getAmmount()));
			section.getFrom().setText(String.valueOf(generator.getFrom()));
			section.getTo().setText(String.valueOf(generator.getTo()));
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			panel.setMaximumSize(new Dimension(2000, 230));
			panel.setLayout(new GridLayout(1, 1));
			panel.setOpaque(false);
			panel.add(section);
			sectionsPanel.add(panel);
		}
		sectionsPanel.setOpaque(false);
	}

	public JButton getPlus() {
		return plus;
	}

	public JButton getClear() {
		return clear;
	}

	public JButton getGenerate() {
		return generate;
	}

	/**
	 * Metoda azurira model na osnovu pogleda
	 */
	public void updateModels() {
		Component[] components = sectionsPanel.getComponents();
		for (Component cmp : components) {
			GeneratorSectionView section = (GeneratorSectionView) ((JPanel) cmp).getComponent(0);
			section.updateModel();
		}
	}

	@Override
	public void update() {
		setUpSections();
		scrollPane.setViewportView(sectionsPanel);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		revalidate();
		repaint();
	}

	public GeneratorModel getModel() {
		return model;
	}

}
