package hr.fer.zemris.hr.webapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that stores values of trigonometric functions sin(x) and cos(x) for
 * all integer angles (in degrees, not radians) in a range determined by URL
 * parameters a and b (if a is missing, assumed a=0; if b is missing, assumed
 * b=360; if a > b, swapped; if b > a+720, sets b to a+720).
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class for providing cosine and sine values of integer number.
	 * 
	 * @author tina
	 *
	 */
	public static class TrigonometricValues {
		/** Number whose function values are calculated */
		private int number;
		/** Number sine value */
		private double sine;
		/** Number cosine value */
		private double cosine;

		/**
		 * Constructor
		 * 
		 * @param number
		 *            number whose function values are going to be calculated
		 */
		public TrigonometricValues(int number) {
			this.number = number;
			sine = Math.sin(Math.PI / 180 * number);
			cosine = Math.cos(Math.PI / 180 * number);
		}

		/**
		 * Getter for number
		 * 
		 * @return number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Getter for value sin(number)
		 * 
		 * @return sine of number
		 */
		public double getSine() {
			return sine;
		}

		/**
		 * Getter for value cos(number)
		 * 
		 * @return cosine of number
		 */
		public double getCosine() {
			return cosine;
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String aParam = req.getParameter("a");
		String bParam = req.getParameter("b");

		int a = aParam == null ? 0 : Integer.parseInt(aParam);
		int b = bParam == null ? 360 : Integer.parseInt(bParam);

		if (a > b) {
			int tmp = b;
			b = a > b + 720 ? b + 720 : a;
			a = tmp;
		}

		List<TrigonometricValues> values = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			values.add(new TrigonometricValues(i));
		}

		req.setAttribute("trigonometricValues", values);

		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
