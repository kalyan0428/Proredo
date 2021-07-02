package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.facebook.login.LoginManager;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import okhttp3.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText edOldPassword, edNewPassword, edConfirmNewPassword;
    private TextView btnSubmit, tvAtLeastEightChars, tvAtLeastOneNumber, tvOnceSpecialChar;
    private String sessionToken,NEWTOKEN;
    private boolean passwordHasNumber, passwordHasSpecialCharacter, passwordHasEightCharacters;

    private View old_pwd_view, new_pwd_view, re_pwd_view;
    public static DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

        SharedPreferences sharedPreferences = getSharedPreferences("SESSION_TOKEN", MODE_PRIVATE);
        sessionToken = sharedPreferences.getString("token", "");
        NEWTOKEN = sharedPreferences.getString("updatetoken", "");

        /******************Database Starts************************/
        mydb = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = mydb.getReadableDatabase();
        /******************Database Ends************************/
        init();

    }

    private void init() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        edOldPassword = findViewById(R.id.edOldPassword);
        edNewPassword = findViewById(R.id.edNewPassword);
        edConfirmNewPassword = findViewById(R.id.edConfirmNewPassword);
        tvAtLeastEightChars = findViewById(R.id.tvAtLeastEightChars);
        tvAtLeastOneNumber = findViewById(R.id.tvAtLeastOneNumber);
        tvOnceSpecialChar = findViewById(R.id.tvOnceSpecialChar);
        btnSubmit = findViewById(R.id.btnSubmit);


        toolbar_title_x.setText(getString(R.string.change_password));
        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        edOldPassword.addTextChangedListener(this);
        edNewPassword.addTextChangedListener(this);
        edConfirmNewPassword.addTextChangedListener(this);
        btnSubmit.setOnClickListener(this);

        old_pwd_view = findViewById(R.id.old_pwd_view);
        new_pwd_view = findViewById(R.id.new_pwd_view);
        re_pwd_view = findViewById(R.id.re_pwd_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.btnSubmit:
                submitData();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        int old_length = edOldPassword.getText().toString().trim().length();
        int new_length = edNewPassword.getText().toString().trim().length();
        int cnf_length = edConfirmNewPassword.getText().toString().trim().length();


        if (old_length == 0) {
            old_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            old_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }
        if (new_length == 0) {
            new_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            new_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }
        if (cnf_length == 0) {
            re_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            re_pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }
        String newPwdStr = edNewPassword.getText().toString();

        passwordValidation(new_length, newPwdStr);

        submitValidation(old_length, new_length, cnf_length);

    }


    private void passwordValidation(int pwd_length, String pwdStr) {

        if (pwd_length >= 8) {
            tvAtLeastEightChars.setTextColor(getResources().getColor(R.color.bg_green));
            tvAtLeastEightChars.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success, 0, 0, 0);
            passwordHasEightCharacters = true;
        } else {
            tvAtLeastEightChars.setTextColor(getResources().getColor(R.color.validation_color));
            tvAtLeastEightChars.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong, 0, 0, 0);
            passwordHasEightCharacters = false;
        }


        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pwdStr);

        boolean isMatched = matcher.matches();

        try {
            if (isMatched) {
                tvAtLeastOneNumber.setTextColor(getResources().getColor(R.color.bg_green));
                tvAtLeastOneNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success, 0, 0, 0);
                passwordHasNumber = true;
            } else {
                tvAtLeastOneNumber.setTextColor(getResources().getColor(R.color.validation_color));
                tvAtLeastOneNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong, 0, 0, 0);
                passwordHasNumber = false;
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
            Log.d("ERROR", ex.toString());
        }

        String regex1 = "\\p{Punct}"; //Special character : `~!@#$%^&*()-_+=\|}{]["';:/?.,><

        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(pwdStr);

        if (matcher1.find()) {
            Log.d("YES::", "we had a special char");
            tvOnceSpecialChar.setTextColor(getResources().getColor(R.color.bg_green));
            tvOnceSpecialChar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success, 0, 0, 0);
            passwordHasSpecialCharacter = true;
        } else {
            Log.d("NO::", "special char");
            tvOnceSpecialChar.setTextColor(getResources().getColor(R.color.validation_color));
            tvOnceSpecialChar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong, 0, 0, 0);
            passwordHasSpecialCharacter = false;
        }


    }

    private void submitValidation(int old_pwd, int new_pwd, int confirm_pwd) {
        String newStr = edNewPassword.getText().toString();
        String confirmStr = edConfirmNewPassword.getText().toString();
        //if (old_pwd > 0 && new_pwd > 0 && confirm_pwd > 0 && newStr.equals(confirmStr)) {
        if (passwordHasEightCharacters && passwordHasNumber && passwordHasSpecialCharacter && newStr.equals(confirmStr)) {
            btnSubmit.setEnabled(true);
            btnSubmit.setTextColor(getResources().getColor(R.color.white));
            btnSubmit.setBackgroundResource(R.drawable.btn_bg_red);
        } else {
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(getResources().getColor(R.color.btn_dis_text));
            btnSubmit.setBackgroundResource(R.drawable.login_bg_disable);
        }

    }

    //Service call to send data to server
    private void submitData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("oldpassword", edOldPassword.getText().toString().trim());
            jsonObject.put("newpassword", edNewPassword.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.put(APIs.CHANGE_PWD_URL)
                .addJSONObjectBody(jsonObject) // posting json
                .addHeaders("Content-Type", "application/json")
                .addHeaders("sessionToken", sessionToken)
                .addHeaders("Authorization", "bearer "+ NEWTOKEN)

                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {

                        if(response.code() == 403){
                            String au = response.message();
                            Utilities.showToast(ChangePassword.this,getString(R.string.servererror_msg));

                        }else
//
                            if (response.code() != 200) {
//                            Utilities.showToast(getApplicationContext(), ""+response.message());

                            String au = response.message();
                            if (au.contains("Unauthorized")) {
                                showAlertBoxAU(ChangePassword.this, "Error", "Session has expired,please try logining again");
                            } else {
//                                Utilities.showToast(ChangePassword.this, ej.getString("message"));
//                                Utilities.showAlertBox(ChangePassword.this, "Alert ", response.message());
                              //  Utilities.showToast(ChangePassword.this,"Old password is worng.Please try again!");
                                Utilities.showToast(ChangePassword.this,au);


                            }

                         /*   try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(ChangePassword.this, "Error", "Session has expired,please try logining again");
                                } else {
//                                Utilities.showToast(ChangePassword.this, ej.getString("message"));
                                    Utilities.showAlertBox(ChangePassword.this, " ", ej.getString("message"));
                                }
*/
                        }else{
                            finish();
                        }
//                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
//                        Utilities.showToast(getApplicationContext(),""+anError.getErrorBody());

                        if (anError.getErrorCode() != 0) {
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());
                            if (anError.getErrorCode() == 403) {
                                Utilities.showToast(ChangePassword.this, getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(ChangePassword.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(ChangePassword.this, ej.getString("message"));
//                                Utilities.showToast(ChangePassword.this,"Old password is worng.Please try again!");
                                }


                            } catch (Exception e) {

                            }
                        }


//                        finish();
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
                mydb.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(ChangePassword.this, OnBoarding.class);
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
