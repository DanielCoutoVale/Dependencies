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
 
	public final long indexOf(DepWord word, String rankName) {
		if (this.word == word) {
			return this.tails.size() + 1;
		}
		for (int index = 0; index < this.tails.size(); index++) {
			DepNode tail = this.tails.get(index);
			long indexInTail = tail.indexOf(word, rankName);
			if (-2 != indexInTail) {
				if (rankNames.indexOf(tail.getHeadRankName()) <= rankNames.indexOf(rankName)) {
					//System.out.println (index + " " + indexInTail);
					return 1 + index + 100 * indexInTail;
				} else {
					return this.tails.size() + 1;
				}
			}
		}
		return -2;
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
			//System.out.println(index);
			if (index % 100 == node.tails.size() + 1) {
				break;
			}
			node = node.tails.get((int)(index % 100 - 1));
			index = Math.floorDiv(index, 100);
		} while (index > 0);
		List<DepDependency> dependencies = node.asWord().getDependencies();
		if (dependencies.isEmpty()) {
			return "-";
		}
		String headRankName = node.getHeadRankName();
		if (headRankName != null && rankNames.indexOf(headRankName) < rankNames.indexOf(rankName)) {
			if (rankName.equals("clause")) {
				return "Process";
			}
		}
		DepDependency dependency = dependencies.get(0);
		return dependency.getFunction().getName();
	}

	private final String getHeadRankName() {
		List<DepDependency> dependencies = this.asWord().getDependencies();
		DepFeature headRank = dependencies.get(0).getHeadRank();
		if (headRank == null) {
			return "clause";
		}
		return headRank.getName();
	}

}
