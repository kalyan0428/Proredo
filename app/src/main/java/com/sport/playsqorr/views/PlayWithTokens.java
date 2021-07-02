package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
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


import java.util.Calendar;

import java.util.Arrays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;

public class
PlayWithTokens extends AppCompatActivity implements View.OnClickListener, TextWatcher {


    private EditText et_password, et_email_address, et_promo_tokens, et_dob;
    private TextView tv_sign_up, tvAtLeastEightChars, tvAtLeastOneNumber, tvOnceSpecialChar, tvEmailError, tvPromoError, tvPromoS, promo_apply, close_promo_e, close_promo_s;
    private View email_view, promo_view, pwd_view;
    private boolean isEmailValid, passwordHasNumber, passwordHasSpecialCharacter, passwordHasEightCharacters;


    private String userId, userToken;
    private String signUpOrigin = "emailpassword";
    private CallbackManager mFacebookCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String userEmail, userFullName;

    LinearLayout error_promo_ll, sucess_promo_ll;
    private static final int RC_SIGN_IN = 20;

    private DataBaseHelper mydb;

    String emailPattern = "[a-zA-Z0-9._+-]+@[a-z]+\\.+[a-z]+";
    MixpanelAPI mMixpanel;

    private String FCMToken;
    ProgressDialog progressDialog;

    public  static  String ppto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_tokens);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        mydb = new DataBaseHelper(this);
        mMixpanel = MixpanelAPI.getInstance(PlayWithTokens.this, getString(R.string.test_MIX_PANEL_TOKEN));


        SharedPreferences sharedPreferences = getSharedPreferences("FCM_TOKEN", MODE_PRIVATE);
        FCMToken= sharedPreferences.getString("fcm_token", "");

        progressDialog = new ProgressDialog(PlayWithTokens.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        init();
        getUserData();
        configureFb();
        configureGoogleLogin();
    }

    private void init() {
        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        TextView already_title_x = findViewById(R.id.already_have_account);

        et_email_address = findViewById(R.id.et_email_address);
        et_promo_tokens = findViewById(R.id.promo_tokens);
        et_password = findViewById(R.id.et_password);
        et_dob = findViewById(R.id.et_dob);
        tv_sign_up = findViewById(R.id.tv_sign_up);
        tvAtLeastEightChars = findViewById(R.id.tvAtLeastEightChars);
        tvAtLeastOneNumber = findViewById(R.id.tvAtLeastOneNumber);
        tvOnceSpecialChar = findViewById(R.id.tvOnceSpecialChar);
        tvEmailError = findViewById(R.id.tvEmailError);
        email_view = findViewById(R.id.email_view);
        pwd_view = findViewById(R.id.pwd_view);
        tvPromoError = findViewById(R.id.tvPromoError);
        promo_view = findViewById(R.id.promo_view);
        tvPromoS = findViewById(R.id.tvPromos);
        promo_apply = findViewById(R.id.promo_apply);
        error_promo_ll = findViewById(R.id.error_promo_ll);
        sucess_promo_ll = findViewById(R.id.sucess_promo_ll);
        close_promo_e = findViewById(R.id.close_promo_e);
        close_promo_s = findViewById(R.id.close_promo_s);

        toolbar_title_x.setText(getString(R.string.sign_up));
        CardView cvFacebook = findViewById(R.id.cvFacebook);
        CardView cvGmail = findViewById(R.id.cvGmail);

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        already_title_x.setOnClickListener(this);

        tvPromoS.addTextChangedListener(this);
        et_password.addTextChangedListener(this);

        et_email_address.addTextChangedListener(this);
        et_promo_tokens.addTextChangedListener(this);
        tv_sign_up.setEnabled(false);


        et_dob.setOnClickListener(this);
        close_promo_e.setOnClickListener(this);
        close_promo_s.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        promo_apply.setOnClickListener(this);

        cvFacebook.setOnClickListener(this);
        cvGmail.setOnClickListener(this);

    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        String uEmail = sharedPreferences.getString("user_email", "");
        et_email_address.setText(uEmail);
        et_email_address.setSelection(uEmail.length());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;
            case R.id.close_promo_e:
                et_promo_tokens.setText("");
                et_promo_tokens.setEnabled(true);

                error_promo_ll.setVisibility(View.GONE);
                break;
            case R.id.close_promo_s:
                et_promo_tokens.setText("");
                et_promo_tokens.setEnabled(true);

                sucess_promo_ll.setVisibility(View.GONE);
                break;
            case R.id.promo_apply:

/*

                int promos = et_promo_tokens.getText().toString().trim().length();
                if (promos == 0) {
                } else if (promos >= 6) {
                    et_promo_tokens.setEnabled(false);
                    tvPromoS.setText("$10 Bonus cash on signup");
                    sucess_promo_ll.setVisibility(View.VISIBLE);
                    tvPromoError.setVisibility(View.GONE);
                    error_promo_ll.setVisibility(View.GONE);
                    promo_apply.setVisibility(View.GONE);
                    et_promo_tokens.setTextColor(ResourcesCompat.getColor(getResources(), R.color.green, null));

                } else if (promos >= 1 && promos < 6) {
                    et_promo_tokens.setEnabled(false);
                    error_promo_ll.setVisibility(View.VISIBLE);
                    sucess_promo_ll.setVisibility(View.GONE);
                    promo_apply.setVisibility(View.GONE);
                    et_promo_tokens.setTextColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));
                    promo_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                }
*/

                break;
            case R.id.tv_login:
                Toast.makeText(PlayWithTokens.this, "Navigate user to dashboard", Toast.LENGTH_LONG).show();

                break;
            case R.id.already_have_account:
                Intent in = new Intent(PlayWithTokens.this, Login.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
            case R.id.tv_sign_up:
                tv_sign_up.setBackgroundResource(R.drawable.btn_bg_red_ripple);
                if(progressDialog!=null)
                    progressDialog.show();
                submitSignUpData();

                break;

            case R.id.et_dob:
                showCalendar();

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

    private void showCalendar() {
        Calendar calendar1 = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(PlayWithTokens.this, R.style.MyDatePickerDialogTheme, listener, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
        //    datePickerDialog.getDatePicker().setMinDate(calendar1.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {


            et_dob.setText((Utilities.getMonthName((monthOfYear + 1)) + " " + dayOfMonth + ", " + year));

            /*Integer age = getAge(year,monthOfYear+1,dayOfMonth);
            Toast.makeText(getApplicationContext(),"a00--"+age,Toast.LENGTH_LONG).show();
            if(age >= 21){
                et_dob.setText((Utilities.getMonthName((monthOfYear+1)) +" "+ dayOfMonth+", " + year));
            }else{
//                et_dob.setText((Utilities.getMonthName((monthOfYear+1)) +" "+ dayOfMonth+", " + year));
                Toast.makeText(getApplicationContext(),"a00--Error",Toast.LENGTH_LONG).show();
            }*/
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

    private void submitSignUpData() {

        //String email_address = et_email_address.getText().toString().trim();


        //if (email_address.matches(emailPattern)) {

        AndroidNetworking.post(APIs.SIGN_UP_URL)
                .addJSONObjectBody(buildRequest()) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("***SIGNUP: Token:", response.toString());
                        if(progressDialog!=null)
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
//                            responsePojo.setTokenBalance(signup_res.getString("tokenBalance"));
                            responsePojo.setTokenBalance(String.format ("%.0f", signup_res.getDouble("tokenBalance")));
                            responsePojo.setSessionToken(response.getString("sessionToken"));
                            responsePojo.setTotalWins(signup_res.getString("totalWins"));
                            responsePojo.setReferralCode(signup_res.getString("referralCode"));


                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                            cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                            cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                            cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                            cv.put(DB_Constants.USER_CITY, "");
                            cv.put(DB_Constants.USER_STATE, "");
                            cv.put(DB_Constants.USER_COUNTRY, "");
                            cv.put(DB_Constants.USER_SESSIONTOKEN, response.getString("sessionToken"));
                            cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
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
                            }else if (signUpOrigin != null && signUpOrigin.equals("google")) {
                                props.put("authType", "google");
                            }else{
                                props.put("authType", "emailpassword");
                            }

                            mMixpanel.track("SignUp Complete", props);

                            trackEvent("SignUp Complete",props);


                            try {
                                JSONObject props_super = new JSONObject();

                                    props_super.put("userOrigin", "none");

                                props_super.put("Name", signup_res.getString("fullName"));
                                props_super.put("platformName", getString(R.string.platform));
//                                props_super.put("isReturn", "true");
                                props_super.put("appVersion", BuildConfig.VERSION_NAME);
                                props_super.put("accountType", "Tokens");
                                mMixpanel.registerSuperPropertiesOnce(props_super);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            Intent _intent = null;
                            Bundle bundle = getIntent().getExtras();
                            if (bundle != null) {
                                if (bundle.containsKey("card_guest")) {


                                    _intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                                    _intent.putExtra("place_p", "M");
                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", response.getString("sessionToken"));
                                    Ed.apply();
                                    Ed.commit();

//                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor Ed = sp.edit();
//                                    Ed.putString("token", response.getString("sessionToken"));
//                                    Ed.apply();
//                                    Dashboard.SESSIONTOKEN= response.getString("sessionToken");
                                    finish();
                                }
                            } else {
                                _intent = new Intent(getApplicationContext(), Dashboard.class);
                                ppto = "t1";
//                            _intent.putExtra("sessionToken", response.getString("sessionToken"));
//                            _intent.putExtra("userrole", signup_res.getString("userPlayMode"));
//                            _intent.putExtra("avatar", signup_res.getString("avatar"));
//                            _intent.putExtra("amount_cash", signup_res.getString("totalCashBalance"));
//                            _intent.putExtra("amount_token", signup_res.getString("tokenBalance"));
//                            _intent.putExtra("wins", signup_res.getString("totalWins"));
//                            _intent.putExtra("acc_name", signup_res.getString("fullName"));
                                _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(_intent);
                                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("token", response.getString("sessionToken"));
                                Ed.apply();
                                Ed.commit();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if(progressDialog!=null)
                                progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "Login----error-------" + anError);

                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        if (anError.getErrorCode() != 0) {
                            Log.d("", "onError errorCode : " + anError.getErrorCode());
                            Log.d("", "onError errorBody : " + anError.getErrorBody());
                            Log.d("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject props = new JSONObject();
                                props.put("returnedError",  anError.getErrorBody());
                                mMixpanel.track("SignUp Error", props);
                                trackEvent("SignUp Error",props);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (anError.getErrorBody().contains("Email Address already exists")) {
                                et_email_address.requestFocus();
                                tvEmailError.setVisibility(View.VISIBLE);
                                email_view.setBackgroundColor(getResources().getColor(R.color.colorAccent));


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

    private JSONObject buildRequest() {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        JSONObject jsonObj = new JSONObject();

        try {


            jsonObj.put("email", et_email_address.getText().toString().trim());
            jsonObj.put("password", et_password.getText().toString().trim());
//            jsonObj.put("fullName", et_full_name.getText().toString().trim());
            jsonObj.put("dob", "");
            jsonObj.put("promoCode", et_promo_tokens.getText().toString().trim());
            jsonObj.put("userOrigin", "none");
            jsonObj.put("platformOrigin", getString(R.string.platform));
            jsonObj.put("signUpOrigin", signUpOrigin);
            jsonObj.put("signUpFor", "token");
            jsonObj.put("referralCode", et_promo_tokens.getText().toString().trim());

           /* if (signUpOrigin != null && !signUpOrigin.equals("EMAIL")) {
                JSONObject fbObject = new JSONObject();
                fbObject.put("email", et_email_address.getText().toString().trim());
                fbObject.put("id", userId);
                fbObject.put("token", userToken);
                fbObject.put("displayName", userFullName);
                jsonObj.put("facebook", fbObject);
            }*/
            if (signUpOrigin != null && signUpOrigin.equals("facebook")) {
                JSONObject fbObject = new JSONObject();
                fbObject.put("email", et_email_address.getText().toString().trim());
                fbObject.put("id", userId);
                fbObject.put("token", userToken);
                fbObject.put("displayName", userFullName);
                jsonObj.put("facebook", fbObject);
            }else if (signUpOrigin != null && signUpOrigin.equals("google")) {
                JSONObject googleObject = new JSONObject();
                googleObject.put("email", et_email_address.getText().toString().trim());
                googleObject.put("id", userId);
                googleObject.put("token", userToken);
                googleObject.put("displayName", userFullName);
                jsonObj.put("google", googleObject);
            }


            JSONObject metrics_json = new JSONObject();
            metrics_json.put("platform", getString(R.string.platform));
            metrics_json.put("appVersion", BuildConfig.VERSION_NAME);

            jsonObj.put("metrics", metrics_json);


            JSONObject device_json = new JSONObject();
            device_json.put("type", getString(R.string.platform));
            device_json.put("deviceName", android.os.Build.MANUFACTURER );
            device_json.put("model", android.os.Build.MODEL);
            device_json.put("osversion",android.os.Build.VERSION.RELEASE);
            device_json.put("deviceToken", FCMToken);
            device_json.put("ipAddress", ip_address);

            jsonObj.put("device", device_json);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        tvEmailError.setVisibility(View.GONE);

        email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
        pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
        int email_length = et_email_address.getText().toString().trim().length();


        int pwd_length = et_password.getText().toString().trim().length();
        String pwdStr = et_password.getText().toString();

        int promo_length = et_promo_tokens.getText().toString().trim().length();

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

//        if (promo_length == 0) {
//            promo_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
//
//        } else {
//            promo_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.medium_gray, null));
//            promo_apply.setVisibility(View.VISIBLE);
//        }

        emailValidation();
        passwordValidation(pwd_length, pwdStr);
        submitValidation();


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
            tvOnceSpecialChar.getTextColors().describeContents();
            tvOnceSpecialChar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wrong, 0, 0, 0);
            passwordHasSpecialCharacter = false;
        }


    }

    private void submitValidation() {


        //if (email_length > 0 && pwd_length > 0) {
        if (isEmailValid && passwordHasSpecialCharacter && passwordHasEightCharacters && passwordHasNumber) {
            tv_sign_up.setEnabled(true);
            tv_sign_up.setTextColor(getResources().getColor(R.color.white));
            tv_sign_up.setBackgroundResource(R.drawable.btn_bg_red);
        } else {
            tv_sign_up.setEnabled(false);
            tv_sign_up.setTextColor(getResources().getColor(R.color.btn_dis_text));
            tv_sign_up.setBackgroundResource(R.drawable.login_bg_disable);
        }
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


        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(PlayWithTokens.this,gso);
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
                                    userId = object.optString("id");
                                    fb_email = object.optString("email");
                                    firstName = object.optString("first_name");
                                    lastName = object.optString("last_name");

                                    userEmail = fb_email;
                                    userFullName = firstName + " " + lastName;
                                    if (!userEmail.equals("")) {
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
    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }

}