package model.entities;

/**
 * An abstract class that represents a generic Player. A Player is a type of Entity.
 * @author Zelinsky
 * @see Entity
 */
public abstract class Player extends Entity {

	/**
	 * An enumeration that represents different types of Player actions. Used to map keyboard inputs to actions a Player can perform.
	 */
	public enum PlayerAction{
		MOVE_LEFT,
		MOVE_RIGHT,
		STOP,
		SPECIAL_ACTION,
		ROTATE_TRASH_LEFT,
		ROTATE_TRASH_RIGHT,
		STOP_ROTATE;
	}

	/**
	 * Constructs a Player. Calls Entity's constructor super(x, y, width, height).
	 * @param x The x position of the Player
	 * @param y The y position of the Player
	 * @param width The width of the Player
	 * @param height The height of the Player
	 * @see Entity
	 */
	Player(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	
	/**
	 * Handles how a Player processes an action. Must be implemented by a type of Player.
	 * @param action The action to be performed
	 */
	abstract public void processInput(String action);
	
	/**
	 * Handles what happens when a Player's intersects with a Trash's  Must be implemented by a type of Player.
	 * @param t The Trash that the Player intersects with
	 */
	abstract public void touchTrash(Trash t);
}
