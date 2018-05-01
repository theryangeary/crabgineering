package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import view.audio.SoundEffect;

/**
 * A class representing a model.entities.Trash object. model.entities.Trash is a type of Entitiy.
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
	 * Constructs a model.entities.Trash object. Calls model.entities.Entity's constructor with super(x, y, width, height).
	 * Sets up the model.entities.Trash's controller.requests.RequestQueue.
	 * @param x The x position of the model.entities.Trash
	 * @param y The y position of the model.entities.Trash
	 * @param width The width of the model.entities.Trash
	 * @param height The height of the model.entities.Trash
	 * @param requestQueue The controller.requests.RequestQueue of the model.entities.Trash
	 * @see RequestQueue
	 */
	Trash(int x, int y, int width, int height, RequestQueue requestQueue) {
		super(x, y, width, height);
		//System.out.println(String.format(
		//        "model.entities.Trash: width=%d height=%d",
		//        width, height));
		this.requestQueue = requestQueue;
	}
	
	/**
 	 * Translates the model.entities.Trash's location a distance specified by the change in x and y.
	 * Once model.entities.Trash touches the bottom of the worldBounds, it will stop moving and increment the pollution level in model.Model.
	 * If model.entities.Trash touches the top of the worldBounds, it will be removed from the game and increment the score.
	 * @param dx The distance to translate the model.entities.Trash in the x direction
	 * @param dy The distance to translate the model.entities.Trash in the y direction
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
	 * Handles how the model.entities.Trash should be thrown. Sets the model.entities.Trash's speed to the specified x and y speeds.
	 * @param xSpeed The speed of the model.entities.Trash in the x direction
	 * @param ySpeed The speed of the model.entities.Trash in the y direction
	 */
	public void throwTrash(int xSpeed, int ySpeed) {
		thrown = true;
		setSpeed(xSpeed, ySpeed);
	}
	
	/**
	 * Returns the state of the model.entities.Trash in regards to if it has been thrown with throwTrash().
	 * @return True if the model.entities.Trash has been thrown, false otherwise
	 */
	public boolean thrown() {
		return thrown;
	}

	/**
	 * Set the "thrown" flag so that falling trash can be thrown (again)
	 * @param t boolean to set thrown to.
	 */
	public void setThrown(boolean t) { thrown = t; }
}