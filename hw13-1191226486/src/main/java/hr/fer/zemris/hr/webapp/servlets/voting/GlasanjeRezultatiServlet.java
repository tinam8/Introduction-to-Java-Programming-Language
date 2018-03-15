package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.hr.webapp.jBeans.Band;

/**
 * Servlet that gets results of voting for each band.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = VotingUtil.getResults(req, resp);

		List<Band> winners = getWinners(bands);
		req.setAttribute("winners", winners);

		req.setAttribute("bandsResult", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * Method that returns list of bands that got most of the votes.
	 * @param bands list of all bands, sorted by the number of votes
	 * @return list of winners
	 */
	private List<Band> getWinners(List<Band> bands) {
		List<Band> winners = new ArrayList<>();
		int max = 0;

		if (bands != null && bands.size() > 0) {
			max = bands.get(0).getVotes();
			winners.add(bands.get(0));
		}

		for (int i = 1; i < bands.size(); i++) {
			if (bands.get(i).getVotes() == max) {
				winners.add(bands.get(i));
			} else {
				break;
			}
		}
		
		return winners;
	}
}
