package com.sport.playsqorr.adapters;


import android.annotation.SuppressLint;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static com.sport.playsqorr.views.PlayPickGo_MatchupScreen.playerGridView;

//import static com.sport.playsqorr.views.MatchupScreen.initApiCall;

public class PlayPickGo_Adapter extends RecyclerView.Adapter<PlayPickGo_Adapter.PlayerHolder> {

    Context mContext;
    //    List<NewPlayerStatistics> mPlayersStatistics = new ArrayList<>();
    List<StatsPlayerStatistics> stats_ps = new ArrayList<>();

    boolean IsPurchased;
    private List<Matchup> matchups = new ArrayList<>();
    private HashMap<String, String> new_pick_index = new HashMap<>();
    private OnItemClick mCallback;
    PlayerB playerB;
    String values;

    TextView im1;
    TextView im2, im3, im4, im5, im6, im7, im8, im9;
    TextView imb1, imb2, imb3, imb4, imb5, imb6, imb7, imb8, imb9;

    JSONArray bingo_array;
    JSONArray jjnewA;

    public PlayPickGo_Adapter(List<Matchup> matchups, PlayerB playerB, List<NewPlayerStatistics> mPlayersStatistics, List<StatsPlayerStatistics> stats_ps,
                              boolean IsPurchased, Context mContext, TextView go_1, TextView go_2, TextView go_3, TextView go_4, TextView go_5, TextView go_6, TextView go_7, TextView go_8, TextView go_9, TextView i1,
                              TextView i2, TextView i3, TextView i4, TextView i5, TextView i6, TextView i7, TextView i8, TextView i9, JSONArray binge_ja, JSONArray jjnewA, OnItemClick mCallback, String values) {
//        this.mPlayersStatistics = mPlayersStatistics;
        this.stats_ps = stats_ps;
        this.mContext = mContext;
        this.IsPurchased = IsPurchased;
        this.matchups = matchups;
        this.mCallback = mCallback;
        this.playerB = playerB;
        this.values = values;
        this.im1 = i1;
        this.im2 = i2;
        this.im3 = i3;
        this.im4 = i4;
        this.im5 = i5;
        this.im6 = i6;
        this.im7 = i7;
        this.im8 = i8;
        this.im9 = i9;
        this.imb1 = go_1;
        this.imb2 = go_2;
        this.imb3 = go_3;
        this.imb4 = go_4;
        this.imb5 = go_5;
        this.imb6 = go_6;
        this.imb7 = go_7;
        this.imb8 = go_8;
        this.imb9 = go_9;
        this.bingo_array = binge_ja;
        this.jjnewA = jjnewA;
    }

