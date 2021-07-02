package com.sport.playsqorr.fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.pojos.MyCardsPojo;
import com.sport.playsqorr.pojos.playerCardsPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.PresetValueButton;
import com.sport.playsqorr.utilities.Utilities;
import com.sport.playsqorr.views.Dashboard;
import com.sport.playsqorr.views.OnBoarding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CardSFrag extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    LinearLayout without, withtc;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    List<MyCardsPojo> myCardsPojo_u;
    List<MyCardsPojo> myCardsPojo_l;
    List<MyCardsPojo> myCardsPojo_s;

    PresetValueButton all_pvb, EPL_pvb, LA_LIGA_pvb, mlb_pvb, mls_pvb, NASCAR_pvb, NBA_pvb, NCAAMB_pvb, NFL_pvb, NHL_pvb, PGA_pvb, NCAAFB_pvb, WILD_pvb,prorod_pvb;

    ProgressBar progressBar;
    private DataBaseHelper myDbHelper;
    String ROLE = "";
    List<MyCardsPojo> EPLData_u;
    List<MyCardsPojo> EPLData_l;
    List<MyCardsPojo> EPLData_s;
    List<MyCardsPojo> LALIGAData_u;
    List<MyCardsPojo> NFLData_u;
    List<MyCardsPojo> NBAData_u;
    List<MyCardsPojo> NHLData_u;
    List<MyCardsPojo> NASCARData_u;
    List<MyCardsPojo> MLBData_u;
    List<MyCardsPojo> MLSData_u;
    List<MyCardsPojo> NCAAMBData_u;
    List<MyCardsPojo> PGAData_u;
    List<MyCardsPojo> LALIGAData_l;
    List<MyCardsPojo> NFLData_l;
    List<MyCardsPojo> NBAData_l;
    List<MyCardsPojo> NHLData_l;
    List<MyCardsPojo> NASCARData_l;
    List<MyCardsPojo> MLBData_l;
    List<MyCardsPojo> MLSData_l;
    List<MyCardsPojo> NCAAMBData_l;
    List<MyCardsPojo> PGAData_l;
    List<MyCardsPojo> LALIGAData_s;
    List<MyCardsPojo> NFLData_s;
    List<MyCardsPojo> NBAData_s;
    List<MyCardsPojo> NHLData_s;
    List<MyCardsPojo> NASCARData_s;
    List<MyCardsPojo> MLBData_s;
    List<MyCardsPojo> MLSData_s;
    List<MyCardsPojo> NCAAMBData_s;
    List<MyCardsPojo> PGAData_s;

    List<MyCardsPojo> NCAAFBData_u;
    List<MyCardsPojo> NCAAFBData_l;
    List<MyCardsPojo> NCAAFBData_s;

    List<MyCardsPojo> WILDData_u;
    List<MyCardsPojo> WILDData_l;
    List<MyCardsPojo> WILDData_s;

    List<MyCardsPojo> redeoData_u;
    List<MyCardsPojo> redeoData_l;
    List<MyCardsPojo> redeoData_s;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragtwo, container, false);
        progressBar = v.findViewById(R.id.progressBar);

//        getActivity().findViewById(R.id.all).setSelected(true);

//        getApp();
//        Toast.makeText(getActivity(), "s1", Toast.LENGTH_SHORT).show();

