package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents blog entry form that has title and text input.
 * @author tina
 *
 */
public class BlogEntryForm {
	/**
	 * Blog entry id.
	 */
	private Long id;
	/**
	 * Title of the blog entry.
	 */
	private String title = "";
	/**
	 * Text of the blog entry.
	 */
	private String text = "";
	/**
	 * Blog user's id.
	 */
	private Long blogUserID;
	/**
	 * Map that holds messages about errors.
	 */
	private Map<String, String> errors;
	
	/**
	 * Constructor.
	 */
	public BlogEntryForm() {
		errors = new HashMap<>();
	}
	
	/**
	 * Getter for blog entry id.
	 * @return blog entry id.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Setter for blog entry id.
	 * @param id blog entry id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Getter for entry's title.
	 * @return Getter for entry's title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter for entry's title.
	 * @param title entry's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for entry's text.
	 * @return Getter for entry's text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Setter for entry's text.
	 * @param text entry's text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Getter blog user's id.
	 * @return  blog user's id.
	 */
	public Long getBlogUserID() {
		return blogUserID;
	}
	
	/**
	 * Setter blog user's id.
	 * @param blogUserID blog user's id.
	 */
	public void setBlogUserID(Long blogUserID) {
		this.blogUserID = blogUserID;
	}
	
	/**
	 * Getter for errors.
	 * @return map that holds error messages.
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Method that checks if form inputs are valid.
	 * If they are not errors map is populates with the appropriate messages.
	 */
	public void validateInput() {
		validateProperty("Naslov", title, 200);
		validateProperty("Tekst", text, 4096);
	}
	
	/**
	 * Validates form property
	 * 
	 * @param properyName
	 *            name of the property
	 * @param input
	 *            value of property
	 * @param maxLen
	 *            maximal length
	 */
	private void validateProperty(String properyName, String input, int maxLen) {
		if (input == null || input.equals("")) {
			errors.put(properyName, properyName + " ne smije biti prazan.");
			return;
		}
		if (input.length() > maxLen) {
			errors.put(properyName, properyName + " ne smije biti duzi od 200 znakova.");
		}
	}
	
}
