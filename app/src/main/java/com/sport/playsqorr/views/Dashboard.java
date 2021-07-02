package com.sport.playsqorr.views;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonElement;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.sport.playsqorr.Api.API_class;
import com.sport.playsqorr.Api.Retrofit_funtion_class;
import com.sport.playsqorr.SensorService;
import com.sport.playsqorr.utilities.APIs;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.fragments.CardSFrag;
import com.sport.playsqorr.fragments.HomeFrag;
import com.sport.playsqorr.fragments.PromosFragment;
import com.sport.playsqorr.fragments.SqorrTvFragment;
import com.sport.playsqorr.utilities.Utilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;
import static com.sport.playsqorr.utilities.UtilitiesAna.trackEventRegister;


public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    public static String ROLE = "";
    public static String SESSIONTOKEN = "";
    public static String NEWTOKEN = "";
    public static String AMOUNT_TOKEN = "";
    public static String AMOUNT_CASH;
    public static String CASH_BAL = "";
    public static String AVATAR = "";
    public static String MYWiNS = "";
    public static String ACCNAME = "";
    public static String ACCEMAIL = "";
    public static String ACCREF = "";
    public static String ACCNUMBER = "";
    public static String v_status = "";

    private ImageView ivUserAvatar;
    private TextView tvUserWins, tvTokens, tvUserCash;
    public static DataBaseHelper myDbHelper;
    private View guestUserHeader, loggedUserHeader;
    private LinearLayout llAddFunds, llUserTokens;
    MixpanelAPI mMixpanel;
    Fragment someFragment;

