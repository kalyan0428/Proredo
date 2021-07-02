package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sport.playsqorr.model.StatsPlayerStatistics;

import java.util.List;

public class Matchup {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("playerA")
    @Expose
    private PlayerA playerA;
    @SerializedName("playerB")
    @Expose
    private PlayerB playerB;

    @SerializedName("playerC")
    @Expose
    private PlayerC playerC;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @SerializedName("isUserPlayed")
    @Expose
    private Boolean isUserPlayed;
    @SerializedName("pickIndex")
    @Expose
    private int pickIndex;
    @SerializedName("winIndex")
    @Expose
    private int winIndex;
    @SerializedName("isFinished")
    @Expose
    private Boolean isFinished;
    @SerializedName("isCancelled")
    @Expose
    private Boolean isCancelled;

    @SerializedName("cancelledReason")
    @Expose
    private String cancelledReason;

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }

    @SerializedName("displayStats")
    @Expose
    private List<StatsPlayerStatistics> displayStats = null;
//
//    public List<StatsPlayerStatistics> getDisplayS() {
//        return displayStats;
//    }

    @SerializedName("featuredOrder")
    @Expose
    private int featuredOrder;

    @SerializedName("handicap")
    @Expose
    private double handicap;

    @SerializedName("handicapAvsB")
    @Expose
    private double handicapAvsB;

    @SerializedName("handicapAvsC")
    @Expose
    private double handicapAvsC;
    @SerializedName("handicapBvsC")
    @Expose
    private double handicapBvsC;


//
//    "handicap": 0,
//            "handicapAvsB": 0,
//            "handicapAvsC": 0,
//            "handicapBvsC": 0


    String Playerstatus;
    String Playerstatus_C;


    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    public double getHandicapAvsB() {
        return handicapAvsB;
    }

    public void setHandicapAvsB(double handicapAvsB) {
        this.handicapAvsB = handicapAvsB;
    }

    public double getHandicapAvsC() {
        return handicapAvsC;
    }

    public void setHandicapAvsC(double handicapAvsC) {
        this.handicapAvsC = handicapAvsC;
    }

    public double getHandicapBvsC() {
        return handicapBvsC;
    }

    public void setHandicapBvsC(double handicapBvsC) {
        this.handicapBvsC = handicapBvsC;
    }

    public String getPlayerstatus_C() {
        return Playerstatus_C;
    }

    public void setPlayerstatus_C(String playerstatus_C) {
        Playerstatus_C = playerstatus_C;
    }

    public PlayerC getPlayerC() {
        return playerC;
    }

    public void setPlayerC(PlayerC playerC) {
        this.playerC = playerC;
    }

    public int getFeaturedOrder() {
        return featuredOrder;
    }

    public void setFeaturedOrder(int featuredOrder) {
        this.featuredOrder = featuredOrder;
    }

    public Boolean getUserPlayed() {
        return isUserPlayed;
    }

    public void setUserPlayed(Boolean userPlayed) {
        isUserPlayed = userPlayed;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public List<StatsPlayerStatistics> getDisplayStats() {
        return displayStats;
    }

    public void setDisplayStats(List<StatsPlayerStatistics> displayStats) {
        this.displayStats = displayStats;
    }

    public PlayerA getPlayerA() {
        return playerA;
    }

    public void setPlayerA(PlayerA playerA) {
        this.playerA = playerA;
    }

    public PlayerB getPlayerB() {
        return playerB;
    }

    public void setPlayerB(PlayerB playerB) {
        this.playerB = playerB;
    }

    public String getPlayerstatus() {
        return Playerstatus;
    }

    public void setPlayerstatus(String playerstatus) {
        Playerstatus = playerstatus;
    }


    public Boolean getIsUserPlayed() {
        return isUserPlayed;
    }

    public void setIsUserPlayed(Boolean isUserPlayed) {
        this.isUserPlayed = isUserPlayed;
    }

    public int getPickIndex() {
        return pickIndex;
    }

    public void setPickIndex(int pickIndex) {
        this.pickIndex = pickIndex;
    }

    public int getWinIndex() {
        return winIndex;
    }

    public void setWinIndex(int winIndex) {
        this.winIndex = winIndex;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

}