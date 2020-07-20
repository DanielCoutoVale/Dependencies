package org.dependencies.conllu;

import static java.lang.String.format;

import java.io.File;
import java.sql.SQLException;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepCorpus;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepLanguage;
import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepWording;

/**
 * A CONLLU file reader.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluFileReader {

	/**
	 * Imports the file as an analyzed text. The analysis is created if there are none.
	 * 
	 * @param corpusName the corpus name
	 * @param languageName the language name
	 * @param textTitle the text title
	 * @param descriptionName the description name
	 * @param analysisName the analysis name
	 * @param fileName the file name
	 * @throws SQLException if analyzed text cannot be stored in the dependency base
	 */
	public final void readFile(String corpusName, String languageName, String textTitle, String descriptionName,
			String analysisName, String fileName) throws SQLException {
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepCorpus corpus = base.getCorpus(corpusName);
		if (corpus == null) {
			corpus = base.addCorpus(corpusName);
		}
		DepLanguage language = base.getLanguage(languageName);
		if (language == null) {
			language = base.addLanguage(languageName);
		}
		DepDescription description = base.getDescription(descriptionName);
		DepAnalysis analysis = base.getAnalysis(description.getId(), analysisName);
		if (analysis == null) {
			analysis = base.addAnalysis(description.getId(), analysisName);
		}
		DepAnalyzedText text = new DepAnalyzedText(base.addText(corpus.getId(), language.getId(), textTitle));
		File file = new File(fileName);
		ConlluDocumentBuilder builder = new ConlluDocumentBuilder();
		ConlluDocument document = builder.parse(file);
		Integer orderInText = 1;
		for (ConlluSentence sentence : document) {
			String form = sentence.getForm();
			DepWording wording = base.addWording(text.getId(), orderInText++, form);
			text.addWording(wording);
			Integer orderInWording = 1;
			for (ConlluWord uWord : sentence.words) {
				DepWord word = base.addWord(wording.getId(), orderInWording++, uWord.form, uWord.backspaced,
						uWord.lemma);
				wording.addWord(word);
				// Word features
				{
					DepSystem system = description.getSystem("WORD-CLASS");
					DepFeature feature = system.getFeature(uWord.pos);
					base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					word.addFeature(feature);
				}
				for (ConlluFeature uFeature : uWord.features) {
					DepSystem system = description.getSystem(uFeature.systemName);
					if (system == null) {
						System.err.println(format("The system %s was not found!", uFeature.systemName));
						continue;
					}
					DepFeature feature = system.getFeature(uFeature.name);
					base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					word.addFeature(feature);
				}
			}
			for (ConlluWord uWord : sentence.words) {
				if (uWord.headOrder == 0) {
					continue;
				}
				DepWord word = wording.getWords().get(uWord.order - 1);
				DepWord head = wording.getWords().get(uWord.headOrder - 1);
				DepMetafunction mixed = description.getMetafunction("MIXED");
				if (mixed != null) {
					// Word dependency
					DepFunction function = mixed.getFunction(uWord.functionName);
					DepFeature rank = description.getRank("word");
					base.addWordFunction(analysis.getId(), function.getId(), word.getId(), rank.getId(), head.getId(),
							rank.getId());
				} else {
					// Ranked dependency
					DepMetafunction exp = description.getMetafunction("EXPERIENTIAL");
					DepMetafunction log = description.getMetafunction("LOGICAL");
					String[] tokens = uWord.functionName.split(":");
					DepFeature wordRank = description.getRank(tokens[0]);
					DepFeature headRank = description.getRank(tokens[tokens.length - 1]);
					for (int i = 1; i + 1 < tokens.length; i++) {
						DepFunction function = exp.getFunction(tokens[i]);
						function = function != null ? function : log.getFunction(tokens[i]);
						if (function != null) {
							base.addWordFunction(analysis.getId(), function.getId(), word.getId(), wordRank.getId(), head.getId(),
									headRank.getId());
						}
					}
				}
			}
		}
	}

}
