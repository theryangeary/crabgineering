package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import java.util.EnumSet;

/**
 * Generates one piece of trash at a time, in a specific order
 */
public class TutorialTrashSpawner extends TrashSpawner {
    private RequestQueue requestQueue;
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

        //combines all of the types of recycling and all the types of trash
        EnumSet<Entity.EntityType> allTrash = EnumSet.copyOf(Trash.TRASH_TYPES);
        allTrash.addAll(Trash.RECYCLING_TYPES);
        this.trashTypes = allTrash.toArray(this.trashTypes);

        this.curTrash = 0;
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

        Entity.EntityType trashType = trashTypes[curTrash];
        curTrash = (curTrash + 1) % trashTypes.length;

        return getFactory().createEasyTrash(randX,getSpawnHeight(), trashType);
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
