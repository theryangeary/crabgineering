public class Trash extends Entity {
	
	private int pollutionCount;
	private final int POLLUTION = 5;
	
	private boolean thrown = false;
	private boolean addedPollution = false;
	
	Trash(int x, int y, int width, int height, int pollutionCount) {
		super(x, y, width, height);
		//System.out.println(String.format(
		//        "Trash: width=%d height=%d",
		//        width, height));
		this.pollutionCount = pollutionCount;
	}
	
	@Override
	protected Sprite initSprite() {
		return new Sprite(SpriteImage.TRASH, getBounds());
	}
	
	@Override
	void translate(double dx, double dy) {
		
		// Bounds check
		if (leftBound() && dx < 0) {
			dx = 0;
		}
		if (rightBound() && dx > 0) {
			dx = 0;
		}
		if (topBound() && dy < 0) {
			Controller.getModel().toRemove().add(this);
			Controller.getModel().incrementScore(1);
		}
		if (bottomBound() && dy > 0) {
			dy = 0;
			isAtBottom = true;
			if (!addedPollution) {
				Controller.getModel().addToPollutionLevel(POLLUTION);
				addedPollution = true;
			}
		}
		
		bounds.translate((int) dx, (int) dy);
	}
	
	public int getPollutionCount() {
		return pollutionCount;
	}
	
	public void throwTrash(int xSpeed, int ySpeed) {
		thrown = true;
		setSpeed(xSpeed, ySpeed);
	}
	
	public boolean thrown() {
		return thrown;
	}
	
}