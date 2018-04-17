import java.awt.*;

public class Crab extends Player {
	
	

	public Crab(int x, int y, int width, int height) {
		super(x,y,width,height);
	}

	@Override
	public void processInput(String action) {
		switch(action){
		case "VK_LEFT": move(this.getBounds().x - 5, this.getBounds().y);
		break;
		case "VK_RIGHT": move(this.getBounds().x + 5, this.getBounds().y);
		break;
		case "VK_SPACE": doAction();
		break;
		}

	}


	public void doAction(){
		//TODO
		System.out.println("SUPER SPECIAL ABILITY");
	}

	//	@Override
	//	public boolean intersects(Entity e) {
	//		return false;
	//		//TODO
	//	}


}
