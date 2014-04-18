package controllers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import models.Player;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.*;
import play.cache.Cache;
import play.db.DB;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

@Component
public class Application extends Controller {

  public static Result index() {
    return ok(views.html.index.render());
  }

  /**
   * Return 'the list' for a particular map (given a certain level).
   * 
   * @param mapId
   * @return
   */
  public static Result getList(Integer mapId) {

    // ensure a valid level and mapid are provided
    String level = session("level");
    try {
      Validate.notEmpty(level, "Level missing");
      Validate.notNull(mapId, "MapId missing");
    } catch (NullPointerException | IllegalArgumentException e) {
      if (mapId == null) {
        return badRequest("Missing map");
      } else {
        return badRequest("No level provided. Is user logged in?");
      }
    }

    final String key = mapId + "+" + level;

    List<Player> result = (List<Player>) Cache.get(key);
    
    if (result != null) {
      return ok(Json.toJson(result));
    } else {
      return internalServerError("Data unavailable. Try again shortly");
    }

  }

 
  
}
