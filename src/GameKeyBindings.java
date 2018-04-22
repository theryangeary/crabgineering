import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The Game's Key Bindings for Player Movement and Action
 */
public class GameKeyBindings {
	
	public GameKeyBindings(JPanel panel, Player player) {
		setKeyBindings(panel, player);
	}
	
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
	
	private class KeyAction extends AbstractAction {
		private Player player;
		
		public KeyAction(String command, Player player) {
			putValue(ACTION_COMMAND_KEY, command);
			this.player = player;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			player.processInput(e.getActionCommand());
		}
		
	}
	
	
}
