package hr.fer.zemris.java.p12.dao;

import java.sql.Connection;
import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Interface towards data persistence system.
 * 
 * @author tina
 *
 */
public interface DAO {

	/**
	 * Adds new row to Polls table in the database.
	 * @param connection connection with the database
	 * 
	 * @param title poll title
	 * @param message poll message
	 * @return id of created poll
	 * @throws DAOException if error occurs
	 */
	public Long addPoll(Connection connection, String title, String message) throws DAOException;

	/**
	 * Adds new row to PollOPtions table in the database.
	 * @param connection connection with the database
	 * @param optionTitle title of poll option
	 * @param optionLink link that describes poll option
	 * @param pollID id of poll that option refer to 
	 * @param votes number of votes that option has
	 * @return id of created poll option
	 * @throws DAOException if error occurs
	 */
	public Long addPollOption(Connection connection, String optionTitle, String optionLink, long pollID, int votes) throws DAOException;

	/**
	 * Gets all existing polls from database.
	 * 
	 * @return list of {@link Poll}
	 * @throws DAOException
	 *             if error occurs
	 */
	public List<Poll> getPollList() throws DAOException;

	/**
	 * Gets all existing poll options from database based on given poll id..
	 * 
	 * @param id
	 *            id of poll that oprions refer to
	 * @return list of {@link PollOption}
	 * @throws DAOException
	 *             if error occurs
	 */
	List<PollOption> getPollOptions(long id) throws DAOException;

	/**
	 * Method that increments number of votes for pool option based on given id
	 * 
	 * @param id
	 *            poll option id
	 * @throws DAOException
	 *             if error occurs
	 */
	public void updateVotes(long id) throws DAOException;

	/**
	 * Method that returns poll from database based on given id.
	 * @param pollID id of poll
	 * @return instance of {@link Poll}
	 * @throws DAOException if error occurs;
	 */
	public Poll getPoll(Long pollID)  throws DAOException;

	
}