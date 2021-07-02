package com.sport.playsqorr.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.sport.playsqorr.R;
import com.sport.playsqorr.model.PlayersStatistics;
import com.sport.playsqorr.pojos.PlayerA;
import com.sport.playsqorr.pojos.PlayerB;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerHolder> {

    Context mContext;
    PlayerB player_TWO;
    PlayerA player;
    private List<PlayerA> mPLayersList_One = new ArrayList<>();
    private List<PlayerB> mPLayersList_Two = new ArrayList<>();
    private List<String> checkedList = new ArrayList<>();
    private List<String> checkedList_Two = new ArrayList<>();
    //    List<Stats> mPlayerA_States_List = new ArrayList<>();
//    List<Stats> mPlayerB_States_List = new ArrayList<>();
    List<PlayersStatistics> mPlayersStatisticsList = new ArrayList<>();

    public PlayerListAdapter(List<PlayerA> mPlayersAListA, List<PlayerB> mPlayersAListB, List<PlayersStatistics> mPlayersStatisticsList, Context mContext) {
        this.mPLayersList_One = mPlayersAListA;
        this.mPLayersList_Two = mPlayersAListB;
        this.mPlayersStatisticsList = mPlayersStatisticsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.single_player_card, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerHolder playerHolder, final int position) {
        player = mPLayersList_One.get(position);
        playerHolder.player_Name.setText(player.getFirstName() + " " + player.getLastName());
        playerHolder.player_position.setText(player.getPositonName());
        playerHolder.player_price.setText(player.getPointSpread() + "");
        if (player.getVenue() != null) {
            playerHolder.player_Match.setVisibility(View.VISIBLE);
            playerHolder.player_Match.setText(player.getVenue());
        } else {
            playerHolder.player_Match.setVisibility(View.INVISIBLE);
        }

        //   playerHolder.player_Time.setText(player.getGameDate());


        if (player.getPlayerImage() != null) {
            Picasso.with(mContext).load(player.getPlayerImage())
                    .placeholder(R.drawable.steph_curry)
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
                    .into(playerHolder.player_img);

        }

        playerHolder.playerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerHolder.no_player.getVisibility() == View.GONE) {
                    mHilightViews(playerHolder, 1, position);
                }
            }
        });

        playerHolder.sq_cc_over.setVisibility(View.VISIBLE);
        playerHolder.sq_cc.setVisibility(View.GONE);
        //2ndplayer
        player_TWO = mPLayersList_Two.get(position);
        if (player_TWO.getPlayerId() == null) {
            playerHolder.second_playerCard.setVisibility(View.GONE);
            playerHolder.no_player.setVisibility(View.VISIBLE);
            playerHolder.playerCard.setClickable(false);
        } else {
            playerHolder.second_playerCard.setVisibility(View.VISIBLE);
            playerHolder.no_player.setVisibility(View.GONE);
            playerHolder.playerCard.setClickable(true);
        }


        playerHolder.second_player_Name.setText(player_TWO.getFirstName() + " " + player_TWO.getLastName());
        playerHolder.second_player_position.setText(player_TWO.getPositonName());
        playerHolder.second_player_price.setText(player_TWO.getPointSpread() + "");
        if (player_TWO.getVenue() != null) {
            playerHolder.second_player_Match.setVisibility(View.VISIBLE);
            playerHolder.second_player_Match.setText(player_TWO.getVenue());
        } else {
            playerHolder.second_player_Match.setVisibility(View.INVISIBLE);
        }
        //   playerHolder.second_player_Time.setText(player_TWO.getGameDate());
        if (player_TWO.getPlayerImage() != null) {
            Picasso.with(mContext).load(player_TWO.getPlayerImage())
                    .placeholder(R.drawable.steph_curry)
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
                    .into(playerHolder.second_player_img);

        }
        playerHolder.second_playerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHilightViews(playerHolder, 2, position);
            }
        });

        playerHolder.over_playercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHilightViews(playerHolder, 3, position);
            }
        });

        playerHolder.under_playercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHilightViews(playerHolder, 4, position);
            }
        });


        playerHolder.playerstatsViewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
                    playerHolder.arrow.setRotation(180);
                } else {
                    playerHolder.arrow.setRotation(360);
                }
            }
        });
