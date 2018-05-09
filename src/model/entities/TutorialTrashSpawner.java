package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
    public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval) {
        super(requestQueue, spawnHeight, spawnWidth, interval);
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
    public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval, int offset) {
        super(requestQueue, spawnHeight, spawnWidth, interval, offset);
    }

    /**
     * Creates the action used by the timer to spawn trash
     * @param requestQueue ADD_TO_MODEL requests for spawned trash are passed to this
     * @param spawnWidth the width of the area where spawning should occur
     * @param spawnHeight the height at which trash should spawn
     * @return
     */
    @Override
    protected Action createSpawnAction(RequestQueue requestQueue, int spawnWidth, int spawnHeight){
        Action spawnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Generates a random x position within rage 0
                int randX = (int)(Math.random()*spawnWidth+getOffset());

                //Decide whether trash should be recyclable or not (50-50 chance)
                boolean recyclable = Math.random() > .5;

                requestQueue.postRequest(
                        RequestFactory.createAddToModelRequest(
                                getFactory().createEasyTrash(randX,spawnHeight, recyclable)
                        )
                );
            }
        };

        return spawnAction;
    }
}
