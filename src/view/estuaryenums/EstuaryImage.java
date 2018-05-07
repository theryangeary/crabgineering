package view.estuaryenums;

import javax.imageio.ImageIO;
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
	ARROW("arrow.png"),
	RECYCLING_BARGE("recycling-barge.png"),
	TRASH_BARGE("trash-barge.png"),
	TITLE("title.png"),
    BOSS("boss.png"),
    BACKGROUND("background.png"),
    FOREGROUND("foreground.png");

    //should be the same for all Sprites
    final static String IMAGE_DIR = "src/resources/images/";

    final BufferedImage image;

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
    public BufferedImage getImage() {
        return image;
    }
}