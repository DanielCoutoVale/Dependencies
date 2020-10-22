package org.dependencies;

import static java.lang.String.format;

import org.dependencies.conllu.ConlluFileWriter;
import org.dependencies.html.HtmlFileWriter;
import org.dependencies.model.DepAnalyzedTextLoader;
import org.dependencies.model.DepSearchResult;

/**
 * Query an analysis
 * 
 * @author Daniel Couto-Vale
 */
public class QueryAnalysis {

	public static final void main(String[] args) {
		if (args.length != 7) {
			System.err.println("USAGE:");
			System.err.println("java -jar query-analysis.jar [corpus] [language] [text] [description] [analysis] [file] [query]");
			System.exit(-1);
		}
		String corpusName = args[0];
		String languageName = args[1];
		String textTitle = args[2];
		String descriptionName = args[3];
		String analysisName = args[4];
		String fileName = args[5];
		String query = args[6];
		DepAnalyzedTextLoader loader = new DepAnalyzedTextLoader();
		ConlluFileWriter conlluFileWriter = new ConlluFileWriter();
		HtmlFileWriter htmlFileWriter = new HtmlFileWriter();
		try {
			System.out.println(format("Querying %s and saving results to %s", textTitle, fileName));
			DepSearchResult result = loader.searchForWordings(descriptionName, analysisName, corpusName, languageName,
					textTitle, query);
			if (fileName.endsWith(".conllu")) {
				conlluFileWriter.writeConlluFile(result.getWordings(), result.getDescription(), fileName);
			} else if (fileName.endsWith(".html")) {
				htmlFileWriter.writeHtmlFile(result.getWordings(), textTitle, result.getDescription(), fileName);
			} else {
				conlluFileWriter.writeConlluFile(result.getWordings(), result.getDescription(), fileName + ".conllu");
				htmlFileWriter.writeHtmlFile(result.getWordings(), textTitle, result.getDescription(), fileName + ".html");
			}
			System.out.println("Queried!");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}

	}
	
}
