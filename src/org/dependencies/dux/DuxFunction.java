package org.dependencies.dux;

/**
 * A function
 * 
 * @author Daniel Couto-Vale
 */
public class DuxFunction implements DuxMatch, DuxChange {
	
	/**
	 * The function prefix
	 */
	private String prefix;

	/**
	 * The metafunction name for this function
	 */
	private String metafunctionName;

	/**
	 * The function name 
	 */
	private String name;

	/**
	 * The word index
	 */
	private Integer wordIndex;

	/**
	 * The word rank name
	 */
	private String wordRankName;

	/**
	 * The head word index
	 */
	private Integer headIndex;

	/**
	 * The rank name for the head word 
	 */
	private String headRankName;

	/**
	 * Constructor
	 * 
	 * @param form the feature form
	 */
	public DuxFunction(String form) {
		String[] A = form.split("#");
		String[] A0 = A[0].split(":");
		this.prefix = A0[0];
		this.metafunctionName = A0[1];
		this.name = A0[2];
		String[] A1 = A[1].split(":");
		this.wordIndex = Integer.parseInt(A1[0]);
		this.wordRankName = A1[1];
		String[] A2 = A[2].split(":");
		this.headIndex = Integer.parseInt(A2[0]);
		this.headRankName = A2[1];
	}

	/**
	 * Gets whether the token has the form of a function.
	 * 
	 * @param token the token
	 * @return <code>true</code> if the token has the form of a feature
	 */
	public final static boolean matches(String token) {
		String[] A = token.split("#");
		if (A.length != 3) return false;
		String[] A0 = A[0].split(":");
		if (A0.length != 3) return false;
		String[] A1 = A[1].split(":");
		if (A1.length != 2) return false;
		String[] A2 = A[2].split(":");
		if (A2.length != 2) return false;
		return true;
	}

	/**
	 * Gets the prefix of this function.
	 * 
	 * @return the function prefix
	 */
	public final String getPrefix() {
		return this.prefix;
	}

	/**
	 * Sets the prefix of this function.
	 * 
	 * @param prefix the function prefix
	 */
	public final void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the name of the metafunction of this function.
	 * 
	 * @return the metafunction name
	 */
	public final String getMetafunctionName() {
		return this.metafunctionName;
	}

	/**
	 * Sets the name of the metafunction of this function.
	 * 
	 * @param metafunctionName the metafunction name
	 */
	public final void setMetafunctionName(String metafunctionName) {
		this.metafunctionName = metafunctionName;
	}

	/**
	 * Gets the name of this function.
	 * 
	 * @return the function name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Sets the name of this function.
	 * 
	 * @param name the function name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the index of the word that has this function.
	 * 
	 * @return the word index
	 */
	public final Integer getWordIndex() {
		return wordIndex;
	}

	/**
	 * Sets the index of the word that has this function.
	 * 
	 * @param wordIndex the word index
	 */
	public final void setWordIndex(Integer wordIndex) {
		this.wordIndex = wordIndex;
	}

	/**
	 * Gets the rank of the word at which it has this function.
	 * 
	 * @return the word rank name
	 */
	public final String getWordRankName() {
		return wordRankName;
	}

	/**
	 * Sets the rank of the word at which it has this function
	 * 
	 * @param wordRankName the word rank name
	 */
	public final void setWordRankName(String wordRankName) {
		this.wordRankName = wordRankName;
	}

	/**
	 * Gets the index of the head word for this function.
	 * 
	 * @return the index of the head word
	 */
	public final Integer getHeadIndex() {
		return headIndex;
	}

	/**
	 * Sets the index of the head word for this function.
	 * 
	 * @param headIndex the index of the head word
	 */
	public final void setHeadIndex(Integer headIndex) {
		this.headIndex = headIndex;
	}

	/**
	 * Gets the rank at which the word is a head for this function. 
	 * 
	 * @return the rank name for the head word
	 */
	public final String getHeadRankName() {
		return headRankName;
	}

	/**
	 * Sets the rank at which the word is a head for this function.
	 * 
	 * @param headRankName the rank name for the head word
	 */
	public final void setHeadRankName(String headRankName) {
		this.headRankName = headRankName;
	}
	
	@Override
	public final String toString() {
		return this.prefix +  
				":" + this.metafunctionName + ":" + this.name +
				"#" + this.wordIndex + ":" + this.wordRankName +
				"#" + this.headIndex + ":" + this.headRankName;
	}

}
