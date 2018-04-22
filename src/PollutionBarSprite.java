import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class PollutionBarSprite implements Sprite, Controller.PollutionListener {

    private static final Color SEA_BLUE = new Color(0x3399ff);

    private Rectangle maxArea;
    private static final double POLLUTION_BAR_SCALAR = 2;
    private int pollutionLevel;

    public PollutionBarSprite(Rectangle maxArea, int pollutionLevel) {
        this.maxArea = maxArea;
        this.pollutionLevel = pollutionLevel;
    }

    @Override
    public void handlePollutionChange(int pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    @Override
    public void draw(Graphics g){
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
