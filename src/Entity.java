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
		bounds.translate(dx, dy);
	}

	boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
	}

	int getCurrentHealth() {
		return currentHealth;
	}

	int getMaxHealth() {
		return maxHealth;
	}

	public void draw(Graphics g) {
		g.drawImage(sprite.getImage(),
				    (int) bounds.getX(),
				    (int) bounds.getY(),
					//a BufferedImage won't change while
					//the image is being loaded, so null
				    //will work for our observer
					null);
	}

	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	void update(Rectangle worldBounds, double gravity) {
		//apply gravity
		dy += gravity;

		double x = this.bounds.getX() + dx;
		double y = this.bounds.getY() + dy;

		if (dy > 0
		    && bounds.getY() + bounds.getHeight() >= worldBounds.getHeight() - bounds.getHeight()) {
				dy = 0;
				y = worldBounds.getHeight() - bounds.getHeight();
		}

		if (dx > 0
		    && bounds.getX() + bounds.getWidth() >= worldBounds.getWidth() - bounds.getWidth()) {
				dx = 0;
				x = worldBounds.getWidth() - bounds.getWidth();
		}
		
		setLocation((int) x, (int) y);
	}
}
