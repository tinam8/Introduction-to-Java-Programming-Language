package hr.fer.zemris.java.webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Http server that offers several services. <br>
 * 1. getting binary data <br>
 * (http://127.0.0.1:5721/sample.txt<br>
 * http://127.0.0.1:5721/index.html<br>
 * http://127.0.0.1:5721/fruits.png)<br>
 * 2. executing scripts that are parsed with {@link SmartScriptParser} <br>
 * (http://127.0.0.1:5721/scripts/osnovni.smscr<br>
 * http://127.0.0.1:5721/scripts/zbrajanje.smscr?a=3&b=7<br>
 * http://127.0.0.1:5721/scripts/brojPoziva.smscr<br>
 * http://127.0.0.1:5721/scripts/fibonacci.smscr) <br>
 * 3. running workers for drawing a circle, returning sent parameters or writing
 * how many signs a parameter has <br>
 * (http://127.0.0.1:5721/hello<br>
 * http://127.0.0.1:5721/hello?name=john<br>
 * http://127.0.0.1:5721/cw<br>
 * http://127.0.0.1:5721/ext/EchoParams?name1=value1&...&namen=valuen<br>
 * http://127.0.0.1:5721/ext/HelloWorker<br>
 * http://127.0.0.1:5721/ext/CircleWorker )<br>
 * 4. addition of two number sent as parameters <br>
 * (http://127.0.0.1:5721/calc?a=11&b=22) if parameter are not included a
 * default is 1 and b default values is 2.
 * 
 * 
 * @author tina
 *
 */
public class SmartHttpServer {
	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One argument expexted: main configuration file path.");
			return;
		}

		try {
			SmartHttpServer server = new SmartHttpServer(args[0]);
			System.out.println("Starting server...");
			server.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String toExit = br.readLine().trim();
			if (toExit.toLowerCase().equals("stop")) {
				System.out.println("Server stopped");
				server.stop();	
				System.exit(0);
			}
		} catch (Exception e) {
			System.err.println("Error occured. " + e.getMessage());
			System.exit(-1);
		}
	}

	/** String used for generation of the cookie */
	static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/** Cookie length */
	static final int SESSION_LENGTH = 20;
	/** Extension of the smart scripts */
	static final String SCRIPT_EXT = "smscr";
	/** Period for thread to remove expired cookies. */
	static final int SESSION_CHECK_TIME = 50000;
	/** Server address */
	private String address;
	/** Port on which server listens */
	private int port;
	/** Number of worker threads */
	private int workerThreads;
	/** Duration of session */
	private int sessionTimeout;
	/** Map of extension and mime types */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** Instance of {@link ServerThread} */
	private ServerThread serverThread;
	/** Pool of {@link ClientWorker} threads */
	private ExecutorService threadPool;
	/** Path to the root that holds serving files */
	private Path documentRoot;
	/** Indicates if server has already been started */
	boolean serverStarted = false;
	/** Map of paths and instances of {@link IWebWorker} */
	private Map<String, IWebWorker> workersMap;
	/** Map of session ids and instances of {@link SessionMapEntry} */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** Used for generating session id */
	private Random sessionRandom = new Random();

	/**
	 * Constructor that sets server configuration using given file path to the
	 * config file.
	 * 
	 * @param configFileName
	 *            file path to the config file
	 */
	public SmartHttpServer(String configFileName) {
		try (FileInputStream is = new FileInputStream(configFileName);) {
			Properties properties = new Properties();
			properties.load(is);

			address = properties.getProperty("server.address");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
			documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
			workersMap = new HashMap<>();

			String mimeProp = properties.getProperty("server.mimeConfig");
			String workersProp = properties.getProperty("server.workers");
			setMimeProperties(mimeProp);
			setWorkersProperties(workersProp);
		} catch (IOException ex) {
			System.out.println("Error with stream while reading properties.");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("Error while reading properties.");
			System.exit(-1);
		}

	}

	/**
	 * Supplementary method that sets mime properties using configuration file
	 * 
	 * @param configFileName
	 *            file path to the configuration file for mime types
	 */
	private void setMimeProperties(String configFileName) {
		try (FileInputStream is = new FileInputStream(configFileName);) {
			Properties properties = new Properties();
			properties.load(is);

			for (Object key : properties.keySet()) {
				mimeTypes.put((String) key, properties.getProperty((String) key));
			}
		} catch (IOException ex) {
			System.out.println("Error while reading properties");
			System.exit(-1);
		}
	}

	/**
	 * Supplementary method that sets worker properties using configuration file
	 * 
	 * @param configFileName
	 *            file path to the configuration file for worksers
	 */
	private void setWorkersProperties(String configFileName) {
		try (FileInputStream is = new FileInputStream(configFileName);) {
			Properties properties = new Properties();
			properties.load(is);

			for (Object key : properties.keySet()) {
				if (workersMap.containsKey(key)) {
					throw new IllegalArgumentException("File contains same paths.");
				}

				String path = key.toString();
				String fqcn = properties.getProperty(key.toString());
				Class<?> referenceToClass;

				Object newObject = null;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					newObject = referenceToClass.newInstance();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			}
		} catch (IOException ex) {
			System.out.println("Error while reading properties");
			System.exit(-1);
		}
	}

	/**
	 * Method that starts server
	 */
	protected synchronized void start() {
		if (serverStarted) {
			return;
		}
		if (serverThread == null) {
			serverThread = new ServerThread();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
		System.out.println("Server is ready...");
		System.out.println("Enter stop for stopping.");
		serverThread.start();
		sessionControlThread.setDaemon(true);
		sessionControlThread.start();
		serverStarted = true;
	}

	/**
	 * Method that stops server.
	 */
	protected synchronized void stop() {
		serverThread.stopRunning();
		threadPool.shutdown();
	}

	/**
	 * Class that implements {@link Thread}. <br>
	 * It implements acceptance of new clients.
	 * 
	 * @author tina
	 *
	 */
	protected class ServerThread extends Thread {
		/** Indicates if thread is active for accepting new clients */
		private boolean running = false;

		@Override
		public void run() {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}

			running = true;
			while (running) {
				Socket client = null;
				try {
					client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					try {
						serverSocket.close();
					} catch (IOException ignorable) {
					}
				}
			}
			try {
				serverSocket.close();
			} catch (IOException ignorable) {
			}
		}

		/**
		 * Method that prevents thread for accepting new clients.
		 */
		public void stopRunning() {
			running = false;
		}
	}

	/**
	 * Class that implements processing of the request.
	 * 
	 * @author tina
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/** Client socket */
		private Socket csocket;
		/** Input stream used for getting data from client */
		private PushbackInputStream istream;
		/** Output stream used for writing data to the client */
		private OutputStream ostream;
		/** Http protocol version */
		private String version;
		/** Method of a request */
		private String method;
		/** Map that stores names and values of parameters */
		private Map<String, String> params = new HashMap<String, String>();
		/** Map that stores names and values of persistent parameters */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/** Map that stores names and values of temporary parameters */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/** List of cookies */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** Session id */
		private String SID;
		/** Context used for communication */
		private RequestContext context;

		/**
		 * Constructor
		 * 
		 * @param csocket
		 *            socket used for communication
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				List<String> headers = readRequest(istream);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length < 1) {
					sendError(400, "Bad request");
					return;
				}

				String method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}

				String version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1")) {
					sendError(505, "HTTP Version Not Supported");
					return;
				}
				String[] pathParam = firstLine[1].split("[?]");
				String path = pathParam[0];

				checkSession(headers);

				if (pathParam.length == 2) {
					String paramString = pathParam[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(path, true);

				csocket.close();
			} catch (IOException e) {
				System.out.println("Error while processing header.");
				System.exit(-1);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		/**
		 * Method that checks if client session is active. If it is duration is
		 * updated, if not new session is started.
		 * 
		 * @param headers
		 *            header lines
		 */
		private synchronized void checkSession(List<String> headers) {
			String sidCandidate = null;

			for (String line : headers) {
				if (line.startsWith("Cookie:")) {
					sidCandidate = getSid(line);
				}
			}

			SessionMapEntry session;
			if (sidCandidate != null) {
				session = sessions.get(sidCandidate);

				if (session != null) {
					if (session.validUntil < System.currentTimeMillis()) {
						sessions.remove(session);
						session = generateSid();
					} else {
						session.validUntil = System.currentTimeMillis() + sessionTimeout;
						SID = session.sid;
					}
				} else {
					session = generateSid();
				}
			} else {
				session = generateSid();
			}

			String host = getHost(headers);
			RCCookie cookie = new RequestContext.RCCookie("sid", SID, null, host, "/");
			cookie.setType("HttpOnly");
			outputCookies.add(cookie);

			permPrams = session.map;
		}

		/**
		 * Method that reads session id from given string.
		 * 
		 * @param line
		 *            line that holds session id
		 * @return session id
		 */
		private String getSid(String line) {
			int start = line.indexOf("sid=");
			if (start == -1) {
				return null;
			}

			String subStart = line.substring(start + 4).trim();
			return subStart.substring(0, SESSION_LENGTH);
		}

		/**
		 * Method that generates new session id and stores it.
		 * 
		 * @return generated representation of the session; instance of
		 *         {@link SessionMapEntry}
		 */
		private synchronized SessionMapEntry generateSid() {
			StringBuilder sb = new StringBuilder(SESSION_LENGTH);
			for (int i = 0; i < SESSION_LENGTH; i++) {
				sb.append(AB.charAt(sessionRandom.nextInt(AB.length())));
			}

			SessionMapEntry sessionEntry = new SessionMapEntry();
			sessionEntry.validUntil = System.currentTimeMillis() + sessionTimeout;
			sessionEntry.sid = sb.toString();
			sessionEntry.map = new ConcurrentHashMap<>();

			SID = sessionEntry.sid;
			sessions.put(sessionEntry.sid, sessionEntry);
			return sessionEntry;
		}

		/**
		 * Method that extracts host name from the header.
		 * 
		 * @param headers
		 *            header lines
		 * @return host name
		 */
		private String getHost(List<String> headers) {
			for (String line : headers) {
				if (line.startsWith("Host:")) {
					String[] hostArg = line.substring(5).trim().split("[:]");
					return hostArg[0];
				}
			}
			return address;
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Method that for each request determines its further processing.
		 * 
		 * @param urlPath
		 *            path to the file extracted from request header
		 * @param directCall
		 *            if method has been directly called or not
		 * @throws Exception
		 *             if error occurs
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, null, this);
			}
			if (urlPath.startsWith("/private/ ")) {
				sendError(403, "forbidden");
				return;
			}
			if (urlPath.startsWith("/ext")) {
				runWorker(urlPath);
				return;
			}
			if (workersMap.get(urlPath) != null) {
				workersMap.get(urlPath).processRequest(context);
				return;
			}

			String requestedPath = documentRoot + urlPath;
			String extension = getExtension(Paths.get(requestedPath));
			if (extension == null) {
				return;
			}

			String mimeType = getMimeType(extension);
			context.setMimeType(mimeType);
			context.setStatusCode(200);

			if (extension.equals(SCRIPT_EXT)) {
				runScript(requestedPath);
			} else {
				writeFile(context, Paths.get(requestedPath));
			}
		}

		/**
		 * Method that runs scripts.
		 * 
		 * @param urlPath
		 *            path to the script
		 */
		private void runScript(String urlPath) {
			String documentBody = readFromDisk(urlPath);
			// create engine and execute it
			DocumentNode documentNode;
			try {
				documentNode = new SmartScriptParser(documentBody).getDocumentNode();
			} catch (SmartScriptParserException e) {
				try {
					csocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return;
			}

			new SmartScriptEngine(documentNode, context).execute();
		}

		/**
		 * Supplementary method for reading files from disk.
		 * 
		 * @param path
		 *            path to the file on the disk
		 * @return read data as string
		 */
		private String readFromDisk(String path) {
			String docBody = null;

			try {
				docBody = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			} catch (IOException e) {
				System.out.println("Can not open file!");
				System.exit(-1);
			}

			return docBody;
		}

		/**
		 * Supplementary method that writes data for the client.
		 * 
		 * @param rc
		 *            context used for communication with the client
		 * @param path
		 *            path to the file
		 */
		private void writeFile(RequestContext rc, Path path) {
			try {
				rc.write(Files.readAllBytes(path));
			} catch (IOException e) {
				System.out.println("Error while reading file!");
				System.exit(-1);
			}

		}

		/**
		 * Method that detects which worker is called trough url and starts
		 * processing.
		 * 
		 * @param urlPath
		 *            path that holds name of worker in form of
		 *            (/ext/WorkerName)
		 */
		private void runWorker(String urlPath) {
			String className = urlPath.substring(5, urlPath.length());
			String fqcn = "hr.fer.zemris.java.webserver.workers." + className;
			Class<?> referenceToClass;

			Object newObject = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				newObject = referenceToClass.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			IWebWorker worker = (IWebWorker) newObject;
			try {
				worker.processRequest(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Method that parsers parameters sent in request header
		 * 
		 * @param paramString
		 *            line of header that holds parameters names and values
		 */
		private void parseParameters(String paramString) {
			String[] pairs = paramString.split("[&]");
			for (String pair : pairs) {
				String[] array = pair.split("[=]");
				if (array.length != 2 || array[0].isEmpty() || array[1].isEmpty()) {
					continue;
				}

				params.put(array[0], array[1]);
			}
		}

		/**
		 * Method that reads header from input stream.
		 * 
		 * @param cis
		 *            input stream from client
		 * @return header request as lines of strings
		 * @throws IOException
		 *             if error occurs while reading header
		 */
		private List<String> readRequest(InputStream cis) throws IOException {
			List<String> request = new ArrayList<>();
			byte[] requestBytes = readRequestBytes(cis);

			if (requestBytes == null) {
				return request;
			}
			String requestStr = new String(requestBytes, StandardCharsets.US_ASCII);
			return extractHeaders(requestStr);
		}

		/**
		 * Method that reads input from client as bytes.
		 * 
		 * @param is
		 *            input stream
		 * @return data as array of bytes
		 * @throws IOException
		 *             exception if error occurs while reading data
		 */
		private byte[] readRequestBytes(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Supplementary method that splits header string into lines.
		 * 
		 * @param requestHeader
		 *            request header
		 * @return list of lines of header
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Supplementary method for sending error messages.
		 * 
		 * @param statusCode
		 *            code of error
		 * @param statusString
		 *            text of error
		 * @throws IOException
		 *             exception if error occurs while sending
		 */
		private void sendError(int statusCode, String statusString) throws IOException {
			RequestContext context = new RequestContext(ostream, null, null, null);
			context.setStatusCode(statusCode);
			context.setStatusText(statusString);
			try {
				context.write(statusString);
				context.write(Integer.toString(statusCode));
				while (true) {
					try {
						ostream.flush();
						csocket.close();
						break;
					} catch (Exception ignorable) {
					}
				}
			} catch (IOException ignorable) {
			}

		}

		/**
		 * Supplementary method that extracts extension from given file path.
		 * 
		 * @param requestedPath
		 *            file path
		 * @return extension of file
		 * @throws IOException
		 *             if error occurs
		 */
		private String getExtension(Path requestedPath) throws IOException {
			if (!Files.exists(requestedPath) || !Files.isReadable(requestedPath)) {
				sendError(404, "no file");
				return null;
			}

			String fileName = requestedPath.getFileName().toString();

			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}

		/**
		 * Method that gets mime type from file extension
		 * 
		 * @param extension
		 *            file extension
		 * @return mime type
		 */
		private String getMimeType(String extension) {
			String mimetype = mimeTypes.get(extension);
			return mimetype == null ? "application/octet-stream" : mimetype;
		}
	}

	/**
	 * Class that holds information abut one user session.
	 * 
	 * @author tina
	 *
	 */
	private static class SessionMapEntry {
		/** Session id */
		String sid;
		/** Time until it is active */
		long validUntil;
		/** Clients data */
		Map<String, String> map;
	}

	/**
	 * Thread that periodically goes through all session records and removes
	 * records for expired sessions from sessions map.
	 */
	private Thread sessionControlThread = new Thread() {
		
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(SESSION_CHECK_TIME);
				} catch (InterruptedException ignorable) {
				}
				sessions.entrySet().removeIf( e -> e.getValue().validUntil < System.currentTimeMillis());
			}
		};
	};
}
