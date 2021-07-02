package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sport.playsqorr.model.Picks;

import java.util.List;

public class MatchupModel {
    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("cardTitle")
    @Expose
    private String cardTitle;
    @SerializedName("isPurchased")
    @Expose
    private Boolean isPurchased;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("currencyTypeIsTokens")
    @Expose
    private Boolean currencyTypeIsTokens;
    @SerializedName("matchupsPlayed")
    @Expose
    private int matchupsPlayed;
    @SerializedName("matchupsWon")
    @Expose
    private int matchupsWon;
    @SerializedName("settlementDate")
    @Expose
    private String settlementDate;
    @SerializedName("payout")
    @Expose
    private int payout;
    @SerializedName("matchups")
    @Expose
    private List<Matchup> matchups = null;

    @SerializedName("payStructure")
    @Expose
    private List<Picks> payStructure = null;

    @SerializedName("totalPurchasedAmount")
    @Expose
    private String totalPurchasedAmount;

    @SerializedName("purchasedAmount")
    @Expose
    private String purchasedAmount;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("purchasedDate")
    @Expose
    private String purchasedDate;


    @SerializedName("supportsMoney")
    @Expose
    private Boolean supportsMoney;


    @SerializedName("supportsTokens")
    @Expose
    private Boolean supportsTokens;


    @SerializedName("cardType")
    @Expose
    private String cardType;

    @SerializedName("league")
    @Expose
    private String league;


    @SerializedName("payType")
    @Expose
    private String payType;

    @SerializedName("maxPurchaseAmount")
    @Expose
    private String maxPurchaseAmount;

    @SerializedName("minPurchaseAmount")
    @Expose
    private String minPurchaseAmount;


//    @SerializedName("winInfo")
//    @Expose
//    private List<WinInfo> winInfo;
    @SerializedName("applyGameEndingPattern")
    @Expose
    private boolean applyGameEndingPattern;






    public boolean isApplyGameEndingPattern() {
        return applyGameEndingPattern;
    }

    public void setApplyGameEndingPattern(boolean applyGameEndingPattern) {
        this.applyGameEndingPattern = applyGameEndingPattern;
    }

    public String getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(String minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public String getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getPurchasedAmount() {
        return purchasedAmount;
    }

    public void setPurchasedAmount(String purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }

    public Boolean getPurchased() {
        return isPurchased;
    }

    public void setPurchased(Boolean purchased) {
        isPurchased = purchased;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getMaxPurchaseAmount() {
        return maxPurchaseAmount;
    }

//    public void setWinInfo(List<WinInfo> winInfo) {
//        this.winInfo = winInfo;
//    }

    public void setMaxPurchaseAmount(String maxPurchaseAmount) {
        this.maxPurchaseAmount = maxPurchaseAmount;
    }

//    public String getWinInfo() {
//        return winInfo;
//    }
//
//    public void setWinInfo(String winInfo) {
//        this.winInfo = winInfo;
//    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Boolean getSupportsMoney() {
        return supportsMoney;
    }

    public void setSupportsMoney(Boolean supportsMoney) {
        this.supportsMoney = supportsMoney;
    }

    public Boolean getSupportsTokens() {
        return supportsTokens;
    }

    public void setSupportsTokens(Boolean supportsTokens) {
        this.supportsTokens = supportsTokens;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Picks> getPayStructure() {
        return payStructure;
    }

    public void setPayStructure(List<Picks> payStructure) {
        this.payStructure = payStructure;
    }

    public String getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(String totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public String getCardId() {
        return cardId;
    }


    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Boolean getCurrencyTypeIsTokens() {
        return currencyTypeIsTokens;
    }

    public void setCurrencyTypeIsTokens(Boolean currencyTypeIsTokens) {
        this.currencyTypeIsTokens = currencyTypeIsTokens;
    }

    public int getMatchupsPlayed() {
        return matchupsPlayed;
    }

    public void setMatchupsPlayed(int matchupsPlayed) {
        this.matchupsPlayed = matchupsPlayed;
    }

    public int getMatchupsWon() {
        return matchupsWon;
    }

    public void setMatchupsWon(int matchupsWon) {
        this.matchupsWon = matchupsWon;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public List<Matchup> getMatchups() {
        return matchups;
    }

    public void setMatchups(List<Matchup> matchups) {
        this.matchups = matchups;
    }


}

