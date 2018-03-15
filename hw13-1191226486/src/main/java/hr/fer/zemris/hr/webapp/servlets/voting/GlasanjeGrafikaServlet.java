package hr.fer.zemris.hr.webapp.servlets.voting;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.hr.webapp.jBeans.Band;

/**
 * Servlet that creates graph for the results of voting.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		BufferedImage image = getChart(req, resp).createBufferedImage(400, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);

		ServletOutputStream os = resp.getOutputStream();

		os.write(baos.toByteArray());
		os.flush();

		try {
			baos.close();
		} catch (Exception ignorable) {
		}
	}

	/**
	 * Creates a chart
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 
	 * @return instance of {@link JFreeChart}F
	 * @throws ServletException
	 *             if error occurs
	 * @throws IOException
	 *             if error with stream occurs
	 * 
	 */
	private JFreeChart getChart(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		PieDataset dataset = createDataset(req, resp);
		JFreeChart chart = ChartFactory.createPieChart3D("Rezultati", // chart
				// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;

	}
	
	/**
	 * Creates a sample data set
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * 
	 * @return instance of {@link PieDataset}
	 * @throws ServletException
	 *             if error occurs
	 * @throws IOException
	 *             if error with stream occurs
	 */
	private PieDataset createDataset(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		DefaultPieDataset result = new DefaultPieDataset();
		List<Band> bands = VotingUtil.getResults(req, resp);
		bands.forEach(band -> {
			result.setValue(band.getName(), band.getVotes());
		});
		return result;
	}
}
