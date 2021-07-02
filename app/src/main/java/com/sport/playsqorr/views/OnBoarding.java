package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.sport.playsqorr.R;
import com.sport.playsqorr.adapters.PagerAdapter;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.fragments.PagerFragment;
import com.sport.playsqorr.services.MyReceiver;
import com.sport.playsqorr.utilities.UtilitiesAna;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

import static com.sport.playsqorr.utilities.UtilitiesAna.setInstance;
import static com.sport.playsqorr.utilities.UtilitiesAna.trackEvent;


public class OnBoarding extends AppCompatActivity implements View.OnClickListener {
    private List<Fragment> fragments;
    private String TAG = OnBoarding.class.getSimpleName();

    Cursor cursor;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;

    MixpanelAPI mMixpanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.primary_color));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(0);
        }


//        First.myInfo(getApplication(),"My First Tost");
//
//        JSONObject jb = new JSONObject();
//        try {
//            jb.put("sending_from","Android");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        trackEvent("OnBoarding Screen",jb);
        /* *****************Database Starts************************/
        mydb = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = mydb.getReadableDatabase();
        /* *****************Database Ends************************/

        handlePageNavigation();
        printKeyHash(this);

        mMixpanel = MixpanelAPI.getInstance(OnBoarding.this, getString(R.string.test_MIX_PANEL_TOKEN));
        mMixpanel.track("On Boarding", null);

        setInstance(OnBoarding.this,"");
        trackEvent("On Boarding",null);

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData data) {
                        if (data == null) {
                            Log.e(TAG, "getInvitation: no data");
                            return;
                        }

                        // Get the deep link
                        Uri deepLink = data.getLink();


                        if (deepLink != null) {
                            Log.e("K------", "" + deepLink);

                            String link_url = String.valueOf(deepLink);
                            String[] arrSplit = link_url.split("=");


                            Log.e("K------", arrSplit[1]);


                            ContentValues cv = new ContentValues();
                            cv.put(DB_Constants.USER_DEEPLINK_CODE, arrSplit[1]);
                            mydb.insertDeepInfo(cv);


                        } else {

                        }

                        // Extract invite
                        FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(data);
                        if (invite != null) {
                            String invitationId = invite.getInvitationId();

                            Log.e("mmy-----", "getInvit----" + invitationId);

                        }
                        Log.e("mmydeep--", "");

                        // Handle the deep link
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
//
//
////        Intent notifyIntent = new Intent(this, MyReceiver.class);
//        alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//
//// Set the alarm to start at 8:30 a.m.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 6);
//        calendar.set(Calendar.MINUTE, 45);
//
//// setRepeating() lets you specify a precise custom interval--in this case,
//// 20 minutes.
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, alarmIntent);

    /*    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, MyReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      //  Calendar cal = Calendar.getInstance();
     //   cal.add(Calendar.SECOND, 5);
                Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);//
        calendar.set(Calendar.MINUTE, 10);//
        calendar.add(Calendar.SECOND, 0);

      //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, broadcast);



        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        calendar1.set(Calendar.HOUR_OF_DAY, 16);//
        calendar1.set(Calendar.MINUTE, 15);//
        calendar1.add(Calendar.SECOND, 0);

        //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                1000 * 60 * 20, broadcast);


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, 16);//
        calendar2.set(Calendar.MINUTE, 20);//
        calendar2.add(Calendar.SECOND, 0);

        //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                1000 * 60 * 20, broadcast);
                */

        init();
    }


    private void handlePageNavigation() {
        String session_t = "";
        String NEWTOKEN = "";
        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                session_t = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
            }
            cursor.close();
        }
        if (session_t != null && !session_t.equals("")) {
            Intent intent = new Intent(OnBoarding.this, Dashboard.class);
            startActivity(intent);
        } else {
            init();
        }

    }

    //To Print the KeyHash of Fb in Logcat
    @SuppressLint("PackageManagerGetSignatures")
    public static void printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key;
        try {
            String packageName = context.getApplicationContext().getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                System.out.println("Key Hash=> " + key);
                Log.e("key hash","Key Hash=> " + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.d("OnBoard::", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.d("OnBoard::", e.toString());
        } catch (Exception e) {
            Log.d("OnBoard::", e.toString());
        }
    }

    TextView letsplay;

    private void init() {
        fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(getBundle(R.drawable.prorodeo_small, getResources().getString(R.string.welcome_msg), "", 0));
        fragments.add(getBundle(R.drawable.win_1, getResources().getString(R.string.on_boarding_2), getResources().getString(R.string.on_boarding_sub_2), 1));
        fragments.add(getBundle(R.drawable.payout_2, getResources().getString(R.string.on_boarding_3), getResources().getString(R.string.on_boarding_sub_3), 2));
        fragments.add(getBundle(R.drawable.easy_3, getResources().getString(R.string.on_boarding_4), getResources().getString(R.string.on_boarding_sub_4), 3));
        fragments.add(getBundle(R.drawable.cards_4, getResources().getString(R.string.on_boarding_5), getResources().getString(R.string.on_boarding_sub_5), 4));
//        fragments.add(getBundle(R.drawable.win_1, "", "", 0));
//        fragments.add(getBundle(R.drawable.payout_2, "", "", 1));
//        fragments.add(getBundle(R.drawable.easy_3, "","", 2));
//        fragments.add(getBundle(R.drawable.cards_4, "","", 3));
        letsplay = findViewById(R.id.pagerBT);
        TextView btnSignup = (TextView) findViewById(R.id.btnSignup);
        TextView btnLogin = (TextView) findViewById(R.id.btnLogin);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        CircleIndicator circleIndicator = findViewById(R.id.indicator);
        letsplay.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        viewPager.setAdapter(new PagerAdapter(this.getSupportFragmentManager(), fragments));
        circleIndicator.setViewPager(viewPager);

    }

    Fragment getBundle(int position, String title, String desc, int pageNo) {
        Log.i(TAG, "position1 = " + position);
        Fragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pic", position);
        bundle.putString("title", title);
        bundle.putString("desc", desc);
        bundle.putInt("pageNo", pageNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup:
                startActivity(new Intent(this, PlayWithCash.class));
//                startActivity(new Intent(this, Signup.class));
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.pagerBT:

                letsplay.setBackgroundResource(R.drawable.btn_bg_red_ripple);


                Intent letsplay = new Intent(this, Dashboard.class);

                letsplay.putExtra("userrole", "0");
                letsplay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(letsplay);


                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        mMixpanel.flush();
        super.onDestroy();
    }
}

