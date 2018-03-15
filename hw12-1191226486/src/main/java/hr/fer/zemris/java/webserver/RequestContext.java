package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class implementing a context of the request. It contains output stream used
 * for communication with client, charset, encoding, mime type. It also contains
 * parameters used for processing requests and cookies.
 * 
 * @author tina
 *
 */
public class RequestContext {
	/** Output stream for communication with the client */
	private OutputStream outputStream;
	/** Charset to use */
	private Charset charset;
	/** Encoding to use for response */
	private String encoding = "UTF-8";
	/** Status of response */
	private int statusCode = 200;
	/** Text of status */
	private String statusText = "OK";
	/** Mime type */
	private String mimeType = "text/html";
	/** Map that stores parameters needed for creating a response */
	private Map<String, String> parameters;
	/** Map that stores temporary parameters needed for creating a response */
	private Map<String, String> temporaryParameters;
	/** Map that stores persistent parameters needed for creating a response */
	private Map<String, String> persistentParameters;
	/** List of cookies */
	private List<RCCookie> outputCookies;
	/** Indicates if header has already been created */
	private boolean headerGenerated = false;
	/** Instance of {@link IDispatcher} */
	private IDispatcher dispatcher;

	/**
	 * Constructor that sets some of the variables of context.
	 * 
	 * @param outputStream
	 *            output stream for communication with the client
	 * @param parameters
	 *            map that stores parameters needed for creating a response
	 * @param persistentParameters
	 *            map that stores persistent parameters needed for creating a
	 *            response
	 * @param outputCookies
	 *            list of cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream can not be null.");
		}
		this.outputStream = outputStream;

		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new LinkedList<>() : outputCookies;
		this.temporaryParameters = new HashMap<>();
	}

	/**
	 * Constructor that sets some of the variables of context.
	 * 
	 * @param outputStream
	 *            output stream for communication with the client
	 * @param parameters
	 *            map that stores parameters needed for creating a response
	 * @param persistentParameters
	 *            map that stores persistent parameters needed for creating a
	 *            response
	 * @param outputCookies
	 *            list of cookies
	 * @param temporaryParameters
	 *            map that stores temporary parameters needed for creating a
	 *            response
	 * @param dispatcher
	 *            instance of {@link IDispatcher}
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		this(outputStream, parameters, persistentParameters, outputCookies);

		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		this.dispatcher = dispatcher;

	}

	/**
	 * Setter for encoding.
	 * 
	 * @param encoding
	 *            name of encoding
	 * @throws RuntimeException
	 *             if header has already been created
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Encoding can not be changed, header has been generated.");
		}
		this.encoding = encoding;
	}

	/**
	 * Setter for status code.
	 * 
	 * @param statusCode
	 *            number of status
	 * @throws RuntimeException
	 *             if header has already been created
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Status code can not be changed, header has been generated.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text.
	 * 
	 * @param statusText
	 *            text of the response status
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Text status can not be changed, header has been generated.");
		}
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type.
	 * 
	 * @param mimeType
	 *            mime type
	 * @throws RuntimeException
	 *             if header has already been created
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Mime type can not be changed, header has been generated.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Setter for output cookies.
	 * 
	 * @param outputCookies
	 *            list of cookies
	 * @throws RuntimeException
	 *             if header has already been created
	 */
	public void setOutputCookies(List<RCCookie> outputCookies) {
		if (headerGenerated) {
			throw new RuntimeException("Cookies can not be changed, header has been generated.");
		}
		this.outputCookies = outputCookies;
	}

	/**
	 * Getter for context parameters.
	 * 
	 * @return map of parameters names and values
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Setter for temporary parameters.
	 * 
	 * @param temporaryParameters
	 *            map of temporary parameters names and values
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Getter for temporary parameters.
	 * 
	 * @return map of temporary parameters names and values
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Setter for persistent parameters.
	 * 
	 * @param persistentParameters
	 *            map of persistent parameters names and values
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Getter for persistent parameters.
	 * 
	 * @return map of persistent parameters names and values
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Method that adds cookie.
	 * 
	 * @param cookie
	 *            instance of {@link RCCookie}
	 * @throws RuntimeException
	 *             if header has already been created
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated) {
			throw new RuntimeException("Cookie can not be changed, header has been generated.");
		}
		if (cookie == null) {
			throw new IllegalArgumentException("Cookie can not be null");
		}

		outputCookies.add(cookie);
	}

	/**
	 * Method that retrieves value from parameters map (or null if no
	 * association exists):
	 * 
	 * @param name
	 *            name of the parameter
	 * @return value for given name or null if it doesn't exist
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in parameters map.
	 * 
	 * @return names of all parameters in parameters map (this set is read-only)
	 */
	public Set<String> getParameterNames() {
		return getKeys(parameters);
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists)
	 * 
	 * @param name
	 *            name of persistent parameter
	 * @return value for given name, null if association does not exist
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters
	 * map (note, this set must be read-only):
	 * 
	 * @return names of all parameters in persistent parameters map (this set is
	 *         read-only)
	 */
	public Set<String> getPersistentParameterNames() {
		return getKeys(persistentParameters);
	}

