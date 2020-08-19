package org.dependencies.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A word as a dependency tree node.
 * 
 * @author Daniel Couto-Vale
 */
public class DepNode {
	
	/**
	 * The ranks
	 */
	private final static List<String> rankNames;
	
	static {
		rankNames = new LinkedList<>();
		rankNames.add("clause-complex");
		rankNames.add("clause");
		rankNames.add("phrase");
		rankNames.add("group-complex");
		rankNames.add("group");
		rankNames.add("word");
	}

	/**
	 * This word.
	 */
	private final DepWord word;

	/**
	 * The head node.
	 */
	private DepNode head;

	/**
	 * The tail nodes.
	 */
	private List<DepNode> tails;

	/**
	 * Constructor
	 */
	public DepNode(DepWord word) {
		this.word = word;
		this.tails = new LinkedList<>();
	}

	/**
	 * Gets the head node of this node.
	 * 
	 * @return the head node
	 */
	public final DepNode getHead() {
		return this.head;
	}

	/**
	 * Sets the head node of this node.
	 * 
	 * @param head the head node
	 */
	public final void setHead(DepNode head) {
		this.head = head;
	}

	/**
	 * Gets this word.
	 * 
	 * @return this word
	 */
	public final DepWord asWord() {
		return this.word;
	}

	/**
	 * Gets all tail nodes
	 * 
	 * @return the tail nodes
	 */
	public final List<DepNode> getTails() {
		return new LinkedList<>(this.tails);
	}

	/**
	 * Adds a tail node to this node.
	 * 
	 * @param tail the tail node
	 */
	public final void addTail(DepNode tail) {
		this.tails.add(tail);
	}
 
	/**
	 * If the word belongs to this node, return the node index.
	 * If the word is in a tail,
	 *   for the current or higher ranks
	 *   - return the subindex of the word.
	 *   for lower ranks
	 *   - return the node index.
	 * If the word is not in this node, return -2.
	 * 
	 * @param word the word
	 * @param rankName the rank name
	 * @return the index
	 */
	public final long indexOf(DepWord word, String rankName) {
		if (this.word == word) {
			return getIndex();
		}
		for (int index = 0; index < this.tails.size(); index++) {
			DepNode tail = this.tails.get(index);
			long indexInTail = tail.indexOf(word, rankName);
			if (-2 != indexInTail) {
				if (rankNames.indexOf(tail.getHeadRankName()) <= rankNames.indexOf(rankName)) {
					return makeSubindex(index, indexInTail);
				} else {
					return getIndex();
				}
			}
		}
		// NOT IN THIS NODE
		return -2;
	}

	private long makeSubindex(int index, long indexInTail) {
		return 1 + index + 100 * indexInTail;
	}

	private int getIndex() {
		return this.tails.size() + 1;
	}

	public final Integer indexOf(DepWord word) {
		if (this.word == word) {
			return -1;
		}
		for (int index = 0; index < this.tails.size(); index++) {
			DepNode tail = this.tails.get(index);
			if (-2 != tail.indexOf(word)) {
				return index;
			}
		}
		return -2;
	}

	public final String getFunctionName(long index, String rankName) {
		DepNode node = this;
		do {
			if (index % 100 == node.tails.size() + 1) {
				break;
			}
			node = node.tails.get((int)(index % 100 - 1));
			index = Math.floorDiv(index, 100);
		} while (index > 0);
		List<DepDependency> dependencies = node.asWord().getDependencies();
		if (dependencies.isEmpty()) {
			if (rankName.equals("clause-complex")) {
				return "Main";
			} else if (rankName.equals("clause")) {
				return makeClauseHeadName(node);
			} else {
				return "Head";
			}
		}
		if (rankNames.indexOf(node.getHeadRankName()) < rankNames.indexOf(rankName)) {
			if (rankName.equals("clause-complex")) {
				return "Main";
			} else if (rankName.equals("clause")) {
				return makeClauseHeadName(node);
			}
		}
		DepDependency dependency = dependencies.get(0);
		return dependency.getFunction().getName();
	}

	private String makeClauseHeadName(DepNode node) {
		if (null != node.asWord().getFeature("lexical-verb") || null != node.asWord().getFeature("conjunction")) {
			return "Process";
		} else if (null != node.asWord().getFeature("noun") || null != node.asWord().getFeature("source")) {
			return "Thing";
		} else if (null != node.asWord().getFeature("adverb") || null != node.asWord().getFeature("adposition")) {
			return "Circumstance";
		} else {
			return "Head";
		}
	}

	private final String getHeadRankName() {
		List<DepDependency> dependencies = this.asWord().getDependencies();
		DepFeature headRank = dependencies.get(0).getHeadRank();
		if (headRank == null) {
			return "clause";
		}
		return headRank.getName();
	}
	
	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		if (this.word != null) {
			buffer.append(this.word.getForm());
		}
		if (this.tails.size() > 0) {
			buffer.append("[");
		}
		for (DepNode tail : this.tails) {
			if (0 != this.tails.indexOf(tail)) {
				buffer.append(" ");
			}
			buffer.append(tail);
		}
		if (this.tails.size() > 0) {
			buffer.append("]");
		}
		return buffer.toString();
	}

}