//        getActivity().findViewById(R.id.all).setSelected(true);
        without = v.findViewById(R.id.without);
        withtc = v.findViewById(R.id.with_tc);

        all_pvb = v.findViewById(R.id.all);
        prorod_pvb = v.findViewById(R.id.prorod);
        EPL_pvb = v.findViewById(R.id.EPL);
        LA_LIGA_pvb = v.findViewById(R.id.LA_LIGA);
        mlb_pvb = v.findViewById(R.id.mlb);
        mls_pvb = v.findViewById(R.id.mls);
        NASCAR_pvb = v.findViewById(R.id.NASCAR);
        NBA_pvb = v.findViewById(R.id.NBA);
        NCAAMB_pvb = v.findViewById(R.id.NCAAMB);
        NCAAFB_pvb = v.findViewById(R.id.NCAAFB);
        WILD_pvb = v.findViewById(R.id.WILD);
        NFL_pvb = v.findViewById(R.id.NFL);
        NHL_pvb = v.findViewById(R.id.NHL);
        PGA_pvb = v.findViewById(R.id.PGA);

        EPLData_u = new ArrayList<>();
        EPLData_l = new ArrayList<>();
        EPLData_s = new ArrayList<>();

        LALIGAData_u = new ArrayList<>();
        NFLData_u = new ArrayList<>();
        NBAData_u = new ArrayList<>();
        NHLData_u = new ArrayList<>();
        NASCARData_u = new ArrayList<>();
        MLBData_u = new ArrayList<>();
        MLSData_u = new ArrayList<>();
        NCAAMBData_u = new ArrayList<>();
        PGAData_u = new ArrayList<>();

        LALIGAData_l = new ArrayList<>();
        NFLData_l = new ArrayList<>();
        NBAData_l = new ArrayList<>();
        NHLData_l = new ArrayList<>();
        NASCARData_l = new ArrayList<>();
        MLBData_l = new ArrayList<>();
        MLSData_l = new ArrayList<>();
        NCAAMBData_l = new ArrayList<>();
        PGAData_l = new ArrayList<>();

        LALIGAData_s = new ArrayList<>();
        NFLData_s = new ArrayList<>();
        NBAData_s = new ArrayList<>();
        NHLData_s = new ArrayList<>();
        NASCARData_s = new ArrayList<>();
        MLBData_s = new ArrayList<>();
        MLSData_s = new ArrayList<>();
        NCAAMBData_s = new ArrayList<>();
        PGAData_s = new ArrayList<>();


        NCAAFBData_u = new ArrayList<>();
        NCAAFBData_l = new ArrayList<>();
        NCAAFBData_s = new ArrayList<>();


        WILDData_u = new ArrayList<>();
        WILDData_l = new ArrayList<>();
        WILDData_s = new ArrayList<>();

        redeoData_u = new ArrayList<>();
        redeoData_l = new ArrayList<>();
        redeoData_s = new ArrayList<>();

        all_pvb.setOnClickListener(this);
        prorod_pvb.setOnClickListener(this);
        EPL_pvb.setOnClickListener(this);
        LA_LIGA_pvb.setOnClickListener(this);
        mlb_pvb.setOnClickListener(this);
        mls_pvb.setOnClickListener(this);
        NASCAR_pvb.setOnClickListener(this);
        NBA_pvb.setOnClickListener(this);
        NCAAMB_pvb.setOnClickListener(this);
        NCAAFB_pvb.setOnClickListener(this);
        WILD_pvb.setOnClickListener(this);
        NFL_pvb.setOnClickListener(this);
        NHL_pvb.setOnClickListener(this);
        PGA_pvb.setOnClickListener(this);


        viewPager = v.findViewById(R.id.pager_mycards);
        tabLayout = v.findViewById(R.id.tablayout);

        progressBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        myCardsPojo_u = new ArrayList<>();
        myCardsPojo_l = new ArrayList<>();
        myCardsPojo_s = new ArrayList<>();
