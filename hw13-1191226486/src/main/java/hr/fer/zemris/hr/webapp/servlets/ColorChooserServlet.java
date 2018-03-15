package hr.fer.zemris.hr.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that sets application background color if chosen during the session,
 * otherwise sets default background color..
 */
@WebServlet(urlPatterns = { "/setcolor" })
public class ColorChooserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Name of the attribute in session that holds the color of the background.
	 */
	private static final String COLOR_ATTRIBUTE = "pickedBgColor";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		req.getSession().setAttribute(COLOR_ATTRIBUTE, color);
		
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}
}
