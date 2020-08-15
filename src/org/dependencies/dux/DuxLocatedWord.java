package org.dependencies.dux;

/**
 * A pattern for a word regarding its location in a corpus.
 * 
 * @author Daniel Couto-Vale
 */
public class DuxLocatedWord implements DuxPattern {

	private final String corpusName;
	
	private final String textTitle;
	
	private final Integer wordingOrder;
	
	private final Integer order;
	
	public DuxLocatedWord(String form) {
		String[] split = form.split("[,]");
		if (split.length != 4) {
			System.err.println("Wrong located word form: " + form);
			System.exit(-1);
		}
		this.corpusName = split[0].trim();
		this.textTitle = split[1].trim();
		this.wordingOrder = Integer.parseInt(split[2].trim());
		this.order = Integer.parseInt(split[3].trim());
	}

	public final String getCorpusName() {
		return this.corpusName;
	}

	public final String getTextTitle() {
		return this.textTitle;
	}

	public final Integer getWordingOrder() {
		return this.wordingOrder;
	}

	public final Integer getOrder() {
		return this.order;
	}

	@Override
	public final String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(this.corpusName);
		buffer.append(", ");
		buffer.append(this.textTitle);
		buffer.append(", ");
		buffer.append(this.wordingOrder);
		buffer.append(", ");
		buffer.append(this.order);
		buffer.append("]");
		return buffer.toString();
	}

	public final static boolean matches(String form) {
		return form.matches("^.*,.*, *[1-9][0-9]* *, *[1-9][0-9]*$");
	}
	
}
