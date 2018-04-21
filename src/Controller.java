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
	
	public static Model getModel() {
		return model;
	}
	
	public static View getView() {
		return view;
	}
	
	/**
	 * Initializes both the view and the model and adds any necessary listeners.
	 * It's the *real* main
	 */
	Controller() {
		view = new View();
		model = new Model(new Bounds(View.FRAME_WIDTH, View.FRAME_HEIGHT),
				          new AddedEntityListener(),
                          new RemovedEntityListener());
		keyBindings = new GameKeyBindings(view, model.getPlayer()); // Sets the key bindings for the game
		view.setButtonListener(this);
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
				view.repaint(); // Repaint basically calls paintComponent, but efficiently
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
			view.addSprite(entity.initSprite());
		}
	}

	public class RemovedEntityListener {
		public void handleRemovedEntity(Entity entity){
			view.removeSprite(entity.initSprite());
		}
	}
}


