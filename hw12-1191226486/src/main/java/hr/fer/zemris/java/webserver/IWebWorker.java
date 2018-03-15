package hr.fer.zemris.java.webserver;

/**
 * Interface toward any object that can process current request: it gets
 * RequestContext as parameter and it is expected to create a content for
 * client.
 * 
 * @author tina
 *
 */
public interface IWebWorker {
	/**
	 * Method that processes request and creates content for the client.
	 * @param context instance of {@link RequestContext}
	 * @throws Exception if error occurs while processing
	 */
	public void processRequest(RequestContext context) throws Exception;
}
