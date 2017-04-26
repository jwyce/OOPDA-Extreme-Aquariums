package extreme_aquariums;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A GUI which allows the user to add fish of their selection to their tank,
 * preview their tank, save a tank for later, open an existing tank, or simply to try again
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see EnvironmentMeter
 * @see FishPanel
 * @version 4.25.2017
 *
 */

public class FishShop extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Fish> availableFish;
	
	private GreetingPanel greetingPanel = new GreetingPanel();
	private JScrollPane freshwaterFishPane;
	private JScrollPane saltwaterFishPane;
	
	public static JRadioButton freshwaterBtn = new JRadioButton("Freshwater Fish");
	public static JRadioButton saltwaterBtn = new JRadioButton("Saltwater Fish");
	public static EnvironmentMeter environmentMeter = new EnvironmentMeter();
	
	private JButton previewTank = new JButton("Preview Tank");
	private JButton saveTank = new JButton("Save Tank");
	private JButton generateText = new JButton("Generate Receipt");
	private JButton openTank = new JButton("Open Tank");

	/**
	 * @param availableFish a list of all available fish in the shop
	 */
	public FishShop(ArrayList<Fish> availableFish) {
		this.availableFish = availableFish;
		freshwaterFishPane = new JScrollPane(new FishPanel(availableFish, FreshwaterFish.class));
		saltwaterFishPane = new JScrollPane(new FishPanel(availableFish, SaltwaterFish.class));
		this.buildFrame();
		this.addComponents();
		this.setVisible(true);
	}

	private void buildFrame() {
		this.setTitle("Fish Shop");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(750, 700));
	}

	private void addComponents() {
		freshwaterFishPane.getVerticalScrollBar().setUnitIncrement(16);
		saltwaterFishPane.getVerticalScrollBar().setUnitIncrement(16);

		ButtonGroup bg = new ButtonGroup();
		bg.add(freshwaterBtn);
		bg.add(saltwaterBtn);
		freshwaterBtn.setSelected(true);
		freshwaterBtn.addActionListener(radioListener);
		saltwaterBtn.addActionListener(radioListener);

		JPanel fishType = new JPanel();
		fishType.add(new JLabel("Which type of fish are you looking for?"));
		fishType.add(freshwaterBtn);
		fishType.add(saltwaterBtn);

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 1));
		northPanel.add(greetingPanel);
		northPanel.add(fishType);

		JButton retry = new JButton("Try again?");
		retry.addActionListener(tryAgainListener);
		previewTank.addActionListener(previewListener);
		saveTank.addActionListener(saveListener);
		generateText.addActionListener(receiptListener);
		openTank.addActionListener(openListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 2));

		buttonPanel.add(previewTank);
		buttonPanel.add(generateText);
		buttonPanel.add(saveTank);
		buttonPanel.add(openTank);
		buttonPanel.add(retry);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(environmentMeter, BorderLayout.WEST);
		southPanel.add(buttonPanel, BorderLayout.EAST);

		this.add(northPanel, BorderLayout.NORTH);
		this.add(freshwaterFishPane, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	ActionListener openListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			CustomerTank currentTank = Serialize.open();		// deserialize tank
			if (currentTank != null) {
				AquariumApp.openExistingTank(currentTank);		// reinitialize customer's tank
				AquariumApp.getTank().refreshImage();			// reinitialize all image variables
				if(AquariumApp.getTank().getSize() != 0) {		// determine the configuration of radio buttons
					Fish fish = AquariumApp.getTank().getFish().get(0);
					if(fish instanceof SaltwaterFish) {
						FishShop.freshwaterBtn.setSelected(false);
						FishShop.saltwaterBtn.setSelected(true);
						radioListener.actionPerformed(e);
						FishShop.saltwaterBtn.setEnabled(true);
						FishShop.freshwaterBtn.setEnabled(false);
					} else if(fish instanceof FreshwaterFish) {
						FishShop.saltwaterBtn.setSelected(false);
						FishShop.freshwaterBtn.setSelected(true);
						radioListener.actionPerformed(e);
						FishShop.saltwaterBtn.setEnabled(false);
						FishShop.freshwaterBtn.setEnabled(true);
					}
				}
			}
			FishShop.environmentMeter.repaint();				// update environment meter
		}
	};

	ActionListener saveListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			Serialize.save(AquariumApp.getTank());
		}
	};

	ActionListener receiptListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg) {
			try {
				validateTankEnvironment();
				GenerateTankDescription.printFile(AquariumApp.getTank());
			} catch (EnvironmentUnbalancedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage() + " Please try again.", "Tank Unbalanced!", JOptionPane.ERROR_MESSAGE);
				restart();
			}
		}
	};
	
	ActionListener tryAgainListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			restart();
		}
	};

	ActionListener previewListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg) {
			try {
				validateTankEnvironment();
				FishShop.this.setEnabled(false);
				TankPreview tank = new TankPreview();
				tank.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				tank.addWindowListener(new WindowAdapter() { 		// detects TankPreview close and re-enables FishShop panel.
					@Override
					public void windowClosing(WindowEvent we) {
						FishShop.this.setEnabled(true);
					}
				});
			} catch (EnvironmentUnbalancedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage() + " Please try again.", "Tank Unbalanced!", JOptionPane.ERROR_MESSAGE);
				restart();
			}
		}
	};

	ActionListener radioListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {			
			if(freshwaterBtn.isSelected()) {
				FishShop.this.remove(saltwaterFishPane);
				FishShop.this.add(freshwaterFishPane, BorderLayout.CENTER);
			}
			if(saltwaterBtn.isSelected()) {
				FishShop.this.remove(freshwaterFishPane);
				FishShop.this.add(saltwaterFishPane, BorderLayout.CENTER);
			}
			SwingUtilities.updateComponentTreeUI(FishShop.this);
		}
	};
	
	/**
	 * This method will throw an exception if the user's tank environment is unbalanced
	 * @throws EnvironmentUnbalancedException
	 */
	private void validateTankEnvironment() {
		if(AquariumApp.getTank().getWaterHardess() > 119)
			throw new EnvironmentUnbalancedException("Water is too hard!");
		if(AquariumApp.getTank().getOxygenLevel() < AquariumApp.getTank().getCarbonDioxideLevel()) 
			throw new EnvironmentUnbalancedException("Not enough oxygen!");
		if(AquariumApp.getTank().getAmmoniaPercentage() > 95)
			throw new EnvironmentUnbalancedException("Water is too basic!");
	}
	
	/**
	 * This method restarts the application. Useful if the user creates an unbalanced environment
	 * and wishes to retry
	 */
	private void restart() {
		AquariumApp.getTank().emptyTank();
		FishShop.freshwaterBtn.setEnabled(true);
		FishShop.saltwaterBtn.setEnabled(true);
		FishShop.this.dispose();
		new FishShop(availableFish);
	}

}
