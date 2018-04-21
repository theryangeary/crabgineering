import java.awt.*;

public class Sprite {
    private final SpriteImage spriteImage;
    private final Rectangle bounds; //should ALWAYS == the bounds of the corresponding Sprite

    Sprite(SpriteImage spriteImage, Rectangle bounds) {
        this.spriteImage = spriteImage;
        this.bounds = bounds;
    }

    @Override
    public int hashCode() {
        return bounds.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Sprite) {
            Sprite otherSprite = (Sprite) other;
            return bounds == otherSprite.bounds;
        }

        return false;
    }

    public void draw(Graphics g){
        g.drawImage(spriteImage.getImage(),
                (int) bounds.getX(),
                (int) bounds.getY(),
                (int) bounds.getWidth(),
                (int) bounds.getHeight(),
                //a BufferedImage won't change while
                //the image is being loaded, so null
                //will work for our observer
                null);
    }
}
