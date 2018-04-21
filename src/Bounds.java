import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

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

    @Override
    public void translate(int dx, int dy){
        for(BoundsListener boundsListener: listeners)
            boundsListener.handleTranslate(dx, dy);
        super.translate(dx, dy);
    }

    @Override
    public void setLocation(int x, int y){
        for (BoundsListener boundsListener: listeners)
            boundsListener.handleSetLocation(x, y);
        super.setLocation(x, y);
    }
}
