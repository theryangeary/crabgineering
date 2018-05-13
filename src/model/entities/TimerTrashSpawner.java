package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * TrashSpawner, a time based trash generator
 */
public class TimerTrashSpawner extends TrashSpawner {
    private int interval;
    private Timer spawnTimer;
    public static final double SPEED_UP = 1.02;

    /**
     * Generate a TrashSpawner
     * @param requestQueue for requests
     * @param spawnHeight Specifies the height at which trash spawns
     * @param spawnWidth Specifies how wide of a range to spawn trash in
     * @param interval time between trash spawning
     * @see Request
     */
    public TimerTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval){
        this(requestQueue, spawnHeight, spawnWidth, interval,0);
    }

    /**
     * Generate a TrashSpawner
     * @param requestQueue for requests
     * @param spawnHeight Specifies the height at which trash spawns
     * @param spawnWidth Specifies how wide of a range to spawn trash in
     * @param interval time between trash spawning
     * @param offset x offset for the trash spawner
     * @see Request
     */
    public TimerTrashSpawner(RequestQueue requestQueue,
                             int spawnHeight, int spawnWidth,
                             int interval, int offset) {
        super(requestQueue, spawnHeight, spawnWidth, offset);

        //Interval is how long it takes between spawns
        this.interval = interval;
        spawnTimer.setDelay(this.interval);
    }

    /**
     * Configures everything so that this TrashSpawner will actually spawn trash
     * @param requestQueue ADD_TO_MODEL requests for spawned trash are passed to this
     * @param spawnWidth the width of the area where spawning should occur
     * @param spawnHeight the height at which trash should spawn
     * @param offset the x-position where we start spawning thrash
     */
    @Override
    void initSpawning(){
        Action spawnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Generates a random x position within rage 0
                int randX = (int)(Math.random()*getSpawnWidth()+getOffset());

                //Decide whether trash should be recyclable or not (50-50 chance)
                boolean recyclable = Math.random() > .5;

                requestQueue.postRequest(
                        RequestFactory.createAddToModelRequest(
                                getFactory().createEasyTrash(randX,getSpawnHeight(), recyclable)
                        )
                );

                if (spawnTimer.getDelay() > 500) {
                    spawnTimer.setDelay((int) (spawnTimer.getDelay() / SPEED_UP));
                }
            }
        };

        //NOTE: interval may not have been intialised yet
        spawnTimer = new Timer(interval, spawnAction);
    }

    /**
     * Stop or pause the trash spawner
     */
    public void stop(){
        spawnTimer.stop();
    }

    /**
     * Start or resume the trash spawner
     */
    public void start(){
        spawnTimer.start();
    }
}
