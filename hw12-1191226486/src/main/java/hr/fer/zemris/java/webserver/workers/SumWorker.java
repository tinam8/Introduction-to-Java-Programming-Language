package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that accepts two parameters: a and b and treat their values as integers. If
 * user passed something that is not integer, or if user did not send any of
 * them, default value of 1 for a and 2 for b are used. This worker will
 * calculate their sum, convert it into string, and place it as temporary
 * parameter “zbroj” into RequestContext. It will also create a temporary
 * parameter a and b with values that were actually used for calculation. Then
 * it will call the dispatcher and ask it to delegate the further processing to
 * the “/private/calc.smscr”.
 * 
 * @author tina
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a = getNumber("a", context);
		Integer b = getNumber("b", context);

		if (a == null) {
			a = 1;
		}
		if (b == null) {
			b = 2;
		}
		Integer sum = a + b;
		context.setTemporaryParameter("zbroj", sum.toString());
		context.setTemporaryParameter("a", a.toString());
		context.setTemporaryParameter("b", b.toString());
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

	/**
	 * Supplementary method that gets parameter as number for given name from
	 * request contexts parameters map.
	 * 
	 * @param name
	 *            name of parameter whose value is wanted
	 * @param context
	 *            instance of {@link RequestContext}
	 * @return number, if it can not be parsed as number than null
	 */
	private Integer getNumber(String name, RequestContext context) {
		Integer number;
		try {
			number = Integer.parseInt(context.getParameter(name));
		} catch (Exception e) {
			number = null;
		}
		return number;
	}

}
