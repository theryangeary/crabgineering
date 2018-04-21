import java.awt.*;

abstract class Player extends Entity {

	public enum PlayerAction{
		MOVE_LEFT,
		MOVE_RIGHT,
		SPECIAL_ACTION,
		ROTATE_TRASH_LEFT,
		ROTATE_TRASH_RIGHT;
	}

	Player(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	
	abstract public void processInput(String action);
	
	abstract public void touchTrash(Trash t);
}
