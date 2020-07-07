package org.dependencies.conllu;

import java.util.Iterator;

/**
 * A CONLLU document.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluDocument implements Iterable<ConlluSentence> {

	/**
	 * The iterator builder.
	 */
	private ConlluSentenceIteratorBuilder builder;

	/**
	 * Constructor
	 * 
	 * @param builder the iterator builder
	 */
	public ConlluDocument(ConlluSentenceIteratorBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Iterator<ConlluSentence> iterator() {
		return builder.build();
	}

}
