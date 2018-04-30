package entities;

import requests.RequestFactory;
import requests.RequestQueue;

/**
 * A class representing a entities.Trash object. entities.Trash is a type of Entitiy.
 * @author Zelinsky
 */
public class Trash extends Entity {

	private RequestQueue requestQueue;


	public enum TrashType{
		AGRICULTURAL,
		INDUSTRIAL,
		COMMERCIAL;
	}

	private int pollutionCount;

	private final int POLLUTION = 10;
	private TrashType type;
	
	private boolean thrown = false;
	private boolean addedPollution = false;
	
	/**
	 * Constructs a entities.Trash object. Calls entities.Entity's constructor with super(x, y, width, height).
	 * Sets up the entities.Trash's requests.RequestQueue.
	 * @param x The x position of the entities.Trash
	 * @param y The y position of the entities.Trash
	 * @param width The width of the entities.Trash
	 * @param height The height of the entities.Trash
	 * @param requestQueue The requests.RequestQueue of the entities.Trash
	 * @see RequestQueue
	 */
	Trash(int x, int y, int width, int height, RequestQueue requestQueue) {
		super(x, y, width, height);
		//System.out.println(String.format(
		//        "entities.Trash: width=%d height=%d",
		//        width, height));
		this.requestQueue = requestQueue;
	}
	
	/**
 	 * Translates the entities.Trash's location a distance specified by the change in x and y.
	 * Once entities.Trash touches the bottom of the worldBounds, it will stop moving and increment the pollution level in Model.
	 * If entities.Trash touches the top of the worldBounds, it will be removed from the game and increment the score.
	 * @param dx The distance to translate the entities.Trash in the x direction
	 * @param dy The distance to translate the entities.Trash in the y direction
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
					RequestFactory.createRemoveFromModelRequest(this)
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
	 * Handles how the entities.Trash should be thrown. Sets the entities.Trash's speed to the specified x and y speeds.
	 * @param xSpeed The speed of the entities.Trash in the x direction
	 * @param ySpeed The speed of the entities.Trash in the y direction
	 */
	public void throwTrash(int xSpeed, int ySpeed) {
		thrown = true;
		setSpeed(xSpeed, ySpeed);
	}
	
	/**
	 * Returns the state of the entities.Trash in regards to if it has been thrown with throwTrash().
	 * @return True if the entities.Trash has been thrown, false otherwise
	 */
	public boolean thrown() {
		return thrown;
	}
}