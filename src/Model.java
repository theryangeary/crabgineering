import java.awt.*;
import java.util.ArrayList;

public class Model {
	private final int worldHeight = Controller.getView().getHeight();
	private final int worldWidth = Controller.getView().getWidth();
	private ArrayList<Entity> entities = new ArrayList<>();
	private Player player;
	
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model() {
		/// Add player(s)
		player = new Crab(10, 10, 100, 100);
		entities.add(player);
		
		/// Add trash
		TrashFactory t = new TrashFactory();
		
		entities.add(t.createEasyTrash(400, 50));
		entities.add(t.createHardTrash(300, 0));
		
	}
	
	/**
	 * Update the model, i.e.
	 * process any entities in the world for things like gravity,
	 * check for collisions super inefficiently
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	int getWorldHeight() {
		return worldHeight;
	}
	
	int getWorldWidth() {
		return worldWidth;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	void drawWorld(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(0, 0, worldWidth, worldHeight);
	}
}