public abstract class Entity {
  
  private Rectangle bounds;
  String imageReference;
  private int currentHealth;
  private int maxHealth;

  public abstract void move(int x, int y);
  public abstract boolean intersects(Entity e);

  Rectangle getBounds() {
	return bounds;
  }	

  int getCurrentHealth() {
	return currentHealth;
  }

  int getMaxHealth() {
	return maxHealth;
  }
}
