package view.jcomponents;

import controller.requests.Request;
import controller.requests.RequestListener;
import model.Model;
import view.estuaryenums.EstuaryFont;

import javax.swing.*;
import java.awt.*;

/**
 * For handling drawing the Score in game
 */
public class JScoreLabel extends JLabel implements RequestListener {
    private int score = 0;

    /**
     * Sets up the score at zero
     */
    public JScoreLabel(){
        super("Score: 0", SwingConstants.RIGHT);

        setFont(new Font(EstuaryFont.MINECRAFT.name(), Font.PLAIN, 32));
    }

    private void incrementScore(int modifier){
    	if (modifier == 0) {
    		this.score = 0;
    	} else {
        this.score = this.score + (modifier * Model.SCORE_INCREMENT);
    	}
        setText("Score: " + score);
    }

    /**
     * Handle request to update score
     * @param request if an UPDATE_SCORE request, increase the score as specified
     */
    @Override
    public void handleRequest(Request request) {
        if (request.getRequestedAction().equals(Request.RequestType.UPDATE_SCORE)) {
            incrementScore(((int) request.getSpecifics()));
        }
    }
}
