package hr.fer.zemris.hr.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.hr.webapp.jBeans.CreateExcelFile;

/**
 * Servlet that accepts three parameters a (integer from [-100,100]) b (integer
 * from [-100,100]) and n (where n>=1 and n<=5). If any parameter is invalid
 * appropriate message is displayed. IF parameters are valid Microsoft Excel
 * document with n pages is dnamically created. On page i there is a table with
 * two columns. The first column contains integer numbers from a to b. The
 * second column contains i-th powers of these numbers.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aParam = req.getParameter("a");
		String bParam = req.getParameter("b");
		String nParam = req.getParameter("n");

		int a = getParameter(aParam, -100, 100, req, resp, "a");
		int b = getParameter(bParam, -100, 100, req, resp, "b");
		int n = getParameter(nParam, 1, 5, req, resp, "n");

		HSSFWorkbook powersExcel = CreateExcelFile.getPowersFile(a, b, n);

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=powers.xls");

		powersExcel.write(resp.getOutputStream());
		resp.getOutputStream().flush();
	}

	/**
	 * Supplementary method that checks if parameter is valid. If it is not
	 * valid message is sent to the client,
	 * 
	 * @param param parameter to check; String
	 * @param min minimal parameter value
	 * @param max maximal parameter values
	 * @param req servlet request
	 * @param resp servlet response
	 * @param name name of parameter
	 * @return value of parameter
	 * @throws ServletException if error occurs when using servlet request and resonse
	 * @throws IOException if error occurs in streams
	 */
	private int getParameter(String param, int min, int max, HttpServletRequest req, HttpServletResponse resp,
			String name) throws ServletException, IOException {
		if (param == null) {
			req.setAttribute("errorMessage", "Parameters a, b and n have to be set.");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
		}

		int paramInt = 0;
		try {
			paramInt = Integer.parseInt(param);
		} catch (Exception e) {
			req.setAttribute("errorMessage", "Parameter " + name + "has to be in interval [" + min + "," + max + "]");
			req.getRequestDispatcher("WEB-INF/pages/invalidParameters.jsp").forward(req, resp);
		}

		if (paramInt < min || paramInt > max) {
			req.setAttribute("errorMessage",
					"Parameter " + name + " has to be in interval [" + min + "," + max + "] it was " + paramInt);
			req.getRequestDispatcher("WEB-INF/pages/invalidParameters.jsp").forward(req, resp);
		}

		return paramInt;
	}

}
