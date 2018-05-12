package model.entities;

import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Boss extends Entity{

    private Action turningAction;
    private static final int BOSS_HEIGHT = 100;
    private static final int BOSS_WIDTH = 200;
    private static final double SPEED = 6;
    private double currentSpeed = SPEED;
    private Timer waitTimer;
    private TrashSpawner spawner;

    public Boss(int x, int y, RequestQueue requestQueue) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, requestQueue);
        this.ignoreBounds = true;

        turningAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSpeed = -currentSpeed;
            }
        };

        spawner = new TimerTrashSpawner(
                requestQueue,
                y+BOSS_HEIGHT/2,
                0,
                100);
        //spawner.start();

        waitTimer = new Timer(2000,turningAction);
        waitTimer.start();
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
        //super.update(gravity,drag);
        int padding = 50;

        if(getBounds().x+getBounds().getWidth()/2 > getWorldBounds().x+padding
                && getBounds().x+getBounds().getWidth()/2+padding <getWorldBounds().x+getWorldBounds().width){
            spawner.start();
            System.out.println("IN");
        }else{
            spawner.stop();
        }

        if(getBounds().x > getWorldBounds().x+getWorldBounds().getWidth()){
          //  System.out.println("RIGHT");
        }else if(getBounds().x+getBounds().getWidth() < getWorldBounds().x){
          //  System.out.println("LEFT");
        }
        translate(currentSpeed,0);
    }
}
