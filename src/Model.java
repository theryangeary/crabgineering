import java.util.ArrayList;

public class Model {
	private int worldHeight = Controller.getView().getHeight();
	private int worldWidth = Controller.getView().getWidth();
	ArrayList<Entity> entities = new ArrayList<>();
	
	Model() {
		Crab crabby = new Crab();
		entities.add(crabby);
	}
	
	public void update() {
		//TODO
	}
	
	
}