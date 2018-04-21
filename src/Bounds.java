import java.awt.Rectangle;
import java.util.Collection;

public class Bounds extends Rectangle {
    private Collection<BoundsListener> listeners;

    public void addListener(BoundsListener listener){
        listeners.add(listener);
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
