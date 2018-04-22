import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
	
	private static Model model; // It's a static global variable because there's only one model we're ever going to use.
	private static View view;
	private static GameKeyBindings keyBindings;
	private Timer updater;
	private static double FRAMERATE = 144;

	private RequestQueue requests;
	
	/**
	 * Initializes both the view and the model and adds any necessary listeners.
	 * It's the *real* main
	 */
	Controller() {
		requests = new RequestQueue();

		view = new View(requests);
		view.setButtonListener(this);

		model = new Model(new Bounds(View.FRAME_WIDTH, View.FRAME_HEIGHT),
				requests);

		keyBindings = new GameKeyBindings(view, model.getPlayer()); // Sets the key bindings for the game

		int progressBarXPosition = 30;
        int progressBarYPosition = 30;
        int progressBarHeight = 40;
        PollutionBarSprite pollutionBar = new PollutionBarSprite(
                new Rectangle(progressBarXPosition,
                           progressBarYPosition,
                           model.getMaxPollutionLevel(),
                           progressBarHeight),
                model.getCurrentPollutionLevel());
        model.setPollutionListener(pollutionBar);
        view.addSprite(pollutionBar);

		initTimer();
	}
	
	/**
	 * Initializes the timer that triggers both the model's logic updates and the view's drawing updates.
	 */
	private void initTimer() {
		int msPerFrame = (int) ((1 / FRAMERATE) * 1000);
		Action updateAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//UPDATE ENTITIES
				//UPDATE VIEW BASED ON UPDATED ENTITIES
				model.update();
				view.update(model.getScore()); //Update with current score and pollution, then repaint
				requests.fulfillAllRequests();
			}
		};
		updater = new Timer(msPerFrame, updateAction);
		//updater.setDelay();
	}
	
	@Override public void actionPerformed(ActionEvent e) {
	  if (updater.isRunning()) {
		updater.stop();
	  } else {
		updater.start();
	  }
	  view.updateButton(updater.isRunning());
	}
	/**
	 * Starts the timer
	 */
	public void start() {
		EventQueue.invokeLater(() -> updater.start());
	}

	public class AddedEntityListener {

		public void handleAddedEntity(Entity entity){
			Sprite sprite = new EntitySprite(
			    SpriteImage.valueOf(entity.getClass().getName().toUpperCase()),
                entity.getBounds());

		    view.addSprite(sprite);
		}
	}

	public class RemovedEntityListener {
		public void handleRemovedEntity(Entity entity){
		    for (BoundsListener listener: entity.getBounds().getListeners()) {
		        if (listener instanceof Sprite)
		            view.removeSprite((Sprite) listener);
            }
		}
	}

	public interface PollutionListener {
	    void handlePollutionChange(int pollutionLevel);
    }
}


