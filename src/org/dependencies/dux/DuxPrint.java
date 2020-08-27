package org.dependencies.dux;

/**
 * A print command for debugging queries.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxPrint implements DuxCommand {

	/**
	 * The output
	 */
	private final String output;

	/**
	 * Constructor
	 * 
	 * @param output the output
	 */
	public DuxPrint(String output) {
		this.output = output;
	}

	/**
	 * Gets the output.
	 * 
	 * @return the output
	 */
	public final String getOutput() {
		return output;
	}

	@Override
	public final String toString() {
		return "## " + getOutput();
	}

}
