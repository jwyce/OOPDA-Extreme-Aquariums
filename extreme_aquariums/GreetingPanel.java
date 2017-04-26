package extreme_aquariums;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simply a panel which welcomes the user to the shop
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @version 4.25.2017
 *
 */

public class GreetingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public GreetingPanel() {
		JLabel greeting = new JLabel("Welcome to Acme Aquarium Inc!");
	    this.add(greeting);
	}

}
