public class TrashFactory {

    private RequestQueue requestQueue;

    /**
     * Construct a trash factory with the given request queue
     * @param requestQueue request queue for making more trash
     */
    public TrashFactory(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    /**
     * Create easy trash at specified x and y coordinates
     * @param x x coordinate to spawn trash
     * @param y y coordinate to spawn trash
     * @return Easy trash object at (x, y)
     */
    public Trash createEasyTrash(int x, int y){
        return new Trash(x,y,50,50, requestQueue);
    }

    /**
     * Create hard trash at specified x and y coordinates
     * @param x x coordinate to spawn trash
     * @param y y coordinate to spawn trash
     * @return hard trash object at (x, y)
     */
    public Trash createHardTrash(int x, int y){
        return new Trash(x,y,10,10, requestQueue);
    }
}
