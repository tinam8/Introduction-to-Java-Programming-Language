package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogCommentForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that handles following processes: <br>
 * <li>showing users entries (/servleti/author/nick).</li>
 * <li>showing single entry (/servleti/author/nick/5)</li>
 * <li>adding new entry (/servleti/author/nick/new)</li>
 * 
 * @author tina
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path == null) {
			sendError(req, resp);
			return;
		}
		
		path = path.substring(1);
		List<String> urlParts = Arrays.asList(path.split("/"));

		if (urlParts.size() == 0 || urlParts.size() > 3) {
			sendError(req, resp);
			return;
		}

		BlogUser blogUser = DAOProvider.getDAO().getUserByNick(urlParts.get(0));
		if (blogUser == null) {
			sendError(req, resp);
			return;
		}

		req.setAttribute("blogUser", blogUser);
		if (urlParts.size() == 1) {
			showAuthor(blogUser, req, resp);
			return;
		}

		if (urlParts.size() == 2) {
			if (urlParts.get(1).equals("new")) {
				newEntry(req, resp);
				return;
			}

			showEntry(blogUser, urlParts.get(1), req, resp);
			return;
		}

		System.out.println(urlParts.get(2));
		editEntry(blogUser, urlParts.get(2), req, resp);
	}

	/**
	 * Method that gets users entries and sets them to the attributes.
	 * 
	 * @param blogUser
	 *            blog user
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	private void showAuthor(BlogUser blogUser, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<BlogEntry> blogEntries = DAOProvider.getDAO().getBlogEntriesFromUser(blogUser);

		req.setAttribute("blogEntries", blogEntries);
		req.getRequestDispatcher("/WEB-INF/pages/showAuthor.jsp").forward(req, resp);
	}

	/**
	 * Redirects to the {@link NewEntryServlet}.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	private void newEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/servleti/newEntry").forward(req, resp);
	}

	/**
	 * Method that sets attributes to be used for showing the entry. s
	 * 
	 * @param blogUser
	 *            blog user
	 * @param blogEntryID
	 *            if of the blog entry
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	private void showEntry(BlogUser blogUser, String blogEntryID, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(blogEntryID));
		if (!(blogEntry.getBlogUser().getId() == blogUser.getId())) {
			sendError(req, resp);
			return;
		}
		
		List<BlogComment> blogComments = DAOProvider.getDAO().getCommentsByEntry(blogEntry);
		req.setAttribute("blogComments", blogComments);
		req.setAttribute("blogEntry", blogEntry);

		BlogCommentForm blogCommentForm = new BlogCommentForm();
		req.setAttribute("blogCommentForm", blogCommentForm);
		req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
	}

	/**
	 * Method that sets attributes to be used for editing entry. Redirects to
	 * {@link EditEntryServlet}.
	 * 
	 * @param blogUser
	 *            blog user
	 * @param blogEntryID
	 *            id of the blog entry
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	private void editEntry(BlogUser blogUser, String blogEntryID, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("blogUser", blogUser);
		req.setAttribute("blogEntryID", blogEntryID);
		req.getRequestDispatcher("/servleti/editEntry").forward(req, resp);
	}

	/**
	 * Sets error to the attributes that is going to be shown to the user.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	private void sendError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("error", "Nema stranice.");
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
	}

}
