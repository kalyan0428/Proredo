package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvgStats {
    @SerializedName("played")
    @Expose
    private Boolean played;
    @SerializedName("fantasyPoints")
    @Expose
    private Double fantasyPoints;
    @SerializedName("stats")
    @Expose
    private Stats stats;

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