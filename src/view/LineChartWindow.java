/***********************************************************************
 * Modul:  	LineChartView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise prozor linijskog grafikona
 ***********************************************************************/

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.RectangleInsets;

import components.MaterialButton;
import settings.ColorTheme;
import settings.Context;

@SuppressWarnings("serial")
public class LineChartWindow extends JFrame {

	private ChartPanel chartPanel;
	private JButton saveBtn;
	private JButton closeBtn;

	public LineChartWindow(DefaultXYDataset dataSet, String title, String xName, String yName) {
		setUpChart(dataSet, title, xName, yName);

		saveBtn = new MaterialButton("Sa\u010duvaj");
		saveBtn.addActionListener(saveBtnListener);
		closeBtn = new MaterialButton("Zatvori");
		closeBtn.addActionListener(closeBtnListener);
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPanel.add(saveBtn);
		btnPanel.add(Box.createHorizontalStrut(20));
		btnPanel.add(closeBtn);
		btnPanel.setBackground(Context.getContext().getColorTheme().getTextSecondaryColor());
		btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

		setLayout(new BorderLayout());

		add(chartPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);

		setTitle("Prikaz grafikona");
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\images\\graph-icon.png"));
		setSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(600, 400));
		Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(new Point(scrnSize.width / 2 - getSize().width / 2, scrnSize.height / 2 - getSize().height / 2));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Metoda vrsi podesavanje grafikona na osnovu proslijedjenih parametara
	 * @param dataSet - set podataka koji se prikazuju
	 * @param title - naslov grafikona
	 * @param xName - naziv x ose
	 * @param yName - naziv y ose
	 */
	private void setUpChart(DefaultXYDataset dataSet, String title, String xName, String yName) {
		ColorTheme th = Context.getContext().getColorTheme();

		JFreeChart chart = ChartFactory.createXYLineChart(title, xName, yName, dataSet, PlotOrientation.VERTICAL, true,
				true, false);

		XYPlot plot = (XYPlot) chart.getPlot();

		XYSplineRenderer splineRenderer = new XYSplineRenderer();
		splineRenderer.setSeriesPaint(0, th.getAccentColor());
		splineRenderer.setSeriesStroke(0, new BasicStroke(3));

		XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer();
		lineRenderer.setSeriesPaint(0, th.getAccentColor());
		lineRenderer.setSeriesStroke(0, new BasicStroke(3));

		if (Context.getContext().getPreferences().isSplineRenderer())
			plot.setRenderer(splineRenderer);
		else
			plot.setRenderer(lineRenderer);

		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2, 3 }, 0);
		plot.setDomainGridlineStroke(dashed);
		plot.setRangeGridlineStroke(dashed);
		plot.setRangeGridlinePaint(th.getThemeColor());
		plot.setDomainGridlinePaint(th.getThemeColor());
		plot.setBackgroundPaint(th.getTextSecondaryColor());

		chartPanel = new ChartPanel(chart);
		chart.setPadding(new RectangleInsets(10, 10, 10, 10));
	}

	/**
	 * Metoda cuva grafiku dijagrama u eksterni png fajl
	 * @param file - fajl u koji se slika cuva
	 */
	private void saveToPNG(File file) {
		int width = chartPanel.getSize().width;
		int height = chartPanel.getSize().height;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bufferedImage.createGraphics();

		chartPanel.paintAll(g);

		RenderedImage renderedImage = bufferedImage;
		try {
			ImageIO.write(renderedImage, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Listener zaduzen za izbor lokacije pomocu fajl dijaloga i cuvanje grafike u eksterni fajl
	 */
	private ActionListener saveBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
			fileChooser.addChoosableFileFilter(fileFilter);
			fileChooser.setPreferredSize(new Dimension(800, 600));

			int returnValue = fileChooser.showSaveDialog(LineChartWindow.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				Pattern pattern = Pattern.compile(".*\\.png");
				String selectedFile = fileChooser.getSelectedFile().getPath().toString();
				Matcher matcher = pattern.matcher(selectedFile);
				selectedFile = (matcher.find()) ? selectedFile : selectedFile + ".png";
				saveToPNG(new File(selectedFile));
			}
		}
	};

	/**
	 * Listener zaduzen za zatvaranje prozora grafikona
	 */
	private ActionListener closeBtnListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			LineChartWindow.this.dispose();
		}
	};

}
