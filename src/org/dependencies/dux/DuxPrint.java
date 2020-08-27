package org.dependencies.dux;

public class DuxPrint implements DuxCommand {

	private final String output;

	public DuxPrint(String output) {
		this.output = output;
	}

	public final String getOutput() {
		return output;
	}
	
	@Override
	public final String toString() {
		return "## " + getOutput();
	}

}
