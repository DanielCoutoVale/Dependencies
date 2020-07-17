package org.dependencies.dux;

/**
 * A command to pause translation.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxStop implements DuxCommand {

	@Override
	public final String toString() {
		return "STOP";
	}

}
