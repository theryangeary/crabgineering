public class Trash extends Entity {

    private int pollutionCount;
    
    private boolean thrown = false;
    
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
    
    public void throwTrash(int xSpeed, int ySpeed) {
    	thrown = true;
    	setSpeed(xSpeed, ySpeed);
    }
    
    public boolean thrown() {
    	return thrown;
    }
    
}