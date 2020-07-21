package org.dependencies.dux;

import static java.lang.String.format;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepWordFeature;
import org.dependencies.model.DepWordFunction;

public class DuxFactory {

	private Map<String, DepDescription> descriptionMap;

	private Map<String, DepAnalysis> analysisMap;

	public DuxFactory(Map<String, DepDescription> descriptionMap, Map<String, DepAnalysis> analysisMap) {
		this.descriptionMap = descriptionMap;
		this.analysisMap = analysisMap;
	}

	public final List<DepWordFeature> makeWordFeatures(DuxWord word) {
		List<DuxFeature> matchTags = word.getMatchTags();
		List<DepWordFeature> features = new LinkedList<>();
		for (int i = 0; i < matchTags.size(); i++) {
			DepWordFeature feature = new DepWordFeature();
			DuxFeature matchTag = matchTags.get(i);
			feature.setDescriptionId(descriptionMap.get(matchTag.getPrefix()).getId());
			feature.setAnalysisId(analysisMap.get(matchTag.getPrefix()).getId());
			feature.setSystemName(matchTag.getSystemName());
			feature.setName(matchTag.getFeatureName());
			features.add(feature);
		}
		return features;
	}

	public DepWordFunction makeWordFunction(DuxFunction function) {
		DepWordFunction depFunction = new DepWordFunction();
		DepDescription description = descriptionMap.get(function.getPrefix());
		depFunction.setAnalysisId(analysisMap.get(function.getPrefix()).getId());
		DepMetafunction metafunction = description.getMetafunction(function.getMetafunctionName());
		if (metafunction == null) {
			System.err.println(format("Metafunction '%s' not found in description '%s'!", function.getMetafunctionName(), description.getName()));
			System.exit(-1);
		}
		depFunction.setMetafunctionId(metafunction.getId());
		depFunction.setName(function.getName());
		depFunction.setWordRankId(description.getRank(function.getWordRankName()).getId());
		depFunction.setHeadRankId(description.getRank(function.getHeadRankName()).getId());
		return depFunction;
	}
}

