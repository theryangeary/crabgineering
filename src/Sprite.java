import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    Sprite(SpriteImage spriteImage, Rectangle bounds) {
        this.spriteImage = spriteImage;
        this.bounds = bounds;
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
