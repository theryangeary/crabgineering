import java.awt.*;

public class Crab extends Player {
	
	public Crab(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void processInput(String action) {
		switch (action) {
			case "VK_LEFT":
				move(-5, 0);
				break;
			case "VK_RIGHT":
				move(5, 0);
				break;
			case "VK_SPACE":
				doAction();
				break;
		}
		
	}
	
	@Override
	public void move(int x, int y) {
		// TODO
		if (x > 0) {
			System.out.println("MOVE RIGHT");
		} else if (x < 0) {
			System.out.println("MOVE LEFT");
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
