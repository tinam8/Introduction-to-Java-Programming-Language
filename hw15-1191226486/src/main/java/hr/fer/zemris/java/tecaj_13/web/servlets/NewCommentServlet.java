package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
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
 * Servlet that handles creation of the new comment.
 * 
 * @author tina
 *
 */
@WebServlet("/servleti/newComment")
public class NewCommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("blogEntryID")));
		
		BlogCommentForm blogCommentForm = new BlogCommentForm();
		blogCommentForm.setUsersEMail(req.getParameter("email"));
		blogCommentForm.setMessage(req.getParameter("message"));
		blogCommentForm.setBlogEntry(blogEntry);

		blogCommentForm.validateInput();
		System.out.println("errors " + blogCommentForm.getErrors());

		if (!blogCommentForm.getErrors().isEmpty()) {
			BlogUser blogUser = DAOProvider.getDAO().getUserByNick(req.getParameter("nick"));
			List<BlogComment> blogComments = DAOProvider.getDAO().getCommentsByEntry(blogEntry);
			
			req.setAttribute("blogUser", blogUser);
			req.setAttribute("blogComments", blogComments);
			req.setAttribute("blogCommentForm", blogCommentForm);
			req.setAttribute("errors", blogCommentForm.getErrors());
			req.setAttribute("blogEntry", blogEntry);
			req.getRequestDispatcher("/WEB-INF/pages/showEntry.jsp").forward(req, resp);
			return;
		}

		DAOProvider.getDAO().createBlogComment(blogCommentForm);
		resp.sendRedirect("/webapp-bazaorm/servleti/author/" + req.getParameter("nick") + "/" + blogEntry.getId());
	}

}
