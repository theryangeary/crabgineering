import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Controller {

	private Model model;
	private View view;
	private Timer updater;

	public Controller() {
		view = new View();
		// INITIALIZE ENTITIES
		// ADD LISTENERS

		initTimer();
	}

	private void initTimer() {
		Action updateAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//UPDATE ENTITIES
				//UPDATE VIEW BASED ON UPDATED ENTITIES
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


