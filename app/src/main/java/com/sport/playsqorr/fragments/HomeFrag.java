package com.sport.playsqorr.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.login.LoginManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sport.playsqorr.adapters.Login_pageviwer;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.views.HomeCards_MoreScreen;
import com.sport.playsqorr.views.Login;
import com.sport.playsqorr.views.MatchupScreen_PlayAPick;
import com.sport.playsqorr.views.Matchup_NewWinScreen;
import com.sport.playsqorr.views.Matchup_WinPlayGoTimeTwo;
import com.sport.playsqorr.views.OnBoarding;
import com.sport.playsqorr.views.PlayPickGo_MatchupScreen;
import com.sport.playsqorr.views.TvFullScreenVideo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sport.playsqorr.R;

import com.sport.playsqorr.pojos.MyCardsPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.PresetValueButton;
import com.sport.playsqorr.utilities.Utilities;
import com.sport.playsqorr.views.Dashboard;
import com.sport.playsqorr.views.MatchupScreen;
import com.sport.playsqorr.views.PlayWithCash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static com.sport.playsqorr.utilities.Utilities.convertTParellelogram;
import static com.sport.playsqorr.utilities.Utilities.getAge;


public class HomeFrag extends Fragment implements View.OnClickListener {

    PresetValueButton all_pvb, pro_pvd, EPL_pvb, LA_LIGA_pvb, mlb_pvb, mls_pvb, NASCAR_pvb, NBA_pvb, NCAAMB_pvb, NCAAFB_pvb, WILD_pvb, NFL_pvb, NHL_pvb, PGA_pvb, WILD_pvb_g, WILD_pvb_b, WILD_pvb_h, WILD_pvb_f, WILD_pvb_n;
    private HorizontalScrollView horizontal, horizontal_new;
    LinearLayout ll_toppro;
    private RecyclerView rvHomeCards;
    View vv, vv1;
    ProgressBar progressBar;
    private List<Object> recyclerViewItems;

    private RecyclerViewAdapterNew recycleAdapter;
    private List<MyCardsPojo> cardsResponse = new ArrayList<>();
    private List<MyCardsPojo> myProRodeoList = new ArrayList<>();
    private List<MyCardsPojo> myProRodeoListBULL = new ArrayList<>();
    private List<MyCardsPojo> myProRodeoListRODEO = new ArrayList<>();
    private List<MyCardsPojo> myProRodeoListTIME = new ArrayList<>();
    private List<MyCardsPojo> cardsResponse_wild = new ArrayList<>();
    private List<MyCardsPojo> cardsList = new ArrayList<>();
    private int MY_CARDS_MAX = 3, CARDS_MAX = 2;
    private int myCardsCount = 0, prorodeoCount = 0, NFLCount = 0, NBACount = 0, NHLCount = 0, NASCARCount = 0, MLBCount = 0,
            EPLCount = 0, LALIGACount = 0, MLSCount = 0, NCAAMBCount = 0,
            PGACount = 0, NCAAFBCount = 0, WILDCount = 0, WILDCount_golf = 0, WILDCount_base = 0, WILDCount_horse = 0, WILDCount_NFL = 0, WILDCount_football = 0, WILDCount_gg = 0;
    private List<String> cardTypes = new ArrayList<>();
    private List<String> cardTypes_subtitle = new ArrayList<>();
    private List<String> cardTypes_subtitle_1 = new ArrayList<>();
    private Map<String, String> leagueData = new HashMap<>();
    private List<MyCardsPojo> myCardsData = new ArrayList<>();
    private List<MyCardsPojo> proRoedoData = new ArrayList<>();
    private List<MyCardsPojo> EPLData = new ArrayList<>();
    private List<MyCardsPojo> LALIGAData = new ArrayList<>();
    private List<MyCardsPojo> NFLData = new ArrayList<>();
    private List<MyCardsPojo> NBAData = new ArrayList<>();
    private List<MyCardsPojo> NHLData = new ArrayList<>();
    private List<MyCardsPojo> NASCARData = new ArrayList<>();
    private List<MyCardsPojo> MLBData = new ArrayList<>();
    private List<MyCardsPojo> MLSData = new ArrayList<>();
    private List<MyCardsPojo> NCAAMBData = new ArrayList<>();
    private List<MyCardsPojo> PGAData = new ArrayList<>();
    private List<MyCardsPojo> NCAAFBData = new ArrayList<>();
    private List<MyCardsPojo> WILDData = new ArrayList<>();
    private List<MyCardsPojo> WILDData_Golf = new ArrayList<>();
    private List<MyCardsPojo> WILDData_Base = new ArrayList<>();
    private List<MyCardsPojo> WILDData_Horse = new ArrayList<>();
    private List<MyCardsPojo> WILDData_NFL = new ArrayList<>();
    private List<MyCardsPojo> WILDData_football = new ArrayList<>();
    private List<MyCardsPojo> WILDData_gg = new ArrayList<>();

    private List<MyCardsPojo> upcomingCardsData = new ArrayList<>();
    private ImageView ivNoCards;
    private ImageView imageView2, imageView1, imageView12;
    private LinearLayout llNoCards;
    private String selLeagueId, selLeagueName;
    private TextView tvNoCards;
    private SwipeRefreshLayout swipe_container;
    private boolean fromRefresh = false;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    String video_id;
    YouTubePlayerTracker youTubePlayerTracker;
    int selectedPosition = -1;
    private boolean playautomatic = true;
    private boolean isAlreadyInitialaised = false;
    private boolean isImageloaded = false;
    private DataBaseHelper myDbHelper;
    String ROLE = "";

    public static Timer timer;
    int[] images = {R.drawable.howtoplay_1, R.drawable.mlb_1, R.drawable.banner_golf, R.drawable.howtoplay_1, R.drawable.nhl_1, R.drawable.foodball_123, R.drawable.gettyimage1, R.drawable.banner_nba};
    ViewPager viewPager;
    int dotcount;
    ImageView relLogin;
    ImageView[] dots;
    LinearLayout slideDotspoints, ll_top;

    Button bb1, bb2, bb3, bb4,bb5;
    Button pro_bb1, pro_bb2, pro_bb3, pro_bb4, pro_bb5, pro_bb6, pro_bb7;

    String Ctype;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragone, container, false);
        vv = v.findViewById(R.id.banner_c);
        vv1 = v.findViewById(R.id.banner_c1);
        rvHomeCards = v.findViewById(R.id.rvHomeCards);


        progressBar = v.findViewById(R.id.progressBar);
        llNoCards = v.findViewById(R.id.no_ll);

        tvNoCards = v.findViewById(R.id.no_txt);
        ivNoCards = v.findViewById(R.id.no_logo);

//        swipe_container = v.findViewById(R.id.swipe_container);


      /*  if ((PlayWithTokens.ppto != null )) {

            if (PlayWithTokens.ppto.equalsIgnoreCase("t1") ) {
                PlayWithTokens.ppto = "tt2";
                Intent ll = new Intent(getActivity(), ProfileEdit.class);
                startActivity(ll);
            }
        }

        if ((UserLocation.ppco != null )) {

            if (UserLocation.ppco.equalsIgnoreCase("t2") ) {
                UserLocation.ppco = "tt2";
//                UserLocation.ppco = "tt2";
                Intent ll = new Intent(getActivity(), ProfileEdit.class);
                startActivity(ll);
            }
        }

*/


        tvNoCards.setText("There are no cards right now");


        progressBar.setVisibility(View.VISIBLE);
        Log.d("ROLE", ROLE);
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
        if (ROLE != null && (ROLE.equalsIgnoreCase("cash") || ROLE.equalsIgnoreCase("tokens"))) {
            //    vv.setVisibility(View.GONE);
            //    vv1.setVisibility(View.VISIBLE);
            //    vv1.setOnClickListener(this);
        } else {
            //    vv.setVisibility(View.VISIBLE);
            //    vv.setOnClickListener(this);
            //    vv1.setVisibility(View.GONE);

        }
        ll_top = v.findViewById(R.id.ll_top);
        if (!Utilities.isNetworkAvailable(getActivity())) {
            Utilities.showNoInternetAlert(getActivity());
        } else {

            getHomeCardsFromServer("M");

        }


        recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
        rvHomeCards.setAdapter(recycleAdapter);


        slideDotspoints = v.findViewById(R.id.ph_news_indicators);
        viewPager = v.findViewById(R.id.login_viewpager);
        Login_pageviwer viewPagerAdapter = new Login_pageviwer(getActivity(), images, timer);
        viewPager.setAdapter(viewPagerAdapter);
        dotcount = viewPagerAdapter.getCount();
        dots = new ImageView[dotcount];

        for (int i = 0; i < dotcount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);
            slideDotspoints.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dots));
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "gfhfsd", Toast.LENGTH_SHORT).show();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {

                    for (int i = 0; i < dotcount; i++) {
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dots));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dots));


                } catch (Exception e) {

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        timer = new Timer();
//        timer.scheduleAtFixedRate(new MytimerTask(), 6000, 5000);

      /*  swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressBar.setVisibility(View.VISIBLE);
                recyclerViewItems.clear();
                if (selLeagueId != null && !selLeagueId.equals("")) {
                    myCardsData.clear();
                    myCardsCount = 0;
                    upcomingCardsData.clear();
                    leagueData.clear();
                    getIndividualTabsData(selLeagueId);
                } else {
                    resetData();
                    selLeagueId = "";
                    selLeagueName = "";
                    getHomeCardsFromServer();
                }
                swipe_container.setRefreshing(false);

            }
        });*/

        return v;
    }


    public class MytimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else if (viewPager.getCurrentItem() == 2) {
                            viewPager.setCurrentItem(3);
                        } else if (viewPager.getCurrentItem() == 3) {
                            viewPager.setCurrentItem(4);
                        } else if (viewPager.getCurrentItem() == 4) {
                            viewPager.setCurrentItem(5);
                        } else if (viewPager.getCurrentItem() == 5) {
                            viewPager.setCurrentItem(6);
                        } else if (viewPager.getCurrentItem() == 6) {
                            viewPager.setCurrentItem(7);
                        } else if (viewPager.getCurrentItem() == 7) {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    private void promoRedeemDialog1() {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_promo_redeem1, viewGroup, false);

        Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

        Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
        ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
        alertDialog.show();


        btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                alertDialog.dismiss();
                Activity act = getActivity();
                if (act instanceof Dashboard)
                    ((Dashboard) act).setPromo();
            }
        });

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), PlayWithCash.class);
                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                startActivity(intent);
            }
        });

        youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);
        getLifecycle().addObserver(youTubePlayerView);

        playVideo("", "https://www.youtube.com/watch?v=HmljRSEHm5M", "dd", "dd", true);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }

                alertDialog.dismiss();
                youTubePlayerView.release();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


            }
        });


    }

    /*public void startListening() {
        try {
            super.startListening();
        } catch (Exception e) {
            if (e.getCause() instanceof TransactionTooLargeException ||
                    e.getCause() instanceof DeadObjectException) {
                // We're willing to let this slide. The exception is being caused by the list of
                // RemoteViews which is being passed back. The startListening relationship will
                // have been established by this point, and we will end up populating the
                // widgets upon bind anyway. See issue 14255011 for more context.
            } else {
                throw new RuntimeException(e);
            }
        }
    }*/

    private void promoRedeemDialog() {
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_promo_redeem, viewGroup, false);

        Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

        Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
        ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
        alertDialog.show();


        btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                alertDialog.dismiss();
                Activity act = getActivity();
                if (act instanceof Dashboard)
                    ((Dashboard) act).setPromo();
            }
        });

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), PlayWithCash.class);
                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                startActivity(intent);
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

        youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);
        getLifecycle().addObserver(youTubePlayerView);
