package model.entities;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TutorialTrashSpawner extends TrashSpawner {
    /**
     * Generate a TrashSpawner
     *
     * @param requestQueue for requests
     * @param spawnHeight
     * @param spawnWidth   Specifies how wide of a range to spawn trash in
     * @param interval     time between trash spawning
     * @see Request
     */
    public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval) {
        super(requestQueue, spawnHeight, spawnWidth, interval);
    }

    /**
     * Generate a TrashSpawner
     *
     * @param requestQueue for requests
     * @param spawnHeight  Specifies the height at which trash spawns
     * @param spawnWidth   Specifies how wide of a range to spawn trash in
     * @param interval     time between trash spawning
     * @param offset       x offset for the trash spawner
     * @see Request
     */
    public TutorialTrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval, int offset) {
        super(requestQueue, spawnHeight, spawnWidth, interval, offset);
    }
}
