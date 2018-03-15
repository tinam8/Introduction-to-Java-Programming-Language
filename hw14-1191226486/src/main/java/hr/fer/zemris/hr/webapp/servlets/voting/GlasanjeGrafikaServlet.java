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

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that creates graph for the results of voting.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/servleti/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		Long pollID = Long.parseLong(req.getParameter("pollID"));
		BufferedImage image = getChart(pollID).createBufferedImage(400, 400);

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
	 * 
	 * @param pollID
	 *            if of poll * 
	 * @return instance of {@link JFreeChart}F
	 * 
	 */
	private JFreeChart getChart(Long pollID) {
		PieDataset dataset = createDataset(pollID);
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
	 * 
	 * @param pollID
	 *            if of poll
	 * 
	 * 
	 * @return instance of {@link PieDataset}
	 * 
	 */
	private PieDataset createDataset(Long pollID) {
		DefaultPieDataset result = new DefaultPieDataset();
		List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID);
		pollOptions.forEach(band -> {
			result.setValue(band.getOptionTitle(), band.getVotes());
		});
		return result;
	}
}
