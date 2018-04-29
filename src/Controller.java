import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that controls updates of a View and Model
 * @author Zelinsky
 *
 */
public class Controller implements ActionListener {

	//TODO: make these NOT be static (please...)
	private static Model model; // It's a static global variable because there's only one model we're ever going to use.
	private static View view;
	private static GameKeyBindings keyBindings;
	private static Timer updater;
	private static final double FRAMERATE = 144;

	private RequestQueue requests;
	
	/**
	 * Constructs the Controller.
	 * Initializes both the view and the model and adds any necessary listeners.
	 * It's the *real* main.
	 */
	Controller() {
		requests = new RequestQueue();

		view = new View(requests);
		view.setButtonListener(this);

		model = new Model(requests);
		model.toggleTrashSpawning(false);

		keyBindings = new GameKeyBindings(view, model.getPlayer()); // Sets the key bindings for the game

		SoundEffect.init();

		//setup pollutionBar
		int progressBarXPosition = 30;
        int progressBarYPosition = 30;
        int progressBarHeight = 40;
        PollutionBarSprite pollutionBar = new PollutionBarSprite(
                new Rectangle(progressBarXPosition,
                           progressBarYPosition,
                           model.getMaxPollutionLevel(),
                           progressBarHeight),
                model.getCurrentPollutionLevel());
        view.addSprite(pollutionBar);
        requests.addListener(pollutionBar);

        //setup score
		ScoreSprite scoreSprite = new ScoreSprite();
		view.addSprite(scoreSprite);
		requests.addListener(scoreSprite);

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
				requests.fulfillAllRequests();
				view.update();
			}
		};
		updater = new Timer(msPerFrame, updateAction);
		//updater.setDelay();
	}
	
	/**
	 * Toggles the timer (start/stop) and updates the pauseButton in the view based on the timer's toggle state.
	 * @param e The ActionEvent passed in when the pasueButton is pressed
	 * @see View
	 */
	@Override public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch(command) {

		case "PAUSE":

			if (updater.isRunning()) {
				updater.stop();
				model.toggleTrashSpawning(false);
			} else {
				updater.start();
				model.toggleTrashSpawning(true);
			}
			break;
			
		case "START":
			start();
			model.toggleTrashSpawning(true);
			break;
		
		case "RESTART":
			model.reset();
			keyBindings = new GameKeyBindings(view, model.getPlayer());
			requests.fulfillAllRequests();
			updater.start();
			break;
		}
		view.updateButton(command, updater.isRunning());
	}

	
	/**
	 * Starts the timer.
	 */
	public void start() {
		EventQueue.invokeLater(() -> updater.start());
	}
	
	/**
	 * Stops the timer and sends a message to the view to end the game.
	 * The model detects the necessary requirements for this method to be called.
	 */
	public static void endGame() {
		updater.stop();
		view.endGame(model.getScore());
	}
}


