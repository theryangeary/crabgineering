package view;

import controller.requests.Request;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import view.sprites.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class for the graphical components of the game. Updates are called by a Controller.
 * @author Zelinsky
 * @see Controller
 */
public class View extends JPanel implements RequestListener {
	// define size of game
	public final static int FRAME_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public final static int FRAME_WIDTH = FRAME_HEIGHT;  // It's a square now

	// relative to model
	private Dimension scale;

	private ArrayList<Sprite> sprites;
	
	private JButton startButton;
	private JButton pauseButton;
	private JPanel buttonPanel;

	private JLabel endScore = new JLabel("");
	private JFrame frame;

	/**
	 * Constructs the View by initializing the JFrame and components.
	 * Also sets up the View's RequestQueue.
	 * @param requests The RequestQueue for the View
	 */
	public View(RequestQueue requests) {
		this.add(endScore, BorderLayout.PAGE_START);
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
		// PAUSE BUTTON
		pauseButton = new JButton("Pause");
		pauseButton.setActionCommand("PAUSE");
		buttonPanel.add(pauseButton);
		pauseButton.setFocusable(false);
		pauseButton.setVisible(false);

		//START BUTTON
		startButton = new JButton("Start");
		startButton.setActionCommand("START");
		buttonPanel.add(startButton);
		startButton.setVisible(true);
		startButton.setFocusable(false);
		
		buttonPanel.setBackground(new Color(0, true));
		buttonPanel.setFocusable(false);
	}
	
	/**
	 * Sets the pause button's listener to the specified ActionListener.
	 * @param l The ActionListener to add to the pause button
	 */
	public void setButtonListener(ActionListener l) {
		pauseButton.addActionListener(l);
		startButton.addActionListener(l);
	}
	
	/**
	 * Updates buttons' display text based upon the running state of the timer and the button pressed.
	 * @param button The button to update
	 * @param running The running state of the timer
	 */
	public void updateButton(String button, boolean running) {
		switch(button) {
		case "PAUSE":
			if (running) {
				pauseButton.setText("Pause");
				frame.revalidate();
				frame.repaint();
			} else {
				pauseButton.setText("Play");
				frame.revalidate();
				frame.repaint();
			}
			break;

		case "START":
			startButton.setVisible(false);
			pauseButton.setVisible(true);
			frame.revalidate();
			frame.repaint();
			break;
			
		case "RESTART":
			startButton.setVisible(false);
			pauseButton.setVisible(true);
			endScore.setVisible(false);
			frame.revalidate();
			frame.repaint();
			break;
		}
	}

	/**
	 *
	 */
	private void configurePane(Container pane) {
		//setup the layout
		pane.setLayout(new GridBagLayout());

		//configure layout for the button
		GridBagConstraints buttonCons = new GridBagConstraints();
		buttonCons.gridx = 0;
		buttonCons.gridy = 0;
		buttonCons.weightx = 1; //expand when window gets wider ("" as for view)
		buttonCons.weighty = 0; //don't expand when the window gets taller
		buttonCons.fill = GridBagConstraints.HORIZONTAL;
		pane.add(buttonPanel, buttonCons);

		//configure layout for main game window (ie View)
		GridBagConstraints viewCons = new GridBagConstraints();
		viewCons.gridx = 0;
		viewCons.gridy = 1;
		viewCons.weightx = 1; //expand when window gets wider ("" as button)
		viewCons.weighty = 1; //give this priority when expanding vertically
		viewCons.fill = GridBagConstraints.BOTH;
		pane.add(this, viewCons);
	}

	/**
	 * Sets up the JFrame for the View.
	 */
	private void initJFrame() {
		frame = new JFrame();

		configurePane(frame.getContentPane());
		//frame.getContentPane().add(this);
		//frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.pack();
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
	public void endGame(int score) {
		pauseButton.setVisible(false);
		startButton.setText("Restart");
		startButton.setActionCommand("RESTART");
		startButton.setVisible(true);
		endScore.setFont(new Font("TimesRoman", Font.BOLD, 50));
		endScore.setText("Final Score: " + score);
		endScore.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Paints the Sprites that are in the View
	 * @param g The Graphics
	 * @see Sprite
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		g2d.scale(size.getWidth() / Model.WORLD_WIDTH,
				  size.getHeight() / Model.WORLD_HEIGHT);

        for (Sprite sprite: sprites) {
            sprite.draw(g2d);
        }
	}
}
