package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
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
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.LocationTrack;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.sport.playsqorr.utilities.LocationTrack.getLocationFu;
import static com.sport.playsqorr.utilities.LocationTrack.llat;
import static com.sport.playsqorr.utilities.LocationTrack.llong;
import static com.sport.playsqorr.utilities.Utilities.checkLocationPermission;
import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, PopupMenu.OnMenuItemClickListener {

    Cursor cursor;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    String ROLE;
    String AMOUNT_TOKEN;
    String AMOUNT_CASH;
    String PROMO_BAL;
    String CASH_BAL;
    String sessionToken,NEWTOKEN;
    String DATA_DOB;
    String DATA_STATE;
    private LinearLayout llAddFunds, llPayWith, llCashAmount, llDeductTokens, llPlay, llPaypal;
    private RelativeLayout rl_add_new_card, click_promo, after_promo, rlAddFundsToSwitchCash;
    EditText promo_txt;
    private TextView tvWagerName, tvAmount, deduct_amt, remaining_bal, tvPlay, tvTokenInsufficient, tvDeductTokens,
            tvFaq;
    ImageView close_img;
    private static final int ADD_NEW_CARD = 1111, EDIT_CARD = 1112, ADD_FUNDS = 1113;
    private double payAmount = 0, diffAmount = 0, remAmount = 0;
    TextView tvAddFunds;
    private String wagerName, selCardId="";
    private List<CardDataPojo> cardsResponse = new ArrayList<>();
    private RecyclerView rvCardsList;
    private CardsListAdapter recycleAdapter;
    private DataBaseHelper myDbHelper;
    // BraintreeFragment mBraintreeFragment;

    JSONObject jsonObj_card;
    ProgressDialog progressDialog;
    private TextView tvRemainBal;
    MixpanelAPI mMixpanel;

    double paypay_amt_1;

    //  private String PayPal_Invoice_id="",paypal_state="";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    // Pls note that these credentials will differ between live & sandbox environments.
    // private static final String CONFIG_CLIENT_ID="AV-6x61qQ1s5x38usb8MsCAkfFDhbuX95vzu090E1rPpqGlXnMNV5qwuCaUt5O8ycLYk7-w3HPr6Ub9x";
    private static final String CONFIG_CLIENT_ID1 = "Af-wV_e8VpVucXJLtcDBgtkcJm4r-i1AnXF5GFMaIAGLxVAPEhY-cOhl9VA-ZF7gINslDatFePLf6RWt";
    private static final String CONFIG_CLIENT_ID = "AZXBmKKIckXQ_g4DKbZfgwKLRSluu287MQ2V0tVW6-VG17460VhE5NK0zkLipOrvf_cDFtsJC37CbQRL";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    PayPalServerResponse paypalResponse;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));
    // .acceptCreditCards(false) -- For Credit cards


    PayPalPayment thingToBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }


        /* *****************Database Starts************************/
        mydb = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = mydb.getReadableDatabase();
        /* *****************Database Ends************************/
        mMixpanel = MixpanelAPI.getInstance(CheckoutActivity.this, getString(R.string.test_MIX_PANEL_TOKEN));
        getDBInfo();
        progressDialog = new ProgressDialog(CheckoutActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        init();

      /*  try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, getString(R.string.PAYPAL_SANBOX));
            //   mBraintreeFragment = BraintreeFragment.newInstance(this, getString(R.string.PAYPAL_LIVE));
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
            Utilities.showToast(CheckoutActivity.this, "You are Unauthorized to make payment though PayPal");
        }*/
        //paypal Configuration
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        myDbHelper = new DataBaseHelper(getApplicationContext());
    }

    private void getDBInfo() {

        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                PROMO_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                DATA_DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                DATA_STATE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_STATE)).trim();
            }
            cursor.close();
        } else {
            ROLE = "0";
        }

    }

    String card_id, legendName, matchupDate;
    String pp;

    boolean sp_c;
    boolean sp_t;
    @Override
    protected void onResume() {
        super.onResume();

        //================ Hide Virtual Key Board When  Clicking==================//

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
        try {

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey("place_p"))
                    pp = bundle.getString("place_p");
            }

            if (pp.equalsIgnoreCase("P")) {
                if (PlayPickGo_MatchupScreen.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(PlayPickGo_MatchupScreen.MATCHUPS_JSON);
                if (PlayPickGo_MatchupScreen.MATCHUPS_CARDAMOUNT != null)
                    payAmount = Integer.parseInt(PlayPickGo_MatchupScreen.MATCHUPS_CARDAMOUNT);
                if (PlayPickGo_MatchupScreen.MATCHUPS_CARDTITLE != null)
                    wagerName = PlayPickGo_MatchupScreen.MATCHUPS_CARDTITLE;
                if (PlayPickGo_MatchupScreen.MATCHUPS_CARD_LEGEND != null)
                    legendName = PlayPickGo_MatchupScreen.MATCHUPS_CARD_LEGEND;
                if (PlayPickGo_MatchupScreen.MATCHUPS_CARD_DATE != null)
                    matchupDate = PlayPickGo_MatchupScreen.MATCHUPS_CARD_DATE;

            } else if(pp.equalsIgnoreCase("WIN")){
                if (Matchup_NewWinScreen.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(Matchup_NewWinScreen.MATCHUPS_JSON);
                if (Matchup_NewWinScreen.MATCHUPS_CARDAMOUNT != null)
                    payAmount = Integer.parseInt(Matchup_NewWinScreen.MATCHUPS_CARDAMOUNT);
                if (Matchup_NewWinScreen.MATCHUPS_CARDTITLE != null)
                    wagerName = Matchup_NewWinScreen.MATCHUPS_CARDTITLE;
                if (Matchup_NewWinScreen.MATCHUPS_CARD_LEGEND != null)
                    legendName = Matchup_NewWinScreen.MATCHUPS_CARD_LEGEND;
                if (Matchup_NewWinScreen.MATCHUPS_CARD_DATE != null)
                    matchupDate = Matchup_NewWinScreen.MATCHUPS_CARD_DATE;
            }else if(pp.equalsIgnoreCase("MT")){
                if (Matchup_WinPlayGoTimeTwo.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(Matchup_WinPlayGoTimeTwo.MATCHUPS_JSON);
                if (Matchup_WinPlayGoTimeTwo.MATCHUPS_CARDAMOUNT != null)
                    payAmount = Integer.parseInt(Matchup_WinPlayGoTimeTwo.MATCHUPS_CARDAMOUNT);
                if (Matchup_WinPlayGoTimeTwo.MATCHUPS_CARDTITLE != null)
                    wagerName = Matchup_WinPlayGoTimeTwo.MATCHUPS_CARDTITLE;
                if (Matchup_WinPlayGoTimeTwo.MATCHUPS_CARD_LEGEND != null)
                    legendName = Matchup_WinPlayGoTimeTwo.MATCHUPS_CARD_LEGEND;
                if (Matchup_WinPlayGoTimeTwo.MATCHUPS_CARD_DATE != null)
                    matchupDate = Matchup_WinPlayGoTimeTwo.MATCHUPS_CARD_DATE;
            }else if(pp.equalsIgnoreCase("MP")){
                if (MatchupScreen_PlayAPick.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(MatchupScreen_PlayAPick.MATCHUPS_JSON);
                if (MatchupScreen_PlayAPick.MATCHUPS_CARDAMOUNT != null)
                    payAmount = Integer.parseInt(MatchupScreen_PlayAPick.MATCHUPS_CARDAMOUNT);
                if (MatchupScreen_PlayAPick.MATCHUPS_CARDTITLE != null)
                    wagerName = MatchupScreen_PlayAPick.MATCHUPS_CARDTITLE;
                if (MatchupScreen_PlayAPick.MATCHUPS_CARD_LEGEND != null)
                    legendName = MatchupScreen_PlayAPick.MATCHUPS_CARD_LEGEND;
                if (MatchupScreen_PlayAPick.MATCHUPS_CARD_DATE != null)
                    matchupDate = MatchupScreen_PlayAPick.MATCHUPS_CARD_DATE;
            }else {
                if (MatchupScreen.MATCHUPS_JSON != null)
                    jsonObj_card = new JSONObject(MatchupScreen.MATCHUPS_JSON);
                if (MatchupScreen.MATCHUPS_CARDAMOUNT != null)
                    payAmount = Integer.parseInt(MatchupScreen.MATCHUPS_CARDAMOUNT);
                if (MatchupScreen.MATCHUPS_CARDTITLE != null)
                    wagerName = MatchupScreen.MATCHUPS_CARDTITLE;
                if (MatchupScreen.MATCHUPS_CARD_LEGEND != null)
                    legendName = MatchupScreen.MATCHUPS_CARD_LEGEND;
                if (MatchupScreen.MATCHUPS_CARD_DATE != null)
                    matchupDate = MatchupScreen.MATCHUPS_CARD_DATE;

                    sp_c = MatchupScreen.support_cash;
                    sp_t = MatchupScreen.support_token;


            }


//            if (MatchupScreen_false.MATCHUPS_JSON != null)
//                jsonObj_card = new JSONObject(MatchupScreen_false.MATCHUPS_JSON);
//            if (MatchupScreen_false.MATCHUPS_CARDAMOUNT != null)
//                payAmount = Integer.parseInt(MatchupScreen_false.MATCHUPS_CARDAMOUNT);
//            if (MatchupScreen_false.MATCHUPS_CARDTITLE != null)
//                wagerName = MatchupScreen_false.MATCHUPS_CARDTITLE;
//            if (MatchupScreen_false.MATCHUPS_CARD_LEGEND != null)
//                legendName = MatchupScreen_false.MATCHUPS_CARD_LEGEND;
//            if (MatchupScreen_false.MATCHUPS_CARD_DATE != null)
//                matchupDate = MatchupScreen_false.MATCHUPS_CARD_DATE;

            Log.e("Json-----303", jsonObj_card + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mMixpanel = MixpanelAPI.getInstance(CheckoutActivity.this, getString(R.string.test_MIX_PANEL_TOKEN));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("card_guest"))
                card_id = bundle.getString("card_guest");
        }
        getDBInfo();
        //Get user cards list
        getAllCardsList();
        setPageData();
        //Set Wager Name
        if (wagerName != null && !wagerName.equals("")) {
            tvWagerName.setText(wagerName);
        }
    }

    TextView toolbar_title_x;
    @SuppressLint({"SetTextI18n", "ResourceType"})
    private void init() {

        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        tvWagerName = findViewById(R.id.tvWagerName);
        tvAmount = findViewById(R.id.tvAmount);
        deduct_amt = findViewById(R.id.deduct_amt);
        remaining_bal = findViewById(R.id.remaining_bal);
        llAddFunds = findViewById(R.id.llAddFunds);
        rl_add_new_card = findViewById(R.id.rl_add_new_card);
        tvPlay = findViewById(R.id.tvPlay);
        after_promo = findViewById(R.id.after_promo);
        click_promo = findViewById(R.id.click_promo);
        promo_txt = findViewById(R.id.promo_txt);
        close_img = findViewById(R.id.promo_close);
        llPayWith = findViewById(R.id.llPayWith);
        llCashAmount = findViewById(R.id.llCashAmount);
        tvAddFunds = findViewById(R.id.tvAddFunds);
        tvTokenInsufficient = findViewById(R.id.tvTokenInsufficient);
        rlAddFundsToSwitchCash = findViewById(R.id.rlAddFundsToSwitchCash);
        TextView tvChange = findViewById(R.id.tvChange);
        llDeductTokens = findViewById(R.id.llDeductTokens);
        tvDeductTokens = findViewById(R.id.tvDeductTokens);
        tvFaq = findViewById(R.id.tvFaq);
        llPlay = findViewById(R.id.llPlay);
        llPaypal = findViewById(R.id.llPayPal);

        rvCardsList = findViewById(R.id.rvCardsList);
        LinearLayoutManager llm = new LinearLayoutManager(CheckoutActivity.this);
        rvCardsList.setLayoutManager(llm);
        rvCardsList.setItemAnimator(null);
        rvCardsList.setNestedScrollingEnabled(false);
        recycleAdapter = new CardsListAdapter(cardsResponse, CheckoutActivity.this);
        rvCardsList.setAdapter(recycleAdapter);

        tvRemainBal = findViewById(R.id.tvRemainBal);


        toolbar_title_x.setText(getString(R.string._checkout));
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        rl_add_new_card.setOnClickListener(this);
        llAddFunds.setOnClickListener(this);
        tvPlay.setOnClickListener(this);
        click_promo.setOnClickListener(this);
        after_promo.setOnClickListener(this);
        close_img.setOnClickListener(this);
        tvAddFunds.setOnClickListener(this);
        promo_txt.addTextChangedListener(this);
        rlAddFundsToSwitchCash.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvFaq.setOnClickListener(this);
        llPaypal.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    private void setPageData() {
        //Handle cash or tokens
        tvRemainBal.setText("Balance to Pay");
        if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // If cash

            if(sp_c==false && sp_t==true){

                llAddFunds.setVisibility(View.GONE);
                llPayWith.setVisibility(View.GONE);
                rlAddFundsToSwitchCash.setVisibility(View.GONE);
                deduct_amt.setVisibility(View.GONE);

                tvAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm, 0, 0, 0);
                tvAmount.setText("" + payAmount);

                double data = Double.parseDouble(AMOUNT_TOKEN);
                // convert into int
//            double value = (int)data;
                double availAmount = data;// Integer.parseInt(AMOUNT_TOKEN);
                diffAmount = payAmount - availAmount;

                if (payAmount > availAmount) {
                    tvTokenInsufficient.setVisibility(View.VISIBLE);
                    llCashAmount.setVisibility(View.VISIBLE);
                    llPayWith.setVisibility(View.GONE);
                    llDeductTokens.setVisibility(View.VISIBLE);
                    tvDeductTokens.setText("" + availAmount);
                    remaining_bal.setText("" + String.format ("%.0f", diffAmount));
//                    remaining_bal.setText("" + diffAmount);
                    remaining_bal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_red, 0, 0, 0);
                    //Disable play button
                    //Enable when user selects a card
                    llPlay.setEnabled(false);
                    llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                    tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                    llPlay.setClickable(false);

                    showAlertBox(CheckoutActivity.this, "You don’t have enough balance to play. New tokens will be added in 24hrs", "");
                } else {

                    double data1 = Double.parseDouble(AMOUNT_TOKEN);
//                // convert into int
                    double value1 = (int)data1;
//                int availAmount = value;// Integer.parseInt(AMOUNT_TOKEN);
                    diffAmount = payAmount - availAmount;

                    double totalAvailAmount = value1;//Integer.parseInt(AMOUNT_TOKEN);
                    diffAmount = totalAvailAmount - payAmount;

                    tvTokenInsufficient.setVisibility(View.GONE);
                    llCashAmount.setVisibility(View.VISIBLE);
                    llDeductTokens.setVisibility(View.VISIBLE);
                    tvRemainBal.setText("Your remaining balance");
                    tvDeductTokens.setText("" + payAmount);
                    remaining_bal.setText("" +  String.format ("%.0f", diffAmount));

//                    remaining_bal.setText("" + diffAmount);
                    remaining_bal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_red, 0, 0, 0);
                    llPayWith.setVisibility(View.GONE);
                    //Enable Play button
                    llPlay.setEnabled(true);
                    llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                    tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                }
            }else{
                llAddFunds.setVisibility(View.VISIBLE);
                rlAddFundsToSwitchCash.setVisibility(View.GONE);
                tvTokenInsufficient.setVisibility(View.GONE);
                deduct_amt.setVisibility(View.VISIBLE);
                llDeductTokens.setVisibility(View.GONE);

                tvAmount.setText("$" + payAmount);

//            dfkhnbfdg

                double data1 = Double.parseDouble(AMOUNT_CASH);
//                // convert into int
//            int value1 = (int)data1;
                double availAmount = data1;//Integer.parseInt(AMOUNT_CASH);
                diffAmount = payAmount - availAmount;

                if (payAmount > availAmount) {
                    llCashAmount.setVisibility(View.VISIBLE);
                    llPayWith.setVisibility(View.VISIBLE);
                    deduct_amt.setText("- $" + availAmount);
                    remaining_bal.setText("$" + diffAmount);
                    //Disable play button
                    //Enable when user selects a card
                    llPlay.setEnabled(false);
                    llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                    tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                } else {
                    double data2 = Double.parseDouble(AMOUNT_CASH);
//                // convert into int
//                double value2 = (int)data2;
                    double totalAvailAmount =data2;// Integer.parseInt(AMOUNT_CASH);
                    diffAmount = totalAvailAmount - payAmount;

                    llCashAmount.setVisibility(View.VISIBLE);
                    llPayWith.setVisibility(View.VISIBLE);

                    tvRemainBal.setText("Your remaining balance");
                    deduct_amt.setText("- $" + payAmount);
//                    remaining_bal.setText("$" + diffAmount);
                    remaining_bal.setText("$" +  String.format ("%.0f", diffAmount));

                    //Enable Play button
                    llPlay.setEnabled(true);
                    llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                    tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

                }
            }



        } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { // if tokens

            llAddFunds.setVisibility(View.GONE);
            llPayWith.setVisibility(View.GONE);
            rlAddFundsToSwitchCash.setVisibility(View.VISIBLE);
            deduct_amt.setVisibility(View.GONE);

            tvAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm, 0, 0, 0);
            tvAmount.setText("" + payAmount);

            double data = Double.parseDouble(AMOUNT_TOKEN);
            // convert into int
