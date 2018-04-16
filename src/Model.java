import java.util.ArrayList;

public class Model {
	private final int worldHeight = Controller.getView().getHeight();
	private final int worldWidth = Controller.getView().getWidth();
	private ArrayList<Entity> entities = new ArrayList<>();
	
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model() {
		entities.add(new Crab());
		entities.add(new Trash());
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
}