import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Iterator;

public class View extends JPanel {
	// define size of game
	final static int frameWidth = 1000;
	final static int frameHeight = 1000;
	
	private JFrame frame;
	
	public View() {
		initJFrame();
		this.setBackground(Color.cyan);
	}
	
	private void initJFrame() {
		frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity entity : Controller.getModel().entities) {
			entity.draw(g, entity.getBounds());
		}
		
	}
}
