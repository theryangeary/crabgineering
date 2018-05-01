package model.entities;

/**
 * An abstract class that represents a generic model.entities.Player. A model.entities.Player is a type of model.entities.Entity.
 * @author Zelinsky
 * @see Entity
 */
public abstract class Player extends Entity {

	/**
	 * An enumeration that represents different types of model.entities.Player actions. Used to map keyboard inputs to actions a model.entities.Player can perform.
	 */
	public enum PlayerAction{
		MOVE_LEFT,
		MOVE_RIGHT,
		STOP,
		SPECIAL_ACTION,
		ROTATE_TRASH_LEFT,
		ROTATE_TRASH_RIGHT;
	}

	/**
	 * Constructs a model.entities.Player. Calls model.entities.Entity's constructor super(x, y, width, height).
	 * @param x The x position of the model.entities.Player
	 * @param y The y position of the model.entities.Player
	 * @param width The width of the model.entities.Player
	 * @param height The height of the model.entities.Player
	 * @see Entity
	 */
	Player(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	
	/**
	 * Handles how a model.entities.Player processes an action. Must be implemented by a type of model.entities.Player.
	 * @param action The action to be performed
	 */
	abstract public void processInput(String action);
	
	/**
	 * Handles what happens when a model.entities.Player's intersects with a model.entities.Trash's  Must be implemented by a type of model.entities.Player.
	 * @param t The model.entities.Trash that the model.entities.Player intersects with
	 */
	abstract public void touchTrash(Trash t);
}
