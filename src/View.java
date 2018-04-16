import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
	// define size of game
	private final static int FRAME_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width * 2 / 3;
	private final static int FRAME_HEIGHT = FRAME_WIDTH * 9 / 16;
	
	View() {
		initJFrame();
		this.setBackground(Color.cyan);
	}
	
	private void initJFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity entity : Controller.getModel().getEntities()) {
			entity.draw(g, entity.getBounds());
		}
	}
}
