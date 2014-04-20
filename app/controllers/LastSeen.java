package controllers;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;

@Component
public class LastSeen extends Controller {

  @Inject
  private ConcurrentHashMap<String, DateTime> lastSeenThrottle;

  /**
   * when a request is made to update last seen of a player, put to in memory hashmap keyed by player in memory hashmap is cycled by a thread which puts to a
   * database periodically.
   * 
   * @return
   */
  public Result updateLastSeen() {
    // ensure a valid level and mapid are provided
    String steamId = session("steamId");
    try {
      Validate.notEmpty(steamId, "No SteamID provided when updating last seen");
    } catch (NullPointerException | IllegalArgumentException e) {
      if (steamId == null) {
        return badRequest("Missing steamId");
      }
    }

    lastSeenThrottle.put(steamId, DateTime.now());

    return ok("Updating last seen");
  }

public ConcurrentHashMap<String, DateTime> getLastSeenThrottle() {
	return lastSeenThrottle;
}

public void setLastSeenThrottle(
		ConcurrentHashMap<String, DateTime> lastSeenThrottle) {
	this.lastSeenThrottle = lastSeenThrottle;
}
}
