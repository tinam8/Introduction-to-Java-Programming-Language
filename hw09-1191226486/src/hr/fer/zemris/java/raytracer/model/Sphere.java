package hr.fer.zemris.java.raytracer.model;

/**
 * Class that represents graphical object sphere that is determined with center
 * point and radius.
 * 
 * @author tina
 *
 */
public class Sphere extends GraphicalObject {
	/** sphere center */
	private Point3D center;
	/** sphere radius */
	private double radius;
	/** coefficient for diffuse component for red color */
	private double kdr;
	/** coefficient for diffuse component for green color */
	private double kdg;
	/** coefficient for diffuse component for blue color */
	private double kdb;
	/** coefficient for reflective component for red color */
	private double krr;
	/** coefficient for reflective component for green color */
	private double krg;
	/** coefficient for reflective component for blue color */
	private double krb;
	/** coefficient n for reflective component */
	private double krn;

	/**
	 * Constructor for setting sphere parameters.
	 * 
	 * @param center
	 *            sphere center
	 * @param radius
	 *            sphere radius
	 * @param kdr
	 *            coefficient for diffuse component for red color
	 * @param kdg
	 *            coefficient for diffuse component for green color
	 * @param kdb
	 *            coefficient for diffuse component for blue color
	 * @param krr
	 *            coefficient for reflective component for red color
	 * @param krg
	 *            coefficient for reflective component for green color
	 * @param krb
	 *            coefficient for reflective component for blue color
	 * @param krn
	 *            coefficient n for reflective component
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D o = ray.start; // O
		Point3D l = ray.direction; 
		Point3D oCenter = o.sub(center);

		double a =1;
		double b = l.scalarProduct(oCenter)*2;
		double c = oCenter.scalarProduct(oCenter) - radius*radius;
		
		double determinant = b*b - 4 * a*c;
		if(determinant < 0) {
			return null;
		}
		
		double coef1 = (-b + Math.sqrt(determinant))/2;
		double coef2 = (-b - Math.sqrt(determinant))/2;
		Point3D intersectionPoint1 = o.add(l.scalarMultiply(coef1));
		Point3D intersectionPoint2 = o.add(l.scalarMultiply(coef2));
		Point3D closerIntersection;
		boolean outer = false;
		
		double distance1 = o.sub(intersectionPoint1).norm();
		double distance2 = o.sub(intersectionPoint2).norm();

		if (Math.abs(distance1 - distance2) < 0.00001) {
			closerIntersection = intersectionPoint1;
			outer = true;
		} else {
			closerIntersection = distance1 < distance2 ? intersectionPoint1 : intersectionPoint2;
		}

		return new RayIntersection(closerIntersection, o.sub(closerIntersection).norm(), outer) {

			@Override
			public Point3D getNormal() {
				return closerIntersection.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

}
