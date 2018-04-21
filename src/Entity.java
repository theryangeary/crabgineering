import java.awt.*;

abstract class Entity implements BoundsListener {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private final Bounds bounds; //should ALWAYS == the bounds of the corresponding Sprite
	private double dx;
	private double dy;

	private Rectangle worldBounds;
	
	private int currentHealth;
	private final int maxHealth;
	private boolean atBottom = false;
	private boolean stopped = false;

	
	//double trashRate = 1;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Bounds(x, y, width, height);
		dx = 0;
		dy = 0;
		currentHealth = 10;
		maxHealth = 10;
	}

	abstract Sprite initSprite();

	void setWorldBounds(Bounds worldBounds){
		this.worldBounds = new Rectangle(worldBounds);
		worldBounds.addListener(this);
	}

	//Rectangle wrapper functions
	Bounds getBounds() {
		return bounds;
	}

	void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}

	void translate(int dx, int dy) {
		int x = dx;
		int y = dy;

		// Bounds check
		if (leftBound() && x < 0) {
			x = 0;
		}
		if (rightBound() && x > 0) {
			x = 0;
		}
		if (topBound() && y < 0) {
			y = 0;
		}
		if (bottomBound() && y > 0) {
			y = 0;
			atBottom = true;
		}

		bounds.translate(x, y);
	}

	boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
	}


	void setSpeed(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	int getCurrentHealth() {
		return currentHealth;
	}

	int getMaxHealth() {
		return maxHealth;
	}
	
	boolean atBottom() {
		return atBottom;
	}

	void update(double gravity, double drag) {
		//apply gravity
		if (!stopped) {
			dy += gravity - dy * drag;

			translate((int) dx, (int) dy);
		}
	}

	void toggleStopped() {
		stopped = !stopped;
	}
	
	
	// The Bound functions return true if the Entity is at the specified bounds
	boolean leftBound() {
		return !worldBounds.contains(bounds.getMinX(), bounds.getCenterY());
	}
	
	boolean rightBound() {
		return !worldBounds.contains(bounds.getMaxX(), bounds.getCenterY());
	}
	
	boolean topBound() {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMinY());
	}
	
	boolean bottomBound() {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMaxY());
	}

	@Override
	public void handleSetLocation(int x, int y) {
		worldBounds.setLocation(x, y);
	}

	@Override
	public void handleTranslate(int dx, int dy) {
		worldBounds.translate(dx, dy);
	}
}
