package com.sport.playsqorr.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.model.NewPlayerStatistics;
import com.sport.playsqorr.model.StatsPlayerStatistics;
import com.sport.playsqorr.pojos.Matchup;
import com.sport.playsqorr.pojos.PlayerB;
import com.sport.playsqorr.views.CustomLinearLayoutManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

//import static com.sport.playsqorr.views.MatchupScreen.initApiCall;


public class NewPlayerListAdapter_false extends RecyclerView.Adapter<NewPlayerListAdapter_false.PlayerHolder> {

    Context mContext;
    //    List<NewPlayerStatistics> mPlayersStatistics = new ArrayList<>();
    List<StatsPlayerStatistics> stats_ps = new ArrayList<>();

    boolean IsPurchased;
    private List<Matchup> matchups = new ArrayList<>();
    private HashMap<String, String> new_pick_index = new HashMap<>();
    private OnItemClick mCallback;
    PlayerB playerB;
    String values;
    private  RecyclerView playerGridView;
    public NewPlayerListAdapter_false(List<Matchup> matchups, PlayerB playerB, List<NewPlayerStatistics> mPlayersStatistics, List<StatsPlayerStatistics> stats_ps, boolean IsPurchased, Context mContext, OnItemClick mCallback, String values) {
//        this.mPlayersStatistics = mPlayersStatistics;
        this.stats_ps = stats_ps;
        this.mContext = mContext;
        this.IsPurchased = IsPurchased;
        this.matchups = matchups;
        this.mCallback = mCallback;
        this.playerB = playerB;
        this.values = values;
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

        //Filling the playA details


        try {
            if (values.equalsIgnoreCase("1")) {
                if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());

                } else if (matchups.get(position).getPlayerA().getFirstName() != null) {

                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                } else if (matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());
                }

