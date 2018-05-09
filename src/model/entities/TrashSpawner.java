
package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * TrashSpawner, a time based trash generator
 */
public abstract class TrashSpawner {
    private int offset;
    private TrashFactory factory;

    /**
     * Generate a TrashSpawner
     * @param requestQueue for requests
     * @param spawnHeight Specifies the height at which trash spawns
     * @param spawnWidth Specifies how wide of a range to spawn trash in
     * @param interval time between trash spawning
     * @param offset x offset for the trash spawner
     * @see Request
     */
    public TrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int offset){
        this.offset = offset;
        factory = new TrashFactory(requestQueue);

        //start the spawning process
        initSpawning(requestQueue, spawnWidth, spawnHeight);
    }

    /**
     * Configures everything so that this TrashSpawner will actually spawn trash
     * @param requestQueue ADD_TO_MODEL requests for spawned trash are passed to this
     * @param spawnWidth the width of the area where spawning should occur
     * @param spawnHeight the height at which trash should spawn
     * @param offset the x-position where we start spawning thrash
     */
    abstract void initSpawning(RequestQueue requestQueue, int spawnWidth, int spawnHeight);

    /**
     * Get the instance of Factory used by this TrashSpawner
     * @return the factory instance used by this TrashSpawner
     */
    TrashFactory getFactory(){
        return factory;
    }

    /**
     * Get the offset used by this spawner
     * @return the offset for spawning used by this TrashSpawner
     */
    int getOffset(){
        return offset;
    }

    /**
     * Stop or pause the trash spawner
     */
    abstract public void stop();

    /**
     * Start or resume the trash spawner
     */
    abstract public void start();

    public void setOffset(int offset){
        this.offset = offset;
    }
}
