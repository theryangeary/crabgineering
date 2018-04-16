import java.awt.*;

public class Crab extends Player {
	int movementIncrement = 5;
	
	public Crab(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	/**
	 * @param action
	 */
	@Override
	public void processInput(String action) {
		switch (action) {
			case "VK_LEFT":
				super.moveRelatively(-movementIncrement, 0);
				break;
			case "VK_RIGHT":
				super.moveRelatively(movementIncrement, 0);
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
