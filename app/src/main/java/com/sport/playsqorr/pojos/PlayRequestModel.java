package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayRequestModel {
    @SerializedName("houseCard")
    @Expose
    private String houseCard;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("selections")
    @Expose
    private List<Selection> selections = null;
    @SerializedName("currencyTypeIsTokens")
    @Expose
    private Boolean currencyTypeIsTokens;

    public String getHouseCard() {
        return houseCard;
    }

    public void setHouseCard(String houseCard) {
        this.houseCard = houseCard;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    public Boolean getCurrencyTypeIsTokens() {
        return currencyTypeIsTokens;
    }

    public void setCurrencyTypeIsTokens(Boolean currencyTypeIsTokens) {
        this.currencyTypeIsTokens = currencyTypeIsTokens;
    }

}
