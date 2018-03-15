package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.forms.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO system using JPA system.
 * 
 * @author tina
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void createBlogUser(BlogUser user) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

	@Override
	public BlogUser getUserByNick(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		 List<BlogUser> blogUsers = (List<BlogUser>)
		 em.createNamedQuery("BlogUser.byNick").setParameter("nick", nick)
		 .getResultList();

		if (blogUsers == null || blogUsers.size() == 0) {
			return null;
		}

		return blogUsers.get(0);
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogUser> blogUsers = (List<BlogUser>) em.createNamedQuery("BlogUser.userList").getResultList();

		return blogUsers;
	}

	@Override
	public List<BlogEntry> getBlogEntriesFromUser(BlogUser blogUser) {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogEntry> blogEntries = (List<BlogEntry>) em.createNamedQuery("BlogEntry.entryList")
				.setParameter("bu", blogUser).getResultList();

		return blogEntries;
	}

	@Override
	public void createBlogEntry(BlogEntryForm blogEntryForm) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser blogUser = em.find(BlogUser.class, blogEntryForm.getBlogUserID());

		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setBlogUser(blogUser);
		blogEntry.setTitle(blogEntryForm.getTitle());
		blogEntry.setText(blogEntryForm.getText());
		blogEntry.setCreatedAt(new Date());
		blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());

		em.persist(blogEntry);
	}

	@Override
	public BlogUser getUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return blogUser;
	}

	@Override
	public List<BlogComment> getCommentsByEntry(BlogEntry blogEntry) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogComment> comments = (List<BlogComment>) em.createNamedQuery("BlogComment.commentList")
				.setParameter("be", blogEntry).getResultList();
		for (BlogComment bc : comments) {
			System.out.println("Blog [" + bc.getBlogEntry().getTitle() + "] ima komentar: [" + bc.getMessage() + "]");
		}

		return comments;
	}

	@Override
	public void createBlogComment(BlogCommentForm blogCommentForm) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		BlogComment blogComment = new BlogComment();
		blogComment.setBlogEntry(blogCommentForm.getBlogEntry());
		blogComment.setUsersEMail(blogCommentForm.getUsersEMail());
		blogComment.setMessage(blogCommentForm.getMessage());
		blogComment.setPostedOn(new Date());

		em.persist(blogComment);
	}
}