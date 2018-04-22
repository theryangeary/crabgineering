import java.awt.*;

abstract class Entity implements BoundsListener {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private final Bounds bounds;
	private double dx;
	private double dy;

	private Rectangle worldBounds;
	
	private int currentHealth;
	private final int maxHealth;
	
	// States, i.e. booleans that are used to perform actions and check for things
	private boolean isMovingRight = false;
	private boolean isMovingLeft = false;
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;
	private boolean isAlive = true;
	protected boolean isAtBottom = false;
	private boolean isStopped = false;
	
	
	//double trashRate = 1;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Bounds(x, y, width, height);
		dx = 0;
		dy = 0;
		currentHealth = 10;
		maxHealth = 10;
	}

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
		return isAtBottom;
	}

	void update(double gravity, double drag) {
		//apply gravity
		if (!isStopped) {
			dy += gravity - dy * drag;
			
			translate((int) dx, (int) dy);
		}

//		if (isMovingDown){
//			translate(SPEED);
//		}
	}
	
	void toggleStopped() {
		isStopped = !isStopped;
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
