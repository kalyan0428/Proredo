package com.sport.playsqorr.model;

public class Player {
    int player_img;
    String player_name;
    String player_price;
    String player_state;
    String player_prev_match;
    String player_match_time;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Player() {
    }

    public int getPlayer_img() {
        return player_img;
    }

    public void setPlayer_img(int player_img) {
        this.player_img = player_img;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_price() {
        return player_price;
    }

    public void setPlayer_price(String player_price) {
        this.player_price = player_price;
    }

    public String getPlayer_state() {
        return player_state;
    }

    public void setPlayer_state(String player_state) {
        this.player_state = player_state;
    }

    public String getPlayer_prev_match() {
        return player_prev_match;
    }

    public void setPlayer_prev_match(String player_prev_match) {
        this.player_prev_match = player_prev_match;
    }

    public String getPlayer_match_time() {
        return player_match_time;
    }

    public void setPlayer_match_time(String player_match_time) {
        this.player_match_time = player_match_time;
    }

}
