package org.dependencies.model;

/**
 * A titled text that can be analyzed multiple times for multiple purposes.
 * 
 * @author Daniel Couto-Vale
 */
public class DepText {

	/**
	 * The text id.
	 */
	private Integer id;

	/**
	 * The text title.
	 */
	private String title;

	/**
	 * Gets the id of this text.
	 * 
	 * @return the text id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this text.
	 * 
	 * @param id the text id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the title of this text.
	 * 
	 * @return the text title
	 */
	public final String getTitle() {
		return this.title;
	}

	/**
	 * Sets the title of this text.
	 * 
	 * @param title the text title
	 */
	public final void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public final String toString() {
		return "Text #" + this.id + " #" + this.title;
	}

}
