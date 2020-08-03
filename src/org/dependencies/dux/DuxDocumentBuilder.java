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
	 * @param file2 
	 * @return the DUX document
	 */
	public final DuxDocument parse(File home, String packName) {
		DuxCommandIteratorBuilder builder = new DuxCommandIteratorBuilder(home);
		return new DuxDocument(builder, packName);
	}

}
