package org.dependencies.dux;

import static java.lang.String.format;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
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
		if (metafunction != null) {
			depFunction.setMetafunctionId(metafunction.getId());
		} else if (function.getMetafunctionName().equals("%")) { 
			depFunction.setMetafunctionId(null);
		} else {
			System.err.println(format("Metafunction '%s' not found in description '%s'!", function.getMetafunctionName(), description.getName()));
			System.exit(-1);
		}
		depFunction.setName(function.getName());
		DepFeature wordRank = description.getRank(function.getWordRankName());
		if (wordRank != null) {
			depFunction.setWordRankId(wordRank.getId());
		} else if (function.getWordRankName().equals("%")) {
			depFunction.setWordRankId(null);
		} else {
			System.err.println(format("Word rank '%s' not found in description '%s'!", function.getWordRankName(), description.getName()));
			System.exit(-1);
		}
		DepFeature headRank = description.getRank(function.getHeadRankName());
		if (headRank != null) {
			depFunction.setHeadRankId(headRank.getId());
		} else if (function.getHeadRankName().equals("%")) {
			depFunction.setHeadRankId(null);
		} else {
			System.err.println(format("Head rank '%s' not found in description '%s'!", function.getHeadRankName(), description.getName()));
			System.exit(-1);
		}
		depFunction.setDescriptionId(description.getId());
		return depFunction;
	}
}

