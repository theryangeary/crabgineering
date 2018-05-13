package model.entities;

import controller.bounds.Bounds;
import controller.bounds.BoundsListener;
import controller.requests.RequestQueue;

import java.awt.*;
import java.io.Serializable;

/**
 * An abstract class that represents a moving component/image of the game.
 * @author Zelinsky
 */

public abstract class Entity implements BoundsListener, Serializable {

	/**
	 * A semantic type indicating what type of Entity this is
	 */
	public enum EntityType{
		CLAM,
		CRAB,
		SHRIMP,
		TURTLE,
		BOSS,
        SNACK_BAG,
        STYROFOAM_CUP,
        SODA_CAN,
        MILK_JUG,
		TRASH_BARGE,
		RECYCLING_BARGE;
	}

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private final Bounds bounds;

	//If true, will ignore the bounds of the screen
	protected boolean ignoreBounds = false;

	private double dx;
	private double dy;

	private Rectangle worldBounds;
	
	private int currentHealth;
	private final int maxHealth;

	/**
	 * The boolean representing whether or not the Entity is at the bottom of the worldBounds Rectangle.
	 * Objects that extend Entity may need access to this attribute.
	 */
	protected boolean isAtBottom = false;
	private boolean isStopped = false;
	
	RequestQueue requestQueue;
	
	
	//double trashRate = 1;
	
	/**
	 * Constructs an Entity. Creates a Bounds for the Entity representing its size and location.
	 * @param x The x position of the Bounds
	 * @param y The y position of the Bounds
	 * @param width The width of the Bounds
	 * @param height The height of the Bounds
	 * @see Bounds
	 */
	Entity(int x, int y, int width, int height, RequestQueue rq) {
		bounds = new Bounds(x, y, width, height);
		dx = 0;
		dy = 0;
		currentHealth = 10;
		maxHealth = 10;
		requestQueue = rq;
	}

	/**
	 * indicates to other classes what type of Entity this is
	 *
	 * @return The EntityType which best describes this given Entity
	 */
	public abstract EntityType getType();

	/**
	* Sets the Bounds of the world for this Entity. An Entity can't move outside of the worldBounds.
	* @param worldBounds A Bounds representing the of the world
	*/
	public void setWorldBounds(Bounds worldBounds){
		this.worldBounds = new Rectangle(worldBounds);
		worldBounds.addListener(this);
	}

	/**
	 * Gets the world bounds of the object
	 * @return The world bounds
	 */
	public Rectangle getWorldBounds(){
		return worldBounds;
	}
	
	/**
	 * Returns the Bounds representing the position for the Entity
	 * @return The Bounds representing the position for the Entity
	 */
	//Rectangle wrapper functions
	public Bounds getBounds() {
		return bounds;
	}
	
