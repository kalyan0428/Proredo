package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.Leaderboard;
import com.sport.playsqorr.pojos.BiggestWinners;
import com.sport.playsqorr.pojos.HighestScoring;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NFLLeaderBoardScreen extends AppCompatActivity implements View.OnClickListener {

    private List<Leaderboard> leaderboardPojo = new ArrayList<>();
    private List<BiggestWinners> biggestWinPojo = new ArrayList<>();
    private List<HighestScoring> hightestScorePojo = new ArrayList<>();
    private RecyclerView leaderlst;
    private NFLLeaderBoardScreen.LeaderListAdapter recycleAdapter;
    private NFLLeaderBoardScreen.LeaderListHighAdapter recyclehightAdapter;

    ImageView ivCard_txt, img_1, img_2, img_3;
    LinearLayout llleader_txt;
    TextView tvName_txt, tvvalue_txt, tvrank_txt;

    CardView ccv_txt;
    Button bw, hs;//= findViewById(R.id.hs);


    private DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nflleader_board_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        init();
        getLeaderAPI();

        UpdateUI();
        myDbHelper = new DataBaseHelper(NFLLeaderBoardScreen.this);

        bw.setBackgroundColor(Color.parseColor("#ffffff"));
        bw.setTextColor(Color.parseColor("#1A323E"));
        hs.setBackgroundColor(Color.parseColor("#1A323E"));
        hs.setTextColor(Color.parseColor("#ffffff"));
    }

    String txt = Profile.PID;
    private void UpdateUI() {



        for (int i = 0; i < biggestWinPojo.size(); i++) {

            BiggestWinners bpp = biggestWinPojo.get(i);
            tvName_txt.setText(bpp.getAccountName());

        }
        if (biggestWinPojo.size() > 10) {




//            tvName.setText(biggestWinPojo.get(10).getAccountName());
//            // tvvalue.setText(biggestWinners_obj.getString("value"));
//            //  tvrank.setText(biggestWinners_obj.getString("rank"));
//
        }

/*
        for (int i = 0; i <leaderboardPojo.size() ; i++) {

            if (!txt.contains(leaderboardPojo.get(i).get_id())) {
                ccv_txt.setVisibility(View.VISIBLE);
                tvrank_txt.setText("-");
                tvvalue_txt.setText("$0");

                if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                    tvName_txt.setText(Profile.PNAME);
                } else {
                    tvName_txt.setText(Profile.PEMAIL);
                }
                if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                    Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(ivCard_txt);
                }
            }
        }*/
    }

    private void getLeaderAPI() {

        AndroidNetworking.get(APIs.NFLLB)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e("TabsData:: ", response.toString());
                        leaderboardPojo.clear();
                        biggestWinPojo.clear();
                        hightestScorePojo.clear();

                        try {
                            JSONObject jsonrespone = new JSONObject(response.toString());

                            Leaderboard lb = new Leaderboard();

                            lb.set_id(jsonrespone.getString("_id"));
                            lb.setCompany(jsonrespone.getString("company"));
                            lb.setLastRefreshTime(jsonrespone.getString("lastRefreshTime"));

                            String txt_b = Profile.PID;

                            if (!txt.contains(jsonrespone.getString("_id"))) {
                                ccv_txt.setVisibility(View.VISIBLE);
                                tvrank_txt.setText("-");
                                tvvalue_txt.setText("$0");

                                if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                                    tvName_txt.setText(Profile.PNAME);
                                } else {
                                    tvName_txt.setText(Profile.PEMAIL);
                                }
                                if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                                    Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                            .placeholder(R.drawable.game_inactive_placeholder)
                                            .transform(new Transformation() {
                                                @Override
                                                public Bitmap transform(Bitmap source) {
                                                    return transformImg(source, R.color.hint);
                                                }

                                                @Override
                                                public String key() {
                                                    return "circle";
                                                }
                                            })
                                            .into(ivCard_txt);
                                }
                            }

                            JSONArray biggestWinners = jsonrespone.getJSONArray("biggestWinners");

                            for (int i = 0; i < biggestWinners.length(); i++) {
                                JSONObject biggestWinners_obj = biggestWinners.getJSONObject(i);

                                BiggestWinners bw = new BiggestWinners();
                                bw.setAccountId(biggestWinners_obj.getString("accountId"));
                                bw.setAccountName(biggestWinners_obj.getString("accountName"));
                                bw.setRank(biggestWinners_obj.getString("rank"));
                                bw.setValue(biggestWinners_obj.getString("value"));
                                bw.setImage(biggestWinners_obj.getString("image"));
                                biggestWinPojo.add(bw);


                            }

                            JSONArray highestScoring = jsonrespone.getJSONArray("highestScoring");
                            for (int i = 0; i < highestScoring.length(); i++) {
                                JSONObject highestScoring_obje = highestScoring.getJSONObject(i);

                                HighestScoring hs = new HighestScoring();
                                hs.setAccountId(highestScoring_obje.getString("accountId"));
                                hs.setAccountName(highestScoring_obje.getString("accountName"));
                                hs.setRank(highestScoring_obje.getString("rank"));
                                hs.setValue(highestScoring_obje.getString("value"));
                                hs.setImage(highestScoring_obje.getString("image"));

                                hightestScorePojo.add(hs);
                            }

                            lb.setBiggestWinners(biggestWinPojo);
                            lb.setHighestScoring(hightestScorePojo);

                            leaderboardPojo.add(lb);





                            for (int i = 0; i < biggestWinPojo.size(); i++) {

                                BiggestWinners bpp = biggestWinPojo.get(i);

                                Integer r = Integer.valueOf(bpp.getRank());

                                if (Integer.valueOf(bpp.getRank()) == 1) {
                                    if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                        Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                                .placeholder(R.drawable.game_inactive_placeholder)
                                                .transform(new Transformation() {
                                                    @Override
                                                    public Bitmap transform(Bitmap source) {
                                                        return transformImg(source, R.color.hint);
                                                    }

                                                    @Override
                                                    public String key() {
                                                        return "circle";
                                                    }
                                                })
                                                .into(img_1);
                                    }

                                } else if (Integer.valueOf(bpp.getRank()) == 2) {

                                    if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                        Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                                .placeholder(R.drawable.game_inactive_placeholder)
                                                .transform(new Transformation() {
                                                    @Override
                                                    public Bitmap transform(Bitmap source) {
                                                        return transformImg(source, R.color.hint);
                                                    }

                                                    @Override
                                                    public String key() {
                                                        return "circle";
                                                    }
                                                })
                                                .into(img_2);
                                    }
                                } else if (Integer.valueOf(bpp.getRank()) == 3) {

                                    if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                        Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                                .placeholder(R.drawable.game_inactive_placeholder)
                                                .transform(new Transformation() {
                                                    @Override
                                                    public Bitmap transform(Bitmap source) {
                                                        return transformImg(source, R.color.hint);
                                                    }

                                                    @Override
                                                    public String key() {
                                                        return "circle";
                                                    }
                                                })
                                                .into(img_3);
                                    }
                                }

                                if (!txt_b.contains(bpp.getAccountId())) {
                                    ccv_txt.setVisibility(View.VISIBLE);
                                    tvrank_txt.setText("-");
                                    tvvalue_txt.setText("$0");


                                    if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                                        tvName_txt.setText(Profile.PNAME);
                                    } else {
                                        tvName_txt.setText(Profile.PEMAIL);
                                    }
                                    if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                                        Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                                .placeholder(R.drawable.game_inactive_placeholder)
                                                .transform(new Transformation() {
                                                    @Override
                                                    public Bitmap transform(Bitmap source) {
                                                        return transformImg(source, R.color.hint);
                                                    }

                                                    @Override
                                                    public String key() {
                                                        return "circle";
                                                    }
                                                })
                                                .into(ivCard_txt);
                                    }
                                }

                                if (txt_b.equalsIgnoreCase(bpp.getAccountId()) && r <= 10) {
                                    ccv_txt.setVisibility(View.GONE);
                                } else {

                                    if (txt_b.equalsIgnoreCase(bpp.getAccountId()) && r >= 10) {
                                        ccv_txt.setVisibility(View.VISIBLE);
                                        tvName_txt.setText(bpp.getAccountName());
                                        if (!bpp.getRank().isEmpty()) {
                                            tvrank_txt.setText("" + bpp.getRank());
                                        } else {
                                            tvrank_txt.setText(" - ");
                                        }

                                        if (!bpp.getValue().isEmpty()) {
                                            tvvalue_txt.setText("$" + bpp.getValue());
                                        } else {
                                            tvvalue_txt.setText(" - ");
                                        }
                                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                                    .placeholder(R.drawable.game_inactive_placeholder)
                                                    .transform(new Transformation() {
                                                        @Override
                                                        public Bitmap transform(Bitmap source) {
                                                            return transformImg(source, R.color.hint);
                                                        }

                                                        @Override
                                                        public String key() {
                                                            return "circle";
                                                        }
                                                    })
                                                    .into(ivCard_txt);
                                        }

                                    }

                                }
                            }
                            if (recycleAdapter != null)
                                recycleAdapter.notifyDataSetChanged();

                            recycleAdapter = new NFLLeaderBoardScreen.LeaderListAdapter(biggestWinPojo, NFLLeaderBoardScreen.this);
                            leaderlst.setAdapter(recycleAdapter);


                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.e("184-----", "error Start"+ anError.getErrorBody());

                        try {
                            JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                            String au = ej.getString("message");
                            if (au.contains("Unauthorized")) {
                                showAlertBox(NFLLeaderBoardScreen.this, "Error", "Session has expired,please try logining again");
                            } else {
                                Utilities.showToast(NFLLeaderBoardScreen.this, ej.getString("message"));
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

                SharedPreferences sp = getSharedPreferences("SESSION_TOKEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                //mydb.clearTableMobileUser();
                myDbHelper.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(NFLLeaderBoardScreen.this, OnBoarding.class);
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

    @SuppressLint("SetTextI18n")
    private void init() {

        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        toolbar_title_x.setText("Leaderboard");
        toolbar_title_x.setOnClickListener(this);

        bw = findViewById(R.id.bw);
        hs = findViewById(R.id.hs);

        ivCard_txt = findViewById(R.id.ivCard);
        img_1 = findViewById(R.id.ivImage1);
        img_2 = findViewById(R.id.ivImage2);
        img_3 = findViewById(R.id.ivImage3);
        tvName_txt = findViewById(R.id.tvName);
        tvvalue_txt = findViewById(R.id.tvvalue);
        tvrank_txt = findViewById(R.id.tvrank);
        llleader_txt = findViewById(R.id.llleader);
        ccv_txt = findViewById(R.id.cardLL);


        bw.setOnClickListener(this);
        hs.setOnClickListener(this);

        leaderlst = findViewById(R.id.leaderList);
        LinearLayoutManager llm = new LinearLayoutManager(NFLLeaderBoardScreen.this);
        leaderlst.setLayoutManager(llm);
        leaderlst.setItemAnimator(null);
        leaderlst.setNestedScrollingEnabled(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;

            case R.id.bw:

                bw.setBackgroundColor(Color.parseColor("#ffffff"));
                bw.setTextColor(Color.parseColor("#1A323E"));
                hs.setBackgroundColor(Color.parseColor("#1A323E"));
                hs.setTextColor(Color.parseColor("#ffffff"));

                String txt_b = Profile.PID;


                for (int i = 0; i < biggestWinPojo.size(); i++) {

                    BiggestWinners bpp = biggestWinPojo.get(i);

                    Integer r = Integer.valueOf(bpp.getRank());


                    if (Integer.valueOf(bpp.getRank()) == 1) {
                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_1);
                        }

                    } else if (Integer.valueOf(bpp.getRank()) == 2) {

                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_2);
                        }
                    } else if (Integer.valueOf(bpp.getRank()) == 3) {

                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_3);
                        }
                    }


                    if (!txt_b.contains(bpp.getAccountId())) {
                        ccv_txt.setVisibility(View.VISIBLE);
                        tvrank_txt.setText("-");
                        tvvalue_txt.setText("$0");

                        if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                            tvName_txt.setText(Profile.PNAME);
                        } else {
                            tvName_txt.setText(Profile.PEMAIL);
                        }
                        if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(ivCard_txt);
                        }
                    }

                    if (txt_b.equalsIgnoreCase(bpp.getAccountId()) && r <= 3) {
                        ccv_txt.setVisibility(View.GONE);
                    } else {

                        if (txt_b.equalsIgnoreCase(bpp.getAccountId()) && r >= 10) {
                            ccv_txt.setVisibility(View.VISIBLE);
                            tvName_txt.setText(bpp.getAccountName());
                            if (!bpp.getRank().isEmpty()) {
                                tvrank_txt.setText("" + bpp.getRank());
                            } else {
                                tvrank_txt.setText(" - ");
                            }
                            if (!bpp.getValue().isEmpty()) {
                                tvvalue_txt.setText("$" + bpp.getValue());
                            } else {
                                tvvalue_txt.setText(" - ");
                            }

                            if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                        .placeholder(R.drawable.game_inactive_placeholder)
                                        .transform(new Transformation() {
                                            @Override
                                            public Bitmap transform(Bitmap source) {
                                                return transformImg(source, R.color.hint);
                                            }

                                            @Override
                                            public String key() {
                                                return "circle";
                                            }
                                        })
                                        .into(ivCard_txt);
                            }
                        }

                    }
                }


                for (int i = 0; i <leaderboardPojo.size() ; i++) {

                    if (!txt.contains(leaderboardPojo.get(i).get_id())) {
                        ccv_txt.setVisibility(View.VISIBLE);
                        tvrank_txt.setText("-");
                        tvvalue_txt.setText("$0");

                        if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                            tvName_txt.setText(Profile.PNAME);
                        } else {
                            tvName_txt.setText(Profile.PEMAIL);
                        }
                        if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(ivCard_txt);
                        }
                    }
                }

                recycleAdapter = new NFLLeaderBoardScreen.LeaderListAdapter(biggestWinPojo, NFLLeaderBoardScreen.this);
                leaderlst.setAdapter(recycleAdapter);


