package hr.fer.zemris.java.custom.scripting.elems.functions;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Function;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that contains implementation of functions. Each one of them can be
 * accessed by a name. <br>
 * 
 * @author tina
 *
 */
public class Functions {
	/** Map that holds function names and implementation */
	private Map<String, IFunction> namesMap;

	/**
	 * Constructor that adds function to the map.
	 */
	public Functions() {
		namesMap = new HashMap<>();

		namesMap.put("sin", SIN);
		namesMap.put("decfmt", DECFMT);
		namesMap.put("dup", DUP);
		namesMap.put("swap", SWAP);
		namesMap.put("setMimeType", SET_MIME_TYPE);
		namesMap.put("paramGet", PARAM_GET);
		namesMap.put("pparamGet", PPARAM_GET);
		namesMap.put("pparamSet", PPARAM_SET);
		namesMap.put("pparamDel", PPARAM_DEL);
		namesMap.put("tparamGet", TPARAM_GET);
		namesMap.put("tparamSet", TPARAM_SET);
		namesMap.put("tparamDel", TPARAM_DEL);
	}

	/** Function that calculates sin of a number. */
	public static final IFunction SIN = (stack, requestContext) -> {
		double argument = Double.valueOf(stack.pop().toString());
		stack.push(Math.sin(Math.toRadians(argument)));
	};
	/**
	 * Function that formats decimal number using given format f which is
	 * compatible with DecimalFormat.
	 */
	public static final IFunction DECFMT = (stack, requestContext) -> {
		String f = stack.pop().toString();
		DecimalFormat decfmt = new DecimalFormat(f);
		Object x = stack.pop();

		stack.push(decfmt.format(x));
	};
	/** Function that duplicates last value in the stack */
	public static final IFunction DUP = (stack, requestContext) -> {
		stack.push(stack.peek());
	};
	/** Function that swaps two last values in the stack. */
	public static final IFunction SWAP = (stack, requestContext) -> {
		Object a = stack.pop();
		Object b = stack.pop();
		stack.push(b);
		stack.push(a);
	};
	/** Function that sets mime type of instance of {@link RequestContext} */
	public static final IFunction SET_MIME_TYPE = (stack, requestContext) -> {
		requestContext.setMimeType(stack.pop().toString());
	};
	/**
	 * Function that gets parameter by the name from the {@link RequestContext}
	 * parameters.
	 */
	public static final IFunction PARAM_GET = (stack, requestContext) -> {
		getParam(stack, name -> requestContext.getParameter(name));
	};
	/**
	 * Function that gets persistent parameter by the name from the
	 * {@link RequestContext} persistent parameters.
	 */
	public static final IFunction PPARAM_GET = (stack, requestContext) -> {
		getParam(stack, name -> requestContext.getPersistentParameter(name));
	};
	/**
	 * Function that sets parameter by the name from the {@link RequestContext}
	 * persistent parameters.
	 */
	public static final IFunction PPARAM_SET = (stack, requestContext) -> {
		setParam(stack, (name, value) -> requestContext.setPersistentParameter(name, value));
	};
	/**
	 * Function that deletes parameter by the name from the
	 * {@link RequestContext} persistent parameters.
	 */
	public static final IFunction PPARAM_DEL = (stack, requestContext) -> {
		requestContext.removePersistentParameter(stack.pop().toString());
	};
	/**
	 * Function that gets temporary parameter by the name from the
	 * {@link RequestContext} temporary parameters.
	 */
	public static final IFunction TPARAM_GET = (stack, requestContext) -> {
		getParam(stack, name -> requestContext.getTemporaryParameter(name));
	};
	/**
	 * Function that sets temporary parameter by the name from the
	 * {@link RequestContext} temporary parameters.
	 */
	public static final IFunction TPARAM_SET = (stack, requestContext) -> {
		setParam(stack, (name, value) -> requestContext.setTemporaryParameter(name, value));
	};
	/**
	 * Function that deletes temporary parameter by the name from the
	 * {@link RequestContext} temporary parameters.
	 */
	public static final IFunction TPARAM_DEL = (stack, requestContext) -> {
		requestContext.removeTemporaryParameter(stack.pop().toString());
	};

	/**
	 * Supplementary method that applies function for values mapped for name and
	 * result pushes onto stack. If there is no mapping for value default values
	 * is used.
	 * 
	 * @param stack
	 *            stack that holds value and default value
	 * @param function
	 *            function that
	 */
	private static void getParam(Stack<Object> stack, Function<String, String> function) {
		Object dv = stack.pop();
		Object name = stack.pop();
		String value = function.apply(name.toString());
		stack.push(value == null ? dv : value);
	}

	/**
	 * Supplementary method that applies function for value and name from stack
	 * and applies biconsumer.
	 * 
	 * @param stack
	 *            stack that holds value and name
	 * @param biConsumer
	 *            instance of {@link BiConsumer}
	 */
	private static void setParam(Stack<Object> stack, BiConsumer<String, String> biConsumer) {
		Object name = stack.pop();
		Object value = stack.pop();
		biConsumer.accept(name.toString(), value.toString());
	}

	/**
	 * Method that returns function for a given name.
	 * 
	 * @param name
	 *            name of function
	 * @return instance of {@link IFunction} that implements function
	 */
	public IFunction getFunction(String name) {
		return namesMap.get(name);
	}
}
