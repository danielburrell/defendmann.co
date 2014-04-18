package models;

import java.math.BigDecimal;

public class Player {

  public String steamId;
  public Integer mapId;
  public Integer tour;
  public Integer xp;
  public Integer favoriteClass;
  public Integer altClass;
  public Long idealTeam;
  public BigDecimal reputation;
  public Boolean active;

  public String getSteamId() {
    return steamId;
  }

  public void setSteamId(String steamId) {
    this.steamId = steamId;
  }

  public Integer getMapId() {
    return mapId;
  }

  public void setMapId(Integer mapId) {
    this.mapId = mapId;
  }

  public Integer getTour() {
    return tour;
  }

  public void setTour(Integer tour) {
    this.tour = tour;
  }

  public Integer getXp() {
    return xp;
  }

  public void setXp(Integer xp) {
    this.xp = xp;
  }

  public Integer getFavoriteClass() {
    return favoriteClass;
  }

  public void setFavoriteClass(Integer favoriteClass) {
    this.favoriteClass = favoriteClass;
  }

  public Integer getAltClass() {
    return altClass;
  }

  public void setAltClass(Integer altClass) {
    this.altClass = altClass;
  }

  public Long getIdealTeam() {
    return idealTeam;
  }

  public void setIdealTeam(Long idealTeam) {
    this.idealTeam = idealTeam;
  }

  public BigDecimal getReputation() {
    return reputation;
  }

  public void setReputation(BigDecimal reputation) {
    this.reputation = reputation;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

}
