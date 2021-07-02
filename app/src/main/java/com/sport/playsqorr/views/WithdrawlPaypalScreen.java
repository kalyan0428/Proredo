package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONObject;

public class WithdrawlPaypalScreen extends AppCompatActivity implements View.OnClickListener {

    private EditText etAmount, etFullName, etStreet, etCity, etZipCode, et_repaypal_address, et_paypal_address;
    private LinearLayout llwithdrawAmount;
    private TextView tvWithdrawFunds;
    String resAmount;
    CheckBox check_paypal;
    private DataBaseHelper myDbHelper;
    private String withdrawCash, totalCash, promoBal, sessionToken,NEWTOKEN;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl_paypal_screen);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("reqAmount"))
                resAmount = bundle.getString("reqAmount");
        }

        initDatabase();
        inputComp();
    }

    private void inputComp() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        TextView tvCashBalance = findViewById(R.id.tvCashBalance);
        TextView tvWithdrawBalance = findViewById(R.id.tvWithdrawBalance);
        TextView tvPromoBalance = findViewById(R.id.tvPromoBalance);
        llwithdrawAmount = findViewById(R.id.llwithdraw_funds);
        tvWithdrawFunds = findViewById(R.id.tvWithdrawFunds);
        etAmount = findViewById(R.id.etAmount);
        etAmount.setText("$" + resAmount);

        toolbar_title_x.setText("Withdraw funds");
        tvWithdrawBalance.setText("$" + withdrawCash);
        tvCashBalance.setText("$" + totalCash);

        et_paypal_address = findViewById(R.id.et_paypal_address);
        et_repaypal_address = findViewById(R.id.et_repaypal_address);

        check_paypal = findViewById(R.id.checkbox_txt);
        check_paypal.setOnClickListener(this);


        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
//        etFullName.addTextChangedListener(this);
//        etStreet.addTextChangedListener(this);
//        etCity.addTextChangedListener(this);
//        etZipCode.addTextChangedListener(this);
        llwithdrawAmount.setOnClickListener(this);

    }

    private void initDatabase() {
        myDbHelper = new DataBaseHelper(getApplicationContext());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                withdrawCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                promoBal = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                totalCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
            }

            cursor.close();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.checkbox_txt:

                if (!et_paypal_address.getText().toString().trim().isEmpty() || !et_repaypal_address.getText().toString().trim().isEmpty()) {


                    if (et_paypal_address.getText().toString().trim().matches(emailPattern) || et_repaypal_address.getText().toString().trim().matches(emailPattern)) {
                        if (check_paypal.isChecked()) {
                            //do some validation

                            if (et_paypal_address.getText().toString().trim().equalsIgnoreCase(et_repaypal_address.getText().toString().trim())) {

                                llwithdrawAmount.setClickable(true);
                                llwithdrawAmount.setEnabled(true);
                                llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_green);
                                tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


                            } else {
                                Utilities.showToast(WithdrawlPaypalScreen.this, "PayPal Email Address was not Match");
                                check_paypal.setChecked(false); //to uncheck

                                llwithdrawAmount.setClickable(false);
                                llwithdrawAmount.setEnabled(false);
                                llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                                tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                            }
                        } else {
                            check_paypal.setChecked(false); //to uncheck

                            llwithdrawAmount.setClickable(false);
                            llwithdrawAmount.setEnabled(false);
                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                        }

                    }else{
                        check_paypal.setChecked(false); //to uncheck
                        Utilities.showToast(WithdrawlPaypalScreen.this, "Please enter vaild emailid");
                        llwithdrawAmount.setClickable(false);
                        llwithdrawAmount.setEnabled(false);
                        llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));


                    }

                } else {
                    check_paypal.setChecked(false); //to uncheck
                    llwithdrawAmount.setClickable(false);
                    llwithdrawAmount.setEnabled(false);
                    llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                    tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));


                    if (check_paypal.isChecked()) {
                        //do some validation
                        if (et_paypal_address.getText().toString().trim().equalsIgnoreCase(et_repaypal_address.getText().toString().trim())) {

//                            llwithdrawAmount.setClickable(true);
//                            llwithdrawAmount.setEnabled(true);
//                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_green);
//                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

                            llwithdrawAmount.setClickable(false);
                            llwithdrawAmount.setEnabled(false);
                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));


                        } else {
                            Utilities.showToast(WithdrawlPaypalScreen.this, "PayPal Email Address was not Match");
                            check_paypal.setChecked(false); //to uncheck

                            llwithdrawAmount.setClickable(false);
                            llwithdrawAmount.setEnabled(false);
                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                        }
                    } else {
                        check_paypal.setChecked(false); //to uncheck

                        llwithdrawAmount.setClickable(false);
                        llwithdrawAmount.setEnabled(false);
                        llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                    }
                }


                break;
            case R.id.llwithdraw_funds:


                llwithdrawAmount.setVisibility(View.GONE);


                if (et_paypal_address.getText().toString().length() > 0 || et_repaypal_address.getText().toString().length() > 0) {
                    if (check_paypal.isChecked()) {

                        if (et_paypal_address.getText().toString().trim().equalsIgnoreCase(et_repaypal_address.getText().toString().trim())) {

                            llwithdrawAmount.setClickable(true);
                            llwithdrawAmount.setEnabled(true);
                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_green);
                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

                            try {

                                JSONObject jsonObject = new JSONObject();
//
                                jsonObject.put("amount", resAmount);
                                jsonObject.put("email", et_paypal_address.getText().toString().trim());
                                SendDataToServer(jsonObject, Integer.parseInt(resAmount));
                            } catch (Exception e) {

                            }


                        } else {

                            llwithdrawAmount.setVisibility(View.VISIBLE);

                            Utilities.showToast(WithdrawlPaypalScreen.this, "PayPal Email Address was not Match");
                            check_paypal.setChecked(false); //to uncheck

                            llwithdrawAmount.setClickable(false);
                            llwithdrawAmount.setEnabled(false);
                            llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                            tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));
                        }


                    } else {
//                        check_paypal.setChecked(false); //to uncheck
                        llwithdrawAmount.setVisibility(View.VISIBLE);
                        llwithdrawAmount.setClickable(false);
                        llwithdrawAmount.setEnabled(false);
                        Utilities.showToast(WithdrawlPaypalScreen.this, "Please select checkbox");

                        llwithdrawAmount.setClickable(false);
                        llwithdrawAmount.setEnabled(false);
                        llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                        tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));


                    }
                } else {
                    llwithdrawAmount.setVisibility(View.VISIBLE);
                    llwithdrawAmount.setClickable(false);
                    llwithdrawAmount.setEnabled(false);
                    check_paypal.setChecked(false); //to uncheck
                    Utilities.showToast(WithdrawlPaypalScreen.this, "Please Enter Paypal EmailID");
                    llwithdrawAmount.setClickable(false);
                    llwithdrawAmount.setEnabled(false);
                    llwithdrawAmount.setBackgroundResource(R.drawable.btn_bg_grey);
                    tvWithdrawFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.add_fund_clr, null));

                }




