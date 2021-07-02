package com.sport.playsqorr.model;

import com.sport.playsqorr.pojos.BiggestWinners;
import com.sport.playsqorr.pojos.HighestScoring;

import java.util.List;

public class Leaderboard {

    String _id;
    String company;
    String lastRefreshTime;

    private List<BiggestWinners> biggestWinners = null;
    private List<HighestScoring> highestScoring = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(String lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public List<BiggestWinners> getBiggestWinners() {
        return biggestWinners;
    }

    public void setBiggestWinners(List<BiggestWinners> biggestWinners) {
        this.biggestWinners = biggestWinners;
    }

    public List<HighestScoring> getHighestScoring() {
        return highestScoring;
    }

    public void setHighestScoring(List<HighestScoring> highestScoring) {
        this.highestScoring = highestScoring;
    }
}
