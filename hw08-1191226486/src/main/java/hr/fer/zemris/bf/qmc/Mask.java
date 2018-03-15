package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import hr.fer.zemris.bf.utils.Util;

/**
 * Class that represents products. <br>
 * Example for boolean function with variables A, B, C, D: <br>
 * Product A'B'CD is represented with mask 0011. <br>
 * Product AC'D is represented with mask 1201. <br>
 * 
 * Complemented variables are represented with 0. <br>
 * Uncomplemented variables are represented with 1. <br>
 * Variable that does not appear in product is represented with -.
 * 
 * @author tina
 *
 */
public class Mask {
	/** Variable that detects whether mask id don't care or not. */
	private boolean dontCare;
	/** Set of minterm indexes */
	Set<Integer> indexes;
	/** Mask */
	byte[] values;
	/** Hash value */
	int hash;
	/** Determines if mask is combined or not */
	boolean combined = false;

	/**
	 * Constructor that creates mask based on given values.
	 * 
	 * @param index
	 *            index of minterm
	 * @param numberOfVariables
	 *            number of variables
	 * @param dontCare
	 *            detects if minterm is don't care or not
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if (numberOfVariables < 1) {
			throw new IllegalArgumentException("Number of variables has to be greater than 1.");
		}

		if (index >= Math.pow(2, numberOfVariables) || index < 0) {
			throw new IllegalArgumentException("Index is invalid");
		}

		this.dontCare = dontCare;

		indexes = new HashSet<>();
		indexes.add(index);
		Collections.unmodifiableSet(indexes);

		boolean[] booleanValues = Util.getValues(numberOfVariables).get(index);

		this.values = new byte[numberOfVariables];
		for (int i = 0; i < numberOfVariables; i++) {
			if (booleanValues[i]) {
				this.values[i] = 1;
			} else {
				this.values[i] = 0;
			}
		}

		hash = Arrays.hashCode(values);
	}

	/**
	 * Constructor that copies and stores mask given as argument.
	 * 
	 * @param values
	 *            mask
	 * @param indexes
	 *            set f indexes
	 * @param dontCare
	 *            flag
	 * @throws IllegalArgumentException
	 *             if mask is null or if set is null or empty
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null) {
			throw new IllegalArgumentException("Mas can not be null.");
		}

		if (indexes == null) {
			throw new IllegalArgumentException("Set of indexes can not be null.");
		}

		if (indexes.isEmpty()) {
			throw new IllegalArgumentException("Set of indexes can not be empty.");
		}

		this.indexes = indexes;
		Collections.unmodifiableSet(this.indexes);

		this.values = values;
		this.dontCare = dontCare;
		hash = Arrays.hashCode(values);
	}

	/**
	 * Returns mask size.
	 * 
	 * @return mask size
	 */
	public int size() {
		return values.length;
	}

	/**
	 * Returns mask value at given position.
	 * 
	 * @param position
	 *            position in mask
	 * @return value at position
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position > values.length) {
			throw new IllegalArgumentException("Invalid position.");
		}
		
		return values[position];
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Mask)) {
			return false;
		}

		if (hash == obj.hashCode()) {
			return true;
		}

		Mask mask = (Mask) obj;
		for (int i = 0; i < values.length; i++) {
			if (values[i] != mask.getValueAt(i)) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Checks if mask was combined
	 * 
	 * @return true if it was combined, otherwise false
	 */
	public boolean isCombined() {
		return combined;
	}

	/**
	 * Changes combines status of a mask.
	 * 
	 * @param combined
	 *            true to set that mask was combined, false if not
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	/**
	 * Detects if mask has don't care flag.
	 * 
	 * @return true if it is don't care, false otherwise
	 */
	public boolean isDontCare() {
		return dontCare;
	}

	/**
	 * Returns set of indexes.
	 * 
	 * @return indexes
	 */
	public Set<Integer> getIndexes() {
		return indexes;
	}

	/**
	 * Calculates and returns how many times value 1 apperas in a mask.
	 * 
	 * @return number of 1 in a mask.
	 */
	public int countOfOnes() {
		int count = 0;

		for (byte value : values) {
			if (value == 1) {
				count++;
			}
		}

		return count;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (byte value : values) {
			if (value == 1) {
				sb.append(1);
			} else if (value == 0) {
				sb.append(0);
			} else
				sb.append('-');
		}

		if (dontCare) {
			sb.append(" D");
		} else {
			sb.append(" .");
		}

		if (combined) {
			sb.append(" * ");
		} else {
			sb.append("   ");
		}

		sb.append(indexes.toString());

		return sb.toString();

	}

	/**
	 * Method that checks if current product can be combined with given one
	 * using theorem of simplification.
	 * 
	 * @param other
	 *            given products
	 * @return new product if combining is possible, otherwise "no result"
	 * @throws IllegalArgumentException
	 *             if argument is null, or two products does not have the same
	 *             length.
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null) {
			throw new IllegalArgumentException("Given argument can not be null.");
		}

		if (values.length != other.size()) {
			throw new IllegalArgumentException("Masks does not have the same length.");
		}

		boolean diffFound = false;
		int position = -1;

		for (int i = 0; i < values.length; i++) {
			if (values[i] != other.getValueAt(i)) {
				if (diffFound) {
					return Optional.empty();
				}

				diffFound = true;
				position = i;
			}
		}
		
		byte[] newValues = new byte[values.length];
		
		for (int i = 0; i < values.length; i++) {
			newValues[i] = values[i];
		}
		newValues[position] = 2; 

		Set<Integer> indexesProduct = new HashSet<>();
		indexesProduct.addAll(indexes);
		indexesProduct.addAll(other.getIndexes());

		boolean dontCareProduct = (dontCare && other.isDontCare());

		Mask product = new Mask(newValues, indexesProduct, dontCareProduct);
		return Optional.of(product);
	}

}
