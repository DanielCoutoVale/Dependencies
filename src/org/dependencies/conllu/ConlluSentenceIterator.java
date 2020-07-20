package org.dependencies.conllu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dependencies.utils.StringUtils;

/**
 * A CONLLU sentence iterator.
 * 
 * @author Daniel Couto-Vale
 */
public class ConlluSentenceIterator implements Iterator<ConlluSentence> {

	/**
	 * Split code of feature set into features.
	 * 
	 * @param setCode the code of a feature set
	 * @return the features
	 */
	private final static List<ConlluFeature> parseFeatures(String setCode) {
		List<ConlluFeature> features = new LinkedList<>();
		if (setCode.equals("_")) {
			return features;
		}
		for (String code : setCode.split("[|]")) {
			String[] tokens = code.split("=");
			ConlluFeature feature = new ConlluFeature();
			feature.systemName = tokens[0].toUpperCase();
			feature.name = tokens[1].toLowerCase();
			features.add(feature);
		}
		return features;
	}

	/**
	 * The reader.
	 */
	private final BufferedReader br;

	/**
	 * The line.
	 */
	private String line;

	/**
	 * Constructor
	 * 
	 * @param file the file
	 */
	public ConlluSentenceIterator(File file) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(-1);
			throw new Error();
		}
		InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
		br = new BufferedReader(reader);
		advance();
	}

	/**
	 * Advance to the next line.
	 */
	private final void advance() {
		if (line != null && line.length() != 0 && !line.startsWith("#")) {
			return;
		}
		while (true) {
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				line = null;
			}
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.length() != 0 & !line.startsWith("#")) {
				break;
			}
		}
	}

	@Override
	public final boolean hasNext() {
		return line != null;
	}

	@Override
	public final ConlluSentence next() {
		if (!this.hasNext())
			return null;
		ConlluSentence sentence = new ConlluSentence();
		words: while (true) {
			ConlluWord word = new ConlluWord();
			String[] tokens = line.split("\t");
			word.order = Integer.parseInt(tokens[0]);
			word.form = tokens[1];
			word.lemma = tokens[2];
			word.pos = tokens[3].toLowerCase();
			word.features = ConlluSentenceIterator.parseFeatures(tokens[5]);
			word.headOrder = Integer.parseInt(tokens[6]);
			word.functionName = getFunctionName(tokens[7]);
			word.backspaced = !tokens[9].contains("SpaceAfter=No");
			sentence.words.add(word);
			lines: while (true) {
				try {
					line = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
					break words;
				}
				if (line == null) {
					break words;
				}
				line = line.trim();
				if (line.length() == 0) {
					break words;
				}
				if (!line.startsWith("#")) {
					break lines;
				}
			}
		}
		this.advance();
		return sentence;
	}

	private String getFunctionName(String token) {
		String[] tokens = token.split(":");
		if (tokens.length < 3) {
			return StringUtils.toHyphenCamelCase(tokens);
		} else {
			return token;
		}
	}

}
