package com.sport.playsqorr.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;


public class AddNewCard extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText etCardNumber, etExpiry, etCVV, etFullName, etStreet, etCity, etZipCode;
    private TextView tvAddCard;
    private Spinner spnrState;
    private ImageView ivCardType;
    private LinearLayout llAddNewCard;
    private String sessionToken, NEWTOKEN, cardType = "", customAmount, customPromo, fromPage, ROLE, AMOUNT_TOKEN, AMOUNT_CASH, PROMO_BAL, CASH_BAL, DATA_DOB;
    private CheckBox cbSaveCard;
    private DataBaseHelper myDbHelper;
    JSONObject jsonObj_card;

    private int payAmount = 0;

    private String wagerName, legendName, matchupDate;

    ProgressDialog progressDialog;

    MixpanelAPI mMixpanel;

    String pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
       /* SharedPreferences sharedPreferences = getSharedPreferences("SESSION_TOKEN", MODE_PRIVATE);
        sessionToken= sharedPreferences.getString("token", "");*/

        myDbHelper = new DataBaseHelper(AddNewCard.this);
        progressDialog = new ProgressDialog(AddNewCard.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        getInfoFromLocalDB();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("AMOUNT")) {
                customAmount = bundle.getString("AMOUNT");
            }
            if (bundle.containsKey("PROMOC")) {
                customPromo = bundle.getString("PROMOC");
            }
            if (bundle.containsKey("fromPage"))
                fromPage = bundle.getString("fromPage");
            try {
                if (bundle.containsKey("cardjson"))
                    jsonObj_card = new JSONObject(getIntent().getStringExtra("cardjson"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

      //      Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey("place_p")) {
                    pp = bundle.getString("place_p");
                }else{
                    pp="";
                }
            }

            Log.e("Json-----", jsonObj_card + "");

        }

        init();
    }


    private void getInfoFromLocalDB() {
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));

                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                PROMO_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                DATA_DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
            }
            cursor.close();
        }
    }

    TextView toolbar_title_x;

    private void init() {
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiry = findViewById(R.id.etExpiry);
        etCVV = findViewById(R.id.etCVV);
        etFullName = findViewById(R.id.etFullName);
        etStreet = findViewById(R.id.etStreet);
        etCity = findViewById(R.id.etCity);
        spnrState = findViewById(R.id.spnrState);
        etZipCode = findViewById(R.id.etZipCode);
        ivCardType = findViewById(R.id.ivCardType);
        tvAddCard = findViewById(R.id.tvAddCard);
        llAddNewCard = findViewById(R.id.llAddNewCard);
        cbSaveCard = findViewById(R.id.cbSaveCard);

        toolbar_title_x.setText(getString(R.string.add_new_card));

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        etCardNumber.addTextChangedListener(this);
        //tvAddCard.setOnClickListener(this);
        llAddNewCard.setOnClickListener(this);
        etCVV.addTextChangedListener(this);
        etFullName.addTextChangedListener(this);
        etStreet.addTextChangedListener(this);
        etCity.addTextChangedListener(this);
        etZipCode.addTextChangedListener(this);
//

        if (fromPage != null && !fromPage.equals("")) {
            tvAddCard.setText("PLAY");
        } else {
            tvAddCard.setText("USE CARD");
        }

        String[] statesArray = getResources().getStringArray(R.array.states_array);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
        spnrState.setAdapter(courseAdapter);

        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String current = s.toString();
                if (current.length() == 2 && start == 1) {
                    etExpiry.setText(current + "/");
                    etExpiry.setSelection(current.length() + 1);
                } else if (current.length() == 2 && before == 1) {
                    current = current.substring(0, 1);
                    etExpiry.setText(current);
                    etExpiry.setSelection(current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        ArrayList<String> listOfPattern = new ArrayList<String>();

        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
        listOfPattern.add(ptDinClb);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
        listOfPattern.add(ptJcb);


        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // etCreditCardNumber.setFloatingLabel(MaterialEditText.FLOATING_LABEL_HIGHLIGHT);

                String initial = s.toString();
                // remove all non-digits characters
                String processed = initial.replaceAll("\\D", "");

                // insert a space after all groups of 4 digits that are followed by another digit
                processed = processed.replaceAll("(\\d{4})(?=\\d)(?=\\d)(?=\\d)", "$1 ");

                //Remove the listener
                etCardNumber.removeTextChangedListener(this);

                int index = etCardNumber.getSelectionEnd();

                if (index == 5 || index == 10 || index == 15)
                    if (count > before)
                        index++;
                    else
                        index--;

                //Assign processed text
                etCardNumber.setText(processed);

                try {
                    etCardNumber.setSelection(index);
                } catch (Exception e) {
                    e.printStackTrace();
                    etCardNumber.setSelection(s.length() - 1);
                }
                //Give back the listener
                etCardNumber.addTextChangedListener(this);

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String cardNumber = etCardNumber.getText().toString();
                if (cardNumber.startsWith("4")) //VISA
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_visa);
                    cardType = "Visa";
                } else if (cardNumber.startsWith("5")) //Mastercard
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_mastercard);
                    cardType = "Mastercard";//"Mastercard"
                } else if (cardNumber.startsWith("6")) //Discover
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "Discover";
                } else if (cardNumber.startsWith("2")) //enRoute
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "enRoute";
                } else if (cardNumber.startsWith("37") || cardNumber.startsWith("34")) //American Express
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
//                    cardType="AMEX";
                    cardType = "American Express";
                } else if (cardNumber.startsWith("30") || cardNumber.startsWith("36")) //Diners Club
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "Diners Club";
//                    cardType="Diners";
                } else if (cardNumber.startsWith("86")) //Voyager
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "Voyager";
                } else if (cardNumber.startsWith("35")) //else if (cardNumber.startsWith("2")) //JCB
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "JCB";
                } else //Default
                {
                    ivCardType.setImageResource(R.drawable.ic_cc_generic);
                    cardType = "";
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {


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

            } else {
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
            }
            Log.e("Json-----", jsonObj_card + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMixpanel = MixpanelAPI.getInstance(AddNewCard.this, getString(R.string.test_MIX_PANEL_TOKEN));


    }


    private boolean performValidations() {
        boolean isValid = false;
        Calendar calendar = Calendar.getInstance();
        //      int curYear = calendar.get(Calendar.YEAR);
        int curYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
        int curMonth = (calendar.get(Calendar.MONTH)) + 1;
        int userEnteredYear = Integer.parseInt(etExpiry.getText().toString().trim().split("/")[1]);
        int userEnteredMonth = Integer.parseInt(etExpiry.getText().toString().trim().split("/")[0]);

        if (userEnteredMonth > 12) {
            if (progressDialog != null)
                progressDialog.dismiss();
            Toast.makeText(AddNewCard.this, "Please enter valid month", Toast.LENGTH_SHORT).show();
        } else if (userEnteredYear < curYear) {
            if (progressDialog != null)
                progressDialog.dismiss();
            Toast.makeText(AddNewCard.this, "Please enter current or next year", Toast.LENGTH_SHORT).show();
        } else if (userEnteredMonth < curMonth && userEnteredYear <= curYear) {
            if (progressDialog != null)
                progressDialog.dismiss();
            Toast.makeText(AddNewCard.this, "Please enter a  valid expiry date", Toast.LENGTH_SHORT).show();
        } else {
            isValid = true;
        }
        return isValid;
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
            case R.id.llAddNewCard:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm2.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {

                    if (progressDialog != null)
                        progressDialog.show();

                    if (etCardNumber.getText().toString().trim().length() >= 1) {
                        String action = tvAddCard.getText().toString().trim();

                        if (performValidations()) {
                            if (ROLE != null && ROLE.equalsIgnoreCase("cash")) {
                                if (action.equalsIgnoreCase("PLAY")) {
                                    try {
                                        //Write purchase card api call here

                                        jsonObj_card.put("balanceToPay", customAmount);

                                        JSONObject cardInfoJson = new JSONObject();
                                        JSONObject balance_payment_json = new JSONObject();


                                        JSONObject cardinf= new JSONObject();
                                        cardinf.put("card_info",cardInfoJson);
                                        cardinf.put("pay_from","new card");




                                        String[] expiry = etExpiry.getText().toString().trim().split("/");
                                        cardInfoJson.put("cardType", cardType);
                                        cardInfoJson.put("cardNumber", etCardNumber.getText().toString().trim().replaceAll("\\s+", ""));
                                        cardInfoJson.put("securityCde", etCVV.getText().toString().trim());
                                        cardInfoJson.put("expiryMonth", expiry[0]);
                                        cardInfoJson.put("expiryYear", expiry[1]);
                                        cardInfoJson.put("fullName", etFullName.getText().toString().trim());
                                        cardInfoJson.put("billingAddress", etStreet.getText().toString().trim());
                                        cardInfoJson.put("city", etCity.getText().toString().trim());
                                        cardInfoJson.put("state", spnrState.getSelectedItem().toString());
                                        if (spnrState.getSelectedItemPosition() <= 50)
                                            cardInfoJson.put("country", "USA");
                                        else
                                            cardInfoJson.put("country", "CANADA");
                                        cardInfoJson.put("zipCode", etZipCode.getText().toString().trim());
                                        String saveCard;
                                        if (cbSaveCard.isChecked()) {
                                            saveCard = "true";
                                        } else {
                                            saveCard = "false";
                                        }

                                        cardInfoJson.put("saveCardForFuture", saveCard);
                                        balance_payment_json.put("balance_payment_info",cardinf);
                                        jsonObj_card.put("currencyType", "cash");
                                        jsonObj_card.put("balance_payment_json", cardinf);
//                                        jsonObj_card.put("cardInfo", cardInfoJson);
//                                        balance_payment_json.put("balance_payment_info",cardinf);

                                        Log.e("468---------------",jsonObj_card.toString());
                                        if (progressDialog != null)
                                            progressDialog.show();
                                        purchaseCardApi(jsonObj_card, matchupDate, legendName, wagerName);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("430 kalyan", e + "");
                                        if (progressDialog != null)
                                            progressDialog.dismiss();
                                    }
                                } else {
                                    buildRequest();
                                }
                            } else {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                showAlertBoxTwo(AddNewCard.this, getString(R.string.switch_to_cash_header), getString(R.string.switch_to_cash_msg));
                            }
                        }
                    } else {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                    }


                } else {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Utilities.showAlertBoxTrans(AddNewCard.this, getString(R.string.age_to_cash_title), getString(R.string.token_to_cash_msg));
                }


                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int card_length = etCardNumber.getText().toString().trim().length();
        int expLength = etExpiry.getText().toString().trim().length();
        int cvvLength = etCVV.getText().toString().trim().length();
        int nameLength = etFullName.getText().toString().trim().length();
        int streetLength = etStreet.getText().toString().trim().length();
        int cityLength = etCity.getText().toString().trim().length();
        int zipLength = etZipCode.getText().toString().trim().length();

        if (!cardType.equals("") && card_length > 14 && expLength > 4 && cvvLength == 3
                && nameLength > 0 && streetLength > 0 && cityLength > 0 && zipLength > 4) {
            llAddNewCard.setEnabled(true);
            if (fromPage != null && !fromPage.equals("")) {
                llAddNewCard.setBackgroundResource(R.drawable.btn_bg_red);
            } else {
                llAddNewCard.setBackgroundResource(R.drawable.btn_bg_green);
            }
            tvAddCard.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        } else {


            llAddNewCard.setEnabled(false);
            llAddNewCard.setBackgroundResource(R.drawable.btn_bg_grey);
            tvAddCard.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void buildRequest() {
        try {
            JSONObject jsonObject = new JSONObject();
            String[] expiry = etExpiry.getText().toString().trim().split("/");

            jsonObject.put("cardType", cardType);
            jsonObject.put("cardNumber", etCardNumber.getText().toString().trim().replaceAll("\\s+", ""));
            jsonObject.put("securityCde", etCVV.getText().toString().trim());
            jsonObject.put("expiryMonth", expiry[0]);
            jsonObject.put("expiryYear", expiry[1]);
            jsonObject.put("fullName", etFullName.getText().toString().trim());
            jsonObject.put("billingAddress", etStreet.getText().toString().trim());
            jsonObject.put("city", etCity.getText().toString().trim());
            jsonObject.put("state", spnrState.getSelectedItem().toString());
            if (spnrState.getSelectedItemPosition() <= 50)
                jsonObject.put("country", "USA");
            else
                jsonObject.put("country", "CANADA");
            jsonObject.put("zipCode", etZipCode.getText().toString().trim());
            jsonObject.put("amount", customAmount);
            jsonObject.put("promoCode", customPromo);
            String saveCard;
            if (cbSaveCard.isChecked()) {
                saveCard = "true";
            } else {
                saveCard = "false";
            }

            jsonObject.put("saveCardForFuture", saveCard);

            Log.e("537--kk", jsonObject.toString());

            SendDataToAddCard(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }


    private void SendDataToAddCard(JSONObject addCardObject) {
        Log.e("ADDCARD::REQESTOBJ", addCardObject.toString());
        AndroidNetworking.post(APIs.ADD_FUNDS_URL_WITH_CARD)
                .addJSONObjectBody(addCardObject) // posting json
                .addHeaders("Content-Type", "application/json")
//                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        try {
                            if (!response.toString().contains("The credit card number is invalid.")) {
                                WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);

                                ContentValues cv = new ContentValues();
                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                                cv.put(DB_Constants.USER_MODETYPE, withdrawResponse.getUserPlayMode());
                                myDbHelper.updateUser(cv);
                                Intent intent = new Intent();
                                //intent.putExtra("result",result);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                Utilities.showToast(AddNewCard.this, "The credit card number is invalid.");
                            }
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

                        if (anError.getErrorBody().contains("The credit card number is invalid.")) {
                            Utilities.showToast(AddNewCard.this, "The credit card number is invalid.");
                        } else {
//                            Utilities.showToast(AddNewCard.this,anError.getErrorBody());
                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
                                Utilities.showToast(AddNewCard.this, ej.getString("message"));

                            } catch (Exception e) {

                            }
                        }

                        if (anError.getErrorCode() != 0) {
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());

//                            try{
//                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                                Utilities.showToast(AddNewCard.this, ej.getString("message"));
//
//                            }catch (Exception e){
//
//                            }

                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });

    }


    private void purchaseCardApi(JSONObject jsonObj_card, final String matchupDate, final String legendName, final String wagerName) {

        if (Utilities.isNetworkAvailable(getApplicationContext())) {

            AndroidNetworking.post(APIs.PURCHASE_CARD)
                    .addJSONObjectBody(jsonObj_card) // posting json
                    .addHeaders("Content-Type", "application/json")
//                    .addHeaders("sessionToken", sessionToken)
                    .addHeaders("Authorization", "bearer " + NEWTOKEN)

                    .setPriority(Priority.IMMEDIATE)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("Purchase Card:: ", response.toString());

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

                            trackEvent("Purchase Complete", props);

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

                            if (progressDialog != null)
                                progressDialog.dismiss();
                            if (anError.getErrorCode() != 0) {
                                Log.e("", "onError errorCode : " + anError.getErrorCode());
                                Log.e("", "onError errorBody : " + anError.getErrorBody());
                                Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                                //       Utilities.showToast(AddNewCard.this, anError.getErrorBody());
                                try {
                                    JSONObject ej = new JSONObject(anError.getErrorBody());
                                    Utilities.showToast(AddNewCard.this, ej.getString("message"));

                                } catch (Exception e) {

                                }
                            } else {
                                Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                            }

                        }
                    });
        } else {
            if (progressDialog != null)
                progressDialog.dismiss();
            Utilities.showNoInternetAlert(AddNewCard.this);

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
//                Intent intent = new Intent(CheckoutActivity.this, CardSFrag.class);
//                CheckoutActivity.this.startActivity(intent);

                Intent intent_ = new Intent(AddNewCard.this, Dashboard.class);
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

                        showAlertBox(AddNewCard.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                    } else {
                        showAlertBox(AddNewCard.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                buildRequest();
            }
        });

        TextView alert_cancel = (TextView) dialog.findViewById(R.id.alert_cancel);
        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }
}
