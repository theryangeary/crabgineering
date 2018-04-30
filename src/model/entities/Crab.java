package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import view.sprites.ArrowSprite;

/**
 * A model.entities.Crab is a type of model.entities.Player.
 * @author Zelinsky
 * @see Player
 */
public class Crab extends Player {
	
	private static final double SPEED = 4;
	private double currentSpeed = 0;
	private boolean hasTrash = false;
	private Trash heldTrash = null;

	private RequestQueue requestQueue;
	private ArrowSprite arrowSprite;

	private double throwAngle = Math.PI/2;
	private final int THROW_SPEED = -25;
	private final double ROTATE_SPEED = Math.PI/32;

	/**
	 * The width of the model.entities.Crab
	 */
	public static final int CRAB_WIDTH = 100;
	/**
	 * The height of the model.entities.Crab
	 */
	public static final int CRAB_HEIGHT = 100;

	/**
	 * Constructs the model.entities.Crab by calling model.entities.Player's Constructor super(x, y, CRAB_WIDTH, CRAB_HEIGHT) and assigning the model.entities.Crab
	 * its view.sprites.Sprite and controller.requests.RequestQueue.
	 * @param x The x position of the model.entities.Crab
	 * @param y The y position of the model.entities.Crab
	 * @param requestQueue The controller.requests.RequestQueue of the model.entities.Crab
	 * @see Player
	 */
	public Crab(int x, int y, RequestQueue requestQueue) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT);
		this.requestQueue = requestQueue;

		arrowSprite = new ArrowSprite(getBounds());
		requestQueue.postRequest(
				RequestFactory.createAddToViewRequest(arrowSprite)
		);
		requestQueue.addListener(arrowSprite);
	}
	
	/**
	 * Handles how a model.entities.Crab processes an action command.
	 */
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

	/**
	 * Updates the position of the model.entities.Crab's controller.bounds.Bounds based on gravity, drag, and the model.entities.Crab's speed.
	 * Calls model.entities.Entity's update(gravity, drag) and handles heldTrash movement..
	 * @param gravity The gravity applied to the model.entities.Entity
	 * @param drag The drag applied to the model.entities.Entity
	 * @see Entity
	 */
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
	
	/**
	 * Performs the model.entities.Crab's special action by throwing currently held model.entities.Trash.
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
			arrowSprite.setVisiblity(false);
		}
	}
	
	/**
	 * Handles how model.entities.Trash and model.entities.Crab respond to intersecting with a model.entities.Trash.
	 * @param t The model.entities.Trash the model.entities.Crab intersects with
	 */
	public void touchTrash(Trash t) {
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			SoundEffect.GET_TRASH.play();
			hasTrash = true;
			t.toggleStopped();
			arrowSprite.setVisiblity(true);
			heldTrash = t;
		}
	}
	
	/**
	 * Rotates the model.entities.Crab's throwing direction for throwing model.entities.Trash by the degrees specified
	 * @param dTheta The degrees to rotate the throwing direction by
	 */
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
