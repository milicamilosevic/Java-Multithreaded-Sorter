/***********************************************************************
 * Modul:  	InfoModel.java
 * Autori:  Milica Milosevic, Boris Boskovic
 * Svrha: 	Definise model sekcije informacije
 ***********************************************************************/

package model;

import java.io.File;
import java.util.ArrayList;

public class InfoModel {

	private String userName;
	private String OS_name;
	private String OS_version;
	private String OS_architecture;
	private int coresCount;
	private String processorName;
	private ArrayList<Disk> disks;

	/**
	 * Konstruktor klase. Pri instanciranju se citaju sistemske informacije
	 */
	public InfoModel() {
		this.userName = System.getProperty("user.name");
		this.OS_name = System.getProperty("os.name");
		this.OS_version = System.getProperty("os.version");
		this.OS_architecture = System.getProperty("os.arch");
		this.coresCount = Runtime.getRuntime().availableProcessors();
		this.processorName=System.getenv("PROCESSOR_IDENTIFIER");

		disks=new ArrayList<>();
		File[] roots = File.listRoots();

		for (File root : roots) {
			disks.add(new Disk(root));
		}

	}

	/**
	 * Klasa zaduzena za cuvanje informacija o disku
	 * @author Boris Boskovic, Milica Milosevic
	 */
	public class Disk{
		private String path;
		private long totalBytes;
		private long freeBytes;
		
		public Disk(File root) {
			this.path=root.getAbsolutePath();
			this.totalBytes=root.getTotalSpace();
			this.freeBytes=root.getFreeSpace();
		}
		
		public String getPath() {
			return path;
		}
		
		public long getTotalBytes() {
			return totalBytes;
		}
		
		public long getFreeBytes() {
			return freeBytes;
		}
	}

	public String getUserName() {
		return userName;
	}

	public String getOS_name() {
		return OS_name;
	}

	public String getOS_version() {
		return OS_version;
	}

	public String getOS_architecture() {
		return OS_architecture;
	}

	public int getCoresCount() {
		return coresCount;
	}

	public String getProcessorName() {
		return processorName;
	}

	public ArrayList<Disk> getDisks() {
		return disks;
	}
	
	
	
}
