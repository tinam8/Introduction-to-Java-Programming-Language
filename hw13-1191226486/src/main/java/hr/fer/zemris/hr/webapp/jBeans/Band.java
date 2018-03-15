package hr.fer.zemris.hr.webapp.jBeans;

/**
 * Class that represent a band. holds id value, name and youtube link.
 * @author tina
 *
 */
public class Band {
	/** Band id */
	private int id;
	/** Band name */
	private String name;
	/** Youtube link */
	private String link;
	/** Number of votes */
	private int votes;
	
	/**
	 * Constructor that sets band properties.
	 * @param id band id
	 * @param name band name
	 * @param link youtube link
	 * @param votes number of votes
	 */
	public Band(int id, String name, String link, int votes) {
		super();
		this.id = id;
		this.name = name;
		this.link = link;
		this.votes = votes;
	}
	
	/**
	 * Id getter.
	 * @return bend id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Getter for bands name.
	 * @return band name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter for youtube link
	 * @return youtube link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Getter for votes.
	 * @return number of votes.
	 */
	public int getVotes() {
		return votes;
	}
	/**
	 * Setter for votes.
	 * @param votes number of votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
