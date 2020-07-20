package org.dependencies.model;

/**
 * A feature as a word attribute when searching for words.
 * 
 * @author Daniel Couto-Vale
 */
public class DepWordFeature {

	/**
	 * The description id.
	 */
	private Integer descriptionId;

	/**
	 * The analysis id.
	 */
	private Integer analysisId;

	/**
	 * The system name.
	 */
	private String systemName;

	/**
	 * The feature name.
	 */
	private String name;

	/**
	 * Gets the id of the description to which this feature belongs.
	 * 
	 * @return the description id
	 */
	public final Integer getDescriptionId() {
		return this.descriptionId;
	}

	/**
	 * Sets the id of the description to which this feature belongs.
	 * 
	 * @param descriptionId the description id
	 */
	public final void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}

	/**
	 * Gets the id of the analysis in which this feature was ascribed to a word.
	 * 
	 * @return the analysis id
	 */
	public final Integer getAnalysisId() {
		return this.analysisId;
	}

	/**
	 * Sets the id of the analysis in which this feature was ascribed to a word.
	 * 
	 * @param analysisId the analysis id
	 */
	public final void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
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
