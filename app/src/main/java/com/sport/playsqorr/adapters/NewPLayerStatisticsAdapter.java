package com.sport.playsqorr.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.model.StatsPlayerStatistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class NewPLayerStatisticsAdapter extends RecyclerView.Adapter<NewPLayerStatisticsAdapter.PlayerHolder> {
    //    NewPlayerStatistics mPlayersStatisticsList;
    Context mContext;
    List<StatsPlayerStatistics> sPlayerStatistics = new ArrayList<>();
    String valub;

    public NewPLayerStatisticsAdapter(List<StatsPlayerStatistics> statsPlayerStatistics, Context mContext, String valub) {
//        this.mPlayersStatisticsList = mPlayersStatisticsList;
        this.sPlayerStatistics = statsPlayerStatistics;
        this.mContext = mContext;
        this.valub = valub;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.player_stat_single_row, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder playerHolder, int i) {
        StatsPlayerStatistics mPlayerStts = sPlayerStatistics.get(i); //mPlayersStatisticsList;


        DecimalFormat df2 = new DecimalFormat("##.##");
        Typeface tf_htxt = Typeface.createFromAsset(mContext.getAssets(), "Exo_ExtraBold.ttf");


        if (valub.equalsIgnoreCase("3")) {

            playerHolder.aorb.setVisibility(View.GONE);
            playerHolder.aorbc.setVisibility(View.VISIBLE);

            playerHolder.players_p.setText(mPlayerStts.getDisplayText());
            playerHolder.p1.setText(mPlayerStts.getPlayerA());
            playerHolder.p2.setText(mPlayerStts.getPlayerB());
            playerHolder.p3.setText(mPlayerStts.getPlayerC());

            playerHolder.players_p.setTypeface(tf_htxt);

            double A_S = Double.parseDouble((mPlayerStts.getPlayerA()));
            double B_S = Double.parseDouble(mPlayerStts.getPlayerB());
            double C_S = Double.parseDouble(mPlayerStts.getPlayerC());


            if(B_S < C_S){

            }
            playerHolder.p1.setTypeface(tf_htxt);
            playerHolder.p2.setTypeface(tf_htxt);
            playerHolder.p3.setTypeface(tf_htxt);
            if (mPlayerStts.getPlayerA().equalsIgnoreCase("0")) {
                playerHolder.p1.setText("0");
//                playerHolder.p1.setText("0.0");
            } else {

                playerHolder.p1.setText(df2.format(Double.parseDouble(mPlayerStts.getPlayerA())));
            }
            if (mPlayerStts.getPlayerB().equalsIgnoreCase("0")) {
                playerHolder.p2.setText("0");
//                playerHolder.p2.setText("0.0");
            } else {

                playerHolder.p2.setText(df2.format(Double.parseDouble(mPlayerStts.getPlayerB())));
            }

            if (mPlayerStts.getPlayerC().equalsIgnoreCase("0")) {
                playerHolder.p3.setText("0");
//                playerHolder.p3.setText("0.0");
            } else {

                playerHolder.p3.setText(df2.format(Double.parseDouble(mPlayerStts.getPlayerC())));
            }


        } else {
            playerHolder.aorb.setVisibility(View.VISIBLE);
            playerHolder.aorbc.setVisibility(View.GONE);
            if (valub.equalsIgnoreCase("2")) {
                playerHolder.second_player_points.setVisibility(View.GONE);
            } else if (valub.equalsIgnoreCase("1")) {
                playerHolder.second_player_points.setVisibility(View.VISIBLE);
            }

//        Typeface tf_htxt = Typeface.createFromAsset(mContext.getAssets(), "Exo_Extrabold.ttf");

            double L_S = Double.parseDouble((mPlayerStts.getLeftPlayerSqorr()));
            double R_S = Double.parseDouble(mPlayerStts.getRightPlayerSqorr());
            if (L_S < R_S) {
                playerHolder.players_positions.setText(mPlayerStts.getDisplayText());
                playerHolder.first_player_points.setText(mPlayerStts.getLeftPlayerSqorr());
                playerHolder.second_player_points.setText(mPlayerStts.getRightPlayerSqorr());
                playerHolder.second_player_points.setTypeface(tf_htxt);

            } else if (L_S > R_S) {
                playerHolder.first_player_points.setText(mPlayerStts.getLeftPlayerSqorr());
                playerHolder.second_player_points.setText(mPlayerStts.getRightPlayerSqorr());
                playerHolder.players_positions.setText(mPlayerStts.getDisplayText());
//            Typeface tf_htxt = Typeface.createFromAsset(mContext.getAssets(), "Exo_SemiBold.ttf");
                playerHolder.first_player_points.setTypeface(tf_htxt);

            } else {
                playerHolder.first_player_points.setText(mPlayerStts.getLeftPlayerSqorr());
                playerHolder.second_player_points.setText(mPlayerStts.getRightPlayerSqorr());
                playerHolder.players_positions.setText(mPlayerStts.getDisplayText());
            }
            if (mPlayerStts.getLeftPlayerSqorr().equalsIgnoreCase("0")) {
                playerHolder.first_player_points.setText("0");
//                playerHolder.first_player_points.setText("0.0");
            } else {

                playerHolder.first_player_points.setText(df2.format(Double.parseDouble(mPlayerStts.getLeftPlayerSqorr())));
            }
            if (mPlayerStts.getRightPlayerSqorr().equalsIgnoreCase("0")) {
                playerHolder.second_player_points.setText("0");
//                playerHolder.second_player_points.setText("0.0");
            } else {

                playerHolder.second_player_points.setText(df2.format(Double.parseDouble(mPlayerStts.getRightPlayerSqorr())));
            }

            System.out.println("size" + sPlayerStatistics.size());
            System.out.println("size" + i);
            int posi = i + 1;
            if (sPlayerStatistics.size() == posi) {
                playerHolder.second_player_points.setTypeface(tf_htxt);
                playerHolder.first_player_points.setTypeface(tf_htxt);
                playerHolder.players_positions.setTypeface(tf_htxt);

            }
        }


        /*playerHolder.first_player_points.setText(df2.format(Double.parseDouble(mPlayerStts.getFirst_player_points().get(i) + "")));
        if (!mPlayerStts.getSecond_player_points().get(i).equals("000")) {
            playerHolder.second_player_points.setText(df2.format(Double.parseDouble(mPlayerStts.getSecond_player_points().get(i) + "")));
        } else {
            playerHolder.second_player_points.setText("-");
        }
        playerHolder.players_positions.setText(mPlayerStts.getPlayer_positions().get(i));*/
    }

    @Override
    public int getItemCount() {
        if (sPlayerStatistics.size() > 0 && sPlayerStatistics != null) {
            return sPlayerStatistics.size();//.getFirst_player_points().size();
        } else {
            return 0;
        }

    }


    public class PlayerHolder extends RecyclerView.ViewHolder {
        TextView first_player_points;
        TextView second_player_points;
        TextView third_player_points;
        TextView players_positions;

        LinearLayout aorbc;
        LinearLayout aorb;
        TextView p1, p2, p3, players_p;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            this.first_player_points = (TextView) itemView.findViewById(R.id.first_player_points);
            this.second_player_points = (TextView) itemView.findViewById(R.id.second_player_points);
            this.players_positions = (TextView) itemView.findViewById(R.id.players_positions);

            this.aorb = itemView.findViewById(R.id.aorb);

            this.aorbc = itemView.findViewById(R.id.aorbc);
            this.players_p = (TextView) itemView.findViewById(R.id.players_p);
            this.p1 = (TextView) itemView.findViewById(R.id.first_player_1);
            this.p2 = (TextView) itemView.findViewById(R.id.second_player_2);
            this.p3 = (TextView) itemView.findViewById(R.id.third_player_3);
        }
    }
}
