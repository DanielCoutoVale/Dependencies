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
	 * The function of the table.
	 */
	private DepFunction function;

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

}
