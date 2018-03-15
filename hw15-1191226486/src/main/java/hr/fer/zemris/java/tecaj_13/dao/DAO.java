package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.forms.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface towards data persistence system.
 * 
 * @author tina
 *
 */
public interface DAO {

	/**
	 * Gets entry with given <code>id</code>. If such entry does not exist
	 * returns <code>null</code>.
	 * 
	 * @param id
	 *            entry id
	 * @return entry or <code>null</code> if entry does not exist
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Creates new blog user.
	 * 
	 * @param user
	 *            blog user
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public void createBlogUser(BlogUser user) throws DAOException;

	/**
	 * Gets user by its <code>nick</code>.
	 * 
	 * @param nick
	 *            nickname
	 * @return entry or <code>null</code> if entry does not exist
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public BlogUser getUserByNick(String nick) throws DAOException;

	/**
	 * Gets all blog users from database
	 * 
	 * @return <code>list</code> of users or <code>null</code> if there are not
	 *         any
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;

	/**
	 * Gets all entries made by the given user.
	 * 
	 * @param blogUser
	 *            blog user
	 * @return <code>list</code> of entries or <code>null</code> if there are
	 *         not any
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public List<BlogEntry> getBlogEntriesFromUser(BlogUser blogUser) throws DAOException;

	/**
	 * Creates blog entry.
	 * 
	 * @param blogEntryForm
	 *            instance of {@link BlogEntryForm}
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public void createBlogEntry(BlogEntryForm blogEntryForm) throws DAOException;

	/**
	 * Gets user with given <code>id</code>. If such entry does not exist
	 * returns <code>null</code>.
	 * 
	 * @param id
	 *            user id
	 * @return user or <code>null</code> if user does not exist
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public BlogUser getUser(Long id) throws DAOException;

	/**
	 * Gets user by its <code>nick</code>.
	 * 
	 * @param nick
	 *            nickname
	 * @return entry or <code>null</code> if entry does not exist
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */

	/**
	 * Gets comments for <code>entry</code>.
	 * 
	 * @param blogEntry
	 *            blog entry
	 * @return <code>list</code> of comments or <code>null</code> if there are
	 *         not any
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public List<BlogComment> getCommentsByEntry(BlogEntry blogEntry) throws DAOException;

	/**
	 * Creates blog comment.
	 * 
	 * @param blogCommentForm
	 *            instance of {@link BlogCommentForm}
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public void createBlogComment(BlogCommentForm blogCommentForm) throws DAOException;

}