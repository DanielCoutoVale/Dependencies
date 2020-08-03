package org.dependencies;

import java.sql.SQLException;

import org.dependencies.dux.DuxFileApplier;

/**
 * Translates an analysis.
 * 
 * @author Daniel Couto-Vale
 */
public class TranslateAnalysis {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public final static void main(String[] args) {
		if (args.length != 6) {
			System.err.println("USAGE:");
			System.err.println("java -jar translate-analysis.jar [source-description] [source-analysis] [target-description] [target-analysis] [base] [module]");
			System.exit(-1);
		}
		String sourceDescriptionName = args[0];
		String sourceAnalysisName = args[1];
		String targetDescriptionName = args[2];
		String targetAnalysisName = args[3];
		String baseName = args[4];
		String fileName = args[5];
		DuxFileApplier applier = new DuxFileApplier();
		try {
			applier.applyFile(sourceDescriptionName, sourceAnalysisName, targetDescriptionName, targetAnalysisName,
					baseName, fileName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}

}
