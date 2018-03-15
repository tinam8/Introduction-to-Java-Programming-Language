package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program u kojem kosrisnik sa tipkovnice unosi broj po broj i dodaje ih u
 * binarno stablo ako tamo vec ne postoje (sto se dogodilo ispisuje na zaslon).
 * Korisnik unos prekida utipkavanjem "kraj". Jednom kad je unos gotov, program
 * ispisuje brojeve naprije sortirano od manjeg prema vecem, a potom u sljedecem
 * retku od veceg prema manjem.
 *
 * @author Tina Maric
 * @version 1.0
 */

public class UniqueNumbers {

	/**
	 * Pomocni rayred koji predstavlja cvor stabla. Sadrzi jednu vrijednost
	 * tipa int.
	 *
	 */

	static class TreeNode {
		/**
		 * lijevo dijete cvora
		 */
		TreeNode left;
		/**
		 * desno dijete cvora
		 */
		TreeNode right;
		/**
		 * vrijednost cvora
		 */
		int value;
	}

	/**
	 * Metoda od koje kreće izvođenje programa.
	 *
	 * @param args
	 *            argumenti zadani preko naredbenog retka. Ne koriste se.
	 */

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode glava = null;

		while (true) {
			System.out.printf("Unesite broj > ");
			if (sc.hasNextInt()) {
				int value = sc.nextInt();
				if (containsValue(glava, value)) {
					System.out.println("Broj vec postoji. Preskacem.");
					continue;
				} else {
					glava = addNode(glava, value);
					System.out.println("Dodano");
				}
			} else {
				String elem = sc.next();
				if (elem.equals("kraj")) {
					break;
				} else {
					System.out.printf("'%s' nije cijeli broj.%n", elem);
				}
			}
		}

		sc.close();
		System.out.printf("Ispis od najmanjeg: ");
		printFromSmallest(glava);
		System.out.println();
		System.out.printf("Ispis od najveceg: ");
		printFromGreatest(glava);
	}

	/**
	 * Metoda kojom se dodaje novi cvor u stablo.
	 *
	 * @param glava
	 *            korijen stabla.
	 * @param value
	 *            vrijednost koju ce sadrzavati novi cvor kojeg dodamo.
	 * @return glava vracamo korijen stabla
	 */

	public static TreeNode addNode(TreeNode glava, int value) {
		if (glava == null) {
			glava = new TreeNode();
			glava.left = null;
			glava.right = null;
			glava.value = value;
			return glava;
		}
		TreeNode temp = glava;
		while (true) {
			if (value == temp.value) {
				return glava;
			} else if (value < temp.value) {
				if (temp.left != null) {
					temp = temp.left;
					continue;
				} else {
					temp.left = new TreeNode();
					temp.left.left = null;
					temp.left.right = null;
					temp.left.value = value;
					return glava;
				}
			} else {
				if (temp.right != null) {
					temp = temp.right;
					continue;
				} else {
					temp.right = new TreeNode();
					temp.right.left = null;
					temp.right.right = null;
					temp.right.value = value;
					return glava;
				}
			}
		}
	}

	/**
	 * Metoda koja vraca velicinu stabla.
	 *
	 * @param glava
	 *            korijen stabla.
	 * @return velicina stabla
	 */

	public static int treeSize(TreeNode glava) {
		if (glava == null) {
			return 0;
		}
		return (1 + treeSize(glava.left) + treeSize(glava.right));
	}

	/**
	 * Metoda koja vraca nalazi li se trazeni element u stablu.
	 *
	 * @param glava
	 *            korijen stabla.
	 * @param value
	 *            vrijednost elementa za kojeg provjeravamo nalizi li se u
	 *            stablu.
	 * @return true ukoliko se elemnt nalazi u stablu inace false
	 */

	public static boolean containsValue(TreeNode glava, int value) {
		while (glava != null) {
			if (glava.value == value) {
				return true;
			} else if (value < glava.value) {
				glava = glava.left;
			} else {
				glava = glava.right;
			}
		}
		return false;
	}

	/**
	 * Metoda koja ispisuje elemente stabla od manjeg ka vecem.
	 *
	 * @param glava
	 *            korijen stabla.
	 */

	public static void printFromSmallest(TreeNode glava) {
		if (glava == null) {
			return;
		}
		printFromSmallest(glava.left);
		System.out.printf(glava.value + " ");
		printFromSmallest(glava.right);
	}

	/**
	 * Metoda koja ispisuje elemente stabla od veceg ka manjem.
	 *
	 * @param glava
	 *            korijen stabla.
	 * 
	 */

	public static void printFromGreatest(TreeNode glava) {
		if (glava == null) {
			return;
		}
		printFromGreatest(glava.right);
		System.out.printf(glava.value + " ");
		printFromGreatest(glava.left);
	}

}
