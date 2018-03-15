package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that gets list of bands for voting.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		req.setAttribute("pollOptions", pollOptions);
		req.setAttribute("poll", poll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
