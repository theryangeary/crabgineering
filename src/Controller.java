import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

public class Controller {
	
	private static Model model; // It's a static global variable because there's only one model we're ever going to use.
	private static View view;
	private static GameKeyBindings keyBindings;
	private Timer updater;
	
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
	public Controller() {
		view = new View();
		model = new Model();
		keyBindings = new GameKeyBindings(view, model); // Sets the key bindings for the games
		initTimer();
	}
	
	/**
	 * Initializes the timer that triggers both the model's logic updates and the view's drawing updates.
	 */
	private void initTimer() {
		Action updateAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//UPDATE ENTITIES
				//UPDATE VIEW BASED ON UPDATED ENTITIES
				model.update();
				view.repaint(); // Repaint basically calls paintComponent, but efficiently
			}
		};
		updater = new Timer(50, updateAction);
	}
	
	/**
	 * Starts the timer
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				updater.start();
			}
		});
	}
}


