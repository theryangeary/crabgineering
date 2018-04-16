import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

public class Controller {
	
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
		model = new Model();
		keyBindings = new GameKeyBindings(view, model); // Sets the key bindings for the game
		initTimer();
	}
	
	/**
	 * Initializes the timer that triggers both the model's logic updates and the view's drawing updates.
	 */
	private void initTimer() {
		int msPerFrame = (int)((1/FRAMERATE)*1000);
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
	
	/**
	 * Starts the timer
	 */
	public void start() {
		EventQueue.invokeLater(() -> updater.start());
	}
}


