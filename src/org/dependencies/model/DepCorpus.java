package org.dependencies.model;

/**
 * A corpus of texts.
 * 
 * @author Daniel Couto-Vale
 */
public class DepCorpus {

	/**
	 * The corpus id.
	 */
	private Integer id;

	/**
	 * The corpus name.
	 */
	private String name;

	/**
	 * Gets the id of this corpus.
	 * 
	 * @return the corpus id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this corpus.
	 * 
	 * @param id the corpus id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this corpus.
	 * 
	 * @return the corpus name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this corpus.
	 * 
	 * @param name the corpus name
	 */
	public final void setName(String name) {
		this.name = name;
	}

}
