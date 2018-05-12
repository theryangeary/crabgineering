package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import view.estuaryenums.EstuarySound;

/**
 * A Crab is a type of Player.
 * @author Zelinsky
 * @see Player
 */
public class Crab extends Player {
	
	private static final double SPEED = 4;
	private double currentSpeed = 0;
	private double currentRotateSpeed = 0;
	private boolean hasTrash = false;
	private Trash heldTrash = null;

	private RequestQueue requestQueue;
//	private ArrowSprite arrowSprite;

	private double throwAngle = Math.PI/2;
	private final int THROW_SPEED = -15;
//	private final double ROTATE_SPEED = Math.PI/40;

	/**
	 * The width of the Crab
	 */
	public static final int CRAB_WIDTH = 100;
	/**
	 * The height of the Crab
	 */
	public static final int CRAB_HEIGHT = 100;

	/**
	 * Constructs the Crab by calling Player's Constructor super(x, y, CRAB_WIDTH, CRAB_HEIGHT) and assigning the Crab
	 * its Sprite and RequestQueue.
	 * @param x The x position of the Crab
	 * @param y The y position of the Crab
	 * @param requestQueue The RequestQueue of the Crab
	 * @see Player
	 */
	public Crab(int x, int y, RequestQueue requestQueue) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT);
		this.requestQueue = requestQueue;

//		arrowSprite = new ArrowSprite(getBounds());
//		requestQueue.postRequest(
//				RequestFactory.createAddToViewRequest(arrowSprite)
//		);
//		requestQueue.addListener(arrowSprite);
	}

	/**
	 * Indicates that this is a Crab
	 * @return EntityType.CRAB
	 */
	@Override
	public EntityType getType(){
		return EntityType.CRAB;
	}
	
	/**
	 * Handles how a Crab processes an action command.
	 */
	@Override
	public void processInput(String action) {
		switch (PlayerAction.valueOf(action)) {
//            case ROTATE_TRASH_LEFT:
//                if (hasTrash) {
//                	currentRotateSpeed = -ROTATE_SPEED;
//                }
//                break;
//            case ROTATE_TRASH_RIGHT:
//                if (hasTrash) {
//                	currentRotateSpeed = ROTATE_SPEED;
//                }
//                break;
//            case STOP_ROTATE:
//            	currentRotateSpeed = 0;
//            	break;
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

	/**
	 * Updates the position of the Crab's Bounds based on gravity, drag, and the Crab's speed.
	 * Calls Entity's update(gravity, drag) and handles heldTrash movement..
	 * @param gravity The gravity applied to the Entity
	 * @param drag The drag applied to the Entity
	 * @see Entity
	 */
	@Override
	public void update(double gravity, double drag){
		super.update(gravity,drag);
		translate(currentSpeed,0);
		if (hasTrash) {
			rotateThrow(currentRotateSpeed);
			heldTrash.setLocation(
					(int) getBounds().getX(),
					(int) getBounds().getY());
		}
	}
	
	/**
	 * Performs the Crab's special action by throwing currently held Trash.
	 */
	public void doAction() {

		if (hasTrash) {
			// Fire trash
			heldTrash.toggleStopped();
			heldTrash.throwTrash(
					(int) Math.round(THROW_SPEED * Math.cos(throwAngle)),
					(int) Math.round(THROW_SPEED * Math.sin(throwAngle)));
			requestQueue.postRequest(
					RequestFactory.createAddThrownTrashRequest(heldTrash));


			heldTrash = null;
			hasTrash = false;
//			arrowSprite.setVisibility(false);
//			arrowSprite.rotate(Math.PI/2 - throwAngle);
			throwAngle = Math.PI/2;
		}
	}
	
	/**
	 * Handles how Trash and Crab respond to intersecting with a Trash.
	 * @param t The Trash the Crab intersects with
	 */
	public void touchTrash(Trash t) {
		t.touch();
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			EstuarySound.GET_TRASH.play();
			hasTrash = true;
			t.toggleStopped();
//			arrowSprite.setVisibility(true);
			heldTrash = t;
		}
	}
	
	/**
	 * Rotates the Crab's throwing direction for throwing Trash by the degrees specified
	 * @param dTheta The degrees to rotate the throwing direction by
	 */
	public void rotateThrow(double dTheta) {
		// ROTATE TRAJECTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
		throwAngle += dTheta;
//		arrowSprite.rotate(dTheta);
	}
	
	/**
	 * Returns the model.entities.Crab's current speed based on input
	 * @return The Crab's current speed
	 */
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	/**
	 * Returns the model.entities.Crab's throwing angle based on input
	 * @return The Crab's throwing angle
	 */
	public double getThrowAngle() {
		return throwAngle;
	}

	/*
	public boolean arrowVisible() {
		return arrowVisible;
	}
	*/
}
