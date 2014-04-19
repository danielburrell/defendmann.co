package globals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.Comparator;

import models.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.TourDao;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.db.DB;
import play.libs.Akka;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Result;
import scala.concurrent.duration.Duration;

public class Global extends GlobalSettings {

  private ApplicationContext applicationContext;

  @Override
  public void onStart(Application arg0) {
    String configLocation = Play.application().configuration().getString("spring.context.location");
    applicationContext = new ClassPathXmlApplicationContext(configLocation);

    Logger.info("Scheduling Cache Refresher");
    Akka.system().scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS), Duration.create(10, TimeUnit.SECONDS),

    		
    new Runnable() {
    	@Autowired @Qualifier("tourDao")
    	private TourDao tourDao;
      public void run() {

    	  if (tourDao == null) {
        Logger.info("Tour dao is null");
    	  } else {
    		  Logger.info("Tourdao is not null");
    	  }
    	  
        List<Player> result = tourDao.getToursForActivePlayersOnAllMaps();
        /*
         * jdbcTemplate.query( "select alt_class, favorite_class, ideal_team, map_id, reputation, steam_id, tour, xp from ranks, orderby map_id, tour", new
         * RowMapper<Player>() {
         * 
         * @Override public Player mapRow(ResultSet rs, int row) throws SQLException { Player item = new Player(); item.setAltClass(rs.getInt("alt_class"));
         * item.setFavoriteClass(rs.getInt("favorite_class")); item.setIdealTeam(rs.getLong("ideal_team")); item.setMapId(rs.getInt("map_id"));
         * item.setReputation(rs.getBigDecimal("reputation")); item.setSteamId(rs.getString("steam_id")); item.setTour(rs.getInt("tour"));
         * item.setXp(rs.getInt("xp")); return item; } });
         */

        result.stream().map(f -> f.getMapId()).distinct().forEach((mapId) -> IntStream.range(0, 201).forEach((tour) -> populateCache(result, mapId, tour)));
        Logger.info("cache population complete");

      }

      private void populateCache(List<Player> result, Integer mapId, int tour) {
        Cache.set(
            mapId + "+" + tour,
            result.stream().filter(x -> x.getMapId() == mapId && x.getTour() <= tour).sorted(Comparator.comparing(Player::getTour))
                .collect(Collectors.toList()));
      }

	public TourDao getTourDao() {
		return tourDao;
	}

	public void setTourDao(TourDao tourDao) {
		this.tourDao = tourDao;
	}

      
    },

    Akka.system().dispatcher()

    );

  }

  @Override
  public <A> A getControllerInstance(Class<A> type) throws Exception {
	  Logger.info("get {}", type);
    return applicationContext.getBean(type);
  }

}