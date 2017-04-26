package extreme_aquariums;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Function;

import javax.imageio.ImageIO;

/**
 * Models the user's fishtank. Contains a collection of fish, 
 * an image, and variables describing the environment.
 * Includes an implementation for rendering the tank using graphics.
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see Fish
 * @see Renderable
 * @see Graphics
 * @see Serializable
 * @version 4.25.2017
 *
 */

public class CustomerTank implements Renderable, Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Fish> tank = new ArrayList<Fish>(50);
	private transient BufferedImage tankImage;
	private String imagePath;
	private int oxygenLevel, carbonDioxideLevel, waterHardness;
	private float pHLevel;

	/**
	 * Creates a stock tank for the customer
	 * @param imagePath is the relative file path for a fishtank image
	 * @see BufferedImage
	 */
	public CustomerTank(String imagePath) {
		this.imagePath = imagePath;
		
		try {
			tankImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.emptyTank();
	}
	
	/**
	 * Adds a fish to the CustomerTank collection of fish.
	 * Updates the tank environment variables
	 * @param fish is the fish to be added to the tank
	 */
	public void addFish(Fish fish) {
		tank.add(fish);
		
		oxygenLevel -= oxygenConsumption.apply(fish);
		carbonDioxideLevel += oxygenConsumption.apply(fish);
		pHLevel += calculateBasicity.apply(fish);
		waterHardness += calculateWaterHardness.apply(fish);
		
		oxygenLevel = (int) clamp(oxygenLevel, 0, 100);
		carbonDioxideLevel = (int) clamp(carbonDioxideLevel, 0, 100);
		pHLevel = clamp(pHLevel, 0, 14);
		waterHardness = (int) clamp(waterHardness, 0, 180);
	}
	
	/**
	 * @return the collection of fish in the user's tank
	 * @see Fish
	 */
	public ArrayList<Fish> getFish() {
		return tank;
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(tankImage, 0, 0, null);
	}
	
	public Function<Fish, Float> calculateBasicity = (Function<Fish, Float> & Serializable) f -> {
		float agroLevel = 0;
		
		switch(f.getBehavior()) {
		case aggressive:
			agroLevel = .3f;
			break;
		case semiaggressive:
			agroLevel = .2f;
			break;
		case peaceful:
			agroLevel = .1f;
			break;
		}
		
		if(f instanceof SaltwaterFish)
			return 2 * agroLevel;
		return agroLevel;
	};
	
	public Function<Fish, Integer> calculateWaterHardness = (Function<Fish, Integer> & Serializable) purchasedFish -> {
		int numberOfPurchasedFish = 0;
		
		for(Fish fish : tank) {
			if(fish.getName().equals(purchasedFish.getName())) {
				numberOfPurchasedFish++;
			}
		}
		
		return (int) (Math.pow(2, numberOfPurchasedFish - 1) / numberOfPurchasedFish);
	};
	
	public Function<Fish, Integer> oxygenConsumption = (Function<Fish, Integer> & Serializable) f -> {
		switch(f.getSwimArea()) {
		case bottom:
			return 3;
		case midlevel:
			return 2;
		default:
			return 1;
		}
	};
	
	/**
	 * Returns a number forced into a given range
	 * @param num the number to be clamped
	 * @param min the minimum value for the number
	 * @param max the maximum value for the number
	 * @return a clamped number
	 */
	private float clamp(float num, int min, int max) {
		if(num > max) 
			return max;
		if(num < min)
			return min;
		return num;
	}
	
	/**
	 * Clears the tank's collection of fish.
	 * Resets the environment variables to stock values
	 */
	public void emptyTank() {
		tank.clear();
		oxygenLevel = 100;
		carbonDioxideLevel = 0;
		pHLevel = 7;
		waterHardness = 0;
	}
	
	/**
	 * This method is used to reinitialize the image of the customer's tank
	 * and all fish contained within after deserialization
	 */
	public void refreshImage() {
		try {
			tankImage = ImageIO.read(getClass().getResource(imagePath));
			for(Fish fish : tank) {
				fish.refreshImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return an integer describing percentage of oxygen in the tank
	 */
	public int getOxygenLevel() {
		return oxygenLevel;
	}
	
	/**
	 * @return an integer describing percentage of carbon dioxide in the tank
	 */
	public int getCarbonDioxideLevel() {
		return carbonDioxideLevel;
	}
	
	/**
	 * @return a float describing the pH of the tank
	 */
	public float getPHLevel() {
		return pHLevel;
	}
	
	/**
	 * @return an integer describing percentage of ammonia in the tank
	 */
	public int getAmmoniaPercentage() {
		return (int) (clamp(100 * pHLevel / 11, 0, 100));
	}
	
	/**
	 * 
	 * @return an integer describing the water hardness of the tank
	 */
	public int getWaterHardess() {
		return waterHardness;
	}
	
	/**
	 * @return the amount of fish in the tank
	 */
	public int getSize() {
		return tank.size();
	}
	
	/**
	 * @return the width of the tank image
	 * @see BufferedImage
	 */
	public int getWidth() {
		return tankImage.getWidth();
	}
	
	/**
	 * @return the height of the tank image
	 * @see BufferedImage
	 */
	public int getHeight() {
		return tankImage.getHeight();
	}
	
}
