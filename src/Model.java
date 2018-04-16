import java.util.ArrayList;

public class Model {
	private int worldHeight = Controller.getView().getHeight();
	private int worldWidth = Controller.getView().getWidth();
	ArrayList<Entity> entities = new ArrayList<>();
	private Player player = new Crab();
	
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model() {
		Crab crabby = new Crab();
		entities.add(crabby);
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like gravity
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update();
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	

	
}