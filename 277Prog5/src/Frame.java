import javax.swing.*;
/**
 * This class creates a Frame for the program, extends JFrame.
 * The goal of this program is to capture at least 10 pokemons by shooting pokeballs.
 * You are only given 25 pokeballs!
 * @author Rachel Dang
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{
	/**
	 * Here is the declaration for the Frame
	 */
	private Panel panel;
	
	/**
	 * This is a constructor for the Frame
	 */
	public Frame()
	{
		setBounds(0, 0, 700, 500);
		panel = new Panel();
		getContentPane().add(panel);
		Thread t = new Thread(panel);
		t.start();
	}

	/**
	 * This is the main method for the Frame
	 * @param args
	 */
	public static void main(String [] args)
	{
		Frame f = new Frame();
		f.setTitle("CAPTURE THEM ALL");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		new Frame();
	}
}
