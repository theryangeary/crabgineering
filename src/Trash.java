public class Trash extends Entity {
//
    private int pollutionCount;

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
}