import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

/*
 * Just a rectangle with listeners, which it informs every time it moves
 */
public class Bounds extends Rectangle {
    private Collection<BoundsListener> listeners;

    public Bounds(int width, int height){
        this(0, 0, width, height);
    }

    public Bounds(int x, int y, int width, int height){
        super(x, y, width, height);

        listeners = new ArrayList<>();
    }

    public void addListener(BoundsListener listener){
        listeners.add(listener);
    }

    public void removeListener(BoundsListener listener){
        listeners.remove(listener);
    }

    public Collection<BoundsListener> getListeners() {
        return listeners;
    }

    @Override
    public void translate(int dx, int dy){
        for(BoundsListener boundsListener: listeners)
            //inform all our listeners about where we're moving
            boundsListener.handleTranslate(dx, dy);

        //then move normally
        super.translate(dx, dy);
    }

    @Override
    public void setLocation(int x, int y){
        for (BoundsListener boundsListener: listeners)
            //inform all our listeners about where we're moving
            boundsListener.handleSetLocation(x, y);

        //then move normally
        super.setLocation(x, y);
    }
}
