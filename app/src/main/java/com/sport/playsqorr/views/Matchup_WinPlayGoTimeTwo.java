package com.sport.playsqorr.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sport.playsqorr.R;
import com.sport.playsqorr.SensorService;
import com.sport.playsqorr.adapters.PicksAdapter;
import com.sport.playsqorr.adapters.WinPlayGoTwoNewPlayerListAdapter;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.NewPlayerStatistics;
import com.sport.playsqorr.model.Picks;
import com.sport.playsqorr.model.StatsPlayerStatistics;
import com.sport.playsqorr.pojos.MatchupModel;
import com.sport.playsqorr.pojos.PlayRequestModel;
import com.sport.playsqorr.pojos.PlayerA;
import com.sport.playsqorr.pojos.PlayerB;
import com.sport.playsqorr.pojos.PlayerC;
import com.sport.playsqorr.pojos.Selection;
import com.sport.playsqorr.services.CounterService;
import com.sport.playsqorr.services.ResponseReceiver;
import com.sport.playsqorr.ui.AppConstants;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.LocationTrack;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.sport.playsqorr.utilities.LocationTrack.getLocationFu;
import static com.sport.playsqorr.utilities.LocationTrack.llat;
import static com.sport.playsqorr.utilities.LocationTrack.llong;
import static com.sport.playsqorr.utilities.Utilities.checkLocationPermission;



public class Matchup_WinPlayGoTimeTwo extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    //    BottomSheetBehavior sheetBehavior;
    public RecyclerView playerGridView;
    LinearLayout dec_pp_ll;
    TextView desc_card,rlues_text;
    PlayerB playerB;
    public static PlayerB playerB1;
    PlayerC playerC;
    public static String home, isLive;
    String playerstatus, Playerstatus_C;
    public static String playerstatus1;
    PlayerA playerA;
    public static PlayerA playerA1;
    public static List<NewPlayerStatistics> mNewPlayerStatisticsList = new ArrayList<>();
    TextView no_of_picks_count, multiplier_count, winpayout, dollarchangetxt;
    RelativeLayout playBtn;
    Button playBtn1;
    LinearLayout layoutBottomSheet;
    List<Map<String, Object>> playerA_Stats = new ArrayList<>();
    List<Map<String, Object>> playerB_Stats = new ArrayList<>();
    String getcardID, cardTitle, Legue_id;
    public static String getcardID1;
    public static String player_id_m1;
    Window window;
    public static int counter = 0;
    ProgressBar progressBar;
    RelativeLayout match_header;
    PlayRequestModel playRequestModel;
    HashMap<String, String> matchup_selections = new HashMap<>();
    PicksAdapter picksAdapter;
    private String wagerName;

    private TextView tvAddFunds, tvAddFundsForCash;
    private LinearLayout llAddFunds;
    LinearLayout win_loss_ll, loss_ll, win_ll;
    TextView win_c, picks_c, payout_c;

    Cursor cursor;
    public static DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;

    public static TextView mim_4;

    public TextView playpick_info;
    public static HashMap<String, String> matchup_selections_new;

    HashMap<String, String> matchup_selections_new_cash;
    HashMap<String, String> matchup_selections_new_token;
    HashMap<String, String> matchup_selections_new_guest;


    LinearLayout m_token, m_cash;
    TextView amount_cash, amount_token, amt_txt, tvFaq;
    EditText amt_edt;
    String home_s;
    static String player_id_m;
    Button backBtn;
    TextView click_promo;
    public static MatchupModel matchupModel;
    ImageView c_btn;

    private TextView twox, fivex, tenx, fivteenx, twentyx;
    private LinearLayout ll_2, ll_5, ll_10, ll_15, ll_20;
    private ImageView iv_2, iv_5, iv_10, iv_15, iv_20;
    RecyclerView content_rv;
    List<Picks> picksList = new ArrayList<>();
    Intent mServiceIntent;
    private SensorService mSensorService;
    Context ctx;
    TextView toolbar_title_x;
    TextView player_Name_start_pos,player_Name_middle_pos,player_Name_last_pos;
    ImageView player_img_middle_pos,player_img_start_pos,player_img_last_pos;

    public Context getCtx() {
        return ctx;
    }


    public static Map<String, Object> toMap(JSONObject object) throws JSONException {

        Map<String, Object> map = new HashMap();
        Iterator keys = object.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    public static final String FILTER_ACTION_KEY = "any_key";

    public static String MATCHUPS_JSON = "";
    public static String MATCHUPS_CARDTITLE = "";
    public static String MATCHUPS_CARDAMOUNT = "";
    public static String MATCHUPS_CARDID = "";
    public static String MATCHUPS_CARD_LEGEND = "";
    public static String MATCHUPS_CARD_DATE = "";

    String db_sessionToken, NEWTOKEN, db_role, db_cash, db_token, DATA_DOB, DATA_STATE;
    public static String card_h;
    ProgressDialog progressDialog;
    public static List<StatsPlayerStatistics> stats_ps;
    private ResponseReceiver receiver;
    public static Context context;
    WinPlayGoTwoNewPlayerListAdapter playerListAdapter;
    public static WinPlayGoTwoNewPlayerListAdapter playerListAdapter1;

    public static String values;

    static boolean k_t;

    String p_amt;
    String Card_type_info;
    RadioButton sqorrup_txt, suresqorr_txt;

    public static boolean support_cash;
    public static boolean support_token;

    Spinner wager_amount_spinner;

    String[] textArray_amunt = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

    String selected_Amount;

    LinearLayout progess_data_ll, fixed_data_ll;


//    CardView  playerCard_middle,playerCard_start;
//    LinearLayout  playerCard_middle,playerCard_start;
    RelativeLayout  playerCard_middle,playerCard_start;

    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;

    boolean playerCard_start_resetLeftFlag = false;
    boolean playerCard_start_resetRightFlag = false;
    boolean playerCard_middle_resetLeftflag = false;
    boolean playerCard_middle_resetRightflag = false;
    int rightMove_flag ;
    int leftMove_flag;
    int dx;
    int dy;

    ArrayList a = new ArrayList();

    LinearLayout linearLayout4;


    TextView pr0_amunt,pr0_amunt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup__win_play_go_time_two);


        Log.e("Setteld--", "2");
        context = Matchup_WinPlayGoTimeTwo.this;
        stats_ps = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("cardid"))
                getcardID = bundle.getString("cardid");
            getcardID1 = bundle.getString("cardid");

            Log.d("getcardID", getcardID + "--" + cardTitle);
            if (bundle.containsKey("cardid_title"))
                cardTitle = bundle.getString("cardid_title");
            if (bundle.containsKey("cardid_color1"))
                Legue_id = bundle.getString("cardid_color1");
            MATCHUPS_CARD_LEGEND = Legue_id;
            if (bundle.containsKey("place"))
                home_s = bundle.getString("place");
            if (bundle.containsKey("cardid_date"))
                MATCHUPS_CARD_DATE = getString(R.string.game_start) + " " + bundle.getString("cardid_date");
            if (bundle.containsKey("playerid_m"))
                player_id_m = bundle.getString("playerid_m");
            player_id_m1 = bundle.getString("playerid_m");
            if (bundle.containsKey("position_data"))
                //   player_id_m = bundle.getString("playerid_m");
                k_t = bundle.getBoolean("position_data");
            if (bundle.containsKey("card_type"))
                //   player_id_m = bundle.getString("playerid_m");
                Card_type_info = bundle.getString("card_type");

            Log.e("C id----matchup", getcardID + "--" + cardTitle + "---b-" + k_t);
        }

        /******************Database Starts************************/
        mydb = new DataBaseHelper(getApplicationContext());
        sqLiteDatabase = mydb.getReadableDatabase();
        /******************Database Ends************************/


        progressDialog = new ProgressDialog(Matchup_WinPlayGoTimeTwo.this);
//        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");

        initDB();
        initViews();

        if (db_role == null || db_role.equalsIgnoreCase("cash")) {
            winpayout.setText("$0");
            winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            winpayout.setText("0");
            winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
        }
//           initApiCall();
        values = db_sessionToken;

        getApiCallWith(db_sessionToken);
        try {
            home = getIntent().getStringExtra("home");

        } catch (Exception e) {

        }
        mSensorService = new SensorService(Matchup_WinPlayGoTimeTwo.this);
        mServiceIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);


        }
        mSensorService.initializeTimerTask();
        IntentFilter filter1 = new IntentFilter(CounterService.ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(testReceiver, filter1);
        Log.i("in timer", "timer +  " + (SensorService.counter++));
//        Log.d("db_sessionToken",db_sessionToken);


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    Log.i("isMyServiceRunning?", true + "");
                    return true;
                }
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    public static void time() {
//        SensorService.timerTask = new TimerTask() {
//            public void run()
//            {
//                if (home.equalsIgnoreCase("2")) {
//                    initApiCall();
//                }
//                Log.i("in timer", "timer ++++  "+ (SensorService.counter++));
//
//            }
//        };
    }

    private void initDB() {

        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                db_sessionToken = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_SESSIONTOKEN));
                NEWTOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKEN));
                db_role = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                db_cash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                db_token = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
                DATA_DOB = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_DOB));
                DATA_STATE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_STATE)).trim();
            }
            cursor.close();
        } else {
            db_role = "0";
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();
        headerMatchup();
        wagerinfo();


        if (support_token == true && support_cash == false) {
            if (amt_edt.getText().toString().trim() != null) {
                amt_txt.setVisibility(View.GONE);
                amt_edt.setVisibility(View.GONE);
            } else {
                amt_txt.setVisibility(View.GONE);
                amt_edt.setVisibility(View.GONE);
            }
        } else {
            if (amt_edt.getText().toString().trim() != null) {
                amt_txt.setVisibility(View.GONE);
                amt_edt.setVisibility(View.VISIBLE);
            } else {
                amt_txt.setVisibility(View.VISIBLE);
                amt_edt.setVisibility(View.GONE);
            }
        }


   /*     sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//                    Toast.makeText(PlayPickGo_Matchup_WinPlayGoTimeTwo.this, "yyyyy", Toast.LENGTH_SHORT).show();
                    playBtn1.setVisibility(View.GONE);
                    backBtn.setVisibility(View.VISIBLE);
                } else if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    playBtn1.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
*/

        //================ Hide Virtual Key Board When  Clicking==================//

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//

        amt_edt.setMaxEms(2);
        amt_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String withdrawAmount = amt_edt.getText().toString();

//                if (withdrawAmount.indexOf("$") == -1 && charSequence.length() >= 1) {
//                    withdrawAmount = "$" + withdrawAmount;
//                    amt_edt.setText(withdrawAmount);
//
//                }
                if (withdrawAmount.contains("$")) {
                    String withdrawAmount1 = withdrawAmount.replace("$", "");

                    if (!withdrawAmount1.equals("")) {

                        picksList.clear();

                        for (int j = 0; j < jpicks.length(); j++) {

                            JSONObject jj = null;
                            try {
                                jj = jpicks.getJSONObject(j);
                                Log.e("1371----", jj + "");
                                Picks mPicks = new Picks();
                                mPicks.setPicks(jj.getString("picks"));
                                mPicks.setMultiplier("x" + jj.getString("multiplier"));

                                double a = Double.parseDouble(jj.getString("multiplier"));
                                double b = Double.parseDouble(selected_Amount);
                                double m = (a * b);
                                mPicks.setWinpayout(String.valueOf(m));
                                picksList.add(mPicks);

                                String mu = "x" + jj.getString("multiplier");
                                if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                                    if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                        winpayout.setText("$" + m);
                                        winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    } else {
                                        winpayout.setText("" + m);
                                        winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (picksAdapter != null) {
                            picksAdapter.notifyDataSetChanged();
                        }
                    }


                } else {
                    if (!withdrawAmount.equals("")) {

                        picksList.clear();

                        for (int j = 0; j < jpicks.length(); j++) {

                            JSONObject jj = null;
                            try {
                                jj = jpicks.getJSONObject(j);
                                Log.e("1371----", jj + "");
                                Picks mPicks = new Picks();
                                mPicks.setPicks(jj.getString("picks"));
                                mPicks.setMultiplier("x" + jj.getString("multiplier"));

                                double a = Double.parseDouble(jj.getString("multiplier"));
                                double b = Double.parseDouble(withdrawAmount);
                                double m = (a * b);
                                mPicks.setWinpayout(String.valueOf(m));
                                picksList.add(mPicks);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if (picksAdapter != null) {
                            picksAdapter.notifyDataSetChanged();
                        }
                    }
                }

                Log.e("1371----", picksList.size() + "");

            }

            @Override
            public void afterTextChanged(Editable s) {

//
//                }
//                if(s.toString().length()>0&&!s.toString().startsWith("$")){
//                    etAmount.setText("$");
//                    Selection.setSelection(etAmount.getText(), etAmount.getText().length());
//                }
//                if (amt_edt.getText().toString().trim().length() == 0) {
//                    amt_edt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.sep_view_color, null));
//                } else {
//                    amountView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.text_color_new, null));
//                }
            }
        });

        if (picksAdapter != null) {
            picksAdapter.notifyDataSetChanged();
        }


        // sqorrup_txt.setClickable(true);
        // suresqorr_txt.setClickable(true);


        fixed_data_ll.setVisibility(View.GONE);
        progess_data_ll.setVisibility(View.VISIBLE);

        sqorrup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqorrup_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                sqorrup_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));


                suresqorr_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray_light, null));
                suresqorr_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));

                fixed_data_ll.setVisibility(View.VISIBLE);
                progess_data_ll.setVisibility(View.GONE);
            }
        });

        suresqorr_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suresqorr_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                suresqorr_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));


                sqorrup_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray_light, null));
                sqorrup_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));

                fixed_data_ll.setVisibility(View.GONE);
                progess_data_ll.setVisibility(View.VISIBLE);
            }
        });

        //



        if (Card_type_info.equalsIgnoreCase("WIN PLAY GO")) {


            linearLayout4.setVisibility(View.VISIBLE);

            a.add(0);
            a.add(1);
            a.add(2);

        }else{


            linearLayout4.setVisibility(View.GONE);
            a.add(0);
            a.add(1);

        }

    }

    private void cashNOToken() {
        {
            Log.e("kalyan---", "test--0");
            twox.setText("$2");
            fivex.setText("$3");
            tenx.setText("$4");
            fivteenx.setText("$5");
            twentyx.setText("$20");
            iv_2.setVisibility(View.GONE);
            iv_5.setVisibility(View.GONE);
            iv_10.setVisibility(View.GONE);
            iv_15.setVisibility(View.GONE);
            iv_20.setVisibility(View.GONE);
            amt_txt.setVisibility(View.VISIBLE);
            amt_edt.setVisibility(View.GONE);
            tvAddFundsForCash.setVisibility(View.GONE);
            tvAddFunds.setVisibility(View.VISIBLE);
            if (db_role == null) {
                llAddFunds.setEnabled(false);
                llAddFunds.setBackgroundResource(R.drawable.btn_bg_grey);
                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
            } else {
                llAddFunds.setEnabled(true);
                llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            }

        }
        support_cash = true;
        support_token = false;
        CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());
        content_rv.setLayoutManager(custom);
        picksAdapter = new PicksAdapter(picksList, this, support_cash, support_token);
        content_rv.setAdapter(picksAdapter);
    }

    private void cashORtoken() {


        Log.e("kk---", "");

//        if (db_role!=null || !db_role.equalsIgnoreCase("tokens")) {
////        if (db_role.equalsIgnoreCase("cash")) {
//            showAlertBoxCT(Matchup_WinPlayGoTimeTwo.this, "this card will play with tokens only. your token balance:" + db_token, "");
//        }

//        if (db_role.equalsIgnoreCase("cash")) {
//            showAlertBoxCT(Matchup_WinPlayGoTimeTwo.this, "This card will play with tokens. your token balance: " + db_token, "");
//        }

        if (db_role == null) {
            Log.e("kalyan---", "test--1");
//            showAlertBoxCT(Matchup_WinPlayGoTimeTwo.this, "This card will play with tokens. your token balance: " + db_token, "");
            twox.setText("2");
            fivex.setText("3");
            tenx.setText("4");
            fivteenx.setText("5");
            twentyx.setText("20");
            iv_2.setVisibility(View.VISIBLE);
            iv_5.setVisibility(View.VISIBLE);
            iv_10.setVisibility(View.VISIBLE);
            iv_15.setVisibility(View.VISIBLE);
            iv_20.setVisibility(View.GONE);
            amt_txt.setVisibility(View.GONE);
            amt_edt.setVisibility(View.GONE);
            tvAddFundsForCash.setVisibility(View.GONE);
            tvAddFunds.setVisibility(View.GONE);
            llAddFunds.setVisibility(View.GONE);

            { //if tokens user

                m_token.setVisibility(View.VISIBLE);
                m_cash.setVisibility(View.GONE);

                if (db_token != null && !db_token.equalsIgnoreCase("null")) {
                    amount_token.setText(db_token);
                } else {
                    amount_token.setText("0");
                }
                amount_token.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
            }


            support_cash = false;
            support_token = true;

            CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());
            content_rv.setLayoutManager(custom);
            picksAdapter = new PicksAdapter(picksList, this, support_cash, support_token);
            content_rv.setAdapter(picksAdapter);
        } else if (support_token == true && support_cash == false) {
            Log.e("kalyan---", "test--2");
            showAlertBoxCT(Matchup_WinPlayGoTimeTwo.this, "This card will play with tokens. your token balance: " + db_token, "");
            twox.setText("2");
            fivex.setText("3");
            tenx.setText("4");
            fivteenx.setText("5");
            twentyx.setText("20");
            iv_2.setVisibility(View.VISIBLE);
            iv_5.setVisibility(View.VISIBLE);
            iv_10.setVisibility(View.VISIBLE);
            iv_15.setVisibility(View.VISIBLE);
            iv_20.setVisibility(View.GONE);
            amt_txt.setVisibility(View.GONE);
            amt_edt.setVisibility(View.GONE);
            tvAddFundsForCash.setVisibility(View.GONE);
            tvAddFunds.setVisibility(View.GONE);
            llAddFunds.setVisibility(View.GONE);

//            { //if tokens user
//
//                m_token.setVisibility(View.VISIBLE);
//                m_cash.setVisibility(View.GONE);
//
//                if (db_token != null && !db_token.equalsIgnoreCase("null")) {
//                    amount_token.setText(db_token);
//                } else {
//                    amount_token.setText("0");
//                }
//                amount_token.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//            }


            support_cash = false;
            support_token = true;

            CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());
            content_rv.setLayoutManager(custom);
            picksAdapter = new PicksAdapter(picksList, this, support_cash, support_token);
            content_rv.setAdapter(picksAdapter);
        }


