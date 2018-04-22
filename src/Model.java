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
	
	//game variables
	private int currentPollutionLevel;
	static final int MAXPOLLUTIONLEVEL = 10;
	private final int SCOREINCREMENT = 10;
	private int score;
	
	//objects in simulation
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;
	private ArrayList<Trash> thrownTrash = new ArrayList<>();
	private ArrayList<Trash> toRemove = new ArrayList<>();
	
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Rectangle worldBounds,
	      Controller.AddedEntityListener addedEntityListener,
	      Controller.RemovedEntityListener removedEntityListener) {
		this.worldBounds = worldBounds;
		this.addedEntityListener = addedEntityListener;
		this.removedEntityListener = removedEntityListener;
		
		reset();
	}
	
	/**
	 * Resets the model
	 */
	public void reset() {
		entities.clear();
		thrownTrash.clear();
		toRemove.clear();
		player = null;
		spawner = null;
		
		int crabInitialX = worldBounds.width / 2 - Crab.CRAB_WIDTH / 2;
		int crabInitialY = worldBounds.height / 2 - Crab.CRAB_HEIGHT / 2;
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
		score = 0;
	}
	
	/**
	 * Update the model, i.e. process any entities in the world for things like GRAVITY
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update(GRAVITY, DRAG);
		}
		
		//Check for player-trash collision and trash-trash collision
		for (Entity entity : entities) {
			if (entity instanceof Trash) {
				Trash trash = (Trash) entity;
				if (player.intersects(trash)) {
					player.touchTrash(trash);
				}
				
				for (Trash tt : thrownTrash) {
					if (entity.intersects(tt) && !entity.atBottom() && !trash.thrown()) {
						toRemove.add(trash);
						toRemove.add(tt);
						incrementScore(3);
					}
				}
			}
		}
		
		// Remove to-be-removed trash; prevents modifying ArrayList while iterating through
		for (Trash t : toRemove) {
			removeEntity(t);
			thrownTrash.remove(t);
		}
		toRemove.clear();
		
		// Check end game
		if (currentPollutionLevel == MAXPOLLUTIONLEVEL) {
			endGame();
		}
		
	}

	void endGame() {
		//reset();
		Controller.endGame();
	}
	
	public void incrementScore(int modifier) {
		score += SCOREINCREMENT * modifier;
	}
	
	public int getScore() {
		return score;
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
	
	public ArrayList<Trash> getThrownTrash() {
		return thrownTrash;
	}
	
	public ArrayList<Trash> toRemove() {
		return toRemove;
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
		return MAXPOLLUTIONLEVEL;
	}
	
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}
