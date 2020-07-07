package org.dependencies.model;

import static java.lang.System.out;

import java.sql.SQLException;
import java.util.List;

import org.dependencies.base.MysqlDependencyBase;

/**
 * An analysis checker.
 * 
 * @author Daniel Couto-Vale
 */
public class DepAnalysisChecker {

	public final void checkAnalysis(String descriptionName, String analysisName, String corpusName, String languageName,
			String textTitle) throws SQLException {
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepDescription description = base.getDescription(descriptionName);
		DepAnalysis analysis = base.getAnalysis(description.getId(), analysisName);
		DepCorpus corpus = base.getCorpus(corpusName);
		DepLanguage language = base.getLanguage(languageName);
		DepAnalyzedText text = base.getAnalyzedText(corpus.getId(), language.getId(), textTitle, analysis.getId());
		for (DepWording wording : text.getWordings()) {
			for (DepWord word : wording.getWords()) {
				for (DepFeature wordClass : description.getSystem("WORD-CLASS")) {
					if (null != word.getFeature(wordClass.getName())) {
						List<DepSystem> systems = description.getSystems();
						for (DepSystem system : systems) {
							if (system.isEntered(systems, word.getFeatures())) {
								if (!system.isExited(word.getFeatures())) {
									out.println("- " + word);
									out.println("Missing " + system.getName());
								}
							}
						}
					}
				}
			}
		}
		
	}

}
