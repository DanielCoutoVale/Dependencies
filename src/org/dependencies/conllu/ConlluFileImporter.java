package org.dependencies.conllu;

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
import org.dependencies.model.DepSystem;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepWording;

/**
 * A CONLLU file importer.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluFileImporter {

	public final void importFile(String corpusName, String languageName, String textTitle,
			String descriptionName, String analysisName, String fileName) throws SQLException {
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
		DepAnalysis analysis = base.addAnalysis(description.getId(), analysisName);
		if (language == null) {
			language = base.addLanguage(languageName);
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
				DepWord word = base.addWord(wording.getId(), orderInWording++, uWord.form, uWord.backspaced, uWord.lemma);
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
					DepFeature feature = system.getFeature(uFeature.name);
					base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					word.addFeature(feature);
				}
			}
			DepFeature rank = description.getSystem("RANK").getFeature("word");
			for (ConlluWord uWord : sentence.words) {
				if (uWord.headOrder == 0) {
					continue;
				}
				DepWord word = wording.getWords().get(uWord.order - 1);
				DepWord head = wording.getWords().get(uWord.headOrder - 1);
				DepFunction function = description.getFunction(uWord.functionName);
				base.addWordFunction(analysis.getId(), function.getId(), word.getId(), rank.getId(), head.getId(), rank.getId());
			}
		}
	}
	
}
