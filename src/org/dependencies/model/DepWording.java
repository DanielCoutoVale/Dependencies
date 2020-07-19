package org.dependencies.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A wording as the largest grammatical unit within a text.
 * 
 * @author Daniel Couto-Vale
 */
public class DepWording implements Iterable<DepWord> {

	/**
	 * The wording id.
	 */
	private Integer id;

	/**
	 * The wording form.
	 */
	private String form;

	/**
	 * The words
	 */
	private final List<DepWord> words;

	/**
	 * Constructor
	 */
	public DepWording() {
		this.words = new LinkedList<>();
	}

	/**
	 * Gets the id of this wording.
	 * 
	 * @return the wording id
	 */
	public final Integer getId() {
		return this.id;
	}

	/**
	 * Sets the id of this wording.
	 * 
	 * @param id the wording id
	 */
	public final void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the form of this wording.
	 * 
	 * @return the wording form
	 */
	public final String getForm() {
		return this.form;
	}

	/**
	 * Sets the form of this wording.
	 * 
	 * @param form the wording form
	 */
	public final void setForm(String form) {
		this.form = form;
	}

	/**
	 * Gets the words in this wording.
	 * 
	 * @return the words
	 */
	public final List<DepWord> getWords() {
		return new LinkedList<>(this.words);
	}

	/**
	 * Appends a word to the end of this wording.
	 * 
	 * @param word the word
	 */
	public final void addWord(DepWord word) {
		this.words.add(word);
	}

	/**
	 * Insert a word at the specified index in this wording.
	 * 
	 * @param index the index for the word
	 * @param word  the word
	 */
	public final void addWord(int index, DepWord word) {
		this.words.add(index, word);
	}

	@Override
	public final Iterator<DepWord> iterator() {
		return this.words.iterator();
	}

	@Override
	public final String toString() {
		return "Wording #" + this.id + " '" + this.form + "' ";
	}

	/**
	 * Returns the order of the word in the wording.
	 * 
	 * @param word the word
	 * @return the order of the word
	 */
	public final int orderOf(DepWord word) {
		return this.words.indexOf(word) + 1;
	}

	/**
	 * Makes a dependency tree for the wording.
	 * 
	 * @return the dependency tree
	 */
	public final DepNode makeDependencyTree() {
		Map<Integer, DepNode> nodeMap = new HashMap<>();
		nodeMap.put(0, new DepNode(null));
		for (DepWord word : this) {
			DepNode node = new DepNode(word);
			nodeMap.put(this.orderOf(word), node);
		}
		Integer followedId = 1;
		for (DepWord word : this) {
			DepNode node = nodeMap.get(this.orderOf(word));
			List<DepDependency> dependencies = word.getDependencies();
			if (dependencies.size() == 0) {
				if (null != word.getFeature("lexical-verb")) {
					followedId = this.orderOf(word);
					DepNode head = nodeMap.get(0);
					head.addTail(node);
					node.setHead(head);
					break;
				}
			}
		}
		for (DepWord word : this) {
			DepNode node = nodeMap.get(this.orderOf(word));
			if (node.getHead() != null) {
				continue;
			}
			List<DepDependency> dependencies = word.getDependencies();
			if (dependencies.size() == 0) {
				DepNode head = nodeMap.get(0);
				if (head.getTails().size() == 0) {
					followedId = this.orderOf(word);
					head.addTail(node);
					node.setHead(head);
				} else {
					DepDependency dependency = new DepDependency();
					DepFunction function = new DepFunction();
					function.setName("xxxxx");
					DepFeature followedRank = new DepFeature();
					followedRank.setName("clause");
					dependency.setFunction(function);
					dependency.setHeadOrder(followedId);
					dependency.setHeadRank(followedRank);
					word.addDependency(dependency);
					head = nodeMap.get(dependency.getHeadOrder());
					head.addTail(node);
					node.setHead(head);
				}
			} else {
				DepDependency dependency = dependencies.get(0);
				DepNode head = nodeMap.get(dependency.getHeadOrder());
				head.addTail(node);
				node.setHead(head);
			}
		}
		if (nodeMap.get(0).getTails().size() == 0) {
			nodeMap.get(1).asWord().clearDependencies();
		}
		return nodeMap.get(0).getTails().get(0);
	}

}
