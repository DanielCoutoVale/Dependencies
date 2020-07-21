package org.dependencies;

import static java.lang.String.format;

import org.dependencies.conllu.ConlluFileWriter;
import org.dependencies.html.HtmlFileWriter;
import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepAnalyzedTextLoader;

/**
 * Exports a CONLLU file.
 * 
 * @author Daniel Couto-Vale
 */
public class ExportAnalysis {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 6) {
			System.err.println("USAGE:");
			System.err.println("java -jar export-analysis.jar [corpus] [language] [text] [description] [analysis] [file]");
			System.exit(-1);
		}
		String corpusName = args[0];
		String languageName = args[1];
		String textTitle = args[2];
		String descriptionName = args[3];
		String analysisName = args[4];
		String fileName = args[5];
		DepAnalyzedTextLoader loader = new DepAnalyzedTextLoader();
		ConlluFileWriter conlluFileWriter = new ConlluFileWriter();
		HtmlFileWriter htmlFileWriter = new HtmlFileWriter();
		try {
			System.out.println(format("Exporting %s to %s", textTitle, fileName));
			DepAnalyzedText text = loader.loadAnalyzedText(descriptionName, analysisName, corpusName, languageName,
					textTitle);
			if (fileName.endsWith(".conllu")) {
				conlluFileWriter.writeConlluFile(text, fileName);
			} else if (fileName.endsWith(".html")) {
				htmlFileWriter.writeHtmlFile(text, fileName);
			} else {
				conlluFileWriter.writeConlluFile(text, fileName + ".conllu");
				htmlFileWriter.writeHtmlFile(text, fileName + ".html");
			}
			System.out.println("Exported!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

}
