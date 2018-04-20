public class Trash extends Entity {

    private int pollutionCount;
    
    private boolean thrown = false;
    private final int THROWSPEED = -25;
    private int xThrow = 0;
    private int yThrow = THROWSPEED;

    Trash(int x, int y, int width, int height, int pollutionCount){
        super(x,y,width,height);
        this.pollutionCount = pollutionCount;
    }

    @Override
    protected void initSprite(){
        setSprite(Sprite.TRASH);
    }

    public int getPollutionCount(){
        return pollutionCount;
    }
    
    public void throwTrash() {
    	thrown = true;
    	setSpeed(xThrow, yThrow);
    }
    
    public boolean thrown() {
    	return thrown;
    }
    
    public void rotateLeft() {
    	// ROTATE TRJACTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
    }
    
    public void rotateRight() {
    	// ROTATE TRJACTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
    }
    
}