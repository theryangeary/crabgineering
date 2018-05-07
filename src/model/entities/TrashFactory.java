package model.entities;

import controller.requests.RequestQueue;

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
     * @param recyclable indicates whether or not the new trash should be recyclable
     * @return Easy trash object at (x, y)
     */
    public Trash createEasyTrash(int x, int y, boolean recyclable){
        return new Trash(x,y,Trash.TRASH_WIDTH,Trash.TRASH_WIDTH, requestQueue, recyclable);
    }

    /**
     * Create hard trash at specified x and y coordinates
     * @param x x coordinate to spawn trash
     * @param y y coordinate to spawn trash
     * @param recyclable indicates whether or not the new trash should be recyclable
     * @return hard trash object at (x, y)
     */
    public Trash createHardTrash(int x, int y, boolean recyclable){
        return new Trash(x,y,10,10, requestQueue, recyclable);
    }
}
