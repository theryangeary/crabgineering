package view.jcomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;
import model.entities.Trash;

public class JPollutionColor extends JPanel implements RequestListener{

	private final int RED = 170;
	private final int GREEN = 50;
	private final int BLUE = 0;
	private int alpha = 0;
	private Color brown;
	private int pollutionLevel = 0;
	private final double MAX_OPACITY = 128.0;
	private final double OPACITY_INCREMENT = MAX_OPACITY * ((double) Trash.POLLUTION / (double) Model.MAX_POLLUTION_LEVEL);

	public JPollutionColor(){
		super();
		brown = new Color(RED, BLUE, GREEN, alpha);
		setLayout( new BorderLayout() );
		setOpaque( false );
	}
	
	@Override
    public void handleRequest(Request request){
        if (request.getRequestedAction().equals(Request.RequestType.UPDATE_POLLUTION)) {
            this.pollutionLevel += (int) request.getSpecifics();
            alpha += OPACITY_INCREMENT;
            if (pollutionLevel == 0) {
            	alpha = 0;
            }
            brown = new Color(RED, BLUE, GREEN, alpha);
        }
    }

	/**
	 *  Paint the background using the background Color of the
	 *  contained component
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		g.setColor(brown);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
