import java.awt.*;
import java.util.ArrayList;

public class Model implements RequestListener {
	//listeners
	RequestQueue requestQueue;

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
	static final int MAX_POLLUTION_LEVEL = 100;
	static final int SCORE_INCREMENT = 10;
	private int score = 0;

	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Bounds worldBounds,
		  RequestQueue requestQueue) {
	    this.worldBounds = worldBounds;

	    this.requestQueue = requestQueue;

	    //setup the RequestQueue Entities can use to post requests
		//for the Model
	    requestQueue.addListener(this::handleRequest);

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
		player = new Crab(crabInitialX, crabInitialY, requestQueue);
		addEntity(player);

		int spawnInterval = 2 * 1000;
		int spawnHeight = 0;
		spawner = new TrashSpawner(requestQueue,
				                   spawnHeight,
				                   (int) worldBounds.getWidth(),
				                   spawnInterval);
		spawner.start();
		currentPollutionLevel = 0;
		score = 0;
	}

	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case ADD_ENTITY:
                addEntity((Entity) request.getSpecifics());
				break;
			case REMOVE_ENTITY:
                removeEntity((Entity) request.getSpecifics());
				break;
			case ADD_THROWN_TRASH:
				thrownTrash.add((Trash) request.getSpecifics());
				break;
			case UPDATE_SCORE:
				incrementScore((int) request.getSpecifics());
				break;
			case UPDATE_POLLUTION:
				incrementPollutionLevel((int) request.getSpecifics());
		}
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
						requestQueue.postRequest(
								RequestFactory.createUpdateScoreRequest(3)
						);
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
		if (currentPollutionLevel == MAX_POLLUTION_LEVEL) {
			endGame();
		}

	}

	void endGame() {
		//reset();
		Controller.endGame();
	}

	public void incrementScore(int modifier) {
		score += SCORE_INCREMENT * modifier;
	}

	public int getScore() {
		return score;
	}

	public void addEntity(Entity entity) {
		//add the Entity, and let it react to being added
		entity.setWorldBounds(worldBounds);
		entities.add(entity);

		//create the corresponding sprite
		Sprite sprite = new EntitySprite(entity);

		//and post a request for it to be added to the view
		requestQueue.postRequest(
				RequestFactory.createAddSpriteRequest(sprite)
		);
	}

    public void removeEntity(Entity entity) {
		entities.remove(entity);

		//remove any Sprites that are following the entity's movements
		for (BoundsListener listener: entity.getBounds().getListeners()) {
			if (listener instanceof Sprite)
				requestQueue.postRequest(
						RequestFactory.createRemoveSpriteRequest(
								(Sprite) listener
						)
				);
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	// returns new pollution level
	int incrementPollutionLevel(int addition) {
		this.currentPollutionLevel += addition;
		return this.currentPollutionLevel;
	}
	
	int getCurrentPollutionLevel() {
		return this.currentPollutionLevel;
	}
	
	int getMaxPollutionLevel() {
		return MAX_POLLUTION_LEVEL;
	}
	
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}