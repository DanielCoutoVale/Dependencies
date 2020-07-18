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

import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
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
	 * Writes an HTML file for the analyzed text.
	 * 
	 * @param text the analyzed text
	 * @param fileName the file name
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public final void writeHtmlFile(DepAnalyzedText text, String fileName) throws SQLException, FileNotFoundException {
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
			pw.print("<th width='100px'>Clauses</th>");
			DepNode tree = wording.makeDependencyTree();
			long index;
			// Run through clauses
			index = - 2;
			for (DepWord word : wording) {
				long newIndex = tree.indexOf(word, "clause-complex");
				if (index != newIndex) {
					index = newIndex;
					Integer span = 1;
					List<DepWord> words = wording.getWords();
					for (int i = wording.orderOf(word); i < words.size(); i++) {
						long otherIndex = tree.indexOf(words.get(i), "clause-complex");
						if (newIndex == otherIndex) {
							span++;
						} else {
							break;
						}
					}
					pw.print(format("<td width='100px' colspan='%d'>", span));
					pw.print(tree.getFunctionName(index, "clause-complex"));
					pw.print("</td>\n");
				}
			}
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<th width='100px'>Clause</th>");
			// Run through clause constituents
			index = - 2;
			for (DepWord word : wording) {
				long newIndex = tree.indexOf(word, "clause");
				if (index != newIndex) {
					index = newIndex;
					Integer span = 1;
					List<DepWord> words = wording.getWords();
					for (int i = wording.orderOf(word); i < words.size(); i++) {
						long otherIndex = tree.indexOf(words.get(i), "clause");
						if (newIndex == otherIndex) {
							span++;
						} else {
							break;
						}
					}
					pw.print(format("<td width='100px' colspan='%d'>", span));
					pw.print(tree.getFunctionName(index, "clause"));
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
			pw.print("<th width='100px'>Word Class</th>");
			DepDescription description = text.getDescription();
			for (DepWord word : wording) {
				DepFeature wordClass = word.getFeatureIn(description.getSystem("WORD-CLASS"));
				pw.print("<td width='100px'>");
				pw.print(wordClass == null ? "-" : wordClass.getName());
				pw.print("</td>\n");
			}
			pw.print("</tr>\n");
			pw.print("</table>\n");
		}
		pw.print("</body>\n");
		pw.print("</html>\n");
		pw.close();
	}

}
