public class TrashFactory {

    public Trash createEasyTrash(int x, int y){
        return new Trash(x,y,50,50,1);
    }

    public Trash createHardTrash(int x, int y){
        return new Trash(x,y,10,10,10);
    }
}
