package view;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Entity;
import view.sprites.PollutionBarSprite;
import view.sprites.ScoreSprite;
import view.sprites.Sprite;
import view.sprites.SpriteImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

	//buttons
	private JButton crabButton;
	private JButton turtleButton;
	private JButton pauseButton;
	//private JPanel buttonPanel;

	//other components
	private JLabel titleImage;
	private JLabel endScore = new JLabel("");
	private JFrame frame;

	private RequestQueue requestQueue;

	/**
	 * Constructs the View by initializing the JFrame and components.
	 * Also sets up the View's RequestQueue.
	 * @param requests The RequestQueue for the View
	 */
	public View(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
		requestQueue.addListener(this);

		createAndShowGUI();

	    //add(endScore, BorderLayout.PAGE_START);
		//endScore.setVisible(false);

		sprites = new ArrayList<>();
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
        configureContentPane(layeredPane);
        frame.setContentPane(layeredPane);

        //display the frame
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates everything that needs to be in the content pane,
     * adds it all to the pane, then configures the layout
     * @param layeredPane The content pane to configure
     */
    private void configureContentPane(JLayeredPane layeredPane) {
        layeredPane.setFocusable(false);

        //create all the layers for the layered pane
        ArrayList<Component> layers = new ArrayList<>();

        //Layer 0: background
        layers.add(createBackground());
        //Layer 1: the main game sprites
        layers.add(configureView());
        //Layer 2: foreground
        layers.add(createForeground());
        //Layer 3: in-game UI elements
        layers.add(createHUD());
        //Layer 4: menu UI elements
        layers.add(createMenu());

        //add the layers to the layered pane in the right order
        for (int i = 0; i < layers.size(); i++) {
            Component layer = layers.get(i);
            layeredPane.add(layer, new Integer(i));

            //make layers resize to match the layered pane
            layeredPane.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    layer.setBounds(layeredPane.getBounds());
                }

                @Override
                public void componentMoved(ComponentEvent e) {

                }

                @Override
                public void componentShown(ComponentEvent e) {

                }

                @Override
                public void componentHidden(ComponentEvent e) {

                }
            });
        }
    }

    /**
     * Creates and configures all the elements of the game's background
     * @return A Component holding all the background elements
     */
    private Component createBackground(){
        //create the component to house the background image
        JLabel background = new JLabel();

        //get the background image
        ImageIcon backgroundIcon = new ImageIcon(SpriteImage.BACKGROUND.getImage()) {
            //make sure the background image is scaled correctly
            @Override
            public void paintIcon(Component component, Graphics g, int x, int y){
                g.drawImage(getImage(),
                            0,
                            0,
                            background.getWidth(),
                            background.getHeight(),
                            null);
            }
        };

        //and put it in a component so that it can be displayed
        background.setIcon(backgroundIcon);
        background.setOpaque(false);
        background.setFocusable(false);
        return background;
    }

    private Component configureView(){
        this.setOpaque(false);
        this.setFocusable(false);

        return this;
    }

    /**
     * Creates and configures all the elements of the game's foreground
     * @return A Component holding all the foreground elements
     */
    private Component createForeground(){
        //create a container to hold the image
        JLabel foreground = new JLabel();

        //get the foreground image
        ImageIcon foregroundIcon = new ImageIcon(SpriteImage.FOREGROUND.getImage()) {
            //make sure the foreground image is scaled correctly
            @Override
            public void paintIcon(Component component, Graphics g, int x, int y){
                g.drawImage(getImage(),
                        0,
                        0,
                        foreground.getWidth(),
                        foreground.getHeight(),
                        null);
            }
        };

        //and put it in a component so that it can be displayed
        foreground.setIcon(foregroundIcon);
        foreground.setOpaque(false);
        foreground.setFocusable(false);
        return foreground;
    }

    /**
     * Creates and configures the game's info display elements
     * @return A Component holding all the HUD elemends
     */
    private Component createHUD(){
		//create a container to hold the game's HUD
		JComponent hud = new JPanel(new GridBagLayout());

		//create pollution bar display
		PollutionBarSprite pollutionBarSprite = new PollutionBarSprite();
		pollutionBarSprite.setPreferredSize(new Dimension(0, 64));
		//make it take Requests
		requestQueue.addListener(pollutionBarSprite);
		//and configure it's location in the HUD
		GridBagConstraints pollutionConstraints = new GridBagConstraints();
		pollutionConstraints.gridx = 1;
		pollutionConstraints.weightx = 1;
		pollutionConstraints.weighty = .1;
		pollutionConstraints.fill = GridBagConstraints.HORIZONTAL;
		pollutionConstraints.anchor = GridBagConstraints.NORTHWEST;
		hud.add(pollutionBarSprite, pollutionConstraints);

		//create score display
		ScoreSprite scoreSprite = new ScoreSprite();
		scoreSprite.setPreferredSize(new Dimension(0, 64));
		//make it take Requests
		requestQueue.addListener(scoreSprite);
		//and configure it's location in the HUD
		GridBagConstraints scoreConstraints = new GridBagConstraints();
		scoreConstraints.gridx = 3;
		scoreConstraints.weightx = 1;
		scoreConstraints.weighty = .1;
		scoreConstraints.fill = GridBagConstraints.HORIZONTAL;
		scoreConstraints.anchor = GridBagConstraints.NORTHWEST;
		hud.add(scoreSprite, scoreConstraints);

		hud.setOpaque(false);
		setFocusable(false);
        return hud;
    }

    /**
     * Creates and configures all the elements of the game's title menu
     * @return A Component holding all the UI elements in the title menu
     */
    private Component createMenu(){
        //create a container to hold all the menu elements
        JComponent menu = new JPanel();

		//create the buttons
		crabButton = new JButton("Crab");
		turtleButton = new JButton("Turtle");

		final String PAUSED_TEXT = "play"; //displayed when game is paused
		final String UNPAUSED_TEXT = "pause"; //displayed when game is playing
		pauseButton = new JButton(UNPAUSED_TEXT);

		//decide whether or not each should initially be visible
		crabButton.setVisible(true);
		turtleButton.setVisible(true);
		pauseButton.setVisible(false);

		//prevent the frame from focusing on the buttons so that
		//pressing space won't trigger them
		crabButton.setFocusable(false);
		turtleButton.setFocusable(false);
		pauseButton.setFocusable(false);

		//and decide what happens when each button is pressed
		crabButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//make a request to start a new game with a Crab as the Player
				requestQueue.postAndFulfillRequest(
						RequestFactory.createStartGameRequest(Entity.EntityType.CRAB)
				);

				//make sure the request is fulfilled even if we're paused
				requestQueue.fulfillAllRequests();
			}
		});
		turtleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//make a request to start a new game with a Turtle as the Player
				requestQueue.postAndFulfillRequest(
						RequestFactory.createStartGameRequest(Entity.EntityType.TURTLE)
				);
			}
		});
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//make a request for game play to be paused/resumed
				requestQueue.postAndFulfillRequest(
						RequestFactory.createTogglePausedRequest()
				);

				//and switch which message we're displaying accordingly
				if (pauseButton.getText().equals(PAUSED_TEXT)){
					pauseButton.setText(UNPAUSED_TEXT);
				} else {
					pauseButton.setText(PAUSED_TEXT);
				}
			}
		});

		//add buttons to menu layer
		menu.add(crabButton);
		menu.add(turtleButton);
		menu.add(pauseButton);

        menu.setOpaque(false);
        menu.setFocusable(false);
        return menu;
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
	 * Specifies how the View should handle a Request
	 * @param request The Request that the View should handle
	 * @see Request
	 */
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case START_GAME:
				//make the player selection buttons disappear
				crabButton.setVisible(false);
				turtleButton.setVisible(false);
				//and the pause button appear
				pauseButton.setVisible(true);
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
		crabButton.setVisible(true);
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
