package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of token types.
 * 
 * @author tina
 *
 */
public enum TokenType {
	/**
	 * Represents that there are no more tokens.
	 */
	EOF,
	/**
	 * Text.
	 */
	TEXT,
	/**
	 * String containing decimal number.
	 */
	STRING,
	/**
	 * Tag echo.
	 */
	TAG_ECHO,
	/**
	 * Tag for.
	 */
	TAG_FOR,
	/**
	 * Tag that ands for loop
	 */
	TAG_END,
	/**
	 * Variable name. <br>
	 * Starts by letter and after follows zero or more letters, <br>
	 * digits or underscores
	 */
	VARIABLE,
	/**
	 * Decimal value.
	 */
	DECIMAL_NUMBER,
	/**
	 * Integer value.
	 */
	INTEGER_NUMBER,
	/**
	 * Function name. <br>
	 * starts by letter and after follows zero or more letters,<br>
	 * digits or underscores
	 */
	FUNCTION,
	/**
	 * Operator. Valid operators are + (plus), - (minus), <br>
	 * * (multiplication), / (division), ^ (power).
	 */
	OPERATOR,
	/**
	 * Ending of tag
	 */
	ENDING_TAG,
	/**
	 * Beginning of tag.
	 */
	BEGINNIG_TAG

}
