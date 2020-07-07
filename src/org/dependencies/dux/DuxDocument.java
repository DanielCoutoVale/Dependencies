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
	 * Constructor
	 * 
	 * @param builder the iterator builder
	 */
	public DuxDocument(DuxCommandIteratorBuilder builder) {
		this.builder = builder;
	}

	@Override
	public Iterator<DuxCommand> iterator() {
		return this.builder.build();
	}

}
