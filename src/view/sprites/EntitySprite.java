package view.sprites;

import controller.bounds.Bounds;
import controller.bounds.BoundsListener;
import model.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Connects Sprites to Entities
 */
public class EntitySprite implements Sprite, BoundsListener {
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    /**
     * Create view.sprites.EntitySprite with the model.entities included controller.bounds
     * @param entity The type of entity to create a sprite for
     */
    public EntitySprite(Entity entity){
        this(SpriteImage.valueOf(entity.getClass().getName().toUpperCase()),
             entity.getBounds());
    }

    /**
     * Create view.sprites.EntitySprite with specified controller.bounds
     * @param spriteImage Image for the entity
     * @param bounds Location of the entity
     */
    EntitySprite(SpriteImage spriteImage, Bounds bounds) {
        this.spriteImage = spriteImage;
        setBounds(bounds);
    }

    /**
     * Set the view.sprites.EntitySprite's controller.bounds to these controller.bounds
     * Listen to these controller.bounds
     * @param bounds The controller.bounds of this view.sprites.EntitySprite
     */
    void setBounds(Bounds bounds){
        this.bounds = new Rectangle(bounds);
        bounds.addListener(this);
    }

    /**
     * Return this view.sprites.EntitySprite's controller.bounds
     * @return Rectangle controller.bounds
     */
    protected Rectangle getBounds(){
        return bounds;
    }

    /**
     * Return the sprite for the entity
     * @return BufferedImage sprite
     */
    protected BufferedImage getImage(){
        return spriteImage.getImage();
    }

    /**
     * Change the entity's controller.bounds relatively
     * @param dx change in x controller.bounds
     * @param dy change in y controller.bounds
     */
    @Override
    public void handleTranslate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    /**
     * Change the entity's controller.bounds absolutely
     * @param x new x bound
     * @param y new y bound
     */
    @Override
    public void handleSetLocation(int x, int y) {
        bounds.setLocation(x, y);
    }

    /**
     * Draw this sprite at its controller.bounds
     * @param g Graphics object to draw on
     */
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
