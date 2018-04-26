import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class for the graphical components of the game. Updates are called by a Controller.
 * @author Zelinsky
 * @see Controller
 */
public class View extends JPanel implements RequestListener{
	// define size of game
	final static int FRAME_HEIGHT = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height) * .9);
	final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now


	private int score = 0;

	private ArrayList<Sprite> sprites;
	
	private JButton pauseButton;
	private JPanel buttonPanel;

	private JLabel endScore = new JLabel("");
	private JFrame frame;

	/**
	 * Constructs the View by initializing the JFrame and components.
	 * Also sets up the View's RequestQueue.
	 * @param requests The RequestQueue for the View
	 */
	View(RequestQueue requests) {
		this.add(endScore, BorderLayout.CENTER);
		endScore.setVisible(false);
		initButton();
		initJFrame();
		this.setBackground(Color.cyan);
		sprites = new ArrayList<>();
		requests.addListener(this::handleRequest);
	}
	
	/**
	 * Sets up the pause button for the game.
	 */
	private void initButton() {
		buttonPanel = new JPanel();
		pauseButton = new JButton("Pause");
		buttonPanel.add(pauseButton);
		buttonPanel.setBackground(new Color(0, true));
		buttonPanel.setFocusable(false);
		pauseButton.setFocusable(false);
	}
	
	/**
	 * Sets the pause button's listener to the specified ActionListener.
	 * @param l The ActionListener to add to the pause button
	 */
	public void setButtonListener(ActionListener l) {
		pauseButton.addActionListener(l);
	}
	
	/**
	 * Updates the pause button's display text based upon the running state of the timer
	 * @param running The running state of the timer
	 */
	public void updateButton(boolean running) {
		if (running) {
			pauseButton.setText("Pause");
		} else {
			pauseButton.setText("Play");
		}
	}
	
	/**
	 * Sets up the JFrame for the View.
	 */
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

	/**
	 * Specifies how the View should handle a Request
	 * @param request The Request that the View should handle
	 * @see Request
	 */
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case ADD_TO_VIEW:
				if (request.getSpecifics() instanceof Sprite)
					addSprite((Sprite) request.getSpecifics());
				break;
			case REMOVE_FROM_VIEW:
				if (request.getSpecifics() instanceof Sprite)
					removeSprite((Sprite) request.getSpecifics());
				break;
		}
	}

	/**
	 * Add a Sprite to the View
	 * @param sprite The Sprite to add to the View
	 * @see Sprite
	 */
	public void addSprite(Sprite sprite){
		sprites.add(sprite);
	}
	
	/**
	 * Removes a Sprite from the View
	 * @param sprite The Sprite to remove from the View
	 */
	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	/**
	 * Update the View by calling repaint().
	 */
	public void update() {
		this.repaint();
	}
	
	/**
	 * Handles what should happen when the game ends. This method will be called by a Controller.
	 */
	public void endGame() {
		endScore.setFont(new Font("TimesRoman", Font.BOLD, 50));
		endScore.setText("Final Score: " + score);
		endScore.setVisible(true);
	}
	
	/**
	 * Paints the Sprites that are in the View
	 * @param g The Graphics
	 * @see Sprite
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        for (Sprite sprite: sprites) {
            sprite.draw(g);
        }
	}
}