//        public static String ppp="2";

    String tamount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);


        myDbHelper = new DataBaseHelper(getApplicationContext());

        mMixpanel = MixpanelAPI.getInstance(Dashboard.this, getString(R.string.test_MIX_PANEL_TOKEN));

        initializeComponents();
        setHome();


    }

    private void initializeComponents() {

        ImageView ivUsersSettings = findViewById(R.id.ivSettings);
        ivUserAvatar = findViewById(R.id.ivUserAvatar);
        LinearLayout llUserAvatar = findViewById(R.id.llUserAvatar);
        LinearLayout llJoinNow = findViewById(R.id.llJoinNow);
        LinearLayout llJoinLogin = findViewById(R.id.llJoinLogin);
        tvUserWins = findViewById(R.id.tvUserWins);
        tvTokens = findViewById(R.id.tvUserTokens);
        guestUserHeader = findViewById(R.id.guestUserHeader);
        loggedUserHeader = findViewById(R.id.loggedUserHeader);
        tvUserCash = findViewById(R.id.tvUserCash);
        llUserTokens = findViewById(R.id.llUserTokens);
        llAddFunds = findViewById(R.id.llAddFunds);

        TextView htxt_tv = findViewById(R.id.hometxt);
        TextView cardtxt_tv = findViewById(R.id.cardtxt);
        TextView tvtxt_tv = findViewById(R.id.tvtxt);
        TextView protxt_tv = findViewById(R.id.promotxt);
        Typeface tf_htxt = Typeface.createFromAsset(getApplicationContext().getAssets(), "Exo_SemiBold.ttf");
        htxt_tv.setTypeface(tf_htxt);
        cardtxt_tv.setTypeface(tf_htxt);
        tvtxt_tv.setTypeface(tf_htxt);
        protxt_tv.setTypeface(tf_htxt);

        findViewById(R.id.footer1).setSelected(true);
        findViewById(R.id.footer2).setSelected(false);
        findViewById(R.id.footer3).setSelected(false);
        findViewById(R.id.footer4).setSelected(false);

        //Add listener(s)
        ivUsersSettings.setOnClickListener(this);
        llJoinNow.setOnClickListener(this);
        llJoinLogin.setOnClickListener(this);
        llAddFunds.setOnClickListener(this);
        llUserAvatar.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    private void setPageData() {
        SharedPreferences prefs = getSharedPreferences("SESSION_TOKEN", MODE_PRIVATE);
        if (prefs != null) {

            String restoredText = prefs.getString("token", null);

            String updatetokenTest = prefs.getString("updatetoken", null);
            if (restoredText != null) {


                if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // If cash user

                    guestUserHeader.setVisibility(View.GONE);
                    loggedUserHeader.setVisibility(View.VISIBLE);
                    llAddFunds.setVisibility(View.VISIBLE);
                    llUserTokens.setVisibility(View.GONE);

//                    double cd = Double.parseDouble(AMOUNT_CASH);
//                    tvUserCash.setText("$" + cd);
                    tvUserCash.setText("$" + AMOUNT_CASH);
                    tvUserWins.setText(MYWiNS);
                    try {
                        JSONObject props = new JSONObject();
                        props.put("userType", "Cash User");
                        mMixpanel.identify(ACCEMAIL);
                        mMixpanel.track("Page User Home", props);

                        trackEvent("Page User Home",props);

                        JSONObject props_register = new JSONObject();
                        props_register.put("first_name", ACCNAME);
                        props_register.put("email", ACCEMAIL);
                        trackEventRegister("",props_register);


                        mMixpanel.getPeople().identify(ACCEMAIL);
                        // Sets user 13793's "Plan" attribute to "Premium"
//                    mMixpanel.getPeople().set("full_name", ACCNAME);
                        mMixpanel.getPeople().set("email", ACCEMAIL);
                        if (ACCNUMBER != null && !ACCNUMBER.isEmpty()) {
                            mMixpanel.getPeople().set("phone", ACCNUMBER);

                        } else {
                            mMixpanel.getPeople().set("phone", "");

                        }
                        mMixpanel.getPeople().set("accountType", "Cash User");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    setUserAvatar();

                } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { //if tokens user

                    tvTokens.setText(tamount);
                    guestUserHeader.setVisibility(View.GONE);
                    loggedUserHeader.setVisibility(View.VISIBLE);
                    llAddFunds.setVisibility(View.GONE);
                    llUserTokens.setVisibility(View.VISIBLE);
                    tvUserWins.setText(MYWiNS);
                    try {
                        JSONObject props = new JSONObject();
                        props.put("userType", "Token User");
                        mMixpanel.identify(ACCEMAIL);
                        mMixpanel.track("Page User Home", props);

                        trackEvent("Page User Home",props);

                        // regiter
                        JSONObject props_register = new JSONObject();
                        props_register.put("first_name", ACCNAME);
                        props_register.put("email", ACCEMAIL);
                        trackEventRegister("",props_register);

                        mMixpanel.getPeople().identify(ACCEMAIL);
                        // Sets user 13793's "Plan" attribute to "Premium"
//                    mMixpanel.getPeople().set("full_name", ACCNAME);
                        mMixpanel.getPeople().set("email", ACCEMAIL);
                        if (ACCNUMBER != null && !ACCNUMBER.isEmpty()) {
                            mMixpanel.getPeople().set("phone", ACCNUMBER);

                        } else {
                            mMixpanel.getPeople().set("phone", "");

                        }
                        mMixpanel.getPeople().set("accountType", "Token User");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setUserAvatar();
                }
            } else {
                // if guest user
                ROLE = "0";
                guestUserHeader.setVisibility(View.VISIBLE);
                loggedUserHeader.setVisibility(View.GONE);

                try {
                    JSONObject props = new JSONObject();
                    props.put("userType", "Guest User");
                    mMixpanel.identify("Guest User");
                    mMixpanel.track("Page User Home", props);

                    trackEvent("Page User Home",props);

                    mMixpanel.getPeople().identify("Guest User");

                    // Sets user 13793's "Plan" attribute to "Premium"
//                    mMixpanel.getPeople().set("full_name", ACCNAME);
                    mMixpanel.getPeople().set("email", "");
                    mMixpanel.getPeople().set("phone", "");
                    mMixpanel.getPeople().set("accountType", "Guest User");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }


    public void userprofileinfo() {

        AndroidNetworking.get(APIs.MYINFO)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String searchResponse = response.toString();
                        Log.e("userdetails", "response  >>" + searchResponse.toString());

                        try {

                            JSONObject signup_res = new JSONObject(searchResponse);
//                        JSONObject jsonObject1=lObj.getJSONObject("data");

                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                            cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                            cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                            cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                            cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                            cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                            cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                            cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                            cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                            cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                            cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));
                            cv.put(DB_Constants.USER_GENDER, signup_res.getString("gender"));
                            cv.put(DB_Constants.USER_SPORTSPRE, signup_res.getString("sportsPreference"));

                            cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                            cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                            myDbHelper.updateUser(cv);

                        } catch (JSONException e) {
                            Log.e("error", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", error.getMessage());
                    }
                });


        /*

        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);

        String Accept="application/json";
        Call<JsonElement> callRetrofit = null;

        callRetrofit = service.info(Accept,Dashboard.SESSIONTOKEN);

        callRetrofit.enqueue(new Callback<JsonElement>()
        {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful())
                {

                    String searchResponse = response.body().toString();
                    Log.d("userdetails", "response  >>" + searchResponse.toString());

                    try {

                        JSONObject signup_res = new JSONObject(searchResponse);
//                        JSONObject jsonObject1=lObj.getJSONObject("data");

                        ContentValues cv = new ContentValues();
                        cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                        cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                        cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                        cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                        cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                        cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                        cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                        cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                        cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                        cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                        cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                        cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));

                        cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                        cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                        myDbHelper.updateUser(cv);

                    } catch (JSONException e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());

            }
        });
*/
    }

    private void setUserAvatar() {
        Picasso.with(Dashboard.this)
                .load(AVATAR)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        ivUserAvatar.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        ivUserAvatar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.profile_new, null));
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                SESSIONTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
                AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                AVATAR = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_IMAGE));
                MYWiNS = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_WINS));
                ACCNAME = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME));
                ACCEMAIL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_EMAIL));
                ACCREF = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_REF));
                ACCNUMBER = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NUMBER));

                Log.e("SESSION_TOKEN::", SESSIONTOKEN);
            } else {
                SESSIONTOKEN = "";
                NEWTOKEN = "";
                ROLE = "0";
                CASH_BAL = "";
                AMOUNT_CASH = "";
                AVATAR = "";
                MYWiNS = "";
                ACCNAME = "";
                ACCEMAIL = "";
                ACCREF = "";
                ACCNUMBER = "";
            }
            cursor.close();
        } else {
            ROLE = "0";
        }

        setPageData();
        init();
        initializeComponents();
        setHome();


        if (!Utilities.isNetworkAvailable(getApplicationContext())) {
            Utilities.showNoInternetAlert(Dashboard.this);
        }

        if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
            userprofdileupdate();

        }

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            if (bundle.containsKey("route"))
//                ppp = bundle.getString("route");
//        }
//
//        if (ppp.equalsIgnoreCase("0")) {
//
//            Intent ll = new Intent(Dashboard.this, ProfileEdit.class);
//            startActivity(ll);
//        }

    }


    private void init() {
//        setHome();
    }

    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.footer1:
                findViewById(R.id.footer1).setSelected(true);
                findViewById(R.id.footer2).setSelected(false);
                findViewById(R.id.footer3).setSelected(false);
                findViewById(R.id.footer4).setSelected(false);

                setHome();

                if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
                    userprofdileupdate();

                }

                return;
            case R.id.footer2:
                findViewById(R.id.footer1).setSelected(false);
                findViewById(R.id.footer2).setSelected(true);
                findViewById(R.id.footer3).setSelected(false);
                findViewById(R.id.footer4).setSelected(false);

                navToMyCards();
                if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
                    userprofdileupdate();

                }
                break;
            case R.id.footer3:
                findViewById(R.id.footer1).setSelected(false);
                findViewById(R.id.footer2).setSelected(false);
                findViewById(R.id.footer3).setSelected(true);
                findViewById(R.id.footer4).setSelected(false);
                setTV();
                if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
                    userprofdileupdate();
                }
                break;
            case R.id.footer4:
                findViewById(R.id.footer1).setSelected(false);
                findViewById(R.id.footer2).setSelected(false);
                findViewById(R.id.footer3).setSelected(false);
                findViewById(R.id.footer4).setSelected(true);
                setPromo();
                if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
                    userprofdileupdate();
                }
                break;
            default:
                break;
        }
    }

    private void setTV() {
        someFragment = new SqorrTvFragment();
        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction3.replace(R.id.frame_layout, someFragment); // give your fragment container id in first parameter
        transaction3.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction3.commit();
    }

    private void setHome() {
        someFragment = new HomeFrag();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, someFragment); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }


    public void setPromo() {

        someFragment = new PromosFragment();
        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction4.replace(R.id.frame_layout, someFragment); // give your fragment container id in first parameter
        transaction4.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction4.commit();
    }


    public void changeToHome() {
        findViewById(R.id.footer1).setSelected(true);
        findViewById(R.id.footer2).setSelected(false);
        findViewById(R.id.footer3).setSelected(false);
        findViewById(R.id.footer4).setSelected(false);
    }

    public void navToMyCards() {
//        findViewById(R.id.footer1).setSelected(false);
//        findViewById(R.id.footer2).setSelected(true);
//        findViewById(R.id.footer3).setSelected(false);
//        findViewById(R.id.footer4).setSelected(false);

        someFragment = new CardSFrag();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction2.replace(R.id.frame_layout, someFragment); // give your fragment container id in first parameter
        transaction2.addToBackStack(null);  // if written, this transaction will be added to back stack
        transaction2.commit();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSettings:
            case R.id.llUserAvatar:
                Intent userProfileIntent = new Intent(Dashboard.this, Profile.class);
                userProfileIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(userProfileIntent);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                break;
            case R.id.llJoinNow:
                Intent in = new Intent(Dashboard.this, PlayWithCash.class);
//                Intent in = new Intent(Dashboard.this, Signup.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);
                break;
            case R.id.llJoinLogin:
                Intent in_login = new Intent(Dashboard.this, Login.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in_login);
                break;
            case R.id.llAddFunds:
                Intent addFundsIntent = new Intent(Dashboard.this, AddFunds.class);
                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addFundsIntent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }

    // Acction info

    public static void time() {
        SensorService.timerTask = new TimerTask() {
            public void run() {


//                if(!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")){
//                    userprofdileupdate();
//
//                }

            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
            userprofdileupdate();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
            userprofdileupdate();

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
            userprofdileupdate();

        }
    }

    public void userprofdileupdate() {

        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);

        String Accept = "application/json";
        Call<JsonElement> callRetrofit = null;

        callRetrofit = service.info(Accept, "bearer " +  Dashboard.NEWTOKEN);

        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {

                    String searchResponse = response.body().toString();
                    Log.d("userdetails", "response  >>" + searchResponse.toString());

                    try {

                        JSONObject signup_res = new JSONObject(searchResponse);

                        Log.d("userdetails  703", "response  >>" + signup_res.toString());


                        Log.d("tamount---703",  String.format ("%.0f", signup_res.getDouble("tokenBalance")));

                        JSONObject agev = signup_res.getJSONObject("userAgeVerification");

                        v_status = agev.getString("verification_status");

//                        JSONObject jsonObject1=lObj.getJSONObject("data");

                        ContentValues cv = new ContentValues();
                        cv.put(DB_Constants.USER_NAME, signup_res.getString("fullName"));
                        cv.put(DB_Constants.USER_EMAIL, signup_res.getString("email"));
                        cv.put(DB_Constants.USER_IMAGE, signup_res.getString("avatar"));
                        cv.put(DB_Constants.USER_WINS, signup_res.getString("totalWins"));
                        cv.put(DB_Constants.USER_CITY, signup_res.getString("city"));
                        cv.put(DB_Constants.USER_STATE, signup_res.getString("state"));
                        cv.put(DB_Constants.USER_COUNTRY, signup_res.getString("country"));
                        cv.put(DB_Constants.USER_MODETYPE, signup_res.getString("userPlayMode"));
                        cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                        cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                        cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                        cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                        cv.put(DB_Constants.USER_TOKENBALANCE, String.format ("%.0f", signup_res.getDouble("tokenBalance")));
//                        cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));

                        cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                        cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                        myDbHelper.updateUser(cv);

                        tamount = String.format ("%.0f", signup_res.getDouble("tokenBalance"));


                        Log.e("tamount---", signup_res.getString("tokenBalance"));

                        setPageData();


                        // ANAl

                        if (ROLE != null && ROLE.equalsIgnoreCase("cash")) { // If cash user

                            guestUserHeader.setVisibility(View.GONE);
                            loggedUserHeader.setVisibility(View.VISIBLE);
                            llAddFunds.setVisibility(View.VISIBLE);
                            llUserTokens.setVisibility(View.GONE);
                            tvUserCash.setText("$" + String.format ("%.0f", signup_res.getDouble("totalCashBalance")));
//                            tvUserCash.setText("$" + signup_res.getString("totalCashBalance"));
                            tvUserWins.setText(signup_res.getString("totalWins"));
                            try {
                                JSONObject props = new JSONObject();
                                props.put("userType", "Cash User");
                                mMixpanel.identify(ACCEMAIL);
                                mMixpanel.track("Page User Home", props);

                                trackEvent("Page User Home",props);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setUserAvatar();

                        } else if (ROLE != null && ROLE.equalsIgnoreCase("tokens")) { //if tokens user

                            tvTokens.setText(tamount);
                            guestUserHeader.setVisibility(View.GONE);
                            loggedUserHeader.setVisibility(View.VISIBLE);
                            llAddFunds.setVisibility(View.GONE);
                            llUserTokens.setVisibility(View.VISIBLE);
                            tvUserWins.setText(signup_res.getString("totalWins"));
                            try {
                                JSONObject props = new JSONObject();
                                props.put("userType", "Token User");
                                mMixpanel.identify(ACCEMAIL);
                                mMixpanel.track("Page User Home", props);

                                trackEvent("Page User Home",props);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setUserAvatar();
                        }


                    } catch (JSONException e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());

            }
        });

    }
}