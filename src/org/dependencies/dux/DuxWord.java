package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

public class DuxWord implements DuxMatch {

	/**
	 * The match tags.
	 */
	private final List<DuxFeature> matchTags;

	/**
	 * Constructor
	 */
	public DuxWord() {
		matchTags = new LinkedList<>();
	}

	/**
	 * Adds a match tag.
	 * 
	 * @param matchTag the match tag
	 */
	public final void addMatchTag(DuxFeature matchTag) {
		matchTags.add(matchTag);
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (DuxFeature tag : matchTags) {
			if (buffer.length() > 1) {
				buffer.append(" ");
			}
			buffer.append(tag);
		}
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Gets the match tags.
	 * 
	 * @return the match tags
	 */
	public final List<DuxFeature> getMatchTags() {
		return new LinkedList<>(this.matchTags);
	}

}