   /* public PlayPickGo_Adapter(List<Matchup> matchups, PlayerB playerB, List<NewPlayerStatistics> mNewPlayerStatisticsList, List<StatsPlayerStatistics> stats_ps, boolean k_t, PlayPickGo_MatchupScreen mContext, ImageView go_1, OnItemClick ready_to_play, String s) {
        this.stats_ps = stats_ps;
        this.mContext = mContext;
        this.IsPurchased = IsPurchased;
        this.matchups = matchups;
        this.mCallback = mCallback;
        this.playerB = playerB;
        this.values = values;
        this.im = go_1;
    }
*/

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.playpickgo_adapter, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerHolder playerHolder, final int position) {

        //Filling the playA details

        if (position == 0) {

            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo1));
        }
        if (position == 1) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo2));
        }
        if (position == 2) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo3));
        }
        if (position == 3) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo4));
        }
        if (position == 4) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo5));
        }
        if (position == 5) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo6));
        }
        if (position == 6) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo7));
        }
        if (position == 7) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo8));
        }
        if (position == 8) {
            playerHolder.go1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dummygo9));
        }


        if (matchups.get(position).getPickIndex() == 1) {


//            if (position == 0) {
//                im1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 1) {
//                im2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 2) {
//                im3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 3) {
//                im4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 4) {
//                im5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 5) {
//                im6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 6) {
//                im7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 7) {
//                im8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
//            if (position == 8) {
//                im9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//            }
        }


        //----------------------

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

                                .into(playerHolder.second_player_img);

                    }
                }

            } else {

                if (matchups.get(position).getPlayerA().getIsLive().equalsIgnoreCase("true"))//|| matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true"))
                {
                    // initApiCall();
                    System.out.println("outes");
                    System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                    matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                    //  matchups.get(position).getPlayerA().setLastName(matchups.get(position).getPlayerA().getFirstName());

                }
                if (matchups.get(position).getPlayerB() != null) {
                    if (matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true")) {
                        //  initApiCall();
                        System.out.println("outes");
                        System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                        matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                        //   matchups.get(position).getPlayerB().setLastName(matchups.get(position).getPlayerB().getFirstName());

                    }
                }
        /*    matchups.get(position).getPlayerstatus();
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
                playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
            }
            if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                        .placeholder(R.drawable.game_inactive_placeholder)
                        .error(R.drawable.game_inactive_placeholder)
                        
                        .into(playerHolder.second_player_img);

            }

            playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
           Log.d("Lastname",matchups.get(position).getPlayerB().getPlayerImage());
            Log.d("Lastname",matchups.get(position).getPlayerB().getFirstName());*/
//            Log.d("fristname",matchups.get(position).getPlayerA()+matchups.get(position).getPlayerA().getFirstName()+matchups.get(position).getPlayerA().getPointSpread());
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


                //   try {
//                    for (int i = 0; i < bingo_array.length(); i++) {
//
//                        JSONObject bin = bingo_array.getJSONObject(i);
//                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
//
//                            playerHolder.play_bingo_left.setText(bin.getString("leftPlayerNumber"));
//                            playerHolder.play_bingo_right.setText(bin.getString("rightPlayerNumber"));
//
//                        }
//
//
//                    }

//                    if (matchups.get(position).getPlayerB() != null) {
//
//                    }else{
//
//                    }


//                    } catch (JSONException e) {
//                    e.printStackTrace();
                //   }


                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                    playerHolder.play_bingo_left.setText(matchups.get(position).getPlayerA().getBingoPlayerNumber());
                    playerHolder.play_bingo_right.setText(matchups.get(position).getPlayerB().getBingoPlayerNumber());
                } else {
                    playerHolder.play_bingo_left.setText(matchups.get(position).getPlayerA().getBingoPlayerNumber());
                    playerHolder.play_bingo_right.setText("");
                }

                Log.e("539--", jjnewA.toString());
                //---
                //     if (matchups.get(position).getPlayerB() != null) {
                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                    try {
                        for (int i = 0; i < jjnewA.length(); i++) {


                            JSONObject bin = jjnewA.getJSONObject(i);
                            JSONObject bin1 = jjnewA.getJSONObject(0);
                            JSONObject bin2 = jjnewA.getJSONObject(1);
                            JSONObject bin3 = jjnewA.getJSONObject(2);
                            JSONObject bin4 = jjnewA.getJSONObject(3);
                            JSONObject bin5 = jjnewA.getJSONObject(4);
                            JSONObject bin6 = jjnewA.getJSONObject(5);
                            JSONObject bin7 = jjnewA.getJSONObject(6);
                            JSONObject bin8 = jjnewA.getJSONObject(7);
                            JSONObject bin9 = jjnewA.getJSONObject(8);

//                    if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
//
//
//                    }

                            if (bin1.length() >= 4) {
                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber"));
                            }

                            if (bin2.length() >= 4) {
                                playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber"));
                            }
                            if (bin3.length() >= 4) {
                                playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber"));
                            }
                            if (bin4.length() >= 4) {
                                playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber"));
                            }
                            if (bin5.length() >= 4) {
                                playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber"));
                            }
                            if (bin6.length() >= 4) {
                                playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber"));
                            }
                            if (bin7.length() >= 4) {
                                playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber"));
                            }
                            if (bin8.length() >= 4) {
                                playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber"));
                            }
                            if (bin9.length() >= 4) {
                                playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                            } else {
                                playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber"));
                            }


//                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//
//                            }

//
//                            playerHolder.bc_1.setText(bin2.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
//                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
//                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
//                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
//                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
//                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
//                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
//                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
//                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));

                            if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                                if (i == 0) {

                                    playerHolder.bc_1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_1.setTextColor(mContext.getResources().getColor(R.color.white));

                                } else if (i == 1) {
                                    playerHolder.bc_2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_2.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 2) {
                                    playerHolder.bc_3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_3.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 3) {
                                    playerHolder.bc_4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_4.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 4) {
                                    playerHolder.bc_5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_5.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 5) {
                                    playerHolder.bc_6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_6.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 6) {
                                    playerHolder.bc_7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_7.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 7) {
                                    playerHolder.bc_8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_8.setTextColor(mContext.getResources().getColor(R.color.white));
                                } else if (i == 8) {
                                    playerHolder.bc_9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                    playerHolder.bc_9.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                            }

//                    if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
//
//                        playerHolder.play_bingo_left.setText(bin.getString("leftPlayerNumber"));
//                        playerHolder.play_bingo_right.setText(bin.getString("rightPlayerNumber"));
//
//
//
//                    }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//            NewPLayerBingoStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerBingoStatisticsAdapter(bingo_array,position, mContext, "1");
////        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
//          //  CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
//          //  playerHolder.playerStatsRcView_child_bingo.setLayoutManager(gridLayoutManager);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
//            gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
//            playerHolder.playerStatsRcView_child_bingo.setLayoutManager(gridLayoutManager);
//            /// ststsus
//            playerHolder.playerStatsRcView_child_bingo.setAdapter(pLayerStatisticsAdapter);


                } else {
                    for (int i = 0; i < jjnewA.length(); i++) {


                        JSONObject bin = jjnewA.getJSONObject(i);
                        JSONObject bin1 = jjnewA.getJSONObject(0);
                        JSONObject bin2 = jjnewA.getJSONObject(1);
                        JSONObject bin3 = jjnewA.getJSONObject(2);
                        JSONObject bin4 = jjnewA.getJSONObject(3);
                        JSONObject bin5 = jjnewA.getJSONObject(4);
                        JSONObject bin6 = jjnewA.getJSONObject(5);
                        JSONObject bin7 = jjnewA.getJSONObject(6);
                        JSONObject bin8 = jjnewA.getJSONObject(7);
                        JSONObject bin9 = jjnewA.getJSONObject(8);

                        if (bin1.length() >= 4) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber"));
                        }

                        if (bin2.length() >= 4) {
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber"));
                        }
                        if (bin3.length() >= 4) {
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber"));
                        }
                        if (bin4.length() >= 4) {
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber"));
                        }
                        if (bin5.length() >= 4) {
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber"));
                        }
                        if (bin6.length() >= 4) {
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber"));
                        }
                        if (bin7.length() >= 4) {
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber"));
                        }
                        if (bin8.length() >= 4) {
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber"));
                        }
                        if (bin9.length() >= 4) {
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else {
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber"));
                        }

                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                            if (i == 0) {

                                playerHolder.bc_1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_1.setTextColor(mContext.getResources().getColor(R.color.white));

                            } else if (i == 1) {
                                playerHolder.bc_2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_2.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 2) {
                                playerHolder.bc_3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_3.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 3) {
                                playerHolder.bc_4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_4.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 4) {
                                playerHolder.bc_5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_5.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 5) {
                                playerHolder.bc_6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_6.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 6) {
                                playerHolder.bc_7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_7.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 7) {
                                playerHolder.bc_8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_8.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 8) {
                                playerHolder.bc_9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_9.setTextColor(mContext.getResources().getColor(R.color.white));
                            }
                        }
                    }
                }

                //---


                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                if (matchups.get(position).getIsFinished()) {

                    playerHolder.win_loss_statusView.setVisibility(View.VISIBLE);
                    playerHolder.statusTxt.setVisibility(View.VISIBLE);

//                    playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.basket_ball_color_org));
//                    playerHolder.statusTxt.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));


                    /*  if winindex=0,pickindex=0 playerA checked Green*/
                    if (matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 0) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//                        if (matchups.get(position).getPlayerB() != null) {
//                            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            playerHolder.left_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
                        } else {
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
//                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));

                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));

                        }
                    }
                    /*  if winindex=1,pickindex=1 playerB checked Green*/
                    if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 1) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");
