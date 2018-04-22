import java.awt.*;
import java.awt.image.BufferedImage;

public class EntitySprite implements Sprite, BoundsListener {
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    EntitySprite(SpriteImage spriteImage, Bounds bounds) {
        this.spriteImage = spriteImage;
        setBounds(bounds);
    }

    void setBounds(Bounds bounds){
        this.bounds = new Rectangle(bounds);
        bounds.addListener(this);
    }

    protected Rectangle getBounds(){
        return bounds;
    }

    protected BufferedImage getImage(){
        return spriteImage.getImage();
    }

    @Override
    public void handleTranslate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    @Override
    public void handleSetLocation(int x, int y) {
        bounds.setLocation(x, y);
    }

    public void draw(Graphics g){
        Rectangle rectangle = getBounds();

        g.drawImage(getImage(),
                (int) rectangle.getX(),
                (int) rectangle.getY(),
                (int) rectangle.getWidth(),
                (int) rectangle.getHeight(),
                //a BufferedImage won't change while
                //the image is being loaded, so null
                //will work for our observer
                null);
    }
}
