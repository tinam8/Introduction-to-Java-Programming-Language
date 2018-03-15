package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that creates image based on objects and light sources positions in
 * the scene. Geometric rays are traced from the eye of the observer to sample
 * the light (radiance) traveling towards the observer from the ray direction.
 * 
 * @author tina
 *
 */
public class RayCaster {
	/**
	 * Method that starts the program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Method that creates instance of {@link IRayTracerProducer}
	 * 
	 * @return instance of {@link IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {

		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).normalize();

				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp.normalize())))
						.normalize();

				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply((x * horizontal) / (width - 1.0)))
								.sub(yAxis.scalarMultiply((y * vertical) / (height - 1.0)));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};

	}

	/**
	 * Method that determines the color for the pixel based on the ray that goes
	 * trough that part of the screen.
	 * 
	 * @param scene
	 *            scene that contains objects and light sources
	 * @param ray
	 *            ray whose intersection we want to detect
	 * @param rgb
	 *            array of color components
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		double[] rgbDouble = new double[] { 15, 15, 15 };

		RayIntersection closestIntersection = getClosestIntersection(scene, ray);

		if (closestIntersection != null) {
			determineColorFor(scene, ray, closestIntersection, rgbDouble);
		}

		rgb[0] = (short) rgbDouble[0];
		rgb[1] = (short) rgbDouble[1];
		rgb[2] = (short) rgbDouble[2];
	}

	/**
	 * For given scene and ray method finds the closest object that ray
	 * intersection and returns intersection.
	 * 
	 * @param scene
	 *            scene that contains objects and light sources
	 * @param ray
	 *            ray whose intersection we want to detect
	 * @return intersection of ray and closest object if exist, if there is no
	 *         intersection null
	 */
	private static RayIntersection getClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;

		for (GraphicalObject graphicalObject : scene.getObjects()) {
			RayIntersection intersection = graphicalObject.findClosestRayIntersection(ray);
			if (intersection != null) {
				if (closestIntersection == null) {
					closestIntersection = intersection;
					continue;
				}

				if (intersection.getDistance() < closestIntersection.getDistance()) {
					closestIntersection = intersection;
				}
			}
		}

		return closestIntersection;
	}

	/**
	 * Method that determines color for given intersection.
	 * 
	 * @param scene
	 *            scene that contains objects and light sources
	 * @param ray
	 *            ray that intersects some object
	 * @param intersection
	 *            intersection of ray and object
	 * @param rgbDouble
	 *            array of color components
	 */
	private static void determineColorFor(Scene scene, Ray ray, RayIntersection intersection, double[] rgbDouble) {
		for (LightSource lightSource : scene.getLights()) {
			Ray rayFromSource = Ray.fromPoints(lightSource.getPoint(), intersection.getPoint());
			double distanceFromSource = lightSource.getPoint().sub(intersection.getPoint()).norm();

			RayIntersection sourceObjectIntersection = getClosestIntersection(scene, rayFromSource);

			if (sourceObjectIntersection != null) {

				if (Math.abs(distanceFromSource - sourceObjectIntersection.getDistance()) < 0.0001) {
					addDiffuseComponent(lightSource, intersection, rgbDouble);
					addReflectiveComponent(lightSource, ray, intersection, rgbDouble);
				}

			}

		}
	}

	/**
	 * Method that adds diffuse color component based on intersection ray and
	 * light source.
	 * 
	 * @param lightSource
	 *            light source
	 * @param intersection
	 *            intersection of ray and object
	 * @param rgbDouble
	 *            array of color components
	 */
	private static void addDiffuseComponent(LightSource lightSource, RayIntersection intersection, double[] rgbDouble) {
		Point3D l = lightSource.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal();
		double cosln = l.scalarProduct(n);

		if (cosln < 0) {
			return;
		}

		rgbDouble[0] += lightSource.getR() * intersection.getKdr() * cosln;
		rgbDouble[1] += lightSource.getG() * intersection.getKdg() * cosln;
		rgbDouble[2] += lightSource.getB() * intersection.getKdb() * cosln;
	}

	/**
	 * Method that adds reflective color component based on intersection ray and
	 * light source.
	 * 
	 * @param lightSource
	 *            light source
	 * @param ray
	 *            ray that intersects object
	 * @param intersection
	 *            intersection of ray and object
	 * @param rgbDouble
	 *            array of color components
	 */
	private static void addReflectiveComponent(LightSource lightSource, Ray ray, RayIntersection intersection,
			double[] rgbDouble) {
		Point3D lOpposite = lightSource.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal();

		Point3D r = lOpposite.sub(n.scalarMultiply(2 * lOpposite.scalarProduct(n)));
		Point3D v = ray.direction.negate();

		double cosrv = r.scalarProduct(v);

		if (cosrv < 0) {
			return;
		}

		double product = Math.pow(cosrv, intersection.getKrn());

		rgbDouble[0] += lightSource.getR() * intersection.getKrr() * product;
		rgbDouble[0] += lightSource.getG() * intersection.getKrg() * product;
		rgbDouble[0] += lightSource.getB() * intersection.getKrb() * product;

	}
}
