import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
	// define size of game
	private final static int frameWidth = 1000;
	private final static int frameHeight = 1000;
	
	View() {
		initJFrame();
		this.setBackground(Color.cyan);
	}
	
	private void initJFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity entity : Controller.getModel().getEntities()) {
			entity.draw(g, entity.getBounds());
		}
	}
}
