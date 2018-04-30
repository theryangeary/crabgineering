package view.sprites;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;
import view.View;

import java.awt.*;

/**
 * For handling drawing the Score in game
 */
public class ScoreSprite implements Sprite, RequestListener {
    //Position the Score relative to the frame
    private final int x = View.FRAME_WIDTH * 17 / 20;
    private final int y = View.FRAME_HEIGHT / 32;

    private Font font = new Font("TimesRoman", Font.PLAIN, 20);
    private int score = 0;

    /**
     * Handle request to update score
     * @param request if an UPDATE_SCORE request, increase the score as specified
     */
    @Override
    public void handleRequest(Request request) {
        switch (request.getRequestedAction()){
            case UPDATE_SCORE:
                score += Model.SCORE_INCREMENT
                         * ((int) request.getSpecifics());
        }
    }

    /**
     * Draw the score on the frame g
     * @param g the Graphics object to add the score to
     */
    @Override
    public void draw(Graphics g) {
        //store old attributes
        Color lastColor = g.getColor();
        Font lastFont = g.getFont();

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("Score: " + score, x, y);

        //reset old attributes
        g.setColor(lastColor);
        g.setFont(lastFont);
    }
}
