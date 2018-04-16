abstract class Player extends Entity {
	
	Player(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	abstract public void processInput(String action);
	
	abstract public void move(int x, int y);
}
