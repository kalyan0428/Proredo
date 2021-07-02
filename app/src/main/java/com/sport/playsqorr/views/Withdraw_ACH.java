package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.WithdrawResponse;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONObject;

public class Withdraw_ACH extends AppCompatActivity implements View.OnClickListener, TextWatcher {


    private TextView tvMailBankCheck;
    private View amountView;
    private EditText etAmount, etFullName, etStreet, etCity, etreCity;
    private LinearLayout llMailBankCheck;
    private Spinner spnrState;
    private DataBaseHelper myDbHelper;
    private String withdrawCash, totalCash, promoBal,sessionToken,NEWTOKEN;
    String resAmount;

    CheckBox check_ach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw__ach);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("reqAmount"))
                resAmount = bundle.getString("reqAmount");
        }

        initDatabase();
        initComponents();
    }

    private void initDatabase() {
        myDbHelper = new DataBaseHelper(getApplicationContext());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                withdrawCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                promoBal = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                totalCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
            }

            cursor.close();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initComponents() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        TextView tvCashBalance = findViewById(R.id.tvCashBalance);
        TextView tvWithdrawBalance = findViewById(R.id.tvWithdrawBalance);
        TextView tvPromoBalance = findViewById(R.id.tvPromoBalance);
        etAmount = findViewById(R.id.etAmount);
        amountView = findViewById(R.id.amountView);
        etFullName = findViewById(R.id.etFullName);
        etCity = findViewById(R.id.etCity);
        etStreet = findViewById(R.id.etStreet);
        etreCity = findViewById(R.id.etreCity);
        spnrState = findViewById(R.id.spnrState);
        llMailBankCheck = findViewById(R.id.llMailBankCheck);
        tvMailBankCheck = findViewById(R.id.tvMailBankCheck);


        check_ach = findViewById(R.id.checkbox_txt);
        check_ach.setOnClickListener(this);

        toolbar_title_x.setText("Withdraw funds");
        tvCashBalance.setText("$" + totalCash);
        tvWithdrawBalance.setText("$" + withdrawCash);
        etAmount.setText("$" + resAmount);

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        etFullName.addTextChangedListener(this);
        etStreet.addTextChangedListener(this);
        etCity.addTextChangedListener(this);
