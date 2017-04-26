package extreme_aquariums;

/**
 * A custom exception which should be thrown any time
 * there is an unbalance in the tank environment variables
 * 
 * @author Jared Wyce
 * @author Jared Hamlin
 * @author Josh Ginchereau
 * @author Will Hou
 * @see RuntimeException
 * @version 4.25.2017
 *
 */

public class EnvironmentUnbalancedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnvironmentUnbalancedException(String message) {
		super(message);
	}
}
