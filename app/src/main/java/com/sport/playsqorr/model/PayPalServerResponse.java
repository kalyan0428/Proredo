package com.sport.playsqorr.model;


/**
 * Created by Rajnai on 1/24/2017
 */

public class PayPalServerResponse {


    /*{
	"client": {

		"environment": "mock",
		"paypal_sdk_version": "2.15.1",
		"platform": "Android",
		"product_name": "PayPal-Android-SDK"
	},
	"response": {
		"create_time": "2014-07-18T18:46:55Z",
		"id": "PAY-18X32451H0459092JKO7KFUI",
		"intent": "sale",
		"state": "approved"
	},
	"response_type": "payment"
}*/

    private client client;

    public PayPalServerResponse.client getClient() {
        return client;
    }

    public PayPalServerResponse.response getResponse() {
        return response;
    }

    public String getResponse_type() {
        return response_type;
    }

    private response response;
    private String response_type;



   public static class client{
       /*"environment": "mock",
		"paypal_sdk_version": "2.15.1",
		"platform": "Android",
		"product_name": "PayPal-Android-SDK"*/
    private String environment;

       public String getEnvironment() {
           return environment;
       }

       public String getPaypal_sdk_version() {
           return paypal_sdk_version;
       }

       public String getPlatform() {
           return platform;
       }

       public String getProduct_name() {
           return product_name;
       }

       private String paypal_sdk_version;
    private String platform;
    private String product_name;
   }


   public static class response{
       /*"create_time": "2014-07-18T18:46:55Z",
		"id": "PAY-18X32451H0459092JKO7KFUI",
		"intent": "sale",
		"state": "approved"*/
       private String create_time;

       public String getCreate_time() {
           return create_time;
       }

       public String getId() {
           return id;
       }

       public String getState() {
           return state;
       }

       private String id;
       private String state;

   }


}
