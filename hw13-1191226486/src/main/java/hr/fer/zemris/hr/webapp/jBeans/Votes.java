package hr.fer.zemris.hr.webapp.jBeans;

/**
 * Class that represents one band votes.
 * @author tina
 *
 */
public class Votes {
	/** Band id */
	private int id;
	/** Number of votes */
	private int votes;
	
	/**
	 * Constructor
	 * @param id band id
	 * @param votes number of votes
	 */
	public Votes(int id, int votes) {
		super();
		this.id = id;
		this.votes = votes;
	}
	
	/**
	 * 
	 * Id getter
	 * @return band id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Votes getter.
	 * @return numebr of votes
	 */
	public int getVotes() {
		return votes;
	}
}
