package hr.fer.zemris.java.hw01;

import java.util.Scanner;


/**
 * Program koji ispisuje faktorijel cijelog broja koji mora biti u rasponu od 1 do 20 i
 * koji se unosi preko tipkovnice. Rad programa zavrsava se nakon unosa "kraj".
 *
 * @author Tina Maric
 * @version 1.0
 */


public class Factorial {

	
	/**
   * Metoda od koje kreće izvođenje programa.
   *
   * @param args argumenti zadani preko naredbenog retka. Ne koriste se.
   */
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.printf("Unesite broj > ");
			if(sc.hasNextLong()) {
				long current = sc.nextLong();
				if(current < 1 || current > 20) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", current);
				} else {
					long factorial = calculateFactorial(current);
					System.out.printf("%d! = %d%n", current, factorial);
				}
			} else  {
				String elem = sc.next();
				if(elem.equals("kraj")) {
					System.out.printf("Dovidjenja.");
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", elem);
			} 
		}
		sc.close();
	}
	
	
	/**
   * Metoda koja racuna faktorijel broja.
   *
   *  @param  number broj ciji faktorijel racunamo
   *  @return izracunata faktorijel vrijednost ukoliko je argument
   *  nenegativan cijeli broj manji ili jednak 20, u suptornom -1
   */
	
	public static long calculateFactorial(long number) {
		if(number < 0 || number > 20) {
			return -1;
		}
		if(number == 1 || number == 0) {
			return 1;
		}
		return number * calculateFactorial(number - 1);
	}
	

}

