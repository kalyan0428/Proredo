package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assists {

    @SerializedName("hide")
    @Expose
    private Boolean hide;
    @SerializedName("multiplier")
    @Expose
    private double multiplier;
    @SerializedName("fantasyPoints")
    @Expose
    private Double fantasyPoints;
    @SerializedName("value")
    @Expose
    private Double value;

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(Double fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
