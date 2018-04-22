import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JPanel implements RequestListener{
	// define size of game
	final static int FRAME_HEIGHT = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * .9);
	final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now
	
	private final static int SCOREXPOS = 580;
	private final static int SCOREYPOS = 15;
	
	int score = 0;

	private ArrayList<Sprite> sprites;

	JButton pauseButton;
	JPanel buttonPanel;

	View(RequestQueue requests) {
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
		//frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
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

	public void removeSprite(Sprite sprite){
		sprites.remove(sprite);
	}
	
	public void update(int score) {
		this.score = score;
		this.repaint();
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
