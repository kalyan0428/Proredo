package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MinutesPlayed {
    @SerializedName("hide")
    @Expose
    private Boolean hide;
    @SerializedName("multiplier")
    @Expose
    private Double multiplier;
    @SerializedName("fantasyPoints")
    @Expose
    private int fantasyPoints;
    @SerializedName("value")
    @Expose
    private int value;

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public int getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(int fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
