package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Program that takes no command line arguments. When started new window with
 * two lists is open. In each list prime numbers are added.
 * 
 * @author tina
 *
 */
public class PrimDemo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that sets appearance of the window
	 */
	public PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(400, 200);
		setSize(500, 200);
		setTitle("Prozor1");

		initGUI();
	}
	
	/**
	 * Method that adds all GUI elements to the window.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		JButton btn = new JButton("Sljedeci");
		cp.add(btn, BorderLayout.PAGE_END);
		btn.addActionListener( e -> model.next());
		
		JPanel donjaPloca = new JPanel();
		donjaPloca.setLayout(new GridLayout(1,2));
		
		
		
		for (int i = 0; i <2 ; i++) {
			JList<Integer> list = new JList<>(model);
			donjaPloca.add(new JScrollPane(list));
		}
	
		cp.add(donjaPloca, BorderLayout.CENTER);
		
	}
	
	/**
	 * Method that starts program
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> { new PrimDemo().setVisible(true); });
	}
}
