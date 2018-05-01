/**
 * A class representing a Trash object. Trash is a type of Entitiy.
 * @author Zelinsky
 */
public class Trash extends Entity {

	private RequestQueue requestQueue;

	private final double BOUNCE_OFFSET = 0.3;

	public enum TrashType{
		AGRICULTURAL,
		INDUSTRIAL,
		COMMERCIAL;
	}

	private int pollutionCount;

	private final int POLLUTION = 5;
	private TrashType type;
	
	private boolean thrown = false;
	private boolean addedPollution = false;
	
	/**
	 * Constructs a Trash object. Calls Entity's constructor with super(x, y, width, height).
	 * Sets up the Trash's RequestQueue.
	 * @param x The x position of the Trash
	 * @param y The y position of the Trash
	 * @param width The width of the Trash
	 * @param height The height of the Trash
	 * @param requestQueue The RequestQueue of the Trash
	 * @see RequestQueue
	 */
	Trash(int x, int y, int width, int height, RequestQueue requestQueue) {
		super(x, y, width, height);
		//System.out.println(String.format(
		//        "Trash: width=%d height=%d",
		//        width, height));
		this.requestQueue = requestQueue;
	}
	
	/**
 	 * Translates the Trash's location a distance specified by the change in x and y.
	 * Once Trash touches the bottom of the worldBounds, it will stop moving and increment the pollution level in Model.
	 * If Trash touches the top of the worldBounds, it will be removed from the game and increment the score.
	 * @param dx The distance to translate the Trash in the x direction
	 * @param dy The distance to translate the Trash in the y direction
	 * @see Model
	 */
	@Override
	void translate(double dx, double dy) {
		
		// Bounds check
		if (leftBound() && dx < 0) {
			dx = 0;
		}
		if (rightBound() && dx > 0) {
			dx = 0;
		}
		if (topBound() && dy < 0) {
			requestQueue.postRequest(
					RequestFactory.createRemoveEntityRequest(this)
			);
			requestQueue.postRequest(
					RequestFactory.createUpdateScoreRequest(1)
			);
		}
		if (bottomBound() && dy > 0) {
			dy = 0;
			isAtBottom = true;
			if (!addedPollution) {
				SoundEffect.HIT_GROUND.play();
				requestQueue.postRequest(
						RequestFactory.createUpdatePollutionRequest(POLLUTION)
				);
				addedPollution = true;
			}
		}
		
		getBounds().translate((int) dx, (int) dy);
	}
	
	/**
	 * Handles how the Trash should be thrown. Sets the Trash's speed to the specified x and y speeds.
	 * @param xSpeed The speed of the Trash in the x direction
	 * @param ySpeed The speed of the Trash in the y direction
	 */
	public void throwTrash(int xSpeed, int ySpeed) {
		thrown = true;
		setSpeed(xSpeed, ySpeed);
	}
	
	/**
	 * Returns the state of the Trash in regards to if it has been thrown with throwTrash().
	 * @return True if the Trash has been thrown, false otherwise
	 */
	public boolean thrown() {
		return thrown;
	}

	/**
	 * Set thrown back to false (used to make trash that has been thrown throwable again, if it is falling again
	 */
	public void resetThrown() { setThrown(false); }

	/**
	 * Set the "thrown" parameter
	 * @param t new value for thrown
	 */
	public void setThrown(boolean t) { thrown = t; }

	public void bounceTrash(Trash t) {
		double totalYMomentum = t.getYSpeed() + getYSpeed();
		double totalXMomentum = t.getXSpeed() + getXSpeed();
		double xDifference = Math.max(3, (t.getBounds().getX() - getBounds().getX()));
		System.out.println(xDifference);
		double angle = xDifference * BOUNCE_OFFSET;
		if (t.getBounds().getX() < getBounds().getX()) {
			t.setSpeed(totalXMomentum / 2 - angle, totalYMomentum / 2 + BOUNCE_OFFSET);
			setSpeed(totalXMomentum / 2 + angle, totalYMomentum / 2 + BOUNCE_OFFSET);
		} else {
			t.setSpeed(totalXMomentum / 2 + angle, totalYMomentum / 2 + BOUNCE_OFFSET);
			setSpeed(totalXMomentum / 2 - angle, totalYMomentum / 2 + BOUNCE_OFFSET);
		}
	}


}