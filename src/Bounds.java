import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Bounds is a utility for handling movement and intersection
 * @see java.awt.Rectangle
 */
public class Bounds extends Rectangle {
    private Collection<BoundsListener> listeners;

    /**
     * The two parameter constructer assumes x and y are 0.
     * @param width The width of the entity.
     * @param height The height of the entity.
     */
    public Bounds(int width, int height){
        this(0, 0, width, height);
    }

    /**
     * The four parameter constructor specifies x and y coordinates.
     * It then generates a BoundsListener ArrayList.
     * @param x the x coordinate of the Bound.
     * @param y the y coordinate of the Bound.
     * @param width The width of the entity.
     * @param height The height of the entity.
     */
    public Bounds(int x, int y, int width, int height){
        super(x, y, width, height);

        listeners = new ArrayList<>();
    }

    /**
     * Add a listener to the Bounds list of listeners
     * @param listener The BoundsListener object to add
     * @return the result of ArrayList.add
     */
    public boolean addListener(BoundsListener listener){
        return listeners.add(listener);
    }

    /**
     * Remove a listener from the Bounds' list of listeners
     * @param listener The BoundsListener object to remove
     * @return the result of ArrayList.remove
     */
    public boolean removeListener(BoundsListener listener){
        return listeners.remove(listener);
    }

    /**
     * Returns the list of BoundsListeners
     * @return the Collection of BoundsListener
     */
    public Collection<BoundsListener> getListeners() {
        return listeners;
    }

    /**
     * Translates a set of bounds by a specified x and y change
     * @param dx Change in x (positive is to the right)
     * @param dy Change in y (positive is downwards)
     */
    @Override
    public void translate(int dx, int dy){
        for(BoundsListener boundsListener: listeners)
            //inform all our listeners about where we're moving
            boundsListener.handleTranslate(dx, dy);

        //then move normally
        super.translate(dx, dy);
    }

    /**
     * Sets the location of a set of bounds absolutely
     * @param x The absolute x position
     * @param y The absolute y position
     */
    @Override
    public void setLocation(int x, int y){
        for (BoundsListener boundsListener: listeners)
            //inform all our listeners about where we're moving
            boundsListener.handleSetLocation(x, y);

        //then move normally
        super.setLocation(x, y);
    }
}
