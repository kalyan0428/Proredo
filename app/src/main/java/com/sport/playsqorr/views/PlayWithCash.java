package com.sport.playsqorr.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.sport.playsqorr.BuildConfig;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.pojos.ResponsePojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;

public class PlayWithCash extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText et_full_name, et_password, et_email_address, et_dob, et_ph_no, et_promo_code;
    private TextView tv_sign_up, tvAtLeastEightChars, tvAtLeastOneNumber, tvOnceSpecialChar, tvEmailError, tvPromoError, tvPromoS, promo_apply, close_promo_e, close_promo_s;

    private View email_view, promo_view, name_view, pwd_view, age_view, number_view;
    private String userId, userToken;
    private String signUpOrigin = "emailpassword";
    private CallbackManager mFacebookCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String userEmail, userFullName;
    LinearLayout error_promo_ll, sucess_promo_ll;
    private boolean isEmailValid, passwordHasNumber, passwordHasSpecialCharacter, passwordHasEightCharacters, dobData, isNamevalue;

    String refCode = "";

    private static final int RC_SIGN_IN = 20;
    String emailPattern = "[a-zA-Z0-9._+-]+@[a-z]+\\.+[a-z]+";
    private String signUpBonus;

    private DataBaseHelper mydb;
    MixpanelAPI mMixpanel;

    private String FCMToken;

    ProgressDialog progressDialog;

    Integer age;

    Integer k_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_cash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        mydb = new DataBaseHelper(this);

        mMixpanel = MixpanelAPI.getInstance(PlayWithCash.this, getString(R.string.test_MIX_PANEL_TOKEN));


        SharedPreferences sharedPreferences = getSharedPreferences("FCM_TOKEN", MODE_PRIVATE);
        FCMToken = sharedPreferences.getString("fcm_token", "");

        progressDialog = new ProgressDialog(PlayWithCash.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        init();
        getUserData();
        configureFb();
        configureGoogleLogin();
    }


    String pp_s;
    TextView toolbar_title_x;

    private void init() {
        toolbar_title_x = findViewById(R.id.toolbar_title_x);
        TextView already_title_x = findViewById(R.id.already_have_account);

        et_full_name = findViewById(R.id.et_full_name);
        et_email_address = findViewById(R.id.et_email_address);
        et_password = findViewById(R.id.et_password);
        et_dob = findViewById(R.id.et_dob);
        et_ph_no = findViewById(R.id.et_ph_no);
        et_promo_code = findViewById(R.id.promo_tokens);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tvAtLeastEightChars = findViewById(R.id.tvAtLeastEightChars);
        tvAtLeastOneNumber = findViewById(R.id.tvAtLeastOneNumber);
        tvOnceSpecialChar = findViewById(R.id.tvOnceSpecialChar);
        tvEmailError = findViewById(R.id.tvEmailError);
        email_view = findViewById(R.id.email_view);
        age_view = findViewById(R.id.age_view);
        number_view = findViewById(R.id.number_view);
        pwd_view = findViewById(R.id.pwd_view);
        name_view = findViewById(R.id.name_view);
        CardView cvFacebook = findViewById(R.id.cvFacebook);
        CardView cvGmail = findViewById(R.id.cvGmail);
        tvPromoError = findViewById(R.id.tvPromoError);
        tvPromoS = findViewById(R.id.tvPromos);
        promo_view = findViewById(R.id.promo_view);
        promo_apply = findViewById(R.id.promo_apply);
        error_promo_ll = findViewById(R.id.error_promo_ll);
        sucess_promo_ll = findViewById(R.id.sucess_promo_ll);
        close_promo_e = findViewById(R.id.close_promo_e);
        close_promo_s = findViewById(R.id.close_promo_s);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("SIGN_UP_BONUS")) {
            signUpBonus = bundle.getString("SIGN_UP_BONUS");
            et_promo_code.setText("SIGNUPBONUS5");
//        applyPromo();
        }

        if (bundle != null && bundle.containsKey("place_p")) {
            pp_s = bundle.getString("place_p");

//        applyPromo();
        }
        toolbar_title_x.setText(getString(R.string.sign_up));

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        already_title_x.setOnClickListener(this);
        et_full_name.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        et_email_address.addTextChangedListener(this);
        et_dob.addTextChangedListener(this);
        et_promo_code.addTextChangedListener(this);
        et_ph_no.addTextChangedListener(this);

        close_promo_e.setOnClickListener(this);
        close_promo_s.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        promo_apply.setOnClickListener(this);

        et_dob.setClickable(true);
        et_dob.setOnClickListener(this);
        tv_sign_up.setEnabled(false);
        tv_sign_up.setOnClickListener(this);
        cvFacebook.setOnClickListener(this);
        cvGmail.setOnClickListener(this);


        Cursor cursor = mydb.getDeepInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DEEPLINK_CODE)));
