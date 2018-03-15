package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;

/**
 * Class that represents one relevant retrieved document. It hold information
 * abut ranking, similarity score and path to the document.
 * 
 * @author tina
 *
 */
public class Result implements Comparable<Result> {
	/**
	 * Similarity score with the query.
	 */
	private double similarity;
	/**
	 * Path to the document.
	 */
	private Path filePath;

	/**
	 * Constructor that sets score and path
	 * 
	 * @param similarity
	 *            similarity score
	 * @param filePath
	 *            path to the document
	 */
	public Result(double similarity, Path filePath) {
		super();
		this.similarity = similarity;
		this.filePath = filePath;
	}

	/**
	 * Getter for the similarity score.
	 * @return similarity score
	 */
	public double getSimilarity() {
		return similarity;
	}

	/**
	 * Getter for document file path.
	 * @return document file path
	 */
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public int compareTo(Result o) {
		if (this == o) {
			return 1;
		}
		if (!(o instanceof Result)) {
			return -1;
		}

		return -Double.compare(this.similarity, o.similarity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(similarity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Result))
			return false;
		Result other = (Result) obj;
		if (Double.doubleToLongBits(similarity) != Double.doubleToLongBits(other.similarity))
			return false;
		return true;
	}

}