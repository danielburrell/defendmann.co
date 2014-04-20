package controllers;

import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class TestLastSeen {
	@Test
	public void shouldCacheLastSeenValue(){
	    Result result = callAction(controllers.routes.ref.LastSeen.updateLastSeen(),fakeRequest().withSession("steamId", "1234"));   
	    assertThat(status(result)).isEqualTo(OK);
	    assertThat(contentAsString(result)).contains("Updating last seen");
	}
	
}
