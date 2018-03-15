package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Class that represents blog user form.
 * 
 * @author tina
 *
 */
public class BlogUserForm {

	/**
	 * User's first name.
	 */
	private String firstName = "";
	/**
	 * User's last name.
	 */
	private String lastName = "";
	/**
	 * User's nickname.
	 */
	private String nick = "";
	/**
	 * User's email.
	 */
	private String email = "";
	/**
	 * User's hashed password.
	 */
	private String password = "";
	/**
	 * Map that holds messages about errors.
	 */
	private Map<String, String> errors;

	/**
	 * Regular expression for names.
	 */
	private static final String NAME_REGEX = "^[\\p{L} .'-]+$";
	/**
	 * Message shown to the client if name is invalid.
	 */
	private static final String NAME_MSG = "Samo slova i praznine";
	/**
	 * Regular expression for nicknames.
	 */
	private static final String NICK_REGEX = "[a-zA-Z0-9.\\-_]{3,}";
	/**
	 * Message shown to the nick name if name is invalid.
	 */
	private static final String NICK_MSG = "Dopusteni znakovi su: '.', '_', '-'.";
	/**
	 * Regular expression for password.
	 */
	private static final String PASSWORD_REGEX = "[^\\s]+";
	/**
	 * Message shown to the client if password is invalid.
	 */
	private static final String PASSWORD_MSG = "Nisu dopustene praznine.";

	/**
	 * Constructor.
	 */
	public BlogUserForm() {
		errors = new HashMap<>();
	}

	/**
	 * Getter for user's first name.
	 * 
	 * @return user's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for user's first name.
	 * 
	 * @param firstName
	 *            user's first name.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for user's last name.
	 * 
	 * @return user's last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 *            user's last name.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for user's nickname.
	 * 
	 * @return user's nickname.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for user's nickname.
	 * 
	 * @param nick
	 *            user's nickname.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for user's email.
	 * 
	 * @return user's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for user's email.
	 * 
	 * @param email
	 *            user's email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for user's password.
	 * 
	 * @return user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for user's password.
	 * 
	 * @param password
	 *            user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for errors.
	 * 
	 * @return map that holds error messages.
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Method that checks if form inputs are valid. If they are not errors map
	 * is populates with the appropriate messages.
	 */
	public void validateInput() {
		validate(firstName, "firstName", 3, 30, NAME_REGEX, NAME_MSG);
		validate(lastName, "lastName", 3, 30, NAME_REGEX, NAME_MSG);
		validate(nick, "nick", 5, 50, NICK_REGEX, NICK_MSG);
		validate(password, "password", 8, 30, PASSWORD_REGEX, PASSWORD_MSG);
		validateMail();
		validateNickExistance();
	}

	/**
	 * Method that checks if user with the same nickname exists.
	 */
	private void validateNickExistance() {
		if (nick.equals("")) {
			return;
		}

		if (DAOProvider.getDAO().getUserByNick(nick) != null) {
			errors.put("nick", "Korisnik sa tim nadimkom vec postoji.");
		}
	}

	/**
	 * Method that validate form inputs.
	 * 
	 * @param input
	 *            input given in form
	 * @param propertyName
	 *            name of property
	 * @param minLen
	 *            minimal length of input
	 * @param maxLen
	 *            maximal length of input
	 * @param Reg
	 *            regular expression that input should match
	 * @param regMsg
	 *            error message
	 */
	private void validate(String input, String propertyName, int minLen, int maxLen, String Reg, String regMsg) {
		if (input == null || input.equals("")) {
			errors.put(propertyName, "Ne moze biti prazno");
			return;
		}

		if (input.length() > 30 || input.length() < 3) {
			errors.put(propertyName, "Duljina mora biti barem" + minLen + " a najvise" + maxLen + " .");
			return;
		}

		if (!Pattern.matches(Reg, input)) {
			errors.put(propertyName, regMsg);
			return;
		}
	}

	/**
	 * Validates mail.
	 */
	private void validateMail() {
		if (email == null || email.equals("")) {
			errors.put("email", "Ne moze biti prazno");
			return;
		}
	}

}
