package com.sport.playsqorr.pojos;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Selection {
    @SerializedName("matchup")
    @Expose
    @Keep
    private String matchup;
    @SerializedName("pickIndex")
    @Expose
    @Keep
    private String pickIndex;

    @SerializedName("selectedOrder")
    @Expose
    @Keep
    private String selectedOrder;


    @SerializedName("winOrder")
    @Expose
    @Keep
    private String winOrder;


    public String getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(String selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public String getWinOrder() {
        return winOrder;
    }

    public void setWinOrder(String winOrder) {
        this.winOrder = winOrder;
    }

    public String getMatchup() {
        return matchup;
    }

    public void setMatchup(String matchup) {
        this.matchup = matchup;
    }

    public String getPickIndex() {
        return pickIndex;
    }

    public void setPickIndex(String pickIndex) {
        this.pickIndex = pickIndex;
    }
}
