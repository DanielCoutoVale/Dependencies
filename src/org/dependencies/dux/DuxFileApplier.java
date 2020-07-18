package org.dependencies.dux;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;
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
		if (descriptionMap.get("S") == null) {
			System.err.println("Source description not found: " + sdName);
			System.exit(-1);
		}
		if (descriptionMap.get("T") == null) {
			System.err.println("Target description not found: " + tdName);
			System.exit(-1);
		}
		Map<String, DepAnalysis> analysisMap = new HashMap<>();
		analysisMap.put("S", base.getAnalysis(descriptionMap.get("S").getId(), saName));
		analysisMap.put("T", base.getAnalysis(descriptionMap.get("T").getId(), taName));
		if (analysisMap.get("S") == null) {
			System.err.println("Source analysis not found: " + saName);
			System.exit(-1);
		}
		if (analysisMap.get("T") == null) {
			analysisMap.put("T", base.addAnalysis(descriptionMap.get("T").getId(), taName));
		}
		File file = new File(fileName);
		DuxDocumentBuilder builder = new DuxDocumentBuilder();
		DuxDocument document = builder.parse(file);
		DuxFactory factory = new DuxFactory(descriptionMap, analysisMap);
		Scanner scanner = new Scanner(System.in);
		for (DuxCommand command : document) {
			if (command instanceof DuxStop) {
				System.out.println("Press ENTER to continue. Press Ctrl+D to stop.");
                try {
                	scanner.nextLine();
    				continue;
                } catch(IllegalStateException | NoSuchElementException e) {
                	scanner.close();
                    break;
                }
			}
			DuxTranslate transate = (DuxTranslate) command;
			List<List<DepWord>> wordMatrix = base.searchForWords(factory, transate.getMatches());
			for (List<DepWord> words : wordMatrix) {
				// FIXME Consider other words
				DepWord word = words.get(0);
				for (DuxChange change : transate.getMagisTags()) {
					if (change instanceof DuxFeature) {
						DuxFeature tag = (DuxFeature) change;
						DepDescription description = descriptionMap.get(tag.getPrefix());
						if (description == null) {
							System.err.println("Error: Description not found for +" + tag);
							continue;
						}
						DepSystem system = description
								.getSystem(tag.getSystemName());
						if (system == null) {
							System.err.println("Error: System not found for +" + tag);
							continue;
						}
						DepFeature feature = system
								.getFeature(tag.getFeatureName());
						if (feature == null) {
							System.err.println("Error: Feature not found for +" + tag);
							continue;
						}
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						if (analysis == null) {
							System.err.println("Error: Analysis not found for +" + tag);
							continue;
						}
						base.addWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepDescription description = descriptionMap.get(tag.getPrefix());
						if (description == null) {
							System.err.println("Error: Description not found for +" + tag);
							continue;
						}
						DepMetafunction metafunction = description
								.getMetafunction(tag.getMetafunctionName());
						if (metafunction == null) {
							System.err.println("Error: Metafunction not found for " + tag);
							continue;
						}
						DepFunction function = metafunction
								.getFunction(tag.getName());
						if (function == null) {
							System.err.println("Error: Function not found for " + tag);
							continue;
						}
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						if (analysis == null) {
							System.err.println("Error: Analysis not found for +" + tag);
							continue;
						}
						DepWord tail = words.get(tag.getWordIndex() - 1);
						DepFeature tailRank = description.getRank(tag.getWordRankName());
						DepWord head = words.get(tag.getHeadIndex() - 1);
						DepFeature headRank = description.getRank(tag.getHeadRankName());
						base.addWordFunction(analysis.getId(), function.getId(), tail.getId(), tailRank.getId(), head.getId(), headRank.getId());
					}
				}
				for (DuxChange change : transate.getMinusTags()) {
					if (change instanceof DuxFeature) {
						DuxFeature tag = (DuxFeature) change;
						DepDescription description = descriptionMap.get(tag.getPrefix());
						if (description == null) {
							System.err.println("Error: Description not found for +" + tag);
							continue;
						}
						DepSystem system = description
								.getSystem(tag.getSystemName());
						if (system == null) {
							System.err.println("Error: System not found for +" + tag);
							continue;
						}
						DepFeature feature = system
								.getFeature(tag.getFeatureName());
						if (feature == null) {
							System.err.println("Error: Feature not found for +" + tag);
							continue;
						}
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						if (analysis == null) {
							System.err.println("Error: Analysis not found for +" + tag);
							continue;
						}
						base.removeWordFeature(analysis.getId(), feature.getId(), word.getId());
					}
					if (change instanceof DuxFunction) {
						DuxFunction tag = (DuxFunction) change;
						DepDescription description = descriptionMap.get(tag.getPrefix());
						if (description == null) {
							System.err.println("Error: Description not found for +" + tag);
							continue;
						}
						DepMetafunction metafunction = description
								.getMetafunction(tag.getMetafunctionName());
						if (metafunction == null) {
							System.err.println("Error: Metafunction not found for " + tag);
							continue;
						}
						DepFunction function = metafunction
								.getFunction(tag.getName());
						if (function == null) {
							System.err.println("Error: Function not found for " + tag);
							continue;
						}
						DepAnalysis analysis = analysisMap.get(tag.getPrefix());
						if (analysis == null) {
							System.err.println("Error: Analysis not found for +" + tag);
							continue;
						}
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
