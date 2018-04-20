public class Trash extends Entity {

    private int pollutionCount;
    
    private boolean thrown = false;
    private final int THROWSPEED = -25;

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
    	setSpeed(0, THROWSPEED);
    }
    
    public boolean thrown() {
    	return thrown;
    }
    
}