//        dsknsdkf
    }

    public void showAlertBoxCT(Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.alerts_two);


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
        alert_ok.setText("PROCEED");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                Intent aa = new Intent()


            }
        });

        TextView alert_cancel = (TextView) dialog.findViewById(R.id.alert_cancel);
        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();


            }
        });


    }

    private void wagerinfo() {


        if (db_role == null || db_role.equalsIgnoreCase("cash")) {

            if (support_token == true && support_cash == false) {
                twox.setText("2");
                fivex.setText("3");
                tenx.setText("4");
                fivteenx.setText("5");
                twentyx.setText("20");
                iv_2.setVisibility(View.VISIBLE);
                iv_5.setVisibility(View.VISIBLE);
                iv_10.setVisibility(View.VISIBLE);
                iv_15.setVisibility(View.VISIBLE);
                iv_20.setVisibility(View.GONE);
                amt_txt.setVisibility(View.GONE);
                amt_edt.setVisibility(View.GONE);
                tvAddFundsForCash.setVisibility(View.GONE);
                tvAddFunds.setVisibility(View.GONE);
                llAddFunds.setVisibility(View.GONE);
//            { //if tokens user
//
//                m_token.setVisibility(View.VISIBLE);
//                m_cash.setVisibility(View.GONE);
//
//                if (db_token != null && !db_token.equalsIgnoreCase("null")) {
//                    amount_token.setText(db_token);
//                } else {
//                    amount_token.setText("0");
//                }
//                amount_token.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//            }


                support_cash = false;
                support_token = true;

                CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());
                content_rv.setLayoutManager(custom);
                picksAdapter = new PicksAdapter(picksList, this, support_cash, support_token);
                content_rv.setAdapter(picksAdapter);
            } else {
                twox.setText("$2");
                fivex.setText("$3");
                tenx.setText("$4");
                fivteenx.setText("$5");
                twentyx.setText("$20");
                iv_2.setVisibility(View.GONE);
                iv_5.setVisibility(View.GONE);
                iv_10.setVisibility(View.GONE);
                iv_15.setVisibility(View.GONE);
                iv_20.setVisibility(View.GONE);
                amt_txt.setVisibility(View.VISIBLE);
                amt_edt.setVisibility(View.GONE);
                tvAddFundsForCash.setVisibility(View.GONE);
                tvAddFunds.setVisibility(View.VISIBLE);
                if (db_role == null) {
                    llAddFunds.setEnabled(false);
                    llAddFunds.setBackgroundResource(R.drawable.btn_bg_grey);
                    tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
                } else {
                    llAddFunds.setEnabled(true);
                    llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
                    tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
                }
            }


        } else if (db_role.equalsIgnoreCase("tokens")) {

            twox.setText("2");
            fivex.setText("3");
            tenx.setText("4");
            fivteenx.setText("5");
            twentyx.setText("20");
            iv_2.setVisibility(View.VISIBLE);
            iv_5.setVisibility(View.VISIBLE);
            iv_10.setVisibility(View.VISIBLE);
            iv_15.setVisibility(View.VISIBLE);
            iv_20.setVisibility(View.GONE);
            amt_txt.setVisibility(View.GONE);
            amt_edt.setVisibility(View.GONE);
            tvAddFundsForCash.setVisibility(View.VISIBLE);
            tvAddFunds.setVisibility(View.GONE);
        }

    }

    private void headerMatchup() {
        if (db_role != null && db_role.equalsIgnoreCase("cash")) { // If cash user
            m_token.setVisibility(View.GONE);
            m_cash.setVisibility(View.VISIBLE);
            if (db_cash != null && !db_cash.equalsIgnoreCase("null")) {
                amount_cash.setText("$ " + db_cash);
            } else {
                amount_cash.setText("$ " + "0");
            }

        } else if (db_role != null && db_role.equalsIgnoreCase("tokens")) { //if tokens user

            m_token.setVisibility(View.VISIBLE);
            m_cash.setVisibility(View.GONE);

            if (db_token != null && !db_token.equalsIgnoreCase("null")) {
                amount_token.setText(db_token);
            } else {
                amount_token.setText("0");
            }
            amount_token.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
        } else { // if guest user
            m_token.setVisibility(View.GONE);
            m_cash.setVisibility(View.VISIBLE);
//            amount_token.setText("$ 0");
//            amount_cash.setText("$ " + "0");


            /*if (db_cash != null || !db_cash.equalsIgnoreCase("null")) {
                amount_cash.setText("$ " + db_cash);
            } else {
                amount_cash.setText("$ " + "0");
            }*/

        }


    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        progressBar = findViewById(R.id.indeterminateBar);

        progressBar.setVisibility(View.VISIBLE);
        ImageView arrow_up;
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        match_header = findViewById(R.id.match_header);

        m_token = findViewById(R.id.m_token);
        m_cash = findViewById(R.id.m_cash);
        pr0_amunt = findViewById(R.id.pr0_amunt);
        pr0_amunt1 = findViewById(R.id.pr0_amunt1);

        sqorrup_txt = findViewById(R.id.sqorrup_txt);
        suresqorr_txt = findViewById(R.id.suresqorr_txt);
        playpick_info = findViewById(R.id.playmat_info);

        progess_data_ll = findViewById(R.id.progess_data_ll);
        fixed_data_ll = findViewById(R.id.fixed_data_ll);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;

//        playerCard_start = findViewById(R.id.playerCard_start_go);
//        playerCard_middle = findViewById(R.id.playerCard_middle_go);
        playerCard_start = findViewById(R.id.rr_start);
        playerCard_middle = findViewById(R.id.rr_middle);


      //  playerCard_start = findViewById(R.id.playerCard_start);
        playerCard_start.setOnTouchListener(this);
      //  playerCard_middle=findViewById(R.id.playerCard_middle);
        playerCard_middle.setOnTouchListener(this);

        playerCard_middle.setOnDragListener(this);
        playerCard_start.setOnDragListener(this);
        sqorrup_txt.setClickable(true);
        suresqorr_txt.setClickable(true);

        sqorrup_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
        sqorrup_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));

        suresqorr_txt.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.gray_light, null));
        suresqorr_txt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));


        amount_token = findViewById(R.id.amount_token);
        amount_cash = findViewById(R.id.amount_cash);
        amount_cash.setOnClickListener(this);

        c_btn = findViewById(R.id.c_btn);
        c_btn.setOnClickListener(this);
        m_token.setOnClickListener(this);

        //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.init_status_bar_color));
        //   }

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.DragandDrop2);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.DragandDrop3);
        linearLayout4 = (LinearLayout) findViewById(R.id.DragandDrop4);


        linearLayout2.setOnTouchListener(touchListener);
        linearLayout3.setOnTouchListener(touchListener);
        linearLayout4.setOnTouchListener(touchListener);

        linearLayout2.setOnDragListener(draglistener);
        linearLayout3.setOnDragListener(draglistener);
        linearLayout4.setOnDragListener(draglistener);



    final String leagueName = Legue_id;

      /*  if (leagueName != null) {

            switch (leagueName) {
                case "NFL":
                case "LA-LIGA":
                    match_header.setBackgroundColor(getResources().getColor(R.color.foot_ball_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.foot_ball_color_org));
                    break;

                case "NBA":
                case "NCAAMB":
                    match_header.setBackgroundColor(getResources().getColor(R.color.basket_ball_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.basket_ball_color_org));
                    break;

                case "NHL":
                    match_header.setBackgroundColor(getResources().getColor(R.color.hockey_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.hockey_color_org));
                    break;

                case "NASCAR":
                    match_header.setBackgroundColor(getResources().getColor(R.color.car_race_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.car_race_color_org));
                    break;

                case "MLB":
                    match_header.setBackgroundColor(getResources().getColor(R.color.base_ball_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.base_ball_color_org));
                    break;

                case "EPL":
                    match_header.setBackgroundColor(getResources().getColor(R.color.tennis_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.tennis_color_org));
                    break;



                case "MLS":
                    match_header.setBackgroundColor(getResources().getColor(R.color.soccer_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.soccer_color_org));
                    break;



                case "PGA":
                    match_header.setBackgroundColor(getResources().getColor(R.color.golf_color_org));
                    window.setStatusBarColor(getResources().getColor(R.color.golf_color_org));
                    break;
                default:
                    break;
            }
        } else {
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
*/
        window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.extra_light_gray, null));
        layoutBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        amt_txt = findViewById(R.id.amt_txt);
        amt_edt = findViewById(R.id.amt_edt);

        tvAddFunds = findViewById(R.id.tvAddFunds);
        tvAddFundsForCash = findViewById(R.id.tvAddFundsForCash);
        llAddFunds = findViewById(R.id.llAddFunds);
        tvFaq = findViewById(R.id.tvFaq);
        wager_amount_spinner = findViewById(R.id.wager_amount_spinner_matchup);

        llAddFunds.setOnClickListener(this);
        tvFaq.setOnClickListener(this);

        twox = findViewById(R.id.twox);
        iv_2 = findViewById(R.id.iv_2);
        ll_2 = findViewById(R.id.ll_2);
        iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        fivex = findViewById(R.id.fivex);
        iv_5 = findViewById(R.id.iv_5);
        ll_5 = findViewById(R.id.ll_5);
        iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        tvAddFundsForCash.setVisibility(View.GONE);
        tvAddFunds.setVisibility(View.VISIBLE);
        tenx = findViewById(R.id.tenx);
        iv_10 = findViewById(R.id.iv_10);
        ll_10 = findViewById(R.id.ll_10);
        iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        fivteenx = findViewById(R.id.fivteenx);
        iv_15 = findViewById(R.id.iv_15);
        ll_15 = findViewById(R.id.ll_15);
        iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        twentyx = findViewById(R.id.twentyx);
        iv_20 = findViewById(R.id.iv_20);
        ll_20 = findViewById(R.id.ll_20);
        iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

       /* if (db_role == null || db_role.equalsIgnoreCase("cash")) {
            twox.setText("$2");
            fivex.setText("$5");
            tenx.setText("$10");
            fivteenx.setText("$15");
            twentyx.setText("$20");
            iv_2.setVisibility(View.GONE);
            iv_5.setVisibility(View.GONE);
            iv_10.setVisibility(View.GONE);
            iv_15.setVisibility(View.GONE);
            iv_20.setVisibility(View.GONE);
            amt_txt.setVisibility(View.VISIBLE);
            amt_edt.setVisibility(View.GONE);
            if (db_role == null) {
                llAddFunds.setEnabled(false);
                llAddFunds.setBackgroundResource(R.drawable.btn_bg_grey);
                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.gray, null));
            } else {
                llAddFunds.setEnabled(true);
                llAddFunds.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_green, null));
                tvAddFunds.setTextColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
            }

        } else if (db_role.equalsIgnoreCase("tokens")) {

            twox.setText("2");
            fivex.setText("5");
            tenx.setText("10");
            fivteenx.setText("15");
            twentyx.setText("20");
            iv_2.setVisibility(View.VISIBLE);
            iv_5.setVisibility(View.VISIBLE);
            iv_10.setVisibility(View.VISIBLE);
            iv_15.setVisibility(View.VISIBLE);
            iv_20.setVisibility(View.VISIBLE);
            amt_txt.setVisibility(View.GONE);
            amt_edt.setVisibility(View.GONE);
            tvAddFundsForCash.setVisibility(View.VISIBLE);
            tvAddFunds.setVisibility(View.GONE);
        }
*/

        amt_txt.setOnClickListener(this);
        no_of_picks_count = (TextView) findViewById(R.id.no_of_picks_count);
        multiplier_count = (TextView) findViewById(R.id.multiplier_count);
        winpayout = (TextView) findViewById(R.id.winpayout);
        //  dollarchangetxt = (TextView) findViewById(R.id.dollarchangetxt);
        playBtn = (RelativeLayout) findViewById(R.id.playBtn);
        playBtn1 = (Button) findViewById(R.id.playBtn1);
        mim_4 = (TextView) findViewById(R.id.mim_4);
        win_ll = findViewById(R.id.win_ll);
        loss_ll = findViewById(R.id.lost_ll);
        win_loss_ll = findViewById(R.id.win_loss_ll);

        win_c = findViewById(R.id.win_c);
        picks_c = findViewById(R.id.picks_c);
        payout_c = findViewById(R.id.payout_c);
        backBtn = findViewById(R.id.backBtn);

