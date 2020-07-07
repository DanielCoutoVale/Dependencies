package org.dependencies;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.dependencies.ud.UdFileImporter;
import org.xml.sax.SAXException;

/**
 * Imports a UD file.
 * 
 * @author Daniel Couto-Vale
 */
public class ImportUdFile {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public static final void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE:");
			System.err.println("java -jar import-ud-file.jar [description] [file]");
		}
		UdFileImporter importer = new UdFileImporter();
		String descriptionName = args[0];
		String fileName = args[1];
		try {
			importer.importFile(descriptionName, fileName);
		} catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

	

}