//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }

                            playerHolder.right_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
//                            playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
//                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));

                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));


//                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
////                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
//                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
//                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                        }
                    }
                    /*  if winindex=0,pickindex=1 or winindex=0,pickindex=-1   playerA checked gray*/
                    if ((matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 1)) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");
//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                            Log.e("bnull", "not null---");
//                            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.left_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }

                            playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            Log.e("bnullnot", "null---");
//                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                        }
                    }
                    if ((matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == -1) && matchups.get(position).getIsCancelled()) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        if (matchups.get(position).getCancelledReason() != null) {
                            playerHolder.statusTxt.setText(matchups.get(position).getCancelledReason());
                        } else {
                            playerHolder.statusTxt.setText("CANCELLED");
                        }


//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }

                            playerHolder.left_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
//                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
//                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_win));
//                            }
                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                        }

                    }
                    if ((matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == -1) && matchups.get(position).getIsCancelled()) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        if (matchups.get(position).getCancelledReason() != null) {
                            playerHolder.statusTxt.setText(matchups.get(position).getCancelledReason());
                        } else {
                            playerHolder.statusTxt.setText("CANCELLED");
                        }
//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }

                            playerHolder.right_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                        } else {
//                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

                        }

                    }

                    /*  if winindex=1,pickindex=0 or winindex=1,pickindex=-1   playerB checked gray*/
                    if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 0) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");
//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            if (position == 0) {
//                                im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 1) {
//                                im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 2) {
//                                im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 3) {
//                                im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 4) {
//                                im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 5) {
//                                im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 6) {
//                                im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 7) {
//                                im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }
//                            if (position == 8) {
//                                im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_loss));
//                            }

                            playerHolder.right_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                        } else {
//                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
//                            playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));


                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));

                        }
                    }
                } else {

                    playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
//

//                    Toast.makeText(context, "120--", Toast.LENGTH_LONG).show();

//                    if (position == 0) {
//                        im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 1) {
//                        im2.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 2) {
//                        im3.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 3) {
//                        im4.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 4) {
//                        im5.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 5) {
//                        im6.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 6) {
//                        im7.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 7) {
//                        im8.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }
//                    if (position == 8) {
//                        im9.setImageDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                    }


                  /*  if (matchups.get(position).getPickIndex() == 0 || matchups.get(position).getPickIndex() == 1) {

                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.base_ball_color_org));
                        playerHolder.statusTxt.setText("STARTS IN " + getTime(matchups.get(position).getPlayerA().getGameDate()));


                    }*/

                    Log.e("277---", "277----not played");
                    /*  if played false,pickindex=0 playerA checked */
                    if (matchups.get(position).getPickIndex() == 0) {

//                        Toast.makeText(context,"120-1-",Toast.LENGTH_LONG).show();

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.left_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));

