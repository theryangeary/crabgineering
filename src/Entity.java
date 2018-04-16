import java.awt.*;

abstract class Entity {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private Rectangle bounds;
	private double xVel;
	private double yVel;
	private final double gravity;

	private final String imageReference;
	private int currentHealth;
	private final int maxHealth;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		xVel = 0;
		yVel = 0;
		gravity = .1;
		imageReference = "TEST_IMAGE";
		currentHealth = 10;
		maxHealth = 10;
	}
	
	void move(double x, double y) {
		this.setBounds(new Rectangle((int) x, (int) y, bounds.width, bounds.height));
	}
	
	boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
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
		g.setColor(Color.pink);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	void update() {
		yVel += gravity;

		double newX = this.bounds.x + xVel;
		double newY = this.bounds.y + yVel;
		Model model = Controller.getModel();
		
		if (bounds.y + bounds.height >= model.getWorldHeight() - bounds.getHeight()) {
			if (yVel > 0) {
				yVel = 0;
				newY = model.getWorldHeight() - bounds.height;
			}
		}
		
		if (bounds.x + bounds.width >= model.getWorldWidth() - bounds.getWidth()) {
			if (xVel > 0) {
				xVel = 0;
				newX = model.getWorldWidth() - bounds.width;
			}
		}
		
		this.move(newX, newY);
	}
}
