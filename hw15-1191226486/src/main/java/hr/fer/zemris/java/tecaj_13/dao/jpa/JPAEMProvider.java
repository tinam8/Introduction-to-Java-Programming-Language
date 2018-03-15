package hr.fer.zemris.java.tecaj_13.dao.jpa;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import javax.persistence.EntityManager;

/**
 * 
 * Stores connections to the database in ThreadLocal object.
 * 
 * @author tina
 *
 */
public class JPAEMProvider {
	/**
	 * Static instance of {@link ThreadLocal}, works with instances of
	 * {@link LocalData}.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * @return instance of {@link EntityManager}.
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Closes the communication with {@link EntityManager}.
	 * 
	 * @throws DAOException
	 *             if error occurs while getting data from database
	 */
	public static void close() throws DAOException {
		LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

	/**
	 * Wrapper of {@link EntityManager}.
	 * 
	 * @author tina
	 *
	 */
	private static class LocalData {
		/**
		 * Instance of {@link EntityManager}
		 */
		EntityManager em;
	}

}