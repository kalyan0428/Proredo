package com.sport.playsqorr.views;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.MyCardsPojo;
import com.sport.playsqorr.utilities.BitmapUtils;
import com.sport.playsqorr.utilities.PathParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomeCards_MoreScreen extends AppCompatActivity implements View.OnClickListener {

    //    RecyclerView cards_cycle;
    MyCardsPojo mmp;
    public View mView;

    public MyCardsPojo mItem;


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
        setContentView(R.layout.activity_my_cards__more_screen);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("morecards_info"))
                //mmp = bundle.getSerializable("cardspojo");

                mmp = (MyCardsPojo) bundle.getSerializable("morecards_info");

            if (bundle.containsKey("cardid"))
                getcardID = bundle.getString("cardid");


            Log.d("getcardID", getcardID + "--" + cardTitle);
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
        initt();
    }

    private void initt() {

        TextView toolbar_title_x = findViewById(R.id.toolbar_title_x);
        toolbar_title_x.setText("" + mmp.getCardTitle());
        toolbar_title_x.setOnClickListener(this);

        list = findViewById(R.id.list_morecards);
//        cards_cycle.setLayoutManager(new LinearLayoutManager(this));
//        cards_cycle.setItemAnimator(new DefaultItemAnimator());

        getCardsList(mmp);

//        if (myCardsPojo_u.size() <= 0) {
//
//        } else {
//
//            MoreMyCards_Adapter adapter = new MoreMyCards_Adapter(myCardsPojo_u, this);
//            cards_cycle.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_title_x:
                finish();
                break;

        }
    }

    private void getCardsList(final MyCardsPojo mmp) {


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
            String formatTimeDiff = "";// getTimeDiff(leagueTime);
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
//        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        UsersAdapter adapter = new UsersAdapter(this, arrayList, mmp);

        // Here, you set the data in your ListView
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
//                Toast.makeText(MyCards_MoreScreen.this, selectedItem + "--Kayn", Toast.LENGTH_LONG).show();

                if (mmp.getCardType().equalsIgnoreCase("PLAY TAC TOE")) {
                    Intent matchup = new Intent(HomeCards_MoreScreen.this, PlayPickGo_MatchupScreen.class);
                    matchup.putExtra("home", "1");
                    matchup.putExtra("cardid", getcardID);
                    matchup.putExtra("cardid_title", cardTitle);
                    matchup.putExtra("cardid_color1", Legue_id);
                    matchup.putExtra("place", "homecards");
                    matchup.putExtra("playerid_m", selectedItem);
                    matchup.putExtra("position_data", true);                    //kalyan

                    //     Toast.makeText(context, "k---" + all_home_cards.getPlayerCardIds().get(0), Toast.LENGTH_LONG).show();
                    if (leagueTime != null && !leagueTime.equals("")) {
                        String formatTimeDiff = "";//getTimeDiff(leagueTime);
                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                            // tvStartTime.setText(formatTimeDiff);
                            matchup.putExtra("cardid_date", formatTimeDiff);
                        }
                    } else {
                        matchup.putExtra("cardid_date", "");

                    }

                    startActivity(matchup);
                } else {
                    Intent matchup = new Intent(HomeCards_MoreScreen.this, MoreCards_MatchupScreen.class);
                    matchup.putExtra("home", "1");
                    matchup.putExtra("cardid", getcardID);
                    matchup.putExtra("cardid_title", cardTitle);
                    matchup.putExtra("cardid_color1", Legue_id);
                    matchup.putExtra("place", "homecards");
                    matchup.putExtra("playerid_m", selectedItem);
                    //kalyan

                    //     Toast.makeText(context, "k---" + all_home_cards.getPlayerCardIds().get(0), Toast.LENGTH_LONG).show();
                    if (leagueTime != null && !leagueTime.equals("")) {
                        String formatTimeDiff = "";//getTimeDiff(leagueTime);
                        if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                            // tvStartTime.setText(formatTimeDiff);
                            matchup.putExtra("cardid_date", formatTimeDiff);
                        }
                    } else {
                        matchup.putExtra("cardid_date", "");

                    }

                    startActivity(matchup);
                }


            }
        });


    }


    public static class UsersAdapter extends ArrayAdapter<String> {
        TextView tvCardTitleCount, tvCardTitle;
        public TextView mIdView, tvMatchUpType, tvCardwin, tvStartTime, mywin_amount, count_txt, tvPlus, toolbar_title_x;
        //        public LinearLayout s_ll, s_won, llLive;
        // public final TextView mContentView;
        public LinearLayout s_ll, s_won, llLive, stas_count;

        private ImageView player1Img, player2Img, ivMatchUp, ivOverUnder;
        private View cardColor;

        static Context cxt;

        MyCardsPojo mmp;
        ArrayList<String> users;

        public UsersAdapter(Context context, ArrayList<String> users, MyCardsPojo mmp) {
            super(context, 0, users);
            this.cxt = context;
            this.mmp = mmp;
            this.users = users;
        }


        public Path resizePath(Path path, float width, float height) {
            RectF bounds = new RectF(0, 0, width, height);
            Path resizedPath = new Path(path);
            RectF src = new RectF();
            resizedPath.computeBounds(src, true);

            Matrix resizeMatrix = new Matrix();
            resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
            resizedPath.transform(resizeMatrix);

            return resizedPath;
        }

        public Bitmap convertTParellelogram(Bitmap src, String type) {
            Bitmap typex;
            if (type.equals("pare")) {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, cxt);
            } else if (type.equals("xxx")) {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, cxt);
            } else {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, cxt);
            }
            return typex;
        }

        public Bitmap frame(Bitmap src, String type) {
            Bitmap typex;
            if (type.equals("pare")) {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type));
            } else if (type.equals("xxx")) {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type));
            } else {
                typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type));
            }
            return typex;
        }

        public Path getParellelogramPath(Bitmap src, String type) {
            Path path = null;
            if (type.equals("pare")) {
                path = resizePath(PathParser.createPathFromPathData(cxt.getString(R.string.pare)),
                        src.getWidth(), src.getHeight());
            } else if (type.equals("xxx")) {
                path = resizePath(PathParser.createPathFromPathData(cxt.getString(R.string.square)),
                        src.getWidth(), src.getHeight());
            } else {
                path = resizePath(PathParser.createPathFromPathData(cxt.getString(R.string.square)),
                        src.getWidth(), src.getHeight());
            }

            return path;
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
            tvCardTitle = convertView.findViewById(R.id.tvCardTitle);
            tvCardTitleCount = convertView.findViewById(R.id.tvCardTitleCount);
//            player1Img = convertView.findViewById(R.id.player1Img);
//            player2Img = convertView.findViewById(R.id.player2Img);
//            LinearLayout llTotal = convertView.findViewById(R.id.llTotal);
//            tvMatchUpType = convertView.findViewById(R.id.tvMatchUpType);
//            tvStartTime = convertView.findViewById(R.id.tvStartTime);
//            cardColor = convertView.findViewById(R.id.cardColor);
//            llLive = convertView.findViewById(R.id.llLive);
//            ivOverUnder = convertView.findViewById(R.id.ivOverUnder);
//            ivMatchUp = convertView.findViewById(R.id.ivMatchUp);
//            tvPlus = convertView.findViewById(R.id.tvPlus);
//            playerFrame1 = convertView.findViewById(R.id.fimg1);
//            playerFrame2 =  convertView.findViewById(R.id.fimg2);
//            s_ll = convertView.findViewById(R.id.stas);
//            s_won = convertView.findViewById(R.id.wonstas);
//            tvCardcount =  convertView.findViewById(R.id.tvCardcount);
            tvMatchUpType = convertView.findViewById(R.id.tvMatchUpType);
            //mContentView = (TextView) view.findViewById(R.id.content);
            player1Img = convertView.findViewById(R.id.player1Img);
            player2Img = convertView.findViewById(R.id.player2Img);
//            playerFrame1 = convertView.findViewById(R.id.fimg1);
//            playerFrame2 = convertView.findViewById(R.id.fimg2);
            cardColor = convertView.findViewById(R.id.cardColor);
            tvCardwin = convertView.findViewById(R.id.tvCardwin);
            s_ll = convertView.findViewById(R.id.stas);
            s_won = convertView.findViewById(R.id.wonstas);
            stas_count = convertView.findViewById(R.id.stas_count);
            mywin_amount = convertView.findViewById(R.id.mywins);
            count_txt = convertView.findViewById(R.id.count_txt);
            tvStartTime = convertView.findViewById(R.id.tvStartTime);
            ivOverUnder = convertView.findViewById(R.id.ivOverUnder);
            ivMatchUp = convertView.findViewById(R.id.ivMatchUp);
            tvPlus = convertView.findViewById(R.id.tvPlus);
            llLive = convertView.findViewById(R.id.llLive);

//            stas_count.setVisibility(View.GONE);
            count_txt.setVisibility(View.GONE);

//            tvCardcount.setVisibility(View.GONE);
            // Populate the data into the template view using the data object
            tvCardTitle.setText(mmp.getCardTitle() + "");

//            mmp.getPlayerCardIds().size();
//            for (int i = 0; i < mmp.getPlayerCardIds().size(); i++) {
////                Toast.makeText(this,""+i,Toast.LENGTH_LONG).show();
//                tvCardTitleCount.setText("#"+i);
//            }
            if (position >= 0) {
                stas_count.setVisibility(View.VISIBLE);
                tvCardTitleCount.setVisibility(View.VISIBLE);

                tvCardTitleCount.setText("#" + (position + 1));
//                for (int i = 1; i <users.size() ; i++) {
//                    tvCardTitleCount.setText("#" + i+1);
//
//                }

            }
            // count_txt.setText(mmp.getMatchupsWon() + " / " + mmp.getMatchupsPlayed());


            final String leagueName = mmp.getLeagueAbbrevation();

            switch (leagueName) {
                case "NFL":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.foot_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_football));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_football));
                    break;

                case "NBA":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.basket_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_basketball));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_basketball));

                    break;

                case "NHL":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.hockey_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_hockey));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_hockey));

                    break;

                case "NASCAR":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.car_race_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_nascar));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_nascar));

                    break;

                case "MLB":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.base_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_baseball));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_baseball));

                    break;

                case "EPL":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.tennis_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_tennis));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_tennis));
                    break;

                case "LA-LIGA":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.foot_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_football));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_football));

                    break;

                case "MLS":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.soccer_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_soccer));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_soccer));

                    break;

                case "NCAAMB":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.basket_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_basketball));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_basketball));

                    break;

                case "NCAAFB":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.foot_ball_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_football));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_football));

                    break;

                case "PGA":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.golf_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_golf));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_golf));
                    break;
                case "WILD CARD":
                    cardColor.setBackgroundColor(cxt.getResources().getColor(R.color.golf_color));
                    player1Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cl_golf));
                    player2Img.setImageDrawable(cxt.getResources().getDrawable(R.drawable.cr_golf));
                    break;
                default:
                    break;
            }


            final Bitmap frame_bitmap = BitmapFactory.decodeResource(cxt.getResources(),
                    R.drawable.frame12);


            //   playerFrame1.setImageBitmap(frame(frame_bitmap, "xxx"));
            final Bitmap frame_bitmap2 = BitmapFactory.decodeResource(cxt.getResources(),
                    R.drawable.frame12);
            // playerFrame2.setImageBitmap(frame(frame_bitmap2, "pare"));
            if (mmp.getPlayerImageLeft() != null && !mmp.getPlayerImageLeft().equals("")) {
                Picasso.with(cxt).load(mmp.getPlayerImageLeft()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        player1Img.setImageBitmap(convertTParellelogram(bitmap, "xxx"));

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }

            if (mmp.getPlayerImageRight() != null && !mmp.getPlayerImageRight().equals("")) {

                Picasso.with(cxt).load(mmp.getPlayerImageRight()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        player2Img.setImageBitmap(convertTParellelogram(bitmap, "pare"));

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
            }

            String cardType = mmp.getMatchupType();
            tvMatchUpType.setText(cardType);
            if (cardType.equalsIgnoreCase("match-up")) {
                //    listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_matchup, 0, 0, 0);
                ivMatchUp.setVisibility(View.VISIBLE);
                ivOverUnder.setVisibility(View.GONE);
                tvPlus.setVisibility(View.GONE);
            } else if (cardType.equalsIgnoreCase("mixed")) {
                // tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ptt, 0, 0, 0);
                ivMatchUp.setVisibility(View.VISIBLE);
                ivOverUnder.setVisibility(View.VISIBLE);
                tvPlus.setVisibility(View.VISIBLE);
            } else if (cardType.equalsIgnoreCase("OVER-UNDER")) {
                //tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_overunder, 0, 0, 0);
                ivMatchUp.setVisibility(View.GONE);
                ivOverUnder.setVisibility(View.VISIBLE);
                tvPlus.setVisibility(View.GONE);
            }


            // time
            final String leagueTime = mmp.getStartTime();

            if (leagueTime != null && !leagueTime.equals("")) {
                String formatTimeDiff = getTimeDiff(leagueTime);
                if (formatTimeDiff != null && !formatTimeDiff.equals("")) {
                    tvStartTime.setText(formatTimeDiff);
                    tvStartTime.setVisibility(View.VISIBLE);
                } else {
                    tvStartTime.setVisibility(View.GONE);
                }
            } else {
                tvStartTime.setVisibility(View.GONE);
            }

            // Return the completed view to render on screen
            return convertView;
        }
    }

    private static String getTimeDiff(String timeFromServer) {
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

    private static String printDifference(Date startDate, Date endDate) {

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
}


/*

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sport.playsqorr.R;

public class HomeCards_MoreScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cards__more_screen);
    }
}
*/
