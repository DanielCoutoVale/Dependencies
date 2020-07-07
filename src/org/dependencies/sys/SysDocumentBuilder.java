package org.dependencies.sys;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.dependencies.model.DepFeature;
import org.dependencies.model.DepSystem;

/**
 * A SYS document builder.
 * 
 * @author Daniel Couto-Vale
 */
public class SysDocumentBuilder {

	/**
	 * Parse the content of the given file as a SYS document and return a new SYS
	 * document object.
	 * 
	 * @param file the SYS document file
	 * @return the SYS document
	 * @throws IOException if the file is not found
	 */
	public final SysDocument parse(File file) throws IOException {
		SysDocument document = new SysDocument();
		FileInputStream stream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(reader);
		String line;
		Integer lineCount = 0;
		int state = 0;
		DepSystem system = null;
		while (null != (line = br.readLine())) {
			line = line.trim();
			lineCount++;
			if (line.startsWith("//")) {
				continue;
			}
			switch (state) {
			case 0:
				if (line.startsWith("system ")) {
					system = new DepSystem();
					system.setName(line.substring(7).trim());
					document.addSystem(system);
					state = 1;
					continue;
				} else if (line.length() != 0) {
					System.err.println("Error: line " + lineCount);
					br.close();
					return document;
				}
			case 1:
				if (line.startsWith("> ")) {
					system.setEntryConditionForm(line.substring(2).trim());
					state = 2;
					continue;
				} else {
					System.err.println("Error: line " + lineCount);
					br.close();
					return document;
				}
			case 2:
				if (line.startsWith("- ")) {
					DepFeature feature = new DepFeature();
					feature.setName(line.substring(2).trim());
					system.addFeature(feature);
					state = 2;
					continue;
				} else {
					state = 0;
					continue;
				}
			}
		}
		br.close();
		return document;
	}

}
