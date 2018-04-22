public class Trash extends Entity {

	RequestQueue requestQueue;

	private int pollutionCount;
	private final int POLLUTION = 5;
	
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
			requestQueue.postRequest(new Request<>(
					this,
					Request.ActionType.REMOVE
			));
			requestQueue.postRequest(new Request<>(
					1,
					Request.ActionType.UPDATE_SCORE
			));
		}
		if (bottomBound() && dy > 0) {
			dy = 0;
			isAtBottom = true;
			if (!addedPollution) {
				requestQueue.postRequest(new Request<>(
						POLLUTION,
						Request.ActionType.UPDATE_POLLUTION
				));
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