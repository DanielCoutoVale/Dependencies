package org.dependencies.model;

/**
 * The language in which a text was written.
 * 
 * @author Daniel Couto-Vale
 */
public class DepLanguage {

	/**
	 * The language id.
	 */
	private Integer id;

	/**
	 * The language name.
	 */
	private String name;

	/**
	 * Gets the id of this language.
	 * 
	 * @return the language id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this language.
	 * 
	 * @param id the language id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this language.
	 * 
	 * @return the language name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this language.
	 * 
	 * @param name the language name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public final String toString() {
		return "Language #" + this.id + " #" + this.name;
	}

}
