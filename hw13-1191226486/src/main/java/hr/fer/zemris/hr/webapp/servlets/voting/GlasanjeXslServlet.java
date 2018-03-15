package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.hr.webapp.jBeans.CreateExcelFile;
/**
 * Servlet that creates excel that contains voting results.
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/glasanje-xls" })
public class GlasanjeXslServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HSSFWorkbook powersExcel = CreateExcelFile.getVotingResults(req, resp);

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=glasanje.xls");

		powersExcel.write(resp.getOutputStream());
		resp.getOutputStream().flush();
	}
}
