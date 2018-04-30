package entities;

import java.awt.*;

/**
 * An abstract class that represents a moving component/image of the game.
 * @author Zelinsky
 */

public abstract class Entity implements BoundsListener {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private final Bounds bounds;
	private double dx;
	private double dy;

	private Rectangle worldBounds;
	
	private int currentHealth;
	private final int maxHealth;

	//TODO: switch to enum flag
	// States, i.e. booleans that are used to perform actions and check for things
	private boolean isMovingRight = false;
	private boolean isMovingLeft = false;
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;
	private boolean isAlive = true;
	/**
	 * The boolean representing whether or not the entities.Entity is at the bottom of the worldBounds Rectangle.
	 * Objects that extend entities.Entity may need access to this attribute.
	 */
	protected boolean isAtBottom = false;
	private boolean isStopped = false;
	
	
	//double trashRate = 1;
	
	/**
	 * Constructs an entities.Entity. Creates a Bounds for the entities.Entity representing its size and location.
	 * @param x The x position of the Bounds
	 * @param y The y position of the Bounds
	 * @param width The width of the Bounds
	 * @param height The height of the Bounds
	 * @see Bounds
	 */
	Entity(int x, int y, int width, int height) {
		bounds = new Bounds(x, y, width, height);
		dx = 0;
		dy = 0;
		currentHealth = 10;
		maxHealth = 10;
	}

	/**
	 * Sets the Bounds of the world for this entities.Entity. An entities.Entity can't move outside of the worldBounds.
	 * @param worldBounds A Bounds representing the bounds of the world
	 */
	public void setWorldBounds(Bounds worldBounds){
		this.worldBounds = new Rectangle(worldBounds);
		worldBounds.addListener(this);
	}
	
	/**
	 * Returns the Bounds representing the position for the entities.Entity
	 * @return The Bounds representing the position for the entities.Entity
	 */
	//Rectangle wrapper functions
	public Bounds getBounds() {
		return bounds;
	}
	
	/**
	 * Sets the location of the Bounds representing the position of the entities.Entity to the specified x and y position.
	 * @param x The new x position of the Bounds
	 * @param y The new y position of the Bounds
	 */
	void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}
	
	/**
	 * Translates the Bounds representing the entities.Entity's location a distance specified by the change in x and y.
	 * Will not allow for the entities.Entity to move outside of the worldBounds.
	 * @param dx The distance to translate the Bounds in the x direction
	 * @param dy The distance to translate the Bounds in the y direction
	 */
	void translate(double dx, double dy) {
		
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
		
		bounds.translate((int) dx, (int) dy);
	}
	
	/**
	 * Checks if this entities.Entity's Bounds intersects the specified entities.Entity's Bounds.
	 * @param e The other entities.Entity
	 * @return True if this entities.Entity's Bounds intersects the other entities.Entity's Bounds, false otherwise
	 */
	public boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
	}
	
	/**
	 * Sets the speed of the entities.Entity in the x and y direction. The entities.Entity moves according to its speed.
	 * @param dx The new speed of the entities.Entity in the x direction
	 * @param dy The new speed of the entities.Entity in the y direction
	 */
	void setSpeed(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Returns the current health of the entities.Entity.
	 * @return The current health of the entities.Entity.
	 */
	int getCurrentHealth() {
		return currentHealth;
	}
	
	/**
	 * Returns the maximum health of the entities.Entity.
	 * @return The maximum health of the entities.Entity.
	 */
	int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Checks to see if the entities.Entity is at the bottom of the worldBounds.
	 * @return True if entities.Entity is at the bottom of the worldBounds, false otherwise
	 */
	public boolean atBottom() {
		return isAtBottom;
	}

	/**
	 * Updates the position of the entities.Entity's BOunds based on gravity, drag, and the entities.Entity's speed.
	 * The entities.Entity will not be updated if it is stopped.
	 * @param gravity The gravity applied to the entities.Entity
	 * @param drag The drag applied to the entities.Entity
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
	 * Toggles whether the entities.Entity moves or not when updated. If the entities.Entity is stopped, it will not move when updated.
	 */
	void toggleStopped() {
		isStopped = !isStopped;
	}
	
	
	// The Bound functions return true if the entities.Entity is at the specified bounds
	/**
	 * Checks if the entities.Entity's Bounds is touching the left of the worldBounds.
	 * @return True if the entities.Entity's Bounds is touching the left of the worldBounds, false otherwise
	 */
	boolean leftBound() {
		return !worldBounds.contains(bounds.getMinX(), bounds.getCenterY());
	}
	
	/**
	 * Checks if the entities.Entity's Bounds is touching the right of the worldBounds.
	 * @return True if the entities.Entity's Bounds is touching the right of the worldBounds, false otherwise
	 */
	boolean rightBound() {
		return !worldBounds.contains(bounds.getMaxX(), bounds.getCenterY());
	}
	
	/**
	 * Checks if the entities.Entity's Bounds is touching the top of the worldBounds.
	 * @return True if the entities.Entity's Bounds is touching the top of the worldBounds, false otherwise
	 */
	boolean topBound() {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMinY());
	}
	
	/**
	 * Checks if the entities.Entity's Bounds is touching the bottom of the worldBounds.
	 * @return True if the entities.Entity's Bounds is touching the bottom of the worldBounds, false otherwise
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
}
