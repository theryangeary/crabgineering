

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.bounds.Bounds;
import controller.requests.Request;
import controller.requests.RequestFactory;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.Barge;
import model.entities.Boss;
import model.entities.Crab;
import model.entities.Trash;
import model.entities.TrashFactory;
import model.entities.TrashSpawner;
import model.entities.Turtle;
import model.entities.Entity.EntityType;

public class ModelRunningTests {
	
	static RequestQueue rq = new RequestQueue();
	static Controller c = new Controller();
	static Model m = new Model(rq);
	static TrashFactory f = new TrashFactory(rq);

	
	// Tests the ability of the model to toggle the TrashSpawner
	@Test
	public void trashSpawningTest() {
		/*m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		
		// Spawner
		TrashSpawner ts = new TrashSpawner(rq, 0, 5, 2000);
		assertEquals(2000, ts.getInterval());
		ts.setInterval(50);
		assertEquals(50, ts.getInterval());
		ts.setOffset(50);
		
		// Toggle
		m.toggleTrashSpawning(false);
		assertFalse(m.trashSpawning);
		m.toggleTrashSpawning(true);
		assertTrue(m.trashSpawning);
		
		// Request
		Request r = RequestFactory.createTogglePausedRequest();
		m.handleRequest(r);
		assertFalse(m.trashSpawning);
		m.handleRequest(r);
		assertTrue(m.trashSpawning);*/
		
		// Trash Type
		Trash trash = f.createEasyTrash(50, 50, false);
		Trash recycle = f.createEasyTrash(150, 150, true);
		//assertEquals(EntityType.TRASH, trash.getType());
		//assertEquals(EntityType.RECYCLING, recycle.getType());
		
		
	}
	
	// Tests the Trash-thrownTrash intersection in update()
	@Test
	public void trashIntersectTests() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();

		m.toggleTrashSpawning(false);

		// ADD SOME SNACK_BAG THAT INTERSECTS, ONE IS 'THROWN' BY PLAYER
		Trash t1 = f.createEasyTrash(150, 150, false);
		Trash t2 = f.createEasyTrash(150, 150, false);
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
	
	// Tests the Trash-Barge and Recycle-Barge intersection
	@Test
	public void trashBargeIntersectTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		Trash trash = f.createEasyTrash(215, 50, false);
		Trash recycle = f.createHardTrash(300, 75, true);
		
