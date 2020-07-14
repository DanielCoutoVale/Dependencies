package org.dependencies;

import static java.lang.String.format;

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
			System.out.println(format("Importing %s as %s", fileName, descriptionName));
			importer.importFile(descriptionName, fileName);
			System.out.println("Imported!");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}
	
}
