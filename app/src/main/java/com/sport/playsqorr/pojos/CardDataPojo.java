package com.sport.playsqorr.pojos;

public class CardDataPojo {


    private String _id;
    private String account;
    private String token;
    private String authorizeNetCustomerProfileId;
    private String authorizeNetCustomerPaymentProfileId;
    private String lastFourDigits;
    private String expiry;
    private String cardType;
    private String createdAt;
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthorizeNetCustomerProfileId() {
        return authorizeNetCustomerProfileId;
    }

    public void setAuthorizeNetCustomerProfileId(String authorizeNetCustomerProfileId) {
        this.authorizeNetCustomerProfileId = authorizeNetCustomerProfileId;
    }

    public String getAuthorizeNetCustomerPaymentProfileId() {
        return authorizeNetCustomerPaymentProfileId;
    }

    public void setAuthorizeNetCustomerPaymentProfileId(String authorizeNetCustomerPaymentProfileId) {
        this.authorizeNetCustomerPaymentProfileId = authorizeNetCustomerPaymentProfileId;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
