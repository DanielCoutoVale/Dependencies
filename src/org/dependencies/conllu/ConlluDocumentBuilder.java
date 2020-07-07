package org.dependencies.conllu;

import java.io.File;

/**
 * A CONLLU document builder.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluDocumentBuilder {

	/**
	 * Parse a CONLLU file as a CONLLU document and return a CONLLU document object.
	 * 
	 * @param file the CONLLU file
	 * @return the CONLLU document
	 */
	public final ConlluDocument parse(File file) {
		ConlluSentenceIteratorBuilder builder = new ConlluSentenceIteratorBuilder(file);
		return new ConlluDocument(builder);
	}

}
