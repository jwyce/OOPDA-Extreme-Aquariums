package extreme_aquariums;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Function;

import javax.imageio.ImageIO;

public class CustomerTank implements Renderable, Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Fish> tank = new ArrayList<Fish>(50);
	private transient BufferedImage tankImage;
	private String imagePath;
	private int oxygenLevel, carbonDioxideLevel, waterHardness;
	private float pHLevel;

	public CustomerTank(String imagePath) {
		this.imagePath = imagePath;
		
		try {
			tankImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.emptyTank();
	}
	
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
	
	public int getAmmoniaPercentage() {
		return (int) (clamp(100 * pHLevel / 11, 0, 100));
	}
	
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
	
	private float clamp(float num, int min, int max) {
		if(num > max) 
			return max;
		if(num < min)
			return min;
		return num;
	}
	
	public void emptyTank() {
		tank.clear();
		oxygenLevel = 100;
		carbonDioxideLevel = 0;
		pHLevel = 7;
		waterHardness = 0;
	}
	
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
	
	public int getOxygenLevel() {
		return oxygenLevel;
	}
	public int getCarbonDioxideLevel() {
		return carbonDioxideLevel;
	}
	public float getPHLevel() {
		return pHLevel;
	}
	public int getWaterHardess() {
		return waterHardness;
	}
	
	public int getSize() {
		return tank.size();
	}
	
	public int getWidth() {
		return tankImage.getWidth();
	}
	
	public int getHeight() {
		return tankImage.getHeight();
	}
	
}
