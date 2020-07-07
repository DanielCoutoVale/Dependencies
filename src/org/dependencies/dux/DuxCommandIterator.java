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
		DuxCommand command = new DuxCommand();
		String[] split = line.split("=>");
		if (split.length != 2) {
			System.err.println("Error: " + line);
			this.advance();
			return null;
		}
		String A0 = split[0].trim();
		String A1 = split[1].trim();
		if (!(A0.startsWith("[") && A0.endsWith("]"))) {
			System.err.println("Error: " + line);
			this.advance();
			return null;
		}
		A0 = A0.substring(1, A0.length() - 1);
		for (String token : A0.split(" ")) {
			token = token.trim();
			if (token.length() == 0)
				continue;
			if (!DuxFeature.matches(token))
				continue;
			command.addMatchTag(new DuxFeature(token));
		}
		for (String token : A1.split(" ")) {
			token = token.trim();
			if (token.length() == 0)
				continue;
			if (token.startsWith("+")) {
				token = token.substring(1);
				if (!DuxFeature.matches(token))
					continue;
				command.addMagisTag(new DuxFeature(token));
			}
			if (token.startsWith("-")) {
				token = token.substring(1);
				if (!DuxFeature.matches(token))
					continue;
				command.addMinusTag(new DuxFeature(token));
			}
		}
		this.advance();
		return command;
	}

}
