package com.sport.playsqorr.model;

import java.util.ArrayList;
import java.util.List;

public class NewPlayerStatistics {
    List<Double> first_player_points = new ArrayList<>();
    List<String> second_player_points = new ArrayList<>();
    List<String> player_positions = new ArrayList<>();

    public List<Double> getFirst_player_points() {
        return first_player_points;
    }

    public void setFirst_player_points(List<Double> first_player_points) {
        this.first_player_points = first_player_points;
    }

    public List<String> getSecond_player_points() {
        return second_player_points;
    }

    public void setSecond_player_points(List<String> second_player_points) {
        this.second_player_points = second_player_points;
    }

    public List<String> getPlayer_positions() {
        return player_positions;
    }

    public void setPlayer_positions(List<String> player_positions) {
        this.player_positions = player_positions;
    }
}
