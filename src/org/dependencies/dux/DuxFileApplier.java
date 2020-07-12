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
			List<List<DepWord>> wordMatrix = base.searchForWords(factory, command.getMatches());
			for (List<DepWord> words : wordMatrix) {
				// FIXME Consider other words
				DepWord word = words.get(0);
				for (DuxChange change : command.getMagisTags()) {
					if (change instanceof DuxFeature) {
						DuxFeature tag = (DuxFeature) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepFeature feature = descriptionMap
								.get(tag.getPrefix())
								.getSystem(tag.getSystemName())
								.getFeature(tag.getFeatureName());
						if (analysis == null) {
							System.err.println("Error: Analysis not found for +" + tag);
							continue;
						}
						if (feature == null) {
							System.err.println("Error: Feature not found for +" + tag);
							continue;
						}
						base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepDescription description = descriptionMap.get(tag.getPrefix());
						DepFunction function = descriptionMap
								.get(tag.getPrefix())
								.getMetafunction(tag.getMetafunctionName())
								.getFunction(tag.getName());
						DepWord tail = words.get(tag.getWordIndex() - 1);
						DepFeature tailRank = description.getRank(tag.getWordRankName());
						DepWord head = words.get(tag.getHeadIndex() - 1);
						DepFeature headRank = description.getRank(tag.getHeadRankName());
						base.addWordFunction(analysis.getId(), function.getId(), tail.getId(), tailRank.getId(), head.getId(), headRank.getId());
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
						if (analysis == null) {
							System.err.println("Error: Analysis not found for -" + tag);
							continue;
						}
						if (feature == null) {
							System.err.println("Error: Feature not found for -" + tag);
							continue;
						}
						base.removeWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						DepDescription description = descriptionMap.get(tag.getPrefix());
						DepFunction function = descriptionMap
								.get(tag.getPrefix())
								.getMetafunction(tag.getMetafunctionName())
								.getFunction(tag.getName());
						DepWord tail = words.get(tag.getWordIndex() - 1);
						DepFeature tailRank = description.getRank(tag.getWordRankName());
						DepWord head = words.get(tag.getHeadIndex() - 1);
						DepFeature headRank = description.getRank(tag.getHeadRankName());
						base.removeWordFunction(analysis.getId(), function.getId(), tail.getId(), tailRank.getId(), head.getId(), headRank.getId());
					}
				}
			}
		}
		base.close();
	}
	
}
