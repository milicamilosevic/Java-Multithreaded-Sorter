/***********************************************************************
 * Modul:  	ThemeWriter.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise pomocnu klasu zaduzenu za zapisivanje teme u JSON
 * 			formatu
 ***********************************************************************/

package helpers;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import settings.ColorTheme;

public class ThemeWriter {

	public static void main(String[] args) {
		writeTheme("newspaper");
	}

	/**
	 * Metoda zaduzena za zapisivanje teme u JSON fajl.
	 * @param name - naziv teme, odnosno fajla 
	 */
	public static void writeTheme(String name) {
		ColorTheme colorTheme;
		colorTheme = new ColorTheme();
		colorTheme.setThemeColor(new Color(255, 137, 59));
		colorTheme.setThemeDarkerColor(new Color(241, 96, 0));
		colorTheme.setThemeLighterColor(new Color(254, 157, 94));
		colorTheme.setSectionColor(new Color(194, 194, 194));
		colorTheme.setBackgroundColor(Color.WHITE);
		colorTheme.setTextPrimaryColor(new Color(50, 50, 50));
		colorTheme.setTextSecondaryColor(Color.WHITE);
		colorTheme.setAccentColor(new Color(61, 20, 3));
		colorTheme.setSpecialColor(new Color(61, 20, 3));
		colorTheme.setInputColor(new Color(92, 92, 92));

		colorTheme.setThemeName(name);
		colorTheme.setBackgroundImageUrl("resources" + File.separator + "images" + File.separator + "newspaper.png");

		Gson gson = new GsonBuilder().create();
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter("resources" + File.separator + "themes" + File.separator + name + ".json"));
			gson.toJson(colorTheme, writer);
			writer.close();
		} catch (IOException e) {
		}
	}

}
