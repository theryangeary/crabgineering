import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JPanel implements RequestListener{
	// define size of game
	final static int FRAME_HEIGHT = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * .9);
	final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now

	private final static int SCOREXPOS = FRAME_WIDTH * 17 / 20; //Position the Score relative to the frame
	private final static int SCOREYPOS = FRAME_HEIGHT / 32;
	
	int score = 0;
	int pollution = 0;
	
	private ArrayList<Sprite> sprites;
	
	private final int pollutionBarScalar = FRAME_WIDTH / Model.MAXPOLLUTIONLEVEL / 3;
	
	JButton pauseButton;
	JPanel buttonPanel;

	JLabel endScore = new JLabel("");
	JFrame frame;

	View(RequestQueue requests) {
		this.add(endScore, BorderLayout.CENTER);
		endScore.setVisible(false);
		initButton();
		initJFrame();
		this.setBackground(Color.cyan);
		sprites = new ArrayList<>();
		requests.addListener(this::handleRequest);
	}
	
	private void initButton() {
		buttonPanel = new JPanel();
		pauseButton = new JButton("Pause");
		buttonPanel.add(pauseButton);
		buttonPanel.setBackground(new Color(0, true));
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
		frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // FULLSCREEN BABY
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case ADD:
				if (request.getSpecifics() instanceof Sprite)
					addSprite((Sprite) request.getSpecifics());
				break;
			case REMOVE:
				System.out.println("View removing sprite");
				if (request.getSpecifics() instanceof Sprite)
					removeSprite((Sprite) request.getSpecifics());
				break;
		}
	}

	public void addSprite(Sprite sprite){
		sprites.add(sprite);
	}
	
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	public void update(int score) {
		this.score = score;
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
		
		//if (null != Controller.getModel()) { /// Only paint if a model exists
			for (Sprite sprite: sprites) {
				sprite.draw(g);
			}
			// SCORE
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString("Score: " + score, SCOREXPOS, SCOREYPOS);
		//}
	}
}
