package com.sport.playsqorr.pojos;

import java.io.Serializable;

public class TvPojo implements Serializable {

    /*  {
        "url": "https://www.youtube.com/watch?v=Wws-p1AQuIc",
        "title": "NBA All-Star Swap",
        "description": "The Rockets and Thunder made a big time move yesterday when they agreed on a trade involving Russell Westbrook and Chris Paul.",
        "leagueId": "547e6e1e57489582581c7d8b",
        "durationInSeconds": 62,
        "isFeatured": false
    },*/


    private String url;
    private String title;
    private String description;
    private String leagueId;
    private String durationInSeconds;
    private boolean isFeatured;
    private String thumbnail;
    private String leagueAbbreviation;
    private String updatedAt;


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLeagueAbbreviation() {
        return leagueAbbreviation;
    }

    public void setLeagueAbbreviation(String leagueAbbreviation) {
        this.leagueAbbreviation = leagueAbbreviation;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
}
