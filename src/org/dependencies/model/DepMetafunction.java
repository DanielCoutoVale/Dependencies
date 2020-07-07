package org.dependencies.model;

/**
 * A metafunction.
 * 
 * @author Daniel Couto-Vale
 */
public class DepMetafunction {
	
	/**
	 * The metafunction id.
	 */
	private Integer id;
	
	/**
	 * The metafunction name.
	 */
	private String name;

	/**
	 * Gets the id of this metafunction.
	 * 
	 * @return the metafunction id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this metafunction.
	 * 
	 * @param id the metafunction id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this metafunction.
	 * 
	 * @return the metafunction name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this metafunction.
	 * 
	 * @param name the metafunction name
	 */
	public final void setName(String name) {
		this.name = name;
	}

}
