package org.dependencies.utils;

/**
 * String utility.
 * 
 * @author Daniel Couto-Vale
 */
public class StringUtils {

	/**
	 * Turns a series of tokens into a camel case colon-separated word
	 * 
	 * @param tokens the tokens
	 * @return the camel case colon-separated string
	 */
	public static String toColonCamelCase(String... tokens) {
		StringBuffer buffer = new StringBuffer();
		for (String part : tokens) {
			if (buffer.length() > 0) {
				buffer.append(":");
			}
			buffer.append(part.substring(0, 1).toUpperCase());
			buffer.append(part.substring(1));
		}
		return buffer.toString();
	}

}
