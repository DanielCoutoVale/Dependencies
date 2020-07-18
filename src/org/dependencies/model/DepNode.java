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

	public final String getFunctionName(Integer index) {
		if (index == -1) return "Head";
		List<DepDependency> dependencies = this.tails.get(index).asWord().getDependencies();
		return dependencies.get(0).getFunction().getName();
	}

}
