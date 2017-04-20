package extreme_aquariums;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.function.Function;

import javax.imageio.ImageIO;

public abstract class Fish implements Renderable, Serializable {

	private static final long serialVersionUID = 1L;
	private String name, imagePath;
	private transient BufferedImage image;
	private Behavior behavior;
	private SwimmingArea swimArea;
	private Random rand = new Random();
	
	protected enum Behavior {
		peaceful, semiaggressive, aggressive
	};
	protected enum SwimmingArea {
		top, midlevel, bottom
	};
	
	private Function<SwimmingArea, Integer> swimAreaAsInt = (Function<SwimmingArea, Integer> & Serializable) b -> {
		switch(b) {
		case bottom:
			return 2;
		case midlevel:
			return 1;
		case top:
			return 0;
		default:
			return -1;
		}
	};
	
	public Fish(String name, Behavior behavior, SwimmingArea swimArea, String filePath) {
		this.name = name;
		this.behavior = behavior;
		this.swimArea = swimArea;
		imagePath = filePath;
		try {
			image = ImageIO.read(getClass().getResource(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics g) {
		int swimAreaHeight = AquariumApp.getTank().getHeight()/3;
		int x = rand.nextInt(AquariumApp.getTank().getWidth() - image.getWidth());
		int y = rand.nextInt(swimAreaHeight - image.getHeight()) + swimAreaAsInt.apply(swimArea)*swimAreaHeight;
		
		if(rand.nextBoolean()) {
			g.drawImage(image, x + image.getWidth(), y, -image.getWidth(), image.getHeight(), null);
		} else {
			g.drawImage(image, x, y, null);
		}
	}
	
	public void refreshImage() {
		try {
			image = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public SwimmingArea getSwimArea() {
		return swimArea;
	}
	
	public Behavior getBehavior() {
		return behavior;
	}
	
	public String getName() {
		return name;
	}
	
}
