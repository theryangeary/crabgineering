package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import view.audio.SoundEffect;

/**
 * A Turtle is a type of Player
 * @author Jimmy
 * @see Player
 */
public class Turtle extends Player{

    public static final int TURTLE_WIDTH = 200;
    public static final int TURTLE_HEIGHT = 100;
    private final int BOUNCE_SPEED = 2;
    private final int ANGLE_FACTOR = 10;
    private final double BOUNCE_ANGLE = Math.PI/4;
    private final int TURTLE_CENTER_OFFSET = 4;

    private static final double SPEED = 5;
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
     * Handles how a Turtle processes an action command.
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
     * Updates the position of the Turtle's Bounds based on gravity, drag, and the Crab's speed.
     * Calls Entity's update(gravity, drag)
     * @param gravity The gravity applied to the Entity
     * @param drag The drag applied to the Entity
     * @see Entity
     */
    @Override
    public void update(double gravity, double drag){
        super.update(gravity,drag);
        translate(currentSpeed,0);
    }

    /**
     * Handles how Trash and Turtle respond to intersecting with a Trash.
     * @param t The Trash that the Player intersects with
     */
    @Override
    public void touchTrash(Trash t) {
        t.touch();
        t.setThrown(true);

        double bounceAngle = (
                (this.getBounds().getCenterX() + (this.getBounds().getWidth() / TURTLE_CENTER_OFFSET)) -
                t.getBounds().getCenterX()) / ANGLE_FACTOR;

        SoundEffect.BOUNCE.play();
        //Apply the velocity to the trash
        t.throwTrash(
                (int) - bounceAngle,
                (int) - (t.getYSpeed() * BOUNCE_SPEED));
        requestQueue.postRequest(
            RequestFactory.createAddThrownTrashRequest(t));

    }
    
    /**
     * Returns the model.entities.Turtle's current speed base on input
     * @return the Turtle's current speed
     */
    public double getCurrentSpeed() {
    	return currentSpeed;
    }
}
