package view.estuaryenums;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * EstuaryImage - an enum to handle inclusion of Sprites in game
 */
public enum EstuaryImage {
	CLAM("clam.png"),
	CRAB("crab.png"),
	CRAB_BUTTON("crab-button.png"),
	SHRIMP("shrimp.png"),
	TURTLE("turtle.png"),
	TURTLE_BUTTON("turtle-button.png"),
	SNACK_BAG("snack-bag.png"),
    STYROFOAM_CUP("styrofoam-cup.png"),
	SODA_CAN("soda-can.png"),
    MILK_JUG("milk-jug.png"),
//	ARROW("arrow.png"),
	RECYCLING_BARGE("recycling-barge.png"),
	TRASH_BARGE("trash-barge.png"),
	TITLE("title.png"),
    BOSS("boss.png"),
    BACKGROUND("background.png"),
    FOREGROUND("foreground.png");

    //should be the same for all Sprites
    private final static String IMAGE_DIR = "src/resources/images/";

    private final Image image;

    //info for scale caching
    private Image lastScaledImage;
    private int lastWidth;
    private int lastHeight;

    /**
     * Constructor to create a EstuaryImage
     * @param image_name the type of image to make
     */
    EstuaryImage(String image_name){
        //get image should be in the folder IMAGE_DIR
        //and have the lowercase version of the name of the constant
        //with the IMAGE_TYPE file extension
        String imagePath = IMAGE_DIR + image_name;

        //load in the image
        image = createImage(imagePath);
    }

    /**
     * Read image from file and return
     * @param imagePath relative image path from PWD
     * @return BufferedImage of either the requested image, or null
     */
    public BufferedImage createImage(String imagePath) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(imagePath));
            return bufferedImage;
        } catch (IOException e) {
            System.out.println(imagePath);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get BufferedImage
     * @return The image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Gets a copy of the image held by this EstuaryImage,
     * scaled to the requested size
     * @param width the desired width of the image
     * @param height the desired height of the image
     * @return the scaled image
     */
    public Image getScaledImage(int width, int height){
        //if we want the same scale as last time...
        if (width == lastWidth && height == lastHeight){
            //...just reuse the result from last time
            return lastScaledImage;
        } else {
            //...otherwise scale the image to the new size...
            lastScaledImage = image.getScaledInstance(
                    width, height, Image.SCALE_SMOOTH);

            //...and store the new size
            System.out.println(String.format("%s scaled to %d by %d ",
                    name(), width, height));
            lastWidth = width;
            lastHeight = height;

            return lastScaledImage;
        }
    }
}
