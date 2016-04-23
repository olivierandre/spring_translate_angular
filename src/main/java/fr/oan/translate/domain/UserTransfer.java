package fr.oan.translate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserTransfer {

	private String id;
	private String token;
	@JsonIgnore
	private String tokenAttribut;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private boolean firstConnection;
	private boolean enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenAttribut() {
		return tokenAttribut;
	}

	public void setTokenAttribut(String tokenAttribut) {
		this.tokenAttribut = tokenAttribut;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isFirstConnection() {
		return firstConnection;
	}

	public void setFirstConnection(boolean firstConnection) {
		this.firstConnection = firstConnection;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
