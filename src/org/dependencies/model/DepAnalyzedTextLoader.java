package org.dependencies.model;

import static java.lang.String.format;

import java.sql.SQLException;

import org.dependencies.base.MysqlDependencyBase;

/**
 * A loader of analyzed texts 
 * 
 * @author Daniel Couto-Vale
 */
public class DepAnalyzedTextLoader {

	public final DepAnalyzedText loadAnalyzedText(String descriptionName, String analysisName, String corpusName, String languageName, String textTitle) throws SQLException {
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepDescription description = base.getDescription(descriptionName);
		if (description == null) {
			System.err.println(format("The description %s does not exist!", descriptionName));
			System.exit(-1);
		}
		DepAnalysis analysis = base.getAnalysis(description.getId(), analysisName);
		if (analysis == null) {
			System.err.println(format("The analysis %s does not exist in the description %s!", analysisName, descriptionName));
			System.exit(-1);
		}
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
		DepAnalyzedText text = base.getAnalyzedText(corpus.getId(), language.getId(), textTitle, analysis.getId());
		if (text == null) {
			System.err.println(format("The text %s does not exist in the corpus %s for the language %s!", textTitle, corpusName, languageName));
			System.exit(-1);
		}
		text.setDescription(description);
		return text;
	}
}
