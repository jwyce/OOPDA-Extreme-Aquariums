package extreme_aquariums;

import java.awt.Graphics;

/**
 * Any class which implements this interface must
 * include an implementation of the render method 
 * so that it can be drawn to a canvas
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see Graphics
 * @version 4.25.2017
 *
 */

public interface Renderable {

	/**
	 * Renders the object by the drawing instructions to a canvas
	 * @param g an instance of Graphics for drawing 
	 */
	void render(Graphics g);
}
