package org.dependencies.ud;

import org.w3c.dom.Element;

/**
 * A UD tag.
 * 
 * @author Daniel Couto-Vale
 */
public class UdTag {

	/**
	 * The DOM element.
	 */
	public Element element;

	/**
	 * Constructor
	 * 
	 * @param element the element
	 */
	public UdTag(Element element) {
		this.element = element;
	}

	/**
	 * Gets the name of this tag.
	 * 
	 * @return the tag name
	 */
	public String getName() {
		return element.getAttribute("name").toLowerCase();
	}

	/**
	 * Sets the name of this tag.
	 * 
	 * @param name the tag name
	 */
	public void setName(String name) {
		element.setAttribute("name", name);
	}

}