	/**
	 * s method that returns set of keys from given map
	 * 
	 * @param set
	 *            set whose key are extracted
	 * @return set of keys from given map (read-only)
	 */
	private Set<String> getKeys(Map<String, String> set) {
		Set<String> keys = new HashSet<>();

		for (String name : set.keySet()) {
			keys.add(name);
		}

		return Collections.unmodifiableSet(keys);
	}

	/**
	 * Method that stores a value to persistentParameters map:
	 * 
	 * @param name
	 *            name of persistent parameter
	 * @param value
	 *            value of persistent parameter
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name
	 *            name of value to remove from map of persistent parameters
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Method that retrieves value from temporary parameters map (or null if no
	 * association exists)
	 * 
	 * @param name
	 *            name of parameter whose value is returned
	 * @return value from temporary parameters map (or null if no association
	 *         exists)
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters
	 * map.
	 * 
	 * @return names of all parameters in temporary parameters map (set
	 *         isread-only)
	 */
	public Set<String> getTemporaryParameterNames() {
		return getKeys(temporaryParameters);
	}

	/**
	 * Method that stores a value to temporary parameters map.
	 * 
	 * @param name
	 *            name of temporary parameter
	 * @param value
	 *            value of temporary parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name
	 *            name of value that is going to be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Getter for dispatcher
	 * 
	 * @return instance of {@link IDispatcher}
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Method that writes response using given bytes data to the client using
	 * the output stream.
	 * 
	 * @param data
	 *            data to write to the client
	 * @return this
	 * @throws IOException
	 *             if error occurs while writing
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Method that writes response using given strings text to the client using
	 * the output stream.
	 * 
	 * @param text
	 *            text to write to the client
	 * @return this
	 * @throws IOException
	 *             if error occurs while writing
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}

		return write(text.getBytes(charset));
	}

	/**
	 * Method that generates header for the response.
	 * 
	 * @throws IOException
	 *             if error occurs while using the stream
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append(" carset: " + encoding + "\r\n");
		} else {
			sb.append("\r\n");
		}
		if (!outputCookies.isEmpty()) {
			appendCookies(sb);
		}

		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();
		headerGenerated = true;
	}

	/**
	 * Method that adds cookies to given string builder
	 * 
	 * @param sb
	 *            instance of {@link StringBuilder}
	 */
	private void appendCookies(StringBuilder sb) {
		outputCookies.forEach(cookie -> {
			sb.append("Set-cookie: ");
			sb.append(cookie.toString());
			sb.append("\r\n");
		});
	}

	/**
	 * Class that represents a cookie. It holds cookie name, value, domain,
	 * oath, maximum age and type.
	 * 
	 * @author tina
	 *
	 */
	public static class RCCookie {
		/** Cookie name */
		private String name;
		/** Cookie value */
		private String value;
		/** Cookie domain */
		private String domain;
		/** Path on the domain */
		private String path;
		/** Maximum duration of the cookie */
		private Integer maxAge;
		/** Cookie type */ 
		private String type;

		/**
		 * Constructor that sets cookie values.
		 * @param name cookie name
		 * @param value cookie value
		 * @param maxAge maximum duration of cookie
		 * @param domain domain
		 * @param path path on the domain
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			if (name == null) {
				throw new IllegalArgumentException("Name of cookie can not be null");
			}

			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter for cookie name.
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}
		/**
		 * Getter for cookie value.
		 * @return cookie value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * Getter for domain.
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}
		/**
		 * Getter for domain path.
		 * @return path
		 */
		public String getPath() {
			return path;
		}
		/**
		 * Getter for maximum duration of the cookie.
		 * @return  maximum duration of the cookie.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		/**
		 * Getter for the cookie type.
		 * @return cookie type
		 */
		public String getType() {
			return type;
		}
		/**
		 * Setter for the cookie type.
		 * @param type type of the cookie
		 */
		public void setType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name + "=" + value);
			if (domain != null) {
				sb.append("; Domain=" + domain);
			}
			if (path != null) {
				sb.append("; Path=" + path);
			}
			if (maxAge != null) {
				sb.append("; Max-age=" + maxAge);
			}

			return sb.toString();
		}
	}

}
