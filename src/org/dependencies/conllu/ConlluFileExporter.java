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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepCorpus;
import org.dependencies.model.DepDependency;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepLanguage;
import org.dependencies.model.DepSystem;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepNode;
import org.dependencies.model.DepWording;

/**
 * A CONLLU file exporter.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluFileExporter {

	/**
	 * Exports the analyzed text into a file.
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
		DepSystem wordClasses = description.getSystem("WORD-CLASS");
		for (DepWording wording : text.getWordings()) {
			pw.println(format("# sent_id = %s-%s-s%d", descriptionName, analysisName, wording.getId()));
			pw.println(format("# text = %s", wording.getForm()));
			Map<Integer, DepNode> nodeMap = new HashMap<>();
			nodeMap.put(0, new DepNode(null));
			for (DepWord word : wording) {
				DepNode node = new DepNode(word);
				nodeMap.put(wording.orderOf(word), node);
			}
			Integer followedId = 1;
			for (DepWord word : wording) {
				DepNode node = nodeMap.get(wording.orderOf(word));
				List<DepDependency> dependencies = word.getDependencies();
				if (dependencies.size() == 0) {
					if (null != word.getFeature("lexical-verb")) {
						followedId = wording.orderOf(word);
						DepNode head = nodeMap.get(0);
						head.addTail(node);
						node.setHead(head);
						break;
					}
				}
			}
			for (DepWord word : wording) {
				DepNode node = nodeMap.get(wording.orderOf(word));
				if (node.getHead() != null) {
					continue;
				}
				List<DepDependency> dependencies = word.getDependencies();
				if (dependencies.size() == 0) {
					DepNode head = nodeMap.get(0);
					if (head.getTails().size() == 0) {
						followedId = wording.orderOf(word);
						head.addTail(node);
						node.setHead(head);
					} else {
						DepDependency dependency = new DepDependency();
						DepFunction function = new DepFunction();
						function.setName("Follower");
						dependency.setFunction(function);
						dependency.setHeadOrder(followedId);
						word.addDependency(dependency);
						head = nodeMap.get(dependency.getHeadOrder());
						head.addTail(node);
						node.setHead(head);
					}
				} else {
					DepDependency dependency = dependencies.get(0);
					DepNode head = nodeMap.get(dependency.getHeadOrder());
					head.addTail(node);
					node.setHead(head);
				}
			}
			if (nodeMap.get(0).getTails().size() == 0) {
				nodeMap.get(1).asWord().clearDependencies();
			}
			for (DepWord word : wording) {
				pw.print(wording.orderOf(word));
				pw.print("\t");
				pw.print(word.getForm());
				pw.print("\t");
				pw.print(word.getLemma());
				pw.print("\t");
				pw.print(getWordClassName(wordClasses, word));
				pw.print("\t");
				pw.print("-");
				pw.print("\t");
				pw.print("-");
				pw.print("\t");
				pw.print(getHeadOrder(word));
				pw.print("\t");
				pw.print(getFunction(word));
				pw.print("\t");
				pw.print("-");
				pw.print("\t");
				pw.print(getWordFeatureNames(description, wordClasses, word));
				pw.print("\n");
			}
			pw.print("\n");
		}
		pw.close();
	}

	private String getFunction(DepWord word) {
		String function = "root";
		if (word.getDependencies().size() >= 1) {
			DepDependency dependency = word.getDependencies().get(0);
			function = dependency.getFunction().getName();
		}
		return function;
	}

	private String getHeadOrder(DepWord word) {
		String headOrder = "0";
		if (word.getDependencies().size() >= 1) {
			DepDependency dependency = word.getDependencies().get(0);
			headOrder = Integer.toString(dependency.getHeadOrder());
		}
		return headOrder;
	}

	private String getWordFeatureNames(DepDescription description, DepSystem wordClasses, DepWord word) {
		StringBuffer featureBuffer = new StringBuffer();
		if (word.getDependencies().size() > 1) {
			featureBuffer.append("MultiDep=Yes");
		}
		if (word.isBackspaced()) {
			featureBuffer.append("SpaceAfter=No");
		}
		List<DepFeature> features;
		features = word.getFeatures();
		features.removeAll(wordClasses.getFeatures());
		features.sort((a, b) -> a.compareTo(b));
		for (DepFeature feature : features) {
			if (featureBuffer.length() != 0) {
				featureBuffer.append("|");
			}
			DepSystem system = description.getSystem(feature);
			if (system == null) continue;
			featureBuffer.append(format("%s=%s", system.getName(), feature.getName()));
		}
		if (featureBuffer.length() == 0) {
			featureBuffer.append("-");
		}
		String featureSetString = featureBuffer.toString();
		return featureSetString;
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
