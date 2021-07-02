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

public class WithdrawFunds extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private TextView tvMailBankCheck;
    private View amountView;
    private EditText etAmount,etFullName,etStreet,etCity,etZipCode;
    private LinearLayout llMailBankCheck;
    private Spinner spnrState;
    private DataBaseHelper myDbHelper;
    private String withdrawCash,totalCash,promoBal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_funds);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(),R.color.white,null));

        initDatabase();
        initComponents();
    }
    private void initDatabase(){
        myDbHelper = new DataBaseHelper(getApplicationContext());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                withdrawCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                promoBal = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_PROMOBALANCE));
                totalCash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
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
        etAmount=findViewById(R.id.etAmount);
        amountView=findViewById(R.id.amountView);
        etFullName=findViewById(R.id.etFullName);
        etCity=findViewById(R.id.etCity);
        etStreet=findViewById(R.id.etStreet);
        etZipCode=findViewById(R.id.etZipCode);
        spnrState=findViewById(R.id.spnrState);
        llMailBankCheck=findViewById(R.id.llMailBankCheck);
        tvMailBankCheck=findViewById(R.id.tvMailBankCheck);

        toolbar_title_x.setText("Withdraw funds");
        tvCashBalance.setText("$"+totalCash);
        tvWithdrawBalance.setText("$"+withdrawCash);
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        etFullName.addTextChangedListener(this);
        etStreet.addTextChangedListener(this);
        etCity.addTextChangedListener(this);
        etZipCode.addTextChangedListener(this);
        llMailBankCheck.setOnClickListener(this);

        String [] statesArray=getResources().getStringArray(R.array.states_array);
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, statesArray);
        spnrState.setAdapter(courseAdapter);


        tvPromoBalance.setText("Promo funds are not available for withdrawal.You currently have $"+promoBal +" in promo funds.");

        etAmount.addTextChangedListener(new TextWatcher() {
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
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.llMailBankCheck:
                buildRequest();
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

        int nameLength=etFullName.getText().toString().trim().length();
        int streetLength=etStreet.getText().toString().trim().length();
        int cityLength=etCity.getText().toString().trim().length();
        int zipLength=etZipCode.getText().toString().trim().length();

        if(nameLength>0&&streetLength>0&&cityLength>0&&zipLength>4){
            llMailBankCheck.setEnabled(true);
            llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_green);
            tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
        }else{
            llMailBankCheck.setEnabled(false);
            llMailBankCheck.setBackgroundResource(R.drawable.btn_bg_grey);
            tvMailBankCheck.setTextColor(ResourcesCompat.getColor(getResources(),R.color.add_fund_clr,null));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private void buildRequest(){
        try{
            String amountTxt=etAmount.getText().toString().trim().replace("$","");
            String withdrawAmount = withdrawCash;

            if(!amountTxt.equals("")&&!withdrawAmount.equals("")) {
                Integer reqAmount = Integer.parseInt(amountTxt);
                Integer availAmount = Integer.parseInt(withdrawAmount);
                //if(reqAmount<=availAmount) {
                if (availAmount - reqAmount >= 0) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("amount", reqAmount);
                    jsonObject.put("fullName", etFullName.getText().toString().trim());
                    jsonObject.put("billingAddress", etStreet.getText().toString().trim());
                    jsonObject.put("city", etCity.getText().toString().trim());
                    jsonObject.put("state", spnrState.getSelectedItem().toString());
                    if(spnrState.getSelectedItemPosition()<=50)
                        jsonObject.put("country", "USA");
                    else
                        jsonObject.put("country", "CANADA");
                    jsonObject.put("zipCode", etZipCode.getText().toString().trim());

                    SendDataToServer(jsonObject,reqAmount);
                }else{
                    Utilities.showToast(WithdrawFunds.this,"Your withdrawal amount should be less than or equals to " +"$"+Dashboard.CASH_BAL);
                }
            }else{
                Utilities.showToast(WithdrawFunds.this,"Something went wrong please try again after sometime");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SendDataToServer(final JSONObject withdrawObject, final int requestAmount){
        Log.e("WITHDRAW::REQESTOBJ",withdrawObject.toString());
        AndroidNetworking.post(APIs.WITHDRAW_FUNDS)
                .addJSONObjectBody(withdrawObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken",Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(),WithdrawResponse.class);
                            if(withdrawResponse!=null){

                                    Utilities.showAlertBox(WithdrawFunds.this, getResources().getString(R.string.process_request), getResources().getString(R.string.withdraw_over_msg));



                                //Update Values to SqliteDb
                                ContentValues cv = new ContentValues();
                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
                                cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
                                cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
                                cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());
                                myDbHelper.updateUser(cv);

                            }

                            Log.e("**WITHDRAW::Response::",response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Utilities.showToast(WithdrawFunds.this,anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());


                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(WithdrawFunds.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(WithdrawFunds.this, ej.getString("message"));
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

                Intent in = new Intent(WithdrawFunds.this, OnBoarding.class);
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


}
