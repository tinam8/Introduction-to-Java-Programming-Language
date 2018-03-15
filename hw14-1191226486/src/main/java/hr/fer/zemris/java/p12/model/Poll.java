package hr.fer.zemris.java.p12.model;

/**
 * Entry for the table {@code Polls}. hold data that represent one poll.
 * 
 * @author tina
 *
 */
public class Poll {
	/**
	 * Poll id
	 */
	private long id;
	/**
	 * Title of the poll
	 */
	private String title;
	/**
	 * Message that describes poll
	 */
	private String message;

	/**
	 * Getter for poll id
	 * 
	 * @return poll id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for poll id
	 * 
	 * @param id
	 *            poll id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for poll title
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for poll title
	 * 
	 * @param title
	 *            poll title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for message that describes poll
	 * 
	 * @return message that describes poll
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message that describes poll
	 * 
	 * @param message
	 *            message that describes poll
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
