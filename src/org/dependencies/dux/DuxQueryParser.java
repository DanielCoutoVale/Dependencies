package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

public class DuxQueryParser {
	
	public List<DuxMatch> parseQuery(String query) {
		List<DuxMatch> matches = new LinkedList<>();
		String[] B = query.split("[\\[]");
		for (int i = 0; i < B.length; i++) {
			B[i] = B[i].trim();
		}
		if (B[0].length() != 0) {
			System.err.println("Error: " + query);
			return null;
		}
		for (int i = 1; i < B.length; i++) {
			String C[] = (B[i] + " ").split("]");
			if (C.length != 2) {
				System.err.println("Error2: " + query);
				return null;
			}
			String C0 = C[0].trim();
			String C1 = C[1].trim();
			DuxWord word = new DuxWord();
			for (String token : C0.split(" ")) {
				token = token.trim();
				if (token.length() == 0)
					continue;
				if (!DuxFeature.matches("H:%:" + token))
					continue;
				word.addMatchTag(new DuxFeature("H:%:" + token));
			}
			matches.add(word);
			for (String token : C1.split(" ")) {
				if (token.length() == 0)
					continue;
				if (!DuxFunction.matches(token))
					continue;
				matches.add(new DuxFunction(token));
			}
		}
		return matches;
	}

}
