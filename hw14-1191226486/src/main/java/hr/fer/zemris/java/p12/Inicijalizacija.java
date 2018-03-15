package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * During the web-application startup this class initializes database and
 * configures connections toward it.
 * 
 * @author tina
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String dbsettings = sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties");

		if (dbsettings == null) {
			System.out.println("no dbsettings.properties file");
			System.exit(-1);
		}

		String host = null;
		String port = null;
		String name = null;
		String user = null;
		String password = null;

		try (FileInputStream is = new FileInputStream(dbsettings);) {
			Properties properties = new Properties();
			properties.load(is);

			host = properties.getProperty("host");
			port = properties.getProperty("port");
			name = properties.getProperty("name");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (IOException ex) {
			System.out.println("Error with stream while reading properties.");
			ex.printStackTrace();
			System.exit(-1);
		}

		if (host == null || port == null || name == null || user == null || password == null) {
			System.out.println("dbsettings.properties is not valid.");
			System.exit(-1);
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + name;
		System.out.println(connectionURL);
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);

		try {
			initializeDatabase(cpds.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	/**
	 * Method that initializes database. It creates tables Polls and PollOptions
	 * (only if they do not already exists) and populates them with data.
	 * 
	 * @param con
	 *            connection with a database
	 */
	private void initializeDatabase(Connection con) {
		try {
			DatabaseMetaData dmd = con.getMetaData();
			ResultSet rs = dmd.getTables(null, null, "POLLS", null);

			if (!rs.next()) {
				System.out.println("No Polls table...");
				cretePollsTable(con);
			} else if (isEmpty("Polls", con)) {
				System.out.println("Pools table is empty...");
				populatePolls(con);
			}
			
			rs = dmd.getTables(null, null, "POLLOPTIONS", null);

			if (!rs.next()) {
				System.out.println("No Polls option table...");
				cretePollOptionsTable(con);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that checks if database table is empty
	 * 
	 * @param string
	 *            name of the table
	 * @param con
	 *            connection with a database
	 * @return true if empty, false otherwise
	 */
	private boolean isEmpty(String string, Connection con) {
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("SELECT * from Polls");

			try {
				ResultSet rset = pst.executeQuery();
				try {
					if (rset != null && rset.next()) {
						return false;
					}
				} finally {
					try {
						rset.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DAOException("Error while Checking tables.", ex);
		}

		return true;
	}

	/**
	 * Creates Polls table and populates it. If PollOptions table does not exist
	 * it is created. PollOptions table is also populated with data.
	 * 
	 * @param con
	 *            connection with a database
	 */
	private void cretePollsTable(Connection con) {
		System.out.println("Creating Pools table...");
		String createCommand = "CREATE TABLE Polls" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "title VARCHAR(150) NOT NULL, " + "message CLOB(2048) NOT NULL)";

		createTable(createCommand, con);

		System.out.println("PRIJE");
		cretePollOptionsTable(con);
		System.out.println("POSLI");
		populatePolls(con);
	}

	/**
	 * Creates PollOptions table.
	 * 
	 * @param con
	 *            connection with a database
	 */
	private void cretePollOptionsTable(Connection con) {
		System.out.println("Creating PollOptions table...");
		String createCommand = "CREATE TABLE PollOptions" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "optionTitle VARCHAR(100) NOT NULL, " + "optionLink VARCHAR(150) NOT NULL, " + "pollID BIGINT, "
				+ "votesCount BIGINT, " + "FOREIGN KEY (pollID) REFERENCES Polls(id))";

		createTable(createCommand, con);
	}

	/**
	 * Creates database table.
	 * 
	 * @param createCommand
	 *            command to execute
	 * @param con
	 *            connection with a database
	 */
	private void createTable(String createCommand, Connection con) {
		System.out.println("Creating " + createCommand);
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createCommand);
		} catch (SQLException e) {
			System.out.println("Error while creating tables...");
			e.printStackTrace();
		}
	}

	/**
	 * Adds data to the Polls table (two concrete polls).
	 * 
	 * @param con
	 *            connection with a database
	 */
	private void populatePolls(Connection con) {
		System.out.println("Populating polls...");
		Long votingPollId = DAOProvider.getDao().addPoll(con, "Glasanje za omiljeni bend:",
				"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
		populatePollOptions(con, votingPollId);
		votingPollId = DAOProvider.getDao().addPoll(con, "Glasanje za najmrzi bend:",
				"Od sljedećih bendova, koji Vam je bend najmrzi? Kliknite na link kako biste glasali!");
		populatePollOptions(con, votingPollId);
	}

	/**
	 * Adds data to the PollOptions table.
	 * 
	 * @param con
	 *            connection with a database
	 * @param pollID
	 *            id of poll that poll option refers to
	 */
	private void populatePollOptions(Connection con, Long pollID) {
		System.out.println("Populating options...");

		DAOProvider.getDao().addPollOption(con, "The Beatles",
				"https://www.youtube.com/watch?v=z9ypq6_5bsg", pollID, 0);
		DAOProvider.getDao().addPollOption(con, "The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", pollID,
				0);
		DAOProvider.getDao().addPollOption(con, "The Beach	Boys", "https://www.youtube.com/watch?v=2s4slliAtQUF",
				pollID, 0);
		DAOProvider.getDao().addPollOption(con, "The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds",
				pollID, 0);
		DAOProvider.getDao().addPollOption(con, "The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", pollID,
				0);
		DAOProvider.getDao().addPollOption(con, "The Everly	Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8",
				pollID, 0);
		DAOProvider.getDao().addPollOption(con, "The Mamas And The Papas",
				"shttps://www.youtube.com/watch?v=N-aK6JnyFmk", pollID, 0);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}