package controller.bounds;

public interface BoundsListener {
    //Should be able to deal with the controller.bounds.Bounds
    //that it's listening to moving
    void handleTranslate(int dx, int dy);
    void handleSetLocation(int x, int y);
}
