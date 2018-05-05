package controller;

import controller.requests.Request;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Entity;
import view.audio.SoundEffect;
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

		SoundEffect.init();

		//setup pollutionBar
		/*
		int progressBarXPosition = 30;
        int progressBarYPosition = 30;
        int progressBarHeight = 40;
        PollutionBarSprite pollutionBar = new PollutionBarSprite(
                new Rectangle(progressBarXPosition,
                           progressBarYPosition,
                           getMaxPollutionLevel(),
                           progressBarHeight),
                getCurrentPollutionLevel());
        //view.addSprite(pollutionBar);
        requestQueue.addListener(pollutionBar);
        */

		/*
        //setup score
		ScoreSprite scoreSprite = new ScoreSprite();
		//view.addSprite(scoreSprite);
		requestQueue.addListener(scoreSprite);
		*/

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
		start();
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
			case START_GAME:
				model.reset((Entity.EntityType) request.getSpecifics());
				keyBindings = new GameKeyBindings(view, model.getPlayer());
				break;
		}
	}
	
	/**
	 * Toggles the timer (start/stop) and updates the pauseButton in the view based on the timer's toggle state.
	 * @param e The ActionEvent passed in when the pauseButton is pressed
	 * @see View
	 */
	/*
	@Override public void actionPerformed(ActionEvent e) {
		System.out.println("here!!!");

		String command = e.getActionCommand();
		System.out.println(command);

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
			
		case "START_CRAB":
			model.reset(EntityType.CRAB);
		case "START_TURTLE":
			if(model.gameOver)
				model.reset(EntityType.TURTLE);
			keyBindings = new GameKeyBindings(view, model.getPlayer()); // Sets the key bindings for the game
			start();
			break;
			
		case "RESTART_CRAB":
			model.reset(EntityType.CRAB);
		case "RESTART_TURTLE":
			if(model.gameOver)
				model.reset(EntityType.TURTLE);
			keyBindings = new GameKeyBindings(view, model.getPlayer());
			requestQueue.fulfillAllRequests();
			updater.start();
			break;
		}
		//view.updateButton(command, updater.isRunning());
	}
	*/

	
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


