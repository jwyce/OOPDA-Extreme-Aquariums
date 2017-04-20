package extreme_aquariums;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TankPreview extends JFrame { 

	private static final long serialVersionUID = 1L;
	private Renderer renderer;

	public TankPreview() {
		renderer = new Renderer();
		
		this.buildFrame();
		this.add(renderer);
		renderer.repaint();
		this.setVisible(true);
	}

	private void buildFrame() {
		this.setTitle("Tank Preview");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(AquariumApp.getTank().getWidth(), AquariumApp.getTank().getHeight()));
	}

	private void render(Graphics2D g) {
		AquariumApp.getTank().render(g);
		for(Fish fish : AquariumApp.getTank().getFish()) {
			fish.render(g);
		}
		FishShop.environmentMeter.render(g);
	}
	
	public class Renderer extends JPanel {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			TankPreview.this.render((Graphics2D) g);
		}
	}

}
