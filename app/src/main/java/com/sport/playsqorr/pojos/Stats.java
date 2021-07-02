package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {
    @SerializedName("Assists")
    @Expose
    private Assists assists;
    @SerializedName("Goals")
    @Expose
    private Goals goals;
    @SerializedName("Yellow Cards")
    @Expose
    private YellowCards yellowCards;
    @SerializedName("Games Started")
    @Expose
    private GamesStarted gamesStarted;
    @SerializedName("gamesPlayed")
    @Expose
    private GamesPlayed gamesPlayed;
    @SerializedName("Red Cards")
    @Expose
    private RedCards redCards;

    public Assists getAssists() {
        return assists;
    }

    public void setAssists(Assists assists) {
        this.assists = assists;
    }

    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public YellowCards getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(YellowCards yellowCards) {
        this.yellowCards = yellowCards;
    }

    public GamesStarted getGamesStarted() {
        return gamesStarted;
    }

    public void setGamesStarted(GamesStarted gamesStarted) {
        this.gamesStarted = gamesStarted;
    }

    public GamesPlayed getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(GamesPlayed gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public RedCards getRedCards() {
        return redCards;
    }

    public void setRedCards(RedCards redCards) {
        this.redCards = redCards;
    }

}
