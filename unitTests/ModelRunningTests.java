

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
import model.entities.Turtle;
import model.entities.Entity.EntityType;
import model.entities.Player;

public class ModelRunningTests {
	
	static RequestQueue rq = new RequestQueue();
	static Controller c = new Controller();
	static Model m = new Model(rq);
	static TrashFactory f = new TrashFactory(rq);

	
	// Tests the ability of the model to toggle the TrashSpawner
	@Test
	public void trashSpawningToggleTest() {
		m.reset(EntityType.CRAB);

		assertTrue(m.trashSpawning);
		m.toggleTrashSpawning(false);
		assertFalse(m.trashSpawning);
		m.toggleTrashSpawning(true);
		assertTrue(m.trashSpawning);
	}
	
	// Tests the Trash-thrownTrash intersection in update()
	@Test
	public void trashIntersectTests() {
		m.reset(EntityType.CRAB);
		m.toggleTrashSpawning(false);

		// ADD SOME TRASH THAT INTERSECTS, ONE IS 'THROWN' BY PLAYER
		Trash t1 = f.createEasyTrash(150, 150);
		Trash t2 = f.createEasyTrash(150, 150);
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
		assertFalse(m.getThrownTrash().contains(t1));
	}
	
	// Tests the Trash-Barge intersection
	@Test
	public void trashBargeIntersectTest() {
		m.reset(EntityType.CRAB);
		m.toggleTrashSpawning(false);
		
		Trash trash = f.createEasyTrash(215, 50);
		//Trash recycle = f.createHardTrash(300, 75);
		
		Request r1 = RequestFactory.createAddToModelRequest(trash);
		//Request r2 = RequestFactory.createAddToModelRequest(recycle);
		m.handleRequest(r1);
		//m.handleRequest(r2);
		trash.touch();
		//recycle.touch();
		trash.setLocation((int) (m.getWorldBounds().getX() + m.getWorldBounds().getWidth() - 200), (int) m.getWorldBounds().getY());
		//recycle.setLocation((int) m.getWorldBounds().getX(), (int) m.getWorldBounds().getY());
		assertEquals(0, m.getScore());
		m.update();
		rq.fulfillAllRequests();
		assertEquals(30, m.getScore());
		
		
	}
	
