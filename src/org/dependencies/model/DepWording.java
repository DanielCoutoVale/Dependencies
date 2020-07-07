package org.dependencies.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A wording as the largest grammatical unit within a text.
 * 
 * @author Daniel Couto-Vale
 */
public class DepWording implements Iterable<DepWord> {

	/**
	 * The wording id.
	 */
	private Integer id;

	/**
	 * The wording form.
	 */
	private String form;

	/**
	 * The words
	 */
	private final List<DepWord> words;

	/**
	 * Constructor
	 */
	public DepWording() {
		this.words = new LinkedList<>();
	}

	/**
	 * Gets the id of this wording.
	 * 
	 * @return the wording id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this wording.
	 * 
	 * @param id the wording id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the form of this wording.
	 * 
	 * @return the wording form
	 */
	public final String getForm() {
		return this.form;
	}

	/**
	 * Sets the form of this wording.
	 * 
	 * @param form the wording form
	 */
	public final void setForm(String form) {
		this.form = form;
	}

	/**
	 * Gets the words in this wording.
	 * 
	 * @return the words
	 */
	public final List<DepWord> getWords() {
		return new LinkedList<>(this.words);
	}

	/**
	 * Appends a word to the end of this wording.
	 * 
	 * @param word the word
	 */
	public final void addWord(DepWord word) {
		this.words.add(word);
	}

	/**
	 * Insert a word at the specified index in this wording.
	 * 
	 * @param index the index for the word
	 * @param word  the word
	 */
	public final void addWord(int index, DepWord word) {
		this.words.add(index, word);
	}

	@Override
	public final Iterator<DepWord> iterator() {
		return this.words.iterator();
	}

	@Override
	public final String toString() {
		return "Wording #" + this.id + " '" + this.form + "' ";
	}

}
