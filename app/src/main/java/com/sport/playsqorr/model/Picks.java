package com.sport.playsqorr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picks {

    @SerializedName("picks")
    @Expose
    private String picks;

    @SerializedName("multiplier")
    @Expose
    private String multiplier;

    String winpayout;

    public String getPicks() {
        return picks;
    }

    public void setPicks(String picks) {
        this.picks = picks;
    }

    public String getWinpayout() {
        return winpayout;
    }

    public void setWinpayout(String winpayout) {
        this.winpayout = winpayout;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }
}
