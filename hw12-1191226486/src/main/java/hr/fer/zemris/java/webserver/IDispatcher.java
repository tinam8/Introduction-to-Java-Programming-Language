package hr.fer.zemris.java.webserver;

/**
 * Interface that has method that dispatches client request for correct
 * processing.
 * 
 * @author tina
 *
 */
public interface IDispatcher {
	/**
	 * Method that dispatches client request for correct further processing.
	 * 
	 * @param urlPath
	 *            path extracted from client request that indicates what service
	 *            client wants
	 * @throws Exception if error occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
