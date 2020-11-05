/***********************************************************************
 * Modul:  	Preferences.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise klasu podesavanja aplikacije
 ***********************************************************************/


package settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Preferences {
	private String themeName;
	private String language;
	private boolean splineRenderer;
	private int maxThreadsAllowed;
	private int filesLimit;

	/**
	 * Metoda cuva podesavanja u JSON fajlu
	 */
	public void save() {
		Gson gson = new GsonBuilder().create();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("preferences" + File.separator + "current.json"));
			gson.toJson(this, writer);
			writer.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Metoda zapisuje podrazumijevana podesavanja u JSON fajl
	 */
	public static void writeDefaultPrefferences() {
		Preferences pref = new Preferences();
		pref.themeName = "default-gray";
		pref.language = "english";
		pref.splineRenderer = false;
		pref.maxThreadsAllowed = 32;
		pref.filesLimit = 5;

		Gson gson = new GsonBuilder().create();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("preferences" + File.separator + "current.json"));
			gson.toJson(pref, writer);
			writer.close();
		} catch (IOException e) {
		}
	}

	
	public String getThemeName() {
		return themeName;
	}
	
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isSplineRenderer() {
		return splineRenderer;
	}

	public void setSplineRenderer(boolean splineRenderer) {
		this.splineRenderer = splineRenderer;
	}

	public int getMaxThreadsAllowed() {
		return maxThreadsAllowed;
	}

	public void setMaxThreadsAllowed(int maxThreadsAllowed) {
		this.maxThreadsAllowed = maxThreadsAllowed;
	}

	public int getFilesLimit() {
		return filesLimit;
	}

	public void setFilesLimit(int filesLimit) {
		this.filesLimit = filesLimit;
	}

	
}
