package org.dependencies.dux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * A DUX command iterator
 * 
 * @author Daniel Couto-Vale
 */
public class DuxCommandIterator implements Iterator<DuxCommand> {

	/**
	 * The buffered reader
	 */
	private BufferedReader br;

	/**
	 * The next line
	 */
	private String line;

	/**
	 * Constructor
	 * 
	 * @param file the file
	 */
	public DuxCommandIterator(File file) {
		FileInputStream stream;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
		br = new BufferedReader(reader);
		this.advance();
	}

	/**
	 * Advance to the next
	 */
	private final void advance() {
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
	public final DuxCommand next() {
		if (!this.hasNext())
			return null;
		if (line.equals("STOP")) {
			this.advance();
			return new DuxStop();
		}
		DuxTranslate translate = new DuxTranslate();
		String[] A = line.split("=>");
		if (A.length != 2) {
			System.err.println("Error: " + line);
			this.advance();
			return null;
		}
		String A0 = A[0].trim();
		String A1 = A[1].trim();
		String[] B = A0.split("[\\[]");
		for (int i = 0; i < B.length; i++) {
			B[i] = B[i].trim();
		}
		if (B[0].length() != 0) {
			System.err.println("Error: " + line);
			this.advance();
			return null;
		}
		for (int i = 1; i < B.length; i++) {
			String C[] = (B[i] + " ").split("]");
			if (C.length != 2) {
				System.err.println("Error2: " + line);
				this.advance();
				return null;
			}
			String C0 = C[0].trim();
			String C1 = C[1].trim();
			DuxWord word = new DuxWord();
			for (String token : C0.split(" ")) {
				token = token.trim();
				if (token.length() == 0)
					continue;
				if (!DuxFeature.matches(token))
					continue;
				word.addMatchTag(new DuxFeature(token));
			}
			translate.addMatch(word);
			for (String token : C1.split(" ")) {
				if (token.length() == 0)
					continue;
				if (!DuxFunction.matches(token))
					continue;
				translate.addMatch(new DuxFunction(token));
			}
		}
		for (String token : A1.split(" ")) {
			token = token.trim();
			if (token.length() == 0)
				continue;
			if (token.startsWith("+")) {
				token = token.substring(1);
				if (DuxFeature.matches(token)) {
					translate.addMagisTag(new DuxFeature(token));
				}
				if (DuxFunction.matches(token)) {
					translate.addMagisTag(new DuxFunction(token));
				}
			}
			if (token.startsWith("-")) {
				token = token.substring(1);
				if (DuxFeature.matches(token)) {
					translate.addMinusTag(new DuxFeature(token));
				}
				if (DuxFunction.matches(token)) {
					translate.addMinusTag(new DuxFunction(token));
				}
			}
		}
		this.advance();
		return translate;
	}

}
