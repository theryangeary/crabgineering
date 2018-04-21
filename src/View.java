import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JPanel {
	// define size of game
	private final static int FRAME_HEIGHT = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * .9);
	private final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now
	
	private final static int progressBarXPosition = 30;
	private final static int progressBarYPosition = 30;
	private final static int progressBarHeight = 40;
	
	private final int pollutionBarScalar = 2;
	
	JButton pauseButton;
	JPanel buttonPanel;
	
	View() {
		initButton();
		initJFrame();
		this.setBackground(Color.cyan);
	}
	
	private void initButton() {
		buttonPanel = new JPanel();
		pauseButton = new JButton("Pause");
		buttonPanel.add(pauseButton);
		buttonPanel.setBackground(Color.cyan);
		buttonPanel.setFocusable(false);
		pauseButton.setFocusable(false);
	}
	
	public void setButtonListener(ActionListener l) {
		pauseButton.addActionListener(l);
	}
	
	public void updateButton(boolean running) {
		if (running) {
			pauseButton.setText("Pause");
		} else {
			pauseButton.setText("Play");
		}
	}
	
	private void initJFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FULLSCREEN BABY
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (null != Controller.getModel()) { /// Only paint if a model exists
			for (Entity entity : Controller.getModel().getEntities()) {
				entity.draw(g);
			}
			
			g.setColor(Color.black);
			g.fillRect(progressBarXPosition,
					progressBarYPosition,
					pollutionBarScalar * Controller.getModel().getMaxPollutionLevel(),
					progressBarHeight);
			
			Color seaBlue = new Color(0x3399ff);
			
			g.setColor(seaBlue);
			g.fillRect(progressBarXPosition,
					progressBarYPosition,
					pollutionBarScalar * Controller.getModel().getCurrentPollutionLevel(),
					progressBarHeight);
		}
	}
}
