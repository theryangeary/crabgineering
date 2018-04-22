import java.awt.*;
import java.util.ArrayList;

public class Model {
	//listeners
	private Controller.AddedEntityListener addedEntityListener;
	private Controller.RemovedEntityListener removedEntityListener;
	private Controller.PollutionListener pollutionListener;

	//constants relevant to simulation's physics
	private final Bounds worldBounds;
	private final double GRAVITY = .05;
	private final double DRAG = .01;
	
	//objects in simulation
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;
	private ArrayList<Trash> thrownTrash = new ArrayList<>();
	private ArrayList<Trash> toRemove = new ArrayList<>();
	
	//game variables
	private int currentPollutionLevel = 0;
	private final int MAXPOLLUTIONLEVEL = 100;
	private final int SCOREINCREMENT = 10;
	private int score = 0;
	
	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Bounds worldBounds,
		  Controller.AddedEntityListener addedEntityListener,
		  Controller.RemovedEntityListener removedEntityListener,
		  View view) {
	    this.worldBounds = worldBounds;
	    this.addedEntityListener = addedEntityListener;
	    this.removedEntityListener = removedEntityListener;

		//Crab crabby = new Crab(10,10,100,100);
		//addEntity(crabby);
		TrashFactory t = new TrashFactory();

		//addEntity(t.createEasyTrash(400,50));
		//addEntity(t.createHardTrash(300,0));

		int crabInitialX = 10;
		int crabInitialY = 10;
		player = new Crab(crabInitialX, crabInitialY, view);
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
		//TODO
		
	}
	
	public void incrementScore(int modifier) {
		score += SCOREINCREMENT * modifier;
	}
	
	public int getScore() {
		return score;
	}

	public void addEntity(Entity entity) {
		//add the Entity, and let it react to being added
		entity.setWorldBounds(worldBounds);
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

	public void setPollutionListener(Controller.PollutionListener pollutionListener) {
		this.pollutionListener = pollutionListener;
	}

	public ArrayList<Trash> getThrownTrash(){
		return thrownTrash;
	}
	
	public ArrayList<Trash> toRemove(){
		return toRemove;
	}
	
	// returns new pollution level
	int addToPollutionLevel(int addition) {
		this.currentPollutionLevel += addition;
		pollutionListener.handlePollutionChange(currentPollutionLevel);
		return this.currentPollutionLevel;
	}
	
	int getCurrentPollutionLevel() {
		return this.currentPollutionLevel;
	}
	
	int getMaxPollutionLevel() {
		return this.MAXPOLLUTIONLEVEL;
	}
	
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}
