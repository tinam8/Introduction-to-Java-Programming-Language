package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that holds comment attributes that s to the ones in database table.
 * 
 * @author tina
 *
 */
@NamedQueries({
	@NamedQuery(name = "BlogComment.commentList", query = "select c from BlogComment as c where c.blogEntry=:be") })

@Entity
@Table(name = "blog_comments")
public class BlogComment {
	/**
	 * Comment id
	 */
	private Long id;
	/**
	 * Blog entry that comment refers to
	 */
	private BlogEntry blogEntry;
	/**
	 * Mail of the user who left comment.
	 */
	private String usersEMail;
	/**
	 * Message that the user wrote.
	 */
	private String message;
	/**
	 * Date on which the comment was posted.
	 */
	private Date postedOn;

	/**
	 * Getter for id.
	 * 
	 * @return comment id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * setter for id.
	 * 
	 * @param id
	 *            comment id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for the blog entry that comment refers to.
	 * 
	 * @return blog entry that comment refers to
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Setter for the blog entry that comment refers to.
	 * 
	 * @param blogEntry
	 *            blog entry that comment refers to
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter for the user email.
	 * @return user email
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter for the user email.
	 * @param usersEMail user email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter for comment massage.
	 * 
	 * @return comment massage
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for comment massage.
	 * 
	 * @param message
	 *            comment massage
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the date that comment was posted on.
	 * @return the date that comment was posted on
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter for the date that comment was posted on.
	 * @param postedOn the date that comment was posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}