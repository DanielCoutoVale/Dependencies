package org.dependencies;

import java.sql.SQLException;

import org.dependencies.model.DepAnalysisChecker;

import static java.lang.System.*;

/**
 * Checks whether an analysis is complete.
 * 
 * @author Daniel Couto-Vale
 */
public class CheckAnalysis {

	/**
	 * Main
	 * 
	 * @param args the script arguments
	 */
	public static final void main(String[] args) {
		if (args.length != 5) {
			err.println("check-analysis [description] [analysis] [corpus] [language] [text]");
			exit(-1);
		}
		String descriptionName = args[0];
		String analysisName = args[1];
		String corpusName = args[2];
		String languageName = args[3];
		String textTitle = args[4];
		DepAnalysisChecker checker = new DepAnalysisChecker();
		try {
			checker.checkAnalysis(descriptionName, analysisName, corpusName, languageName, textTitle);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("There is a bug in this script.");
		}
	}
	
}
