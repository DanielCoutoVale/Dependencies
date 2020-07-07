package org.dependencies.conllu;

import java.io.File;


/**
 * A CONLLU sentence iterator builder
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluSentenceIteratorBuilder {

	/**
	 * The CONLLU file.
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param file the CONLLU file
	 */
	public ConlluSentenceIteratorBuilder(File file) {
		this.file = file;

	}

	/**
	 * Builds a CONLLU sentence iterator.
	 * 
	 * @return the CONLLU sentence iterator
	 */
	public final ConlluSentenceIterator build() {
		return new ConlluSentenceIterator(file);
	}

}
