package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that gets results of voting for each band.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);

		List<PollOption> winners = getWinners(pollOptions);
		req.setAttribute("winners", winners);

		req.setAttribute("pollID", pollID);
		req.setAttribute("pollOptions", pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method that returns list of poll options that got most of the votes.
	 * @param pollOptions list of all options, sorted by the number of votes
	 * @return list of winners
	 */
	private List<PollOption> getWinners(List<PollOption> pollOptions) {
		List<PollOption> winners = new ArrayList<>();
		pollOptions.sort((opt1, opt2) -> {
			return opt2.getVotes() - opt1.getVotes();
		});
		
		int max = 0;

		if (pollOptions != null && pollOptions.size() > 0) {
			max = pollOptions.get(0).getVotes();
			winners.add(pollOptions.get(0));
		}

		for (int i = 1; i < pollOptions.size(); i++) {
			if (pollOptions.get(i).getVotes() == max) {
				winners.add(pollOptions.get(i));
			} else {
				break;
			}
		}
		
		return winners;
	}
}
