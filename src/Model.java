import java.util.ArrayList;

public class Model {
	private final int worldHeight = Controller.getView().getHeight();
	private final int worldWidth = Controller.getView().getWidth();
	private ArrayList<Entity> entities = new ArrayList<>();
	private Player player = new Crab(5, 5, 5, 5);
	

	private int currentPollutionLevel;
	private final int maxPollutionLevel = 100;
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model() {
		TrashFactory t = new TrashFactory();

		entities.add(t.createEasyTrash(400,50));
		entities.add(t.createHardTrash(300,0));
		Crab crabby = new Crab(10,10,100,100);
		entities.add(crabby);

		currentPollutionLevel = 0;	
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like gravity
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

	public Player getPlayer(){
		return player;
	}
}
	// returns new pollution level 
	int addToPollutionLevel(int addition) {
	  this.currentPollutionLevel += addition;
	  return this.currentPollutionLevel;
	}

	int getCurrentPollutionLevel() {
	  return this.currentPollutionLevel;
	}

	int getMaxPollutionLevel() {
	  return this.maxPollutionLevel;
	}
}
