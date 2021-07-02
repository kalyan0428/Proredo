package com.sport.playsqorr.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.MyCardsPojo;
import com.sport.playsqorr.utilities.BitmapUtils;
import com.sport.playsqorr.utilities.PathParser;
import com.sport.playsqorr.views.Dashboard;
import com.sport.playsqorr.views.MatchupScreen;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MoreMyCards_Adapter extends RecyclerView.Adapter<MoreMyCards_Adapter.ViewHolder> {


    //    private final PromosFragment.OnListFragmentInteractionListener mListener;
    static FragmentActivity activity_t;
    private final List<MyCardsPojo> mValues;

    public MoreMyCards_Adapter(List<MyCardsPojo> items, FragmentActivity activity) {
        mValues = items;
        this.activity_t = activity;
    }

    public static Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }

    public static Bitmap convertTParellelogram(Bitmap src, String type) {
        Bitmap typex;
        if (type.equals("pare")) {
            typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, activity_t);
        } else if (type.equals("xxx")) {
            typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, activity_t);
        } else {
            typex = BitmapUtils.getCroppedBitmap(src, getParellelogramPath(src, type), type, activity_t);
        }
        return typex;
    }

    public static Bitmap frame(Bitmap src, String type) {
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

    public static Path getParellelogramPath(Bitmap src, String type) {
        Path path = null;
        if (type.equals("pare")) {
            path = resizePath(PathParser.createPathFromPathData(activity_t.getString(R.string.pare)),
                    src.getWidth(), src.getHeight());
        } else if (type.equals("xxx")) {
            path = resizePath(PathParser.createPathFromPathData(activity_t.getString(R.string.square)),
                    src.getWidth(), src.getHeight());
        } else {
            path = resizePath(PathParser.createPathFromPathData(activity_t.getString(R.string.square)),
                    src.getWidth(), src.getHeight());
        }

        return path;
    }

    @NonNull
    @Override
    public MoreMyCards_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mycards_more, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoreMyCards_Adapter.ViewHolder holder, int position) {

        final MyCardsPojo myCardsList = mValues.get(position);

        holder.mIdView.setText(myCardsList.getCardTitle());
        holder.tvMatchUpType.setText(myCardsList.getMatchupType());

        holder.count_txt.setText(myCardsList.getMatchupsWon() + " / " + myCardsList.getMatchupsPlayed());

        if ((myCardsList.getStatus().equalsIgnoreCase("PENDING"))) {
            holder.s_ll.setVisibility(View.GONE);
            holder.s_won.setVisibility(View.GONE);
            holder.playerFrame1.setVisibility(View.GONE);
            holder.playerFrame2.setVisibility(View.GONE);
            holder.count_txt.setVisibility(View.GONE);
            holder.tvStartTime.setVisibility(View.VISIBLE);
            Log.e("time--",myCardsList.getStartTime());




        } else if ((myCardsList.getStatus().equalsIgnoreCase("LIVE"))) {
            holder.s_ll.setVisibility(View.GONE);
            holder.s_won.setVisibility(View.GONE);
            holder.playerFrame1.setVisibility(View.GONE);
            holder.playerFrame2.setVisibility(View.GONE);
            holder.count_txt.setVisibility(View.GONE);
            holder.tvStartTime.setVisibility(View.GONE);
            holder.llLive.setVisibility(View.VISIBLE);

        } else {




            if(myCardsList.getPlayerCardIds().size()>=2){
                Log.e("cards mycards--- + >=2",""+ myCardsList.getPlayerCardIds());
            }else {
              //  Log.e("cards mycards----+ <=2", "" + myCardsList.getPlayerCardIds());


          /*      if (myCardsList.getStatus().equalsIgnoreCase("LOSS")) {
                    holder.s_ll.setVisibility(View.VISIBLE);
                    holder.s_won.setVisibility(View.GONE);

                    holder.tvCardwin.setText(myCardsList.getStatus());
                } else if (myCardsList.getStatus().equalsIgnoreCase("WIN")) {

                    if(myCardsList.getCurrencyTypeIsTokens().equalsIgnoreCase("true")){
                        holder.s_ll.setVisibility(View.GONE);
                        holder.s_won.setVisibility(View.VISIBLE);
                        if(myCardsList.getWinAmount()!=null){
                            holder.mywin_amount.setText(myCardsList.getWinAmount());
                            holder.mywin_amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);

                        }else{
                            holder.mywin_amount.setText("0");
                            holder.mywin_amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_token_sm_white, 0, 0, 0);

                        }
                    }else{
                        holder.s_ll.setVisibility(View.GONE);
                        holder.s_won.setVisibility(View.VISIBLE);
                        if(myCardsList.getWinAmount()!=null){
                            holder.mywin_amount.setText("$" + myCardsList.getWinAmount());
                        }else{
                            holder.mywin_amount.setText("$ 0");
                        }
                    }
//                holder.s_ll.setVisibility(View.GONE);
//                holder.s_won.setVisibility(View.VISIBLE);
//                if(myCardsList.getWinAmount()!=null){
//                    holder.mywin_amount.setText(myCardsList.getWinAmount());
//                }else{
//                    holder.mywin_amount.setText("0");
//                }

                } else if (myCardsList.getStatus().equalsIgnoreCase("CANCELLED")) {
                    holder.s_ll.setVisibility(View.VISIBLE);
                    holder.s_won.setVisibility(View.GONE);

                    holder.tvCardwin.setText(myCardsList.getStatus());
                } else {
//            holder.s_ll.setVisibility(View.GONE);
                }


*/
            }
        }
        final String leagueTime = myCardsList.getStartTime();

        if (leagueTime != null && !leagueTime.equals("")) {
            String formatTimeDiff=getTimeDiff(leagueTime,myCardsList.getStatus());
            if(formatTimeDiff!=null&&!formatTimeDiff.equals("")) {
                holder.tvStartTime.setText(formatTimeDiff);
                holder.tvStartTime.setVisibility(View.VISIBLE);
            }else{
                holder.tvStartTime.setVisibility(View.GONE);
            }
        } else {
            holder.tvStartTime.setVisibility(View.GONE);
        }

        if (myCardsList.getPlayerCardIds().size() > 0 && myCardsList.getPlayerCardIds().size() <= 1) {


            holder.tvCardcount.setVisibility(View.GONE);
            holder.stas_count.setVisibility(View.GONE);
             String leagueName = myCardsList.getLeagueAbbrevation();

            switch (leagueName) {
                case "NFL":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.foot_ball_color));
                    holder.player1Img.setImageDrawable(activity_t.getDrawable(R.drawable.cl_football));
                    holder.player2Img.setImageDrawable(activity_t.getDrawable(R.drawable.cr_football));
                    break;

                case "NBA":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.basket_ball_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_basketball));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_basketball));
                    break;

                case "NHL":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.hockey_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_hockey));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_hockey));
                    break;

                case "NASCAR":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.car_race_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_nascar));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_nascar));
                    break;

                case "MLB":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.base_ball_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_baseball));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_baseball));
                    break;

                case "EPL":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.tennis_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_tennis));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_tennis));
                    break;

                case "LA-LIGA":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.foot_ball_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_football));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_football));
                    break;

                case "MLS":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.soccer_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_soccer));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_soccer));
                    break;

                case "NCAAMB":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.basket_ball_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_basketball));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_basketball));
                    break;

                case "PGA":
                    holder.cardColor.setBackgroundColor(activity_t.getResources().getColor(R.color.golf_color));
                    holder.player1Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cl_golf));
                    holder.player2Img.setImageDrawable(activity_t.getResources().getDrawable(R.drawable.cr_golf));
                    break;
                default:

                    break;
            }
        }else if (myCardsList.getPlayerCardIds().size() >= 2) {

//            switch (myCardsList.getLeagueAbbrevation()) {
            String leagueName = myCardsList.getLeagueAbbrevation();

            switch (leagueName) {
                case "NFL":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.foot_ball_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "NBA":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.basket_ball_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "NHL":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.hockey_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "NASCAR":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.car_race_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "MLB":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.base_ball_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "EPL":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.tennis_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "LA-LIGA":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.foot_ball_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "MLS":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.soccer_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "NCAAMB":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.basket_ball_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;

                case "PGA":
                    holder.tvCardcount.setBackgroundColor(activity_t.getResources().getColor(R.color.golf_color));
                    holder.tvCardcount.setText(myCardsList.getPlayerCardIds().size()+"");
                    break;
                default:

                    break;
            }

            holder.tvCardcount.setVisibility(View.VISIBLE);
            holder.stas_count.setVisibility(View.VISIBLE);
        }


        final Bitmap frame_bitmap = BitmapFactory.decodeResource(activity_t.getResources(),
                R.drawable.frame12);


        holder.playerFrame1.setImageBitmap(frame(frame_bitmap, "xxx"));
        final Bitmap frame_bitmap2 = BitmapFactory.decodeResource(activity_t.getResources(),
                R.drawable.frame12);
        holder.playerFrame2.setImageBitmap(frame(frame_bitmap2, "pare"));
        if (myCardsList.getPlayerImageLeft() != null && !myCardsList.getPlayerImageLeft().equals("")) {
            Picasso.with(activity_t).load(myCardsList.getPlayerImageLeft()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.player1Img.setImageBitmap(convertTParellelogram(bitmap, "xxx"));

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        if (myCardsList.getPlayerImageRight() != null && !myCardsList.getPlayerImageRight().equals("")) {

            Picasso.with(activity_t).load(myCardsList.getPlayerImageRight()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.player2Img.setImageBitmap(convertTParellelogram(bitmap, "pare"));

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myCardsList.getPlayerCardIds().size() > 0 && myCardsList.getPlayerCardIds().size() <= 1) {


                    Intent matchup = new Intent(activity_t, MatchupScreen.class);
                    matchup.putExtra("cardid", myCardsList.getCardId());
                    matchup.putExtra("home","2");
                    matchup.putExtra("cardid_title", myCardsList.getCardTitle());
                    matchup.putExtra("cardid_color1", myCardsList.getLeagueAbbrevation());
                    matchup.putExtra("place", "mycards");
                    matchup.putExtra("cardid_date", "");
                    matchup.putExtra("playerid_m", myCardsList.getPlayerCardIds().get(0));
                    activity_t.startActivity(matchup);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            Activity act = activity_t;
                            if (act instanceof Dashboard)
                                ((Dashboard) act).changeToHome();
                        }
                    }, 100);


                }else if (myCardsList.getPlayerCardIds().size() >= 2) {


////                    Kalyan kumar
//                    Intent matchup = new Intent(activity_t, MoreCardsScreen.class);
//                    matchup.putExtra("cardspojo", myCardsList);
//                    activity_t.startActivity(matchup);
//


                }
                else {
                    Intent matchup = new Intent(activity_t, MatchupScreen.class);
                    matchup.putExtra("cardid", myCardsList.getCardId());
                    matchup.putExtra("home","2");
                    matchup.putExtra("cardid_title", myCardsList.getCardTitle());
                    matchup.putExtra("cardid_color1", myCardsList.getLeagueAbbrevation());
                    matchup.putExtra("place", "mycards");
                    matchup.putExtra("cardid_date", "");
                    matchup.putExtra("playerid_m", "");
                    activity_t.startActivity(matchup);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            Activity act = activity_t;
                            if (act instanceof Dashboard)
                                ((Dashboard) act).changeToHome();
                        }
                    }, 100);

                }





            }
        });


        String cardType = myCardsList.getMatchupType();
        holder.tvMatchUpType.setText(cardType);

       /* if (cardType.equalsIgnoreCase("match-up")) {
            holder.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_matchup, 0, 0, 0);
        } else if (cardType.equalsIgnoreCase("mixed")) {
            holder.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ptt, 0, 0, 0);
        } else if (cardType.equalsIgnoreCase("OVER-UNDER")) {
            holder.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_overunder, 0, 0, 0);
        }*/
        if (cardType.equalsIgnoreCase("match-up")) {
            //    listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_matchup, 0, 0, 0);
            holder.ivMatchUp.setVisibility(View.VISIBLE);
            holder.ivOverUnder.setVisibility(View.GONE);
            holder.tvPlus.setVisibility(View.GONE);
        } else if (cardType.equalsIgnoreCase("mixed")) {
            // holder.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ptt, 0, 0, 0);
            holder.ivMatchUp.setVisibility(View.VISIBLE);
            holder.ivOverUnder.setVisibility(View.VISIBLE);
            holder.tvPlus.setVisibility(View.VISIBLE);
        } else if (cardType.equalsIgnoreCase("OVER-UNDER")) {
            //holder.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_overunder, 0, 0, 0);
            holder.ivMatchUp.setVisibility(View.GONE);
            holder.ivOverUnder.setVisibility(View.VISIBLE);
            holder.tvPlus.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return this.mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  View mView;
        public  TextView mIdView, tvMatchUpType, tvCardwin, tvStartTime, mywin_amount, count_txt,tvPlus,tvCardcount;
        public  LinearLayout s_ll, s_won,llLive, stas_count;
        // public final TextView mContentView;
        private ImageView player1Img, player2Img, playerFrame1, playerFrame2,ivMatchUp,ivOverUnder;
        public MyCardsPojo mItem;
        private View cardColor;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView =  view.findViewById(R.id.tvCardTitle);
            tvCardcount =  view.findViewById(R.id.tvCardcount);
            tvMatchUpType =  view.findViewById(R.id.tvMatchUpType);
            //mContentView = (TextView) view.findViewById(R.id.content);
            player1Img =  view.findViewById(R.id.player1Img);
            player2Img =  view.findViewById(R.id.player2Img);
            playerFrame1 = view.findViewById(R.id.fimg1);
            playerFrame2 =  view.findViewById(R.id.fimg2);
            cardColor = view.findViewById(R.id.cardColor);
            tvCardwin = view.findViewById(R.id.tvCardwin);
            s_ll = view.findViewById(R.id.stas);
            s_won = view.findViewById(R.id.wonstas);
            stas_count = view.findViewById(R.id.stas_count);
            mywin_amount = view.findViewById(R.id.mywins);
            count_txt = view.findViewById(R.id.count_txt);
            tvStartTime = view.findViewById(R.id.tvStartTime);
            ivOverUnder=view.findViewById(R.id.ivOverUnder);
            ivMatchUp=view.findViewById(R.id.ivMatchUp);
            tvPlus=view.findViewById(R.id.tvPlus);
            llLive=view.findViewById(R.id.llLive);

            stas_count.setVisibility(View.GONE);
            tvCardcount.setVisibility(View.GONE);

        }
    }



    private String getTimeDiff(String timeFromServer,String leagueStatus){
        String formattedTime="";
        String [] timeArray= timeFromServer.split("T");
        String  serverDate="";
        String sererTime="";

        if(timeArray.length>0){
            serverDate=timeArray[0];
            sererTime=timeArray[1].replace("Z","");
            timeFromServer=serverDate+" "+sererTime;
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
            if(date2!=null&&date1!=null) {
                if(leagueStatus.equalsIgnoreCase("pending"))
                formattedTime = printDifference(date1, date2);
                else
                    formattedTime = printDifference(date2, date1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedTime;
    }

    private String  printDifference(Date startDate, Date endDate) {

        String diffDate="";
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
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
        if(elapsedDays>0&&elapsedHours>0&&elapsedMinutes>0){
            diffDate=elapsedDays+"d "+elapsedHours+"h";
        }else if(elapsedHours>0&&elapsedMinutes>0){
            diffDate=elapsedHours+"h "+elapsedMinutes+"m";
        }else if(elapsedMinutes>0){
            diffDate=elapsedMinutes+"m";
        }

        return  diffDate;
    }
}
