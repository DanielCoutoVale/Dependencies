package org.dependencies.sys;

import java.util.LinkedList;
import java.util.List;

import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;

/**
 * A SYS document.
 * 
 * @author Daniel Couto-Vale
 */
public class SysDocument {

	/**
	 * The systems.
	 */
	private final List<DepSystem> systems;

	/**
	 * The metafunctions.
	 */
	private final List<DepMetafunction> metafunctions;

	/**
	 * Constructor
	 */
	public SysDocument() {
		this.systems = new LinkedList<>();
		this.metafunctions = new LinkedList<>();
	}

	/**
	 * Gets the systems in this SYS document.
	 * 
	 * @return the systems
	 */
	public final List<DepSystem> getSystems() {
		return new LinkedList<>(this.systems);
	}

	/**
	 * Adds a system to this SYS document.
	 * 
	 * @param system the system
	 */
	public void addSystem(DepSystem system) {
		this.systems.add(system);
	}

	/**
	 * Gets the metafunctions in this SYS document.
	 * 
	 * @return the metafunctions
	 */
	public final List<DepMetafunction> getMetafunctions() {
		return new LinkedList<>(this.metafunctions);
	}

	/**
	 * Adds a metafunction to this SYS document.
	 * 
	 * @param metafunction the metafunction
	 */
	public void addMetafunction(DepMetafunction metafunction) {
		this.metafunctions.add(metafunction);
	}

}
