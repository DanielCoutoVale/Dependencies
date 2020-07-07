package org.dependencies.ud;

import org.dependencies.utils.StringUtils;
import org.w3c.dom.Element;

/**
 * A UD dependency.
 * 
 * @author Daniel Couto-Vale
 */
public class UdDependency {

	/**
	 * The DOM element.
	 */
	public final Element element;

	/**
	 * Constructor.
	 * 
	 * @param element the element
	 */
	public UdDependency(Element element) {
		this.element = element;
	}

	/**
	 * Gets the name of this dependency.
	 * 
	 * @return the dependency name
	 */
	public final String getName() {
		return StringUtils.toColonCamelCase(element.getAttribute("name").split(":"));
	}

	/**
	 * Sets the name of this dependency.
	 * 
	 * @param the dependency name.
	 */
	public void setName(String name) {
		element.setAttribute("name", name);
	}

}
