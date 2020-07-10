package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepDescription;
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
			feature.setDescriptionName(descriptionMap.get(matchTag.getPrefix()).getName());
			feature.setAnalysisName(analysisMap.get(matchTag.getPrefix()).getName());
			feature.setSystemName(matchTag.getSystemName());
			feature.setName(matchTag.getFeatureName());
			features.add(feature);
		}
		return features;
	}

	public DepWordFunction makeWordFunction(DuxFunction function) {
		DepWordFunction depFunction = new DepWordFunction();
		depFunction.setDescriptionName(descriptionMap.get(function.getPrefix()).getName());
		depFunction.setAnalysisName(analysisMap.get(function.getPrefix()).getName());
		depFunction.setMetafunctionName(function.getMetafunctionName());
		depFunction.setName(function.getName());
		depFunction.setWordRankName(function.getWordRankName());
		depFunction.setHeadRankName(function.getHeadRankName());
		return depFunction;
	}
}

