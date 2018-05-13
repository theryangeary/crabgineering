package view;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestListener;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Entity;
import view.estuaryenums.EstuaryFont;
import view.estuaryenums.EstuaryImage;
import view.jcomponents.JEstuaryImageLabel;
import view.jcomponents.JPollutionBar;
import view.jcomponents.JPollutionColor;
import view.jcomponents.JScoreLabel;
import view.sprites.Sprite;

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
	
	// Button Image dimension
	private final int BUTTON_WIDTH = 100;
	private final int BUTTON_HEIGHT = 100;

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
        //Layer 3: pollution
        layers.add(createPollution());
        //Layer 4: in-game UI elements
        layers.add(createHUD());
        //Layer 5: menu UI elements
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
        JLabel background = new JEstuaryImageLabel(EstuaryImage.BACKGROUND);

        //get the background image
        ImageIcon backgroundIcon = new ImageIcon(EstuaryImage.BACKGROUND.getImage()) {
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
        JLabel foreground = new JEstuaryImageLabel(EstuaryImage.FOREGROUND);

        //get the foreground image
        ImageIcon foregroundIcon = new ImageIcon(EstuaryImage.FOREGROUND.getImage()) {
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

		//create score display
		JScoreLabel scoreLabel = new JScoreLabel();
		scoreLabel.setPreferredSize(new Dimension(0, JPollutionBar.HEIGHT));
		//make it take Requests
		requestQueue.addListener(scoreLabel);
		//and configure it's location in the HUD
		GridBagConstraints scoreConstraints = new GridBagConstraints();
		scoreConstraints.gridx = 3;
		scoreConstraints.weightx = 1;
		scoreConstraints.weighty = 1;
		scoreConstraints.insets = new Insets(16, 16, 16, 16);
		scoreConstraints.fill = GridBagConstraints.HORIZONTAL;
		scoreConstraints.anchor = GridBagConstraints.NORTHWEST;
		hud.add(scoreLabel, scoreConstraints);

		GridBagConstraints endScoreConstraints = new GridBagConstraints();
		endScoreConstraints.gridx = 2;
		endScoreConstraints.gridy = 1;
		endScoreConstraints.weighty = 1;
		endScoreConstraints.weightx = 1;
		endScoreConstraints.anchor = GridBagConstraints.EAST;
		endScoreConstraints.fill = GridBagConstraints.BOTH;
		hud.add(endScore, endScoreConstraints);

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

		//create the buttons with images
        Image crabButtonImage = EstuaryImage.CRAB_BUTTON.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH);
        Image turtleButtonImage = EstuaryImage.TURTLE_BUTTON.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH);

        
		crabButton = new JButton("", new ImageIcon(crabButtonImage));
		turtleButton = new JButton("", new ImageIcon(turtleButtonImage));
		
		crabButton.setOpaque(false);
		turtleButton.setOpaque(false);
		crabButton.setContentAreaFilled(false);
		turtleButton.setContentAreaFilled(false);
		crabButton.setBorderPainted(false);
		turtleButton.setBorderPainted(false);
		crabButton.setFocusPainted(false);
		turtleButton.setFocusPainted(false);

		final String PAUSED_TEXT = "Play"; //displayed when game is paused
		final String UNPAUSED_TEXT = "Pause"; //displayed when game is playing
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
				//make a request to start the game with a Crab as the Player
				/*
				requestQueue.postAndFulfillRequest(
						RequestFactory.createStartTutorialRequest(Entity.EntityType.CRAB)
				);
				*/

				requestQueue.postAndFulfillRequest(
						RequestFactory.createStartTutorialRequest(Entity.EntityType.CRAB)
				);
			}
		});
		turtleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//make a request to start the game with a Turtle as the Player
				requestQueue.postAndFulfillRequest(
						RequestFactory.createStartTutorialRequest(Entity.EntityType.TURTLE)
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
    
    /**
     * Creates an AlphaContainer that changes color over time according to the pollution level.
     * @return A Component representing the pollution level with color opacity
     */
    private Component createPollution() {
    	JPollutionColor pc  = new JPollutionColor();
    	requestQueue.addListener(pc);
    	return pc;
    }

	/**
	 * Specifies how the View should handle a Request
	 * @param request The Request that the View should handle
	 * @see Request
	 */
	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case START_TUTORIAL:
			case START_BOSS:
				//same as when starting a normal game here
			case START_GAME:
				//make sure we're not just continuing a game
				if (request.getSpecifics() != null) {
					//reset the sprites
					sprites.clear();

					//make the player selection buttons disappear
					crabButton.setVisible(false);
					turtleButton.setVisible(false);
					endScore.setVisible(false);

					//and the pause button appear
					pauseButton.setVisible(true);
				}
				break;
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
		endScore.setFont(new Font(EstuaryFont.MINECRAFT.name(), Font.BOLD, 50));
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
            sprite.draw(g2d, , );
        }
	}
}