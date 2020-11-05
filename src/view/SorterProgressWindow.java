/***********************************************************************
 * Modul:  	SorterProgresWindow.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise prozor komponente za pracenje progresa testa
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.MaterialButton;
import settings.Context;

@SuppressWarnings("serial")
public class SorterProgressWindow extends JFrame {

	private JLabel loader;
	private JLabel progress;
	private JLabel description;
	private JButton abort;

	public SorterProgressWindow() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
		panel.setLayout(boxLayout);

		Font font = Context.getContext().getFonts().getMainButtonFont();

		Image loaderImg = Toolkit.getDefaultToolkit().getImage("resources\\images\\loading-ring.gif");
		ImageIcon icon = new ImageIcon(loaderImg);
		loader = new JLabel(icon);
		loader.setPreferredSize(new Dimension(200, 200));
		JPanel loaderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		loaderPanel.setBackground(Color.WHITE);
		loaderPanel.add(loader);

		progress = new JLabel(" ");
		progress.setForeground(new Color(44, 72, 254));
		progress.setFont(font);
		JPanel progressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		progressPanel.setBackground(Color.WHITE);
		progressPanel.add(progress);

		description = new JLabel(" ");
		description.setForeground(new Color(44, 72, 254));
		description.setFont(font);
		JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		descriptionPanel.setBackground(Color.WHITE);
		descriptionPanel.add(description);

		abort = new MaterialButton("Prekini");
		JPanel abortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		abortPanel.setOpaque(false);
		abortPanel.add(abort);

		panel.add(Box.createVerticalGlue());
		panel.add(loaderPanel);
		panel.add(progressPanel);
		panel.add(descriptionPanel);
		panel.add(abortPanel);

		add(panel);
		setSize(new Dimension(640, 480));
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new Point(scrnSize.width / 2 - getSize().width / 2, scrnSize.height / 2 - getSize().height / 2));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public JButton getAbort() {
		return abort;
	}

	public void setProgress(String progress) {
		this.progress.setText(progress);
	}

	public void setDescription(String description) {
		this.description.setText(description);
	}

}
