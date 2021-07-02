package com.sport.playsqorr.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatsPlayerStatistics {

    @SerializedName("displayText")
    @Expose
    private String displayText;

    @SerializedName("leftPlayerSqorr")
    @Expose
    private String leftPlayerSqorr;

//    String leftPlayerSqorr;

    @SerializedName("rightPlayerSqorr")
    @Expose
    private String rightPlayerSqorr;
//    String rightPlayerSqorr;


    @SerializedName("playerA")
    @Expose
    private String playerA;
    @SerializedName("playerB")
    @Expose
    private String playerB;
    @SerializedName("playerC")
    @Expose
    private String playerC;

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public void setPlayerB(String playerB) {
        this.playerB = playerB;
    }

    public String getPlayerC() {
        return playerC;
    }

    public void setPlayerC(String playerC) {
        this.playerC = playerC;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getLeftPlayerSqorr() {
        return leftPlayerSqorr;
    }

    public void setLeftPlayerSqorr(String leftPlayerSqorr) {
        this.leftPlayerSqorr = leftPlayerSqorr;
    }

    public String getRightPlayerSqorr() {
        return rightPlayerSqorr;
    }

    public void setRightPlayerSqorr(String rightPlayerSqorr) {
        this.rightPlayerSqorr = rightPlayerSqorr;
    }
}
