package org.dependencies.model;

/**
 * A function that a word can have.
 * 
 * @author Daniel Couto-Vale
 */
public class DepFunction {
	
	/**
	 * The function id.
	 */
	private Integer id;
	
	/**
	 * The function name.
	 */
	private String name;

	/**
	 * Gets the id of this function.
	 * 
	 * @return the function id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this function.
	 * 
	 * @param id the function id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this function.
	 * 
	 * @return the function name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this function.
	 * 
	 * @param name the function name
	 */
	public final void setName(String name) {
		this.name = name;
	}

}
