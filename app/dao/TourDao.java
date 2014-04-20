package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import models.Player;

import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import play.Logger;

@Component
public class TourDao {

	private JdbcTemplate jdbcTemplate;// = new JdbcTemplate(DB.getDataSource());

	public List<Player> getToursForActivePlayersOnAllMaps() {
		Logger.debug("Getting tours for active players.");
		List<Player> result = new ArrayList<Player>(5 * 200);
		IntStream.range(0, 4).forEach(
				(map) -> IntStream.range(0, 10000).forEach(
						(steamId) -> addPlayerToList(steamId, map, result)));
		return result;
		/*
		 * jdbcTemplate .query(
		 * "select alt_class, favorite_class, ideal_team, map_id, reputation, steam_id, tour, xp from ranks, orderby map_id, tour"
		 * , new RowMapper<Player>() {
		 * 
		 * @Override public Player mapRow(ResultSet rs, int row) throws
		 * SQLException { Player item = new Player();
		 * item.setAltClass(rs.getInt("alt_class")); item.setFavoriteClass(rs
		 * .getInt("favorite_class"));
		 * item.setIdealTeam(rs.getLong("ideal_team"));
		 * item.setMapId(rs.getInt("map_id")); item.setReputation(rs
		 * .getBigDecimal("reputation"));
		 * item.setSteamId(rs.getString("steam_id"));
		 * item.setTour(rs.getInt("tour")); item.setXp(rs.getInt("xp")); return
		 * item; } });
		 */
	}
	
	private Player addPlayerToList(int tour, int mapId, List<Player> result) {
        Player p = new Player();
        p.setTour(new Random().nextInt(200));
        p.setSteamId(Integer.toString(tour));
        p.setMapId(mapId);
        result.add(p);
        return p;
      }

}
