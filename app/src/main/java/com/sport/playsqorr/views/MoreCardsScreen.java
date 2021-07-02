package com.sport.playsqorr.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.MyCardsPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MoreCardsScreen extends AppCompatActivity {

    MyCardsPojo mmp;
    public View mView;
    public TextView mIdView, tvMatchUpType, tvCardwin, tvStartTime, mywin_amount, count_txt, tvPlus, toolbar_title_x;
    public LinearLayout s_ll, s_won, llLive;
    // public final TextView mContentView;
    private ImageView player1Img, player2Img, playerFrame1, playerFrame2, ivMatchUp, ivOverUnder;
    public MyCardsPojo mItem;
    private View cardColor;


    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    String getcardID, cardTitle, Legue_id;
    public static String player_id_m;
    String home_s;
    public static String MATCHUPS_CARD_DATE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_cards_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("morecards_info"))
                //mmp = bundle.getSerializable("cardspojo");

                mmp = (MyCardsPojo) bundle.getSerializable("morecards_info");
            Log.e("myd1111-", mmp.toString() + "");

            if (bundle.containsKey("cardid"))
                getcardID = bundle.getString("cardid");

            if (bundle.containsKey("cardid_title"))
                cardTitle = bundle.getString("cardid_title");
            if (bundle.containsKey("cardid_color1"))
                Legue_id = bundle.getString("cardid_color1");
            if (bundle.containsKey("place"))
                home_s = bundle.getString("place");
            if (bundle.containsKey("cardid_date"))
                MATCHUPS_CARD_DATE = getString(R.string.game_start) + " " + bundle.getString("cardid_date");
            if (bundle.containsKey("playerid_m"))
                player_id_m = bundle.getString("playerid_m");


        }
        toolbar_title_x = findViewById(R.id.toolbar_title_x);

        toolbar_title_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    RecyclerView recyl_match1;

    @Override
    protected void onStart() {
        super.onStart();

        recyl_match1 = findViewById(R.id.multi_cards);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MoreCardsScreen.this);
        recyl_match1.setLayoutManager(mLayoutManager);
        recyl_match1.setItemAnimator(new DefaultItemAnimator());