//        etZipCode.addTextChangedListener(this);
        llMailBankCheck.setOnClickListener(this);

        String[] statesArray = getResources().getStringArray(R.array.achtypes_array);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
        spnrState.setAdapter(courseAdapter);


        tvPromoBalance.setText("Promo funds are not available for withdrawal.You currently have $" + promoBal + " in promo funds.");

        llMailBankCheck.setVisibility(View.VISIBLE);
        llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_green);
        tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

      /*  etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String withdrawAmount = etAmount.getText().toString();
                if (charSequence.length() == 0) {
                    return;
                }
                if (withdrawAmount.indexOf("$") == -1 && charSequence.length() >= 1) {
                    withdrawAmount = "$" + withdrawAmount;
                    etAmount.setText(withdrawAmount);
                    if (i1 == 0 && i2 == 1) {
                        etAmount.setSelection(2);
                    } else {
                        etAmount.setSelection(1);
                    }
                } else {
                    if (withdrawAmount.length() == 1) {
                        etAmount.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

//
//                }
//                if(s.toString().length()>0&&!s.toString().startsWith("$")){
//                    etAmount.setText("$");
//                    Selection.setSelection(etAmount.getText(), etAmount.getText().length());
//                }
                if (etAmount.getText().toString().trim().length() == 0) {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
                } else {
                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.text_color_new, null));
                }
            }
        });*/
    }


    public boolean validateRoutingNumber(String s) {

        int checksum = 0, len = 0, sum = 0, mod = 0;
        len = s.length();

        if (len != 9) {
            return false;
        } else {
            String newString = s.substring(s.length() - 1);
            checksum = Integer.parseInt(newString);

            sum = (7 * (Integer.parseInt("" + s.charAt(0)) + Integer.parseInt("" + s.charAt(3)) + Integer.parseInt("" + s.charAt(6)))) +
                    (3 * (Integer.parseInt("" + s.charAt(1)) + Integer.parseInt("" + s.charAt(4)) + Integer.parseInt("" + s.charAt(7)))) +
                    (9 * (Integer.parseInt("" + s.charAt(2)) + Integer.parseInt("" + s.charAt(5))));


            mod = sum % 10;

            if (mod == checksum)
                return true;
            else
                return false;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
                Intent i1 = new Intent(Withdraw_ACH.this,Withdraw_paypal.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);
                break;
            case R.id.checkbox_txt:
//                if (etCity.getText().toString().trim().equalsIgnoreCase(etreCity.getText().toString().trim())) {
//
//
//
//
//                } else {
//
//
//                    Utilities.showToast(Withdraw_ACH.this, "PayPal Email Address was not Match");
//
//
//                }
                break;
            case R.id.llMailBankCheck:
                llMailBankCheck.setVisibility(View.VISIBLE);
                llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_green);
                tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));

                if (etFullName.getText().toString().length() <= 0) {
                    Utilities.showToast(Withdraw_ACH.this, "Please Enter Account Name");
                } else if (etStreet.getText().toString().length() <= 0) {
                    Utilities.showToast(Withdraw_ACH.this, "Please Enter Routing Number");
                } else if (etCity.getText().toString().length() <= 0) {
                    Utilities.showToast(Withdraw_ACH.this, "Please Enter Account Number");
                } else if (etreCity.getText().toString().length() <= 0) {
                    Utilities.showToast(Withdraw_ACH.this, "Please Enter Confirm Account Number");
                } else if (!check_ach.isChecked()) {
                    Utilities.showToast(Withdraw_ACH.this, "Please Select checkbox");
                } else {

                    if (etCity.getText().toString().trim().equalsIgnoreCase(etreCity.getText().toString().trim())) {

                        validateRoutingNumber(etStreet.getText().toString().trim());

                        String s = etStreet.getText().toString().trim();
                        int checksum = 0, len = 0, sum = 0, mod = 0;
                        len = s.length();

                        if (len != 9) {
                            Utilities.showToast(Withdraw_ACH.this, "Please Enter correct Routing Number");
                            check_ach.setChecked(false);
                        } else {
                            String newString = s.substring(s.length() - 1);
                            checksum = Integer.parseInt(newString);

                            sum = (7 * (Integer.parseInt("" + s.charAt(0)) + Integer.parseInt("" + s.charAt(3)) + Integer.parseInt("" + s.charAt(6)))) +
                                    (3 * (Integer.parseInt("" + s.charAt(1)) + Integer.parseInt("" + s.charAt(4)) + Integer.parseInt("" + s.charAt(7)))) +
                                    (9 * (Integer.parseInt("" + s.charAt(2)) + Integer.parseInt("" + s.charAt(5))));


                            mod = sum % 10;

                            if (mod == checksum) {
                                llMailBankCheck.setVisibility(View.GONE);
                                buildRequest();

                            } else {
                                Utilities.showToast(Withdraw_ACH.this, "Please Enter correct Routing Number");
                                check_ach.setChecked(false);
                            }


                        }
                    } else {
                        check_ach.setChecked(false);
                        Utilities.showToast(Withdraw_ACH.this, "Account number was not Match");
                    }


//                    Log.e("rn----",   validateRoutingNumber(etStreet.getText().toString().trim()));
//
                }


               /* if(etFullName.getText().toString().length()>0){
                    llMailBankCheck.setVisibility(View.VISIBLE);

                    validateRoutingNumber(etStreet.getText().toString().trim());

                String s=    etStreet.getText().toString().trim();
                    int checksum=0, len=0, sum=0, mod = 0;
                    len = s.length();

                    if(len != 9){
                        Utilities.showToast(Withdraw_ACH.this, "Please Enter correct Routing Number");
                    }else {
                        String newString = s.substring(s.length()-1);
                        checksum = Integer.parseInt(newString);

                        sum = (7*(Integer.parseInt(""+s.charAt(0))+Integer.parseInt(""+s.charAt(3))+ Integer.parseInt(""+s.charAt(6)))) +
                                (3*(Integer.parseInt(""+s.charAt(1))+Integer.parseInt(""+s.charAt(4))+ Integer.parseInt(""+s.charAt(7))))+
                                (9*(Integer.parseInt(""+s.charAt(2))+Integer.parseInt(""+s.charAt(5))));


                        mod = sum % 10;

                        if(mod == checksum)
                            Utilities.showToast(Withdraw_ACH.this, " correct Routing Number");
                        else
                            Utilities.showToast(Withdraw_ACH.this, "Please Enter correct Routing Number");

                    }

//                    Log.e("rn----",   validateRoutingNumber(etStreet.getText().toString().trim()));
                   buildRequest();
//
                }else{
                    Utilities.showToast(Withdraw_ACH.this, "Please Fill Details");
                    llMailBankCheck.setVisibility(View.VISIBLE);

                }*/

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

        int nameLength = etFullName.getText().toString().trim().length();
        int streetLength = etStreet.getText().toString().trim().length();
        int cityLength = etCity.getText().toString().trim().length();
        int zipLength = etreCity.getText().toString().trim().length();