//                    String amountTxt = etAmount.getText().toString().trim().replace("$", "");
//                    String withdrawAmount = withdrawCash;
//
//                    if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
//                        Integer reqAmount = Integer.parseInt(amountTxt);
//                        Integer availAmount = Integer.parseInt(withdrawAmount);
//                        //if(reqAmount<=availAmount) {
//                        if (availAmount - reqAmount >= 0) {
////                            JSONObject jsonObject = new JSONObject();
////
////                            jsonObject.put("amount", reqAmount);
//
////                            SendDataToServer(jsonObject, reqAmount);
//                            if (method.equalsIgnoreCase("paypal")) {
//                                Intent withdrawIntent = new Intent(Withdraw_paypal.this, WithdrawlPaypalScreen.class);
//                                withdrawIntent.putExtra("reqAmount",String.valueOf(reqAmount));
//                                withdrawIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(withdrawIntent);
//                            } else {
//
//                            }
//
//                        } else {
//                            Utilities.showToast(Withdraw_paypal.this, "Your withdrawal amount should be less than or equals to " + "$" + withdrawCash);
//                        }
//                    } else {
//                        Utilities.showToast(Withdraw_paypal.this, "Something went wrong please try again after sometime");
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//               buildRequest();
                break;


            default:
                break;
        }
    }

    private void SendDataToServer(final JSONObject withdrawObject, final int requestAmount) {
        Log.e("WITHDRAW::REQESTOBJ", withdrawObject.toString());
        AndroidNetworking.post(APIs.WITHDRAW_FUNDS_paypal)
                .addJSONObjectBody(withdrawObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);
                            if (withdrawResponse != null) {
                                if (requestAmount < 100) {
                                    showAlertBoxWith(WithdrawlPaypalScreen.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_under_msg));
                                } else {
                                    showAlertBoxWith(WithdrawlPaypalScreen.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_over_msg));

                                }

                                //Update Values to SqliteDb
                                ContentValues cv = new ContentValues();
                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                                myDbHelper.updateUser(cv);

                            }

                            Log.e("**WITHDRAW::Response::", response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        llwithdrawAmount.setVisibility(View.VISIBLE);
                        Utilities.showToast(WithdrawlPaypalScreen.this, anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());

                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });
    }

    private void showAlertBoxWith(final Context context, String title, String message) {

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
                Intent transactionsIntent = new Intent(WithdrawlPaypalScreen.this, Dashboard.class);
                transactionsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(transactionsIntent);


            }
        });

    }
}
