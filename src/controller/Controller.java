package controller;

import controller.requests.Request;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Entity;
import view.estuaryenums.EstuarySound;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A class that controls updates of a View and Model
 * @author Zelinsky
 *
 */
public class Controller implements RequestListener {

	//TODO: make these NOT be static (please...)
	private static Model model; // It's a static global variable because there's only one model we're ever going to use.
	private static View view;
	private static GameKeyBindings keyBindings;
	private static Timer updater;
	private static final double FRAMERATE = 144;

	private RequestQueue requestQueue;
	
	/**
	 * Constructs the Controller.
	 * Initializes both the view and the model and adds any necessary listeners.
	 * It's the *real* main.
	 */
	public Controller() {
		requestQueue = new RequestQueue();
		requestQueue.addListener(this);

		view = new View(requestQueue);
		//view.setButtonListener(this);

		model = new Model(requestQueue);

		EstuarySound.init();

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
				requestQueue.fulfillAllRequests();
				view.update();
			}
		};
		updater = new Timer(msPerFrame, updateAction);
		//updater.setDelay();
	}

	/**
	 * Allows Controller to handle Requests
	 * @param request The Request to be handled
	 */
	@Override
	public void handleRequest(Request request){
		switch (request.getRequestedAction()){
			case TOGGLE_PAUSED:
				if (updater.isRunning()){
					updater.stop();
				} else {
					updater.start();
				}
				break;
			case START_TUTORIAL:
				//if we do this right away, the model will post a bunch of ADD_TO_VIEW Requests
				//and cause concurrent modification problems, so just wait a bit
				EventQueue.invokeLater(
						new Runnable() {
							@Override
							public void run() {
								model.reset((Entity.EntityType) request.getSpecifics());
								keyBindings = new GameKeyBindings(view, model.getPlayer());
								requestQueue.fulfillAllRequests();
								start();
							}
						});
				break;
			case START_GAME:
				//if we do this right away, the model will post a bunch of ADD_TO_VIEW Requests
				//and cause concurrent modification problems, so just wait a bit
				EventQueue.invokeLater(
						new Runnable() {
							@Override
							public void run() {
								model.reset((Entity.EntityType) request.getSpecifics());
								keyBindings = new GameKeyBindings(view, model.getPlayer());
								requestQueue.fulfillAllRequests();
								start();
							}
						});
				break;
		}
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


