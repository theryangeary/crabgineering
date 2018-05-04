package model.entities;

import controller.requests.RequestQueue;
import view.audio.SoundEffect;

/**
 * A model.entities.Turtle is a type of model.entities.Player
 * @author Jimmy
 * @see Player
 */
public class Turtle extends Player{

    public static final int TURTLE_WIDTH = 200;
    public static final int TURTLE_HEIGHT = 100;
    private final int BOUNCE_SPEED = -10;
    private final double BOUNCE_ANGLE = Math.PI/4;

    private static final double SPEED = 2;
    private double currentSpeed = 0;

    private RequestQueue requestQueue;

    public Turtle(int x, int y, RequestQueue requestQueue){
        super(x,y,TURTLE_WIDTH,TURTLE_HEIGHT);
        this.requestQueue = requestQueue;
    }

    /**
     * Indicates that this is a Turtle
     * @return EntityType.TURTLE
     */
    @Override
    public EntityType getType(){
        return EntityType.TURTLE;
    }

    /**
     * Handles how a model.entities.Turtle processes an action command.
     * @param action The action to be performed
     */
    @Override
    public void processInput(String action) {
        switch (PlayerAction.valueOf(action)){
            case MOVE_LEFT:
                currentSpeed = -SPEED;
                break;
            case MOVE_RIGHT:
                currentSpeed = SPEED;
                break;
            case STOP:
                currentSpeed = 0;
        }
    }

    /**
     * Updates the position of the model.entities.Turtle's Bounds based on gravity, drag, and the model.entities.Crab's speed.
     * Calls model.entities.Entity's update(gravity, drag)
     * @param gravity The gravity applied to the model.entities.Entity
     * @param drag The drag applied to the model.entities.Entity
     * @see Entity
     */
    @Override
    public void update(double gravity, double drag){
        super.update(gravity,drag);
        translate(currentSpeed,0);
    }

    /**
     * Handles how model.entities.Trash and model.entities.Turtle respond to intersecting with a model.entities.Trash.
     * @param t The model.entities.Trash that the model.entities.Player intersects with
     */
    @Override
    public void touchTrash(Trash t) {
        t.setThrown(true);

        //Splits the turtle into thirds
        int middleThird = getBounds().x+getBounds().width/3;
        int rightThird = getBounds().x+2*getBounds().width/3;

        int trashMiddleX = t.getBounds().x+t.getBounds().width/2;

        double bounceAngle = 0;

        /**
         *  Depending on where the middle of the trash is relative to the Turtle, it goes left, up, or right.
         */

        if(trashMiddleX < middleThird){
            bounceAngle = BOUNCE_ANGLE;
        }else if(trashMiddleX >= middleThird && trashMiddleX < rightThird){
            bounceAngle = 0;
        }else if(trashMiddleX >= rightThird){
            bounceAngle = -BOUNCE_ANGLE;
        }

        SoundEffect.BOUNCE.play();
        //Apply the velocity to the trash
        t.throwTrash(
                (int) Math.round(BOUNCE_SPEED * Math.sin(bounceAngle)),
                (int) Math.round(BOUNCE_SPEED * Math.cos(bounceAngle)));

    }
}
