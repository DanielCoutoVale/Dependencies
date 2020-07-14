package org.dependencies;

import static java.lang.String.format;

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
	public final static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE:");
			System.err.println("java -jar import-ud-file.jar [description] [file]");
			System.exit(-1);
		}
		UdFileImporter importer = new UdFileImporter();
		String descriptionName = args[0];
		String fileName = args[1];
		try {
			System.out.println(format("Importing %s as %s", fileName, descriptionName));
			importer.importFile(descriptionName, fileName);
			System.out.println("Imported");
		} catch (ParserConfigurationException | SAXException | IOException | SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

	

}