		Request r1 = RequestFactory.createAddToModelRequest(trash);
		Request r2 = RequestFactory.createAddToModelRequest(recycle);
		m.handleRequest(r1);
		m.handleRequest(r2);
		trash.touch();
		recycle.touch();
		trash.setLocation((int) (m.getWorldBounds().getX() + m.getWorldBounds().getWidth() - Barge.BARGE_WIDTH - Barge.BARGE_PADDING), (int) m.getWorldBounds().getY() + Barge.BARGE_PADDING);
		recycle.setLocation((int) m.getWorldBounds().getX() + Barge.BARGE_PADDING, (int) m.getWorldBounds().getY() + Barge.BARGE_PADDING);
		assertEquals(0, m.getScore());
		m.update();
		rq.fulfillAllRequests();
		assertEquals(60, m.getScore());
		
		
	}
	
	// Tests the Trash-Player intersection in update()
	@Test
	public void trashPlayerIntersectTests() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		int crabX = m.getWorldBounds().width/2 - Crab.CRAB_WIDTH/2;
		int crabY = m.getWorldBounds().height/2 - Crab.CRAB_HEIGHT/2;
		Trash t = f.createEasyTrash(crabX, crabY, false);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		m.update();
		assertTrue(m.getPlayer().intersects(t));
	}
	
	/* NOT A FEATURE ANYMORE
	// Tests the removal of 'touched' Trash at the top of the screen in update()
	@Test
	public void trashAtTopTests() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		Trash t = f.createEasyTrash(100, 100, false);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		assertTrue(m.getEntities().contains(t));
		t.touch();
		t.setLocation(100, 0);
		m.update();
		assertFalse(m.getEntities().contains(t));
	}*/
	
	// Tests the movement of Trash
	@Test
	public void trashMovementTest() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		Trash t = f.createEasyTrash(200, 200, false);
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
		assertEquals(9.95, t.getYSpeed(), 0.01);
		assertEquals(205, t.getBounds().x);
		assertEquals(209, t.getBounds().y);
	}
	
	// Tests the bounds of Trash and Player
	@Test
	public void trashAndPlayerWorldBoundsTests() {
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		Turtle p = (Turtle) m.getPlayer();
		
		int bottom = (int) m.getWorldBounds().getMaxY();
		int top = (int) m.getWorldBounds().getY();
		int left = (int) m.getWorldBounds().getX();
		int right = (int) m.getWorldBounds().getMaxX();

		// SNACK_BAG
		// Bottom
		assertEquals(0, m.getCurrentPollutionLevel());
		Trash t = f.createEasyTrash(5, bottom - 100, false);
		t.setSpeed(0, 55);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		for (int i = 0; i<8; i++) {
			m.update();
		}
		rq.fulfillAllRequests();
		assertEquals(10, m.getCurrentPollutionLevel());
		assertEquals(bottom, t.getBounds().getMaxY(), 25);
		
		
		// Top
		// TO CHANGE
		t = f.createEasyTrash(right/2 + 50, top - 100, false);
		t.setSpeed(0, -50);
		r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		assertTrue(m.getEntities().contains(t));
		assertEquals(0, m.getScore());
		for(int i=0;i<100;i++) {
			m.update();
			rq.fulfillAllRequests();
		}
		rq.fulfillAllRequests();
		assertTrue(m.getEntities().contains(t));
		assertTrue(t.getYSpeed() > 0);
		
		// Left
		t = f.createEasyTrash(25, bottom - 150, false);
		t.setSpeed(-50, 0);
		r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		for(int i=0;i<10;i++) {
			m.update();
		}
		assertEquals(left, t.getBounds().getX(), 25);
		
		// Right
		t = f.createEasyTrash(right - 25, bottom - 150, false);
		t.setSpeed(50, 0);
		r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		for(int i=0;i<10;i++) {
			m.update();
		}
		assertEquals(right, t.getBounds().getX(), 25);
				
		// PLAYER
		// Bottom
		p.setLocation(100, bottom - 100);
		for (int i=0;i<50;i++) {
			m.update();
		}
		assertEquals(bottom, p.getBounds().getMaxY(), 25);
		
		// Left
		p.setLocation(left + 50, p.getBounds().y);
		p.processInput("MOVE_LEFT");
		for (int i=0;i<100;i++) {
			m.update();
		}
		assertEquals(left, p.getBounds().getX(), 25);
		
		// Right
		p.setLocation(right - 200, p.getBounds().y);
		p.processInput("MOVE_RIGHT");
		for (int i=0;i<100;i++) {
			m.update();
		}
		assertEquals(right, p.getBounds().getMaxX(), 25);
		
		// TOP
		p.processInput("STOP");
		p.setLocation(200, top + 200);
		p.setSpeed(0, -50);
		for (int i=0; i<100; i++) {
			m.update();
		}
		assertEquals(top, p.getBounds().getY(), 50);
		
	}
	
	// Tests the inputs for Crab and Turtle
	@Test
	public void playerInputTests() {
		// CRAB
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		Crab p = (Crab) m.getPlayer();
		
		// Give Crab Trash to hold
		int crabX = m.getWorldBounds().width/2 - Crab.CRAB_WIDTH/2;
		int crabY = m.getWorldBounds().height/2 - Crab.CRAB_HEIGHT/2;
		Trash t = f.createEasyTrash(crabX, crabY, false);
		Request r = RequestFactory.createAddToModelRequest(t);
		m.handleRequest(r);
		m.update();
		
		/* NOT A FEATURE ANYMORE
		// Rotate Trash Left
		p.processInput("ROTATE_TRASH_LEFT");
		m.update();
		assertEquals(Math.PI/2 - Math.PI/40, p.getThrowAngle());
		
		// Stop rotating Trash
		p.processInput("STOP_ROTATE");
		m.update();
		assertEquals(Math.PI/2 - Math.PI/40, p.getThrowAngle());
		
		// Rotate Trash Right
		p.processInput("ROTATE_TRASH_RIGHT");
		m.update();
		m.update();
		assertEquals(Math.PI/2 + Math.PI/40, p.getThrowAngle());*/
		
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
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		Turtle p2 = (Turtle) m.getPlayer();
		
		// Left Bounce
		Trash t1 = f.createEasyTrash(crabX, crabY, false);
		r = RequestFactory.createAddToModelRequest(t1);
		m.handleRequest(r);
		
		// Middle Bounce
		Trash t2 = f.createEasyTrash(crabX + 50, crabY, false);
		r = RequestFactory.createAddToModelRequest(t2);
		m.handleRequest(r);
		
		// Right Bounce
		Trash t3 = f.createEasyTrash(crabX + 125, crabY, false);
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
		assertEquals(-5, p2.getCurrentSpeed());
		assertEquals(currentX - 5, p2.getBounds().x);
		
		// Right Movement
		currentX = p2.getBounds().x;
		p2.processInput("MOVE_RIGHT");
		m.update();
		assertEquals(5, p2.getCurrentSpeed());
		assertEquals(currentX + 5, p2.getBounds().x);
		m.update();
		assertEquals(currentX + 10, p2.getBounds().x);
		currentX = p2.getBounds().x;
		
		// Stop
		p2.processInput("STOP");
		m.update();
		assertEquals(0, p2.getCurrentSpeed());
		assertEquals(currentX, p2.getBounds().x);
	}
	
	// Test worldBounds stuff
	@Test
	public void otherTests() {
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		rq.fulfillAllRequests();
		m.toggleTrashSpawning(false);
		
		Trash t = f.createEasyTrash(50, 50, false);
		t.setWorldBounds((Bounds) m.getWorldBounds());
		assertEquals(m.getWorldBounds(), t.getWorldBounds());
		t.handleSetLocation(5, 10);
		assertEquals(5, t.getWorldBounds().x);
		assertEquals(10, t.getWorldBounds().y);
		t.handleTranslate(5, 10);
		assertEquals(10, t.getWorldBounds().x);
		assertEquals(20, t.getWorldBounds().y);
	}
	
	// Tests the Boss's behavior TODO
	@Test
	public void bossTests() {
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		rq.fulfillAllRequests();
		
		Boss b = new Boss(50, 50, rq);
		assertEquals(EntityType.BOSS, b.getType());
		
		Request r = RequestFactory.createAddToModelRequest(b);
		m.handleRequest(r);
		
		m.update();
	}
	
	// Tests that the game ends when endGame() is called
	// Also tests that the game is restarted when reset() is called with the right Player
	@Test
	public void gameEndTest1() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		assertTrue(m.getPlayer() instanceof Crab);
		assertFalse(m.gameOver);
		m.endGame();
		assertTrue(m.gameOver);
		m.reset(EntityType.TURTLE, RequestFactory.createStartGameRequest(EntityType.TURTLE).getRequestedAction());
		assertFalse(m.gameOver);
		assertTrue(m.getPlayer() instanceof Turtle);
	}
	
	// Tests that the game ends when the pollution level reaches 100 in update()
	@Test
	public void gameEndTest2() {
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		rq.fulfillAllRequests();
		assertEquals(0, m.getCurrentPollutionLevel());
		assertFalse(m.gameOver);
		Request r = RequestFactory.createUpdatePollutionRequest(100);
		m.handleRequest(r);
		assertEquals(100, m.getCurrentPollutionLevel());
		m.update();
		assertTrue(m.gameOver);
		m.reset(EntityType.CRAB, RequestFactory.createStartGameRequest(EntityType.CRAB).getRequestedAction());
		assertFalse(m.gameOver);
		
	}
	

}
