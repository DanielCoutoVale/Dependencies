package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

/**
 * A pattern for a word regarding its features.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxFeaturedWord implements DuxPattern {

	/**
	 * The match tags.
	 */
	private final List<DuxFeature> features;

	/**
	 * Constructor
	 */
	public DuxFeaturedWord() {
		features = new LinkedList<>();
	}

	/**
	 * Gets the features.
	 * 
	 * @return the features
	 */
	public final List<DuxFeature> getFeatures() {
		return new LinkedList<>(this.features);
	}

	/**
	 * Adds a feature.
	 * 
	 * @param feature the feature
	 */
	public final void addFeature(DuxFeature feature) {
		features.add(feature);
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (DuxFeature feature : features) {
			if (buffer.length() > 1) {
				buffer.append(" ");
			}
			buffer.append(feature);
		}
		buffer.append("]");
		return buffer.toString();
	}

}
