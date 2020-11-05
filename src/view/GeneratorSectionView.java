/***********************************************************************
 * Modul:  	GeneratorSectionView.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pogled podsekcije generatora testnih podataka
 ***********************************************************************/

package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.MaterialButton;
import components.MaterialTextField;
import model.GeneratorModel;
import model.GeneratorSectionModel;
import settings.ColorTheme;
import settings.Context;
import settings.FontTheme;

@SuppressWarnings("serial")
public class GeneratorSectionView extends JPanel {

	private GeneratorSectionModel model;

	private JTextField path;
	private JTextField ammount;
	private JTextField from;
	private JTextField to;
	private JButton browse;
	private JButton delete;
	private JLabel number;

	public GeneratorSectionView(GeneratorSectionModel model) {
		this.model = model;
		ColorTheme theme = Context.getContext().getColorTheme();
		FontTheme fonts = Context.getContext().getFonts();
		setBackground(theme.getSectionColor());

		BoxLayout mainLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(mainLayout);

		JPanel pathRow = new JPanel();
		BoxLayout pathLayout = new BoxLayout(pathRow, BoxLayout.LINE_AXIS);
		pathRow.setLayout(pathLayout);
		setUpPathRow(pathRow);

		JPanel ammountRow = new JPanel();
		BoxLayout ammountLayout = new BoxLayout(ammountRow, BoxLayout.LINE_AXIS);
		ammountRow.setLayout(ammountLayout);
		setUpAmmountRow(ammountRow);

		JPanel messageRow = new JPanel(new GridLayout(1, 1));
		JLabel message = new JLabel(model.getMessage());
		message.setFont(fonts.getNoteFont());
		message.setForeground(theme.getAccentColor());
		messageRow.setOpaque(false);
		messageRow.add(message);

		JPanel inputPanel = new JPanel();
		BoxLayout inputLayout = new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS);
		inputPanel.setOpaque(false);
		inputPanel.setLayout(inputLayout);
		inputPanel.add(Box.createVerticalStrut(40));
		inputPanel.add(pathRow);
		inputPanel.add(Box.createVerticalStrut(20));
		inputPanel.add(ammountRow);

		inputPanel.add(Box.createVerticalStrut(20));
		inputPanel.add(messageRow);
		inputPanel.add(Box.createVerticalStrut(10));

		JPanel numberPanel = new JPanel();
		setUpNumberPanel(numberPanel);

		JPanel buttonPanel = new JPanel();
		setUpButtonPanel(buttonPanel);

