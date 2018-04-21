import java.awt.*;
import java.util.ArrayList;

public class Model {
	//listeners
	private final Controller.AddedEntityListener addedEntityListener;
	//private final RemovedEntityListener removedEntityListener;

	//constants relevant to simulation's physics
	private final Rectangle worldBounds; //should ALWAYS == the worldBounds of the corresponding entities
	private final double gravity = .05;

	//objects in simulation
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;

	//game variables
	private int currentPollutionLevel;
	private final int maxPollutionLevel = 100;

	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Rectangle worldBounds,
		  Controller.AddedEntityListener addedEntityListener) {
	    this.worldBounds = worldBounds;
	    this.addedEntityListener = addedEntityListener;

		//Crab crabby = new Crab(10,10,100,100);
		//addEntity(crabby);
		TrashFactory t = new TrashFactory();

		addEntity(t.createEasyTrash(400,50));
		addEntity(t.createHardTrash(300,0));
		player = new Crab(10,10,100,100);
		addEntity(player);

		spawner = new TrashSpawner(this,
				                   0,
				                   (int) worldBounds.getWidth(),
				                   2*1000);
		spawner.start();
		currentPollutionLevel = 0;
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like gravity
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update(gravity);
		}
		
		//Check for player-trash collision
		for (Entity entity : entities) {
			if (entity instanceof Trash) {
				if (player.intersects(entity)) {
					player.touchTrash((Trash) entity);
				}
			}
		}
		
	}

	public void addEntity(Entity e) {
		//add the Entity, and let it react to being added
		e.handleBeingAddedTo(this);
		entities.add(e);

		//let the proper listener respond to the Entity being added
		addedEntityListener.handleAddedEntity(e);
    }


	public ArrayList<Entity> getEntities(){
		return entities;
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
	
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}
