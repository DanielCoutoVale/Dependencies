package org.dependencies.model;

import static java.lang.String.format;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.dux.DuxFactory;
import org.dependencies.dux.DuxPattern;
import org.dependencies.dux.DuxQueryParser;

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

	public DepSearchResult searchForWordings(String descriptionName, String analysisName, String corpusName,
			String languageName, String textTitle, String query) throws SQLException {
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
		
		Map<String, DepDescription> descriptionMap = new HashMap<>();
		Map<String, DepAnalysis> analysisMap = new HashMap<>();
		descriptionMap.put("H", description);
		analysisMap.put("H", analysis);
		DuxFactory factory = new DuxFactory(descriptionMap, analysisMap);
		DuxQueryParser parser = new DuxQueryParser();
		List<DuxPattern> matches = parser.parseQuery(query);
		List<DepWording> wordings = base.searchForWordings(corpus.getId(), language.getId(), textTitle, analysis.getId(), factory, matches);
		if (wordings == null) {
			System.err.println(format("The text %s does not exist in the corpus %s for the language %s!", textTitle, corpusName, languageName));
			System.exit(-1);
		}
		DepSearchResult result = new DepSearchResult();
		result.setWordings(wordings);
		result.setDescription(description);
		return result;
	}
}
