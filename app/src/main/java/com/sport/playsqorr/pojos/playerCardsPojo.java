package com.sport.playsqorr.pojos;

import java.io.Serializable;

public class playerCardsPojo implements Serializable {
    private String playerCardId;
    private String status;
    private String currencyTypeIsTokens;
    private String matchupsPlayed;
    private String matchupsWon;
    private String purchasedTime;
    private String settlementDate;
    private String payout;


    public String getPlayerCardId() {
        return playerCardId;
    }

    public void setPlayerCardId(String playerCardId) {
        this.playerCardId = playerCardId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrencyTypeIsTokens() {
        return currencyTypeIsTokens;
    }

    public void setCurrencyTypeIsTokens(String currencyTypeIsTokens) {
        this.currencyTypeIsTokens = currencyTypeIsTokens;
    }

    public String getMatchupsPlayed() {
        return matchupsPlayed;
    }

    public void setMatchupsPlayed(String matchupsPlayed) {
        this.matchupsPlayed = matchupsPlayed;
    }

    public String getMatchupsWon() {
        return matchupsWon;
    }

    public void setMatchupsWon(String matchupsWon) {
        this.matchupsWon = matchupsWon;
    }

    public String getPurchasedTime() {
        return purchasedTime;
    }

    public void setPurchasedTime(String purchasedTime) {
        this.purchasedTime = purchasedTime;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }
}
