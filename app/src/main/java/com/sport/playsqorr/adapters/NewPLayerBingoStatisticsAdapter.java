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

import com.google.gson.JsonArray;
import com.sport.playsqorr.R;
import com.sport.playsqorr.model.StatsPlayerStatistics;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class NewPLayerBingoStatisticsAdapter extends RecyclerView.Adapter<NewPLayerBingoStatisticsAdapter.PlayerHolder> {
    //    NewPlayerStatistics mPlayersStatisticsList;
    Context mContext;
   // List<StatsPlayerStatistics> sPlayerStatistics = new ArrayList<>();
    String valub;
JSONArray jj;
    public NewPLayerBingoStatisticsAdapter(JSONArray statsPlayerStatistics, int position, Context mContext, String valub) {
//        this.mPlayersStatisticsList = mPlayersStatisticsList;
        this.jj = statsPlayerStatistics;
        this.mContext = mContext;
        this.valub = valub;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.player_bingo_single_row, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder playerHolder, int i) {
       // StatsPlayerStatistics mPlayerStts = sPlayerStatistics.get(i); //mPlayersStatisticsList;



    }

    @Override
    public int getItemCount() {
        if (jj.length() > 0 && jj != null) {
            return jj.length();//.getFirst_player_points().size();
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
//            this.first_player_points = (TextView) itemView.findViewById(R.id.first_player_points);
//            this.second_player_points = (TextView) itemView.findViewById(R.id.second_player_points);
//            this.players_positions = (TextView) itemView.findViewById(R.id.players_positions);
//
//            this.aorb = itemView.findViewById(R.id.aorb);
//
//            this.aorbc = itemView.findViewById(R.id.aorbc);
//            this.players_p = (TextView) itemView.findViewById(R.id.players_p);
//            this.p1 = (TextView) itemView.findViewById(R.id.first_player_1);
//            this.p2 = (TextView) itemView.findViewById(R.id.second_player_2);
//            this.p3 = (TextView) itemView.findViewById(R.id.third_player_3);
        }
    }
}
