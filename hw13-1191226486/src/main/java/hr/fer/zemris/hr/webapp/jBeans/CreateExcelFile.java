package hr.fer.zemris.hr.webapp.jBeans;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.hr.webapp.servlets.voting.VotingUtil;

/**
 * Class that implements creation of excel documents.
 * 
 * @author tina
 *
 */
public class CreateExcelFile {

	/**
	 * Method that creates and returns a Microsoft Excel document with n pages.
	 * On page i there is a table with two columns. The first column contains
	 * integer numbers from a to b. The second column contains i-th powers of
	 * these numbers.
	 * 
	 * @param a
	 *            lower limit
	 * @param b
	 *            upper limit
	 * @param n
	 *            upper limit for power value
	 * 
	 * @return created document; instance of {@link HSSFWorkbook}
	 */
	public static HSSFWorkbook getPowersFile(int a, int b, int n) {
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Power to the " + i);
			for (int j = a; j <= b; j++) {
				HSSFRow rowhead = sheet.createRow((short) j - a);
				rowhead.createCell((short) 0).setCellValue(j);
				rowhead.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}

		return hwb;
	}

	/**
	 * Method that creates excel file with coting results.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @return instanceof {@link HSSFWorkbook}
	 * @throws ServletException
	 *             if error occurs when using servlet request and response
	 * @throws IOException
	 *             if error occurs in streams
	 */
	public static HSSFWorkbook getVotingResults(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Glasanje");
		List<Band> bands = VotingUtil.getResults(req, resp);
		short row = 0;
		
		for (Band band : bands) {
			HSSFRow rowhead = sheet.createRow(row);
			rowhead.createCell((short) 0).setCellValue(band.getName());
			rowhead.createCell((short) 1).setCellValue(band.getVotes());
			row++;
		}
		return hwb;
	}
}
