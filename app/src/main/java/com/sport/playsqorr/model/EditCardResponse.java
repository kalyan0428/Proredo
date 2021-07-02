package com.sport.playsqorr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditCardResponse {

    @SerializedName("accountId")
    @Expose
    private Object accountId;
    @SerializedName("cardNumber")
    @Expose
    private String cardNumber;
    @SerializedName("expiryMonth")
    @Expose
    private String expiryMonth;
    @SerializedName("expiryYear")
    @Expose
    private String expiryYear;
    @SerializedName("securityCode")
    @Expose
    private Object securityCode;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zipCode")
    @Expose
    private String zipCode;
    @SerializedName("apiLoginID")
    @Expose
    private Object apiLoginID;
    @SerializedName("apiTransactionKey")
    @Expose
    private Object apiTransactionKey;
    @SerializedName("customerProfileId")
    @Expose
    private Object customerProfileId;
    @SerializedName("customerPaymentProfileId")
    @Expose
    private Object customerPaymentProfileId;
    @SerializedName("amount")
    @Expose
    private Double amount;

    public Object getAccountId() {
        return accountId;
    }

    public void setAccountId(Object accountId) {
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Object getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(Object securityCode) {
        this.securityCode = securityCode;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Object getApiLoginID() {
        return apiLoginID;
    }

    public void setApiLoginID(Object apiLoginID) {
        this.apiLoginID = apiLoginID;
    }

    public Object getApiTransactionKey() {
        return apiTransactionKey;
    }

    public void setApiTransactionKey(Object apiTransactionKey) {
        this.apiTransactionKey = apiTransactionKey;
    }

    public Object getCustomerProfileId() {
        return customerProfileId;
    }

    public void setCustomerProfileId(Object customerProfileId) {
        this.customerProfileId = customerProfileId;
    }

    public Object getCustomerPaymentProfileId() {
        return customerPaymentProfileId;
    }

    public void setCustomerPaymentProfileId(Object customerPaymentProfileId) {
        this.customerPaymentProfileId = customerPaymentProfileId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
