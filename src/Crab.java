import java.awt.*;

public class Crab extends Player {
	
	Crab() {
		this.setBounds(new Rectangle(10, 10, 10, 10));
	}
	
	// How the Crab should handle movement and action inputs
	@Override
	public void processInput(String action) {
		switch(action) {
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
		//TODO
		if (x > 0) {
			System.out.println("MOVE RIGHT");
		} else if (x < 0){
			System.out.println("MOVE LEFT");
		}
	}
	
	public void doAction() {
		//TODO
		System.out.println("SUPER SPECIAL ABILITY");
	};
	
	
//	@Override
//	public boolean intersects(Entity e) {
//		return false;
//		//TODO
//	}
	
	
}
