package view.jcomponents;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the graphics of the pollution bar
 */
public class JPollutionBar extends JComponent implements RequestListener {
    public static final int HEIGHT = 64;

    private static final Color SEA_BLUE = new Color(0x3399ff);

    private int pollutionLevel = 0;

    /**
     * Update the pollution level if it is an UPDATE_POLLUTION request
     * @param request Change requested to be made. If not an UPDATE_POLLUTION request, nothing happens.
     */
    @Override
    public void handleRequest(Request request){
        if (request.getRequestedAction().equals(Request.RequestType.UPDATE_POLLUTION)) {
            this.pollutionLevel += (int) request.getSpecifics();
        }
    }

    /**
     * Draw the pollution bar onto g
     * @param g The Graphics object to add the pollution bar to.
     */
    @Override
    protected void paintComponent(Graphics g){
        Color origColor = g.getColor();

        //determine how big the bar should be
        Rectangle bounds = getBounds();
        double progress = ((double) pollutionLevel) / Model.MAX_POLLUTION_LEVEL;

        g.setColor(Color.black);
        g.fillRect((int) bounds.getX(),
                   (int) bounds.getY(),
                   (int) bounds.getWidth(),
                   (int) bounds.getHeight());

        g.setColor(SEA_BLUE);
        g.fillRect((int) bounds.getX(),
                   (int) bounds.getY(),
                   (int) (progress * bounds.getWidth()),
                   (int) bounds.getHeight());

        //restore the original color
        g.setColor(origColor);
    }
}