//        sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
//        sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
        myDbHelper = new DataBaseHelper(getActivity());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));

                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));

            } else {

                ROLE = "0";

            }
            cursor.close();
        } else {
            ROLE = "0";
        }
        Log.d("ROLEcard", ROLE);
        if (ROLE.equalsIgnoreCase("0")) {

            without.setVisibility(View.VISIBLE);
            withtc.setVisibility(View.GONE);
        } else {
            without.setVisibility(View.GONE);
            withtc.setVisibility(View.VISIBLE);
//            Toast.makeText(getActivity(), "s2", Toast.LENGTH_SHORT).show();
//                 getUserSavedCards();
//            sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
//            sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");

        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        myDbHelper = new DataBaseHelper(getActivity());
        Cursor cursor = myDbHelper.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));

                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));

            } else {

                ROLE = "0";

            }
            cursor.close();
        } else {
            ROLE = "0";
        }
        Log.d("ROLEcard", ROLE);
        if (ROLE.equalsIgnoreCase("0")) {

            without.setVisibility(View.VISIBLE);
            withtc.setVisibility(View.GONE);
        } else {
            without.setVisibility(View.GONE);
            withtc.setVisibility(View.VISIBLE);
//            Toast.makeText(getActivity(), "s2", Toast.LENGTH_SHORT).show();
            getUserSavedCards();

//            sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
//            sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");

        }
    }

    private void getUserSavedCards() {

        AndroidNetworking.get(APIs.MY_CARDS)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("80-----", "Response Start");
                        Log.e("80--cards---", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                Log.e("90-res---", jb.toString());
                                Log.e("304----", jb.getString("cardType"));

                                String status_data = jb.getString("status");
                                if (status_data.equalsIgnoreCase("PENDING")) {
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    String a1 = String.valueOf(ja.get(0));
                                    String a2 = String.valueOf(ja.get(1));

                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));
                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));
                                    mp.setPlayerImageLeft(a1);
                                    mp.setPlayerImageRight(a2);
                                    mp.setStatus(jb.getString("status"));
                                    mp.setCurrencyTypeIsTokens(jb.getString("currencyTypeIsTokens"));
                                    mp.setMatchupsPlayed(jb.getString("matchupsPlayed"));
                                    mp.setMatchupsWon(jb.getString("matchupsWon"));
                                    mp.setSettlementDate(jb.getString("settlementDate"));
                                    mp.setWinAmount(jb.getString("payout"));

                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);

                                    // playercards

                                    JSONArray playerjson_ids = jb.getJSONArray("playerCards");

                                    List<playerCardsPojo> cpo = new ArrayList<>();
                                    for (int j = 0; j < playerjson_ids.length(); j++) {

                                        JSONObject jb_playercardsids = playerjson_ids.getJSONObject(j);
                                        playerCardsPojo ccp = new playerCardsPojo();
                                        ccp.setPlayerCardId(jb_playercardsids.getString("playerCardId"));
                                        ccp.setStatus(jb_playercardsids.getString("status"));
                                        ccp.setCurrencyTypeIsTokens(jb_playercardsids.getString("currencyTypeIsTokens"));
                                        ccp.setMatchupsPlayed(jb_playercardsids.getString("matchupsPlayed"));
                                        ccp.setMatchupsWon(jb_playercardsids.getString("matchupsWon"));
                                        ccp.setPurchasedTime(jb_playercardsids.getString("purchasedTime"));
                                        ccp.setSettlementDate(jb_playercardsids.getString("settlementDate"));
                                        ccp.setPayout(jb_playercardsids.getString("payout"));
                                        cpo.add(ccp);
                                    }

                                    mp.setPlayerCardsPojo(cpo);
                                    mp.setPlayerCardIds(player_ids);


                                    myCardsPojo_u.add(mp);

                                } else if (status_data.equalsIgnoreCase("LIVE")) {

                                    MyCardsPojo mp = new MyCardsPojo();
                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    String a1 = String.valueOf(ja.get(0));
                                    String a2 = String.valueOf(ja.get(1));

                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));
                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));

                                    mp.setPlayerImageLeft(a1);
                                    mp.setPlayerImageRight(a2);
                                    mp.setStatus(jb.getString("status"));
                                    mp.setCurrencyTypeIsTokens(jb.getString("currencyTypeIsTokens"));
                                    mp.setMatchupsPlayed(jb.getString("matchupsPlayed"));
                                    mp.setMatchupsWon(jb.getString("matchupsWon"));
                                    mp.setSettlementDate(jb.getString("settlementDate"));
                                    mp.setWinAmount(jb.getString("payout"));

                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }


                                    // playercards

                                    JSONArray playerjson_ids = jb.getJSONArray("playerCards");

                                    List<playerCardsPojo> cpo = new ArrayList<>();
                                    for (int j = 0; j < playerjson_ids.length(); j++) {

                                        JSONObject jb_playercardsids = playerjson_ids.getJSONObject(j);
                                        playerCardsPojo ccp = new playerCardsPojo();
                                        ccp.setPlayerCardId(jb_playercardsids.getString("playerCardId"));
                                        ccp.setStatus(jb_playercardsids.getString("status"));
                                        ccp.setCurrencyTypeIsTokens(jb_playercardsids.getString("currencyTypeIsTokens"));
                                        ccp.setMatchupsPlayed(jb_playercardsids.getString("matchupsPlayed"));
                                        ccp.setMatchupsWon(jb_playercardsids.getString("matchupsWon"));
                                        ccp.setPurchasedTime(jb_playercardsids.getString("purchasedTime"));
                                        ccp.setSettlementDate(jb_playercardsids.getString("settlementDate"));
                                        ccp.setPayout(jb_playercardsids.getString("payout"));
                                        cpo.add(ccp);
                                    }

                                    mp.setPlayerCardsPojo(cpo);
                                    mp.setPlayerCardIds(player_ids);
                                    myCardsPojo_l.add(mp);
                                } else {

                                    MyCardsPojo mp = new MyCardsPojo();
                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    String a1 = String.valueOf(ja.get(0));
                                    String a2 = String.valueOf(ja.get(1));

                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));
                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));

                                    mp.setPlayerImageLeft(a1);
                                    mp.setPlayerImageRight(a2);
                                    mp.setStatus(jb.getString("status"));
                                    mp.setCurrencyTypeIsTokens(jb.getString("currencyTypeIsTokens"));
                                    mp.setMatchupsPlayed(jb.getString("matchupsPlayed"));
                                    mp.setMatchupsWon(jb.getString("matchupsWon"));
                                    mp.setSettlementDate(jb.getString("settlementDate"));
                                    mp.setWinAmount(jb.getString("payout"));

                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);


                                    // playercards

                                    JSONArray playerjson_ids = jb.getJSONArray("playerCards");

                                    List<playerCardsPojo> cpo = new ArrayList<>();
                                    for (int j = 0; j < playerjson_ids.length(); j++) {

                                        JSONObject jb_playercardsids = playerjson_ids.getJSONObject(j);
                                        playerCardsPojo ccp = new playerCardsPojo();
                                        ccp.setPlayerCardId(jb_playercardsids.getString("playerCardId"));
                                        ccp.setStatus(jb_playercardsids.getString("status"));
                                        ccp.setCurrencyTypeIsTokens(jb_playercardsids.getString("currencyTypeIsTokens"));
                                        ccp.setMatchupsPlayed(jb_playercardsids.getString("matchupsPlayed"));
                                        ccp.setMatchupsWon(jb_playercardsids.getString("matchupsWon"));
                                        ccp.setPurchasedTime(jb_playercardsids.getString("purchasedTime"));
                                        ccp.setSettlementDate(jb_playercardsids.getString("settlementDate"));
                                        ccp.setPayout(jb_playercardsids.getString("payout"));
                                        cpo.add(ccp);
                                    }

                                    mp.setPlayerCardsPojo(cpo);

                                    myCardsPojo_s.add(mp);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        cardsData();
                        sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
                        progressBar.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                    }


                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("184-----", "error Start");
                        Log.e("184-----", "" + error.getErrorBody());
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                            String au = ej.getString("message");
                            if (au.contains("Unauthorized")) {
                                showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                            } else {
                                Utilities.showToast(getActivity(), ej.getString("message"));
                            }


                        } catch (Exception e) {

                        }

                    }
                });

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

