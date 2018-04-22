import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JPanel {
	// define size of game
	private final static int FRAME_HEIGHT = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * .9);
	private final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now
	
	private final static int progressBarXPosition = FRAME_WIDTH / 32;
	private final static int progressBarYPosition = FRAME_HEIGHT / 32;
	private final static int progressBarHeight = FRAME_HEIGHT / 24;
	
	private final static int SCOREXPOS = FRAME_WIDTH * 7 / 8; //Position the Score relative to the frame
	private final static int SCOREYPOS = FRAME_HEIGHT / 32;
	
	int score = 0;
	int pollution = 0;
	
	private ArrayList<Sprite> sprites;
	
	private final int pollutionBarScalar = FRAME_WIDTH / Model.MAXPOLLUTIONLEVEL / 3;
	
	JButton pauseButton;
	JPanel buttonPanel;

	JLabel endScore = new JLabel("");
	JFrame frame;

	View() {
		this.add(endScore, BorderLayout.CENTER);
		endScore.setVisible(false);
		initButton();
		initJFrame();
		this.setBackground(Color.cyan);
		sprites = new ArrayList<>();
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
		frame = new JFrame();
		frame.getContentPane().add(this);
		//frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FULLSCREEN BABY
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	public void update(int score, int pollution) {
		this.score = score;
		this.pollution = pollution;
		this.repaint();
	}
	
	public void endGame() {
		endScore.setFont(new Font("TimesRoman", Font.BOLD, 50));
		endScore.setText("Final Score: " + score);
		endScore.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (null != Controller.getModel()) { /// Only paint if a model exists
			for (Sprite sprite : sprites) {
				sprite.draw(g);
			}
			
			// POLLUTION BAR BASE
			g.setColor(Color.black);
			g.fillRect(progressBarXPosition,
					progressBarYPosition,
					pollutionBarScalar * Controller.getModel().getMaxPollutionLevel(),
					progressBarHeight);
			
			// POLLUTION BAR FILLED
			Color seaBlue = new Color(0x3399ff);
			g.setColor(seaBlue);
			g.fillRect(progressBarXPosition,
					progressBarYPosition,
					pollutionBarScalar * pollution,
					progressBarHeight);
			
			// SCORE
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("Score: " + score, SCOREXPOS, SCOREYPOS);
		}
	}
}
