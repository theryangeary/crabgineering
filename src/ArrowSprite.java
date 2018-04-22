public class ArrowSprite extends EntitySprite {
    private double angle;
    private boolean is_visible = true;

    ArrowSprite(Bounds bounds){
        super(SpriteImage.ARROW, bounds);
    }

    public void setVisiblity(boolean is_visible){
        this.is_visible = is_visible;
    }

    public void rotate(double dTheta){
        angle += dTheta;
    }
}
