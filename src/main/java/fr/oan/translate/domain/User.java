package fr.oan.translate.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String userName;

	private String email;

	// Default password
	@JsonIgnore
	private String password = new String("password");

	@JsonIgnore
	private String token;

	@JsonIgnore
	private String salt;

	private boolean enabled;

	@JsonIgnore
	private boolean firstConnexion = true;

	/**
	 * 
	 */
	public User() {
		super();
	}

	public User(UserTransfer ut) {
		this.userName = ut.getUsername();
		this.firstName = ut.getFirstName();
		this.lastName = ut.getLastName();
		this.email = ut.getEmail();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public boolean isFirstConnexion() {
		return firstConnexion;
	}

	public void setFirstConnexion(boolean firstConnexion) {
		this.firstConnexion = firstConnexion;
	}

	public void updateUserByUserTransfer(UserTransfer ut) {
		this.userName = ut.getUsername();
		this.firstName = ut.getFirstName();
		this.lastName = ut.getLastName();
		this.email = ut.getEmail();
	}
}
