public class Crab extends Player {
	
	private static final int SPEED = 10;
	private boolean hasTrash = false;
	private Trash heldTrash = null;
	
	private Sprite arrow; // Image of the trajectory arrow
	private boolean arrowVisible = false;
	private final int THROWSPEED = -25;
	private final int ROTATESPEED = 10;
    private int xThrow = 0;
    private int yThrow = THROWSPEED;

	public Crab(int x, int y, int width, int height) {
		super(x,y,width,height);
	}

	@Override
	public void processInput(String action) {
		switch(PlayerAction.valueOf(action)){
		case MOVE_LEFT: translate(-SPEED, 0);
			if (hasTrash) {
				heldTrash.translate(-SPEED, 0);
			}
			break;
		case MOVE_RIGHT: translate(SPEED, 0);
			if (hasTrash) {
				heldTrash.translate(SPEED, 0);
			}
			break;
		case SPECIAL_ACTION: doAction();
			break;
		case ROTATE_TRASH_LEFT: 
			if (hasTrash) {
				rotateArrow(-ROTATESPEED);
			}
			break;
		case ROTATE_TRASH_RIGHT:
			if (hasTrash) {
				rotateArrow(ROTATESPEED);
			}
			break;
		}

	}

	@Override
	protected void initSprite(){
	    setSprite(Sprite.CRAB);
    }

	public void doAction(){
		if (hasTrash) {
			// Fire trash
			heldTrash.throwTrash(xThrow, yThrow);
			heldTrash.toggleStop();
			heldTrash = null;
			hasTrash = false;
			arrowVisible = false;
		}
	}
	
	public void touchTrash(Trash t) {
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			hasTrash = true;
			arrowVisible = true;
			t.toggleStop();
			heldTrash = t;
		}
	}
	
	  public void rotateArrow(int rotation) {
	    	// ROTATE TRJACTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
	    }
	    
	
	public boolean arrowVisible() {
		return arrowVisible;
	}

}
