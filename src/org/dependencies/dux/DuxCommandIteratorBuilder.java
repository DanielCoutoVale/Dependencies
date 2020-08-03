package org.dependencies.dux;

import java.io.File;

/**
 * A DUX command iterator builder
 * 
 * @author Daniel Couto-Vale
 */
public class DuxCommandIteratorBuilder {
	
	/**
	 * The home
	 */
	private File home;

	/**
	 * Constructor
	 * 
	 * @param home the home
	 * @param file the DUX file
	 */
	public DuxCommandIteratorBuilder(File home) {
		this.home = home;
	}

	/**
	 * Builds a DUX command iterator.
	 * 
	 * @param moduleName the module name
	 * @return the DUX command iterator
	 */
	public final DuxCommandIterator build(String moduleName) {
		File file = new File(home, moduleName.replace('.', '/') + ".dux");
		return new DuxCommandIterator(this, file);
	}

}
