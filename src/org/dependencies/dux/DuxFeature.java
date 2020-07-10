package org.dependencies.dux;

/**
 * A DUX feature.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxFeature implements DuxChange {

	/**
	 * The prefix, which stands for a description and an analysis.
	 */
	private String prefix;

	/**
	 * The system name.
	 */
	private String systemName;

	/**
	 * The feature name.
	 */
	private String featureName;

	/**
	 * Constructor
	 * 
	 * @param form the feature form
	 */
	public DuxFeature(String form) {
		String[] split = form.split(":");
		this.prefix = split[0];
		this.systemName = split[1];
		this.featureName = split[2];
	}

	/**
	 * Gets whether the token has the form of a feature.
	 * 
	 * @param token the token
	 * @return <code>true</code> if the token has the form of a feature
	 */
	public final static boolean matches(String token) {
		return token.split(":").length == 3;
	}

	/**
	 * Gets the prefix of this feature.
	 * 
	 * @return the feature prefix
	 */
	public final String getPrefix() {
		return this.prefix;
	}

	/**
	 * Sets the prefix of this feature.
	 * 
	 * @param prefix the feature prefix
	 */
	public final void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the name of the system that has this feature.
	 * 
	 * @return the feature name
	 */
	public final String getSystemName() {
		return systemName;
	}

	/**
	 * Sets the name of the system that has this feature.
	 * 
	 * @param systemName the system name
	 */
	public final void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * Gets the name of this feature.
	 * 
	 * @return the feature name
	 */
	public final String getFeatureName() {
		return featureName;
	}

	/**
	 * Sets the name of this feature.
	 * 
	 * @param featureName the feature name
	 */
	public final void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	@Override
	public final String toString() {
		return this.prefix + ":" + this.systemName + ":" + this.featureName;
	}

}
