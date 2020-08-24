package org.dependencies.conllu;

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
import org.dependencies.model.DepDependency;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepSystem;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepWording;

/**
 * A CONLLU file exporter.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluFileWriter {

	/**
	 * Writes a CONLLU file for the analyzed text.
	 * 
	 * @param text the analyzed text
	 * @param fileName the file name
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public final void writeConlluFile(DepAnalyzedText text, String fileName) throws SQLException, FileNotFoundException {
		writeConlluFile(text.getWordings(), text.getDescription(), fileName);
	}

	public final void writeConlluFile(List<DepWording> wordings, DepDescription description, String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		BufferedWriter bw = new BufferedWriter(osw);
		PrintWriter pw = new PrintWriter(bw);
		DepSystem wordClasses = description.getSystem("WORD-CLASS");
		for (DepWording wording : wordings) {
			pw.println(format("# sent_id = %s-s%d", description.getName(), wording.getId()));
			pw.println(format("# text = %s", wording.getForm()));
			wording.makeDependencyTree();
			for (DepWord word : wording) {
				String wordClassName = getWordClassName(wordClasses, word);
				pw.print(wording.orderOf(word));
				pw.print("\t");
				pw.print(word.getForm());
				pw.print("\t");
				pw.print(wordClassName);
				pw.print("-");
				pw.print(word.getLemma());
				pw.print("\t");
				pw.print(wordClassName);
				pw.print("\t");
				pw.print("-");
				pw.print("\t");
				pw.print(getWordFeatureLabels(description, wordClasses, word));
				pw.print("\t");
				pw.print(getHeadOrder(word));
				pw.print("\t");
				pw.print(getFunction(word));
				pw.print("\t");
				pw.print("-");
				pw.print("\t");
				pw.print(getMixedLabels(word));
				pw.print("\n");
			}
			pw.print("\n");
		}
		pw.close();
	}

	private String getFunction(DepWord word) {
		String wordRank = "";
		String function = "root";
		String headRank = "";
		if (word.getDependencies().size() >= 1) {
			DepDependency dependency = word.getDependencies().get(0);
			wordRank = dependency.getWordRank().getName() + ":";
			function = dependency.getFunction().getName();
			headRank = ":" + dependency.getHeadRank().getName();
		}
		return wordRank + function + headRank;
	}

	private String getHeadOrder(DepWord word) {
		String headOrder = "0";
		if (word.getDependencies().size() >= 1) {
			DepDependency dependency = word.getDependencies().get(0);
			headOrder = Integer.toString(dependency.getHeadOrder());
		}
		return headOrder;
	}

	private String getWordFeatureLabels(DepDescription description, DepSystem wordClasses, DepWord word) {
		StringBuffer buffer = new StringBuffer();
		List<DepFeature> features;
		features = word.getFeatures();
		features.removeAll(wordClasses.getFeatures());
		features.sort((a, b) -> a.compareTo(b));
		for (DepFeature feature : features) {
			DepSystem system = description.getSystem(feature);
			if (system == null) continue;
			if (system.getName().endsWith("RANK")) continue;
			if (system.getName().endsWith("COMPLEXITY")) continue;
			if (buffer.length() != 0) {
				buffer.append("|");
			}
			buffer.append(format("%s=%s", system.getName(), feature.getName()));
		}
		if (buffer.length() == 0) {
			buffer.append("-");
		}
		return buffer.toString();
	}
	
	private String getMixedLabels(DepWord word) {
		StringBuffer buffer = new StringBuffer();
		if (word.isBackspaced()) {
			buffer.append("SpaceAfter=No");
		}
		if (buffer.length() == 0) {
			buffer.append("-");
		}
		return buffer.toString();
	}

	private String getWordClassName(DepSystem wordClasses, DepWord word) {
		String wordClassName = "-";
		List<DepFeature> features = word.getFeatures();
		features.retainAll(wordClasses.getFeatures());
		if (!features.isEmpty()) {
			wordClassName = features.get(0).getName();
		}
		return wordClassName;
	}

}
