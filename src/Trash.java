import java.awt.*;

public class Trash extends Entity {

    private int pollutionCount;
    private final int POLLUTION = 5;
    
    private boolean thrown = false;
    private boolean addedPollution = false;
    
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
    
    @Override
    void translate(int dx, int dy) {
		int x = dx;
		int y = dy;

		// Bounds check
		if (leftBound() && x < 0) {
			x = 0;
		}
		if (rightBound() && x > 0) {
			x = 0;
		}
		if (topBound() && y < 0) {
			Controller.getModel().toRemove().add(this);
			Controller.getModel().incrementScore(1);
		}
		if (bottomBound() && y > 0) {
			y = 0;
			atBottom = true;
			if (!addedPollution) {
				Controller.getModel().addToPollutionLevel(POLLUTION);
				addedPollution = true;
			}
		}

		bounds.translate(x, y);
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