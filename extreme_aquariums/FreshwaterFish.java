package extreme_aquariums;

/**
 * A type of fish modeling a freshwater fish
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see Fish
 * @version 4.25.2017
 *
 */

public class FreshwaterFish extends Fish {

	private static final long serialVersionUID = 1L;

	public FreshwaterFish(String name, Behavior behavior, SwimmingArea swimArea, String filePath) {
		super(name, behavior, swimArea, filePath);
	}
	
}