//                et_promo_code.setText(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DEEPLINK_CODE)));
                refCode = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DEEPLINK_CODE));
            }

            cursor.close();

        }

    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String uEmail = sharedPreferences.getString("user_email", "");
        String uFullName = sharedPreferences.getString("user_name", "");

        et_email_address.setText(uEmail);
        et_email_address.setSelection(uEmail.length());

        et_full_name.setText(uFullName);
        et_full_name.setSelection(uFullName.length());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                finish();
                break;
            case R.id.close_promo_e:
                et_promo_code.setText("");
                et_promo_code.setEnabled(true);
                error_promo_ll.setVisibility(View.GONE);
                break;
            case R.id.close_promo_s:
                et_promo_code.setText("");
                et_promo_code.setEnabled(true);
                sucess_promo_ll.setVisibility(View.GONE);
                break;
            case R.id.promo_apply:
//                applyPromo();

                break;
            case R.id.already_have_account:
                Intent in = new Intent(PlayWithCash.this, Login.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
            case R.id.tv_sign_up:
                tv_sign_up.setBackgroundResource(R.drawable.btn_bg_red_ripple);

                if (progressDialog != null)
                    progressDialog.show();
//                submitSignUpData();

                Log.e("date--", et_dob.getText().toString().trim());

                //Integer age = getAge(year, monthOfYear + 1, dayOfMonth);

                if(k_age!=null){
                    if (k_age == 2) {
                        submitSignUpData();
                    } else if (k_age == 1) {
                        //   showAlertBox(PlayWithCash.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                        submitSignUpToken();
                    }
                }


/*
k
                int promos = et_promo_code.getText().toString().trim().length();
                if (promos == 0) {
                } else if (promos >= 6) {
                    et_promo_code.setEnabled(false);
                    tvPromoS.setText("$10 Bonus cash on signup");
                    tvPromoS.setVisibility(View.VISIBLE);
                    tvPromoError.setVisibility(View.GONE);
                    submitSignUpData();
                } else if (promos >= 1 && promos < 6) {
                    et_promo_code.requestFocus();
                    tvPromoError.setVisibility(View.VISIBLE);
                    promo_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
*/
                break;
            case R.id.et_dob:
                showCalendar();
                break;
            case R.id.cvFacebook:
                LoginManager.getInstance().logOut();

                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.cvGmail:

                googleSignIn();
                break;
            default:
                break;

        }
    }

    private void applyPromo() {
        int promos = et_promo_code.getText().toString().trim().length();
        if (promos == 0) {
        } else if (promos >= 6) {
            et_promo_code.setEnabled(false);
            tvPromoS.setText("$5 Bonus cash on signup");
            sucess_promo_ll.setVisibility(View.VISIBLE);
            tvPromoError.setVisibility(View.GONE);
            error_promo_ll.setVisibility(View.GONE);
            promo_apply.setVisibility(View.GONE);
            et_promo_code.setTextColor(ResourcesCompat.getColor(getResources(), R.color.dark_gray, null));

        } else if (promos >= 1 && promos < 6) {
            et_promo_code.setEnabled(false);
            error_promo_ll.setVisibility(View.VISIBLE);
            sucess_promo_ll.setVisibility(View.GONE);
            promo_apply.setVisibility(View.GONE);
            et_promo_code.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));
            promo_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        int name_length = et_full_name.getText().toString().trim().length();
        int dob_length = et_dob.getText().toString().trim().length();
        int num_length = et_ph_no.getText().toString().trim().length();

        int email_length = et_email_address.getText().toString().trim().length();
        int pwd_length = et_password.getText().toString().trim().length();
        String pwdStr = et_password.getText().toString();


        tvEmailError.setVisibility(View.GONE);

        email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
        pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));


        int promo_length = et_promo_code.getText().toString().trim().length();

        if (email_length == 0) {
            email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
        } else {
            email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }

        if (pwd_length == 0) {
            pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
        } else {
            pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }

        if (promo_length == 0) {
            promo_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            promo_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
            if (signUpBonus != null && !signUpBonus.equals("")) {
                promo_apply.setVisibility(View.GONE);
            } else {
                promo_apply.setVisibility(View.GONE);
            }
        }

        if (name_length == 0) {
            name_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            name_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }
        if (dob_length == 0) {
            age_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            age_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }

        if (num_length == 0) {
            number_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));

        } else {
            number_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
        }

        emailValidation();
        passwordValidation(pwd_length, pwdStr);

        submitValidation(name_length, email_length, pwd_length, dob_length);

    }

    private void emailValidation() {
        String email_address = et_email_address.getText().toString().trim();


        if (email_address.matches(emailPattern)) {
            isEmailValid = true;
        } else {
            isEmailValid = false;
        }
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

    private void submitValidation(int name_length, int email_length, int pwd_length, int dob_length) {
        //if (name_length > 0 && email_length > 0 && pwd_length > 0 && dob_length > 0) {
        if (isEmailValid && passwordHasEightCharacters && passwordHasSpecialCharacter && passwordHasNumber && dob_length > 0) {
            tv_sign_up.setEnabled(true);
            tv_sign_up.setTextColor(getResources().getColor(R.color.white));
            tv_sign_up.setBackgroundResource(R.drawable.btn_bg_red);
        } else {
            tv_sign_up.setEnabled(false);
            tv_sign_up.setTextColor(getResources().getColor(R.color.btn_dis_text));
            tv_sign_up.setBackgroundResource(R.drawable.login_bg_disable);
        }


//        if (name_length > 0) {
//
//            tv_sign_up.setEnabled(true);
//            tv_sign_up.setTextColor(getResources().getColor(R.color.white));
//            tv_sign_up.setBackgroundResource(R.drawable.btn_bg_red);
//        } else {
//            tv_sign_up.setEnabled(false);
//            tv_sign_up.setTextColor(getResources().getColor(R.color.btn_dis_text));
//            tv_sign_up.setBackgroundResource(R.drawable.login_bg_disable);
//        }
    }

    private void showCalendar() {
        Calendar calendar1 = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PlayWithCash.this, R.style.MyDatePickerDialogTheme, listener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
        //    datePickerDialog.getDatePicker().setMinDate(calendar1.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

            et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));

            age = getAge(year, monthOfYear + 1, dayOfMonth);

