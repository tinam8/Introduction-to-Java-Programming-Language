package hr.fer.zemris.bf.qmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.utils.ExpressionStringGetter;

/**
 * Implementation of minimization using Quine-McCluskey with Pyne-McCluskey
 * approach for one Bollean function.
 * 
 * @author tina
 *
 */
public class Minimizer {
	/** Logger */
	// private static final Logger LOG =
	// Logger.getLogger("hr.fer.zemris.bf.qmc");
	private static final Logger LOG = Logger.getLogger("hr.fer.zemris.bf.qmc");

	/** Groups of minterms and don't cares. */
	private List<Set<Mask>> column;
	/** Set of minterm indexes */
	Set<Integer> mintermSet;
	/** Set of don't care indexes */
	Set<Integer> dontCareSet;
	/** List of variables */
	List<String> variables;
	/** Minimal forms */
	List<Set<Mask>> minimalForms;

	/**
	 * Constructor checks if given arguments are valid, meaning that there is no
	 * overlapping in given sets and that indexes are in appropriate range based
	 * on given number of variables.
	 * 
	 * @param mintermSet
	 *            set of miniterm indexes
	 * @param dontCareSet
	 *            set of don't care indexes
	 * @param variables
	 *            list of variables
	 * @throws IllegalArgumentException
	 *             if there is overlapping in given sets or if indexes are not
	 *             in an appropriate range based on given number of variables.
	 */
	public Minimizer(Set<Integer> mintermSet, Set<Integer> dontCareSet, List<String> variables) {
		this.mintermSet = mintermSet;
		this.dontCareSet = dontCareSet;
		this.variables = variables;

		checkNonOverlapping();
		checkIndexes();

		Set<Mask> primCover = findPrimaryImplicants();
		minimalForms = chooseMinimalCover(primCover);
	}

	/**
	 * Supplementary method that checks if two set are overlapping.
	 * 
	 * @throws IllegalArgumentException
	 *             if sets overlap
	 */
	private void checkNonOverlapping() {
		int size = mintermSet.size();
		mintermSet.removeAll(dontCareSet);

		if (mintermSet.size() != size) {
			throw new IllegalArgumentException("Indexes of miniterms and don't cares are overlaaping.");
		}

	}

	/**
	 * Supplementary method that checks if indexes are valid
	 * 
	 * @throws IllegalArgumentException
	 *             if indexes are invalid
	 */
	private void checkIndexes() {
		int indexlimit = (int) Math.pow(2, variables.size()) - 1;

		if (dontCareSet.size() != 0) {
			if (Collections.min(dontCareSet) < 0 || Collections.max(dontCareSet) > indexlimit) {
				throw new IllegalArgumentException("Invalid index.");
			}
		}

		if (mintermSet.size() != 0) {
			if (Collections.min(mintermSet) < 0 || Collections.max(mintermSet) > indexlimit) {
				throw new IllegalArgumentException("Invalid index.");
			}
		}
	}


	/**
	 * Method that find primary implicants
	 * 
	 * @return primary implicants
	 */
	private Set<Mask> findPrimaryImplicants() {
		column = createFirstColumn();
		Set<Mask> primaryImplicants = new LinkedHashSet<Mask>();

		while (!column.isEmpty()) {
			Set<Mask> newPrimaryImplicants = new LinkedHashSet<>();
			List<Set<Mask>> newColumn = generateNewColumn();

			column.forEach(row -> row.forEach(mask -> {
				if (mask.isCombined() == false && !mask.isDontCare()) {
					newPrimaryImplicants.add(mask);
				}
			}));

			if (LOG.isLoggable(Level.FINER)) {
				logColumnStatus();
			}

			if (LOG.isLoggable(Level.FINEST)) {
				logPrimaryImplicants(newPrimaryImplicants, "Pronašao primarni implikant: ", Level.FINEST);
				LOG.log(Level.FINEST, "");
			}

			primaryImplicants.addAll(newPrimaryImplicants);
			column = newColumn;
		}

		if (LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "");
			LOG.log(Level.FINE, "Svi primarni implikanti: ");
			logPrimaryImplicants(primaryImplicants, "", Level.FINE);
		}

