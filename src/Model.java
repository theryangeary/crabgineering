import java.awt.*;
import java.util.ArrayList;

public class Model {
	//listeners
	private final Controller.AddedEntityListener addedEntityListener;
	private final Controller.RemovedEntityListener removedEntityListener;

	//constants relevant to simulation's physics
	private final Rectangle worldBounds; //should ALWAYS == the worldBounds of the corresponding entities
	private final double GRAVITY = .05;
	private final double DRAG = .01;
	
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
		  Controller.AddedEntityListener addedEntityListener,
		  Controller.RemovedEntityListener removedEntityListener) {
	    this.worldBounds = worldBounds;
	    this.addedEntityListener = addedEntityListener;
	    this.removedEntityListener = removedEntityListener;

		//Crab crabby = new Crab(10,10,100,100);
		//addEntity(crabby);
		TrashFactory t = new TrashFactory();

		addEntity(t.createEasyTrash(400,50));
		addEntity(t.createHardTrash(300,0));

		int crabInitialX = 10;
		int crabInitialY = 10;
		player = new Crab(crabInitialX, crabInitialY);
		addEntity(player);

		int spawnInterval = 2 * 1000;
		int spawnHeight = 0;
		spawner = new TrashSpawner(this,
				                   spawnHeight,
				                   (int) worldBounds.getWidth(),
				                   spawnInterval);
		spawner.start();
		currentPollutionLevel = 0;
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like gravity
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update(GRAVITY, DRAG);
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

	public void addEntity(Entity entity) {
		//add the Entity, and let it react to being added
		entity.handleBeingAddedTo(this);
		entities.add(entity);

		//let the proper listener respond to the Entity being added
		addedEntityListener.handleAddedEntity(entity);
    }

    public void removeEntity(Entity entity) {
		entities.remove(entity);

		removedEntityListener.handleRemovedEntity(entity);
	}

	public Player getPlayer() {
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
