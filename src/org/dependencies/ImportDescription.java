package org.dependencies;

import static java.lang.String.format;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.dependencies.sys.SysFileReader;
import org.dependencies.ud.UdFileReader;
import org.xml.sax.SAXException;

/**
 * Imports a description from either a SYS file or an XML file.
 * 
 * @author Daniel Couto-Vale
 */
public class ImportDescription {
	
	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE:");
			System.err.println("java -jar import-description.jar [description] [file]");
			System.exit(-1);
		}
		String descriptionName = args[0];
		String fileName = args[1];
		try {
			System.out.println(format("Importing descrition %s from %s", descriptionName, fileName));
			if (fileName.endsWith(".sys")) {
				SysFileReader reader = new SysFileReader();
				reader.importFile(descriptionName, fileName);
			} else if (fileName.endsWith(".xml")) {
				UdFileReader reader = new UdFileReader();
				reader.importFile(descriptionName, fileName);
			} else {
				System.err.println("This extension is not supported!");
				System.exit(-1);
			}
			System.out.println("Imported!");
		} catch (IOException | SQLException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script!");
		}
	}
	
}
