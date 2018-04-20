import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public enum Sprite {
    CLAM("clam.png"),
    CRAB("crab.png"),
    SHRIMP("shrimp.png"),
    TURTLE("turtle.png"),
    TRASH("trash.png"),
	ARROW("arrow.png");

    //should be the same for all Sprites
    final static String IMAGE_DIR = "resources/images/";

    final BufferedImage image;

    Sprite(String image_name){
        //get image should be in the folder IMAGE_DIR
        //and have the lowercase version of the name of the constant
        //with the IMAGE_TYPE file extension
        String imagePath = IMAGE_DIR + image_name;

        //load in the image
        image = createImage(imagePath);
    }

    //Read image from file and return
    private BufferedImage createImage(String imagePath) {
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

    //getters

    public BufferedImage getImage() {
        return image;
    }
}
