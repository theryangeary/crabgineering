package view.jcomponents;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.*;

/**
 * For handling drawing the Score in game
 */
public class JScoreLabel extends JLabel implements RequestListener {
    private static final Font font = new Font("TimesRoman", Font.PLAIN, 20);
    private int score = 0;

    /**
     * Sets up the score at zero
     */
    public JScoreLabel(){
        super("Score: 0", SwingConstants.RIGHT);
        setFont(font);
    }

    private void incrementScore(int dScore){
        score += dScore;
        setText("Score: " + score);
    }

    /**
     * Handle request to update score
     * @param request if an UPDATE_SCORE request, increase the score as specified
     */
    @Override
    public void handleRequest(Request request) {
        if (request.getRequestedAction().equals(Request.RequestType.UPDATE_SCORE)) {
            incrementScore(Model.SCORE_INCREMENT
                    * ((int) request.getSpecifics()));
        }
    }
}
