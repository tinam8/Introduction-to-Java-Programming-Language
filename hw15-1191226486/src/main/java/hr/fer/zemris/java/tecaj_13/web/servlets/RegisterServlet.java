package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.BlogUserForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.BlogUserUtil;

/**
 * Servlet that handles registration of new user.
 * @author tina
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUserForm blogUserForm = new BlogUserForm();
		
		req.setAttribute("bloguserForm", blogUserForm);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		BlogUserForm blogUserForm = new BlogUserForm();
		blogUserForm.setFirstName(req.getParameter("firstName"));
		blogUserForm.setLastName(req.getParameter("lastName"));
		blogUserForm.setNick(req.getParameter("nick"));
		blogUserForm.setEmail(req.getParameter("email"));
		blogUserForm.setPassword(req.getParameter("password"));
		
		blogUserForm.validateInput();
		
		req.setAttribute("blogUserForm", blogUserForm);
		
		if (!blogUserForm.getErrors().isEmpty()) {
			req.setAttribute("errors", blogUserForm.getErrors());
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}
				
		DAOProvider.getDAO().createBlogUser(getUserFromForm(blogUserForm));
		req.getRequestDispatcher("/servleti/main").forward(req, resp);
	}
	
	/**
	 * Method that creates instance {@link BlogUser} from instance of {@link BlogUserForm}
	 * @param blogUserForm instacne fo {@link BlogUserForm}
	 * @return created blog user
	 */
	public static BlogUser getUserFromForm(BlogUserForm blogUserForm) {		
		BlogUser blogUser = new BlogUser();
		blogUser.setCreatedAt(new Date());
		blogUser.setLastModifiedAt(blogUser.getCreatedAt());
		blogUser.setFirstName(blogUserForm.getFirstName());
		blogUser.setLastName(blogUserForm.getLastName());
		blogUser.setEmail(blogUserForm.getEmail());
		blogUser.setNick(blogUserForm.getNick());	
		blogUser.setPasswordHash(BlogUserUtil.calcHash(blogUserForm.getPassword()));
		
		return blogUser;
	}
}
