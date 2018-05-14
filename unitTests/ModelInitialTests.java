import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.bounds.Bounds;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Player;
import model.entities.Entity.EntityType;

public class ModelInitialTests {
	
	static Model m = new Model(new RequestQueue());
	
	// Tests all final variables' values
	@Test
	public void finalVariablesTest() {
		assertEquals(750, Model.WORLD_WIDTH);
		assertEquals(750, Model.WORLD_HEIGHT);
		assertEquals(10, Model.SCORE_INCREMENT);
		assertEquals(100, m.getMaxPollutionLevel());
	}

	// Tests the initial state just after a new Model is created
	@Test 
	public void initialStateTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		assertTrue(m.getPlayer() instanceof Player);
		assertTrue(m.getWorldBounds() instanceof Bounds);
		assertEquals(0, m.getCurrentPollutionLevel());
		assertTrue(m.getEntities().contains(m.getPlayer()));
		assertTrue(m.trashSpawning);
		assertFalse(m.gameOver);
		
		assertEquals(10, m.getPlayer().getMaxHealth());
		assertEquals(10, m.getPlayer().getCurrentHealth());
	}
	
	

}
