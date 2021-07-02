package com.sport.playsqorr.pojos;

public class ACHCardDataPojo {


    private String _id;
    private String accountId;
    private String nameOnAccount;
    private String accountType;
    private String accountNumber;
    private String routingNumber;
    private String createdDate;
//
//
//     "_id": "5e20a4199cbc212c280c3928",
//             "accountId": "5daf745d4793fa42581cd467",
//             "nameOnAccount": "Yashwanth Reddy",
//             "accountType": "Savings",
//             "accountNumber": "12345677",
//             "routingNumber": "1234567",
//             "createdDate": "2020-01-16T17:57:45.582Z"


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNameOnAccount() {
        return nameOnAccount;
    }

    public void setNameOnAccount(String nameOnAccount) {
        this.nameOnAccount = nameOnAccount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
