package org.dependencies.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A metafunction.
 * 
 * @author Daniel Couto-Vale
 */
public class DepMetafunction {
	
	/**
	 * The function id.
	 */
	private Integer id;
	
	/**
	 * The metafunction name.
	 */
	private String name;

	/**
	 * The function map.
	 */
	private final Map<String, DepFunction> functionMap;
	
	/**
	 * Constructor
	 */
	public DepMetafunction() {
		this.functionMap = new HashMap<>();		
	}

	/**
	 * Gets the id of this function.
	 * 
	 * @return the function id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this function.
	 * 
	 * @param id the function id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this function.
	 * 
	 * @return the function name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this function.
	 * 
	 * @param name the function name
	 */
	public final void setName(String name) {
		this.name = name;
	}	
	
	/**
	 * Adds a function to this metafunction.
	 * 
	 * @param function the function
	 */
	public final void addFunction(DepFunction function) {
		this.functionMap.put(function.getName(), function);
	}

	/**
	 * Gets a function in this metafunction by name.
	 * 
	 * @param functionName the function name
	 * @return the function
	 */
	public final DepFunction getFunction(String functionName) {
		return this.functionMap.get(functionName);
	}

	/**
	 * Gets the functions in this metafunction.
	 * 
	 * @return the functions
	 */
	public final List<DepFunction> getFunctions() {
		return new LinkedList<>(this.functionMap.values());
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("metafunction " + this.name);
		buffer.append("\n");
		for (DepFunction function : functionMap.values()) {
			buffer.append("- " + function);
			buffer.append("\n");
		}
		return buffer.toString();
	}

}
