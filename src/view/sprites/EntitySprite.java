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
    private final boolean DEBUG = true;
    private final SpriteImage spriteImage;
    private Rectangle bounds;

    /**
     * Create EntitySprite with the entities included bounds
     * @param entity The type of entity to create a sprite for
     */
    public EntitySprite(Entity entity){
        this(imageOf(entity),
             entity.getBounds());
    }

    /**
     * Create EntitySprite with specified bounds
     * @param spriteImage Image for the entity
     * @param bounds Location of the entity
     */
    EntitySprite(SpriteImage spriteImage, Bounds bounds) {
        this.spriteImage = spriteImage;
        setBounds(bounds);
    }

    /**
     * Gets the SpriteImage which corresponds to the given Entity
     * @param entity The Entity to be depicted
     * @return A SpriteImage depicting that Entity
     */
    static SpriteImage imageOf(Entity entity){
        //convert from EntityType to SpriteImage
        switch (entity.getType()){
            case CRAB:
                return SpriteImage.CRAB;
            case TURTLE:
                return SpriteImage.TURTLE;
            case CLAM:
                return SpriteImage.CLAM;
            case SHRIMP:
                return SpriteImage.SHRIMP;
            case TRASH:
                return SpriteImage.TRASH;
            case RECYCLING:
                return SpriteImage.RECYCLING;
            case TRASH_BARGE:
                return SpriteImage.TRASH_BARGE;
            case RECYCLING_BARGE:
                return SpriteImage.RECYCLING_BARGE;
        }

        throw new IllegalArgumentException("Entity without valid EntityType");
    }

    /**
     * Set the EntitySprite's bounds to these bounds
     * Listen to these bounds
     * @param bounds The bounds of this EntitySprite
     */
    void setBounds(Bounds bounds){
        this.bounds = new Rectangle(bounds);
        bounds.addListener(this);
    }

    /**
     * Return this EntitySprite's bounds
     * @return Rectangle bounds
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
     * Change the entity's relatively
     * @param dx change in x bounds
     * @param dy change in y bounds
     */
    @Override
    public void handleTranslate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    /**
     * Change the entity's absolutely
     * @param x new x bound
     * @param y new y bound
     */
    @Override
    public void handleSetLocation(int x, int y) {
        bounds.setLocation(x, y);
    }

    /**
     * Draw this sprite at its bounds
     * @param g Graphics object to draw on
     */
    public void draw(Graphics g){
        Rectangle rectangle = getBounds();

        if(DEBUG){
            g.drawRect((int) rectangle.getX(),
                    (int) rectangle.getY(),
                    (int) rectangle.getWidth(),
                    (int) rectangle.getHeight());
        }


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