//        playVideo("", "https://www.youtube.com/watch?v=dZVcznuYd6Y", "dd","dd", true);

    }

    private void playVideo(String view, final String url, String title, String description, final boolean getIsFeatured) {

        String currentString = url;
        final String[] separated = currentString.split("=");
        if (!isAlreadyInitialaised) {
            youTubePlayerView.setEnableAutomaticInitialization(false);
            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    mYouTubePlayer = youTubePlayer;
                    youTubePlayerTracker = new YouTubePlayerTracker();
                    youTubePlayer.addListener(youTubePlayerTracker);
                    loadVideo(youTubePlayer, separated[1], getIsFeatured);
                    isAlreadyInitialaised = true;
                }
            });
        } else {
            loadVideo(mYouTubePlayer, separated[1], false);
        }
    }

    private void loadVideo(final YouTubePlayer youTubePlayer, String video_code, boolean getIsFeatured) {
        Log.v("VIDEO_CODE", video_code);
        video_id = video_code;
        if (getIsFeatured) {
            youTubePlayer.loadVideo(video_code, 0f);
        } else {
            youTubePlayer.loadVideo(video_code, 0f);
        }
       /* video_music_toggle_button.setClickable(true);
        fullscreen_button.setClickable(true);*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        all_pvb = getActivity().findViewById(R.id.all);
        pro_pvd = getActivity().findViewById(R.id.prorod);
        EPL_pvb = getActivity().findViewById(R.id.EPL);
        LA_LIGA_pvb = getActivity().findViewById(R.id.LA_LIGA);
        mlb_pvb = getActivity().findViewById(R.id.mlb);
        mls_pvb = getActivity().findViewById(R.id.mls);
        NASCAR_pvb = getActivity().findViewById(R.id.NASCAR);
        NBA_pvb = getActivity().findViewById(R.id.NBA);
        NCAAMB_pvb = getActivity().findViewById(R.id.NCAAMB);
        NCAAFB_pvb = getActivity().findViewById(R.id.NCAAFB);
        WILD_pvb = getActivity().findViewById(R.id.WILD);
        WILD_pvb_b = getActivity().findViewById(R.id.WILD);
        WILD_pvb_g = getActivity().findViewById(R.id.WILD);
        WILD_pvb_h = getActivity().findViewById(R.id.WILD);
        WILD_pvb_f = getActivity().findViewById(R.id.WILD);
        WILD_pvb_n = getActivity().findViewById(R.id.WILD);
        NFL_pvb = getActivity().findViewById(R.id.NFL);
        NHL_pvb = getActivity().findViewById(R.id.NHL);
        PGA_pvb = getActivity().findViewById(R.id.PGA);
        horizontal = getActivity().findViewById(R.id.horizontal);
        horizontal_new = getActivity().findViewById(R.id.horizontal_new);
        ll_toppro = getActivity().findViewById(R.id.ll_toppro);
        imageView2 = getActivity().findViewById(R.id.imageView2);
        imageView1 = getActivity().findViewById(R.id.imageView1);
        imageView12 = getActivity().findViewById(R.id.imageView12);


        bb1 = getActivity().findViewById(R.id.matchup_b1);
        bb2 = getActivity().findViewById(R.id.matchup_b2);
        bb3 = getActivity().findViewById(R.id.matchup_b3);
        bb4 = getActivity().findViewById(R.id.matchup_b4);
        bb5 = getActivity().findViewById(R.id.matchup_b5);

        pro_bb1 = getActivity().findViewById(R.id.pro_b1);
        pro_bb2 = getActivity().findViewById(R.id.pro_b2);
        pro_bb3 = getActivity().findViewById(R.id.pro_b3);
        pro_bb4 = getActivity().findViewById(R.id.pro_b4);
        pro_bb5 = getActivity().findViewById(R.id.pro_b5);
        pro_bb6 = getActivity().findViewById(R.id.pro_b6);
        pro_bb7 = getActivity().findViewById(R.id.pro_b7);


        bb1.setTag("11");
        bb2.setTag("11");
        bb3.setTag("11");
        bb4.setTag("11");
        bb5.setTag("11");


        pro_bb1.setTag("pro_11");
        pro_bb2.setTag("pro_11");
        pro_bb3.setTag("pro_11");
        pro_bb4.setTag("pro_11");
        pro_bb5.setTag("pro_11");
        pro_bb6.setTag("pro_11");
        pro_bb7.setTag("pro_11");

        pro_bb1.setOnClickListener(this);
        pro_bb2.setOnClickListener(this);
        pro_bb3.setOnClickListener(this);
        pro_bb4.setOnClickListener(this);
        pro_bb5.setOnClickListener(this);
        pro_bb6.setOnClickListener(this);
        pro_bb7.setOnClickListener(this);

        bb1.setOnClickListener(this);
        bb2.setOnClickListener(this);
        bb3.setOnClickListener(this);
        bb4.setOnClickListener(this);
        bb5.setOnClickListener(this);
        all_pvb.setOnClickListener(this);
        pro_pvd.setOnClickListener(this);
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
        horizontal.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView12.setOnClickListener(this);

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

        if (!Dashboard.SESSIONTOKEN.equalsIgnoreCase("null")) {
            userprofdileupdate();

        }
//        RecyclerViewLayoutManager= new LinearLayoutManager(getActivity());
//
//        // Set LayoutManager on Recycler View
//        rvHomeCards.setLayoutManager(RecyclerViewLayoutManager);
//        HorizontalLayout  = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
//        rvHomeCards.setLayoutManager(HorizontalLayout);
//        rvHomeCards.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
//
        bb2.setBackgroundColor(Color.parseColor("#ffffff"));
        bb2.setTextColor(Color.parseColor("#1A323E"));

        bb3.setBackgroundColor(Color.parseColor("#ffffff"));
        bb3.setTextColor(Color.parseColor("#1A323E"));

        bb4.setBackgroundColor(Color.parseColor("#ffffff"));
        bb4.setTextColor(Color.parseColor("#1A323E"));

        bb5.setBackgroundColor(Color.parseColor("#ffffff"));
        bb5.setTextColor(Color.parseColor("#1A323E"));

        bb1.setBackgroundColor(Color.parseColor("#000000"));
        bb1.setTextColor(Color.parseColor("#ffffff"));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all /*2131230804*/:
                recyclerViewItems.clear();
                getActivity().findViewById(R.id.all);//setSelected(false);
//                getActivity().findViewById(R.id.all).setSelected(true);
                resetData();
                selLeagueId = "";
                selLeagueName = "";
                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);
                if (bb1.getTag().equals("11")) {
                    handleDiffData("MATCH-UP");
                    setPageData("MATCH-UP");
                } else {
                    handleDiffData("PLAY TAC TOE");
                    setPageData("PLAY TAC TOE");
                }


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
                if (recycleAdapter != null)
                    recycleAdapter.notifyDataSetChanged();
                return;
            case R.id.EPL /*2131230804*/:
                getActivity().findViewById(R.id.EPL);//.setSelected(true);
                // sethomeAll(getResources().getString(R.string.epl_lg_id),"EPL");
                //sethomeOther(getResources().getString(R.string.epl_lg_id));

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    selLeagueId = getResources().getString(R.string.epl_lg_id);
                    selLeagueName = "EPL";
                    fromRefresh = false;

                    HandleIndividualTabs("MATCH-UP");
                } else {
                    selLeagueId = getResources().getString(R.string.epl_lg_id);
                    selLeagueName = "EPL";
                    fromRefresh = false;

                    HandleIndividualTabs("PLAY TAC TOE");
                }


                return;
            case R.id.LA_LIGA /*2131230804*/:
                getActivity().findViewById(R.id.LA_LIGA);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.LALIGA_lg_id));
                selLeagueId = getResources().getString(R.string.LALIGA_lg_id);
                selLeagueName = "LA_LIGA";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.mlb /*2131230804*/:
                getActivity().findViewById(R.id.mlb);//.setSelected(true);
                //  sethomeOther(getResources().getString(R.string.mlb_lg_id));
                selLeagueId = getResources().getString(R.string.mlb_lg_id);
                selLeagueName = "MLB";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.mls /*2131230804*/:
                getActivity().findViewById(R.id.mls);
                // sethomeOther(getResources().getString(R.string.mls_lg_id));
                selLeagueId = getResources().getString(R.string.mls_lg_id);
                selLeagueName = "MLS";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.NASCAR /*2131230804*/:
                getActivity().findViewById(R.id.NASCAR);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.nascar_lg_id));
                selLeagueId = getResources().getString(R.string.nascar_lg_id);
                selLeagueName = "NASCAR";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);


                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.NBA /*2131230804*/:
                getActivity().findViewById(R.id.NBA);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.nba_lg_id));
                selLeagueId = getResources().getString(R.string.nba_lg_id);
                selLeagueName = "NBA";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.NCAAMB /*2131230804*/:
                getActivity().findViewById(R.id.NCAAMB);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.NCAAMB_lg_id));
                selLeagueId = getResources().getString(R.string.NCAAMB_lg_id);
                selLeagueName = "NCAAMB";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);


                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
            case R.id.NFL /*2131230804*/:
                getActivity().findViewById(R.id.NFL);//.setSelected(true);
                //  sethomeOther(getResources().getString(R.string.nfl_lg_id));
                selLeagueId = getResources().getString(R.string.nfl_lg_id);
                selLeagueName = "NFL";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);


                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.NHL /*2131230804*/:

                getActivity().findViewById(R.id.NHL);//.setSelected(true);
                //  sethomeOther(getResources().getString(R.string.nhl_lg_id));
                selLeagueId = getResources().getString(R.string.nhl_lg_id);
                selLeagueName = "NHL";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);


                if (bb1.getTag().equals("11")) {

                    HandleIndividualTabs("MATCH-UP");
                } else {
//                    getActivity().findViewById(R.id.NHL);//.setSelected(true);
//                    //  sethomeOther(getResources().getString(R.string.nhl_lg_id));
//                    selLeagueId = getResources().getString(R.string.nhl_lg_id);
//                    selLeagueName = "NHL";
//                    fromRefresh = false;
                    HandleIndividualTabs("PLAY TAC TOE");
                }

                return;
            case R.id.PGA /*2131230804*/:
                getActivity().findViewById(R.id.PGA);
                // sethomeOther(getResources().getString(R.string.pga_lg_id));
                selLeagueId = getResources().getString(R.string.pga_lg_id);
                selLeagueName = "PGA";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.NCAAFB /*2131230804*/:
                getActivity().findViewById(R.id.NCAAFB);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.NCAAMB_lg_id));
                selLeagueId = getResources().getString(R.string.nccfb_id);
                selLeagueName = "NCAAFB";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.WILD /*2131230804*/:
                getActivity().findViewById(R.id.WILD);//.setSelected(true);
                // sethomeOther(getResources().getString(R.string.NCAAMB_lg_id));
                selLeagueId = getResources().getString(R.string.wildcard_id);
                selLeagueName = "WILD CARD";
                fromRefresh = false;

                horizontal_new.setVisibility(View.VISIBLE);
                ll_toppro.setVisibility(View.GONE);

                if (cardTypes_subtitle.size() > 0) {
//
                    HandleIndividualTabs("MATCH-UP");

                }
                if (bb1.getTag().equals("11")) {
                    HandleIndividualTabs("MATCH-UP");
                } else {

                    HandleIndividualTabs("PLAY TAC TOE");
                }
                return;
            case R.id.prorod /*2131230804*/:

                getActivity().findViewById(R.id.prorod).setSelected(true);
                // sethomeOther(getResources().getString(R.string.LALIGA_lg_id));
                selLeagueId = getResources().getString(R.string.pro_lg_id);
                selLeagueName = "PRORODEO";
                fromRefresh = false;

                horizontal_new.setVisibility(View.GONE);
                ll_toppro.setVisibility(View.VISIBLE);
                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb1.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb1.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_11");
                pro_bb2.setTag("pro_11");
                pro_bb3.setTag("pro_11");
                pro_bb4.setTag("pro_11");
                pro_bb5.setTag("pro_11");
                pro_bb6.setTag("pro_11");
                pro_bb7.setTag("pro_11");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServerBull("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }

//                getActivity().findViewById(R.id.prorod);//.setSelected(true);
//                // sethomeOther(getResources().getString(R.string.LALIGA_lg_id));
//                selLeagueId = getResources().getString(R.string.pro_lg_id);
//                selLeagueName = "PRO RODEO";
//                fromRefresh = false;
////                if (bb1.getTag().equals("11")) {
////                    HandleIndividualTabs("MATCH-UP");
////                } else {
////
////                    HandleIndividualTabs("PLAY TAC TOE");
////                }
//
//                horizontal_new.setVisibility(View.GONE);
//                ll_toppro.setVisibility(View.VISIBLE);
//
//                getHomeCardsFromServerBull("M");
//                getHomeCardsFromServerRODEO("B");
//                getHomeCardsFromServerTIME("B");
//
//             //   Log.e("101-------------",myProRodeoList.size()+"");
//          //      HandleIndividualTabs("PRORODEO");


                return;
            case R.id.imageView12:
                promoRedeemDialog1();
                return;
            case R.id.horizontal:
                promoRedeemDialog();
                return;
            case R.id.banner_c:
                promoRedeemDialog();
//            case R.id.imageView2:
//                promoRedeemDialog();
                return;
            case R.id.imageView1:
                promoRedeemDialog();
                return;
            case R.id.matchup_b1:

                bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                bb2.setTextColor(Color.parseColor("#1A323E"));
                bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                bb3.setTextColor(Color.parseColor("#1A323E"));
                bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                bb4.setTextColor(Color.parseColor("#1A323E"));
                bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                bb5.setTextColor(Color.parseColor("#1A323E"));

                bb1.setBackgroundColor(Color.parseColor("#000000"));
                bb1.setTextColor(Color.parseColor("#ffffff"));

                bb1.setTag("11");
                bb2.setTag("11");
                bb3.setTag("11");
                bb4.setTag("11");
                bb5.setTag("11");


//                recycleAdapter.getFilter().filter("Matchup");


                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServer("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            case R.id.matchup_b2:


                bb2.setBackgroundColor(Color.parseColor("#000000"));
                bb2.setTextColor(Color.parseColor("#ffffff"));
                bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                bb1.setTextColor(Color.parseColor("#1A323E"));
                bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                bb3.setTextColor(Color.parseColor("#1A323E"));
                bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                bb4.setTextColor(Color.parseColor("#1A323E"));
                bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                bb5.setTextColor(Color.parseColor("#1A323E"));

                bb1.setTag("22");
                bb2.setTag("22");
                bb3.setTag("22");
                bb4.setTag("22");
                bb5.setTag("22");

                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
//                rvHomeCards.
                getHomeCardsFromServerP("PLAY TAC TOE");

                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);


                return;

            case R.id.matchup_b3:


                bb3.setBackgroundColor(Color.parseColor("#000000"));
                bb3.setTextColor(Color.parseColor("#ffffff"));
                bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                bb1.setTextColor(Color.parseColor("#1A323E"));
                bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                bb2.setTextColor(Color.parseColor("#1A323E"));
                bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                bb4.setTextColor(Color.parseColor("#1A323E"));
                bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                bb5.setTextColor(Color.parseColor("#1A323E"));

                bb1.setTag("33");
                bb2.setTag("33");
                bb3.setTag("33");
                bb4.setTag("33");
                bb5.setTag("33");

                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
//                rvHomeCards.
                getHomeCardsFromServerABC("ABC");

                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);


                return;
            case R.id.matchup_b4:

                bb4.setBackgroundColor(Color.parseColor("#000000"));
                bb4.setTextColor(Color.parseColor("#ffffff"));
                bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                bb1.setTextColor(Color.parseColor("#1A323E"));
                bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                bb2.setTextColor(Color.parseColor("#1A323E"));
                bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                bb3.setTextColor(Color.parseColor("#1A323E"));
                bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                bb5.setTextColor(Color.parseColor("#1A323E"));

                bb1.setTag("44");
                bb2.setTag("44");
                bb3.setTag("44");
                bb4.setTag("44");
                bb5.setTag("44");

                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
//                rvHomeCards.
                getHomeCardsFromServerWIN("WIN PLAY SHOW");

                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);


                return;
            case R.id.matchup_b5:

                bb5.setBackgroundColor(Color.parseColor("#000000"));
                bb5.setTextColor(Color.parseColor("#ffffff"));
                bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                bb1.setTextColor(Color.parseColor("#1A323E"));
                bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                bb2.setTextColor(Color.parseColor("#1A323E"));
                bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                bb3.setTextColor(Color.parseColor("#1A323E"));
                bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                bb4.setTextColor(Color.parseColor("#1A323E"));

                bb1.setTag("55");
                bb2.setTag("55");
                bb3.setTag("55");
                bb4.setTag("55");
                bb5.setTag("55");

                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
