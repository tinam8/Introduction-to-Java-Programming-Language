package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet that handles creation of the new blog entry.
 * @author tina
 *
 */
@WebServlet("/servleti/newEntry")
public class NewEntryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser blogUser = (BlogUser) req.getAttribute("blogUser");
		
		if (blogUser == null) {
			req.setAttribute("error", "Doslo je do pogreske.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		BlogEntryForm blogEntryForm = new BlogEntryForm();
		blogEntryForm.setBlogUserID(blogUser.getId());
		
		req.setAttribute("blogEntryForm", blogEntryForm);
		req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		BlogEntryForm blogEntryForm = new BlogEntryForm();
		blogEntryForm.setTitle(req.getParameter("title"));
		blogEntryForm.setText(req.getParameter("text"));
		blogEntryForm.setBlogUserID(Long.parseLong(req.getParameter("blogUserID")));
		
		blogEntryForm.validateInput();
		
		if (!blogEntryForm.getErrors().isEmpty()) {
			req.setAttribute("blogEntryForm", blogEntryForm);
			req.setAttribute("errors", blogEntryForm.getErrors());
			req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
			return;
		}
				
		BlogUser blogUser = DAOProvider.getDAO().getUser(blogEntryForm.getBlogUserID());
		DAOProvider.getDAO().createBlogEntry(blogEntryForm);
		resp.sendRedirect("/webapp-bazaorm/servleti/author/" + blogUser.getNick());
	}
	
}