	/**
	 * Sets the location of the Bounds representing the position of the Entity to the specified x and y position.
	 * @param x The new x position of the Bounds
	 * @param y The new y position of the Bounds
	 */
	public void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}
	
	/**
	 * Translates the Bounds representing the Entity's location a distance specified by the change in x and y.
	 * Will not allow for the Entity to move outside of the worldBounds.
	 * @param dx The distance to translate the Bounds in the x direction
	 * @param dy The distance to translate the Bounds in the y direction
	 */
	void translate(double dx, double dy) {

		if(!ignoreBounds) {
			// Bounds check
			if (leftBound() && dx < 0) {
				dx = 0;
			}
			if (rightBound() && dx > 0) {
				dx = 0;
			}
			if (topBound() && dy < 0) {
				dy = 0;
			}
			if (bottomBound() && dy > 0) {
				dy = 0;
				isAtBottom = true;
			}
		}
		
		bounds.translate((int) dx, (int) dy);
	}
	
	/**
	 * Checks if this Entity's Bounds intersects the specified Entity's Bounds.
	 * @param e The other Entity
	 * @return True if this Entity's Bounds intersects the other Entity's Bounds, false otherwise
	 */
	public boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
	}
	
	/**
	 * Sets the speed of the Entity in the x and y direction. The Entity moves according to its speed.
	 * @param dx The new speed of the Entity in the x direction
	 * @param dy The new speed of the Entity in the y direction
	 */
	public void setSpeed(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Returns the current velocity along the y-axis
	 * @return dy
	 */
	public double getDY(){
		return dy;
	}
	
	/**
	 * Returns the current health of the Entity.
	 * @return The current health of the Entity.
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	/**
	 * Returns the maximum health of the Entity.
	 * @return The maximum health of the Entity.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Checks to see if the Entity is at the bottom of the worldBounds.
	 * @return True if Entity is at the bottom of the worldBounds, false otherwise
	 */
	public boolean atBottom() {
		return isAtBottom;
	}

	/**
	 * Check if trash is at or above the top of the water
	 * @return Boolean: true if trash is at or above the top of the world
	 */
	public boolean atTop() {
		return getBounds().getY() <= worldBounds.getY();
	}

	/**
	 * Updates the position of the Entity's BOunds based on gravity, drag, and the Entity's speed.
	 * The Entity will not be updated if it is stopped.
	 * @param gravity The gravity applied to the Entity
	 * @param drag The drag applied to the Entity
	 */
	public void update(double gravity, double drag) {
		//apply gravity
		if (!isStopped) {
			dy += gravity - dy * drag;
			
			translate((int) dx, (int) dy);
		}

//		if (isMovingDown){
//			translate(SPEED);
//		}
	}
	
	/**
	 * Toggles whether the Entity moves or not when updated. If the Entity is stopped, it will not move when updated.
	 */
	public void toggleStopped() {
		isStopped = !isStopped;
	}
	
	
	// The Bound functions return true if the Entity is at the specified controller.bounds
	/**
	 * Checks if the Entity's Bounds is touching the left of the worldBounds.
	 * @return True if the Entity's Bounds is touching the left of the worldBounds, false otherwise
	 */
	boolean leftBound() {
		return !worldBounds.contains(bounds.getMinX(), bounds.getCenterY());
	}
	
	/**
	 * Checks if the Entity's Bounds is touching the right of the worldBounds.
	 * @return True if the Entity's Bounds is touching the right of the worldBounds, false otherwise
	 */
	boolean rightBound() {
		return !worldBounds.contains(bounds.getMaxX(), bounds.getCenterY());
	}
	
	/**
	 * Checks if the Entity's Bounds is touching the top of the worldBounds.
	 * @return True if the Entity's Bounds is touching the top of the worldBounds, false otherwise
	 */
	boolean topBound() {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMinY());
	}
	
	/**
	 * Checks if the Entity's Bounds is touching the bottom of the worldBounds.
	 * @return True if the Entity's Bounds is touching the bottom of the worldBounds, false otherwise
	 */
	boolean bottomBound() {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMaxY());
	}

	/**
     * Sets the location of the worldBounds
	 * to the specified x and y location.
	 * @param x The new x location for the worldBounds
	 * @param y The new y location for the worldBounds
	 */
	@Override
	public void handleSetLocation(int x, int y) {
		worldBounds.setLocation(x, y);
	}

	/**
	 * Translates the worldBounds a distance specified by the change in x and y.
	 * @param dx The x distance to translate the worldBounds by
	 * @param dy The y distance to translate the worldBounds by
	 */
	@Override
	public void handleTranslate(int dx, int dy) {
		worldBounds.translate(dx, dy);
	}

	/**
	 * Get the current speed in the X direction
	 * @return speed in the X direction, as a double. Right is positive.
	 */
	public double getXSpeed() { return dx; }

	/**
	 * Get the current speed in the Y direction
	 * @return speed in the Y direction, as a double. Down is positive.
	 */
	public double getYSpeed() {
		return dy;
	}
	
	/**
	 * Sets the RequestQueue for this Entity.
	 * @param rq The RequestQueue to set for this Entity
	 */
	public void setRequestQueue(RequestQueue rq) {
		requestQueue = rq;
	}
}
