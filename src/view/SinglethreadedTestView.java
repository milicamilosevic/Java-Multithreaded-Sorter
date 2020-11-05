/***********************************************************************
 * Modul:  	SinglethreadedTestView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled sekcije serijski test
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import components.CustomScrollBar;
import components.MaterialButton;
import components.MaterialComboBox;
import components.VerticallyScrollablePanel;
import controller.SinglethreadedTestController;
import model.SinglethreadedTestModel;
import settings.Algorithms;
import settings.Context;
import settings.FontTheme;

@SuppressWarnings("serial")
public class SinglethreadedTestView extends JPanel implements ObserverInterface {
	private FontTheme fonts;
	private SinglethreadedTestModel model;
	private VerticallyScrollablePanel sectionsPanel;

	private JButton addButton;
	private JButton removeButton;
	private JButton sortButton;
	private JScrollPane scrollPane;
	private JComboBox<String> comboBox;

	public SinglethreadedTestView(SinglethreadedTestModel model) {
		this.model = model;
		this.fonts = Context.getContext().getFonts();
		model.addObserver(this);
		setLayout(new BorderLayout());

		setUpSections();

		scrollPane = new JScrollPane(sectionsPanel);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());
		scrollPane.getVerticalScrollBar().setUnitIncrement(32);

		add(scrollPane, BorderLayout.CENTER);
		addButtons();

		setOpaque(false);

		new SinglethreadedTestController(model, this);
	}

	/**
	 * Metoda dodaje podsekcije u pogled na osnovu modela
	 */
	private void setUpSections() {
		sectionsPanel = new VerticallyScrollablePanel();
		BoxLayout sectionsLayout = new BoxLayout(sectionsPanel, BoxLayout.PAGE_AXIS);
		sectionsPanel.setLayout(sectionsLayout);
		sectionsPanel.setOpaque(false);

		for (int i = 0; i < model.getPathModels().size(); i++) {
			PathSectionView pathSection = new PathSectionView(model.getPathModels().get(i));
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			panel.setMaximumSize(new Dimension(2000, 170));
			panel.setLayout(new GridLayout(1, 1));
			panel.setOpaque(false);
			panel.add(pathSection);

			sectionsPanel.add(panel);
		}

	}

	/**
	 * Metoda kreira i dodaje kontrolnu dugmad
	 */
	private void addButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		BoxLayout bl = new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS);
		buttonPanel.setLayout(bl);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 20, 20));

		Font fnt = fonts.getMainButtonFont();
		Font large = new Font(fnt.getName(), fnt.getStyle(), 32);
		this.addButton = new MaterialButton(" + ");
		this.addButton.setFont(large);
		this.removeButton = new MaterialButton(" X ");
		this.removeButton.setFont(large);
		this.sortButton = new MaterialButton("Sortiraj");
		this.sortButton.setFont(large);

		buttonPanel.add(addButton);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(removeButton);

		comboBox = new MaterialComboBox<>(Algorithms.getInstance().getList());
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(comboBox);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(sortButton);

		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void update() {
		setUpSections();
		scrollPane.setViewportView(sectionsPanel);
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		revalidate();
		repaint();
	}

	public JButton getAddButton() {
		return addButton;
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public JButton getSortButton() {
		return sortButton;
	}

	public SinglethreadedTestModel getModel() {
		return model;
	}

}
