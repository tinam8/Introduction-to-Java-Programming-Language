package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Point;

/**
 * Implements methods for calculation of geometry formulas like distance between
 * two points.
 * 
 * @author tina
 *
 */
public class GeometryUtil {

	/**
	 * Calculates eucledian distance of two point.
	 * @param point1 first point
	 * @param point2 second point
	 * @return calculated distance
	 */
	public static double distanceFromPoint(Point point1, Point point2) {
		double x = Math.pow(point1.getX() - point2.getX(), 2);	
		double y = Math.pow(point1.getY() - point2.getY(), 2);	
		return Math.sqrt(x + y);
	}

}