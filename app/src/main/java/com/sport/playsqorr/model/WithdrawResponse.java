package com.sport.playsqorr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawResponse {

    @SerializedName("userPlayMode")
    @Expose
    private String userPlayMode;
    @SerializedName("totalCashBalance")
    @Expose
    private double totalCashBalance;
    @SerializedName("cashBalance")
    @Expose
    private double cashBalance;
    @SerializedName("promoBalance")
    @Expose
    private double promoBalance;
    @SerializedName("tokenBalance")
    @Expose
    private double tokenBalance;

    @SerializedName("isFirst")
    @Expose
    private boolean isFirst;

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getUserPlayMode() {
        return userPlayMode;
    }

    public void setUserPlayMode(String userPlayMode) {
        this.userPlayMode = userPlayMode;
    }

    public double getTotalCashBalance() {
        return totalCashBalance;
    }

    public void setTotalCashBalance(double totalCashBalance) {
        this.totalCashBalance = totalCashBalance;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
    }

    public double getPromoBalance() {
        return promoBalance;
    }

    public void setPromoBalance(double promoBalance) {
        this.promoBalance = promoBalance;
    }

    public double getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(double tokenBalance) {
        this.tokenBalance = tokenBalance;
    }
}
