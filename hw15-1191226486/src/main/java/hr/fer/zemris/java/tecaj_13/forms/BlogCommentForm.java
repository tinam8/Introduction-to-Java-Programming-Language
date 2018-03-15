package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Class that represents form for blog comments.
 * 
 * @author tina
 *
 */
public class BlogCommentForm {
	private BlogEntry blogEntry;
	private String usersEMail = "";
	private String message = "";

	/**
	 * Map that holds messages about errors.
	 */
	private Map<String, String> errors;

	/**
	 * Constructor.
	 */
	public BlogCommentForm() {
		errors = new HashMap<>();
	}

	/**
	 * Getter for blog entry.
	 * 
	 * @return blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for blog entry.
	 * 
	 * @param blogEntry
	 *            blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for user email.
	 * 
	 * @return user email
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for user email.
	 * 
	 * @param usersEMail
	 *            user email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for comment message.
	 * 
	 * @return comment message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for comment message.
	 * 
	 * @param message
	 *            comment message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for errors.
	 * 
	 * @return map that holds error messages.
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Method that checks if inputs are valid, if not puts error messages to the
	 * errors map.
	 */
	public void validateInput() {

		if (message == null || message.equals("")) {
			errors.put("message", "Komentar ne smije biti prazan.");
			return;
		}
		if (message.length() > 4096) {
			errors.put("message", "Komentar ne smije biti duzi od 4096 znakova.");
		}
		if (usersEMail.length() > 200) {
			errors.put("email", "Email ne smije biti duzi od 100 znakova.");
		}
		if (usersEMail.length() == 0) {
			errors.put("email", "Obavezno polje.");
		}
	}

}
