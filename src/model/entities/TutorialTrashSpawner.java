package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

/**
 * Generates one piece of trash at a time, in a specific order
 */
public class TutorialTrashSpawner extends TrashSpawner {
    private RequestQueue requestQueue;

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

        this.requestQueue = requestQueue;
    }

    /**
     * Configures everything so that this TrashSpawner will actually spawn trash
     * @param requestQueue ADD_TO_MODEL requests for spawned trash are passed to this
     * @param spawnWidth the width of the area where spawning should occur
     * @param spawnHeight the height at which trash should spawn
     * @param offset the x-position where we start spawning thrash
     */
    @Override
    void initSpawning(RequestQueue requestQueue){
        //post the first piece of trash
        requestQueue.postRequest(
                RequestFactory.createAddToModelRequest(getNextTrash())
        );
    }

    /**
     * @return the next piece of trash to be added to the model
     */
    private Trash getNextTrash(){
        //Generates a random x position within rage 0
        int randX = (int)(Math.random()*getSpawnWidth()+getOffset());

        //Decide whether trash should be recyclable or not (50-50 chance)
        boolean recyclable = Math.random() > .5;

        return getFactory().createEasyTrash(randX,getSpawnHeight(), recyclable);
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
