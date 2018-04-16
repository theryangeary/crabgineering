import java.awt.*;

public class Crab extends Player {
	int movementIncrement = 1;
	
	public Crab(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param action
	 */
	@Override
	public void processInput(String action) {
		switch (action) {
			case "VK_UP":
				push(0, movementIncrement);
				System.out.println("DOWN");
				break;
			case "VK_DOWN":
				push(0, -movementIncrement);
				System.out.println("UP");
				break;
			case "VK_LEFT":
				push(-movementIncrement, 0);
				break;
			case "VK_RIGHT":
				push(movementIncrement, 0);
				break;
			case "VK_SPACE":
				doAction();
				break;
		}
	}
	
	public void doAction() {
		//TODO
		System.out.println("SUPER SPECIAL ABILITY");
	}
	
	//	@Override
	//	public boolean intersects(Entity e) {
	//		return false;
	//		//TODO
	//	}
	
	@Override
	public void draw(Graphics g, Rectangle bounds) {
		g.setColor(Color.red);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
}
