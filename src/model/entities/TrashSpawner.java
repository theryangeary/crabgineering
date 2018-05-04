package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * TrashSpawner, a time based trash generator
 */
public class TrashSpawner {
    private int interval = 0;
    private TrashFactory factory;
    private Action spawnAction;
    private Timer spawnTimer;
    private int offset;

    /**
     * Generate a TrashSpawner
     * @param requestQueue for controller.requests
     * @param spawnHeight
     * @param spawnWidth Specifies how wide of a range to spawn trash in
     * @param interval time between trash spawning
     * @see Request
     */
    public TrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval){
        //Interval is how long it talks between spawns
        this.interval = interval;
        factory = new TrashFactory(requestQueue);

        //Abstract action that spawns trash randomly
        spawnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Generates a random x position within rage 0
                int randX = (int)(Math.random()*spawnWidth+offset);
                //Decide whether trash should be recyclable or not (50-50 chance)
                boolean recyclable = Math.random() > .5;
                requestQueue.postRequest(
                        RequestFactory.createAddToModelRequest(
                                factory.createEasyTrash(randX,spawnHeight, recyclable)
                        )
                );
                if (spawnTimer.getDelay() > 500) {
                    spawnTimer.setDelay((int) (spawnTimer.getDelay() / 1.03));
                }
            }
        };

        spawnTimer = new Timer(interval, spawnAction);
    }

    /**
     * Set the time interval to spawn trash
     * @param interval time, in ms, between trash generation
     */
    public void setInterval(int interval){
        this.interval = interval;
    }

    /**
     * Get the time interval to spawn trash
     * @return time, in ms, between trash generation
     */
    public int getInterval(){
        return interval;
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

    public void setOffset(int offset){
        this.offset = offset;
    }
}
