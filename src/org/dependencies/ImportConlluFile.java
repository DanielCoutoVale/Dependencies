package org.dependencies;

import java.sql.SQLException;

import org.dependencies.conllu.ConlluFileImporter;

/**
 * Imports a CONLLU file.
 * 
 * @author Daniel Couto-Vale
 */
public class ImportConlluFile {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 6) {
			System.err.println("USAGE:");
			System.err.println("java -jar import-conllu-file.jar [corpus] [language] [text] [description] [analysis] [file]");
			System.exit(-1);
		}
		String corpusName = args[0];
		String languageName = args[1];
		String textTitle = args[2];
		String descriptionName = args[3];
		String analysisName = args[4];
		String fileName = args[5];
		ConlluFileImporter importer = new ConlluFileImporter();
		try {
			importer.importFile(corpusName, languageName, textTitle, descriptionName, analysisName, fileName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

}
