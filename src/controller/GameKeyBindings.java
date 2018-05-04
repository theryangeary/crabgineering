package controller;

import model.entities.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The Game's Key Bindings for Player Movement and Action
 * @author Zelinsky
 */
public class GameKeyBindings {
	
	/**
	 * Constructs a controller.GameKeyBindings by calling setKeyBindings(panel, player). Maps the inputs from the panel to the player.
	 * @param panel The JPanel to get input from
	 * @param player The Player to modify based on inputs from panel
	 */
	public GameKeyBindings(JPanel panel, Player player) {
		setKeyBindings(panel, player);
	}
	
	/**
	 * Maps the inputs from panel to player.
	 * @param panel The JPanel to get input from
	 * @param player The Player to modify based on inputs from panel
	 */
	private void setKeyBindings(JPanel panel, Player player) {
		//get the necessary maps from the JPanel
		ActionMap actionMap = panel.getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = panel.getInputMap(condition);
		
		//map KeyStrokes to player actions
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
				Player.PlayerAction.MOVE_LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
				Player.PlayerAction.MOVE_RIGHT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
				Player.PlayerAction.SPECIAL_ACTION);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0),
				Player.PlayerAction.ROTATE_TRASH_LEFT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0),
				Player.PlayerAction.ROTATE_TRASH_RIGHT);

		//when left or right is released, stop
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),
				Player.PlayerAction.STOP);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),
				Player.PlayerAction.STOP);
		
		actionMap.put(Player.PlayerAction.MOVE_LEFT,
				new KeyAction(Player.PlayerAction.MOVE_LEFT.name(), player));
		actionMap.put(Player.PlayerAction.MOVE_RIGHT,
				new KeyAction(Player.PlayerAction.MOVE_RIGHT.name(), player));
		actionMap.put(Player.PlayerAction.SPECIAL_ACTION,
				new KeyAction(Player.PlayerAction.SPECIAL_ACTION.name(), player));
		actionMap.put(Player.PlayerAction.ROTATE_TRASH_LEFT,
				new KeyAction(Player.PlayerAction.ROTATE_TRASH_LEFT.name(), player));
		actionMap.put(Player.PlayerAction.ROTATE_TRASH_RIGHT,
				new KeyAction(Player.PlayerAction.ROTATE_TRASH_RIGHT.name(), player));
		actionMap.put(Player.PlayerAction.STOP,
				new KeyAction(Player.PlayerAction.STOP.name(), player));
	}
	
	/**
	 * A type of AbstractAction specifically for key inputs
	 */
	private class KeyAction extends AbstractAction {
		private Player player;
		
		/**
		 * Constructs a KeyAction.
		 * @param command The command corresponding the the key input
		 * @param player The Player to modify based on the command
		 */
		public KeyAction(String command, Player player) {
			putValue(ACTION_COMMAND_KEY, command);
			this.player = player;
		}
		
		/**
		 * Handles what happens when a key input is registered.
		 * Calls Player's processInput(e.getActionCommand()).
		 * @see Player
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			player.processInput(e.getActionCommand());
		}
		
	}
	
	
}
