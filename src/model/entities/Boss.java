package model.entities;

import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Boss extends Entity{

    private static final int BOSS_HEIGHT = 100;
    private static final int BOSS_WIDTH = 200;
    private static final double SPEED = 6;
    private double currentSpeed = SPEED;
    private TrashSpawner spawner;

    private RequestQueue requestQueue;

    public Boss(int x, int y, RequestQueue requestQueue) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT);
        this.requestQueue = requestQueue;
        this.ignoreBounds = true;

        spawner = new TimerTrashSpawner(
                requestQueue,
                y+BOSS_HEIGHT/2,
                0,
                1000);
    }

    /**
     * Indicates that this is a Boss
     * @return EntityType.BOSS
     */
    @Override
    public EntityType getType(){
        return EntityType.BOSS;
    }

    /**
     * Updates the position of the Crab's Bounds based on gravity, drag, and the Crab's speed.
     * Calls Entity's update(gravity, drag) and handles heldTrash movement..
     * @param gravity The gravity applied to the Entity
     * @param drag The drag applied to the Entity
     * @see Entity
     */
    @Override
    public void update(double gravity, double drag){
        spawner.setOffset(getBounds().x+BOSS_WIDTH/2);
        int padding = 50;


        //Check if the center of the ship is in bounds of the screen
        if(getBounds().x+getBounds().getWidth()/2 > getWorldBounds().x+padding
                && getBounds().x+getBounds().getWidth()/2+padding <getWorldBounds().x+getWorldBounds().width){
            spawner.start();
        }else{
            spawner.stop();
        }

        //Change direction if out of bounds
        if((getBounds().x > getWorldBounds().x+getWorldBounds().getWidth() && currentSpeed > 0) ||
            (getBounds().x+getBounds().getWidth() < getWorldBounds().x && currentSpeed < 0)){
            currentSpeed*=-1;
        }
        translate(currentSpeed,0);
    }
}
