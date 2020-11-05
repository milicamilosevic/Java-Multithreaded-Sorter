/***********************************************************************
 * Modul:  	SorterFinishWindow.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise dijalog prozor koji obavjestava o zavrsetku testa 
 ***********************************************************************/

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.MaterialButton;
import helpers.ArrayWithPath;
import settings.Context;
import settings.FontTheme;

@SuppressWarnings("serial")
public class SorterFinishWindow extends JDialog {
	private ArrayList<ArrayWithPath> arrays;
	private JButton yes;
	private JButton no;

	private FontTheme fonts;

	public SorterFinishWindow(ArrayList<ArrayWithPath> arrays, String message) {
		this.arrays = arrays;

		this.fonts = Context.getContext().getFonts();
		Font font = fonts.getMainButtonFont();

		JPanel mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS);
		mainPanel.setLayout(mainPanelLayout);
		mainPanel.setBackground(Color.WHITE);

		Image done = Toolkit.getDefaultToolkit().getImage("resources\\images\\done.png").getScaledInstance(100, 100,
				Image.SCALE_AREA_AVERAGING);
		JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		imagePanel.setBackground(Color.WHITE);
		imagePanel.add(new JLabel(new ImageIcon(done)));

		JPanel finishPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel finishLabel = new JLabel("Zavr\u0161eno");
		finishLabel.setFont(font);
		finishLabel.setForeground(new Color(44, 72, 254));
		finishPanel.setBackground(Color.WHITE);
		finishPanel.add(finishLabel);

		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel messageLabel = new JLabel(message);
		messageLabel.setFont(font);
		messageLabel.setForeground(new Color(44, 72, 254));
		messagePanel.setBackground(Color.WHITE);
		messagePanel.add(messageLabel);

		JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		questionPanel.setBackground(Color.WHITE);
		JLabel questionLbl = new JLabel("Da li \u017eelite da sa\u010duvate sortirane podatke?");
		questionLbl.setFont(font);
		questionLbl.setForeground(new Color(44, 72, 254));
		questionPanel.add(questionLbl);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(Color.WHITE);
		yes = new MaterialButton(" Da ");
		yes.addActionListener(yesBtnListener);
		no = new MaterialButton(" Ne ");
		no.addActionListener(noBtnListener);
		buttonPanel.add(yes);
		buttonPanel.add(Box.createHorizontalStrut(100));
		buttonPanel.add(no);
		JPanel promptPanel = new JPanel();
		BoxLayout promptPanelLayout = new BoxLayout(promptPanel, BoxLayout.PAGE_AXIS);
		promptPanel.setLayout(promptPanelLayout);
		promptPanel.add(questionPanel);
		promptPanel.add(buttonPanel);

		mainPanel.add(Box.createVerticalStrut(50));
		mainPanel.add(imagePanel);
		mainPanel.add(finishPanel);
		mainPanel.add(messagePanel);
		mainPanel.add(Box.createVerticalStrut(50));
		mainPanel.add(promptPanel);

		add(mainPanel);
		setSize(new Dimension(640, 480));
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new Point(scrnSize.width / 2 - getSize().width / 2, scrnSize.height / 2 - getSize().height / 2));
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModal(true);
		setVisible(true);
	}

	/**
	 * Listener zaduzen za cuvanje sortiranih podataka u tekstualni fajl (pri ozboru opcije DA)
	 */
	private ActionListener yesBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SorterFinishWindow.this.setEnabled(false);
			for (ArrayWithPath array : arrays) {
				try {
					String path = array.getPath();
					path = path.substring(0, path.length() - 4) + " - sortirano.txt";
					BufferedWriter writer = new BufferedWriter(new FileWriter(path));
					for (Integer num : array.getArray()) {
						writer.write(num + ", ");
					}
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			SorterFinishWindow.this.dispose();
		}
	};

	/**
	 * Listener zaduzen za zatvaranje dijalog prozora pri izboru opcije ne
	 */
	private ActionListener noBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SorterFinishWindow.this.dispose();
		}
	};

}