//            Toast.makeText(getApplicationContext(),"a00--"+age,Toast.LENGTH_LONG).show();

            //    et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));

            if (age >= 18) {
                k_age = 2;
                et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));
            } else {
                k_age = 1;
//                et_dob.setText((Utilities.getMonthName((monthOfYear+1)) +" "+ dayOfMonth+", " + year));
//                Toast.makeText(getApplicationContext(),"a00--Error",Toast.LENGTH_LONG).show();
                showAlertBox(PlayWithCash.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
//                et_dob.setText("");
                et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));
            }
        }
    };

    private Integer getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageInt;
    }

    private JSONObject buildRequest() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("email", et_email_address.getText().toString().trim());
            jsonObj.put("password", et_password.getText().toString().trim());
            jsonObj.put("fullName", et_full_name.getText().toString().trim());
            if (et_dob.getText().toString().trim().equalsIgnoreCase("null")) {
                jsonObj.put("dob", "");
            } else {
                jsonObj.put("dob", et_dob.getText().toString().trim());
            }

            jsonObj.put("phoneNumber", et_ph_no.getText().toString().trim());
            jsonObj.put("countryCode", "1");
            jsonObj.put("promoCode", et_promo_code.getText().toString().trim());
            if (refCode != null || !refCode.isEmpty()) {
                jsonObj.put("userOrigin", refCode);
            } else {
                jsonObj.put("userOrigin", "none");
            }

            jsonObj.put("platformOrigin", getString(R.string.platform));
            jsonObj.put("signUpOrigin", signUpOrigin);

            if (k_age == 2) {
                jsonObj.put("signUpFor", "cash");
            } else if (k_age == 1) {

                jsonObj.put("signUpFor", "tokens");

            }

            jsonObj.put("referrerCode", "");
