package org.dependencies.dux;

/**
 * A function
 * 
 * @author Daniel Couto-Vale
 */
public class DuxFunction implements DuxPattern, DuxChange {
	
	/**
	 * The function prefix
	 */
	private final String prefix;

	/**
	 * The metafunction name for this function
	 */
	private final String metafunctionName;

	/**
	 * The function name 
	 */
	private final String name;

	/**
	 * The word index
	 */
	private final Integer wordIndex;

	/**
	 * The word rank name
	 */
	private final String wordRankName;

	/**
	 * The head word index
	 */
	private final Integer headIndex;

	/**
	 * The rank name for the head word 
	 */
	private final String headRankName;

	/**
	 * Constructor
	 * 
	 * @param form the feature form
	 */
	public DuxFunction(String form) {
		String[] A = form.substring(0, form.length() - 1).split("[(]");
		String[] split = A[0].split(":");
		if (split.length == 3) {
			this.prefix = split[0];
			this.metafunctionName = split[1];
			this.name = split[2];
		} else if (split.length == 2) {
			if (split[0].equals("S") || split[0].equals("T")) {
				this.prefix = split[0];
				this.metafunctionName = "%";
				this.name = split[1];
			} else {
				this.prefix = "H";
				this.metafunctionName = split[0];
				this.name = split[1];
			}
		} else if (split.length == 1) {
			this.prefix = "H";
			this.metafunctionName = "%";
			this.name = split[0];
		} else {
			this.prefix = null;
			this.metafunctionName = null;
			this.name = null;
			System.err.println("Incorrect feature form: " + form);
			System.exit(-1);
		}
		String[] B = A[1].split(",");
		String[] B0 = B[0].split(":");
		String[] B1 = B[1].split(":");
		this.wordIndex = Integer.parseInt(B0[0]);
		this.wordRankName = B0.length > 1 ? B0[1] : "%";
		this.headIndex = Integer.parseInt(B1[0]);
		this.headRankName = B1.length > 1 ? B1[1] : "%";
	}

	/**
	 * Gets whether the token has the form of a function.
	 * 
	 * @param token the token
	 * @return <code>true</code> if the token has the form of a feature
	 */
	public final static boolean matches(String token) {
		return token.matches("^.*\\(.*\\)$");
	}

	/**
	 * Gets the prefix of this function.
	 * 
	 * @return the function prefix
	 */
	public final String getPrefix() {
		return this.prefix;
	}

	/**
	 * Gets the name of the metafunction of this function.
	 * 
	 * @return the metafunction name
	 */
	public final String getMetafunctionName() {
		return this.metafunctionName;
	}

	/**
	 * Gets the name of this function.
	 * 
	 * @return the function name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Gets the index of the word that has this function.
	 * 
	 * @return the word index
	 */
	public final Integer getWordIndex() {
		return wordIndex;
	}

	/**
	 * Gets the rank of the word at which it has this function.
	 * 
	 * @return the word rank name
	 */
	public final String getWordRankName() {
		return wordRankName;
	}

	/**
	 * Gets the index of the head word for this function.
	 * 
	 * @return the index of the head word
	 */
	public final Integer getHeadIndex() {
		return headIndex;
	}

	/**
	 * Gets the rank at which the word is a head for this function. 
	 * 
	 * @return the rank name for the head word
	 */
	public final String getHeadRankName() {
		return headRankName;
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		if (!this.prefix.equals("H")) {
			buffer.append(this.prefix);
			buffer.append(":");
		}
		if (!metafunctionName.equals("%")) {
			buffer.append(this.metafunctionName);
			buffer.append(":");
		}
		buffer.append(this.name);
		buffer.append("(");
		buffer.append(this.wordIndex);
		buffer.append(this.wordRankName.equals("%") ? "" : ":" + this.wordRankName);
		buffer.append(",");
		buffer.append(this.headIndex);
		buffer.append(this.headRankName.equals("%") ? "" : ":" + this.headRankName);
		buffer.append(")");
		return buffer.toString();
	}

	public final boolean isBefore() {
		return wordRankName.equals("%") && 
				headRankName.equals("%") &&
				metafunctionName.equals("%") &&
				name.equals("before");
	}

}
