/***********************************************************************
 * Modul:  	Context.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Singleton klasa, nosi informacije o trenutnim podesavanjima
 * 			aplikacije i trenutno izabranoj temi 
 ***********************************************************************/

package settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Context {
	private static Context instance = null;
	private Preferences preferences;
	private ColorTheme colorTheme;
	private FontTheme fonts;

	private Context() {
		// registerFonts();
		readPreferences();
		readTheme();
		readFonts();
	}

	/**
	 * Metoda vraca singleton instancu klase
	 */
	public static Context getContext() {
		if (instance == null)
			instance = new Context();
		return instance;
	}

	/**
	 * Metoda cita podesavanja iz JSON fajla, u slucaju da to nije moguce kreira se fajl
	 * sa podrazumijevanim podesavanjima.
	 */
	private void readPreferences() {
		Gson gson = new GsonBuilder().create();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("preferences" + File.separator + "current.json"));
			preferences = gson.fromJson(reader, Preferences.class);
			reader.close();
		} catch (IOException e) {
			writeDefaultPreferences();
		}
	}

	/**
	 * Metoda cita temu iz JSON fajla. U slucaju da to nije moguce kreira se fajl
	 * sa podrazumijevanim podesavanjima.
	 */
	private void readTheme() {
		Gson gson = new GsonBuilder().create();
		try {
			File themeFile = new File(
					"resources" + File.separator + "themes" + File.separator + preferences.getThemeName() + ".json");
			BufferedReader reader = new BufferedReader(new FileReader(themeFile));
			colorTheme = gson.fromJson(reader, ColorTheme.class);
			reader.close();
		} catch (IOException e) {
			preferences.setThemeName("default-gray");
			savePreferences();
			writeDefaultTheme();
		}
	}

	/**
	 * Citanje podesavanja vezanih za font iz JSON fajla. U slucaju da to nije moguce kreira se fajl
	 * sa podrazumijevanim podesavanjima.
	 */
	private void readFonts() {
		Gson gson = new GsonBuilder().create();
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("resources" + File.separator + "font-settings.json"));
			fonts = gson.fromJson(reader, FontTheme.class);
			reader.close();
		} catch (IOException e) {
			writeDefaultFontSettings();
		}
	}

	/**
	 * Registrovanje korisnickih fontova u graficko okruzenje. Metoda se koristi za dodavanje novih fontova,
	 * koji nisu dodati unutar podesavanja operativnog sistema
	 */
	public void registerFonts() {
		File fontsDirectory = new File("resources//fonts");
		File[] fontFiles = fontsDirectory.listFiles();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		FileInputStream inputStream = null;
		for (File file : fontFiles) {
			try {
				inputStream = new FileInputStream(file);
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, inputStream));
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Metoda cuva trenutna podesavanja u tekstualni fajl (JSON)
	 */
	public void savePreferences() {
		Gson gson = new GsonBuilder().create();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("preferences" + File.separator + "current.json"));
			gson.toJson(preferences, writer);
			writer.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Metoda zaduzena za zapisivanje podrazumijevanih podesavanja u JSON fajl
	 */
	public void writeDefaultPreferences() {
		preferences = new Preferences();
		preferences.setThemeName("default-gray");
		preferences.setLanguage("english");
		preferences.setSplineRenderer(false);
		preferences.setMaxThreadsAllowed(32);
		preferences.setFilesLimit(5);
		
		Gson gson = new GsonBuilder().create();
		try {
			Files.createDirectories(Paths.get("preferences"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("preferences" + File.separator + "current.json"));
			gson.toJson(preferences, writer);
			writer.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Metoda zaduzena za zapisivanje podrazumijevane teme u JSON fajl
	 */
	public void writeDefaultTheme() {
		colorTheme = new ColorTheme();
		colorTheme.setThemeColor(new Color(92, 92, 92));
		colorTheme.setThemeDarkerColor(new Color(58, 58, 58));
		colorTheme.setThemeLighterColor(new Color(117, 117, 117));
		colorTheme.setSectionColor(new Color(197, 197, 197));
		colorTheme.setBackgroundColor(Color.WHITE);
		colorTheme.setTextPrimaryColor(new Color(50, 50, 50));
		colorTheme.setTextSecondaryColor(Color.WHITE);
		colorTheme.setAccentColor(new Color(3, 102, 214));
		colorTheme.setSpecialColor(Color.BLACK);
		colorTheme.setInputColor(new Color(92, 92, 92));

		colorTheme.setThemeName("default-gray");
		colorTheme.setBackgroundImageUrl("resources/images/numbers.png");

		Gson gson = new GsonBuilder().create();
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("resources" + File.separator + "themes" + File.separator + "default-gray.json"));
			gson.toJson(colorTheme, writer);
			writer.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Metoda zaduzena za zapisivanje podrazumijevanih podesavanja vezanih za font u JSON fajl
	 */
	public void writeDefaultFontSettings() {
		fonts = new FontTheme();
		fonts.setPanelButtonFont(new Font("Roboto Cn", Font.PLAIN, 20));
		fonts.setMainButtonFont(new Font("Roboto Cn", Font.PLAIN, 24));
		fonts.setLabelFont(new Font("Roboto Cn", Font.PLAIN, 20));
		fonts.setTextFieldFont(new Font("Roboto Cn", Font.PLAIN, 18));
		fonts.setStatusBarFont(new Font("Roboto Cn", Font.PLAIN, 18));
		fonts.setImportantFont(new Font("Roboto Cn", Font.BOLD | Font.ITALIC, 36));
		fonts.setNoteFont(new Font("Roboto Cn", Font.ITALIC, 14));

		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("resources" + File.separator + "font-settings.json"));
			Gson gson = new GsonBuilder().create();
			gson.toJson(fonts, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public ColorTheme getColorTheme() {
		return colorTheme;
	}

	public FontTheme getFonts() {
		return fonts;
	}

	public void setPreferences(Preferences preferences) {
		this.preferences = preferences;
	}

	public void setColorTheme(ColorTheme colorTheme) {
		this.colorTheme = colorTheme;
	}
}