//                rvHomeCards.
                getHomeCardsFromServerGO("WIN PLAY GO");

                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);


                return;
            case R.id.pro_b1:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb1.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb1.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_11");
                pro_bb2.setTag("pro_11");
                pro_bb3.setTag("pro_11");
                pro_bb4.setTag("pro_11");
                pro_bb5.setTag("pro_11");
                pro_bb6.setTag("pro_11");
                pro_bb7.setTag("pro_11");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServerBull("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;

            case R.id.pro_b2:

                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb2.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb2.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_12");
                pro_bb2.setTag("pro_12");
                pro_bb3.setTag("pro_12");
                pro_bb4.setTag("pro_12");
                pro_bb5.setTag("pro_12");
                pro_bb6.setTag("pro_12");
                pro_bb7.setTag("pro_12");

                if (pro_bb1.getTag().equals("11")) {
//                    selLeagueId = getResources().getString(R.string.epl_lg_id);
//                    selLeagueName = "EPL";
//                    fromRefresh = false;

                    HandleIndividualTabs("MATCH-UP");
                }

//                recycleAdapter.getFilter().filter("Matchup");


                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServerRODEO("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;

            case R.id.pro_b3:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb3.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb3.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_13");
                pro_bb2.setTag("pro_13");
                pro_bb3.setTag("pro_13");
                pro_bb4.setTag("pro_13");
                pro_bb5.setTag("pro_13");
                pro_bb6.setTag("pro_13");
                pro_bb7.setTag("pro_13");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServerTIME("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            case R.id.pro_b4:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb4.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb4.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_14");
                pro_bb2.setTag("pro_14");
                pro_bb3.setTag("pro_14");
                pro_bb4.setTag("pro_14");
                pro_bb5.setTag("pro_14");
                pro_bb6.setTag("pro_14");
                pro_bb7.setTag("pro_14");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServer4("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            case R.id.pro_b5:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb5.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb5.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_15");
                pro_bb2.setTag("pro_15");
                pro_bb3.setTag("pro_15");
                pro_bb4.setTag("pro_15");
                pro_bb5.setTag("pro_15");
                pro_bb6.setTag("pro_15");
                pro_bb7.setTag("pro_15");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServer5("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            case R.id.pro_b6:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));
                pro_bb7.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb7.setTextColor(Color.parseColor("#1A323E"));

                pro_bb6.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb6.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_16");
                pro_bb2.setTag("pro_16");
                pro_bb3.setTag("pro_16");
                pro_bb4.setTag("pro_16");
                pro_bb5.setTag("pro_16");
                pro_bb6.setTag("pro_16");
                pro_bb7.setTag("pro_16");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServer6("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            case R.id.pro_b7:

                pro_bb2.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb2.setTextColor(Color.parseColor("#1A323E"));
                pro_bb1.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb1.setTextColor(Color.parseColor("#1A323E"));
                pro_bb3.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb3.setTextColor(Color.parseColor("#1A323E"));
                pro_bb4.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb4.setTextColor(Color.parseColor("#1A323E"));
                pro_bb6.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb6.setTextColor(Color.parseColor("#1A323E"));
                pro_bb5.setBackgroundColor(Color.parseColor("#ffffff"));
                pro_bb5.setTextColor(Color.parseColor("#1A323E"));

                pro_bb7.setBackgroundColor(Color.parseColor("#000000"));
                pro_bb7.setTextColor(Color.parseColor("#ffffff"));

                pro_bb1.setTag("pro_17");
                pro_bb2.setTag("pro_17");
                pro_bb3.setTag("pro_17");
                pro_bb4.setTag("pro_17");
                pro_bb5.setTag("pro_17");
                pro_bb6.setTag("pro_17");
                pro_bb7.setTag("pro_17");

//                recycleAdapter.getFilter().filter("Matchup");

                HandleIndividualTabs("MATCH-UP");
                recyclerViewItems = new ArrayList<>();
                recyclerViewItems.clear();
                cardsResponse = new ArrayList<>();
                cardsResponse.clear();
                resetData();
                getHomeCardsFromServer7("M");
////
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvHomeCards.setAdapter(recycleAdapter);

                if (recycleAdapter != null) {
                    recycleAdapter.notifyDataSetChanged();

                }


                return;
            default:
                break;
        }
    }

    // Play PICK changed to PLAY TAC TOE
    //To get data of ALL tab data of home
    private void getHomeCardsFromServerP(final String msg) {

        Ctype = "";
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("ALL +HOME :: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                if (jb.getString("cardType").equalsIgnoreCase("PLAY TAC TOE")) {

                                    Ctype = "PLAY TAC TOE";
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

//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


                                  /*  JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);
*/
                                    cardsResponse.add(mp);

                                } else {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("PLAY TAC TOE");
                        setPageData("PLAY TAC TOE");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {

                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    //ABC
    private void getHomeCardsFromServerABC(final String msg) {

        Ctype = "";
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("PLAY A PICK", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                if (jb.getString("cardTitle").contains("ABC")) {
                                if (jb.getString("cardType").equalsIgnoreCase("PLAY A PICK")) {

                                    Ctype = "PLAY A PICK";

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

//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

                                    cardsResponse.add(mp);

                                } else {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("PLAY A PICK");
                        setPageData("PLAY A PICK");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {

                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    //WIN PLAY SHOW
    private void getHomeCardsFromServerWIN(final String msg) {
        Ctype = "";


        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("PLAY Show", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                if (jb.getString("cardType").equalsIgnoreCase("WIN PLAY SHOW")) {

                                    Ctype = "WIN PLAY SHOW";

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

//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);
/*

                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);*/

                                    cardsResponse.add(mp);

                                } else {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("WIN PLAY SHOW");
                        setPageData("WIN PLAY SHOW");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {

                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }


    //WIN PLAY GO
    private void getHomeCardsFromServerGO(final String msg) {
        Ctype = "";


        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("PLAY Show", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                if (jb.getString("cardType").equalsIgnoreCase("WIN PLAY GO")) {

                                    Ctype = "WIN PLAY GO";

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

//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);
/*

                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);*/

                                    cardsResponse.add(mp);

                                } else {

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("WIN PLAY GO");
                        setPageData("WIN PLAY GO");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {

                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }


    // BULL PRo
    private void getHomeCardsFromServerBull(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListBULL.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);


                                if (jb.getString("cardType").equalsIgnoreCase("MAKE THE 8")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "MAKE THE 8";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
                                    //         Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListBULL.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006---------b", "" + myProRodeoListBULL.size());

                        handleDiffData("MAKE THE 8");
                        setPageData("MAKE THE 8");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    // BULL PRo
    private void getHomeCardsFromServerRODEO(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListRODEO.clear();
                        Log.e("ALL +HOME pro:: B&R ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);


                                if (jb.getString("cardType").equalsIgnoreCase("BRONC N ROLL")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "BRONC N ROLL";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListRODEO.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("2006 ------r", "" + myProRodeoListRODEO.size());
//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");

                        handleDiffData("BRONC N ROLL");
                        setPageData("BRONC N ROLL");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    // TIME PRo
    private void getHomeCardsFromServerTIME(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
                //  .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListTIME.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                cardType -> WIN PLAY SQOR
                                if (jb.getString("cardType").equalsIgnoreCase("WIN PLAY SQOR")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "WIN PLAY SQOR";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);

//
//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListTIME.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006", "" + myProRodeoListTIME.size());

//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");
                        handleDiffData("WIN PLAY SQOR");
                        setPageData("WIN PLAY SQOR");
//
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    //4
    private void getHomeCardsFromServer4(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
                //  .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListTIME.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                cardType -> WIN PLAY SQOR
                                if (jb.getString("cardType").equalsIgnoreCase("SADDLE BRONC")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "SADDLE BRONC";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);

//
//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListTIME.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006", "" + myProRodeoListTIME.size());

//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");
                        handleDiffData("SADDLE BRONC");
                        setPageData("SADDLE BRONC");
//
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }
    //5
    private void getHomeCardsFromServer5(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
                //  .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListTIME.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                cardType -> WIN PLAY SQOR
                                if (jb.getString("cardType").equalsIgnoreCase("RIDE PLAY SQOR")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "RIDE PLAY SQOR";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);

//
//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListTIME.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006", "" + myProRodeoListTIME.size());

//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");
                        handleDiffData("RIDE PLAY SQOR");
                        setPageData("RIDE PLAY SQOR");
//
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {




                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }
    //6
    private void getHomeCardsFromServer6(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
                //  .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListTIME.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                cardType -> WIN PLAY SQOR
                                if (jb.getString("cardType").equalsIgnoreCase("COWGRLZ")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "COWGRLZ";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);

//
//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListTIME.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006", "" + myProRodeoListTIME.size());

//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");
                        handleDiffData("COWGRLZ");
                        setPageData("COWGRLZ");
//
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {




                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }
    //7
    private void getHomeCardsFromServer7(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
                //  .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        myProRodeoListTIME.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

//                                cardType -> WIN PLAY SQOR
                                if (jb.getString("cardType").equalsIgnoreCase("HEAD N HEEL")) {
                                    //     ll_top.setVisibility(View.GONE);
//                                    Ctype = "PRO RODEO";
                                    Ctype = "HEAD N HEEL";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


//                                    mp.setIsPurchased(jb.getString("isPurchased"));
//                                    mp.setIsLive(jb.getString("isLive"));
//                                    Log.e("cards- 1691-", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);

//
//                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
//                                    List<String> player_ids = new ArrayList<>();
//
//                                    for (int j = 0; j < pjson_ids.length(); j++) {
//                                        player_ids.add(String.valueOf(pjson_ids.get(j)));
//
//                                    }
//                                    mp.setPlayerCardIds(player_ids);

//                                    myProRodeoListTIME.add(mp);
                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.e("2006", "" + myProRodeoListTIME.size());

//                        handleDiffData("MAKE THE 8");
//                        setPageData("MAKE THE 8");
                        handleDiffData("HEAD N HEEL");
                        setPageData("HEAD N HEEL");
//
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {




                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }
    // PRORODEO
    private void getHomeCardsFromServerALL(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("ALL +HOME pro:: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);


                                if (jb.getString("cardType").equalsIgnoreCase("BRONC N ROLL") &&
                                        jb.getString("cardType").equalsIgnoreCase("MAKE THE 8") &&
                                        jb.getString("cardType").equalsIgnoreCase("WIN PLAY SQOR")) {
                                    //     ll_top.setVisibility(View.GONE);
                                    Ctype = "PRO RODEO";
//                                    Ctype = "BRONC N ROLL";
                                    MyCardsPojo mp = new MyCardsPojo();

//                                JSONArray ja = jb.getJSONArray("playerImages");
//
//                                if (ja.length() > 0) {
//                                    String a1 = String.valueOf(ja.get(0));
//                                    String a2 = String.valueOf(ja.get(1));
//                                    mp.setPlayerImageLeft(a1);
//                                    mp.setPlayerImageRight(a2);
//                                }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


                                    mp.setIsPurchased(jb.getString("isPurchased"));
                                    mp.setIsLive(jb.getString("isLive"));
                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);

                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("ALL");
                        setPageData("ALL");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        }

                    }
                });

    }

    // MATCHUP
    //To get data of ALL tab data of home
    private void getHomeCardsFromServer(String msg) {
        Ctype = "";
        Log.e("ALL API :: ", APIs.GET_CARDS);
        recyclerViewItems = new ArrayList<>();
        AndroidNetworking.get(APIs.GET_CARDS)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        cardsResponse.clear();
                        Log.e("ALL +HOME :: ", response.toString());

                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);


                                if (jb.getString("cardType").equalsIgnoreCase("MATCH-UP")) {
                                    //     ll_top.setVisibility(View.GONE);
                                    Ctype = "MATCH-UP";
                                    MyCardsPojo mp = new MyCardsPojo();

                                    JSONArray ja = jb.getJSONArray("playerImages");

                                    if (ja.length() > 0) {
                                        String a1 = String.valueOf(ja.get(0));
                                        String a2 = String.valueOf(ja.get(1));
                                        mp.setPlayerImageLeft(a1);
                                        mp.setPlayerImageRight(a2);
                                    }


                                    mp.setCardId(jb.getString("cardId"));
                                    mp.setCardTitle(jb.getString("cardTitle"));
                                    mp.setMatchupType(jb.getString("matchupType"));
                                    mp.setCardType(jb.getString("cardType"));
                                    mp.setStartTime(jb.getString("startTime"));
                                    mp.setLeagueId(jb.getString("leagueId"));

//                                if(jb.getString("leagueSubTitle")!=null){
                                    mp.setLeagueSubTitle(jb.getString("leagueSubTitle"));
//                            }


                                    mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));


                              /*      mp.setIsPurchased(jb.getString("isPurchased"));
                                    mp.setIsLive(jb.getString("isLive"));
                                    Log.e("cards--", jb.getJSONArray("playerCardIds") + "----");
//                                playerCardIds
                                    //         mp.setPlayerCardIds(player_ids);


                                    JSONArray pjson_ids = jb.getJSONArray("playerCardIds");
                                    List<String> player_ids = new ArrayList<>();

                                    for (int j = 0; j < pjson_ids.length(); j++) {
                                        player_ids.add(String.valueOf(pjson_ids.get(j)));

                                    }
                                    mp.setPlayerCardIds(player_ids);*/

                                    cardsResponse.add(mp);

                                } else {
                                    ll_top.setVisibility(View.VISIBLE);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData("MATCH-UP");
                        setPageData("MATCH-UP");

                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        progressBar.setVisibility(View.GONE);
                        if (error.getErrorCode() != 0) {



                          /*  if (error.getErrorCode() == 401) {

                                Intent in_login = new Intent(getActivity(), Login.class);
                                startActivity(in_login);
                                getActivity().finish();
//                                Utilities.showToast(getActivity(), "Please");
                            } else {
                                Utilities.showToast(getActivity(), "Something went wrong,Please try again later.");
                            }
*/
                            if (error.getErrorCode() == 403) {
                                Utilities.showToast(getActivity(), getString(R.string.servererror_msg));
                            }

                            try {
                                JSONObject ej = new JSONObject(error.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                String ab = ej.getString("error");
                                if (au.contains("Unauthorized") || au.contains("ab")) {
                                    showAlertBox(getActivity(), "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(getActivity(), ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


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

    //To get individual tabs data on pull to refresh by user
    private void getIndividualTabsData(String leagueId) {

        AndroidNetworking.get(APIs.GET_CARDS + "?leagueId=" + leagueId)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.e("TabsData:: ", response.toString());
                        cardsList.clear();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                MyCardsPojo mp = new MyCardsPojo();

                                JSONArray ja = jb.getJSONArray("playerImages");

                                String a1 = String.valueOf(ja.get(0));
                                String a2 = String.valueOf(ja.get(1));

                                mp.setCardId(jb.getString("cardId"));
                                mp.setCardTitle(jb.getString("cardTitle"));
                                mp.setMatchupType(jb.getString("matchupType"));
                                mp.setStartTime(jb.getString("startTime"));
                                mp.setLeagueId(jb.getString("leagueId"));
                                mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));
                                mp.setPlayerImageLeft(a1);
                                mp.setPlayerImageRight(a2);

                                mp.setIsPurchased(jb.getString("isPurchased"));
                                mp.setIsLive(jb.getString("isLive"));

                                cardsList.add(mp);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        fromRefresh = true;
                        if (bb1.getTag().equals("11")) {
                            HandleIndividualTabs("MATCH-UP");
                        } else {

                            HandleIndividualTabs("PLAY TAC TOE");
                        }
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.GONE);
                        Utilities.showToast(getActivity(), error.getErrorBody());
                    }
                });

    }


    private void handleDiffData(String dd) {


        //If No response
        if (cardsResponse.size() <= 0) {
            rvHomeCards.setVisibility(View.GONE);
            llNoCards.setVisibility(View.VISIBLE);
            ivNoCards.setImageResource(R.drawable.tro_b);
        } else { // If we had cards in response
            rvHomeCards.setVisibility(View.VISIBLE);
            llNoCards.setVisibility(View.GONE);
            processResponseData(dd);
        }
    }


    //Process the cards fetched from server
    private void processResponseData(String dd) {


        if (dd.equalsIgnoreCase(Ctype)) {

            for (int i = 0; i < cardsResponse.size(); i++) {

                if (cardsResponse.get(i).getIsPurchased().contains("true")) {
                    if (myCardsCount < MY_CARDS_MAX) {
                        myCardsData.add(cardsResponse.get(i));
                    }
                    myCardsCount++;
                }

                //else removed
                {
                    String leagueName = cardsResponse.get(i).getLeagueAbbrevation();
                    if (!cardTypes.contains(leagueName)) {
                        cardTypes.add(leagueName);
                    } else {
                        cardTypes.add(cardsResponse.get(i).getCardType());

                    }
                    //   03-08 myself removed Kalyan
//                    if (cardsResponse.get(i).getLeagueSubTitle() != null || !cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("null")) {
//                        String league_sub = cardsResponse.get(i).getLeagueSubTitle();
//                        if (!cardTypes_subtitle.contains(league_sub)) {
//                            cardTypes_subtitle.add(league_sub);
//                            cardTypes_subtitle_1.add(league_sub);
//                        }
//                    }

                    String leagueId = cardsResponse.get(i).getLeagueId();
                    if (!leagueData.containsKey(leagueName))
                        leagueData.put(leagueName, leagueId);


                    Log.e("22222222---------", leagueName);
                    switch (leagueName) {
                        case "NFL":
                            if (NFLCount < CARDS_MAX) {
                                NFLData.add(cardsResponse.get(i));
                            }
                            NFLCount++;
                            break;

                        case "NBA":
                            if (NBACount < CARDS_MAX) {
                                NBAData.add(cardsResponse.get(i));
                            }
                            NBACount++;
                            break;

                        case "NHL":
                            if (NHLCount < CARDS_MAX) {
                                NHLData.add(cardsResponse.get(i));
                            }
                            NHLCount++;
                            break;

                        case "NASCAR":
                            if (NASCARCount < CARDS_MAX) {
                                NASCARData.add(cardsResponse.get(i));
                            }
                            NASCARCount++;
                            break;

                        case "MLB":
                            if (MLBCount < CARDS_MAX) {
                                MLBData.add(cardsResponse.get(i));
                            }
                            MLBCount++;
                            break;

                        case "EPL":
                            if (EPLCount < CARDS_MAX) {
                                EPLData.add(cardsResponse.get(i));
                            }
                            EPLCount++;
                            break;
                        case "PRORODEO":
                            if (prorodeoCount < CARDS_MAX) {
                                proRoedoData.add(cardsResponse.get(i));
                            }
                            prorodeoCount++;
                            break;

                        case "LA-LIGA":
                            if (LALIGACount < CARDS_MAX) {
                                LALIGAData.add(cardsResponse.get(i));
                            }
                            LALIGACount++;
                            break;

                        case "MLS":
                            if (MLSCount < CARDS_MAX) {
                                MLSData.add(cardsResponse.get(i));
                            }
                            MLSCount++;
                            break;

                        case "NCAAMB":
                            if (NCAAMBCount < CARDS_MAX) {
                                NCAAMBData.add(cardsResponse.get(i));
                            }
                            NCAAMBCount++;
                            break;
                        case "NCAAFB":
                            if (NCAAFBCount < CARDS_MAX) {
                                NCAAFBData.add(cardsResponse.get(i));
                            }
                            NCAAFBCount++;
                            break;
                        case "WILD CARD":
//                            cardTypes_subtitle.clear();WILDCARD Baseball Future
                            Log.e("1620--", "kalyan---- list");
//                            WILDCount_horse=0;
//                            WILDCount_base=0;
//                            WILDCount_golf=0;

                            if (leagueName.equalsIgnoreCase("WILD CARD")) {
//                                if (cardTypes_subtitle.size() > 0) {
//
//                                    //      Log.e("base---K", "1621--kalyan" + cardsResponse.get(i).getLeagueSubTitle() + "--" + cardsResponse.get(i).getCardTitle());
//
//
//                                    for (MyCardsPojo t : cardsResponse) {
//                                        if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Baseball Future")) {
//
//
//                                            if (WILDCount_base < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                WILDData_Base.add(t);
//                                                //    }
//                                            }
//                                            WILDCount_base++;
//
////                                            Log.e("size---k", "330--" + WILDData_Golf.size());
//                                        }else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//
//
//                                            if (WILDCount_golf < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                WILDData_Golf.add(t);
//                                                //    }
//                                            }
//                                            WILDCount_golf++;
//
//
//                                        }else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Horse Racing")) {
//
//
//                                            if (WILDCount_horse < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                WILDData_Horse.add(t);
//                                                //    }
//                                            }
//                                            WILDCount_horse++;
//
////
//                                        }else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD NFL Draft")) {
//
//
//                                            if (WILDCount_NFL < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                WILDData_NFL.add(t);
//                                                //    }
//                                            }
//                                            WILDCount_NFL++;
//
////
//                                        }else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Football")) {
//
//
//                                            if (WILDCount_football < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                WILDData_football.add(t);
//                                                //    }
//                                            }
//                                            WILDCount_football++;
//
////
//                                        }else if (t.getLeagueSubTitle().equals("WILDCARD Golf")) {
//
//
//                                            if (WILDCount_gg < CARDS_MAX) {
////
//                                                WILDData_gg.add(t);
//
//                                            }
//                                            WILDCount_gg++;
//
//                                            Log.e("size---k", "330--gg" + WILDData_gg.size());
//
//                                        }
//
//                                    }
//
//                                } else {
//                                    if (WILDCount < CARDS_MAX) {
//                                        WILDData.add(cardsResponse.get(i));
//                                    }
//                                    WILDCount++;
//                                }
                            }

                            break;

                        case "PGA":
                            if (PGACount < CARDS_MAX) {
                                PGAData.add(cardsResponse.get(i));
                            }
                            PGACount++;
                            break;
                        default:
                            break;
                    }


                }
            }
        } else {
            for (int i = 0; i < cardsResponse.size(); i++) {


                if (cardsResponse.get(i).getIsPurchased().contains("true")) {
                    if (myCardsCount < MY_CARDS_MAX) {
                        myCardsData.add(cardsResponse.get(i));
                    }
                    myCardsCount++;
                }

                //else removed
                {
                    String leagueName = cardsResponse.get(i).getLeagueAbbrevation();
                    if (!cardTypes.contains(leagueName)) {
                        cardTypes.add(leagueName);
                    }
                    String leagueId = cardsResponse.get(i).getLeagueId();
                    if (!leagueData.containsKey(leagueName))
                        leagueData.put(leagueName, leagueId);

                    switch (leagueName) {
                        case "NFL":
                            if (NFLCount < CARDS_MAX) {
                                NFLData.add(cardsResponse.get(i));
                            }
                            NFLCount++;
                            break;

                        case "NBA":
                            if (NBACount < CARDS_MAX) {
                                NBAData.add(cardsResponse.get(i));
                            }
                            NBACount++;
                            break;

                        case "NHL":
                            if (NHLCount < CARDS_MAX) {
                                NHLData.add(cardsResponse.get(i));
                            }
                            NHLCount++;
                            break;

                        case "NASCAR":
                            if (NASCARCount < CARDS_MAX) {
                                NASCARData.add(cardsResponse.get(i));
                            }
                            NASCARCount++;
                            break;

                        case "MLB":
                            if (MLBCount < CARDS_MAX) {
                                MLBData.add(cardsResponse.get(i));
                            }
                            MLBCount++;
                            break;

                        case "EPL":
                            if (EPLCount < CARDS_MAX) {
                                EPLData.add(cardsResponse.get(i));
                            }
                            EPLCount++;
                            break;
                        case "PRORODEO":
                            if (prorodeoCount < CARDS_MAX) {
                                proRoedoData.add(cardsResponse.get(i));
                            }
                            prorodeoCount++;
                            break;

                        case "LA-LIGA":
                            if (LALIGACount < CARDS_MAX) {
                                LALIGAData.add(cardsResponse.get(i));
                            }
                            LALIGACount++;
                            break;

                        case "MLS":
                            if (MLSCount < CARDS_MAX) {
                                MLSData.add(cardsResponse.get(i));
                            }
                            MLSCount++;
                            break;

                        case "NCAAMB":
                            if (NCAAMBCount < CARDS_MAX) {
                                NCAAMBData.add(cardsResponse.get(i));
                            }
                            NCAAMBCount++;
                            break;
                        case "NCAAFB":
                            if (NCAAFBCount < CARDS_MAX) {
                                NCAAFBData.add(cardsResponse.get(i));
                            }
                            NCAAFBCount++;
                            break;
                        case "WILD CARD":
//////                            if (WILDCount < CARDS_MAX) {
//////                                WILDData.add(cardsResponse.get(i));
//////                            }
//////                            WILDCount++;
////
                            if (cardTypes_subtitle.size() > 0) {
//
//
////
////                                for (int j = 0; j < cardTypes_subtitle.size(); j++) {
//////                                    recyclerViewItems.add(cardTypes_subtitle.get(j));
//////                                    recyclerViewItems.addAll(WILDData_Golf);
////
////                                    if (WILDCount_golf < CARDS_MAX) {
////                                        WILDData_Golf.add(cardsResponse.get(i));
////                                    }
////                                    WILDCount_golf++;
////
//////                                   if (WILDCount < CARDS_MAX) {
//////                                       WILDData.add(cardsResponse.get(i));
//////                                   }
//////                                   WILDCount++;
////                                }
////
////                              /*  for (int j = 0; j < cardTypes_subtitle.size(); j++) {
////                                    if (cardTypes_subtitle.get(j).equalsIgnoreCase("WILDCARD Golf Future")) {
////                                        if (WILDCount_golf < CARDS_MAX) {
////                                            WILDData_Golf.add(cardsResponse.get(j));
////                                        }
////                                        WILDCount_golf++;
////                                    } else if (cardTypes_subtitle.get(j).equalsIgnoreCase("WILDCARD Baseball Future")) {
////                                        if (WILDCount_base < CARDS_MAX) {
////                                            WILDData_Base.add(cardsResponse.get(j));
////                                        }
////                                        WILDCount_base++;
////                                    }
////
////
////                                }*/
////
//////                                if (WILDCount < CARDS_MAX) {
//////                                    WILDData.add(cardsResponse.get(i));
//////                                }
//////                                WILDCount++;
////
//                                if (leagueName.equalsIgnoreCase("WILD CARD")) {
//                                    if (cardTypes_subtitle.size() > 0) {
//
//                                        //      Log.e("base---K", "1621--kalyan" + cardsResponse.get(i).getLeagueSubTitle() + "--" + cardsResponse.get(i).getCardTitle());
//
//
//                                        for (MyCardsPojo t : cardsResponse) {
//                                            if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Baseball Future")) {
//
//
//                                                if (WILDCount_base < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                    WILDData_Base.add(t);
//                                                    //    }
//                                                }
//                                                WILDCount_base++;
//
//                                                Log.e("size---k", "330--" + WILDData_Golf.size());
//                                            }
//
//
//                                            if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//
//
//                                                if (WILDCount_golf < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                    WILDData_Golf.add(t);
//                                                    //    }
//                                                }
//                                                WILDCount_golf++;
//
//                                                Log.e("size---k", "330--" + WILDData_Golf.size());
//                                            }
//                                            if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Horse Racing")) {
//
//
//                                                if (WILDCount_horse < CARDS_MAX) {
////                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
////                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
////                                                            WILDData_Golf.add(cardsResponse.get(i));
////                                                        } else {
////
////                                                        }---
//                                                    WILDData_Horse.add(t);
//                                                    //    }
//                                                }
//                                                WILDCount_horse++;
//
//                                                Log.e("size---k", "330--" + WILDData_Golf.size());
//                                            }
//
//                                        }
//
//                                    } else {
//                                        if (WILDCount < CARDS_MAX) {
//                                            WILDData.add(cardsResponse.get(i));
//                                        }
//                                        WILDCount++;
//                                    }
//                                }
//
//
////
                            } else {
                                if (WILDCount < CARDS_MAX) {
                                    WILDData.add(cardsResponse.get(i));
                                }
                                WILDCount++;
                            }
////


                            if (WILDCount < CARDS_MAX) {
                                WILDData.add(cardsResponse.get(i));
                            }
                            WILDCount++;

                            break;

                        case "PGA":
                            if (PGACount < CARDS_MAX) {
                                PGAData.add(cardsResponse.get(i));
                            }
                            PGACount++;
                            break;
                        default:
                            break;
                    }
                }
            }


        }
        if (cardTypes_subtitle.size() > 0) {


            //      Log.e("base---K", "1621--kalyan" + cardsResponse.get(i).getLeagueSubTitle() + "--" + cardsResponse.get(i).getCardTitle());


            for (MyCardsPojo t : cardsResponse) {
                if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Baseball Future")) {


                    if (WILDCount_base < CARDS_MAX) {
//                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
//                                                            WILDData_Golf.add(cardsResponse.get(i));
//                                                        } else {
//
//                                                        }---
                        WILDData_Base.add(t);
                        //    }
                    }
                    WILDCount_base++;

//                                            Log.e("size---k", "330--" + WILDData_Golf.size());
                } else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {


                    if (WILDCount_golf < CARDS_MAX) {
//                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
//                                                            WILDData_Golf.add(cardsResponse.get(i));
//                                                        } else {
//
//                                                        }---
                        WILDData_Golf.add(t);
                        //    }
                    }
                    WILDCount_golf++;


                } else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Horse Racing")) {


                    if (WILDCount_horse < CARDS_MAX) {
//                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
//                                                            WILDData_Golf.add(cardsResponse.get(i));
//                                                        } else {
//
//                                                        }---
                        WILDData_Horse.add(t);
                        //    }
                    }
                    WILDCount_horse++;

//
                } else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD NFL Draft")) {


                    if (WILDCount_NFL < CARDS_MAX) {
//                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
//                                                            WILDData_Golf.add(cardsResponse.get(i));
//                                                        } else {
//
//                                                        }---
                        WILDData_NFL.add(t);
                        //    }
                    }
                    WILDCount_NFL++;

//
                } else if (t.getLeagueSubTitle().equalsIgnoreCase("WILDCARD Football")) {


                    if (WILDCount_football < CARDS_MAX) {
//                                                      /*  //    if(cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase("WILDCARD Golf Future")) {
//                                                        if (cardsResponse.get(i).getLeagueSubTitle().equalsIgnoreCase(cardTypes_subtitle.get(j))) {
//                                                            WILDData_Golf.add(cardsResponse.get(i));
//                                                        } else {
//
//                                                        }---
                        WILDData_football.add(t);
                        //    }
                    }
                    WILDCount_football++;

//
                } else if (t.getLeagueSubTitle().equals("WILDCARD Golf")) {


                    if (WILDCount_gg < CARDS_MAX) {
//
                        WILDData_gg.add(t);

                    }
                    WILDCount_gg++;

                    Log.e("size---k", "330--gg" + WILDData_gg.size());

                }

            }

        } else {
//                if (WILDCount < CARDS_MAX) {
//                    WILDData.add(cardsResponse.get(i));
//                }
//                WILDCount++;
        }


           /* } else {
                if (cardsResponse.get(i).getIsPurchased().contains("true")) {
                    if (myCardsCount < MY_CARDS_MAX) {
                        myCardsData.add(cardsResponse.get(i));
                    }
                    myCardsCount++;
                }


                //else removed
                {
                    String leagueName = cardsResponse.get(i).getLeagueAbbrevation();
                    if (!cardTypes.contains(leagueName)) {
                        cardTypes.add(leagueName);
                    }
                    String leagueId = cardsResponse.get(i).getLeagueId();
                    if (!leagueData.containsKey(leagueName))
                        leagueData.put(leagueName, leagueId);

                    switch (leagueName) {
                        case "NFL":
                            if (NFLCount < CARDS_MAX) {
                                NFLData.add(cardsResponse.get(i));
                            }
                            NFLCount++;
                            break;

                        case "NBA":
                            if (NBACount < CARDS_MAX) {
                                NBAData.add(cardsResponse.get(i));
                            }
                            NBACount++;
                            break;

                        case "NHL":
                            if (NHLCount < CARDS_MAX) {
                                NHLData.add(cardsResponse.get(i));
                            }
                            NHLCount++;
                            break;

                        case "NASCAR":
                            if (NASCARCount < CARDS_MAX) {
                                NASCARData.add(cardsResponse.get(i));
                            }
                            NASCARCount++;
                            break;

                        case "MLB":
                            if (MLBCount < CARDS_MAX) {
                                MLBData.add(cardsResponse.get(i));
                            }
                            MLBCount++;
                            break;

                        case "EPL":
                            if (EPLCount < CARDS_MAX) {
                                EPLData.add(cardsResponse.get(i));
                            }
                            EPLCount++;
                            break;

                        case "LA-LIGA":
                            if (LALIGACount < CARDS_MAX) {
                                LALIGAData.add(cardsResponse.get(i));
                            }
                            LALIGACount++;
                            break;

                        case "MLS":
                            if (MLSCount < CARDS_MAX) {
                                MLSData.add(cardsResponse.get(i));
                            }
                            MLSCount++;
                            break;

                        case "NCAAMB":
                            if (NCAAMBCount < CARDS_MAX) {
                                NCAAMBData.add(cardsResponse.get(i));
                            }
                            NCAAMBCount++;
                            break;
                        case "NCAAFB":
                            if (NCAAFBCount < CARDS_MAX) {
                                NCAAFBData.add(cardsResponse.get(i));
                            }
                            NCAAFBCount++;
                            break;
                        case "WILD CARD":
                            if (WILDCount < CARDS_MAX) {
                                WILDData.add(cardsResponse.get(i));
                            }
                            WILDCount++;
                            break;

                        case "PGA":
                            if (PGACount < CARDS_MAX) {
                                PGAData.add(cardsResponse.get(i));
                            }
                            PGACount++;
                            break;
                        default:
                            break;
                    }
                }

            }


            */

        //   }

    }

    private void setPageData(String dd) {


        if (dd.equalsIgnoreCase("MATCH-UP")) {
            if (myCardsCount > 0) {
//                recyclerViewItems.add("My cards");
//                recyclerViewItems.addAll(myCardsData);

                Log.e("2065---K  my", myCardsData.size() + "");
            }

            if (selLeagueId == null || selLeagueId.equals("")) {
                for (int i = 0; i < cardTypes.size(); i++) {
                    Log.e("2065---K  my", cardTypes.get(i));

                    switch (cardTypes.get(i)) {
                        case "NFL":
                            recyclerViewItems.add("NFL");
                            recyclerViewItems.addAll(NFLData);
                            break;

                        case "NBA":
                            recyclerViewItems.add("NBA");
                            recyclerViewItems.addAll(NBAData);
                            break;

                        case "NHL":
                            recyclerViewItems.add("NHL");
                            recyclerViewItems.addAll(NHLData);
                            break;

                        case "NASCAR":
                            recyclerViewItems.add("NASCAR");
                            recyclerViewItems.addAll(NASCARData);
                            break;

                        case "MLB":
                            recyclerViewItems.add("MLB");
                            recyclerViewItems.addAll(MLBData);
                            break;

                        case "EPL":
                            recyclerViewItems.add("EPL");
                            recyclerViewItems.addAll(EPLData);
                            break;
                        case "PRORODEO":
                            recyclerViewItems.add("PRO RODEO");
                            recyclerViewItems.addAll(proRoedoData);
                            break;

                        case "LA-LIGA":
                            recyclerViewItems.add("LA-LIGA");
                            recyclerViewItems.addAll(LALIGAData);
                            break;

                        case "MLS":
                            recyclerViewItems.add("MLS");
                            recyclerViewItems.addAll(MLSData);
                            break;

                        case "NCAAMB":
                            recyclerViewItems.add("NCAAMB");
                            recyclerViewItems.addAll(NCAAMBData);
                            break;
                        case "NCAAFB":
                            recyclerViewItems.add("NCAAFB");
                            recyclerViewItems.addAll(NCAAFBData);
                            break;
                        case "WILD CARD":

                            Log.e("1973---K", cardTypes_subtitle.toString());
//                            recyclerViewItems.add("WILD CARD");
//                            recyclerViewItems.addAll(WILDData);
                            Log.e("1973---S", WILDData_Golf.size() + "");
                            if (cardTypes_subtitle.size() > 0) {
//                                for (int j = 0; j < cardTypes_subtitle.size(); j++) {
//                                    recyclerViewItems.add(cardTypes_subtitle.get(j));
//                                    recyclerViewItems.addAll(WILDData_Golf);
//                                }

                                for (int j = 0; j < cardTypes_subtitle.size(); j++) {
                                    switch (cardTypes_subtitle.get(j)) {

                                        case "WILDCARD Golf Future":
                                            recyclerViewItems.add("WILDCARD Golf Future");
                                            recyclerViewItems.addAll(WILDData_Golf);
                                            break;
                                        case "WILDCARD Baseball Future":
                                            recyclerViewItems.add("WILDCARD Baseball Future");
                                            recyclerViewItems.addAll(WILDData_Base);
                                            break;
                                        case "WILDCARD Horse Racing":
                                            recyclerViewItems.add("WILDCARD Horse Racing");
                                            recyclerViewItems.addAll(WILDData_Horse);
                                            break;

                                        case "WILDCARD NFL Draft":
                                            recyclerViewItems.add("WILDCARD NFL Draft");
                                            recyclerViewItems.addAll(WILDData_NFL);
                                            break;
                                        case "WILDCARD Football":
                                            recyclerViewItems.add("WILDCARD Football");
                                            recyclerViewItems.addAll(WILDData_football);
                                            break;
                                        case "WILDCARD Golf":
                                            recyclerViewItems.add("WILDCARD Golf");
                                            recyclerViewItems.addAll(WILDData_gg);
                                            break;
                                        default:
                                            break;

                                    }
                                }

                            } else {
                                recyclerViewItems.add("WILD CARD");
                                recyclerViewItems.addAll(WILDData);
                            }
                            //    recyclerViewItems.add("WILD CARD");
                            //    recyclerViewItems.addAll(WILDData);
                            break;

                        case "PGA":
                            recyclerViewItems.add("PGA");
                            recyclerViewItems.addAll(PGAData);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                for (int i = 0; i < cardTypes.size(); i++) {

                    Log.e("333-----------", cardTypes.get(i));

                    switch (cardTypes.get(i)) {
                        case "NFL":
                            recyclerViewItems.add("NFL");
                            recyclerViewItems.addAll(NFLData);
                            break;

                        case "NBA":
                            recyclerViewItems.add("NBA");
                            recyclerViewItems.addAll(NBAData);
                            break;

                        case "NHL":
                            recyclerViewItems.add("NHL");
                            recyclerViewItems.addAll(NHLData);
                            break;

                        case "NASCAR":
                            recyclerViewItems.add("NASCAR");
                            recyclerViewItems.addAll(NASCARData);
                            break;

                        case "MLB":
                            recyclerViewItems.add("MLB");
                            recyclerViewItems.addAll(MLBData);
                            break;

                        case "EPL":
                            recyclerViewItems.add("EPL");
                            recyclerViewItems.addAll(EPLData);
                            break;
                        case "PRORODEO":
                            recyclerViewItems.add("PRO RODEO");
                            recyclerViewItems.addAll(proRoedoData);
                            break;
                        case "LA-LIGA":
                            recyclerViewItems.add("LA-LIGA");
                            recyclerViewItems.addAll(LALIGAData);
                            break;

                        case "MLS":
                            recyclerViewItems.add("MLS");
                            recyclerViewItems.addAll(MLSData);
                            break;

                        case "NCAAMB":
                            recyclerViewItems.add("NCAAMB");
                            recyclerViewItems.addAll(NCAAMBData);
                            break;
                        case "NCAAFB":
                            recyclerViewItems.add("NCAAFB");
                            recyclerViewItems.addAll(NCAAFBData);
                            break;
                        case "WILD CARD":
//                            recyclerViewItems.add("WILD CARD");
//                            recyclerViewItems.addAll(WILDData);
                            Log.e("2065---K", cardTypes_subtitle.toString());
                            if (cardTypes_subtitle.size() > 0) {

//                                for (int j = 0; j < cardTypes_subtitle.size(); j++) {
//                                    recyclerViewItems.add(cardTypes_subtitle.get(j));
//                                    recyclerViewItems.addAll(WILDData_Golf);
//                                }
                                for (int j = 0; j < cardTypes_subtitle.size(); j++) {
                                    switch (cardTypes_subtitle.get(j)) {

                                        case "WILDCARD Golf Future":
                                            recyclerViewItems.add("WILDCARD Golf Future");
                                            recyclerViewItems.addAll(WILDData_Golf);
                                            break;
                                        case "WILDCARD Baseball Future":
                                            recyclerViewItems.add("WILDCARD Baseball Future");
//                                            recyclerViewItems.addAll(WILDData_Golf);
                                            recyclerViewItems.addAll(WILDData_Base);
                                            break;
                                        case "WILDCARD Horse Racing":
                                            recyclerViewItems.add("WILDCARD Horse Racing");
                                            recyclerViewItems.addAll(WILDData_Horse);
                                            break;
                                        case "WILDCARD NFL Draft":
                                            recyclerViewItems.add("WILDCARD NFL Draft");
                                            recyclerViewItems.addAll(WILDData_NFL);
                                            break;
                                        case "WILDCARD Football":
                                            recyclerViewItems.add("WILDCARD Football");
//                                            recyclerViewItems.addAll(WILDData_Golf);
                                            recyclerViewItems.addAll(WILDData_football);
                                            break;
                                        case "WILDCARD Golf":
                                            recyclerViewItems.add("WILDCARD Golf");
                                            recyclerViewItems.addAll(WILDData_gg);
                                            break;
                                        default:
                                            break;

                                    }
                                }

                              /*  for (int j = 0; j < cardTypes_subtitle.size(); j++) {
                                    switch (cardTypes_subtitle.get(j)) {

                                        case "WILDCARD Golf Future":
                                            recyclerViewItems.add("WILDCARD Golf Future");
                                            recyclerViewItems.addAll(WILDData_Golf);
                                            break;
                                        case "WILDCARD Baseball Future":
                                            recyclerViewItems.add("WILDCARD Baseball Future");
                                            recyclerViewItems.addAll(WILDData_Base);
                                            break;
                                        default:
                                            break;
                                    }
                                }*/

                            } else {
                                recyclerViewItems.add("WILD CARD");
                                recyclerViewItems.addAll(WILDData);
                            }


                            break;

                        case "PGA":
                            recyclerViewItems.add("PGA");
                            recyclerViewItems.addAll(PGAData);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {

            {
                if (myCardsCount > 0) {
//                    recyclerViewItems.add("My cards");
//                    recyclerViewItems.addAll(myCardsData);
                }

                if (selLeagueId == null || selLeagueId.equals("")) {
                    for (int i = 0; i < cardTypes.size(); i++) {
                        switch (cardTypes.get(i)) {
                            case "NFL":
                                recyclerViewItems.add("NFL");
                                recyclerViewItems.addAll(NFLData);
                                break;

                            case "NBA":
                                recyclerViewItems.add("NBA");
                                recyclerViewItems.addAll(NBAData);
                                break;

                            case "NHL":
                                recyclerViewItems.add("NHL");
                                recyclerViewItems.addAll(NHLData);
                                break;

                            case "NASCAR":
                                recyclerViewItems.add("NASCAR");
                                recyclerViewItems.addAll(NASCARData);
                                break;

                            case "MLB":
                                recyclerViewItems.add("MLB");
                                recyclerViewItems.addAll(MLBData);
                                break;

                            case "EPL":
                                recyclerViewItems.add("EPL");
                                recyclerViewItems.addAll(EPLData);
                                break;
                            case "PRORODEO":
                                recyclerViewItems.add("PRO RODEO");
                                recyclerViewItems.addAll(proRoedoData);
                                break;
                            case "LA-LIGA":
                                recyclerViewItems.add("LA-LIGA");
                                recyclerViewItems.addAll(LALIGAData);
                                break;

                            case "MLS":
                                recyclerViewItems.add("MLS");
                                recyclerViewItems.addAll(MLSData);
                                break;

                            case "NCAAMB":
                                recyclerViewItems.add("NCAAMB");
                                recyclerViewItems.addAll(NCAAMBData);
                                break;
                            case "NCAAFB":
                                recyclerViewItems.add("NCAAFB");
                                recyclerViewItems.addAll(NCAAFBData);
                                break;
                            case "WILD CARD":

//                                Log.e("2165---K", cardTypes_subtitle.toString());
////                                if (cardTypes_subtitle.size() > 0) {
////
//////                                    for (int j = 0; j < cardTypes_subtitle.size(); j++) {
//////                                        recyclerViewItems.add(cardTypes_subtitle.get(j));
//////                                        recyclerViewItems.addAll(WILDData_Golf);
//////                                    }
////                                    for (int j = 0; j < cardTypes_subtitle.size(); j++) {
////                                        switch (cardTypes_subtitle.get(j)) {
////
////                                            case "WILDCARD Golf Future":
////                                                recyclerViewItems.add("WILDCARD Golf Future");
////                                                recyclerViewItems.addAll(WILDData_Golf);
////                                                break;
////                                            case "WILDCARD Baseball Future":
////                                                recyclerViewItems.add("WILDCARD Baseball Future");
//////                                            recyclerViewItems.addAll(WILDData_Golf);
////                                                recyclerViewItems.addAll(WILDData_Base);
////                                            case "WILDCARD Horse Racing":
////                                                recyclerViewItems.add("WILDCARD Horse Racing");
//////                                            recyclerViewItems.addAll(WILDData_Golf);
////                                                recyclerViewItems.addAll(WILDData_Horse);
////                                                break;
////
////                                        }
////                                    }
////
////                                  /*  for (int j = 0; j < cardTypes_subtitle.size(); j++) {
////                                        switch (cardTypes_subtitle.get(j)) {
////
////                                            case "WILDCARD Golf Future":
////                                                recyclerViewItems.add("WILDCARD Golf Future");
////                                                recyclerViewItems.addAll(WILDData_Golf);
////                                                break;
////                                            case "WILDCARD Baseball Future":
////                                                recyclerViewItems.add("WILDCARD Baseball Future");
////                                                recyclerViewItems.addAll(WILDData_Base);
////                                                break;
////                                            default:
////                                                break;
////                                        }
////                                    }*/
////
//////                                    recyclerViewItems.add(cardTypes_subtitle);
//////                                    recyclerViewItems.addAll(WILDData);
////
////                                } else {
////                                    recyclerViewItems.add("WILD CARD");
////                                    recyclerViewItems.addAll(WILDData);
////                                }

                                recyclerViewItems.add("WILD CARD");
                                recyclerViewItems.addAll(WILDData);
                                break;

                            case "PGA":
                                recyclerViewItems.add("PGA");
                                recyclerViewItems.addAll(PGAData);
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    for (int i = 0; i < cardTypes.size(); i++) {

                        switch (cardTypes.get(i)) {
                            case "NFL":
                                recyclerViewItems.add("NFL");
                                recyclerViewItems.addAll(NFLData);
                                break;

                            case "NBA":
                                recyclerViewItems.add("NBA");
                                recyclerViewItems.addAll(NBAData);
                                break;

                            case "NHL":
                                recyclerViewItems.add("NHL");
                                recyclerViewItems.addAll(NHLData);
                                break;

                            case "NASCAR":
                                recyclerViewItems.add("NASCAR");
                                recyclerViewItems.addAll(NASCARData);
                                break;

                            case "MLB":
                                recyclerViewItems.add("MLB");
                                recyclerViewItems.addAll(MLBData);
                                break;

                            case "EPL":
                                recyclerViewItems.add("EPL");
                                recyclerViewItems.addAll(EPLData);
                                break;
                            case "PRORODEO":
                                recyclerViewItems.add("PRO RODEO");
                                recyclerViewItems.addAll(proRoedoData);
                                break;
                            case "LA-LIGA":
                                recyclerViewItems.add("LA-LIGA");
                                recyclerViewItems.addAll(LALIGAData);
                                break;

                            case "MLS":
                                recyclerViewItems.add("MLS");
                                recyclerViewItems.addAll(MLSData);
                                break;

                            case "NCAAMB":
                                recyclerViewItems.add("NCAAMB");
                                recyclerViewItems.addAll(NCAAMBData);
                                break;
                            case "NCAAFB":
                                recyclerViewItems.add("NCAAFB");
                                recyclerViewItems.addAll(NCAAFBData);
                                break;
                            case "WILD CARD":
//                                recyclerViewItems.add("WILD CARD");
//                                recyclerViewItems.addAll(WILDData);
                                Log.e("2258---K", cardTypes_subtitle.toString());
                                if (cardTypes_subtitle.size() > 0) {

//                                    for (int j = 0; j < cardTypes_subtitle.size(); j++) {
//                                        recyclerViewItems.add(cardTypes_subtitle.get(j));
//                                        recyclerViewItems.addAll(WILDData_Golf);
//                                    }
                                    for (int j = 0; j < cardTypes_subtitle.size(); j++) {
                                        switch (cardTypes_subtitle.get(j)) {

                                            case "WILDCARD Golf Future":
                                                recyclerViewItems.add("WILDCARD Golf Future");
                                                recyclerViewItems.addAll(WILDData_Golf);
                                                break;
                                            case "WILDCARD Baseball Future":
                                                recyclerViewItems.add("WILDCARD Baseball Future");
//                                            recyclerViewItems.addAll(WILDData_Golf);
                                                recyclerViewItems.addAll(WILDData_Base);
                                                break;
//                                            case "WILDCARD Horse Racing":
//                                                recyclerViewItems.add("WILDCARD Horse Racing");
////                                            recyclerViewItems.addAll(WILDData_Golf);
//                                                recyclerViewItems.addAll(WILDData_Horse);
//                                                break;
                                            case "WILDCARD Horse Racing":
                                                recyclerViewItems.add("WILDCARD Horse Racing");
                                                recyclerViewItems.addAll(WILDData_Horse);
                                                break;
                                            case "WILDCARD NFL Draft":
                                                recyclerViewItems.add("WILDCARD NFL Draft");
                                                recyclerViewItems.addAll(WILDData_NFL);
                                                break;
                                            case "WILDCARD Football":
                                                recyclerViewItems.add("WILDCARD Football");
//                                            recyclerViewItems.addAll(WILDData_Golf);
                                                recyclerViewItems.addAll(WILDData_football);
                                                break;
                                            case "WILDCARD Golf":
                                                recyclerViewItems.add("WILDCARD Golf");
                                                recyclerViewItems.addAll(WILDData_gg);
                                                break;
                                            default:
                                                break;

                                        }
                                    }
                                  /*  for (int j = 0; j < cardTypes_subtitle.size(); j++) {
                                        switch (cardTypes_subtitle.get(j)) {

                                            case "WILDCARD Golf Future":
                                                recyclerViewItems.add("WILDCARD Golf Future");
                                                recyclerViewItems.addAll(WILDData_Golf);
                                                break;
                                            case "WILDCARD Baseball Future":
                                                recyclerViewItems.add("WILDCARD Baseball Future");
                                                recyclerViewItems.addAll(WILDData_Base);
                                                break;
                                            default:
                                                break;
                                        }
                                    }*/

//                                    recyclerViewItems.add(cardTypes_subtitle);
//                                    recyclerViewItems.addAll(WILDData);

                                } else {

                                    recyclerViewItems.add("WILD CARD");
                                    recyclerViewItems.addAll(WILDData);
                                }

                                break;

                            case "PGA":
                                recyclerViewItems.add("PGA");
                                recyclerViewItems.addAll(PGAData);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }


    }


    private void HandleMyCardsForIndividualTabs(String dd) {

        if (dd.equalsIgnoreCase("MATCH-UP")) {
            if (fromRefresh) {
                for (int i = 0; i < cardsList.size(); i++) {
                    if (cardsList.get(i).getLeagueId().equals(selLeagueId)) {

                        //  if (cardsList.get(i).getCardType().equalsIgnoreCase(ctype)) {
                        if (cardsList.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsList.get(i));
                            }
                            myCardsCount++;
                        }
//                    else removed
                        {
                            upcomingCardsData.add(cardsList.get(i));
                        }
                  /*  } else {
                        if (cardsList.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsList.get(i));
                            }
                            myCardsCount++;
                        }
//                    else removed
                        {
                            upcomingCardsData.add(cardsList.get(i));
                        }
                    }*/

                    }
                }
            } else {
                for (int i = 0; i < cardsResponse.size(); i++) {
                    if (cardsResponse.get(i).getLeagueId().equals(selLeagueId)) {
                        if (cardsResponse.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsResponse.get(i));
                            }
                            myCardsCount++;
                        }
                        //else removed
                        {
                            upcomingCardsData.add(cardsResponse.get(i));
                        }
                    } else {
//                        if (cardTypes_subtitle.size() > 0) {
//
//                            upcomingCardsData.add(cardsResponse.get(i));
//
//                        }
                    }
                }
            }
            if (myCardsCount > 0) {
//                recyclerViewItems.add("My cards");
//                recyclerViewItems.addAll(myCardsData);
            }

            if (upcomingCardsData.size() > 0) {
                llNoCards.setVisibility(View.GONE);
                recyclerViewItems.add("Upcoming cards");
                recyclerViewItems.addAll(upcomingCardsData);

            } else {
                llNoCards.setVisibility(View.VISIBLE);
                Log.e("2823---", "NO data");
                handleNoData();
            }
            if (recycleAdapter != null) {
                recycleAdapter.notifyDataSetChanged();

            }


        } else {
            if (fromRefresh) {
                for (int i = 0; i < cardsList.size(); i++) {
                    if (cardsList.get(i).getLeagueId().equals(selLeagueId)) {

                        //  if (cardsList.get(i).getCardType().equalsIgnoreCase(ctype)) {
                        if (cardsList.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsList.get(i));
                            }
                            myCardsCount++;
                        }
//                    else removed
                        {
                            upcomingCardsData.add(cardsList.get(i));
                        }
                  /*  } else {
                        if (cardsList.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsList.get(i));
                            }
                            myCardsCount++;
                        }
//                    else removed
                        {
                            upcomingCardsData.add(cardsList.get(i));
                        }
                    }*/

                    } else {

                    }
                }
            } else {
                for (int i = 0; i < cardsResponse.size(); i++) {
                    if (cardsResponse.get(i).getLeagueId().equals(selLeagueId)) {
                        if (cardsResponse.get(i).getIsPurchased().contains("true")) {
                            if (myCardsCount < MY_CARDS_MAX) {
                                myCardsData.add(cardsResponse.get(i));
                            }
                            myCardsCount++;
                        }
                        //else removed
                        {
                            upcomingCardsData.add(cardsResponse.get(i));
                        }
                    } else {
//                        if (cardTypes_subtitle.size() > 0) {
//
//                                    upcomingCardsData.add(cardsResponse.get(i));
//
//                        }
                    }
                }
            }
            if (myCardsCount > 0) {
//                recyclerViewItems.add("My cards");
//                recyclerViewItems.addAll(myCardsData);
            }

            if (upcomingCardsData.size() > 0) {
                llNoCards.setVisibility(View.GONE);
                recyclerViewItems.add("Upcoming cards");
                recyclerViewItems.addAll(upcomingCardsData);
            } else {
                llNoCards.setVisibility(View.VISIBLE);
                handleNoData();
                Log.e("2823---", "NO data 2");
            }
            if (recycleAdapter != null) {
                recycleAdapter.notifyDataSetChanged();

            }

        }


        ///------------- one

    }


    @SuppressLint("SetTextI18n")
    private void handleNoData() {

        ivNoCards.setColorFilter(ivNoCards.getContext().getResources().getColor(R.color.light_gray), PorterDuff.Mode.SRC_ATOP);
        if (selLeagueId.equals(getResources().getString(R.string.nfl_lg_id))) {
            tvNoCards.setText("There are no NFL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_am_football);
        } else if (selLeagueId.equals(getResources().getString(R.string.nba_lg_id))) {
            tvNoCards.setText("There are no NBA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_basketball);
        } else if (selLeagueId.equals(getResources().getString(R.string.nhl_lg_id))) {
            tvNoCards.setText("There are no NHL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_hockey);
        } else if (selLeagueId.equals(getResources().getString(R.string.nascar_lg_id))) {
            tvNoCards.setText("There are no NASCAR cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_nascar_h);
        } else if (selLeagueId.equals(getResources().getString(R.string.mlb_lg_id))) {
            tvNoCards.setText("There are no MLB cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_baseball);
        } else if (selLeagueId.equals(getResources().getString(R.string.epl_lg_id))) {
            tvNoCards.setText("There are no EPL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_tennis);
        } else if (selLeagueId.equals(getResources().getString(R.string.pro_lg_id))) {
            tvNoCards.setText("There are no PRO RODEO cards right now.");
            ivNoCards.setImageResource(R.drawable.rodeo);
        } else if (selLeagueId.equals(getResources().getString(R.string.LALIGA_lg_id))) {
            tvNoCards.setText("There are no LA-LIGA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_am_football);
        } else if (selLeagueId.equals(getResources().getString(R.string.mls_lg_id))) {
            tvNoCards.setText("There are no MLS cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_soccer);
        } else if (selLeagueId.equals(getResources().getString(R.string.NCAAMB_lg_id))) {
            tvNoCards.setText("There are no NCAAMB cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_basketball);
        } else if (selLeagueId.equals(getResources().getString(R.string.pga_lg_id))) {
            tvNoCards.setText("There are no PGA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_golf);
        } else if (selLeagueId.equals(getResources().getString(R.string.nccfb_id))) {
            tvNoCards.setText("There are no NCAAFB cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_am_football);
        } else if (selLeagueId.equals(getResources().getString(R.string.wildcard_id))) {
            tvNoCards.setText("There are no WILD CARD cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_wildcard);
        } else {
            ivNoCards.setImageResource(R.drawable.tro_b);
            tvNoCards.setText("There are no cards right now.");

        }

    }


    private void HandleIndividualTabs(String dd) {

        recyclerViewItems.clear();
        myCardsData.clear();
        myCardsCount = 0;
        upcomingCardsData.clear();
        leagueData.clear();


        HandleMyCardsForIndividualTabs(dd);

        if (recycleAdapter != null) {
            recycleAdapter.notifyDataSetChanged();

        }
    }


    public void userprofdileupdate() {

        AndroidNetworking.get(APIs.MYINFO)
                .setPriority(Priority.HIGH)
//                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer " + Dashboard.NEWTOKEN)
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
                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, String.format("%.0f", signup_res.getDouble("totalCashBalance")));
//                            cv.put(DB_Constants.USER_TOTALCASHBALANCE, signup_res.getString("totalCashBalance"));
                            cv.put(DB_Constants.USER_CASHBALANCE, signup_res.getString("cashBalance"));
                            cv.put(DB_Constants.USER_PROMOBALANCE, signup_res.getString("promoBalance"));
                            cv.put(DB_Constants.USER_TOKENBALANCE, String.format("%.0f", signup_res.getDouble("tokenBalance")));
//                            cv.put(DB_Constants.USER_TOKENBALANCE, signup_res.getString("tokenBalance"));

                            cv.put(DB_Constants.USER_NUMBER, signup_res.getString("phoneNumber"));
                            cv.put(DB_Constants.USER_DOB, signup_res.getString("dob"));
                            cv.put(DB_Constants.USER_GENDER, signup_res.getString("gender"));
                            cv.put(DB_Constants.USER_SPORTSPRE, signup_res.getString("sportsPreference"));

                            myDbHelper.updateUser(cv);

                        } catch (JSONException e) {
                            Log.e("error---1731--", e.getMessage());
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

    public class RecyclerViewAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {//implements Filterable {
        // A menu item view type.
        private static final int MENU_ITEM_VIEW_TYPE = 0;

        // The banner ad view type.
        private static final int BANNER_AD_VIEW_TYPE = 1;

        // An Activity's Context.
        private final Context context;

        // The list of banner ads and menu items.
        private final List<Object> recyclerViewItems;
        private List<Object> recyclerViewItems_NewFliter;

        // FragmentActivity ;

        //handle header data
        String headerStr;
//        MyCardsPojo all_home_cards;
//        int ss;
//        String leagueName;

        RecyclerViewAdapterNew(List<Object> recyclerViewItems, Context context) {
            //    this.context = context;
            this.recyclerViewItems = recyclerViewItems;
            this.context = context;
            this.recyclerViewItems_NewFliter = recyclerViewItems;
//            setHasStableIds(true);
        }

        @Override
        public int getItemCount() {
            return recyclerViewItems.size();
        }

        /**
         * Determines the view type for the given position.
         */
        @Override
        public int getItemViewType(int position) {
            int viewType = -1;

            if (recyclerViewItems.get(position) instanceof MyCardsPojo) {
                viewType = 0;
            } else {
                viewType = 1;
            }
            return viewType;
        }

        /**
         * Creates a new view for a menu item view or a banner ad view
         * based on the viewType. This method is invoked by the layout manager.
         */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.fragment_mlb_new, viewGroup, false);
                    return new MenuItemViewHolder(menuItemLayoutView);
                case BANNER_AD_VIEW_TYPE:
                    // fall through
//                    View bannerLayoutView = LayoutInflater.from(
//                            viewGroup.getContext()).inflate(R.layout.header_layout,
//                            viewGroup, false);
//                    return new AdViewHolder(bannerLayoutView);
                default:
                    View bannerLayoutView = LayoutInflater.from(
                            viewGroup.getContext()).inflate(R.layout.header_layout,
                            viewGroup, false);
                    return new AdViewHolder(bannerLayoutView);
            }
        }

        /**
         * Replaces the content in the views that make up the menu item view and the
         * banner ad view. This method is invoked by the layout manager.
         */
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int ele_position) {

            int viewType = getItemViewType(ele_position);
            final int position = holder.getAdapterPosition();
//            holder.setIsRecyclable(false);


            if (recyclerViewItems.get(ele_position) instanceof String) {
                headerStr = (String) recyclerViewItems.get(ele_position);

            }

            Log.e("3243----k", headerStr + " postion");


//            if (recyclerViewItems.get(ele_position) instanceof MyCardsPojo) {
//
//
//
//            }


            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:

                    final MenuItemViewHolder listingView = (MenuItemViewHolder) holder;

                    final MyCardsPojo all_home_cards = (MyCardsPojo) recyclerViewItems.get(ele_position);
                    //      final int ss = all_home_cards.getPlayerCardIds().size();
                    final String leagueName = all_home_cards.getLeagueAbbrevation();

                    listingView.tvCardTitle.setText(all_home_cards.getCardTitle());


                    String cardType = all_home_cards.getMatchupType();
                    listingView.tvMatchUpType.setText(cardType);

                    if (cardType.equalsIgnoreCase("match-up")) {
                        //    listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_matchup, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.VISIBLE);
                        listingView.ivOverUnder.setVisibility(View.GONE);
                        listingView.tvPlus.setVisibility(View.GONE);
                    } else if (cardType.equalsIgnoreCase("mixed")) {
                        // listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ptt, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.VISIBLE);
                        listingView.ivOverUnder.setVisibility(View.VISIBLE);
                        listingView.tvPlus.setVisibility(View.VISIBLE);
                    } else if (cardType.equalsIgnoreCase("OVER-UNDER")) {
                        //listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_overunder, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.GONE);
                        listingView.ivOverUnder.setVisibility(View.VISIBLE);
                        listingView.tvPlus.setVisibility(View.GONE);
                    }

//                    if (all_home_cards.getIsLive().equalsIgnoreCase("true")) {
//                        listingView.tvStartTime.setVisibility(View.GONE);
//                        listingView.llLive.setVisibility(View.VISIBLE);
//                    } else {
//                        listingView.tvStartTime.setVisibility(View.VISIBLE);
//                        listingView.llLive.setVisibility(View.GONE);
//                    }

                    if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.nfl_lg_id))) {

                        listingView.head_img_icon.setImageResource(R.drawable.ic_am_football);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.nba_lg_id))) {

                        listingView.head_img_icon.setImageResource(R.drawable.ic_basketball);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.nhl_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_hockey);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.nascar_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_nascar_h);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.mlb_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_baseball);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.epl_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_tennis);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.pro_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.rodeo);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.LALIGA_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_am_football);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.mls_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_soccer);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.NCAAMB_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_basketball);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.pga_lg_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_golf);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.nccfb_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_am_football);
                    } else if (all_home_cards.getLeagueId().equals(getResources().getString(R.string.wildcard_id))) {
                        listingView.head_img_icon.setImageResource(R.drawable.ic_wildcard);
                    } else {
                        listingView.head_img_icon.setImageResource(R.drawable.rodeo);

                    }

                    final String leagueTime = all_home_cards.getStartTime();

                    if (leagueTime != null && !leagueTime.equals("")) {
                        String formatTimeDiff = getTimeDiff(leagueTime);
                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                            listingView.tvStartTime.setText(formatTimeDiff);
                            listingView.tvStartTime.setVisibility(View.VISIBLE);
                        } else {
                            listingView.tvStartTime.setVisibility(View.GONE);
                        }
                    } else {
                        listingView.tvStartTime.setVisibility(View.GONE);
                    }

                    Log.e("3333----------", leagueName);
                    listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.profile_placeholder));
                    listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.profile_placeholder));
//                    switch (leagueName) {
//                        case "NFL":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_football));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_football));
//
//                            break;
//
//                        case "NBA":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_basketball));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_basketball));
//
//                            break;
//
//                        case "NHL":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.hockey_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_hockey));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_hockey));
//
//                            break;
//
//                        case "NASCAR":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.car_race_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_nascar));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_nascar));
//
//                            break;
//
//                        case "MLB":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.base_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_baseball));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_baseball));
//
//                            break;
//
//                        case "EPL":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.tennis_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_tennis));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_tennis));
//                            break;
//                        case "PRO RODEO":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.gray));
////                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_tennis));
////                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_tennis));
//                            break;
//
//                        case "LA-LIGA":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_football));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_football));
//
//                            break;
//
//                        case "MLS":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.soccer_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_soccer));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_soccer));
//
//                            break;
//
//                        case "NCAAMB":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_basketball));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_basketball));
//
//                            break;
//
//                        case "NCAAFB":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_football));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_football));
//
//                            break;
//
//                        case "PGA":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_golf));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_golf));
//                            break;
//
//                        case "WILD CARD":
//                            listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color));
//                            listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_golf));
//                            listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_golf));
//                            break;
//
//                        default:
//                            break;
//                    }
//

                    //       Log.e("Kalyan--count", "header--" + headerStr + "  title  " + all_home_cards.getCardTitle() + "  " + ss);


                    if (headerStr.equalsIgnoreCase("My cards")) {
                        listingView.cardColor.setVisibility(View.VISIBLE);
                        listingView.stas_count.setVisibility(View.VISIBLE);
                        listingView.tvCardTitleCount.setVisibility(View.VISIBLE);

                        //      Log.e("Kalyan--count", "" + all_home_cards.getCardTitle() + " count: mycards " + ss);

/*

                        if (ss >= 2) {

                            Log.e("Kalyan--count", "" + all_home_cards.getCardTitle() + " count: >2  " + ss);
//                            all_home_cards.setCout_data(true);//
//            switch (myCardsList.getLeagueAbbrevation()) {


                            switch (leagueName) {
                                case "NFL":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
//                                    all_home_cards.setCout_data(true);
                                    break;

                                case "NBA":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
//                                    all_home_cards.setCout_data(true);
                                    break;

                                case "NHL":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.hockey_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
//                                    all_home_cards.setCout_data(true);
                                    break;

                                case "NASCAR":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.car_race_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "MLB":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.base_ball_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "EPL":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.tennis_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;
                                case "PRORODEO":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.tennis_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "LA-LIGA":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "MLS":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.soccer_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "NCAAMB":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "PGA":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.golf_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "WILD CARD":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.golf_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;

                                case "NCAAFB":
                                    listingView.tvCardTitleCount.setBackgroundColor(getResources().getColor(R.color.golf_color));
                                    listingView.tvCardTitleCount.setText(all_home_cards.getPlayerCardIds().size() + "");
                                    // all_home_cards.setCout_data(true);
                                    break;
                                default:

                                    break;
                            }


                        } else {

                            Log.e("Kalyan--count", "" + all_home_cards.getCardTitle() + " count: scroll " + ss);
                            listingView.tvCardTitleCount.setVisibility(View.INVISIBLE);
                            // all_home_cards.setCout_data(false);
                        }
*/


                    } else {
                        listingView.tvCardTitleCount.setVisibility(View.INVISIBLE);
                        // all_home_cards.setCout_data(false);
                    }


                    if (all_home_cards.getPlayerImageLeft() != null && !all_home_cards.getPlayerImageLeft().equals("")) {
                        Picasso.with(getActivity())
                                .load(all_home_cards.getPlayerImageLeft())
                                .into(new Target() {

                                    @Override
                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                                        listingView.player1Img.setImageBitmap(convertTParellelogram(bitmap, "xxx", getActivity()));
                                        listingView.player1Img.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                    }
                                });

                    }
                    if (all_home_cards.getPlayerImageRight() != null && !all_home_cards.getPlayerImageRight().equals("")) {
                        Picasso.with(getActivity())
                                .load(all_home_cards.getPlayerImageRight())
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                                        listingView.player2Img.setImageBitmap(convertTParellelogram(bitmap, "pare", getContext()));
                                        listingView.player2Img.setImageBitmap(bitmap);
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                    }
                                });

                    }


                    if (headerStr.equalsIgnoreCase("My cards")) {

                        //     Toast.makeText(context, headerStr + "--1894--", Toast.LENGTH_LONG).show();

                        listingView.llTotal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (all_home_cards.getPlayerCardIds().size() >= 2) {
                                    Log.e("size---", "--1904---l");
//                                Intent matchup = new Intent(context, MyCards_MoreScreen.class);
//                                    Intent matchup = new Intent(context, MoreCardsScreen.class);

                                    if (all_home_cards.getCardType().trim().equalsIgnoreCase("PLAY TAC TOE")) {
                                        Intent matchup_play = new Intent(context, HomeCards_MoreScreen.class);
                                        matchup_play.putExtra("morecards_info", all_home_cards);
                                        matchup_play.putExtra("cardid", all_home_cards.getCardId());
                                        matchup_play.putExtra("home", "1");
                                        matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
                                        matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                        matchup_play.putExtra("place", "home");
                                        matchup_play.putExtra("cardid_date", "");
                                        matchup_play.putExtra("playerid_m", "");
                                        context.startActivity(matchup_play);
                                    } else if (all_home_cards.getCardType().trim().equalsIgnoreCase("WIN PLAY SHOW")) {
//                                        // WIN PLAY SHOW New have to create
//                                        Intent matchup_play = new Intent(context, HomeCards_MoreScreen.class);
//                                        matchup_play.putExtra("morecards_info", all_home_cards);
//                                        matchup_play.putExtra("cardid", all_home_cards.getCardId());
//                                        matchup_play.putExtra("home", "1");
//                                        matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
//                                        matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
//                                        matchup_play.putExtra("place", "home");
//                                        matchup_play.putExtra("cardid_date", "");
//                                        matchup_play.putExtra("playerid_m", "");
//                                        context.startActivity(matchup_play);
                                    } else {
                                        Intent matchup = new Intent(context, HomeCards_MoreScreen.class);
                                        matchup.putExtra("morecards_info", all_home_cards);
                                        matchup.putExtra("cardid", all_home_cards.getCardId());
                                        matchup.putExtra("home", "1");
                                        matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                        matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                        matchup.putExtra("place", "home");
                                        matchup.putExtra("cardid_date", "");
                                        matchup.putExtra("playerid_m", "");
                                        context.startActivity(matchup);

                                    }


//
                                } else {


                                    if (all_home_cards.getCardType().equalsIgnoreCase("PLAY TAC TOE")) {

                                        Intent matchup_play = new Intent(context, PlayPickGo_MatchupScreen.class);
                                        matchup_play.putExtra("home", "1");
                                        matchup_play.putExtra("cardid", all_home_cards.getCardId());
                                        matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
                                        matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                        matchup_play.putExtra("place", "homecards");
                                        matchup_play.putExtra("position_data", true);

                                        matchup_play.putExtra("playerid_m", "");
                                        if (leagueTime != null && !leagueTime.equals("")) {
                                            String formatTimeDiff = getTimeDiff(leagueTime);
                                            if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                                listingView.tvStartTime.setText(formatTimeDiff);
                                                matchup_play.putExtra("cardid_date", formatTimeDiff);
                                            }
                                        } else {
                                            matchup_play.putExtra("cardid_date", "");

                                        }
                                        context.startActivity(matchup_play);
                                    } else if (all_home_cards.getCardType().equalsIgnoreCase("WIN PLAY SHOW")) {
//
//                                        Intent matchup_play = new Intent(context, Matchup_NewWinScreen.class);
//                                        matchup_play.putExtra("home", "1");
//                                        matchup_play.putExtra("cardid", all_home_cards.getCardId());
//                                        matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
//                                        matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
//                                        matchup_play.putExtra("place", "homecards");
//                                        matchup_play.putExtra("position_data", true);
//                                        matchup_play.putExtra("playerid_m", "");
//                                        if (leagueTime != null && !leagueTime.equals("")) {
//                                            String formatTimeDiff = getTimeDiff(leagueTime);
//                                            if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
//                                                listingView.tvStartTime.setText(formatTimeDiff);
//                                                matchup_play.putExtra("cardid_date", formatTimeDiff);
//                                            }
//                                        } else {
//                                            matchup_play.putExtra("cardid_date", "");
//
//                                        }
//                                        context.startActivity(matchup_play);
                                    } else if (all_home_cards.getCardType().equalsIgnoreCase("PLAY A PICK")) {
//
//
                                        Log.e("size---", "--1918---l");
                                        Intent matchup = new Intent(context, MatchupScreen_PlayAPick.class);
                                        matchup.putExtra("home", "1");
                                        matchup.putExtra("cardid", all_home_cards.getCardId());
                                        matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                        matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                        matchup.putExtra("place", "homecards");
                                        matchup.putExtra("position_data", false);
                                        matchup.putExtra("card_type", all_home_cards.getCardType());
                                        matchup.putExtra("playerid_m", "");
                                        if (leagueTime != null && !leagueTime.equals("")) {
                                            String formatTimeDiff = getTimeDiff(leagueTime);
                                            if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                                listingView.tvStartTime.setText(formatTimeDiff);
                                                matchup.putExtra("cardid_date", formatTimeDiff);
                                            }
                                        } else {
                                            matchup.putExtra("cardid_date", "");

                                        }

                                        context.startActivity(matchup);
                                    } else {
                                        Log.e("size---", "--1918---l");
                                        Intent matchup = new Intent(context, MatchupScreen.class);
                                        matchup.putExtra("home", "1");
                                        matchup.putExtra("cardid", all_home_cards.getCardId());
                                        matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                        matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                        matchup.putExtra("place", "homecards");
                                        matchup.putExtra("position_data", true);
                                        matchup.putExtra("card_type", all_home_cards.getCardType());
                                        matchup.putExtra("playerid_m", "");
                                        if (leagueTime != null && !leagueTime.equals("")) {
                                            String formatTimeDiff = getTimeDiff(leagueTime);
                                            if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                                listingView.tvStartTime.setText(formatTimeDiff);
                                                matchup.putExtra("cardid_date", formatTimeDiff);
                                            }
                                        } else {
                                            matchup.putExtra("cardid_date", "");

                                        }

                                        context.startActivity(matchup);
                                    }
                                }

                            }
                        });
                    } else {


                        // Home Screens
                        Log.e("4------------------", all_home_cards.getCardType());
                        listingView.llTotal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (all_home_cards.getCardType().equalsIgnoreCase("PLAY TAC TOE")) {

                                    Intent matchup_play = new Intent(context, PlayPickGo_MatchupScreen.class);
                                    matchup_play.putExtra("home", "1");
                                    matchup_play.putExtra("cardid", all_home_cards.getCardId());
                                    matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
                                    matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                    matchup_play.putExtra("place", "homecards");
                                    matchup_play.putExtra("position_data", false);
                                    matchup_play.putExtra("playerid_m", "");
                                    if (leagueTime != null && !leagueTime.equals("")) {
                                        String formatTimeDiff = getTimeDiff(leagueTime);
                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                            listingView.tvStartTime.setText(formatTimeDiff);
                                            matchup_play.putExtra("cardid_date", formatTimeDiff);
                                        }
                                    } else {
                                        matchup_play.putExtra("cardid_date", "");

                                    }
                                    context.startActivity(matchup_play);
                                } else if (all_home_cards.getCardType().equalsIgnoreCase("WIN PLAY SHOW")) {

//                                    Intent matchup_play = new Intent(context, Matchup_NewWinScreen.class);
//                                    matchup_play.putExtra("home", "1");
//                                    matchup_play.putExtra("cardid", all_home_cards.getCardId());
//                                    matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
//                                    matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
//                                    matchup_play.putExtra("place", "homecards");
//                                    matchup_play.putExtra("position_data", false);
//                                    matchup_play.putExtra("playerid_m", "");
//                                    if (leagueTime != null && !leagueTime.equals("")) {
//                                        String formatTimeDiff = getTimeDiff(leagueTime);
//                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
//                                            listingView.tvStartTime.setText(formatTimeDiff);
//                                            matchup_play.putExtra("cardid_date", formatTimeDiff);
//                                        }
//                                    } else {
//                                        matchup_play.putExtra("cardid_date", "");
//
//                                    }
//                                    context.startActivity(matchup_play);
                                } else if (all_home_cards.getCardType().equalsIgnoreCase("WIN PLAY SQOR")) {

                                    Intent matchup_play = new Intent(context, Matchup_WinPlayGoTimeTwo.class);
                                    matchup_play.putExtra("home", "1");
                                    matchup_play.putExtra("cardid", all_home_cards.getCardId());
                                    matchup_play.putExtra("cardid_title", all_home_cards.getCardTitle());
                                    matchup_play.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                    matchup_play.putExtra("place", "homecards");
                                    matchup_play.putExtra("position_data", false);
                                    matchup_play.putExtra("playerid_m", "");
                                    matchup_play.putExtra("card_type", all_home_cards.getCardType());
                                    if (leagueTime != null && !leagueTime.equals("")) {
                                        String formatTimeDiff = getTimeDiff(leagueTime);
                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                            listingView.tvStartTime.setText(formatTimeDiff);
                                            matchup_play.putExtra("cardid_date", formatTimeDiff);
                                        }
                                    } else {
                                        matchup_play.putExtra("cardid_date", "");

                                    }
                                    context.startActivity(matchup_play);


                                } else if (all_home_cards.getCardType().equalsIgnoreCase("PLAY A PICK")) {
//
//
                                    Log.e("size---", "--1918---l");
                                    Intent matchup = new Intent(context, MatchupScreen_PlayAPick.class);
                                    matchup.putExtra("home", "1");
                                    matchup.putExtra("cardid", all_home_cards.getCardId());
                                    matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                    matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                    matchup.putExtra("place", "homecards");
                                    matchup.putExtra("position_data", false);
                                    matchup.putExtra("card_type", all_home_cards.getCardType());
                                    matchup.putExtra("playerid_m", "");
                                    if (leagueTime != null && !leagueTime.equals("")) {
                                        String formatTimeDiff = getTimeDiff(leagueTime);
                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                            listingView.tvStartTime.setText(formatTimeDiff);
                                            matchup.putExtra("cardid_date", formatTimeDiff);
                                        }
                                    } else {
                                        matchup.putExtra("cardid_date", "");

                                    }


                                } else if (all_home_cards.getCardType().equalsIgnoreCase("WIN PLAY GO")) {
//
//
                                    Log.e("size---", "--1918---l"+all_home_cards.getCardType());
                                    Intent matchup = new Intent(context, Matchup_WinPlayGoTimeTwo.class);
                                    matchup.putExtra("home", "1");
                                    matchup.putExtra("cardid", all_home_cards.getCardId());
                                    matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                    matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                    matchup.putExtra("place", "homecards");
                                    matchup.putExtra("position_data", false);
                                    matchup.putExtra("card_type", all_home_cards.getCardType());
                                    matchup.putExtra("playerid_m", "");
                                    if (leagueTime != null && !leagueTime.equals("")) {
                                        String formatTimeDiff = getTimeDiff(leagueTime);
                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                            listingView.tvStartTime.setText(formatTimeDiff);
                                            matchup.putExtra("cardid_date", formatTimeDiff);
                                        }
                                    } else {
                                        matchup.putExtra("cardid_date", "");

                                    }

                                    context.startActivity(matchup);
                                } else {
                                    Log.e("size---", "--1941---l");
                                    Intent matchup = new Intent(context, MatchupScreen.class);
                                    matchup.putExtra("home", "1");
                                    matchup.putExtra("cardid", all_home_cards.getCardId());
                                    matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                    matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                    matchup.putExtra("place", "homecards");
                                    matchup.putExtra("position_data", false);
                                    matchup.putExtra("card_type", all_home_cards.getCardType());
                                    matchup.putExtra("playerid_m", "");
                                    if (leagueTime != null && !leagueTime.equals("")) {
                                        String formatTimeDiff = getTimeDiff(leagueTime);
                                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                                            listingView.tvStartTime.setText(formatTimeDiff);
                                            matchup.putExtra("cardid_date", formatTimeDiff);
                                        }
                                    } else {
                                        matchup.putExtra("cardid_date", "");

                                    }

                                    context.startActivity(matchup);
                                }


                            }
                        });
                    }


                    break;
                case BANNER_AD_VIEW_TYPE:
                    // fall through
                    final AdViewHolder adViewHolder = (AdViewHolder) holder;

                    //handle header data
