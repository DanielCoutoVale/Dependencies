package org.dependencies.model;

/**
 * A dependency.
 * 
 * @author Daniel Couto-Vale
 */
public class DepDependency {

	/**
	 * The head order in the wording.
	 */
	private Integer headOrder;

	/**
	 * The rank at which the head word is head.
	 */
	private DepFeature headRank;
	
	/**
	 * The function of the table.
	 */
	private DepFunction function;

	/**
	 * The rank at which the word depends on the head word.
	 */
	private DepFeature wordRank;

	/**
	 * Gets the head order.
	 * 
	 * @return the head order
	 */
	public final Integer getHeadOrder() {
		return this.headOrder;
	}

	/**
	 * Sets the head order.
	 * 
	 * @param headOrder the head order
	 */
	public final void setHeadOrder(Integer headOrder) {
		this.headOrder = headOrder;
	}

	/**
	 * Gets the function.
	 * 
	 * @return the function
	 */
	public final DepFunction getFunction() {
		return this.function;
	}

	/**
	 * Sets the function.
	 * 
	 * @param function the function
	 */
	public final void setFunction(DepFunction function) {
		this.function = function;
	}

	/**
	 * Gets the rank at which the head word is head.
	 * 
	 * @return the head rank
	 */
	public final DepFeature getHeadRank() {
		return headRank;
	}

	/**
	 * Sets the rank at which the head word is head.
	 * 
	 * @param headRank the head rank to set
	 */
	public final void setHeadRank(DepFeature headRank) {
		this.headRank = headRank;
	}

	/**
	 * Gets the rank at which the word depends on the head word.
	 * 
	 * @return the wordRank the word rank
	 */
	public DepFeature getWordRank() {
		return wordRank;
	}

	/**
	 * Sets the rank at which the word depends on the head word.
	 * 
	 * @param wordRank the word rank
	 */
	public void setWordRank(DepFeature wordRank) {
		this.wordRank = wordRank;
	}

}
