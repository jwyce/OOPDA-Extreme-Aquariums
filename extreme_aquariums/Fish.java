package extreme_aquariums;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.function.Function;

import javax.imageio.ImageIO;

/**
 * Models a fish object. Contains information about its name, 
 * behavior, preferred swimming area, and image.
 * Includes an implementation for rendering a fish using graphics.
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see Renderable
 * @see Graphics
 * @see Serializable
 * @version 4.25.2017
 *
 */

public abstract class Fish implements Renderable, Serializable {

	private static final long serialVersionUID = 1L;
	protected enum Behavior { peaceful, semiaggressive, aggressive };
	protected enum SwimmingArea { top, midlevel, bottom };
	private String name, imagePath;
	private transient BufferedImage image;
	private Behavior behavior;
	private SwimmingArea swimArea;
	private Random rand = new Random();
	
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
	
	/**
	 * Super constructor for all fish
	 * 
	 * @param name is a String for the name of the fish
	 * @param behavior is the enumeration of behavior for the fish
	 * @param swimArea is the enumeration of preferred swimming area for the fish
	 * @param filePath is the relative file path for image of the fish 
	 */
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
	
	/**
	 * This method is used to reinitialize the image
	 * of the fish after deserialization
	 */
	public void refreshImage() {
		try {
			image = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return an image for the fish
	 * @see BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * @return an enumeration of the preferred swimming area of the fish
	 */
	public SwimmingArea getSwimArea() {
		return swimArea;
	}
	
	/**
	 * @return an enumeration of the behavior of the fish
	 */
	public Behavior getBehavior() {
		return behavior;
	}
	
	/**
	 * @return the name of the fish
	 */
	public String getName() {
		return name;
	}
	
}
