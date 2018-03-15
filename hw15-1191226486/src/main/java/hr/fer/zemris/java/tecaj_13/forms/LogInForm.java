package hr.fer.zemris.java.tecaj_13.forms;

import javax.persistence.Column;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserUtil;

/**
 * CLass that represents login form for the user.
 * 
 * @author tina
 *
 */
public class LogInForm {

	/**
	 * User's password.
	 */
	private String password = "";

	/**
	 * User's nickname.
	 */
	private String nick = "";
	

	/**
	 * Getter for the nickname.
	 * @return nickname of the user
	 */
	public String getNick() {
		return nick;
	}
	
	
	/**
	 * Getter for the hashed password.
	 * @return hashed password
	 */
	@Column(length = 30, nullable = false)
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter for the hashed password.
	 * @param password hashed password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Setter for the nickname.
	 * @param nick nickname of the user
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Method that finds user.
	 * @return user if it exists in database otherwise null.
	 */
	public BlogUser findUser() {
		BlogUser blogUser = DAOProvider.getDAO().getUserByNick(nick);
		if (blogUser == null) {
			return null;
		}
		
		if (!BlogUserUtil.checkPassword(password, blogUser.getPasswordHash())) {
			return null;
		}
		
		return blogUser;
	}

	
}
