package org.dependencies.dux;

import java.io.File;

/**
 * A DUX document builder.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxDocumentBuilder {

	/**
	 * Parse a DUX file as a DUX document and return a DUX document object.
	 * 
	 * @param file the DUX file
	 * @return the DUX document
	 */
	public final DuxDocument parse(File file) {
		DuxCommandIteratorBuilder builder = new DuxCommandIteratorBuilder(file);
		return new DuxDocument(builder);
	}

}
