public class Trash extends Entity {

	RequestQueue requestQueue;

	public enum TrashType{
		AGRICULTURAL,
		INDUSTRIAL,
		COMMERCIAL;
	}

	private int pollutionCount;
	private final int POLLUTION = 5;
	private TrashType type;
	
	private boolean thrown = false;
	private boolean addedPollution = false;
	
	Trash(int x, int y, int width, int height, int pollutionCount, RequestQueue requestQueue) {
		super(x, y, width, height);
		//System.out.println(String.format(
		//        "Trash: width=%d height=%d",
		//        width, height));
		this.pollutionCount = pollutionCount;
		this.requestQueue = requestQueue;
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
			requestQueue.postRequest(
					RequestFactory.createRemoveEntityRequest(this)
			);
			requestQueue.postRequest(
					RequestFactory.createUpdateScoreRequest(1)
			);
		}
		if (bottomBound() && dy > 0) {
			dy = 0;
			isAtBottom = true;
			if (!addedPollution) {
				SoundEffect.HIT_GROUND.play();
				requestQueue.postRequest(
						RequestFactory.createUpdatePollutionRequest(POLLUTION)
				);
				addedPollution = true;
			}
		}
		
		getBounds().translate((int) dx, (int) dy);
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