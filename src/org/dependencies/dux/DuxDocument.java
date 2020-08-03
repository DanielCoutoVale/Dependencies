package org.dependencies.dux;

import java.util.Iterator;

/**
 * The DUX document.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxDocument implements Iterable<DuxCommand> {

	/**
	 * The iterator builder
	 */
	private DuxCommandIteratorBuilder builder;
	
	/**
	 * The module name
	 */
	private String moduleName;

	/**
	 * Constructor
	 * 
	 * @param builder the iterator builder
	 * @param moduleName the module name
	 */
	public DuxDocument(DuxCommandIteratorBuilder builder, String moduleName) {
		this.builder = builder;
		this.moduleName = moduleName;
	}

	@Override
	public Iterator<DuxCommand> iterator() {
		return this.builder.build(moduleName);
	}

}
