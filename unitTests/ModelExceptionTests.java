import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import controller.Controller;
import controller.requests.Request.RequestType;
import controller.requests.RequestQueue;
import model.Model;
import model.entities.TrashFactory;
import model.entities.Entity.EntityType;

public class ModelExceptionTests {
	static RequestQueue rq = new RequestQueue();
	static Model m = new Model(rq);
	
	
	@Test
	public void playerTypeExceptionTest() {
		assertThrows(ValueException.class,
				()->{
					m.reset(EntityType.STYROFOAM_CUP, RequestType.START_GAME);
				});
	}
	
	@Test
	public void resetModeExceptionTest() {
		assertThrows(ValueException.class,
				()->{
					m.reset(EntityType.CRAB, RequestType.PLAY_SOUND);
				});
	}
	
	
	
	
}
