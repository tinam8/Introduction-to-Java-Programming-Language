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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class that represent one blog user.
 * 
 * @author tina
 *
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.userList",query="select u from BlogUser as u",
			hints=@QueryHint(name = "org.hibernate.cacheable", value = "true")),
	@NamedQuery(name="BlogUser.byNick",query="select u from BlogUser as u where u.nick=:nick",
		hints=@QueryHint(name = "org.hibernate.cacheable", value = "true")),
})


@Entity
@Table(name = "blog_users")
@Cacheable(true)
public class BlogUser {
	/**
	 * User's id.
	 */
	private Long id;
	/**
	 * User's first name.
	 */
	private String firstName;
	/**
	 * User's last name.
	 */
	private String lastName;
	/**
	 * User's nickname.
	 */
	private String nick;
	/**
	 * User's email.
	 */
	private String email;
	/**
	 * User's hashed password.
	 */
	private String passwordHash;
	/**
	 * Date on which user was created.
	 */
	private Date createdAt;
	/**
	 * Date on which user was last modified.
	 */
	private Date lastModifiedAt;
	/**
	 * Blog entries that user created.
	 */
	private List<BlogEntry> entries = new ArrayList<>();
	
	
	/**
	 * Getter for id.
	 * @return blog entry id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * @param id blog entry id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter for entries.
	 * @return entries made by blog user
	 */ 
	@OneToMany(mappedBy = "blogUser", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for the entries.
	 * @param entries made by blog user
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
	
	/**
	 * Getter for the first name.
	 * @return first name of the user
	 */
	@Column(length = 30, nullable = false)
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Getter for the first name.
	 * @param firstName first name of the user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for the last name.
	 * @return last name of the user
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for the last name
	 * @param lastName last name of the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter for the nickname.
	 * @return nickname of the user
	 */
	@Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}
	
	/**
	 * Setter for the nickname.
	 * @param nick nickname of the user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for the user email.
	 * @return user email
	 */
	@Column(length = 50, nullable = false)
	public String getEmail() {
		return email;
	}
	
	/**
	 * Setter of the user's email.
	 * @param email user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Getter for the hashed password.
	 * @return hashed password
	 */
	@Column(nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * Setter for the hashed password.
	 * @param passwordHash hashed password
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	
	/**
	 * Getter for date of creation of blog entry.
	 * @return date of creation.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter for date of creation.
	 * @param createdAt date when entry was created.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter for date on which entry was last modified on.
	 * @return date on which entry was last modified on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter for date on which entry was last modified on.
	 * @param lastModifiedAt date on which entry was last modified on
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
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
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
