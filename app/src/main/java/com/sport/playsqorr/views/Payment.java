package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sport.playsqorr.R;

import net.authorize.acceptsdk.AcceptSDKApiClient;
import net.authorize.acceptsdk.datamodel.common.Message;
import net.authorize.acceptsdk.datamodel.merchant.AbstractMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.merchant.ClientKeyBasedMerchantAuthentication;
import net.authorize.acceptsdk.datamodel.transaction.CardData;
import net.authorize.acceptsdk.datamodel.transaction.EncryptTransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionObject;
import net.authorize.acceptsdk.datamodel.transaction.TransactionType;
import net.authorize.acceptsdk.datamodel.transaction.callbacks.EncryptTransactionCallback;
import net.authorize.acceptsdk.datamodel.transaction.response.EncryptTransactionResponse;
import net.authorize.acceptsdk.datamodel.transaction.response.ErrorTransactionResponse;

import static net.authorize.acceptsdk.parser.JSONConstants.Card.CARD_HOLDER_NAME;

public class Payment extends AppCompatActivity  implements EncryptTransactionCallback {

    public static final String TAG = "WebCheckoutFragment";
    private final String CARD_NUMBER = "4111111111111111";
    private final String EXPIRATION_MONTH = "11";
    private final String EXPIRATION_YEAR = "2020";
    private final String CVV = "256";
    private final String POSTAL_CODE = "98001";


    private final String CLIENT_KEY =
            "7GEZp5RVZKfjUuU73K43L2bcGT4488e2eU4EsSzxXmyrbw8a6R8AmGZf5fn8vU6M";
    // replace with your CLIENT KEY
    private final String API_LOGIN_ID = "543SZC2zWcet"; // replace with your API LOGIN_ID


    private final int MIN_CARD_NUMBER_LENGTH = 13;
    private final int MIN_YEAR_LENGTH = 2;
    private final int MIN_CVV_LENGTH = 3;
    private final String YEAR_PREFIX = "20";

    private final String TRANS_KEY="77jNJb4cGpd332H9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        AcceptSDKApiClient   apiClient = new AcceptSDKApiClient.Builder (Payment.this,
                AcceptSDKApiClient.Environment.SANDBOX)
                .connectionTimeout(5000) // optional connection time out in milliseconds
                .build();

        EncryptTransactionObject transactionObject = TransactionObject.
                createTransactionObject(TransactionType.SDK_TRANSACTION_ENCRYPTION)// type of transaction object
                .cardData(prepareCardDataFromFields()) // card data to be encrypted
                .merchantAuthentication(prepareMerchantAuthentication()) //Merchant authentication
                .build();


        apiClient.getTokenWithRequest(transactionObject, this);

    }

    private AbstractMerchantAuthentication prepareMerchantAuthentication() {

        ClientKeyBasedMerchantAuthentication merchantAuthentication = ClientKeyBasedMerchantAuthentication.
                createMerchantAuthentication(API_LOGIN_ID, CLIENT_KEY);
        return  merchantAuthentication;
    }


    @Override
    public void onErrorReceived(ErrorTransactionResponse errorResponse) {
        Message error = errorResponse.getFirstErrorMessage();
        Log.e("PAYERROR",error.getMessageCode() + " : " + error.getMessageText());
        Toast.makeText(Payment.this,
                error.getMessageCode() + " : " + error.getMessageText() ,
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onEncryptionFinished(EncryptTransactionResponse response)
    {
        Toast.makeText(Payment.this,
                response.getDataDescriptor() + " : " + response.getDataValue(),
                Toast.LENGTH_LONG)
                .show();
    }

    private CardData prepareCardDataFromFields() {

        CardData cardData = new CardData.Builder(CARD_NUMBER,
                EXPIRATION_MONTH, // MM
                EXPIRATION_YEAR) // YYYY
                .cvvCode(CVV) // Optional
                .zipCode(POSTAL_CODE)// Optional
                .cardHolderName(CARD_HOLDER_NAME)// Optional
                .build();

        return  cardData;
    }


}
