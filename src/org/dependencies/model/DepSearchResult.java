package org.dependencies.model;

import java.util.List;

public class DepSearchResult {
	
	private DepDescription description;
	
	private List<DepWording> wordings;
	
	public DepSearchResult() {
	}

	public DepDescription getDescription() {
		return description;
	}

	public void setDescription(DepDescription description) {
		this.description = description;
	}

	public List<DepWording> getWordings() {
		return wordings;
	}

	public void setWordings(List<DepWording> wordings) {
		this.wordings = wordings;
	}

}
