package hr.fer.zemris.java.p12.model;

/**
 * Entry for the table {@code PollOptions}. Holds data that represent one option
 * of poll.
 * 
 * @author tina
 *
 */
public class PollOption {

	/**
	 * Id of poll option
	 */
	private Long id;
	/**
	 * Poll title
	 */
	private String optionTitle;
	/**
	 * Link to video
	 */
	private String optionLink;
	/**
	 * Id of poll that this option belongs to
	 */
	private long pollID;
	/**
	 * Number of votes
	 */
	private int votes;

	/**
	 * Getter for id
	 * 
	 * @return poll option id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for option id
	 * 
	 * @param id
	 *            if of poll options
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for options title
	 * 
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for option title
	 * 
	 * @param optionTitle
	 *            title of poll option
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for option link
	 * 
	 * @return link that describes option
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for option link
	 * 
	 * @param optionLink
	 *            link that describes option
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Getter for poll id
	 * 
	 * @return id of poll that option belongs to
	 */
	public Long getPollID() {
		return pollID;
	}

	/**
	 * Setter for poll id
	 * 
	 * @param pollID
	 *            id of poll that option belongs to
	 */
	public void setPollID(Long pollID) {
		this.pollID = pollID;
	}
	
	/**
	 * Getter for number of votes
	 * @return number of votes
	 */
	public int getVotes() {
		return votes;
	}
	
	/**
	 * Setter for options number of votes
	 * @param votes numebr of votes
	 */ 
	public void setVotes(int votes) {
		this.votes = votes;
	}
}
