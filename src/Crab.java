public class Crab extends Player {
	
	private static final double SPEED = 10;
	private boolean hasTrash = false;
	private Trash heldTrash = null;

	private ArrowSprite arrowSprite;

	private double throwAngle = Math.PI/2;
	private final int THROW_SPEED = -25;
	private final double ROTATE_SPEED = Math.PI/32;

	private static final int CRAB_WIDTH = 100;
	private static final int CRAB_HEIGHT = 100;

	public Crab(int x, int y, View view) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT);
		arrowSprite = new ArrowSprite(getBounds());
		view.addSprite(arrowSprite);
	}
	
	@Override
	public void processInput(String action) {
		switch (PlayerAction.valueOf(action)) {
			case MOVE_LEFT:
				translate(-SPEED, 0);
<<<<<<< HEAD
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
            case ROTATE_TRASH_LEFT:
                if (hasTrash) {
                    rotateThrow(-ROTATE_SPEED);
                }
                break;
            case ROTATE_TRASH_RIGHT:
                if (hasTrash) {
                    rotateThrow(ROTATE_SPEED);
                }
                break;
		}
		
	}
<<<<<<< HEAD

	public void doAction(){
		if (hasTrash) {
			// Fire trash
			heldTrash.toggleStopped();
			heldTrash.throwTrash(
					(int) Math.round(THROW_SPEED * Math.cos(throwAngle)),
					(int) Math.round(THROW_SPEED * Math.sin(throwAngle)));
			//Controller.getModel().getThrownTrash().add(heldTrash);
			heldTrash = null;
			hasTrash = false;
			arrowSprite.setVisiblity(false);
		}
	}
	
	public void touchTrash(Trash t) {
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			hasTrash = true;
			t.toggleStopped();
			arrowSprite.setVisiblity(true);
			heldTrash = t;
		}
	}
	
	public void rotateThrow(double dTheta) {
		// ROTATE TRAJECTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
		throwAngle += dTheta;
		arrowSprite.rotate(dTheta);
	}

	/*
	public boolean arrowVisible() {
		return arrowVisible;
	}
	*/
}