//                dialog.dismiss();

                SharedPreferences sp = getActivity().getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                myDbHelper.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(getActivity(), OnBoarding.class);
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

  /*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        without = getActivity().findViewById(R.id.without);
        withtc = getActivity().findViewById(R.id.with_tc);

        all_pvb = getActivity().findViewById(R.id.all);
        EPL_pvb = getActivity().findViewById(R.id.EPL);
        LA_LIGA_pvb = getActivity().findViewById(R.id.LA_LIGA);
        mlb_pvb = getActivity().findViewById(R.id.mlb);
        mls_pvb = getActivity().findViewById(R.id.mls);
        NASCAR_pvb = getActivity().findViewById(R.id.NASCAR);
        NBA_pvb = getActivity().findViewById(R.id.NBA);
        NCAAMB_pvb = getActivity().findViewById(R.id.NCAAMB);
        NFL_pvb = getActivity().findViewById(R.id.NFL);
        NHL_pvb = getActivity().findViewById(R.id.NHL);
        PGA_pvb = getActivity().findViewById(R.id.PGA);


        all_pvb.setOnClickListener(this);
        EPL_pvb.setOnClickListener(this);
        LA_LIGA_pvb.setOnClickListener(this);
        mlb_pvb.setOnClickListener(this);
        mls_pvb.setOnClickListener(this);
        NASCAR_pvb.setOnClickListener(this);
        NBA_pvb.setOnClickListener(this);
        NCAAMB_pvb.setOnClickListener(this);
        NFL_pvb.setOnClickListener(this);
        NHL_pvb.setOnClickListener(this);
        PGA_pvb.setOnClickListener(this);

        updateUI();


    }*/

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        String rrole = Dashboard.ROLE;
        Log.d("ROLE", ROLE);


    }


    private void cardsData() {
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("PRORODEO")) {
                redeoData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("PRORODEO")) {
                redeoData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("PRORODEO")) {
                redeoData_s.add(myCardsPojo_s.get(i));
            }
        }

        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("EPL")) {
                EPLData_u.add(myCardsPojo_u.get(i));
            }
        }

        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("EPL")) {
                EPLData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("EPL")) {
                EPLData_s.add(myCardsPojo_s.get(i));
            }
        }

        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("LA-LIGA")) {
                LALIGAData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("LA-LIGA")) {
                LALIGAData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("LA-LIGA")) {
                LALIGAData_s.add(myCardsPojo_s.get(i));
            }
        }


        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("MLB")) {
                MLBData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("MLB")) {
                MLBData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("MLB")) {
                MLBData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("MLS")) {
                MLSData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("MLS")) {
                MLSData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("MLS")) {
                MLSData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NASCAR")) {
                NASCARData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NASCAR")) {
                NASCARData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NASCAR")) {
                NASCARData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NBA")) {
                NBAData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NBA")) {
                NBAData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NBA")) {
                NBAData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NCAAMB")) {
                NCAAMBData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NCAAMB")) {
                NCAAMBData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NCAAMB")) {
                NCAAMBData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NFL")) {
                NFLData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NFL")) {
                NFLData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NFL")) {
                NFLData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NHL")) {
                NHLData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NHL")) {
                NHLData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NHL")) {
                NHLData_s.add(myCardsPojo_s.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("PGA")) {
                PGAData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("PGA")) {
                PGAData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("PGA")) {
                PGAData_s.add(myCardsPojo_s.get(i));
            }
        }


        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("NCAAFB")) {
                NCAAFBData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("NCAAFB")) {
                NCAAFBData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("NCAAFB")) {
                NCAAFBData_s.add(myCardsPojo_s.get(i));
            }
        }
        // wild
        for (int i = 0; i < myCardsPojo_u.size(); i++) {
            if (myCardsPojo_u.get(i).getLeagueAbbrevation().contains("WILD CARD")) {
                WILDData_u.add(myCardsPojo_u.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_l.size(); i++) {
            if (myCardsPojo_l.get(i).getLeagueAbbrevation().contains("WILD CARD")) {
                WILDData_l.add(myCardsPojo_l.get(i));
            }
        }
        for (int i = 0; i < myCardsPojo_s.size(); i++) {
            if (myCardsPojo_s.get(i).getLeagueAbbrevation().contains("WILD CARD")) {
                WILDData_s.add(myCardsPojo_s.get(i));
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all /*2131230804*/:
                getActivity().findViewById(R.id.all).setSelected(true);
                sethomeAll(myCardsPojo_u, myCardsPojo_l, myCardsPojo_s, "ALL");
                return;
                case R.id.prorod /*2131230804*/:
                getActivity().findViewById(R.id.prorod);//.setSelected(true);
                sethomeAll(redeoData_u, redeoData_l, redeoData_s, "PRORODEO");
                return;
            case R.id.EPL /*2131230804*/:
                getActivity().findViewById(R.id.EPL);//.setSelected(true);
                sethomeAll(EPLData_u, EPLData_l, EPLData_s, "EPL");
                return;
            case R.id.LA_LIGA /*2131230804*/:
                getActivity().findViewById(R.id.LA_LIGA);//.setSelected(true);
                sethomeAll(LALIGAData_u, LALIGAData_l, LALIGAData_s, "LA_LIGA");
                return;
            case R.id.mlb /*2131230804*/:
                getActivity().findViewById(R.id.mlb);//.setSelected(true);
                sethomeAll(MLBData_u, MLBData_l, MLBData_s, "MLB");
                return;
            case R.id.mls /*2131230804*/:
                getActivity().findViewById(R.id.mls);
                sethomeAll(MLSData_u, MLSData_l, MLSData_s, "MLS");
                return;
            case R.id.NASCAR /*2131230804*/:
                getActivity().findViewById(R.id.NASCAR);//.setSelected(true);
                sethomeAll(NASCARData_u, NASCARData_l, NASCARData_s, "NASCAR");
                return;
            case R.id.NBA /*2131230804*/:
                getActivity().findViewById(R.id.NBA);//.setSelected(true);
                sethomeAll(NBAData_u, NBAData_l, NBAData_s, "NBA");
                return;
            case R.id.NCAAMB /*2131230804*/:
                getActivity().findViewById(R.id.NCAAMB);//.setSelected(true);
                sethomeAll(NCAAMBData_u, NCAAMBData_l, NCAAMBData_s, "NCAAMB");
                return;
            case R.id.NFL /*2131230804*/:
                getActivity().findViewById(R.id.NFL);//.setSelected(true);
                sethomeAll(NFLData_u, NFLData_l, NFLData_s, "NFL");
                return;
            case R.id.NHL /*2131230804*/:
                getActivity().findViewById(R.id.NHL);//.setSelected(true);
                sethomeAll(NHLData_u, NHLData_l, NHLData_s, "NHL");
                return;
            case R.id.PGA /*2131230804*/:
                getActivity().findViewById(R.id.PGA);
                sethomeAll(PGAData_u, PGAData_l, PGAData_s, "PGA");
                return;
            case R.id.NCAAFB /*2131230804*/:
                getActivity().findViewById(R.id.NCAAFB);
                sethomeAll(NCAAFBData_u, NCAAFBData_l, NCAAFBData_s, "NCAAFB");
                return;
            case R.id.WILD /*2131230804*/:
                getActivity().findViewById(R.id.WILD);
                sethomeAll(WILDData_u, WILDData_l, WILDData_s, "WILD CARD");
                return;

            default:
                return;
        }
    }

    private void sethomeAll(List<MyCardsPojo> myCardsPojo_u, List<MyCardsPojo> myCardsPojo_l,
                            List<MyCardsPojo> myCardsPojo_s, String l) {

//        tabLayout = getActivity().findViewById(R.id.tablayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new MyCardsUpComing().instantiate(l, "", myCardsPojo_u), "UPCOMING");
        adapter.addFragment(new MyCardsLive().instantiate(l, "", myCardsPojo_l), "LIVE");
        adapter.addFragment(new MyCardsSettled().instantiate(l, "", myCardsPojo_s), "SETTLED");

        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        System.out.println("onPageSelected Called");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private static int count = 3;

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new MyCardsUpComing();
                case 1:
                    return new MyCardsLive();
                case 2:
                    return new MyCardsSettled();
            }
            return null;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "UPCOMING";
                case 1:
                    return "LIVE";
                case 2:
                    return "SETTLED";
            }
            return null;
        }
    }
}



