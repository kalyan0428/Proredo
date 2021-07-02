package com.sport.playsqorr.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedMatchModel {
    @SerializedName("matchup_id")
    @Expose
    String matchup_id;
    @SerializedName("pickIndex")
    @Expose
    int pickIndex;

    public String getMatchup_id() {
        return matchup_id;
    }

    public void setMatchup_id(String matchup_id) {
        this.matchup_id = matchup_id;
    }

    public int getPickIndex() {
        return pickIndex;
    }

    public void setPickIndex(int pickIndex) {
        this.pickIndex = pickIndex;
    }
}
