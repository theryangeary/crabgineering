package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

public class TutorialTrashSpawner extends TrashSpawner {
    /**
     * Generate a TrashSpawner
     *
     * @param requestQueue for requests
     * @param spawnHeight
     * @param spawnWidth   Specifies how wide of a range to spawn trash in
     * @param interval     time between trash spawning
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
     * @param interval     time between trash spawning
     * @param offset       x offset for the trash spawner
     * @see Request
     */
    public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int offset) {
        super(requestQueue, spawnHeight, spawnWidth, offset);
    }

    /**
     * Configures everything so that this TrashSpawner will actually spawn trash
     * @param requestQueue ADD_TO_MODEL requests for spawned trash are passed to this
     * @param spawnWidth the width of the area where spawning should occur
     * @param spawnHeight the height at which trash should spawn
     * @param offset the x-position where we start spawning thrash
     */
    @Override
    void initSpawning(RequestQueue requestQueue, int spawnWidth, int spawnHeight){

    }

    /**
     * Stop or pause the trash spawner
     */
    public void stop(){

    }

    /**
     * Start or resume the trash spawner
     */
    public void start(){

    }
}