//                Toast.makeText(NFLLeaderBoardScreen.this, "biggestWinPojo----" + biggestWinPojo.size(), Toast.LENGTH_LONG).show();
                break;

            case R.id.hs:

                String txt = Profile.PID;

                bw.setBackgroundColor(Color.parseColor("#1A323E"));
                bw.setTextColor(Color.parseColor("#ffffff"));
                hs.setBackgroundColor(Color.parseColor("#ffffff"));
                hs.setTextColor(Color.parseColor("#1A323E"));


                for (int i = 0; i < hightestScorePojo.size(); i++) {

                    HighestScoring bpp = hightestScorePojo.get(i);

                    Integer r = Integer.valueOf(bpp.getRank());


                    if (Integer.valueOf(bpp.getRank()) == 1) {
                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_1);
                        }

                    } else if (Integer.valueOf(bpp.getRank()) == 2) {

                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_2);
                        }
                    } else if (Integer.valueOf(bpp.getRank()) == 3) {

                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(img_3);
                        }
                    }



                    if (!txt.contains(bpp.getAccountId())) {
                        ccv_txt.setVisibility(View.VISIBLE);
                        tvrank_txt.setText("-");
                        tvvalue_txt.setText("0 wins");
                        if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                            tvName_txt.setText(Profile.PNAME);
                        } else {
                            tvName_txt.setText(Profile.PEMAIL);
                        }
                        if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(ivCard_txt);
                        }
                    }else{
                        tvrank_txt.setText(bpp.getRank());
                        tvvalue_txt.setText(bpp.getValue()+" wins");

                        tvName_txt.setText(bpp.getAccountName());

                        if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(ivCard_txt);
                        }

                        break;
                    }

                    if (txt.equalsIgnoreCase(bpp.getAccountId()) && r <= 10) {
                        ccv_txt.setVisibility(View.GONE);
                    } else {

                        if (txt.equalsIgnoreCase(bpp.getAccountId())) {
                            ccv_txt.setVisibility(View.VISIBLE);
                            tvName_txt.setText(bpp.getAccountName());
                            if (!bpp.getRank().isEmpty()) {
                                tvrank_txt.setText("" + bpp.getRank());
                            } else {
                                tvrank_txt.setText("-");
                            }
                            if (!bpp.getValue().isEmpty()) {
                                tvvalue_txt.setText(bpp.getValue() + " wins");
                            } else {
                                tvvalue_txt.setText(" - ");
                            }

                            if (bpp.getImage() != null && !bpp.getImage().equals("")) {
                                Picasso.with(NFLLeaderBoardScreen.this).load(bpp.getImage())
                                        .placeholder(R.drawable.game_inactive_placeholder)
                                        .transform(new Transformation() {
                                            @Override
                                            public Bitmap transform(Bitmap source) {
                                                return transformImg(source, R.color.hint);
                                            }

                                            @Override
                                            public String key() {
                                                return "circle";
                                            }
                                        })
                                        .into(ivCard_txt);
                            }
                        }

                    }
                }

                for (int i = 0; i <leaderboardPojo.size() ; i++) {

                    if (!txt.contains(leaderboardPojo.get(i).get_id())) {
                        ccv_txt.setVisibility(View.VISIBLE);
                        tvrank_txt.setText("-");
                        tvvalue_txt.setText("0 wins");

                        if (Profile.PNAME != null && !Profile.PNAME.equals("") && !Profile.PNAME.equals(" ")) {
                            tvName_txt.setText(Profile.PNAME);
                        } else {
                            tvName_txt.setText(Profile.PEMAIL);
                        }
                        if (Profile.PIMAGE != null && !Profile.PIMAGE.equals("")) {
                            Picasso.with(NFLLeaderBoardScreen.this).load(Profile.PIMAGE)
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(ivCard_txt);
                        }
                    }
                }
                recyclehightAdapter = new NFLLeaderBoardScreen.LeaderListHighAdapter(hightestScorePojo, NFLLeaderBoardScreen.this);
                leaderlst.setAdapter(recyclehightAdapter);


