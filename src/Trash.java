import java.awt.*;

public class Trash extends Entity {
	
	private int pollutionCount;
	
	Trash(int x, int y, int width, int height, int pollutionCount) {
		super(x, y, width, height);
		this.pollutionCount = pollutionCount;
	}
	
	public int getPollutionCount() {
		return pollutionCount;
	}
	
	@Override
	public void draw(Graphics g, Rectangle bounds) {
		g.setColor(Color.green);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
}