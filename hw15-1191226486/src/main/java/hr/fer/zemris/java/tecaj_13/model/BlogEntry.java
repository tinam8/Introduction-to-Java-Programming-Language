package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represents one blog entry.
 * 
 * @author tina
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.entryList", query = "select e from BlogEntry as e where e.blogUser=:bu") })

@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {
	/**
	 * Blog entry id.
	 */
	private Long id;
	/**
	 * Comments that refer to the blog entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Date on which blog entry was created.
	 */
	private Date createdAt;
	/**
	 * Date on which blog entry was last modified.
	 */
	private Date lastModifiedAt;
	/**
	 * Title of the blog entry.
	 */
	private String title;
	/**
	 * Text of the blog entry.
	 */
	private String text;
	/**
	 * Blog user that created entry.
	 */
	private BlogUser blogUser;

	/**
	 * Getter for id.
	 * 
	 * @return blog entry id
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id
	 *            blog entry id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for comments.
	 * 
	 * @return comments that refer to the blog entry
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Setter for the comments.
	 * 
	 * @param comments
	 *            list of comments that refer to the blog entry.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter for date of creation of blog entry.
	 * 
	 * @return date of creation.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for date of creation.
	 * 
	 * @param createdAt
	 *            date when entry was created.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for date on which entry was last modified on.
	 * 
	 * @return date on which entry was last modified on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for date on which entry was last modified on.
	 * 
	 * @param lastModifiedAt
	 *            date on which entry was last modified on
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter for title.
	 * 
	 * @return title of the blog entry
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for the blog entry title.
	 * 
	 * @param title
	 *            blog entry title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for the text of blog entry.
	 * 
	 * @return text of blog entry
	 */
	@Column(nullable = false, length = 4096)
	public String getText() {
		return text;
	}

	/**
	 * Setter for the text of blog entry.
	 * 
	 * @param text
	 *            text of blog entry
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter for blog user.
	 * 
	 * @return blog user that created entry
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getBlogUser() {
		return blogUser;
	}

	/**
	 * Setter for blog user.
	 * 
	 * @param blogUser
	 *            blog user that created entry
	 */
	public void setBlogUser(BlogUser blogUser) {
		this.blogUser = blogUser;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}