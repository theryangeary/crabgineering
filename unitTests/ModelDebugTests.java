import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.TrashFactory;
import model.entities.Entity.EntityType;

public class ModelDebugTests {

	
	static RequestQueue rq = new RequestQueue();
	static Model m = new Model(rq);
	static TrashFactory f = new TrashFactory(rq);
	
	// Tests debug-specific commands
	@Test
	public void debugTests() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		assertEquals(0, m.getCurrentPollutionLevel());
		m.retireModel();
		assertTrue(m.getEntities().isEmpty());
		rq.postAndFulfillRequest(RequestFactory.createUpdatePollutionRequest(10));
		assertEquals(0, m.getCurrentPollutionLevel());
		
		RequestQueue rq2 = new RequestQueue();
		Model m2 = new Model(rq2);
		m2.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq2.fulfillAllRequests();
		m2.toggleTrashSpawning(false);
		
		m2.restore(rq);
		rq.postAndFulfillRequest(RequestFactory.createUpdatePollutionRequest(10));
		assertEquals(10, m2.getCurrentPollutionLevel());
		
	}
}
