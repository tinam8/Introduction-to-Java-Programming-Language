package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class for storing and fetching the {@link EntityManagerFactory} used in
 * application.
 * 
 * @author tina
 *
 */
public class JPAEMFProvider {
	/**
	 * Instance fo {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory emf;

	/**
	 * Gets instance of {@link EntityManagerFactory}
	 * @return instance of {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	/**
	 * Sets the instance of {@link EntityManagerFactory} used.
	 * @param emf instance of {@link EntityManagerFactory} used.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}