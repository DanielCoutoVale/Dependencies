package org.dependencies;

import static java.lang.String.format;

import org.dependencies.html.HtmlFileWriter;

/**
 * Exports a CONLLU file.
 * 
 * @author Daniel Couto-Vale
 */
public class ExportHtmlFile {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 6) {
			System.err.println("USAGE:");
			System.err.println("java -jar export-html-file.jar [corpus] [language] [text] [description] [analysis] [file]");
			System.exit(-1);
		}
		String corpusName = args[0];
		String languageName = args[1];
		String textTitle = args[2];
		String descriptionName = args[3];
		String analysisName = args[4];
		String fileName = args[5];
		HtmlFileWriter exporter = new HtmlFileWriter();
		try {
			System.out.println(format("Exporting %s to %s", textTitle, fileName));
			exporter.exportFile(corpusName, languageName, textTitle, descriptionName, analysisName, fileName);
			System.out.println("Exported!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

}
