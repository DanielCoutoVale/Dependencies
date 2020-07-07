package org.dependencies;

import java.io.IOException;
import java.sql.SQLException;

import org.dependencies.sys.SysFileImporter;

/**
 * Imports a SYS file.
 * 
 * @author Daniel Couto-Vale
 */
public class ImportSysFile {
	
	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE:");
			System.err.println("java -jar import-sys-file.jar [description] [file]");
			System.exit(-1);
		}
		String descriptionName = args[0];
		String fileName = args[1];
		SysFileImporter importer = new SysFileImporter();
		try {
			importer.importFile(descriptionName, fileName);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}
	
}