//                Toast.makeText(NFLLeaderBoardScreen.this, "hightestScorePojo-----" + hightestScorePojo.size(), Toast.LENGTH_LONG).show();
                break;

        }
    }


    @SuppressLint("NewApi")
    public Bitmap transformImg(Bitmap source, int color) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r1 = size / 2f;
        canvas.drawCircle(r1, r1, r1, paint);
        //border code
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(NFLLeaderBoardScreen.this.getColor(color));
        paint.setStrokeWidth(1f);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    public class LeaderListAdapter extends RecyclerView.Adapter<NFLLeaderBoardScreen.LeaderListAdapter.ViewHolder> {


        private final List<BiggestWinners> mValues;
        private Context context;


        public LeaderListAdapter(List<BiggestWinners> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public NFLLeaderBoardScreen.LeaderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.leaderboard_view, parent, false);

            return new NFLLeaderBoardScreen.LeaderListAdapter.ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final NFLLeaderBoardScreen.LeaderListAdapter.ViewHolder holder, final int position) {


//            if(position>2){
            BiggestWinners bbw = mValues.get(position);
            String txt_id = Profile.PID;

            if (Integer.valueOf(bbw.getRank()) == 1) {
//                holder.llleader.setBackground(Color.);
                holder.llleader.setBackgroundColor(Color.parseColor("#EBA540"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);

            } else if (Integer.valueOf(bbw.getRank()) == 2) {

                holder.llleader.setBackgroundColor(Color.parseColor("#5FC4FA"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);
            } else if (Integer.valueOf(bbw.getRank()) == 3) {

                holder.llleader.setBackgroundColor(Color.parseColor("#6FD5B7"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);
            } else {
                holder.llleader.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvName.setTextColor(Color.BLACK);
                holder.tvvalue.setTextColor(Color.BLACK);
                holder.tvrank.setTextColor(Color.BLACK);

            }


            // if ((Integer.valueOf(bbw.getRank()) <= 10)) {

            if (bbw.getAccountId().contains(txt_id)) {
                holder.llleader.setVisibility(View.GONE);

            } else {
                holder.tvName.setText(bbw.getAccountName());
                holder.llleader.setVisibility(View.VISIBLE);
                holder.tvvalue.setText("$ " + bbw.getValue());
                holder.tvrank.setText("" + bbw.getRank());
                if (bbw.getImage() != null && !bbw.getImage().equals("")) {
                    Picasso.with(context).load(bbw.getImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(holder.ivCard);
                }
            }
//
//            } else {
////                holder.tvName.setText("Text");
//                holder.llleader.setVisibility(View.GONE);
//
//            }




            /*
            if (Integer.valueOf(bbw.getRank()) <= 10 && !txt_id.equalsIgnoreCase(bbw.getAccountId()) ) {

//            Toast.makeText(context, "myname--" + bbw.getAccountName(), Toast.LENGTH_LONG).show();

                holder.tvName.setText(bbw.getAccountName());
                holder.llleader.setVisibility(View.VISIBLE);
                holder.tvvalue.setText("$ " + bbw.getValue());
                holder.tvrank.setText("" + bbw.getRank());

                if (bbw.getImage() != null && !bbw.getImage().equals("")) {
                    Picasso.with(context).load(bbw.getImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(holder.ivCard);
                }
            }else{


            }*/

//            }

        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }

        @SuppressLint("NewApi")
        public Bitmap transformImg(Bitmap source, int color) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float r1 = size / 2f;
            canvas.drawCircle(r1, r1, r1, paint);
            //border code
            paint.setShader(null);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(context.getColor(color));
            paint.setStrokeWidth(1f);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivCard;
            LinearLayout llleader;
            TextView tvName, tvvalue, tvrank;

            public ViewHolder(View view) {
                super(view);
                ivCard = view.findViewById(R.id.ivCard);
                tvName = view.findViewById(R.id.tvName);
                tvvalue = view.findViewById(R.id.tvvalue);
                tvrank = view.findViewById(R.id.tvrank);
                llleader = view.findViewById(R.id.llleader);
                llleader = view.findViewById(R.id.llleader);


            }
        }
    }

    // Hight
    public class LeaderListHighAdapter extends RecyclerView.Adapter<NFLLeaderBoardScreen.LeaderListHighAdapter.ViewHolder> {


        private final List<HighestScoring> mValues;
        private Context context;


        public LeaderListHighAdapter(List<HighestScoring> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @NonNull
        @Override
        public NFLLeaderBoardScreen.LeaderListHighAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.leaderboard_view, parent, false);

            return new NFLLeaderBoardScreen.LeaderListHighAdapter.ViewHolder(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final NFLLeaderBoardScreen.LeaderListHighAdapter.ViewHolder holder, final int position) {


//            Toast.makeText(context, "myname--" + bbw.getAccountName(), Toast.LENGTH_LONG).show();


//            if(position>2){
            HighestScoring bbw = mValues.get(position);
            String txt_id = Profile.PID;
//            Toast.makeText(context, "myname--" + bbw.getAccountName(), Toast.LENGTH_LONG).show();

            if (Integer.valueOf(bbw.getRank()) == 1) {
//                holder.llleader.setBackground(Color.);
                holder.llleader.setBackgroundColor(Color.parseColor("#EBA540"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);

            } else if (Integer.valueOf(bbw.getRank()) == 2) {

                holder.llleader.setBackgroundColor(Color.parseColor("#5FC4FA"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);
            } else if (Integer.valueOf(bbw.getRank()) == 3) {

                holder.llleader.setBackgroundColor(Color.parseColor("#6FD5B7"));
                holder.tvName.setTextColor(Color.WHITE);
                holder.tvvalue.setTextColor(Color.WHITE);
                holder.tvrank.setTextColor(Color.WHITE);
            } else {
                holder.llleader.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvName.setTextColor(Color.BLACK);
                holder.tvvalue.setTextColor(Color.BLACK);
                holder.tvrank.setTextColor(Color.BLACK);

            }


//            if ((Integer.valueOf(bbw.getRank()) <= 10)) {

            if (bbw.getAccountId().contains(txt_id)) {
                holder.llleader.setVisibility(View.GONE);

            } else {
                holder.tvName.setText(bbw.getAccountName());
                holder.llleader.setVisibility(View.VISIBLE);
                holder.tvvalue.setText(bbw.getValue() + " wins");
                holder.tvrank.setText("" + bbw.getRank());
                if (bbw.getImage() != null && !bbw.getImage().equals("")) {
                    Picasso.with(context).load(bbw.getImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(holder.ivCard);
                }
            }

//            } else {
////                holder.tvName.setText("Text");
//                holder.llleader.setVisibility(View.GONE);
//
//            }

           /* if (Integer.valueOf(bbw.getRank()) <= 10) {

                holder.llleader.setVisibility(View.VISIBLE);
                holder.tvName.setText(bbw.getAccountName());
                holder.tvvalue.setText(bbw.getValue() + " wins");
                holder.tvrank.setText("" + bbw.getRank());

                if (bbw.getImage() != null && !bbw.getImage().equals("")) {
                    Picasso.with(context).load(bbw.getImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(holder.ivCard);
                }

            }*/

        }

        @Override
        public int getItemCount() {
            return this.mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivCard;
            LinearLayout llleader;
            TextView tvName, tvvalue, tvrank;

            public ViewHolder(View view) {
                super(view);
                ivCard = view.findViewById(R.id.ivCard);
                tvName = view.findViewById(R.id.tvName);
                tvvalue = view.findViewById(R.id.tvvalue);
                tvrank = view.findViewById(R.id.tvrank);
                llleader = view.findViewById(R.id.llleader);


            }
        }

        @SuppressLint("NewApi")
        public Bitmap transformImg(Bitmap source, int color) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float r1 = size / 2f;
            canvas.drawCircle(r1, r1, r1, paint);
            //border code
            paint.setShader(null);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(context.getColor(color));
            paint.setStrokeWidth(1f);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }
    }
}