//                    headerStr = "Header";


                    Log.e("3243----kk", headerStr);
                    Log.e("3243----kk", proRoedoData.size() + "==" + prorodeoCount);
                    adViewHolder.tvHeader.setText(headerStr);

                    //handle view all
                    boolean showViewAll = false;
                    adViewHolder.viewSeparator.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                    adViewHolder.viewSeparator.setVisibility(View.VISIBLE);
                    //    adViewHolder.rlHeader.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.white,null));
                    switch (headerStr) {
//                        case "My cards":
//                            if (myCardsCount > MY_CARDS_MAX)
//                                showViewAll = true;
//                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                            adViewHolder.viewSeparator.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.extra_light_gray, null));
//                            //       adViewHolder.rlHeader.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.extra_light_gray,null));
//                            adViewHolder.viewSeparator.setVisibility(View.GONE);
//                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
//
//                            break;
                        case "NFL":
                            if (NFLCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0, 0);

                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            adViewHolder.tvHeader_Play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    headerPlayUrlNFL("https://www.youtube.com/watch?v=NNZjpoT4z2Q");
                                    headerPlayUrlNHL("https://www.youtube.com/watch?v=NNZjpoT4z2Q", "NNZjpoT4z2Q");

                                }
                            });
                            break;

                        case "NBA":
                            if (NBACount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            adViewHolder.tvHeader_Play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    headerPlayUrl("https://www.youtube.com/watch?v=iwxAvA6vS90");
                                    headerPlayUrlNHL("https://www.youtube.com/watch?v=EL-jpyKUH1o", "EL-jpyKUH1o");

                                }
                            });
                            break;

                        case "NHL":
                            if (NHLCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hockey, 0, 0, 0);

                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            adViewHolder.tvHeader_Play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    headerPlayUrlNHL("https://www.youtube.com/watch?v=y5yWwFbGfF0", "y5yWwFbGfF0");
                                }
                            });
                            break;
                        case "NASCAR":
                            if (NASCARCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nascar_h, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "MLB":
                            if (MLBCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseball, 0, 0, 0);

                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            adViewHolder.tvHeader_Play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    headerPlayUrl("https://www.youtube.com/watch?v=iwxAvA6vS90");
                                    headerPlayUrlNHL("https://www.youtube.com/watch?v=iwxAvA6vS90", "iwxAvA6vS90");

                                }
                            });
                            break;

                        case "EPL":
                            if (EPLCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tennis, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "PRORODEO":
                            adViewHolder.rlHeader.setVisibility(View.GONE);
//                            rlHeader
                            if (prorodeoCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tennis, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
//                        case "PRO RODEO":
//                            if (prorodeoCount > CARDS_MAX)
//                                showViewAll = true;
//                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tennis, 0, 0, 0);
//                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
//                            break;

                        case "LA-LIGA":
                            if (LALIGACount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "MLS":
                            if (MLSCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "NCAAMB":
                            if (NCAAMBCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "NCAAFB":
                            if (NCAAFBCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "WILDCARD Horse Racing":
                            if (WILDCount_horse > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "WILDCARD Golf Future":

                            Log.e("wwwwwwwwwwwww", "  G:  " + WILDCount_golf + "   " + CARDS_MAX);
                            if (WILDCount_golf > CARDS_MAX) {
                                showViewAll = true;
                            }
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "WILDCARD Football":
                            if (WILDCount_football > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "WILDCARD NFL Draft":
                            if (WILDCount_NFL > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "WILDCARD Baseball Future":
                            if (WILDCount_base > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "WILD CARD":

                            //  adViewHolder.tvHeader.setText("klayan");
/*
                            if (WILDCount_golf > CARDS_MAX) {
                                showViewAll = true;
                                adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                                adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            } else if (WILDCount_base > CARDS_MAX) {
                                showViewAll = true;
                                adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                                adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            } else*/
                            if (WILDCount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;

                        case "PGA":
                            if (PGACount > CARDS_MAX)
                                showViewAll = true;
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_golf, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                        case "Upcoming cards":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            break;
                        default:
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wildcard, 0, 0, 0);
                            adViewHolder.tvHeader_Play.setVisibility(View.GONE);
                            break;
                    }


                    final String finalHeaderStr = headerStr;
                    String leagueId = "";

                    for (Map.Entry<String, String> entry : leagueData.entrySet()) {
                        if (entry.getKey().equals(finalHeaderStr)) {
                            leagueId = entry.getValue();
                        }
                    }
                    if (showViewAll) {
                        if (selLeagueId != null && !selLeagueId.equalsIgnoreCase("")) {
                            adViewHolder.tvViewALL.setVisibility(View.GONE);
                        } else {
                            adViewHolder.tvViewALL.setVisibility(View.VISIBLE);
                        }
                    } else {
                        adViewHolder.tvViewALL.setVisibility(View.GONE);
                    }

                    final String finalLeagueId = leagueId;
                    adViewHolder.tvViewALL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (finalHeaderStr.equalsIgnoreCase("My cards")) {
//                                try {
//                                    Activity act = getActivity();
//                                    if (act instanceof Dashboard)
//                                        ((Dashboard) act).navToMyCards();
//                                } catch (Exception e) {
//                                    e.getStackTrace();
//                                }
                            } else {

                                //To scroll the view programmatically
                                if (horizontal != null) {
                                    // Obtain MotionEvent object
                                    long downTime = SystemClock.uptimeMillis();
                                    long eventTime = SystemClock.uptimeMillis() + 100;
                                    MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, 0.0f, 0.0f, 0);
                                    switch (finalHeaderStr) {

                                        case "NFL":
                                            horizontal.smoothScrollBy((NFL_pvb.getLeft() - 500), 0);
                                            NFL_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "NBA":
                                            horizontal.smoothScrollBy((NBA_pvb.getLeft() - 500), 0);
                                            NBA_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "NHL":
                                            horizontal.smoothScrollBy((NHL_pvb.getLeft() - 500), 0);
                                            NHL_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "NASCAR":
                                            horizontal.smoothScrollBy((NASCAR_pvb.getLeft() - 500), 0);
                                            NASCAR_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "MLB":
                                            horizontal.smoothScrollBy((mlb_pvb.getLeft() - 500), 0);
                                            mlb_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "EPL":
                                            horizontal.smoothScrollBy((EPL_pvb.getLeft() - 500), 0);
                                            EPL_pvb.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "PRORODEO":
                                            horizontal.smoothScrollBy((pro_pvd.getLeft() - 500), 0);
                                            pro_pvd.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "LA-LIGA":
                                            horizontal.smoothScrollBy((LA_LIGA_pvb.getLeft() - 500), 0);
                                            LA_LIGA_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "MLS":
                                            horizontal.smoothScrollBy((mls_pvb.getLeft() - 500), 0);
                                            mls_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "NCAAMB":
                                            horizontal.smoothScrollBy((NCAAMB_pvb.getLeft() - 500), 0);
                                            NCAAMB_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "NCAAFB":
                                            horizontal.smoothScrollBy((NCAAFB_pvb.getLeft() - 500), 0);
                                            NCAAFB_pvb.dispatchTouchEvent(motionEvent);
                                            break;
//                                        case "WILD CARD":
//                                            horizontal.smoothScrollBy((WILD_pvb.getLeft() - 500), 0);
//                                            WILD_pvb.dispatchTouchEvent(motionEvent);
//                                            break;
                                        case "PGA":
                                            horizontal.smoothScrollBy((PGA_pvb.getLeft() - 500), 0);
                                            PGA_pvb.dispatchTouchEvent(motionEvent);
                                            break;

                                        case "WILDCARD Horse Racing":
                                            horizontal.smoothScrollBy((WILD_pvb_h.getLeft() - 500), 0);
                                            WILD_pvb_h.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "WILDCARD Golf Future":
                                            horizontal.smoothScrollBy((WILD_pvb_g.getLeft() - 500), 0);
                                            WILD_pvb_g.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "WILDCARD Baseball Future":
                                            horizontal.smoothScrollBy((WILD_pvb_b.getLeft() - 500), 0);
                                            WILD_pvb_b.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "WILDCARD NFL Draft":
                                            horizontal.smoothScrollBy((WILD_pvb_n.getLeft() - 500), 0);
                                            WILD_pvb_n.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "WILD CARD":
                                            horizontal.smoothScrollBy((WILD_pvb.getLeft() - 500), 0);
                                            WILD_pvb.dispatchTouchEvent(motionEvent);
                                            break;
                                        case "WILDCARD Football":
                                            horizontal.smoothScrollBy((WILD_pvb_f.getLeft() - 500), 0);
                                            WILD_pvb_f.dispatchTouchEvent(motionEvent);

                                            break;
                                        default:
                                            break;
                                    }

                                }
                                selLeagueId = finalLeagueId;
                                selLeagueName = finalHeaderStr;
                                fromRefresh = false;

                                if (bb1.getTag().equals("11")) {
                                    HandleIndividualTabs("MATCH-UP");
                                } else {

                                    HandleIndividualTabs("PLAY TAC TOE");
                                }
                            }
                        }
                    });
                default:
                    break;
            }


        }

        class MenuItemViewHolder extends RecyclerView.ViewHolder {

            private ImageView player1Img, player2Img, ivMatchUp, ivOverUnder, head_img_icon;
            LinearLayout llTotal, llLive, stas_count;
            private TextView tvCardTitle, tvMatchUpType, tvStartTime, tvPlus, tvPIds, tvCardTitleCount;
            private View cardColor;

//            private TextView tvHeader, tvViewALL, tvHeader_Play;
//            private View viewSeparator;
//            private RelativeLayout rlHeader;

            MenuItemViewHolder(View convertView) {
                super(convertView);

                tvCardTitle = convertView.findViewById(R.id.tvCardTitle);
                player1Img = convertView.findViewById(R.id.player1Img);
                player2Img = convertView.findViewById(R.id.player2Img);
                head_img_icon = convertView.findViewById(R.id.head_img_icon);
                llTotal = convertView.findViewById(R.id.llTotal);
                tvMatchUpType = convertView.findViewById(R.id.tvMatchUpType);
                tvStartTime = convertView.findViewById(R.id.tvStartTime);
                cardColor = convertView.findViewById(R.id.cardColor);
                llLive = convertView.findViewById(R.id.llLive);
                ivOverUnder = convertView.findViewById(R.id.ivOverUnder);
                ivMatchUp = convertView.findViewById(R.id.ivMatchUp);
                tvPlus = convertView.findViewById(R.id.tvPlus);
                tvPIds = convertView.findViewById(R.id.tvCardids);

                stas_count = convertView.findViewById(R.id.stas_count);
                tvCardTitleCount = convertView.findViewById(R.id.tvCardTitleCount);
                stas_count.setVisibility(View.INVISIBLE);
                tvCardTitleCount.setVisibility(View.INVISIBLE);

//                tvHeader = convertView.findViewById(R.id.tvHeader);
//                tvHeader_Play = convertView.findViewById(R.id.tvHeader_Play);
//                tvViewALL = convertView.findViewById(R.id.tvViewALL);
//                viewSeparator = convertView.findViewById(R.id.viewSeparator);
//                rlHeader = convertView.findViewById(R.id.rlHeader);

            }
        }

        class AdViewHolder extends RecyclerView.ViewHolder {
            private TextView tvHeader, tvViewALL, tvHeader_Play;
            private View viewSeparator;
            private RelativeLayout rlHeader;

            AdViewHolder(View adview) {
                super(adview);
                tvHeader = adview.findViewById(R.id.tvHeader);
                tvHeader_Play = adview.findViewById(R.id.tvHeader_Play);
                tvViewALL = adview.findViewById(R.id.tvViewALL);
                viewSeparator = adview.findViewById(R.id.viewSeparator);
                rlHeader = adview.findViewById(R.id.rlHeader);
            }
        }

        private void headerPlayUrl(String youtubeurl) {
            ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_promo_redeem1, viewGroup, false);

            Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

            Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
            ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);

            // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();

            alertDialog.setCancelable(false);
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
            alertDialog.show();


            btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                    alertDialog.dismiss();
                    Activity act = getActivity();
                    if (act instanceof Dashboard)
                        ((Dashboard) act).setPromo();
                }
            });

            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    startActivity(intent);
                }
            });


            youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
            youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
            youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
            youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            youTubePlayerView.getPlayerUiController().showVideoTitle(false);
            youTubePlayerView.getPlayerUiController().showMenuButton(false);
            getLifecycle().addObserver(youTubePlayerView);

            playVideo("", youtubeurl, "dd", "dd", true);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    alertDialog.dismiss();
                    youTubePlayerView.release();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


                }
            });


        }

        private void headerPlayUrlNFL(String youtubeurl) {
            ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_promo_redeem1, viewGroup, false);

            Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

            Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
            ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);

            // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();

            alertDialog.setCancelable(false);
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
            alertDialog.show();


            btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                    alertDialog.dismiss();
                    Activity act = getActivity();
                    if (act instanceof Dashboard)
                        ((Dashboard) act).setPromo();
                }
            });

            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    startActivity(intent);
                }
            });

            youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
            youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
            youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
            youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            youTubePlayerView.getPlayerUiController().showVideoTitle(false);
            youTubePlayerView.getPlayerUiController().showMenuButton(false);
            getLifecycle().addObserver(youTubePlayerView);

            playVideo("", youtubeurl, "dd", "dd", true);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    alertDialog.dismiss();
                    youTubePlayerView.release();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


                }
            });


        }

        private void headerPlayUrlNHL(String surl, final String scode) {


            final Dialog dialogView = new Dialog(context);
            dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogView.setContentView(R.layout.dialog_promo_redeem1);
            dialogView.setCancelable(false);

            Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

            Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
            ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);
            ToggleButton fullscreen_button_lol = dialogView.findViewById(R.id.fullscreen_button_lol);


            dialogView.show();

            Window window = dialogView.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            dialogView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogView.dismiss();
                    if (dialogView.isShowing()) {
                        dialogView.dismiss();
                    }



               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                    dialogView.dismiss();


                }
            });
            fullscreen_button_lol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TvFullScreenVideo.class);
                    intent.putExtra("video_id", scode);
                    intent.putExtra("video_time", "3.00");
                    context.startActivity(intent);
                }
            });
            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, PlayWithCash.class);
                    intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                    if (dialogView.isShowing()) {
                        dialogView.dismiss();
                    }

                    timer = new Timer();
                    dialogView.dismiss();
                    intent.putExtra("referredby", "");
                    context.startActivity(intent);
                }
            });
            youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
            youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
            youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
            youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            youTubePlayerView.getPlayerUiController().showVideoTitle(false);
            youTubePlayerView.getPlayerUiController().showMenuButton(false);

