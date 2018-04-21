public class Crab extends Player {
	
	private static final int SPEED = 10;
	private boolean hasTrash = false;
	private Trash heldTrash = null;

	public Crab(int x, int y, int width, int height) {
		super(x,y,width,height);
	}

	@Override
	public void processInput(String action) {
		switch(PlayerAction.valueOf(action)){
			case MOVE_LEFT:
				translate(-SPEED, 0);
                if (hasTrash) {
                    heldTrash.translate(-SPEED, 0);
                }
                break;
			case MOVE_RIGHT:
				translate(SPEED, 0);
                if (hasTrash) {
                    heldTrash.translate(SPEED, 0);
                }
            	break;
			case SPECIAL_ACTION:
				doAction();
				break;
		}

	}

	@Override
	protected void initSprite(){
	    setSpriteImage(SpriteImage.CRAB);
    }

	public void doAction(){
		if (hasTrash) {
			// Fire trash
			heldTrash.throwTrash();
			heldTrash.toggleStopped();
			heldTrash = null;
			hasTrash = false;
		}
	}
	
	public void touchTrash(Trash t) {
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			hasTrash = true;
			t.toggleStopped();
			heldTrash = t;
		}
	}

}
