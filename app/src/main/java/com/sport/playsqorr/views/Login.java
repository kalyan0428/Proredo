package com.sport.playsqorr.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;

public class Login extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private TextView tv_login, tv_error_msg;
    private EditText et_email_address, et_password;
    private View email_view, pwd_view;
    private CallbackManager mFacebookCallbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String userEmail, userFullName, userId;

    private static final int RC_SIGN_IN = 20;

    String emailPattern = "[a-zA-Z0-9._+]+@[a-z]+\\.+[a-z]+";

    private DataBaseHelper mydb;
    MixpanelAPI mMixpanel;
    private String FCMToken;

    ProgressDialog progressDialog;
    TextView toolbar_title_x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);

        mydb = new DataBaseHelper(this);

        trackEvent("Page SignIn",null);

        mMixpanel = MixpanelAPI.getInstance(Login.this, getString(R.string.test_MIX_PANEL_TOKEN));
        mMixpanel.track("Page SignIn", null);
        SharedPreferences sharedPreferences = getSharedPreferences("FCM_TOKEN", MODE_PRIVATE);
        FCMToken = sharedPreferences.getString("fcm_token", "");

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        initViews();
        configureFb();
        configureGoogleLogin();

    }

    private void configureGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
                                                handleSocialUser("facebook", userEmail);
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

    @SuppressLint("SetTextI18n")
    private void initViews() {
         toolbar_title_x = findViewById(R.id.toolbar_title_x);
        et_email_address = findViewById(R.id.et_email_address);
        et_password = findViewById(R.id.et_password);
        tv_login = findViewById(R.id.tv_login);
        TextView tv_forgot_pwd = findViewById(R.id.tv_forgot_pwd);
        tv_error_msg = findViewById(R.id.tv_error_msg);
        email_view = findViewById(R.id.email_view);
        pwd_view = findViewById(R.id.pwd_view);
        CardView cvFacebook = findViewById(R.id.cvFacebook);
        CardView cvGmail = findViewById(R.id.cvGmail);

        toolbar_title_x.setText("Login ");

        //Add listener(s)
        toolbar_title_x.setOnClickListener(this);
        et_password.addTextChangedListener(this);
        et_email_address.addTextChangedListener(this);
        tv_forgot_pwd.setOnClickListener(this);
        tv_login.setEnabled(false);
        tv_login.setOnClickListener(this);
        cvFacebook.setOnClickListener(this);
        cvGmail.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//
                finish();
                break;
            case R.id.tv_login:

                tv_login.setBackgroundResource(R.drawable.btn_bg_red_ripple);
                if(progressDialog!=null)
                    progressDialog.show();
                loginData();


                break;
            case R.id.tv_forgot_pwd:
                Intent passwordRecoveryIntent = new Intent(Login.this, PasswordRecovery.class);
                passwordRecoveryIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(passwordRecoveryIntent);
                break;
            case R.id.cvFacebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
            case R.id.cvGmail:
                googleSignIn();
                break;
            default:
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loginData() {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        String email_address = et_email_address.getText().toString().trim();
        String userPwd = et_password.getText().toString().trim();

        if (email_address.matches(emailPattern)) {
            JSONObject login_json = new JSONObject();

            try {
                login_json.put("email", email_address);
                login_json.put("password", userPwd);

                JSONObject metrics_json = new JSONObject();
                metrics_json.put("platform", getString(R.string.platform));
                metrics_json.put("appVersion", BuildConfig.VERSION_NAME);

                login_json.put("metrics", metrics_json);

                JSONObject device_json = new JSONObject();
                device_json.put("type", getString(R.string.platform));
                device_json.put("deviceName", android.os.Build.MANUFACTURER);
                device_json.put("model", android.os.Build.MODEL);
                device_json.put("osversion", android.os.Build.VERSION.RELEASE);
                device_json.put("deviceToken", FCMToken);
                device_json.put("ipAddress", ip_address);
//                login_json.put("device", device_json);

            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Utilities.showAlertBoxTrans(Login.this,"json object",login_json.toString());


            if (Utilities.isNetworkAvailable(getApplicationContext())) {
                Log.e("LoginRequest::", login_json.toString());
                AndroidNetworking.post(APIs.LOGIN)
                        .addJSONObjectBody(login_json) // posting json
                        .addHeaders("Content-Type", "application/json")
                        .setPriority(Priority.IMMEDIATE)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("LoginREs::", response.toString());
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
//                                    responsePojo.setTotalCashBalance(signup_res.getString("totalCashBalance"));
                                    responsePojo.setAvatar(signup_res.getString("avatar"));
                                    responsePojo.setTokenBalance(String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                                    responsePojo.setTokenBalance(signup_res.getString("tokenBalance"));
                                    responsePojo.setTotalWins(signup_res.getString("totalWins"));
                                    responsePojo.setReferralCode(signup_res.getString("referralCode"));
                                    responsePojo.setGender(signup_res.getString("gender"));
                                    responsePojo.setSportsPreference(signup_res.getString("sportsPreference"));


                                    responsePojo.setSessionToken(response.getString("token"));
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
//                                    cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                                    cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                                    cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                                    cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                                    cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
//                                    cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                                    cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                                    cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                                    cv.put(DB_Constants.USER_REF, signup_res.getString("referralCode"));
                                    cv.put(DB_Constants.USER_GENDER, signup_res.getString("gender"));
                                    cv.put(DB_Constants.USER_SPORTSPRE, signup_res.getString("sportsPreference"));

                                    mydb.insertUserInfo(cv);

                                    Intent _intent = null;
                                    Bundle bundle = getIntent().getExtras();
                                    if (bundle != null) {
                                        if (bundle.containsKey("cardid")) {
                                            SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor Ed = sp.edit();
                                            Ed.putString("token", response.getString("token"));
                                            Ed.putString("updatetoken", response.getString("token"));
                                            Ed.apply();
                                            Dashboard.SESSIONTOKEN = response.getString("token");
                                            finish();
                                        }
                                    } else {
                                        _intent = new Intent(getApplicationContext(), Dashboard.class);
                                        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(_intent);

                                        //Save user Session token
                                        SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor Ed = sp.edit();
                                        Ed.putString("token", response.getString("token"));
                                        Ed.putString("updatetoken", response.getString("token"));
                                        Ed.apply();
                                    }

                                    /*Intent _intent = new Intent(getApplicationContext(), Dashboard.class);

                                    _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(_intent);

                                    //Save user Session token
                                    SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor Ed = sp.edit();
                                    Ed.putString("token", response.getString("token"));
                                    Ed.apply();*/
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    if(progressDialog!=null)
                                        progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                                if(progressDialog!=null)
                                    progressDialog.dismiss();
                                if (anError.getErrorCode() != 0) {
                                    Log.d("", "onError errorCode : " + anError.getErrorCode());
                                    Log.d("", "onError errorBody : " + anError.getErrorBody());
                                    Log.d("", "onError errorDetail : " + anError.getErrorDetail());
                                    if(anError.getErrorCode() == 403){
                                        Utilities.showToast(Login.this, getString(R.string.servererror_msg));
                                    }

                                    if (anError.getErrorBody().contains("The email or password is incorrect")) {
                                        et_email_address.requestFocus();
                                        tv_error_msg.setVisibility(View.VISIBLE);

                                        email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));
                                        pwd_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sqorr_red, null));

                                    } else {
                                        Utilities.showToast(Login.this, anError.getErrorDetail());
                                    }
                                } else {
                                    Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                                }

                            }
                        });
            } else {
                Utilities.showNoInternetAlert(Login.this);
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }

        } else {
            if(progressDialog!=null)
                progressDialog.dismiss();
            et_email_address.requestFocus();
            tv_error_msg.setText("Invalid Email Address");
            tv_error_msg.setVisibility(View.VISIBLE);
            email_view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


        int email_length = et_email_address.getText().toString().trim().length();
        int pwd_length = et_password.getText().toString().trim().length();


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

    }

    @Override
    public void afterTextChanged(Editable s) {
        tv_error_msg.setVisibility(View.GONE);
        int email_length = et_email_address.getText().toString().trim().length();
        int pwd_length = et_password.getText().toString().trim().length();
        if (email_length > 0 && pwd_length > 0) {
            tv_login.setEnabled(true);
            tv_login.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            tv_login.setBackgroundResource(R.drawable.btn_bg_red);
        } else {
            tv_login.setEnabled(false);
            tv_login.setTextColor(ResourcesCompat.getColor(getResources(), R.color.btn_dis_text, null));
            tv_login.setBackgroundResource(R.drawable.login_bg_disable);

        }
    }
    //Text

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

                                String fb_email = null;
                                String firstName = null;
                                String lastName = null;
                                String id = null;
                                try {
                                    userId = object.optString("id");
                                    fb_email = object.optString("email");
                                    firstName = object.optString("first_name");
                                    lastName = object.optString("last_name");

                                    userEmail = fb_email;
                                    userFullName = firstName + " " + lastName;
                                    if (!userEmail.equals("")) {
                                        handleSocialUser("facebook", userEmail);

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

        //mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateUI(GoogleSignInAccount signInAccount) {
        try {
            if (signInAccount != null) {
                userEmail = signInAccount.getEmail();
                String firstName = signInAccount.getGivenName();
                String Last_name = signInAccount.getFamilyName();
                userFullName = firstName + " " + Last_name;
                if (userEmail != null && !userEmail.equals(""))
                    handleSocialUser("google", userEmail);
            }
        } catch (Exception e) {
            e.getStackTrace();
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
                updateUI(account);
            } else {
                updateUI(null);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LoginScreen", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void navUserToSignUp() {
        SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("user_email", userEmail);
        Ed.putString("user_name", userFullName);
        Ed.apply();

        Intent signupIntent = new Intent(Login.this, PlayWithCash.class);
//        Intent signupIntent = new Intent(Login.this, Signup.class);
        signupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        signupIntent.putExtra("fromPage", "LOGIN_FB_G");
        startActivity(signupIntent);
    }


    private void handleSocialUser(String socialLoginName, String userEmail) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", userEmail);
            jsonObject.put("source", socialLoginName);

            JSONObject metricsObj = new JSONObject();
            metricsObj.put("platform", getString(R.string.platform));
            metricsObj.put("appVersion", BuildConfig.VERSION_NAME);

            jsonObject.put("metrics", metricsObj);

            JSONObject device_json = new JSONObject();
            device_json.put("type", getString(R.string.platform));
            device_json.put("deviceName", android.os.Build.MANUFACTURER);
            device_json.put("model", android.os.Build.MODEL);
            device_json.put("osversion", android.os.Build.VERSION.RELEASE);
            device_json.put("deviceToken", FCMToken);

            jsonObject.put("device", device_json);


            sendSocialLoginDataToServer(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendSocialLoginDataToServer(JSONObject jsonObject) {

        if (Utilities.isNetworkAvailable(getApplicationContext())) {

            AndroidNetworking.post(APIs.SOCIAL_LOGIN_URL)
                    .addJSONObjectBody(jsonObject) // posting json
                    .addHeaders("Content-Type", "application/json")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("***SOCIAL LOGIN: :", response.toString());

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
//                                responsePojo.setTotalCashBalance(signup_res.getString("totalCashBalance"));
                                responsePojo.setSessionToken(response.getString("token"));
                                responsePojo.setReferralCode(signup_res.getString("referralCode"));

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
//                                cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                                cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                                cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                                cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                                cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
                                cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                                cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                                cv.put(DB_Constants.USER_REF, signup_res.getString("referralCode"));
                                mydb.insertUserInfo(cv);

                                Intent _intent = new Intent(getApplicationContext(), Dashboard.class);
//                                _intent.putExtra("sessionToken", response.getString("sessionToken"));
//                                _intent.putExtra("userrole", signup_res.getString("userPlayMode"));
//                                _intent.putExtra("avatar", signup_res.getString("avatar"));
//                                _intent.putExtra("amount_cash", signup_res.getString("totalCashBalance"));
//                                _intent.putExtra("amount_token", signup_res.getString("tokenBalance"));
//                                _intent.putExtra("wins", signup_res.getString("totalWins"));
//                                _intent.putExtra("acc_name", signup_res.getString("fullName"));
                                _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(_intent);

                                //Save user Session token
                                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor Ed = sp.edit();
                                Ed.putString("token", response.getString("token"));
                                Ed.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                            if (anError.getErrorCode() != 0) {
                                Log.d("", "onError errorCode : " + anError.getErrorCode());
                                Log.d("", "onError errorBody : " + anError.getErrorBody());
                                Log.d("", "onError errorDetail : " + anError.getErrorDetail());

                                if (anError.getErrorBody().contains("No Account found. Please do Signup.")) {
                                    navUserToSignUp();
                                } else {
                                    Utilities.showToast(Login.this, anError.getErrorBody());
                                }

                            } else {
                                Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                            }

                        }
                    });
        } else {
            Utilities.showNoInternetAlert(Login.this);

        }

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }

}