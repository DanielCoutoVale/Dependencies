package org.dependencies.ud;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * A UD document builder.
 * 
 * @author Daniel Couto-Vale
 */
public class UdDocumentBuilder {

	/**
	 * The DOM document builder.
	 */
	private DocumentBuilder builder;

	/**
	 * Constructor
	 * 
	 * @throws ParserConfigurationException the the XML parser is badly configured
	 */
	public UdDocumentBuilder() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
	}

	/**
	 * Parse the content of the given file as an XML document and return a new UD
	 * document object.
	 * 
	 * @param file the document file
	 * @return the document
	 * @throws IOException  if the file is not found
	 * @throws SAXException if the XML file is malformed
	 */
	public final UdDocument parse(File file) throws SAXException, IOException {
		return new UdDocument(this.builder.parse(file));
	}

}
