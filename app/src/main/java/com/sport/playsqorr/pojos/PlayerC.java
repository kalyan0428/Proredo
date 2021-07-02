package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerC {
    @SerializedName("playerId")
    @Expose
    private String playerId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("positonName")
    @Expose
    private String positonName;
    @SerializedName("positionAbbreviation")
    @Expose
    private String positionAbbreviation;
    @SerializedName("dataProviderId")
    @Expose
    private String dataProviderId;
    @SerializedName("avgStats")
    @Expose
    private AvgStats avgStats;
    @SerializedName("teamId")
    @Expose
    private String teamId;
    @SerializedName("teamAbbreviation")
    @Expose
    private String teamAbbreviation;
    @SerializedName("uniformNumber")
    @Expose
    private String uniformNumber;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("playerStats")
    @Expose
    private PlayerStats playerStats;
    @SerializedName("playerImage")
    @Expose
    private String playerImage;
    @SerializedName("gameDate")
    @Expose
    private String gameDate;
    @SerializedName("pointSpread")
    @Expose
    private Double pointSpread;

    @SerializedName("isLive")
    @Expose
    private String isLive;

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }



    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPositonName() {
        return positonName;
    }

    public void setPositonName(String positonName) {
        this.positonName = positonName;
    }

    public String getPositionAbbreviation() {
        return positionAbbreviation;
    }

    public void setPositionAbbreviation(String positionAbbreviation) {
        this.positionAbbreviation = positionAbbreviation;
    }

    public String getDataProviderId() {
        return dataProviderId;
    }

    public void setDataProviderId(String dataProviderId) {
        this.dataProviderId = dataProviderId;
    }

    public AvgStats getAvgStats() {
        return avgStats;
    }

    public void setAvgStats(AvgStats avgStats) {
        this.avgStats = avgStats;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamAbbreviation() {
        return teamAbbreviation;
    }

    public void setTeamAbbreviation(String teamAbbreviation) {
        this.teamAbbreviation = teamAbbreviation;
    }

    public String getUniformNumber() {
        return uniformNumber;
    }

    public void setUniformNumber(String uniformNumber) {
        this.uniformNumber = uniformNumber;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public Double getPointSpread() {
        return pointSpread;
    }

    public void setPointSpread(Double pointSpread) {
        this.pointSpread = pointSpread;
    }

}
