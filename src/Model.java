import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class that contains the game's logic. Updates are called by a Controller.
 *
 * @author Zelinsky
 * @see Controller
 */
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
	/**
	 * The maximum pollution level. The game ends once this level is reached.
	 */
	static final int MAX_POLLUTION_LEVEL = 100;
	
	/**
	 * The amounts the score should be incremented by.
	 */
	static final int SCORE_INCREMENT = 10;
	private int score = 0;
	
	/**
	 * Constructs the Model with its Bounds and RequestQueue.
	 * Starts a new game by calling reset().
	 *
	 * @param worldBounds  The Bounds of the world
	 * @param requestQueue The RequestQueue for the Model
	 * @see Bounds
	 * @see RequestQueue
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
	 * Resets the model by clearing all components on the screen and resetting variables to their initial state
	 * and adds a TrashSpawner and Player.
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
	
	/**
	 * Handles how a Request is processed.
	 *
	 * @param request
	 * @see Request
	 */
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()) {
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
				break;
			case PLAY_SOUND:
				System.out.println(request.getSpecifics());
		}
	}
	
	/**
	 * Updates the model by updating all Entities, checking for and processing collisions, and checking for end-game conditions.
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
				if (trash.getYSpeed() > 0) {
					trash.resetThrown();
				}
			Date date = new Date();
				for (Trash tt : thrownTrash) {
					if (entity.intersects(tt) && !entity.atBottom() && !trash.thrown()) {
					    System.out.println(date.toString() + "thrown trash intersected trash");
						tt.bounceTrash((Trash) entity);
						//toRemove.add(trash);
						//toRemove.add(tt);
						SoundEffect.TRASH_HIT.play();
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
	
	/**
	 * Handles what should happen when the game ends. Tells the Controller the game is over by calling Controller.endGame().
	 *
	 * @see Controller
	 */
	void endGame() {
		spawner.stop();
		//reset()
		Controller.endGame();
	}
	
	/**
	 * Increments the score by the (modifier * SCORE_INCREMENT).
	 *
	 * @param modifier The amount to multiply SCORE_INCREMENT by
	 */
	public void incrementScore(int modifier) {
		SoundEffect.POINTS.play();
		score += SCORE_INCREMENT * modifier;
	}
	
	/**
	 * Returns the current score
	 *
	 * @return The current score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Adds an Entity to the Entities that will be processed during update().
	 * The Entity will also be added to the View through the requestQueue.
	 *
	 * @param entity The Entity to add to the Model
	 */
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
	
	/**
	 * Removes an Entity from the Entities that will be processed during update().
	 * The Entity will also be removed from the view through the requestQueue.
	 *
	 * @param entity The Entity to be removed from the Model
	 */
	public void removeEntity(Entity entity) {
		entities.remove(entity);
		
		//remove any Sprites that are following the entity's movements
		for (BoundsListener listener : entity.getBounds().getListeners()) {
			if (listener instanceof Sprite)
				requestQueue.postRequest(
						RequestFactory.createRemoveSpriteRequest(
								(Sprite) listener
						)
				);
		}
	}
	
	/**
	 * Returns the current player.
	 *
	 * @return The current player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Increments the current pollution level by the specified amount.
	 *
	 * @param addition The amount to add to the current pollution level
	 * @return The new pollution level
	 */
	// returns new pollution level
	int incrementPollutionLevel(int addition) {
		this.currentPollutionLevel += addition;
		return this.currentPollutionLevel;
	}
	
	/**
	 * Returns the current pollution level
	 *
	 * @return The current pollution level
	 */
	int getCurrentPollutionLevel() {
		return this.currentPollutionLevel;
	}
	
	/**
	 * Returns the maximum pollution level specified by MAX_POLLUTION_LEVEL
	 *
	 * @return The amount specified by MAXIMUM_POLLUTION_LEVEL
	 */
	int getMaxPollutionLevel() {
		return MAX_POLLUTION_LEVEL;
	}
	
	/**
	 * Returns the Bounds of the world.
	 *
	 * @return The world Bounds
	 */
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}