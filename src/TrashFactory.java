public class TrashFactory {

    public Trash createEasyTrash(int x, int y){
        return new Trash(x,y,10,10,1);
    }

    public Trash createHardTrash(int x, int y){
        return new Trash(x,y,10,10,10);
    }
}
