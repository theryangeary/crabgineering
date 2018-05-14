package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import view.View;

import java.awt.*;
import java.util.EnumSet;

/**
 * Generates one piece of trash at a time, in a specific order
 */
public class TutorialTrashSpawner extends TrashSpawner implements RequestListener {
	public static final int NUM_CYCLES = 2;
	
	private boolean onFirst = true;
	private int curTrash;
	private Entity.EntityType[] trashTypes;
	
	/**
	 * Generate a TrashSpawner
	 *
	 * @param requestQueue for requests
	 * @param spawnHeight
	 * @param spawnWidth   Specifies how wide of a range to spawn trash in
	 * @see Request
	 */
	public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth) {
		this(requestQueue, spawnHeight, spawnWidth, 0);
	}
	
	/**
	 * Generate a TrashSpawner
	 *
	 * @param requestQueue for requests
	 * @param spawnHeight  Specifies the height at which trash spawns
	 * @param spawnWidth   Specifies how wide of a range to spawn trash in
	 * @param offset       x offset for the trash spawner
	 * @see Request
	 */
	public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int offset) {
		super(requestQueue, spawnHeight, spawnWidth, offset);
		
		this.requestQueue = requestQueue;
		requestQueue.addListener(this);
	}
	
	/**
	 * Configures everything so that this TrashSpawner will actually spawn trash
	 */
	@Override
	void initSpawning() {
		//combines all of the types of recycling and all the types of trash
		EnumSet<Entity.EntityType> allTrash = EnumSet.copyOf(Trash.TRASH_TYPES);
		allTrash.addAll(Trash.RECYCLING_TYPES);
		
		trashTypes = new Entity.EntityType[allTrash.size()];
		trashTypes = allTrash.toArray(trashTypes);
		
		curTrash = 0;
		
		//post the first piece of trash
		requestQueue.postRequest(
				RequestFactory.createAddToModelRequest(getNextTrash())
		);
	}
	
	/**
	 * @return the next piece of trash to be added to the model
	 */
	private Trash getNextTrash() {
		//Generates a random x position within rage 0
		int randX = (int) (Math.random() * getSpawnWidth() + getOffset());
		
		Entity.EntityType trashType = trashTypes[curTrash % trashTypes.length];
		
		return getFactory().createEasyTrash(randX, getSpawnHeight(), trashType);
	}
	
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()) {
			case UPDATE_SCORE:
				//ignore the reset versions:
				//they don't actually indicate that trash has been disposed of
				if ((int) request.getSpecifics() <= 0) {
					break;
				}
				
				//move on to the next trash type, since the player got rid of it correctly
				curTrash++;
				
				//start the real game if the player's shown their worth
				if (completed()) {
					RequestListener listener = this;
					
					//do this later, so as to avoid concurrent modification
					EventQueue.invokeLater(
							new Runnable() {
								@Override
								public void run() {
									//we've fulfilled our task...
									//and don't want to exponentially
									//increase the amount of trash onscreen
									requestQueue.removeListener(listener);
								}
							}
					);
					
					requestQueue.postRequest(
							RequestFactory.createStartGameRequest(null)
					);
				}
				break;
			
			case UPDATE_POLLUTION:
				//stick with the current trash type since the player
				//didn't deal with it right
				
				//ignore the reset versions:
				//they don't actually indicate that trash has been disposed of
				if ((int) request.getSpecifics() <= 0) {
					break;
				}
			
			case REMOVE_FROM_MODEL:
				
				//after the first piece of trash is picked up,
				//the sorting popup should be displayed
				if (onFirst) {
					requestQueue.postAndFulfillRequest(
							RequestFactory.createShowPopupRequest(View.PopupType.SORTING_TUTORIAL)
					);
					
					onFirst = false;
				}
				
				//either way, post a new piece of trash
				if (!completed()) {
					requestQueue.postRequest(
							RequestFactory.createAddToModelRequest(getNextTrash())
					);
				}
		}
	}
	
	/**
	 * @return a boolean indicating whether or not the tutorial has been completed
	 */
	private boolean completed() {
		return curTrash > trashTypes.length * NUM_CYCLES;
	}
	
	/**
	 * Stop or pause the trash spawner
	 */
	public void stop() {
		//don't really need to do anything?
	}
	
	/**
	 * Start or resume the trash spawner
	 */
	public void start() {
		//don't really need to do anything?
	}
}
