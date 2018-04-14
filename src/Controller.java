import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Controller {
	
	private static Model model; // It's a static global variable because there's only one model we're ever going to use.
	private static View view;
	private Timer updater;
	
	public static Model getModel() {
		return model;
	}
	
	public static View getView() {
		return view;
	}
	
	/**
	 * INITIALIZE ENTITIES
	 * ADD LISTENERS
	 */
	public Controller() {
		view = new View();
		model = new Model();
		initTimer();
	}
	
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
	
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				updater.start();
			}
		});
	}
}


