import java.awt.*;

public class Trash extends Entity {

    private int pollutionCount;
    
    private boolean thrown = false;
    private final int THROWSPEED = -25;

    Trash(int x, int y, int width, int height, int pollutionCount){
        super(x, y, width, height);
        //System.out.println(String.format(
        //        "Trash: width=%d height=%d",
        //        width, height));
        this.pollutionCount = pollutionCount;
    }

    @Override
    protected void initSprite(){
        setSprite(new Sprite(SpriteImage.TRASH, getBounds()));
    }

    public int getPollutionCount(){
        return pollutionCount;
    }
    
    public void throwTrash() {
    	thrown = true;
    	setSpeed(0, THROWSPEED);
    }
    
    public boolean thrown() {
    	return thrown;
    }
    
}