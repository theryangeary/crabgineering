package model.entities;

import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import view.audio.SoundEffect;

public class Barge extends Entity {

    EntityType bargeType;
    RequestQueue requestQueue;

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
}
