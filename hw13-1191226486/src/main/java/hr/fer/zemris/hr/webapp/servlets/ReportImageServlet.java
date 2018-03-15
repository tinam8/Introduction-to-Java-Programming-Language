package hr.fer.zemris.hr.webapp.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

/**
 * Servlet that creates image showing Pie Chart.
 * 
 * @author tina
 *
 */
@WebServlet(urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class that represents pie chart graph.
	 * 
	 * @author tina
	 *
	 */
	private static class PieChart {
		/** Instance of {@link JFreeChart} */
		private JFreeChart chart;

		/**
		 * Constructor
		 * 
		 * @param applicationTitle
		 *            title of application
		 * 
		 * @param chartTitle
		 *            chart title
		 */
		public PieChart(String applicationTitle, String chartTitle) {
			// This will create the data set
			PieDataset dataset = createDataset();
			// based on the data set we create the chart
			chart = createChart(dataset, chartTitle);
		}

		/**
		 * Creates a sample data set
		 * 
		 * @return instance of {@link PieDataset}
		 */
		private PieDataset createDataset() {
			DefaultPieDataset result = new DefaultPieDataset();
			result.setValue("Linux", 29);
			result.setValue("Mac", 20);
			result.setValue("Windows", 51);
			return result;
		}

		/**
		 * Creates a chart
		 * 
		 * @param dataset
		 *            data set of chart
		 * @param title
		 *            chart title
		 * @return instance of {@link JFreeChart}F
		 * 
		 */
		private JFreeChart createChart(PieDataset dataset, String title) {

			JFreeChart chart = ChartFactory.createPieChart3D(title, // chart
																	// title
					dataset, // data
					true, // include///// legend
					true, false);

			PiePlot3D plot = (PiePlot3D) chart.getPlot();
			plot.setStartAngle(290);
			plot.setDirection(Rotation.CLOCKWISE);
			plot.setForegroundAlpha(0.5f);
			return chart;

		}

		/**
		 * Chart getter
		 * 
		 * @return instance of {@link JFreeChart}
		 */
		public JFreeChart getChart() {
			return chart;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		PieChart pieChart = new PieChart("Comparison", "Which operating system are you using?");
		BufferedImage image = pieChart.getChart().createBufferedImage(600, 400);

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

}
