package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Goals {
    @SerializedName("hide")
    @Expose
    private Boolean hide;
    @SerializedName("multiplier")
    @Expose
    private int multiplier;
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

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
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

