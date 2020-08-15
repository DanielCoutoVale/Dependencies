package org.dependencies.dux;

import java.util.LinkedList;
import java.util.List;

public class DuxQueryParser {
	
	public List<DuxPattern> parseQuery(String query) {
		List<DuxPattern> patterns = new LinkedList<>();
		String[] B = query.split("[\\[]");
		for (int i = 0; i < B.length; i++) {
			B[i] = B[i].trim();
		}
		if (B[0].length() != 0) {
			System.err.println("Error: " + query);
			System.exit(-1);
			return null;
		}
		for (int i = 1; i < B.length; i++) {
			String C[] = (B[i] + " ").split("]");
			if (C.length != 2) {
				System.err.println("Error2: " + query);
				System.exit(-1);
				return null;
			}
			String C0 = C[0].trim();
			String C1 = C[1].trim();
			DuxFeaturedWord word = new DuxFeaturedWord();
			for (String token : C0.split(" ")) {
				token = token.trim();
				if (token.length() == 0)
					continue;
				if (!DuxFeature.matches(token))
					continue;
				word.addFeature(new DuxFeature(token));
			}
			patterns.add(word);
			for (String token : C1.split(" ")) {
				if (token.length() == 0)
					continue;
				if (!DuxFunction.matches(token))
					continue;
				patterns.add(new DuxFunction(token));
			}
		}
		return patterns;
	}

}
