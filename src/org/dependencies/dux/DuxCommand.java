package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

/**
 * A DUX command for translating a word analysis from a linguistic description
 * to another.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxCommand {

	/**
	 * The match tags.
	 */
	private final List<DuxFeature> matchTags;

	/**
	 * The magis tags.
	 */
	private final List<DuxFeature> magisTags;

	/**
	 * The minus tags.
	 */
	private final List<DuxFeature> minusTags;

	/**
	 * Constructor
	 */
	public DuxCommand() {
		matchTags = new LinkedList<>();
		magisTags = new LinkedList<>();
		minusTags = new LinkedList<>();
	}

	/**
	 * Adds a match tag.
	 * 
	 * @param matchTag the match tag
	 */
	public final void addMatchTag(DuxFeature matchTag) {
		matchTags.add(matchTag);
	}

	/**
	 * Adds a magis tag.
	 * 
	 * @param magisTag the magis tag
	 */
	public final void addMagisTag(DuxFeature magisTag) {
		magisTags.add(magisTag);
	}

	/**
	 * Adds a minus tag.
	 * 
	 * @param minusTag the minus tag
	 */
	public final void addMinusTag(DuxFeature minusTag) {
		minusTags.add(minusTag);
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
		buffer.append(" =>");
		for (DuxFeature tag : magisTags) {
			buffer.append(" +");
			buffer.append(tag);
		}
		for (DuxFeature tag : minusTags) {
			buffer.append(" -");
			buffer.append(tag);
		}
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

	/**
	 * Gets the magis tags.
	 * 
	 * @return the magis tags
	 */
	public List<DuxFeature> getMagisTags() {
		return new LinkedList<>(this.magisTags);
	}

	/**
	 * Gets the minus tags.
	 * 
	 * @return the minus tags
	 */
	public List<DuxFeature> getMinusTags() {
		return new LinkedList<>(this.minusTags);
	}

}
