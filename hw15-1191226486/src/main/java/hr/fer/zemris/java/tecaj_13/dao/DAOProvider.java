package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Class used for getting the instance of implementation of {@link DAO} which
 * will be used in application.
 * 
 * @author tina
 *
 */
public class DAOProvider {
	/**
	 * Instance of {@link DAO}
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Getter for dao intance.
	 * @return instance of {@link DAO} used in application.
	 */
	public static DAO getDAO() {
		return dao;
	}

}