//        click_promo=findViewById(R.id.click_promo);
//
//
//        click_promo.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        playBtn1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        ll_10.setOnClickListener(this);
        ll_15.setOnClickListener(this);
        ll_20.setOnClickListener(this);
//        LinearLayout apply_promo_code = (LinearLayout) findViewById(R.id.apply_promo_code);
        content_rv = (RecyclerView) findViewById(R.id.content_rv);
        arrow_up = (ImageView) findViewById(R.id.arrow_up);
        playerGridView = (RecyclerView) findViewById(R.id.playerGridView);
        desc_card = findViewById(R.id.des);
        toolbar_title_x = (TextView) findViewById(R.id.toolbar_title_x);
        player_Name_start_pos = (TextView) findViewById(R.id.player_Name_start_pos);
        player_Name_middle_pos = (TextView) findViewById(R.id.player_Name_middle_pos);
        player_Name_last_pos = (TextView) findViewById(R.id.player_Name_last_pos);
        player_img_middle_pos =  findViewById(R.id.player_img_middle_pos);
        player_img_start_pos =  findViewById(R.id.player_img_start_pos);
        player_img_last_pos =  findViewById(R.id.player_img_last_pos);

        toolbar_title_x.setText(cardTitle);
        arrow_up.setOnClickListener(Matchup_WinPlayGoTimeTwo.this);
        toolbar_title_x.setOnClickListener(this);

        rlues_text = findViewById(R.id.rlues_text);
        dec_pp_ll = findViewById(R.id.dec_pp_ll);


        rlues_text.setClickable(true);
        rlues_text.setOnClickListener(this);
//        apply_promo_code.setOnClickListener(this);
      /*  sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);  //STATE_HIDDEN STATE_HALF_EXPANDED
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
//                    Toast.makeText(Matchup_WinPlayGoTimeTwo.this, "yyyyy", Toast.LENGTH_SHORT).show();
                    playBtn1.setVisibility(View.GONE);
                    backBtn.setVisibility(View.VISIBLE);
                } else if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    playBtn1.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });*/


