package org.dependencies.html;

import static java.lang.String.format;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepCorpus;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepLanguage;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepNode;
import org.dependencies.model.DepWording;

/**
 * An HTML file writer for analyzed texts.
 * 
 * @author Daniel Couto-Vale
 */
public class HtmlFileWriter {

	/**
	 * Exports the analyzed text into an HTML file.
	 * 
	 * @param corpusName the corpus name
	 * @param languageName the language name
	 * @param textTitle the text title
	 * @param descriptionName the description name
	 * @param analysisName the analysis name
	 * @param fileName the file name
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public final void exportFile(String corpusName, String languageName, String textTitle, String descriptionName,
			String analysisName, String fileName) throws SQLException, FileNotFoundException {
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepCorpus corpus = base.getCorpus(corpusName);
		if (corpus == null) {
			System.err.println(format("The corpus %s does not exist!", corpusName));
			System.exit(-1);
		}
		DepLanguage language = base.getLanguage(languageName);
		if (language == null) {
			System.err.println(format("The language %s does not exist!", languageName));
			System.exit(-1);
		}
		DepDescription description = base.getDescription(descriptionName);
		if (description == null) {
			System.err.println(format("The description %s does not exist!", descriptionName));
			System.exit(-1);
		}
		DepAnalysis analysis = base.getAnalysis(description.getId(), analysisName);
		if (analysis == null) {
			System.err.println(format("The analysis %s does not exist!", analysisName));
			System.exit(-1);
		}
		DepAnalyzedText text = base.getAnalyzedText(corpus.getId(), language.getId(), textTitle, analysis.getId());
		if (text == null) {
			System.err.println(format("The text %s does not exist!", textTitle));
			System.exit(-1);
		}
		File file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		BufferedWriter bw = new BufferedWriter(osw);
		PrintWriter pw = new PrintWriter(bw);
		pw.print("<html>\n");
		pw.print("<head>\n");
		pw.print("<style>\n");
		pw.print("table { margin: 20px; border-spacing: 0px; border-bottom: 1px solid black; border-right: 1px solid black; }\n");
		pw.print("table, th, td { box-sizing: border-box; font-size: 11px; text-align: center; }");
		pw.print("th, td { border-left: 1px solid black; border-top: 1px solid black; }");
		pw.print("</style>\n");
		pw.print("</head>\n");
		pw.print("<body>\n");
		for (DepWording wording : text.getWordings()) {
			wording.makeDependencyTree();
			pw.print(format("<table width='%dpx'>\n", (wording.getWords().size() + 1) * 100));
			pw.print("<tr>");
			pw.print("<th width='100px'>Logic</th>");
			DepNode tree = wording.makeDependencyTree();
			Integer index = - 2;
			for (DepWord word : wording) {
				Integer newIndex = tree.indexOf(word);
				if (index != newIndex) {
					index = newIndex;
					Integer span = 1;
					List<DepWord> words = wording.getWords();
					for (int i = wording.orderOf(word); i < words.size(); i++) {
						Integer otherIndex = tree.indexOf(words.get(i));
						if (newIndex == otherIndex) {
							span++;
						} else {
							break;
						}
					}
					pw.print(format("<td width='100px' colspan='%d'>", span));
					pw.print(tree.getFunctionName(index));
					pw.print("</td>\n");
				}
			}
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<th width='100px'>Form</th>");
			for (DepWord word : wording) {
				pw.print("<td width='100px'><i>");
				pw.print(word.getForm());
				pw.print("</i></td>\n");
			}
			pw.print("</tr>\n");
			pw.print("</table>\n");
		}
		pw.print("</body>\n");
		pw.print("</html>\n");
		pw.close();
	}

}
