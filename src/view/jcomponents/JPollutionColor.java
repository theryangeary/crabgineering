package view.jcomponents;

import java.awt.*;
import javax.swing.JPanel;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;
import model.entities.Trash;
import view.estuaryenums.EstuaryImage;

import static view.estuaryenums.EstuaryImage.POLLUTION_EFFECT;

public class JPollutionColor extends JPanel implements RequestListener{

	private int pollutionLevel = 0;
	//private final double MAX_OPACITY = 128.0;
	//private final double OPACITY_INCREMENT = MAX_OPACITY * ((double) Trash.POLLUTION / (double) Model.MAX_POLLUTION_LEVEL);

	public JPollutionColor(){
		super();
		//setLayout( new BorderLayout() );
		setOpaque(false);
	}
	
	@Override
    public void handleRequest(Request request){
        if (request.getRequestedAction().equals(Request.RequestType.UPDATE_POLLUTION)) {
            this.pollutionLevel += (int) request.getSpecifics();
            if (this.pollutionLevel < 0) {
            	this.pollutionLevel = 0;
            }
            /*
            alpha += OPACITY_INCREMENT;
            if (pollutionLevel <= 0) {
            	alpha = 0;
            }
            */
        }
    }

	/**
	 *  Paint the background using the background Color of the
	 *  contained component
	 */
	@Override
	public void paintComponent(Graphics g)
	{
        Graphics2D g2d = (Graphics2D) g;
        Composite oldComposite = g2d.getComposite();

        //System.out.println(((float) pollutionLevel) / Model.MAX_POLLUTION_LEVEL);
        AlphaComposite alphaComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                ((float) pollutionLevel) / Model.MAX_POLLUTION_LEVEL
        );
        g2d.setComposite(alphaComposite);

		g2d.drawImage(EstuaryImage.POLLUTION_EFFECT.getScaledImage(
                getWidth(),
                getHeight()),
				0,
				0,
				null);

		g2d.setComposite(oldComposite);
	}
}
