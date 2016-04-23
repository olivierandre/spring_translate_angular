package fr.oan.translate.domain;

import java.util.HashMap;
import java.util.Map;

public class AbstractWord {

	protected Map<String, String> labels = new HashMap<>();

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public void setLabels(String codeLabel1, String label1, String codeLabel2, String label2) {
		this.labels.put(codeLabel1, label1);
		this.labels.put(codeLabel2, label2);
	}
}
