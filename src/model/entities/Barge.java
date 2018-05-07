package model.entities;

import controller.requests.RequestQueue;
import model.Model;

public class Barge extends Entity {

    EntityType bargeType;
    RequestQueue requestQueue;

    public static final int BARGE_WIDTH = 200;
    public static final int BARGE_HEIGHT = BARGE_WIDTH * 20 / 40;
    public static final int BARGE_PADDING = Model.WATER_HEIGHT - (BARGE_HEIGHT / 2);

    public Barge(int x, int y, int width, int height, EntityType bargeType, RequestQueue requestQueue) {
        super(x, y, width, height);
        this.bargeType = bargeType;
        this.requestQueue = requestQueue;
        toggleStopped(); // so that the boat won't sink
    }

    /**
     * Overrides Entity's update method so that the barge doesn't sink and contribute to the world's overwhelmingly large amount of pollution.
     */
    public void update () {

    }

    /**
     * check which type of ENtity and which type of barge this is
     * @return either TRASH_BARGE or RECYCLING_BARGE
     */
    public EntityType getType() {
        return bargeType;
    }

    /**
     * Check if a piece of trash matches the barge (Trash->TrashBarge, Recycling->RecyclingBarge)
     * @param t the trash to check
     * @return true if the trash matches the barge
     */
    public boolean bargeMatchesTrash(Trash t) {
        return (getType().equals(EntityType.RECYCLING_BARGE) &&
                t.getType().equals(EntityType.RECYCLING)) ||
                (getType().equals(EntityType.TRASH_BARGE) &&
                        t.getType().equals(EntityType.TRASH));
    }
}
