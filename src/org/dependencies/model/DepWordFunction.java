package org.dependencies.model;

public class DepWordFunction {

	/**
	 * The analysis name.
	 */
	private Integer analysisId;

	/**
	 * The metafunction name.
	 */
	private Integer metafunctionId;

	/**
	 * The function name.
	 */
	private String name;
	
	/**
	 * The word rank name.
	 */
	private Integer wordRankId;
	
	/**
	 * The head rank name.
	 */
	private Integer headRankId;

	private Integer descriptionId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the analysisId
	 */
	public Integer getAnalysisId() {
		return analysisId;
	}

	/**
	 * @param analysisId the analysisId to set
	 */
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}

	/**
	 * @return the metafunctionId
	 */
	public Integer getMetafunctionId() {
		return metafunctionId;
	}

	/**
	 * @param metafunctionId the metafunctionId to set
	 */
	public void setMetafunctionId(Integer metafunctionId) {
		this.metafunctionId = metafunctionId;
	}

	/**
	 * @return the wordRankId
	 */
	public Integer getWordRankId() {
		return wordRankId;
	}

	/**
	 * @param wordRankId the wordRankId to set
	 */
	public void setWordRankId(Integer wordRankId) {
		this.wordRankId = wordRankId;
	}

	/**
	 * @return the headRankId
	 */
	public Integer getHeadRankId() {
		return headRankId;
	}

	/**
	 * @param headRankId the headRankId to set
	 */
	public void setHeadRankId(Integer headRankId) {
		this.headRankId = headRankId;
	}

	public Integer getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}
	
}
