package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerStats {
    @SerializedName("playerStatsId")
    @Expose
    private String playerStatsId;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("injured")
    @Expose
    private Boolean injured;
    @SerializedName("suspended")
    @Expose
    private Boolean suspended;
    @SerializedName("cancelled")
    @Expose
    private Boolean cancelled;
    @SerializedName("live")
    @Expose
    private Boolean live;
    @SerializedName("played")
    @Expose
    private Boolean played;
    @SerializedName("fantasyPoints")
    @Expose
    private Double fantasyPoints;
    @SerializedName("stats")
    @Expose
    private Stats stats;

    public String getPlayerStatsId() {
        return playerStatsId;
    }

    public void setPlayerStatsId(String playerStatsId) {
        this.playerStatsId = playerStatsId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getInjured() {
        return injured;
    }

    public void setInjured(Boolean injured) {
        this.injured = injured;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Boolean getPlayed() {
        return played;
    }

    public void setPlayed(Boolean played) {
        this.played = played;
    }

    public Double getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(Double fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

}