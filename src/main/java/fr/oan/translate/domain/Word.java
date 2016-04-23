package fr.oan.translate.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "word")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Word extends AbstractWord {

	@Id
	private String id;

	private String category;

	private String level;

	public Word() {
		super();
	}

	public String getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
