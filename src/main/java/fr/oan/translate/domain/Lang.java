package fr.oan.translate.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "lang")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Lang extends AbstractWord {

	@Id
	private String id;

	private String code;

	// private String label;

	private String country;

	/**
	 * 
	 */
	public Lang() {
		super();
	}

	/**
	 * @param code
	 * @param label
	 * @param country
	 */
	public Lang(String code, String country) {
		super();
		this.code = code;
		// this.label = label;
		this.country = country;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