//                            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                        }
                    }

                    /*  if played =flase,pickindex=1 playerB checked */
                    if (matchups.get(position).getPickIndex() == 1) {
//                        Toast.makeText(context,"120-2-",Toast.LENGTH_LONG).show();

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.right_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                            //  playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                        }
                    }
                }

            } catch (Exception e) {
            }
        } else {

            //  New cards no Upcoming
/*
            //--------------
            if (matchups.get(position).getPickIndex() == 0) {

                this.im.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
            }
            if (matchups.get(position).getPickIndex() == 1) {

            }
            if (matchups.get(position).getPickIndex() == 2) {

            }
            if (matchups.get(position).getPickIndex() == 3) {

            }
            if (matchups.get(position).getPickIndex() == 4) {

            }
            if (matchups.get(position).getPickIndex() == 5) {

            }
            if (matchups.get(position).getPickIndex() == 6) {

            }
            if (matchups.get(position).getPickIndex() == 7) {

            }
            if (matchups.get(position).getPickIndex() == 8) {

            }
*/

          /*  if (!playerHolder.first_playerCheck.isChecked() && !playerHolder.first_playerCheck.isChecked()) {
                this.im1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_inactive_placeholder));
                this.im2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                this.im9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

            }*/

//kalyan bingo
            try {
                for (int i = 0; i < bingo_array.length(); i++) {

                    JSONObject bin = bingo_array.getJSONObject(i);
                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                            // if(matchups.get(position).getPlayerB() != null) {
                            //  if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                            playerHolder.play_bingo_left.setText(bin.getString("leftPlayerNumber"));
                            playerHolder.play_bingo_right.setText(bin.getString("rightPlayerNumber"));
//                            }else{
//                                playerHolder.play_bingo_left.setText(bin.getString("rightPlayerNumber"));
//                            }

                        }
                    } else {
                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                            playerHolder.play_bingo_left.setText(bin.getString("leftPlayerNumber"));
                            playerHolder.play_bingo_right.setText("");
                        }
                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //       if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
            //    if (matchups.get(position).getPlayerB() != null) {

            try {

                Log.e("1431---", bingo_array.toString());

                // getMid(matchups.get(position).getPlayerstatus().equalsIgnoreCase("1"));

                //    JSONArray New_Bingo = new JSONArray();
                List<String> Bsting = new ArrayList<>();
                for (int i = 0; i < matchups.size(); i++) {
                    JSONObject t1 = bingo_array.getJSONObject(i);

                    if (matchups.get(i).getPlayerstatus().equalsIgnoreCase("1")) {
                        Bsting.add(t1.getString("leftPlayerNumber") + " , " + t1.getString("rightPlayerNumber"));
                        // playerHolder.bc_1.setText();
                        //    t1.getString("leftPlayerNumber") + " , " + t1.getString("rightPlayerNumber");

                        //   New_Bingo.put(t1);
                    } else {
                        Bsting.add(t1.getString("leftPlayerNumber"));
                    }
                }

                Log.e("1449---", Bsting.toString());


                playerHolder.bc_1.setText(Bsting.get(0));
                playerHolder.bc_2.setText(Bsting.get(1));
                playerHolder.bc_3.setText(Bsting.get(2));
                playerHolder.bc_4.setText(Bsting.get(3));
                playerHolder.bc_5.setText(Bsting.get(4));
                playerHolder.bc_6.setText(Bsting.get(5));
                playerHolder.bc_7.setText(Bsting.get(6));
                playerHolder.bc_8.setText(Bsting.get(7));
                playerHolder.bc_9.setText(Bsting.get(8));


                for (int i = 0; i < bingo_array.length(); i++) {


                    JSONObject bin = bingo_array.getJSONObject(i);
                    JSONObject bin1 = bingo_array.getJSONObject(0);
                    JSONObject bin2 = bingo_array.getJSONObject(1);
                    JSONObject bin3 = bingo_array.getJSONObject(2);
                    JSONObject bin4 = bingo_array.getJSONObject(3);
                    JSONObject bin5 = bingo_array.getJSONObject(4);
                    JSONObject bin6 = bingo_array.getJSONObject(5);
                    JSONObject bin7 = bingo_array.getJSONObject(6);
                    JSONObject bin8 = bingo_array.getJSONObject(7);
                    JSONObject bin9 = bingo_array.getJSONObject(8);


//                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//                        Log.e("1114---1",matchups.get(position).get_id());
//                        String iid = matchups.get(position).get_id();
//                        if(iid.equalsIgnoreCase(bin.getString("matchupId"))){
//                            bin.getString("leftPlayerNumber");
//                        }
//                    }else{
//                        Log.e("1114---0",matchups.get(position).get_id());
//                    }

              /*      if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {// NOT NULL B MATCTHUP
                        playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                        playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                        playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                        playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                        playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                        playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                        playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                        playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                        playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                    } else { //OVERUNDER
                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
                        if (i == 0) {

                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));

                        } else if (i == 1) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 2) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 3) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 4) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 5) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 6) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 7) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") + " , " + bin9.getString("rightPlayerNumber"));
                        } else if (i == 8) {
                            playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));
                            playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") + " , " + bin3.getString("rightPlayerNumber"));
                            playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") + " , " + bin4.getString("rightPlayerNumber"));
                            playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") + " , " + bin5.getString("rightPlayerNumber"));
                            playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") + " , " + bin6.getString("rightPlayerNumber"));
                            playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") + " , " + bin7.getString("rightPlayerNumber"));
                            playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") + " , " + bin8.getString("rightPlayerNumber"));
                            playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber"));
                        }
                    }

//                        playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") );
//                        playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") );
//                        playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber") );
//                        playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") );
//                        playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber"));
//                        playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber"));
//                        playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") );
//                        playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") );
//                        playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") );
                    }
*/


//                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//
//                    }else{
//                        playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") );
//                        playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber"));
//                        playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber"));
//                        playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber") );
//                        playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber") );
//                        playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber") );
//                        playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber") );
//                        playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber") );
//                        playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber") );
//                    }


//                    if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
//
//                        if (i == 0) {
//                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
//
//                            }else{
//                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber"));
//
//                            }
//                        }
//                        } else if (i == 1) {
//
//                        } else if (i == 2) {
//                        } else if (i == 3) {
//
//                        } else if (i == 4) {
//                        } else if (i == 5) {
//                        }

                  /*  if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
                        if (i == 0) {
                            if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + " , " + bin1.getString("rightPlayerNumber"));
                            }
                        } else if (i == 1) {
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + " , " + bin2.getString("rightPlayerNumber"));

                        }

                    } else {
                        if (i == 0) {
                            if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
                                playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber") + "  ");
                            }
                        } else if (i == 1) {
                            playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber") + "  ");

                        }
                    }
*/

                    if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                        if (i == 0) {

                            playerHolder.bc_1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_1.setTextColor(mContext.getResources().getColor(R.color.white));

                        } else if (i == 1) {
                            playerHolder.bc_2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_2.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 2) {
                            playerHolder.bc_3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_3.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 3) {
                            playerHolder.bc_4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_4.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 4) {
                            playerHolder.bc_5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_5.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 5) {
                            playerHolder.bc_6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_6.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 6) {
                            playerHolder.bc_7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_7.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 7) {
                            playerHolder.bc_8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_8.setTextColor(mContext.getResources().getColor(R.color.white));
                        } else if (i == 8) {
                            playerHolder.bc_9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                            playerHolder.bc_9.setTextColor(mContext.getResources().getColor(R.color.white));
                        }
                    }

//

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //     } else {

/*
                try {
                    for (int i = 0; i < bingo_array.length(); i++) {


                        JSONObject bin = bingo_array.getJSONObject(i);
                        JSONObject bin1 = bingo_array.getJSONObject(0);
                        JSONObject bin2 = bingo_array.getJSONObject(1);
                        JSONObject bin3 = bingo_array.getJSONObject(2);
                        JSONObject bin4 = bingo_array.getJSONObject(3);
                        JSONObject bin5 = bingo_array.getJSONObject(4);
                        JSONObject bin6 = bingo_array.getJSONObject(5);
                        JSONObject bin7 = bingo_array.getJSONObject(6);
                        JSONObject bin8 = bingo_array.getJSONObject(7);
                        JSONObject bin9 = bingo_array.getJSONObject(8);

//                    if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {
//
//
//                    }
                        playerHolder.bc_1.setText(bin1.getString("leftPlayerNumber"));
                        playerHolder.bc_2.setText(bin2.getString("leftPlayerNumber"));
                        playerHolder.bc_3.setText(bin3.getString("leftPlayerNumber"));
                        playerHolder.bc_4.setText(bin4.getString("leftPlayerNumber"));
                        playerHolder.bc_5.setText(bin5.getString("leftPlayerNumber"));
                        playerHolder.bc_6.setText(bin6.getString("leftPlayerNumber"));
                        playerHolder.bc_7.setText(bin7.getString("leftPlayerNumber"));
                        playerHolder.bc_8.setText(bin8.getString("leftPlayerNumber"));
                        playerHolder.bc_9.setText(bin9.getString("leftPlayerNumber"));

                        if (matchups.get(position).get_id().equalsIgnoreCase(bin.getString("matchupId"))) {

                            if (i == 0) {

                                playerHolder.bc_1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_1.setTextColor(mContext.getResources().getColor(R.color.white));

                            } else if (i == 1) {
                                playerHolder.bc_2.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_2.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 2) {
                                playerHolder.bc_3.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_3.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 3) {
                                playerHolder.bc_4.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_4.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 4) {
                                playerHolder.bc_5.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_5.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 5) {
                                playerHolder.bc_6.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_6.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 6) {
                                playerHolder.bc_7.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_7.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 7) {
                                playerHolder.bc_8.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_8.setTextColor(mContext.getResources().getColor(R.color.white));
                            } else if (i == 8) {
                                playerHolder.bc_9.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edit_border));
                                playerHolder.bc_9.setTextColor(mContext.getResources().getColor(R.color.white));
                            }
                        }

//

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            //     }

            playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
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

        // PLayer status
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
                Log.e("size", size + "---00----- " + position);
                int values = position + 1;
                if (size == values) {
                    Log.e("size", size + "-----1--" + (position + 1));
                    int values1 = position + 1;
                    playerGridView.scrollToPosition(values1);
//                    playerGridView.scrollscrollToPositionWithOffset(position,values1);
//                    playerGridView.scrollTo(position, values1);
//                    playerGridView.setSelection(scorll);
                    playerGridView.setVerticalScrollbarPosition(values1);
                    playerGridView.smoothScrollToPosition(values1);
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


        //BINGO
        playerHolder.playerstatsViewSpinner_bingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerHolder.playerStatisticsView_bingo.setVisibility(playerHolder.playerStatisticsView_bingo.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (playerHolder.playerStatisticsView_bingo.getVisibility() == View.VISIBLE) {
                    playerHolder.arrow_bingo.setRotation(180);
                } else {
                    playerHolder.arrow_bingo.setRotation(360);
                }
                int size = matchups.size();
                Log.e("size", size + "---00----- " + position);
                int values = position + 1;
                if (size == values) {
                    Log.e("size", size + "-----1--" + (position + 1));
                    int values1 = position + 1;
                    playerGridView.scrollToPosition(values1);
//                    playerGridView.scrollscrollToPositionWithOffset(position,values1);
//                    playerGridView.scrollTo(position, values1);
//                    playerGridView.setSelection(scorll);
                    playerGridView.setVerticalScrollbarPosition(values1);
                    playerGridView.smoothScrollToPosition(values1);
                }

            }
        });


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

                if (position == 0) {
                    Log.e("0---------", "" + (matchups.get(position).getPlayerA().getUniformNumber()));
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));

//                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                    im2.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                    im3.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                    im4.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                    im5.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                    im6.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                    im7.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                    im8.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                    im9.setText(matchups.get(position).getPlayerA().getUniformNumber());

                }
                // PLAYER A REMOVED
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());


                }
                playerHolder.first_playerCheck.setChecked(false);
                setFirstPlayerTextEnableDisable(playerHolder, false, position);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));

                if (position == 0) {
                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_empty));
//                    im1.setText("");

                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im2.setText("");
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im3.setText("");
                }
                if (position == 3) {
//                    im4.setText("");
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 4) {
//                    im5.setText("");
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 5) {
//                    im6.setText("");
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 6) {
//                    im7.setText("");
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 7) {
//                    im8.setText("");
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 8) {
//                    im9.setText("");
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
            } else {
                if (position == 0) {
                    //   im1.setText(matchups.get(position).getPlayerA().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                    //     im2.setText(matchups.get(position).getPlayerA().getUniformNumber());
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }

                if (playerHolder.second_playerCheck.isChecked()) {
                    //PLAYER B REMOVED
                    playerHolder.second_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setSecondPlayerTextDisableEnableMode(playerHolder, false, position);

                }
                //PLAYER A ADDED
                playerHolder.first_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setFirstPlayerTextEnableDisable(playerHolder, true, position);
            }
        } else if (from == 2) {
            if (playerHolder.second_playerCheck.isChecked()) {
                //PLAYER B REMOVED
                playerHolder.second_playerCheck.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }

                if (position == 0) {
                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_empty));
//                    im1.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im2.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                setSecondPlayerTextDisableEnableMode(playerHolder, false, position);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {

                if (position == 0) {
                    im1.setText(matchups.get(position).getPlayerB().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                    im3.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                    im4.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                    im5.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                    im6.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                    im7.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                    im8.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                    im9.setText(matchups.get(position).getPlayerB().getUniformNumber());
                }
                if (playerHolder.first_playerCheck.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.first_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setFirstPlayerTextEnableDisable(playerHolder, false, position);
                }
                //PLAYER B ADDED
                playerHolder.second_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setSecondPlayerTextDisableEnableMode(playerHolder, true, position);
            }
        } else if (from == 3) {
            if (playerHolder.overCheckbox.isChecked()) {

                if (position == 0) {
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }
                // PLAYER A REMOVED
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());


                }
                playerHolder.overCheckbox.setChecked(false);
                setAPlayerTextDisableEnableMode(playerHolder, false);

                if (position == 0) {
                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_empty));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
            } else {
                if (position == 0) {
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }

                if (playerHolder.underCheckbox.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.underCheckbox.setChecked(false);
                    setBPlayerTextDisableEnableMode(playerHolder, false);
                }
                playerHolder.overCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setAPlayerTextDisableEnableMode(playerHolder, true);
            }
            /*if (playerHolder.overCheckbox.isChecked()) {

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
            }*/
        } else if (from == 4) {
            if (playerHolder.underCheckbox.isChecked()) {

                if (position == 0) {
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                    im2.setText(matchups.get(position).getPlayerA().getUniformNumber());
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }
                playerHolder.underCheckbox.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }


                setBPlayerTextDisableEnableMode(playerHolder, false);

                if (position == 0) {
                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
//                    im1.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cell_empty));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.gray_pick));
                }
            } else {
                if (position == 0) {
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());

                    im1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }
                if (position == 1) {
                    im2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                }
                if (position == 2) {
                    im3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }
                if (position == 3) {
                    im4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                }
                if (position == 4) {
                    im5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }
                if (position == 5) {
                    im6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }
                if (position == 6) {
                    im7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }
                if (position == 7) {
                    im8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }
                if (position == 8) {
                    im9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }

                if (playerHolder.overCheckbox.isChecked()) {
                    playerHolder.overCheckbox.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());

                    setAPlayerTextDisableEnableMode(playerHolder, false);
                }

                playerHolder.underCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setBPlayerTextDisableEnableMode(playerHolder, true);
            }
          /*  if (playerHolder.underCheckbox.isChecked()) {

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
            }*/
        }
    }


    private void setAPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
