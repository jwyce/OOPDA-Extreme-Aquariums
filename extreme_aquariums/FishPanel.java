package extreme_aquariums;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A panel which displays all fish of a certain type.
 * Allows user to add fish to their tank
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see FishShop
 * @version 4.25.2017
 *
 */

public class FishPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Fish> availableFish;

	/**
	 * @param availableFish a list of all available fish
	 * @param fishType the subclass of fish for this instance of panel
	 */
	public FishPanel(ArrayList<Fish> availableFish, Class<? extends Fish> fishType) {
		this.availableFish = availableFish;
		this.setLayout(new BorderLayout());
		this.addComponents(fishType);
	}

	private void addComponents(Class<? extends Fish> fishType) {
		JPanel fishIcon = new JPanel();
		JPanel fishInfo = new JPanel();
		JPanel purchasePanel = new JPanel();
		
		fishIcon.setLayout(new GridLayout(0, 1));
		fishInfo.setLayout(new GridLayout(0, 1));
		purchasePanel.setLayout(new GridLayout(0, 1));
		
		for(Fish fish : availableFish) {
			if(fishType.isInstance(fish)) {
				JPanel infoPanel = new JPanel();
				JPanel addToTankPanel = new JPanel();
				
				JLabel fishName = new JLabel(fish.getName());
				JLabel fishBehavior = new JLabel(fish.getBehavior().name());
				JLabel fishSwimArea = new JLabel(fish.getSwimArea().name() + " swimmer");
				fishName.setVerticalAlignment(JLabel.CENTER);
				fishBehavior.setVerticalAlignment(JLabel.CENTER);
				fishSwimArea.setVerticalAlignment(JLabel.CENTER);
				
				infoPanel.setLayout(new GridLayout(1, 3));
				infoPanel.add(fishName);
				infoPanel.add(fishBehavior);
				infoPanel.add(fishSwimArea);
				
				JButton purchaseBtn = new JButton("Add " + fish.getName() + " to tank");
				purchaseBtn.addActionListener(purchaseListener);
				addToTankPanel.setLayout(new GridBagLayout());
				addToTankPanel.add(purchaseBtn);
				
				fishIcon.add(new JLabel(new ImageIcon(fish.getImage())));
				fishInfo.add(infoPanel);
				purchasePanel.add(addToTankPanel);
			}
			
			this.add(fishIcon, BorderLayout.WEST);
			this.add(fishInfo, BorderLayout.CENTER);
			this.add(purchasePanel, BorderLayout.EAST);
		}
		
	}
	
	ActionListener purchaseListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fishConsidered = e.getActionCommand().substring(4, 4 + e.getActionCommand().length() - 12);
			String[] options = generateSeq(20);
			String response = (String) JOptionPane.showInputDialog(null, "How many " + fishConsidered + " would you like to add to the tank?", 
					"Add to tank", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(response != null) {
				for(Fish fish : availableFish) {
					if(fish.getName().equals(fishConsidered)) {
						if(fish instanceof SaltwaterFish) 
							FishShop.freshwaterBtn.setEnabled(false);
						if(fish instanceof FreshwaterFish)
							FishShop.saltwaterBtn.setEnabled(false);
							
						for(int i = 0; i < Integer.parseInt(response); i++) {
							AquariumApp.getTank().addFish(fish);
						}
					}
				}
			}
			FishShop.environmentMeter.repaint();
		}
		
		private String[] generateSeq(int num) {
			String seq[] = new String[num + 1];
			for(int i = 1; i < num + 1; i++) {
				seq[i - 1] = Integer.toString(i);
			}
			return seq;
		}
		
	};
	
}
