/**
 * A class containing solely a main method
 * @author Zelinsky
 */
public class Main {
	/**
	 * Begins the game.
	 * Constructs a Controller and calls that Controller's start() method.
	 * @param args Any String inputs (none specified)
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
}