package com.sport.playsqorr.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.model.Picks;

import java.util.List;


public class SureSqorrPicksAdapter extends RecyclerView.Adapter<SureSqorrPicksAdapter.PlayerHolder> {
    List<Picks> mPlayersStatisticsList;
    Context mContext;
    Cursor cursor;
    private DataBaseHelper mydb;
    SQLiteDatabase sqLiteDatabase;

    String db_role, db_cash, db_token;
    String  pickCount="0";// Remove this hardcoded value and get this from user selection from match ups -- Important
    boolean support_cash;
    boolean support_token;
    public SureSqorrPicksAdapter(List<Picks> mPlayersStatisticsList, Context mContext, boolean support_cash, boolean support_token) {
        this.mPlayersStatisticsList = mPlayersStatisticsList;
        this.mContext = mContext;
        this.support_cash = support_cash;
        this.support_token = support_token;
    }

    @NonNull
    @Override
    public SureSqorrPicksAdapter.PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.picks_layout_single, viewGroup, false);
        SureSqorrPicksAdapter.PlayerHolder viewHolder = new SureSqorrPicksAdapter.PlayerHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SureSqorrPicksAdapter.PlayerHolder playerHolder, int i) {

        /******************Database Starts************************/
        mydb = new DataBaseHelper(mContext);
        sqLiteDatabase = mydb.getReadableDatabase();
        /******************Database Ends************************/


        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println(cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME)));
                db_role = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
                db_cash = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
                db_token = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));

            }
            cursor.close();
        } else {
            db_role = "0";

        }

        Picks player_stats = mPlayersStatisticsList.get(i);

        playerHolder.picks_tv.setText(player_stats.getPicks());
        playerHolder.multiplier_tv.setText(player_stats.getMultiplier());

        if (db_role == null || db_role.equalsIgnoreCase("cash")) {

            Log.e("88--------",support_cash+ "----" + support_token);

                if (support_cash==false && support_token==true) {
                    playerHolder.winpayout_tv.setText(player_stats.getWinpayout());
                    playerHolder.ivTokens.setVisibility(View.VISIBLE);
                }else{
                    playerHolder.winpayout_tv.setText("$"+player_stats.getWinpayout());
                    playerHolder.ivTokens.setVisibility(View.GONE);
                }
            



        } else if ( db_role.equalsIgnoreCase("tokens")) {

            playerHolder.winpayout_tv.setText(player_stats.getWinpayout());
            playerHolder.ivTokens.setVisibility(View.VISIBLE);
        }
        if(pickCount.equalsIgnoreCase(playerHolder.picks_tv.getText().toString())){
            playerHolder.llPick.setBackgroundColor(Color.parseColor("#363636"));
        }else{
            playerHolder.llPick.setBackgroundColor(Color.parseColor("#202020"));
        }

    }

    @Override
    public int getItemCount() {
        return mPlayersStatisticsList.size();
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {
        TextView picks_tv;
        TextView multiplier_tv;
        TextView winpayout_tv;
        private ImageView ivTokens;
        private LinearLayout llPick;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            this.picks_tv =  itemView.findViewById(R.id.picks_tv);
            this.multiplier_tv =  itemView.findViewById(R.id.multiplier_tv);
            this.winpayout_tv =  itemView.findViewById(R.id.winpayout_tv);
            this.ivTokens=itemView.findViewById(R.id.ivTokens);
            this.llPick=itemView.findViewById(R.id.llPick);
        }
    }
}
