package view.sprites;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the graphics of the pollution bar
 */
public class PollutionBarSprite extends JComponent implements RequestListener {

    private static final Color SEA_BLUE = new Color(0x3399ff);
    private int POLLUTION_BAR_SCALAR = 2;
    private int POLLUTION_BAR_HIEGHT = 40;

    private Rectangle maxArea;
    private int pollutionLevel = 0;

    /**
     * Create a pollution bar specifying max area and current pollution level
     * @param maxArea The Rectangle representing the maximum size of the pollution bar
     * @param pollutionLevel The current pollutionLevel, between 0 and 100
     */
    public PollutionBarSprite() {
        this.maxArea = new Rectangle(
                POLLUTION_BAR_SCALAR * Model.MAX_POLLUTION_LEVEL,
                POLLUTION_BAR_HIEGHT);
        setPreferredSize(new Dimension(maxArea.getSize()));
    }

    /**
     * Update the pollution level if it is an UPDATE_POLLUTION request
     * @param request Change requested to be made. If not an UPDATE_POLLUTION request, nothing happens.
     */
    @Override
    public void handleRequest(Request request){
        switch (request.getRequestedAction()){
            case UPDATE_POLLUTION:
                this.pollutionLevel += (int) request.getSpecifics();
        }
    }

    /**
     * Draw the pollution bar onto g
     * @param g The Graphics object to add the pollution bar to.
     */
    @Override
    public void paintComponent(Graphics g){
        Color origColor = g.getColor();

        g.setColor(Color.black);
        g.fillRect((int) maxArea.getX(),
                   (int) maxArea.getY(),
                   (int) (maxArea.getWidth() * POLLUTION_BAR_SCALAR),
                   (int) maxArea.getHeight());

        g.setColor(SEA_BLUE);
        g.fillRect((int) maxArea.getX(),
                   (int) maxArea.getY(),
                   (int) (pollutionLevel * POLLUTION_BAR_SCALAR),
                   (int) maxArea.getHeight());

        //restore the original color
        g.setColor(origColor);
    }
}
