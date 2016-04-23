package fr.oan.translate.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "level")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level {

	@Id
	private String id;

	private Map<String, String> labels;

	private Integer order;

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

}
