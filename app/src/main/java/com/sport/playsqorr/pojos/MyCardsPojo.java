package com.sport.playsqorr.pojos;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kalyan on 22-07-2019.
 */

public class MyCardsPojo implements Serializable {

    private String cardId;
    private String cardTitle;
    private String matchupType;
    private String startTime;
    private String leagueId;
    private String leagueAbbrevation;
    private String playerImageLeft;
    private String playerImageRight;
    private String status;
    private String currencyTypeIsTokens;
    private String matchupsPlayed;
    private String matchupsWon;
    private String settlementDate;
    private String winAmount;
    private String isPurchased;

    private String leagueSubTitle;


    public String getLeagueSubTitle() {
        return leagueSubTitle;
    }

    public void setLeagueSubTitle(String leagueSubTitle) {
        this.leagueSubTitle = leagueSubTitle;
    }

    private List<String> playerCardIds = null;
    private List<playerCardsPojo> playerCardsPojo = null;


    private String cardType;
    private  boolean cout_data;

    public boolean isCout_data() {
        return cout_data;
    }

    public void setCout_data(boolean cout_data) {
        this.cout_data = cout_data;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public List<playerCardsPojo> getPlayerCardsPojo() {
        return playerCardsPojo;
    }

    public void setPlayerCardsPojo(List<playerCardsPojo> playerCardsPojo) {
        this.playerCardsPojo = playerCardsPojo;
    }

    public List<String> getPlayerCardIds() {
        return playerCardIds;
    }

    public void setPlayerCardIds(List<String> playerCardIds) {
        this.playerCardIds = playerCardIds;
    }

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }

    private String isLive;

    public String getIsPurchased() {
        return "false";
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = "false";
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

    public String getMatchupType() {
        return matchupType;
    }

    public void setMatchupType(String matchupType) {
        this.matchupType = matchupType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueAbbrevation() {
        return leagueAbbrevation;
    }

    public void setLeagueAbbrevation(String leagueAbbrevation) {
        this.leagueAbbrevation = leagueAbbrevation;
    }

    public String getPlayerImageLeft() {
        return playerImageLeft;
    }

    public void setPlayerImageLeft(String playerImageLeft) {
        this.playerImageLeft = playerImageLeft;
    }

    public String getPlayerImageRight() {
        return playerImageRight;
    }

    public void setPlayerImageRight(String playerImageRight) {
        this.playerImageRight = playerImageRight;
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

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }
}
