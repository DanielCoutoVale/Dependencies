package org.dependencies.model;

/**
 * A feature as a word attribute when searching for words.
 * 
 * @author Daniel Couto-Vale
 */
public class DepWordFeature {

	/**
	 * The description name.
	 */
	private String descriptionName;

	/**
	 * The analysis name.
	 */
	private String analysisName;

	/**
	 * The system name.
	 */
	private String systemName;

	/**
	 * The feature name.
	 */
	private String name;

	/**
	 * Gets the name of the description to which this feature belongs.
	 * 
	 * @return the description name
	 */
	public final String getDescriptionName() {
		return this.descriptionName;
	}

	/**
	 * Sets the name of the description to which this feature belongs.
	 * 
	 * @param descriptionName the description name
	 */
	public final void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}

	/**
	 * Gets the name of the analysis in which this feature was ascribed to a word.
	 * 
	 * @return the analysis name
	 */
	public final String getAnalysisName() {
		return this.analysisName;
	}

	/**
	 * Sets the name of the analysis in which this feature was ascribed to a word.
	 * 
	 * @param analysisName the analysis name
	 */
	public final void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	/**
	 * Gets the name of the system to which this feature belongs.
	 * 
	 * @return the system name
	 */
	public final String getSystemName() {
		return this.systemName;
	}

	/**
	 * Sets the name of the system to which this feature belongs.
	 * 
	 * @param systemName the system name
	 */
	public final void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * Gets the name of this feature.
	 * 
	 * @return the feature name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this feature.
	 * 
	 * @param name the feature name
	 */
	public final void setName(String name) {
		this.name = name;
	}

}
