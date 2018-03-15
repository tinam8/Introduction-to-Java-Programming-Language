package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {


	@Override
	public Long addPoll(Connection connection, String title, String message) throws DAOException {
		Connection con ;
		if(connection != null) {
			con = connection;
		} else {
			con = SQLConnectionProvider.getConnection();
		}
				
		PreparedStatement pst = null;
		Long noviID = -1L;

		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, title);
			pst.setString(2, message);
			
			try {
				int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
				System.out.println("Broj redaka koji su pogođeni ovim unosom: "+numberOfAffectedRows);
				ResultSet rset = pst.getGeneratedKeys();
				
				try {
					if(rset != null && rset.next()) {
						noviID = rset.getLong(1);
						System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id="+noviID);
					}
				} finally {
					try { 
						rset.close(); 
					} catch(Exception ignorable) {
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
			throw new DAOException("Error while adding poll.", ex);
		}
		
		return noviID;
	}

	@Override
	public Long addPollOption(Connection connection, String optionTitle, String optionLink, long pollID, int votes) throws DAOException {
		Connection con ;
		if(connection != null) {
			con = connection;
		} else {
			con = SQLConnectionProvider.getConnection();
		}

		PreparedStatement pst = null;
		Long noviID = -1L;

		try {
			pst = con.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, optionTitle);
			pst.setString(2, optionLink);
			pst.setLong(3, pollID);
			pst.setInt(4, votes);
			
			try {
				int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
				System.out.println("Broj redaka koji su pogođeni ovim unosom: "+numberOfAffectedRows);
				ResultSet rset = pst.getGeneratedKeys();
				try {
					if (rset != null && rset.next()) {
						noviID = rset.getLong(1);
						System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id=" + noviID);
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
			throw new DAOException("Error while addding poll option.", ex);
		}

		return noviID;
	}

	@Override
	public List<Poll> getPollList() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
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
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return polls;
	}
	
	@Override
	public List<PollOption> getPollOptions(long id) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, votesCount from PollOptions WHERE pollID=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setVotes(rs.getInt(4));
						pollOption.setPollID(id);
						pollOptions.add(pollOption);
					}
				} finally {
					try {
						rs.close();
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
			throw new DAOException("Error while getting poll options.", ex);
		}
		return pollOptions;
	}

	@Override
	public void updateVotes(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("UPDATE PollOptions set votesCount=votesCount+1 WHERE id=?");
			pst.setLong(1, id);
			try {
				int numberOfAffectedRows = pst.executeUpdate(); // Ocekujemo da je numberOfAffectedRows=1
				System.out.println("Number of updated rows: " + numberOfAffectedRows);
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while updating poll votes.", ex);
		}
		
	}

	@Override
	public Poll getPoll(Long pollID) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("select title, message from Polls where id=?");
			pst.setLong(1, pollID);
			try {
		
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						poll = new Poll();
						poll.setTitle(rs.getString(1));
						poll.setMessage(rs.getString(2));
						
					}
				} finally {
					try {
						rs.close();
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
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		
		return poll;
	}

}