package com.sport.playsqorr.utilities;


public class APIs {

    //    static final String BASE_URL = "http://40.70.208.170:8080/api/"; // IP -node
//   public static final String BASE_URL = "https://staging-backoffice.azurewebsites.net/api/"; //  IP - .net - DEV
//   public static final String BASE_URL = "https://api.playsqorr.com/stage/api/"; // now  IP - .net - DEV
    public static final String BASE_URL = "https://api-playsqorr.azurewebsites.net/sqlstage/api/"; //  IP - sql - DEV
//    public static final String BASE_URL = "https://api-playsqorr.azurewebsites.net/stage/api/"; //  IP - .net - DEV
    //    public static final String BASE_URL = "https://api.playsqorr.com/api/";// --  PROD //"https://staging-backoffice.azurewebsites.net/api/"; //  IP - .net
//    public static final String BASE_URL = "https://api-playsqorr-prod.azurewebsites.net/api/";// --  PROD //"https://staging-backoffice.azurewebsites.net/api/"; //  IP - .net
//    https://api.playsqorr.com/api/
//    https://api.playsqorr.com/stage/api/
    public static final String LOGIN = BASE_URL + "login";
    public static final String SIGN_UP_URL = BASE_URL + "account/create";
    public static final String LEAGUE = BASE_URL + "league";
    public static final String GET_CARDS = BASE_URL + "cards";//"dummycards";
    public static final String CARD_DETAILS = BASE_URL + "card/";//"http://40.70.208.170:8080/api/card/5ce730856eb0612865b0f712"; /matchups
    //    public static final String MY_CARDS = BASE_URL + "mydummycards";
    public static final String MY_CARDS = BASE_URL + "mycards";
    public static final String ACCOUNT = BASE_URL + "auth/accounts/me";
    public static final String PROMOS = BASE_URL + "promos";
    public static final String WEB_LINKS = BASE_URL + "links";//https://staging-backoffice.azurewebsites.net/api/links
    //    public static final String SQORRTV_URL = BASE_URL + "sqorrtv";
    public static final String SQORRTV_URL = BASE_URL + "playsqorrtv";
    public static final String LOCATION_USER = BASE_URL + "enablelocation";
    public static final String LOCATION_USER_VAL = BASE_URL + "validatelocation";
    public static final String SIGNUP_LOCATION_USER = BASE_URL + "enablelocation";

    //Password recovery;
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "forgotPassword";

    //Change Password
    public static final String CHANGE_PWD_URL = BASE_URL + "account/updatepassword";

    //Update account data
    public static final String ACCOUNT_UPDATE_URL = BASE_URL + "account/update";

    public static final String SOCIAL_LOGIN_URL = BASE_URL + "sociallogin";

    public static final String ADD_CARD_URL = BASE_URL + "account/addcard";

    public static final String CARDS_LIST_URL = BASE_URL + "account/cards";

    public static final String EDIT_CARD_URL = BASE_URL + "account/editcard";

    public static final String DELETE_CARD_URL = BASE_URL + "account/deletecard";

    public static final String ADD_FUNDS_URL = BASE_URL + "account/addfunds";

    public static final String PURCHASE_CARD = BASE_URL + "account/purchasecard";

    public static final String TRANSACTIONS_URL = BASE_URL + "account/transactions";

    public static final String GET_CARD_DATA = BASE_URL + "account/card";

    public static final String ADD_FUNDS_URL_WITH_CARD = BASE_URL + "account/addfunds/newcard";

    public static final String WITHDRAW_FUNDS = BASE_URL + "account/withdrawfunds";
    public static final String WITHDRAW_FUNDS_paypal = BASE_URL + "account/withdrawviapaypal";
    public static final String IMAGE_UPLOAD = BASE_URL + "account/profileimage";
    public static final String ADD_FUNDS_PAYPAL = BASE_URL + "account/addpaypalfunds";
    public static final String CHECKPROMO = BASE_URL + "validatepromocode";
    public static final String MYINFO = BASE_URL + "myinfo";
    public static final String GETLEADERBOARD = BASE_URL + "GetLeaderboardData";

    public static final String GETACHBANKCARDS = BASE_URL + "account/bankaccounts";
    public static final String ADDACHACCOUNTS = BASE_URL + "account/addACHAccount";
    public static final String DELETEACHACCOUNTS = BASE_URL + "account/deleteACHAccount";
    public static final String WITHDRAWVIAACH = BASE_URL + "account/withdrawviaACH";

    public static final String LB = BASE_URL + "lb";
    public static final String NFLLB = BASE_URL + "nfllb";
    public static final String TS = BASE_URL + "u/ts";

    public static final String VB = BASE_URL + "upload/driverlicense/back";
    public static final String VF = BASE_URL + "upload/driverlicense/front";
    public static final String VALIDDOC = BASE_URL + "validateveficationdocs";


    public static final String BINGO_VERIFY = BASE_URL + "isbingocodeexists";


    //Freshdesk APi

    //    public  static final String FRESHDESK_BASEURL ="https://newaccount1603908299081.freshdesk.com/";
    //    public  static final String FRESHDESK_AUTH ="Basic cHJha2FzaC50aG9yYW1AZ21haWwuY29tOnRva2VuQDEyMw==";
    public static final String FRESHDESK_BASEURL = "https://newaccount1613837110659.freshdesk.com/";
    public static final String FRESHDESK_AUTH = "Basic YXNob2tyQHZldG5vcy5jbzpKdW5lQDEyMw==";
    public static final String FRESHDESK_CREATE = FRESHDESK_BASEURL + "api/v2/tickets";
    public static final String FRESHDESK_GETALLTICKETS = FRESHDESK_BASEURL + "api/v2/tickets";//api/v2/tickets?email=prak7@myorigami.co&&include=description


}

