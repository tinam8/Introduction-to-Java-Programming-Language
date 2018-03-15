package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.hr.webapp.jBeans.Band;

/**
 * Servlet that gets list of bands for voting.
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = VotingUtil.getBands(req, resp);
		
		req.setAttribute("bands", bands);
		req.setAttribute("test", "test");
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
