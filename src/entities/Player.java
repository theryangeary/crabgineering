package entities;

/**
 * An abstract class that represents a generic entities.Player. A entities.Player is a type of entities.Entity.
 * @author Zelinsky
 * @see Entity
 */
public abstract class Player extends Entity {

	/**
	 * An enumeration that represents different types of entities.Player actions. Used to map keyboard inputs to actions a entities.Player can perform.
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
	 * Constructs a entities.Player. Calls entities.Entity's constructor super(x, y, width, height).
	 * @param x The x position of the entities.Player
	 * @param y The y position of the entities.Player
	 * @param width The width of the entities.Player
	 * @param height The height of the entities.Player
	 * @see Entity
	 */
	Player(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	
	/**
	 * Handles how a entities.Player processes an action. Must be implemented by a type of entities.Player.
	 * @param action The action to be performed
	 */
	abstract public void processInput(String action);
	
	/**
	 * Handles what happens when a entities.Player's bounds intersects with a entities.Trash's bounds. Must be implemented by a type of entities.Player.
	 * @param t The entities.Trash that the entities.Player intersects with
	 */
	abstract public void touchTrash(Trash t);
}
