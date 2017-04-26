package extreme_aquariums;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Draws a meter for the customer's tank evironment.
 * Warns user when something is out of balance
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see CustomerTank
 * @see Renderable
 * @version 4.25.2017
 *
 */

public class EnvironmentMeter extends JPanel implements Renderable {

	private static final long serialVersionUID = 1L;
	
	public EnvironmentMeter() {
		this.setPreferredSize(new Dimension(460, 55));
		this.repaint();
	}
	
	@Override
	public void render(Graphics g) {
		int oxygenLevel = AquariumApp.getTank().getOxygenLevel();
		int carbonLevel = AquariumApp.getTank().getCarbonDioxideLevel();
		int ammoniaLevel = AquariumApp.getTank().getAmmoniaPercentage();
		float pH = AquariumApp.getTank().getPHLevel();
		int waterHardness = AquariumApp.getTank().getWaterHardess();
		
		g.setColor(Color.BLACK);
		g.drawString("O2: " + oxygenLevel + "%", 5, 10);
		if(oxygenLevel < 50)
			g.setColor(Color.red);
		else
			g.setColor(Color.GREEN);
		g.fillRect(70, 0, oxygenLevel, 10);
		if(oxygenLevel > 0) {
			if(oxygenLevel < 50)
				g.setColor(Color.red.darker());
			else
				g.setColor(Color.GREEN.darker());
			g.drawRect(70, 0, oxygenLevel, 10);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("CO2: " + carbonLevel + "%", 5, 30);
		if(carbonLevel > 50)
			g.setColor(Color.red);
		else
			g.setColor(Color.BLUE);
		g.fillRect(70, 20, carbonLevel, 10);
		if(carbonLevel > 0) {
			if(carbonLevel > 50)
				g.setColor(Color.red.darker());
			else
				g.setColor(Color.BLUE.darker());
			g.drawRect(70, 20, carbonLevel, 10);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("NH4: " + ammoniaLevel + "%", 5, 50);
		if(ammoniaLevel > 95)
			g.setColor(Color.red);
		else
			g.setColor(Color.MAGENTA);
		g.fillRect(70, 40, ammoniaLevel, 10);
		if(ammoniaLevel > 0) {
			if(ammoniaLevel > 95)
				g.setColor(Color.red.darker());
			else
				g.setColor(Color.MAGENTA.darker());
			g.drawRect(70, 40, ammoniaLevel, 10);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("pH: " + String.format("%.1f", pH), 290, 20);
		if(ammoniaLevel > 95)
			g.setColor(Color.red);
		else
			g.setColor(Color.CYAN);
		g.fillRect(350, 10, (int) (100 * pH / 14), 10);
		if(pH > 0) {
			if(ammoniaLevel > 95)
				g.setColor(Color.red.darker());
			else
				g.setColor(Color.cyan.darker());
			g.drawRect(350, 10, (int) (100 * pH / 14), 10);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("Water Hardness: " + waterHardness + " mg/L", 200, 40);
		if(waterHardness > 119)
			g.setColor(Color.red);
		else
			g.setColor(Color.ORANGE);
		g.fillRect(350, 30, 100 * waterHardness / 180, 10);
		if(waterHardness > 0) {
			if(waterHardness > 119)
				g.setColor(Color.red.darker());
			else
				g.setColor(Color.ORANGE.darker());
			g.drawRect(350, 30, 100 * waterHardness / 180, 10);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		EnvironmentMeter.this.render((Graphics2D) g);
	}

}
