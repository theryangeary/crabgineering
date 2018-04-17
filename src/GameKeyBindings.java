import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * The Game's Key Bindings for Player Movement and Action
 */
public class GameKeyBindings {

	public GameKeyBindings(JPanel panel, Player player) {
		setKeyBindings(panel, player);
	}
	
	private void setKeyBindings(JPanel panel, Player player) {
		//get the neccessary maps from the JPanel
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

		actionMap.put(Player.PlayerAction.MOVE_LEFT,
				      new KeyAction(Player.PlayerAction.MOVE_LEFT.name(), player));
		actionMap.put(Player.PlayerAction.MOVE_RIGHT,
				      new KeyAction(Player.PlayerAction.MOVE_RIGHT.name(), player));
		actionMap.put(Player.PlayerAction.SPECIAL_ACTION,
					  new KeyAction(Player.PlayerAction.SPECIAL_ACTION.name(), player));
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