import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Trash;
import model.entities.TrashFactory;
import view.estuaryenums.EstuarySound;
import model.entities.Entity.EntityType;

public class ModelRequestTests {
	
	static Model m = new Model(new RequestQueue());
	
	// Tests the handling of the UPDATE_SCORE Request
	@Test
	public void updateScoreTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		assertEquals(0, m.getScore());
		assertEquals(10, Model.SCORE_INCREMENT);
		Request r = RequestFactory.createUpdateScoreRequest(1);
		m.handleRequest(r);
		assertEquals(10, m.getScore());
		
		r = RequestFactory.createUpdateScoreRequest(3);
		m.handleRequest(r);
		assertEquals(40, m.getScore());	
	}
	
	// Tests the handling of the UPDATE_POLLUTION Request
	@Test
	public void updatePollutionTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		assertEquals(0, m.getCurrentPollutionLevel());
		Request r = RequestFactory.createUpdatePollutionRequest(10);
		m.handleRequest(r);
		assertEquals(10, m.getCurrentPollutionLevel());
		
	}
	
	// Tests the handling of the ADD_TO_MODEL, ADD_THROWN_TRASH, and REMOVE_FROM_MODEL Requests
	@Test
	public void addToAndRemoveFromModelTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());

		// ADD SNACK_BAG TO MODEL
		TrashFactory f = new TrashFactory(new RequestQueue());
		Trash t = f.createEasyTrash(15, 15, false);
		Request r1 = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r1);
		assertTrue(m.getEntities().contains(t));
		
		// ADD THROWN SNACK_BAG
		t.setThrown(true);
		Request r2 = RequestFactory.createAddThrownTrashRequest(t);
		m.handleRequest(r2);
		assertTrue(m.getEntities().contains(t));
		assertTrue(m.getThrownTrash().contains(t));
		
		// REMOVE SNACK_BAG
		Request r3 = RequestFactory.createRemoveFromModelRequest(t);
		m.handleRequest(r3);
		assertFalse(m.getEntities().contains(t));
	}
	
	// Tests the TOGGLE_PAUSED Request
	@Test
	public void togglePausedTest() {
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		assertTrue(m.trashSpawning);
		Request r = RequestFactory.createTogglePausedRequest();
		m.handleRequest(r);
		assertFalse(m.trashSpawning);
		m.handleRequest(r);
		assertTrue(m.trashSpawning);
	}
	
	// Tests the handling of the PLAY_SOUND Request
	@Test
	public void playSoundTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());

		Request r = RequestFactory.createPlaySoundRequest(EstuarySound.POINTS.toString());
		m.handleRequest(r);
	}
	
	
}
