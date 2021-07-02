package com.sport.playsqorr.pojos;

/**
 * Created by Kalyan on 22-07-2019.
 */

public class ResponsePojo {

//    {"account":{"_id":"5d3a9d91b8e89d3eb8e5c347","email":"test1234@gmail.com","fullName":"test","phoneNumber":"",
//            "dob":"1978-07-26T00:00:00Z","city":null,"state":null,"country":null,"pinRequired":false,"pinCode":"",
//            "facebook":null,"google":null,"settings":{"evalEnabled":false},"eulaAccepted":false,"userPlayMode":"cash",
//            "totalCashBalance":5,"cashBalance":0,
//            "promoBalance":5,"tokenBalance":20,"avatar":null,"totalWins":0},"sessionToken":"5d3a9d91b8e89d3eb8e5c34c"}

    private String _id;
    private String email;
    private String fullName;
    private String city;
    private String state;
    private String country;
    private String userPlayMode;
    private String sessionToken;
    private String token;
    private String totalCashBalance;
    private String tokenBalance;
    private String avatar;
    private String totalWins;
    private String referralCode;
    private String gender;
    private String sportsPreference;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSportsPreference() {
        return sportsPreference;
    }

    public void setSportsPreference(String sportsPreference) {
        this.sportsPreference = sportsPreference;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(String totalWins) {
        this.totalWins = totalWins;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTotalCashBalance() {
        return totalCashBalance;
    }

    public void setTotalCashBalance(String totalCashBalance) {
        this.totalCashBalance = totalCashBalance;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserPlayMode() {
        return userPlayMode;
    }

    public void setUserPlayMode(String userPlayMode) {
        this.userPlayMode = userPlayMode;
    }

    public String getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(String tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
