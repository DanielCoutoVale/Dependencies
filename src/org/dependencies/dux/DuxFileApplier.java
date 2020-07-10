package org.dependencies.dux;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepWordFeature;

/**
 * A DUX file applier.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxFileApplier {

	public final void applyFile(String sdName, String saName, String tdName, String taName, String fileName) throws SQLException {
		MysqlDependencyBase base = new MysqlDependencyBase();
		Map<String, DepDescription> descriptionMap = new HashMap<>();
		descriptionMap.put("S", base.getDescription(sdName));
		descriptionMap.put("T", base.getDescription(tdName));
		Map<String, DepAnalysis> analysisMap = new HashMap<>();
		analysisMap.put("S", base.getAnalysis(descriptionMap.get("S").getId(), saName));
		analysisMap.put("T", base.getAnalysis(descriptionMap.get("T").getId(), taName));
		if (analysisMap.get("T") == null) {
			analysisMap.put("T", base.addAnalysis(descriptionMap.get("T").getId(), taName));
		}
		File file = new File(fileName);
		DuxDocumentBuilder builder = new DuxDocumentBuilder();
		DuxDocument document = builder.parse(file);
		DuxFactory factory = new DuxFactory(descriptionMap, analysisMap);
		for (DuxCommand command : document) {
			// FIXME Consider other words besides the first
			DuxWord duxWord = (DuxWord)(command.getMatches().get(0));
			List<DepWordFeature> wordFeatures = factory.makeWordFeatures(duxWord);
			List<DepWord> words = base.searchForWords(wordFeatures);
			for (DepWord word : words) {
				for (DuxChange change : command.getMagisTags()) {
					if (change instanceof DuxFeature) {
						DuxFeature tag = (DuxFeature) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepFeature feature = descriptionMap
								.get(tag.getPrefix())
								.getSystem(tag.getSystemName())
								.getFeature(tag.getFeatureName());
						base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepFunction function = descriptionMap
								.get(tag.getPrefix())
								.getMetafunction(tag.getMetafunctionName())
								.getFunction(tag.getName());
						// TODO Add a function to a word
						System.out.println("+" + function);
					}
				}
				for (DuxChange change : command.getMinusTags()) {
					if (change instanceof DuxFeature) {
						DuxFeature tag = (DuxFeature) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepFeature feature = descriptionMap
								.get(tag.getPrefix())
								.getSystem(tag.getSystemName())
								.getFeature(tag.getFeatureName());
						base.removeWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepFunction function = descriptionMap
								.get(tag.getPrefix())
								.getMetafunction(tag.getMetafunctionName())
								.getFunction(tag.getName());
						System.out.println("-" + function);
						// TODO Add a function to a word
					}
				}
			}
		}
		base.close();
	}
	
}
