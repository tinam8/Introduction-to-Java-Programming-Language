package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that processes one vote.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Integer, Integer> votes = VotingUtil.getVotes(req, resp);
		votes.merge(Integer.parseInt(req.getParameter("id")), 0, (v1, v2) -> v1+1);
		
		VotingUtil.UpdateVotes(req, resp, votes);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
