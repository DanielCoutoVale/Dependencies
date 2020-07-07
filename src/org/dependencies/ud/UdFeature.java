package org.dependencies.ud;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

/**
 * A UD feature.
 * 
 * @author Daniel Couto-Vale
 */
public class UdFeature {

	/**
	 * The DOM element.
	 */
	public final Element element;

	/**
	 * Constructor
	 * 
	 * @param element the DOM element
	 */
	public UdFeature(Element element) {
		this.element = element;
	}

	/**
	 * Gets the name of this feature.
	 * 
	 * @return the feature name
	 */
	public final String getName() {
		return this.element.getAttribute("name").toUpperCase();
	}

	/**
	 * Gets the value of this feature.
	 * 
	 * @return the feature value
	 */
	public final String getValue() {
		return this.element.getAttribute("value").toLowerCase();
	}

	/**
	 * Gets the tag names of this feature.
	 * 
	 * @return the tag names
	 */
	public List<String> getTagNames() {
		String[] array = element.getAttribute("upos").toLowerCase().split(",");
		List<String> list = Arrays.asList(array);
		return new LinkedList<String>(list);
	}

	/**
	 * Sets the name of this feature.
	 * 
	 * @param name the feature name
	 */
	public void setName(String name) {
		element.setAttribute("name", name);
	}

	/**
	 * Sets the value of this feature.
	 * 
	 * @param value the feature value
	 */
	public void setValue(String value) {
		element.setAttribute("value", value);
	}

	/**
	 * Sets the tag names of this feature.
	 * 
	 * @param tagNames the tag names
	 */
	public void setTagNames(List<String> tagNames) {
		element.setAttribute("upos", String.join(",", tagNames));
	}

}
