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
	private final String prefix;

	/**
	 * The system name.
	 */
	private final String systemName;

	/**
	 * The feature name.
	 */
	private final String name;

	/**
	 * Constructor
	 * 
	 * @param form the feature form
	 */
	public DuxFeature(String form) {
		if (form.startsWith("#") && !form.contains(":")) {
			this.prefix = "W";
			this.systemName = "LEMMA";
			this.name = form.substring(1);
			return;
		}
		if (form.startsWith("'") && form.endsWith("'") && !form.contains(":")) {
			this.prefix = "W";
			this.systemName = "FORM";
			this.name = form.substring(1, form.length() - 1);
			return;
		}
		String[] split = form.split(":");
		if (split.length == 3) {
			this.prefix = split[0];
			this.systemName = split[1];
			this.name = split[2];
		} else if (split.length == 2) {
			if (split[0].equals("S") || split[0].equals("T")) {
				this.prefix = split[0];
				this.systemName = "%";
				this.name = split[1];
			} else {
				this.prefix = "H";
				this.systemName = split[0];
				this.name = split[1];
			}
		} else if (split.length == 1) {
			this.prefix = "H";
			this.systemName = "%";
			this.name = split[0];
		} else {
			this.prefix = null;
			this.systemName = null;
			this.name = null;
			System.err.println("Incorrect feature form: " + form);
			System.exit(-1);
		}
	}

	/**
	 * Gets whether the token has the form of a feature.
	 * 
	 * @param token the token
	 * @return <code>true</code> if the token has the form of a feature
	 */
	public final static boolean matches(String token) {
		return token.split(":").length <= 3;
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
	 * Gets the name of the system that has this feature.
	 * 
	 * @return the feature name
	 */
	public final String getSystemName() {
		return systemName;
	}

	/**
	 * Gets the name of this feature.
	 * 
	 * @return the feature name
	 */
	public final String getName() {
		return name;
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		if (!this.prefix.equals("H") && !this.prefix.equals("W")) {
			buffer.append(this.prefix);
			buffer.append(":");
		}
		if (this.systemName.equals("LEMMA")) {
			buffer.append("#");
			buffer.append(this.name);
		} else if (this.systemName.equals("FORM")) {
			buffer.append("'");
			buffer.append(this.name);
			buffer.append("'");
		} else {
			if (!systemName.equals("%")) {
				buffer.append(this.systemName);
				buffer.append(":");
			}
			buffer.append(this.name);
		}
		
		return buffer.toString();
	}

}
