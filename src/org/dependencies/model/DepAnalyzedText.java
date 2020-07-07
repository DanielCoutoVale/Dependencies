package org.dependencies.model;

import java.util.LinkedList;
import java.util.List;

/**
 * An analyzed text, i.e. a text broken down into wordings, which are analyzed
 * for grammatical features and functions.
 * 
 * @author Daniel Couto-Vale
 */
public class DepAnalyzedText extends DepText {

	/**
	 * The wordings in a text.
	 */
	private final List<DepWording> wordings;

	/**
	 * Constructor
	 */
	public DepAnalyzedText() {
		this.wordings = new LinkedList<>();
	}

	/**
	 * Constructor
	 * 
	 * @param title the text title
	 */
	public DepAnalyzedText(DepText title) {
		this();
		this.setId(title.getId());
		this.setTitle(title.getTitle());
	}

	/**
	 * Gets the wordings in this text as a list.
	 * 
	 * @return the wordings
	 */
	public final List<DepWording> getWordings() {
		return this.wordings;
	}

	/**
	 * Appends a wording at the end of this text.
	 * 
	 * @param wording the new wording
	 */
	public final void addWording(DepWording wording) {
		this.wordings.add(wording);
	}

	/**
	 * Inserts the specified wording at the specified index in this text.
	 * 
	 * @param index   the index of the wording
	 * @param wording the wording
	 */
	public final void addWording(int index, DepWording wording) {
		this.wordings.add(index, wording);
	}

}
