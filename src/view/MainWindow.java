/***********************************************************************
 * Modul:  	MainWindow.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise glavni prozor aplikacije
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.ImageBackgroundContentPane;
import model.GeneratorModel;
import model.InfoModel;
import model.MultithreadedTestModel;
import model.SinglethreadedTestModel;
import model.PanelSwitchingModel;
import model.SettingsModel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ObserverInterface {

	private ArrayList<JPanel> views;

	private JPanel mainPanel;
	private PanelSwitchingModel model;

	public MainWindow(PanelSwitchingModel model) {
		this.model = model;
		this.model.addObserver(this);

		this.views = new ArrayList<>();
		this.views.add(new InfoView(new InfoModel()));
		this.views.add(new GeneratorView(new GeneratorModel()));
		this.views.add(new SinglethreadedTestView(new SinglethreadedTestModel()));
		this.views.add(new MultithreadedTestView(new MultithreadedTestModel()));
		this.views.add(new SettingsView(new SettingsModel()));
		this.views.add(new AboutView());

		mainPanel = views.get(0);

		setTitle("Multithreaded sorter - by Milica M. & Boris B.");
		setSize(new Dimension(1366, 768));
		setMinimumSize(new Dimension(1100, 576));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(getToolkit().getImage("resources/images/icon.png"));

		setContentPane(new ImageBackgroundContentPane());

		// Side Menu Component
		SideMenuView sideMenu = new SideMenuView(model);
		sideMenu.setPreferredSize(new Dimension(250, 1000));
		sideMenu.setEdge(15);
		setLayout(new BorderLayout());
		add(sideMenu, BorderLayout.WEST);

		add(mainPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	@Override
	public void update() {
		this.remove(mainPanel);
		this.mainPanel = views.get(model.getActive());
		this.add(mainPanel);
		this.revalidate();
		this.repaint();
	}

}