		add(Box.createHorizontalStrut(20));
		add(numberPanel);
		add(inputPanel);
		add(buttonPanel);
		add(Box.createHorizontalStrut(20));
		setOpaque(false);
	}

	/**
	 * Metoda kreira panel sa dugmetom za uklanjanje sekcije
	 */
	private void setUpButtonPanel(JPanel buttonPanel) {
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		delete = new MaterialButton("Ukloni");
		delete.addActionListener(deletingListener);
		buttonPanel.add(Box.createVerticalStrut(40));
		buttonPanel.add(delete);
		buttonPanel.add(Box.createVerticalGlue());
	}

	/**
	 * Metoda kreira panel sa rednim brojem sekcije
	 */
	private void setUpNumberPanel(JPanel numberPanel) {
		ColorTheme theme = Context.getContext().getColorTheme();
		FontTheme fonts = Context.getContext().getFonts();
		numberPanel.setOpaque(false);
		number = new JLabel(String.valueOf(model.getSectionNumber()));
		number.setForeground(theme.getSpecialColor());
		number.setFont(fonts.getImportantFont());
		number.setHorizontalAlignment(SwingConstants.RIGHT);
		numberPanel.add(number);
	}

	/**
	 * Metoda kreira panel sa poljem za unos putanje
	 */
	private void setUpPathRow(JPanel pathRow) {
		FontTheme fonts = Context.getContext().getFonts();
		pathRow.setOpaque(false);
		Font labelFont = fonts.getLabelFont();
		JLabel pathLbl = new JLabel("Putanja:");
		pathLbl.setFont(labelFont);
		this.path = new MaterialTextField(512);
		this.path.setText(model.getPath());
		this.path.getDocument().addDocumentListener(textChanged);
		this.browse = new MaterialButton("...");
		browse.addActionListener(browseListener);

		pathRow.add(Box.createHorizontalStrut(10));
		pathRow.add(pathLbl);
		pathRow.add(Box.createHorizontalStrut(10));
		pathRow.add(path);
		pathRow.add(Box.createHorizontalStrut(10));
		pathRow.add(browse);
		pathRow.add(Box.createHorizontalStrut(10));
	}

	/**
	 * Metoda kreira panel sa tekstualnim poljem za unos parametara generatora
	 */
	private void setUpAmmountRow(JPanel ammountRow) {
		FontTheme fonts = Context.getContext().getFonts();
		ammountRow.setOpaque(false);
		Font labelFont = fonts.getLabelFont();
		JLabel ammountLbl = new JLabel("Koli\u010dina:");
		JLabel fromLbl = new JLabel("Od:");
		JLabel toLbl = new JLabel("Do:");
		ammountLbl.setFont(labelFont);
		fromLbl.setFont(labelFont);
		toLbl.setFont(labelFont);

		ammount = new MaterialTextField(64);
		ammount.setText(String.valueOf(model.getAmmount()));
		ammount.getDocument().addDocumentListener(textChanged);
		from = new MaterialTextField(64);
		from.setText(String.valueOf(model.getFrom()));
		from.getDocument().addDocumentListener(textChanged);
		to = new MaterialTextField(64);
		to.setText(String.valueOf(model.getTo()));
		to.getDocument().addDocumentListener(textChanged);

		ammountRow.add(Box.createHorizontalStrut(10));
		ammountRow.add(ammountLbl);
		ammountRow.add(Box.createHorizontalStrut(10));
		ammountRow.add(ammount);
		ammountRow.add(Box.createHorizontalStrut(20));
		ammountRow.add(fromLbl);
		ammountRow.add(Box.createHorizontalStrut(10));
		ammountRow.add(from);
		ammountRow.add(Box.createHorizontalStrut(20));
		ammountRow.add(toLbl);
		ammountRow.add(Box.createHorizontalStrut(10));
		ammountRow.add(to);
		ammountRow.add(Box.createHorizontalStrut(10));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setColor(Context.getContext().getColorTheme().getSectionColor());
		graphics2d.fillRoundRect(0, 0, getSize().width, getSize().height, 50, 50);
		super.paint(g);
	}

	/**
	 * Listener za uklanjanje podsekcije iz generatora test podataka
	 */
	private ActionListener deletingListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			GeneratorView gView = (GeneratorView) (GeneratorSectionView.this.getParent().getParent().getParent()
					.getParent().getParent());

			GeneratorModel gModel = gView.getModel();
			gModel.getGenerators().remove(model);
			gModel.notifyObservers();
		}
	};

	/**
	 * Listener za otvaranje fajl dijaloga i izbor putanje
	 */
	private ActionListener browseListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("TXT files", "txt");
			fileChooser.addChoosableFileFilter(fileFilter);
			fileChooser.setPreferredSize(new Dimension(800, 600));

			int returnValue = fileChooser.showSaveDialog(GeneratorSectionView.this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				Pattern pattern = Pattern.compile(".*\\.txt");
				String selectedFile = fileChooser.getSelectedFile().getPath().toString();
				Matcher matcher = pattern.matcher(selectedFile);
				selectedFile = (matcher.find()) ? selectedFile : selectedFile + ".txt";
				GeneratorSectionView.this.path.setText(selectedFile);
			}
		}
	};

	/**
	 * Listener za pracenje promjena u tekstualnim poljima. Azurira model po potrebi
	 */
	private DocumentListener textChanged = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateModel();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateModel();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			updateModel();
		}
	};

	/**
	 * Metoda za azuriranje modela na osnovu stanja pogleda
	 */
	public void updateModel() {
		model.setPath(path.getText());
		try {
			model.setAmmount(Integer.valueOf(ammount.getText()));
			model.setFrom(Integer.valueOf(from.getText()));
			model.setTo(Integer.valueOf(to.getText()));
			model.setMessage(" ");
		} catch (NumberFormatException e) {
		}
	}

	public JTextField getPath() {
		return path;
	}

	public JTextField getAmmount() {
		return ammount;
	}

	public JTextField getFrom() {
		return from;
	}

	public JTextField getTo() {
		return to;
	}

}
