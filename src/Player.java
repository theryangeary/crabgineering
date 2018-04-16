abstract class Player extends Entity {

	Player(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	abstract public void processInput(String action);
	
	 public void move(double x, double y) {
		 super.move(x, y);
	 }
}
