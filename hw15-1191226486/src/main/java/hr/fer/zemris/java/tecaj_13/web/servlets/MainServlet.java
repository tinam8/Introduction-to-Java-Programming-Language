package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.LogInForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that handles user's login process.
 * 
 * @author tina
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LogInForm loginForm = new LogInForm();

		List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
		req.setAttribute("authors", blogUsers);
		
		req.setAttribute("loginForm", loginForm);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LogInForm loginForm = new LogInForm();
		loginForm.setNick(req.getParameter("nick"));
		loginForm.setPassword(req.getParameter("password"));

		BlogUser blogUser = loginForm.findUser();
		
		if (blogUser == null) {
			List<BlogUser> blogUsers = DAOProvider.getDAO().getBlogUsers();
			req.setAttribute("authors", blogUsers);
			req.setAttribute("loginForm", loginForm);
			req.setAttribute("error", "Nadimak ili sifra su neispravni");
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		createSession(blogUser, req, resp);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

	/**
	 * Method that adds informations of logged user to the session.
	 * @param blogUser blog user
	 * @param req servlet request
	 * @param resp servlet response
	 */
	private void createSession(BlogUser blogUser, HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().setAttribute("current.user", blogUser);
		req.getSession().setAttribute("current.user.nick", blogUser.getNick());
		req.getSession().setAttribute("current.user.id", blogUser.getId());
		req.getSession().setAttribute("current.user.first", blogUser.getFirstName());
		req.getSession().setAttribute("current.user.last", blogUser.getLastName());
	}

	
}