//            jsonObj.put("referrerCode", et_promo_code.getText().toString().trim());
//            jsonObj.put("referralCode", refCode);

            if (signUpOrigin != null && signUpOrigin.equals("facebook")) {
                JSONObject fbObject = new JSONObject();
                fbObject.put("email", et_email_address.getText().toString().trim());
                fbObject.put("id", userId);
                fbObject.put("token", userToken);
                fbObject.put("displayName", et_full_name.getText().toString().trim());
                jsonObj.put("facebook", fbObject);
            } else if (signUpOrigin != null && signUpOrigin.equals("google")) {
                JSONObject googleObject = new JSONObject();
                googleObject.put("email", et_email_address.getText().toString().trim());
                googleObject.put("id", userId);
                googleObject.put("token", userToken);
                googleObject.put("displayName", et_full_name.getText().toString().trim());
                jsonObj.put("google", googleObject);
            }

            JSONObject metrics_json = new JSONObject();
            metrics_json.put("platform", getString(R.string.platform));
            metrics_json.put("appVersion", BuildConfig.VERSION_NAME);

            jsonObj.put("metrics", metrics_json);
            JSONObject device_json = new JSONObject();
            device_json.put("type", getString(R.string.platform));
            device_json.put("deviceName", android.os.Build.MANUFACTURER);
            device_json.put("model", android.os.Build.MODEL);
            device_json.put("osversion", android.os.Build.VERSION.RELEASE);
            device_json.put("deviceToken", FCMToken);
            device_json.put("ipAddress", ip_address);

        //    jsonObj.put("device", device_json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    String mytoken;
    private void submitSignUpData() {

//        String email_address = et_email_address.getText().toString().trim();
//
//
//        if (email_address.matches(emailPattern)) {
        Log.e("sign", "" + buildRequest());
        AndroidNetworking.post(APIs.SIGN_UP_URL)
                .addJSONObjectBody(buildRequest()) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("***SIGNUP::", response.toString());

                        if (progressDialog != null)
                            progressDialog.dismiss();
                        try {


                            JSONObject signup_res = response.getJSONObject("account");
                            ResponsePojo responsePojo = new ResponsePojo();
                            responsePojo.set_id(signup_res.getString("_id"));
                            responsePojo.setEmail(signup_res.getString("email"));
                            responsePojo.setFullName(signup_res.getString("fullName"));
                            responsePojo.setCity(signup_res.getString("city"));
                            responsePojo.setState(signup_res.getString("state"));
                            responsePojo.setCountry(signup_res.getString("country"));
                            responsePojo.setUserPlayMode(signup_res.getString("userPlayMode"));
                            responsePojo.setTotalCashBalance(String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            responsePojo.setTotalCashBalance(signup_res.getString("totalCashBalance"));
                            responsePojo.setAvatar(signup_res.getString("avatar"));
                            responsePojo.setTokenBalance(String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            responsePojo.setTokenBalance(signup_res.getString("tokenBalance"));
                            responsePojo.setSessionToken(response.getString("token"));
                            responsePojo.setTotalWins(signup_res.getString("totalWins"));
                            responsePojo.setReferralCode(signup_res.getString("referralCode"));
                            responsePojo.setToken(response.getString("token"));
//                            responsePojo.setGender(signup_res.getString("gender"));
//                            responsePojo.setSportsPreference(signup_res.getString("sportsPreference"));

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                            cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                            cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                            cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                            cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                            cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                            cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                            cv.put(DB_Constants.USER_SESSIONTOKEN, response.getString("token"));
                            cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                            cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                            cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                            cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
                            cv.put(DB_Constants.USER_TOKEN, response.getString("token"));
                            cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                            cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                            cv.put(DB_Constants.USER_REF, signup_res.getString("referralCode"));
//                            cv.put(DB_Constants.USER_GENDER, signup_res.getString("gender"));
//                            cv.put(DB_Constants.USER_SPORTSPRE, signup_res.getString("sportsPreference"));

                            mydb.insertUserInfo(cv);

                            mytoken = response.getString("token");
                            JSONObject props = new JSONObject();

                            if (signUpOrigin != null && signUpOrigin.equals("facebook")) {
                                props.put("authType", "facebook");
                            } else if (signUpOrigin != null && signUpOrigin.equals("google")) {
                                props.put("authType", "google");
                            } else {
                                props.put("authType", "emailpassword");
                            }

                            mMixpanel.track("SignUp Complete", props);
                            trackEvent("SignUp Complete",props);

                            try {
                                JSONObject props_super = new JSONObject();
                                if (refCode != null) {
                                    props_super.put("userOrigin", refCode);
                                } else {
                                    props_super.put("userOrigin", "none");
                                }
                                props_super.put("Name", signup_res.getString("fullName"));
                                props_super.put("platformName", getString(R.string.platform));
//                                props_super.put("isReturn", "true");
                                props_super.put("appVersion", BuildConfig.VERSION_NAME);
                                props_super.put("accountType", "Cash");
                                mMixpanel.registerSuperPropertiesOnce(props_super);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if(signup_res.getString("userPlayMode").equalsIgnoreCase("cash")){

                                promoRedeemDialog("Start playing your fee $5 now","Jump into ProRodeo Pick and get started woth a $5 bonous!");
                            }else{

                                promoRedeemDialog("Start playing your fee 20 tokens now","Jump into ProRodeo Pick and get started woth a 20 tokens bonous!");
                            }

                            Intent _intent = null;
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
//                            if(!bundle.containsKey("SIGN_UP_BONUS")){
                                if (bundle.containsKey("card_guest")) {

                                    if (bundle.containsKey("card_city"))
                                        cv.put(DB_Constants.USER_CITY, bundle.getString("card_city"));
                                    if (bundle.containsKey("card_state"))
                                        cv.put(DB_Constants.USER_STATE, bundle.getString("card_state"));
                                    if (bundle.containsKey("card_country"))
                                        cv.put(DB_Constants.USER_COUNTRY, bundle.getString("card_country"));


                                    mydb.updateUser(cv);

//                                    _intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                                    _intent = new Intent(getApplicationContext(), UserLocation.class);
                                    _intent.putExtra("place_p", pp_s);
                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", mytoken);
                                    Ed.apply();

                                    finish();
                                } else {
                                    _intent = new Intent(getApplicationContext(), UserLocation.class);
                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", mytoken);
                                    Ed.apply();

                                }

                            } else {

                                _intent = new Intent(getApplicationContext(), UserLocation.class);
//                            _intent.putExtra("sessionToken", response.getString("token"));
//                            _intent.putExtra("userPlayMode", signup_res.getString("userPlayMode"));
//                            _intent.putExtra("avatar", signup_res.getString("avatar"));
//                            _intent.putExtra("amount_cash", signup_res.getString("totalCashBalance"));
//                            _intent.putExtra("amount_token", signup_res.getString("tokenBalance"));
//                            _intent.putExtra("wins", signup_res.getString("totalWins"));
//                            _intent.putExtra("acc_name", signup_res.getString("fullName"));
                                _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(_intent);

                                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("token", mytoken);
                                Ed.apply();
                                Ed.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "sign---error-------" + anError + "--" + anError.getErrorBody());

                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
                                //   String au = ej.getString("message");

                                Utilities.showToast(PlayWithCash.this, ej.getString("message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONObject props = new JSONObject();
                                props.put("returnedError", anError.getErrorBody());
                                mMixpanel.track("SignUp Error", props);
                                trackEvent("SignUp Error",props);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (anError.getErrorBody().contains("Email Address already exists")) {
                                et_email_address.requestFocus();
                                tvEmailError.setVisibility(View.VISIBLE);
                                email_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }else{
                                try {
                                    JSONObject ej = new JSONObject(anError.getErrorBody());
                                    //   String au = ej.getString("message");

                                    Utilities.showToast(PlayWithCash.this, ej.getString("message"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });
//        } else {
//            et_email_address.requestFocus();
//            tvEmailError.setText("Invalid Email Address");
//            tvEmailError.setVisibility(View.VISIBLE);
//            email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
//        }

    }


    // token

    private void submitSignUpToken() {

//        String email_address = et_email_address.getText().toString().trim();
//
//
//        if (email_address.matches(emailPattern)) {
        Log.e("sign", "" + buildRequest());
        AndroidNetworking.post(APIs.SIGN_UP_URL)
                .addJSONObjectBody(buildRequest()) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("***SIGNUP::", response.toString());

                        if (progressDialog != null)
                            progressDialog.dismiss();
                        try {


                            JSONObject signup_res = response.getJSONObject("account");
                            ResponsePojo responsePojo = new ResponsePojo();
                            responsePojo.set_id(signup_res.getString("_id"));
                            responsePojo.setEmail(signup_res.getString("email"));
                            responsePojo.setFullName(signup_res.getString("fullName"));
                            responsePojo.setCity(signup_res.getString("city"));
                            responsePojo.setState(signup_res.getString("state"));
                            responsePojo.setCountry(signup_res.getString("country"));
                            responsePojo.setUserPlayMode(signup_res.getString("userPlayMode"));
                            responsePojo.setTotalCashBalance(String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            responsePojo.setTotalCashBalance(signup_res.getString("totalCashBalance"));
                            responsePojo.setAvatar(signup_res.getString("avatar"));
                            responsePojo.setTokenBalance(String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            responsePojo.setTokenBalance(signup_res.getString("tokenBalance"));
                            responsePojo.setSessionToken(response.getString("token"));
                            responsePojo.setTotalWins(signup_res.getString("totalWins"));
                            responsePojo.setReferralCode(signup_res.getString("referralCode"));
                            responsePojo.setToken(response.getString("token"));


                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                            cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                            cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                            cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                            cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                            cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                            cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                            cv.put(DB_Constants.USER_SESSIONTOKEN, response.getString("token"));
                            cv.put(DB_Constants.USER_TOKEN, response.getString("token"));
                            cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE,String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                            cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                            cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                            cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
                            cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                            cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                            cv.put(DB_Constants.USER_REF, signup_res.getString("referralCode"));

                            mydb.insertUserInfo(cv);


                            JSONObject props = new JSONObject();

                            if (signUpOrigin != null && signUpOrigin.equals("facebook")) {
                                props.put("authType", "facebook");
                            } else if (signUpOrigin != null && signUpOrigin.equals("google")) {
                                props.put("authType", "google");
                            } else {
                                props.put("authType", "emailpassword");
                            }

                            mMixpanel.track("SignUp Complete", props);

                            trackEvent("SignUp Complete",props);
                            try {
                                JSONObject props_super = new JSONObject();
                                if (refCode != null) {
                                    props_super.put("userOrigin", refCode);
                                } else {
                                    props_super.put("userOrigin", "none");
                                }
                                props_super.put("Name", signup_res.getString("fullName"));
                                props_super.put("platformName", getString(R.string.platform));
//                                props_super.put("isReturn", "true");
                                props_super.put("appVersion", BuildConfig.VERSION_NAME);
                                props_super.put("accountType", "Tokens");
                                mMixpanel.registerSuperPropertiesOnce(props_super);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            mytoken = response.getString("token");


                            if(signup_res.getString("userPlayMode").equalsIgnoreCase("cash")){

                                promoRedeemDialog("Start playing your fee $5 now","Jump into ProRodeo Pick and get started woth a $5 bonous!");
                            }else{

                                promoRedeemDialog("Start playing your fee 20 tokens now","Jump into ProRodeo Pick and get started woth a 20 tokens bonous!");
                            }

                            Intent _intent = null;
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
//                            if(!bundle.containsKey("SIGN_UP_BONUS")){
                                if (bundle.containsKey("card_guest")) {

                                    if (bundle.containsKey("card_city"))
                                        cv.put(DB_Constants.USER_CITY, bundle.getString("card_city"));
                                    if (bundle.containsKey("card_state"))
                                        cv.put(DB_Constants.USER_STATE, bundle.getString("card_state"));
                                    if (bundle.containsKey("card_country"))
                                        cv.put(DB_Constants.USER_COUNTRY, bundle.getString("card_country"));


                                    mydb.updateUser(cv);

                                    _intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                                    _intent.putExtra("place_p", pp_s);
                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", response.getString("token"));
                                    Ed.putString("updatetoken", response.getString("token"));

                                    Ed.apply();

                                    finish();
                                } else {
                                    _intent = new Intent(getApplicationContext(), Dashboard.class);
                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", response.getString("token"));
                                    Ed.putString("updatetoken", response.getString("token"));

                                    Ed.apply();

                                }

                            } else {

                                _intent = new Intent(getApplicationContext(), Dashboard.class);
//                            _intent.putExtra("sessionToken", response.getString("token"));
//                            _intent.putExtra("userPlayMode", signup_res.getString("userPlayMode"));
//                            _intent.putExtra("avatar", signup_res.getString("avatar"));
//                            _intent.putExtra("amount_cash", signup_res.getString("totalCashBalance"));
//                            _intent.putExtra("amount_token", signup_res.getString("tokenBalance"));
//                            _intent.putExtra("wins", signup_res.getString("totalWins"));
//                            _intent.putExtra("acc_name", signup_res.getString("fullName"));
                                _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(_intent);

                                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("token", response.getString("token"));
                                Ed.putString("updatetoken", response.getString("token"));

                                Ed.apply();
                                Ed.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "sign---error-------" + anError + "--" + anError.getErrorBody());
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject props = new JSONObject();
                                props.put("returnedError", anError.getErrorBody());
                                mMixpanel.track("SignUp Error", props);
                                trackEvent("SignUp Error",props);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (anError.getErrorBody().contains("Email Address already exists")) {
                                et_email_address.requestFocus();
                                tvEmailError.setVisibility(View.VISIBLE);
                                email_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }else{
                                try {
                                    JSONObject ej = new JSONObject(anError.getErrorBody());
                                    //   String au = ej.getString("message");

                                    Utilities.showToast(PlayWithCash.this, ej.getString("message"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });
//        } else {
//            et_email_address.requestFocus();
//            tvEmailError.setText("Invalid Email Address");
//            tvEmailError.setVisibility(View.VISIBLE);
//            email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
//        }

    }

    private void promoRedeemDialog(String msg,String msg1) {
        ViewGroup viewGroup = findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(PlayWithCash.this).inflate(R.layout.dialog_promo_singup, viewGroup, false);


        Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
        ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);
        TextView contentMain = dialogView.findViewById(R.id.contentMain);
        TextView textViewContent = dialogView.findViewById(R.id.textViewContent);

        contentMain.setText(msg);
        textViewContent.setText(msg1);
        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


        final AlertDialog.Builder builder = new AlertDialog.Builder(PlayWithCash.this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
//        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

     //   Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
        alertDialog.show();



        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity(), PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
//                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

                alertDialog.dismiss();

            /*    Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);
*/

            }
        });



    }
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String idToken = account.getIdToken();
                // Signed in successfully, show authenticated UI.
                Log.e("GOOGLE TOKEN::", "::" + idToken);
                userToken = idToken;
                updateUI(account);
            } else {
                updateUI(null);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("PLAY WITH CASH", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount signInAccount) {
        try {
            if (signInAccount != null) {
                userEmail = signInAccount.getEmail();
                String firstName = signInAccount.getGivenName();
                String Last_name = signInAccount.getFamilyName();
                userFullName = firstName + " " + Last_name;
                if (userEmail != null && !userEmail.equals("")) {
                    et_full_name.setText(userFullName);
                    et_full_name.setSelection(userFullName.length());
                    et_email_address.setText(userEmail);
                    et_email_address.setEnabled(false);
                    signUpOrigin = "google";

                    if (progressDialog != null)
                        progressDialog.show();
                    submitSignUpData();
                }

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void configureFb() {

        mFacebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        String fb_email;
                                        String firstName;
                                        String lastName;
                                        try {
                                            userId = object.optString("id");
                                            fb_email = object.optString("email");
                                            firstName = object.optString("first_name");
                                            lastName = object.optString("last_name");

                                            userEmail = fb_email;
                                            userFullName = firstName + " " + lastName;
                                            if (!userEmail.equals("")) {
                                                et_full_name.setText(userFullName);
                                                et_full_name.setSelection(userFullName.length());
                                                et_email_address.setText(userEmail);
                                                et_email_address.setEnabled(false);
                                                signUpOrigin = "facebook";


                                                if (progressDialog != null)
                                                    progressDialog.show();
                                                submitSignUpData();

                                            }


                                            Log.d("data::", "Got info::" + userId + "#" + fb_email + "#" + firstName + "#" + lastName);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    private void configureGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(PlayWithCash.this, gso);
        googleSignInClient.signOut();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {


            if (AccessToken.getCurrentAccessToken() != null) {

                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                String fb_email;
                                String firstName;
                                String lastName;

                                try {
                                    Log.e("object", object.toString());
                                    Toast.makeText(PlayWithCash.this, object.toString(), Toast.LENGTH_SHORT).show();
                                    userId = object.optString("id");
                                    fb_email = object.optString("email");
                                    firstName = object.optString("first_name");
                                    lastName = object.optString("last_name");

                                    userEmail = fb_email;
                                    userFullName = firstName + " " + lastName;
                                    if (!userEmail.equals("")) {
                                        et_full_name.setText(userFullName);
                                        et_full_name.setSelection(userFullName.length());
                                        et_email_address.setText(userEmail);
                                        et_email_address.setEnabled(false);
                                        signUpOrigin = "facebook";
                                    }

                                    Log.d("data::", "Got info::" + userId + "#" + fb_email + "#" + firstName + "#" + lastName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email");
                request.setParameters(parameters);
                request.executeAsync();
            } else {
                mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void showAlertBox(final Context context, String title, String message) {

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


                //Text token
            }
        });

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }
}