

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Crab;
import model.entities.Trash;
import model.entities.TrashFactory;

public class ModelRunningTests {
	
	static RequestQueue rq = new RequestQueue();
	static Controller c = new Controller();
	static Model m = new Model(rq);
	static TrashFactory f = new TrashFactory(rq);

	
	// Tests the ability of the model to toggle the TrashSpawner
	@Test
	public void trashSpawningToggleTest() {
		assertTrue(m.trashSpawning);
		m.toggleTrashSpawning(false);
		assertFalse(m.trashSpawning);
		m.toggleTrashSpawning(true);
		assertTrue(m.trashSpawning);
	}
	
	// Tests the Trash-thrownTrash intersection in update()
	@Test
	public void trashIntersectTests() {
		m.toggleTrashSpawning(false);

		// ADD SOME TRASH THAT INTERSECTS, ONE IS 'THROWN' BY PLAYER
		Trash t1 = f.createEasyTrash(20, 20);
		Trash t2 = f.createEasyTrash(20, 20);
		Request r1 = RequestFactory.createAddToModelRequest(t1);
		Request r2 = RequestFactory.createAddToModelRequest(t2);
		m.handleRequest(r1);
		m.handleRequest(r2);
		t1.setThrown(true);
		t1.setSpeed(0, -5);
		Request r3 = RequestFactory.createAddThrownTrashRequest(t1);
		m.handleRequest(r3);	
		assertTrue(m.getThrownTrash().contains(t1));
		assertTrue(t2.intersects(t1));
		m.update();
		rq.fulfillAllRequests();
		assertEquals(30, m.getScore());
		assertFalse(m.getThrownTrash().contains(t1));
	}
	
	// Tests the Trash-Player intersection in update()
	@Test
	public void trashPlayerIntersectTests() {
		m.toggleTrashSpawning(false);
		int crabX = m.getWorldBounds().width/2 - Crab.CRAB_WIDTH/2;
		int crabY = m.getWorldBounds().height/2 - Crab.CRAB_HEIGHT/2;
		Trash t = f.createEasyTrash(crabX, crabY);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		m.update();
		assertTrue(m.getPlayer().intersects(t));
	}
	
	// Tests the removal of 'touched' Trash at the top of the screen in update()
	@Test
	public void trashAtTopTests() {
		m.toggleTrashSpawning(false);
		
		Trash t = f.createEasyTrash(100, 100);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		assertTrue(m.getEntities().contains(t));
		t.touch();
		t.setLocation(100, 0);
		m.update();
		assertFalse(m.getEntities().contains(t));
	}
	
	// Tests that the game ends when endGame() is called
	// Also tests that the game is restarted when reset() is called
	@Test
	public void gameEndTest1() {
		assertFalse(m.gameOver);
		m.endGame();
		assertTrue(m.gameOver);
		m.reset();
		assertFalse(m.gameOver);
	}
	
	// Tests that the game ends when the pollution level reaches 100 in update()
	// Also tests that the game is restarted when reset() is called
	@Test
	public void gameEndTest2() {
		assertFalse(m.gameOver);
		Request r = RequestFactory.createUpdatePollutionRequest(100);
		m.handleRequest(r);
		m.update();
		assertTrue(m.gameOver);
		m.reset();
		assertFalse(m.gameOver);
		
	}
	

}