                if (matchups.get(position).getPlayerA().getPositonName() != null) {
                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                }
                if (matchups.get(position).getPlayerA().getPointSpread() != null) {
                    playerHolder.first_player_price.setText(matchups.get(position).getPlayerA().getPointSpread() + "");
                }
                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.first_player_Match.setVisibility(View.VISIBLE);
                    playerHolder.first_player_Match.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.first_player_Match.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
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
                            .into(playerHolder.first_player_img);
                }
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.first_platytime.setVisibility(View.VISIBLE);
                        playerHolder.first_platytime.setText("Live");
                        playerHolder.first_platytime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.first_platytime.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.first_player_Time.setVisibility(View.GONE);
                        // playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.first_platytime.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setVisibility(View.VISIBLE);
                        //  playerHolder.play.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }

                }
                //Filling the PlayerB details

                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                    playerHolder.second_playerCard.setVisibility(View.GONE);
                    playerHolder.no_player.setVisibility(View.VISIBLE);
                    playerHolder.first_playerCard.setClickable(false);
                    playerHolder.tvPlayPoints.setText(matchups.get(position).getPlayerA().getPointSpread() + " ");

                } else {
                    playerHolder.second_playerCard.setVisibility(View.VISIBLE);
                    playerHolder.no_player.setVisibility(View.GONE);
                    playerHolder.first_playerCard.setClickable(true);
                    playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                    playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                    playerHolder.second_player_price.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                    if (matchups.get(position).getPlayerB().getVenue() != null) {
                        playerHolder.second_player_Match.setVisibility(View.VISIBLE);
                        playerHolder.second_player_Match.setText(matchups.get(position).getPlayerB().getVenue());
                    } else {
                        playerHolder.second_player_Match.setVisibility(View.INVISIBLE);
                    }
                    if (matchups.get(position).getPlayerB().getGameDate() != null) {
                        String live = matchups.get(position).getPlayerB().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.secondtime.setVisibility(View.VISIBLE);
                            //    playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.secondtime.setText("Live");
                            playerHolder.secondtime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.secondtime.setTextColor(mContext.getResources().getColor(R.color.white));


                            playerHolder.second_player_Time.setVisibility(View.GONE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            //     playerHolder.play.setVisibility(View.GONE);
                            playerHolder.second_player_Time.setVisibility(View.VISIBLE);
                            playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
//                        playerHolder.second_player_Time.setText("Live");
                    }
                    if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                .placeholder(R.drawable.game_inactive_placeholder)
                                .error(R.drawable.game_inactive_placeholder)
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
                }

            } else {

                if (matchups.get(position).getPlayerA().getIsLive().equalsIgnoreCase("true"))//|| matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true"))
                {
              //      initApiCall();
                    System.out.println("outes");
                    System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                    matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                    //  matchups.get(position).getPlayerA().setLastName(matchups.get(position).getPlayerA().getFirstName());

                }
                if (matchups.get(position).getPlayerB() != null) {
                    if (matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true")) {
             //           initApiCall();
                        System.out.println("outes");
                        System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                        matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                        //   matchups.get(position).getPlayerB().setLastName(matchups.get(position).getPlayerB().getFirstName());

                    }
                }

                if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());

                } else if (matchups.get(position).getPlayerA().getFirstName() != null) {

                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                } else if (matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());
                }

                if (matchups.get(position).getPlayerA().getPositonName() != null) {
                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                }
                if (matchups.get(position).getPlayerA().getPointSpread() != null) {
                    playerHolder.first_player_price.setText(matchups.get(position).getPlayerA().getPointSpread() + "");
                }
                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.first_player_Match.setVisibility(View.VISIBLE);
                    playerHolder.first_player_Match.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.first_player_Match.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
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
                            .into(playerHolder.first_player_img);
                }
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
//                    playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.first_platytime.setVisibility(View.VISIBLE);
                        playerHolder.first_platytime.setText("Live");
                        playerHolder.first_platytime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.first_platytime.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.first_player_Time.setVisibility(View.GONE);

                        //playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.first_platytime.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setVisibility(View.VISIBLE);
                        //   playerHolder.play.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }
                }
                playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                //Filling the PlayerB details

                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                    playerHolder.second_playerCard.setVisibility(View.GONE);
                    playerHolder.no_player.setVisibility(View.VISIBLE);
                    playerHolder.first_playerCard.setClickable(false);
                    playerHolder.tvPlayPoints.setText(matchups.get(position).getPlayerA().getPointSpread() + " ");

                } else {
                    playerHolder.second_playerCard.setVisibility(View.VISIBLE);
                    playerHolder.no_player.setVisibility(View.GONE);
                    playerHolder.first_playerCard.setClickable(true);
                    playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                    playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                    playerHolder.second_player_price.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                    if (matchups.get(position).getPlayerB().getVenue() != null) {
                        playerHolder.second_player_Match.setVisibility(View.VISIBLE);
                        playerHolder.second_player_Match.setText(matchups.get(position).getPlayerB().getVenue());
                    } else {
                        playerHolder.second_player_Match.setVisibility(View.INVISIBLE);
                    }
                    if (matchups.get(position).getPlayerB().getGameDate() != null) {
                        String live = matchups.get(position).getPlayerB().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.secondtime.setVisibility(View.VISIBLE);
                            //   playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.secondtime.setText("Live");
                            playerHolder.secondtime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.secondtime.setTextColor(mContext.getResources().getColor(R.color.white));


                            playerHolder.second_player_Time.setVisibility(View.GONE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            //         playerHolder.play.setVisibility(View.GONE);
                            playerHolder.second_player_Time.setVisibility(View.VISIBLE);
                            playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
//                        playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                    }
                    if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                .placeholder(R.drawable.game_inactive_placeholder)
                                .error(R.drawable.game_inactive_placeholder)
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
                }
            }
        } catch (Exception e) {

        }

/*
        Checking IsPurchased or not, if purchase player cound't clickble else can clickble
*/
        //if IsPurchased true
        /*
Checking picindex and win index of player A and PLayer B
*/
        if (IsPurchased) {
            try {

            } catch (Exception e) {
            }
        } else {
            playerHolder.win_loss_statusView_ly.setVisibility(View.GONE);
            playerHolder.first_playerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerHolder.no_player.getVisibility() == View.GONE) {
                        mHilightViews(playerHolder, 1, position);
                    }
                }
            });

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

        }

//            playerHolder.playerstatsViewSpinner.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//                    if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
//                        playerHolder.arrow.setRotation(180);
//                    } else {
//                        playerHolder.arrow.setRotation(360);
//                    }
//                }
//            });

        playerHolder.playerstatsViewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
                    playerHolder.arrow.setRotation(180);
                } else {
                    playerHolder.arrow.setRotation(360);
                }
                int size = matchups.size();
                System.out.println("size" + size + " " + position);
                int values = position + 1;
                if (size == values) {
                    System.out.println("size" + size + position);
//                        int values=position+1;
                    playerGridView.scrollToPosition(values);
                    playerGridView.scrollTo(position, values);
//                    playerGridView.setSelection(scorll);
                    playerGridView.setVerticalScrollbarPosition(values);
                    playerGridView.smoothScrollToPosition(values);
                }

            }
        });