//            context.addObserver(youTubePlayerView);
            playVideo("", surl, "dd", "dd", true);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialogView.isShowing()) {
                        dialogView.dismiss();
                    }

                    dialogView.dismiss();
                    youTubePlayerView.release();
//                youTubePlayerView.clearAnimation();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


                }
            });


        }

        /*private void headerPlayUrlNHL(String youtubeurl) {
            ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_promo_redeem1, viewGroup, false);

            Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

            Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
            ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);

            // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));


            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();

            alertDialog.setCancelable(false);
            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            //alertDialog.setBackgroundDrawable(Color.TRANSPARENT);
            alertDialog.show();


            btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
               *//* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*//*
                    alertDialog.dismiss();
                    Activity act = getActivity();
                    if (act instanceof Dashboard)
                        ((Dashboard) act).setPromo();
                }
            });

            btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                    startActivity(intent);
                }
            });

            youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
            youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
            youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
            youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            youTubePlayerView.getPlayerUiController().showVideoTitle(false);
            youTubePlayerView.getPlayerUiController().showMenuButton(false);
            getLifecycle().addObserver(youTubePlayerView);

            playVideo("", youtubeurl, "dd", "dd", true);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }

                    alertDialog.dismiss();
                    youTubePlayerView.release();
              *//*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*//*


                }
            });


        }*/



        /*@Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        recyclerViewItems_NewFliter = recyclerViewItems;

//                        MyCardsPojo all_home_cards = (MyCardsPojo) recyclerViewItems.get(position);            if (recyclerViewItems.get(position) instanceof MyCardsPojo) {
                    } else {
                        List<Object> filteredList = new ArrayList<>();
                        if (recyclerViewItems.size() > 0) {


                            for (int i = 0; i < recyclerViewItems.size(); i++) {

                                for (Object row12 : recyclerViewItems) {


                                    // name match condition. this might differ depending on your requirement
                                    // here we are looking for name or phone number match
                                    if (all_home_rows.getCardType().contains(charString.toLowerCase()) ) {
                                        filteredList.add(row12);
                                    }
                                }


                            }

                        }

                        recyclerViewItems_NewFliter = filteredList;

                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = recyclerViewItems_NewFliter;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    recyclerViewItems_NewFliter = (ArrayList<Object>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }*/
    }


    private String getTimeDiff(String timeFromServer) {
        String formattedTime = "";
        String[] timeArray = timeFromServer.split("T");
        String serverDate = "";
        String sererTime = "";

        if (timeArray.length > 0) {
            serverDate = timeArray[0];
            sererTime = timeArray[1].replace("Z", "");
            timeFromServer = serverDate + " " + sererTime;
        }
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String datenow = dateFormat.format(currentDate);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = spf.parse(datenow);
            date2 = spf.parse(timeFromServer);
            if (date2 != null && date1 != null)
                formattedTime = printDifference(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedTime;
    }

    private String printDifference(Date startDate, Date endDate) {

        String diffDate = "";
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        if (elapsedDays > 0 && elapsedHours > 0 && elapsedMinutes > 0) {
            diffDate = elapsedDays + "d " + elapsedHours + "h";
        } else if (elapsedHours > 0 && elapsedMinutes > 0) {
            diffDate = elapsedHours + "h " + elapsedMinutes + "m";
        } else if (elapsedMinutes > 0) {
            diffDate = elapsedMinutes + "m";
        }

        return diffDate;
    }

    private void resetData() {
        myCardsCount = 0;
        NFLCount = 0;
        NBACount = 0;
        NHLCount = 0;
        NASCARCount = 0;
        MLBCount = 0;
        EPLCount = 0;
        prorodeoCount = 0;
        LALIGACount = 0;
        MLSCount = 0;
        NCAAMBCount = 0;
        NCAAFBCount = 0;
        WILDCount = 0;
        WILDCount_golf = 0;
        WILDCount_base = 0;
        WILDCount_horse = 0;
        WILDCount_NFL = 0;
        WILDCount_football = 0;
        WILDCount_gg = 0;
        PGACount = 0;

        cardTypes.clear();
        leagueData.clear();
        myCardsData.clear();
        EPLData.clear();
        proRoedoData.clear();
        LALIGAData.clear();
        NFLData.clear();
        NBAData.clear();
        NHLData.clear();
        NASCARData.clear();
        MLBData.clear();
        MLSData.clear();
        NCAAMBData.clear();
        NCAAFBData.clear();
        PGAData.clear();
        WILDData.clear();
        WILDData_Golf.clear();
        WILDData_Base.clear();
        WILDData_Horse.clear();
        WILDData_NFL.clear();
        WILDData_football.clear();
        WILDData_gg.clear();

    }
}