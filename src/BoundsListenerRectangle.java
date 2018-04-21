import java.awt.Rectangle;

public class BoundsListenerRectangle extends Rectangle implements BoundsListener {
    @Override
    public void handleTranslate(int dx, int dy){
        translate(dx, dy);
    }

    @Override
    public void handleSetLocation(int x, int y){
        setLocation(x, y);
    }
}
