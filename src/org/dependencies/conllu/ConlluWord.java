package org.dependencies.conllu;

import java.util.LinkedList;
import java.util.List;

/**
 * A CONLLLU word.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluWord {

	/**
	 * The order of this word in the sentence.
	 */
	public Integer order;

	/**
	 * The word form.
	 */
	public String form;

	/**
	 * The word lemma.
	 */
	public String lemma;

	/**
	 * The word class.
	 */
	public String pos;

	/**
	 * The other features.
	 */
	public List<ConlluFeature> features = new LinkedList<>();

	/**
	 * The order of the head word in the sentence.
	 */
	public Integer headOrder;

	/**
	 * The name of the function of this word relative to the head word.
	 */
	public String functionName;

	/**
	 * Whether this word has a backspace.
	 */
	public boolean backspaced;

}