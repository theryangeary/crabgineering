public class Crab extends Player {
	
	private static final int SPEED = 5;

	public Crab(int x, int y, int width, int height) {
		super(x,y,width,height);
	}

	@Override
	public void processInput(String action) {
		switch(PlayerAction.valueOf(action)){
			case MOVE_LEFT: translate(-SPEED, 0);
		break;
			case MOVE_RIGHT: translate(SPEED, 0);
		break;
			case SPECIAL_ACTION: doAction();
		break;
		}

	}

	@Override
	protected void initSprite(){
	    setSprite(Sprite.CRAB);
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