//        mIdView = findViewById(R.id.tvCardTitle);
//        tvMatchUpType = findViewById(R.id.tvMatchUpType);
//        //mContentView = (TextView) findViewById(R.id.content);
//        player1Img = findViewById(R.id.player1Img);
//        player2Img = findViewById(R.id.player2Img);
//        playerFrame1 = findViewById(R.id.fimg1);
//        playerFrame2 = findViewById(R.id.fimg2);
//        cardColor = findViewById(R.id.cardColor);
//        tvCardwin = findViewById(R.id.tvCardwin);
//        s_ll = findViewById(R.id.stas);
//        s_won = findViewById(R.id.wonstas);
//        mywin_amount = findViewById(R.id.mywins);
//        count_txt = findViewById(R.id.count_txt);
//        tvStartTime = findViewById(R.id.tvStartTime);
//        ivOverUnder = findViewById(R.id.ivOverUnder);
//        ivMatchUp = findViewById(R.id.ivMatchUp);
//        tvPlus = findViewById(R.id.tvPlus);
//        llLive = findViewById(R.id.llLive);


        list = findViewById(R.id.mList);


        getCardsList(mmp);
    }

    private void getCardsList(MyCardsPojo mmp) {

        toolbar_title_x.setText(cardTitle);

        List<String> kk = new ArrayList();
        arrayList = new ArrayList<String>();

        for (int i = 0; i < mmp.getPlayerCardIds().size(); i++) {

            kk.add(mmp.getPlayerCardIds().get(i));


        }


//        MoreCards_Adapter adapter = new MoreCards_Adapter(mmp, kk, MoreCardsScreen.this);
//        recyl_match1.setAdapter(adapter);

        for (int j = 0; j < kk.size(); j++) {
            //   mIdView.setTag(kk.get(j));
            //     mIdView.setText(mmp.getCardTitle());
            Log.e("i0--------", kk.get(j) + "");
            arrayList.add(kk.get(j));
        }

        final String leagueTime = mmp.getStartTime();

        if (leagueTime != null && !leagueTime.equals("")) {
            String formatTimeDiff = getTimeDiff(leagueTime);
            if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
//                // tvStartTime.setText(formatTimeDiff);
                // tvStartTime.setVisibility(View.VISIBLE);
            } else {
                // tvStartTime.setVisibility(View.GONE);
            }
        } else {
            // tvStartTime.setVisibility(View.GONE);
        }

        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        UsersAdapter adapter = new UsersAdapter(this, arrayList);

        // Here, you set the data in your ListView
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Toast.makeText(MoreCardsScreen.this, selectedItem + "--Kayn", Toast.LENGTH_LONG).show();
                Intent matchup = new Intent(MoreCardsScreen.this, MatchupScreen.class);
                matchup.putExtra("home", "1");
                matchup.putExtra("cardid", getcardID);
                matchup.putExtra("cardid_title", cardTitle);
                matchup.putExtra("cardid_color1", Legue_id);
                matchup.putExtra("place", "homecards");
                matchup.putExtra("playerid_m", selectedItem);
                //kalyan

                //     Toast.makeText(context, "k---" + all_home_cards.getPlayerCardIds().get(0), Toast.LENGTH_LONG).show();
                if (leagueTime != null && !leagueTime.equals("")) {
                    String formatTimeDiff = getTimeDiff(leagueTime);
                    if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                        // tvStartTime.setText(formatTimeDiff);
                        matchup.putExtra("cardid_date", formatTimeDiff);
                    }
                } else {
                    matchup.putExtra("cardid_date", "");

                }

                startActivity(matchup);

            }
        });


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

    public class UsersAdapter extends ArrayAdapter<String> {


        public UsersAdapter(Context context, ArrayList<String> users) {
            super(context, 0, users);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String user = getItem(position);


            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_morecards, parent, false);
            }
            // Lookup view for data population
            TextView tvCardTitle = convertView.findViewById(R.id.tvCardTitle);
            TextView tvCardTitleCount = convertView.findViewById(R.id.tvCardTitleCount);
            player1Img = convertView.findViewById(R.id.player1Img);
            player2Img = convertView.findViewById(R.id.player2Img);
            LinearLayout llTotal = convertView.findViewById(R.id.llTotal);
            tvMatchUpType = convertView.findViewById(R.id.tvMatchUpType);
            tvStartTime = convertView.findViewById(R.id.tvStartTime);
            cardColor = convertView.findViewById(R.id.cardColor);
            llLive = convertView.findViewById(R.id.llLive);
            ivOverUnder = convertView.findViewById(R.id.ivOverUnder);
            ivMatchUp = convertView.findViewById(R.id.ivMatchUp);
            tvPlus = convertView.findViewById(R.id.tvPlus);
            // Populate the data into the template view using the data object
            tvCardTitle.setText(mmp.getCardTitle() + "");
            tvCardTitleCount.setText("#"+position);

            // Return the completed view to render on screen
            return convertView;
        }
    }


    public class MoreCards_Adapter extends RecyclerView.Adapter<MoreCards_Adapter.ViewHolder> {

        MyCardsPojo mp;
        Activity act;
        List<String> d;

        public MoreCards_Adapter(MyCardsPojo mmp, List<String> kk, MoreCardsScreen moreCardsScreen) {
            this.mp = mmp;
            this.d = kk;
            this.act = moreCardsScreen;
        }

        @NonNull
        @Override
        public MoreCards_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_mycards_s, parent, false);


            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull MoreCards_Adapter.ViewHolder holder, int position) {


            for (int i = 0; i < d.size(); i++) {
                Toast.makeText(act, d.get(i) + "", Toast.LENGTH_LONG).show();

            }
            holder.tvCardTitle.setText(mp.getCardTitle());
        }

        @Override
        public int getItemCount() {
            return 0;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            private ImageView player1Img, player2Img, ivMatchUp, ivOverUnder;
            LinearLayout llTotal, llLive;
            private TextView tvCardTitle, tvMatchUpType, tvStartTime, tvPlus;
            private View cardColor;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                tvCardTitle = view.findViewById(R.id.tvCardTitle);
                player1Img = view.findViewById(R.id.player1Img);
                player2Img = view.findViewById(R.id.player2Img);
                llTotal = view.findViewById(R.id.llTotal);
                tvMatchUpType = view.findViewById(R.id.tvMatchUpType);
                tvStartTime = view.findViewById(R.id.tvStartTime);
                cardColor = view.findViewById(R.id.cardColor);
                llLive = view.findViewById(R.id.llLive);
                ivOverUnder = view.findViewById(R.id.ivOverUnder);
                ivMatchUp = view.findViewById(R.id.ivMatchUp);
                tvPlus = view.findViewById(R.id.tvPlus);


            }
        }
    }


}