//        if(nameLength>0&&streetLength>0&&cityLength>0&&zipLength>4){
//            llMailBankCheck.setEnabled(true);
//            llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_green);
//            tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
//        }else{
//            llMailBankCheck.setEnabled(false);
//            llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_grey);
//            tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(),R.color.add_fund_clr,null));
//        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private void buildRequest() {
        try {
            String amountTxt = etAmount.getText().toString().trim().replace("$", "");
            String withdrawAmount = withdrawCash;

            if (!amountTxt.equals("") && !withdrawAmount.equals("")) {
                Integer reqAmount = Integer.parseInt(amountTxt);
                Integer availAmount = Integer.parseInt(withdrawAmount);
                //if(reqAmount<=availAmount) {
                JSONObject jsonObject = new JSONObject();


                jsonObject.put("nameOnAccount", etFullName.getText().toString().trim());
                jsonObject.put("accountType", spnrState.getSelectedItem().toString().trim());
                jsonObject.put("accountNumber", etCity.getText().toString().trim());
                jsonObject.put("routingNumber", etStreet.getText().toString().trim());
                jsonObject.put("amount", reqAmount);

                SendDataToServer(jsonObject, reqAmount);
            } else {
                Utilities.showToast(Withdraw_ACH.this, "Something went wrong please try again after sometime");
                llMailBankCheck.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {
            e.printStackTrace();
            llMailBankCheck.setVisibility(View.VISIBLE);
        }
    }

    private void SendDataToServer(final JSONObject withdrawObject, final int requestAmount) {
        Log.e("WITHDRAW::REQESTOBJ", withdrawObject.toString());
        AndroidNetworking.post(APIs.ADDACHACCOUNTS)
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

                                showAlertBoxWith(Withdraw_ACH.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_over_msg));


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
                            llMailBankCheck.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        llMailBankCheck.setVisibility(View.VISIBLE);

                     //   Utilities.showToast(Withdraw_ACH.this, anError.getErrorBody());
                        Log.e("", "onError errorBody : " + anError.getErrorBody());
                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());
                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(Withdraw_ACH.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(Withdraw_ACH.this, ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }
                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });
    }

    private void showAlertBoxAU(final Context context, String title, String message) {

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

//                dialog.dismiss();

                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                myDbHelper.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(Withdraw_ACH.this, OnBoarding.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                // getApplicationContext().deleteDatabase(DataBaseHelper.DATABASE_NAME);


                //    resetDatabase();

              /*  Intent mStartActivity = new Intent(Profile.this, OnBoarding.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Profile.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);*/

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
                Intent transactionsIntent = new Intent(Withdraw_ACH.this, Dashboard.class);
                transactionsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(transactionsIntent);


            }
        });

    }
}
