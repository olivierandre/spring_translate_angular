package fr.oan.translate.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "category")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends AbstractWord {

	@Id
	private String id;

	public String getId() {
		return id;
	}

}
