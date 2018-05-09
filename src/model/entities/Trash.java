package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import view.estuaryenums.EstuarySound;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Random;

/**
 * A class representing a Trash object. Trash is a type of Entitiy.
 * @author Zelinsky
 */
public class Trash extends Entity {

    private static Random random = new Random();

	private RequestQueue requestQueue;

	private int pollutionCount;

	private final int ANGLE_FACTOR = 5;

	public static final int POLLUTION = 10;
	
	private boolean thrown = false;
	private boolean addedPollution = false;
	private boolean touched = false;

	public static final int TRASH_WIDTH = 50;
	public static final int TRASH_HEIGHT = 50;

	private EntityType type;
    /**
     * Contains all of the different types of [non-recyclable] Trash
     */
    public static final EnumSet<EntityType> TRASH_TYPES =
            EnumSet.of(EntityType.SNACK_BAG, EntityType.STYROFOAM_CUP);
    /**
     * Contains all of the different types of recyclables
     */
    public static final EnumSet<EntityType> RECYCLING_TYPES =
            EnumSet.of(EntityType.SODA_CAN, EntityType.MILK_JUG);

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
	Trash(int x, int y, int width, int height, RequestQueue requestQueue, boolean isRecyclable) {
		super(x, y, width, height);
		this.requestQueue = requestQueue;

		//choose specific type of trash depending on isRecyclable
        if (isRecyclable){
            //if recyclable, choose one of the EntityTypes in RECYCLING_TYPES
            int typeNum = random.nextInt(RECYCLING_TYPES.size());
            this.type = (EntityType) RECYCLING_TYPES.toArray()[typeNum];
        } else {
            //otherwise, choose one of the EntityTypes in TRASH_TYPES
            int typeNum = random.nextInt(TRASH_TYPES.size());
            this.type = (EntityType) TRASH_TYPES.toArray()[typeNum];
        }
	}

	/**
	 * Indicates whether or not this is recyclable,
	 * as well as that it is refuse of some sort
	 * @return one of the types in TRASH_TYPES or RECYCLING_TYPES
	 */
	@Override
	public EntityType getType(){
		return type;
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

		}
		if (bottomBound() && dy > 0) {
			dy = 0;
			isAtBottom = true;
			if (!addedPollution) {
				EstuarySound.HIT_GROUND.play();
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
	 * Set the "thrown" flag so that falling trash can be thrown (again)
	 * @param t boolean to set thrown to.
	 */
	public void setThrown(boolean t) { thrown = t; }

    /**
     * Bounch thrown trash off of other trash
     * @param t the non-thrown trash to bounce off
     */
	public void bounceTrash(Trash t) {
	    t.touch();
	    touch();
	    double angle = this.getBounds().getCenterX() - t.getBounds().getCenterX();
        this.setSpeed(-angle / ANGLE_FACTOR,  this.getYSpeed());
	    t.setSpeed(angle / ANGLE_FACTOR, this.getYSpeed());
	}

	/**
	 * Call this function once a trash has been touched, so signify that it can be removed under the right conditions.
	 */
	public void touch() {
		if (!atTop()) {
			touched = true;
		}
	}

	/**
	 * Check if trash has been touched.
	 * If trash hasn't been touched and is at the top of the world, it shouldn't be removed.
	 * If trash HAS been touched and is at the top of the world, it SHOULD be removed.
	 * @return Boolean: true if the trash has been hit by another piece of trash, touched by a player, etc.
	 */
	public boolean touched() { return touched; }
}
