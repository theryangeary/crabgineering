package view.sprites;

import controller.bounds.Bounds;
import controller.bounds.BoundsListener;
import model.entities.Entity;
import view.estuaryenums.EstuaryImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Connects Sprites to Entities
 */
public class EntitySprite implements Sprite, BoundsListener {
    private final boolean DEBUG = true;
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
            case TRASH:
                return EstuaryImage.SNACK_BAG;
            case RECYCLING:
                return EstuaryImage.SODA_CAN;
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
     * Return the sprite for the entity
     * @return BufferedImage sprite
     */
    protected BufferedImage getImage(){
        return estuaryImage.getImage();
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
