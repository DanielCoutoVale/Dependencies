package org.dependencies.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A system as a set of alternative features.
 * 
 * @author Daniel Couto-Vale
 */
public class DepSystem implements Iterable<DepFeature> {

	/**
	 * The system id.
	 */
	private Integer id;

	/**
	 * The system name.
	 */
	private String name;

	/**
	 * The entry condition.
	 */
	private DepEntryCondition entryCondition;

	/**
	 * The feature map.
	 */
	private final Map<String, DepFeature> featureMap;

	/**
	 * Constructor
	 */
	public DepSystem() {
		this.featureMap = new HashMap<>();
	}

	/**
	 * Gets the id of this system.
	 * 
	 * @return the system id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this system.
	 * 
	 * @param id the system id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this system.
	 * 
	 * @return the system name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this system.
	 * 
	 * @param name the system name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the condition for entering this system.
	 * 
	 * @return the entry condition
	 */
	public final DepEntryCondition getEntryCondition() {
		return this.entryCondition;
	}

	/**
	 * Sets the condition for entering this system.
	 * 
	 * @param entryCondition the entry condition
	 */
	public final void setEntryCondition(DepEntryCondition entryCondition) {
		this.entryCondition = entryCondition;
	}

	/**
	 * Sets entry condition form.
	 * 
	 * @param form the form of the entry condition.
	 */
	public final void setEntryConditionForm(String form) {
		this.entryCondition = new DepEntryCondition(form);
	}

	/**
	 * Gets the entry condition form.
	 * 
	 * @return the form of the entry condition
	 */
	public final String getEntryConditionForm() {
		return this.entryCondition.toString();
	}

	/**
	 * Gets the features in this system.
	 * 
	 * @return the features
	 */
	public final List<DepFeature> getFeatures() {
		return new LinkedList<>(this.featureMap.values());
	}

	/**
	 * Adds a feature to this system.
	 * 
	 * @param feature the feature
	 */
	public final void addFeature(DepFeature feature) {
		this.featureMap.put(feature.getName(), feature);
	}

	/**
	 * Gets a feature in this system by name.
	 * 
	 * @param featureName the feature name
	 * @return the feature
	 */
	public final DepFeature getFeature(String featureName) {
		return this.featureMap.get(featureName);
	}

	/**
	 * Gets whether this system is entered given the features chosen and not chosen.
	 * 
	 * @param systems  the systems
	 * @param features the features
	 * @return <code>true</code> if the system is entered and <code>false</code>
	 *         otherwise.
	 */
	public final boolean isEntered(List<DepSystem> systems, List<DepFeature> features) {
		return this.entryCondition.isFulfilled(systems, features);
	}

	/**
	 * Gets whether this system is exited given the features chosen.
	 * 
	 * @param features the features
	 * @return <code>true</code> if the system is exited and <code>false</code>
	 *         otherwise.
	 */
	public final boolean isExited(List<DepFeature> features) {
		List<DepFeature> A = new LinkedList<DepFeature>(this.featureMap.values());
		List<DepFeature> B = new LinkedList<DepFeature>(features);
		A.sort((x, y) -> x.compareTo(y));
		B.sort((x, y) -> x.compareTo(y));
		while (!A.isEmpty() && !B.isEmpty()) {
			DepFeature a = A.get(0);
			DepFeature b = B.get(0);
			int comparison = a.compareTo(b);
			if (comparison < 0) {
				A.remove(0);
			}
			if (comparison > 0) {
				B.remove(0);
			}
			if (comparison == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Iterator<DepFeature> iterator() {
		return this.featureMap.values().iterator();
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("system " + this.name);
		buffer.append("\n");
		buffer.append("> " + this.entryCondition);
		buffer.append("\n");
		for (DepFeature feature : featureMap.values()) {
			buffer.append("- " + feature);
			buffer.append("\n");
		}
		return buffer.toString();
	}

}
