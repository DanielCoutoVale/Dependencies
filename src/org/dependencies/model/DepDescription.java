package org.dependencies.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A description of a language or set of languages.
 * 
 * @author Daniel Couto-Vale
 */
public class DepDescription {

	/**
	 * The description id.
	 */
	private Integer id;

	/**
	 * The description name.
	 */
	private String name;

	/**
	 * The system map.
	 */
	private final Map<String, DepSystem> systemMap;

	/**
	 * The function map.
	 */
	private final Map<String, DepFunction> functionMap;

	/**
	 * Constructor
	 */
	public DepDescription() {
		this.systemMap = new HashMap<>();
		this.functionMap = new HashMap<>();
	}

	/**
	 * Gets the id of this description.
	 * 
	 * @return the description id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this description.
	 * 
	 * @param id the description id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this description.
	 * 
	 * @return the description name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this description.
	 * 
	 * @param name the description name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * Adds a system to this description.
	 * 
	 * @param system the system
	 */
	public final void addSystem(DepSystem system) {
		this.systemMap.put(system.getName(), system);
	}

	/**
	 * Gets a system in this description by name.
	 * 
	 * @param systemName the system name
	 * @return the system
	 */
	public final DepSystem getSystem(String systemName) {
		return this.systemMap.get(systemName);
	}

	/**
	 * Gets the systems in this description.
	 * 
	 * @return the systems
	 */
	public final List<DepSystem> getSystems() {
		return new LinkedList<>(this.systemMap.values());
	}

	/**
	 * Adds a function to this description.
	 * 
	 * @param function the function
	 */
	public final void addFunction(DepFunction function) {
		this.functionMap.put(function.getName(), function);
	}

	/**
	 * Gets a function in this description by name.
	 * 
	 * @param functionName the function name
	 * @return the function
	 */
	public final DepFunction getFunction(String functionName) {
		return this.functionMap.get(functionName);
	}

	/**
	 * Gets the functions in this description.
	 * 
	 * @return the functions
	 */
	public final List<DepFunction> getFunctions() {
		return new LinkedList<>(this.functionMap.values());
	}

	@Override
	public final String toString() {
		return "Description #" + this.id + " #" + this.name;
	}

}
