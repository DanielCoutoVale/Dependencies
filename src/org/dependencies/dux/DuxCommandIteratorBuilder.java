package org.dependencies.dux;

import java.io.File;

/**
 * A DUX command iterator builder
 * 
 * @author Daniel Couto-Vale
 */
public class DuxCommandIteratorBuilder {

	/**
	 * The DUX file.
	 */
	private File file;

	/**
	 * Constructor
	 * 
	 * @param file the DUX file
	 */
	public DuxCommandIteratorBuilder(File file) {
		this.file = file;

	}

	/**
	 * Builds a DUX command iterator.
	 * 
	 * @return the DUX commmand iterator
	 */
	public final DuxCommandIterator build() {
		return new DuxCommandIterator(file);
	}

}