//        if (enable) {
//            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
//        } else {
//            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
//            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
//        }
        if (enable) {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setBPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
//        if (enable) {
//            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
//        } else {
//            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
//
//        }
        if (enable) {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }
        mCallback.onClick(new_pick_index);


    }

    public void firstAndLastCharacter(String str, String str1) {

        // Finding string length
        int n = str.length();
        int n1 = str1.length();

        // First character of a string
        char first = str.charAt(0);

        // Last character of a string
        char last = str1.charAt(0);

        System.out.println("First: " + first);
        System.out.println("Last: " + last);
    }

    private void setFirstPlayerTextEnableDisable(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            playerHolder.left_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.first_player_Name, mContext.getResources().getColor(R.color.text_color_new));

            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));


            if (position == 0) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im1.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb1.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    Log.e("099------------", f);
                    im1.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb1.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
                imb1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                imb2.setBackgroundColor(mContext.getResources().getColor(R.color.p1));


                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im2.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb2.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im2.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb2.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
            }

            if (position == 2) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));

                imb3.setBackgroundColor(mContext.getResources().getColor(R.color.p2));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im3.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb3.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im3.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb3.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
            }

            if (position == 3) {

                imb4.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im4.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb4.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im4.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb4.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
            }

            if (position == 4) {
                imb5.setBackgroundColor(mContext.getResources().getColor(R.color.p4));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im5.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb5.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im5.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb5.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                imb6.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im6.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb6.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im6.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb6.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
            }

            if (position == 6) {
                imb7.setBackgroundColor(mContext.getResources().getColor(R.color.p6));

                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im7.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb7.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im7.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb7.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
            }

            if (position == 7) {
                imb8.setBackgroundColor(mContext.getResources().getColor(R.color.p7));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im8.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb8.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im8.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb8.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                imb9.setBackgroundColor(mContext.getResources().getColor(R.color.p8));

                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    im9.setText(matchups.get(position).getPlayerA().getUniformNumber());
                    imb9.setText(matchups.get(position).getPlayerA().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerA().getFirstName();
                    String l = matchups.get(position).getPlayerA().getLastName();
                    im9.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb9.setText(f.substring(0, 1) + "" + l.substring(0, 1));
//                    im1.setText(firstAndLastCharacter(matchups.get(position).getPlayerA().getFirstName(),matchups.get(position).getPlayerA().getLastName()));
//                    imb1.setText(matchups.get(position).getPlayerA().getFirstName().charAt(0));
                }
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }

        } else {

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
//            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.left_matchcard_pick.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        mCallback.onClick(new_pick_index);

    }

    private void setSecondPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            playerHolder.right_matchcard_pick.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.second_player_Name, mContext.getResources().getColor(R.color.text_color_new));

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));


            if (position == 0) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));

                imb1.setBackgroundColor(mContext.getResources().getColor(R.color.p0));

                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im1.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb1.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im1.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb1.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
            }

            if (position == 1) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                imb2.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im2.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb2.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im2.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb2.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
            }

            if (position == 2) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                imb3.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im3.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb3.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im3.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb3.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
            }

            if (position == 3) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                imb4.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im4.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb4.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im4.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb4.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
            }

            if (position == 4) {
              if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im5.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb5.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im5.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb5.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
                imb5.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                imb6.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im6.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb6.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im6.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb6.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
            }

            if (position == 6) {
                imb7.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
               if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im7.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb7.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im7.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb7.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                imb8.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
               if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im8.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb8.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im8.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb8.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                imb9.setBackgroundColor(mContext.getResources().getColor(R.color.p9));
                if (matchups.get(position).getPlayerB().getUniformNumber() != null) {
                    im9.setText(matchups.get(position).getPlayerB().getUniformNumber());
                    imb9.setText(matchups.get(position).getPlayerB().getUniformNumber());
                } else {
                    String f = matchups.get(position).getPlayerB().getFirstName();
                    String l = matchups.get(position).getPlayerB().getLastName();
                    im9.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                    imb9.setText(f.substring(0, 1) + "" + l.substring(0, 1));
                }
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }

