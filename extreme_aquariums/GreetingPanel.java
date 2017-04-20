package extreme_aquariums;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GreetingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public GreetingPanel() {
		JLabel greeting = new JLabel("Welcome to Acme Aquarium Inc!");
	    this.add(greeting);
	}

}