//        playerHolder.sq_cc.setClickable(true);
        playerHolder.sq_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("185----------","11111111111111");

                playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
                    playerHolder.arrow.setRotation(180);
                } else {
                    playerHolder.arrow.setRotation(360);
                }
            }
        });

        PLayerStatisticsAdapter pLayerStatisticsAdapter = new PLayerStatisticsAdapter(mPlayersStatisticsList.get(position), mContext);
//        CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
        playerHolder.playerStatsRcView_child.setLayoutManager(new LinearLayoutManager(mContext));//(gridLayoutManager);
        playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);
    }


    private void mHilightViews(PlayerHolder playerHolder, int from, int position) {

        if (from == 1) {
            if (playerHolder.playerCheck.isChecked()) {
                playerHolder.playerCheck.setChecked(false);
                setFirstPlayerTextEnableDisable(playerHolder, false);
            } else {
                if (playerHolder.second_playerCheck.isChecked()) {
                    playerHolder.second_playerCheck.setChecked(false);
                    setSecondPlayerTextDisableEnableMode(playerHolder, false);
                }
                playerHolder.playerCheck.setChecked(true);
                setFirstPlayerTextEnableDisable(playerHolder, true);

            }
        } else if (from == 2) {
            if (playerHolder.second_playerCheck.isChecked()) {
                playerHolder.second_playerCheck.setChecked(false);
                setSecondPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                playerHolder.second_playerCheck.setChecked(true);
                if (playerHolder.playerCheck.isChecked()) {
                    playerHolder.playerCheck.setChecked(false);
                    setFirstPlayerTextEnableDisable(playerHolder, false);
                }
                setSecondPlayerTextDisableEnableMode(playerHolder, true);
            }
        } else if (from == 3) {

            if (playerHolder.overCheckbox.isChecked()) {
                playerHolder.overCheckbox.setChecked(false);
                setAPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                playerHolder.overCheckbox.setChecked(true);
                if (playerHolder.underCheckbox.isChecked()) {
                    playerHolder.underCheckbox.setChecked(false);
                    setBPlayerTextDisableEnableMode(playerHolder, false);
                }
                setAPlayerTextDisableEnableMode(playerHolder, true);
            }
        } else if (from == 4) {
            if (playerHolder.underCheckbox.isChecked()) {
                playerHolder.underCheckbox.setChecked(false);
                setBPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                playerHolder.underCheckbox.setChecked(true);
                if (playerHolder.overCheckbox.isChecked()) {
                    playerHolder.overCheckbox.setChecked(false);
                    setAPlayerTextDisableEnableMode(playerHolder, false);
                }
                setBPlayerTextDisableEnableMode(playerHolder, true);

            }
        }

    }


    private void setAPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
        }
    }


    private void setBPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));

        }
    }

    private void setFirstPlayerTextEnableDisable(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.player_position.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.player_Match.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.player_Time.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));

        } else {
            playerHolder.player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.player_position.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.player_Match.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.player_Time.setTextColor(mContext.getResources().getColor(R.color.hint));
        }

    }

    private void setSecondPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.black)));
            playerHolder.second_player_position.setTextColor(mContext.getResources().getColor((R.color.black)));
            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle));
            playerHolder.second_player_Match.setTextColor(mContext.getResources().getColor((R.color.black)));
            playerHolder.second_player_Time.setTextColor(mContext.getResources().getColor((R.color.black)));

        } else {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_position.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_Match.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_Time.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));

        }
    }


    @Override
    public int getItemCount() {
        return mPLayersList_One.size();
    }

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
        paint.setColor(mContext.getColor(color));
        paint.setStrokeWidth(1f);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        textView.setCompoundDrawableTintList(ColorStateList.valueOf(color));
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {

        //1St PLayer
        ImageView player_img;
        TextView player_Name;
        TextView player_Time;
        TextView player_position;
        TextView player_Match;
        Button player_price;
        CardView playerCard;
        CheckBox playerCheck;

        //2nd player
        ImageView second_player_img;
        TextView second_player_Name;
        TextView second_player_Time;
        TextView second_player_position;
        TextView second_player_Match;
        Button second_player_price;
        CardView second_playerCard;
        CheckBox second_playerCheck;

        LinearLayout sq_cc,sq_cc_over;
        //Player statistics
        CardView playerstatsViewSpinner;
        LinearLayout playerStatisticsView;
        ImageView arrow;
        RecyclerView playerStatsRcView_child;
        //B player off
        CheckBox overCheckbox;
        CheckBox underCheckbox;
        RelativeLayout under_playercard;
        RelativeLayout over_playercard;
        TextView overview_txt;
        TextView underView_txt;
        LinearLayout overView;
        LinearLayout underView;
        CardView no_player;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);

            //1ST PLAYER
            this.player_img = (ImageView) itemView.findViewById(R.id.player_img);
            this.player_Name = (TextView) itemView.findViewById(R.id.player_Name);
            this.player_position = (TextView) itemView.findViewById(R.id.player_position);
            this.player_Match = (TextView) itemView.findViewById(R.id.player_Match);
            this.player_Time = (TextView) itemView.findViewById(R.id.player_time);
            this.player_price = (Button) itemView.findViewById(R.id.player_price);
            this.playerCard = (CardView) itemView.findViewById(R.id.playerCard);
            this.playerCheck = (CheckBox) itemView.findViewById(R.id.playerCheck);

            //2ND PLAYER
            this.second_player_img = (ImageView) itemView.findViewById(R.id.second_player_img);
            this.second_player_Name = (TextView) itemView.findViewById(R.id.second_player_Name);
            this.second_player_position = (TextView) itemView.findViewById(R.id.second_player_position);
            this.second_player_Match = (TextView) itemView.findViewById(R.id.second_player_Match);
            this.second_player_Time = (TextView) itemView.findViewById(R.id.second_player_time);
            this.second_player_price = (Button) itemView.findViewById(R.id.second_player_price);
            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
            this.second_playerCheck = (CheckBox) itemView.findViewById(R.id.second_playerCheck);

            //Player ststistics
            this.playerstatsViewSpinner = (CardView) itemView.findViewById(R.id.playerstatsViewSpinner);
            this.playerStatisticsView = (LinearLayout) itemView.findViewById(R.id.playerStatisticsView);
            this.arrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.playerStatsRcView_child = (RecyclerView) itemView.findViewById(R.id.playerStatsRcView_child);

            //B OFF
            this.overCheckbox = (CheckBox) itemView.findViewById(R.id.overCheckbox);
            this.underCheckbox = (CheckBox) itemView.findViewById(R.id.underCheckbox);
            this.over_playercard = (RelativeLayout) itemView.findViewById(R.id.over_playercard);
            this.under_playercard = (RelativeLayout) itemView.findViewById(R.id.under_playercard);
            this.overview_txt = (TextView) itemView.findViewById(R.id.overview_txt);
            this.underView_txt = (TextView) itemView.findViewById(R.id.underView_txt);
            this.overView = (LinearLayout) itemView.findViewById(R.id.overView);
            this.underView = (LinearLayout) itemView.findViewById(R.id.underView);
            this.no_player = (CardView) itemView.findViewById(R.id.no_player);


            this.sq_cc =  itemView.findViewById(R.id.sq_cc);
            this.sq_cc_over =  itemView.findViewById(R.id.sq_cc_over);

        }

    }
}
