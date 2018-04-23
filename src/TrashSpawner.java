import javax.swing.*;
import java.awt.event.ActionEvent;

/*
    TrashSpawner psudorandomly adds different types of trash on random places on the screen.
 */
public class TrashSpawner {
    private int interval = 0;
    private TrashFactory factory;
    private Action spawnAction;
    private Timer spawnTimer;


    TrashSpawner(RequestQueue requestQueue, int spawnHeight, int spawnWidth, int interval){
        //Interval is how long it talks between spawns
        this.interval = interval;
        factory = new TrashFactory(requestQueue);

        //Abstract action that spawns trash randomly
        spawnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Generates a random x position within rage 0
                int randX = (int)(Math.random()*spawnWidth);
                requestQueue.postRequest(
                        RequestFactory.createAddEntityRequest(
                                factory.createEasyTrash(randX,10)
                        )
                );
            }
        };

        spawnTimer = new Timer(interval, spawnAction);
    }

    public void setInterval(int interval){
        this.interval = interval;
    }
    public int getInterval(){
        return interval;
    }

    public void stop(){
        spawnTimer.stop();
    }
    public void start(){
        spawnTimer.start();
    }
}
