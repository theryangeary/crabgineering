import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JPanel {
	// define size of game
	private final static int frameWidth = 1000;
	private final static int frameHeight = 1000;
	
	private final static int progressBarXPosition = 30;
	private final static int progressBarYPosition = 30;
	private final static int progressBarHeight = 40;

	private int pollutionBarScalar = 2;

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

	public void updateButton (boolean running) {
	  if (running) {
		pauseButton.setText("Pause");
	  } else {
		pauseButton.setText("Play");
	  }
	}

	private void initJFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FULLSCREEN BABY
		frame.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity entity : Controller.getModel().getEntities()) {
			entity.draw(g);
		}
		
		g.setColor(Color.black);
		g.fillRect(progressBarXPosition, progressBarYPosition,
			pollutionBarScalar * Controller.getModel().getMaxPollutionLevel(), progressBarHeight);

		g.setColor(new Color(0x33, 0x99, 0xFF));
		g.fillRect(progressBarXPosition, progressBarYPosition,
			pollutionBarScalar * Controller.getModel().getCurrentPollutionLevel(), progressBarHeight);
	}
}
