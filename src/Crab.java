public class Crab extends Player {
	
	private static final double SPEED = 4;
	private double currentSpeed = 0;
	private boolean hasTrash = false;
	private Trash heldTrash = null;

	private ArrowSprite arrowSprite;

	private double throwAngle = Math.PI/2;
	private final int THROW_SPEED = -25;
	private final double ROTATE_SPEED = Math.PI/32;

	static final int CRAB_WIDTH = 100;
	static final int CRAB_HEIGHT = 100;

	public Crab(int x, int y, RequestQueue requestQueue) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT);
		arrowSprite = new ArrowSprite(getBounds());
		requestQueue.postRequest(new Request<>(
				arrowSprite,
				Request.ActionType.ADD));
	}
	
	@Override
	public void processInput(String action) {
		switch (PlayerAction.valueOf(action)) {
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
			case MOVE_LEFT:
				currentSpeed = -SPEED;
				break;
			case MOVE_RIGHT:
				currentSpeed = SPEED;
				break;
			case STOP:
				currentSpeed = 0;
				break;
			case SPECIAL_ACTION:
				doAction();
				break;
		}
		
	}

	@Override
	public void update(double gravity, double drag){
		super.update(gravity,drag);
		translate(currentSpeed,0);
		if (hasTrash) {
			heldTrash.setLocation(
					(int) getBounds().getX(),
					(int) getBounds().getY());
		}
	}
	
	public void doAction() {
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
