package org.dependencies.conllu;

import java.util.LinkedList;
import java.util.List;

/**
 * The CONLLU sentence.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluSentence {

	/**
	 * The sentence words.
	 */
	public List<ConlluWord> words = new LinkedList<>();

	/**
	 * Gets the sentence form.
	 * 
	 * @return the sentence form
	 */
	public String getForm() {
		StringBuffer buffer = new StringBuffer();
		for (ConlluWord word : words) {
			buffer.append(word.form);
			if (word.backspaced) {
				buffer.append(" ");
			}
		}
		return buffer.toString();
	}

}
