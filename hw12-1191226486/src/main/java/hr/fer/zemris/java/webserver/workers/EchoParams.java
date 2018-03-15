package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that outputs back to user parameters it obtained in a HTML table
 * 
 * @author tina
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		try {
			context.write("<html><body>");
			context.write("<h1>Params</h1>");
			context.write("<table>");
			context.write("<tr><th>Name</th><th>Value</th></tr>\r\n");
			for (Map.Entry<String, String> entry : context.getParameters().entrySet()) {
				context.write("<tr>");
			    context.write("<td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
			}
			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException ex) {
			// Log exception to servers log...
			ex.printStackTrace();
		}
	}

}
