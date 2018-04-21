import java.awt.*;

public class Trash extends Entity {

    private int pollutionCount;
    
    private boolean thrown = false;
    
    Trash(int x, int y, int width, int height, int pollutionCount){
        super(x, y, width, height);
        //System.out.println(String.format(
        //        "Trash: width=%d height=%d",
        //        width, height));
        this.pollutionCount = pollutionCount;
    }

    @Override
    protected Sprite initSprite(){
        return new Sprite(SpriteImage.TRASH, getBounds());
    }

    public int getPollutionCount(){
        return pollutionCount;
    }
    
    public void throwTrash(int xSpeed, int ySpeed) {
    	thrown = true;
    	setSpeed(xSpeed, ySpeed);
    }
    
    public boolean thrown() {
    	return thrown;
    }
    
}