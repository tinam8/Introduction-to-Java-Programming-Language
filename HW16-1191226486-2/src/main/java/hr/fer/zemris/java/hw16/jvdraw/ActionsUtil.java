package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Class that implements method used for action functionalities such as parsing
 * .jvd documents and exporting drawn images.
 * 
 * @author tina
 *
 */
public class ActionsUtil {

	/**
	 * Method that parses string that represents image. It should contain lines
	 * that match one of the following patterns:
	 * <li>LINE x0 y0 x1 y1 red green blue</li>
	 * <li>CIRCLE centerx centery radius red green blue</li>
	 * <li>FCIRCLE centerx centery radius red green blue red green blue</li>
	 * 
	 * @param text
	 *            text that represents image
	 * @return list of {@link GeometricalObject}
	 */
	public static List<GeometricalObject> parseImage(String text) {
		String lines[] = text.split("\\r?\\n");
		List<GeometricalObject> objects = new ArrayList<>();

		for (int i = 0; i < lines.length; i++) {
			GeometricalObject object = getObject(lines[i]);
			if (object == null) {
				return null;
			}

			objects.add(object);
		}

		return objects;
	}

	/**
	 * Supplementary method that parses one line and creates
	 * {@link GeometricalObject}
	 * 
	 * @param line
	 *            line that represents data that determine one
	 *            {@link GeometricalObject}
	 * @return {@link GeometricalObject} or null if line is invalid
	 */
	private static GeometricalObject getObject(String line) {
		line.trim();
		String[] arg = line.split("\\s+");
		if (arg.length < 7) {
			return null;
		}

		if (arg[0].equals("LINE")) {
			if (arg.length != 8) {
				return null;
			}
			try {
				int startPointX = Integer.parseInt(arg[1]);
				int startPointY = Integer.parseInt(arg[2]);
				int endPointX = Integer.parseInt(arg[3]);
				int endPointY = Integer.parseInt(arg[4]);
				int r = Integer.parseInt(arg[5]);
				int g = Integer.parseInt(arg[6]);
				int b = Integer.parseInt(arg[7]);

				Point startPoint = new Point(startPointX, startPointY);
				Point endPoint = new Point(endPointX, endPointY);
				Color fgColor = new Color(r, g, b);

				return new Line(startPoint, endPoint, fgColor);
			} catch (Exception e) {
				return null;
			}
		} else if (arg[0].equals("CIRCLE") || arg[0].equals("FCIRCLE")) {
			int centerX = Integer.parseInt(arg[1]);
			int centerY = Integer.parseInt(arg[2]);
			int radius = Integer.parseInt(arg[3]);
			int r = Integer.parseInt(arg[4]);
			int g = Integer.parseInt(arg[5]);
			int b = Integer.parseInt(arg[6]);

			Point startPoint = new Point(centerX, centerY);
			Point endPoint = new Point(centerX + radius, centerY);
			Color fgColor = new Color(r, g, b);

			if (arg[0].equals("CIRCLE")) {
				if (arg.length != 7) {
					return null;
				} else {
					return new Circle(startPoint, endPoint, fgColor);
				}
			}

			if (arg.length != 10) {
				return null;
			}

			int r1 = Integer.parseInt(arg[7]);
			int g1 = Integer.parseInt(arg[8]);
			int b1 = Integer.parseInt(arg[9]);
			Color bgColor = new Color(r1, g1, b1);

			return new FilledCircle(startPoint, endPoint, fgColor, bgColor);
		} else {
			return null;
		}
	}

	/**
	 * Method that creates text that represent {@link GeometricalObject} that
	 * are in {@link DrawingModel}
	 * 
	 * @param model
	 *            instance {@link DrawingModel} that holds objects
	 * @return string that represents list of objects
	 */
	public static String createJVDfile(DrawingModel model) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < model.getSize(); i++) {
			sb.append(model.getObject(i).formatOutput() + "\n");
		}

		return sb.toString();
	}

	/**
	 * Method that looks at the objects in DrawingModel and find out the
	 * bounding box (the minimal box that encapsulates the whole image). Creates
	 * a BufferedImage.
	 * 
	 * @param canvas
	 *            canvas used for drawing
	 * @param model
	 *            model that holds geometric objects
	 * @param extension
	 *            image extension
	 * @param filePath
	 *            path on the disk where image will be saved
	 */
	public static void exportImage(JDrawingCanvas canvas, DrawingModel model, String extension, Path filePath) {
		int upperX = canvas.getBounds().width;
		int upperY = canvas.getBounds().height;
		int lowerX = 0;
		int lowerY = 0;
		GeometricalObject object;

		if (model.getSize() == 0) {
			JOptionPane.showMessageDialog(canvas, "Nothing to export!", "error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (int i = 0; i < model.getSize(); ++i) {
			object = model.getObject(i);

			upperX = Math.min(object.getX(), upperX);
			upperY = Math.min(object.getY(), upperY);
			lowerX = Math.max(object.getWidth() + object.getX(), lowerX);
			lowerY = Math.max(object.getHeight() + object.getY(), lowerY);
		}

		BufferedImage image = new BufferedImage(lowerX - upperX, lowerY - upperY, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();
		for (int i = 0; i < model.getSize(); ++i) {
			model.getObject(i).draw(g, upperX, upperY);
		}
		g.dispose();
		File file = filePath.toFile();
		try {
			ImageIO.write(image, extension, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(canvas, "Image has not been exported, error occured!", "error",
					JOptionPane.ERROR_MESSAGE);

		}
		JOptionPane.showMessageDialog(canvas, "Image has been exported!", "Info", JOptionPane.INFORMATION_MESSAGE);

	}
}
