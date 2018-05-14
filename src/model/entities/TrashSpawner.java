
package model.entities;

import controller.requests.Request;
import controller.requests.RequestQueue;

import java.io.Serializable;

public abstract class TrashSpawner implements Serializable {
	private int offset;
	private int spawnHeight;
	private int spawnWidth;
	private TrashFactory factory;
	RequestQueue requestQueue;
	
	/**
	 * Generate a TrashSpawner
	 *
	 * @param requestQueue for requests
	 * @param spawnHeight  Specifies the height at which trash spawns
	 * @param spawnWidth   Specifies how wide of a range to spawn trash in
	 * @param offset       x offset for the trash spawner
	 * @see Request
	 */
	public TrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int offset) {
		this.requestQueue = requestQueue;
		this.offset = offset;
		factory = new TrashFactory(requestQueue);
		this.spawnHeight = spawnHeight;
		this.spawnWidth = spawnWidth;
		
		//start the spawning process
		initSpawning();
	}
	
	/**
	 * Configures everything so that this TrashSpawner will actually spawn trash
	 */
	abstract void initSpawning();
	
	/**
	 * Get the instance of Factory used by this TrashSpawner
	 *
	 * @return the factory instance used by this TrashSpawner
	 */
	TrashFactory getFactory() {
		return factory;
	}
	
	/**
	 * Get the offset used by this spawner
	 *
	 * @return the offset for spawning used by this TrashSpawner
	 */
	public int getOffset() {
		return offset;
	}
	
	/**
	 * Get the height at which this spawner spawns trash
	 *
	 * @return the height at which this spawner spawns trash
	 */
	public int getSpawnHeight() {
		return spawnHeight;
	}
	
	/**
	 * Get the width of the spawning area
	 *
	 * @return the width of the spawning area
	 */
	public int getSpawnWidth() {
		return spawnWidth;
	}
	
	/**
	 * Stop or pause the trash spawner
	 */
	abstract public void stop();
	
	/**
	 * Start or resume the trash spawner
	 */
	abstract public void start();
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * Sets the RequestQueue for the TrashSpawner.
	 *
	 * @param rq The RequestQueue to set for the TrashSpawner
	 */
	public void setRequestQueue(RequestQueue rq) {
		requestQueue = rq;
		factory.setRequestQueue(rq);
	}
	
}
