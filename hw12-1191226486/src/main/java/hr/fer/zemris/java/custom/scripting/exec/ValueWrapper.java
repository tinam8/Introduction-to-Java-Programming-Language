package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Stores value that will be associated with keys of instances of
 * {@link ObjectMultistack}.
 * 
 * @author tina
 *
 */
public class ValueWrapper {
	/** Stored value */
	private Object value;

	/**
	 * Public constructor that accepts initial value
	 * 
	 * @param value
	 *            initial value
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Gets stored value.
	 * 
	 * @return stored value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Stores given value
	 * 
	 * @param value
	 *            value to be stored
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds given value to the stored value
	 * 
	 * @param incValue
	 *            value to be added to the stored value
	 */
	public void add(Object incValue) {
		Number valueStored = getNumber(value);
		Number valueArgument = getNumber(incValue);

		if (valueArgument instanceof Double || valueStored instanceof Double) {
			value = valueStored.doubleValue() + valueArgument.doubleValue();
		} else {
			value = valueStored.intValue() + valueArgument.intValue();
		}
	}

	/**
	 * Subtracts stored value by given one.
	 * 
	 * @param decValue
	 *            Subtracter
	 */
	public void subtract(Object decValue) {
		Number valueStored = getNumber(value);
		Number valueArgument = getNumber(decValue);

		if (valueArgument instanceof Double || valueStored instanceof Double) {
			value = valueStored.doubleValue() - valueArgument.doubleValue();
		} else {
			value = valueStored.intValue() - valueArgument.intValue();
		}
	}

	/**
	 * Multiplies stored value with given one.
	 * 
	 * @param mulValue
	 *            value to multiply with
	 */
	public void multiply(Object mulValue) {
		Number valueStored = getNumber(value);
		Number valueArgument = getNumber(mulValue);

		if (valueArgument instanceof Double || valueStored instanceof Double) {
			value = valueStored.doubleValue() * valueArgument.doubleValue();
		} else {
			value = valueStored.intValue() * valueArgument.intValue();
		}
	}

	/**
	 * Divides stored value with given one.
	 * 
	 * @param divValue
	 *            value to divide with
	 * @throws RuntimeException
	 *             if divisor is zero
	 */
	public void divide(Object divValue) {
		Number valueStored = getNumber(value);
		Number valueArgument = getNumber(divValue);

		if (valueArgument.doubleValue() == 0) {
			throw new RuntimeException("Can not divide with zero.");
		}

		if (valueArgument instanceof Double || valueStored instanceof Double) {
			value = valueStored.doubleValue() / valueArgument.doubleValue();
		} else {
			value = valueStored.intValue() / valueArgument.intValue();
		}
	}

	/**
	 * Method sompares currently stored value in ValueWrapper and given
	 * argument. <br>
	 * 1. If both values are null, treated as equal <br>
	 * 2. If one is null and the other is not, treats the null-value being equal
	 * to an integer with value 0 <br>
	 * 3. Otherwise, promotes both values to same type and then perform the
	 * comparison.
	 * 
	 * @param withValue
	 *            value to compare to
	 * @return integer less than zero if currently stored value is smaller than
	 *         argument, an integer greater than zero if currently stored value
	 *         is larger than argument or an integer 0 if they are equal
	 */
	public int numCompare(Object withValue) {
		Double valueStored = getNumber(value).doubleValue();
		Double valueArgument = getNumber(withValue).doubleValue();

		return valueStored.compareTo(valueArgument);
	}

	/**
	 * Supplmentary mathod that returns instance of Number based on rules. null
	 * value is represented as 0, String can be parsed as Double or as Integer
	 * value.
	 * 
	 * @param value
	 *            value on which rules are applied
	 * @return instance of number
	 * @throws RuntimeException
	 *             if given value can not be represented neither as Integer or
	 *             Double.
	 */
	private Number getNumber(Object value) {
		if (value instanceof Integer || value instanceof Double) {
			return (Number) value;
		}
		
		if (value == null) {
			return 0;
		}

		if (value instanceof String) {
			Number valueTemp;

			try {
				valueTemp = Integer.parseInt((String) value);
			} catch (Exception e) {
				try {
					valueTemp = Double.parseDouble((String) value);
				} catch (Exception e2) {
					throw new RuntimeException("String value can not be represented as number");
				}
			}

			return (Number) valueTemp;
		}

		throw new RuntimeException("Value is not null, Integer, Double or String.");
	}
}
