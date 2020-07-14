package org.dependencies.model;

/**
 * A feature that a word can have.
 * 
 * @author Daniel Couto-Vale
 */
public class DepFeature implements Comparable<DepFeature> {

	/**
	 * The feature id.
	 */
	private Integer id;

	/**
	 * The feature name.
	 */
	private String name;

	/**
	 * Gets the id of this feature.
	 * 
	 * @return the feature id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this feature.
	 * 
	 * @param id the feature id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name of this feature.
	 * 
	 * @return the feature name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Sets the name of this feature.
	 * 
	 * @param name the feature name
	 */
	public final void setName(String name) {
		this.name = name;
	}

	@Override
	public final String toString() {
		return this.name;
	}

	@Override
	public final int hashCode() {
		return this.id;
	}

	@Override
	public final int compareTo(DepFeature feature) {
		return this.id.compareTo(feature.id);
	}

	@Override
	public final boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (!(object instanceof DepFeature)) {
			return false;
		}
		DepFeature feature = (DepFeature) object;
		return this.name.equals(feature.name);
	}

}
