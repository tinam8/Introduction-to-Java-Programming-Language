package hr.fer.zemris.java.hw11.jnotepadpp.sort;

import java.util.List;

/**
 * Interface that has method that has custom method for sorting list of strings.
 * @author tina
 *
 */
public interface ISort {
	/**
	 * Method that sorts list of strings.
	 * @param lines list of lines
	 * @return sorted lines put together as text
	 */
	public String sort(List<String> lines);
}