//            double value = (int)data;
            double availAmount = data;// Integer.parseInt(AMOUNT_TOKEN);
            diffAmount = payAmount - availAmount;

            if (payAmount > availAmount) {
                tvTokenInsufficient.setVisibility(View.VISIBLE);
                llCashAmount.setVisibility(View.VISIBLE);
                llPayWith.setVisibility(View.GONE);
                llDeductTokens.setVisibility(View.VISIBLE);
                tvDeductTokens.setText("" + availAmount);
                remaining_bal.setText("" + diffAmount);
                remaining_bal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_red, 0, 0, 0);
                //Disable play button
                //Enable when user selects a card
                llPlay.setEnabled(false);
                llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                llPlay.setClickable(false);

                showAlertBox(CheckoutActivity.this, "You don’t have enough balance to play. New tokens will be added in 24hrs", "");
            } else {

                double data1 = Double.parseDouble(AMOUNT_TOKEN);
//                // convert into int
                double value1 = (int)data1;
//                int availAmount = value;// Integer.parseInt(AMOUNT_TOKEN);
                diffAmount = payAmount - availAmount;

                double totalAvailAmount = value1;//Integer.parseInt(AMOUNT_TOKEN);
                diffAmount = totalAvailAmount - payAmount;

                tvTokenInsufficient.setVisibility(View.GONE);
                llCashAmount.setVisibility(View.VISIBLE);
                llDeductTokens.setVisibility(View.VISIBLE);
                tvRemainBal.setText("Your remaining balance");
                tvDeductTokens.setText("" + payAmount);
                remaining_bal.setText("" +  String.format ("%.0f", diffAmount));
//                remaining_bal.setText("" + diffAmount);
                remaining_bal.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_red, 0, 0, 0);
                llPayWith.setVisibility(View.GONE);
                //Enable Play button
                llPlay.setEnabled(true);
                llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            }

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
                             /*   CardDataPojo cardData = new CardDataPojo();
                                cardData.set_id(jb.getString("_id"));
                                cardData.setAccount(jb.getString("account"));
                                cardData.setToken(jb.getString("fdToken"));
                                cardData.setAuthorizeNetCustomerProfileId(jb.getString("authorizeNetCustomerProfileId"));
                                cardData.setAuthorizeNetCustomerPaymentProfileId(jb.getString("authorizeNetCustomerPaymentProfileId"));
                                cardData.setLastFourDigits(jb.getString("lastFourDigits"));
                                cardData.setExpiry(jb.getString("expiry"));
                                cardData.setCardType(jb.getString("cardType"));
                                cardData.setCreatedAt(jb.getString("createdAt"));*/
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
                        Utilities.showToast(CheckoutActivity.this, error.getErrorBody());
                    }
                });

    }


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
                Intent addFundsIntent_1 = new Intent(CheckoutActivity.this, AddFunds.class);
                addFundsIntent_1.putExtra("customAmount", payAmount);
                addFundsIntent_1.putExtra("fromPage", AppConstants.CHECKOUT);
                addFundsIntent_1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(addFundsIntent_1, ADD_FUNDS);

                break;
            case R.id.tvAddFunds:

                Intent addFundsIntent = new Intent(CheckoutActivity.this, AddFunds.class);
                addFundsIntent.putExtra("customAmount", payAmount);
                addFundsIntent.putExtra("fromPage", AppConstants.CHECKOUT);
                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(addFundsIntent, ADD_FUNDS);

                break;
            case R.id.rlAddFundsToSwitchCash:
                getTokenFromCash();
                break;
            case R.id.tvPlay:
                //Add service call for purchase card




                try {
//                    jsonObj_card.put("creditCardId", selCardId);
                    if(selCardId.isEmpty()){

                        jsonObj_card.put("balanceToPay", "0");
                    }else{
                        jsonObj_card.put("balanceToPay", payAmount);

                    }
//                    jsonObj_card.put("balanceToPay", diffAmount);
                    //Add service call for purchase card


                    if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // If cash user

                        if(sp_c==true && sp_t==false) {
                            jsonObj_card.put("currencyType", "cash");
                        }else{
                            jsonObj_card.put("currencyType", "token");
                        }

                        if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                            if (Utilities.getAge(DATA_DOB) >= 18) {

                                if (progressDialog != null)
                                    progressDialog.show();
                                purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);
                                Log.e("jsonObj_card--> cash", String.valueOf(jsonObj_card));


                            } else {
                                showAlertBox(CheckoutActivity.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                            }


                        } else {
                            Utilities.showAlertBoxTrans(CheckoutActivity.this, getString(R.string.age_to_cash_title), getString(R.string.token_to_cash_msg));
                        }


                    } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { //if tokens user

                        if (progressDialog != null)
                            progressDialog.show();
                        jsonObj_card.put("currencyType", "token");
                        purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);
                        Log.e("jsonObj_card", String.valueOf(jsonObj_card));


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.rl_add_new_card:
                if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { // If tokens
                    showAlertBoxTwo(CheckoutActivity.this, "Adding funds will switch to cash mode.", "You will lose all tokens and will play with for cash from now on. You can not switch back to tokens.");
                } else {
//                    pp = bundle.getString("place_p");
                    Intent addNewCardIntent = new Intent(CheckoutActivity.this, AddNewCard.class);
                    addNewCardIntent.putExtra("AMOUNT", diffAmount);
                    addNewCardIntent.putExtra("place_p", pp);
//                    pp = bundle.getString("place_p");
                    addNewCardIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    addNewCardIntent.putExtra("fromPage", AppConstants.CHECKOUT);
                    startActivityForResult(addNewCardIntent, ADD_NEW_CARD);
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
                break;
            case R.id.tvChange:
                finish();
                break;
            case R.id.tvFaq:
                Intent webIntent = new Intent(CheckoutActivity.this, WebScreens.class);
                webIntent.putExtra("title", AppConstants.FAQS);
                startActivity(webIntent);
                break;
            case R.id.llPayPal:
                //setupBraintreeAndStartExpressCheckout();

                double data2 = Double.parseDouble(AMOUNT_CASH);
//                // convert into int
//                int value2 = (int)data2;
                double t_balance = data2;//Integer.parseInt(AMOUNT_CASH);

                Log.e("b----------", "ct-----" + t_balance);
                Log.e("b----------", "patcc-----" + payAmount);

                if (t_balance >= payAmount) {
                    Log.e("b----------", "ct-->---" + payAmount);
                    paypay_amt_1 = payAmount;
                    setupPayPal(paypay_amt_1);
                } else if (t_balance < payAmount) {
                    Log.e("b----------", "ct--<---" + payAmount);

                    double bb = t_balance - payAmount;
                    Log.e("b----------", "ct--<---" + bb);

                    double kk = -(bb);
                    paypay_amt_1 = kk;
                    Log.e("b----------", "ct-kk-<---" + kk);
                    setupPayPal(paypay_amt_1);
                }
//                tvAmount.setText("$" + payAmount);
//                int availAmount = Integer.parseInt(AMOUNT_CASH);
//                diffAmount = payAmount - availAmount;


                break;
            default:
                break;

        }
    }


    private void purchaseCardApi(JSONObject jsonObj_card, final String matchupDate, final String legendName, final String wagerName) {


        Log.e("784-- check--",jsonObj_card.toString());
        if (Utilities.isNetworkAvailable(getApplicationContext())) {

            AndroidNetworking.post(APIs.PURCHASE_CARD)
                    .addJSONObjectBody(jsonObj_card) // posting json
                    .addHeaders("Content-Type", "application/json")
//                    .addHeaders("sessionToken", sessionToken)
                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("code", String.valueOf(response.hashCode()));
                            Log.e("Purchase Crda:: ", response.toString());
                            if (progressDialog != null)
                                progressDialog.dismiss();

                            JSONObject props = new JSONObject();
                            try {
                                String b = response.getString("isFirst");
                                props.put("isFirstPurchase ", null);
                                props.put("price ", payAmount);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mMixpanel.track("Purchase Complete", props);

                            trackEvent("Purchase Complete",props);

                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                            cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                            cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                            cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                            mydb.updateUser(cv);


                            footBallDialog(matchupDate, legendName, wagerName);
                        }

                        @Override
                        public void onError(ANError anError) {

                            if (progressDialog != null)
                                progressDialog.dismiss();
                            if (anError.getErrorCode() != 0) {
                                Log.e("", "onError errorCode : " + anError.getErrorCode());
                                Log.e("", "onError errorBody : " + anError.getErrorBody());
                                Log.e("", "onError errorDetail : " + anError.getErrorDetail());


                                try {

                                    JSONObject ej = new JSONObject(anError.getErrorBody());
                                    Utilities.showToast(CheckoutActivity.this, ej.getString("message"));

                                } catch (Exception e) {

                                }

//                                Utilities.showToast(CheckoutActivity.this, "Something went wrong,Please try again later.");
//                                Utilities.showToast(CheckoutActivity.this, "Cash Location, Cant play with token");

                            } else {
                                Log.e("", "onError errorDetail  0: " + anError.getErrorDetail());
                            }

                        }
                    });
        } else {
            Utilities.showNoInternetAlert(CheckoutActivity.this);

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Toast.makeText(CheckoutActivity.this, "1---"+requestCode +"-----"+resultCode+"------"+data.toString(), Toast.LENGTH_LONG).show();

        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == ADD_NEW_CARD || requestCode == EDIT_CARD) {
                    Log.e("HEy called Back", "FRom Add CARD");
                }
            }

            //Toast.makeText(CheckoutActivity.this, "1---", Toast.LENGTH_LONG).show();
            Log.e("PayPal Test", "3---AR-----------");

            if (requestCode == REQUEST_CODE_PAYMENT) {
                Log.e("PayPal Test", "4--------------");

                if (resultCode == Activity.RESULT_OK) {
                    Log.e("PayPal Test", "6--------------");
                    //Toast.makeText(CheckoutActivity.this, "2---", Toast.LENGTH_LONG).show();
                    PaymentConfirmation confirm = data
                            .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);


                    Log.e("PayPal Test", "6-------tconfirm-------" + confirm);
                    Log.e("PayPal Test", "6-------tdata-------" + data);
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
                            if (progressDialog != null)
                                progressDialog.show();
                            sendNonceToServer(res_k.getString("id"));


                            /*paypalResponse = new Gson().fromJson(confirm.toJSONObject().toString(4), PayPalServerResponse.class);
                            Log.e("PayPal Test", "7------paypalResponse--------"+paypalResponse);
                            if (paypalResponse != null) {
                                //PayPal_Invoice_id =paypalResponse.getResponse().getId();
                                //paypal_state=paypalResponse.getResponse().getState();
                                //Toast.makeText(CheckoutActivity.this, "4---", Toast.LENGTH_LONG).show();
                                Log.e("PayPal Test", "8--------------"+ paypalResponse.getResponse().getId());

//                                //Toast.makeText(CheckoutActivity.this, "INVOICE::" + paypalResponse.getResponse().getId(), Toast.LENGTH_LONG).show();
                                sendNonceToServer(paypalResponse.getResponse().getId());
                            }

                            */
                        } catch (JSONException e) {
                            //Toast.makeText(CheckoutActivity.this, "ERROR::" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("PayPal Test", "9--------------");

                        }
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    System.out.println("The user canceled.");
                    Toast.makeText(CheckoutActivity.this, "The user canceled.", Toast.LENGTH_LONG).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                    Toast.makeText(CheckoutActivity.this, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
                Log.e("PayPal Test", "5--------------");
                //Toast.makeText(CheckoutActivity.this, "10---", Toast.LENGTH_LONG).show();
                if (resultCode == Activity.RESULT_OK) {
                    PayPalAuthorization auth = data
                            .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                    Log.e("PayPal Test", "10--------------" + data.toString());
                    Log.e("PayPal Test", "1011--------------" + auth.toString());
                    //Toast.makeText(CheckoutActivity.this, "11---", Toast.LENGTH_LONG).show();
                    if (auth != null) {

                        try {
                            Toast.makeText(getApplicationContext(),
                                    "Future Payment code received from PayPal",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("PayPal Test", "11--------------");
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
//            Toast.makeText(CheckoutActivity.this, "Try Again..!",
//                    Toast.LENGTH_SHORT).show();
            Log.e("PayPal Test", "102--------------");
            //Toast.makeText(CheckoutActivity.this, "12---", Toast.LENGTH_LONG).show();
            //  Toast.makeText(CheckoutActivity.this, "12---"+ex.toString(), Toast.LENGTH_LONG).show();
        }

    }


    public static void showAlertBoxTwo(Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alerts_two);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().setLayout(((Utilities.getWidth(context) / 100) * 94), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
            dialog.setCancelable(false);
        }

        TextView alert_title = dialog.findViewById(R.id.alert_title);
        TextView alert_msg = dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView alert_cancel = dialog.findViewById(R.id.alert_cancel);
        alert_cancel.setText("CANCEL");

        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
//            if (cardType.equalsIgnoreCase("master")) {
//                holder.ivCard.setImageResource(R.drawable.ic_cc_mastercard);
//                holder.tvCardName.setText("Mastercard* " + cardsResponse.get(position).getLastFourDigits());
//            } else if (cardType.equalsIgnoreCase("visa")) {
//                holder.ivCard.setImageResource(R.drawable.ic_cc_visa);
//                holder.tvCardName.setText("Visa* " + cardsResponse.get(position).getLastFourDigits());
//            } else if (cardType.equalsIgnoreCase("JCB")) {
//                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
//                holder.tvCardName.setText("JCB* " + cardsResponse.get(position).getLastFourDigits());
//            } else if (cardType.equalsIgnoreCase("Discover")) {
//                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
//                holder.tvCardName.setText("Discover* " + cardsResponse.get(position).getLastFourDigits());
//            } else {
//                holder.ivCard.setImageResource(R.drawable.ic_cc_generic);
//                holder.tvCardName.setText("test* " + cardsResponse.get(position).getLastFourDigits());
//            }
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
                    Context wrapper = new ContextThemeWrapper(CheckoutActivity.this, R.style.popupMenuStyle1);
                    PopupMenu popup = new PopupMenu(wrapper, view);
                    popup.setOnMenuItemClickListener(CheckoutActivity.this);
                    popup.inflate(R.menu.cc_card_menu);
                    popup.show();
                }
            });

            if (selPos == position) {
                holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_highlight, null));
                holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            } else {
                holder.llCard.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.card_sel_normal, null));
                holder.tvCardName.setTextColor(ResourcesCompat.getColor(getResources(), R.color.header, null));
            }

            holder.llCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selCardId = cardsResponse.get(position).get_id();


                    if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { // If tokens

                        showAlertBoxTwo(CheckoutActivity.this, "Adding funds will switch to cash mode.", "You will lose all tokens and will play with for cash from now on. You can not switch back to tokens.");
                    } else if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // if  cash
                        selPos = position;
                        notifyDataSetChanged();
                        llPlay.setClickable(true);
                        llPlay.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                        tvPlay.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    }

                   /* if(etCustomAmt.getText().toString().trim().length()>0){
                        llAddFunds.setClickable(true);
                        if(customAmount>0){
                            llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                        }else {
                            llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
                        }
                        tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    }else{
                        llAddFunds.setClickable(true);
                        llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_gray_bg, null));
                        tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                    }*/
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

    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.edit_card:
                Intent editCardIntent = new Intent(CheckoutActivity.this, EditCreditCard.class);
                editCardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                editCardIntent.putExtra("CARD_ID", selCardId);
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

    private void DeleteCreditCard(JSONObject jsonObject) {
        Log.e("DeleteCard::REQUEST::", jsonObject.toString());
        AndroidNetworking.delete(APIs.DELETE_CARD_URL)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
//                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

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
//                Intent intent = new Intent(CheckoutActivity.this, CardSFrag.class);
//                CheckoutActivity.this.startActivity(intent);
//
                Intent intent_ = new Intent(CheckoutActivity.this, Dashboard.class);
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
            case "PRORODEO":
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.rodeo));
                break;
            default:
                alert_legendname.setImageDrawable(getResources().getDrawable(R.drawable.rodeo));
                break;
        }
    }


    public String getJson() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.state_rules);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    //Tokens
    void obj_list_token(String state_txt) {

        try {
            JSONArray array = new JSONArray(getJson());
            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                String State = object.getString("State");
                if (State.equalsIgnoreCase(state_txt)) {
                    String cashValue = object.getString("Cash");
                    System.out.println(cashValue);

                    if (cashValue.equalsIgnoreCase("YES")) {
                        if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                            if (Utilities.getAge(DATA_DOB) >= 18) {
                                Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

//                                getFinalLocationChekup();
                                Intent addFundsIntent = new Intent(CheckoutActivity.this, AddFunds.class);
                                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(addFundsIntent);
                            } else {
                                showAlertBox(CheckoutActivity.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                            }


                        } else {
                            Utilities.showAlertBoxTrans(CheckoutActivity.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                        }
//                        showAlertBox(CheckoutActivity.this, getString(R.string.token_to_cash_title) , getString(R.string.token_to_cash_msg));
                    } else {
                        showAlertBox(CheckoutActivity.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFinalLocationChekup() {
        {


            //  if (Helper.isGPSEnabled(MainActivity.this)) {
            LocationTrack locationTrack = new LocationTrack(CheckoutActivity.this);
//            if (locationTrack.canGetLocation) {
//                double lat = locationTrack.getLatitude();
//                double lon = locationTrack.getLongitude();

            getLocationFu(CheckoutActivity.this);
            double lat = llat;
            double lon = llong;
                try {
                    Geocoder gcd = new Geocoder(CheckoutActivity.this, Locale.getDefault());
                    List<Address> addresses = gcd.getFromLocation(lat,
                            lon, 1);

                    if (addresses.size() > 0) {

                        final String state_txt = addresses.get(0).getAdminArea();
                        final String city_txt = addresses.get(0).getLocality();
                        final String country_txt = addresses.get(0).getCountryName();
                        {


                            JSONObject jsonObj = new JSONObject();

                            try {
                                jsonObj.put("city", city_txt);
                                jsonObj.put("stateName", state_txt);
                                jsonObj.put("stateCode", "");
                                if (country_txt.equalsIgnoreCase("United States")) {
                                    jsonObj.put("country", "USA");
                                } else {
                                    jsonObj.put("country", country_txt);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            AndroidNetworking.post(APIs.LOCATION_USER_VAL)
                                    .addJSONObjectBody(jsonObj) // posting json
//                                    .addHeaders("sessionToken", sessionToken)
                                    .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                                    .setPriority(Priority.MEDIUM)
                                    .build()
                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                        @Override
                                        public void onResponse(JSONObject response) {


                                            Log.e("***MA: Token:", response.toString());

                                            try {
                                                String Usermode = response.getString("userPlayMode");

                                                if (Usermode.equalsIgnoreCase("cash")) {
                                                    Intent addFundsIntent = new Intent(CheckoutActivity.this, AddFunds.class);
                                                    addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                    startActivity(addFundsIntent);

                                                } else {
                                                    showAlertBox(CheckoutActivity.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(ANError anError) {
                                            Log.e("js", "Login----error-------" + anError);


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
//
                    } else {
                        Log.e("test--", "enable loction");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            checkLocationPermission(getApplicationContext(), CheckoutActivity.this);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
         //   }


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

    private void getTokenFromCash() {

        try {
            Dexter.withContext(CheckoutActivity.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            LocationTrack locationTrack = new LocationTrack(CheckoutActivity.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(CheckoutActivity.this);
                            double lat = llat;
                            double lon = llong;
                                try {
                                    Geocoder gcd = new Geocoder(CheckoutActivity.this, Locale.getDefault());
                                    List<Address> addresses = gcd.getFromLocation(lat,
                                            lon, 1);

                                    if (addresses.size() > 0) {

                                        final String state_txt = addresses.get(0).getAdminArea();
                                        final String city_txt = addresses.get(0).getLocality();
                                        final String country_txt = addresses.get(0).getCountryName();

                                        {

                                            JSONObject jsonObj = new JSONObject();

                                            try {
                                                jsonObj.put("city", city_txt);
                                                jsonObj.put("stateName", state_txt);
                                                jsonObj.put("stateCode", "");
                                                jsonObj.put("country", country_txt);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


//                                            obj_list_token(state_txt);
                                            if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                                                if (Utilities.getAge(DATA_DOB) >= 18) {
                                                    Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

                                                    getFinalLocationChekup();

                                                } else {
                                                    showAlertBox(CheckoutActivity.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                                                }


                                            } else {
                                                Utilities.showAlertBoxTrans(CheckoutActivity.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                                            }


                                        }
//
                                    } else {
                                        Log.e("test--", "enable loction");
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            checkLocationPermission(getApplicationContext(), CheckoutActivity.this);

                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                         //   }

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(CheckoutActivity.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }

                    }).check();

            // finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* public void setupBraintreeAndStartExpressCheckout() {
         PayPalRequest request = new PayPalRequest(String.valueOf(diffAmount))
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
             Utilities.showToast(CheckoutActivity.this, "Something went wrong! Please try after sometime ");
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
     }
 */
    private void sendNonceToServer(String payPalNonce) {
        //    Toast.makeText(CheckoutActivity.this, "5---"+payPalNonce, Toast.LENGTH_LONG).show();
        /*{Toast.makeText(CheckoutActivity.this, "3---", Toast.LENGTH_LONG).show();
	"transactionId": "",
	"amount": 20
}*/
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("transactionId", payPalNonce);
            jsonObject.put("amount", paypay_amt_1);
            jsonObject.put("notify", false);
//            jsonObject.put("amount", diffAmount);
            Log.e("payPalNonce::", jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(APIs.ADD_FUNDS_PAYPAL)
                .setPriority(Priority.HIGH)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
//                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            //    Toast.makeText(CheckoutActivity.this, "6---", Toast.LENGTH_LONG).show();
                            JSONObject props = new JSONObject();
                            props.put("isFirstDeposit", null);
//                            props.put("amount", diffAmount);
                            props.put("amount", paypay_amt_1);
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
                            myDbHelper.updateUser(cv);
                            getDBInfo();
                            setPageData();

                            if (progressDialog != null)
                                progressDialog.show();
                            if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // If cash user


                                jsonObj_card.put("currencyType", "cash");
                                purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);

                            } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) {

                                jsonObj_card.put("currencyType", "token");
                                purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);
                            }
                            //       purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);

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
                        Utilities.showToast(CheckoutActivity.this, anError.getErrorBody());
                        finish();

                        //   Toast.makeText(CheckoutActivity.this, "7---", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void setupPayPal(double payAmt_paypal) {
        Log.e("PayPal Test", "1--------------");
       /* String purchaseType="";
        if(tvAddFunds!=null&&tvAddFunds.getText().toString().equalsIgnoreCase("PLAY")){
            purchaseType="Purchase Card";
        }else{
            purchaseType="Add Funds";
        }*/
//            thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(1)), "USD",
        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(payAmt_paypal)), "USD",
//        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(diffAmount)), "USD",
                "Purchase Card", PayPalPayment.PAYMENT_INTENT_SALE);

        thingToBuy.enablePayPalShippingAddressesRetrieval(true);
        Log.e("PayPal Test", "22--------------" + thingToBuy);

        Intent intent = new Intent(CheckoutActivity.this,
                PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }
}
