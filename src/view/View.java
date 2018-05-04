package view;

import controller.requests.Request;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import view.sprites.PollutionBarSprite;
import view.sprites.ScoreSprite;
import view.sprites.Sprite;
import view.sprites.SpriteImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
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
	
	private JButton crabButton;
	private JButton turtleButton;
	private JButton pauseButton;
	private JPanel buttonPanel;

	private JLabel titleImage;
	private JLabel endScore = new JLabel("");
	private JFrame frame;

	/**
	 * Constructs the View by initializing the JFrame and components.
	 * Also sets up the View's RequestQueue.
	 * @param requests The RequestQueue for the View
	 */
	public View(RequestQueue requests) {
		createAndShowGUI();

	    //add(endScore, BorderLayout.PAGE_START);
		//endScore.setVisible(false);

		sprites = new ArrayList<>();
		requests.addListener(this::handleRequest);
	}

    /**
     * Sets up the GUI and then makes it visible
     */
    private void createAndShowGUI(){
        //create the JFrame and set it up
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //create a layered pane to serve as the content pane for the frame
        JLayeredPane layeredPane = new JLayeredPane();
        configurePane(layeredPane);
        frame.setContentPane(layeredPane);

        //display the frame
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates everything that needs to be in the content pane,
     * adds it all to the pane, then configures the layout
     */
    private void configurePane(JLayeredPane layeredPane){

        //create all the layers for the layered pane
        ArrayList<Component> layers = new ArrayList<>();



        //add the layers to the layered pane in the right order
        for (int i = 0; i < layers.size(); i++){
            Component layer = layers.get(i);
            layeredPane.add(layer, i);
        }
    }

    /**
	 * Sets up the pause button for the game.
	 */
	private void initButtons() {
		buttonPanel = new JPanel();
		// PAUSE BUTTON
		pauseButton = new JButton("Pause");
		pauseButton.setActionCommand("PAUSE");
		buttonPanel.add(pauseButton);
		pauseButton.setFocusable(false);
		pauseButton.setVisible(false);

		//START BUTTONS
		crabButton = new JButton("Crab");
		crabButton.setActionCommand("START_CRAB");
		buttonPanel.add(crabButton);
		crabButton.setVisible(true);
		crabButton.setFocusable(false);
		
		turtleButton = new JButton("Turtle");
		turtleButton.setActionCommand("START_TURTLE");
		buttonPanel.add(turtleButton);
		turtleButton.setVisible(true);
		turtleButton.setFocusable(false);
		
		buttonPanel.setBackground(new Color(0, true));
		buttonPanel.setFocusable(false);
	}
	
	/**
	 * Sets the pause button's listener to the specified ActionListener.
	 * @param l The ActionListener to add to the pause button
	 */
	public void setButtonListener(ActionListener l) {
		pauseButton.addActionListener(l);
		crabButton.addActionListener(l);
		turtleButton.addActionListener(l);
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

		case "START_CRAB":
		case "START_TURTLE":
			crabButton.setVisible(false);
			turtleButton.setVisible(false);
			pauseButton.setVisible(true);
			this.remove(titleImage);
			frame.revalidate();
			frame.repaint();
			break;
			
		case "RESTART_CRAB":
		case "RESTART_TURTLE":
			crabButton.setVisible(false);
			turtleButton.setVisible(false);
			pauseButton.setVisible(true);
			endScore.setVisible(false);
			frame.revalidate();
			frame.repaint();
			break;
		}
	}

	/*
	private void configurePane(JLayeredPane pane) {
		//setup the layout
		pane.setLayout(new GridBagLayout());

		//configure layout for background
		JPanel background = new JPanel();
		background.setBackground(Color.CYAN);
		GridBagConstraints backCons = new GridBagConstraints();
		backCons.gridx = 0;
		backCons.gridy = 1;
		backCons.gridwidth = 3;
		backCons.weightx = 1; //expand when window gets wider ("" as button)
		backCons.weighty = 1; //give this priority when expanding vertically
		backCons.fill = GridBagConstraints.BOTH;
		pane.add(background, backCons, 2);

		//configure layout for main game window (ie View)
		GridBagConstraints viewCons = new GridBagConstraints();
		viewCons.gridx = 0;
		viewCons.gridy = 1;
		viewCons.gridwidth = 3;
		viewCons.weightx = 1; //expand when window gets wider ("" as button)
		viewCons.weighty = 1; //give this priority when expanding vertically
		viewCons.fill = GridBagConstraints.BOTH;
		pane.add(this, viewCons, 1);

		//configure layout for pollution bar
		PollutionBarSprite pollutionBar = new PollutionBarSprite();
		GridBagConstraints barCons = new GridBagConstraints();
		barCons.gridx = 0;
		barCons.gridy = 1;
		barCons.weighty = 1;
		barCons.anchor = GridBagConstraints.NORTHWEST;
		viewCons.fill = GridBagConstraints.BOTH;
		pane.add(pollutionBar, barCons, 0);

		//configure layout for score
		ScoreSprite score = new ScoreSprite();
		GridBagConstraints scoreCons = new GridBagConstraints();
		scoreCons.gridx = 2;
		scoreCons.gridy = 1;
		scoreCons.weightx = 1;
		scoreCons.fill = GridBagConstraints.BOTH;
		scoreCons.anchor = GridBagConstraints.NORTHEAST;
		pane.add(score, scoreCons, 0);

		//configure layout for the button
		GridBagConstraints buttonCons = new GridBagConstraints();
		buttonCons.gridx = 1;
		buttonCons.gridy = 0;
		//buttonCons.gridwidth = 3;
		buttonCons.weightx = 1; //expand when window gets wider ("" as for view)
		buttonCons.weighty = 0; //don't expand when the window gets taller
		buttonCons.fill = GridBagConstraints.HORIZONTAL;
		pane.add(buttonPanel, buttonCons, 0);
	}
    */

	/**
	 * Sets up the JFrame for the View.
	 */
	private void initJFrame() {
		frame = new JFrame();

		//create a new layered pane to hold the frame's content
		JLayeredPane contentLayeredPane = new JLayeredPane();
		//populate it
		configurePane(contentLayeredPane);
		//and tell the frame to use it
		frame.setContentPane(contentLayeredPane);
		
		titleImage = new JLabel(new ImageIcon(SpriteImage.TITLE.getImage()));
		this.add(titleImage);

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
		crabButton.setActionCommand("RESTART_CRAB");
		crabButton.setVisible(true);
		turtleButton.setActionCommand("RESTART_TURTLE");
		turtleButton.setVisible(true);
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
