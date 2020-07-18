package org.dependencies.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A word as the smallest independent unit in a text.
 * 
 * @author Daniel Couto-Vale
 */
public class DepWord {

	/**
	 * The word id.
	 */
	private Integer id;

	/**
	 * The word form.
	 */
	private String form;

	/**
	 * Whether the word is followed by a space.
	 */
	private boolean backspaced;

	/**
	 * The word lemma.
	 */
	private String lemma;

	/**
	 * The word features.
	 */
	private final Map<String, DepFeature> featureMap;

	/**
	 * The word features.
	 */
	private final Map<String, DepDependency> dependencyMap;

	/**
	 * Constructor
	 */
	public DepWord() {
		this.featureMap = new HashMap<>();
		this.dependencyMap = new HashMap<>();
	}

	/**
	 * Gets the id of this word.
	 * 
	 * @return the word id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this word.
	 * 
	 * @param id the word id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the form of this word.
	 * 
	 * @return the word form
	 */
	public final String getForm() {
		return this.form;
	}

	/**
	 * Sets the form of this word.
	 * 
	 * @param form the word form
	 */
	public final void setForm(String form) {
		this.form = form;
	}

	/**
	 * Gets whether the word is followed by a space.
	 * 
	 * @return <code>true</code> if the word is followed by a backspace and
	 *         <code>false</code> otherwise
	 */
	public final boolean isBackspaced() {
		return this.backspaced;
	}

	/**
	 * Sets whether the word is followed by a space.
	 * 
	 * @param backspaced <code>true</code> if the word is followed by a backspace
	 *                   and <code>false</code> otherwise
	 */
	public final void setBackspaced(boolean backspaced) {
		this.backspaced = backspaced;
	}

	/**
	 * Gets the lemma of this word.
	 * 
	 * @return the word lemma
	 */
	public String getLemma() {
		return this.lemma;
	}

	/**
	 * Sets the lemma of this word.
	 * 
	 * @param lemma the word lemma
	 */
	public final void setLemma(String lemma) {
		this.lemma = lemma;
	}

	/**
	 * Adds a feature to this word.
	 * 
	 * @param feature the feature
	 */
	public final void addFeature(DepFeature feature) {
		this.featureMap.put(feature.getName(), feature);
	}

	/**
	 * Gets a feature in this word by name
	 * 
	 * @param featureName the feature name
	 * @return the feature
	 */
	public final DepFeature getFeature(String featureName) {
		return this.featureMap.get(featureName);
	}

	/**
	 * Gets the features in this word.
	 * 
	 * @return the features
	 */
	public final List<DepFeature> getFeatures() {
		return new LinkedList<>(this.featureMap.values());
	}

	/**
	 * Adds a dependency to this word.
	 * 
	 * @param dependency the dependency
	 */
	public final void addDependency(DepDependency dependency) {
		this.dependencyMap.put(dependency.getFunction().getName(), dependency);
	}

	/**
	 * Gets a dependency in this word by name
	 * 
	 * @param functionName the function name
	 * @return the dependency
	 */
	public final DepDependency getDependency(String functionName) {
		return this.dependencyMap.get(functionName);
	}

	/**
	 * Gets the dependencies in this word.
	 * 
	 * @return the dependencies
	 */
	public final List<DepDependency> getDependencies() {
		return new LinkedList<>(this.dependencyMap.values());
	}

	@Override
	public final String toString() {
		return this.form + " #" + this.lemma + " " + this.featureMap.values();
	}

	/**
	 * Clears the dependencies.
	 */
	public void clearDependencies() {
		this.dependencyMap.clear();
	}

	/**
	 * Gets the word feature in the given system.
	 * 
	 * @param system the system
	 * @return the feature
	 */
	public final DepFeature getFeatureIn(DepSystem system) {
		Set<DepFeature> features = new HashSet<>(this.featureMap.values());
		features.retainAll(system.getFeatures());
		if (features.isEmpty()) return null;
		return features.iterator().next();
	}

}
