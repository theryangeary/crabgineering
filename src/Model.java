import java.awt.*;
import java.util.ArrayList;

public class Model {
	private final Rectangle worldBounds;
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;


	private int currentPollutionLevel;
	private final int maxPollutionLevel = 100;
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Rectangle worldBounds) {
	    this.worldBounds = worldBounds;

		//Crab crabby = new Crab(10,10,100,100);
		//entities.add(crabby);
		TrashFactory t = new TrashFactory();

		entities.add(t.createEasyTrash(400,50));
		entities.add(t.createHardTrash(300,0));
		player = new Crab(10,10,100,100);
		entities.add(player);

		spawner = new TrashSpawner(entities, 0, worldWidth, 2*1000);
		spawner.start();
		currentPollutionLevel = 0;
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like gravity
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update(worldBounds);
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