//        for (int i = 0; i < getResources().getStringArray(R.array.picks).length; i++) {
//            Picks mPicks = new Picks();
//            mPicks.setMultiplier(getResources().getStringArray(R.array.multiplier)[i]);
//            mPicks.setPicks(getResources().getStringArray(R.array.picks)[i]);
//            mPicks.setWinpayout(getResources().getStringArray(R.array.winpayout)[i]);
//            picksList.add(mPicks);
//        }
//        CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());


        CustomLinearLayoutManager custom = new CustomLinearLayoutManager(getApplicationContext());
        content_rv.setLayoutManager(custom);
        picksAdapter = new PicksAdapter(picksList, this, support_cash, support_token);
        content_rv.setAdapter(picksAdapter);


        SpinnerAdapter adapter = new SpinnerAdapter(Matchup_WinPlayGoTimeTwo.this, R.layout.spinner_value_layout,
                textArray_amunt, db_role);
        wager_amount_spinner.setAdapter(adapter);
        wager_amount_spinner.setSelection(1);

        wager_amount_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                //    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                selected_Amount = item;


                pr0_amunt.setText("$ "+ Double.parseDouble(selected_Amount) * Double.parseDouble("15.0"));
                pr0_amunt1.setText("$ "+ Double.parseDouble(selected_Amount) * Double.parseDouble("3.0"));
                if (picksList != null) {
                    picksList.clear();

                    if (jpicks != null)
                        for (int i = 0; i < jpicks.length(); i++) {

                            JSONObject jj = null;
                            try {
                                jj = jpicks.getJSONObject(i);
                                Log.e("1371----", jj + "");
                                Picks mPicks = new Picks();
                                mPicks.setPicks(jj.getString("picks"));
                                mPicks.setMultiplier("x" + jj.getString("multiplier"));

                                double a = Double.parseDouble(jj.getString("multiplier"));
                                double b = Double.parseDouble(selected_Amount);
                                double m = (a * b);
                                mPicks.setWinpayout(String.valueOf(m));
                                picksList.add(mPicks);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    if (picksAdapter != null) {
                        picksAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder myshadow = new View.DragShadowBuilder(v);
                v.startDrag(data, myshadow, v, 0);
                return true;
            } else {
                return false;
            }
            }

    };

    View.OnDragListener draglistener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v1, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View v =(View) event.getLocalState();
                    ViewGroup child=(ViewGroup)v.getParent();
                    child.bringChildToFront(v);
                    LinearLayout container = (LinearLayout) v;
                    container.bringChildToFront(v);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }

    };

   /* @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        int action=event.getAction();
        Log.i("@@@@@@", "Touch:"+action);
        //Toast.makeText(DraftTest.this, "??????????"+x+","+y, Toast.LENGTH_SHORT).show();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                break;
            *//**
             * layout(l,t,r,b)
             * l  Left position, relative to parent
             t  Top position, relative to parent
             r  Right position, relative to parent
             b  Bottom position, relative to parent
             * *//*
            case MotionEvent.ACTION_MOVE:
                dx =(int)event.getRawX() - lastX;
                dy =(int)event.getRawY() - lastY;

                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if(left < 0){
                    left = 0;
                    right = left + v.getWidth();
                }
                if(right > screenWidth){
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if(top < 0){
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if(bottom > screenHeight){
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                switch (v.getId()){
                    case R.id.playerCard_start:
                        if(dx > 0){
                            if(playerCard_start.getLeft() >=150 ){
                                if(300 == playerCard_middle.getLeft()) {
                                    playerCard_middle.layout(0, 0, 300, 300);
                                    Animation translateAnimation = new TranslateAnimation(300, 0, 0, 0);
                                    translateAnimation.setFillAfter(true);
                                    translateAnimation.setDuration(600);
                                    playerCard_middle.startAnimation(translateAnimation);
                                    rightMove_flag = 1;
                                }
                            }
                        }else{
                            if(playerCard_start.getLeft()<=150){
                                if(0 == playerCard_middle.getLeft()) {
                                    playerCard_middle.layout(300, 0, 600, 300);
                                    Animation translateAnimation = new TranslateAnimation(-300, 0, 0, 0);
                                    translateAnimation.setFillAfter(true);
                                    translateAnimation.setDuration(600);
                                    playerCard_middle.startAnimation(translateAnimation);
                                    leftMove_flag = 1;
                                }
                            }
                        }
                        break;
                    case R.id.playerCard_middle:
                        if(dx > 0){
                            if(playerCard_middle.getLeft() >= 150){
                                if(300 ==playerCard_start.getLeft()) {
                                    playerCard_start.layout(0, 0, 300, 300);
                                    Animation translateAnimation = new TranslateAnimation(300, 0, 0, 0);
                                    translateAnimation.setFillAfter(true);
                                    translateAnimation.setDuration(600);
                                    playerCard_start.startAnimation(translateAnimation);
                                    rightMove_flag = 1;
                                }
                            }
                        }else{
                            if(playerCard_middle.getLeft() <= 150){
                                if(0 == playerCard_start.getLeft()) {
                                    playerCard_start.layout(300, 0, 600, 300);
                                    Animation translateAnimation = new TranslateAnimation(-300, 0, 0, 0);
                                    translateAnimation.setFillAfter(true);
                                    translateAnimation.setDuration(600);
                                    playerCard_start.startAnimation(translateAnimation);
                                    leftMove_flag = 1;
                                }
                            }
                        }
                }
                break;
            case MotionEvent.ACTION_UP:

                switch (v.getId()){
                    case R.id.playerCard_start:
                        if(dx > 0){
                            if(playerCard_start.getLeft() <=150 ){
                                playerCard_start_resetLeftFlag =true;
                            }
                            if(playerCard_start.getLeft()>=300){
                                playerCard_start_resetRightFlag = true;
                            }
                        }else{
                            if(playerCard_start.getLeft()>=150){
                                playerCard_start_resetRightFlag = true;
                            }

                        }
                        if(1 == rightMove_flag){
                            rightMove_flag = 0;
                            int x1 = playerCard_start.getLeft();
                            int x2 = playerCard_start.getTop();
                            playerCard_start.layout(300,0,600,300);
                            Animation translateAnimation = new TranslateAnimation(x1-300, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_start.startAnimation(translateAnimation);
                            break;
                        }
                        if(playerCard_start_resetLeftFlag){
                            playerCard_start_resetLeftFlag=false;
                            int x1 = playerCard_start.getLeft();
                            int high = playerCard_start.getTop();
                            playerCard_start.layout(0,0,300,300);
                            Animation translateAnimation = new TranslateAnimation(x1, 0, high, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_start.startAnimation(translateAnimation);
                            break;
                        }
                        if(1 ==leftMove_flag){
                            leftMove_flag =0;
                            int x1 = playerCard_start.getLeft();
                            int x2 = playerCard_start.getTop();
                            playerCard_start.layout(0,0,300,300);
                            Animation translateAnimation = new TranslateAnimation(x1, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_start.startAnimation(translateAnimation);
                            break;
                        }
                        if(playerCard_start_resetRightFlag){
                            playerCard_start_resetRightFlag = false;
                            int x1 = playerCard_start.getLeft();
                            int x2 = playerCard_start.getTop();
                            playerCard_start.layout(300,0,600,300);
                            Animation translateAnimation = new TranslateAnimation(x1-300, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_start.startAnimation(translateAnimation);
                            break;
                        }

                        break;
                    case R.id.playerCard_middle:
                        if(dx > 0){
                            if(playerCard_middle.getLeft() <= 150){
                                playerCard_middle_resetLeftflag =true;
                            }
                            if(playerCard_middle.getLeft() >= 300){
                                playerCard_middle_resetRightflag = true;
                            }
                        }else{
                            if(playerCard_middle.getLeft() >= 150){
                                playerCard_middle_resetRightflag = true;
                            }
                        }
                        if(1 == rightMove_flag){
                            rightMove_flag = 0;
                            int x1 = playerCard_middle.getLeft();
                            int x2 = playerCard_middle.getTop();
                            playerCard_middle.layout(300,0,600,300);
                            Animation translateAnimation = new TranslateAnimation(x1-300, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_middle.startAnimation(translateAnimation);
                            break;
                        }
                        if(playerCard_middle_resetLeftflag){
                            playerCard_middle_resetLeftflag=false;
                            int x1 = playerCard_middle.getLeft();
                            int high = playerCard_middle.getTop();
                            playerCard_middle.layout(0,0,300,300);
                            Animation translateAnimation = new TranslateAnimation(x1, 0, high, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_middle.startAnimation(translateAnimation);
                            break;
                        }
                        if(1 ==leftMove_flag){
                            leftMove_flag =0;
                            int x1 = playerCard_middle.getLeft();
                            int x2 = playerCard_middle.getTop();
                            playerCard_middle.layout(0,0,300,300);
                            Animation translateAnimation = new TranslateAnimation(x1, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_middle.startAnimation(translateAnimation);
                            break;
                        }
                        if(playerCard_middle_resetRightflag){
                            playerCard_middle_resetRightflag = false;
                            int x1 = playerCard_middle.getLeft();
                            int x2 = playerCard_middle.getTop();
                            playerCard_middle.layout(300,0,600,300);
                            Animation translateAnimation = new TranslateAnimation(x1-300, 0, x2, 0);
                            translateAnimation.setFillAfter(true);
                            translateAnimation.setDuration(600);
                            playerCard_middle.startAnimation(translateAnimation);
                            break;
                        }
                        break;
                }
                break;
        }

        return false;
    }*/

    public void initApiCall() {

        getApiCallWith1(values);

        getfriendslist();


//        Log.d("db_sessionToken",db_sessionToken);
    }

    public void getfriendslist() {

        Log.d("anderoid", APIs.CARD_DETAILS + getcardID1 + "/matchups");
        AndroidNetworking.get(APIs.CARD_DETAILS + getcardID1 + "/matchups/" + player_id_m1)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", values)
                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("***match : :", response.toString());
                        JSONArray jsonArray = new JSONArray();
                        JSONArray jsonArray_stats = new JSONArray();

                        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                        final Gson gson = builder.create();
                        final Type type = new TypeToken<MatchupModel>() {
                        }.getType();

                        try {
                            matchupModel = gson.fromJson(response.toString(), type);
                            String json = response.toString();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                jsonArray = jsonObject.getJSONArray("matchups");
                                Log.d("jsonArray", String.valueOf(jsonArray));

//                                for(int i =0; i< matchupModel.getMatchups().size(); i++) {
//
//                                    JSONObject jbb = jsonArray.getJSONObject(i);
//
//
//                                    JSONArray array_s = jbb.getJSONArray("displayStats");
//                                    for (int k = 0; k < array_s.length(); k++) {
//
//                                        JSONObject object_ss = array_s.getJSONObject(i);
//                                        StatsPlayerStatistics s_stats = new StatsPlayerStatistics();
//                                        s_stats.setDisplayText(object_ss.getString("displayText"));
//                                        s_stats.setLeftPlayerSqorr(object_ss.getString("leftPlayerSqorr"));
//                                        s_stats.setRightPlayerSqorr(object_ss.getString("rightPlayerSqorr"));
//                                        stats_ps.add(s_stats);
//                                    }
//                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < matchupModel.getMatchups().size(); i++) {
                                JSONObject jbb = jsonArray.getJSONObject(i);
                                if (jbb.has("playerA")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerA");

                                    if (dataObject != null) {
                                        playerA1 = gson.fromJson(jbb.getJSONObject("playerA").toString(), PlayerA.class);

//                                        Log.d("playerB", String.valueOf(playerA));

                                        //Do things with object.

                                    } else {
//                                        Log.d("data", String.valueOf(dataObject));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }

                                if (jbb.has("playerc")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerc");

                                    if (dataObject != null) {
                                        playerC = gson.fromJson(jbb.getJSONObject("playerc").toString(), PlayerC.class);

//                                        Log.d("playerB", String.valueOf(playerA));

                                        //Do things with object.

                                    } else {
//                                        Log.d("data", String.valueOf(dataObject));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }

                                if (jbb.has("playerB")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerB");
                                    if (dataObject != null) {
                                        playerB1 = gson.fromJson(jbb.getJSONObject("playerB").toString(), PlayerB.class);
                                        playerstatus1 = "1";
//                                        Log.d("playerB", String.valueOf(playerB));

                                        //Do things with object.

                                    } else {
                                        jbb.optJSONObject("playerB");
                                        playerstatus1 = "0";
//                                        Log.d("data", String.valueOf(jbb.optJSONObject("playerB")));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }

                                matchupModel.getMatchups().get(i).setPlayerA(playerA1);
                                matchupModel.getMatchups().get(i).setPlayerB(playerB1);
                                matchupModel.getMatchups().get(i).setPlayerstatus(playerstatus1);

                            }

                       /*     LinearLayoutManager gridLayoutManager = new LinearLayoutManager(context);
//                            playerGridView.setLayoutManager(gridLayoutManager);
                            playerGridView.setLayoutManager(new LinearLayoutManager(context));
                            playerGridView.setItemAnimator(new DefaultItemAnimator());
*/


//                            Log.d("size",playerListAdapter.getItemCount());
                            /*final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    playerGridView.setAdapter(playerListAdapter);
                                }
                            }, 5000);*/

//                            progressBar.setVisibility(0);    --visible
//                            progressBar.setVisibility(4);    --invisible


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "matchs----error-------" + anError);

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());


                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }

                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });


    }


    public void getApiCallWith1(String sessiontoken) {

        Log.d("anderoid", APIs.CARD_DETAILS + getcardID1 + "/matchups");
        AndroidNetworking.get(APIs.CARD_DETAILS + getcardID1 + "/matchups/" + player_id_m1)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", sessiontoken)
                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("***match : :", response.toString());
                        JSONArray jsonArray = new JSONArray();
                        JSONArray jsonArray_stats = new JSONArray();

                        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                        final Gson gson = builder.create();
                        final Type type = new TypeToken<MatchupModel>() {
                        }.getType();

                        try {
                            matchupModel = gson.fromJson(response.toString(), type);
                            String json = response.toString();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                jsonArray = jsonObject.getJSONArray("matchups");
                                Log.d("jsonArray", String.valueOf(jsonArray));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jbb = jsonArray.getJSONObject(i);
                                    if (jbb.has("playerA")) {
                                        JSONObject dataObject = jbb.optJSONObject("playerA");

                                        String isLive = dataObject.getString("isLive");
//                                        Log.d("playerB", String.valueOf(playerA));
                                        Log.d("isLive", String.valueOf(isLive));
                                        if (dataObject != null) {
                                            playerA1 = gson.fromJson(jbb.getJSONObject("playerA").toString(), PlayerA.class);


                                            //Do things with object.
                                            matchupModel.getMatchups().get(i).getPlayerA().setIsLive(isLive);
                                        } else {
//                                        Log.d("data", String.valueOf(dataObject));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                            //Do things with array
                                        }

                                    }
                                    if (jbb.has("playerB")) {
                                        JSONObject dataObject = jbb.optJSONObject("playerB");
                                        if (dataObject != null) {
                                            playerB1 = gson.fromJson(jbb.getJSONObject("playerB").toString(), PlayerB.class);
                                            //   playerstatus1 = "1";


                                        } else {
                                            jbb.optJSONObject("playerB");
                                            //      playerstatus1 = "0";
//                                        Log.d("data", String.valueOf(jbb.optJSONObject("playerB")));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                            //Do things with array
                                        }

                                    }


//                                    matchupModel.getMatchups().get(i).getPlayerA().setLastName("sreenivasulu");
//
////                                    matchupModel.getMatchups().get(i).setPlayerB(playerB1);
//                                    matchupModel.getMatchups().get(i).setPlayerstatus(playerstatus1);


                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }




                            /*
                            for (int j = 0; j < matchupModel.getMatchups().size(); j++) {



                                List<Double> first_A_points = new ArrayList<>();
                                List<String> first_B_points = new ArrayList<>();
                                List<String> keyNames = new ArrayList<>();
                                Set<String> A = playerA_Stats.get(j).keySet();
                                Set<String> B = playerB_Stats.get(j).keySet();
                                Iterator<String> a = A.iterator();

                                while (a.hasNext()) {
                                    String playerA_object_data = a.next();
                                    // recreate iterator for second list
                                    Iterator<String> secondIt = B.iterator();
                                    if (!secondIt.hasNext()) {
                                        first_B_points.add("000");
                                        try {
                                            JSONObject AjsonObject = new JSONObject(playerA_Stats.get(j).get(playerA_object_data).toString());
                                            first_A_points.add(AjsonObject.getDouble("value"));
                                            keyNames.add(playerA_object_data);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    while (secondIt.hasNext()) {
                                        String playerB_object_data = secondIt.next();
                                        if (playerA_object_data.equals(playerB_object_data)) {
                                            JSONObject AjsonObject = null;
                                            JSONObject BjsonObject = null;
                                            Log.v("KEY" + j + "==>", playerA_object_data + "value==>" + playerA_Stats.get(j).get(playerA_object_data));
                                            try {
                                                AjsonObject = new JSONObject(playerA_Stats.get(j).get(playerA_object_data).toString());
                                                BjsonObject = new JSONObject(playerB_Stats.get(j).get(playerA_object_data).toString());
                                                Log.v("VALUE==>", AjsonObject.getDouble("value") + "");
                                                first_A_points.add(AjsonObject.getDouble("value"));
                                                first_B_points.add(String.valueOf(BjsonObject.getDouble("value")));
                                                keyNames.add(playerA_object_data);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                NewPlayerStatistics mNewPlayerStatistics = new NewPlayerStatistics();
                                mNewPlayerStatistics.setFirst_player_points(first_A_points);
                                mNewPlayerStatistics.setSecond_player_points(first_B_points);
                                mNewPlayerStatistics.setPlayer_positions(keyNames);
                                mNewPlayerStatisticsList.add(mNewPlayerStatistics);





                            }

                            */

                            //   MATCHUPS_CARD_DATE = matchupModel.getSettlementDate();

//                            if (matchupModel.getIsPurchased()) {
                            if (k_t == true) {
                                ViewGroup.MarginLayoutParams marginLayoutParams =
                                        (ViewGroup.MarginLayoutParams) playerGridView.getLayoutParams();
                                marginLayoutParams.setMargins(8, 8, 8, 5);

                          /*      playerListAdapter1 = new NewPlayerListAdapter(matchupModel.getMatchups(), playerB1, mNewPlayerStatisticsList, stats_ps, matchupModel.getIsPurchased(), context, new NewPlayerListAdapter.OnItemClick() {
                                    @Override
                                    public void onClick(HashMap<String, String> matchup_selections) {


                                    }
                                }, "2");*/
                            }

//                            playerGridView.setAdapter(playerListAdapter1);


//                            Log.d("size",playerListAdapter.getItemCount());
                            /*final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    playerGridView.setAdapter(playerListAdapter);
                                }
                            }, 5000);*/

//                            progressBar.setVisibility(0);    --visible
//                            progressBar.setVisibility(4);    --invisible
//                            progressBar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "matchs----error-------" + anError);

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
                                }


                            } catch (Exception e) {

                            }


                        } else {
                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                        }

                    }
                });


    }

    JSONArray jpicks;

    private void getApiCallWith(String sessiontoken) {
        Log.e("anderoid", APIs.CARD_DETAILS + getcardID + "/matchups/" + player_id_m);
//        AndroidNetworking.get(APIs.CARD_DETAILS + getcardID)
        AndroidNetworking.get(APIs.CARD_DETAILS + getcardID + "/matchups/" + player_id_m1)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", sessiontoken)
                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("***match : :", response.toString());
                        JSONArray jsonArray = new JSONArray();
                        JSONArray jsonArray_stats = new JSONArray();

                        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                        final Gson gson = builder.create();
                        final Type type = new TypeToken<MatchupModel>() {
                        }.getType();

                        try {
                            final MatchupModel matchupModel = gson.fromJson(response.toString(), type);
                            String json = response.toString();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                jsonArray = jsonObject.getJSONArray("matchups");
                                Log.d("jsonArray", String.valueOf(jsonArray));


                                if (matchupModel.getDescription() != null) {
                                    desc_card.setVisibility(View.VISIBLE);
                                    desc_card.setText(matchupModel.getDescription());
                                } else {
                                    desc_card.setVisibility(View.GONE);

                                }


                                jpicks = jsonObject.getJSONArray("payStructure");

                                for (int i = 0; i < jpicks.length(); i++) {

                                    JSONObject jj = jpicks.getJSONObject(i);
                                    Log.e("1371----", jj + "");
                                    Picks mPicks = new Picks();
                                    mPicks.setPicks(jj.getString("picks"));
                                    mPicks.setMultiplier("x" + jj.getString("multiplier"));

                                    mPicks.setWinpayout("0");
                                    picksList.add(mPicks);
                                }

                                tenn();

//                                for(int i =0; i< matchupModel.getMatchups().size(); i++) {
//
//                                    JSONObject jbb = jsonArray.getJSONObject(i);
//
//
//                                    JSONArray array_s = jbb.getJSONArray("displayStats");
//                                    for (int k = 0; k < array_s.length(); k++) {
//
//                                        JSONObject object_ss = array_s.getJSONObject(i);
//                                        StatsPlayerStatistics s_stats = new StatsPlayerStatistics();
//                                        s_stats.setDisplayText(object_ss.getString("displayText"));
//                                        s_stats.setLeftPlayerSqorr(object_ss.getString("leftPlayerSqorr"));
//                                        s_stats.setRightPlayerSqorr(object_ss.getString("rightPlayerSqorr"));
//                                        stats_ps.add(s_stats);
//                                    }
//                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            for (int i = 0; i < matchupModel.getMatchups().size(); i++) {
                                JSONObject jbb = jsonArray.getJSONObject(i);
                                if (jbb.has("playerA")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerA");
                                    if (dataObject != null) {
                                        playerA = gson.fromJson(jbb.getJSONObject("playerA").toString(), PlayerA.class);

//                                        Log.d("playerB", String.valueOf(playerA));

                                        //Do things with object.

                                    } else {
//                                        Log.d("data", String.valueOf(dataObject));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }
                                if (jbb.has("playerB")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerB");
                                    if (dataObject != null) {
                                        playerB = gson.fromJson(jbb.getJSONObject("playerB").toString(), PlayerB.class);
                                        playerstatus = "1";
//                                        Log.d("playerB", String.valueOf(playerB));

                                        //Do things with object.

                                    } else {
                                        jbb.optJSONObject("playerB");
                                        playerstatus = "0";
//                                        Log.d("data", String.valueOf(jbb.optJSONObject("playerB")));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }


                                // C

                                if (jbb.has("playerC")) {
                                    JSONObject dataObject = jbb.optJSONObject("playerC");

                                    if (dataObject != null) {
                                        playerC = gson.fromJson(jbb.getJSONObject("playerC").toString(), PlayerC.class);
                                        Playerstatus_C = "1";
//                                        Log.d("playerB", String.valueOf(playerA));

                                        //Do things with object.

                                    } else {
//                                        jbb.optJSONObject("playerC");
                                        Playerstatus_C = "0";
//                                        Log.d("data", String.valueOf(dataObject));

//                                        JSONArray link_data = pagination_data.getJSONArray("links");

                                        //Do things with array
                                    }

                                }

                                matchupModel.getMatchups().get(i).setPlayerA(playerA);
                                matchupModel.getMatchups().get(i).setPlayerC(playerC);
                                matchupModel.getMatchups().get(i).setPlayerB(playerB);
                                matchupModel.getMatchups().get(i).setPlayerstatus(playerstatus);
                                matchupModel.getMatchups().get(i).setPlayerstatus_C(Playerstatus_C);


//                                try {

                                   /* JSONObject jbb = jsonArray.getJSONObject(i);

                                    JSONArray array_s= jbb.getJSONArray("displayStats");
                                    for(int k=0;k<array_s.length();k++) {

                                        JSONObject object_ss = array_s.getJSONObject(i);
                                        StatsPlayerStatistics s_stats = new StatsPlayerStatistics();
                                        s_stats.setDisplayText(object_ss.getString("displayText"));
                                        s_stats.setLeftPlayerSqorr(object_ss.getString("leftPlayerSqorr"));
                                        s_stats.setRightPlayerSqorr(object_ss.getString("rightPlayerSqorr"));
                                        stats_ps.add(s_stats);
                                    }
*/

                                    /*if (!jsonArray.getJSONObject(i).isNull("playerA") ){//&& !jsonArray.getJSONObject(i).getJSONObject("playerA").isNull("avgStats")) {
                                        Map<String, Object> playerA = toMap(jsonArray.getJSONObject(i).getJSONObject("playerA").getJSONObject("avgStats").getJSONObject("stats"));
                                        playerA_Stats.add(playerA);
                                    } else {
                                        JSONObject playerA_dummy_jsonObject = new JSONObject(AppConstants.PLAYERA_DUMMY_JSON);
                                        Map<String, Object> playerA = toMap(playerA_dummy_jsonObject);
                                        playerA_Stats.add(playerA);
                                    }
                                    if (!jsonArray.getJSONObject(i).isNull("playerB") && !jsonArray.getJSONObject(i).getJSONObject("playerB").isNull("avgStats")) {
                                        Map<String, Object> playerB = toMap(jsonArray.getJSONObject(i).getJSONObject("playerB").getJSONObject("avgStats").getJSONObject("stats"));
                                        playerB_Stats.add(playerB);
                                    } else {
                                        Map<String, Object> playerB = new HashMap<>();
                                        playerB_Stats.add(playerB);
                                    }*/

                                    /*jsonArray_stats =jsonArray.getJSONObject(i) //getJson().getJSONArray("matchups");
//                                    jsonArray = jsonObject.getJSONArray("matchups");
                                    if(jsonArray.getJSONObject(i).isNull("displayStats")){

                                        StatsPlayerStatistics s_stats = new StatsPlayerStatistics();
                                        s_stats.setDisplayText("display");
                                        s_stats.setLeftPlayerSqorr("1");
                                        s_stats.setRightPlayerSqorr("2");
                                        stats_ps.add(s_stats);
                                    }
*/

//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }for
                            }




                            /*
                            for (int j = 0; j < matchupModel.getMatchups().size(); j++) {



                                List<Double> first_A_points = new ArrayList<>();
                                List<String> first_B_points = new ArrayList<>();
                                List<String> keyNames = new ArrayList<>();
                                Set<String> A = playerA_Stats.get(j).keySet();
                                Set<String> B = playerB_Stats.get(j).keySet();
                                Iterator<String> a = A.iterator();

                                while (a.hasNext()) {
                                    String playerA_object_data = a.next();
                                    // recreate iterator for second list
                                    Iterator<String> secondIt = B.iterator();
                                    if (!secondIt.hasNext()) {
                                        first_B_points.add("000");
                                        try {
                                            JSONObject AjsonObject = new JSONObject(playerA_Stats.get(j).get(playerA_object_data).toString());
                                            first_A_points.add(AjsonObject.getDouble("value"));
                                            keyNames.add(playerA_object_data);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    while (secondIt.hasNext()) {
                                        String playerB_object_data = secondIt.next();
                                        if (playerA_object_data.equals(playerB_object_data)) {
                                            JSONObject AjsonObject = null;
                                            JSONObject BjsonObject = null;
                                            Log.v("KEY" + j + "==>", playerA_object_data + "value==>" + playerA_Stats.get(j).get(playerA_object_data));
                                            try {
                                                AjsonObject = new JSONObject(playerA_Stats.get(j).get(playerA_object_data).toString());
                                                BjsonObject = new JSONObject(playerB_Stats.get(j).get(playerA_object_data).toString());
                                                Log.v("VALUE==>", AjsonObject.getDouble("value") + "");
                                                first_A_points.add(AjsonObject.getDouble("value"));
                                                first_B_points.add(String.valueOf(BjsonObject.getDouble("value")));
                                                keyNames.add(playerA_object_data);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                NewPlayerStatistics mNewPlayerStatistics = new NewPlayerStatistics();
                                mNewPlayerStatistics.setFirst_player_points(first_A_points);
                                mNewPlayerStatistics.setSecond_player_points(first_B_points);
                                mNewPlayerStatistics.setPlayer_positions(keyNames);
                                mNewPlayerStatisticsList.add(mNewPlayerStatistics);





                            }

                            */

                            //   MATCHUPS_CARD_DATE = matchupModel.getSettlementDate();

//                            if (matchupModel.getIsPurchased()) {
                            if (k_t == true) {
                                ViewGroup.MarginLayoutParams marginLayoutParams =
                                        (ViewGroup.MarginLayoutParams) playerGridView.getLayoutParams();
                                marginLayoutParams.setMargins(8, 8, 8, 5);
                                playerGridView.setLayoutParams(marginLayoutParams);

//                                playerGridView.setMa
//                                setVisibility(View.GONE);

                                win_loss_ll.setVisibility(View.VISIBLE);
                                win_c.setText("" + matchupModel.getMatchupsWon());
                                picks_c.setText("" + matchupModel.getMatchupsPlayed());

                                Log.e("k---------", matchupModel.getCurrencyTypeIsTokens() + "");
                                if (matchupModel.getCurrencyTypeIsTokens()) {
                                    payout_c.setText("" + matchupModel.getPayout());
                                    payout_c.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);

                                } else {
                                    payout_c.setText("$" + matchupModel.getPayout());
                                }


                                if (matchupModel.getStatus().equals("CANCELLED")) {
                                    loss_ll.setVisibility(View.VISIBLE);
                                } else if (matchupModel.getStatus().equals("LOSS")) {
                                    loss_ll.setVisibility(View.VISIBLE);
                                } else if (matchupModel.getStatus().equals("WIN")) {
                                    win_ll.setVisibility(View.VISIBLE);
                                }

                            } else {

                                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                    Log.e("kk---", "1");

                                    if (matchupModel.getSupportsMoney().equals(false) && matchupModel.getSupportsTokens().equals(true)) {
                                        Log.e("kk---", "2");
                                        support_cash = false;
                                        support_token = true;
                                        cashORtoken();
                                    } else {
                                        Log.e("kk---", "3");

                                        cashNOToken();
                                    }
                                }

                                int s_count = Integer.parseInt(matchupModel.getTotalPurchasedAmount());
                                Log.e("1464", "----" + s_count);
                                if (s_count >= 2) {
                                    playpick_info.setVisibility(View.VISIBLE);
                                    if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                        playpick_info.setText("You purchased this card before for $" + matchupModel.getPurchasedAmount() + " but you can purchase it again for up to $" + (Integer.parseInt(matchupModel.getMaxPurchaseAmount()) - Integer.parseInt(matchupModel.getTotalPurchasedAmount())) + ". The limit is $" + matchupModel.getMaxPurchaseAmount() + " total");
                                        playpick_info.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    } else {
                                        playpick_info.setText("You purchased this card before for " + matchupModel.getPurchasedAmount() + " but you can purchase it again for up to " + (Integer.parseInt(matchupModel.getTotalPurchasedAmount()) - Integer.parseInt(matchupModel.getMaxPurchaseAmount())) + " tokens. The limit is " + matchupModel.getMaxPurchaseAmount() + " tokens total");

                                        //      playpick_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                    }
                                } else {
                                    playpick_info.setVisibility(View.GONE);
                                }


//                                setVisibility(View.VISIBLE);
                                win_loss_ll.setVisibility(View.GONE);
                            }
                            no_of_picks_count.setText("0" + '/' + matchupModel.getMatchups().size());


                            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            playerGridView.setLayoutManager(gridLayoutManager);
//                            playerGridView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            playerGridView.setLayoutManager(gridLayoutManager);
                            playerGridView.setItemAnimator(new DefaultItemAnimator());

//                            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
////                            playerGridView.setLayoutManager(gridLayoutManager);
//                            playerGridView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                            playerGridView.setItemAnimator(new DefaultItemAnimator());

                            p_amt = matchupModel.getTotalPurchasedAmount();

                            WinPlayGoTwoNewPlayerListAdapter playerListAdapter = new WinPlayGoTwoNewPlayerListAdapter(player_Name_start_pos,player_Name_middle_pos,player_Name_last_pos,
                                    player_img_middle_pos,player_img_start_pos,player_img_last_pos,Card_type_info,
                                    matchupModel.getMatchups(), playerB, mNewPlayerStatisticsList, stats_ps, k_t, Matchup_WinPlayGoTimeTwo.this, playerGridView, new WinPlayGoTwoNewPlayerListAdapter.OnItemClick() {
                                @Override
                                public void onClick(HashMap<String, String> matchup_selections) {


                                    List<String> matchups_c1 = new ArrayList<String>(matchup_selections_new_cash.keySet());
                                    List<String> pickindexes_c1 = new ArrayList<String>(matchup_selections_new_cash.values());

                                    for (int i = 0; i < matchups_c1.size(); i++) {
                                        Selection selection = new Selection();
                                        selection.setMatchup(matchups_c1.get(i));
                                        selection.setPickIndex(pickindexes_c1.get(i));

                                    }
                                    Log.e("2383-------------",matchup_selections+"----1----"+matchups_c1 + "----------2---"+pickindexes_c1);

                                    if ((matchup_selections.size()) >= 2 && (matchup_selections.size()) <= 10) {
//                                        matchup_selections = matchup_selections;

                                        matchup_selections_new = matchup_selections;
                                        matchup_selections_new_cash = matchup_selections;
                                        matchup_selections_new_token = matchup_selections;
                                        matchup_selections_new_guest = matchup_selections;

                                        //playBtn.setBackgroundColor(getResources().getColor(R.color.darkred));
//                                        playBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_red_ripple, null));
                                        playBtn.setEnabled(true);
                                        playBtn.setClickable(true);
                                        mim_4.setVisibility(View.GONE);
//                                        mim_4.setVisibility(View.VISIBLE);

                                        card_h = matchupModel.getCardId();
                                        MATCHUPS_CARDID = matchupModel.getCardId();
                                        playBtn.setVisibility(View.VISIBLE);


                                        List<String> matchups_c = new ArrayList<String>(matchup_selections_new_cash.keySet());
                                        List<String> pickindexes_c = new ArrayList<String>(matchup_selections_new_cash.values());

                                        for (int i = 0; i < matchups_c.size(); i++) {
                                            Selection selection = new Selection();
                                            selection.setMatchup(matchups_c.get(i));
                                            selection.setPickIndex(pickindexes_c.get(i));

                                        }

//                                        player_Name_start_pos.setText(matchup_selections.);




                                    } else {
                                        playBtn.setClickable(false);
                                        mim_4.setVisibility(View.VISIBLE);
                                        //    playBtn.setBackgroundColor(getResources().getColor(R.color.hint));
                                        //   playBtn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_bg_grey, null));
                                        playBtn.setEnabled(false);
                                        playBtn.setVisibility(View.GONE);
                                    }
                                    setUpPicksMultiplerWinPayOut(matchup_selections.size());
                                    no_of_picks_count.setText(matchup_selections.size() + "/" + matchupModel.getMatchups().size());
                                }
                            }, "1");

                            playerGridView.setAdapter(playerListAdapter);



                            if (playerListAdapter != null) {
                                playerListAdapter.notifyDataSetChanged();
                            }

//                            Log.d("size",playerListAdapter.getItemCount());
                            /*final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    playerGridView.setAdapter(playerListAdapter);
                                }
                            }, 5000);*/

//                            progressBar.setVisibility(0);    --visible
//                            progressBar.setVisibility(4);    --invisible
                            progressBar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("js", "matchs----error-------" + anError);

                        if (anError.getErrorCode() != 0) {
                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());

                            try {
                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                String au = ej.getString("message");
                                if (au.contains("Unauthorized")) {
                                    showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                } else {
                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
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
                mydb.resetLocalData();

                LoginManager.getInstance().logOut();

                Intent in = new Intent(Matchup_WinPlayGoTimeTwo.this, OnBoarding.class);
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

    //Inside the activity that makes a connection to the helper class
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //call close() of the helper class
        mydb.close();
    }

    @SuppressLint("SetTextI18n")
    public void setUpPicksMultiplerWinPayOut(int picks) {
        switch (picks) {
            case 4:

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("4")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {

                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

//
//                multiplier_count.setText("X10");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$100");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("100");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }

                break;
            case 5:
//                multiplier_count.setText("X18");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$180");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("180");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }
                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("5")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                                winpayout.setText("$" + m);
//                                winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 6:
//                multiplier_count.setText("X35");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$350");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("350");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }
                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("6")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {


                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 7:
//                multiplier_count.setText("X70");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$700");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("700");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }
                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("7")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                                winpayout.setText("$" + m);
//                                winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 8:
//                multiplier_count.setText("X125");
//                winpayout.setText("$1250");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$1250");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("1250");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }
                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("8")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                                winpayout.setText("$" + m);
//                                winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 9:
//                multiplier_count.setText("X250");
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$2500");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("2500");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }
                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("9")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                                winpayout.setText("$" + m);
//                                winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 10:
//                multiplier_count.setText("X500");
//
//                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$5000");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                } else {
//                    winpayout.setText("5000");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                }

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));

                        if (jj.getString("picks").equalsIgnoreCase("10")) {

                            mPicks.setMultiplier("x" + jj.getString("multiplier"));

                            double a = Double.parseDouble(jj.getString("multiplier"));
                            double m = (a * 10);
                            mPicks.setWinpayout(String.valueOf(m));

                            multiplier_count.setText("X" + jj.getString("multiplier"));
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                multiplier_count.setText("X0");
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                    winpayout.setText("$0");
//                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    if (support_token == true && support_cash == false) {
                        winpayout.setText("");
                        winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                    } else {
                        winpayout.setText("$0");
                        winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                } else {
                    winpayout.setText("0");
                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                }
                break;

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playBtn:


                MATCHUPS_CARDTITLE = cardTitle;
                MATCHUPS_CARDID = card_h;


                if (progressDialog != null)
                    progressDialog.show();
                String userEnteredAmount = selected_Amount;
//                String userEnteredAmount = amt_edt.getText().toString().trim();
                if (userEnteredAmount.length() == 0) { // If user did not selected any amount
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Please select amount");
                } else { // User selected amount

                    if (Integer.valueOf(p_amt) <= 18) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (userEnteredAmount.contains("$")) {
                            userEnteredAmount = userEnteredAmount.replace("$", "");
                        } else {
                            userEnteredAmount = selected_Amount;

                        }

//                        if (userEnteredAmount.contains("$")) {
//                            userEnteredAmount = userEnteredAmount.replace("$", "");
//                        }
                        if (!userEnteredAmount.equals("")) {
                            int selectedAmount = Integer.parseInt(userEnteredAmount);
                            if (selectedAmount < 1) {  // Check for minimum amount of 2

                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Minimum amount should be $1");
                            } else if (selectedAmount > 20) { // Check for max amount of 20
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Custom amount cannot exceed $20");
                            } else { // If everything is valid
                           /* InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                            if(imm!=null)
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);*/
                                if (db_role != null && db_role.equalsIgnoreCase("cash")) { // If cash user

                                    if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                                        if (Utilities.getAge(DATA_DOB) >= 18) {
                                            getCashPurchaseCard(userEnteredAmount, card_h, false);

                                        } else {
                                            showAlertBox(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                                        }


                                    } else {
                                        Utilities.showAlertBoxTrans(Matchup_WinPlayGoTimeTwo.this, getString(R.string.age_to_cash_title), getString(R.string.token_to_cash_msg));
                                    }


                                } else if (db_role != null && db_role.equalsIgnoreCase("tokens")) { //if tokens user
//                                if(progressDialog!=null)
//                                    progressDialog.show();

                                    getTokensPurchaseCard(userEnteredAmount, card_h, true);
                                } else {
//                                if(progressDialog!=null)
//                                    progressDialog.show();

                                    getGuestPurchaseCard(userEnteredAmount, MATCHUPS_CARDID);
                                }
                            }
                        }
                    } else {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Utilities.showAlertBoxTrans(Matchup_WinPlayGoTimeTwo.this, "Alert", "Maximum  amount  you pay on a card is exceed.");

                    }
                }


                break;
            case R.id.playBtn1:
                //        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                playBtn1.setVisibility(View.GONE);
                backBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.backBtn:

                //   sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                backBtn.setVisibility(View.GONE);
                playBtn1.setVisibility(View.VISIBLE);
                break;
            case R.id.rlues_text:
                dec_pp_ll.setVisibility(dec_pp_ll.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            rlues_text.setright(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//                desc_card.setVisibility(View.GONE);
                break;
            case R.id.toolbar_title_x:

                //================ Hide Virtual Key Board When  Clicking==================//

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toolbar_title_x.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

//======== Hide Virtual Keyboard =====================//

                if (home_s.equalsIgnoreCase("homecards")) {
                    int pickCount = 0;
                    if (no_of_picks_count.getText().toString().contains("/")) {
                        String[] picksCnt = no_of_picks_count.getText().toString().split("/");
                        if (picksCnt.length > 0) {
                            pickCount = Integer.parseInt(picksCnt[0]);
                            if (pickCount > 0) {
                                showAlertBoxTwo(Matchup_WinPlayGoTimeTwo.this, "Are you sure you want \nto close this card?", "Your progress will be lost.");
                            } else {
                                finish();
                            }
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
//                finish();
                break;
            case R.id.amt_txt:
                amt_txt.setVisibility(View.GONE);
                amt_edt.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_2:

                picksList.clear();

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));
                        mPicks.setMultiplier("x" + jj.getString("multiplier"));

                        double a = Double.parseDouble(jj.getString("multiplier"));
                        double m = (a * 2);
                        mPicks.setWinpayout(String.valueOf(m));
                        picksList.add(mPicks);

                        String mu = "x" + jj.getString("multiplier");
                        if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {

                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }

                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }

//                        if (db_role == null || db_role.equalsIgnoreCase("cash")) {
//                            winpayout.setText("$" + m);
//                            winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                        } else {
//                            winpayout.setText("" + m);
//                            winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (picksAdapter != null) {
                    picksAdapter.notifyDataSetChanged();
                }
                Log.e("1371----", picksList.size() + "");
                amt_txt.setVisibility(View.GONE);
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        amt_edt.setVisibility(View.GONE);
                    } else {
                        amt_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    amt_edt.setVisibility(View.GONE);
                }
                amt_edt.setText(twox.getText().toString().trim());
                amt_edt.setSelection(twox.getText().toString().length());

                ll_2.setBackgroundResource(R.drawable.btn_white_bg);
                twox.setTextColor(getResources().getColor(R.color.dark_gray));
                iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);

                ll_5.setBackgroundResource(R.drawable.btn_gray_bg);
                fivex.setTextColor(getResources().getColor(R.color.white));
                iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_10.setBackgroundResource(R.drawable.btn_gray_bg);
                tenx.setTextColor(getResources().getColor(R.color.white));
                iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_15.setBackgroundResource(R.drawable.btn_gray_bg);
                fivteenx.setTextColor(getResources().getColor(R.color.white));
                iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_20.setBackgroundResource(R.drawable.btn_gray_bg);
                twentyx.setTextColor(getResources().getColor(R.color.white));
                iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.ll_5:

                picksList.clear();

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));
                        mPicks.setMultiplier("x" + jj.getString("multiplier"));

                        double a = Double.parseDouble(jj.getString("multiplier"));
                        double m = (a * 5);
                        mPicks.setWinpayout(String.valueOf(m));
                        picksList.add(mPicks);

                        String mu = "x" + jj.getString("multiplier");
                        if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }


                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (picksAdapter != null) {
                    picksAdapter.notifyDataSetChanged();
                }
                Log.e("1371----", picksList.size() + "");
                amt_txt.setVisibility(View.GONE);
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        amt_edt.setVisibility(View.GONE);

                    } else {
                        amt_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    amt_edt.setVisibility(View.GONE);
                }

                amt_edt.setText(fivex.getText().toString().trim());
                amt_edt.setSelection(twox.getText().toString().length());

                ll_2.setBackgroundResource(R.drawable.btn_gray_bg);
                twox.setTextColor(getResources().getColor(R.color.white));
                iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_5.setBackgroundResource(R.drawable.btn_white_bg);
                fivex.setTextColor(getResources().getColor(R.color.dark_gray));
                iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);

                ll_10.setBackgroundResource(R.drawable.btn_gray_bg);
                tenx.setTextColor(getResources().getColor(R.color.white));
                iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_15.setBackgroundResource(R.drawable.btn_gray_bg);
                fivteenx.setTextColor(getResources().getColor(R.color.white));
                iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_20.setBackgroundResource(R.drawable.btn_gray_bg);
                twentyx.setTextColor(getResources().getColor(R.color.white));
                iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;

            case R.id.ll_10:

                picksList.clear();

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));
                        mPicks.setMultiplier("x" + jj.getString("multiplier"));

                        double a = Double.parseDouble(jj.getString("multiplier"));
                        double m = (a * 10);
                        mPicks.setWinpayout(String.valueOf(m));
                        picksList.add(mPicks);

                        String mu = "x" + jj.getString("multiplier");
                        if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (picksAdapter != null) {
                    picksAdapter.notifyDataSetChanged();
                }
                Log.e("1371----", picksList.size() + "");
                amt_txt.setVisibility(View.GONE);
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        amt_edt.setVisibility(View.GONE);

                    } else {
                        amt_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    amt_edt.setVisibility(View.GONE);
                }
                amt_edt.setText(tenx.getText().toString().trim());
                amt_edt.setSelection(twox.getText().toString().length());

                ll_2.setBackgroundResource(R.drawable.btn_gray_bg);
                twox.setTextColor(getResources().getColor(R.color.white));
                iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_5.setBackgroundResource(R.drawable.btn_gray_bg);
                fivex.setTextColor(getResources().getColor(R.color.white));
                iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_10.setBackgroundResource(R.drawable.btn_white_bg);
                tenx.setTextColor(getResources().getColor(R.color.dark_gray));
                iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);

                ll_15.setBackgroundResource(R.drawable.btn_gray_bg);
                fivteenx.setTextColor(getResources().getColor(R.color.white));
                iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_20.setBackgroundResource(R.drawable.btn_gray_bg);
                twentyx.setTextColor(getResources().getColor(R.color.white));
                iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.ll_15:


                picksList.clear();

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));
                        mPicks.setMultiplier("x" + jj.getString("multiplier"));

                        double a = Double.parseDouble(jj.getString("multiplier"));
                        double m = (a * 15);
                        mPicks.setWinpayout(String.valueOf(m));
                        picksList.add(mPicks);

                        String mu = "x" + jj.getString("multiplier");
                        if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (picksAdapter != null) {
                    picksAdapter.notifyDataSetChanged();
                }
                Log.e("1371----", picksList.size() + "");

                amt_txt.setVisibility(View.GONE);
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        amt_edt.setVisibility(View.GONE);

                    } else {
                        amt_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    amt_edt.setVisibility(View.GONE);

                }
                amt_edt.setText(fivteenx.getText().toString().trim());
                amt_edt.setSelection(twox.getText().toString().length());

                ll_2.setBackgroundResource(R.drawable.btn_gray_bg);
                twox.setTextColor(getResources().getColor(R.color.white));
                iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_5.setBackgroundResource(R.drawable.btn_gray_bg);
                fivex.setTextColor(getResources().getColor(R.color.white));
                iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_10.setBackgroundResource(R.drawable.btn_gray_bg);
                tenx.setTextColor(getResources().getColor(R.color.white));
                iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_15.setBackgroundResource(R.drawable.btn_white_bg);
                fivteenx.setTextColor(getResources().getColor(R.color.dark_gray));
                iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);

                ll_20.setBackgroundResource(R.drawable.btn_gray_bg);
                twentyx.setTextColor(getResources().getColor(R.color.white));
                iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.ll_20:


                picksList.clear();

                for (int i = 0; i < jpicks.length(); i++) {

                    JSONObject jj = null;
                    try {
                        jj = jpicks.getJSONObject(i);
                        Log.e("1371----", jj + "");
                        Picks mPicks = new Picks();
                        mPicks.setPicks(jj.getString("picks"));
                        mPicks.setMultiplier("x" + jj.getString("multiplier"));

                        double a = Double.parseDouble(jj.getString("multiplier"));
                        double m = (a * 20);
                        mPicks.setWinpayout(String.valueOf(m));
                        picksList.add(mPicks);

                        String mu = "x" + jj.getString("multiplier");
                        if (mu.equalsIgnoreCase(multiplier_count.getText().toString().trim())) {
                            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                                if (support_token == true && support_cash == false) {
                                    winpayout.setText("" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                                } else {
                                    winpayout.setText("$" + m);
                                    winpayout.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                }
                            } else {
                                winpayout.setText("" + m);
                                winpayout.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (picksAdapter != null) {
                    picksAdapter.notifyDataSetChanged();
                }
                Log.e("1371----", picksList.size() + "");

                amt_txt.setVisibility(View.GONE);
                if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        amt_edt.setVisibility(View.GONE);

                    } else {
                        amt_edt.setVisibility(View.VISIBLE);
                    }
                } else {
                    amt_edt.setVisibility(View.GONE);
                }
                amt_edt.setText(twentyx.getText().toString().trim());
                amt_edt.setSelection(twox.getText().toString().length());

                ll_2.setBackgroundResource(R.drawable.btn_gray_bg);
                twox.setTextColor(getResources().getColor(R.color.white));
                iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_5.setBackgroundResource(R.drawable.btn_gray_bg);
                fivex.setTextColor(getResources().getColor(R.color.white));
                iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_10.setBackgroundResource(R.drawable.btn_gray_bg);
                tenx.setTextColor(getResources().getColor(R.color.white));
                iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_15.setBackgroundResource(R.drawable.btn_gray_bg);
                fivteenx.setTextColor(getResources().getColor(R.color.white));
                iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

                ll_20.setBackgroundResource(R.drawable.btn_white_bg);
                twentyx.setTextColor(getResources().getColor(R.color.dark_gray));
                iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
                break;
            case R.id.llAddFunds:
                if (db_role != null && db_role.equalsIgnoreCase("cash")) {

                    if (support_token == true && support_cash == false) {

                    } else {
                        Intent addFundsIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                        addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(addFundsIntent);
                    }

                } else if (db_role != null && db_role.equalsIgnoreCase("tokens")) {
                    getTokenFromCash();
                }
                break;
           /* case R.id.tvAddFundsForCash:
              // Token Button
                getTokenFromCash();
                break;*/
            case R.id.c_btn:
                if (db_role != null && db_role.equalsIgnoreCase("cash")) {
                    if (support_token == true && support_cash == false) {
                        Intent addFundsIntent1 = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                        addFundsIntent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(addFundsIntent1);
                    } else {
                        Intent addFundsIntent1 = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                        addFundsIntent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(addFundsIntent1);
                    }

                }
                break;
            case R.id.m_token:

                Intent addFundsIntent1 = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                addFundsIntent1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(addFundsIntent1);


                break;
            case R.id.tvFaq:
                Intent webIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, WebScreens.class);
                webIntent.putExtra("title", AppConstants.FAQS);
                startActivity(webIntent);
                break;
            default:
                break;

        }

    }

    private void getTokenFromCash() {

        try {
            Dexter.withContext(Matchup_WinPlayGoTimeTwo.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {

                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(Matchup_WinPlayGoTimeTwo.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(Matchup_WinPlayGoTimeTwo.this);
                            double lat = llat;
                            double lon = llong;
                            try {
                                Geocoder gcd = new Geocoder(Matchup_WinPlayGoTimeTwo.this, Locale.getDefault());
                                List<Address> addresses = gcd.getFromLocation(lat, lon, 1);

                                if (addresses.size() > 0) {

                                    final String state_txt = addresses.get(0).getAdminArea();
                                    final String city_txt = addresses.get(0).getLocality();
                                    final String country_txt = addresses.get(0).getCountryName();
                                    state = state_txt;
                                    {

                                        Log.e("Address", city
                                                + state + country);

                                        JSONObject jsonObj = new JSONObject();

                                        try {
                                            jsonObj.put("city", city_txt);
                                            jsonObj.put("stateName", state_txt);
                                            jsonObj.put("stateCode", "");
                                            jsonObj.put("country", country_txt);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

//                                            obj_list_token(state_txt);
                                        if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {
                                            if (Utilities.getAge(DATA_DOB) >= 18) {
                                                Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

                                                getFinalLocationChekup();

                                            } else {
                                                showAlertBox(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                                            }


                                        } else {
                                            Utilities.showAlertBoxTrans(Matchup_WinPlayGoTimeTwo.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                                        }


                                    }
//
                                } else {


                                    Log.e("test--", "enable loction");

//                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Try Again Later");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        checkLocationPermission(getApplicationContext(), Matchup_WinPlayGoTimeTwo.this);

                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //  }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }


                    }).check();


            // finish();
        } catch (Exception e) {

        }

    }

    private void getGuestPurchaseCard(final String amount_c, final String card_h) {

        {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            try {
                Dexter.withContext(Matchup_WinPlayGoTimeTwo.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {


                                //  if (Helper.isGPSEnabled(MainActivity.this)) {
                                LocationTrack locationTrack = new LocationTrack(Matchup_WinPlayGoTimeTwo.this);
//                                if (locationTrack.canGetLocation) {
//                                    double lat = locationTrack.getLatitude();
//                                    double lon = locationTrack.getLongitude();
                                getLocationFu(Matchup_WinPlayGoTimeTwo.this);
                                double lat = llat;
                                double lon = llong;
                                try {
                                    Geocoder gcd = new Geocoder(Matchup_WinPlayGoTimeTwo.this, Locale.getDefault());
                                    List<Address> addresses = gcd.getFromLocation(lat,
                                            lon, 1);

                                    if (addresses.size() > 0) {

                                        final String state_txt = addresses.get(0).getAdminArea();
                                        final String city_txt = addresses.get(0).getLocality();
                                        final String country_txt = addresses.get(0).getCountryName();
                                        state = state_txt;
                                        {

                                            Log.e("Address", city
                                                    + state + country);

                                            JSONObject jsonObj = new JSONObject();

                                            try {
                                                jsonObj.put("city", city_txt);
                                                jsonObj.put("state", state_txt);
//                                                    jsonObj.put("stateCode", "");
                                                jsonObj.put("country", country_txt);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            obj_list(state_txt, amount_c, city_txt, country_txt, ip_address);

                                        }
//
                                    } else {
                                        Log.e("test--", "enable loction");
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            checkLocationPermission(getApplicationContext(), Matchup_WinPlayGoTimeTwo.this);

                                        }
                                        if (progressDialog != null)
                                            progressDialog.dismiss();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                }
                                //    }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                // check for permanent denial of permission
                                if (response.isPermanentlyDenied()) {
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
                                    // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                    Utilities.showAlertBoxLoc(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                                }

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                token.continuePermissionRequest();
                            }


                        }).check();


                // finish();
            } catch (Exception e) {
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        }
    }

//    GPSTracker gps;

    private void getCashPurchaseCard(final String amount_c, final String card_h, boolean b) {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        try {
            Dexter.withContext(Matchup_WinPlayGoTimeTwo.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {


                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(Matchup_WinPlayGoTimeTwo.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(Matchup_WinPlayGoTimeTwo.this);
                            double lat = llat;
                            double lon = llong;
                            try {
                                Geocoder gcd = new Geocoder(Matchup_WinPlayGoTimeTwo.this, Locale.getDefault());
                                List<Address> addresses = gcd.getFromLocation(lat,
                                        lon, 1);

                                if (addresses.size() > 0) {

                                    final String state_txt = addresses.get(0).getAdminArea();
                                    final String city_txt = addresses.get(0).getLocality();
                                    final String country_txt = addresses.get(0).getCountryName();
                                    state = state_txt;
                                    {

                                        Log.e("Address", city
                                                + state + country);

                                        JSONObject jsonObj = new JSONObject();

                                        try {
                                            jsonObj.put("city", city_txt);
                                            jsonObj.put("stateName", state_txt);
                                            jsonObj.put("stateCode", "");
                                            if (country_txt.equalsIgnoreCase("United States")) {
                                                jsonObj.put("country", "USA");
                                            } else {
                                                jsonObj.put("country", country_txt);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        AndroidNetworking.post(APIs.LOCATION_USER_VAL)
                                                .addJSONObjectBody(jsonObj) // posting json
                                                .addHeaders("sessionToken", db_sessionToken)
                                                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                                                .setPriority(Priority.MEDIUM)
                                                .build()
                                                .getAsJSONObject(new JSONObjectRequestListener() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {

                                                        if (progressDialog != null)
                                                            progressDialog.dismiss();

                                                        Log.e("***MA: Token:", response.toString());

                                                        try {
//                                                                                 WithdrawResponse withdrawResponse = new Gson().fromJson(response.toString(), WithdrawResponse.class);
//                                                                                 ContentValues cv = new ContentValues();
//                                                                                 cv.put(DB_Constants.USER_CITY, city_txt);
//                                                                                 cv.put(DB_Constants.USER_STATE, state_txt);
//                                                                                 cv.put(DB_Constants.USER_COUNTRY, country_txt);
//                                                                                 cv.put(DB_Constants.USER_TOTALCASHBALANCE, withdrawResponse.getTotalCashBalance());
//                                                                                 cv.put(DB_Constants.USER_CASHBALANCE, (withdrawResponse.getCashBalance()));
//                                                                                 cv.put(DB_Constants.USER_PROMOBALANCE, withdrawResponse.getPromoBalance());
//                                                                                 cv.put(DB_Constants.USER_TOKENBALANCE, withdrawResponse.getTokenBalance());

                                                            String Usermode = response.getString("userPlayMode");

                                                            if (Usermode.equalsIgnoreCase("cash")) {
                                                                JSONObject Loc_json = new JSONObject();

                                                                String payAmount = amount_c.replace("$", "");

                                                                if (!payAmount.equalsIgnoreCase("")) {
                                                                    try {


                                                                        Loc_json.put("amount", Integer.valueOf(payAmount));
                                                                        Loc_json.put("houseCard", card_h);
                                                                        Loc_json.put("currencyTypeIsTokens", false);
                                                                        JSONObject metrics_json = new JSONObject();
                                                                        metrics_json.put("city", city_txt);
                                                                        metrics_json.put("state", state_txt);
                                                                        metrics_json.put("country", country_txt);
                                                                        metrics_json.put("ipAddress", ip_address);
                                                                        Loc_json.put("location", metrics_json);

                                                                        List<Selection> mSelectionList_c = new ArrayList<>();

                                                                        List<String> matchups_c = new ArrayList<String>(matchup_selections_new_cash.keySet());
                                                                        List<String> pickindexes_c = new ArrayList<String>(matchup_selections_new_cash.values());


                                                                        for (int i = 0; i < matchups_c.size(); i++) {
                                                                            Selection selection = new Selection();
                                                                            selection.setMatchup(matchups_c.get(i));
                                                                            selection.setPickIndex(pickindexes_c.get(i));
                                                                            selection.setSelectedOrder(String.valueOf(a.get(i)));
                                                                            mSelectionList_c.add(selection);
                                                                        }


                                                                        // GSON
                                                                        //       if(mSelectionList_c.size()>0){
                                                                        Gson gson = new GsonBuilder().create();
                                                                        String json_cash = gson.toJson(mSelectionList_c);

                                                                        JSONArray matchup_pickindex_is_c = new JSONArray(json_cash);

                                                                        Loc_json.put("selections", matchup_pickindex_is_c);
                                                                        Loc_json.put("payType", "PROGRESSIVE");

//                                                                            }else{
//                                                                                Toast.makeText(Matchup_WinPlayGoTimeTwo.this,"empry-----------",Toast.LENGTH_LONG).show();
//
//                                                                            }

//                                                                    playbuttonJsonObject.put("selections", matchup_pickindex_is);
//                                                                    playbuttonJsonObject.put("location", "location");

                                                                        Log.e("Fi---", "" + Loc_json);
                                                                        MATCHUPS_JSON = Loc_json.toString();

//                                                                            if(progressDialog!=null)
//                                                                                progressDialog.show();

                                                                        handlePlayButton(cardTitle, amount_c, Loc_json);

                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                        if (progressDialog != null)
                                                                            progressDialog.dismiss();
                                                                    }
                                                                } else {
                                                                    if (progressDialog != null)
                                                                        progressDialog.dismiss();
                                                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Please select or enter amount");
                                                                }
                                                            } else {

                                                                if (progressDialog != null)
                                                                    progressDialog.dismiss();

                                                                showAlertBox(Matchup_WinPlayGoTimeTwo.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                                            }


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                            if (progressDialog != null)
                                                                progressDialog.dismiss();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(ANError anError) {
                                                        Log.e("js", "user----error-------" + anError);
                                                        if (anError.getErrorCode() != 0) {
                                                            Log.e("", "onError errorCode : " + anError.getErrorCode());
                                                            Log.e("", "onError errorBody : " + anError.getErrorBody());
                                                            Log.e("", "onError errorDetail : " + anError.getErrorDetail());
                                                            try {
                                                                JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                                                String au = ej.getString("message");
                                                                if (au.contains("Unauthorized")) {
                                                                    showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                                                } else {
                                                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
                                                                }


                                                            } catch (Exception e) {

                                                            }

                                                        }

                                                        if (progressDialog != null)
                                                            progressDialog.dismiss();
                                                    }
                                                });
                                    }
//
                                } else {
                                    Log.e("test--", "enable loction");
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
//                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Try Again Later");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        checkLocationPermission(getApplicationContext(), Matchup_WinPlayGoTimeTwo.this);

                                    }

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }
                            //   }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                // navigate user to app settings
//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            token.continuePermissionRequest();
                        }


                    }).check();


            // finish();
        } catch (Exception e) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    private void getTokensPurchaseCard(String amount_c, String card_h, boolean b) {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        JSONObject Loc_json = new JSONObject();

        String payAmount = amount_c.replace("$", "");

        if (!amount_c.equalsIgnoreCase("")) {
            try {

                Loc_json.put("amount", Integer.valueOf(payAmount));
                Loc_json.put("houseCard", card_h);
                Loc_json.put("currencyTypeIsTokens", b);
                JSONObject metrics_json = new JSONObject();
                metrics_json.put("city", "");
                metrics_json.put("state", "");
                metrics_json.put("country", "");
                metrics_json.put("ipAddress", ip_address);
                Loc_json.put("location", metrics_json);

                List<Selection> mSelectionList = new ArrayList<>();
                List<String> matchups = new ArrayList<String>(matchup_selections_new.keySet());
                List<String> pickindexes = new ArrayList<String>(matchup_selections_new.values());
                for (int i = 0; i < matchups.size(); i++) {
                    Selection selection = new Selection();
                    selection.setMatchup(matchups.get(i));
                    selection.setPickIndex(pickindexes.get(i));
                    mSelectionList.add(selection);
                }
                Gson gson = new GsonBuilder().create();
                String json = gson.toJson(mSelectionList);
                JSONArray matchup_pickindex_is = new JSONArray(json);
                Loc_json.put("selections", matchup_pickindex_is);

                Log.e("Fi---", "" + Loc_json);
                MATCHUPS_JSON = Loc_json.toString();

                if (progressDialog != null)
                    progressDialog.dismiss();

                handlePlayButton(cardTitle, amount_c, Loc_json);

            } catch (JSONException e) {
                e.printStackTrace();
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        } else {
            if (progressDialog != null)
                progressDialog.dismiss();
            Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Please select amount");
        }


    }


    private void handlePlayButton(String cardTitle, String amount_c, JSONObject loc_json) {
        Log.e("1713-", "data" + db_sessionToken);
        MATCHUPS_CARDTITLE = cardTitle;
        MATCHUPS_JSON = loc_json.toString();
        if (db_sessionToken != null) { // Logged in user


            if (db_role.equalsIgnoreCase("cash")) { //Cash


                if (support_token == true && support_cash == false) {
                    MATCHUPS_CARDAMOUNT = amount_c;
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    Intent checkoutIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, CheckoutActivity.class);
                    checkoutIntent.putExtra("place_p", "MT");
//                checkoutIntent.putExtra("payAmount", Integer.parseInt("10"));
//                checkoutIntent.putExtra("wagerName", cardTitle);
//                checkoutIntent.putExtra("cardjson", loc_json.toString());
                    startActivity(checkoutIntent);

                } else {
                    String payAmount = amount_c.replace("$", "");
                    MATCHUPS_CARDAMOUNT = payAmount;
                    //1. show are you sure popup
                    //2.check location
                    //3. if location available -- Nav user to Check out as below
                    if (progressDialog != null)
                        progressDialog.dismiss();

                    Intent checkoutIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, CheckoutActivity.class);
                    checkoutIntent.putExtra("place_p", "MT");
//                checkoutIntent.putExtra("wagerName", cardTitle);
//                checkoutIntent.putExtra("payAmount", Integer.parseInt(payAmount));
//                checkoutIntent.putExtra("cardjson", loc_json.toString());
                    startActivity(checkoutIntent);
                }

            } else if (db_role.equalsIgnoreCase("tokens")) { //Cash // Tokens

                MATCHUPS_CARDAMOUNT = amount_c;
                if (progressDialog != null)
                    progressDialog.dismiss();
                Intent checkoutIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, CheckoutActivity.class);
                checkoutIntent.putExtra("place_p", "MT");
//                checkoutIntent.putExtra("payAmount", Integer.parseInt("10"));
//                checkoutIntent.putExtra("wagerName", cardTitle);
//                checkoutIntent.putExtra("cardjson", loc_json.toString());
                startActivity(checkoutIntent);
            }
        }
        Log.e("1746--", "data" + db_sessionToken);
//        else { // Guest user


//
//            Intent loginIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, Signup.class);
//            startActivity(loginIntent);
//            Intent loginIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, Login.class);
//            startActivityForResult(loginIntent, 1111);
//        }

    }

    String city, state, country;


    private void getLocationEnable(final String amount_c, final String h_card, final boolean type) {

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        final String ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        try {
            Dexter.withContext(Matchup_WinPlayGoTimeTwo.this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {


                            //  if (Helper.isGPSEnabled(MainActivity.this)) {
                            LocationTrack locationTrack = new LocationTrack(Matchup_WinPlayGoTimeTwo.this);
//                            if (locationTrack.canGetLocation) {
//                                double lat = locationTrack.getLatitude();
//                                double lon = locationTrack.getLongitude();
                            getLocationFu(Matchup_WinPlayGoTimeTwo.this);
                            double lat = llat;
                            double lon = llong;
                            try {
                                Geocoder gcd = new Geocoder(Matchup_WinPlayGoTimeTwo.this, Locale.getDefault());
                                List<Address> addresses = gcd.getFromLocation(lat,
                                        lon, 1);

                                if (addresses.size() > 0) {

                                    final String state_txt = addresses.get(0).getAdminArea();
                                    final String city_txt = addresses.get(0).getLocality();
                                    final String country_txt = addresses.get(0).getCountryName();
                                    state = state_txt;
                                    {

                                        Log.e("Address", city
                                                + state + country);

                                        JSONObject jsonObj = new JSONObject();

                                        try {
                                            jsonObj.put("city", city_txt);
                                            jsonObj.put("state", state_txt);
                                            if (country_txt.equalsIgnoreCase("United States")) {
                                                jsonObj.put("country", "USA");
                                            } else {
                                                jsonObj.put("country", country_txt);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        AndroidNetworking.post(APIs.LOCATION_USER_VAL)
                                                .addJSONObjectBody(jsonObj) // posting json
                                                .addHeaders("sessionToken", db_sessionToken)
                                                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                                                .setPriority(Priority.MEDIUM)
                                                .build()
                                                .getAsString(new StringRequestListener() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        Log.e("***Location: ", response.toString());
                                                        if (response.equalsIgnoreCase("true")) {

                                                            ContentValues cv = new ContentValues();

                                                            cv.put(DB_Constants.USER_CITY, city_txt);
                                                            cv.put(DB_Constants.USER_STATE, state_txt);
                                                            cv.put(DB_Constants.USER_COUNTRY, country_txt);

                                                            mydb.updateUser(cv);

                                                            JSONObject Loc_json = new JSONObject();

                                                            String payAmount = amount_c.replace("$", "");

                                                            if (!payAmount.equalsIgnoreCase("")) {
                                                                try {

                                                                    Loc_json.put("amount", Integer.valueOf(payAmount));
                                                                    Loc_json.put("houseCard", h_card);
                                                                    Loc_json.put("currencyTypeIsTokens", type);
                                                                    JSONObject metrics_json = new JSONObject();
                                                                    metrics_json.put("city", city_txt);
                                                                    metrics_json.put("state", state_txt);
                                                                    metrics_json.put("country", country_txt);
                                                                    metrics_json.put("ipAddress", ip_address);
                                                                    Loc_json.put("location", metrics_json);

                                                                    List<Selection> mSelectionList = new ArrayList<>();
                                                                    List<String> matchups = new ArrayList<String>(matchup_selections_new.keySet());
                                                                    List<String> pickindexes = new ArrayList<String>(matchup_selections_new.values());
                                                                    for (int i = 0; i < matchups.size(); i++) {
                                                                        Selection selection = new Selection();
                                                                        selection.setMatchup(matchups.get(i));
                                                                        selection.setPickIndex(pickindexes.get(i));
                                                                        mSelectionList.add(selection);
                                                                    }
                                                                    Gson gson = new GsonBuilder().create();
                                                                    String json = gson.toJson(mSelectionList);
                                                                    JSONArray matchup_pickindex_is = new JSONArray(json);


//                                                                    playbuttonJsonObject.put("selections", matchup_pickindex_is);
//                                                                    playbuttonJsonObject.put("location", "location");
                                                                    Loc_json.put("selections", matchup_pickindex_is);

                                                                    Log.e("Fi---", "" + Loc_json);
                                                                    MATCHUPS_JSON = Loc_json.toString();

                                                                    handlePlayButton(cardTitle, amount_c, Loc_json);

                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    if (progressDialog != null)
                                                                        progressDialog.dismiss();

                                                                }
                                                            } else {
                                                                if (progressDialog != null)
                                                                    progressDialog.dismiss();

                                                                Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Please select or enter amount");
                                                            }


//                                                                login_json.put("metrics", metrics_json);
                                                        } else {
                                                            if (progressDialog != null)
                                                                progressDialog.dismiss();

//                                                                        Toast.makeText(getApplicationContext(), " No address associated with hostname", Toast.LENGTH_LONG).show();
                                                            Utilities.showAlertBox(Matchup_WinPlayGoTimeTwo.this, getString(R.string.location_msg) + " " + state, getString(R.string.location_msg));
                                                        }

                                                    }

                                                    @Override
                                                    public void onError(ANError anError) {
                                                        Log.e("js", "user----error-------" + anError);
                                                        if (progressDialog != null)
                                                            progressDialog.dismiss();
                                                        try {
                                                            JSONObject ej = new JSONObject(anError.getErrorBody());
//                            Utilities.showToast(getActivity(), ej.getString("message"));
//                            if (text.toString().contains(LINK))
                                                            String au = ej.getString("message");
                                                            if (au.contains("Unauthorized")) {
                                                                showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                                            } else {
                                                                Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
                                                            }


                                                        } catch (Exception e) {

                                                        }


                                                    }
                                                });
                                    }
//
                                } else {
                                    Log.e("test--", "enable loction");
                                    if (progressDialog != null)
                                        progressDialog.dismiss();
//                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Try Again Later");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        checkLocationPermission(getApplicationContext(), Matchup_WinPlayGoTimeTwo.this);
//                    getAddress();

                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //     }
                                      /*  } else {
                                            Toast.makeText(MainActivity.this, "Location service not enabled", Toast.LENGTH_LONG).show();
                                        }*/


                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                // navigate user to app settings
                                if (progressDialog != null)
                                    progressDialog.dismiss();

//                                        Toast.makeText(getApplicationContext(), "Location service not enabled", Toast.LENGTH_LONG).show();
                                Utilities.showAlertBoxLoc(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.enable_location_title), getResources().getString(R.string.enable_location_msg));
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                            if (progressDialog != null)
                                progressDialog.dismiss();

                        }


                    }).check();


            // finish();
        } catch (Exception e) {

        }
    }

    public String getJson() {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.state_rules);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    void obj_list(String state_txt, String amount_c, String city_txt, String country_txt, String ip_address) {
        MATCHUPS_CARDTITLE = cardTitle;

        try {
            JSONArray array = new JSONArray(getJson());
            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                String State = object.getString("stateName");
                if (State.equalsIgnoreCase(state_txt)) {
                    String cashValue = object.getString("cash");
                    System.out.println(cashValue);


                    if (cashValue.equalsIgnoreCase("YES")) {

                        JSONObject Loc_json = new JSONObject();

//                        String payAmount = amount_c.replace("$", "");

                        String payAmount = amount_c.replace("$", "");
                        MATCHUPS_CARDAMOUNT = payAmount;
                        if (!payAmount.equalsIgnoreCase("")) {
                            try {

                                Loc_json.put("amount", Integer.valueOf(payAmount));
                                Loc_json.put("houseCard", card_h);
                                Loc_json.put("currencyTypeIsTokens", false);
                                JSONObject metrics_json = new JSONObject();
                                metrics_json.put("city", city_txt);
                                metrics_json.put("state", state_txt);
                                metrics_json.put("country", country_txt);
                                metrics_json.put("ipAddress", ip_address);
                                Loc_json.put("location", metrics_json);

                                List<Selection> mSelectionList = new ArrayList<>();
                                List<String> matchups = new ArrayList<String>(matchup_selections_new.keySet());
                                List<String> pickindexes = new ArrayList<String>(matchup_selections_new.values());
                                for (int i = 0; i < matchups.size(); i++) {
                                    Selection selection = new Selection();
                                    selection.setMatchup(matchups.get(i));
                                    selection.setPickIndex(pickindexes.get(i));
                                    mSelectionList.add(selection);
                                }
                                Gson gson = new GsonBuilder().create();
                                String json = gson.toJson(mSelectionList);
                                JSONArray matchup_pickindex_is = new JSONArray(json);

                                Loc_json.put("selections", matchup_pickindex_is);

                                Log.e("Fi---", "" + Loc_json);
                                MATCHUPS_JSON = Loc_json.toString();
//                                MATCHUPS_CARDAMOUNT = amount_c;
                                MATCHUPS_CARDTITLE = cardTitle;

                                if (progressDialog != null)
                                    progressDialog.dismiss();

                                Intent cash_intent = new Intent(Matchup_WinPlayGoTimeTwo.this, PlayWithCash.class);
//                                cash_intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                                cash_intent.putExtra("card_guest", card_h);
                                cash_intent.putExtra("card_city", city_txt);
                                cash_intent.putExtra("card_state", state_txt);
                                cash_intent.putExtra("card_country", country_txt);
                                cash_intent.putExtra("place_p", "M");
                                startActivity(cash_intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }
                        } else {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Please select or enter amount");
                        }


//                                                                login_json.put("metrics", metrics_json);
                    } else {
//                                                                        Toast.makeText(getApplicationContext(), " No address associated with hostname", Toast.LENGTH_LONG).show();
//                                                                    Utilities.showAlertBox(Matchup_WinPlayGoTimeTwo.this, getString(R.string.location_msg) + " " + state, getString(R.string.location_msg));
                        JSONObject Loc_json = new JSONObject();

//                        String payAmount = "10";
//                        String payAmount = amt_edt.getText().toString().trim();
                        String payAmount = amount_c.replace("$", "");
                        MATCHUPS_CARDAMOUNT = payAmount;

                        if (!payAmount.equalsIgnoreCase("")) {


                            try {
                                Loc_json.put("amount", Integer.valueOf(payAmount));
                                Loc_json.put("houseCard", card_h);
                                Loc_json.put("currencyTypeIsTokens", true);
                                JSONObject metrics_json = new JSONObject();
                                metrics_json.put("city", "");
                                metrics_json.put("state", "");
                                metrics_json.put("country", "");
                                metrics_json.put("ipAddress", ip_address);
                                Loc_json.put("location", metrics_json);

                                List<Selection> mSelectionList = new ArrayList<>();
                                List<String> matchups = new ArrayList<String>(matchup_selections_new.keySet());
                                List<String> pickindexes = new ArrayList<String>(matchup_selections_new.values());
                                for (int j = 0; j < matchups.size(); j++) {
                                    Selection selection = new Selection();
                                    selection.setMatchup(matchups.get(j));
                                    selection.setPickIndex(pickindexes.get(j));
                                    mSelectionList.add(selection);
                                }
                                Gson gson = new GsonBuilder().create();
                                String json = gson.toJson(mSelectionList);
                                JSONArray matchup_pickindex_is = new JSONArray(json);

                                Loc_json.put("selections", matchup_pickindex_is);

                                Log.e("Fi---", "" + Loc_json);
                                MATCHUPS_JSON = Loc_json.toString();
                                MATCHUPS_CARDTITLE = cardTitle;

                                if (progressDialog != null)
                                    progressDialog.dismiss();

                                Intent token_intent = new Intent(Matchup_WinPlayGoTimeTwo.this, PlayWithCash.class);
//                                Intent token_intent = new Intent(Matchup_WinPlayGoTimeTwo.this, PlayWithTokens.class);
                                token_intent.putExtra("card_guest", card_h);
                                token_intent.putExtra("place_p", "M");
                                startActivity(token_intent);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }

                        }

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }


    //Tokens
    void obj_list_token(String state_txt) {

        try {
            JSONArray array = new JSONArray(getJson());
            for (int k = 0; k < array.length(); k++) {
                JSONObject object = array.getJSONObject(k);
                String State = object.getString("State");
                if (State.equalsIgnoreCase(state_txt)) {
                    String cashValue = object.getString("Cash");
                    System.out.println(cashValue);

                    if (cashValue.equalsIgnoreCase("YES")) {
                        if (DATA_DOB != null && !TextUtils.isEmpty(DATA_DOB) && !DATA_DOB.equalsIgnoreCase("null")) {

                            if (Utilities.getAge(DATA_DOB) >= 18) {
                                Log.e("524--", DATA_DOB + "-----" + Utilities.getAge(DATA_DOB));

//                                getFinalLocationChekup();
                                Intent addFundsIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(addFundsIntent);

                            } else {
                                showAlertBox(Matchup_WinPlayGoTimeTwo.this, getResources().getString(R.string.dob_title), getResources().getString(R.string.dob_msg));
                            }


                        } else {

                            Utilities.showAlertBoxTrans(Matchup_WinPlayGoTimeTwo.this, getString(R.string.token_to_cash_title), getString(R.string.token_to_cash_msg));
                        }
                    } else {
                        showAlertBox(Matchup_WinPlayGoTimeTwo.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getFinalLocationChekup() {
        {


            //  if (Helper.isGPSEnabled(MainActivity.this)) {
            LocationTrack locationTrack = new LocationTrack(Matchup_WinPlayGoTimeTwo.this);
//            if (locationTrack.canGetLocation) {
//                double lat = locationTrack.getLatitude();
//                double lon = locationTrack.getLongitude();
            getLocationFu(Matchup_WinPlayGoTimeTwo.this);
            double lat = llat;
            double lon = llong;
            try {
                Geocoder gcd = new Geocoder(Matchup_WinPlayGoTimeTwo.this, Locale.getDefault());
                List<Address> addresses = gcd.getFromLocation(lat,
                        lon, 1);

                if (addresses.size() > 0) {

                    final String state_txt = addresses.get(0).getAdminArea();
                    final String city_txt = addresses.get(0).getLocality();
                    final String country_txt = addresses.get(0).getCountryName();
                    state = state_txt;
                    {

                        Log.e("Address", city
                                + state + country);

                        JSONObject jsonObj = new JSONObject();

                        try {
                            jsonObj.put("city", city_txt);
                            jsonObj.put("stateName", state_txt);
                            jsonObj.put("stateCode", "");
                            if (country_txt.equalsIgnoreCase("United States")) {
                                jsonObj.put("country", "USA");
                            } else {
                                jsonObj.put("country", country_txt);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AndroidNetworking.post(APIs.LOCATION_USER_VAL)
                                .addJSONObjectBody(jsonObj) // posting json
                                .addHeaders("sessionToken", db_sessionToken)
                                .addHeaders("Authorization", "bearer " + NEWTOKEN)

                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {


                                        Log.e("***MA: Token:", response.toString());

                                        try {
                                            String Usermode = response.getString("userPlayMode");

                                            if (Usermode.equalsIgnoreCase("cash")) {
                                                Intent addFundsIntent = new Intent(Matchup_WinPlayGoTimeTwo.this, AddFunds.class);
                                                addFundsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                startActivity(addFundsIntent);

                                            } else {
                                                showAlertBox(Matchup_WinPlayGoTimeTwo.this, getString(R.string.location_title) + " " + state_txt, getString(R.string.location_msg));
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        Log.e("js", "Login----error-------" + anError);


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
                                                    showAlertBoxAU(Matchup_WinPlayGoTimeTwo.this, "Error", "Session has expired,please try logining again");
                                                } else {
                                                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, ej.getString("message"));
                                                }


                                            } catch (Exception e) {

                                            }

                                        } else {
                                            Log.d("", "onError errorDetail  0: " + anError.getErrorDetail());
                                        }

                                    }
                                });
                    }
//
                } else {
                    Log.e("test--", "enable loction");
                    Utilities.showToast(Matchup_WinPlayGoTimeTwo.this, "Location was not detected ");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        checkLocationPermission(getApplicationContext(), Matchup_WinPlayGoTimeTwo.this);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   }


        }
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

                dialog.dismiss();


            }
        });

    }

    public static void showAlertBoxTwo(final Context context, String title, String message) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alerts_two);
        Window window = dialog.getWindow();
        if (window != null) {
            dialog.getWindow().setLayout(((Utilities.getWidth(context) / 100) * 94), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.show();
            dialog.setCancelable(false);
        }

        TextView alert_title = dialog.findViewById(R.id.alert_title);
        TextView alert_msg = dialog.findViewById(R.id.alert_msg);

        alert_title.setText(title);
        alert_msg.setText(message);
        TextView alert_ok = dialog.findViewById(R.id.alert_ok);
        alert_ok.setText("OK");
        // if decline button is clicked, close the custom dialog
        alert_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                ((Activity) context).finish();


            }
        });

        TextView alert_cancel = dialog.findViewById(R.id.alert_cancel);
        alert_cancel.setText("CANCEL");

        // if decline button is clicked, close the custom dialog
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public void tenn() {
        picksList.clear();

        for (int i = 0; i < jpicks.length(); i++) {

            JSONObject jj = null;
            try {
                jj = jpicks.getJSONObject(i);
                Log.e("1371----", jj + "");
                Picks mPicks = new Picks();
                mPicks.setPicks(jj.getString("picks"));
                mPicks.setMultiplier("x" + jj.getString("multiplier"));

                double a = Double.parseDouble(jj.getString("multiplier"));
                double m = (a * 10);
                mPicks.setWinpayout(String.valueOf(m));
                picksList.add(mPicks);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (picksAdapter != null) {
            picksAdapter.notifyDataSetChanged();
        }
        Log.e("1371----", picksList.size() + "");
        amt_txt.setVisibility(View.GONE);
        if (db_role == null || db_role.equalsIgnoreCase("cash")) {

            if (support_cash == false && support_token == true) {
                amt_edt.setVisibility(View.GONE);
            } else {
                amt_edt.setVisibility(View.VISIBLE);
            }

        } else {
            amt_edt.setVisibility(View.GONE);
        }
        amt_edt.setText(tenx.getText().toString().trim());
        amt_edt.setSelection(twox.getText().toString().length());

        ll_2.setBackgroundResource(R.drawable.btn_gray_bg);
        twox.setTextColor(getResources().getColor(R.color.white));
        iv_2.setColorFilter(iv_2.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ll_5.setBackgroundResource(R.drawable.btn_gray_bg);
        fivex.setTextColor(getResources().getColor(R.color.white));
        iv_5.setColorFilter(iv_5.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ll_10.setBackgroundResource(R.drawable.btn_white_bg);
        tenx.setTextColor(getResources().getColor(R.color.dark_gray));
        iv_10.setColorFilter(iv_10.getContext().getResources().getColor(R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);

        ll_15.setBackgroundResource(R.drawable.btn_gray_bg);
        fivteenx.setTextColor(getResources().getColor(R.color.white));
        iv_15.setColorFilter(iv_15.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ll_20.setBackgroundResource(R.drawable.btn_gray_bg);
        twentyx.setTextColor(getResources().getColor(R.color.white));
        iv_20.setColorFilter(iv_20.getContext().getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myshadow = new View.DragShadowBuilder(v);
            v.startDrag(data, myshadow, v, 0);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onDrag(View v1, DragEvent event) {

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View v =(View) event.getLocalState();
                ViewGroup child=(ViewGroup)v.getParent();
                child.bringChildToFront(v);
                RelativeLayout container = (RelativeLayout) v;
                container.bringChildToFront(v);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            default:
                break;
        }
        return true;

    }


    public class SpinnerAdapter extends ArrayAdapter<String> {

        private Context ctx;
        private String[] contentArray;
        private String spin_role;

        public SpinnerAdapter(Context context, int resource, String[] objects, String role) {
//            super(context, R.layout.spinner_value_layout, objects);
            super(context, R.layout.spinner_value_layout, R.id.spinner_txt1, objects);

            this.ctx = context;
            this.contentArray = objects;
            this.spin_role = role;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_value_layout, parent, false);

            }
//            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View row = inflater.inflate(R.layout.spinner_value_layout, parent, false);

            TextView textspin = convertView.findViewById(R.id.spinner_txt1);
//
        /*    if (spin_role.equalsIgnoreCase("tokens")) {
                textspin.setText(contentArray[position]);
                textspin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
            } else {
                textspin.setText("$" + contentArray[position]);

            }*/
            if (db_role == null || db_role.equalsIgnoreCase("cash")) {
                textspin.setText(contentArray[position]);
//                textspin.setText("$" + contentArray[position]);
            } else {
                textspin.setText(contentArray[position]);
                textspin.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
            }

//            textspin.setText("$" + contentArray[position]);

            return convertView;
        }
     /*   @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.spinner_value_layout, parent, false);

            }

            TextView textView = (TextView) convertView.findViewById(R.id.spinner_value);

            if (db_role == null || db_role.equalsIgnoreCase("tokens")) {
                textView.setText(contentArray[position]);
                amount_token.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);
            } else {
                textView.setText("$" + contentArray[position]);

            }


            return convertView;

        }*/

    }
}