	// Tests the Trash-Player intersection in update()
	@Test
	public void trashPlayerIntersectTests() {
		m.reset(EntityType.CRAB);
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
		m.reset(EntityType.CRAB);
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
	
	// Tests the movement of Trash
	@Test
	public void trashMovementTest() {
		m.reset(EntityType.CRAB);
		m.toggleTrashSpawning(false);
		
		Trash t = f.createEasyTrash(200, 200);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		t.setSpeed(5, 10);
		assertEquals(5.0, t.getXSpeed());
		assertEquals(10.0, t.getYSpeed());
		m.update();
		assertEquals(5.0, t.getXSpeed());
		assertEquals(9.95, t.getYSpeed(), 0.01);
		assertEquals(205, t.getBounds().x);
		assertEquals(209, t.getBounds().y);
		t.toggleStopped();
		m.update();
		assertEquals(5.0, t.getXSpeed());
		assertEquals(9.9, t.getYSpeed(), 0.01);
		assertEquals(205, t.getBounds().x);
		assertEquals(209, t.getBounds().y);
	}
	
	// Tests the bounds of Trash
	@Test
	public void trashBoundsTests() {
		
	}
	
	@Test
	public void playerInputTests() {
		// CRAB
		m.reset(EntityType.CRAB);
		m.toggleTrashSpawning(false);
		Crab p = (Crab) m.getPlayer();
		
		// Give Crab Trash to hold
		int crabX = m.getWorldBounds().width/2 - Crab.CRAB_WIDTH/2;
		int crabY = m.getWorldBounds().height/2 - Crab.CRAB_HEIGHT/2;
		Trash t = f.createEasyTrash(crabX, crabY);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		m.update();
		
		// Rotate Trash Left
		p.processInput("ROTATE_TRASH_LEFT");
		assertEquals(Math.PI/2 - Math.PI/32, p.getThrowAngle());
		
		// Rotate Trash Right
		p.processInput("ROTATE_TRASH_RIGHT");
		p.processInput("ROTATE_TRASH_RIGHT");
		assertEquals(Math.PI/2 + Math.PI/32, p.getThrowAngle());
		
		// Throw Trash
		p.processInput("SPECIAL_ACTION");
		rq.fulfillAllRequests();
		assertTrue(m.getThrownTrash().contains(t));
		
		// Left Movement
		int currentX = p.getBounds().x;
		assertEquals(0, p.getCurrentSpeed());
		p.processInput("MOVE_LEFT");
		m.update();
		assertEquals(-4, p.getCurrentSpeed());
		assertEquals(currentX - 4, p.getBounds().x);
		
		// Right Movement
		currentX = p.getBounds().x;
		p.processInput("MOVE_RIGHT");
		m.update();
		assertEquals(4, p.getCurrentSpeed());
		assertEquals(currentX + 4, p.getBounds().x);
		m.update();
		assertEquals(currentX + 8, p.getBounds().x);
		currentX = p.getBounds().x;
		
		// Stop
		p.processInput("STOP");
		m.update();
		assertEquals(0, p.getCurrentSpeed());
		assertEquals(currentX, p.getBounds().x);
		
		// TURTLE
		m.reset(EntityType.TURTLE);
		m.toggleTrashSpawning(false);
		Turtle p2 = (Turtle) m.getPlayer();
		
		// Left Bounce
		Trash t1 = f.createEasyTrash(crabX, crabY);
		r = RequestFactory.createAddToModelRequest(t1);
		m.handleRequest(r);
		
		// Middle Bounce
		Trash t2 = f.createEasyTrash(crabX + 50, crabY);
		r = RequestFactory.createAddToModelRequest(t2);
		m.handleRequest(r);
		
		// Right Bounce
		Trash t3 = f.createEasyTrash(crabX + 125, crabY);
		r = RequestFactory.createAddToModelRequest(t3);
		m.handleRequest(r);

		m.update();
		int middleThird = p2.getBounds().x+p2.getBounds().width/3;
		int rightThird = p2.getBounds().x+2*p2.getBounds().width/3;

		int t1MiddleX = t1.getBounds().x+t1.getBounds().width/2;
		int t2MiddleX = t2.getBounds().x+t2.getBounds().width/2;
		int t3MiddleX = t3.getBounds().x+t3.getBounds().width/2;
		
		assertTrue(t1.intersects(p2) && t1MiddleX < middleThird);
		assertTrue(t2.intersects(p2) && t2MiddleX >= middleThird && t2MiddleX < rightThird);
		assertTrue(t3.intersects(p2) && t3MiddleX >= rightThird);
		
		// Left Movement
		currentX = p2.getBounds().x;
		assertEquals(0, p2.getCurrentSpeed());
		p2.processInput("MOVE_LEFT");
		m.update();
		assertEquals(-2, p2.getCurrentSpeed());
		assertEquals(currentX - 2, p2.getBounds().x);
		
		// Right Movement
		currentX = p2.getBounds().x;
		p2.processInput("MOVE_RIGHT");
		m.update();
		assertEquals(2, p2.getCurrentSpeed());
		assertEquals(currentX + 2, p2.getBounds().x);
		m.update();
		assertEquals(currentX + 4, p2.getBounds().x);
		currentX = p2.getBounds().x;
		
		// Stop
		p2.processInput("STOP");
		m.update();
		assertEquals(0, p2.getCurrentSpeed());
		assertEquals(currentX, p2.getBounds().x);
		
		
		
		
	}
	
	// Tests that the game ends when endGame() is called
	// Also tests that the game is restarted when reset() is called with the right Player
	@Test
	public void gameEndTest1() {
		m.reset(EntityType.CRAB);
		assertTrue(m.getPlayer() instanceof Crab);
		assertFalse(m.gameOver);
		m.endGame();
		assertTrue(m.gameOver);
		m.reset(EntityType.TURTLE);
		assertFalse(m.gameOver);
		assertTrue(m.getPlayer() instanceof Turtle);
	}
	
	// Tests that the game ends when the pollution level reaches 100 in update()
	@Test
	public void gameEndTest2() {
		m.reset(EntityType.CRAB);
		assertFalse(m.gameOver);
		Request r = RequestFactory.createUpdatePollutionRequest(100);
		m.handleRequest(r);
		m.update();
		assertTrue(m.gameOver);
		m.reset(EntityType.CRAB);
		assertFalse(m.gameOver);
		
	}
	

}
