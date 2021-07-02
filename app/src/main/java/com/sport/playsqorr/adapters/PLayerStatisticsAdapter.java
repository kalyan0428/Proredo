package com.sport.playsqorr.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.model.PlayersStatistics;

public class PLayerStatisticsAdapter extends RecyclerView.Adapter<PLayerStatisticsAdapter.PlayerHolder> {
    PlayersStatistics mPlayersStatisticsList;
    Context mContext;

    public PLayerStatisticsAdapter(PlayersStatistics mPlayersStatisticsList, Context mContext) {
        this.mPlayersStatisticsList = mPlayersStatisticsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.player_stat_single_row, viewGroup, false);
        PLayerStatisticsAdapter.PlayerHolder viewHolder = new PLayerStatisticsAdapter.PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder playerHolder, int i) {
        PlayersStatistics player_stats = mPlayersStatisticsList;
        playerHolder.first_player_points.setText(player_stats.getFirst_player_points());
        playerHolder.second_player_points.setText(player_stats.getSecond_player_points());
        playerHolder.players_positions.setText(player_stats.getPlaying_positions());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

   /* @Override
    public int getItemCount() {
        return mPlayersStatisticsList.;
    }*/

    public class PlayerHolder extends RecyclerView.ViewHolder {
        TextView first_player_points;
        TextView second_player_points;
        TextView players_positions;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            this.first_player_points = (TextView) itemView.findViewById(R.id.first_player_points);
            this.second_player_points = (TextView) itemView.findViewById(R.id.second_player_points);
            this.players_positions = (TextView) itemView.findViewById(R.id.players_positions);

        }
    }
}
