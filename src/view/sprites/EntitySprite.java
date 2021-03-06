package view.sprites;

import controller.bounds.Bounds;
import controller.bounds.BoundsListener;
import model.entities.Entity;
import view.estuaryenums.EstuaryImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Connects Sprites to Entities
 */
public class EntitySprite implements Sprite, BoundsListener, Serializable {

    private final boolean DEBUG = false;
    private final EstuaryImage estuaryImage;
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
     * @param estuaryImage Image for the entity
     * @param bounds Location of the entity
     */
    EntitySprite(EstuaryImage estuaryImage, Bounds bounds) {
        this.estuaryImage = estuaryImage;
        setBounds(bounds);
    }

    /**
     * Gets the EstuaryImage which corresponds to the given Entity
     * @param entity The Entity to be depicted
     * @return A EstuaryImage depicting that Entity
     */
    static EstuaryImage imageOf(Entity entity){
        //convert from EntityType to EstuaryImage
        switch (entity.getType()){
            case CRAB:
                return EstuaryImage.CRAB;
            case TURTLE:
                return EstuaryImage.TURTLE;
            case CLAM:
                return EstuaryImage.CLAM;
            case SHRIMP:
                return EstuaryImage.SHRIMP;
            case SNACK_BAG:
                return EstuaryImage.SNACK_BAG;
            case STYROFOAM_CUP:
                return EstuaryImage.STYROFOAM_CUP;
            case SODA_CAN:
                return EstuaryImage.SODA_CAN;
            case MILK_JUG:
                return EstuaryImage.MILK_JUG;
            case BOSS:
                return EstuaryImage.BOSS;
            case TRASH_BARGE:
                return EstuaryImage.TRASH_BARGE;
            case RECYCLING_BARGE:
                return EstuaryImage.RECYCLING_BARGE;
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
     * Return the image drawn by this sprite
     * @return BufferedImage the image
     */
    protected Image getImage(){
        return estuaryImage.getImage();
    }

    /**
     * Gets a scaled copy of the image drawn by this sprite
     * @param width the desired width
     * @param height the desired height
     * @return BufferedImage the image, scaled to the given size
     */
    protected Image getScaledImage(int width, int height) {
        return estuaryImage.getScaledImage(width, height);
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
     * @param scaleX
     * @param scaleY
     */
    public void draw(Graphics g, double scaleX, double scaleY){
        Rectangle rectangle = getBounds();

        //scale bounds
        int drawX = (int) (rectangle.getX() * scaleX);
        int drawY = (int) (rectangle.getY() * scaleY);
        int drawWidth = (int) (rectangle.getWidth() * scaleX);
        int drawHeight = (int) (rectangle.getHeight() * scaleY);

        if(DEBUG){
            g.drawRect(drawX, drawY, drawWidth, drawHeight);
        }

        g.drawImage(
                getScaledImage(drawWidth, drawHeight),
                drawX,
                drawY,
                //a BufferedImage won't change while
                //the image is being loaded, so null
                //will work for our observer
                null);
    }
}
