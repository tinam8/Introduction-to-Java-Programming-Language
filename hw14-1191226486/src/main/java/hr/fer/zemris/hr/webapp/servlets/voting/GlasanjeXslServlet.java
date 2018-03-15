package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;
/**
 * Servlet that creates excel that contains voting results.
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-xls" })
public class GlasanjeXslServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = Long.parseLong(req.getParameter("pollID"));
		HSSFWorkbook powersExcel = getVotingResults(pollID);

		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment;filename=glasanje.xls");

		powersExcel.write(resp.getOutputStream());
		resp.getOutputStream().flush();
	}

	private HSSFWorkbook getVotingResults(Long pollID) {
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Glasanje");
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		short row = 0;
		
		for (PollOption pollOption : pollOptions) {
			HSSFRow rowhead = sheet.createRow(row);
			rowhead.createCell((short) 0).setCellValue(pollOption.getOptionTitle());
			rowhead.createCell((short) 1).setCellValue(pollOption.getVotes());
			row++;
		}
		
		return hwb;
	}
}
