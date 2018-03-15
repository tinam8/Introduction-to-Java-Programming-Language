package hr.fer.zemris.hr.webapp.servlets.voting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.hr.webapp.jBeans.Band;

/**
 * Class that implements methods used in voting servlets suck as getting bands,
 * updating votes and reading votes.
 * 
 * @author tina
 *
 */
public class VotingUtil {
	/** File path to the file that holds information about bands */
	private static final String BANDS_PATH = "/WEB-INF/glasanje-definicija.txt";
	/** File path to the file that holds information about voting result */
	private static final String RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";

	/**
	 * Method that returns list of bands stored in file
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @return list of bands
	 * @throws IOException
	 *             if error occurs with streams
	 * @throws ServletException
	 *             if erro occurs with servlet
	 */
	public static List<Band> getBands(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String fileName = req.getServletContext().getRealPath(BANDS_PATH);
		List<String> bands = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		List<Band> bandsList = new ArrayList<>();

		for (String band : bands) {
			String[] bandAttr = band.split("\\t");

			if (bandAttr.length != 3) {
				req.setAttribute("errorMessage", "Sorry error while getting bands.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
			bandsList.add(new Band(Integer.parseInt(bandAttr[0]), bandAttr[1], bandAttr[2], 0));

		}

		return bandsList;
	}

	/**
	 * Method that creates files of voting results.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @throws ServletException
	 *             if error occurs with servlet
	 * @throws IOException
	 *             if error occurs with streams
	 */
	public synchronized static void createVotes(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> bands = getBands(req, resp);

		String filePath = req.getServletContext().getRealPath(RESULTS_PATH);
		File file = null;
		try {
			file = new File(filePath);
			if (!file.createNewFile()) {
				req.setAttribute("errorMessage", "Sorry error while getting votes111.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
		} catch (IOException ignorable) {
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException ignorable) {
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (Band band : bands) {
			bw.write(band.getId() + "\t" + 0);
			bw.newLine();
		}

		bw.close();
	}

	/**
	 * Method that gets voting results from the file.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @return map of band id and number of votes
	 * @throws ServletException
	 *             if servlet error occurs
	 * @throws IOException
	 *             if error with streams occur
	 */
	public synchronized static Map<Integer, Integer> getVotes(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String filePath = req.getServletContext().getRealPath(RESULTS_PATH);

		if (!Files.exists(Paths.get(filePath))) {
			createVotes(req, resp);
		}

		List<String> votesLines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
		Map<Integer, Integer> votesMap = new TreeMap<>();

		for (String votes : votesLines) {
			String[] votesAttr = votes.split("\\t");

			if (votesAttr.length != 2) {
				req.setAttribute("errorMessage", "Sorry error while getting votes222.");
				req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			}
			votesMap.put(Integer.parseInt(votesAttr[0]), Integer.parseInt(votesAttr[1]));
		}

		return votesMap;
	}

	/**
	 * Method that updates file with voting results.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @param votes
	 *            map with bands id and votes
	 * @throws IOException
	 *             if error with stream occur
	 */
	public synchronized static void UpdateVotes(HttpServletRequest req, HttpServletResponse resp,
			Map<Integer, Integer> votes) throws IOException {
		String filePath = req.getServletContext().getRealPath(RESULTS_PATH);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
		} catch (FileNotFoundException ignorable) {
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (Map.Entry<Integer, Integer> entry : votes.entrySet()) {
			Integer id = entry.getKey();
			Integer votesNumber = entry.getValue();
			bw.write(id + "\t" + votesNumber);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	/**
	 * Method that updates results and returns list of bands.
	 * 
	 * @param req
	 *            servlet request
	 * @param resp
	 *            servlet response
	 * @return sorted band list
	 * @throws ServletException
	 *             if error occurs
	 * @throws IOException
	 *             if error with stream occurs
	 */
	public static List<Band> getResults(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<Integer, Integer> votes = VotingUtil.getVotes(req, resp);
		List<Band> bands = VotingUtil.getBands(req, resp);

		bands.forEach(band -> {
			band.setVotes(votes.get(band.getId()));
		});

		bands.sort(new Comparator<Band>() {
			@Override
			public int compare(Band band1, Band band2) {
				return -Integer.compare(band1.getVotes(), band2.getVotes());
			}
		});

		return bands;
	}

}
