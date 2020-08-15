package org.dependencies.dux;

/**
 * A pattern for a word regarding its serial number.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxNumberedWord implements DuxPattern {

	private final Integer id;

	public DuxNumberedWord(String form) {
		this.id = Integer.parseInt(form);
	}

	public final Integer getId() {
		return id;
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(this.id);
		buffer.append("]");
		return buffer.toString();
	}

	public final static boolean matches(String form) {
		return form.matches("^[1-9][0-9]*$");
	}

}
