package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
/*
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.PayPalAccountNonce;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;
*/
import com.google.gson.Gson;
import com.kevalpatel2106.fingerprintdialog.AuthenticationCallback;
import com.kevalpatel2106.fingerprintdialog.FingerprintDialogBuilder;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.PayPalServerResponse;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.pojos.CardDataPojo;
import com.sport.playsqorr.ui.AppConstants;
import com.sport.playsqorr.utilities.Utilities;
//import me.aflak.libraries.callback.FingerprintDialogCallback;
//import me.aflak.libraries.dialog.DialogAnimation;
//import me.aflak.libraries.dialog.FingerprintDialog;
import com.sport.playsqorr.utilities.APIs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;

public class AddFunds extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, TextWatcher {

    private RelativeLayout rl_add_new_card;
    private EditText etCustomAmt;
    private View amountView;
    private RecyclerView rvCardsList;
    private CardsListAdapter recycleAdapter;
    private List<String> cardDetails = new ArrayList<>();
    private TextView tvAddFunds, tvFaq;
    private LinearLayout llAddFunds, llPaypal;
    private String selCardId = "", fromPage;
    private List<CardDataPojo> cardsResponse = new ArrayList<>();
    private int customAmount;
    private DataBaseHelper myDbHelper;
    Cursor cursor;
    String ROLE, AMOUNT_TOKEN, AMOUNT_CASH, PROMO_BAL, CASH_BAL, sessionToken, DATA_DOB, DATA_STATE,NEWTOKEN;
    private String selectedCardType;
    String ppcode_p;
    private RelativeLayout click_promo, after_promo;
    EditText promo_txt;
    ImageView close_img;

    private static final int ADD_NEW_CARD = 1111, EDIT_CARD = 1112, PHONE_LOCK = 1113;

    JSONObject jsonObj_card;

    //  BraintreeFragment mBraintreeFragment;

    private String wagerName, legendName, matchupDate;
    MixpanelAPI mMixpanel;

    ProgressDialog progressDialog;

    //private String PayPal_Invoice_id="",paypal_state="";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
//    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    // Pls note that these credentials will differ between live & sandbox environments.
    // private static final String CONFIG_CLIENT_ID="AV-6x61qQ1s5x38usb8MsCAkfFDhbuX95vzu090E1rPpqGlXnMNV5qwuCaUt5O8ycLYk7-w3HPr6Ub9x";
    private static final String CONFIG_CLIENT_ID1 = "Af-wV_e8VpVucXJLtcDBgtkcJm4r-i1AnXF5GFMaIAGLxVAPEhY-cOhl9VA-ZF7gINslDatFePLf6RWt";
    private static final String CONFIG_CLIENT_ID = "AZXBmKKIckXQ_g4DKbZfgwKLRSluu287MQ2V0tVW6-VG17460VhE5NK0zkLipOrvf_cDFtsJC37CbQRL";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    PayPalServerResponse paypalResponse;


    private String promo_code = "";
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .acceptCreditCards(false)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));
    // .acceptCreditCards(false) -- For Credit cards


    PayPalPayment thingToBuy;

    String status;
    TextView pay_tv;
String pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funds);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

        mMixpanel = MixpanelAPI.getInstance(AddFunds.this, getString(R.string.test_MIX_PANEL_TOKEN));
        mMixpanel.track("Page Add Funds", null);

        trackEvent("Page Add Funds",null);

        //Get data from Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("fromPage"))
                fromPage = bundle.getString("fromPage");
            if (bundle.containsKey("customAmount"))
                customAmount = bundle.getInt("customAmount");
            if (bundle.containsKey("ppcode"))
                ppcode_p = bundle.getString("ppcode");
        }
        myDbHelper = new DataBaseHelper(getApplicationContext());
        progressDialog = new ProgressDialog(AddFunds.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        getDBInfo();

        init();

     /*   try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, getString(R.string.PAYPAL_SANBOX));
            //    mBraintreeFragment = BraintreeFragment.newInstance(this, getString(R.string.PAYPAL_LIVE));
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
            Utilities.showToast(AddFunds.this, "You are Unauthorized to make payment though PayPal");
        }*/

        //paypal Configuration
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

    }


    private void getDBInfo() {
        cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                PROMO_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                DATA_DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                DATA_STATE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_STATE)).trim();
            }
            cursor.close();
        } else {
            ROLE = "0";
        }
    }

    TextView toolbar_title_x;

    private void init() {
        toolbar_title_x = findViewById(R.id.toolbar_title_x);

        rl_add_new_card = findViewById(R.id.rl_add_new_card);
        etCustomAmt = findViewById(R.id.etCustomAmt);
        amountView = findViewById(R.id.amountView);
        final LinearLayout llAdd5 = findViewById(R.id.llAdd5);
        LinearLayout llAdd10 = findViewById(R.id.llAdd10);
        LinearLayout llAdd25 = findViewById(R.id.llAdd25);
        LinearLayout llAdd50 = findViewById(R.id.llAdd50);
        tvAddFunds = findViewById(R.id.tvAddFunds);
        llAddFunds = findViewById(R.id.llAddFunds);
        tvFaq = findViewById(R.id.tvFaq);
        llPaypal = findViewById(R.id.llPaypal);
        rvCardsList = findViewById(R.id.rvCardsList);

        after_promo = findViewById(R.id.after_promo);
        click_promo = findViewById(R.id.click_promo);
        promo_txt = findViewById(R.id.promo_txt);
        close_img = findViewById(R.id.promo_close);
        pay_tv = findViewById(R.id.pay_tv);


        LinearLayoutManager llm = new LinearLayoutManager(AddFunds.this);
        rvCardsList.setLayoutManager(llm);
        rvCardsList.setItemAnimator(null);
        rvCardsList.setNestedScrollingEnabled(false);
        recycleAdapter = new CardsListAdapter(cardsResponse, AddFunds.this);
        rvCardsList.setAdapter(recycleAdapter);


        toolbar_title_x.setText(getString(R.string.add_funds));

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        rl_add_new_card.setOnClickListener(this);
        llAddFunds.setOnClickListener(this);
        llAdd5.setOnClickListener(this);
        llAdd10.setOnClickListener(this);
        llAdd25.setOnClickListener(this);
        llAdd50.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        llPaypal.setOnClickListener(this);
        llAddFunds.setEnabled(false);
        click_promo.setOnClickListener(this);
        after_promo.setOnClickListener(this);
        close_img.setOnClickListener(this);
        promo_txt.addTextChangedListener(this);


        handleSubmitButton();

        if (customAmount > 0) {
            etCustomAmt.setText("$" + customAmount);
        }

        etCustomAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String customAmount = etCustomAmt.getText().toString();
                if (charSequence.length() == 0) {
                    return;
                }
                if (customAmount.indexOf("$") == -1 && charSequence.length() >= 1) {
                    customAmount = "$" + customAmount;
                    etCustomAmt.setText(customAmount);
                    if (i1 == 0 && i2 == 1) {
                        etCustomAmt.setSelection(2);
                    } else {
                        etCustomAmt.setSelection(1);
                    }
                } else {
                    if (customAmount.length() == 1) {
                        etCustomAmt.setText("");
                    }
                }

//                String text = etCustomAmt.getText().toString();
//                int textlength = etCustomAmt.getText().length();
//
//                if (text.endsWith(" "))
//                    return;
//
//                if (textlength == 1) {
//                    if (!text.contains("$")) {
//                        etCustomAmt.setText(new StringBuilder(text).insert(text.length() - 1, "$").toString());
//                        etCustomAmt.setSelection(etCustomAmt.getText().length());
//                    }
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                llAddFunds.setBackgroundResource(R.drawable.btn_bg_grey);
                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                llAddFunds.setEnabled(false);
                if (etCustomAmt.getText().toString().trim().length() == 0) {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
                } else {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.text_color_new, null));
                }
            }
        });

        Cursor cursor = myDbHelper.getDeepInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DEEPLINK_CODE)));
                ppcode_p = (cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DEEPLINK_CODE)));
            }

            cursor.close();

        }
        if (ppcode_p != null) {
            click_promo.setVisibility(View.GONE);
            after_promo.setVisibility(View.VISIBLE);
            close_img.setVisibility(View.GONE);
            promo_txt.setFocusable(true);
            promo_txt.setText(ppcode_p);
        } else {
            click_promo.setVisibility(View.VISIBLE);
            after_promo.setVisibility(View.GONE);
            promo_txt.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleSubmitButton() {
        if (customAmount > 0) {
            tvAddFunds.setText("PLAY");
        } else {
            tvAddFunds.setText("ADD FUNDS");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//Get list of cards data
        getAllCardsList();

        try {
//            Bundle bundle = getIntent().getExtras();
//            if (bundle != null) {
//                if (bundle.containsKey("place_p"))
//                    pp = bundle.getString("place_p");
//            }

//            if (pp.equalsIgnoreCase("P")) {
//                if (PlayPickGo_MatchupScreen.MATCHUPS_JSON != null)
//                    jsonObj_card = new JSONObject(PlayPickGo_MatchupScreen.MATCHUPS_JSON);
//                if (PlayPickGo_MatchupScreen.MATCHUPS_CARDAMOUNT != null)
//             //       payAmount = Integer.parseInt(PlayPickGo_MatchupScreen.MATCHUPS_CARDAMOUNT);
//                if (PlayPickGo_MatchupScreen.MATCHUPS_CARDTITLE != null)
//                    wagerName = PlayPickGo_MatchupScreen.MATCHUPS_CARDTITLE;
//                if (PlayPickGo_MatchupScreen.MATCHUPS_CARD_LEGEND != null)
//                    legendName = PlayPickGo_MatchupScreen.MATCHUPS_CARD_LEGEND;
//                if (PlayPickGo_MatchupScreen.MATCHUPS_CARD_DATE != null)
//                    matchupDate = PlayPickGo_MatchupScreen.MATCHUPS_CARD_DATE;
//
//            } else
                {
                if (MatchupScreen.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(MatchupScreen.MATCHUPS_JSON);
                if (MatchupScreen.MATCHUPS_CARDAMOUNT != null)
               //     payAmount = Integer.parseInt(MatchupScreen.MATCHUPS_CARDAMOUNT);
                if (MatchupScreen.MATCHUPS_CARDTITLE != null)
                    wagerName = MatchupScreen.MATCHUPS_CARDTITLE;
                if (MatchupScreen.MATCHUPS_CARD_LEGEND != null)
                    legendName = MatchupScreen.MATCHUPS_CARD_LEGEND;
                if (MatchupScreen.MATCHUPS_CARD_DATE != null)
                    matchupDate = MatchupScreen.MATCHUPS_CARD_DATE;
            }

            Log.e("Json-----", jsonObj_card + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //get user saved cards list
    private void getAllCardsList() {

        AndroidNetworking.get(APIs.CARDS_LIST_URL)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("getAllCardsList :: ", response.toString());

                        cardsResponse.clear();

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                CardDataPojo cardData = new CardDataPojo();
                                cardData.set_id(jb.getString("id"));
//                                cardData.setAccount(jb.getString("account"));
//                                cardData.setToken(jb.getString("fdToken"));
//                                cardData.setAuthorizeNetCustomerProfileId(jb.getString("authorizeNetCustomerProfileId"));
//                                cardData.setAuthorizeNetCustomerPaymentProfileId(jb.getString("authorizeNetCustomerPaymentProfileId"));
//                                cardData.setLastFourDigits(jb.getString("lastFourDigits"));
                                cardData.setLastFourDigits(jb.getString("last_four_digits"));
                                cardData.setExpiry(jb.getString("expiry"));
//                                cardData.setCardType(jb.getString("cardType"));
                                cardData.setCardType(jb.getString("card_type"));
//                                cardData.setCreatedAt(jb.getString("createdAt"));
                                cardData.setFull_name(jb.getString("full_name"));
                                cardsResponse.add(cardData);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (cardsResponse.size() > 0) {
                            rvCardsList.setVisibility(View.VISIBLE);
                            if (recycleAdapter != null)
                                recycleAdapter.notifyDataSetChanged();
                        } else {
                            rvCardsList.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utilities.showToast(AddFunds.this, error.getErrorBody());
                    }
                });

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                finish();
                break;
            case R.id.llAddFunds:
//                showAlertBoxFing(AddFunds.this);
                //   if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {


                if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                    if (Utilities.getAge(DATA_DOB) >= 18) {
                        Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

                        if (progressDialog != null)
                            progressDialog.show();


                        String action = tvAddFunds.getText().toString().trim();
                        if (action.equalsIgnoreCase("PLAY")) {
                            //Write purchase card service call here
                            try {
                                jsonObj_card = new JSONObject(MatchupScreen.MATCHUPS_JSON);
                                jsonObj_card.put("creditCardId", selCardId);
                                if(selCardId.isEmpty()){

                                    jsonObj_card.put("balanceToPay", "0");
                                }else{
                                    jsonObj_card.put("balanceToPay", customAmount);

                                }
//                                jsonObj_card.put("balanceToPay", customAmount);

                                if (progressDialog != null)
                                    progressDialog.show();
                                purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);
//                jsonObj_card =  new JSONObject(getIntent().getStringExtra("cardjson"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (progressDialog != null)
                                progressDialog.show();

                            checkpromoAPI();


                        }

                    } else {
                        showAlertBox(AddFunds.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                    }


                } else {
                    Utilities.showAlertBoxTrans(AddFunds.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                }


//                } else {
//                    Utilities.showAlertBoxTrans(AddFunds.this, getString(R.string.age_to_cash_title), getString(R.string.token_to_cash_msg));
//                }


                break;
            case R.id.rl_add_new_card:

                if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                    if (Utilities.getAge(DATA_DOB) >= 18) {
                        Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));


                        final String amountTxt = etCustomAmt.getText().toString().trim().replace("$", "");
                        if (!amountTxt.equals("")) {
                            if (Integer.parseInt(amountTxt) >= 1) {

                                if (promo_txt.getText().toString().trim().length() > 0 && promo_txt.getVisibility() == View.VISIBLE) {

                                    AndroidNetworking.get(APIs.CHECKPROMO + "?code=" + promo_txt.getText().toString().trim())
                                            .setPriority(Priority.HIGH)
                                            .addHeaders("sessionToken", sessionToken)
                                            .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    if (progressDialog != null)
                                                        progressDialog.dismiss();

                                                    try {
//                                String s = response.getString("isValidPromoCode");
                                                        response.getBoolean("isValidPromoCode");
                                                        if (response.getBoolean("isValidPromoCode") == true) {

                                                            if (progressDialog != null)
                                                                progressDialog.dismiss();
                                                            Intent addNewCardIntent = new Intent(AddFunds.this, AddNewCard.class);
                                                            addNewCardIntent.putExtra("AMOUNT", amountTxt);
                                                            addNewCardIntent.putExtra("place_p", "");
                                                            addNewCardIntent.putExtra("PROMOC", promo_txt.getText().toString().trim());
                                                            if (customAmount > 0) {
                                                                addNewCardIntent.putExtra("fromPage", AppConstants.CHECKOUT);
                                                            }
                                                            addNewCardIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                            startActivityForResult(addNewCardIntent, ADD_NEW_CARD);
                                                            if (tvAddFunds != null && !tvAddFunds.getText().toString().trim().equalsIgnoreCase("PLAY"))
                                                                finish();


                                                        } else {
                                                            String m = response.getString("message");
                                                            Utilities.showToast(AddFunds.this, m);


                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    if (progressDialog != null)
                                                        progressDialog.dismiss();
                                                    Utilities.showToast(AddFunds.this, "Please try again later");
                                                }
                                            });
                                } else {
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                    Intent addNewCardIntent = new Intent(AddFunds.this, AddNewCard.class);
                                    addNewCardIntent.putExtra("AMOUNT", amountTxt);
                                    addNewCardIntent.putExtra("PROMOC", promo_txt.getText().toString().trim());
                                    if (customAmount > 0) {
                                        addNewCardIntent.putExtra("fromPage", AppConstants.CHECKOUT);
                                    }
                                    addNewCardIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivityForResult(addNewCardIntent, ADD_NEW_CARD);
                                    if (tvAddFunds != null && !tvAddFunds.getText().toString().trim().equalsIgnoreCase("PLAY"))
                                        finish();

                                }


                            } else {
                                Utilities.showToast(AddFunds.this, "Please enter minimum amount , it should be at least of $1");
                            }
                        } else {
                            Utilities.showToast(AddFunds.this, "Please enter minimum amount , it should be at least of $1");
                        }


                    } else {
                        showAlertBox(AddFunds.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                    }


                } else {
                    Utilities.showAlertBoxTrans(AddFunds.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                }


                break;
            case R.id.llAdd5:
                etCustomAmt.setText("$5");
                etCustomAmt.setSelection(etCustomAmt.getText().toString().length());
                break;
            case R.id.llAdd10:
                etCustomAmt.setText("$10");
                etCustomAmt.setSelection(etCustomAmt.getText().toString().length());
                break;
            case R.id.llAdd25:
                etCustomAmt.setText("$25");
                etCustomAmt.setSelection(etCustomAmt.getText().toString().length());
                break;
            case R.id.llAdd50:
                etCustomAmt.setText("$50");
                etCustomAmt.setSelection(etCustomAmt.getText().toString().length());
                break;
            case R.id.tvFaq:
                Intent webIntent = new Intent(AddFunds.this, WebScreens.class);
                webIntent.putExtra("title", AppConstants.FAQS);
                startActivity(webIntent);
                break;
            case R.id.llPaypal:
                String amountTxt1 = etCustomAmt.getText().toString().trim().replace("$", "");
                if (!amountTxt1.equals("") && Integer.parseInt(amountTxt1) >= 1) {
                    if (ROLE.equalsIgnoreCase("cash")) {
                        //setupBraintreeAndStartExpressCheckout();

                        status = "Paypal";
//                        getAllCardsList();
//                        recycleAdapter = new CardsListAdapter(cardsResponse, AddFunds.this);
//                        rvCardsList.setAdapter(recycleAdapter);
//                        recycleAdapter.notifyDataSetChanged();
//                        tvAddFunds.setText("ADD FUNDS");
//                        pay_tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
//                        tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
//                        llAddFunds.setEnabled(true);
//                        llPaypal.setBackgroundResource(R.drawable.btn_bg_grey);
//                        llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
//

                        setupPayPal();

//                        setupPayPal();

                    } else {
                        showAlertBoxTwo(AddFunds.this, getString(R.string.switch_to_cash_header), getString(R.string.switch_to_cash_msg));
                    }
                } else {
                    Utilities.showToast(AddFunds.this, "Please enter minimum amount , it should be at least of $1");
                }
                break;
            case R.id.promo_close:
                click_promo.setVisibility(View.VISIBLE);
                after_promo.setVisibility(View.GONE);
                promo_txt.setText("");

                break;
            case R.id.click_promo:
                click_promo.setVisibility(View.GONE);
                after_promo.setVisibility(View.VISIBLE);
                close_img.setVisibility(View.GONE);
                promo_txt.setFocusable(true);
                break;
            default:
                break;
        }
    }


    private void showAlertBox(final Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts);


        Window window = dialog.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);


        dialog.show();
        dialog.setCancelable(false);


        TextView alert_title = (TextView) dialog.findViewById(R.id.alert_title);
        TextView alert_msg = (TextView) dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = (TextView) dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });

    }

    private void checkpromoAPI() {
//        promo_txt.getVisibility() == View.VISIBLE
//String pr = promo_txt.getText().toString().trim()
        final String action = tvAddFunds.getText().toString().trim();
        ;
        if (promo_txt.getText().toString().trim().length() > 0 && promo_txt.getVisibility() == View.VISIBLE) {

            AndroidNetworking.get(APIs.CHECKPROMO + "?code=" + promo_txt.getText().toString().trim())
                    .setPriority(Priority.HIGH)
                    .addHeaders("sessionToken", sessionToken)
                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (progressDialog != null)
                                progressDialog.dismiss();

                            try {
//                                String s = response.getString("isValidPromoCode");
                                response.getBoolean("isValidPromoCode");
                                if (response.getBoolean("isValidPromoCode") == true) {

                                    if (progressDialog != null)
                                        progressDialog.dismiss();

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        final FingerprintDialogBuilder dialogBuilder = new FingerprintDialogBuilder(AddFunds.this)
                                                .setTitle("Confirm to add funds")
                                                .setSubtitle(" ")
                                                .setDescription(" ")
                                                .setNegativeButton("Cancel");
                                        dialogBuilder.show(AddFunds.this.getSupportFragmentManager(), callback);

                                    } else if (action.equalsIgnoreCase("ADD FUNDS") && status.equalsIgnoreCase("Paypal")) {
//                    setupBraintreeAndStartExpressCheckout();
                                        setupPayPal();

                                    } else {
                                        AuthenticateUserWIthLock();
                                    }

                                } else {
                                    String m = response.getString("message");
                                    Utilities.showToast(AddFunds.this, m);


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Utilities.showToast(AddFunds.this, anError.getErrorBody());
                        }
                    });
        } else {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                final FingerprintDialogBuilder dialogBuilder = new FingerprintDialogBuilder(AddFunds.this)
                        .setTitle("Confirm to add funds")
                        .setSubtitle(" ")
                        .setDescription(" ")
                        .setNegativeButton("Cancel");
                dialogBuilder.show(AddFunds.this.getSupportFragmentManager(), callback);

            } else if (action.equalsIgnoreCase("ADD FUNDS") && status.equalsIgnoreCase("Paypal")) {
//                    setupBraintreeAndStartExpressCheckout();
                setupPayPal();

            } else {
                AuthenticateUserWIthLock();
            }
        }


    }

   /* public void setupBraintreeAndStartExpressCheckout() {
        PayPalRequest request = new PayPalRequest(etCustomAmt.getText().toString().replace("$", ""))
                .currencyCode("USD")
                .intent(PayPalRequest.INTENT_AUTHORIZE);

        PayPal.requestOneTimePayment(mBraintreeFragment, request);
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {

        if (paymentMethodNonce instanceof PayPalAccountNonce) {
            PayPalAccountNonce payPalAccountNonce = (PayPalAccountNonce) paymentMethodNonce;

            // Access additional information
            String email = payPalAccountNonce.getEmail();
            String firstName = payPalAccountNonce.getFirstName();
            String lastName = payPalAccountNonce.getLastName();
            String phone = payPalAccountNonce.getPhone();

            // See PostalAddress.java for details
            PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
            PostalAddress shippingAddress = payPalAccountNonce.getShippingAddress();
        }
        // Send nonce to server
        String nonce = paymentMethodNonce.getNonce();
        if (nonce != null && !nonce.equals("")) {
            sendNonceToServer(nonce);
        } else {
            Utilities.showToast(AddFunds.this, "Something went wrong! Please try after sometime ");
        }

    }

    @Override
    public void onCancel(int requestCode) {
        // Use this to handle a canceled activity, if the given requestCode is important.
        // You may want to use this callback to hide loading indicators, and prepare your UI for input
    }

    @Override
    public void onError(Exception error) {
        if (error instanceof ErrorWithResponse) {
            ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;
            BraintreeError cardErrors = errorWithResponse.errorFor("creditCard");
            if (cardErrors != null) {
                // There is an issue with the credit card.
                BraintreeError expirationMonthError = cardErrors.errorFor("expirationMonth");
                if (expirationMonthError != null) {
                    // There is an issue with the expiration month.
                    //setErrorMessage(expirationMonthError.getMessage());

                    Log.e("PAYPAL ERROR::", expirationMonthError.getMessage());
                }
            }
        }
    }*/


    private void fingerprintAlert() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(AddFunds.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts_finger);

        Window window = dialog.getWindow();
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams param = window.getAttributes();
        window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        param.y = 10;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.show();
        dialog.setCancelable(false);

        TextView declineButton = dialog.findViewById(R.id.alert_cancel);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


    }

    final AuthenticationCallback callback = new AuthenticationCallback() {
        @Override
        public void fingerprintAuthenticationNotSupported() {
            // Device doesn't support fingerprint authentication. May be device doesn't have fingerprint hardware or device is running on Android below Marshmallow.
            // Switch to alternate authentication method.
            Log.e("No", "doesn't support fingerprint");
            AuthenticateUserWIthLock();
        }

        @Override
        public void hasNoFingerprintEnrolled() {
            // User has no fingerprint enrolled.
            // Application should redirect the user to the lock screen settings.
            // FingerprintUtils.openSecuritySettings(this)
            Log.e("No", "doesn't enrolled fingerprint");
            AuthenticateUserWIthLock();
        }

        @Override
        public void onAuthenticationError(final int errorCode, @Nullable final CharSequence errString) {
            // Unrecoverable error. Cannot use fingerprint scanner. Library will stop scanning for the fingerprint after this callback.
            // Switch to alternate authentication method.
            AuthenticateUserWIthLock();
        }

        @Override
        public void onAuthenticationHelp(final int helpCode, @Nullable final CharSequence helpString) {
            // Authentication process has some warning. such as "Sensor dirty, please clean it."
            // Handle it if you want. Library will continue scanning for the fingerprint after this callback.
        }

        @Override
        public void authenticationCanceledByUser() {
            // User canceled the authentication by tapping on the cancel button (which is at the bottom of the dialog).
            AuthenticateUserWIthLock();
        }

        @Override
        public void onAuthenticationSucceeded() {
            // Authentication success
            // Your user is now authenticated.
            Log.e("Yes", "Fingetprint auth success");
            if (progressDialog != null)
                progressDialog.show();
            buildRequestForSubmit();
        }

        @Override
        public void onAuthenticationFailed() {
            // Authentication failed.
            // Library will continue scanning the fingerprint after this callback.
            Utilities.showToast(AddFunds.this, "Finger print authentocation failed");
            Log.e("Oops!!", "Authentication failed");

        }
    };


    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.edit_card:
                Intent editCardIntent = new Intent(AddFunds.this, EditCreditCard.class);
                editCardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                editCardIntent.putExtra("CARD_ID", selCardId);
                editCardIntent.putExtra("CARD_TYPE", selectedCardType);
                startActivityForResult(editCardIntent, EDIT_CARD);
                return true;
            case R.id.delete_card:
                try {
                    // do your code
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("cardId", selCardId);
                    DeleteCreditCard(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            default:
                return false;
        }


    }


    private void purchaseCardApi(JSONObject jsonObj_card, final String matchupDate, final String legendName, final String wagerName) {

        if (Utilities.isNetworkAvailable(getApplicationContext())) {

            AndroidNetworking.post(APIs.PURCHASE_CARD)
                    .addJSONObjectBody(jsonObj_card) // posting json
                    .addHeaders("Content-Type", "application/json")
                    .addHeaders("sessionToken", sessionToken)
                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (progressDialog != null)
                                progressDialog.dismiss();


                            JSONObject props = new JSONObject();
                            try {
                                JSONObject jsonObj_card_a = new JSONObject();
                                props.put("isFirstPurchase ", null);
                                if (jsonObj_card_a.getString("amount") != null)
                                    props.put("price ", jsonObj_card_a.getString("amount"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mMixpanel.track("Purchase Complete", props);

                            trackEvent("Purchase Complete",props);

                            Log.e("Purchase Crda:: ", response.toString());

                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                            cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                            cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                            cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                            myDbHelper.updateUser(cv);
                            footBallDialog(matchupDate, legendName, wagerName);
                        }

                        @Override
                        public void onError(ANError anError) {

                            if (anError.getErrorCode() != 0) {
                                Log.e("", "onError errorCode : " + anError.getErrorCode());
                                Log.e("", "onError errorBody : " + anError.getErrorBody());
                                Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Utilities.showToast(AddFunds.this, "Something went wrong,Please try again later.");//                                Utilities.showToast(AddFunds.this, anError.getErrorBody());

                            } else {
                                Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                            }

                        }
                    });
        } else {
            Utilities.showNoInternetAlert(AddFunds.this);

        }

    }

    private void footBallDialog(String matchupDate, String legendName, String wagerName) {


        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_game_started, viewGroup, false);

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialog.show();

        TextView alert_title = (TextView) alertDialog.findViewById(R.id.tvGame);
        TextView alert_msg = (TextView) alertDialog.findViewById(R.id.tvMatchStartedTime);

        alert_title.setText(wagerName);
        alert_msg.setText(matchupDate);
        TextView alert_ok = (TextView) alertDialog.findViewById(R.id.btnGotIt);
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//
////                CardSFrag
                Intent intent_ = new Intent(AddFunds.this, Dashboard.class);
                startActivity(intent_);

            }
        });

        de.hdodenhof.circleimageview.CircleImageView alert_legendname = alertDialog.findViewById(R.id.imageViewFootBall);


        String headerStr = legendName;

        switch (headerStr) {
            case "NFL":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_am_football));
                break;
            case "NBA":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_basketball));
                break;

            case "NHL":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_hockey));
                break;
            case "NASCAR":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_nascar_h));
                break;

            case "MLB":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseball));
                break;

            case "EPL":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_tennis));
                break;

            case "LA-LIGA":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_am_football));
                break;

            case "MLS":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_soccer));
                break;

            case "NCAAMB":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_basketball));
                break;

            case "PGA":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.ic_golf));
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        int promo_length = promo_txt.getText().toString().trim().length();

        if (promo_length == 0) {
            close_img.setVisibility(View.GONE);
        } else if (promo_length >= 1 && promo_length <= 5) {
            close_img.setVisibility(View.VISIBLE);
        } else {
//            promo_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_promo_sm_applied_s, 0, 0, 0);

            close_img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.ViewHolder> {


        private final List<CardDataPojo> mValues;
        private Context context;
        private int selPos = -1;

        public CardsListAdapter(List<CardDataPojo> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.added_card_view, parent, false);

            return new ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

            String cardType = cardsResponse.get(position).getCardType();
            selectedCardType = cardType;
            if (cardType.equalsIgnoreCase("Mastercard")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_mastercard);
                holder.tvCardName.setText("Mastercard* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("visa")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_visa);
                holder.tvCardName.setText("Visa* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("JCB")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
                holder.tvCardName.setText("JCB* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("Discover")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
                holder.tvCardName.setText("Discover* " + cardsResponse.get(position).getLastFourDigits());
            } else if (cardType.equalsIgnoreCase("American Express")) {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
                holder.tvCardName.setText("American Ex* " + cardsResponse.get(position).getLastFourDigits());
            } else {
                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
            }

            String expDate = cardsResponse.get(position).getExpiry();
            if (expDate != null && !expDate.equalsIgnoreCase("")) {
                holder.tvExpiry.setVisibility(View.VISIBLE);
                holder.tvExpiry.setText("exp " + expDate);
            } else {
                holder.tvExpiry.setVisibility(View.GONE);
            }

            holder.ivOverFlow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = cardsResponse.get(position).get_id();
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                    notifyDataSetChanged();
                    Context wrapper = new ContextThemeWrapper(AddFunds.this, R.style.popupMenuStyle1);
                    PopupMenu popup = new PopupMenu(wrapper, view);
                    popup.setOnMenuItemClickListener(AddFunds.this);
                    popup.inflate(R.menu.cc_card_menu);
                    popup.show();
                }
            });


            final String amountTxt = etCustomAmt.getText().toString().trim().replace("$", "");
            if (!amountTxt.equals("")) {
                if (Integer.parseInt(amountTxt) >= 1) {
                    if (etCustomAmt.getText().toString().trim().length() > 0) {
                        if (selPos == position) {
                            holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
                            holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                        } else {
                            holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                            holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                        }
                    }
                }else{
                    notifyDataSetChanged();
                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                    holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));

                }
            }


            holder.llCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String amountTxt = etCustomAmt.getText().toString().trim().replace("$", "");
                    if (!amountTxt.equals("")) {
                        if (Integer.parseInt(amountTxt) >= 1) {
                            selCardId = cardsResponse.get(position).get_id();
                            selPos = position;
                            notifyDataSetChanged();


                            if (etCustomAmt.getText().toString().trim().length() > 0) {
                                llAddFunds.setEnabled(true);
                                if (customAmount > 0) {
                                    llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                                } else {
//                            llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));

                                    status = "ADD FUNDS";
                                    tvAddFunds.setText("ADD FUNDS");
                                    pay_tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                                    llPaypal.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.price_bg, null));
                                    llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
                                    holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));

                                }
                                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                            } else {
                                llAddFunds.setEnabled(false);
                                llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                            }

                        } else {

                            holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                            holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
                            Toast.makeText(AddFunds.this, "Enter Amount more than 0", Toast.LENGTH_LONG).show();

                        }
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivCard, ivOverFlow;
            TextView tvCardName, tvExpiry;
            LinearLayout llCard;

            public ViewHolder(View view) {
                super(view);
                llCard = view.findViewById(R.id.llCard);
                ivCard = view.findViewById(R.id.ivCard);
                tvCardName = view.findViewById(R.id.tvCardName);
                tvExpiry = view.findViewById(R.id.tvExpiry);
                ivOverFlow = view.findViewById(R.id.ivOverFlow);

            }
        }
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == ADD_NEW_CARD || requestCode == EDIT_CARD) {
                    getAllCardsList();
                }
            }

            if (resultCode == RESULT_OK && requestCode == PHONE_LOCK) {

                if (progressDialog != null)
                    progressDialog.show();
                buildRequestForSubmit();
            }


            //Do something after 100ms
            if (requestCode == REQUEST_CODE_PAYMENT) {
                if (resultCode == Activity.RESULT_OK) {

                    PaymentConfirmation confirm = data
                            .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirm != null) {
                        String paymentDetails = confirm.toJSONObject().toString(4);

                        try {
                            System.out.println(confirm.toJSONObject().toString(4));
                            System.out.println(confirm.getPayment().toJSONObject()
                                    .toString(4));
                            Log.e("PayPal Test", "7--------------" + confirm.getPayment().toJSONObject());
                            Log.e("PayPal Test", "7------testc--------" + paymentDetails);


                            JSONObject jsonDetails = new JSONObject(paymentDetails);

                            Log.e("PayPal Test", "7------testc00000000000--------" + jsonDetails.getJSONObject("response"));

                            JSONObject res_k = jsonDetails.getJSONObject("response");

                            res_k.getString("id");

                            Log.e("PayPal Test", "7------testc00000iiid--------" + res_k.getString("id"));


                            Log.e("PayPal Test", "7------testres_kc--------" + res_k);
                            Log.e("PayPal Test", "7------res_k.getString--------" + res_k.getString("id"));

                            sendNonceToServer(res_k.getString("id"));


                            /*paypalResponse = new Gson().fromJson(confirm.toJSONObject().toString(4), PayPalServerResponse.class);
                            Log.e("PayPal Test", "7------paypalResponse--------"+paypalResponse);
                            if (paypalResponse != null) {
                                //PayPal_Invoice_id =paypalResponse.getResponse().getId();
                                //paypal_state=paypalResponse.getResponse().getState();
                                Toast.makeText(CheckoutActivity.this, "4---", Toast.LENGTH_LONG).show();
                                Log.e("PayPal Test", "8--------------"+ paypalResponse.getResponse().getId());

//                                Toast.makeText(CheckoutActivity.this, "INVOICE::" + paypalResponse.getResponse().getId(), Toast.LENGTH_LONG).show();
                                sendNonceToServer(paypalResponse.getResponse().getId());
                            }

                            */
                        } catch (JSONException e) {
                            Toast.makeText(AddFunds.this, "ERROR::" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("PayPal Test", "9--------------");

                        }
                    }
//                            if (confirm != null) {
//                                try {
//                                    System.out.println(confirm.toJSONObject().toString(4));
//                                    System.out.println(confirm.getPayment().toJSONObject()
//                                            .toString(4));
//
//                                    paypalResponse = new Gson().fromJson(confirm.toJSONObject().toString(4), PayPalServerResponse.class);
//
//                                    if (paypalResponse != null) {
//                               /* PayPal_Invoice_id =paypalResponse.getResponse().getId();
//                                paypal_state=paypalResponse.getResponse().getState();
//                                Toast.makeText(AddFunds.this,"INVOICE::"+PayPal_Invoice_id,Toast.LENGTH_LONG).show();
//                                sendNonceToServer(PayPal_Invoice_id);*/
//                                        //PayPal_Invoice_id =paypalResponse.getResponse().getId();
//                                        //paypal_state=paypalResponse.getResponse().getState();
//                                        //  Toast.makeText(AddFunds.this, "INVOICE::" + paypalResponse.getResponse().getId(), Toast.LENGTH_LONG).show();
//                                        sendNonceToServer(paypalResponse.getResponse().getId());
//                                    }
//                                } catch (JSONException e) {
//                                    Toast.makeText(AddFunds.this, "ERROR::" + e.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }
                } else if (resultCode == Activity.RESULT_CANCELED) {

                    Toast.makeText(AddFunds.this, "The user canceled.", Toast.LENGTH_LONG).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                    Toast.makeText(AddFunds.this, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
                if (resultCode == Activity.RESULT_OK) {
                    PayPalAuthorization auth = data
                            .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                    if (auth != null) {
                        try {
                            Toast.makeText(getApplicationContext(),
                                    "Future Payment code received from PayPal",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "EXCEPTION::",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(),
                            "Transaction cancelled",
                            Toast.LENGTH_LONG).show();
                } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                    Toast.makeText(getApplicationContext(),
                            "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.",
                            Toast.LENGTH_LONG).show();
                }
            }


        } catch (Exception ex) {
//            Toast.makeText(AddFunds.this, ex.toString(),
//                    Toast.LENGTH_SHORT).show();
        }

    }


    private void buildRequestForSubmit() {
        try {
            JSONObject jsonObject = new JSONObject();
            String amount = etCustomAmt.getText().toString().trim().replace("$", "");
            String promocode_txt = promo_txt.getText().toString().trim();
            jsonObject.put("amount", amount);
            jsonObject.put("cardId", selCardId);
            jsonObject.put("promoCode", promocode_txt);
//            if(promocode_txt!=null){
//                jsonObject.put("promoCode", promocode_txt);
//            }else{
//                jsonObject.put("promoCode", "");
//            }

            SubmitFunds(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Add Funds Submit
    private void SubmitFunds(JSONObject jsonObject) {
        Log.e("ADDFUNDSREQUEST::", jsonObject.toString());
        AndroidNetworking.post(APIs.ADD_FUNDS_URL)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (progressDialog != null)
                            progressDialog.dismiss();
                        try {


                            JSONObject props = new JSONObject();
                            props.put("isFirstDeposit", null);
                            props.put("amount", customAmount);
                            props.put("payMethod", "creditcard");
                            mMixpanel.track("Add Funds Complete", props);


                            trackEvent("Add Funds Complete",props);

                            Log.e("ADDFUNDS::RESPONSE::", response.toString());
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                            cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                            cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                            cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                            myDbHelper.updateUser(cv);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Utilities.showToast(AddFunds.this, anError.getErrorBody());
                        finish();

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            JSONObject props = new JSONObject();

                            try {
                                props.put("returnedError", null);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mMixpanel.track("Add Funds Error", props);
                            trackEvent("Add Funds Error",props);
                        } else {
                            Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }
                    }
                });

    }

    private void DeleteCreditCard(JSONObject jsonObject) {
        Log.e("DeleteCard::REQUEST::", jsonObject.toString());
        AndroidNetworking.delete(APIs.DELETE_CARD_URL)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < cardsResponse.size(); i++) {
                                if (cardsResponse.get(i).get_id().equalsIgnoreCase(selCardId)) {
                                    if (cardsResponse.size() > i) {
                                        cardsResponse.remove(i);
                                        break;
                                    }
                                }
                            }

                            if (recycleAdapter != null) {
                                recycleAdapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        /*Remove this while pushing it to live*/
                        for (int i = 0; i < cardsResponse.size(); i++) {
                            if (cardsResponse.get(i).get_id().equalsIgnoreCase(selCardId)) {
                                if (cardsResponse.size() > i) {
                                    cardsResponse.remove(i);
                                    break;
                                }
                            }
                        }

                        if (recycleAdapter != null) {
                            recycleAdapter.notifyDataSetChanged();
                        }
                        /*Till here */
                        Log.e("ERROR####", "ERROR-------" + anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                        } else {
                            Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }
                    }
                });
    }

    private void AuthenticateUserWIthLock() {
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (km != null && km.isKeyguardSecure()) {
            Intent i = km.createConfirmDeviceCredentialIntent("Authentication required", "Please enter your ph0ne PIN/Pattern/Password");
            startActivityForResult(i, PHONE_LOCK);
        } else {
            if (progressDialog != null)
                progressDialog.show();
            buildRequestForSubmit();
        }
    }

    private void footBallDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_game_started, viewGroup, false);

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
        alertDialog.show();
    }

    private void sendNonceToServer(String payPalNonce) {

        /*{
	"transactionId": "",
	"amount": 20
}*/
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("transactionId", payPalNonce);
            jsonObject.put("amount", etCustomAmt.getText().toString().replace("$", ""));
            jsonObject.put("promoCode", promo_txt.getText().toString().trim());
            jsonObject.put("notify", true);
            Log.e("payPalNonce::", jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(APIs.ADD_FUNDS_PAYPAL)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject props = new JSONObject();
                            props.put("isFirstDeposit", null);
                            props.put("amount", etCustomAmt.getText().toString().replace("$", ""));
                            props.put("payMethod", "paypal");
                            mMixpanel.track("Add Funds Complete", props);

                            trackEvent("Add Funds Complete",props);

                            Log.e("PAYPAL::RESPONSE::", response.toString());
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                            cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                            cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                            cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                            cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                            cv.put(DB_Constants.USER_MODETYPE, withdrawResponse.getUserPlayMode());
                            myDbHelper.updateUser(cv);
                            if (tvAddFunds != null && tvAddFunds.getText().toString().equalsIgnoreCase("PLAY")) {
                                llAddFunds.setEnabled(true);
                                llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                            } else {
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utilities.showToast(AddFunds.this, anError.getErrorBody());
                        finish();

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());
                        } else {
                            Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }
                    }
                });
    }

    private void showAlertBoxTwo(Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts_two);


        Window window = dialog.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        // window.setGravity(Gravity.CENTER);
//        window.setGravity(Gravity.BOTTOM);


        dialog.show();
        dialog.setCancelable(false);

        TextView alert_title = (TextView) dialog.findViewById(R.id.alert_title);
        TextView alert_msg = (TextView) dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = (TextView) dialog.findViewById(R.id.alert_ok);
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //setupBraintreeAndStartExpressCheckout();
                setupPayPal();
            }
        });

        TextView alert_cancel = (TextView) dialog.findViewById(R.id.alert_cancel);
        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();

    }

    private void setupPayPal() {
        String purchaseType = "";
        if (tvAddFunds != null && tvAddFunds.getText().toString().equalsIgnoreCase("PLAY")) {
            purchaseType = "Purchase Card";
        } else {
            purchaseType = "Add Funds";
        }
//        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(1)), "USD",
        thingToBuy = new PayPalPayment(new BigDecimal(etCustomAmt.getText().toString().replace("$", "")), "USD",
                purchaseType, PayPalPayment.PAYMENT_INTENT_SALE);
        thingToBuy.enablePayPalShippingAddressesRetrieval(true);
        Intent intent = new Intent(AddFunds.this,
                PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }
}
