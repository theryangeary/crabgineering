import java.awt.*;

abstract class Entity {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private Rectangle bounds;
	private double dx;
	private double dy;
	
    private Sprite sprite;
	private int currentHealth;
	private final int maxHealth;
	private boolean atBottom = false;
	private boolean stopped = false;

	
	//double trashRate = 1;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		dx = 0;
		dy = 0;
		currentHealth = 10;
		maxHealth = 10;
		initSprite();
	}

	abstract void initSprite();

	protected void setSprite(Sprite sprite){
		this.sprite = sprite;
	}

	//Rectangle wrapper functions
	Rectangle getBounds() {
		return bounds;
	}

	void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}

	void translate(int dx, int dy) {
		int x = dx;
		int y = dy;
		Rectangle worldBounds = Controller.getModel().getWorldBounds();

		// Bounds check
		if (leftBound(worldBounds) && x < 0) {
			x = 0;
		}
		if (rightBound(worldBounds) && x > 0) {
			x = 0;
		}
		if (topBound(worldBounds) && y < 0) {
			y = 0;
		}
		if (bottomBound(worldBounds) && y > 0) {
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

	public void draw(Graphics g) {
		sprite.draw(g);
	}

	void update(Rectangle worldBounds, double gravity) {
		//apply gravity
		if (!stopped) {
			dy += gravity;

			translate((int) dx, (int) dy);
		}
	}

	void toggleStopped() {
		stopped = !stopped;
	}
	
	
	// The Bound functions return true if the Entity is at the specified bounds
	boolean leftBound(Rectangle worldBounds) {
		return !worldBounds.contains(bounds.getMinX(), bounds.getCenterY());
	}
	
	boolean rightBound(Rectangle worldBounds) {
		return !worldBounds.contains(bounds.getMaxX(), bounds.getCenterY());
	}
	
	boolean topBound(Rectangle worldBounds) {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMinY());
	}
	
	boolean bottomBound(Rectangle worldBounds) {
		return !worldBounds.contains(bounds.getCenterX(), bounds.getMaxY());
	}
}
