import java.awt.*;

public abstract class Entity {
	
	private Rectangle bounds = new Rectangle(10, 10, 10, 10);
	String imageReference = null;
	private int currentHealth = 10;
	private int maxHealth = 10;
	
	Entity() {
	}
	
	public void move(int x, int y) {
		this.bounds = new Rectangle(x, y, bounds.width, bounds.height);
	}

//	public boolean intersects(Entity e) {
//		Rectangle.intersect(bounds, e.bounds);
//	}
	
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
	
	protected void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
