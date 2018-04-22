import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ArrowSprite extends EntitySprite {
    private double theta;
    private boolean is_visible = true;

    ArrowSprite(Bounds bounds){
        super(SpriteImage.ARROW, bounds);
    }

    public void setVisiblity(boolean is_visible){
        this.is_visible = is_visible;
    }

    public void rotate(double dTheta){
        theta += dTheta;
    }

    @Override
    public BufferedImage getImage(){
        //create transform
        AffineTransform transform = new AffineTransform();
        BufferedImage image = super.getImage();
        transform.rotate(theta,
                        image.getWidth()/2,
                        image.getHeight()/2);

        //create operation to actually transform an image
        AffineTransformOp op = new AffineTransformOp(
                transform,
                AffineTransformOp.TYPE_BILINEAR);

        //actually rotate the image
        BufferedImage rotatedImage = op.filter(image, null);
        return rotatedImage;
    }

    @Override
    public void draw(Graphics g){
        if (is_visible){
            super.draw(g);
        }
    }
}
