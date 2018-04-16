import java.awt.*;

abstract class Entity {
	private final int initialWidth = 10;
	private final int initialHeight = 10;
	private final int initialX = 10;
	private final int initialY = 10;
	private Rectangle bounds;
	private double xVel;
	private double yVel;
	private final double gravity = .1;
	private final String imageReference;
	private int currentHealth;
	private final int maxHealth;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		xVel = 0;
		yVel = 0;
		imageReference = "TEST_IMAGE";
		currentHealth = 10;
		maxHealth = 10;
	}
	
	//TODO: the two movement methods are kind of redundant, we should maybe fix that
	
	/**
	 * Given an x and a y, move directly to that position on the screen
	 *
	 * @param x
	 * @param y
	 */
	private void moveDirectly(double x, double y) {
		this.setBounds(new Rectangle((int) x, (int) y, bounds.width, bounds.height));
	}
	
	boolean intersects(Entity e) {
		boolean result;
		if (this.bounds.intersects(e.bounds)) {
			System.out.printf("%s intersects %s!", this, e);
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	Rectangle getBounds() {
		return bounds;
	}
	
	int getCurrentHealth() {
		return currentHealth;
	}
	
	int getMaxHealth() {
		return maxHealth;
	}
	
	public void draw(Graphics g, Rectangle bounds) {
		g.setColor(Color.MAGENTA);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	void update() {
		yVel += gravity;
		//TODO: xVel is always the same which is kind of unnatural, there needs to be some sort of drag that kills it over time
		
		double newX = bounds.x + xVel;
		double newY = bounds.y + yVel;
		
		if (bounds.y + bounds.height >= Controller.getModel().getWorldHeight() - bounds.getHeight()) {
			if (yVel > 0) {
				yVel = 0;
				newY = Controller.getModel().getWorldHeight() - bounds.height;
			}
		}
		
		// TODO: Collision with the outer left/right wall is broken
		if (bounds.x + bounds.width >= Controller.getModel().getWorldWidth() - bounds.getWidth()) {
			if (xVel > 0) {
				xVel = 0;
				newX = Controller.getModel().getWorldWidth() - bounds.width;
			}
		}
		
		this.moveDirectly(newX, newY);
	}
	
	/**
	 * Add to velocity in order to move the entity
	 *
	 * @param xVel
	 * @param yVel
	 */
	void push(double xVel, double yVel) {
		this.xVel += xVel;
		this.yVel += yVel;
	}
}
