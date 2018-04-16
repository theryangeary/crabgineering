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
	
	private JPanel panel;
	private Model model;
	
	public GameKeyBindings(JPanel panel, Model model) {
		this.panel = panel;
		this.model = model;
		setKeyBindings(panel);
	}
	
	private void setKeyBindings(JPanel panel) {
		
		ActionMap actionMap = panel.getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = panel.getInputMap(condition);
		
		String vkLeft = "VK_LEFT";
		String vkRight = "VK_RIGHT";
		String vkSpace = "VK_SPACE";
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), vkSpace);
		
		actionMap.put(vkLeft, new KeyAction(vkLeft));
		actionMap.put(vkRight, new KeyAction(vkRight));
		actionMap.put(vkSpace, new KeyAction(vkSpace));
	}
	
	private class KeyAction extends AbstractAction {
		
		public KeyAction(String command) {
			putValue(ACTION_COMMAND_KEY, command);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			model.getPlayer().processInput(e.getActionCommand());
			
		}
		
	}
	
	
}
