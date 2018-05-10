package model;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import view.estuaryenums.EstuarySound;
import controller.bounds.Bounds;
import controller.bounds.BoundsListener;
import controller.Controller;
import model.entities.*;
import model.entities.Entity.EntityType;
import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import view.sprites.EntitySprite;
import view.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;

import static model.entities.Barge.BARGE_HEIGHT;
import static model.entities.Barge.BARGE_PADDING;
import static model.entities.Barge.BARGE_WIDTH;

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
	public static final int WORLD_WIDTH = 750;
	public static final int WORLD_HEIGHT = WORLD_WIDTH; //it's a square
	private final Bounds worldBounds;
	private final double GRAVITY = .05;
	private final double DRAG = .01;
	public static final int WATER_HEIGHT = 100;

	//objects in simulation
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;
	private Barge trashBarge;
	private Barge recyclingBarge;
	private ArrayList<Trash> thrownTrash = new ArrayList<>();
	private ArrayList<Trash> removeFromThrownTrash = new ArrayList<>();

	//game variables
	private int currentPollutionLevel = 0;
	/**
	 * The maximum pollution level. The game ends once this level is reached.
	 */
	public static final int MAX_POLLUTION_LEVEL = 100;

	/**
	 * The amounts the score should be incremented by.
	 */
	public static final int SCORE_INCREMENT = 10;
	private int score = 0;
	
	/**
	 * The state of the game. True if the game is not running, false if it is. For testing purposes.
	 */
	public boolean gameOver = true;
	
	/**
	 * The state of the TrashSpawner. True if it is spawning trash, false if not. For testing purposes.
	 */
	public boolean trashSpawning = true;

	/**
	 * Constructs the Model with its Bounds and RequestQueue.
	 * Starts a new game by calling reset().
	 *
	 * @param requestQueue The RequestQueue for the Model
	 * @see Bounds
	 * @see RequestQueue
	 */
	public Model(RequestQueue requestQueue) {
		this.worldBounds = new Bounds(WORLD_WIDTH, WORLD_HEIGHT);
		this.requestQueue = requestQueue;
		
		//setup the RequestQueue Entities can use to post controller.requests
		//for the Model
		requestQueue.addListener(this);
	}
	
	/**
	 * Resets the model by clearing all components on the screen and resetting variables to their initial state
	 * and adds a TrashSpawner and Player.
	 */
	public void reset(EntityType playerType, Request.RequestType resetMode) {
		//System.out.println(resetMode);
		//if playerType is null, we're continuing a game,
		//so only reset the spawner
		if (playerType != null) {
			entities.clear();
			thrownTrash.clear();
			removeFromThrownTrash.clear();
			player = null;
			spawner = null;
			gameOver = false;

			int playerInitialX = worldBounds.width / 2 - Crab.CRAB_WIDTH / 2;
			int playerInitialY = worldBounds.height / 2 - Crab.CRAB_HEIGHT / 2;

			switch (playerType) {
				case CRAB:
					player = new Crab(playerInitialX, playerInitialY, requestQueue);
					break;
				case TURTLE:
					player = new Turtle(playerInitialX, playerInitialY, requestQueue);
					break;
				default:
					throw new ValueException(playerType.name() + " not a valid player type");
			}
			addEntity(player);


			recyclingBarge = new Barge((int) getWorldBounds().getX() + BARGE_PADDING, (int) getWorldBounds().getY() + BARGE_PADDING,
					BARGE_WIDTH, BARGE_HEIGHT, EntityType.RECYCLING_BARGE, requestQueue);
			trashBarge = new Barge((int) (getWorldBounds().getX() + getWorldBounds().getWidth() - BARGE_WIDTH - BARGE_PADDING),
					(int) getWorldBounds().getY() + BARGE_PADDING,
					BARGE_WIDTH, BARGE_HEIGHT, EntityType.TRASH_BARGE, requestQueue);

			addEntity(trashBarge);
			addEntity(recyclingBarge);

			requestQueue.postRequest(
					RequestFactory.createUpdatePollutionRequest(-currentPollutionLevel)
			);
			requestQueue.postRequest(
					RequestFactory.createUpdateScoreRequest(-1* score/SCORE_INCREMENT)
			);
		}


		//set up the spawner
		int spawnInterval = 2 * 1000;
		int spawnHeight = -Trash.TRASH_HEIGHT;
		switch (resetMode) {
			case START_TUTORIAL:
				spawner = new TutorialTrashSpawner(
						requestQueue,
						spawnHeight,
						(int) worldBounds.getWidth() - Trash.TRASH_WIDTH - (2 * BARGE_WIDTH) - BARGE_PADDING,
						BARGE_PADDING + BARGE_WIDTH);
				spawner.start();

				break;
			case START_GAME:

				//set up the spawner for the regular game
				spawner = new TimerTrashSpawner(
						requestQueue,
						spawnHeight,
						(int) worldBounds.getWidth() - Trash.TRASH_WIDTH - (2 * BARGE_WIDTH) - BARGE_PADDING,
						spawnInterval,
						BARGE_PADDING + BARGE_WIDTH);
				spawner.start();

				//Adding boss

				//addEntity(boss);

				break;

			case START_BOSS:
				System.out.println("STARTED BOSS");
				Entity boss = new Boss(-500, 25, requestQueue);
				addEntity(boss);
				break;
			default:
				throw new ValueException(resetMode.name() + " not a valid game mode");
		}
	}

	/**
	 * Handles how a Request is processed.
	 *
	 * @param request The request to be processed
	 * @see Request
	 */
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()) {
			case TOGGLE_PAUSED:
				toggleTrashSpawning(!trashSpawning);
				break;
			case ADD_TO_MODEL:
				addEntity((Entity) request.getSpecifics());
				break;
			case REMOVE_FROM_MODEL:
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
			//System.out.println(entity.getType());
			if(entity instanceof Boss){
				System.out.println("yeet");
			}
			entity.update(GRAVITY, DRAG);
//			System.out.println(entity.toString());
			//Check for player-trash collision and trash-trash collision

			if (entity instanceof Trash) {
				Trash trash = (Trash) entity;
				if (player.intersects(trash) && !trash.atBottom()) {
					player.touchTrash(trash);
				}
				if ((trashBarge.intersects(trash) && trash.touched() && trashBarge.bargeMatchesTrash(trash)) ||
						(recyclingBarge.intersects(trash) && trash.touched() && recyclingBarge.bargeMatchesTrash(trash))) {
						requestQueue.postRequest(
								RequestFactory.createUpdateScoreRequest(3)
						);
				}
				if ((recyclingBarge.intersects(trash) || trashBarge.intersects(trash) || trash.atTop()) && trash.touched()) {
					requestQueue.postRequest(
							RequestFactory.createRemoveFromModelRequest(trash)
					);
					removeFromThrownTrash.add(trash);
				}
				if (trash.getYSpeed() > 0) {
					thrownTrash.remove(trash);
					trash.setThrown(false);
				}
				for (Trash tt : thrownTrash) {
					if (entity.intersects(tt) && !entity.atBottom() && !trash.thrown()) {
						tt.bounceTrash((Trash) entity);
						removeFromThrownTrash.add(trash);
						removeFromThrownTrash.add(tt);
						EstuarySound.TRASH_HIT.play();
					}
				}
			}
		}

		for (Trash t : removeFromThrownTrash) {
			thrownTrash.remove(t);
		}
		removeFromThrownTrash.clear();

		// Check end game
		if (currentPollutionLevel >= MAX_POLLUTION_LEVEL) {
			endGame();
		}

	}

	/**
	 * Handles what should happen when the game ends. Tells the Controller the game is over by calling Controller.endGame().
	 *
	 * @see Controller
	 */
	public void endGame() {
		spawner.stop();
		gameOver = true;
		//reset()
		Controller.endGame();
	}

	/**
	 * Increments the score by the (modifier * SCORE_INCREMENT).
	 *
	 * @param modifier The amount to multiply SCORE_INCREMENT by
	 */
	public void incrementScore(int modifier) {
		EstuarySound.POINTS.play();
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
		if(entity instanceof Boss){
			System.out.println("yote");
		}
		entity.setWorldBounds(worldBounds);
		entities.add(entity);

		for(Entity e : entities){
			if(e instanceof Boss){
				System.out.println("YEET");
			}
		}

		//create the corresponding sprite
		Sprite sprite = new EntitySprite(entity);

		//and post a request for it to be added to the view
		requestQueue.postRequest(
				RequestFactory.createAddToViewRequest(sprite)
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
						RequestFactory.createRemoveFromViewRequest(
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
	public int getCurrentPollutionLevel() {
		return this.currentPollutionLevel;
	}
	
	/**
	 * Returns the maximum pollution level specified by MAX_POLLUTION_LEVEL
	 *
	 * @return The amount specified by MAXIMUM_POLLUTION_LEVEL
	 */
	public int getMaxPollutionLevel() {
		return MAX_POLLUTION_LEVEL;
	}
	
	/**
	 * Returns the Bounds of the world.
	 *
	 * @return The world Bounds
	 */
	public Rectangle getWorldBounds() {
		return worldBounds;
	}

	/**
	 * Turns on the Trash Spawner if the state is true, off if the state is false.
	 * @param state Determines whether the Trash Spawner is turned off or on
	 */
	public void toggleTrashSpawning(boolean state) {
		trashSpawning = state;
		if (state) {
			spawner.start();
		} else {
			spawner.stop();
		}
	}
	
	/**
	 * Returns the ArrayList of Entities in the Model. For testing purposes.
	 * @return The Model's ArrayList of Entities
	 */
	public ArrayList<Entity> getEntities(){
		return entities;
	}
	
	/**
	 * Returns the ArrayList of Trash that has been 'thrown'. For testing purposes.
	 * @return The Model's ArrayList of 'thrown' Trash
	 */
	public ArrayList<Trash> getThrownTrash(){
		return thrownTrash;
	}
}