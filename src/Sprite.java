import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    Sprite(SpriteImage spriteImage, Rectangle bounds) {
        this.spriteImage = spriteImage;
        this.bounds = bounds;
    }

    public BufferedImage getImage() {
        return spriteImage.getImage();
    }
}
