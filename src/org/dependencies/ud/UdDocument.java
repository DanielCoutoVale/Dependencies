package org.dependencies.ud;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A UD document.
 * 
 * @author Daniel Couto-Vale
 */
public class UdDocument {

	/**
	 * The DOM document.
	 */
	public final Document document;

	/**
	 * Constructor
	 * 
	 * @param document the DOM document
	 */
	public UdDocument(Document document) {
		this.document = document;
	}

	/**
	 * Gets the tags in this document.
	 * 
	 * @return the tags
	 */
	public final List<UdTag> getTags() {
		List<UdTag> tags = new LinkedList<>();
		NodeList nodeList = this.document.getElementsByTagName("tag");
		for (int i = 0; i < nodeList.getLength(); i++) {
			tags.add(new UdTag((Element) nodeList.item(i)));
		}
		return tags;
	}

	/**
	 * Gets the features in this document.
	 * 
	 * @return the features
	 */
	public final List<UdFeature> getFeats() {
		List<UdFeature> tags = new LinkedList<>();
		NodeList nodeList = this.document.getElementsByTagName("feat");
		for (int i = 0; i < nodeList.getLength(); i++) {
			tags.add(new UdFeature((Element) nodeList.item(i)));
		}
		return tags;
	}

	/**
	 * Gets the dependencies in this document.
	 * 
	 * @return the dependencies
	 */
	public final List<UdDependency> getDeps() {
		List<UdDependency> tags = new LinkedList<>();
		NodeList nodeList = this.document.getElementsByTagName("dep");
		for (int i = 0; i < nodeList.getLength(); i++) {
			tags.add(new UdDependency((Element) nodeList.item(i)));
		}
		return tags;
	}

}
