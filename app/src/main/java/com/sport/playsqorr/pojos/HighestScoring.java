package com.sport.playsqorr.pojos;

public class HighestScoring {
//     "rank": 1,
//             "accountId": "5c9a8715ce6c5ffa767c01d2",
//             "accountName": "William Marudas",
//             "value": 290

    String rank;
    String accountId;
    String accountName;
    String image;
    String value;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}