		return primaryImplicants;
	}

	/**
	 * Creates groups of minterms and don't cares. First group are products that
	 * contain zero 1, than the second one are products that contain two 1, and
	 * so on..
	 * 
	 * @return returns list of mask groups
	 */
	private List<Set<Mask>> createFirstColumn() {
		List<Set<Mask>> col = new ArrayList<Set<Mask>>();
		Map<Integer, Integer> mapIndexRow = new HashMap<>();

		Set<Integer> numberOfOnes = new TreeSet<>();

		for (Integer index : mintermSet) {
			addRow(index, numberOfOnes);
		}

		for (Integer index : dontCareSet) {
			addRow(index, numberOfOnes);
		}

		int row = 0;
		for (Integer integer : numberOfOnes) {
			mapIndexRow.put(integer, row);
			row++;

			col.add(new LinkedHashSet<>());
		}

		for (Integer index : mintermSet) {
			addIndex(index, false, mapIndexRow, col);
		}

		for (Integer index : dontCareSet) {
			addIndex(index, true, mapIndexRow, col);
		}

		return col;

	}

	/**
	 * Method that creates and adds mask of given index.
	 * 
	 * @param index
	 *            index
	 * @param type
	 *            true - don't care, false - miniterm
	 * @param mapIndexRow
	 *            map that connects number of 1 in index and row of column that
	 *            holds such values.
	 * @param col
	 *            column that contains sets of mask, each member of one set has
	 *            the same number of 1 in a mask
	 */
	private void addIndex(Integer index, boolean type, Map<Integer, Integer> mapIndexRow,
			List<Set<Mask>> col) {
		Mask mask = new Mask(index, variables.size(), type);
		int numOne = mask.countOfOnes();

		int row1 = mapIndexRow.get(numOne);
		col.get(row1).add(mask);
	}

	/**
	 * Method that adds number of 1 in index to the given set.
	 * 
	 * @param index
	 *            index
	 * @param numberOfOnes
	 *            set that contains different appearances of 1 in indexes.
	 */
	private void addRow(int index, Set<Integer> numberOfOnes) {
		Mask mask = new Mask(index, variables.size(), false);
		int row = mask.countOfOnes();

		numberOfOnes.add(row);
	}

	/**
	 * Method that generates new column by trying to combine elements of current
	 * one.
	 * 
	 * @return new column of combined masks
	 */
	private List<Set<Mask>> generateNewColumn() {
		List<Set<Mask>> newColumn = new ArrayList<>();

		for (int i = 0; i < column.size() - 1;) {

			Set<Mask> firstGroup = column.get(i);
			Set<Mask> secondGroup = column.get(i + 1);

			boolean combined = false;
			outerloop: for (Mask mask1 : firstGroup) {
				for (Mask mask2 : secondGroup) {
					if (mask2.countOfOnes() - mask1.countOfOnes() > 1) {
						break outerloop;
					}

					if (mask1.combineWith(mask2).isPresent()) {
						if (!combined) {
							newColumn.add(new LinkedHashSet<>());
							combined = true;
						}

						newColumn.get(newColumn.size() - 1).add(mask1.combineWith(mask2).get());
						mask1.setCombined(true);
						mask2.setCombined(true);
					}
				}
			}

			i++;
		}

		return newColumn;
	}


	/**
	 * Method that logs current column.
	 */
	private void logColumnStatus() {
		LOG.log(Level.FINER, "Stupac tablice: ");
		LOG.log(Level.FINER, "=================================");
		for (int i = 0; i < column.size(); i++) {
			Set<Mask> group = column.get(i);

			if (group.size() == 0) {
				continue;
			}

			group.forEach(mask -> {
				LOG.log(Level.FINER, mask.toString());
			});

			if (i != Minimizer.this.column.size() - 1) {
				LOG.log(Level.FINER, "-------------------------------");
			}
		}

		LOG.log(Level.FINER, "");

	}

	/**
	 * Method that logs primary implicants.
	 * 
	 * @param primaryImplicants
	 *            implicants to write
	 * @param text
	 *            text to be written before masks
	 * @param level
	 *            level of logger
	 */
	private void logPrimaryImplicants(Set<Mask> primaryImplicants, String text, Level level) {
		primaryImplicants.forEach(mask -> {
			LOG.log(level, text + mask.toString());
		});
	}

	/**
	 * Method that finds primary implicants for minimal cover.
	 * 
	 * @param primCover
	 *            implicants
	 * @return minimal cover
	 */
	private List<Set<Mask>> chooseMinimalCover(Set<Mask> primCover) {
		// Izgradi polja implikanata i minterma (rub tablice):
		Mask[] implicants = primCover.toArray(new Mask[primCover.size()]);
		Integer[] minterms = mintermSet.toArray(new Integer[mintermSet.size()]);

		// Mapiraj minterm u stupac u kojem se nalazi:
		Map<Integer, Integer> mintermToColumnMap = new HashMap<>();
		for (int i = 0; i < minterms.length; i++) {
			Integer index = minterms[i];
			mintermToColumnMap.put(index, i);
		}

		// Napravi praznu tablicu pokrivenosti:
		boolean[][] table = buildCoverTable(implicants, minterms, mintermToColumnMap);

		// Donji redak tablice: koje sam minterme pokrio?
		boolean[] coveredMinterms = new boolean[minterms.length];

		// Pronađi primarne implikante...
		Set<Mask> importantSet = selectImportantPrimaryImplicants(implicants, mintermToColumnMap, table,
				coveredMinterms);

		if (LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "");
			LOG.log(Level.FINE, "Bitni primarni implikanti su: ");
			logPrimaryImplicants(importantSet, "", Level.FINE);
		}

		// Izgradi funkciju pokrivenosti:
		List<Set<BitSet>> pFunction = buildPFunction(table, coveredMinterms);

		if (LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "");
			LOG.log(Level.FINER, "p funkcija je: ");
			LOG.log(Level.FINER, pFunction.toString());
		}

		// Pronađi minimalne dopune:
		Set<BitSet> minset = findMinimalSet(pFunction);

		if (LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "");
			LOG.log(Level.FINER, "Minimalna pokrivanja još trebaju:");
			LOG.log(Level.FINER, minset.toString());
		}

		// Izgradi minimalne zapise funkcije:
		List<Set<Mask>> minimalForms = new ArrayList<>();
		if (minset.isEmpty()) {
			minimalForms.add(importantSet);
		}
		
		for (BitSet bs : minset) {
			Set<Mask> set = new LinkedHashSet<>(importantSet);
			bs.stream().forEach(i -> set.add(implicants[i]));
			minimalForms.add(set);
		}

		if (LOG.isLoggable(Level.FINE)) {
			logMinimalForms(Level.FINE, minimalForms);
		}

		return minimalForms;
	}

	/**
	 * Supplementary method that logs minimal forms.
	 * 
	 * @param level
	 *            logging level
	 * 
	 * @param minimalForms
	 *            list of possible minimal forms
	 */
	private void logMinimalForms(Level level, List<Set<Mask>> minimalForms) {
		LOG.log(level, "");
		LOG.log(level, "Minimalni oblici funkcije su:");

		for (int i = 0; i < minimalForms.size(); i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%d. ", i + 1));
			sb.append(minimalForms.get(i).toString());

			LOG.log(level, sb.toString());
		}
	}

	/**
	 * Builds cover table
	 * 
	 * @param implicants
	 *            primary implicants
	 * @param minterms
	 *            set of minterms
	 * @param mintermToColumnMap
	 *            map that maps minterm index with column index
	 * @return cover table
	 */
	private boolean[][] buildCoverTable(Mask[] implicants, Integer[] minterms,
			Map<Integer, Integer> mintermToColumnMap) {
		boolean[][] table = new boolean[implicants.length][minterms.length];

		for (int i = 0; i < implicants.length; i++) {
			Set<Integer> indexes = implicants[i].getIndexes();

			for (Integer index : indexes) {
				if (dontCareSet.contains(index)) {
					continue;
				}

				table[i][mintermToColumnMap.get(index)] = true;
			}
		}

		return table;
	}

	/**
	 * Mthod that finds and return set of important primary implicants.
	 * 
	 * @param implicants
	 *            primary implicants
	 * @param mintermToColumnMap
	 *            map that maps minterm index with column index
	 * @param table
	 *            cover table
	 * @param coveredMinterm
	 *            minterms that are covered
	 * @return mask of important primary implicants
	 */
	private Set<Mask> selectImportantPrimaryImplicants(Mask[] implicants, Map<Integer, Integer> mintermToColumnMap,
			boolean[][] table, boolean[] coveredMinterm) {
		Set<Mask> importantPrimary = new LinkedHashSet<>();

		for (int i = 0; i < coveredMinterm.length; i++) {
			int position = -1;
			boolean found = false;

			for (int j = 0; j < implicants.length; j++) {
				if (table[j][i]) {
					if (!found) {
						found = true;
						position = j;
					} else {
						found = false;
						break;
					}
				}
			}

			if (found) {
				implicants[position].getIndexes().forEach(index -> {
					if (!dontCareSet.contains(index)) {
						int column = mintermToColumnMap.get(index);
						coveredMinterm[column] = true;
					}

				});

				importantPrimary.add(implicants[position]);
			}
		}

		return importantPrimary;

	}

	/**
	 * Method that finds coverage function. <br>
	 * For example: p(P0, ..., P7) = (P0 + P1) * (P0 + P2) * (P2 + P3)
	 * 
	 * @param table
	 *            cover table
	 * @param coveredMinterms
	 *            minterms that are covered
	 * @return returns coverage function
	 * @throws IllegalArgumentException
	 *             if any argument is null
	 */
	private List<Set<BitSet>> buildPFunction(boolean[][] table, boolean[] coveredMinterms) {
		if (table == null || coveredMinterms == null) {
			throw new IllegalArgumentException("Arguments can not be null.");
		}

		List<Set<BitSet>> sumProduct = new ArrayList<>();
		int numberOfImplicants = table.length;

		for (int i = 0; i < coveredMinterms.length; i++) {
			if (!coveredMinterms[i]) {
				Set<BitSet> sum = new LinkedHashSet<>();

				for (int j = 0; j < numberOfImplicants; j++) {
					if (table[j][i]) {
						BitSet implicant = new BitSet();
						implicant.set(j);

						sum.add(implicant);
					}
				}

				sumProduct.add(sum);
			}
		}
		return sumProduct;
	}

	/**
	 * Find minimal set of implicants that cover uncovered minterms.
	 * 
	 * @param pFunction
	 *            product of sums
	 * @return minimal set of implicants
	 */
	private Set<BitSet> findMinimalSet(List<Set<BitSet>> pFunction) {
		Set<BitSet> tmpSet = new LinkedHashSet<>();

		if (pFunction.isEmpty()) {
			return tmpSet;
		}

		tmpSet.addAll(pFunction.get(0));

		for (int i = 1; i < pFunction.size(); i++) {
			tmpSet = multiply(tmpSet, pFunction.get(i));
		}

		if (LOG.isLoggable(Level.FINER)) {
			LOG.log(Level.FINER, "");
			LOG.log(Level.FINER, "Nakon prevorbe p-funkcije u sumu produkata: ");
			LOG.log(Level.FINER, tmpSet.toString());
		}

		int minElements = 0;
		Set<BitSet> minimalSet = new LinkedHashSet<>();

		for (BitSet bitSet : tmpSet) {
			minElements = bitSet.cardinality();
			minimalSet.add(bitSet);
			break;
		}

		for (BitSet bitSet : tmpSet) {
			int cardinality = bitSet.cardinality();

			if (cardinality < minElements) {
				minimalSet.clear();
				minimalSet.add(bitSet);

				minElements = bitSet.cardinality();
			} else if (cardinality == minElements) {
				minimalSet.add(bitSet);
			}
		}

		return minimalSet;
	}

	/**
	 * Supplementary method that multiples two sum expression. <br>
	 * For example: (P0 + P1) * (P0 + P2) = P0 + P0P2 + P0P1 + P1P2
	 * 
	 * @param first
	 *            first sum
	 * @param second
	 *            second sum
	 * @return multiplied expression
	 */
	private Set<BitSet> multiply(Set<BitSet> first, Set<BitSet> second) {
		Set<BitSet> product = new LinkedHashSet<>();

		for (BitSet bitSet1 : first) {
			for (BitSet bitSet2 : second) {
				BitSet bitSetProduct = new BitSet();
				bitSetProduct = (BitSet) bitSet1.clone();
				bitSetProduct.or(bitSet2);
				product.add(bitSetProduct);
			}
		}
		first.forEach(bitSet1 -> {
			second.forEach(bitSet2 -> {
				BitSet bitSetProduct = new BitSet();
				bitSetProduct = (BitSet) bitSet1.clone();
				bitSetProduct.or(bitSet2);
				product.add(bitSetProduct);
			});
		});

		return product;
	}

	/**
	 * Getter for minimal forms
	 * 
	 * @return list of minimal forms; List<Set<Mask>>
	 */
	public List<Set<Mask>> getMinimalForms() {
		return minimalForms;
	}

	/**
	 * Method that for each minimal form creates list of expression.
	 * 
	 * @return list of boolean expressions
	 */
	public List<Node> getMinimalFormsAsExpressions() {
		List<Node> minimalFormsExpression = new ArrayList<>();

		for (Set<Mask> maskSet : minimalForms) {
			List<Node> orNodeList = new ArrayList<>();

			for (Mask mask : maskSet) {
				List<Node> andNodeList = new ArrayList<>();

				for (int i = 0; i < mask.size(); i++) {
					Node variable = null;

					if (mask.getValueAt(i) != 2) {
						variable = new VariableNode(variables.get(i));
					}

					if (mask.getValueAt(i) == 1) {
						andNodeList.add(variable);
					} else if (mask.getValueAt(i) == 0) {
						andNodeList.add(new UnaryOperatorNode("NOT", variable, v -> !v));
					}
				}

				if (andNodeList.size() == 1) {
					orNodeList.addAll(andNodeList);
					continue;
				}

				Node andOperator = new BinaryOperatorNode("AND", andNodeList, (a, b) -> a && b);
				orNodeList.add(andOperator);
			}

			if (orNodeList.size() == 1) {
				minimalFormsExpression.addAll(orNodeList);
				continue;
			}

			Node orOperator = new BinaryOperatorNode("OR", orNodeList, (a, b) -> a || b);
			minimalFormsExpression.add(orOperator);
		}

		return minimalFormsExpression;
	}

	/**
	 * Method creates list of strings, one string for each minimal form. Parsing
	 * those forms we get function that has the same minterms as initial one.
	 * 
	 * @return list of minimal forms as strings
	 */
	public List<String> getMinimalFormsAsString() {
		List<Node> minimalFormsExpression = getMinimalFormsAsExpressions();
		ExpressionStringGetter stringGetter = new ExpressionStringGetter();
		List<String> expressions = new ArrayList<>();

		for (Node node : minimalFormsExpression) {
			stringGetter.start();

			node.accept(stringGetter);

			expressions.add(stringGetter.getExpressionString());
		}

		return expressions;
	}
}