import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class PollutionBarSprite implements Sprite, RequestListener {

    private static final Color SEA_BLUE = new Color(0x3399ff);

    private Rectangle maxArea;
    private int pollutionLevel;
    private int POLLUTION_BAR_SCALAR = 2;

    public PollutionBarSprite(Rectangle maxArea, int pollutionLevel) {
        this.maxArea = maxArea;
        this.pollutionLevel = pollutionLevel;
    }

    @Override
    public void handleRequest(Request request){
        switch (request.getRequestedAction()){
            case UPDATE_POLLUTION:
                this.pollutionLevel += (int) request.getSpecifics();
        }
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
