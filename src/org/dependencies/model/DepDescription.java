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
	 * Lazy system map.
	 */
	private final Map<DepFeature, DepSystem> systemMapByFeature;

	/**
	 * The metafunction map.
	 */
	private final Map<String, DepMetafunction> metafunctionMap;

	/**
	 * Constructor
	 */
	public DepDescription() {
		this.systemMap = new HashMap<>();
		this.systemMapByFeature = new HashMap<>();
		this.metafunctionMap = new HashMap<>();
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
	 * Adds a metafunction to this description.
	 * 
	 * @param metafunction the metafunction
	 */
	public final void addMetafunction(DepMetafunction metafunction) {
		this.metafunctionMap.put(metafunction.getName(), metafunction);
	}

	/**
	 * Gets a metafunction in this description by name.
	 * 
	 * @param metafunctionName the function name
	 * @return the function
	 */
	public final DepMetafunction getMetafunction(String metafunctionName) {
		return this.metafunctionMap.get(metafunctionName);
	}

	/**
	 * Gets the metafunctions in this description.
	 * 
	 * @return the metafunctions
	 */
	public final List<DepMetafunction> getMetafunctions() {
		return new LinkedList<>(this.metafunctionMap.values());
	}

	@Override
	public final String toString() {
		return "Description #" + this.id + " #" + this.name;
	}

	public final DepFeature getRank(String rankName) {
		for (DepSystem system : this.systemMap.values()) {
			String systemName = system.getName();
			if (systemName.equals("RANK") || systemName.endsWith("-COMPLEXITY")) {
				for (DepFeature feature : system) {
					if (feature.getName().equals(rankName)) {
						return feature;
					}
				}
			}
		}
		return null;
	}

	public final DepSystem getSystem(DepFeature feature) {
		DepSystem system = systemMapByFeature.get(feature);
		if (system == null) {
			systemMap.values().forEach(value -> value.getFeatures().forEach(key -> systemMapByFeature.put(key, value)));
			system = systemMapByFeature.get(feature);
		}
		return system;
	}

}