//             NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext);
////        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
//            CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
//            playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);
//
//            /// ststsus
//            playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);

        if (matchups.get(position).getPlayerB() != null) {
            NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext, "1");
//        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
            CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
            playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);

            /// ststsus
            playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);
        } else {
            NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext, "2");
//        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
            CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
            playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);

            /// ststsus
            playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);
        }

    }

    private String getTime(String leagueTime) {
        String time = null;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentDate = f.format(new Date()).toString();
        String[] currentTime = currentDate.split(" ");
        String[] hoursMIns = currentTime[1].split(":");
        int curHour = Integer.parseInt(hoursMIns[0]);
        int curMins = Integer.parseInt(hoursMIns[1]);
        if (leagueTime != null && !leagueTime.equals("")) {
            String[] timeArray = leagueTime.split("T");
            if (timeArray.length > 0) {
                String lg_time = timeArray[1];
                String[] hourArray = lg_time.split("\\.");
                if (hourArray.length > 0) {
                    String[] final_time = hourArray[0].split(":");

                    if (final_time.length > 0) {
                        int lg_hour = Integer.parseInt(final_time[0]);
                        int lg_mins = Integer.parseInt(final_time[1]);

                        int diffHours = lg_hour - curHour;
                        int diffMins = lg_mins - curMins;
                        if (diffHours > 0) {
                            if (diffMins > 0)
                                time = diffHours + "h " + diffMins + "m";
                            else
                                time = diffHours + "h";
                        } else {
                            if (diffMins > 0)
                                time = diffMins + "m";
                        }
                    }
                }
            }
        } else {
            time = "";
        }

        return time;
    }


    private void mHilightViews(PlayerHolder playerHolder, int from, int position) {
        if (from == 1) {
            if (playerHolder.first_playerCheck.isChecked()) {
                // PLAYER A REMOVED
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.first_playerCheck.setChecked(false);
                setFirstPlayerTextEnableDisable(playerHolder, false);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
                if (playerHolder.second_playerCheck.isChecked()) {
                    //PLAYER B REMOVED
                    playerHolder.second_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setSecondPlayerTextDisableEnableMode(playerHolder, false);
                }
                //PLAYER A ADDED
                playerHolder.first_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setFirstPlayerTextEnableDisable(playerHolder, true);
            }
        } else if (from == 2) {
            if (playerHolder.second_playerCheck.isChecked()) {
                //PLAYER B REMOVED
                playerHolder.second_playerCheck.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setSecondPlayerTextDisableEnableMode(playerHolder, false);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {

                if (playerHolder.first_playerCheck.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.first_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setFirstPlayerTextEnableDisable(playerHolder, false);
                }
                //PLAYER B ADDED
                playerHolder.second_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setSecondPlayerTextDisableEnableMode(playerHolder, true);
            }
        } else if (from == 3) {
            if (playerHolder.overCheckbox.isChecked()) {
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.overCheckbox.setChecked(false);
                setAPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.underCheckbox.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.underCheckbox.setChecked(false);
                    setBPlayerTextDisableEnableMode(playerHolder, false);
                }
                playerHolder.overCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setAPlayerTextDisableEnableMode(playerHolder, true);
            }
        } else if (from == 4) {
            if (playerHolder.underCheckbox.isChecked()) {

                playerHolder.underCheckbox.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setBPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.overCheckbox.isChecked()) {
                    playerHolder.overCheckbox.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setAPlayerTextDisableEnableMode(playerHolder, false);
                }

                playerHolder.underCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setBPlayerTextDisableEnableMode(playerHolder, true);
            }
        }
    }


    private void setAPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setBPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));

        }
        mCallback.onClick(new_pick_index);


    }

    private void setFirstPlayerTextEnableDisable(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.first_player_Name, mContext.getResources().getColor(R.color.text_color_new));

        } else {
            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);

    }

    private void setSecondPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.second_player_Name, mContext.getResources().getColor(R.color.text_color_new));
        } else {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));

        }
        mCallback.onClick(new_pick_index);


    }


    @Override
    public int getItemCount() {
        return matchups.size();
    }


    @SuppressLint("NewApi")
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

    @SuppressLint("NewApi")
    private void setTextViewDrawableColor(TextView textView, int color) {
        textView.setCompoundDrawableTintList(ColorStateList.valueOf(color));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnItemClick {
        void onClick(HashMap<String, String> matchup_selections);
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {

        //1St PLayer
        ImageView first_player_img;
        TextView first_player_Name;
        TextView first_player_Time;
        TextView first_player_position;
        TextView first_player_Match;
        Button first_player_price;
        CardView first_playerCard;
        CheckBox first_playerCheck;
        TextView first_platytime;

        //2nd player
        ImageView second_player_img;
        TextView second_player_Name;
        TextView second_player_Time;
        TextView second_player_position;
        TextView second_player_Match;
        Button second_player_price;
        CardView second_playerCard;
        CheckBox second_playerCheck;
        TextView secondtime;

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
        TextView tvPlayPoints;

        /*win loss status View*/
        FrameLayout win_loss_statusView_ly;
        CardView win_loss_statusView;
        TextView statusTxt;
        //  TextView play;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            // PLAYER A
            this.first_platytime = (TextView) itemView.findViewById(R.id.player_time1);
            this.first_player_img = (ImageView) itemView.findViewById(R.id.player_img);
            this.first_player_Name = (TextView) itemView.findViewById(R.id.player_Name);
            this.first_player_position = (TextView) itemView.findViewById(R.id.player_position);
            this.first_player_Match = (TextView) itemView.findViewById(R.id.player_Match);
            this.first_player_Time = (TextView) itemView.findViewById(R.id.player_time);
            this.first_player_price = (Button) itemView.findViewById(R.id.player_price);
            this.first_playerCard = (CardView) itemView.findViewById(R.id.playerCard);
            this.first_playerCheck = (CheckBox) itemView.findViewById(R.id.playerCheck);

            // PLAYER B
            this.second_player_img = (ImageView) itemView.findViewById(R.id.second_player_img);
            this.second_player_Name = (TextView) itemView.findViewById(R.id.second_player_Name);
            this.second_player_position = (TextView) itemView.findViewById(R.id.second_player_position);
            this.second_player_Match = (TextView) itemView.findViewById(R.id.second_player_Match);
            this.second_player_Time = (TextView) itemView.findViewById(R.id.second_player_time);
            this.second_player_price = (Button) itemView.findViewById(R.id.second_player_price);
            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
            this.second_playerCheck = (CheckBox) itemView.findViewById(R.id.second_playerCheck);
            this.secondtime = (TextView) itemView.findViewById(R.id.second_player_time1);
            //Players ststistics
            this.playerstatsViewSpinner = (CardView) itemView.findViewById(R.id.playerstatsViewSpinner);
            this.playerStatisticsView = (LinearLayout) itemView.findViewById(R.id.playerStatisticsView);
            this.arrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.playerStatsRcView_child = (RecyclerView) itemView.findViewById(R.id.playerStatsRcView_child);

            //PlayerB OFF
            this.overCheckbox = (CheckBox) itemView.findViewById(R.id.overCheckbox);
            this.underCheckbox = (CheckBox) itemView.findViewById(R.id.underCheckbox);
            this.over_playercard = (RelativeLayout) itemView.findViewById(R.id.over_playercard);
            this.under_playercard = (RelativeLayout) itemView.findViewById(R.id.under_playercard);
            this.overview_txt = (TextView) itemView.findViewById(R.id.overview_txt);
            this.underView_txt = (TextView) itemView.findViewById(R.id.underView_txt);
            this.overView = (LinearLayout) itemView.findViewById(R.id.overView);
            this.underView = (LinearLayout) itemView.findViewById(R.id.underView);
            this.no_player = (CardView) itemView.findViewById(R.id.no_player);
            this.tvPlayPoints = (TextView) itemView.findViewById(R.id.tvPlayPoints);


            this.win_loss_statusView_ly = (FrameLayout) itemView.findViewById(R.id.win_loss_statusView_ly);
            this.win_loss_statusView = (CardView) itemView.findViewById(R.id.win_loss_statusView);
            this.statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            //  this.play=(TextView) itemView.findViewById(R.id.play);

        }

    }

}
