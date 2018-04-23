public class TrashFactory {

    private RequestQueue requestQueue;

    public TrashFactory(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public Trash createEasyTrash(int x, int y){
        return new Trash(x,y,50,50,1, requestQueue);
    }

    public Trash createHardTrash(int x, int y){
        return new Trash(x,y,10,10,10, requestQueue);
    }
}