//            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));

        } else {

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.right_matchcard_pick.setBackgroundColor(mContext.getResources().getColor(R.color.white));


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
        RelativeLayout left_matchcard_pick;
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
        RelativeLayout right_matchcard_pick;

        //Player statistics
        CardView playerstatsViewSpinner;
        LinearLayout playerStatisticsView;
        ImageView arrow, go1;
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
        //    LinearLayout play_win,play_payout;
        CardView no_player;
        TextView tvPlayPoints;

        /*win loss status View*/
        FrameLayout win_loss_statusView_ly;
        CardView win_loss_statusView;
        TextView statusTxt;
        //  TextView play;

        //bingo
        CardView playerstatsViewSpinner_bingo;
        LinearLayout playerStatisticsView_bingo;
        ImageView arrow_bingo;
        RecyclerView playerStatsRcView_child_bingo;

        TextView play_bingo_right, play_bingo_left;

        TextView bc_1, bc_2, bc_3, bc_4, bc_5, bc_6, bc_7, bc_8, bc_9;

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
            this.left_matchcard_pick = itemView.findViewById(R.id.left_matchcard_pick);

            // PLAYER B
            this.second_player_img = (ImageView) itemView.findViewById(R.id.second_player_img);
            this.second_player_Name = (TextView) itemView.findViewById(R.id.second_player_Name);
            this.second_player_position = (TextView) itemView.findViewById(R.id.second_player_position);
            this.second_player_Match = (TextView) itemView.findViewById(R.id.second_player_Match);
            this.second_player_Time = (TextView) itemView.findViewById(R.id.second_player_time);
            this.second_player_price = (Button) itemView.findViewById(R.id.second_player_price);
            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
            this.second_playerCheck = (CheckBox) itemView.findViewById(R.id.second_playerCheck);
            this.right_matchcard_pick = itemView.findViewById(R.id.right_matchcard_pick);
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

//            this.play_payout = (LinearLayout) itemView.findViewById(R.id.play_payout);
//            this.play_win = (LinearLayout) itemView.findViewById(R.id.play_win);

            this.win_loss_statusView_ly = (FrameLayout) itemView.findViewById(R.id.win_loss_statusView_ly);
            this.win_loss_statusView = (CardView) itemView.findViewById(R.id.win_loss_statusView);
            win_loss_statusView.setVisibility(View.GONE);
            this.statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            this.go1 = itemView.findViewById(R.id.go1);
            //  this.play=(TextView) itemView.findViewById(R.id.play);
            //BINGO
            //Players ststistics
            this.playerstatsViewSpinner_bingo = (CardView) itemView.findViewById(R.id.playerstatsViewSpinner_bingo);
            this.playerStatisticsView_bingo = (LinearLayout) itemView.findViewById(R.id.playerStatisticsView_bingo);
            this.arrow_bingo = (ImageView) itemView.findViewById(R.id.arrow_bingo);
            this.playerStatsRcView_child_bingo = (RecyclerView) itemView.findViewById(R.id.playerStatsRcView_child_bingo);
            this.play_bingo_left = itemView.findViewById(R.id.play_bingo_left);
            this.play_bingo_right = itemView.findViewById(R.id.play_bingo_right);

            this.bc_1 = itemView.findViewById(R.id.bc_1);
            this.bc_2 = itemView.findViewById(R.id.bc_2);
            this.bc_3 = itemView.findViewById(R.id.bc_3);
            this.bc_4 = itemView.findViewById(R.id.bc_4);
            this.bc_5 = itemView.findViewById(R.id.bc_5);
            this.bc_6 = itemView.findViewById(R.id.bc_6);
            this.bc_7 = itemView.findViewById(R.id.bc_7);
            this.bc_8 = itemView.findViewById(R.id.bc_8);
            this.bc_9 = itemView.findViewById(R.id.bc_9);
        }

    }

}

//public class PlayPickGo_Adapter {
//}
