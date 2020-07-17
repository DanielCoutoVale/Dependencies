package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

/**
 * A DUX command for translating a word analysis from a linguistic description
 * to another.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxTranslate implements DuxCommand {
	
	private final List<DuxMatch> matches;

	/**
	 * The magis tags.
	 */
	private final List<DuxChange> magisTags;

	/**
	 * The minus tags.
	 */
	private final List<DuxChange> minusTags;

	/**
	 * Constructor
	 */
	public DuxTranslate() {
		matches = new LinkedList<>();
		magisTags = new LinkedList<>();
		minusTags = new LinkedList<>();
	}
	
	/**
	 * Ads a match.
	 * 
	 * @param match the match
	 */
	public final void addMatch(DuxMatch match) {
		matches.add(match);
	}
	

	/**
	 * Adds a magis tag.
	 * 
	 * @param magisTag the magis tag
	 */
	public final void addMagisTag(DuxChange magisTag) {
		magisTags.add(magisTag);
	}

	/**
	 * Adds a minus tag.
	 * 
	 * @param minusTag the minus tag
	 */
	public final void addMinusTag(DuxChange minusTag) {
		minusTags.add(minusTag);
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		for (DuxMatch match : matches) {
			if (buffer.length() > 0) {
				buffer.append(" ");
			}
			buffer.append(match);
		}
		buffer.append(" =>");
		for (DuxChange tag : magisTags) {
			buffer.append(" +");
			buffer.append(tag);
		}
		for (DuxChange tag : minusTags) {
			buffer.append(" -");
			buffer.append(tag);
		}
		return buffer.toString();
	}

	/**
	 * Gets the matches.
	 * 
	 * @return the matches
	 */
	public List<DuxMatch> getMatches() {
		return new LinkedList<>(this.matches);
	}

	/**
	 * Gets the magis tags.
	 * 
	 * @return the magis tags
	 */
	public List<DuxChange> getMagisTags() {
		return new LinkedList<>(this.magisTags);
	}

	/**
	 * Gets the minus tags.
	 * 
	 * @return the minus tags
	 */
	public List<DuxChange> getMinusTags() {
		return new LinkedList<>(this.minusTags);
	}

}
