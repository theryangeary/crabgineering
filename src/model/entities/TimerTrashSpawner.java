package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TimerTrashSpawner extends TrashSpawner {
    private int interval;
    private Timer spawnTimer;

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

                if (spawnTimer.getDelay() > 500) {
                    spawnTimer.setDelay((int) (spawnTimer.getDelay() / 1.02));
                }
            }
        };

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
