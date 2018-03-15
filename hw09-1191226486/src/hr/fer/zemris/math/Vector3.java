package hr.fer.zemris.math;

/**
 * Class that represents 3-dimensional unmodifiable vector.
 * 
 * @author tina
 *
 */
public class Vector3 {
	/** x coordinate */
	private double x;
	/** y coordinate */
	private double y;
	/** z coordinate */
	private double z;

	/**
	 * Constructor that sets vector coordinates.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param z
	 *            z coordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Method that calculates nor norm of the vector.
	 * 
	 * @return vector norm
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Method that normalizes vector.
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		if (norm == 0) {
			return new Vector3(x, y, z);
		}

		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Method that adds two vectors.
	 * 
	 * @param other
	 *            vector to add
	 * @return vector that represent addition
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Method that subtracts instance vector with the one given as argument.
	 * 
	 * @param other
	 *            subtracter
	 * @return result of subtraction
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Method that calculates scalar product.
	 * 
	 * @param other
	 *            other vector for scalar product
	 * @return result of operation
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Cross product of two vectors/.
	 * 
	 * @param other
	 *            other vector
	 * @return cross product
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Method that scales vector with given factor.
	 * 
	 * @param s
	 *            scaling factor
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x *s, y * s, z * s);
	}

	/**
	 * Cosine angle between two vectors
	 * 
	 * @param other
	 *            other vector
	 * @return cosine angle
	 */
	public double cosAngle(Vector3 other) {
		return (dot(other)) / (norm() * other.norm());
	}

	/**
	 * Getter for fist vector component.
	 * 
	 * @return x coordinate component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for second vector component.
	 * 
	 * @return y coordinate component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Getter for third vector component.
	 * 
	 * @return z coordinate component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Method that transforms vector to an array with 3 elements.
	 * 
	 * @return array of vector components
	 */
	public double[] toArray() {
		return new double[]{x, y, z};
	}

	/**
	 * Method that transforms vector to string. <br>
	 * Example: (1.000000, 0.000000, 0.000000)
	 */
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	} 
}
