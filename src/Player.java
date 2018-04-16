abstract class Player extends Entity {
//
//	int initialWidth = 20;
//
//	@Override
//	private int getInitialWidth() {
//		return initialWidth;
//	}
	
	Player(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	abstract public void processInput(String action);
}
